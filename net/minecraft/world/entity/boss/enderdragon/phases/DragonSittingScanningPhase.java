/*    */ package net.minecraft.world.entity.boss.enderdragon.phases;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.ai.targeting.TargetingConditions;
/*    */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class DragonSittingScanningPhase extends AbstractDragonSittingPhase {
/*    */   private static final int SITTING_SCANNING_IDLE_TICKS = 100;
/*    */   private static final int SITTING_ATTACK_Y_VIEW_RANGE = 10;
/*    */   private static final int SITTING_ATTACK_VIEW_RANGE = 20;
/*    */   private static final int SITTING_CHARGE_VIEW_RANGE = 150;
/* 15 */   private static final TargetingConditions CHARGE_TARGETING = TargetingConditions.forCombat().range(150.0D);
/*    */   
/*    */   private final TargetingConditions scanTargeting;
/*    */   private int scanningTime;
/*    */   
/*    */   public DragonSittingScanningPhase(EnderDragon $$0) {
/* 21 */     super($$0);
/*    */     
/* 23 */     this.scanTargeting = TargetingConditions.forCombat().range(20.0D).selector($$1 -> (Math.abs($$1.getY() - $$0.getY()) <= 10.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void doServerTick() {
/* 28 */     this.scanningTime++;
/* 29 */     Player player = this.dragon.level().getNearestPlayer(this.scanTargeting, (LivingEntity)this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/*    */     
/* 31 */     if (player != null) {
/* 32 */       if (this.scanningTime > 25) {
/* 33 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.SITTING_ATTACKING);
/*    */       } else {
/* 35 */         Vec3 $$1 = (new Vec3(player.getX() - this.dragon.getX(), 0.0D, player.getZ() - this.dragon.getZ())).normalize();
/* 36 */         Vec3 $$2 = (new Vec3(Mth.sin(this.dragon.getYRot() * 0.017453292F), 0.0D, -Mth.cos(this.dragon.getYRot() * 0.017453292F))).normalize();
/* 37 */         float $$3 = (float)$$2.dot($$1);
/* 38 */         float $$4 = (float)(Math.acos($$3) * 57.2957763671875D) + 0.5F;
/*    */         
/* 40 */         if ($$4 < 0.0F || $$4 > 10.0F) {
/* 41 */           double $$5 = player.getX() - this.dragon.head.getX();
/* 42 */           double $$6 = player.getZ() - this.dragon.head.getZ();
/* 43 */           double $$7 = Mth.clamp(Mth.wrapDegrees(180.0D - Mth.atan2($$5, $$6) * 57.2957763671875D - this.dragon.getYRot()), -100.0D, 100.0D);
/*    */           
/* 45 */           this.dragon.yRotA *= 0.8F;
/*    */           
/* 47 */           float $$8 = (float)Math.sqrt($$5 * $$5 + $$6 * $$6) + 1.0F;
/* 48 */           float $$9 = $$8;
/* 49 */           if ($$8 > 40.0F) {
/* 50 */             $$8 = 40.0F;
/*    */           }
/* 52 */           this.dragon.yRotA += (float)$$7 * 0.7F / $$8 / $$9;
/* 53 */           this.dragon.setYRot(this.dragon.getYRot() + this.dragon.yRotA);
/*    */         } 
/*    */       } 
/* 56 */     } else if (this.scanningTime >= 100) {
/* 57 */       player = this.dragon.level().getNearestPlayer(CHARGE_TARGETING, (LivingEntity)this.dragon, this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
/* 58 */       this.dragon.getPhaseManager().setPhase(EnderDragonPhase.TAKEOFF);
/* 59 */       if (player != null) {
/* 60 */         this.dragon.getPhaseManager().setPhase(EnderDragonPhase.CHARGING_PLAYER);
/* 61 */         ((DragonChargePlayerPhase)this.dragon.getPhaseManager().<DragonChargePlayerPhase>getPhase(EnderDragonPhase.CHARGING_PLAYER)).setTarget(new Vec3(player.getX(), player.getY(), player.getZ()));
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void begin() {
/* 68 */     this.scanningTime = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragonPhase<DragonSittingScanningPhase> getPhase() {
/* 73 */     return EnderDragonPhase.SITTING_SCANNING;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\boss\enderdragon\phases\DragonSittingScanningPhase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */