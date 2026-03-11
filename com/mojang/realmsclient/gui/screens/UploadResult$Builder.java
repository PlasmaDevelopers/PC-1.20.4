/*    */ package com.mojang.realmsclient.gui.screens;
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
/*    */ public class Builder
/*    */ {
/* 16 */   private int statusCode = -1;
/*    */   private String errorMessage;
/*    */   
/*    */   public Builder withStatusCode(int $$0) {
/* 20 */     this.statusCode = $$0;
/* 21 */     return this;
/*    */   }
/*    */   
/*    */   public Builder withErrorMessage(@Nullable String $$0) {
/* 25 */     this.errorMessage = $$0;
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public UploadResult build() {
/* 30 */     return new UploadResult(this.statusCode, this.errorMessage);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\UploadResult$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */