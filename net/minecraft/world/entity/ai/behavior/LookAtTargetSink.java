/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class LookAtTargetSink extends Behavior<Mob> {
/*    */   public LookAtTargetSink(int $$0, int $$1) {
/* 11 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.LOOK_TARGET, MemoryStatus.VALUE_PRESENT), $$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Mob $$1, long $$2) {
/* 16 */     return $$1.getBrain().getMemory(MemoryModuleType.LOOK_TARGET)
/* 17 */       .filter($$1 -> $$1.isVisibleBy((LivingEntity)$$0))
/* 18 */       .isPresent();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Mob $$1, long $$2) {
/* 23 */     $$1.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Mob $$1, long $$2) {
/* 28 */     $$1.getBrain().getMemory(MemoryModuleType.LOOK_TARGET).ifPresent($$1 -> $$0.getLookControl().setLookAt($$1.currentPosition()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LookAtTargetSink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */