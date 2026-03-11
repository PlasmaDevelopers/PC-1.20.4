/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorkAtPoi
/*    */   extends Behavior<Villager>
/*    */ {
/*    */   private static final int CHECK_COOLDOWN = 300;
/*    */   private static final double DISTANCE = 1.73D;
/*    */   private long lastCheck;
/*    */   
/*    */   public WorkAtPoi() {
/* 25 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.JOB_SITE, MemoryStatus.VALUE_PRESENT, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/* 33 */     if ($$0.getGameTime() - this.lastCheck < 300L) {
/* 34 */       return false;
/*    */     }
/*    */     
/* 37 */     if ($$0.random.nextInt(2) != 0) {
/* 38 */       return false;
/*    */     }
/*    */     
/* 41 */     this.lastCheck = $$0.getGameTime();
/*    */     
/* 43 */     GlobalPos $$2 = $$1.getBrain().getMemory(MemoryModuleType.JOB_SITE).get();
/* 44 */     return ($$2.dimension() == $$0.dimension() && $$2.pos().closerToCenterThan((Position)$$1.position(), 1.73D));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/* 49 */     Brain<Villager> $$3 = $$1.getBrain();
/* 50 */     $$3.setMemory(MemoryModuleType.LAST_WORKED_AT_POI, Long.valueOf($$2));
/* 51 */     $$3.getMemory(MemoryModuleType.JOB_SITE).ifPresent($$1 -> $$0.setMemory(MemoryModuleType.LOOK_TARGET, new BlockPosTracker($$1.pos())));
/*    */ 
/*    */ 
/*    */     
/* 55 */     $$1.playWorkSound();
/* 56 */     useWorkstation($$0, $$1);
/*    */     
/* 58 */     if ($$1.shouldRestock()) {
/* 59 */       $$1.restock();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void useWorkstation(ServerLevel $$0, Villager $$1) {}
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 68 */     Optional<GlobalPos> $$3 = $$1.getBrain().getMemory(MemoryModuleType.JOB_SITE);
/* 69 */     if ($$3.isEmpty()) {
/* 70 */       return false;
/*    */     }
/*    */     
/* 73 */     GlobalPos $$4 = $$3.get();
/* 74 */     return ($$4.dimension() == $$0.dimension() && $$4
/* 75 */       .pos().closerToCenterThan((Position)$$1.position(), 1.73D));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\WorkAtPoi.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */