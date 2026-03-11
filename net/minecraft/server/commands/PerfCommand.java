/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Consumer;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.SystemReport;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.FileZipper;
/*     */ import net.minecraft.util.TimeUtil;
/*     */ import net.minecraft.util.profiling.EmptyProfileResults;
/*     */ import net.minecraft.util.profiling.ProfileResults;
/*     */ import net.minecraft.util.profiling.metrics.storage.MetricsPersister;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class PerfCommand {
/*  32 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  33 */   private static final SimpleCommandExceptionType ERROR_NOT_RUNNING = new SimpleCommandExceptionType((Message)Component.translatable("commands.perf.notRunning"));
/*  34 */   private static final SimpleCommandExceptionType ERROR_ALREADY_RUNNING = new SimpleCommandExceptionType((Message)Component.translatable("commands.perf.alreadyRunning"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  37 */     $$0.register(
/*  38 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("perf")
/*  39 */         .requires($$0 -> $$0.hasPermission(4)))
/*  40 */         .then(Commands.literal("start").executes($$0 -> startProfilingDedicatedServer((CommandSourceStack)$$0.getSource()))))
/*  41 */         .then(Commands.literal("stop").executes($$0 -> stopProfilingDedicatedServer((CommandSourceStack)$$0.getSource()))));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int startProfilingDedicatedServer(CommandSourceStack $$0) throws CommandSyntaxException {
/*  46 */     MinecraftServer $$1 = $$0.getServer();
/*  47 */     if ($$1.isRecordingMetrics()) {
/*  48 */       throw ERROR_ALREADY_RUNNING.create();
/*     */     }
/*     */     
/*  51 */     Consumer<ProfileResults> $$2 = $$1 -> whenStopped($$0, $$1);
/*  52 */     Consumer<Path> $$3 = $$2 -> saveResults($$0, $$2, $$1);
/*     */     
/*  54 */     $$1.startRecordingMetrics($$2, $$3);
/*  55 */     $$0.sendSuccess(() -> Component.translatable("commands.perf.started"), false);
/*  56 */     return 0;
/*     */   }
/*     */   
/*     */   private static int stopProfilingDedicatedServer(CommandSourceStack $$0) throws CommandSyntaxException {
/*  60 */     MinecraftServer $$1 = $$0.getServer();
/*  61 */     if (!$$1.isRecordingMetrics()) {
/*  62 */       throw ERROR_NOT_RUNNING.create();
/*     */     }
/*     */     
/*  65 */     $$1.finishRecordingMetrics();
/*  66 */     return 0;
/*     */   }
/*     */   
/*     */   private static void saveResults(CommandSourceStack $$0, Path $$1, MinecraftServer $$2) {
/*  70 */     String $$4, $$3 = String.format(Locale.ROOT, "%s-%s-%s", new Object[] {
/*  71 */           Util.getFilenameFormattedDateTime(), $$2
/*  72 */           .getWorldData().getLevelName(), 
/*  73 */           SharedConstants.getCurrentVersion().getId()
/*     */         });
/*     */     
/*     */     try {
/*  77 */       $$4 = FileUtil.findAvailableName(MetricsPersister.PROFILING_RESULTS_DIR, $$3, ".zip");
/*  78 */     } catch (IOException $$5) {
/*  79 */       $$0.sendFailure((Component)Component.translatable("commands.perf.reportFailed"));
/*  80 */       LOGGER.error("Failed to create report name", $$5);
/*     */       
/*     */       return;
/*     */     } 
/*  84 */     FileZipper $$7 = new FileZipper(MetricsPersister.PROFILING_RESULTS_DIR.resolve($$4)); 
/*  85 */     try { $$7.add(Paths.get("system.txt", new String[0]), $$2.fillSystemReport(new SystemReport()).toLineSeparatedString());
/*  86 */       $$7.add($$1);
/*  87 */       $$7.close(); } catch (Throwable throwable) { try { $$7.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/*  90 */      try { FileUtils.forceDelete($$1.toFile()); }
/*  91 */     catch (IOException $$8)
/*  92 */     { LOGGER.warn("Failed to delete temporary profiling file {}", $$1, $$8); }
/*     */ 
/*     */     
/*  95 */     $$0.sendSuccess(() -> Component.translatable("commands.perf.reportSaved", new Object[] { $$0 }), false);
/*     */   }
/*     */   
/*     */   private static void whenStopped(CommandSourceStack $$0, ProfileResults $$1) {
/*  99 */     if ($$1 == EmptyProfileResults.EMPTY) {
/*     */       return;
/*     */     }
/*     */     
/* 103 */     int $$2 = $$1.getTickDuration();
/* 104 */     double $$3 = $$1.getNanoDuration() / TimeUtil.NANOSECONDS_PER_SECOND;
/* 105 */     $$0.sendSuccess(() -> Component.translatable("commands.perf.stopped", new Object[] { String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$0) }), Integer.valueOf($$1), String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$1 / $$0) }) }), false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PerfCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */