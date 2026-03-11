/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public class StraightTrunkPlacer extends TrunkPlacer {
/*    */   static {
/* 17 */     CODEC = RecordCodecBuilder.create($$0 -> trunkPlacerParts($$0).apply((Applicative)$$0, StraightTrunkPlacer::new));
/*    */   } public static final Codec<StraightTrunkPlacer> CODEC;
/*    */   public StraightTrunkPlacer(int $$0, int $$1, int $$2) {
/* 20 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected TrunkPlacerType<?> type() {
/* 25 */     return TrunkPlacerType.STRAIGHT_TRUNK_PLACER;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, int $$3, BlockPos $$4, TreeConfiguration $$5) {
/* 30 */     setDirtAt($$0, $$1, $$2, $$4.below(), $$5);
/*    */     
/* 32 */     for (int $$6 = 0; $$6 < $$3; $$6++) {
/* 33 */       placeLog($$0, $$1, $$2, $$4.above($$6), $$5);
/*    */     }
/* 35 */     return (List<FoliagePlacer.FoliageAttachment>)ImmutableList.of(new FoliagePlacer.FoliageAttachment($$4.above($$3), 0, false));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\StraightTrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */