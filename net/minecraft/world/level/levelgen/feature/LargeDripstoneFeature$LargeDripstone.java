/*     */ package net.minecraft.world.level.levelgen.feature;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.feature.configurations.LargeDripstoneConfiguration;
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
/*     */ final class LargeDripstone
/*     */ {
/*     */   private BlockPos root;
/*     */   private final boolean pointingUp;
/*     */   private int radius;
/*     */   private final double bluntness;
/*     */   private final double scale;
/*     */   
/*     */   LargeDripstone(BlockPos $$0, boolean $$1, int $$2, double $$3, double $$4) {
/* 116 */     this.root = $$0;
/* 117 */     this.pointingUp = $$1;
/* 118 */     this.radius = $$2;
/* 119 */     this.bluntness = $$3;
/* 120 */     this.scale = $$4;
/*     */   }
/*     */   
/*     */   private int getHeight() {
/* 124 */     return getHeightAtRadius(0.0F);
/*     */   }
/*     */   
/*     */   private int getMinY() {
/* 128 */     if (this.pointingUp) {
/* 129 */       return this.root.getY();
/*     */     }
/* 131 */     return this.root.getY() - getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMaxY() {
/* 136 */     if (!this.pointingUp) {
/* 137 */       return this.root.getY();
/*     */     }
/* 139 */     return this.root.getY() + getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean moveBackUntilBaseIsInsideStoneAndShrinkRadiusIfNecessary(WorldGenLevel $$0, LargeDripstoneFeature.WindOffsetter $$1) {
/* 144 */     while (this.radius > 1) {
/* 145 */       BlockPos.MutableBlockPos $$2 = this.root.mutable();
/* 146 */       int $$3 = Math.min(10, getHeight());
/* 147 */       for (int $$4 = 0; $$4 < $$3; $$4++) {
/* 148 */         if ($$0.getBlockState((BlockPos)$$2).is(Blocks.LAVA)) {
/* 149 */           return false;
/*     */         }
/* 151 */         if (DripstoneUtils.isCircleMostlyEmbeddedInStone($$0, $$1.offset((BlockPos)$$2), this.radius)) {
/* 152 */           this.root = (BlockPos)$$2;
/* 153 */           return true;
/*     */         } 
/* 155 */         $$2.move(this.pointingUp ? Direction.DOWN : Direction.UP);
/*     */       } 
/* 157 */       this.radius /= 2;
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */   
/*     */   private int getHeightAtRadius(float $$0) {
/* 163 */     return (int)DripstoneUtils.getDripstoneHeight($$0, this.radius, this.scale, this.bluntness);
/*     */   }
/*     */   
/*     */   void placeBlocks(WorldGenLevel $$0, RandomSource $$1, LargeDripstoneFeature.WindOffsetter $$2) {
/* 167 */     for (int $$3 = -this.radius; $$3 <= this.radius; $$3++) {
/* 168 */       for (int $$4 = -this.radius; $$4 <= this.radius; $$4++) {
/* 169 */         float $$5 = Mth.sqrt(($$3 * $$3 + $$4 * $$4));
/* 170 */         if ($$5 <= this.radius) {
/*     */ 
/*     */ 
/*     */           
/* 174 */           int $$6 = getHeightAtRadius($$5);
/* 175 */           if ($$6 > 0) {
/*     */ 
/*     */             
/* 178 */             if ($$1.nextFloat() < 0.2D)
/*     */             {
/* 180 */               $$6 = (int)($$6 * Mth.randomBetween($$1, 0.8F, 1.0F));
/*     */             }
/*     */             
/* 183 */             BlockPos.MutableBlockPos $$7 = this.root.offset($$3, 0, $$4).mutable();
/* 184 */             boolean $$8 = false;
/*     */             
/* 186 */             int $$9 = this.pointingUp ? $$0.getHeight(Heightmap.Types.WORLD_SURFACE_WG, $$7.getX(), $$7.getZ()) : Integer.MAX_VALUE;
/* 187 */             for (int $$10 = 0; $$10 < $$6 && 
/* 188 */               $$7.getY() < $$9; $$10++) {
/*     */ 
/*     */               
/* 191 */               BlockPos $$11 = $$2.offset((BlockPos)$$7);
/* 192 */               if (DripstoneUtils.isEmptyOrWaterOrLava((LevelAccessor)$$0, $$11)) {
/* 193 */                 $$8 = true;
/* 194 */                 Block $$12 = Blocks.DRIPSTONE_BLOCK;
/* 195 */                 $$0.setBlock($$11, $$12.defaultBlockState(), 2);
/* 196 */               } else if ($$8 && $$0.getBlockState($$11).is(BlockTags.BASE_STONE_OVERWORLD)) {
/*     */                 break;
/*     */               } 
/* 199 */               $$7.move(this.pointingUp ? Direction.UP : Direction.DOWN);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isSuitableForWind(LargeDripstoneConfiguration $$0) {
/* 209 */     return (this.radius >= $$0.minRadiusForWind && this.bluntness >= $$0.minBluntnessForWind);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\LargeDripstoneFeature$LargeDripstone.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */