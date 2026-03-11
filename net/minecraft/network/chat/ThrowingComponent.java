/*    */ package net.minecraft.network.chat;
/*    */ 
/*    */ public class ThrowingComponent extends Exception {
/*    */   private final Component component;
/*    */   
/*    */   public ThrowingComponent(Component $$0) {
/*  7 */     super($$0.getString());
/*  8 */     this.component = $$0;
/*    */   }
/*    */   
/*    */   public ThrowingComponent(Component $$0, Throwable $$1) {
/* 12 */     super($$0.getString(), $$1);
/* 13 */     this.component = $$0;
/*    */   }
/*    */   
/*    */   public Component getComponent() {
/* 17 */     return this.component;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\ThrowingComponent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */