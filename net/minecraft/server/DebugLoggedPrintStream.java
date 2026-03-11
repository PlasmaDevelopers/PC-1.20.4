/*    */ package net.minecraft.server;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.OutputStream;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DebugLoggedPrintStream
/*    */   extends LoggedPrintStream {
/*  9 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   public DebugLoggedPrintStream(String $$0, OutputStream $$1) {
/* 12 */     super($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void logLine(String $$0) {
/* 17 */     StackTraceElement[] $$1 = Thread.currentThread().getStackTrace();
/* 18 */     StackTraceElement $$2 = $$1[Math.min(3, $$1.length)];
/* 19 */     LOGGER.info("[{}]@.({}:{}): {}", new Object[] { this.name, $$2.getFileName(), Integer.valueOf($$2.getLineNumber()), $$0 });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\DebugLoggedPrintStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */