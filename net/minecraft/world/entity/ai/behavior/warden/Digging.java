/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class Digging<E extends Warden> extends Behavior<E> {
/*    */   public Digging(int $$0) {
/* 15 */     super((Map)ImmutableMap.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/* 23 */     return ($$1.getRemovalReason() == null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, E $$1) {
/* 28 */     return ($$1.onGround() || $$1.isInWater() || $$1.isInLava());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/* 33 */     if ($$1.onGround()) {
/* 34 */       $$1.setPose(Pose.DIGGING);
/* 35 */       $$1.playSound(SoundEvents.WARDEN_DIG, 5.0F, 1.0F);
/*    */     } else {
/* 37 */       $$1.playSound(SoundEvents.WARDEN_AGITATED, 5.0F, 1.0F);
/* 38 */       stop($$0, $$1, $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, E $$1, long $$2) {
/* 44 */     if ($$1.getRemovalReason() == null)
/* 45 */       $$1.remove(Entity.RemovalReason.DISCARDED); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\Digging.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */