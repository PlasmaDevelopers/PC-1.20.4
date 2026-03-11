/*    */ package net.minecraft.client.resources;
/*    */ 
/*    */ import javax.annotation.Nullable;
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
/*    */ public enum Model
/*    */ {
/* 16 */   SLIM("slim"),
/* 17 */   WIDE("default");
/*    */   
/*    */   private final String id;
/*    */   
/*    */   Model(String $$0) {
/* 22 */     this.id = $$0;
/*    */   }
/*    */   
/*    */   public static Model byName(@Nullable String $$0) {
/* 26 */     if ($$0 == null)
/*    */     {
/* 28 */       return WIDE;
/*    */     }
/* 30 */     switch ($$0) { case "slim":  }  return 
/*    */       
/* 32 */       WIDE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String id() {
/* 37 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\PlayerSkin$Model.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */