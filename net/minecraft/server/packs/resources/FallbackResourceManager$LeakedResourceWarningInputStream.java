/*     */ package net.minecraft.server.packs.resources;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class LeakedResourceWarningInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private final Supplier<String> message;
/*     */   private boolean closed;
/*     */   
/*     */   public LeakedResourceWarningInputStream(InputStream $$0, ResourceLocation $$1, String $$2) {
/* 105 */     super($$0);
/* 106 */     Exception $$3 = new Exception("Stacktrace");
/* 107 */     this.message = (() -> {
/*     */         StringWriter $$3 = new StringWriter();
/*     */         $$0.printStackTrace(new PrintWriter($$3));
/*     */         return "Leaked resource: '" + $$1 + "' loaded from pack: '" + $$2 + "'\n" + $$3;
/*     */       });
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 116 */     super.close();
/* 117 */     this.closed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 122 */     if (!this.closed) {
/* 123 */       FallbackResourceManager.LOGGER.warn("{}", this.message.get());
/*     */     }
/*     */     
/* 126 */     super.finalize();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\FallbackResourceManager$LeakedResourceWarningInputStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */