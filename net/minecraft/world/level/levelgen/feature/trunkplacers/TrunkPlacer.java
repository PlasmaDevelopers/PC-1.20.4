/*    */ package net.minecraft.world.level.levelgen.feature.trunkplacers;
/*    */ 
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.feature.Feature;
/*    */ import net.minecraft.world.level.levelgen.feature.TreeFeature;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
/*    */ 
/*    */ public abstract class TrunkPlacer {
/* 23 */   public static final Codec<TrunkPlacer> CODEC = BuiltInRegistries.TRUNK_PLACER_TYPE.byNameCodec().dispatch(TrunkPlacer::type, TrunkPlacerType::codec);
/*    */   
/*    */   private static final int MAX_BASE_HEIGHT = 32;
/*    */   private static final int MAX_RAND = 24;
/*    */   public static final int MAX_HEIGHT = 80;
/*    */   
/*    */   protected static <P extends TrunkPlacer> Products.P3<RecordCodecBuilder.Mu<P>, Integer, Integer, Integer> trunkPlacerParts(RecordCodecBuilder.Instance<P> $$0) {
/* 30 */     return $$0.group(
/* 31 */         (App)Codec.intRange(0, 32).fieldOf("base_height").forGetter($$0 -> Integer.valueOf($$0.baseHeight)), 
/* 32 */         (App)Codec.intRange(0, 24).fieldOf("height_rand_a").forGetter($$0 -> Integer.valueOf($$0.heightRandA)), 
/* 33 */         (App)Codec.intRange(0, 24).fieldOf("height_rand_b").forGetter($$0 -> Integer.valueOf($$0.heightRandB)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected final int baseHeight;
/*    */   protected final int heightRandA;
/*    */   protected final int heightRandB;
/*    */   
/*    */   public TrunkPlacer(int $$0, int $$1, int $$2) {
/* 42 */     this.baseHeight = $$0;
/* 43 */     this.heightRandA = $$1;
/* 44 */     this.heightRandB = $$2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getTreeHeight(RandomSource $$0) {
/* 52 */     return this.baseHeight + $$0.nextInt(this.heightRandA + 1) + $$0.nextInt(this.heightRandB + 1);
/*    */   }
/*    */   
/*    */   private static boolean isDirt(LevelSimulatedReader $$0, BlockPos $$1) {
/* 56 */     return $$0.isStateAtPosition($$1, $$0 -> 
/* 57 */         (Feature.isDirt($$0) && !$$0.is(Blocks.GRASS_BLOCK) && !$$0.is(Blocks.MYCELIUM)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected static void setDirtAt(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
/* 64 */     if ($$4.forceDirt || !isDirt($$0, $$3))
/*    */     {
/* 66 */       $$1.accept($$3, $$4.dirtProvider.getState($$2, $$3));
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean placeLog(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
/* 71 */     return placeLog($$0, $$1, $$2, $$3, $$4, Function.identity());
/*    */   }
/*    */   
/*    */   protected boolean placeLog(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4, Function<BlockState, BlockState> $$5) {
/* 75 */     if (validTreePos($$0, $$3)) {
/* 76 */       $$1.accept($$3, $$5.apply($$4.trunkProvider.getState($$2, $$3)));
/*    */       
/* 78 */       return true;
/*    */     } 
/* 80 */     return false;
/*    */   }
/*    */   
/*    */   protected void placeLogIfFree(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos.MutableBlockPos $$3, TreeConfiguration $$4) {
/* 84 */     if (isFree($$0, (BlockPos)$$3)) {
/* 85 */       placeLog($$0, $$1, $$2, (BlockPos)$$3, $$4);
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean validTreePos(LevelSimulatedReader $$0, BlockPos $$1) {
/* 90 */     return TreeFeature.validTreePos($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFree(LevelSimulatedReader $$0, BlockPos $$1) {
/* 95 */     return (validTreePos($$0, $$1) || $$0.isStateAtPosition($$1, $$0 -> $$0.is(BlockTags.LOGS)));
/*    */   }
/*    */   
/*    */   protected abstract TrunkPlacerType<?> type();
/*    */   
/*    */   public abstract List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader paramLevelSimulatedReader, BiConsumer<BlockPos, BlockState> paramBiConsumer, RandomSource paramRandomSource, int paramInt, BlockPos paramBlockPos, TreeConfiguration paramTreeConfiguration);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\trunkplacers\TrunkPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */