/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.GlobalPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.ai.village.poi.PoiManager;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ import net.minecraft.world.entity.schedule.Activity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GoToPotentialJobSite
/*    */   extends Behavior<Villager>
/*    */ {
/*    */   private static final int TICKS_UNTIL_TIMEOUT = 1200;
/*    */   final float speedModifier;
/*    */   
/*    */   public GoToPotentialJobSite(float $$0) {
/* 29 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.POTENTIAL_JOB_SITE, MemoryStatus.VALUE_PRESENT), 1200);
/*    */ 
/*    */     
/* 32 */     this.speedModifier = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Villager $$1) {
/* 37 */     return ((Boolean)$$1.getBrain().getActiveNonCoreActivity().map($$0 -> Boolean.valueOf(($$0 == Activity.IDLE || $$0 == Activity.WORK || $$0 == Activity.PLAY))).orElse(Boolean.valueOf(true))).booleanValue();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Villager $$1, long $$2) {
/* 43 */     return $$1.getBrain().hasMemoryValue(MemoryModuleType.POTENTIAL_JOB_SITE);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Villager $$1, long $$2) {
/* 48 */     BehaviorUtils.setWalkAndLookTargetMemories((LivingEntity)$$1, ((GlobalPos)$$1.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get()).pos(), this.speedModifier, 1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Villager $$1, long $$2) {
/* 53 */     Optional<GlobalPos> $$3 = $$1.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
/* 54 */     $$3.ifPresent($$1 -> {
/*    */           BlockPos $$2 = $$1.pos();
/*    */           ServerLevel $$3 = $$0.getServer().getLevel($$1.dimension());
/*    */           if ($$3 == null) {
/*    */             return;
/*    */           }
/*    */           PoiManager $$4 = $$3.getPoiManager();
/*    */           if ($$4.exists($$2, ())) {
/*    */             $$4.release($$2);
/*    */           }
/*    */           DebugPackets.sendPoiTicketCountPacket($$0, $$2);
/*    */         });
/* 66 */     $$1.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\GoToPotentialJobSite.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */