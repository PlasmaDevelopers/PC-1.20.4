/*     */ package net.minecraft.world.level.levelgen;
/*     */ 
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class OreVeinifier
/*     */ {
/*     */   private static final float VEININESS_THRESHOLD = 0.4F;
/*     */   private static final int EDGE_ROUNDOFF_BEGIN = 20;
/*     */   private static final double MAX_EDGE_ROUNDOFF = 0.2D;
/*     */   private static final float VEIN_SOLIDNESS = 0.7F;
/*     */   private static final float MIN_RICHNESS = 0.1F;
/*     */   private static final float MAX_RICHNESS = 0.3F;
/*     */   private static final float MAX_RICHNESS_THRESHOLD = 0.6F;
/*     */   private static final float CHANCE_OF_RAW_ORE_BLOCK = 0.02F;
/*     */   private static final float SKIP_ORE_IF_GAP_NOISE_IS_BELOW = -0.3F;
/*     */   
/*     */   protected static NoiseChunk.BlockStateFiller create(DensityFunction $$0, DensityFunction $$1, DensityFunction $$2, PositionalRandomFactory $$3) {
/*  42 */     BlockState $$4 = null;
/*     */     
/*  44 */     return $$5 -> {
/*     */         double $$6 = $$0.compute($$5);
/*     */         
/*     */         int $$7 = $$5.blockY();
/*     */         
/*     */         VeinType $$8 = ($$6 > 0.0D) ? VeinType.COPPER : VeinType.IRON;
/*     */         
/*     */         double $$9 = Math.abs($$6);
/*     */         
/*     */         int $$10 = $$8.maxY - $$7;
/*     */         
/*     */         int $$11 = $$7 - $$8.minY;
/*     */         
/*     */         if ($$11 < 0 || $$10 < 0) {
/*     */           return $$1;
/*     */         }
/*     */         
/*     */         int $$12 = Math.min($$10, $$11);
/*     */         
/*     */         double $$13 = Mth.clampedMap($$12, 0.0D, 20.0D, -0.2D, 0.0D);
/*     */         
/*     */         if ($$9 + $$13 < 0.4000000059604645D) {
/*     */           return $$1;
/*     */         }
/*     */         
/*     */         RandomSource $$14 = $$2.at($$5.blockX(), $$7, $$5.blockZ());
/*     */         if ($$14.nextFloat() > 0.7F) {
/*     */           return $$1;
/*     */         }
/*     */         if ($$3.compute($$5) >= 0.0D) {
/*     */           return $$1;
/*     */         }
/*     */         double $$15 = Mth.clampedMap($$9, 0.4000000059604645D, 0.6000000238418579D, 0.10000000149011612D, 0.30000001192092896D);
/*  77 */         return ($$14.nextFloat() < $$15 && $$4.compute($$5) > -0.30000001192092896D) ? (($$14.nextFloat() < 0.02F) ? $$8.rawOreBlock : $$8.ore) : $$8.filler;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected enum VeinType
/*     */   {
/*  85 */     COPPER((String)Blocks.COPPER_ORE.defaultBlockState(), Blocks.RAW_COPPER_BLOCK.defaultBlockState(), Blocks.GRANITE.defaultBlockState(), 0, 50),
/*  86 */     IRON((String)Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), Blocks.RAW_IRON_BLOCK.defaultBlockState(), Blocks.TUFF.defaultBlockState(), -60, -8);
/*     */     
/*     */     final BlockState ore;
/*     */     
/*     */     final BlockState rawOreBlock;
/*     */     final BlockState filler;
/*     */     protected final int minY;
/*     */     protected final int maxY;
/*     */     
/*     */     VeinType(BlockState $$0, BlockState $$1, BlockState $$2, int $$3, int $$4) {
/*  96 */       this.ore = $$0;
/*  97 */       this.rawOreBlock = $$1;
/*  98 */       this.filler = $$2;
/*  99 */       this.minY = $$3;
/* 100 */       this.maxY = $$4;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\OreVeinifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */