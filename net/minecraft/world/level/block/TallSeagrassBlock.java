/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.item.context.BlockPlaceContext;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
/*    */ import net.minecraft.world.level.block.state.properties.EnumProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TallSeagrassBlock extends DoublePlantBlock implements LiquidBlockContainer {
/* 25 */   public static final MapCodec<TallSeagrassBlock> CODEC = simpleCodec(TallSeagrassBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TallSeagrassBlock> codec() {
/* 29 */     return CODEC;
/*    */   }
/*    */   
/* 32 */   public static final EnumProperty<DoubleBlockHalf> HALF = DoublePlantBlock.HALF;
/*    */   
/*    */   protected static final float AABB_OFFSET = 6.0F;
/* 35 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);
/*    */   
/*    */   public TallSeagrassBlock(BlockBehaviour.Properties $$0) {
/* 38 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 43 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return ($$0.isFaceSturdy($$1, $$2, Direction.UP) && !$$0.is(Blocks.MAGMA_BLOCK));
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCloneItemStack(LevelReader $$0, BlockPos $$1, BlockState $$2) {
/* 53 */     return new ItemStack(Blocks.SEAGRASS);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockState getStateForPlacement(BlockPlaceContext $$0) {
/* 59 */     BlockState $$1 = super.getStateForPlacement($$0);
/*    */     
/* 61 */     if ($$1 != null) {
/* 62 */       FluidState $$2 = $$0.getLevel().getFluidState($$0.getClickedPos().above());
/* 63 */       if ($$2.is(FluidTags.WATER) && $$2.getAmount() == 8) {
/* 64 */         return $$1;
/*    */       }
/*    */     } 
/*    */     
/* 68 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canSurvive(BlockState $$0, LevelReader $$1, BlockPos $$2) {
/* 73 */     if ($$0.getValue((Property)HALF) == DoubleBlockHalf.UPPER) {
/* 74 */       BlockState $$3 = $$1.getBlockState($$2.below());
/* 75 */       return ($$3.is(this) && $$3.getValue((Property)HALF) == DoubleBlockHalf.LOWER);
/*    */     } 
/*    */     
/* 78 */     FluidState $$4 = $$1.getFluidState($$2);
/* 79 */     return (super.canSurvive($$0, $$1, $$2) && $$4.is(FluidTags.WATER) && $$4.getAmount() == 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 84 */     return Fluids.WATER.getSource(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 89 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 94 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TallSeagrassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */