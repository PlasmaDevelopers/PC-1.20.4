/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class MoveBackToVillageGoal
/*    */   extends RandomStrollGoal {
/*    */   private static final int MAX_XZ_DIST = 10;
/*    */   private static final int MAX_Y_DIST = 7;
/*    */   
/*    */   public MoveBackToVillageGoal(PathfinderMob $$0, double $$1, boolean $$2) {
/* 19 */     super($$0, $$1, 10, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 24 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/* 25 */     BlockPos $$1 = this.mob.blockPosition();
/*    */     
/* 27 */     if ($$0.isVillage($$1)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     return super.canUse();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Vec3 getPosition() {
/* 37 */     ServerLevel $$0 = (ServerLevel)this.mob.level();
/* 38 */     BlockPos $$1 = this.mob.blockPosition();
/*    */     
/* 40 */     SectionPos $$2 = SectionPos.of($$1);
/* 41 */     SectionPos $$3 = BehaviorUtils.findSectionClosestToVillage($$0, $$2, 2);
/*    */     
/* 43 */     if ($$3 != $$2) {
/* 44 */       return DefaultRandomPos.getPosTowards(this.mob, 10, 7, Vec3.atBottomCenterOf((Vec3i)$$3.center()), 1.5707963705062866D);
/*    */     }
/*    */     
/* 47 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\MoveBackToVillageGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */