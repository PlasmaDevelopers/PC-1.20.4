/*     */ package net.minecraft.world.level.block;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.context.BlockPlaceContext;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.shapes.CollisionContext;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ 
/*     */ public class SeagrassBlock extends BushBlock implements BonemealableBlock, LiquidBlockContainer {
/*  26 */   public static final MapCodec<SeagrassBlock> CODEC = simpleCodec(SeagrassBlock::new);
/*     */   protected static final float AABB_OFFSET = 6.0F;
/*     */   
/*     */   public MapCodec<SeagrassBlock> codec() {
/*  30 */     return CODEC;
/*     */   }
/*     */ 
/*     */   
/*  34 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 12.0D, 14.0D);
/*     */   
/*     */   protected SeagrassBlock(BlockBehaviour.Properties $$0) {
/*  37 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/*  42 */     return SHAPE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/*  47 */     return ($$0.isFaceSturdy($$1, $$2, Direction.UP) && !$$0.is(Blocks.MAGMA_BLOCK));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/*  53 */     FluidState $$1 = $$0.getLevel().getFluidState($$0.getClickedPos());
/*     */     
/*  55 */     if ($$1.is(FluidTags.WATER) && $$1.getAmount() == 8) {
/*  56 */       return super.getStateForPlacement($$0);
/*     */     }
/*  58 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState updateShape(BlockState $$0, Direction $$1, BlockState $$2, LevelAccessor $$3, BlockPos $$4, BlockPos $$5) {
/*  63 */     BlockState $$6 = super.updateShape($$0, $$1, $$2, $$3, $$4, $$5);
/*  64 */     if (!$$6.isAir()) {
/*  65 */       $$3.scheduleTick($$4, (Fluid)Fluids.WATER, Fluids.WATER.getTickDelay((LevelReader)$$3));
/*     */     }
/*  67 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidBonemealTarget(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/*  72 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBonemealSuccess(Level $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public FluidState getFluidState(BlockState $$0) {
/*  82 */     return Fluids.WATER.getSource(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void performBonemeal(ServerLevel $$0, RandomSource $$1, BlockPos $$2, BlockState $$3) {
/*  87 */     BlockState $$4 = Blocks.TALL_SEAGRASS.defaultBlockState();
/*  88 */     BlockState $$5 = (BlockState)$$4.setValue((Property)TallSeagrassBlock.HALF, (Comparable)DoubleBlockHalf.UPPER);
/*  89 */     BlockPos $$6 = $$2.above();
/*  90 */     if ($$0.getBlockState($$6).is(Blocks.WATER)) {
/*  91 */       $$0.setBlock($$2, $$4, 2);
/*  92 */       $$0.setBlock($$6, $$5, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 103 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\SeagrassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */