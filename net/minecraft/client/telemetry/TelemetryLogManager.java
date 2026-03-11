/*    */ package net.minecraft.client.telemetry;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.nio.channels.FileChannel;
/*    */ import java.nio.file.Path;
/*    */ import java.time.LocalDate;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.util.eventlog.EventLogDirectory;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class TelemetryLogManager
/*    */   implements AutoCloseable {
/* 17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private static final String RAW_EXTENSION = ".json";
/*    */   
/*    */   private static final int EXPIRY_DAYS = 7;
/*    */   private final EventLogDirectory directory;
/*    */   @Nullable
/*    */   private CompletableFuture<Optional<TelemetryEventLog>> sessionLog;
/*    */   
/*    */   private TelemetryLogManager(EventLogDirectory $$0) {
/* 27 */     this.directory = $$0;
/*    */   }
/*    */   
/*    */   public static CompletableFuture<Optional<TelemetryLogManager>> open(Path $$0) {
/* 31 */     return CompletableFuture.supplyAsync(() -> {
/*    */           try {
/*    */             EventLogDirectory $$1 = EventLogDirectory.open($$0, ".json");
/*    */             
/*    */             $$1.listFiles().prune(LocalDate.now(), 7).compressAll();
/*    */             
/*    */             return Optional.of(new TelemetryLogManager($$1));
/* 38 */           } catch (Exception $$2) {
/*    */             LOGGER.error("Failed to create telemetry log manager", $$2);
/*    */             return Optional.empty();
/*    */           } 
/* 42 */         }Util.backgroundExecutor());
/*    */   }
/*    */   
/*    */   public CompletableFuture<Optional<TelemetryEventLogger>> openLogger() {
/* 46 */     if (this.sessionLog == null) {
/* 47 */       this.sessionLog = CompletableFuture.supplyAsync(() -> {
/*    */             try {
/*    */               EventLogDirectory.RawFile $$0 = this.directory.createNewFile(LocalDate.now());
/*    */               FileChannel $$1 = $$0.openChannel();
/*    */               return Optional.of(new TelemetryEventLog($$1, Util.backgroundExecutor()));
/* 52 */             } catch (IOException $$2) {
/*    */               LOGGER.error("Failed to open channel for telemetry event log", $$2);
/*    */               return Optional.empty();
/*    */             } 
/* 56 */           }Util.backgroundExecutor());
/*    */     }
/* 58 */     return this.sessionLog.thenApply($$0 -> $$0.map(TelemetryEventLog::logger));
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 63 */     if (this.sessionLog != null)
/* 64 */       this.sessionLog.thenAccept($$0 -> $$0.ifPresent(TelemetryEventLog::close)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryLogManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */