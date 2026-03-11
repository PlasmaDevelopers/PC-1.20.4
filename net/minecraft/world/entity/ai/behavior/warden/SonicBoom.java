/*     */ package net.minecraft.world.entity.ai.behavior.warden;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.Unit;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*     */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*     */ import net.minecraft.world.entity.monster.warden.Warden;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class SonicBoom extends Behavior<Warden> {
/*     */   private static final int DISTANCE_XZ = 15;
/*     */   private static final int DISTANCE_Y = 20;
/*     */   private static final double KNOCKBACK_VERTICAL = 0.5D;
/*     */   private static final double KNOCKBACK_HORIZONTAL = 2.5D;
/*     */   public static final int COOLDOWN = 40;
/*  27 */   private static final int TICKS_BEFORE_PLAYING_SOUND = Mth.ceil(34.0D);
/*  28 */   private static final int DURATION = Mth.ceil(60.0F);
/*     */   
/*     */   public SonicBoom() {
/*  31 */     super((Map)ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.SONIC_BOOM_COOLDOWN, MemoryStatus.VALUE_ABSENT, MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, MemoryStatus.REGISTERED, MemoryModuleType.SONIC_BOOM_SOUND_DELAY, MemoryStatus.REGISTERED), DURATION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkExtraStartConditions(ServerLevel $$0, Warden $$1) {
/*  41 */     return $$1.closerThan($$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).get(), 15.0D, 20.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canStillUse(ServerLevel $$0, Warden $$1, long $$2) {
/*  46 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void start(ServerLevel $$0, Warden $$1, long $$2) {
/*  52 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, Boolean.valueOf(true), DURATION);
/*     */     
/*  54 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_DELAY, Unit.INSTANCE, TICKS_BEFORE_PLAYING_SOUND);
/*     */     
/*  56 */     $$0.broadcastEntityEvent((Entity)$$1, (byte)62);
/*  57 */     $$1.playSound(SoundEvents.WARDEN_SONIC_CHARGE, 3.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void tick(ServerLevel $$0, Warden $$1, long $$2) {
/*  62 */     $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).ifPresent($$1 -> $$0.getLookControl().setLookAt($$1.position()));
/*     */ 
/*     */ 
/*     */     
/*  66 */     if ($$1.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_DELAY) || $$1.getBrain().hasMemoryValue(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN)) {
/*     */       return;
/*     */     }
/*     */     
/*  70 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_SOUND_COOLDOWN, Unit.INSTANCE, (DURATION - TICKS_BEFORE_PLAYING_SOUND));
/*     */     
/*  72 */     Objects.requireNonNull($$1); $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter($$1::canTargetEntity)
/*  73 */       .filter($$1 -> $$0.closerThan((Entity)$$1, 15.0D, 20.0D))
/*  74 */       .ifPresent($$2 -> {
/*     */           Vec3 $$3 = $$0.position().add(0.0D, 1.600000023841858D, 0.0D);
/*     */           Vec3 $$4 = $$2.getEyePosition().subtract($$3);
/*     */           Vec3 $$5 = $$4.normalize();
/*     */           for (int $$6 = 1; $$6 < Mth.floor($$4.length()) + 7; $$6++) {
/*     */             Vec3 $$7 = $$3.add($$5.scale($$6));
/*     */             $$1.sendParticles((ParticleOptions)ParticleTypes.SONIC_BOOM, $$7.x, $$7.y, $$7.z, 1, 0.0D, 0.0D, 0.0D, 0.0D);
/*     */           } 
/*     */           $$0.playSound(SoundEvents.WARDEN_SONIC_BOOM, 3.0F, 1.0F);
/*     */           $$2.hurt($$1.damageSources().sonicBoom((Entity)$$0), 10.0F);
/*     */           double $$8 = 0.5D * (1.0D - $$2.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
/*     */           double $$9 = 2.5D * (1.0D - $$2.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
/*     */           $$2.push($$5.x() * $$9, $$5.y() * $$8, $$5.z() * $$9);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void stop(ServerLevel $$0, Warden $$1, long $$2) {
/*  97 */     setCooldown((LivingEntity)$$1, 40);
/*     */   }
/*     */   
/*     */   public static void setCooldown(LivingEntity $$0, int $$1) {
/* 101 */     $$0.getBrain().setMemoryWithExpiry(MemoryModuleType.SONIC_BOOM_COOLDOWN, Unit.INSTANCE, $$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\SonicBoom.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */