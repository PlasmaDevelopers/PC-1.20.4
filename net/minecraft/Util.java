/*      */ package net.minecraft;
/*      */ 
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.collect.Iterators;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.util.concurrent.ListeningExecutorService;
/*      */ import com.google.common.util.concurrent.MoreExecutors;
/*      */ import com.mojang.datafixers.DSL;
/*      */ import com.mojang.datafixers.DataFixUtils;
/*      */ import com.mojang.datafixers.Typed;
/*      */ import com.mojang.datafixers.types.Type;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.Dynamic;
/*      */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*      */ import it.unimi.dsi.fastutil.objects.ObjectList;
/*      */ import it.unimi.dsi.fastutil.objects.Reference2IntOpenHashMap;
/*      */ import it.unimi.dsi.fastutil.objects.ReferenceImmutableList;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.lang.management.ManagementFactory;
/*      */ import java.lang.management.RuntimeMXBean;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URL;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.spi.FileSystemProvider;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.time.Duration;
/*      */ import java.time.Instant;
/*      */ import java.time.ZonedDateTime;
/*      */ import java.time.format.DateTimeFormatter;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.BlockingQueue;
/*      */ import java.util.concurrent.CompletableFuture;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.ExecutorService;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.ForkJoinPool;
/*      */ import java.util.concurrent.ForkJoinWorkerThread;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.BooleanSupplier;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Function;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.function.Supplier;
/*      */ import java.util.function.ToIntFunction;
/*      */ import java.util.function.UnaryOperator;
/*      */ import java.util.stream.Collector;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.IntStream;
/*      */ import java.util.stream.LongStream;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.Bootstrap;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.SingleKeyCache;
/*      */ import net.minecraft.util.TimeSource;
/*      */ import net.minecraft.util.datafix.DataFixers;
/*      */ import net.minecraft.world.level.block.state.properties.Property;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Util
/*      */ {
/*   86 */   static final Logger LOGGER = LogUtils.getLogger();
/*      */   private static final int DEFAULT_MAX_THREADS = 255;
/*      */   private static final int DEFAULT_SAFE_FILE_OPERATION_RETRIES = 10;
/*      */   private static final String MAX_THREADS_SYSTEM_PROPERTY = "max.bg.threads";
/*   90 */   private static final ExecutorService BACKGROUND_EXECUTOR = makeExecutor("Main");
/*   91 */   private static final ExecutorService IO_POOL = makeIoExecutor("IO-Worker-", false);
/*   92 */   private static final ExecutorService DOWNLOAD_POOL = makeIoExecutor("Download-", true);
/*   93 */   private static final DateTimeFormatter FILENAME_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss", Locale.ROOT);
/*      */   
/*      */   private static final int LINEAR_LOOKUP_THRESHOLD = 8;
/*      */   public static final long NANOS_PER_MILLI = 1000000L;
/*   97 */   public static TimeSource.NanoTimeSource timeSource = System::nanoTime;
/*   98 */   public static final Ticker TICKER = new Ticker()
/*      */     {
/*      */       public long read() {
/*  101 */         return Util.timeSource.getAsLong();
/*      */       }
/*      */     };
/*  104 */   public static final UUID NIL_UUID = new UUID(0L, 0L);
/*      */   
/*      */   public static final FileSystemProvider ZIP_FILE_SYSTEM_PROVIDER;
/*      */   
/*      */   static {
/*  109 */     ZIP_FILE_SYSTEM_PROVIDER = (FileSystemProvider)FileSystemProvider.installedProviders().stream().filter($$0 -> $$0.getScheme().equalsIgnoreCase("jar")).findFirst().orElseThrow(() -> new IllegalStateException("No jar file system provider found"));
/*      */   } private static Consumer<String> thePauser = $$0 -> {
/*      */     
/*      */     };
/*      */   public static <K, V> Collector<Map.Entry<? extends K, ? extends V>, ?, Map<K, V>> toMap() {
/*  114 */     return Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue);
/*      */   }
/*      */ 
/*      */   
/*      */   public static <T extends Comparable<T>> String getPropertyName(Property<T> $$0, Object $$1) {
/*  119 */     return $$0.getName((Comparable)$$1);
/*      */   }
/*      */   
/*      */   public static String makeDescriptionId(String $$0, @Nullable ResourceLocation $$1) {
/*  123 */     if ($$1 == null) {
/*  124 */       return $$0 + ".unregistered_sadface";
/*      */     }
/*  126 */     return $$0 + "." + $$0 + "." + $$1.getNamespace();
/*      */   }
/*      */   
/*      */   public static long getMillis() {
/*  130 */     return getNanos() / 1000000L;
/*      */   }
/*      */   
/*      */   public static long getNanos() {
/*  134 */     return timeSource.getAsLong();
/*      */   }
/*      */   
/*      */   public static long getEpochMillis() {
/*  138 */     return Instant.now().toEpochMilli();
/*      */   }
/*      */   
/*      */   public static String getFilenameFormattedDateTime() {
/*  142 */     return FILENAME_DATE_TIME_FORMATTER.format(ZonedDateTime.now());
/*      */   }
/*      */   private static ExecutorService makeExecutor(String $$0) {
/*      */     ExecutorService $$4;
/*  146 */     int $$1 = Mth.clamp(Runtime.getRuntime().availableProcessors() - 1, 1, getMaxThreads());
/*      */     
/*  148 */     if ($$1 <= 0) {
/*      */       
/*  150 */       ListeningExecutorService listeningExecutorService = MoreExecutors.newDirectExecutorService();
/*      */     } else {
/*  152 */       AtomicInteger $$3 = new AtomicInteger(1);
/*  153 */       $$4 = new ForkJoinPool($$1, $$2 -> {
/*      */             ForkJoinWorkerThread $$3 = new ForkJoinWorkerThread($$2)
/*      */               {
/*      */                 protected void onTermination(Throwable $$0) {
/*  157 */                   if ($$0 != null) {
/*  158 */                     Util.LOGGER.warn("{} died", getName(), $$0);
/*      */                   } else {
/*  160 */                     Util.LOGGER.debug("{} shutdown", getName());
/*      */                   } 
/*  162 */                   super.onTermination($$0);
/*      */                 }
/*      */               };
/*      */             $$3.setName("Worker-" + $$0 + "-" + $$1.getAndIncrement());
/*      */             return $$3;
/*      */           }Util::onThreadException, true);
/*      */     } 
/*  169 */     return $$4;
/*      */   }
/*      */   
/*      */   private static int getMaxThreads() {
/*  173 */     String $$0 = System.getProperty("max.bg.threads");
/*  174 */     if ($$0 != null) {
/*      */       try {
/*  176 */         int $$1 = Integer.parseInt($$0);
/*  177 */         if ($$1 >= 1 && $$1 <= 255) {
/*  178 */           return $$1;
/*      */         }
/*  180 */         LOGGER.error("Wrong {} property value '{}'. Should be an integer value between 1 and {}.", new Object[] { "max.bg.threads", $$0, Integer.valueOf(255) });
/*  181 */       } catch (NumberFormatException $$2) {
/*  182 */         LOGGER.error("Could not parse {} property value '{}'. Should be an integer value between 1 and {}.", new Object[] { "max.bg.threads", $$0, Integer.valueOf(255) });
/*      */       } 
/*      */     }
/*  185 */     return 255;
/*      */   }
/*      */   
/*      */   public static ExecutorService backgroundExecutor() {
/*  189 */     return BACKGROUND_EXECUTOR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ExecutorService ioPool() {
/*  196 */     return IO_POOL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ExecutorService nonCriticalIoPool() {
/*  203 */     return DOWNLOAD_POOL;
/*      */   }
/*      */   
/*      */   public static void shutdownExecutors() {
/*  207 */     shutdownExecutor(BACKGROUND_EXECUTOR);
/*  208 */     shutdownExecutor(IO_POOL);
/*      */   }
/*      */   private static void shutdownExecutor(ExecutorService $$0) {
/*      */     boolean $$3;
/*  212 */     $$0.shutdown();
/*      */     
/*      */     try {
/*  215 */       boolean $$1 = $$0.awaitTermination(3L, TimeUnit.SECONDS);
/*  216 */     } catch (InterruptedException $$2) {
/*  217 */       $$3 = false;
/*      */     } 
/*  219 */     if (!$$3) {
/*  220 */       $$0.shutdownNow();
/*      */     }
/*      */   }
/*      */   
/*      */   private static ExecutorService makeIoExecutor(String $$0, boolean $$1) {
/*  225 */     AtomicInteger $$2 = new AtomicInteger(1);
/*  226 */     return Executors.newCachedThreadPool($$3 -> {
/*      */           Thread $$4 = new Thread($$3);
/*      */           $$4.setName($$0 + $$0);
/*      */           $$4.setDaemon($$2);
/*      */           $$4.setUncaughtExceptionHandler(Util::onThreadException);
/*      */           return $$4;
/*      */         });
/*      */   }
/*      */   
/*      */   public static void throwAsRuntime(Throwable $$0) {
/*  236 */     throw ($$0 instanceof RuntimeException) ? (RuntimeException)$$0 : new RuntimeException($$0);
/*      */   }
/*      */   
/*      */   private static void onThreadException(Thread $$0, Throwable $$1) {
/*  240 */     pauseInIde($$1);
/*  241 */     if ($$1 instanceof java.util.concurrent.CompletionException) {
/*  242 */       $$1 = $$1.getCause();
/*      */     }
/*  244 */     if ($$1 instanceof ReportedException) { ReportedException $$2 = (ReportedException)$$1;
/*  245 */       Bootstrap.realStdoutPrintln($$2.getReport().getFriendlyReport());
/*  246 */       System.exit(-1); }
/*      */     
/*  248 */     LOGGER.error(String.format(Locale.ROOT, "Caught exception in thread %s", new Object[] { $$0 }), $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static Type<?> fetchChoiceType(DSL.TypeReference $$0, String $$1) {
/*  253 */     if (!SharedConstants.CHECK_DATA_FIXER_SCHEMA) {
/*  254 */       return null;
/*      */     }
/*  256 */     return doFetchChoiceType($$0, $$1);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private static Type<?> doFetchChoiceType(DSL.TypeReference $$0, String $$1) {
/*  261 */     Type<?> $$2 = null;
/*      */     try {
/*  263 */       $$2 = DataFixers.getDataFixer().getSchema(DataFixUtils.makeKey(SharedConstants.getCurrentVersion().getDataVersion().getVersion())).getChoiceType($$0, $$1);
/*  264 */     } catch (IllegalArgumentException $$3) {
/*  265 */       LOGGER.error("No data fixer registered for {}", $$1);
/*  266 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  267 */         throw $$3;
/*      */       }
/*      */     } 
/*  270 */     return $$2;
/*      */   }
/*      */   
/*      */   public static Runnable wrapThreadWithTaskName(String $$0, Runnable $$1) {
/*  274 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  275 */       return () -> {
/*      */           Thread $$2 = Thread.currentThread();
/*      */           
/*      */           String $$3 = $$2.getName();
/*      */           $$2.setName($$0);
/*      */           try {
/*      */             $$1.run();
/*      */           } finally {
/*      */             $$2.setName($$3);
/*      */           } 
/*      */         };
/*      */     }
/*  287 */     return $$1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static <V> Supplier<V> wrapThreadWithTaskName(String $$0, Supplier<V> $$1) {
/*  292 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  293 */       return () -> {
/*      */           Thread $$2 = Thread.currentThread();
/*      */           
/*      */           String $$3 = $$2.getName();
/*      */           $$2.setName($$0);
/*      */           try {
/*      */             return $$1.get();
/*      */           } finally {
/*      */             $$2.setName($$3);
/*      */           } 
/*      */         };
/*      */     }
/*  305 */     return $$1;
/*      */   }
/*      */   
/*      */   public enum OS
/*      */   {
/*  310 */     LINUX("linux"),
/*  311 */     SOLARIS("solaris"),
/*  312 */     WINDOWS("windows")
/*      */     {
/*      */       protected String[] getOpenUrlArguments(URL $$0) {
/*  315 */         return new String[] { "rundll32", "url.dll,FileProtocolHandler", $$0.toString() };
/*      */       }
/*      */     },
/*  318 */     OSX("mac")
/*      */     {
/*      */       protected String[] getOpenUrlArguments(URL $$0) {
/*  321 */         return new String[] { "open", $$0.toString() };
/*      */       }
/*      */     },
/*  324 */     UNKNOWN("unknown");
/*      */     
/*      */     private final String telemetryName;
/*      */     
/*      */     OS(String $$0) {
/*  329 */       this.telemetryName = $$0;
/*      */     }
/*      */     
/*      */     public void openUrl(URL $$0) {
/*      */       try {
/*  334 */         Process $$1 = AccessController.<Process>doPrivileged(() -> Runtime.getRuntime().exec(getOpenUrlArguments($$0)));
/*  335 */         $$1.getInputStream().close();
/*  336 */         $$1.getErrorStream().close();
/*  337 */         $$1.getOutputStream().close();
/*  338 */       } catch (PrivilegedActionException|IOException $$2) {
/*  339 */         Util.LOGGER.error("Couldn't open url '{}'", $$0, $$2);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void openUri(URI $$0) {
/*      */       try {
/*  345 */         openUrl($$0.toURL());
/*  346 */       } catch (MalformedURLException $$1) {
/*  347 */         Util.LOGGER.error("Couldn't open uri '{}'", $$0, $$1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public void openFile(File $$0) {
/*      */       try {
/*  353 */         openUrl($$0.toURI().toURL());
/*  354 */       } catch (MalformedURLException $$1) {
/*  355 */         Util.LOGGER.error("Couldn't open file '{}'", $$0, $$1);
/*      */       } 
/*      */     }
/*      */     
/*      */     protected String[] getOpenUrlArguments(URL $$0) {
/*  360 */       String $$1 = $$0.toString();
/*  361 */       if ("file".equals($$0.getProtocol()))
/*      */       {
/*  363 */         $$1 = $$1.replace("file:", "file://");
/*      */       }
/*  365 */       return new String[] { "xdg-open", $$1 };
/*      */     }
/*      */     
/*      */     public void openUri(String $$0) {
/*      */       try {
/*  370 */         openUrl((new URI($$0)).toURL());
/*      */       }
/*  372 */       catch (URISyntaxException|MalformedURLException|IllegalArgumentException $$1) {
/*  373 */         Util.LOGGER.error("Couldn't open uri '{}'", $$0, $$1);
/*      */       } 
/*      */     }
/*      */     
/*      */     public String telemetryName() {
/*  378 */       return this.telemetryName;
/*      */     }
/*      */   }
/*      */   
/*      */   public static OS getPlatform() {
/*  383 */     String $$0 = System.getProperty("os.name").toLowerCase(Locale.ROOT);
/*  384 */     if ($$0.contains("win")) {
/*  385 */       return OS.WINDOWS;
/*      */     }
/*  387 */     if ($$0.contains("mac")) {
/*  388 */       return OS.OSX;
/*      */     }
/*  390 */     if ($$0.contains("solaris")) {
/*  391 */       return OS.SOLARIS;
/*      */     }
/*  393 */     if ($$0.contains("sunos")) {
/*  394 */       return OS.SOLARIS;
/*      */     }
/*  396 */     if ($$0.contains("linux")) {
/*  397 */       return OS.LINUX;
/*      */     }
/*  399 */     if ($$0.contains("unix")) {
/*  400 */       return OS.LINUX;
/*      */     }
/*  402 */     return OS.UNKNOWN;
/*      */   }
/*      */   enum null {
/*      */     protected String[] getOpenUrlArguments(URL $$0) { return new String[] { "rundll32", "url.dll,FileProtocolHandler", $$0.toString() }; } } enum null {
/*  406 */     protected String[] getOpenUrlArguments(URL $$0) { return new String[] { "open", $$0.toString() }; } } public static Stream<String> getVmArguments() { RuntimeMXBean $$0 = ManagementFactory.getRuntimeMXBean();
/*  407 */     return $$0.getInputArguments().stream().filter($$0 -> $$0.startsWith("-X")); }
/*      */ 
/*      */   
/*      */   public static <T> T lastOf(List<T> $$0) {
/*  411 */     return $$0.get($$0.size() - 1);
/*      */   }
/*      */   
/*      */   public static <T> T findNextInIterable(Iterable<T> $$0, @Nullable T $$1) {
/*  415 */     Iterator<T> $$2 = $$0.iterator();
/*  416 */     T $$3 = $$2.next();
/*      */     
/*  418 */     if ($$1 != null) {
/*  419 */       T $$4 = $$3;
/*      */       while (true) {
/*  421 */         if ($$4 == $$1) {
/*  422 */           if ($$2.hasNext()) {
/*  423 */             return $$2.next();
/*      */           }
/*      */           
/*      */           break;
/*      */         } 
/*  428 */         if ($$2.hasNext()) {
/*  429 */           $$4 = $$2.next();
/*      */         }
/*      */       } 
/*      */     } 
/*  433 */     return $$3;
/*      */   }
/*      */   
/*      */   public static <T> T findPreviousInIterable(Iterable<T> $$0, @Nullable T $$1) {
/*  437 */     Iterator<T> $$2 = $$0.iterator();
/*  438 */     T $$3 = null;
/*  439 */     while ($$2.hasNext()) {
/*  440 */       T $$4 = $$2.next();
/*  441 */       if ($$4 == $$1) {
/*  442 */         if ($$3 == null) {
/*  443 */           $$3 = $$2.hasNext() ? (T)Iterators.getLast($$2) : $$1;
/*      */         }
/*      */         break;
/*      */       } 
/*  447 */       $$3 = $$4;
/*      */     } 
/*  449 */     return $$3;
/*      */   }
/*      */   
/*      */   public static <T> T make(Supplier<T> $$0) {
/*  453 */     return $$0.get();
/*      */   }
/*      */   
/*      */   public static <T> T make(T $$0, Consumer<? super T> $$1) {
/*  457 */     $$1.accept($$0);
/*  458 */     return $$0;
/*      */   }
/*      */   
/*      */   public static <V> CompletableFuture<List<V>> sequence(List<? extends CompletableFuture<V>> $$0) {
/*  462 */     if ($$0.isEmpty()) {
/*  463 */       return CompletableFuture.completedFuture(List.of());
/*      */     }
/*      */     
/*  466 */     if ($$0.size() == 1) {
/*  467 */       return ((CompletableFuture)$$0.get(0)).thenApply(List::of);
/*      */     }
/*      */     
/*  470 */     CompletableFuture<Void> $$1 = CompletableFuture.allOf((CompletableFuture<?>[])$$0.<CompletableFuture>toArray(new CompletableFuture[0]));
/*  471 */     return $$1.thenApply($$1 -> $$0.stream().map(CompletableFuture::join).toList());
/*      */   }
/*      */ 
/*      */   
/*      */   public static <V> CompletableFuture<List<V>> sequenceFailFast(List<? extends CompletableFuture<? extends V>> $$0) {
/*  476 */     CompletableFuture<List<V>> $$1 = new CompletableFuture<>();
/*      */     
/*  478 */     Objects.requireNonNull($$1); return fallibleSequence($$0, $$1::completeExceptionally)
/*  479 */       .applyToEither($$1, (Function)Function.identity());
/*      */   }
/*      */   
/*      */   public static <V> CompletableFuture<List<V>> sequenceFailFastAndCancel(List<? extends CompletableFuture<? extends V>> $$0) {
/*  483 */     CompletableFuture<List<V>> $$1 = new CompletableFuture<>();
/*      */     
/*  485 */     return fallibleSequence($$0, $$2 -> {
/*      */           if ($$0.completeExceptionally($$2)) {
/*      */             for (CompletableFuture<? extends V> $$3 : (Iterable<CompletableFuture<? extends V>>)$$1) {
/*      */               $$3.cancel(true);
/*      */             }
/*      */           }
/*  491 */         }).applyToEither($$1, (Function)Function.identity());
/*      */   }
/*      */   
/*      */   private static <V> CompletableFuture<List<V>> fallibleSequence(List<? extends CompletableFuture<? extends V>> $$0, Consumer<Throwable> $$1) {
/*  495 */     List<V> $$2 = Lists.newArrayListWithCapacity($$0.size());
/*  496 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[$$0.size()];
/*      */     
/*  498 */     $$0.forEach($$3 -> {
/*      */           int $$4 = $$0.size();
/*      */ 
/*      */ 
/*      */           
/*      */           $$0.add(null);
/*      */ 
/*      */           
/*      */           $$1[$$4] = $$3.whenComplete(());
/*      */         });
/*      */ 
/*      */     
/*  510 */     return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture).thenApply($$1 -> $$0);
/*      */   }
/*      */   
/*      */   public static <T> Optional<T> ifElse(Optional<T> $$0, Consumer<T> $$1, Runnable $$2) {
/*  514 */     if ($$0.isPresent()) {
/*  515 */       $$1.accept($$0.get());
/*      */     } else {
/*  517 */       $$2.run();
/*      */     } 
/*  519 */     return $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> Supplier<T> name(Supplier<T> $$0, Supplier<String> $$1) {
/*  537 */     return $$0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Runnable name(Runnable $$0, Supplier<String> $$1) {
/*  556 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void logAndPauseIfInIde(String $$0) {
/*  561 */     LOGGER.error($$0);
/*  562 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  563 */       doPause($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static void logAndPauseIfInIde(String $$0, Throwable $$1) {
/*  568 */     LOGGER.error($$0, $$1);
/*  569 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  570 */       doPause($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static <T extends Throwable> T pauseInIde(T $$0) {
/*  575 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/*  576 */       LOGGER.error("Trying to throw a fatal exception, pausing in IDE", (Throwable)$$0);
/*  577 */       doPause($$0.getMessage());
/*      */     } 
/*  579 */     return $$0;
/*      */   }
/*      */   
/*      */   public static void setPause(Consumer<String> $$0) {
/*  583 */     thePauser = $$0;
/*      */   }
/*      */   
/*      */   private static void doPause(String $$0) {
/*  587 */     Instant $$1 = Instant.now();
/*      */     
/*  589 */     LOGGER.warn("Did you remember to set a breakpoint here?");
/*  590 */     boolean $$2 = (Duration.between($$1, Instant.now()).toMillis() > 500L);
/*  591 */     if (!$$2) {
/*  592 */       thePauser.accept($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String describeError(Throwable $$0) {
/*  597 */     if ($$0.getCause() != null)
/*  598 */       return describeError($$0.getCause()); 
/*  599 */     if ($$0.getMessage() != null) {
/*  600 */       return $$0.getMessage();
/*      */     }
/*  602 */     return $$0.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static <T> T getRandom(T[] $$0, RandomSource $$1) {
/*  607 */     return $$0[$$1.nextInt($$0.length)];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getRandom(int[] $$0, RandomSource $$1) {
/*  612 */     return $$0[$$1.nextInt($$0.length)];
/*      */   }
/*      */   
/*      */   public static <T> T getRandom(List<T> $$0, RandomSource $$1) {
/*  616 */     return $$0.get($$1.nextInt($$0.size()));
/*      */   }
/*      */   
/*      */   public static <T> Optional<T> getRandomSafe(List<T> $$0, RandomSource $$1) {
/*  620 */     if ($$0.isEmpty()) {
/*  621 */       return Optional.empty();
/*      */     }
/*  623 */     return Optional.of(getRandom($$0, $$1));
/*      */   }
/*      */   
/*      */   private static BooleanSupplier createRenamer(final Path from, final Path to) {
/*  627 */     return new BooleanSupplier()
/*      */       {
/*      */         public boolean getAsBoolean() {
/*      */           try {
/*  631 */             Files.move(from, to, new java.nio.file.CopyOption[0]);
/*  632 */             return true;
/*  633 */           } catch (IOException $$0) {
/*  634 */             Util.LOGGER.error("Failed to rename", $$0);
/*  635 */             return false;
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  641 */           return "rename " + from + " to " + to;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static BooleanSupplier createDeleter(final Path target) {
/*  647 */     return new BooleanSupplier()
/*      */       {
/*      */         public boolean getAsBoolean() {
/*      */           try {
/*  651 */             Files.deleteIfExists(target);
/*  652 */             return true;
/*  653 */           } catch (IOException $$0) {
/*  654 */             Util.LOGGER.warn("Failed to delete", $$0);
/*  655 */             return false;
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  661 */           return "delete old " + target;
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static BooleanSupplier createFileDeletedCheck(final Path target) {
/*  667 */     return new BooleanSupplier()
/*      */       {
/*      */         public boolean getAsBoolean() {
/*  670 */           return !Files.exists(target, new java.nio.file.LinkOption[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  675 */           return "verify that " + target + " is deleted";
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static BooleanSupplier createFileCreatedCheck(final Path target) {
/*  681 */     return new BooleanSupplier()
/*      */       {
/*      */         public boolean getAsBoolean() {
/*  684 */           return Files.isRegularFile(target, new java.nio.file.LinkOption[0]);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  689 */           return "verify that " + target + " is present";
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   private static boolean executeInSequence(BooleanSupplier... $$0) {
/*  695 */     for (BooleanSupplier $$1 : $$0) {
/*  696 */       if (!$$1.getAsBoolean()) {
/*  697 */         LOGGER.warn("Failed to execute {}", $$1);
/*  698 */         return false;
/*      */       } 
/*      */     } 
/*  701 */     return true;
/*      */   }
/*      */   
/*      */   private static boolean runWithRetries(int $$0, String $$1, BooleanSupplier... $$2) {
/*  705 */     for (int $$3 = 0; $$3 < $$0; $$3++) {
/*  706 */       if (executeInSequence($$2)) {
/*  707 */         return true;
/*      */       }
/*  709 */       LOGGER.error("Failed to {}, retrying {}/{}", new Object[] { $$1, Integer.valueOf($$3), Integer.valueOf($$0) });
/*      */     } 
/*  711 */     LOGGER.error("Failed to {}, aborting, progress might be lost", $$1);
/*  712 */     return false;
/*      */   }
/*      */   
/*      */   public static void safeReplaceFile(Path $$0, Path $$1, Path $$2) {
/*  716 */     safeReplaceOrMoveFile($$0, $$1, $$2, false);
/*      */   }
/*      */   
/*      */   public static boolean safeReplaceOrMoveFile(Path $$0, Path $$1, Path $$2, boolean $$3) {
/*  720 */     if (Files.exists($$0, new java.nio.file.LinkOption[0]) && 
/*  721 */       !runWithRetries(10, "create backup " + $$2, new BooleanSupplier[] {
/*  722 */           createDeleter($$2), 
/*  723 */           createRenamer($$0, $$2), 
/*  724 */           createFileCreatedCheck($$2)
/*      */         })) {
/*  726 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  731 */     if (!runWithRetries(10, "remove old " + $$0, new BooleanSupplier[] {
/*  732 */           createDeleter($$0), 
/*  733 */           createFileDeletedCheck($$0)
/*      */         })) {
/*  735 */       return false;
/*      */     }
/*      */     
/*  738 */     if (!runWithRetries(10, "replace " + $$0 + " with " + $$1, new BooleanSupplier[] {
/*  739 */           createRenamer($$1, $$0), 
/*  740 */           createFileCreatedCheck($$0)
/*      */         }) && !$$3) {
/*  742 */       runWithRetries(10, "restore " + $$0 + " from " + $$2, new BooleanSupplier[] {
/*  743 */             createRenamer($$2, $$0), 
/*  744 */             createFileCreatedCheck($$0)
/*      */           });
/*  746 */       return false;
/*      */     } 
/*  748 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int offsetByCodepoints(String $$0, int $$1, int $$2) {
/*  753 */     int $$3 = $$0.length();
/*  754 */     if ($$2 >= 0) {
/*  755 */       for (int $$4 = 0; $$1 < $$3 && $$4 < $$2; $$4++) {
/*  756 */         if (Character.isHighSurrogate($$0.charAt($$1++)) && $$1 < $$3 && 
/*  757 */           Character.isLowSurrogate($$0.charAt($$1)))
/*      */         {
/*  759 */           $$1++;
/*      */         }
/*      */       } 
/*      */     } else {
/*  763 */       for (int $$5 = $$2; $$1 > 0 && $$5 < 0; $$5++) {
/*  764 */         if (Character.isLowSurrogate($$0.charAt(--$$1)) && $$1 > 0 && 
/*  765 */           Character.isHighSurrogate($$0.charAt($$1 - 1)))
/*      */         {
/*  767 */           $$1--;
/*      */         }
/*      */       } 
/*      */     } 
/*  771 */     return $$1;
/*      */   }
/*      */   
/*      */   public static Consumer<String> prefix(String $$0, Consumer<String> $$1) {
/*  775 */     return $$2 -> $$0.accept($$1 + $$1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DataResult<int[]> fixedSize(IntStream $$0, int $$1) {
/*  782 */     int[] $$2 = $$0.limit(($$1 + 1)).toArray();
/*  783 */     if ($$2.length != $$1) {
/*  784 */       Supplier<String> $$3 = () -> "Input is not a list of " + $$0 + " ints";
/*  785 */       if ($$2.length >= $$1) {
/*  786 */         return DataResult.error($$3, Arrays.copyOf($$2, $$1));
/*      */       }
/*  788 */       return DataResult.error($$3);
/*      */     } 
/*      */     
/*  791 */     return DataResult.success($$2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DataResult<long[]> fixedSize(LongStream $$0, int $$1) {
/*  798 */     long[] $$2 = $$0.limit(($$1 + 1)).toArray();
/*  799 */     if ($$2.length != $$1) {
/*  800 */       Supplier<String> $$3 = () -> "Input is not a list of " + $$0 + " longs";
/*  801 */       if ($$2.length >= $$1) {
/*  802 */         return DataResult.error($$3, Arrays.copyOf($$2, $$1));
/*      */       }
/*  804 */       return DataResult.error($$3);
/*      */     } 
/*      */     
/*  807 */     return DataResult.success($$2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> DataResult<List<T>> fixedSize(List<T> $$0, int $$1) {
/*  814 */     if ($$0.size() != $$1) {
/*  815 */       Supplier<String> $$2 = () -> "Input is not a list of " + $$0 + " elements";
/*  816 */       if ($$0.size() >= $$1) {
/*  817 */         return DataResult.error($$2, $$0.subList(0, $$1));
/*      */       }
/*  819 */       return DataResult.error($$2);
/*      */     } 
/*      */     
/*  822 */     return DataResult.success($$0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void startTimerHackThread() {
/*  828 */     Thread $$0 = new Thread("Timer hack thread")
/*      */       {
/*      */         public void run() {
/*      */           try {
/*      */             while (true)
/*  833 */               Thread.sleep(2147483647L); 
/*  834 */           } catch (InterruptedException $$0) {
/*  835 */             Util.LOGGER.warn("Timer hack thread interrupted, that really should not happen");
/*      */             
/*      */             return;
/*      */           } 
/*      */         }
/*      */       };
/*  841 */     $$0.setDaemon(true);
/*  842 */     $$0.setUncaughtExceptionHandler(new DefaultUncaughtExceptionHandler(LOGGER));
/*  843 */     $$0.start();
/*      */   }
/*      */   
/*      */   public static void copyBetweenDirs(Path $$0, Path $$1, Path $$2) throws IOException {
/*  847 */     Path $$3 = $$0.relativize($$2);
/*  848 */     Path $$4 = $$1.resolve($$3);
/*  849 */     Files.copy($$2, $$4, new java.nio.file.CopyOption[0]);
/*      */   }
/*      */   
/*      */   public static String sanitizeName(String $$0, CharPredicate $$1) {
/*  853 */     return $$0.toLowerCase(Locale.ROOT).chars().<CharSequence>mapToObj($$1 -> $$0.test((char)$$1) ? Character.toString((char)$$1) : "_").collect(Collectors.joining());
/*      */   }
/*      */   
/*      */   public static <K, V> SingleKeyCache<K, V> singleKeyCache(Function<K, V> $$0) {
/*  857 */     return new SingleKeyCache($$0);
/*      */   }
/*      */   
/*      */   public static <T, R> Function<T, R> memoize(final Function<T, R> function) {
/*  861 */     return new Function<T, R>() {
/*  862 */         private final Map<T, R> cache = new ConcurrentHashMap<>();
/*      */ 
/*      */         
/*      */         public R apply(T $$0) {
/*  866 */           return this.cache.computeIfAbsent($$0, function);
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  871 */           return "memoize/1[function=" + function + ", size=" + this.cache.size() + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static <T, U, R> BiFunction<T, U, R> memoize(final BiFunction<T, U, R> function) {
/*  877 */     return new BiFunction<T, U, R>() {
/*  878 */         private final Map<Pair<T, U>, R> cache = new ConcurrentHashMap<>();
/*      */ 
/*      */         
/*      */         public R apply(T $$0, U $$1) {
/*  882 */           return this.cache.computeIfAbsent(Pair.of($$0, $$1), $$1 -> $$0.apply($$1.getFirst(), $$1.getSecond()));
/*      */         }
/*      */ 
/*      */         
/*      */         public String toString() {
/*  887 */           return "memoize/2[function=" + function + ", size=" + this.cache.size() + "]";
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public static <T> List<T> toShuffledList(Stream<T> $$0, RandomSource $$1) {
/*  893 */     ObjectArrayList<T> $$2 = $$0.collect(ObjectArrayList.toList());
/*  894 */     shuffle((List<T>)$$2, $$1);
/*  895 */     return (List<T>)$$2;
/*      */   }
/*      */   
/*      */   public static IntArrayList toShuffledList(IntStream $$0, RandomSource $$1) {
/*  899 */     IntArrayList $$2 = IntArrayList.wrap($$0.toArray());
/*  900 */     int $$3 = $$2.size();
/*  901 */     for (int $$4 = $$3; $$4 > 1; $$4--) {
/*  902 */       int $$5 = $$1.nextInt($$4);
/*  903 */       $$2.set($$4 - 1, $$2.set($$5, $$2.getInt($$4 - 1)));
/*      */     } 
/*  905 */     return $$2;
/*      */   }
/*      */   
/*      */   public static <T> List<T> shuffledCopy(T[] $$0, RandomSource $$1) {
/*  909 */     ObjectArrayList<T> $$2 = new ObjectArrayList((Object[])$$0);
/*  910 */     shuffle((List<T>)$$2, $$1);
/*  911 */     return (List<T>)$$2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> List<T> shuffledCopy(ObjectArrayList<T> $$0, RandomSource $$1) {
/*  918 */     ObjectArrayList<T> $$2 = new ObjectArrayList((ObjectList)$$0);
/*  919 */     shuffle((List<T>)$$2, $$1);
/*  920 */     return (List<T>)$$2;
/*      */   }
/*      */   
/*      */   public static <T> void shuffle(List<T> $$0, RandomSource $$1) {
/*  924 */     int $$2 = $$0.size();
/*  925 */     for (int $$3 = $$2; $$3 > 1; $$3--) {
/*  926 */       int $$4 = $$1.nextInt($$3);
/*  927 */       $$0.set($$3 - 1, $$0.set($$4, $$0.get($$3 - 1)));
/*      */     } 
/*      */   }
/*      */   
/*      */   public static <T> CompletableFuture<T> blockUntilDone(Function<Executor, CompletableFuture<T>> $$0) {
/*  932 */     return blockUntilDone($$0, CompletableFuture::isDone);
/*      */   }
/*      */   
/*      */   public static <T> T blockUntilDone(Function<Executor, T> $$0, Predicate<T> $$1) {
/*  936 */     BlockingQueue<Runnable> $$2 = new LinkedBlockingQueue<>();
/*      */     
/*  938 */     Objects.requireNonNull($$2); T $$3 = $$0.apply($$2::add);
/*  939 */     while (!$$1.test($$3)) {
/*      */       
/*      */       try {
/*  942 */         Runnable $$4 = $$2.poll(100L, TimeUnit.MILLISECONDS);
/*  943 */         if ($$4 != null) {
/*  944 */           $$4.run();
/*      */         }
/*  946 */       } catch (InterruptedException $$5) {
/*  947 */         LOGGER.warn("Interrupted wait");
/*      */         
/*      */         break;
/*      */       } 
/*      */     } 
/*  952 */     int $$6 = $$2.size();
/*  953 */     if ($$6 > 0)
/*      */     {
/*      */       
/*  956 */       LOGGER.warn("Tasks left in queue: {}", Integer.valueOf($$6));
/*      */     }
/*      */     
/*  959 */     return $$3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> ToIntFunction<T> createIndexLookup(List<T> $$0) {
/*  968 */     int $$1 = $$0.size();
/*  969 */     if ($$1 < 8) {
/*  970 */       Objects.requireNonNull($$0); return $$0::indexOf;
/*      */     } 
/*      */     
/*  973 */     Object2IntOpenHashMap object2IntOpenHashMap = new Object2IntOpenHashMap($$1);
/*  974 */     object2IntOpenHashMap.defaultReturnValue(-1);
/*  975 */     for (int $$3 = 0; $$3 < $$1; $$3++) {
/*  976 */       object2IntOpenHashMap.put($$0.get($$3), $$3);
/*      */     }
/*  978 */     return (ToIntFunction<T>)object2IntOpenHashMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> ToIntFunction<T> createIndexIdentityLookup(List<T> $$0) {
/*  987 */     int $$1 = $$0.size();
/*      */     
/*  989 */     if ($$1 < 8) {
/*  990 */       ReferenceImmutableList referenceImmutableList = new ReferenceImmutableList($$0);
/*  991 */       Objects.requireNonNull(referenceImmutableList); return referenceImmutableList::indexOf;
/*      */     } 
/*      */     
/*  994 */     Reference2IntOpenHashMap reference2IntOpenHashMap = new Reference2IntOpenHashMap($$1);
/*  995 */     reference2IntOpenHashMap.defaultReturnValue(-1);
/*  996 */     for (int $$4 = 0; $$4 < $$1; $$4++) {
/*  997 */       reference2IntOpenHashMap.put($$0.get($$4), $$4);
/*      */     }
/*  999 */     return (ToIntFunction<T>)reference2IntOpenHashMap;
/*      */   }
/*      */   
/*      */   public static <T, E extends Throwable> T getOrThrow(DataResult<T> $$0, Function<String, E> $$1) throws E {
/* 1003 */     Optional<DataResult.PartialResult<T>> $$2 = $$0.error();
/* 1004 */     if ($$2.isPresent()) {
/* 1005 */       throw (E)$$1.apply(((DataResult.PartialResult)$$2.get()).message());
/*      */     }
/* 1007 */     return $$0.result().orElseThrow();
/*      */   }
/*      */   
/*      */   public static <T, E extends Throwable> T getPartialOrThrow(DataResult<T> $$0, Function<String, E> $$1) throws E {
/* 1011 */     Optional<DataResult.PartialResult<T>> $$2 = $$0.error();
/* 1012 */     if ($$2.isPresent()) {
/* 1013 */       Optional<T> $$3 = $$0.resultOrPartial($$0 -> { 
/* 1014 */           }); if ($$3.isPresent()) {
/* 1015 */         return $$3.get();
/*      */       }
/* 1017 */       throw (E)$$1.apply(((DataResult.PartialResult)$$2.get()).message());
/*      */     } 
/* 1019 */     return $$0.result().orElseThrow();
/*      */   }
/*      */   
/*      */   public static <A, B> Typed<B> writeAndReadTypedOrThrow(Typed<A> $$0, Type<B> $$1, UnaryOperator<Dynamic<?>> $$2) {
/* 1023 */     Dynamic<?> $$3 = getOrThrow($$0.write(), IllegalStateException::new);
/*      */     
/* 1025 */     return readTypedOrThrow($$1, $$2.apply($$3), true);
/*      */   }
/*      */   
/*      */   public static <T> Typed<T> readTypedOrThrow(Type<T> $$0, Dynamic<?> $$1) {
/* 1029 */     return readTypedOrThrow($$0, $$1, false);
/*      */   }
/*      */   
/*      */   public static <T> Typed<T> readTypedOrThrow(Type<T> $$0, Dynamic<?> $$1, boolean $$2) {
/* 1033 */     DataResult<Typed<T>> $$3 = $$0.readTyped($$1).map(Pair::getFirst);
/*      */     try {
/* 1035 */       if ($$2) {
/* 1036 */         return getPartialOrThrow($$3, IllegalStateException::new);
/*      */       }
/* 1038 */       return getOrThrow($$3, IllegalStateException::new);
/* 1039 */     } catch (IllegalStateException $$4) {
/* 1040 */       CrashReport $$5 = CrashReport.forThrowable($$4, "Reading type");
/* 1041 */       CrashReportCategory $$6 = $$5.addCategory("Info");
/* 1042 */       $$6.setDetail("Data", $$1);
/* 1043 */       $$6.setDetail("Type", $$0);
/* 1044 */       throw new ReportedException($$5);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWhitespace(int $$0) {
/* 1050 */     return (Character.isWhitespace($$0) || Character.isSpaceChar($$0));
/*      */   }
/*      */   
/*      */   public static boolean isBlank(@Nullable String $$0) {
/* 1054 */     if ($$0 == null || $$0.length() == 0) {
/* 1055 */       return true;
/*      */     }
/* 1057 */     return $$0.chars().allMatch(Util::isWhitespace);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\Util.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */