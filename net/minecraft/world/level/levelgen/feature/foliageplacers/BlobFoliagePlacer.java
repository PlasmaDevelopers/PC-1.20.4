/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class BlobFoliagePlacer extends FoliagePlacer {
/*    */   public static final Codec<BlobFoliagePlacer> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> blobParts($$0).apply((Applicative)$$0, BlobFoliagePlacer::new));
/*    */   } protected final int height;
/*    */   protected static <P extends BlobFoliagePlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, IntProvider, Integer> blobParts(RecordCodecBuilder.Instance<P> $$0) {
/* 15 */     return foliagePlacerParts((RecordCodecBuilder.Instance)$$0).and(
/* 16 */         (App)Codec.intRange(0, 16).fieldOf("height").forGetter($$0 -> Integer.valueOf($$0.height)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BlobFoliagePlacer(IntProvider $$0, IntProvider $$1, int $$2) {
/* 23 */     super($$0, $$1);
/* 24 */     this.height = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 29 */     return FoliagePlacerType.BLOB_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 34 */     for (int $$9 = $$8; $$9 >= $$8 - $$6; $$9--) {
/* 35 */       int $$10 = Math.max($$7 + $$5.radiusOffset() - 1 - $$9 / 2, 0);
/* 36 */       placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$10, $$9, $$5.doubleTrunk());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 42 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 47 */     return ($$1 == $$4 && $$3 == $$4 && ($$0.nextInt(2) == 0 || $$2 == 0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\BlobFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */