/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.util.concurrent.Executor;
/*    */ import net.minecraft.util.eventlog.JsonEventLog;
/*    */ import net.minecraft.util.thread.ProcessorMailbox;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class TelemetryEventLog implements AutoCloseable {
/* 14 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final JsonEventLog<TelemetryEventInstance> log;
/*    */   private final ProcessorMailbox<Runnable> mailbox;
/*    */   
/*    */   public TelemetryEventLog(FileChannel $$0, Executor $$1) {
/* 20 */     this.log = new JsonEventLog(TelemetryEventInstance.CODEC, $$0);
/* 21 */     this.mailbox = ProcessorMailbox.create($$1, "telemetry-event-log");
/*    */   }
/*    */   
/*    */   public TelemetryEventLogger logger() {
/* 25 */     return $$0 -> this.mailbox.tell(());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void close() {
/* 36 */     this.mailbox.tell(() -> IOUtils.closeQuietly((Closeable)this.log));
/* 37 */     this.mailbox.close();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryEventLog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */