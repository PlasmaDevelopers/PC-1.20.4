/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.Brain;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class LookAndFollowTradingPlayerSink extends Behavior<Villager> {
/*    */   public LookAndFollowTradingPlayerSink(float $$0) {
/* 16 */     super(
/* 17 */         (Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED, MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED), 2147483647);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 23 */     this.speedModifier = $$0;
/*    */   }
/*    */   private final float speedModifier;
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/* 28 */     Player $$2 = $$1.getTradingPlayer();
/*    */     
/* 30 */     return ($$1.isAlive() && $$2 != null && 
/*    */       
/* 32 */       !$$1.isInWater() && !$$1.hurtMarked && $$1
/*    */       
/* 34 */       .distanceToSqr((Entity)$$2) <= 16.0D && $$2.containerMenu != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 41 */     return checkExtraStartConditions($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Villager $$1, long $$2) {
/* 46 */     followPlayer($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 51 */     Brain<?> $$3 = $$1.getBrain();
/* 52 */     $$3.eraseMemory(MemoryModuleType.WALK_TARGET);
/* 53 */     $$3.eraseMemory(MemoryModuleType.LOOK_TARGET);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 58 */     followPlayer($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean timedOut(long $$0) {
/* 63 */     return false;
/*    */   }
/*    */   
/*    */   private void followPlayer(Villager $$0) {
/* 67 */     Brain<?> $$1 = $$0.getBrain();
/* 68 */     $$1.setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(new EntityTracker((Entity)$$0.getTradingPlayer(), false), this.speedModifier, 2));
/* 69 */     $$1.setMemory(MemoryModuleType.LOOK_TARGET, new EntityTracker((Entity)$$0.getTradingPlayer(), true));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\LookAndFollowTradingPlayerSink.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */