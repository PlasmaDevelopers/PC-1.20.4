/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintStream;
/*    */ import javax.annotation.Nullable;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class LoggedPrintStream
/*    */   extends PrintStream {
/* 11 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   protected final String name;
/*    */   
/*    */   public LoggedPrintStream(String $$0, OutputStream $$1) {
/* 16 */     super($$1);
/* 17 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(@Nullable String $$0) {
/* 22 */     logLine($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void println(Object $$0) {
/* 27 */     logLine(String.valueOf($$0));
/*    */   }
/*    */   
/*    */   protected void logLine(@Nullable String $$0) {
/* 31 */     LOGGER.info("[{}]: {}", this.name, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\LoggedPrintStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */