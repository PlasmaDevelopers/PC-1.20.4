/*    */ package net.minecraft.world.entity.ai.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class HoverRandomPos
/*    */ {
/*    */   @Nullable
/*    */   public static Vec3 getPos(PathfinderMob $$0, int $$1, int $$2, double $$3, double $$4, float $$5, int $$6, int $$7) {
/* 12 */     boolean $$8 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 14 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$9 = RandomPos.generateRandomDirectionWithinRadians($$0.getRandom(), $$1, $$2, 0, $$3, $$4, $$5);
/*    */           
/*    */           if ($$9 == null) {
/*    */             return null;
/*    */           }
/*    */           
/*    */           BlockPos $$10 = LandRandomPos.generateRandomPosTowardDirection($$0, $$1, $$6, $$9);
/*    */           if ($$10 == null) {
/*    */             return null;
/*    */           }
/*    */           $$10 = RandomPos.moveUpToAboveSolid($$10, $$0.getRandom().nextInt($$7 - $$8 + 1) + $$8, $$0.level().getMaxBuildHeight(), ());
/* 26 */           return (GoalUtils.isWater($$0, $$10) || GoalUtils.hasMalus($$0, $$10)) ? null : $$10;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\HoverRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */