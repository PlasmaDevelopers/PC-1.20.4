/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class AirBlock extends Block {
/* 12 */   public static final MapCodec<AirBlock> CODEC = simpleCodec(AirBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<AirBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/*    */   public AirBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 25 */     return RenderShape.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 30 */     return Shapes.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AirBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */