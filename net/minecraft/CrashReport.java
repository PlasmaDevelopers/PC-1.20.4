/*     */ package net.minecraft;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.time.ZonedDateTime;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.MemoryReserve;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CrashReport
/*     */ {
/*  25 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  26 */   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ROOT);
/*     */   
/*     */   private final String title;
/*     */   private final Throwable exception;
/*  30 */   private final List<CrashReportCategory> details = Lists.newArrayList();
/*     */   @Nullable
/*     */   private File saveFile;
/*     */   private boolean trackingStackTrace = true;
/*  34 */   private StackTraceElement[] uncategorizedStackTrace = new StackTraceElement[0];
/*     */   
/*  36 */   private final SystemReport systemReport = new SystemReport();
/*     */   
/*     */   public CrashReport(String $$0, Throwable $$1) {
/*  39 */     this.title = $$0;
/*  40 */     this.exception = $$1;
/*     */   }
/*     */   
/*     */   public String getTitle() {
/*  44 */     return this.title;
/*     */   }
/*     */   
/*     */   public Throwable getException() {
/*  48 */     return this.exception;
/*     */   }
/*     */   
/*     */   public String getDetails() {
/*  52 */     StringBuilder $$0 = new StringBuilder();
/*     */     
/*  54 */     getDetails($$0);
/*     */     
/*  56 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   public void getDetails(StringBuilder $$0) {
/*  60 */     if ((this.uncategorizedStackTrace == null || this.uncategorizedStackTrace.length <= 0) && !this.details.isEmpty()) {
/*  61 */       this.uncategorizedStackTrace = (StackTraceElement[])ArrayUtils.subarray((Object[])((CrashReportCategory)this.details.get(0)).getStacktrace(), 0, 1);
/*     */     }
/*     */     
/*  64 */     if (this.uncategorizedStackTrace != null && this.uncategorizedStackTrace.length > 0) {
/*  65 */       $$0.append("-- Head --\n");
/*  66 */       $$0.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
/*  67 */       $$0.append("Stacktrace:\n");
/*     */       
/*  69 */       for (StackTraceElement $$1 : this.uncategorizedStackTrace) {
/*  70 */         $$0.append("\t").append("at ").append($$1);
/*  71 */         $$0.append("\n");
/*     */       } 
/*  73 */       $$0.append("\n");
/*     */     } 
/*     */     
/*  76 */     for (CrashReportCategory $$2 : this.details) {
/*  77 */       $$2.getDetails($$0);
/*  78 */       $$0.append("\n\n");
/*     */     } 
/*     */     
/*  81 */     this.systemReport.appendToCrashReportString($$0);
/*     */   }
/*     */   
/*     */   public String getExceptionMessage() {
/*  85 */     StringWriter $$0 = null;
/*  86 */     PrintWriter $$1 = null;
/*  87 */     Throwable $$2 = this.exception;
/*     */     
/*  89 */     if ($$2.getMessage() == null) {
/*     */       
/*  91 */       if ($$2 instanceof NullPointerException) {
/*  92 */         $$2 = new NullPointerException(this.title);
/*  93 */       } else if ($$2 instanceof StackOverflowError) {
/*  94 */         $$2 = new StackOverflowError(this.title);
/*  95 */       } else if ($$2 instanceof OutOfMemoryError) {
/*  96 */         $$2 = new OutOfMemoryError(this.title);
/*     */       } 
/*     */       
/*  99 */       $$2.setStackTrace(this.exception.getStackTrace());
/*     */     } 
/*     */     
/*     */     try {
/* 103 */       $$0 = new StringWriter();
/* 104 */       $$1 = new PrintWriter($$0);
/* 105 */       $$2.printStackTrace($$1);
/* 106 */       return $$0.toString();
/*     */     } finally {
/* 108 */       IOUtils.closeQuietly($$0);
/* 109 */       IOUtils.closeQuietly($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getFriendlyReport() {
/* 114 */     StringBuilder $$0 = new StringBuilder();
/*     */     
/* 116 */     $$0.append("---- Minecraft Crash Report ----\n");
/* 117 */     $$0.append("// ");
/* 118 */     $$0.append(getErrorComment());
/* 119 */     $$0.append("\n\n");
/*     */     
/* 121 */     $$0.append("Time: ");
/* 122 */     $$0.append(DATE_TIME_FORMATTER.format(ZonedDateTime.now()));
/* 123 */     $$0.append("\n");
/*     */     
/* 125 */     $$0.append("Description: ");
/* 126 */     $$0.append(this.title);
/* 127 */     $$0.append("\n\n");
/*     */     
/* 129 */     $$0.append(getExceptionMessage());
/* 130 */     $$0.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
/*     */     
/* 132 */     for (int $$1 = 0; $$1 < 87; $$1++) {
/* 133 */       $$0.append("-");
/*     */     }
/* 135 */     $$0.append("\n\n");
/* 136 */     getDetails($$0);
/*     */     
/* 138 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public File getSaveFile() {
/* 143 */     return this.saveFile;
/*     */   }
/*     */   
/*     */   public boolean saveToFile(File $$0) {
/* 147 */     if (this.saveFile != null) {
/* 148 */       return false;
/*     */     }
/* 150 */     if ($$0.getParentFile() != null) {
/* 151 */       $$0.getParentFile().mkdirs();
/*     */     }
/*     */     
/* 154 */     Writer $$1 = null;
/*     */     try {
/* 156 */       $$1 = new OutputStreamWriter(new FileOutputStream($$0), StandardCharsets.UTF_8);
/* 157 */       $$1.write(getFriendlyReport());
/*     */       
/* 159 */       this.saveFile = $$0;
/* 160 */       return true;
/* 161 */     } catch (Throwable $$2) {
/* 162 */       LOGGER.error("Could not save crash report to {}", $$0, $$2);
/* 163 */       return false;
/*     */     } finally {
/* 165 */       IOUtils.closeQuietly($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public SystemReport getSystemReport() {
/* 170 */     return this.systemReport;
/*     */   }
/*     */   
/*     */   public CrashReportCategory addCategory(String $$0) {
/* 174 */     return addCategory($$0, 1);
/*     */   }
/*     */   
/*     */   public CrashReportCategory addCategory(String $$0, int $$1) {
/* 178 */     CrashReportCategory $$2 = new CrashReportCategory($$0);
/*     */     
/* 180 */     if (this.trackingStackTrace) {
/* 181 */       int $$3 = $$2.fillInStackTrace($$1);
/* 182 */       StackTraceElement[] $$4 = this.exception.getStackTrace();
/* 183 */       StackTraceElement $$5 = null;
/* 184 */       StackTraceElement $$6 = null;
/*     */       
/* 186 */       int $$7 = $$4.length - $$3;
/* 187 */       if ($$7 < 0) {
/* 188 */         LOGGER.error("Negative index in crash report handler ({}/{})", Integer.valueOf($$4.length), Integer.valueOf($$3));
/*     */       }
/*     */       
/* 191 */       if ($$4 != null && 0 <= $$7 && $$7 < $$4.length) {
/* 192 */         $$5 = $$4[$$7];
/*     */         
/* 194 */         if ($$4.length + 1 - $$3 < $$4.length) {
/* 195 */           $$6 = $$4[$$4.length + 1 - $$3];
/*     */         }
/*     */       } 
/*     */       
/* 199 */       this.trackingStackTrace = $$2.validateStackTrace($$5, $$6);
/*     */       
/* 201 */       if ($$4 != null && $$4.length >= $$3 && 0 <= $$7 && $$7 < $$4.length) {
/* 202 */         this.uncategorizedStackTrace = new StackTraceElement[$$7];
/* 203 */         System.arraycopy($$4, 0, this.uncategorizedStackTrace, 0, this.uncategorizedStackTrace.length);
/*     */       } else {
/* 205 */         this.trackingStackTrace = false;
/*     */       } 
/*     */     } 
/*     */     
/* 209 */     this.details.add($$2);
/* 210 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getErrorComment() {
/* 215 */     String[] $$0 = { "Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine." };
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
/*     */     try {
/* 253 */       return $$0[(int)(Util.getNanos() % $$0.length)];
/* 254 */     } catch (Throwable $$1) {
/* 255 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static CrashReport forThrowable(Throwable $$0, String $$1) {
/*     */     CrashReport $$4;
/* 262 */     while ($$0 instanceof java.util.concurrent.CompletionException && $$0.getCause() != null) {
/* 263 */       $$0 = $$0.getCause();
/*     */     }
/*     */     
/* 266 */     if ($$0 instanceof ReportedException) { ReportedException $$2 = (ReportedException)$$0;
/* 267 */       CrashReport $$3 = $$2.getReport(); }
/*     */     else
/* 269 */     { $$4 = new CrashReport($$1, $$0); }
/*     */ 
/*     */     
/* 272 */     return $$4;
/*     */   }
/*     */   
/*     */   public static void preload() {
/* 276 */     MemoryReserve.allocate();
/* 277 */     (new CrashReport("Don't panic!", new Throwable())).getFriendlyReport();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\CrashReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */