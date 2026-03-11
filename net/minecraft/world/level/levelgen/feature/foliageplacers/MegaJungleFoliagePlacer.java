/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class MegaJungleFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and((App)Codec.intRange(0, 16).fieldOf("height").forGetter(())).apply((Applicative)$$0, MegaJungleFoliagePlacer::new));
/*    */   }
/*    */   
/*    */   public static final Codec<MegaJungleFoliagePlacer> CODEC;
/*    */   protected final int height;
/*    */   
/*    */   public MegaJungleFoliagePlacer(IntProvider $$0, IntProvider $$1, int $$2) {
/* 19 */     super($$0, $$1);
/* 20 */     this.height = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 25 */     return FoliagePlacerType.MEGA_JUNGLE_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 31 */     int $$9 = $$5.doubleTrunk() ? $$6 : (1 + $$2.nextInt(2));
/*    */     
/* 33 */     for (int $$10 = $$8; $$10 >= $$8 - $$9; $$10--) {
/* 34 */       int $$11 = $$7 + $$5.radiusOffset() + 1 - $$10;
/* 35 */       placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$11, $$10, $$5.doubleTrunk());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 41 */     return this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 46 */     if ($$1 + $$3 >= 7) {
/* 47 */       return true;
/*    */     }
/* 49 */     return ($$1 * $$1 + $$3 * $$3 > $$4 * $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\MegaJungleFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */