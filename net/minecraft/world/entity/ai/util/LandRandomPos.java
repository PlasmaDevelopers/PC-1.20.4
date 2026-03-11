/*    */ package net.minecraft.world.entity.ai.util;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.function.ToDoubleFunction;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class LandRandomPos
/*    */ {
/*    */   @Nullable
/*    */   public static Vec3 getPos(PathfinderMob $$0, int $$1, int $$2) {
/* 14 */     Objects.requireNonNull($$0); return getPos($$0, $$1, $$2, $$0::getWalkTargetValue);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Vec3 getPos(PathfinderMob $$0, int $$1, int $$2, ToDoubleFunction<BlockPos> $$3) {
/* 19 */     boolean $$4 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 21 */     return RandomPos.generateRandomPos(() -> { BlockPos $$4 = RandomPos.generateRandomDirection($$0.getRandom(), $$1, $$2); BlockPos $$5 = generateRandomPosTowardDirection($$0, $$1, $$3, $$4); return ($$5 == null) ? null : movePosUpOutOfSolid($$0, $$5); }$$3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static Vec3 getPosTowards(PathfinderMob $$0, int $$1, int $$2, Vec3 $$3) {
/* 35 */     Vec3 $$4 = $$3.subtract($$0.getX(), $$0.getY(), $$0.getZ());
/* 36 */     boolean $$5 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 38 */     return getPosInDirection($$0, $$1, $$2, $$4, $$5);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static Vec3 getPosAway(PathfinderMob $$0, int $$1, int $$2, Vec3 $$3) {
/* 43 */     Vec3 $$4 = $$0.position().subtract($$3);
/* 44 */     boolean $$5 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 46 */     return getPosInDirection($$0, $$1, $$2, $$4, $$5);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private static Vec3 getPosInDirection(PathfinderMob $$0, int $$1, int $$2, Vec3 $$3, boolean $$4) {
/* 51 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$5 = RandomPos.generateRandomDirectionWithinRadians($$0.getRandom(), $$1, $$2, 0, $$3.x, $$3.z, 1.5707963705062866D);
/*    */           if ($$5 == null) {
/*    */             return null;
/*    */           }
/*    */           BlockPos $$6 = generateRandomPosTowardDirection($$0, $$1, $$4, $$5);
/*    */           return ($$6 == null) ? null : movePosUpOutOfSolid($$0, $$6);
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static BlockPos movePosUpOutOfSolid(PathfinderMob $$0, BlockPos $$1) {
/* 68 */     $$1 = RandomPos.moveUpOutOfSolid($$1, $$0.level().getMaxBuildHeight(), $$1 -> GoalUtils.isSolid($$0, $$1));
/* 69 */     if (GoalUtils.isWater($$0, $$1) || GoalUtils.hasMalus($$0, $$1)) {
/* 70 */       return null;
/*    */     }
/* 72 */     return $$1;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BlockPos generateRandomPosTowardDirection(PathfinderMob $$0, int $$1, boolean $$2, BlockPos $$3) {
/* 77 */     BlockPos $$4 = RandomPos.generateRandomPosTowardDirection($$0, $$1, $$0.getRandom(), $$3);
/* 78 */     if (GoalUtils.isOutsideLimits($$4, $$0) || GoalUtils.isRestricted($$2, $$0, $$4) || GoalUtils.isNotStable($$0.getNavigation(), $$4)) {
/* 79 */       return null;
/*    */     }
/*    */     
/* 82 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\LandRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */