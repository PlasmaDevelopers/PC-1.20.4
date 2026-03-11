/*    */ package net.minecraft.world.level.levelgen.feature.foliageplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ 
/*    */ public class MegaPineFoliagePlacer extends FoliagePlacer {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> foliagePlacerParts($$0).and((App)IntProvider.codec(0, 24).fieldOf("crown_height").forGetter(())).apply((Applicative)$$0, MegaPineFoliagePlacer::new));
/*    */   }
/*    */   
/*    */   public static final Codec<MegaPineFoliagePlacer> CODEC;
/*    */   private final IntProvider crownHeight;
/*    */   
/*    */   public MegaPineFoliagePlacer(IntProvider $$0, IntProvider $$1, IntProvider $$2) {
/* 21 */     super($$0, $$1);
/* 22 */     this.crownHeight = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected FoliagePlacerType<?> type() {
/* 27 */     return FoliagePlacerType.MEGA_PINE_FOLIAGE_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void createFoliage(LevelSimulatedReader $$0, FoliagePlacer.FoliageSetter $$1, RandomSource $$2, TreeConfiguration $$3, int $$4, FoliagePlacer.FoliageAttachment $$5, int $$6, int $$7, int $$8) {
/* 32 */     BlockPos $$9 = $$5.pos();
/*    */     
/* 34 */     int $$10 = 0;
/* 35 */     for (int $$11 = $$9.getY() - $$6 + $$8; $$11 <= $$9.getY() + $$8; $$11++) {
/* 36 */       int $$15, $$12 = $$9.getY() - $$11;
/* 37 */       int $$13 = $$7 + $$5.radiusOffset() + Mth.floor($$12 / $$6 * 3.5F);
/*    */       
/* 39 */       if ($$12 > 0 && $$13 == $$10 && ($$11 & 0x1) == 0) {
/* 40 */         int $$14 = $$13 + 1;
/*    */       } else {
/* 42 */         $$15 = $$13;
/*    */       } 
/*    */       
/* 45 */       placeLeavesRow($$0, $$1, $$2, $$3, new BlockPos($$9.getX(), $$11, $$9.getZ()), $$15, 0, $$5.doubleTrunk());
/* 46 */       $$10 = $$13;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int foliageHeight(RandomSource $$0, int $$1, TreeConfiguration $$2) {
/* 52 */     return this.crownHeight.sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldSkipLocation(RandomSource $$0, int $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 57 */     if ($$1 + $$3 >= 7) {
/* 58 */       return true;
/*    */     }
/* 60 */     return ($$1 * $$1 + $$3 * $$3 > $$4 * $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\foliageplacers\MegaPineFoliagePlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */