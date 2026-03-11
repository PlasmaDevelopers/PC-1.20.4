/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.tags.BiomeTags;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
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
/*     */ abstract class MineShaftPiece
/*     */   extends StructurePiece
/*     */ {
/*     */   protected MineshaftStructure.Type type;
/*     */   
/*     */   public MineShaftPiece(StructurePieceType $$0, int $$1, MineshaftStructure.Type $$2, BoundingBox $$3) {
/*  61 */     super($$0, $$1, $$3);
/*  62 */     this.type = $$2;
/*     */   }
/*     */   
/*     */   public MineShaftPiece(StructurePieceType $$0, CompoundTag $$1) {
/*  66 */     super($$0, $$1);
/*  67 */     this.type = MineshaftStructure.Type.byId($$1.getInt("MST"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canBeReplaced(LevelReader $$0, int $$1, int $$2, int $$3, BoundingBox $$4) {
/*  73 */     BlockState $$5 = getBlock((BlockGetter)$$0, $$1, $$2, $$3, $$4);
/*  74 */     return (!$$5.is(this.type.getPlanksState().getBlock()) && 
/*  75 */       !$$5.is(this.type.getWoodState().getBlock()) && 
/*  76 */       !$$5.is(this.type.getFenceState().getBlock()) && 
/*  77 */       !$$5.is(Blocks.CHAIN));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  82 */     $$1.putInt("MST", this.type.ordinal());
/*     */   }
/*     */   
/*     */   protected boolean isSupportingBox(BlockGetter $$0, BoundingBox $$1, int $$2, int $$3, int $$4, int $$5) {
/*  86 */     for (int $$6 = $$2; $$6 <= $$3; $$6++) {
/*  87 */       if (getBlock($$0, $$6, $$4 + 1, $$5, $$1).isAir()) {
/*  88 */         return false;
/*     */       }
/*     */     } 
/*  91 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean isInInvalidLocation(LevelAccessor $$0, BoundingBox $$1) {
/*  95 */     int $$2 = Math.max(this.boundingBox.minX() - 1, $$1.minX());
/*  96 */     int $$3 = Math.max(this.boundingBox.minY() - 1, $$1.minY());
/*  97 */     int $$4 = Math.max(this.boundingBox.minZ() - 1, $$1.minZ());
/*  98 */     int $$5 = Math.min(this.boundingBox.maxX() + 1, $$1.maxX());
/*  99 */     int $$6 = Math.min(this.boundingBox.maxY() + 1, $$1.maxY());
/* 100 */     int $$7 = Math.min(this.boundingBox.maxZ() + 1, $$1.maxZ());
/*     */     
/* 102 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos(($$2 + $$5) / 2, ($$3 + $$6) / 2, ($$4 + $$7) / 2);
/*     */     
/* 104 */     if ($$0.getBiome((BlockPos)$$8).is(BiomeTags.MINESHAFT_BLOCKING)) {
/* 105 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 109 */     for (int $$9 = $$2; $$9 <= $$5; $$9++) {
/* 110 */       for (int $$10 = $$4; $$10 <= $$7; $$10++) {
/* 111 */         if ($$0.getBlockState((BlockPos)$$8.set($$9, $$3, $$10)).liquid()) {
/* 112 */           return true;
/*     */         }
/* 114 */         if ($$0.getBlockState((BlockPos)$$8.set($$9, $$6, $$10)).liquid()) {
/* 115 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     for (int $$11 = $$2; $$11 <= $$5; $$11++) {
/* 121 */       for (int $$12 = $$3; $$12 <= $$6; $$12++) {
/* 122 */         if ($$0.getBlockState((BlockPos)$$8.set($$11, $$12, $$4)).liquid()) {
/* 123 */           return true;
/*     */         }
/* 125 */         if ($$0.getBlockState((BlockPos)$$8.set($$11, $$12, $$7)).liquid()) {
/* 126 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 131 */     for (int $$13 = $$4; $$13 <= $$7; $$13++) {
/* 132 */       for (int $$14 = $$3; $$14 <= $$6; $$14++) {
/* 133 */         if ($$0.getBlockState((BlockPos)$$8.set($$2, $$14, $$13)).liquid()) {
/* 134 */           return true;
/*     */         }
/* 136 */         if ($$0.getBlockState((BlockPos)$$8.set($$5, $$14, $$13)).liquid()) {
/* 137 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 141 */     return false;
/*     */   }
/*     */   
/*     */   protected void setPlanksBlock(WorldGenLevel $$0, BoundingBox $$1, BlockState $$2, int $$3, int $$4, int $$5) {
/* 145 */     if (!isInterior((LevelReader)$$0, $$3, $$4, $$5, $$1)) {
/*     */       return;
/*     */     }
/* 148 */     BlockPos.MutableBlockPos mutableBlockPos = getWorldPos($$3, $$4, $$5);
/* 149 */     BlockState $$7 = $$0.getBlockState((BlockPos)mutableBlockPos);
/* 150 */     if (!$$7.isFaceSturdy((BlockGetter)$$0, (BlockPos)mutableBlockPos, Direction.UP))
/*     */     {
/* 152 */       $$0.setBlock((BlockPos)mutableBlockPos, $$2, 2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftPieces$MineShaftPiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */