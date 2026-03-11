/*     */ package net.minecraft.client.telemetry;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.mojang.authlib.minecraft.TelemetrySession;
/*     */ import com.mojang.authlib.minecraft.UserApiService;
/*     */ import java.nio.file.Path;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.User;
/*     */ 
/*     */ public class ClientTelemetryManager implements AutoCloseable {
/*  24 */   private static final AtomicInteger THREAD_COUNT = new AtomicInteger(1); static {
/*  25 */     EXECUTOR = Executors.newSingleThreadExecutor($$0 -> {
/*     */           Thread $$1 = new Thread($$0);
/*     */           $$1.setName("Telemetry-Sender-#" + THREAD_COUNT.getAndIncrement());
/*     */           return $$1;
/*     */         });
/*     */   }
/*     */   private static final Executor EXECUTOR;
/*     */   private final Minecraft minecraft;
/*     */   private final UserApiService userApiService;
/*     */   private final TelemetryPropertyMap deviceSessionProperties;
/*     */   private final Path logDirectory;
/*     */   private final CompletableFuture<Optional<TelemetryLogManager>> logManager;
/*  37 */   private final Supplier<TelemetryEventSender> outsideSessionSender = (Supplier<TelemetryEventSender>)Suppliers.memoize(this::createEventSender);
/*     */   
/*     */   public ClientTelemetryManager(Minecraft $$0, UserApiService $$1, User $$2) {
/*  40 */     this.minecraft = $$0;
/*  41 */     this.userApiService = $$1;
/*     */     
/*  43 */     TelemetryPropertyMap.Builder $$3 = TelemetryPropertyMap.builder();
/*  44 */     $$2.getXuid().ifPresent($$1 -> $$0.put(TelemetryProperty.USER_ID, $$1));
/*  45 */     $$2.getClientId().ifPresent($$1 -> $$0.put(TelemetryProperty.CLIENT_ID, $$1));
/*  46 */     $$3.put(TelemetryProperty.MINECRAFT_SESSION_ID, UUID.randomUUID());
/*  47 */     $$3.put(TelemetryProperty.GAME_VERSION, SharedConstants.getCurrentVersion().getId());
/*  48 */     $$3.put(TelemetryProperty.OPERATING_SYSTEM, Util.getPlatform().telemetryName());
/*  49 */     $$3.put(TelemetryProperty.PLATFORM, System.getProperty("os.name"));
/*  50 */     $$3.put(TelemetryProperty.CLIENT_MODDED, Boolean.valueOf(Minecraft.checkModStatus().shouldReportAsModified()));
/*  51 */     $$3.putIfNotNull(TelemetryProperty.LAUNCHER_NAME, Minecraft.getLauncherBrand());
/*  52 */     this.deviceSessionProperties = $$3.build();
/*     */     
/*  54 */     this.logDirectory = $$0.gameDirectory.toPath().resolve("logs/telemetry");
/*  55 */     this.logManager = TelemetryLogManager.open(this.logDirectory);
/*     */   }
/*     */   
/*     */   public WorldSessionTelemetryManager createWorldSessionManager(boolean $$0, @Nullable Duration $$1, @Nullable String $$2) {
/*  59 */     return new WorldSessionTelemetryManager(createEventSender(), $$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public TelemetryEventSender getOutsideSessionSender() {
/*  63 */     return this.outsideSessionSender.get();
/*     */   }
/*     */   
/*     */   private TelemetryEventSender createEventSender() {
/*  67 */     if (!this.minecraft.allowsTelemetry()) {
/*  68 */       return TelemetryEventSender.DISABLED;
/*     */     }
/*     */     
/*  71 */     TelemetrySession $$0 = this.userApiService.newTelemetrySession(EXECUTOR);
/*  72 */     if (!$$0.isEnabled()) {
/*  73 */       return TelemetryEventSender.DISABLED;
/*     */     }
/*     */     
/*  76 */     CompletableFuture<Optional<TelemetryEventLogger>> $$1 = this.logManager.thenCompose($$0 -> (CompletionStage)$$0.map(TelemetryLogManager::openLogger).orElseGet(()));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     return ($$2, $$3) -> {
/*     */         if ($$2.isOptIn() && !Minecraft.getInstance().telemetryOptInExtra()) {
/*     */           return;
/*     */         }
/*     */         TelemetryPropertyMap.Builder $$4 = TelemetryPropertyMap.builder();
/*     */         $$4.putAll(this.deviceSessionProperties);
/*     */         $$4.put(TelemetryProperty.EVENT_TIMESTAMP_UTC, Instant.now());
/*     */         $$4.put(TelemetryProperty.OPT_IN, Boolean.valueOf($$2.isOptIn()));
/*     */         $$3.accept($$4);
/*     */         TelemetryEventInstance $$5 = new TelemetryEventInstance($$2, $$4.build());
/*     */         $$0.thenAccept(());
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path getLogDirectory() {
/* 109 */     return this.logDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 114 */     this.logManager.thenAccept($$0 -> $$0.ifPresent(TelemetryLogManager::close));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\telemetry\ClientTelemetryManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */