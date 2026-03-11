/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class BaseCoralPlantBlock extends BaseCoralPlantTypeBlock {
/* 11 */   public static final MapCodec<BaseCoralPlantBlock> CODEC = simpleCodec(BaseCoralPlantBlock::new);
/*    */   protected static final float AABB_OFFSET = 6.0F;
/*    */   
/*    */   public MapCodec<BaseCoralPlantBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 19 */   protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 15.0D, 14.0D);
/*    */   
/*    */   protected BaseCoralPlantBlock(BlockBehaviour.Properties $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 27 */     return SHAPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\BaseCoralPlantBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */