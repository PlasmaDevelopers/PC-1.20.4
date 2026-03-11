/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class HalfTransparentBlock extends Block {
/*  8 */   public static final MapCodec<HalfTransparentBlock> CODEC = simpleCodec(HalfTransparentBlock::new);
/*    */ 
/*    */   
/*    */   protected MapCodec<? extends HalfTransparentBlock> codec() {
/* 12 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected HalfTransparentBlock(BlockBehaviour.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean skipRendering(BlockState $$0, BlockState $$1, Direction $$2) {
/* 21 */     if ($$1.is(this)) {
/* 22 */       return true;
/*    */     }
/* 24 */     return super.skipRendering($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\HalfTransparentBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */