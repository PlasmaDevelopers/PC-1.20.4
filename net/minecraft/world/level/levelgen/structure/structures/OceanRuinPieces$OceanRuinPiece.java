/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.monster.Drowned;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ChestBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.storage.loot.BuiltInLootTables;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OceanRuinPiece
/*     */   extends TemplateStructurePiece
/*     */ {
/*     */   private final OceanRuinStructure.Type biomeType;
/*     */   private final float integrity;
/*     */   private final boolean isLarge;
/*     */   
/*     */   public OceanRuinPiece(StructureTemplateManager $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3, float $$4, OceanRuinStructure.Type $$5, boolean $$6) {
/* 231 */     super(StructurePieceType.OCEAN_RUIN, 0, $$0, $$1, $$1.toString(), makeSettings($$3, $$4, $$5), $$2);
/*     */     
/* 233 */     this.integrity = $$4;
/* 234 */     this.biomeType = $$5;
/* 235 */     this.isLarge = $$6;
/*     */   }
/*     */   
/*     */   private OceanRuinPiece(StructureTemplateManager $$0, CompoundTag $$1, Rotation $$2, float $$3, OceanRuinStructure.Type $$4, boolean $$5) {
/* 239 */     super(StructurePieceType.OCEAN_RUIN, $$1, $$0, $$3 -> makeSettings($$0, $$1, $$2));
/*     */     
/* 241 */     this.integrity = $$3;
/* 242 */     this.biomeType = $$4;
/* 243 */     this.isLarge = $$5;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static StructurePlaceSettings makeSettings(Rotation $$0, float $$1, OceanRuinStructure.Type $$2) {
/* 249 */     StructureProcessor $$3 = ($$2 == OceanRuinStructure.Type.COLD) ? OceanRuinPieces.COLD_SUSPICIOUS_BLOCK_PROCESSOR : OceanRuinPieces.WARM_SUSPICIOUS_BLOCK_PROCESSOR;
/*     */     
/* 251 */     return (new StructurePlaceSettings())
/* 252 */       .setRotation($$0)
/* 253 */       .setMirror(Mirror.NONE)
/* 254 */       .addProcessor((StructureProcessor)new BlockRotProcessor($$1))
/* 255 */       .addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR)
/* 256 */       .addProcessor($$3);
/*     */   }
/*     */   
/*     */   public static OceanRuinPiece create(StructureTemplateManager $$0, CompoundTag $$1) {
/* 260 */     Rotation $$2 = Rotation.valueOf($$1.getString("Rot"));
/* 261 */     float $$3 = $$1.getFloat("Integrity");
/* 262 */     OceanRuinStructure.Type $$4 = OceanRuinStructure.Type.valueOf($$1.getString("BiomeType"));
/* 263 */     boolean $$5 = $$1.getBoolean("IsLarge");
/* 264 */     return new OceanRuinPiece($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/* 269 */     super.addAdditionalSaveData($$0, $$1);
/* 270 */     $$1.putString("Rot", this.placeSettings.getRotation().name());
/* 271 */     $$1.putFloat("Integrity", this.integrity);
/* 272 */     $$1.putString("BiomeType", this.biomeType.toString());
/* 273 */     $$1.putBoolean("IsLarge", this.isLarge);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleDataMarker(String $$0, BlockPos $$1, ServerLevelAccessor $$2, RandomSource $$3, BoundingBox $$4) {
/* 278 */     if ("chest".equals($$0)) {
/* 279 */       $$2.setBlock($$1, (BlockState)Blocks.CHEST.defaultBlockState().setValue((Property)ChestBlock.WATERLOGGED, Boolean.valueOf($$2.getFluidState($$1).is(FluidTags.WATER))), 2);
/*     */       
/* 281 */       BlockEntity $$5 = $$2.getBlockEntity($$1);
/* 282 */       if ($$5 instanceof ChestBlockEntity) {
/* 283 */         ((ChestBlockEntity)$$5).setLootTable(this.isLarge ? BuiltInLootTables.UNDERWATER_RUIN_BIG : BuiltInLootTables.UNDERWATER_RUIN_SMALL, $$3.nextLong());
/*     */       }
/*     */     }
/* 286 */     else if ("drowned".equals($$0)) {
/* 287 */       Drowned $$6 = (Drowned)EntityType.DROWNED.create((Level)$$2.getLevel());
/* 288 */       if ($$6 != null) {
/* 289 */         $$6.setPersistenceRequired();
/* 290 */         $$6.moveTo($$1, 0.0F, 0.0F);
/* 291 */         $$6.finalizeSpawn($$2, $$2.getCurrentDifficultyAt($$1), MobSpawnType.STRUCTURE, null, null);
/* 292 */         $$2.addFreshEntityWithPassengers((Entity)$$6);
/* 293 */         if ($$1.getY() > $$2.getSeaLevel()) {
/* 294 */           $$2.setBlock($$1, Blocks.AIR.defaultBlockState(), 2);
/*     */         } else {
/* 296 */           $$2.setBlock($$1, Blocks.WATER.defaultBlockState(), 2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/* 304 */     int $$7 = $$0.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, this.templatePosition.getX(), this.templatePosition.getZ());
/* 305 */     this.templatePosition = new BlockPos(this.templatePosition.getX(), $$7, this.templatePosition.getZ());
/* 306 */     BlockPos $$8 = StructureTemplate.transform(new BlockPos(this.template.getSize().getX() - 1, 0, this.template.getSize().getZ() - 1), Mirror.NONE, this.placeSettings.getRotation(), BlockPos.ZERO).offset((Vec3i)this.templatePosition);
/* 307 */     this.templatePosition = new BlockPos(this.templatePosition.getX(), getHeight(this.templatePosition, (BlockGetter)$$0, $$8), this.templatePosition.getZ());
/*     */     
/* 309 */     super.postProcess($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   private int getHeight(BlockPos $$0, BlockGetter $$1, BlockPos $$2) {
/* 313 */     int $$3 = $$0.getY();
/* 314 */     int $$4 = 512;
/* 315 */     int $$5 = $$3 - 1;
/* 316 */     int $$6 = 0;
/* 317 */     for (BlockPos $$7 : BlockPos.betweenClosed($$0, $$2)) {
/* 318 */       int $$8 = $$7.getX();
/* 319 */       int $$9 = $$7.getZ();
/* 320 */       int $$10 = $$0.getY() - 1;
/* 321 */       BlockPos.MutableBlockPos $$11 = new BlockPos.MutableBlockPos($$8, $$10, $$9);
/* 322 */       BlockState $$12 = $$1.getBlockState((BlockPos)$$11);
/* 323 */       FluidState $$13 = $$1.getFluidState((BlockPos)$$11);
/* 324 */       while (($$12.isAir() || $$13.is(FluidTags.WATER) || $$12.is(BlockTags.ICE)) && $$10 > $$1.getMinBuildHeight() + 1) {
/* 325 */         $$10--;
/* 326 */         $$11.set($$8, $$10, $$9);
/* 327 */         $$12 = $$1.getBlockState((BlockPos)$$11);
/* 328 */         $$13 = $$1.getFluidState((BlockPos)$$11);
/*     */       } 
/*     */       
/* 331 */       $$4 = Math.min($$4, $$10);
/* 332 */       if ($$10 < $$5 - 2) {
/* 333 */         $$6++;
/*     */       }
/*     */     } 
/*     */     
/* 337 */     int $$14 = Math.abs($$0.getX() - $$2.getX());
/* 338 */     if ($$5 - $$4 > 2 && $$6 > $$14 - 2) {
/* 339 */       $$3 = $$4 + 1;
/*     */     }
/*     */     
/* 342 */     return $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanRuinPieces$OceanRuinPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */