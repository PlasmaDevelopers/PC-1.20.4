/*    */ package net.minecraft.client.player;
/*    */ 
/*    */ import net.minecraft.client.Options;
/*    */ 
/*    */ public class KeyboardInput extends Input {
/*    */   private final Options options;
/*    */   
/*    */   public KeyboardInput(Options $$0) {
/*  9 */     this.options = $$0;
/*    */   }
/*    */   
/*    */   private static float calculateImpulse(boolean $$0, boolean $$1) {
/* 13 */     if ($$0 == $$1) {
/* 14 */       return 0.0F;
/*    */     }
/*    */     
/* 17 */     return $$0 ? 1.0F : -1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(boolean $$0, float $$1) {
/* 22 */     this.up = this.options.keyUp.isDown();
/* 23 */     this.down = this.options.keyDown.isDown();
/* 24 */     this.left = this.options.keyLeft.isDown();
/* 25 */     this.right = this.options.keyRight.isDown();
/*    */     
/* 27 */     this.forwardImpulse = calculateImpulse(this.up, this.down);
/* 28 */     this.leftImpulse = calculateImpulse(this.left, this.right);
/*    */     
/* 30 */     this.jumping = this.options.keyJump.isDown();
/* 31 */     this.shiftKeyDown = this.options.keyShift.isDown();
/* 32 */     if ($$0) {
/*    */ 
/*    */       
/* 35 */       this.leftImpulse *= $$1;
/* 36 */       this.forwardImpulse *= $$1;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\KeyboardInput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */