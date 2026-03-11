/*     */ package com.mojang.blaze3d.audio;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.OptionalLong;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.lwjgl.openal.AL;
/*     */ import org.lwjgl.openal.AL10;
/*     */ import org.lwjgl.openal.ALC;
/*     */ import org.lwjgl.openal.ALC10;
/*     */ import org.lwjgl.openal.ALC11;
/*     */ import org.lwjgl.openal.ALCCapabilities;
/*     */ import org.lwjgl.openal.ALCapabilities;
/*     */ import org.lwjgl.openal.ALUtil;
/*     */ import org.lwjgl.openal.SOFTHRTF;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Library
/*     */ {
/*     */   public enum Pool
/*     */   {
/*  35 */     STATIC,
/*  36 */     STREAMING;
/*     */   }
/*     */ 
/*     */   
/*  40 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int NO_DEVICE = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   private static final int DEFAULT_CHANNEL_COUNT = 30;
/*     */ 
/*     */   
/*     */   private long currentDevice;
/*     */ 
/*     */   
/*     */   private long context;
/*     */ 
/*     */   
/*     */   private boolean supportsDisconnections;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private String defaultDeviceName;
/*     */ 
/*     */ 
/*     */   
/*  65 */   private static final ChannelPool EMPTY = new ChannelPool()
/*     */     {
/*     */       @Nullable
/*     */       public Channel acquire() {
/*  69 */         return null;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean release(Channel $$0) {
/*  74 */         return false;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void cleanup() {}
/*     */ 
/*     */       
/*     */       public int getMaxCount() {
/*  83 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public int getUsedCount() {
/*  88 */         return 0;
/*     */       }
/*     */     };
/*     */   
/*     */   private static class CountingChannelPool implements ChannelPool {
/*     */     private final int limit;
/*  94 */     private final Set<Channel> activeChannels = Sets.newIdentityHashSet();
/*     */     
/*     */     public CountingChannelPool(int $$0) {
/*  97 */       this.limit = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     public Channel acquire() {
/* 103 */       if (this.activeChannels.size() >= this.limit) {
/* 104 */         if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 105 */           Library.LOGGER.warn("Maximum sound pool size {} reached", Integer.valueOf(this.limit));
/*     */         }
/* 107 */         return null;
/*     */       } 
/*     */       
/* 110 */       Channel $$0 = Channel.create();
/* 111 */       if ($$0 != null) {
/* 112 */         this.activeChannels.add($$0);
/*     */       }
/*     */       
/* 115 */       return $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean release(Channel $$0) {
/* 120 */       if (!this.activeChannels.remove($$0)) {
/* 121 */         return false;
/*     */       }
/* 123 */       $$0.destroy();
/* 124 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void cleanup() {
/* 129 */       this.activeChannels.forEach(Channel::destroy);
/* 130 */       this.activeChannels.clear();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxCount() {
/* 135 */       return this.limit;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getUsedCount() {
/* 140 */       return this.activeChannels.size();
/*     */     }
/*     */   }
/*     */   
/* 144 */   private ChannelPool staticChannels = EMPTY;
/* 145 */   private ChannelPool streamingChannels = EMPTY;
/*     */   
/* 147 */   private final Listener listener = new Listener();
/*     */   
/*     */   public Library() {
/* 150 */     this.defaultDeviceName = getDefaultDeviceName();
/*     */   }
/*     */   
/*     */   public void init(@Nullable String $$0, boolean $$1) {
/* 154 */     this.currentDevice = openDeviceOrFallback($$0);
/* 155 */     this.supportsDisconnections = false;
/*     */     
/* 157 */     ALCCapabilities $$2 = ALC.createCapabilities(this.currentDevice);
/* 158 */     if (OpenAlUtil.checkALCError(this.currentDevice, "Get capabilities")) {
/* 159 */       throw new IllegalStateException("Failed to get OpenAL capabilities");
/*     */     }
/*     */     
/* 162 */     if (!$$2.OpenALC11) {
/* 163 */       throw new IllegalStateException("OpenAL 1.1 not supported");
/*     */     }
/* 165 */     setHrtf(($$2.ALC_SOFT_HRTF && $$1));
/*     */     
/* 167 */     MemoryStack $$3 = MemoryStack.stackPush();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 173 */     try { IntBuffer $$4 = $$3.callocInt(3).put(6554).put(1).put(0).flip();
/* 174 */       this.context = ALC10.alcCreateContext(this.currentDevice, $$4);
/* 175 */       if ($$3 != null) $$3.close();  } catch (Throwable throwable) { if ($$3 != null)
/*     */         try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 177 */      if (OpenAlUtil.checkALCError(this.currentDevice, "Create context")) {
/* 178 */       throw new IllegalStateException("Unable to create OpenAL context");
/*     */     }
/*     */     
/* 181 */     ALC10.alcMakeContextCurrent(this.context);
/*     */     
/* 183 */     int $$5 = getChannelCount();
/* 184 */     int $$6 = Mth.clamp((int)Mth.sqrt($$5), 2, 8);
/* 185 */     int $$7 = Mth.clamp($$5 - $$6, 8, 255);
/*     */     
/* 187 */     this.staticChannels = new CountingChannelPool($$7);
/* 188 */     this.streamingChannels = new CountingChannelPool($$6);
/*     */     
/* 190 */     ALCapabilities $$8 = AL.createCapabilities($$2);
/* 191 */     OpenAlUtil.checkALError("Initialization");
/*     */     
/* 193 */     if (!$$8.AL_EXT_source_distance_model)
/*     */     {
/* 195 */       throw new IllegalStateException("AL_EXT_source_distance_model is not supported");
/*     */     }
/* 197 */     AL10.alEnable(512);
/*     */     
/* 199 */     if (!$$8.AL_EXT_LINEAR_DISTANCE) {
/* 200 */       throw new IllegalStateException("AL_EXT_LINEAR_DISTANCE is not supported");
/*     */     }
/* 202 */     OpenAlUtil.checkALError("Enable per-source distance models");
/* 203 */     LOGGER.info("OpenAL initialized on device {}", getCurrentDeviceName());
/*     */ 
/*     */ 
/*     */     
/* 207 */     this.supportsDisconnections = ALC10.alcIsExtensionPresent(this.currentDevice, "ALC_EXT_disconnect");
/*     */   }
/*     */   
/*     */   private void setHrtf(boolean $$0) {
/* 211 */     int $$1 = ALC10.alcGetInteger(this.currentDevice, 6548);
/* 212 */     if ($$1 > 0) {
/* 213 */       MemoryStack $$2 = MemoryStack.stackPush();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 218 */       try { IntBuffer $$3 = $$2.callocInt(10).put(6546).put($$0 ? 1 : 0).put(6550).put(0).put(0).flip();
/*     */         
/* 220 */         if (!SOFTHRTF.alcResetDeviceSOFT(this.currentDevice, $$3)) {
/* 221 */           LOGGER.warn("Failed to reset device: {}", ALC10.alcGetString(this.currentDevice, ALC10.alcGetError(this.currentDevice)));
/*     */         }
/* 223 */         if ($$2 != null) $$2.close();  } catch (Throwable throwable) { if ($$2 != null)
/*     */           try { $$2.close(); }
/*     */           catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */             throw throwable; }
/*     */     
/* 228 */     }  } private int getChannelCount() { MemoryStack $$0 = MemoryStack.stackPush(); 
/* 229 */     try { int $$1 = ALC10.alcGetInteger(this.currentDevice, 4098);
/* 230 */       if (OpenAlUtil.checkALCError(this.currentDevice, "Get attributes size")) {
/* 231 */         throw new IllegalStateException("Failed to get OpenAL attributes");
/*     */       }
/*     */       
/* 234 */       IntBuffer $$2 = $$0.mallocInt($$1);
/* 235 */       ALC10.alcGetIntegerv(this.currentDevice, 4099, $$2);
/* 236 */       if (OpenAlUtil.checkALCError(this.currentDevice, "Get attributes")) {
/* 237 */         throw new IllegalStateException("Failed to get OpenAL attributes");
/*     */       }
/*     */       
/* 240 */       int $$3 = 0;
/* 241 */       while ($$3 < $$1)
/* 242 */       { int $$4 = $$2.get($$3++);
/* 243 */         if ($$4 == 0) {
/*     */           break;
/*     */         }
/* 246 */         int $$5 = $$2.get($$3++);
/*     */         
/* 248 */         if ($$4 == 4112)
/* 249 */         { int i = $$5;
/*     */ 
/*     */           
/* 252 */           if ($$0 != null) $$0.close();  return i; }  }  if ($$0 != null) $$0.close();  } catch (Throwable throwable) { if ($$0 != null)
/* 253 */         try { $$0.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  return 30; }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String getDefaultDeviceName() {
/* 258 */     if (!ALC10.alcIsExtensionPresent(0L, "ALC_ENUMERATE_ALL_EXT")) {
/* 259 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 264 */     ALUtil.getStringList(0L, 4115);
/*     */     
/* 266 */     return ALC10.alcGetString(0L, 4114);
/*     */   }
/*     */   
/*     */   public String getCurrentDeviceName() {
/* 270 */     String $$0 = ALC10.alcGetString(this.currentDevice, 4115);
/* 271 */     if ($$0 == null) {
/* 272 */       $$0 = ALC10.alcGetString(this.currentDevice, 4101);
/*     */     }
/* 274 */     if ($$0 == null) {
/* 275 */       $$0 = "Unknown";
/*     */     }
/* 277 */     return $$0;
/*     */   }
/*     */   
/*     */   public synchronized boolean hasDefaultDeviceChanged() {
/* 281 */     String $$0 = getDefaultDeviceName();
/* 282 */     if (Objects.equals(this.defaultDeviceName, $$0)) {
/* 283 */       return false;
/*     */     }
/* 285 */     this.defaultDeviceName = $$0;
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static long openDeviceOrFallback(@Nullable String $$0) {
/* 291 */     OptionalLong $$1 = OptionalLong.empty();
/* 292 */     if ($$0 != null) {
/* 293 */       $$1 = tryOpenDevice($$0);
/*     */     }
/* 295 */     if ($$1.isEmpty()) {
/* 296 */       $$1 = tryOpenDevice(getDefaultDeviceName());
/*     */     }
/* 298 */     if ($$1.isEmpty()) {
/* 299 */       $$1 = tryOpenDevice(null);
/*     */     }
/* 301 */     if ($$1.isEmpty()) {
/* 302 */       throw new IllegalStateException("Failed to open OpenAL device");
/*     */     }
/*     */     
/* 305 */     return $$1.getAsLong();
/*     */   }
/*     */   
/*     */   private static OptionalLong tryOpenDevice(@Nullable String $$0) {
/* 309 */     long $$1 = ALC10.alcOpenDevice($$0);
/*     */     
/* 311 */     if ($$1 != 0L && !OpenAlUtil.checkALCError($$1, "Open device")) {
/* 312 */       return OptionalLong.of($$1);
/*     */     }
/*     */     
/* 315 */     return OptionalLong.empty();
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 319 */     this.staticChannels.cleanup();
/* 320 */     this.streamingChannels.cleanup();
/*     */     
/* 322 */     ALC10.alcDestroyContext(this.context);
/* 323 */     if (this.currentDevice != 0L) {
/* 324 */       ALC10.alcCloseDevice(this.currentDevice);
/*     */     }
/*     */   }
/*     */   
/*     */   public Listener getListener() {
/* 329 */     return this.listener;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Channel acquireChannel(Pool $$0) {
/* 334 */     return (($$0 == Pool.STREAMING) ? this.streamingChannels : this.staticChannels).acquire();
/*     */   }
/*     */   
/*     */   public void releaseChannel(Channel $$0) {
/* 338 */     if (!this.staticChannels.release($$0) && !this.streamingChannels.release($$0)) {
/* 339 */       throw new IllegalStateException("Tried to release unknown channel");
/*     */     }
/*     */   }
/*     */   
/*     */   public String getDebugString() {
/* 344 */     return String.format(Locale.ROOT, "Sounds: %d/%d + %d/%d", new Object[] { Integer.valueOf(this.staticChannels.getUsedCount()), Integer.valueOf(this.staticChannels.getMaxCount()), Integer.valueOf(this.streamingChannels.getUsedCount()), Integer.valueOf(this.streamingChannels.getMaxCount()) });
/*     */   }
/*     */   
/*     */   public List<String> getAvailableSoundDevices() {
/* 348 */     List<String> $$0 = ALUtil.getStringList(0L, 4115);
/* 349 */     if ($$0 == null) {
/* 350 */       return Collections.emptyList();
/*     */     }
/* 352 */     return $$0;
/*     */   }
/*     */   
/*     */   public boolean isCurrentDeviceDisconnected() {
/* 356 */     return (this.supportsDisconnections && ALC11.alcGetInteger(this.currentDevice, 787) == 0);
/*     */   }
/*     */   
/*     */   private static interface ChannelPool {
/*     */     @Nullable
/*     */     Channel acquire();
/*     */     
/*     */     boolean release(Channel param1Channel);
/*     */     
/*     */     void cleanup();
/*     */     
/*     */     int getMaxCount();
/*     */     
/*     */     int getUsedCount();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\Library.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */