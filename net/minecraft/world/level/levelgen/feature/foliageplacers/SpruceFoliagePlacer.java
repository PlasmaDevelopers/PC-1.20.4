/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class SpruceFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 12 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and((App)IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(())).apply((Applicative)$$0, SpruceFoliagePlacer::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SpruceFoliagePlacer> CODEC;
/*    */   private final IntProvider trunkHeight;
/*    */   
/*    */   public SpruceFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2) {
/* 19 */     super($$0, $$1);
/* 20 */     this.trunkHeight = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 25 */     return FoliagePlacerType.SPRUCE_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 30 */     BlockPos $$9 = $$5.pos();
/*    */     
/* 32 */     int $$10 = $$2.nextInt(2);
/* 33 */     int $$11 = 1;
/* 34 */     int $$12 = 0;
/*    */     
/* 36 */     for (int $$13 = $$8; $$13 >= -$$6; $$13--) {
/* 37 */       placeLeavesRow($$0, $$1, $$2, $$3, $$9, $$10, $$13, $$5.doubleTrunk());
/*    */       
/* 39 */       if ($$10 >= $$11) {
/* 40 */         $$10 = $$12;
/* 41 */         $$12 = 1;
/* 42 */         $$11 = Math.min($$11 + 1, $$7 + $$5.radiusOffset());
/*    */       } else {
/* 44 */         $$10++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 52 */     return Math.max(4, $$1 - this.trunkHeight.sample($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 57 */     return ($$1 == $$4 && $$3 == $$4 && $$4 > 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\SpruceFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */