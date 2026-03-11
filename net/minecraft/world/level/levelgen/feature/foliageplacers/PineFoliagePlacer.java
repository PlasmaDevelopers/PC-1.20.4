/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class PineFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 11 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and((App)IntProvider.codec(0, 24).fieldOf("height").forGetter(())).apply((Applicative)$$0, PineFoliagePlacer::new));
/*    */   }
/*    */   
/*    */   public static final Codec<PineFoliagePlacer> CODEC;
/*    */   private final IntProvider height;
/*    */   
/*    */   public PineFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2) {
/* 18 */     super($$0, $$1);
/* 19 */     this.height = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 24 */     return FoliagePlacerType.PINE_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 29 */     int $$9 = 0;
/*    */     
/* 31 */     for (int $$10 = $$8; $$10 >= $$8 - $$6; $$10--) {
/* 32 */       placeLeavesRow($$0, $$1, $$2, $$3, $$5.pos(), $$9, $$10, $$5.doubleTrunk());
/*    */       
/* 34 */       if ($$9 >= 1 && $$10 == $$8 - $$6 + 1) {
/* 35 */         $$9--;
/* 36 */       } else if ($$9 < $$7 + $$5.radiusOffset()) {
/* 37 */         $$9++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageRadius(RandomSource $$0, int $$1) {
/* 44 */     return super.foliageRadius($$0, $$1) + $$0.nextInt(Math.max($$1 + 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 49 */     return this.height.sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 54 */     return ($$1 == $$4 && $$3 == $$4 && $$4 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\PineFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */