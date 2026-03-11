/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.tags.FluidTags;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ 
/*    */ public class TryFindWaterGoal extends Goal {
/*    */   private final PathfinderMob mob;
/*    */   
/*    */   public TryFindWaterGoal(PathfinderMob $$0) {
/* 12 */     this.mob = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 17 */     return (this.mob.onGround() && !this.mob.level().getFluidState(this.mob.blockPosition()).is(FluidTags.WATER));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 22 */     BlockPos $$0 = null;
/*    */     
/* 24 */     Iterable<BlockPos> $$1 = BlockPos.betweenClosed(
/* 25 */         Mth.floor(this.mob.getX() - 2.0D), 
/* 26 */         Mth.floor(this.mob.getY() - 2.0D), 
/* 27 */         Mth.floor(this.mob.getZ() - 2.0D), 
/* 28 */         Mth.floor(this.mob.getX() + 2.0D), this.mob
/* 29 */         .getBlockY(), 
/* 30 */         Mth.floor(this.mob.getZ() + 2.0D));
/*    */ 
/*    */     
/* 33 */     for (BlockPos $$2 : $$1) {
/* 34 */       if (this.mob.level().getFluidState($$2).is(FluidTags.WATER)) {
/* 35 */         $$0 = $$2;
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 40 */     if ($$0 != null)
/* 41 */       this.mob.getMoveControl().setWantedPosition($$0.getX(), $$0.getY(), $$0.getZ(), 1.0D); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\TryFindWaterGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */