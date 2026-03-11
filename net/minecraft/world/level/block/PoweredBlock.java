/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class PoweredBlock extends Block {
/* 11 */   public static final MapCodec<PoweredBlock> CODEC = simpleCodec(PoweredBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<PoweredBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */   
/*    */   public PoweredBlock(BlockBehaviour.Properties $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSignalSource(BlockState $$0) {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSignal(BlockState $$0, BlockGetter $$1, BlockPos $$2, Direction $$3) {
/* 29 */     return 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\PoweredBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */