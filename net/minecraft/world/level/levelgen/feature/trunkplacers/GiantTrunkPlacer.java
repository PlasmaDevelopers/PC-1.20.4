/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class GiantTrunkPlacer extends TrunkPlacer {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, GiantTrunkPlacer::new));
/*    */   } public static final Codec<GiantTrunkPlacer> CODEC;
/*    */   public GiantTrunkPlacer(int $$0, int $$1, int $$2) {
/* 20 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 25 */     return TrunkPlacerType.GIANT_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 30 */     BlockPos $$6 = $$4.below();
/* 31 */     setDirtAt($$0, $$1, $$2, $$6, $$5);
/* 32 */     setDirtAt($$0, $$1, $$2, $$6.east(), $$5);
/* 33 */     setDirtAt($$0, $$1, $$2, $$6.south(), $$5);
/* 34 */     setDirtAt($$0, $$1, $$2, $$6.south().east(), $$5);
/*    */     
/* 36 */     BlockPos.MutableBlockPos $$7 = new BlockPos.MutableBlockPos();
/*    */     
/* 38 */     for (int $$8 = 0; $$8 < $$3; $$8++) {
/* 39 */       placeLogIfFreeWithOffset($$0, $$1, $$2, $$7, $$5, $$4, 0, $$8, 0);
/*    */       
/* 41 */       if ($$8 < $$3 - 1) {
/* 42 */         placeLogIfFreeWithOffset($$0, $$1, $$2, $$7, $$5, $$4, 1, $$8, 0);
/* 43 */         placeLogIfFreeWithOffset($$0, $$1, $$2, $$7, $$5, $$4, 1, $$8, 1);
/* 44 */         placeLogIfFreeWithOffset($$0, $$1, $$2, $$7, $$5, $$4, 0, $$8, 1);
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return (List<FoliagePlacer.FoliageAttachment>)ImmutableList.of(new FoliagePlacer.FoliageAttachment($$4.above($$3), 0, true));
/*    */   }
/*    */   
/*    */   private void placeLogIfFreeWithOffset(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos.MutableBlockPos $$3, TreeConfiguration $$4, BlockPos $$5, int $$6, int $$7, int $$8) {
/* 52 */     $$3.setWithOffset((Vec3i)$$5, $$6, $$7, $$8);
/* 53 */     placeLogIfFree($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\GiantTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */