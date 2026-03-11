/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class MudBlock extends Block {
/* 13 */   public static final MapCodec<MudBlock> CODEC = simpleCodec(MudBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<MudBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/* 20 */   protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);
/*    */   
/*    */   public MudBlock(BlockBehaviour.Properties $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getCollisionShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 28 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getBlockSupportShape(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 33 */     return Shapes.block();
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 38 */     return Shapes.block();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPathfindable(BlockState $$0, BlockGetter $$1, BlockPos $$2, PathComputationType $$3) {
/* 43 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 48 */     return 0.2F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MudBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */