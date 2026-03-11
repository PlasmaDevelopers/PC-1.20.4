/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ enum OutlineProperty
/*    */ {
/* 22 */   NONE("none"),
/* 23 */   IS_OUTLINE("is_outline"),
/* 24 */   AFFECTS_OUTLINE("affects_outline");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   OutlineProperty(String $$0) {
/* 30 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderType$OutlineProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */