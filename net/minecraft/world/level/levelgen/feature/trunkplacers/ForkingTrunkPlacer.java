/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.OptionalInt;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class ForkingTrunkPlacer extends TrunkPlacer {
/*    */   static {
/* 19 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, ForkingTrunkPlacer::new));
/*    */   } public static final Codec<ForkingTrunkPlacer> CODEC;
/*    */   public ForkingTrunkPlacer(int $$0, int $$1, int $$2) {
/* 22 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 27 */     return TrunkPlacerType.FORKING_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 32 */     setDirtAt($$0, $$1, $$2, $$4.below(), $$5);
/*    */     
/* 34 */     List<FoliagePlacer.FoliageAttachment> $$6 = Lists.newArrayList();
/*    */     
/* 36 */     Direction $$7 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 37 */     int $$8 = $$3 - $$2.nextInt(4) - 1;
/* 38 */     int $$9 = 3 - $$2.nextInt(3);
/*    */     
/* 40 */     BlockPos.MutableBlockPos $$10 = new BlockPos.MutableBlockPos();
/* 41 */     int $$11 = $$4.getX();
/* 42 */     int $$12 = $$4.getZ();
/* 43 */     OptionalInt $$13 = OptionalInt.empty();
/* 44 */     for (int $$14 = 0; $$14 < $$3; $$14++) {
/* 45 */       int $$15 = $$4.getY() + $$14;
/* 46 */       if ($$14 >= $$8 && $$9 > 0) {
/* 47 */         $$11 += $$7.getStepX();
/* 48 */         $$12 += $$7.getStepZ();
/* 49 */         $$9--;
/*    */       } 
/* 51 */       if (placeLog($$0, $$1, $$2, (BlockPos)$$10.set($$11, $$15, $$12), $$5)) {
/* 52 */         $$13 = OptionalInt.of($$15 + 1);
/*    */       }
/*    */     } 
/*    */     
/* 56 */     if ($$13.isPresent()) {
/* 57 */       $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$11, $$13.getAsInt(), $$12), 1, false));
/*    */     }
/*    */     
/* 60 */     $$11 = $$4.getX();
/* 61 */     $$12 = $$4.getZ();
/* 62 */     Direction $$16 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 63 */     if ($$16 != $$7) {
/* 64 */       int $$17 = $$8 - $$2.nextInt(2) - 1;
/* 65 */       int $$18 = 1 + $$2.nextInt(3);
/*    */       
/* 67 */       $$13 = OptionalInt.empty();
/* 68 */       for (int $$19 = $$17; $$19 < $$3 && $$18 > 0; $$19++, $$18--) {
/* 69 */         if ($$19 >= 1) {
/*    */ 
/*    */           
/* 72 */           int $$20 = $$4.getY() + $$19;
/* 73 */           $$11 += $$16.getStepX();
/* 74 */           $$12 += $$16.getStepZ();
/* 75 */           if (placeLog($$0, $$1, $$2, (BlockPos)$$10.set($$11, $$20, $$12), $$5))
/* 76 */             $$13 = OptionalInt.of($$20 + 1); 
/*    */         } 
/*    */       } 
/* 79 */       if ($$13.isPresent()) {
/* 80 */         $$6.add(new FoliagePlacer.FoliageAttachment(new BlockPos($$11, $$13.getAsInt(), $$12), 0, false));
/*    */       }
/*    */     } 
/*    */     
/* 84 */     return $$6;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\ForkingTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */