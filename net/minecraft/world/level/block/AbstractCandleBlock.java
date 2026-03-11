/*    */ package net.minecraft.world.level.block;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.util.function.BiConsumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.particles.ParticleOptions;
/*    */ import net.minecraft.core.particles.ParticleTypes;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.entity.projectile.Projectile;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.Explosion;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.block.state.BlockBehaviour;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.BlockStateProperties;
/*    */ import net.minecraft.world.level.block.state.properties.BooleanProperty;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.gameevent.GameEvent;
/*    */ import net.minecraft.world.phys.BlockHitResult;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public abstract class AbstractCandleBlock extends Block {
/* 29 */   public static final BooleanProperty LIT = BlockStateProperties.LIT;
/*    */   
/*    */   public static final int LIGHT_PER_CANDLE = 3;
/*    */ 
/*    */   
/*    */   protected AbstractCandleBlock(BlockBehaviour.Properties $$0) {
/* 35 */     super($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isLit(BlockState $$0) {
/* 41 */     return ($$0.hasProperty((Property)LIT) && ($$0.is(BlockTags.CANDLES) || $$0.is(BlockTags.CANDLE_CAKES)) && ((Boolean)$$0.getValue((Property)LIT)).booleanValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onProjectileHit(Level $$0, BlockState $$1, BlockHitResult $$2, Projectile $$3) {
/* 46 */     if (!$$0.isClientSide && $$3.isOnFire() && canBeLit($$1)) {
/* 47 */       setLit((LevelAccessor)$$0, $$1, $$2.getBlockPos(), true);
/*    */     }
/*    */   }
/*    */   
/*    */   protected boolean canBeLit(BlockState $$0) {
/* 52 */     return !((Boolean)$$0.getValue((Property)LIT)).booleanValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public void animateTick(BlockState $$0, Level $$1, BlockPos $$2, RandomSource $$3) {
/* 57 */     if (!((Boolean)$$0.getValue((Property)LIT)).booleanValue()) {
/*    */       return;
/*    */     }
/*    */     
/* 61 */     getParticleOffsets($$0).forEach($$3 -> addParticlesAndSound($$0, $$3.add($$1.getX(), $$1.getY(), $$1.getZ()), $$2));
/*    */   }
/*    */   
/*    */   private static void addParticlesAndSound(Level $$0, Vec3 $$1, RandomSource $$2) {
/* 65 */     float $$3 = $$2.nextFloat();
/* 66 */     if ($$3 < 0.3F) {
/* 67 */       $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$1.x, $$1.y, $$1.z, 0.0D, 0.0D, 0.0D);
/* 68 */       if ($$3 < 0.17F) {
/* 69 */         $$0.playLocalSound($$1.x + 0.5D, $$1.y + 0.5D, $$1.z + 0.5D, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + $$2.nextFloat(), $$2.nextFloat() * 0.7F + 0.3F, false);
/*    */       }
/*    */     } 
/* 72 */     $$0.addParticle((ParticleOptions)ParticleTypes.SMALL_FLAME, $$1.x, $$1.y, $$1.z, 0.0D, 0.0D, 0.0D);
/*    */   }
/*    */   
/*    */   public static void extinguish(@Nullable Player $$0, BlockState $$1, LevelAccessor $$2, BlockPos $$3) {
/* 76 */     setLit($$2, $$1, $$3, false);
/* 77 */     if ($$1.getBlock() instanceof AbstractCandleBlock) {
/* 78 */       ((AbstractCandleBlock)$$1.getBlock()).getParticleOffsets($$1).forEach($$2 -> $$0.addParticle((ParticleOptions)ParticleTypes.SMOKE, $$1.getX() + $$2.x(), $$1.getY() + $$2.y(), $$1.getZ() + $$2.z(), 0.0D, 0.10000000149011612D, 0.0D));
/*    */     }
/* 80 */     $$2.playSound(null, $$3, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 81 */     $$2.gameEvent((Entity)$$0, GameEvent.BLOCK_CHANGE, $$3);
/*    */   }
/*    */   
/*    */   private static void setLit(LevelAccessor $$0, BlockState $$1, BlockPos $$2, boolean $$3) {
/* 85 */     $$0.setBlock($$2, (BlockState)$$1.setValue((Property)LIT, Boolean.valueOf($$3)), 11);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onExplosionHit(BlockState $$0, Level $$1, BlockPos $$2, Explosion $$3, BiConsumer<ItemStack, BlockPos> $$4) {
/* 90 */     if ($$3.getBlockInteraction() == Explosion.BlockInteraction.TRIGGER_BLOCK && 
/* 91 */       !$$1.isClientSide() && ((Boolean)$$0
/* 92 */       .getValue((Property)LIT)).booleanValue()) {
/* 93 */       extinguish(null, $$0, (LevelAccessor)$$1, $$2);
/*    */     }
/* 95 */     super.onExplosionHit($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   protected abstract MapCodec<? extends AbstractCandleBlock> codec();
/*    */   
/*    */   protected abstract Iterable<Vec3> getParticleOffsets(BlockState paramBlockState);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\AbstractCandleBlock.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */