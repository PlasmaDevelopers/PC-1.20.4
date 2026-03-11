/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class WalkAnimationState {
/*    */   private float speedOld;
/*    */   private float speed;
/*    */   private float position;
/*    */   
/*    */   public void setSpeed(float $$0) {
/* 11 */     this.speed = $$0;
/*    */   }
/*    */   
/*    */   public void update(float $$0, float $$1) {
/* 15 */     this.speedOld = this.speed;
/* 16 */     this.speed += ($$0 - this.speed) * $$1;
/* 17 */     this.position += this.speed;
/*    */   }
/*    */   
/*    */   public float speed() {
/* 21 */     return this.speed;
/*    */   }
/*    */   
/*    */   public float speed(float $$0) {
/* 25 */     return Mth.lerp($$0, this.speedOld, this.speed);
/*    */   }
/*    */   
/*    */   public float position() {
/* 29 */     return this.position;
/*    */   }
/*    */   
/*    */   public float position(float $$0) {
/* 33 */     return this.position - this.speed * (1.0F - $$0);
/*    */   }
/*    */   
/*    */   public boolean isMoving() {
/* 37 */     return (this.speed > 1.0E-5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\WalkAnimationState.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */