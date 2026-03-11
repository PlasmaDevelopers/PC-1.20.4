/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class TintedGlassBlock extends TransparentBlock {
/*  9 */   public static final MapCodec<TintedGlassBlock> CODEC = simpleCodec(TintedGlassBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<TintedGlassBlock> codec() {
/* 13 */     return CODEC;
/*    */   }
/*    */   public TintedGlassBlock(BlockBehaviour.Properties $$0) {
/* 16 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean propagatesSkylightDown(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 21 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLightBlock(BlockState $$0, BlockGetter $$1, BlockPos $$2) {
/* 26 */     return $$1.getMaxLightLevel();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\TintedGlassBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */