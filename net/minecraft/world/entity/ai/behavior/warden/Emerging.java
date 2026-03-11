/*    */ package net.minecraft.world.entity.ai.behavior.warden;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.monster.warden.Warden;
/*    */ 
/*    */ public class Emerging<E extends Warden> extends Behavior<E> {
/*    */   public Emerging(int $$0) {
/* 14 */     super((Map)ImmutableMap.of(MemoryModuleType.IS_EMERGING, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), $$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, E $$1, long $$2) {
/* 23 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, E $$1, long $$2) {
/* 28 */     $$1.setPose(Pose.EMERGING);
/* 29 */     $$1.playSound(SoundEvents.WARDEN_EMERGE, 5.0F, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, E $$1, long $$2) {
/* 34 */     if ($$1.hasPose(Pose.EMERGING))
/* 35 */       $$1.setPose(Pose.STANDING); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\warden\Emerging.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */