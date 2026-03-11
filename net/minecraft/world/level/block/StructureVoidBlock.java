/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class StructureVoidBlock extends Block {
/* 11 */   public static final MapCodec<StructureVoidBlock> CODEC = simpleCodec(StructureVoidBlock::new);
/*    */   private static final double SIZE = 5.0D;
/*    */   
/*    */   public MapCodec<StructureVoidBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */ 
/*    */   
/* 19 */   private static final VoxelShape SHAPE = Block.box(5.0D, 5.0D, 5.0D, 11.0D, 11.0D, 11.0D);
/*    */   
/*    */   protected StructureVoidBlock(BlockBehaviour.Properties $$0) {
/* 22 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public RenderShape getRenderShape(BlockState $$0) {
/* 27 */     return RenderShape.INVISIBLE;
/*    */   }
/*    */ 
/*    */   
/*    */   public VoxelShape getShape(BlockState $$0, BlockGetter $$1, BlockPos $$2, CollisionContext $$3) {
/* 32 */     return SHAPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadeBrightness(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 37 */     return 1.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\StructureVoidBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */