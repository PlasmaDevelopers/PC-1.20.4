/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.MobSpawnType;
/*     */ import net.minecraft.world.entity.animal.Cat;
/*     */ import net.minecraft.world.entity.monster.Witch;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.StairBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.StairsShape;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ 
/*     */ public class SwampHutPiece extends ScatteredFeaturePiece {
/*     */   private boolean spawnedWitch;
/*     */   
/*     */   public SwampHutPiece(RandomSource $$0, int $$1, int $$2) {
/*  30 */     super(StructurePieceType.SWAMPLAND_HUT, $$1, 64, $$2, 7, 7, 9, getRandomHorizontalDirection($$0));
/*     */   }
/*     */   private boolean spawnedCat;
/*     */   public SwampHutPiece(CompoundTag $$0) {
/*  34 */     super(StructurePieceType.SWAMPLAND_HUT, $$0);
/*  35 */     this.spawnedWitch = $$0.getBoolean("Witch");
/*  36 */     this.spawnedCat = $$0.getBoolean("Cat");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  41 */     super.addAdditionalSaveData($$0, $$1);
/*  42 */     $$1.putBoolean("Witch", this.spawnedWitch);
/*  43 */     $$1.putBoolean("Cat", this.spawnedCat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  48 */     if (!updateAverageGroundHeight((LevelAccessor)$$0, $$4, 0)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  53 */     generateBox($$0, $$4, 1, 1, 1, 5, 1, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*  54 */     generateBox($$0, $$4, 1, 4, 2, 5, 4, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*  55 */     generateBox($$0, $$4, 2, 1, 0, 4, 1, 0, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*     */ 
/*     */     
/*  58 */     generateBox($$0, $$4, 2, 2, 2, 3, 3, 2, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*  59 */     generateBox($$0, $$4, 1, 2, 3, 1, 3, 6, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*  60 */     generateBox($$0, $$4, 5, 2, 3, 5, 3, 6, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*  61 */     generateBox($$0, $$4, 2, 2, 7, 4, 3, 7, Blocks.SPRUCE_PLANKS.defaultBlockState(), Blocks.SPRUCE_PLANKS.defaultBlockState(), false);
/*     */ 
/*     */     
/*  64 */     generateBox($$0, $$4, 1, 0, 2, 1, 3, 2, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
/*  65 */     generateBox($$0, $$4, 5, 0, 2, 5, 3, 2, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
/*  66 */     generateBox($$0, $$4, 1, 0, 7, 1, 3, 7, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
/*  67 */     generateBox($$0, $$4, 5, 0, 7, 5, 3, 7, Blocks.OAK_LOG.defaultBlockState(), Blocks.OAK_LOG.defaultBlockState(), false);
/*     */ 
/*     */     
/*  70 */     placeBlock($$0, Blocks.OAK_FENCE.defaultBlockState(), 2, 3, 2, $$4);
/*  71 */     placeBlock($$0, Blocks.OAK_FENCE.defaultBlockState(), 3, 3, 7, $$4);
/*  72 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 1, 3, 4, $$4);
/*  73 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 5, 3, 4, $$4);
/*  74 */     placeBlock($$0, Blocks.AIR.defaultBlockState(), 5, 3, 5, $$4);
/*  75 */     placeBlock($$0, Blocks.POTTED_RED_MUSHROOM.defaultBlockState(), 1, 3, 5, $$4);
/*     */ 
/*     */     
/*  78 */     placeBlock($$0, Blocks.CRAFTING_TABLE.defaultBlockState(), 3, 2, 6, $$4);
/*  79 */     placeBlock($$0, Blocks.CAULDRON.defaultBlockState(), 4, 2, 6, $$4);
/*     */ 
/*     */     
/*  82 */     placeBlock($$0, Blocks.OAK_FENCE.defaultBlockState(), 1, 2, 1, $$4);
/*  83 */     placeBlock($$0, Blocks.OAK_FENCE.defaultBlockState(), 5, 2, 1, $$4);
/*     */ 
/*     */     
/*  86 */     BlockState $$7 = (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.NORTH);
/*  87 */     BlockState $$8 = (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.EAST);
/*  88 */     BlockState $$9 = (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.WEST);
/*  89 */     BlockState $$10 = (BlockState)Blocks.SPRUCE_STAIRS.defaultBlockState().setValue((Property)StairBlock.FACING, (Comparable)Direction.SOUTH);
/*     */     
/*  91 */     generateBox($$0, $$4, 0, 4, 1, 6, 4, 1, $$7, $$7, false);
/*  92 */     generateBox($$0, $$4, 0, 4, 2, 0, 4, 7, $$8, $$8, false);
/*  93 */     generateBox($$0, $$4, 6, 4, 2, 6, 4, 7, $$9, $$9, false);
/*  94 */     generateBox($$0, $$4, 0, 4, 8, 6, 4, 8, $$10, $$10, false);
/*  95 */     placeBlock($$0, (BlockState)$$7.setValue((Property)StairBlock.SHAPE, (Comparable)StairsShape.OUTER_RIGHT), 0, 4, 1, $$4);
/*  96 */     placeBlock($$0, (BlockState)$$7.setValue((Property)StairBlock.SHAPE, (Comparable)StairsShape.OUTER_LEFT), 6, 4, 1, $$4);
/*  97 */     placeBlock($$0, (BlockState)$$10.setValue((Property)StairBlock.SHAPE, (Comparable)StairsShape.OUTER_LEFT), 0, 4, 8, $$4);
/*  98 */     placeBlock($$0, (BlockState)$$10.setValue((Property)StairBlock.SHAPE, (Comparable)StairsShape.OUTER_RIGHT), 6, 4, 8, $$4);
/*     */ 
/*     */     
/* 101 */     for (int $$11 = 2; $$11 <= 7; $$11 += 5) {
/* 102 */       for (int $$12 = 1; $$12 <= 5; $$12 += 4) {
/* 103 */         fillColumnDown($$0, Blocks.OAK_LOG.defaultBlockState(), $$12, -1, $$11, $$4);
/*     */       }
/*     */     } 
/*     */     
/* 107 */     if (!this.spawnedWitch) {
/* 108 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(2, 2, 5);
/* 109 */       if ($$4.isInside((Vec3i)mutableBlockPos)) {
/* 110 */         this.spawnedWitch = true;
/*     */         
/* 112 */         Witch $$14 = (Witch)EntityType.WITCH.create((Level)$$0.getLevel());
/* 113 */         if ($$14 != null) {
/* 114 */           $$14.setPersistenceRequired();
/* 115 */           $$14.moveTo(mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY(), mutableBlockPos.getZ() + 0.5D, 0.0F, 0.0F);
/* 116 */           $$14.finalizeSpawn((ServerLevelAccessor)$$0, $$0.getCurrentDifficultyAt((BlockPos)mutableBlockPos), MobSpawnType.STRUCTURE, null, null);
/* 117 */           $$0.addFreshEntityWithPassengers((Entity)$$14);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 122 */     spawnCat((ServerLevelAccessor)$$0, $$4);
/*     */   }
/*     */   
/*     */   private void spawnCat(ServerLevelAccessor $$0, BoundingBox $$1) {
/* 126 */     if (!this.spawnedCat) {
/* 127 */       BlockPos.MutableBlockPos mutableBlockPos = getWorldPos(2, 2, 5);
/* 128 */       if ($$1.isInside((Vec3i)mutableBlockPos)) {
/* 129 */         this.spawnedCat = true;
/*     */         
/* 131 */         Cat $$3 = (Cat)EntityType.CAT.create((Level)$$0.getLevel());
/* 132 */         if ($$3 != null) {
/* 133 */           $$3.setPersistenceRequired();
/* 134 */           $$3.moveTo(mutableBlockPos.getX() + 0.5D, mutableBlockPos.getY(), mutableBlockPos.getZ() + 0.5D, 0.0F, 0.0F);
/* 135 */           $$3.finalizeSpawn($$0, $$0.getCurrentDifficultyAt((BlockPos)mutableBlockPos), MobSpawnType.STRUCTURE, null, null);
/* 136 */           $$0.addFreshEntityWithPassengers((Entity)$$3);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\SwampHutPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */