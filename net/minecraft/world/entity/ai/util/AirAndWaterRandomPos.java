/*    */ package net.minecraft.world.entity.ai.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class AirAndWaterRandomPos
/*    */ {
/*    */   @Nullable
/*    */   public static Vec3 getPos(PathfinderMob $$0, int $$1, int $$2, int $$3, double $$4, double $$5, double $$6) {
/* 12 */     boolean $$7 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 14 */     return RandomPos.generateRandomPos($$0, () -> generateRandomPos($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public static BlockPos generateRandomPos(PathfinderMob $$0, int $$1, int $$2, int $$3, double $$4, double $$5, double $$6, boolean $$7) {
/* 19 */     BlockPos $$8 = RandomPos.generateRandomDirectionWithinRadians($$0.getRandom(), $$1, $$2, $$3, $$4, $$5, $$6);
/* 20 */     if ($$8 == null) {
/* 21 */       return null;
/*    */     }
/*    */     
/* 24 */     BlockPos $$9 = RandomPos.generateRandomPosTowardDirection($$0, $$1, $$0.getRandom(), $$8);
/* 25 */     if (GoalUtils.isOutsideLimits($$9, $$0) || GoalUtils.isRestricted($$7, $$0, $$9)) {
/* 26 */       return null;
/*    */     }
/*    */     
/* 29 */     $$9 = RandomPos.moveUpOutOfSolid($$9, $$0.level().getMaxBuildHeight(), $$1 -> GoalUtils.isSolid($$0, $$1));
/* 30 */     if (GoalUtils.hasMalus($$0, $$9)) {
/* 31 */       return null;
/*    */     }
/*    */     
/* 34 */     return $$9;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\AirAndWaterRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */