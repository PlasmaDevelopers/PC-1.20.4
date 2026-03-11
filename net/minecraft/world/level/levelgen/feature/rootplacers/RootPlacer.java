/*    */ package net.minecraft.world.level.levelgen.feature.rootplacers;
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiConsumer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ import net.minecraft.world.level.LevelSimulatedReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
/*    */ import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ 
/*    */ public abstract class RootPlacer {
/* 22 */   public static final Codec<RootPlacer> CODEC = BuiltInRegistries.ROOT_PLACER_TYPE.byNameCodec().dispatch(RootPlacer::type, RootPlacerType::codec);
/*    */   
/*    */   protected final IntProvider trunkOffsetY;
/*    */   protected final BlockStateProvider rootProvider;
/*    */   protected final Optional<AboveRootPlacement> aboveRootPlacement;
/*    */   
/*    */   protected static <P extends RootPlacer> Products.P3<RecordCodecBuilder.Mu<P>, IntProvider, BlockStateProvider, Optional<AboveRootPlacement>> rootPlacerParts(RecordCodecBuilder.Instance<P> $$0) {
/* 29 */     return $$0.group((App)IntProvider.CODEC
/* 30 */         .fieldOf("trunk_offset_y").forGetter($$0 -> $$0.trunkOffsetY), (App)BlockStateProvider.CODEC
/* 31 */         .fieldOf("root_provider").forGetter($$0 -> $$0.rootProvider), (App)AboveRootPlacement.CODEC
/* 32 */         .optionalFieldOf("above_root_placement").forGetter($$0 -> $$0.aboveRootPlacement));
/*    */   }
/*    */ 
/*    */   
/*    */   public RootPlacer(IntProvider $$0, BlockStateProvider $$1, Optional<AboveRootPlacement> $$2) {
/* 37 */     this.trunkOffsetY = $$0;
/* 38 */     this.rootProvider = $$1;
/* 39 */     this.aboveRootPlacement = $$2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canPlaceRoot(LevelSimulatedReader $$0, BlockPos $$1) {
/* 47 */     return TreeFeature.validTreePos($$0, $$1);
/*    */   }
/*    */   
/*    */   protected void placeRoot(LevelSimulatedReader $$0, BiConsumer<BlockPos, BlockState> $$1, RandomSource $$2, BlockPos $$3, TreeConfiguration $$4) {
/* 51 */     if (!canPlaceRoot($$0, $$3)) {
/*    */       return;
/*    */     }
/* 54 */     $$1.accept($$3, getPotentiallyWaterloggedState($$0, $$3, this.rootProvider.getState($$2, $$3)));
/* 55 */     if (this.aboveRootPlacement.isPresent()) {
/* 56 */       AboveRootPlacement $$5 = this.aboveRootPlacement.get();
/* 57 */       BlockPos $$6 = $$3.above();
/* 58 */       if ($$2.nextFloat() < $$5.aboveRootPlacementChance() && $$0.isStateAtPosition($$6, BlockBehaviour.BlockStateBase::isAir)) {
/* 59 */         $$1.accept($$6, getPotentiallyWaterloggedState($$0, $$6, $$5.aboveRootProvider().getState($$2, $$6)));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected BlockState getPotentiallyWaterloggedState(LevelSimulatedReader $$0, BlockPos $$1, BlockState $$2) {
/* 65 */     if ($$2.hasProperty((Property)BlockStateProperties.WATERLOGGED)) {
/* 66 */       boolean $$3 = $$0.isFluidAtPosition($$1, $$0 -> $$0.is(FluidTags.WATER));
/* 67 */       return (BlockState)$$2.setValue((Property)BlockStateProperties.WATERLOGGED, Boolean.valueOf($$3));
/*    */     } 
/* 69 */     return $$2;
/*    */   }
/*    */   
/*    */   public BlockPos getTrunkOrigin(BlockPos $$0, RandomSource $$1) {
/* 73 */     return $$0.above(this.trunkOffsetY.sample($$1));
/*    */   }
/*    */   
/*    */   protected abstract RootPlacerType<?> type();
/*    */   
/*    */   public abstract boolean placeRoots(LevelSimulatedReader paramLevelSimulatedReader, BiConsumer<BlockPos, BlockState> paramBiConsumer, RandomSource paramRandomSource, BlockPos paramBlockPos1, BlockPos paramBlockPos2, TreeConfiguration paramTreeConfiguration);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\rootplacers\RootPlacer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */