/*    */ package net.minecraft.world.entity.ai.control;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*    */ 
/*    */ public class FlyingMoveControl extends MoveControl {
/*    */   private final int maxTurn;
/*    */   private final boolean hoversInPlace;
/*    */   
/*    */   public FlyingMoveControl(Mob $$0, int $$1, boolean $$2) {
/* 12 */     super($$0);
/* 13 */     this.maxTurn = $$1;
/* 14 */     this.hoversInPlace = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 19 */     if (this.operation == MoveControl.Operation.MOVE_TO) {
/* 20 */       float $$6; this.operation = MoveControl.Operation.WAIT;
/*    */       
/* 22 */       this.mob.setNoGravity(true);
/*    */       
/* 24 */       double $$0 = this.wantedX - this.mob.getX();
/* 25 */       double $$1 = this.wantedY - this.mob.getY();
/* 26 */       double $$2 = this.wantedZ - this.mob.getZ();
/* 27 */       double $$3 = $$0 * $$0 + $$1 * $$1 + $$2 * $$2;
/* 28 */       if ($$3 < 2.500000277905201E-7D) {
/* 29 */         this.mob.setYya(0.0F);
/* 30 */         this.mob.setZza(0.0F);
/*    */         return;
/*    */       } 
/* 33 */       float $$4 = (float)(Mth.atan2($$2, $$0) * 57.2957763671875D) - 90.0F;
/* 34 */       this.mob.setYRot(rotlerp(this.mob.getYRot(), $$4, 90.0F));
/*    */       
/* 36 */       if (this.mob.onGround()) {
/* 37 */         float $$5 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.MOVEMENT_SPEED));
/*    */       } else {
/* 39 */         $$6 = (float)(this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED));
/*    */       } 
/* 41 */       this.mob.setSpeed($$6);
/*    */       
/* 43 */       double $$7 = Math.sqrt($$0 * $$0 + $$2 * $$2);
/* 44 */       if (Math.abs($$1) > 9.999999747378752E-6D || Math.abs($$7) > 9.999999747378752E-6D) {
/* 45 */         float $$8 = (float)-(Mth.atan2($$1, $$7) * 57.2957763671875D);
/* 46 */         this.mob.setXRot(rotlerp(this.mob.getXRot(), $$8, this.maxTurn));
/* 47 */         this.mob.setYya(($$1 > 0.0D) ? $$6 : -$$6);
/*    */       } 
/*    */     } else {
/* 50 */       if (!this.hoversInPlace) {
/* 51 */         this.mob.setNoGravity(false);
/*    */       }
/*    */       
/* 54 */       this.mob.setYya(0.0F);
/* 55 */       this.mob.setZza(0.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\control\FlyingMoveControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */