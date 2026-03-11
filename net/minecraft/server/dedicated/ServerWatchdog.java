/*     */ package net.minecraft.server.dedicated;
/*     */ 
/*     */ import com.google.common.collect.Streams;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.lang.management.ThreadMXBean;
/*     */ import java.util.Locale;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.server.Bootstrap;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerWatchdog
/*     */   implements Runnable {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final long MAX_SHUTDOWN_TIME = 10000L;
/*     */   private static final int SHUTDOWN_STATUS = 1;
/*     */   private final DedicatedServer server;
/*     */   private final long maxTickTimeNanos;
/*     */   
/*     */   public ServerWatchdog(DedicatedServer $$0) {
/*  32 */     this.server = $$0;
/*  33 */     this.maxTickTimeNanos = $$0.getMaxTickLength() * TimeUtil.NANOSECONDS_PER_MILLISECOND;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  38 */     while (this.server.isRunning()) {
/*  39 */       long $$0 = this.server.getNextTickTime();
/*  40 */       long $$1 = Util.getNanos();
/*  41 */       long $$2 = $$1 - $$0;
/*     */       
/*  43 */       if ($$2 > this.maxTickTimeNanos) {
/*  44 */         LOGGER.error(LogUtils.FATAL_MARKER, "A single server tick took {} seconds (should be max {})", String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf((float)$$2 / (float)TimeUtil.NANOSECONDS_PER_SECOND) }), String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf(this.server.tickRateManager().millisecondsPerTick() / (float)TimeUtil.MILLISECONDS_PER_SECOND) }));
/*  45 */         LOGGER.error(LogUtils.FATAL_MARKER, "Considering it to be crashed, server will forcibly shutdown.");
/*     */         
/*  47 */         ThreadMXBean $$3 = ManagementFactory.getThreadMXBean();
/*  48 */         ThreadInfo[] $$4 = $$3.dumpAllThreads(true, true);
/*     */         
/*  50 */         StringBuilder $$5 = new StringBuilder();
/*  51 */         Error $$6 = new Error("Watchdog");
/*     */         
/*  53 */         for (ThreadInfo $$7 : $$4) {
/*  54 */           if ($$7.getThreadId() == this.server.getRunningThread().getId()) {
/*  55 */             $$6.setStackTrace($$7.getStackTrace());
/*     */           }
/*     */           
/*  58 */           $$5.append($$7);
/*  59 */           $$5.append("\n");
/*     */         } 
/*     */         
/*  62 */         CrashReport $$8 = new CrashReport("Watching Server", $$6);
/*  63 */         this.server.fillSystemReport($$8.getSystemReport());
/*  64 */         CrashReportCategory $$9 = $$8.addCategory("Thread Dump");
/*  65 */         $$9.setDetail("Threads", $$5);
/*     */         
/*  67 */         CrashReportCategory $$10 = $$8.addCategory("Performance stats");
/*  68 */         $$10.setDetail("Random tick rate", () -> ((GameRules.IntegerValue)this.server.getWorldData().getGameRules().getRule(GameRules.RULE_RANDOMTICKING)).toString());
/*  69 */         $$10.setDetail("Level stats", () -> (String)Streams.stream(this.server.getAllLevels()).map(()).collect(Collectors.joining(",\n")));
/*     */         
/*  71 */         Bootstrap.realStdoutPrintln("Crash report:\n" + $$8.getFriendlyReport());
/*     */         
/*  73 */         File $$11 = new File(new File(this.server.getServerDirectory(), "crash-reports"), "crash-" + Util.getFilenameFormattedDateTime() + "-server.txt");
/*  74 */         if ($$8.saveToFile($$11)) {
/*  75 */           LOGGER.error("This crash report has been saved to: {}", $$11.getAbsolutePath());
/*     */         } else {
/*  77 */           LOGGER.error("We were unable to save this crash report to disk.");
/*     */         } 
/*     */         
/*  80 */         exit();
/*     */       } 
/*     */       
/*     */       try {
/*  84 */         Thread.sleep(($$0 + this.maxTickTimeNanos - $$1) / TimeUtil.NANOSECONDS_PER_MILLISECOND);
/*  85 */       } catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void exit() {
/*     */     try {
/*  92 */       Timer $$0 = new Timer();
/*  93 */       $$0.schedule(new TimerTask()
/*     */           {
/*     */             public void run() {
/*  96 */               Runtime.getRuntime().halt(1);
/*     */             }
/*     */           },  10000L);
/*     */       
/* 100 */       System.exit(1);
/* 101 */     } catch (Throwable $$1) {
/* 102 */       Runtime.getRuntime().halt(1);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\dedicated\ServerWatchdog.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */