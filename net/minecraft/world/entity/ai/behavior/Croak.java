/*    */ package net.minecraft.world.entity.ai.behavior;
/*    */ 
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.animal.frog.Frog;
/*    */ 
/*    */ public class Croak extends Behavior<Frog> {
/*    */   private static final int CROAK_TICKS = 60;
/*    */   private static final int TIME_OUT_DURATION = 100;
/*    */   private int croakCounter;
/*    */   
/*    */   public Croak() {
/* 18 */     super((Map<MemoryModuleType<?>, MemoryStatus>)ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), 100);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Frog $$1) {
/* 25 */     return ($$1.getPose() == Pose.STANDING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canStillUse(ServerLevel $$0, Frog $$1, long $$2) {
/* 30 */     return (this.croakCounter < 60);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Frog $$1, long $$2) {
/* 35 */     if ($$1.isInLiquid()) {
/*    */       return;
/*    */     }
/* 38 */     $$1.setPose(Pose.CROAKING);
/* 39 */     this.croakCounter = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Frog $$1, long $$2) {
/* 44 */     $$1.setPose(Pose.STANDING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void tick(ServerLevel $$0, Frog $$1, long $$2) {
/* 49 */     this.croakCounter++;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\behavior\Croak.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */