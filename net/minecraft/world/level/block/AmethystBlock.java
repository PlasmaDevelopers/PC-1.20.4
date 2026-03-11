/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.projectile.Projectile;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ 
/*    */ public class AmethystBlock extends Block {
/* 13 */   public static final MapCodec<AmethystBlock> CODEC = simpleCodec(AmethystBlock::new);
/*    */ 
/*    */   
/*    */   public MapCodec<? extends AmethystBlock> codec() {
/* 17 */     return CODEC;
/*    */   }
/*    */   
/*    */   public AmethystBlock(BlockBehaviour.Properties $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 26 */     if (!$$0.isClientSide) {
/* 27 */       BlockPos $$4 = $$2.getBlockPos();
/* 28 */       $$0.playSound(null, $$4, SoundEvents.AMETHYST_BLOCK_HIT, SoundSource.BLOCKS, 1.0F, 0.5F + $$0.random.nextFloat() * 1.2F);
/* 29 */       $$0.playSound(null, $$4, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F, 0.5F + $$0.random.nextFloat() * 1.2F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AmethystBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */