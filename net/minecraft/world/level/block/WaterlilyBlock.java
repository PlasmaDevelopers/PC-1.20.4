/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.material.FluidState;
/*    */ import net.minecraft.world.level.material.Fluids;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class WaterlilyBlock extends BushBlock {
/* 17 */   public static final MapCodec<WaterlilyBlock> CODEC = simpleCodec(WaterlilyBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WaterlilyBlock> codec() {
/* 21 */     return CODEC;
/*    */   }
/*    */   
/* 24 */   protected static final VoxelShape AABB = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 1.5D, 15.0D);
/*    */   
/*    */   protected WaterlilyBlock(BlockBehaviour.Properties $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 32 */     super.entityInside($$0, $$1, $$2, $$3);
/*    */     
/* 34 */     if ($$1 instanceof net.minecraft.server.level.ServerLevel && $$3 instanceof net.minecraft.world.entity.vehicle.Boat) {
/* 35 */       $$1.destroyBlock(new BlockPos((Vec3i)$$2), true, $$3);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 41 */     return AABB;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean mayPlaceOn(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 46 */     FluidState $$3 = $$1.getFluidState($$2);
/* 47 */     FluidState $$4 = $$1.getFluidState($$2.above());
/* 48 */     return (($$3.getType() == Fluids.WATER || $$0.getBlock() instanceof IceBlock) && $$4.getType() == Fluids.EMPTY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WaterlilyBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */