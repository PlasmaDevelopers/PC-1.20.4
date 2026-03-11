/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ 
/*    */ public class CountDownCooldownTicks
/*    */   extends Behavior<LivingEntity> {
/*    */   private final MemoryModuleType<Integer> cooldownTicks;
/*    */   
/*    */   public CountDownCooldownTicks(MemoryModuleType<Integer> $$0) {
/* 16 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of($$0, MemoryStatus.VALUE_PRESENT));
/*    */ 
/*    */     
/* 19 */     this.cooldownTicks = $$0;
/*    */   }
/*    */   
/*    */   private Optional<Integer> getCooldownTickMemory(LivingEntity $$0) {
/* 23 */     return $$0.getBrain().getMemory(this.cooldownTicks);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean timedOut(long $$0) {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 33 */     Optional<Integer> $$3 = getCooldownTickMemory($$1);
/* 34 */     return ($$3.isPresent() && ((Integer)$$3.get()).intValue() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 39 */     Optional<Integer> $$3 = getCooldownTickMemory($$1);
/* 40 */     $$1.getBrain().setMemory(this.cooldownTicks, Integer.valueOf(((Integer)$$3.get()).intValue() - 1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, LivingEntity $$1, long $$2) {
/* 45 */     $$1.getBrain().eraseMemory(this.cooldownTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\CountDownCooldownTicks.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */