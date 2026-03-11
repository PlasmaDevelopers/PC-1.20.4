/*     */ package net.minecraft.client.sounds;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.audio.Channel;
/*     */ import com.mojang.blaze3d.audio.Library;
/*     */ import com.mojang.blaze3d.audio.Listener;
/*     */ import com.mojang.blaze3d.audio.ListenerTransform;
/*     */ import com.mojang.blaze3d.audio.SoundBuffer;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.resources.sounds.Sound;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.client.resources.sounds.TickableSoundInstance;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceProvider;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.Marker;
/*     */ import org.slf4j.MarkerFactory;
/*     */ 
/*     */ public class SoundEngine {
/*  41 */   private static final Marker MARKER = MarkerFactory.getMarker("SOUNDS");
/*  42 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final float PITCH_MIN = 0.5F;
/*     */   private static final float PITCH_MAX = 2.0F;
/*     */   private static final float VOLUME_MIN = 0.0F;
/*     */   private static final float VOLUME_MAX = 1.0F;
/*     */   private static final int MIN_SOURCE_LIFETIME = 20;
/*  48 */   private static final Set<ResourceLocation> ONLY_WARN_ONCE = Sets.newHashSet(); private static final long DEFAULT_DEVICE_CHECK_INTERVAL_MS = 1000L;
/*     */   public static final String MISSING_SOUND = "FOR THE DEBUG!";
/*     */   public static final String OPEN_AL_SOFT_PREFIX = "OpenAL Soft on ";
/*     */   
/*  52 */   private enum DeviceCheckState { ONGOING, CHANGE_DETECTED, NO_CHANGE; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  58 */   public static final int OPEN_AL_SOFT_PREFIX_LENGTH = "OpenAL Soft on ".length();
/*     */   
/*     */   private final SoundManager soundManager;
/*     */   
/*     */   private final Options options;
/*     */   
/*     */   private boolean loaded;
/*  65 */   private final Library library = new Library();
/*  66 */   private final Listener listener = this.library.getListener();
/*     */   
/*     */   private final SoundBufferLibrary soundBuffers;
/*  69 */   private final SoundEngineExecutor executor = new SoundEngineExecutor();
/*     */   
/*  71 */   private final ChannelAccess channelAccess = new ChannelAccess(this.library, (Executor)this.executor);
/*     */   
/*     */   private int tickCount;
/*     */   private long lastDeviceCheckTime;
/*  75 */   private final AtomicReference<DeviceCheckState> devicePoolState = new AtomicReference<>(DeviceCheckState.NO_CHANGE);
/*     */   
/*  77 */   private final Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel = Maps.newHashMap();
/*  78 */   private final Multimap<SoundSource, SoundInstance> instanceBySource = (Multimap<SoundSource, SoundInstance>)HashMultimap.create();
/*     */   
/*  80 */   private final List<TickableSoundInstance> tickingSounds = Lists.newArrayList();
/*  81 */   private final Map<SoundInstance, Integer> queuedSounds = Maps.newHashMap();
/*  82 */   private final Map<SoundInstance, Integer> soundDeleteTime = Maps.newHashMap();
/*  83 */   private final List<SoundEventListener> listeners = Lists.newArrayList();
/*  84 */   private final List<TickableSoundInstance> queuedTickableSounds = Lists.newArrayList();
/*     */   
/*  86 */   private final List<Sound> preloadQueue = Lists.newArrayList();
/*     */   
/*     */   public SoundEngine(SoundManager $$0, Options $$1, ResourceProvider $$2) {
/*  89 */     this.soundManager = $$0;
/*  90 */     this.options = $$1;
/*  91 */     this.soundBuffers = new SoundBufferLibrary($$2);
/*     */   }
/*     */   
/*     */   public void reload() {
/*  95 */     ONLY_WARN_ONCE.clear();
/*  96 */     for (SoundEvent $$0 : BuiltInRegistries.SOUND_EVENT) {
/*  97 */       if ($$0 != SoundEvents.EMPTY) {
/*  98 */         ResourceLocation $$1 = $$0.getLocation();
/*  99 */         if (this.soundManager.getSoundEvent($$1) == null) {
/* 100 */           LOGGER.warn("Missing sound for event: {}", BuiltInRegistries.SOUND_EVENT.getKey($$0));
/* 101 */           ONLY_WARN_ONCE.add($$1);
/*     */         } 
/*     */       } 
/*     */     } 
/* 105 */     destroy();
/* 106 */     loadLibrary();
/*     */   }
/*     */   
/*     */   private synchronized void loadLibrary() {
/* 110 */     if (this.loaded) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 115 */       String $$0 = (String)this.options.soundDevice().get();
/* 116 */       this.library.init("".equals($$0) ? null : $$0, ((Boolean)this.options.directionalAudio().get()).booleanValue());
/* 117 */       this.listener.reset();
/* 118 */       this.listener.setGain(this.options.getSoundSourceVolume(SoundSource.MASTER));
/* 119 */       Objects.requireNonNull(this.preloadQueue); this.soundBuffers.preload(this.preloadQueue).thenRun(this.preloadQueue::clear);
/* 120 */       this.loaded = true;
/* 121 */       LOGGER.info(MARKER, "Sound engine started");
/* 122 */     } catch (RuntimeException $$1) {
/* 123 */       LOGGER.error(MARKER, "Error starting SoundSystem. Turning off sounds & music", $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getVolume(@Nullable SoundSource $$0) {
/* 128 */     if ($$0 == null || $$0 == SoundSource.MASTER) {
/* 129 */       return 1.0F;
/*     */     }
/*     */     
/* 132 */     return this.options.getSoundSourceVolume($$0);
/*     */   }
/*     */   
/*     */   public void updateCategoryVolume(SoundSource $$0, float $$1) {
/* 136 */     if (!this.loaded) {
/*     */       return;
/*     */     }
/*     */     
/* 140 */     if ($$0 == SoundSource.MASTER) {
/* 141 */       this.listener.setGain($$1);
/*     */       
/*     */       return;
/*     */     } 
/* 145 */     this.instanceToChannel.forEach(($$0, $$1) -> {
/*     */           float $$2 = calculateVolume($$0);
/*     */           $$1.execute(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 159 */     if (this.loaded) {
/* 160 */       stopAll();
/* 161 */       this.soundBuffers.clear();
/* 162 */       this.library.cleanup();
/* 163 */       this.loaded = false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void emergencyShutdown() {
/* 168 */     if (this.loaded) {
/* 169 */       this.library.cleanup();
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop(SoundInstance $$0) {
/* 174 */     if (this.loaded) {
/* 175 */       ChannelAccess.ChannelHandle $$1 = this.instanceToChannel.get($$0);
/* 176 */       if ($$1 != null) {
/* 177 */         $$1.execute(Channel::stop);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void stopAll() {
/* 183 */     if (this.loaded) {
/* 184 */       this.executor.flush();
/* 185 */       this.instanceToChannel.values().forEach($$0 -> $$0.execute(Channel::stop));
/* 186 */       this.instanceToChannel.clear();
/* 187 */       this.channelAccess.clear();
/* 188 */       this.queuedSounds.clear();
/* 189 */       this.tickingSounds.clear();
/* 190 */       this.instanceBySource.clear();
/* 191 */       this.soundDeleteTime.clear();
/* 192 */       this.queuedTickableSounds.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addEventListener(SoundEventListener $$0) {
/* 197 */     this.listeners.add($$0);
/*     */   }
/*     */   
/*     */   public void removeEventListener(SoundEventListener $$0) {
/* 201 */     this.listeners.remove($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldChangeDevice() {
/* 210 */     if (this.library.isCurrentDeviceDisconnected()) {
/* 211 */       LOGGER.info("Audio device was lost!");
/* 212 */       return true;
/*     */     } 
/*     */     
/* 215 */     long $$0 = Util.getMillis();
/* 216 */     boolean $$1 = ($$0 - this.lastDeviceCheckTime >= 1000L);
/* 217 */     if ($$1) {
/* 218 */       this.lastDeviceCheckTime = $$0;
/*     */       
/* 220 */       if (this.devicePoolState.compareAndSet(DeviceCheckState.NO_CHANGE, DeviceCheckState.ONGOING)) {
/* 221 */         String $$2 = (String)this.options.soundDevice().get();
/* 222 */         Util.ioPool().execute(() -> {
/*     */               if ("".equals($$0)) {
/*     */                 if (this.library.hasDefaultDeviceChanged()) {
/*     */                   LOGGER.info("System default audio device has changed!");
/*     */                   
/*     */                   this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
/*     */                 } 
/*     */               } else if (!this.library.getCurrentDeviceName().equals($$0) && this.library.getAvailableSoundDevices().contains($$0)) {
/*     */                 LOGGER.info("Preferred audio device has become available!");
/*     */                 
/*     */                 this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.CHANGE_DETECTED);
/*     */               } 
/*     */               
/*     */               this.devicePoolState.compareAndSet(DeviceCheckState.ONGOING, DeviceCheckState.NO_CHANGE);
/*     */             });
/*     */       } 
/*     */     } 
/* 239 */     return this.devicePoolState.compareAndSet(DeviceCheckState.CHANGE_DETECTED, DeviceCheckState.NO_CHANGE);
/*     */   }
/*     */   
/*     */   public void tick(boolean $$0) {
/* 243 */     if (shouldChangeDevice()) {
/* 244 */       reload();
/*     */     }
/* 246 */     if (!$$0) {
/* 247 */       tickNonPaused();
/*     */     }
/* 249 */     this.channelAccess.scheduleTick();
/*     */   }
/*     */   
/*     */   private void tickNonPaused() {
/* 253 */     this.tickCount++;
/*     */     
/* 255 */     this.queuedTickableSounds.stream().filter(SoundInstance::canPlaySound).forEach(this::play);
/* 256 */     this.queuedTickableSounds.clear();
/*     */ 
/*     */     
/* 259 */     for (Iterator<TickableSoundInstance> iterator = this.tickingSounds.iterator(); iterator.hasNext(); ) { TickableSoundInstance $$0 = iterator.next();
/* 260 */       if (!$$0.canPlaySound()) {
/* 261 */         stop((SoundInstance)$$0);
/*     */       }
/* 263 */       $$0.tick();
/*     */       
/* 265 */       if ($$0.isStopped()) {
/* 266 */         stop((SoundInstance)$$0); continue;
/*     */       } 
/* 268 */       float $$1 = calculateVolume((SoundInstance)$$0);
/* 269 */       float $$2 = calculatePitch((SoundInstance)$$0);
/* 270 */       Vec3 $$3 = new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
/* 271 */       ChannelAccess.ChannelHandle $$4 = this.instanceToChannel.get($$0);
/* 272 */       if ($$4 != null) {
/* 273 */         $$4.execute($$3 -> {
/*     */               $$3.setVolume($$0);
/*     */               
/*     */               $$3.setPitch($$1);
/*     */               
/*     */               $$3.setSelfPosition($$2);
/*     */             });
/*     */       } }
/*     */ 
/*     */     
/* 283 */     Iterator<Map.Entry<SoundInstance, ChannelAccess.ChannelHandle>> $$5 = this.instanceToChannel.entrySet().iterator();
/* 284 */     while ($$5.hasNext()) {
/* 285 */       Map.Entry<SoundInstance, ChannelAccess.ChannelHandle> $$6 = $$5.next();
/*     */       
/* 287 */       ChannelAccess.ChannelHandle $$7 = $$6.getValue();
/* 288 */       SoundInstance $$8 = $$6.getKey();
/* 289 */       float $$9 = this.options.getSoundSourceVolume($$8.getSource());
/*     */       
/* 291 */       if ($$9 <= 0.0F) {
/* 292 */         $$7.execute(Channel::stop);
/* 293 */         $$5.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 297 */       if ($$7.isStopped()) {
/* 298 */         int $$10 = ((Integer)this.soundDeleteTime.get($$8)).intValue();
/* 299 */         if ($$10 <= this.tickCount) {
/* 300 */           if (shouldLoopManually($$8)) {
/* 301 */             this.queuedSounds.put($$8, Integer.valueOf(this.tickCount + $$8.getDelay()));
/*     */           }
/* 303 */           $$5.remove();
/* 304 */           LOGGER.debug(MARKER, "Removed channel {} because it's not playing anymore", $$7);
/* 305 */           this.soundDeleteTime.remove($$8);
/*     */           
/*     */           try {
/* 308 */             this.instanceBySource.remove($$8.getSource(), $$8);
/* 309 */           } catch (RuntimeException runtimeException) {}
/*     */ 
/*     */ 
/*     */           
/* 313 */           if ($$8 instanceof TickableSoundInstance) {
/* 314 */             this.tickingSounds.remove($$8);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 320 */     Iterator<Map.Entry<SoundInstance, Integer>> $$11 = this.queuedSounds.entrySet().iterator();
/* 321 */     while ($$11.hasNext()) {
/* 322 */       Map.Entry<SoundInstance, Integer> $$12 = $$11.next();
/*     */       
/* 324 */       if (this.tickCount >= ((Integer)$$12.getValue()).intValue()) {
/* 325 */         SoundInstance $$13 = $$12.getKey();
/*     */ 
/*     */         
/* 328 */         if ($$13 instanceof TickableSoundInstance) {
/* 329 */           ((TickableSoundInstance)$$13).tick();
/*     */         }
/*     */         
/* 332 */         play($$13);
/* 333 */         $$11.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean requiresManualLooping(SoundInstance $$0) {
/* 339 */     return ($$0.getDelay() > 0);
/*     */   }
/*     */   
/*     */   private static boolean shouldLoopManually(SoundInstance $$0) {
/* 343 */     return ($$0.isLooping() && requiresManualLooping($$0));
/*     */   }
/*     */   
/*     */   private static boolean shouldLoopAutomatically(SoundInstance $$0) {
/* 347 */     return ($$0.isLooping() && !requiresManualLooping($$0));
/*     */   }
/*     */   
/*     */   public boolean isActive(SoundInstance $$0) {
/* 351 */     if (!this.loaded) {
/* 352 */       return false;
/*     */     }
/*     */     
/* 355 */     if (this.soundDeleteTime.containsKey($$0) && ((Integer)this.soundDeleteTime.get($$0)).intValue() <= this.tickCount) {
/* 356 */       return true;
/*     */     }
/*     */     
/* 359 */     return this.instanceToChannel.containsKey($$0);
/*     */   }
/*     */   
/*     */   public void play(SoundInstance $$0) {
/* 363 */     if (!this.loaded) {
/*     */       return;
/*     */     }
/*     */     
/* 367 */     if (!$$0.canPlaySound()) {
/*     */       return;
/*     */     }
/*     */     
/* 371 */     WeighedSoundEvents $$1 = $$0.resolve(this.soundManager);
/* 372 */     ResourceLocation $$2 = $$0.getLocation();
/* 373 */     if ($$1 == null) {
/* 374 */       if (ONLY_WARN_ONCE.add($$2)) {
/* 375 */         LOGGER.warn(MARKER, "Unable to play unknown soundEvent: {}", $$2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */     
/* 384 */     Sound $$3 = $$0.getSound();
/*     */     
/* 386 */     if ($$3 == SoundManager.INTENTIONALLY_EMPTY_SOUND) {
/*     */       return;
/*     */     }
/*     */     
/* 390 */     if ($$3 == SoundManager.EMPTY_SOUND) {
/* 391 */       if (ONLY_WARN_ONCE.add($$2)) {
/* 392 */         LOGGER.warn(MARKER, "Unable to play empty soundEvent: {}", $$2);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 397 */     float $$4 = $$0.getVolume();
/* 398 */     float $$5 = Math.max($$4, 1.0F) * $$3.getAttenuationDistance();
/*     */     
/* 400 */     SoundSource $$6 = $$0.getSource();
/* 401 */     float $$7 = calculateVolume($$4, $$6);
/* 402 */     float $$8 = calculatePitch($$0);
/*     */     
/* 404 */     SoundInstance.Attenuation $$9 = $$0.getAttenuation();
/* 405 */     boolean $$10 = $$0.isRelative();
/*     */     
/* 407 */     if ($$7 == 0.0F && !$$0.canStartSilent()) {
/* 408 */       LOGGER.debug(MARKER, "Skipped playing sound {}, volume was zero.", $$3.getLocation());
/*     */       
/*     */       return;
/*     */     } 
/* 412 */     Vec3 $$11 = new Vec3($$0.getX(), $$0.getY(), $$0.getZ());
/*     */     
/* 414 */     if (!this.listeners.isEmpty()) {
/* 415 */       float $$12 = ($$10 || $$9 == SoundInstance.Attenuation.NONE) ? Float.POSITIVE_INFINITY : $$5;
/* 416 */       for (SoundEventListener $$13 : this.listeners) {
/* 417 */         $$13.onPlaySound($$0, $$1, $$12);
/*     */       }
/*     */     } 
/*     */     
/* 421 */     if (this.listener.getGain() <= 0.0F) {
/* 422 */       LOGGER.debug(MARKER, "Skipped playing soundEvent: {}, master volume was zero", $$2);
/*     */       
/*     */       return;
/*     */     } 
/* 426 */     boolean $$14 = shouldLoopAutomatically($$0);
/* 427 */     boolean $$15 = $$3.shouldStream();
/*     */     
/* 429 */     CompletableFuture<ChannelAccess.ChannelHandle> $$16 = this.channelAccess.createHandle($$3.shouldStream() ? Library.Pool.STREAMING : Library.Pool.STATIC);
/* 430 */     ChannelAccess.ChannelHandle $$17 = $$16.join();
/* 431 */     if ($$17 == null) {
/* 432 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 433 */         LOGGER.warn("Failed to create new sound handle");
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 438 */     LOGGER.debug(MARKER, "Playing sound {} for event {}", $$3.getLocation(), $$2);
/*     */     
/* 440 */     this.soundDeleteTime.put($$0, Integer.valueOf(this.tickCount + 20));
/* 441 */     this.instanceToChannel.put($$0, $$17);
/* 442 */     this.instanceBySource.put($$6, $$0);
/*     */     
/* 444 */     $$17.execute($$8 -> {
/*     */           $$8.setPitch($$0);
/*     */           
/*     */           $$8.setVolume($$1);
/*     */           if ($$2 == SoundInstance.Attenuation.LINEAR) {
/*     */             $$8.linearAttenuation($$3);
/*     */           } else {
/*     */             $$8.disableAttenuation();
/*     */           } 
/* 453 */           $$8.setLooping(($$4 && !$$5));
/*     */           
/*     */           $$8.setSelfPosition($$6);
/*     */           $$8.setRelative($$7);
/*     */         });
/* 458 */     if (!$$15) {
/* 459 */       this.soundBuffers.getCompleteBuffer($$3.getPath())
/* 460 */         .thenAccept($$1 -> $$0.execute(()));
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 466 */       this.soundBuffers.getStream($$3.getPath(), $$14)
/* 467 */         .thenAccept($$1 -> $$0.execute(()));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 474 */     if ($$0 instanceof TickableSoundInstance) {
/* 475 */       this.tickingSounds.add((TickableSoundInstance)$$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void queueTickingSound(TickableSoundInstance $$0) {
/* 480 */     this.queuedTickableSounds.add($$0);
/*     */   }
/*     */   
/*     */   public void requestPreload(Sound $$0) {
/* 484 */     this.preloadQueue.add($$0);
/*     */   }
/*     */   
/*     */   private float calculatePitch(SoundInstance $$0) {
/* 488 */     return Mth.clamp($$0.getPitch(), 0.5F, 2.0F);
/*     */   }
/*     */   
/*     */   private float calculateVolume(SoundInstance $$0) {
/* 492 */     return calculateVolume($$0.getVolume(), $$0.getSource());
/*     */   }
/*     */   
/*     */   private float calculateVolume(float $$0, SoundSource $$1) {
/* 496 */     return Mth.clamp($$0 * getVolume($$1), 0.0F, 1.0F);
/*     */   }
/*     */   
/*     */   public void pause() {
/* 500 */     if (this.loaded) {
/* 501 */       this.channelAccess.executeOnChannels($$0 -> $$0.forEach(Channel::pause));
/*     */     }
/*     */   }
/*     */   
/*     */   public void resume() {
/* 506 */     if (this.loaded) {
/* 507 */       this.channelAccess.executeOnChannels($$0 -> $$0.forEach(Channel::unpause));
/*     */     }
/*     */   }
/*     */   
/*     */   public void playDelayed(SoundInstance $$0, int $$1) {
/* 512 */     this.queuedSounds.put($$0, Integer.valueOf(this.tickCount + $$1));
/*     */   }
/*     */   
/*     */   public void updateSource(Camera $$0) {
/* 516 */     if (!this.loaded || !$$0.isInitialized()) {
/*     */       return;
/*     */     }
/*     */     
/* 520 */     ListenerTransform $$1 = new ListenerTransform($$0.getPosition(), new Vec3($$0.getLookVector()), new Vec3($$0.getUpVector()));
/* 521 */     this.executor.execute(() -> this.listener.setTransform($$0));
/*     */   }
/*     */   
/*     */   public void stop(@Nullable ResourceLocation $$0, @Nullable SoundSource $$1) {
/* 525 */     if ($$1 != null) {
/* 526 */       for (SoundInstance $$2 : this.instanceBySource.get($$1)) {
/* 527 */         if ($$0 == null || $$2.getLocation().equals($$0)) {
/* 528 */           stop($$2);
/*     */         }
/*     */       }
/*     */     
/* 532 */     } else if ($$0 == null) {
/* 533 */       stopAll();
/*     */     } else {
/* 535 */       for (SoundInstance $$3 : this.instanceToChannel.keySet()) {
/* 536 */         if ($$3.getLocation().equals($$0)) {
/* 537 */           stop($$3);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugString() {
/* 545 */     return this.library.getDebugString();
/*     */   }
/*     */   
/*     */   public List<String> getAvailableSoundDevices() {
/* 549 */     return this.library.getAvailableSoundDevices();
/*     */   }
/*     */   
/*     */   public ListenerTransform getListenerTransform() {
/* 553 */     return this.listener.getTransform();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundEngine.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */