/*     */ package net.minecraft.client.player;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class RemotePlayer extends AbstractClientPlayer {
/*  12 */   private Vec3 lerpDeltaMovement = Vec3.ZERO;
/*     */   private int lerpDeltaMovementSteps;
/*     */   
/*     */   public RemotePlayer(ClientLevel $$0, GameProfile $$1) {
/*  16 */     super($$0, $$1);
/*     */     
/*  18 */     setMaxUpStep(1.0F);
/*  19 */     this.noPhysics = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderAtSqrDistance(double $$0) {
/*  24 */     double $$1 = getBoundingBox().getSize() * 10.0D;
/*  25 */     if (Double.isNaN($$1)) {
/*  26 */       $$1 = 1.0D;
/*     */     }
/*  28 */     $$1 *= 64.0D * getViewScale();
/*  29 */     return ($$0 < $$1 * $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hurt(DamageSource $$0, float $$1) {
/*  34 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  39 */     super.tick();
/*  40 */     calculateEntityAnimation(false);
/*     */   }
/*     */   
/*     */   public void aiStep() {
/*     */     float $$1;
/*  45 */     if (this.lerpSteps > 0) {
/*  46 */       lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.lerpXRot);
/*  47 */       this.lerpSteps--;
/*     */     } 
/*  49 */     if (this.lerpHeadSteps > 0) {
/*  50 */       lerpHeadRotationStep(this.lerpHeadSteps, this.lerpYHeadRot);
/*  51 */       this.lerpHeadSteps--;
/*     */     } 
/*  53 */     if (this.lerpDeltaMovementSteps > 0) {
/*  54 */       addDeltaMovement(new Vec3((this.lerpDeltaMovement.x - 
/*  55 */             (getDeltaMovement()).x) / this.lerpDeltaMovementSteps, (this.lerpDeltaMovement.y - 
/*  56 */             (getDeltaMovement()).y) / this.lerpDeltaMovementSteps, (this.lerpDeltaMovement.z - 
/*  57 */             (getDeltaMovement()).z) / this.lerpDeltaMovementSteps));
/*     */       
/*  59 */       this.lerpDeltaMovementSteps--;
/*     */     } 
/*  61 */     this.oBob = this.bob;
/*     */     
/*  63 */     updateSwingTime();
/*     */ 
/*     */     
/*  66 */     if (!onGround() || isDeadOrDying()) {
/*  67 */       float $$0 = 0.0F;
/*     */     } else {
/*  69 */       $$1 = (float)Math.min(0.1D, getDeltaMovement().horizontalDistance());
/*     */     } 
/*     */     
/*  72 */     this.bob += ($$1 - this.bob) * 0.4F;
/*     */     
/*  74 */     level().getProfiler().push("push");
/*  75 */     pushEntities();
/*  76 */     level().getProfiler().pop();
/*     */   }
/*     */ 
/*     */   
/*     */   public void lerpMotion(double $$0, double $$1, double $$2) {
/*  81 */     this.lerpDeltaMovement = new Vec3($$0, $$1, $$2);
/*  82 */     this.lerpDeltaMovementSteps = getType().updateInterval() + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updatePlayerPose() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSystemMessage(Component $$0) {
/*  94 */     Minecraft $$1 = Minecraft.getInstance();
/*  95 */     $$1.gui.getChat().addMessage($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateFromPacket(ClientboundAddEntityPacket $$0) {
/* 100 */     super.recreateFromPacket($$0);
/* 101 */     setOldPosAndRot();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\RemotePlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */