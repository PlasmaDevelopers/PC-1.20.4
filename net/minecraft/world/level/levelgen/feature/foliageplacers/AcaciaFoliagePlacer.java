/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class AcaciaFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).apply((Applicative)$$0, AcaciaFoliagePlacer::new));
/*    */   } public static final Codec<AcaciaFoliagePlacer> CODEC;
/*    */   public AcaciaFoliagePlacer(IntProvider $$0, IntProvider $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 20 */     return FoliagePlacerType.ACACIA_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 25 */     boolean $$9 = $$5.doubleTrunk();
/* 26 */     BlockPos $$10 = $$5.pos().above($$8);
/*    */     
/* 28 */     placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$7 + $$5.radiusOffset(), -1 - $$6, $$9);
/* 29 */     placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$7 - 1, -$$6, $$9);
/* 30 */     placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$7 + $$5.radiusOffset() - 1, 0, $$9);
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 35 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 40 */     if ($$2 == 0)
/*    */     {
/* 42 */       return (($$1 > 1 || $$3 > 1) && $$1 != 0 && $$3 != 0);
/*    */     }
/* 44 */     return ($$1 == $$4 && $$3 == $$4 && $$4 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\AcaciaFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */