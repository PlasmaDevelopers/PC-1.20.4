/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class CherryFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and($$0.group((App)IntProvider.codec(4, 16).fieldOf("height").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("wide_bottom_layer_hole_chance").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("corner_hole_chance").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_chance").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("hanging_leaves_extension_chance").forGetter(()))).apply((Applicative)$$0, CherryFoliagePlacer::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<CherryFoliagePlacer> CODEC;
/*    */   
/*    */   private final IntProvider height;
/*    */   
/*    */   private final float wideBottomLayerHoleChance;
/*    */   
/*    */   private final float cornerHoleChance;
/*    */   private final float hangingLeavesChance;
/*    */   private final float hangingLeavesExtensionChance;
/*    */   
/*    */   public CherryFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2, float $$3, float $$4, float $$5, float $$6) {
/* 27 */     super($$0, $$1);
/* 28 */     this.height = $$2;
/* 29 */     this.wideBottomLayerHoleChance = $$3;
/* 30 */     this.cornerHoleChance = $$4;
/* 31 */     this.hangingLeavesChance = $$5;
/* 32 */     this.hangingLeavesExtensionChance = $$6;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 37 */     return FoliagePlacerType.CHERRY_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 63 */     boolean $$9 = $$5.doubleTrunk();
/* 64 */     BlockPos $$10 = $$5.pos().above($$8);
/*    */     
/* 66 */     int $$11 = $$7 + $$5.radiusOffset() - 1;
/*    */     
/* 68 */     placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11 - 2, $$6 - 3, $$9);
/* 69 */     placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11 - 1, $$6 - 4, $$9);
/*    */     
/* 71 */     for (int $$12 = $$6 - 5; $$12 >= 0; $$12--) {
/* 72 */       placeLeavesRow($$0, $$1, $$2, $$3, $$10, $$11, $$12, $$9);
/*    */     }
/*    */     
/* 75 */     placeLeavesRowWithHangingLeavesBelow($$0, $$1, $$2, $$3, $$10, $$11, -1, $$9, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
/* 76 */     placeLeavesRowWithHangingLeavesBelow($$0, $$1, $$2, $$3, $$10, $$11 - 1, -2, $$9, this.hangingLeavesChance, this.hangingLeavesExtensionChance);
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 81 */     return this.height.sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 86 */     if ($$2 == -1 && ($$1 == $$4 || $$3 == $$4) && $$0.nextFloat() < this.wideBottomLayerHoleChance) {
/* 87 */       return true;
/*    */     }
/*    */     
/* 90 */     boolean $$6 = ($$1 == $$4 && $$3 == $$4);
/* 91 */     boolean $$7 = ($$4 > 2);
/*    */     
/* 93 */     if ($$7)
/*    */     {
/* 95 */       return ($$6 || ($$1 + $$3 > $$4 * 2 - 2 && $$0.nextFloat() < this.cornerHoleChance));
/*    */     }
/* 97 */     return ($$6 && $$0.nextFloat() < this.cornerHoleChance);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\CherryFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */