/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.SculkCatalystBlock;
/*     */ import net.minecraft.world.level.block.SculkSpreader;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.gameevent.GameEventListener;
/*     */ import net.minecraft.world.level.gameevent.PositionSource;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CatalystListener
/*     */   implements GameEventListener
/*     */ {
/*     */   public static final int PULSE_TICKS = 8;
/*     */   final SculkSpreader sculkSpreader;
/*     */   private final BlockState blockState;
/*     */   private final PositionSource positionSource;
/*     */   
/*     */   public CatalystListener(BlockState $$0, PositionSource $$1) {
/*  62 */     this.blockState = $$0;
/*  63 */     this.positionSource = $$1;
/*  64 */     this.sculkSpreader = SculkSpreader.createLevelSpreader();
/*     */   }
/*     */ 
/*     */   
/*     */   public PositionSource getListenerSource() {
/*  69 */     return this.positionSource;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getListenerRadius() {
/*  74 */     return 8;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameEventListener.DeliveryMode getDeliveryMode() {
/*  79 */     return GameEventListener.DeliveryMode.BY_DISTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean handleGameEvent(ServerLevel $$0, GameEvent $$1, GameEvent.Context $$2, Vec3 $$3) {
/*  84 */     if ($$1 == GameEvent.ENTITY_DIE) { Entity entity = $$2.sourceEntity(); if (entity instanceof LivingEntity) { LivingEntity $$4 = (LivingEntity)entity;
/*  85 */         if (!$$4.wasExperienceConsumed()) {
/*  86 */           int $$5 = $$4.getExperienceReward();
/*  87 */           if ($$4.shouldDropExperience() && $$5 > 0) {
/*  88 */             this.sculkSpreader.addCursors(BlockPos.containing((Position)$$3.relative(Direction.UP, 0.5D)), $$5);
/*  89 */             tryAwardItSpreadsAdvancement((Level)$$0, $$4);
/*     */           } 
/*  91 */           $$4.skipDropExperience();
/*  92 */           this.positionSource.getPosition((Level)$$0).ifPresent($$1 -> bloom($$0, BlockPos.containing((Position)$$1), this.blockState, $$0.getRandom()));
/*     */         } 
/*  94 */         return true; }
/*     */        }
/*     */     
/*  97 */     return false;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   public SculkSpreader getSculkSpreader() {
/* 102 */     return this.sculkSpreader;
/*     */   }
/*     */   
/*     */   private void bloom(ServerLevel $$0, BlockPos $$1, BlockState $$2, RandomSource $$3) {
/* 106 */     $$0.setBlock($$1, (BlockState)$$2.setValue((Property)SculkCatalystBlock.PULSE, Boolean.valueOf(true)), 3);
/* 107 */     $$0.scheduleTick($$1, $$2.getBlock(), 8);
/*     */     
/* 109 */     $$0.sendParticles((ParticleOptions)ParticleTypes.SCULK_SOUL, $$1.getX() + 0.5D, $$1.getY() + 1.15D, $$1.getZ() + 0.5D, 2, 0.2D, 0.0D, 0.2D, 0.0D);
/*     */     
/* 111 */     $$0.playSound(null, $$1, SoundEvents.SCULK_CATALYST_BLOOM, SoundSource.BLOCKS, 2.0F, 0.6F + $$3.nextFloat() * 0.4F);
/*     */   }
/*     */   
/*     */   private void tryAwardItSpreadsAdvancement(Level $$0, LivingEntity $$1) {
/* 115 */     LivingEntity $$2 = $$1.getLastHurtByMob();
/* 116 */     if ($$2 instanceof ServerPlayer) { ServerPlayer $$3 = (ServerPlayer)$$2;
/* 117 */       DamageSource $$4 = ($$1.getLastDamageSource() == null) ? $$0.damageSources().playerAttack((Player)$$3) : $$1.getLastDamageSource();
/* 118 */       CriteriaTriggers.KILL_MOB_NEAR_SCULK_CATALYST.trigger($$3, (Entity)$$1, $$4); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\SculkCatalystBlockEntity$CatalystListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */