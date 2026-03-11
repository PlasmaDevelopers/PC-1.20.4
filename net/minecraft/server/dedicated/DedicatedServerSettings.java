/*    */ package net.minecraft.server.dedicated;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import java.util.function.UnaryOperator;
/*    */ 
/*    */ public class DedicatedServerSettings {
/*    */   private final Path source;
/*    */   private DedicatedServerProperties properties;
/*    */   
/*    */   public DedicatedServerSettings(Path $$0) {
/* 11 */     this.source = $$0;
/* 12 */     this.properties = DedicatedServerProperties.fromFile($$0);
/*    */   }
/*    */   
/*    */   public DedicatedServerProperties getProperties() {
/* 16 */     return this.properties;
/*    */   }
/*    */   
/*    */   public void forceSave() {
/* 20 */     this.properties.store(this.source);
/*    */   }
/*    */   
/*    */   public DedicatedServerSettings update(UnaryOperator<DedicatedServerProperties> $$0) {
/* 24 */     (this.properties = $$0.apply(this.properties)).store(this.source);
/* 25 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\DedicatedServerSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */