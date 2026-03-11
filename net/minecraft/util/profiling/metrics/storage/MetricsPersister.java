/*     */ package net.minecraft.util.profiling.metrics.storage;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.UncheckedIOException;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.CsvOutput;
/*     */ import net.minecraft.util.profiling.ProfileResults;
/*     */ import net.minecraft.util.profiling.metrics.MetricCategory;
/*     */ import net.minecraft.util.profiling.metrics.MetricSampler;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class MetricsPersister {
/*  30 */   public static final Path PROFILING_RESULTS_DIR = Paths.get("debug/profiling", new String[0]);
/*     */   public static final String METRICS_DIR_NAME = "metrics";
/*     */   public static final String DEVIATIONS_DIR_NAME = "deviations";
/*     */   public static final String PROFILING_RESULT_FILENAME = "profiling.txt";
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final String rootFolderName;
/*     */   
/*     */   public MetricsPersister(String $$0) {
/*  39 */     this.rootFolderName = $$0;
/*     */   }
/*     */   
/*     */   public Path saveReports(Set<MetricSampler> $$0, Map<MetricSampler, List<RecordedDeviation>> $$1, ProfileResults $$2) {
/*     */     try {
/*  44 */       Files.createDirectories(PROFILING_RESULTS_DIR, (FileAttribute<?>[])new FileAttribute[0]);
/*  45 */     } catch (IOException $$3) {
/*  46 */       throw new UncheckedIOException($$3);
/*     */     } 
/*     */     
/*     */     try {
/*  50 */       Path $$4 = Files.createTempDirectory("minecraft-profiling", (FileAttribute<?>[])new FileAttribute[0]);
/*  51 */       $$4.toFile().deleteOnExit();
/*     */       
/*  53 */       Files.createDirectories(PROFILING_RESULTS_DIR, (FileAttribute<?>[])new FileAttribute[0]);
/*  54 */       Path $$5 = $$4.resolve(this.rootFolderName);
/*  55 */       Path $$6 = $$5.resolve("metrics");
/*     */       
/*  57 */       saveMetrics($$0, $$6);
/*     */       
/*  59 */       if (!$$1.isEmpty()) {
/*  60 */         saveDeviations($$1, $$5.resolve("deviations"));
/*     */       }
/*     */       
/*  63 */       saveProfilingTaskExecutionResult($$2, $$5);
/*  64 */       return $$4;
/*  65 */     } catch (IOException $$7) {
/*  66 */       throw new UncheckedIOException($$7);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveMetrics(Set<MetricSampler> $$0, Path $$1) {
/*  71 */     if ($$0.isEmpty()) {
/*  72 */       throw new IllegalArgumentException("Expected at least one sampler to persist");
/*     */     }
/*     */     
/*  75 */     Map<MetricCategory, List<MetricSampler>> $$2 = (Map<MetricCategory, List<MetricSampler>>)$$0.stream().collect(Collectors.groupingBy(MetricSampler::getCategory));
/*  76 */     $$2.forEach(($$1, $$2) -> saveCategory($$1, $$2, $$0));
/*     */   }
/*     */   
/*     */   private void saveCategory(MetricCategory $$0, List<MetricSampler> $$1, Path $$2) {
/*  80 */     Path $$3 = $$2.resolve(Util.sanitizeName($$0.getDescription(), ResourceLocation::validPathChar) + ".csv");
/*     */     
/*  82 */     Writer $$4 = null;
/*     */     try {
/*  84 */       Files.createDirectories($$3.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/*  85 */       $$4 = Files.newBufferedWriter($$3, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/*     */       
/*  87 */       CsvOutput.Builder $$5 = CsvOutput.builder();
/*  88 */       $$5.addColumn("@tick");
/*  89 */       for (MetricSampler $$6 : $$1) {
/*  90 */         $$5.addColumn($$6.getName());
/*     */       }
/*  92 */       CsvOutput $$7 = $$5.build($$4);
/*     */ 
/*     */ 
/*     */       
/*  96 */       List<MetricSampler.SamplerResult> $$8 = (List<MetricSampler.SamplerResult>)$$1.stream().map(MetricSampler::result).collect(Collectors.toList());
/*     */       
/*  98 */       int $$9 = $$8.stream().mapToInt(MetricSampler.SamplerResult::getFirstTick).summaryStatistics().getMin();
/*  99 */       int $$10 = $$8.stream().mapToInt(MetricSampler.SamplerResult::getLastTick).summaryStatistics().getMax();
/*     */       
/* 101 */       for (int $$11 = $$9; $$11 <= $$10; $$11++) {
/* 102 */         int $$12 = $$11;
/*     */         
/* 104 */         Stream<String> $$13 = $$8.stream().map($$1 -> String.valueOf($$1.valueAtTick($$0)));
/*     */         
/* 106 */         Object[] $$14 = Stream.<String>concat(Stream.of(String.valueOf($$11)), $$13).toArray($$0 -> new String[$$0]);
/* 107 */         $$7.writeRow($$14);
/*     */       } 
/*     */       
/* 110 */       LOGGER.info("Flushed metrics to {}", $$3);
/* 111 */     } catch (Exception $$15) {
/* 112 */       LOGGER.error("Could not save profiler results to {}", $$3, $$15);
/*     */     } finally {
/* 114 */       IOUtils.closeQuietly($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void saveDeviations(Map<MetricSampler, List<RecordedDeviation>> $$0, Path $$1) {
/* 119 */     DateTimeFormatter $$2 = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss.SSS", Locale.UK).withZone(ZoneId.systemDefault());
/* 120 */     $$0.forEach(($$2, $$3) -> $$3.forEach(()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveProfilingTaskExecutionResult(ProfileResults $$0, Path $$1) {
/* 130 */     $$0.saveResults($$1.resolve("profiling.txt"));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\metrics\storage\MetricsPersister.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */