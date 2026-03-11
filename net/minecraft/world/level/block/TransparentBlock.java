/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.Shapes;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class TransparentBlock extends HalfTransparentBlock {
/* 12 */   public static final MapCodec<TransparentBlock> CODEC = simpleCodec(TransparentBlock::new);
/*    */   protected TransparentBlock(BlockBehaviour.Properties $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected MapCodec<? extends TransparentBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getVisualShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 24 */     return Shapes.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 29 */     return 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TransparentBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */