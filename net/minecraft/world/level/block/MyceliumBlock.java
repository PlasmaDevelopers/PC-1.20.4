/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class MyceliumBlock extends SpreadingSnowyDirtBlock {
/* 11 */   public static final MapCodec<MyceliumBlock> CODEC = simpleCodec(MyceliumBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<MyceliumBlock> codec() {
/* 15 */     return CODEC;
/*    */   }
/*    */   
/*    */   public MyceliumBlock(BlockBehaviour.Properties $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 24 */     super.animateTick($$0, $$1, $$2, $$3);
/* 25 */     if ($$3.nextInt(10) == 0)
/* 26 */       $$1.addParticle((ParticleOptions)ParticleTypes.MYCELIUM, $$2.getX() + $$3.nextDouble(), $$2.getY() + 1.1D, $$2.getZ() + $$3.nextDouble(), 0.0D, 0.0D, 0.0D); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\MyceliumBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */