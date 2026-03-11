/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class DarkOakTrunkPlacer extends TrunkPlacer {
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, DarkOakTrunkPlacer::new));
/*    */   } public static final Codec<DarkOakTrunkPlacer> CODEC;
/*    */   public DarkOakTrunkPlacer(int $$0, int $$1, int $$2) {
/* 22 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 27 */     return TrunkPlacerType.DARK_OAK_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 32 */     List<FoliagePlacer.FoliageAttachment> $$6 = Lists.newArrayList();
/*    */     
/* 34 */     BlockPos $$7 = $$4.below();
/* 35 */     setDirtAt($$0, $$1, $$2, $$7, $$5);
/* 36 */     setDirtAt($$0, $$1, $$2, $$7.east(), $$5);
/* 37 */     setDirtAt($$0, $$1, $$2, $$7.south(), $$5);
/* 38 */     setDirtAt($$0, $$1, $$2, $$7.south().east(), $$5);
/*    */     
/* 40 */     Direction $$8 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 41 */     int $$9 = $$3 - $$2.nextInt(4);
/* 42 */     int $$10 = 2 - $$2.nextInt(3);
/*    */     
/* 44 */     int $$11 = $$4.getX();
/* 45 */     int $$12 = $$4.getY();
/* 46 */     int $$13 = $$4.getZ();
/*    */     
/* 48 */     int $$14 = $$11;
/* 49 */     int $$15 = $$13;
/* 50 */     int $$16 = $$12 + $$3 - 1;
/*    */ 
/*    */     
/* 53 */     for (int $$17 = 0; $$17 < $$3; $$17++) {
/* 54 */       if ($$17 >= $$9 && $$10 > 0) {
/* 55 */         $$14 += $$8.getStepX();
/* 56 */         $$15 += $$8.getStepZ();
/* 57 */         $$10--;
/*    */       } 
/*    */       
/* 60 */       int $$18 = $$12 + $$17;
/* 61 */       BlockPos $$19 = new BlockPos($$14, $$18, $$15);
/* 62 */       if (TreeFeature.isAirOrLeaves($$0, $$19)) {
/* 63 */         placeLog($$0, $$1, $$2, $$19, $$5);
/* 64 */         placeLog($$0, $$1, $$2, $$19.east(), $$5);
/* 65 */         placeLog($$0, $$1, $$2, $$19.south(), $$5);
/* 66 */         placeLog($$0, $$1, $$2, $$19.east().south(), $$5);
/*    */       } 
/*    */     } 
/*    */     
/* 70 */     $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$14, $$16, $$15), 0, true));
/*    */ 
/*    */     
/* 73 */     for (int $$20 = -1; $$20 <= 2; $$20++) {
/* 74 */       for (int $$21 = -1; $$21 <= 2; $$21++) {
/* 75 */         if ($$20 < 0 || $$20 > 1 || $$21 < 0 || $$21 > 1)
/*    */         {
/*    */           
/* 78 */           if ($$2.nextInt(3) <= 0) {
/*    */ 
/*    */             
/* 81 */             int $$22 = $$2.nextInt(3) + 2;
/* 82 */             for (int $$23 = 0; $$23 < $$22; $$23++) {
/* 83 */               placeLog($$0, $$1, $$2, new BlockPos($$11 + $$20, $$16 - $$23 - 1, $$13 + $$21), $$5);
/*    */             }
/*    */             
/* 86 */             $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$14 + $$20, $$16, $$15 + $$21), 0, false));
/*    */           }  } 
/*    */       } 
/*    */     } 
/* 90 */     return $$6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\DarkOakTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */