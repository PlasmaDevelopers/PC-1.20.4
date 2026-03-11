/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class FancyFoliagePlacer extends BlobFoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> blobParts($$0).apply((Applicative)$$0, FancyFoliagePlacer::new));
/*    */   } public static final Codec<FancyFoliagePlacer> CODEC;
/*    */   public FancyFoliagePlacer(IntProvider $$0, IntProvider $$1, int $$2) {
/* 15 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 20 */     return FoliagePlacerType.FANCY_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 25 */     for (int $$9 = $$8; $$9 >= $$8 - $$6; $$9--) {
/* 26 */       int $$10 = $$7 + (($$9 == $$8 || $$9 == $$8 - $$6) ? 0 : 1);
/* 27 */       placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$10, $$9, $$5.doubleTrunk());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 33 */     return (Mth.square($$1 + 0.5F) + Mth.square($$3 + 0.5F) > ($$4 * $$4));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\FancyFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */