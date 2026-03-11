/*    */ package net.minecraft.world.entity.monster.breeze;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.Pose;
/*    */ import net.minecraft.world.entity.ai.behavior.Behavior;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryModuleType;
/*    */ import net.minecraft.world.entity.ai.memory.MemoryStatus;
/*    */ import net.minecraft.world.entity.ai.memory.WalkTarget;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.entity.ai.util.LandRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class Slide extends Behavior<Breeze> {
/*    */   public Slide() {
/* 23 */     super(Map.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_JUMP_COOLDOWN, MemoryStatus.VALUE_ABSENT, MemoryModuleType.BREEZE_SHOOT, MemoryStatus.VALUE_ABSENT));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean checkExtraStartConditions(ServerLevel $$0, Breeze $$1) {
/* 33 */     return ($$1.onGround() && !$$1.isInWater() && $$1.getPose() == Pose.STANDING);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void start(ServerLevel $$0, Breeze $$1, long $$2) {
/* 38 */     LivingEntity $$3 = $$1.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
/* 39 */     if ($$3 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     boolean $$4 = $$1.withinOuterCircleRange($$3.position());
/* 44 */     boolean $$5 = $$1.withinMiddleCircleRange($$3.position());
/* 45 */     boolean $$6 = $$1.withinInnerCircleRange($$3.position());
/*    */     
/* 47 */     Vec3 $$7 = null;
/*    */     
/* 49 */     if ($$4) {
/*    */       
/* 51 */       $$7 = randomPointInMiddleCircle($$1, $$3);
/* 52 */     } else if ($$6) {
/*    */       
/* 54 */       Vec3 $$8 = DefaultRandomPos.getPosAway((PathfinderMob)$$1, 5, 5, $$3.position());
/* 55 */       if ($$8 != null && $$3.distanceToSqr($$8.x, $$8.y, $$8.z) > $$3.distanceToSqr((Entity)$$1)) {
/* 56 */         $$7 = $$8;
/*    */       }
/* 58 */     } else if ($$5) {
/*    */       
/* 60 */       $$7 = LandRandomPos.getPos((PathfinderMob)$$1, 5, 3);
/*    */     } 
/*    */     
/* 63 */     if ($$7 != null) {
/* 64 */       $$1.getBrain().setMemory(MemoryModuleType.WALK_TARGET, new WalkTarget(BlockPos.containing((Position)$$7), 0.6F, 1));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void stop(ServerLevel $$0, Breeze $$1, long $$2) {
/* 70 */     $$1.getBrain().setMemoryWithExpiry(MemoryModuleType.BREEZE_JUMP_COOLDOWN, Unit.INSTANCE, 20L);
/*    */   }
/*    */   
/*    */   private static Vec3 randomPointInMiddleCircle(Breeze $$0, LivingEntity $$1) {
/* 74 */     Vec3 $$2 = $$1.position().subtract($$0.position());
/* 75 */     double $$3 = $$2.length() - Mth.lerp($$0.getRandom().nextDouble(), 8.0D, 4.0D);
/*    */     
/* 77 */     Vec3 $$4 = $$2.normalize().multiply($$3, $$3, $$3);
/* 78 */     return $$0.position().add($$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\monster\breeze\Slide.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */