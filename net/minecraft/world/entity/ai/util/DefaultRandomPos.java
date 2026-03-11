/*    */ package net.minecraft.world.entity.ai.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultRandomPos
/*    */ {
/*    */   @Nullable
/*    */   public static Vec3 getPos(PathfinderMob $$0, int $$1, int $$2) {
/* 14 */     boolean $$3 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 16 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$4 = RandomPos.generateRandomDirection($$0.getRandom(), $$1, $$2);
/*    */           return generateRandomPosTowardDirection($$0, $$1, $$3, $$4);
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Vec3 getPosTowards(PathfinderMob $$0, int $$1, int $$2, Vec3 $$3, double $$4) {
/* 25 */     Vec3 $$5 = $$3.subtract($$0.getX(), $$0.getY(), $$0.getZ());
/* 26 */     boolean $$6 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 28 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$6 = RandomPos.generateRandomDirectionWithinRadians($$0.getRandom(), $$1, $$2, 0, $$3.x, $$3.z, $$4);
/*    */           return ($$6 == null) ? null : generateRandomPosTowardDirection($$0, $$1, $$5, $$6);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Vec3 getPosAway(PathfinderMob $$0, int $$1, int $$2, Vec3 $$3) {
/* 41 */     Vec3 $$4 = $$0.position().subtract($$3);
/* 42 */     boolean $$5 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 44 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$5 = RandomPos.generateRandomDirectionWithinRadians($$0.getRandom(), $$1, $$2, 0, $$3.x, $$3.z, 1.5707963705062866D);
/*    */           return ($$5 == null) ? null : generateRandomPosTowardDirection($$0, $$1, $$4, $$5);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   private static BlockPos generateRandomPosTowardDirection(PathfinderMob $$0, int $$1, boolean $$2, BlockPos $$3) {
/* 56 */     BlockPos $$4 = RandomPos.generateRandomPosTowardDirection($$0, $$1, $$0.getRandom(), $$3);
/* 57 */     if (GoalUtils.isOutsideLimits($$4, $$0) || GoalUtils.isRestricted($$2, $$0, $$4) || GoalUtils.isNotStable($$0.getNavigation(), $$4) || GoalUtils.hasMalus($$0, $$4)) {
/* 58 */       return null;
/*    */     }
/*    */     
/* 61 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\DefaultRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */