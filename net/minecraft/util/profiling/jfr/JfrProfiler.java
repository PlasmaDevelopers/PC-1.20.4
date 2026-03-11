/*     */ package net.minecraft.util.profiling.jfr;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.time.Instant;
/*     */ import java.time.ZoneId;
/*     */ import java.time.format.DateTimeFormatter;
/*     */ import java.time.format.DateTimeFormatterBuilder;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import jdk.jfr.Configuration;
/*     */ import jdk.jfr.Event;
/*     */ import jdk.jfr.FlightRecorder;
/*     */ import jdk.jfr.FlightRecorderListener;
/*     */ import jdk.jfr.Recording;
/*     */ import jdk.jfr.RecordingState;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.network.ConnectionProtocol;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
/*     */ import net.minecraft.util.profiling.jfr.event.ChunkGenerationEvent;
/*     */ import net.minecraft.util.profiling.jfr.event.NetworkSummaryEvent;
/*     */ import net.minecraft.util.profiling.jfr.event.PacketReceivedEvent;
/*     */ import net.minecraft.util.profiling.jfr.event.PacketSentEvent;
/*     */ import net.minecraft.util.profiling.jfr.event.ServerTickTimeEvent;
/*     */ import net.minecraft.util.profiling.jfr.event.WorldLoadFinishedEvent;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class JfrProfiler implements JvmProfiler {
/*  47 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final String ROOT_CATEGORY = "Minecraft";
/*     */   
/*     */   public static final String WORLD_GEN_CATEGORY = "World Generation";
/*     */   
/*     */   public static final String TICK_CATEGORY = "Ticking";
/*     */   public static final String NETWORK_CATEGORY = "Network";
/*  55 */   private static final List<Class<? extends Event>> CUSTOM_EVENTS = (List)List.of(ChunkGenerationEvent.class, PacketReceivedEvent.class, PacketSentEvent.class, NetworkSummaryEvent.class, ServerTickTimeEvent.class, WorldLoadFinishedEvent.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String FLIGHT_RECORDER_CONFIG = "/flightrecorder-config.jfc";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final DateTimeFormatter DATE_TIME_FORMATTER = (new DateTimeFormatterBuilder()).appendPattern("yyyy-MM-dd-HHmmss").toFormatter().withZone(ZoneId.systemDefault());
/*     */   
/*  67 */   private static final JfrProfiler INSTANCE = new JfrProfiler();
/*     */   
/*     */   @Nullable
/*     */   Recording recording;
/*     */   
/*     */   private float currentAverageTickTime;
/*  73 */   private final Map<String, NetworkSummaryEvent.SumAggregation> networkTrafficByAddress = new ConcurrentHashMap<>();
/*     */ 
/*     */   
/*     */   private JfrProfiler() {
/*  77 */     CUSTOM_EVENTS.forEach(FlightRecorder::register);
/*  78 */     FlightRecorder.addPeriodicEvent((Class)ServerTickTimeEvent.class, () -> (new ServerTickTimeEvent(this.currentAverageTickTime)).commit());
/*  79 */     FlightRecorder.addPeriodicEvent((Class)NetworkSummaryEvent.class, () -> {
/*     */           Iterator<NetworkSummaryEvent.SumAggregation> $$0 = this.networkTrafficByAddress.values().iterator();
/*     */           while ($$0.hasNext()) {
/*     */             ((NetworkSummaryEvent.SumAggregation)$$0.next()).commitEvent();
/*     */             $$0.remove();
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public static JfrProfiler getInstance() {
/*  89 */     return INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean start(Environment $$0) {
/*  94 */     URL $$1 = JfrProfiler.class.getResource("/flightrecorder-config.jfc");
/*  95 */     if ($$1 == null) {
/*  96 */       LOGGER.warn("Could not find default flight recorder config at {}", "/flightrecorder-config.jfc");
/*  97 */       return false;
/*     */     }  
/*  99 */     try { BufferedReader $$2 = new BufferedReader(new InputStreamReader($$1.openStream())); 
/* 100 */       try { boolean bool = start($$2, $$0);
/* 101 */         $$2.close(); return bool; } catch (Throwable throwable) { try { $$2.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException $$3)
/* 102 */     { LOGGER.warn("Failed to start flight recorder using configuration at {}", $$1, $$3);
/* 103 */       return false; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public Path stop() {
/* 109 */     if (this.recording == null) {
/* 110 */       throw new IllegalStateException("Not currently profiling");
/*     */     }
/*     */     
/* 113 */     this.networkTrafficByAddress.clear();
/*     */     
/* 115 */     Path $$0 = this.recording.getDestination();
/* 116 */     this.recording.stop();
/*     */     
/* 118 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRunning() {
/* 123 */     return (this.recording != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAvailable() {
/* 128 */     return FlightRecorder.isAvailable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean start(Reader $$0, Environment $$1) {
/* 140 */     if (isRunning()) {
/* 141 */       LOGGER.warn("Profiling already in progress");
/* 142 */       return false;
/*     */     } 
/*     */     
/*     */     try {
/* 146 */       Configuration $$2 = Configuration.create($$0);
/* 147 */       String $$3 = DATE_TIME_FORMATTER.format(Instant.now());
/* 148 */       this.recording = (Recording)Util.make(new Recording($$2), $$2 -> {
/*     */             Objects.requireNonNull($$2); CUSTOM_EVENTS.forEach($$2::enable);
/*     */             $$2.setDumpOnExit(true);
/*     */             $$2.setToDisk(true);
/*     */             $$2.setName(String.format(Locale.ROOT, "%s-%s-%s", new Object[] { $$0.getDescription(), SharedConstants.getCurrentVersion().getName(), $$1 }));
/*     */           });
/* 154 */       Path $$4 = Paths.get(String.format(Locale.ROOT, "debug/%s-%s.jfr", new Object[] { $$1.getDescription(), $$3 }), new String[0]);
/* 155 */       FileUtil.createDirectoriesSafe($$4.getParent());
/* 156 */       this.recording.setDestination($$4);
/* 157 */       this.recording.start();
/*     */       
/* 159 */       setupSummaryListener();
/* 160 */     } catch (IOException|java.text.ParseException $$5) {
/* 161 */       LOGGER.warn("Failed to start jfr profiling", $$5);
/* 162 */       return false;
/*     */     } 
/* 164 */     LOGGER.info("Started flight recorder profiling id({}):name({}) - will dump to {} on exit or stop command", new Object[] { Long.valueOf(this.recording.getId()), this.recording.getName(), this.recording.getDestination() });
/* 165 */     return true;
/*     */   }
/*     */   
/*     */   private void setupSummaryListener() {
/* 169 */     FlightRecorder.addListener(new FlightRecorderListener() {
/* 170 */           final SummaryReporter summaryReporter = new SummaryReporter(() -> JfrProfiler.this.recording = null);
/*     */ 
/*     */           
/*     */           public void recordingStateChanged(Recording $$0) {
/* 174 */             if ($$0 != JfrProfiler.this.recording || $$0.getState() != RecordingState.STOPPED) {
/*     */               return;
/*     */             }
/* 177 */             this.summaryReporter.recordingStopped($$0.getDestination());
/* 178 */             FlightRecorder.removeListener(this);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onServerTick(float $$0) {
/* 185 */     if (ServerTickTimeEvent.TYPE.isEnabled()) {
/* 186 */       this.currentAverageTickTime = $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketReceived(ConnectionProtocol $$0, int $$1, SocketAddress $$2, int $$3) {
/* 192 */     if (PacketReceivedEvent.TYPE.isEnabled()) {
/* 193 */       (new PacketReceivedEvent($$0.id(), $$1, $$2, $$3)).commit();
/*     */     }
/*     */     
/* 196 */     if (NetworkSummaryEvent.TYPE.isEnabled()) {
/* 197 */       networkStatFor($$2).trackReceivedPacket($$3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onPacketSent(ConnectionProtocol $$0, int $$1, SocketAddress $$2, int $$3) {
/* 203 */     if (PacketSentEvent.TYPE.isEnabled()) {
/* 204 */       (new PacketSentEvent($$0.id(), $$1, $$2, $$3)).commit();
/*     */     }
/*     */     
/* 207 */     if (NetworkSummaryEvent.TYPE.isEnabled()) {
/* 208 */       networkStatFor($$2).trackSentPacket($$3);
/*     */     }
/*     */   }
/*     */   
/*     */   private NetworkSummaryEvent.SumAggregation networkStatFor(SocketAddress $$0) {
/* 213 */     return this.networkTrafficByAddress.computeIfAbsent($$0.toString(), net.minecraft.util.profiling.jfr.event.NetworkSummaryEvent.SumAggregation::new);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ProfiledDuration onWorldLoadedStarted() {
/* 219 */     if (!WorldLoadFinishedEvent.TYPE.isEnabled()) {
/* 220 */       return null;
/*     */     }
/* 222 */     WorldLoadFinishedEvent $$0 = new WorldLoadFinishedEvent();
/* 223 */     $$0.begin();
/* 224 */     Objects.requireNonNull($$0); return $$0::commit;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ProfiledDuration onChunkGenerate(ChunkPos $$0, ResourceKey<Level> $$1, String $$2) {
/* 230 */     if (!ChunkGenerationEvent.TYPE.isEnabled()) {
/* 231 */       return null;
/*     */     }
/* 233 */     ChunkGenerationEvent $$3 = new ChunkGenerationEvent($$0, $$1, $$2);
/* 234 */     $$3.begin();
/* 235 */     Objects.requireNonNull($$3); return $$3::commit;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\JfrProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */