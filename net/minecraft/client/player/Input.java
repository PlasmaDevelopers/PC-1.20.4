/*    */ package net.minecraft.client.player;
/*    */ 
/*    */ import net.minecraft.world.phys.Vec2;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Input
/*    */ {
/*    */   public float leftImpulse;
/*    */   public float forwardImpulse;
/*    */   public boolean up;
/*    */   public boolean down;
/*    */   public boolean left;
/*    */   public boolean right;
/*    */   public boolean jumping;
/*    */   public boolean shiftKeyDown;
/*    */   
/*    */   public void tick(boolean $$0, float $$1) {}
/*    */   
/*    */   public Vec2 getMoveVector() {
/* 21 */     return new Vec2(this.leftImpulse, this.forwardImpulse);
/*    */   }
/*    */   
/*    */   public boolean hasForwardImpulse() {
/* 25 */     return (this.forwardImpulse > 1.0E-5F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\Input.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */