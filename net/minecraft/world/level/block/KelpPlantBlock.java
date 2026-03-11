/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ 
/*    */ public class KelpPlantBlock extends GrowingPlantBodyBlock implements LiquidBlockContainer {
/* 18 */   public static final MapCodec<KelpPlantBlock> CODEC = simpleCodec(KelpPlantBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<KelpPlantBlock> codec() {
/* 22 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected KelpPlantBlock(BlockBehaviour.Properties $$0) {
/* 26 */     super($$0, Direction.UP, Shapes.block(), true);
/*    */   }
/*    */ 
/*    */   
/*    */   protected GrowingPlantHeadBlock getHeadBlock() {
/* 31 */     return (GrowingPlantHeadBlock)Blocks.KELP;
/*    */   }
/*    */ 
/*    */   
/*    */   public FluidState getFluidState(BlockState $$0) {
/* 36 */     return Fluids.WATER.getSource(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canAttachTo(BlockState $$0) {
/* 41 */     return getHeadBlock().canAttachTo($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canPlaceLiquid(@Nullable Player $$0, BlockGetter $$1, BlockPos $$2, BlockState $$3, Fluid $$4) {
/* 46 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean placeLiquid(LevelAccessor $$0, BlockPos $$1, BlockState $$2, FluidState $$3) {
/* 51 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\KelpPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */