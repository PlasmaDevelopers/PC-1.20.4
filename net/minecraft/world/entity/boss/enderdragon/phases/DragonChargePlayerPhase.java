/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DragonChargePlayerPhase
/*    */   extends AbstractDragonPhaseInstance {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private static final int CHARGE_RECOVERY_TIME = 10;
/*    */   @Nullable
/*    */   private Vec3 targetLocation;
/*    */   private int timeSinceCharge;
/*    */   
/*    */   public DragonChargePlayerPhase(EnderDragon $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 24 */     if (this.targetLocation == null) {
/* 25 */       LOGGER.warn("Aborting charge player as no target was set.");
/* 26 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/*    */       
/*    */       return;
/*    */     } 
/* 30 */     if (this.timeSinceCharge > 0 && 
/* 31 */       this.timeSinceCharge++ >= 10) {
/* 32 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.HOLDING_PATTERN);
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 37 */     double $$0 = this.targetLocation.distanceToSqr(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/* 38 */     if ($$0 < 100.0D || $$0 > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
/* 39 */       this.timeSinceCharge++;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 45 */     this.targetLocation = null;
/* 46 */     this.timeSinceCharge = 0;
/*    */   }
/*    */   
/*    */   public void setTarget(Vec3 $$0) {
/* 50 */     this.targetLocation = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFlySpeed() {
/* 55 */     return 3.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Vec3 getFlyTargetLocation() {
/* 61 */     return this.targetLocation;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonChargePlayerPhase> getPhase() {
/* 66 */     return EnderDragonPhase.CHARGING_PLAYER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonChargePlayerPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */