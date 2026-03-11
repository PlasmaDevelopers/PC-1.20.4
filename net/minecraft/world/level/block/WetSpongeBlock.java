/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ 
/*    */ public class WetSpongeBlock extends Block {
/* 15 */   public static final MapCodec<WetSpongeBlock> CODEC = simpleCodec(WetSpongeBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<WetSpongeBlock> codec() {
/* 19 */     return CODEC;
/*    */   }
/*    */   
/*    */   protected WetSpongeBlock(BlockBehaviour.Properties $$0) {
/* 23 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onPlace(BlockState $$0, Level $$1, BlockPos $$2, BlockState $$3, boolean $$4) {
/* 28 */     if ($$1.dimensionType().ultraWarm()) {
/* 29 */       $$1.setBlock($$2, Blocks.SPONGE.defaultBlockState(), 3);
/* 30 */       $$1.levelEvent(2009, $$2, 0);
/* 31 */       $$1.playSound(null, $$2, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, (1.0F + $$1.getRandom().nextFloat() * 0.2F) * 0.7F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 37 */     Direction $$4 = Direction.getRandom($$3);
/* 38 */     if ($$4 == Direction.UP) {
/*    */       return;
/*    */     }
/* 41 */     BlockPos $$5 = $$2.relative($$4);
/* 42 */     BlockState $$6 = $$1.getBlockState($$5);
/* 43 */     if ($$0.canOcclude() && $$6.isFaceSturdy((BlockGetter)$$1, $$5, $$4.getOpposite())) {
/*    */       return;
/*    */     }
/*    */     
/* 47 */     double $$7 = $$2.getX();
/* 48 */     double $$8 = $$2.getY();
/* 49 */     double $$9 = $$2.getZ();
/*    */ 
/*    */ 
/*    */     
/* 53 */     if ($$4 == Direction.DOWN) {
/* 54 */       $$8 -= 0.05D;
/* 55 */       $$7 += $$3.nextDouble();
/* 56 */       $$9 += $$3.nextDouble();
/*    */     } else {
/* 58 */       $$8 += $$3.nextDouble() * 0.8D;
/* 59 */       if ($$4.getAxis() == Direction.Axis.X) {
/* 60 */         $$9 += $$3.nextDouble();
/* 61 */         if ($$4 == Direction.EAST) {
/* 62 */           $$7 += 1.1D;
/*    */         } else {
/* 64 */           $$7 += 0.05D;
/*    */         } 
/*    */       } else {
/* 67 */         $$7 += $$3.nextDouble();
/* 68 */         if ($$4 == Direction.SOUTH) {
/* 69 */           $$9 += 1.1D;
/*    */         } else {
/* 71 */           $$9 += 0.05D;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     $$1.addParticle((ParticleOptions)ParticleTypes.DRIPPING_WATER, $$7, $$8, $$9, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\WetSpongeBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */