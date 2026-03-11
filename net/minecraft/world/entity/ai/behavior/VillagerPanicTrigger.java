/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.schedule.Activity;
/*    */ 
/*    */ public class VillagerPanicTrigger
/*    */   extends Behavior<Villager> {
/*    */   public VillagerPanicTrigger() {
/* 16 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of());
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 21 */     return (isHurt((LivingEntity)$$1) || hasHostile((LivingEntity)$$1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/* 26 */     if (isHurt((LivingEntity)$$1) || hasHostile((LivingEntity)$$1)) {
/* 27 */       Brain<?> $$3 = $$1.getBrain();
/*    */ 
/*    */       
/* 30 */       if (!$$3.isActive(Activity.PANIC)) {
/* 31 */         $$3.eraseMemory(MemoryModuleType.PATH);
/* 32 */         $$3.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 33 */         $$3.eraseMemory(MemoryModuleType.LOOK_TARGET);
/* 34 */         $$3.eraseMemory(MemoryModuleType.BREED_TARGET);
/* 35 */         $$3.eraseMemory(MemoryModuleType.INTERACTION_TARGET);
/*    */       } 
/* 37 */       $$3.setActiveActivityIfPossible(Activity.PANIC);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 43 */     if ($$2 % 100L == 0L) {
/* 44 */       $$1.spawnGolemIfNeeded($$0, $$2, 3);
/*    */     }
/*    */   }
/*    */   
/*    */   public static boolean hasHostile(LivingEntity $$0) {
/* 49 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.NEAREST_HOSTILE);
/*    */   }
/*    */   
/*    */   public static boolean isHurt(LivingEntity $$0) {
/* 53 */     return $$0.getBrain().hasMemoryValue(MemoryModuleType.HURT_BY);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\VillagerPanicTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */