/*    */ package net.minecraft.client;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.InputConstants;
/*    */ import java.util.function.BooleanSupplier;
/*    */ 
/*    */ public class ToggleKeyMapping
/*    */   extends KeyMapping {
/*    */   private final BooleanSupplier needsToggle;
/*    */   
/*    */   public ToggleKeyMapping(String $$0, int $$1, String $$2, BooleanSupplier $$3) {
/* 11 */     super($$0, InputConstants.Type.KEYSYM, $$1, $$2);
/* 12 */     this.needsToggle = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDown(boolean $$0) {
/* 17 */     if (this.needsToggle.getAsBoolean()) {
/* 18 */       if ($$0) {
/* 19 */         super.setDown(!isDown());
/*    */       }
/*    */     } else {
/* 22 */       super.setDown($$0);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void reset() {
/* 27 */     super.setDown(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\ToggleKeyMapping.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */