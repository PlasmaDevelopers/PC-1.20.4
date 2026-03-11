/*    */ package net.minecraft.client.multiplayer.chat.report;
/*    */ 
/*    */ import java.util.Locale;
/*    */ 
/*    */ public enum ReportType {
/*  6 */   CHAT("chat"),
/*  7 */   SKIN("skin"),
/*  8 */   USERNAME("username");
/*    */   
/*    */   private final String backendName;
/*    */   
/*    */   ReportType(String $$0) {
/* 13 */     this.backendName = $$0.toUpperCase(Locale.ROOT);
/*    */   }
/*    */   
/*    */   public String backendName() {
/* 17 */     return this.backendName;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\multiplayer\chat\report\ReportType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */