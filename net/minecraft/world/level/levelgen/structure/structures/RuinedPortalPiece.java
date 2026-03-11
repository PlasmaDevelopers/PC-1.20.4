/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function6;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.Dynamic;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.VineBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlackstoneReplaceProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.LavaSubmergedBlockProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ public class RuinedPortalPiece extends TemplateStructurePiece {
/*  53 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final float PROBABILITY_OF_GOLD_GONE = 0.3F; private static final float PROBABILITY_OF_MAGMA_INSTEAD_OF_NETHERRACK = 0.07F; private static final float PROBABILITY_OF_MAGMA_INSTEAD_OF_LAVA = 0.2F; private final VerticalPlacement verticalPlacement; private final Properties properties;
/*     */   
/*     */   public static class Properties { public static final Codec<Properties> CODEC;
/*     */     public boolean cold;
/*     */     public float mossiness;
/*     */     public boolean airPocket;
/*     */     public boolean overgrown;
/*     */     public boolean vines;
/*     */     public boolean replaceWithBlackstone;
/*     */     
/*     */     static {
/*  64 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.BOOL.fieldOf("cold").forGetter(()), (App)Codec.FLOAT.fieldOf("mossiness").forGetter(()), (App)Codec.BOOL.fieldOf("air_pocket").forGetter(()), (App)Codec.BOOL.fieldOf("overgrown").forGetter(()), (App)Codec.BOOL.fieldOf("vines").forGetter(()), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(())).apply((Applicative)$$0, Properties::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Properties() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Properties(boolean $$0, float $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5) {
/*  84 */       this.cold = $$0;
/*  85 */       this.mossiness = $$1;
/*  86 */       this.airPocket = $$2;
/*  87 */       this.overgrown = $$3;
/*  88 */       this.vines = $$4;
/*  89 */       this.replaceWithBlackstone = $$5;
/*     */     } }
/*     */ 
/*     */   
/*     */   public RuinedPortalPiece(StructureTemplateManager $$0, BlockPos $$1, VerticalPlacement $$2, Properties $$3, ResourceLocation $$4, StructureTemplate $$5, Rotation $$6, Mirror $$7, BlockPos $$8) {
/*  94 */     super(StructurePieceType.RUINED_PORTAL, 0, $$0, $$4, $$4.toString(), makeSettings($$7, $$6, $$2, $$8, $$3), $$1);
/*     */     
/*  96 */     this.verticalPlacement = $$2;
/*  97 */     this.properties = $$3;
/*     */   }
/*     */   
/*     */   public RuinedPortalPiece(StructureTemplateManager $$0, CompoundTag $$1) {
/* 101 */     super(StructurePieceType.RUINED_PORTAL, $$1, $$0, $$2 -> makeSettings($$0, $$1, $$2));
/*     */     
/* 103 */     this.verticalPlacement = VerticalPlacement.byName($$1.getString("VerticalPlacement"));
/* 104 */     Objects.requireNonNull(LOGGER); this.properties = (Properties)Properties.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$1.get("Properties"))).getOrThrow(true, LOGGER::error);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 109 */     super.addAdditionalSaveData($$0, $$1);
/* 110 */     $$1.putString("Rotation", this.placeSettings.getRotation().name());
/* 111 */     $$1.putString("Mirror", this.placeSettings.getMirror().name());
/* 112 */     $$1.putString("VerticalPlacement", this.verticalPlacement.getName());
/* 113 */     Objects.requireNonNull(LOGGER); Properties.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, this.properties).resultOrPartial(LOGGER::error).ifPresent($$1 -> $$0.put("Properties", $$1));
/*     */   }
/*     */   
/*     */   private static StructurePlaceSettings makeSettings(StructureTemplateManager $$0, CompoundTag $$1, ResourceLocation $$2) {
/* 117 */     StructureTemplate $$3 = $$0.getOrCreate($$2);
/*     */     
/* 119 */     BlockPos $$4 = new BlockPos($$3.getSize().getX() / 2, 0, $$3.getSize().getZ() / 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     Objects.requireNonNull(LOGGER); return makeSettings(Mirror.valueOf($$1.getString("Mirror")), Rotation.valueOf($$1.getString("Rotation")), VerticalPlacement.byName($$1.getString("VerticalPlacement")), $$4, (Properties)Properties.CODEC.parse(new Dynamic((DynamicOps)NbtOps.INSTANCE, $$1.get("Properties"))).getOrThrow(true, LOGGER::error));
/*     */   }
/*     */ 
/*     */   
/*     */   private static StructurePlaceSettings makeSettings(Mirror $$0, Rotation $$1, VerticalPlacement $$2, BlockPos $$3, Properties $$4) {
/* 130 */     BlockIgnoreProcessor $$5 = $$4.airPocket ? BlockIgnoreProcessor.STRUCTURE_BLOCK : BlockIgnoreProcessor.STRUCTURE_AND_AIR;
/*     */     
/* 132 */     List<ProcessorRule> $$6 = Lists.newArrayList();
/* 133 */     $$6.add(getBlockReplaceRule(Blocks.GOLD_BLOCK, 0.3F, Blocks.AIR));
/* 134 */     $$6.add(getLavaProcessorRule($$2, $$4));
/* 135 */     if (!$$4.cold) {
/* 136 */       $$6.add(getBlockReplaceRule(Blocks.NETHERRACK, 0.07F, Blocks.MAGMA_BLOCK));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     StructurePlaceSettings $$7 = (new StructurePlaceSettings()).setRotation($$1).setMirror($$0).setRotationPivot($$3).addProcessor((StructureProcessor)$$5).addProcessor((StructureProcessor)new RuleProcessor($$6)).addProcessor((StructureProcessor)new BlockAgeProcessor($$4.mossiness)).addProcessor((StructureProcessor)new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)).addProcessor((StructureProcessor)new LavaSubmergedBlockProcessor());
/*     */     
/* 149 */     if ($$4.replaceWithBlackstone) {
/* 150 */       $$7.addProcessor((StructureProcessor)BlackstoneReplaceProcessor.INSTANCE);
/*     */     }
/* 152 */     return $$7;
/*     */   }
/*     */   
/*     */   private static ProcessorRule getLavaProcessorRule(VerticalPlacement $$0, Properties $$1) {
/* 156 */     if ($$0 == VerticalPlacement.ON_OCEAN_FLOOR)
/* 157 */       return getBlockReplaceRule(Blocks.LAVA, Blocks.MAGMA_BLOCK); 
/* 158 */     if ($$1.cold) {
/* 159 */       return getBlockReplaceRule(Blocks.LAVA, Blocks.NETHERRACK);
/*     */     }
/* 161 */     return getBlockReplaceRule(Blocks.LAVA, 0.2F, Blocks.MAGMA_BLOCK);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 168 */     BoundingBox $$7 = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
/* 169 */     if (!$$4.isInside((Vec3i)$$7.getCenter())) {
/*     */       return;
/*     */     }
/*     */     
/* 173 */     $$4.encapsulate($$7);
/*     */     
/* 175 */     super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */     
/* 177 */     spreadNetherrack($$3, (LevelAccessor)$$0);
/* 178 */     addNetherrackDripColumnsBelowPortal($$3, (LevelAccessor)$$0);
/*     */     
/* 180 */     if (this.properties.vines || this.properties.overgrown) {
/* 181 */       BlockPos.betweenClosedStream(getBoundingBox()).forEach($$2 -> {
/*     */             if (this.properties.vines) {
/*     */               maybeAddVines($$0, (LevelAccessor)$$1, $$2);
/*     */             }
/*     */             if (this.properties.overgrown) {
/*     */               maybeAddLeavesAbove($$0, (LevelAccessor)$$1, $$2);
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {}
/*     */ 
/*     */   
/*     */   private void maybeAddVines(RandomSource $$0, LevelAccessor $$1, BlockPos $$2) {
/* 197 */     BlockState $$3 = $$1.getBlockState($$2);
/* 198 */     if ($$3.isAir() || $$3.is(Blocks.VINE)) {
/*     */       return;
/*     */     }
/*     */     
/* 202 */     Direction $$4 = getRandomHorizontalDirection($$0);
/* 203 */     BlockPos $$5 = $$2.relative($$4);
/* 204 */     BlockState $$6 = $$1.getBlockState($$5);
/* 205 */     if (!$$6.isAir()) {
/*     */       return;
/*     */     }
/* 208 */     if (!Block.isFaceFull($$3.getCollisionShape((BlockGetter)$$1, $$2), $$4)) {
/*     */       return;
/*     */     }
/* 211 */     BooleanProperty $$7 = VineBlock.getPropertyForFace($$4.getOpposite());
/* 212 */     $$1.setBlock($$5, (BlockState)Blocks.VINE.defaultBlockState().setValue((Property)$$7, Boolean.valueOf(true)), 3);
/*     */   }
/*     */   
/*     */   private void maybeAddLeavesAbove(RandomSource $$0, LevelAccessor $$1, BlockPos $$2) {
/* 216 */     if ($$0.nextFloat() < 0.5F && $$1.getBlockState($$2).is(Blocks.NETHERRACK) && $$1.getBlockState($$2.above()).isAir()) {
/* 217 */       $$1.setBlock($$2.above(), (BlockState)Blocks.JUNGLE_LEAVES.defaultBlockState().setValue((Property)LeavesBlock.PERSISTENT, Boolean.valueOf(true)), 3);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addNetherrackDripColumnsBelowPortal(RandomSource $$0, LevelAccessor $$1) {
/* 222 */     for (int $$2 = this.boundingBox.minX() + 1; $$2 < this.boundingBox.maxX(); $$2++) {
/* 223 */       for (int $$3 = this.boundingBox.minZ() + 1; $$3 < this.boundingBox.maxZ(); $$3++) {
/* 224 */         BlockPos $$4 = new BlockPos($$2, this.boundingBox.minY(), $$3);
/* 225 */         if ($$1.getBlockState($$4).is(Blocks.NETHERRACK)) {
/* 226 */           addNetherrackDripColumn($$0, $$1, $$4.below());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addNetherrackDripColumn(RandomSource $$0, LevelAccessor $$1, BlockPos $$2) {
/* 233 */     BlockPos.MutableBlockPos $$3 = $$2.mutable();
/* 234 */     placeNetherrackOrMagma($$0, $$1, (BlockPos)$$3);
/* 235 */     int $$4 = 8;
/* 236 */     while ($$4 > 0 && $$0.nextFloat() < 0.5F) {
/* 237 */       $$3.move(Direction.DOWN);
/* 238 */       $$4--;
/* 239 */       placeNetherrackOrMagma($$0, $$1, (BlockPos)$$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spreadNetherrack(RandomSource $$0, LevelAccessor $$1) {
/* 244 */     boolean $$2 = (this.verticalPlacement == VerticalPlacement.ON_LAND_SURFACE || this.verticalPlacement == VerticalPlacement.ON_OCEAN_FLOOR);
/*     */     
/* 246 */     BlockPos $$3 = this.boundingBox.getCenter();
/* 247 */     int $$4 = $$3.getX();
/* 248 */     int $$5 = $$3.getZ();
/*     */     
/* 250 */     float[] $$6 = { 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 0.9F, 0.9F, 0.8F, 0.7F, 0.6F, 0.4F, 0.2F };
/* 251 */     int $$7 = $$6.length;
/* 252 */     int $$8 = (this.boundingBox.getXSpan() + this.boundingBox.getZSpan()) / 2;
/* 253 */     int $$9 = $$0.nextInt(Math.max(1, 8 - $$8 / 2));
/* 254 */     int $$10 = 3;
/* 255 */     BlockPos.MutableBlockPos $$11 = BlockPos.ZERO.mutable();
/* 256 */     for (int $$12 = $$4 - $$7; $$12 <= $$4 + $$7; $$12++) {
/* 257 */       for (int $$13 = $$5 - $$7; $$13 <= $$5 + $$7; $$13++) {
/* 258 */         int $$14 = Math.abs($$12 - $$4) + Math.abs($$13 - $$5);
/* 259 */         int $$15 = Math.max(0, $$14 + $$9);
/* 260 */         if ($$15 < $$7) {
/*     */ 
/*     */           
/* 263 */           float $$16 = $$6[$$15];
/* 264 */           if ($$0.nextDouble() < $$16) {
/* 265 */             int $$17 = getSurfaceY($$1, $$12, $$13, this.verticalPlacement);
/* 266 */             int $$18 = $$2 ? $$17 : Math.min(this.boundingBox.minY(), $$17);
/* 267 */             $$11.set($$12, $$18, $$13);
/* 268 */             if (Math.abs($$18 - this.boundingBox.minY()) <= 3 && canBlockBeReplacedByNetherrackOrMagma($$1, (BlockPos)$$11)) {
/* 269 */               placeNetherrackOrMagma($$0, $$1, (BlockPos)$$11);
/* 270 */               if (this.properties.overgrown) {
/* 271 */                 maybeAddLeavesAbove($$0, $$1, (BlockPos)$$11);
/*     */               }
/* 273 */               addNetherrackDripColumn($$0, $$1, $$11.below());
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean canBlockBeReplacedByNetherrackOrMagma(LevelAccessor $$0, BlockPos $$1) {
/* 281 */     BlockState $$2 = $$0.getBlockState($$1);
/* 282 */     return (!$$2.is(Blocks.AIR) && 
/* 283 */       !$$2.is(Blocks.OBSIDIAN) && 
/* 284 */       !$$2.is(BlockTags.FEATURES_CANNOT_REPLACE) && (this.verticalPlacement == VerticalPlacement.IN_NETHER || 
/* 285 */       !$$2.is(Blocks.LAVA)));
/*     */   }
/*     */   
/*     */   private void placeNetherrackOrMagma(RandomSource $$0, LevelAccessor $$1, BlockPos $$2) {
/* 289 */     if (!this.properties.cold && $$0.nextFloat() < 0.07F) {
/* 290 */       $$1.setBlock($$2, Blocks.MAGMA_BLOCK.defaultBlockState(), 3);
/*     */     } else {
/* 292 */       $$1.setBlock($$2, Blocks.NETHERRACK.defaultBlockState(), 3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getSurfaceY(LevelAccessor $$0, int $$1, int $$2, VerticalPlacement $$3) {
/* 297 */     return $$0.getHeight(getHeightMapType($$3), $$1, $$2) - 1;
/*     */   }
/*     */   
/*     */   public static Heightmap.Types getHeightMapType(VerticalPlacement $$0) {
/* 301 */     return ($$0 == VerticalPlacement.ON_OCEAN_FLOOR) ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
/*     */   }
/*     */   
/*     */   private static ProcessorRule getBlockReplaceRule(Block $$0, float $$1, Block $$2) {
/* 305 */     return new ProcessorRule((RuleTest)new RandomBlockMatchTest($$0, $$1), (RuleTest)AlwaysTrueTest.INSTANCE, $$2.defaultBlockState());
/*     */   }
/*     */   
/*     */   private static ProcessorRule getBlockReplaceRule(Block $$0, Block $$1) {
/* 309 */     return new ProcessorRule((RuleTest)new BlockMatchTest($$0), (RuleTest)AlwaysTrueTest.INSTANCE, $$1.defaultBlockState());
/*     */   }
/*     */   
/*     */   public enum VerticalPlacement implements StringRepresentable {
/* 313 */     ON_LAND_SURFACE("on_land_surface"),
/* 314 */     PARTLY_BURIED("partly_buried"),
/* 315 */     ON_OCEAN_FLOOR("on_ocean_floor"),
/* 316 */     IN_MOUNTAIN("in_mountain"),
/* 317 */     UNDERGROUND("underground"),
/* 318 */     IN_NETHER("in_nether");
/*     */ 
/*     */     
/* 321 */     public static final StringRepresentable.EnumCodec<VerticalPlacement> CODEC = StringRepresentable.fromEnum(VerticalPlacement::values); private final String name;
/*     */     static {
/*     */     
/*     */     }
/*     */     VerticalPlacement(String $$0) {
/* 326 */       this.name = $$0;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 330 */       return this.name;
/*     */     }
/*     */     
/*     */     public static VerticalPlacement byName(String $$0) {
/* 334 */       return (VerticalPlacement)CODEC.byName($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 339 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\RuinedPortalPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */