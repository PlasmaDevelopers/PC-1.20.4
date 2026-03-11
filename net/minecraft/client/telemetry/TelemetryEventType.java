/*     */ package net.minecraft.client.telemetry;
/*     */ import com.mojang.authlib.minecraft.TelemetryEvent;
/*     */ import com.mojang.authlib.minecraft.TelemetrySession;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import it.unimi.dsi.fastutil.longs.LongList;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.client.telemetry.events.GameLoadTimesEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ 
/*     */ public class TelemetryEventType {
/*  17 */   static final Map<String, TelemetryEventType> REGISTRY = (Map<String, TelemetryEventType>)new Object2ObjectLinkedOpenHashMap(); public static final Codec<TelemetryEventType> CODEC;
/*     */   static {
/*  19 */     CODEC = Codec.STRING.comapFlatMap($$0 -> { TelemetryEventType $$1 = REGISTRY.get($$0); return ($$1 != null) ? DataResult.success($$1) : DataResult.error(()); }TelemetryEventType::id);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  27 */   private static final List<TelemetryProperty<?>> GLOBAL_PROPERTIES = List.of(TelemetryProperty.USER_ID, TelemetryProperty.CLIENT_ID, TelemetryProperty.MINECRAFT_SESSION_ID, TelemetryProperty.GAME_VERSION, TelemetryProperty.OPERATING_SYSTEM, TelemetryProperty.PLATFORM, TelemetryProperty.CLIENT_MODDED, TelemetryProperty.LAUNCHER_NAME, TelemetryProperty.EVENT_TIMESTAMP_UTC, TelemetryProperty.OPT_IN);
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
/*  43 */   private static final List<TelemetryProperty<?>> WORLD_SESSION_PROPERTIES = Stream.<TelemetryProperty<?>>concat(GLOBAL_PROPERTIES.stream(), Stream.of((TelemetryProperty<?>[])new TelemetryProperty[] { TelemetryProperty.WORLD_SESSION_ID, TelemetryProperty.SERVER_MODDED, TelemetryProperty.SERVER_TYPE
/*     */ 
/*     */ 
/*     */         
/*  47 */         })).toList();
/*     */   
/*  49 */   public static final TelemetryEventType WORLD_LOADED = builder("world_loaded", "WorldLoaded")
/*  50 */     .defineAll(WORLD_SESSION_PROPERTIES)
/*  51 */     .<TelemetryProperty.GameMode>define(TelemetryProperty.GAME_MODE)
/*  52 */     .<String>define(TelemetryProperty.REALMS_MAP_CONTENT)
/*  53 */     .register();
/*     */   
/*  55 */   public static final TelemetryEventType PERFORMANCE_METRICS = builder("performance_metrics", "PerformanceMetrics")
/*  56 */     .defineAll(WORLD_SESSION_PROPERTIES)
/*  57 */     .<LongList>define(TelemetryProperty.FRAME_RATE_SAMPLES)
/*  58 */     .<LongList>define(TelemetryProperty.RENDER_TIME_SAMPLES)
/*  59 */     .<LongList>define(TelemetryProperty.USED_MEMORY_SAMPLES)
/*  60 */     .<Integer>define(TelemetryProperty.NUMBER_OF_SAMPLES)
/*  61 */     .<Integer>define(TelemetryProperty.RENDER_DISTANCE)
/*  62 */     .<Integer>define(TelemetryProperty.DEDICATED_MEMORY_KB)
/*  63 */     .optIn()
/*  64 */     .register();
/*     */   
/*  66 */   public static final TelemetryEventType WORLD_LOAD_TIMES = builder("world_load_times", "WorldLoadTimes")
/*  67 */     .defineAll(WORLD_SESSION_PROPERTIES)
/*  68 */     .<Integer>define(TelemetryProperty.WORLD_LOAD_TIME_MS)
/*  69 */     .<Boolean>define(TelemetryProperty.NEW_WORLD)
/*  70 */     .optIn()
/*  71 */     .register();
/*     */   
/*  73 */   public static final TelemetryEventType WORLD_UNLOADED = builder("world_unloaded", "WorldUnloaded")
/*  74 */     .defineAll(WORLD_SESSION_PROPERTIES)
/*  75 */     .<Integer>define(TelemetryProperty.SECONDS_SINCE_LOAD)
/*  76 */     .<Integer>define(TelemetryProperty.TICKS_SINCE_LOAD)
/*  77 */     .register();
/*     */   
/*  79 */   public static final TelemetryEventType ADVANCEMENT_MADE = builder("advancement_made", "AdvancementMade")
/*  80 */     .defineAll(WORLD_SESSION_PROPERTIES)
/*  81 */     .<String>define(TelemetryProperty.ADVANCEMENT_ID)
/*  82 */     .<Long>define(TelemetryProperty.ADVANCEMENT_GAME_TIME)
/*  83 */     .optIn()
/*  84 */     .register();
/*     */   
/*  86 */   public static final TelemetryEventType GAME_LOAD_TIMES = builder("game_load_times", "GameLoadTimes")
/*  87 */     .defineAll(GLOBAL_PROPERTIES)
/*  88 */     .<GameLoadTimesEvent.Measurement>define(TelemetryProperty.LOAD_TIME_TOTAL_TIME_MS)
/*  89 */     .<GameLoadTimesEvent.Measurement>define(TelemetryProperty.LOAD_TIME_PRE_WINDOW_MS)
/*  90 */     .<GameLoadTimesEvent.Measurement>define(TelemetryProperty.LOAD_TIME_BOOTSTRAP_MS)
/*  91 */     .<GameLoadTimesEvent.Measurement>define(TelemetryProperty.LOAD_TIME_LOADING_OVERLAY_MS)
/*  92 */     .optIn()
/*  93 */     .register();
/*     */   
/*     */   private final String id;
/*     */   
/*     */   private final String exportKey;
/*     */   private final List<TelemetryProperty<?>> properties;
/*     */   private final boolean isOptIn;
/*     */   private final Codec<TelemetryEventInstance> codec;
/*     */   
/*     */   TelemetryEventType(String $$0, String $$1, List<TelemetryProperty<?>> $$2, boolean $$3) {
/* 103 */     this.id = $$0;
/* 104 */     this.exportKey = $$1;
/* 105 */     this.properties = $$2;
/* 106 */     this.isOptIn = $$3;
/* 107 */     this.codec = TelemetryPropertyMap.createCodec($$2).xmap($$0 -> new TelemetryEventInstance(this, $$0), TelemetryEventInstance::properties);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder builder(String $$0, String $$1) {
/* 114 */     return new Builder($$0, $$1);
/*     */   }
/*     */   
/*     */   public String id() {
/* 118 */     return this.id;
/*     */   }
/*     */   
/*     */   public List<TelemetryProperty<?>> properties() {
/* 122 */     return this.properties;
/*     */   }
/*     */   
/*     */   public Codec<TelemetryEventInstance> codec() {
/* 126 */     return this.codec;
/*     */   }
/*     */   
/*     */   public boolean isOptIn() {
/* 130 */     return this.isOptIn;
/*     */   }
/*     */   
/*     */   public TelemetryEvent export(TelemetrySession $$0, TelemetryPropertyMap $$1) {
/* 134 */     TelemetryEvent $$2 = $$0.createNewEvent(this.exportKey);
/* 135 */     for (TelemetryProperty<?> $$3 : this.properties) {
/* 136 */       $$3.export($$1, (TelemetryPropertyContainer)$$2);
/*     */     }
/* 138 */     return $$2;
/*     */   }
/*     */   
/*     */   public <T> boolean contains(TelemetryProperty<T> $$0) {
/* 142 */     return this.properties.contains($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 147 */     return "TelemetryEventType[" + this.id + "]";
/*     */   }
/*     */   
/*     */   public MutableComponent title() {
/* 151 */     return makeTranslation("title");
/*     */   }
/*     */   
/*     */   public MutableComponent description() {
/* 155 */     return makeTranslation("description");
/*     */   }
/*     */   
/*     */   private MutableComponent makeTranslation(String $$0) {
/* 159 */     return Component.translatable("telemetry.event." + this.id + "." + $$0);
/*     */   }
/*     */   
/*     */   public static List<TelemetryEventType> values() {
/* 163 */     return List.copyOf(REGISTRY.values());
/*     */   }
/*     */   
/*     */   public static class Builder {
/*     */     private final String id;
/*     */     private final String exportKey;
/* 169 */     private final List<TelemetryProperty<?>> properties = new ArrayList<>();
/*     */     private boolean isOptIn;
/*     */     
/*     */     Builder(String $$0, String $$1) {
/* 173 */       this.id = $$0;
/* 174 */       this.exportKey = $$1;
/*     */     }
/*     */     
/*     */     public Builder defineAll(List<TelemetryProperty<?>> $$0) {
/* 178 */       this.properties.addAll($$0);
/* 179 */       return this;
/*     */     }
/*     */     
/*     */     public <T> Builder define(TelemetryProperty<T> $$0) {
/* 183 */       this.properties.add($$0);
/* 184 */       return this;
/*     */     }
/*     */     
/*     */     public Builder optIn() {
/* 188 */       this.isOptIn = true;
/* 189 */       return this;
/*     */     }
/*     */     
/*     */     public TelemetryEventType register() {
/* 193 */       TelemetryEventType $$0 = new TelemetryEventType(this.id, this.exportKey, List.copyOf(this.properties), this.isOptIn);
/* 194 */       if (TelemetryEventType.REGISTRY.putIfAbsent(this.id, $$0) != null) {
/* 195 */         throw new IllegalStateException("Duplicate TelemetryEventType with key: '" + this.id + "'");
/*     */       }
/* 197 */       return $$0;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\TelemetryEventType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */