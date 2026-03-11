/*   */ package net.minecraft.client.model;
/*   */ 
/*   */ import net.minecraft.client.renderer.RenderType;
/*   */ 
/*   */ public abstract class SkullModelBase extends Model {
/*   */   public SkullModelBase() {
/* 7 */     super(RenderType::entityTranslucent);
/*   */   }
/*   */   
/*   */   public abstract void setupAnim(float paramFloat1, float paramFloat2, float paramFloat3);
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SkullModelBase.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */