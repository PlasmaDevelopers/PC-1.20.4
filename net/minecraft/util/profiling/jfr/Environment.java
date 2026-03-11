/*    */ package net.minecraft.util.profiling.jfr;
/*    */ 
/*    */ import net.minecraft.server.MinecraftServer;
/*    */ 
/*    */ public enum Environment {
/*  6 */   CLIENT("client"), SERVER("server");
/*    */   
/*    */   private final String description;
/*    */   
/*    */   Environment(String $$0) {
/* 11 */     this.description = $$0;
/*    */   }
/*    */   
/*    */   public static Environment from(MinecraftServer $$0) {
/* 15 */     return $$0.isDedicatedServer() ? SERVER : CLIENT;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 19 */     return this.description;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\Environment.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */