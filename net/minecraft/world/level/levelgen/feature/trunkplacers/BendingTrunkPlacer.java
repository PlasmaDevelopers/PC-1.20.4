/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.TreeFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class BendingTrunkPlacer extends TrunkPlacer {
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).and($$0.group((App)ExtraCodecs.POSITIVE_INT.optionalFieldOf("min_height_for_leaves", Integer.valueOf(1)).forGetter(()), (App)IntProvider.codec(1, 64).fieldOf("bend_length").forGetter(()))).apply((Applicative)$$0, BendingTrunkPlacer::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<BendingTrunkPlacer> CODEC;
/*    */   
/*    */   private final int minHeightForLeaves;
/*    */   
/*    */   private final IntProvider bendLength;
/*    */   
/*    */   public BendingTrunkPlacer(int $$0, int $$1, int $$2, int $$3, IntProvider $$4) {
/* 32 */     super($$0, $$1, $$2);
/*    */     
/* 34 */     this.minHeightForLeaves = $$3;
/* 35 */     this.bendLength = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 40 */     return TrunkPlacerType.BENDING_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 45 */     Direction $$6 = Direction.Plane.HORIZONTAL.getRandomDirection($$2);
/* 46 */     int $$7 = $$3 - 1;
/* 47 */     BlockPos.MutableBlockPos $$8 = $$4.mutable();
/* 48 */     BlockPos $$9 = $$8.below();
/*    */     
/* 50 */     setDirtAt($$0, $$1, $$2, $$9, $$5);
/* 51 */     List<FoliagePlacer.FoliageAttachment> $$10 = Lists.newArrayList();
/*    */     
/* 53 */     for (int $$11 = 0; $$11 <= $$7; $$11++) {
/*    */       
/* 55 */       if ($$11 + 1 >= $$7 + $$2.nextInt(2)) {
/* 56 */         $$8.move($$6);
/*    */       }
/*    */       
/* 59 */       if (TreeFeature.validTreePos($$0, (BlockPos)$$8)) {
/* 60 */         placeLog($$0, $$1, $$2, (BlockPos)$$8, $$5);
/*    */       }
/*    */       
/* 63 */       if ($$11 >= this.minHeightForLeaves) {
/* 64 */         $$10.add(new FoliagePlacer.FoliageAttachment($$8.immutable(), 0, false));
/*    */       }
/*    */       
/* 67 */       $$8.move(Direction.UP);
/*    */     } 
/*    */ 
/*    */     
/* 71 */     int $$12 = this.bendLength.sample($$2);
/* 72 */     for (int $$13 = 0; $$13 <= $$12; $$13++) {
/* 73 */       if (TreeFeature.validTreePos($$0, (BlockPos)$$8)) {
/* 74 */         placeLog($$0, $$1, $$2, (BlockPos)$$8, $$5);
/*    */       }
/*    */       
/* 77 */       $$10.add(new FoliagePlacer.FoliageAttachment($$8.immutable(), 0, false));
/* 78 */       $$8.move($$6);
/*    */     } 
/*    */     
/* 81 */     return $$10;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\BendingTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */