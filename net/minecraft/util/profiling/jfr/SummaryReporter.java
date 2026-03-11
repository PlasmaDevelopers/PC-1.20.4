/*    */ package net.minecraft.util.profiling.jfr;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.OpenOption;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.StandardOpenOption;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Supplier;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.server.Bootstrap;
/*    */ import net.minecraft.util.profiling.jfr.parse.JfrStatsParser;
/*    */ import net.minecraft.util.profiling.jfr.parse.JfrStatsResult;
/*    */ import org.apache.commons.lang3.StringUtils;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class SummaryReporter {
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Runnable onDeregistration;
/*    */   
/*    */   protected SummaryReporter(Runnable $$0) {
/* 23 */     this.onDeregistration = $$0;
/*    */   }
/*    */   public void recordingStopped(@Nullable Path $$0) {
/*    */     JfrStatsResult $$1;
/* 27 */     if ($$0 == null) {
/*    */       return;
/*    */     }
/* 30 */     this.onDeregistration.run();
/*    */     
/* 32 */     infoWithFallback(() -> "Dumped flight recorder profiling to " + $$0);
/*    */ 
/*    */     
/*    */     try {
/* 36 */       $$1 = JfrStatsParser.parse($$0);
/* 37 */     } catch (Throwable $$2) {
/* 38 */       warnWithFallback(() -> "Failed to parse JFR recording", $$2);
/*    */       
/*    */       return;
/*    */     } 
/*    */     try {
/* 43 */       Objects.requireNonNull($$1); infoWithFallback($$1::asJson);
/* 44 */       Path $$4 = $$0.resolveSibling("jfr-report-" + StringUtils.substringBefore($$0.getFileName().toString(), ".jfr") + ".json");
/* 45 */       Files.writeString($$4, $$1.asJson(), new OpenOption[] { StandardOpenOption.CREATE });
/* 46 */       infoWithFallback(() -> "Dumped recording summary to " + $$0);
/* 47 */     } catch (Throwable $$5) {
/* 48 */       warnWithFallback(() -> "Failed to output JFR report", $$5);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void infoWithFallback(Supplier<String> $$0) {
/* 58 */     if (LogUtils.isLoggerActive()) {
/* 59 */       LOGGER.info($$0.get());
/*    */     } else {
/* 61 */       Bootstrap.realStdoutPrintln($$0.get());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void warnWithFallback(Supplier<String> $$0, Throwable $$1) {
/* 72 */     if (LogUtils.isLoggerActive()) {
/* 73 */       LOGGER.warn($$0.get(), $$1);
/*    */     } else {
/* 75 */       Bootstrap.realStdoutPrintln($$0.get());
/* 76 */       $$1.printStackTrace(Bootstrap.STDOUT);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\SummaryReporter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */