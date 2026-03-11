/*     */ package net.minecraft.world.level.chunk.storage;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortList;
/*     */ import it.unimi.dsi.fastutil.shorts.ShortListIterator;
/*     */ import java.util.Arrays;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.LongArrayTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerChunkCache;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ThreadedLevelLightEngine;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.Biomes;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.CarvingMask;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.DataLayer;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunkSection;
/*     */ import net.minecraft.world.level.chunk.PalettedContainer;
/*     */ import net.minecraft.world.level.chunk.PalettedContainerRO;
/*     */ import net.minecraft.world.level.chunk.ProtoChunk;
/*     */ import net.minecraft.world.level.chunk.UpgradeData;
/*     */ import net.minecraft.world.level.levelgen.BelowZeroRetrogen;
/*     */ import net.minecraft.world.level.levelgen.GenerationStep;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.blending.BlendingData;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.ticks.LevelChunkTicks;
/*     */ import net.minecraft.world.ticks.ProtoChunkTicks;
/*     */ 
/*     */ public class ChunkSerializer {
/*  68 */   private static final Codec<PalettedContainer<BlockState>> BLOCK_STATE_CODEC = PalettedContainer.codecRW((IdMap)Block.BLOCK_STATE_REGISTRY, BlockState.CODEC, PalettedContainer.Strategy.SECTION_STATES, Blocks.AIR.defaultBlockState());
/*  69 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final String TAG_UPGRADE_DATA = "UpgradeData"; private static final String BLOCK_TICKS_TAG = "block_ticks";
/*     */   private static final String FLUID_TICKS_TAG = "fluid_ticks";
/*     */   public static final String X_POS_TAG = "xPos";
/*     */   public static final String Z_POS_TAG = "zPos";
/*     */   public static final String HEIGHTMAPS_TAG = "Heightmaps";
/*     */   public static final String IS_LIGHT_ON_TAG = "isLightOn";
/*     */   public static final String SECTIONS_TAG = "sections";
/*     */   public static final String BLOCK_LIGHT_TAG = "BlockLight";
/*     */   public static final String SKY_LIGHT_TAG = "SkyLight";
/*     */   
/*     */   public static ProtoChunk read(ServerLevel $$0, PoiManager $$1, ChunkPos $$2, CompoundTag $$3) {
/*     */     BlendingData $$31;
/*     */     ProtoChunk protoChunk1;
/*  82 */     ChunkPos $$4 = new ChunkPos($$3.getInt("xPos"), $$3.getInt("zPos"));
/*  83 */     if (!Objects.equals($$2, $$4)) {
/*  84 */       LOGGER.error("Chunk file at {} is in the wrong location; relocating. (Expected {}, got {})", new Object[] { $$2, $$2, $$4 });
/*     */     }
/*     */     
/*  87 */     UpgradeData $$5 = $$3.contains("UpgradeData", 10) ? new UpgradeData($$3.getCompound("UpgradeData"), (LevelHeightAccessor)$$0) : UpgradeData.EMPTY;
/*     */     
/*  89 */     boolean $$6 = $$3.getBoolean("isLightOn");
/*     */     
/*  91 */     ListTag $$7 = $$3.getList("sections", 10);
/*  92 */     int $$8 = $$0.getSectionsCount();
/*  93 */     LevelChunkSection[] $$9 = new LevelChunkSection[$$8];
/*     */     
/*  95 */     boolean $$10 = $$0.dimensionType().hasSkyLight();
/*  96 */     ServerChunkCache serverChunkCache = $$0.getChunkSource();
/*     */     
/*  98 */     LevelLightEngine $$12 = serverChunkCache.getLightEngine();
/*     */     
/* 100 */     Registry<Biome> $$13 = $$0.registryAccess().registryOrThrow(Registries.BIOME);
/* 101 */     Codec<PalettedContainerRO<Holder<Biome>>> $$14 = makeBiomeCodec($$13);
/* 102 */     boolean $$15 = false;
/* 103 */     for (int $$16 = 0; $$16 < $$7.size(); $$16++) {
/* 104 */       CompoundTag $$17 = $$7.getCompound($$16);
/*     */       
/* 106 */       int $$18 = $$17.getByte("Y");
/* 107 */       int $$19 = $$0.getSectionIndexFromSectionY($$18);
/* 108 */       if ($$19 >= 0 && $$19 < $$9.length) {
/*     */         PalettedContainer<BlockState> $$21;
/*     */         PalettedContainer palettedContainer;
/* 111 */         if ($$17.contains("block_states", 10)) {
/* 112 */           Objects.requireNonNull(LOGGER); PalettedContainer<BlockState> $$20 = (PalettedContainer<BlockState>)BLOCK_STATE_CODEC.parse((DynamicOps)NbtOps.INSTANCE, $$17.getCompound("block_states")).promotePartial($$2 -> logErrors($$0, $$1, $$2)).getOrThrow(false, LOGGER::error);
/*     */         } else {
/* 114 */           $$21 = new PalettedContainer((IdMap)Block.BLOCK_STATE_REGISTRY, Blocks.AIR.defaultBlockState(), PalettedContainer.Strategy.SECTION_STATES);
/*     */         } 
/* 116 */         if ($$17.contains("biomes", 10)) {
/* 117 */           Objects.requireNonNull(LOGGER); PalettedContainerRO<Holder<Biome>> $$22 = (PalettedContainerRO<Holder<Biome>>)$$14.parse((DynamicOps)NbtOps.INSTANCE, $$17.getCompound("biomes")).promotePartial($$2 -> logErrors($$0, $$1, $$2)).getOrThrow(false, LOGGER::error);
/*     */         } else {
/* 119 */           palettedContainer = new PalettedContainer($$13.asHolderIdMap(), $$13.getHolderOrThrow(Biomes.PLAINS), PalettedContainer.Strategy.SECTION_BIOMES);
/*     */         } 
/* 121 */         LevelChunkSection $$24 = new LevelChunkSection($$21, (PalettedContainerRO)palettedContainer);
/* 122 */         $$9[$$19] = $$24;
/*     */         
/* 124 */         SectionPos $$25 = SectionPos.of($$2, $$18);
/* 125 */         $$1.checkConsistencyWithBlocks($$25, $$24);
/*     */       } 
/* 127 */       boolean $$26 = $$17.contains("BlockLight", 7);
/* 128 */       boolean $$27 = ($$10 && $$17.contains("SkyLight", 7));
/* 129 */       if ($$26 || $$27) {
/* 130 */         if (!$$15) {
/* 131 */           $$12.retainData($$2, true);
/* 132 */           $$15 = true;
/*     */         } 
/*     */         
/* 135 */         if ($$26) {
/* 136 */           $$12.queueSectionData(LightLayer.BLOCK, SectionPos.of($$2, $$18), new DataLayer($$17.getByteArray("BlockLight")));
/*     */         }
/*     */         
/* 139 */         if ($$27) {
/* 140 */           $$12.queueSectionData(LightLayer.SKY, SectionPos.of($$2, $$18), new DataLayer($$17.getByteArray("SkyLight")));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 145 */     long $$28 = $$3.getLong("InhabitedTime");
/*     */     
/* 147 */     ChunkStatus.ChunkType $$29 = getChunkTypeFromTag($$3);
/*     */ 
/*     */     
/* 150 */     if ($$3.contains("blending_data", 10)) {
/*     */       
/* 152 */       Objects.requireNonNull(LOGGER);
/* 153 */       BlendingData $$30 = BlendingData.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$3.getCompound("blending_data"))).resultOrPartial(LOGGER::error).orElse(null);
/*     */     } else {
/* 155 */       $$31 = null;
/*     */     } 
/*     */ 
/*     */     
/* 159 */     if ($$29 == ChunkStatus.ChunkType.LEVELCHUNK) {
/* 160 */       LevelChunkTicks<Block> $$32 = LevelChunkTicks.load($$3.getList("block_ticks", 10), $$0 -> BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse($$0)), $$2);
/* 161 */       LevelChunkTicks<Fluid> $$33 = LevelChunkTicks.load($$3.getList("fluid_ticks", 10), $$0 -> BuiltInRegistries.FLUID.getOptional(ResourceLocation.tryParse($$0)), $$2);
/*     */       
/* 163 */       LevelChunk levelChunk = new LevelChunk((Level)$$0.getLevel(), $$2, $$5, $$32, $$33, $$28, $$9, postLoadChunk($$0, $$3), $$31);
/*     */     } else {
/* 165 */       ProtoChunkTicks<Block> $$35 = ProtoChunkTicks.load($$3.getList("block_ticks", 10), $$0 -> BuiltInRegistries.BLOCK.getOptional(ResourceLocation.tryParse($$0)), $$2);
/* 166 */       ProtoChunkTicks<Fluid> $$36 = ProtoChunkTicks.load($$3.getList("fluid_ticks", 10), $$0 -> BuiltInRegistries.FLUID.getOptional(ResourceLocation.tryParse($$0)), $$2);
/*     */       
/* 168 */       ProtoChunk $$37 = new ProtoChunk($$2, $$5, $$9, $$35, $$36, (LevelHeightAccessor)$$0, $$13, $$31);
/* 169 */       protoChunk1 = $$37;
/* 170 */       protoChunk1.setInhabitedTime($$28);
/*     */       
/* 172 */       if ($$3.contains("below_zero_retrogen", 10)) {
/*     */         
/* 174 */         Objects.requireNonNull(LOGGER);
/* 175 */         Objects.requireNonNull($$37); BelowZeroRetrogen.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$3.getCompound("below_zero_retrogen"))).resultOrPartial(LOGGER::error).ifPresent($$37::setBelowZeroRetrogen);
/*     */       } 
/*     */       
/* 178 */       ChunkStatus $$39 = ChunkStatus.byName($$3.getString("Status"));
/* 179 */       $$37.setStatus($$39);
/* 180 */       if ($$39.isOrAfter(ChunkStatus.INITIALIZE_LIGHT)) {
/* 181 */         $$37.setLightEngine($$12);
/*     */       }
/*     */     } 
/*     */     
/* 185 */     protoChunk1.setLightCorrect($$6);
/*     */     
/* 187 */     CompoundTag $$40 = $$3.getCompound("Heightmaps");
/*     */     
/* 189 */     EnumSet<Heightmap.Types> $$41 = EnumSet.noneOf(Heightmap.Types.class);
/*     */     
/* 191 */     for (Heightmap.Types $$42 : protoChunk1.getStatus().heightmapsAfter()) {
/* 192 */       String $$43 = $$42.getSerializationKey();
/*     */       
/* 194 */       if ($$40.contains($$43, 12)) {
/* 195 */         protoChunk1.setHeightmap($$42, $$40.getLongArray($$43)); continue;
/*     */       } 
/* 197 */       $$41.add($$42);
/*     */     } 
/*     */ 
/*     */     
/* 201 */     Heightmap.primeHeightmaps((ChunkAccess)protoChunk1, $$41);
/*     */     
/* 203 */     CompoundTag $$44 = $$3.getCompound("structures");
/* 204 */     protoChunk1.setAllStarts(unpackStructureStart(StructurePieceSerializationContext.fromLevel($$0), $$44, $$0.getSeed()));
/* 205 */     protoChunk1.setAllReferences(unpackStructureReferences($$0.registryAccess(), $$2, $$44));
/*     */     
/* 207 */     if ($$3.getBoolean("shouldSave")) {
/* 208 */       protoChunk1.setUnsaved(true);
/*     */     }
/*     */     
/* 211 */     ListTag $$45 = $$3.getList("PostProcessing", 9);
/* 212 */     for (int $$46 = 0; $$46 < $$45.size(); $$46++) {
/* 213 */       ListTag $$47 = $$45.getList($$46);
/* 214 */       for (int $$48 = 0; $$48 < $$47.size(); $$48++) {
/* 215 */         protoChunk1.addPackedPostProcess($$47.getShort($$48), $$46);
/*     */       }
/*     */     } 
/*     */     
/* 219 */     if ($$29 == ChunkStatus.ChunkType.LEVELCHUNK) {
/* 220 */       return (ProtoChunk)new ImposterProtoChunk((LevelChunk)protoChunk1, false);
/*     */     }
/*     */     
/* 223 */     ProtoChunk $$49 = protoChunk1;
/* 224 */     ListTag $$50 = $$3.getList("entities", 10);
/* 225 */     for (int $$51 = 0; $$51 < $$50.size(); $$51++) {
/* 226 */       $$49.addEntity($$50.getCompound($$51));
/*     */     }
/*     */     
/* 229 */     ListTag $$52 = $$3.getList("block_entities", 10);
/* 230 */     for (int $$53 = 0; $$53 < $$52.size(); $$53++) {
/* 231 */       CompoundTag $$54 = $$52.getCompound($$53);
/* 232 */       protoChunk1.setBlockEntityNbt($$54);
/*     */     } 
/*     */     
/* 235 */     CompoundTag $$55 = $$3.getCompound("CarvingMasks");
/* 236 */     for (String $$56 : $$55.getAllKeys()) {
/* 237 */       GenerationStep.Carving $$57 = GenerationStep.Carving.valueOf($$56);
/* 238 */       $$49.setCarvingMask($$57, new CarvingMask($$55.getLongArray($$56), protoChunk1.getMinBuildHeight()));
/*     */     } 
/*     */     
/* 241 */     return $$49;
/*     */   }
/*     */   
/*     */   private static void logErrors(ChunkPos $$0, int $$1, String $$2) {
/* 245 */     LOGGER.error("Recoverable errors when loading section [" + $$0.x + ", " + $$1 + ", " + $$0.z + "]: " + $$2);
/*     */   }
/*     */   
/*     */   private static Codec<PalettedContainerRO<Holder<Biome>>> makeBiomeCodec(Registry<Biome> $$0) {
/* 249 */     return PalettedContainer.codecRO($$0.asHolderIdMap(), $$0.holderByNameCodec(), PalettedContainer.Strategy.SECTION_BIOMES, $$0.getHolderOrThrow(Biomes.PLAINS));
/*     */   }
/*     */   
/*     */   public static CompoundTag write(ServerLevel $$0, ChunkAccess $$1) {
/* 253 */     ChunkPos $$2 = $$1.getPos();
/* 254 */     CompoundTag $$3 = NbtUtils.addCurrentDataVersion(new CompoundTag());
/* 255 */     $$3.putInt("xPos", $$2.x);
/* 256 */     $$3.putInt("yPos", $$1.getMinSection());
/* 257 */     $$3.putInt("zPos", $$2.z);
/* 258 */     $$3.putLong("LastUpdate", $$0.getGameTime());
/* 259 */     $$3.putLong("InhabitedTime", $$1.getInhabitedTime());
/* 260 */     $$3.putString("Status", BuiltInRegistries.CHUNK_STATUS.getKey($$1.getStatus()).toString());
/* 261 */     BlendingData $$4 = $$1.getBlendingData();
/* 262 */     if ($$4 != null) {
/*     */       
/* 264 */       Objects.requireNonNull(LOGGER); BlendingData.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$4).resultOrPartial(LOGGER::error)
/* 265 */         .ifPresent($$1 -> $$0.put("blending_data", $$1));
/*     */     } 
/*     */     
/* 268 */     BelowZeroRetrogen $$5 = $$1.getBelowZeroRetrogen();
/* 269 */     if ($$5 != null) {
/*     */       
/* 271 */       Objects.requireNonNull(LOGGER); BelowZeroRetrogen.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$5).resultOrPartial(LOGGER::error)
/* 272 */         .ifPresent($$1 -> $$0.put("below_zero_retrogen", $$1));
/*     */     } 
/*     */     
/* 275 */     UpgradeData $$6 = $$1.getUpgradeData();
/* 276 */     if (!$$6.isEmpty()) {
/* 277 */       $$3.put("UpgradeData", (Tag)$$6.write());
/*     */     }
/*     */     
/* 280 */     LevelChunkSection[] $$7 = $$1.getSections();
/* 281 */     ListTag $$8 = new ListTag();
/*     */     
/* 283 */     ThreadedLevelLightEngine threadedLevelLightEngine = $$0.getChunkSource().getLightEngine();
/*     */     
/* 285 */     Registry<Biome> $$10 = $$0.registryAccess().registryOrThrow(Registries.BIOME);
/* 286 */     Codec<PalettedContainerRO<Holder<Biome>>> $$11 = makeBiomeCodec($$10);
/* 287 */     boolean $$12 = $$1.isLightCorrect();
/* 288 */     for (int $$13 = threadedLevelLightEngine.getMinLightSection(); $$13 < threadedLevelLightEngine.getMaxLightSection(); $$13++) {
/*     */       
/* 290 */       int $$14 = $$1.getSectionIndexFromSectionY($$13);
/* 291 */       boolean $$15 = ($$14 >= 0 && $$14 < $$7.length);
/*     */       
/* 293 */       DataLayer $$16 = threadedLevelLightEngine.getLayerListener(LightLayer.BLOCK).getDataLayerData(SectionPos.of($$2, $$13));
/* 294 */       DataLayer $$17 = threadedLevelLightEngine.getLayerListener(LightLayer.SKY).getDataLayerData(SectionPos.of($$2, $$13));
/*     */       
/* 296 */       if ($$15 || $$16 != null || $$17 != null) {
/*     */ 
/*     */ 
/*     */         
/* 300 */         CompoundTag $$18 = new CompoundTag();
/*     */         
/* 302 */         if ($$15) {
/* 303 */           LevelChunkSection $$19 = $$7[$$14];
/* 304 */           Objects.requireNonNull(LOGGER); $$18.put("block_states", (Tag)BLOCK_STATE_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$19.getStates()).getOrThrow(false, LOGGER::error));
/* 305 */           Objects.requireNonNull(LOGGER); $$18.put("biomes", (Tag)$$11.encodeStart((DynamicOps)NbtOps.INSTANCE, $$19.getBiomes()).getOrThrow(false, LOGGER::error));
/*     */         } 
/* 307 */         if ($$16 != null && !$$16.isEmpty()) {
/* 308 */           $$18.putByteArray("BlockLight", $$16.getData());
/*     */         }
/* 310 */         if ($$17 != null && !$$17.isEmpty()) {
/* 311 */           $$18.putByteArray("SkyLight", $$17.getData());
/*     */         }
/*     */         
/* 314 */         if (!$$18.isEmpty()) {
/* 315 */           $$18.putByte("Y", (byte)$$13);
/* 316 */           $$8.add($$18);
/*     */         } 
/*     */       } 
/* 319 */     }  $$3.put("sections", (Tag)$$8);
/*     */     
/* 321 */     if ($$12) {
/* 322 */       $$3.putBoolean("isLightOn", true);
/*     */     }
/*     */ 
/*     */     
/* 326 */     ListTag $$20 = new ListTag();
/* 327 */     for (BlockPos $$21 : $$1.getBlockEntitiesPos()) {
/* 328 */       CompoundTag $$22 = $$1.getBlockEntityNbtForSaving($$21);
/* 329 */       if ($$22 != null) {
/* 330 */         $$20.add($$22);
/*     */       }
/*     */     } 
/* 333 */     $$3.put("block_entities", (Tag)$$20);
/*     */     
/* 335 */     if ($$1.getStatus().getChunkType() == ChunkStatus.ChunkType.PROTOCHUNK) {
/*     */       
/* 337 */       ProtoChunk $$23 = (ProtoChunk)$$1;
/*     */       
/* 339 */       ListTag $$24 = new ListTag();
/* 340 */       $$24.addAll($$23.getEntities());
/* 341 */       $$3.put("entities", (Tag)$$24);
/*     */ 
/*     */       
/* 344 */       CompoundTag $$25 = new CompoundTag();
/* 345 */       for (GenerationStep.Carving $$26 : GenerationStep.Carving.values()) {
/* 346 */         CarvingMask $$27 = $$23.getCarvingMask($$26);
/* 347 */         if ($$27 != null) {
/* 348 */           $$25.putLongArray($$26.toString(), $$27.toArray());
/*     */         }
/*     */       } 
/* 351 */       $$3.put("CarvingMasks", (Tag)$$25);
/*     */     } 
/*     */     
/* 354 */     saveTicks($$0, $$3, $$1.getTicksForSerialization());
/*     */ 
/*     */     
/* 357 */     $$3.put("PostProcessing", (Tag)packOffsets($$1.getPostProcessing()));
/*     */     
/* 359 */     CompoundTag $$28 = new CompoundTag();
/* 360 */     for (Map.Entry<Heightmap.Types, Heightmap> $$29 : (Iterable<Map.Entry<Heightmap.Types, Heightmap>>)$$1.getHeightmaps()) {
/* 361 */       if ($$1.getStatus().heightmapsAfter().contains($$29.getKey())) {
/* 362 */         $$28.put(((Heightmap.Types)$$29.getKey()).getSerializationKey(), (Tag)new LongArrayTag(((Heightmap)$$29.getValue()).getRawData()));
/*     */       }
/*     */     } 
/* 365 */     $$3.put("Heightmaps", (Tag)$$28);
/*     */     
/* 367 */     $$3.put("structures", (Tag)packStructureData(StructurePieceSerializationContext.fromLevel($$0), $$2, $$1.getAllStarts(), $$1.getAllReferences()));
/* 368 */     return $$3;
/*     */   }
/*     */   
/*     */   private static void saveTicks(ServerLevel $$0, CompoundTag $$1, ChunkAccess.TicksToSave $$2) {
/* 372 */     long $$3 = $$0.getLevelData().getGameTime();
/* 373 */     $$1.put("block_ticks", $$2.blocks().save($$3, $$0 -> BuiltInRegistries.BLOCK.getKey($$0).toString()));
/* 374 */     $$1.put("fluid_ticks", $$2.fluids().save($$3, $$0 -> BuiltInRegistries.FLUID.getKey($$0).toString()));
/*     */   }
/*     */   
/*     */   public static ChunkStatus.ChunkType getChunkTypeFromTag(@Nullable CompoundTag $$0) {
/* 378 */     if ($$0 != null) {
/* 379 */       return ChunkStatus.byName($$0.getString("Status")).getChunkType();
/*     */     }
/* 381 */     return ChunkStatus.ChunkType.PROTOCHUNK;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static LevelChunk.PostLoadProcessor postLoadChunk(ServerLevel $$0, CompoundTag $$1) {
/* 386 */     ListTag $$2 = getListOfCompoundsOrNull($$1, "entities");
/* 387 */     ListTag $$3 = getListOfCompoundsOrNull($$1, "block_entities");
/*     */     
/* 389 */     if ($$2 == null && $$3 == null) {
/* 390 */       return null;
/*     */     }
/*     */     
/* 393 */     return $$3 -> {
/*     */         if ($$0 != null) {
/*     */           $$1.addLegacyChunkEntities(EntityType.loadEntitiesRecursive((List)$$0, (Level)$$1));
/*     */         }
/*     */         if ($$2 != null) {
/*     */           for (int $$4 = 0; $$4 < $$2.size(); $$4++) {
/*     */             CompoundTag $$5 = $$2.getCompound($$4);
/*     */             boolean $$6 = $$5.getBoolean("keepPacked");
/*     */             if ($$6) {
/*     */               $$3.setBlockEntityNbt($$5);
/*     */             } else {
/*     */               BlockPos $$7 = BlockEntity.getPosFromTag($$5);
/*     */               BlockEntity $$8 = BlockEntity.loadStatic($$7, $$3.getBlockState($$7), $$5);
/*     */               if ($$8 != null) {
/*     */                 $$3.setBlockEntity($$8);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private static ListTag getListOfCompoundsOrNull(CompoundTag $$0, String $$1) {
/* 420 */     ListTag $$2 = $$0.getList($$1, 10);
/* 421 */     return $$2.isEmpty() ? null : $$2;
/*     */   }
/*     */   
/*     */   private static CompoundTag packStructureData(StructurePieceSerializationContext $$0, ChunkPos $$1, Map<Structure, StructureStart> $$2, Map<Structure, LongSet> $$3) {
/* 425 */     CompoundTag $$4 = new CompoundTag();
/*     */     
/* 427 */     CompoundTag $$5 = new CompoundTag();
/* 428 */     Registry<Structure> $$6 = $$0.registryAccess().registryOrThrow(Registries.STRUCTURE);
/* 429 */     for (Map.Entry<Structure, StructureStart> $$7 : $$2.entrySet()) {
/* 430 */       ResourceLocation $$8 = $$6.getKey($$7.getKey());
/* 431 */       $$5.put($$8.toString(), (Tag)((StructureStart)$$7.getValue()).createTag($$0, $$1));
/*     */     } 
/* 433 */     $$4.put("starts", (Tag)$$5);
/*     */     
/* 435 */     CompoundTag $$9 = new CompoundTag();
/* 436 */     for (Map.Entry<Structure, LongSet> $$10 : $$3.entrySet()) {
/* 437 */       if (((LongSet)$$10.getValue()).isEmpty()) {
/*     */         continue;
/*     */       }
/* 440 */       ResourceLocation $$11 = $$6.getKey($$10.getKey());
/* 441 */       $$9.put($$11.toString(), (Tag)new LongArrayTag($$10.getValue()));
/*     */     } 
/* 443 */     $$4.put("References", (Tag)$$9);
/*     */     
/* 445 */     return $$4;
/*     */   }
/*     */   
/*     */   private static Map<Structure, StructureStart> unpackStructureStart(StructurePieceSerializationContext $$0, CompoundTag $$1, long $$2) {
/* 449 */     Map<Structure, StructureStart> $$3 = Maps.newHashMap();
/*     */     
/* 451 */     Registry<Structure> $$4 = $$0.registryAccess().registryOrThrow(Registries.STRUCTURE);
/* 452 */     CompoundTag $$5 = $$1.getCompound("starts");
/* 453 */     for (String $$6 : $$5.getAllKeys()) {
/* 454 */       ResourceLocation $$7 = ResourceLocation.tryParse($$6);
/* 455 */       Structure $$8 = (Structure)$$4.get($$7);
/* 456 */       if ($$8 == null) {
/* 457 */         LOGGER.error("Unknown structure start: {}", $$7);
/*     */         continue;
/*     */       } 
/* 460 */       StructureStart $$9 = StructureStart.loadStaticStart($$0, $$5.getCompound($$6), $$2);
/* 461 */       if ($$9 != null) {
/* 462 */         $$3.put($$8, $$9);
/*     */       }
/*     */     } 
/*     */     
/* 466 */     return $$3;
/*     */   }
/*     */   
/*     */   private static Map<Structure, LongSet> unpackStructureReferences(RegistryAccess $$0, ChunkPos $$1, CompoundTag $$2) {
/* 470 */     Map<Structure, LongSet> $$3 = Maps.newHashMap();
/*     */     
/* 472 */     Registry<Structure> $$4 = $$0.registryOrThrow(Registries.STRUCTURE);
/* 473 */     CompoundTag $$5 = $$2.getCompound("References");
/* 474 */     for (String $$6 : $$5.getAllKeys()) {
/*     */       
/* 476 */       ResourceLocation $$7 = ResourceLocation.tryParse($$6);
/* 477 */       Structure $$8 = (Structure)$$4.get($$7);
/* 478 */       if ($$8 == null) {
/* 479 */         LOGGER.warn("Found reference to unknown structure '{}' in chunk {}, discarding", $$7, $$1);
/*     */         continue;
/*     */       } 
/* 482 */       long[] $$9 = $$5.getLongArray($$6);
/* 483 */       if ($$9.length == 0) {
/*     */         continue;
/*     */       }
/* 486 */       $$3.put($$8, new LongOpenHashSet(Arrays.stream($$9).filter($$2 -> {
/*     */                 ChunkPos $$3 = new ChunkPos($$2);
/*     */                 
/*     */                 if ($$3.getChessboardDistance($$0) > 8) {
/*     */                   LOGGER.warn("Found invalid structure reference [ {} @ {} ] for chunk {}.", new Object[] { $$1, $$3, $$0 });
/*     */                   return false;
/*     */                 } 
/*     */                 return true;
/* 494 */               }).toArray()));
/*     */     } 
/*     */     
/* 497 */     return $$3;
/*     */   }
/*     */   
/*     */   public static ListTag packOffsets(ShortList[] $$0) {
/* 501 */     ListTag $$1 = new ListTag();
/* 502 */     for (ShortList $$2 : $$0) {
/* 503 */       ListTag $$3 = new ListTag();
/* 504 */       if ($$2 != null) {
/* 505 */         for (ShortListIterator<Short> shortListIterator = $$2.iterator(); shortListIterator.hasNext(); ) { Short $$4 = shortListIterator.next();
/* 506 */           $$3.add(ShortTag.valueOf($$4.shortValue())); }
/*     */       
/*     */       }
/* 509 */       $$1.add($$3);
/*     */     } 
/* 511 */     return $$1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\ChunkSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */