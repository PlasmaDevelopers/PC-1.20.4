/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class UploadResult {
/*    */   public final int statusCode;
/*    */   @Nullable
/*    */   public final String errorMessage;
/*    */   
/*    */   UploadResult(int $$0, String $$1) {
/* 11 */     this.statusCode = $$0;
/* 12 */     this.errorMessage = $$1;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 16 */     private int statusCode = -1;
/*    */     private String errorMessage;
/*    */     
/*    */     public Builder withStatusCode(int $$0) {
/* 20 */       this.statusCode = $$0;
/* 21 */       return this;
/*    */     }
/*    */     
/*    */     public Builder withErrorMessage(@Nullable String $$0) {
/* 25 */       this.errorMessage = $$0;
/* 26 */       return this;
/*    */     }
/*    */     
/*    */     public UploadResult build() {
/* 30 */       return new UploadResult(this.statusCode, this.errorMessage);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\UploadResult.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */