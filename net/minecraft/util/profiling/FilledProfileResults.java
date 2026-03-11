/*     */ package net.minecraft.util.profiling;
/*     */ 
/*     */ import com.google.common.base.Splitter;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2LongMaps;
/*     */ import java.io.Writer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class FilledProfileResults implements ProfileResults {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  29 */   private static final ProfilerPathEntry EMPTY = new ProfilerPathEntry()
/*     */     {
/*     */       public long getDuration() {
/*  32 */         return 0L;
/*     */       }
/*     */ 
/*     */       
/*     */       public long getMaxDuration() {
/*  37 */         return 0L;
/*     */       }
/*     */ 
/*     */       
/*     */       public long getCount() {
/*  42 */         return 0L;
/*     */       }
/*     */ 
/*     */       
/*     */       public Object2LongMap<String> getCounters() {
/*  47 */         return Object2LongMaps.emptyMap();
/*     */       }
/*     */     };
/*     */   private static final Comparator<Map.Entry<String, CounterCollector>> COUNTER_ENTRY_COMPARATOR;
/*  51 */   private static final Splitter SPLITTER = Splitter.on('\036'); static {
/*  52 */     COUNTER_ENTRY_COMPARATOR = Map.Entry.<String, CounterCollector>comparingByValue(Comparator.comparingLong($$0 -> $$0.totalValue)).reversed();
/*     */   }
/*     */   private final Map<String, ? extends ProfilerPathEntry> entries;
/*     */   private final long startTimeNano;
/*     */   private final int startTimeTicks;
/*     */   private final long endTimeNano;
/*     */   private final int endTimeTicks;
/*     */   private final int tickDuration;
/*     */   
/*     */   public FilledProfileResults(Map<String, ? extends ProfilerPathEntry> $$0, long $$1, int $$2, long $$3, int $$4) {
/*  62 */     this.entries = $$0;
/*  63 */     this.startTimeNano = $$1;
/*  64 */     this.startTimeTicks = $$2;
/*  65 */     this.endTimeNano = $$3;
/*  66 */     this.endTimeTicks = $$4;
/*  67 */     this.tickDuration = $$4 - $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   private ProfilerPathEntry getEntry(String $$0) {
/*  72 */     ProfilerPathEntry $$1 = this.entries.get($$0);
/*  73 */     return ($$1 != null) ? $$1 : EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResultField> getTimes(String $$0) {
/*  78 */     String $$1 = $$0;
/*  79 */     ProfilerPathEntry $$2 = getEntry("root");
/*  80 */     long $$3 = $$2.getDuration();
/*  81 */     ProfilerPathEntry $$4 = getEntry($$0);
/*  82 */     long $$5 = $$4.getDuration();
/*  83 */     long $$6 = $$4.getCount();
/*     */     
/*  85 */     List<ResultField> $$7 = Lists.newArrayList();
/*     */     
/*  87 */     if (!$$0.isEmpty()) {
/*  88 */       $$0 = $$0 + "\036";
/*     */     }
/*  90 */     long $$8 = 0L;
/*     */     
/*  92 */     for (String $$9 : this.entries.keySet()) {
/*  93 */       if (isDirectChild($$0, $$9)) {
/*  94 */         $$8 += getEntry($$9).getDuration();
/*     */       }
/*     */     } 
/*     */     
/*  98 */     float $$10 = (float)$$8;
/*  99 */     if ($$8 < $$5) {
/* 100 */       $$8 = $$5;
/*     */     }
/* 102 */     if ($$3 < $$8) {
/* 103 */       $$3 = $$8;
/*     */     }
/*     */     
/* 106 */     for (String $$11 : this.entries.keySet()) {
/* 107 */       if (isDirectChild($$0, $$11)) {
/* 108 */         ProfilerPathEntry $$12 = getEntry($$11);
/* 109 */         long $$13 = $$12.getDuration();
/* 110 */         double $$14 = $$13 * 100.0D / $$8;
/* 111 */         double $$15 = $$13 * 100.0D / $$3;
/* 112 */         String $$16 = $$11.substring($$0.length());
/* 113 */         $$7.add(new ResultField($$16, $$14, $$15, $$12.getCount()));
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     if ((float)$$8 > $$10) {
/* 118 */       $$7.add(new ResultField("unspecified", ((float)$$8 - $$10) * 100.0D / $$8, ((float)$$8 - $$10) * 100.0D / $$3, $$6));
/*     */     }
/*     */     
/* 121 */     Collections.sort($$7);
/* 122 */     $$7.add(0, new ResultField($$1, 100.0D, $$8 * 100.0D / $$3, $$6));
/* 123 */     return $$7;
/*     */   }
/*     */   
/*     */   private static boolean isDirectChild(String $$0, String $$1) {
/* 127 */     return ($$1.length() > $$0.length() && $$1.startsWith($$0) && $$1.indexOf('\036', $$0.length() + 1) < 0);
/*     */   }
/*     */   
/*     */   private Map<String, CounterCollector> getCounterValues() {
/* 131 */     Map<String, CounterCollector> $$0 = Maps.newTreeMap();
/* 132 */     this.entries.forEach(($$1, $$2) -> {
/*     */           Object2LongMap<String> $$3 = $$2.getCounters();
/*     */           
/*     */           if (!$$3.isEmpty()) {
/*     */             List<String> $$4 = SPLITTER.splitToList($$1);
/*     */             
/*     */             $$3.forEach(());
/*     */           } 
/*     */         });
/* 141 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getStartTimeNano() {
/* 146 */     return this.startTimeNano;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStartTimeTicks() {
/* 151 */     return this.startTimeTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getEndTimeNano() {
/* 156 */     return this.endTimeNano;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getEndTimeTicks() {
/* 161 */     return this.endTimeTicks;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean saveResults(Path $$0) {
/* 166 */     Writer $$1 = null;
/*     */     try {
/* 168 */       Files.createDirectories($$0.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 169 */       $$1 = Files.newBufferedWriter($$0, StandardCharsets.UTF_8, new java.nio.file.OpenOption[0]);
/* 170 */       $$1.write(getProfilerResults(getNanoDuration(), getTickDuration()));
/* 171 */       return true;
/* 172 */     } catch (Throwable $$2) {
/* 173 */       LOGGER.error("Could not save profiler results to {}", $$0, $$2);
/* 174 */       return false;
/*     */     } finally {
/* 176 */       IOUtils.closeQuietly($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected String getProfilerResults(long $$0, int $$1) {
/* 181 */     StringBuilder $$2 = new StringBuilder();
/*     */     
/* 183 */     $$2.append("---- Minecraft Profiler Results ----\n");
/* 184 */     $$2.append("// ");
/* 185 */     $$2.append(getComment());
/* 186 */     $$2.append("\n\n");
/*     */     
/* 188 */     $$2.append("Version: ").append(SharedConstants.getCurrentVersion().getId()).append('\n');
/* 189 */     $$2.append("Time span: ").append($$0 / 1000000L).append(" ms\n");
/* 190 */     $$2.append("Tick span: ").append($$1).append(" ticks\n");
/* 191 */     $$2.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$1 / (float)$$0 / 1.0E9F) })).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
/*     */     
/* 193 */     $$2.append("--- BEGIN PROFILE DUMP ---\n\n");
/*     */     
/* 195 */     appendProfilerResults(0, "root", $$2);
/*     */     
/* 197 */     $$2.append("--- END PROFILE DUMP ---\n\n");
/*     */     
/* 199 */     Map<String, CounterCollector> $$3 = getCounterValues();
/*     */     
/* 201 */     if (!$$3.isEmpty()) {
/* 202 */       $$2.append("--- BEGIN COUNTER DUMP ---\n\n");
/* 203 */       appendCounters($$3, $$2, $$1);
/* 204 */       $$2.append("--- END COUNTER DUMP ---\n\n");
/*     */     } 
/*     */     
/* 207 */     return $$2.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProfilerResults() {
/* 212 */     StringBuilder $$0 = new StringBuilder();
/* 213 */     appendProfilerResults(0, "root", $$0);
/* 214 */     return $$0.toString();
/*     */   }
/*     */   
/*     */   private static StringBuilder indentLine(StringBuilder $$0, int $$1) {
/* 218 */     $$0.append(String.format(Locale.ROOT, "[%02d] ", new Object[] { Integer.valueOf($$1) }));
/* 219 */     for (int $$2 = 0; $$2 < $$1; $$2++) {
/* 220 */       $$0.append("|   ");
/*     */     }
/* 222 */     return $$0;
/*     */   }
/*     */   
/*     */   private void appendProfilerResults(int $$0, String $$1, StringBuilder $$2) {
/* 226 */     List<ResultField> $$3 = getTimes($$1);
/*     */     
/* 228 */     Object2LongMap<String> $$4 = ((ProfilerPathEntry)ObjectUtils.firstNonNull((Object[])new ProfilerPathEntry[] { this.entries.get($$1), EMPTY })).getCounters();
/* 229 */     $$4.forEach(($$2, $$3) -> indentLine($$0, $$1).append('#').append($$2).append(' ').append($$3).append('/').append($$3.longValue() / this.tickDuration).append('\n'));
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
/* 240 */     if ($$3.size() < 3) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 245 */     for (int $$5 = 1; $$5 < $$3.size(); $$5++) {
/* 246 */       ResultField $$6 = $$3.get($$5);
/*     */       
/* 248 */       indentLine($$2, $$0)
/* 249 */         .append($$6.name)
/* 250 */         .append('(')
/* 251 */         .append($$6.count)
/* 252 */         .append('/')
/* 253 */         .append(String.format(Locale.ROOT, "%.0f", new Object[] { Float.valueOf((float)$$6.count / this.tickDuration)
/* 254 */             })).append(')')
/* 255 */         .append(" - ")
/* 256 */         .append(String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$6.percentage) })).append("%/")
/* 257 */         .append(String.format(Locale.ROOT, "%.2f", new Object[] { Double.valueOf($$6.globalPercentage) })).append("%\n");
/*     */       
/* 259 */       if (!"unspecified".equals($$6.name)) {
/*     */         try {
/* 261 */           appendProfilerResults($$0 + 1, $$1 + "\036" + $$1, $$2);
/* 262 */         } catch (Exception $$7) {
/* 263 */           $$2.append("[[ EXCEPTION ").append($$7).append(" ]]");
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendCounterResults(int $$0, String $$1, CounterCollector $$2, int $$3, StringBuilder $$4) {
/* 270 */     indentLine($$4, $$0)
/* 271 */       .append($$1).append(" total:")
/* 272 */       .append($$2.selfValue).append('/')
/* 273 */       .append($$2.totalValue).append(" average: ")
/* 274 */       .append($$2.selfValue / $$3)
/* 275 */       .append('/')
/* 276 */       .append($$2.totalValue / $$3)
/* 277 */       .append('\n');
/* 278 */     $$2.children.entrySet().stream().sorted(COUNTER_ENTRY_COMPARATOR).forEach($$3 -> appendCounterResults($$0 + 1, (String)$$3.getKey(), (CounterCollector)$$3.getValue(), $$1, $$2));
/*     */   }
/*     */   
/*     */   private void appendCounters(Map<String, CounterCollector> $$0, StringBuilder $$1, int $$2) {
/* 282 */     $$0.forEach(($$2, $$3) -> {
/*     */           $$0.append("-- Counter: ").append($$2).append(" --\n");
/*     */           appendCounterResults(0, "root", $$3.children.get("root"), $$1, $$0);
/*     */           $$0.append("\n\n");
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private static String getComment() {
/* 291 */     String[] $$0 = { "I'd Rather Be Surfing", "Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server." };
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
/* 310 */       return $$0[(int)(Util.getNanos() % $$0.length)];
/* 311 */     } catch (Throwable $$1) {
/* 312 */       return "Witty comment unavailable :(";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTickDuration() {
/* 318 */     return this.tickDuration;
/*     */   }
/*     */   
/*     */   private static class CounterCollector {
/*     */     long selfValue;
/*     */     long totalValue;
/* 324 */     final Map<String, CounterCollector> children = Maps.newHashMap();
/*     */     
/*     */     public void addValue(Iterator<String> $$0, long $$1) {
/* 327 */       this.totalValue += $$1;
/* 328 */       if (!$$0.hasNext()) {
/* 329 */         this.selfValue += $$1;
/*     */       } else {
/* 331 */         ((CounterCollector)this.children.computeIfAbsent($$0.next(), $$0 -> new CounterCollector())).addValue($$0, $$1);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\FilledProfileResults.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */