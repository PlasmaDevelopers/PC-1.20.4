/*    */ package net.minecraft.client;
/*    */ 
/*    */ public enum InputType {
/*  4 */   NONE,
/*  5 */   MOUSE,
/*  6 */   KEYBOARD_ARROW,
/*  7 */   KEYBOARD_TAB;
/*    */ 
/*    */   
/*    */   public boolean isMouse() {
/* 11 */     return (this == MOUSE);
/*    */   }
/*    */   
/*    */   public boolean isKeyboard() {
/* 15 */     return (this == KEYBOARD_ARROW || this == KEYBOARD_TAB);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\InputType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */