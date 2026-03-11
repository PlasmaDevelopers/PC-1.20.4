/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class DarkOakFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).apply((Applicative)$$0, DarkOakFoliagePlacer::new));
/*    */   } public static final Codec<DarkOakFoliagePlacer> CODEC;
/*    */   public DarkOakFoliagePlacer(IntProvider $$0, IntProvider $$1) {
/* 15 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 20 */     return FoliagePlacerType.DARK_OAK_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 25 */     BlockPos $$9 = $$5.pos().above($$8);
/* 26 */     boolean $$10 = $$5.doubleTrunk();
/*    */     
/* 28 */     if ($$10) {
/* 29 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7 + 2, -1, $$10);
/* 30 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7 + 3, 0, $$10);
/* 31 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7 + 2, 1, $$10);
/* 32 */       if ($$2.nextBoolean()) {
/* 33 */         placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7, 2, $$10);
/*    */       }
/*    */     } else {
/* 36 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7 + 2, -1, $$10);
/* 37 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$7 + 1, 0, $$10);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 43 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocationSigned(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 48 */     if ($$2 == 0 && $$5 && (
/* 49 */       $$1 == -$$4 || $$1 >= $$4) && ($$3 == -$$4 || $$3 >= $$4)) {
/* 50 */       return true;
/*    */     }
/*    */     
/* 53 */     return super.shouldSkipLocationSigned($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 58 */     if ($$2 == -1 && !$$5) {
/* 59 */       return ($$1 == $$4 && $$3 == $$4);
/*    */     }
/* 61 */     if ($$2 == 1) {
/* 62 */       return ($$1 + $$3 > $$4 * 2 - 2);
/*    */     }
/* 64 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\DarkOakFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */