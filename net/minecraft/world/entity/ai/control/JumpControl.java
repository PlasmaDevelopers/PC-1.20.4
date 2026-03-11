/*    */ package net.minecraft.world.entity.ai.control;
/*    */ 
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public class JumpControl implements Control {
/*    */   private final Mob mob;
/*    */   protected boolean jump;
/*    */   
/*    */   public JumpControl(Mob $$0) {
/* 10 */     this.mob = $$0;
/*    */   }
/*    */   
/*    */   public void jump() {
/* 14 */     this.jump = true;
/*    */   }
/*    */   
/*    */   public void tick() {
/* 18 */     this.mob.setJumping(this.jump);
/* 19 */     this.jump = false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\control\JumpControl.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */