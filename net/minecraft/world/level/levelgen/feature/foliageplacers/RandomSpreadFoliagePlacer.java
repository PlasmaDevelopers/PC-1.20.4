/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class RandomSpreadFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and($$0.group((App)IntProvider.codec(1, 512).fieldOf("foliage_height").forGetter(()), (App)Codec.intRange(0, 256).fieldOf("leaf_placement_attempts").forGetter(()))).apply((Applicative)$$0, RandomSpreadFoliagePlacer::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomSpreadFoliagePlacer> CODEC;
/*    */   
/*    */   private final IntProvider foliageHeight;
/*    */   
/*    */   private final int leafPlacementAttempts;
/*    */   
/*    */   public RandomSpreadFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2, int $$3) {
/* 23 */     super($$0, $$1);
/*    */     
/* 25 */     this.foliageHeight = $$2;
/* 26 */     this.leafPlacementAttempts = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 31 */     return FoliagePlacerType.RANDOM_SPREAD_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 36 */     BlockPos $$9 = $$5.pos();
/* 37 */     BlockPos.MutableBlockPos $$10 = $$9.mutable();
/*    */     
/* 39 */     for (int $$11 = 0; $$11 < this.leafPlacementAttempts; $$11++) {
/* 40 */       $$10.setWithOffset((Vec3i)$$9, $$2.nextInt($$7) - $$2.nextInt($$7), $$2.nextInt($$6) - $$2.nextInt($$6), $$2.nextInt($$7) - $$2.nextInt($$7));
/* 41 */       tryPlaceLeaf($$0, $$1, $$2, $$3, (BlockPos)$$10);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 47 */     return this.foliageHeight.sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 52 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\RandomSpreadFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */