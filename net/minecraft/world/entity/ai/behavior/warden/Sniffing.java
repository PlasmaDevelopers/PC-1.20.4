/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ import net.minecraft.world.entity.monster.warden.WardenAi;
/*    */ 
/*    */ public class Sniffing<E extends Warden> extends Behavior<E> {
/*    */   public Sniffing(int $$0) {
/* 19 */     super((Map)ImmutableMap.of(MemoryModuleType.IS_SNIFFING, MemoryStatus.VALUE_PRESENT, MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.NEAREST_ATTACKABLE, MemoryStatus.REGISTERED, MemoryModuleType.DISTURBANCE_LOCATION, MemoryStatus.REGISTERED, MemoryModuleType.SNIFF_COOLDOWN, MemoryStatus.REGISTERED), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_XZ = 6.0D;
/*    */ 
/*    */   
/*    */   private static final double ANGER_FROM_SNIFFING_MAX_DISTANCE_Y = 20.0D;
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/* 37 */     $$1.playSound(SoundEvents.WARDEN_SNIFF, 5.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, E $$1, long $$2) {
/* 42 */     if ($$1.hasPose(Pose.SNIFFING)) {
/* 43 */       $$1.setPose(Pose.STANDING);
/*    */     }
/*    */     
/* 46 */     $$1.getBrain().eraseMemory(MemoryModuleType.IS_SNIFFING);
/* 47 */     Objects.requireNonNull($$1); $$1.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE).filter($$1::canTargetEntity).ifPresent($$1 -> {
/*    */           if ($$0.closerThan((Entity)$$1, 6.0D, 20.0D))
/*    */             $$0.increaseAngerAt((Entity)$$1); 
/*    */           if (!$$0.getBrain().hasMemoryValue(MemoryModuleType.DISTURBANCE_LOCATION))
/*    */             WardenAi.setDisturbanceLocation($$0, $$1.blockPosition()); 
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\Sniffing.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */