/*    */ package net.minecraft.world.entity.ai.util;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class AirRandomPos
/*    */ {
/*    */   @Nullable
/*    */   public static Vec3 getPosTowards(PathfinderMob $$0, int $$1, int $$2, int $$3, Vec3 $$4, double $$5) {
/* 12 */     Vec3 $$6 = $$4.subtract($$0.getX(), $$0.getY(), $$0.getZ());
/* 13 */     boolean $$7 = GoalUtils.mobRestricted($$0, $$1);
/*    */     
/* 15 */     return RandomPos.generateRandomPos($$0, () -> {
/*    */           BlockPos $$7 = AirAndWaterRandomPos.generateRandomPos($$0, $$1, $$2, $$3, $$4.x, $$4.z, $$5, $$6);
/* 17 */           return ($$7 == null || GoalUtils.isWater($$0, $$7)) ? null : $$7;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\a\\util\AirRandomPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */