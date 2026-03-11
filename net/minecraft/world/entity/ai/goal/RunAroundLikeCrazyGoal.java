/*    */ package net.minecraft.world.entity.ai.goal;
/*    */ 
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.PathfinderMob;
/*    */ import net.minecraft.world.entity.ai.util.DefaultRandomPos;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class RunAroundLikeCrazyGoal
/*    */   extends Goal {
/*    */   private final AbstractHorse horse;
/*    */   private final double speedModifier;
/*    */   private double posX;
/*    */   private double posY;
/*    */   private double posZ;
/*    */   
/*    */   public RunAroundLikeCrazyGoal(AbstractHorse $$0, double $$1) {
/* 20 */     this.horse = $$0;
/* 21 */     this.speedModifier = $$1;
/* 22 */     setFlags(EnumSet.of(Goal.Flag.MOVE));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canUse() {
/* 27 */     if (this.horse.isTamed() || !this.horse.isVehicle()) {
/* 28 */       return false;
/*    */     }
/* 30 */     Vec3 $$0 = DefaultRandomPos.getPos((PathfinderMob)this.horse, 5, 4);
/* 31 */     if ($$0 == null) {
/* 32 */       return false;
/*    */     }
/* 34 */     this.posX = $$0.x;
/* 35 */     this.posY = $$0.y;
/* 36 */     this.posZ = $$0.z;
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 42 */     this.horse.getNavigation().moveTo(this.posX, this.posY, this.posZ, this.speedModifier);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canContinueToUse() {
/* 47 */     return (!this.horse.isTamed() && !this.horse.getNavigation().isDone() && this.horse.isVehicle());
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 52 */     if (!this.horse.isTamed() && this.horse.getRandom().nextInt(adjustedTickDelay(50)) == 0) {
/* 53 */       Entity $$0 = this.horse.getFirstPassenger();
/* 54 */       if ($$0 == null) {
/*    */         return;
/*    */       }
/*    */       
/* 58 */       if ($$0 instanceof Player) { Player $$1 = (Player)$$0;
/* 59 */         int $$2 = this.horse.getTemper();
/* 60 */         int $$3 = this.horse.getMaxTemper();
/* 61 */         if ($$3 > 0 && this.horse.getRandom().nextInt($$3) < $$2) {
/* 62 */           this.horse.tameWithName($$1);
/*    */           return;
/*    */         } 
/* 65 */         this.horse.modifyTemper(5); }
/*    */ 
/*    */       
/* 68 */       this.horse.ejectPassengers();
/* 69 */       this.horse.makeMad();
/* 70 */       this.horse.level().broadcastEntityEvent((Entity)this.horse, (byte)6);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\goal\RunAroundLikeCrazyGoal.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */