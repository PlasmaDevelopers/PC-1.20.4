/*    */ package net.minecraft.world.entity.ai.navigation;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WallClimberNavigation
/*    */   extends GroundPathNavigation
/*    */ {
/*    */   @Nullable
/*    */   private BlockPos pathToPosition;
/*    */   
/*    */   public WallClimberNavigation(Mob $$0, Level $$1) {
/* 24 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Path createPath(BlockPos $$0, int $$1) {
/* 29 */     this.pathToPosition = $$0;
/* 30 */     return super.createPath($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Path createPath(Entity $$0, int $$1) {
/* 35 */     this.pathToPosition = $$0.blockPosition();
/* 36 */     return super.createPath($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean moveTo(Entity $$0, double $$1) {
/* 41 */     Path $$2 = createPath($$0, 0);
/* 42 */     if ($$2 != null) {
/* 43 */       return moveTo($$2, $$1);
/*    */     }
/* 45 */     this.pathToPosition = $$0.blockPosition();
/* 46 */     this.speedModifier = $$1;
/* 47 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick() {
/* 53 */     if (isDone()) {
/* 54 */       if (this.pathToPosition != null)
/*    */       {
/* 56 */         if (this.pathToPosition.closerToCenterThan((Position)this.mob.position(), this.mob.getBbWidth()) || (this.mob.getY() > this.pathToPosition.getY() && BlockPos.containing(this.pathToPosition.getX(), this.mob.getY(), this.pathToPosition.getZ()).closerToCenterThan((Position)this.mob.position(), this.mob.getBbWidth()))) {
/* 57 */           this.pathToPosition = null;
/*    */         } else {
/* 59 */           this.mob.getMoveControl().setWantedPosition(this.pathToPosition.getX(), this.pathToPosition.getY(), this.pathToPosition.getZ(), this.speedModifier);
/*    */         } 
/*    */       }
/*    */       return;
/*    */     } 
/* 64 */     super.tick();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\navigation\WallClimberNavigation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */