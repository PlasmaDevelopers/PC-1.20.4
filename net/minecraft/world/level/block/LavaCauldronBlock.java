/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.cauldron.CauldronInteraction;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class LavaCauldronBlock extends AbstractCauldronBlock {
/* 12 */   public static final MapCodec<LavaCauldronBlock> CODEC = simpleCodec(LavaCauldronBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<LavaCauldronBlock> codec() {
/* 16 */     return CODEC;
/*    */   }
/*    */   
/*    */   public LavaCauldronBlock(BlockBehaviour.Properties $$0) {
/* 20 */     super($$0, CauldronInteraction.LAVA);
/*    */   }
/*    */ 
/*    */   
/*    */   protected double getContentHeight(BlockState $$0) {
/* 25 */     return 0.9375D;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFull(BlockState $$0) {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void entityInside(BlockState $$0, Level $$1, BlockPos $$2, Entity $$3) {
/* 35 */     if (isEntityInsideContent($$0, $$2, $$3)) {
/* 36 */       $$3.lavaHurt();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAnalogOutputSignal(BlockState $$0, Level $$1, BlockPos $$2) {
/* 42 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\LavaCauldronBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */