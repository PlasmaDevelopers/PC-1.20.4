/*    */ package net.minecraft.server.packs;
/*    */ 
/*    */ public enum PackType {
/*  4 */   CLIENT_RESOURCES("assets"),
/*  5 */   SERVER_DATA("data");
/*    */   
/*    */   private final String directory;
/*    */ 
/*    */   
/*    */   PackType(String $$0) {
/* 11 */     this.directory = $$0;
/*    */   }
/*    */   
/*    */   public String getDirectory() {
/* 15 */     return this.directory;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\PackType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */