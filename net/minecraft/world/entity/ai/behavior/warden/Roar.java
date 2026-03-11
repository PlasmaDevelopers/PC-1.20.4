/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ import net.minecraft.world.entity.monster.warden.WardenAi;
/*    */ 
/*    */ public class Roar extends Behavior<Warden> {
/*    */   public Roar() {
/* 22 */     super((Map)ImmutableMap.of(MemoryModuleType.ROAR_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.ROAR_SOUND_COOLDOWN, MemoryStatus.REGISTERED, MemoryModuleType.ROAR_SOUND_DELAY, MemoryStatus.REGISTERED), WardenAi.ROAR_DURATION);
/*    */   }
/*    */ 
/*    */   
/*    */   private static final int TICKS_BEFORE_PLAYING_ROAR_SOUND = 25;
/*    */   
/*    */   private static final int ROAR_ANGER_INCREASE = 20;
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Warden $$1, long $$2) {
/* 32 */     Brain<Warden> $$3 = $$1.getBrain();
/* 33 */     $$3.setMemoryWithExpiry(MemoryModuleType.ROAR_SOUND_DELAY, Unit.INSTANCE, 25L);
/* 34 */     $$3.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 35 */     LivingEntity $$4 = $$1.getBrain().getMemory(MemoryModuleType.ROAR_TARGET).get();
/* 36 */     BehaviorUtils.lookAtEntity((LivingEntity)$$1, $$4);
/* 37 */     $$1.setPose(Pose.ROARING);
/* 38 */     $$1.increaseAngerAt((Entity)$$4, 20, false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Warden $$1, long $$2) {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Warden $$1, long $$2) {
/* 48 */     if ($$1.getBrain().hasMemoryValue(MemoryModuleType.ROAR_SOUND_DELAY) || $$1.getBrain().hasMemoryValue(MemoryModuleType.ROAR_SOUND_COOLDOWN)) {
/*    */       return;
/*    */     }
/*    */     
/* 52 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.ROAR_SOUND_COOLDOWN, Unit.INSTANCE, (WardenAi.ROAR_DURATION - 25));
/*    */     
/* 54 */     $$1.playSound(SoundEvents.WARDEN_ROAR, 3.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Warden $$1, long $$2) {
/* 59 */     if ($$1.hasPose(Pose.ROARING)) {
/* 60 */       $$1.setPose(Pose.STANDING);
/*    */     }
/*    */     
/* 63 */     Objects.requireNonNull($$1); $$1.getBrain().getMemory(MemoryModuleType.ROAR_TARGET).ifPresent($$1::setAttackTarget);
/* 64 */     $$1.getBrain().eraseMemory(MemoryModuleType.ROAR_TARGET);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\Roar.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */