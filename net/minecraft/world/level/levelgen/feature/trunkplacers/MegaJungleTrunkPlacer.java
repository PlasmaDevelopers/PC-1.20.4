/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class MegaJungleTrunkPlacer extends GiantTrunkPlacer {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, MegaJungleTrunkPlacer::new));
/*    */   } public static final Codec<MegaJungleTrunkPlacer> CODEC;
/*    */   public MegaJungleTrunkPlacer(int $$0, int $$1, int $$2) {
/* 21 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 26 */     return TrunkPlacerType.MEGA_JUNGLE_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 31 */     List<FoliagePlacer.FoliageAttachment> $$6 = Lists.newArrayList();
/* 32 */     $$6.addAll(super.placeTrunk($$0, $$1, $$2, $$3, $$4, $$5));
/*    */     
/*    */     int $$7;
/* 35 */     for ($$7 = $$3 - 2 - $$2.nextInt(4); $$7 > $$3 / 2; $$7 -= 2 + $$2.nextInt(4)) {
/* 36 */       float $$8 = $$2.nextFloat() * 6.2831855F;
/* 37 */       int $$9 = 0;
/* 38 */       int $$10 = 0;
/*    */       
/* 40 */       for (int $$11 = 0; $$11 < 5; $$11++) {
/* 41 */         $$9 = (int)(1.5F + Mth.cos($$8) * $$11);
/* 42 */         $$10 = (int)(1.5F + Mth.sin($$8) * $$11);
/* 43 */         BlockPos $$12 = $$4.offset($$9, $$7 - 3 + $$11 / 2, $$10);
/* 44 */         placeLog($$0, $$1, $$2, $$12, $$5);
/*    */       } 
/*    */       
/* 47 */       $$6.add(new FoliagePlacer.FoliageAttachment($$4.offset($$9, $$7, $$10), -2, false));
/*    */     } 
/*    */     
/* 50 */     return $$6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\MegaJungleTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */