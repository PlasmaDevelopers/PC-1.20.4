/*     */ package com.mojang.blaze3d.audio;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import net.minecraft.client.sounds.AudioStream;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.lwjgl.openal.AL10;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public class Channel
/*     */ {
/*  17 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int QUEUED_BUFFER_COUNT = 4;
/*     */   public static final int BUFFER_DURATION_SECONDS = 1;
/*     */   private final int source;
/*  22 */   private final AtomicBoolean initialized = new AtomicBoolean(true);
/*     */   
/*  24 */   private int streamingBufferSize = 16384;
/*     */   
/*     */   @Nullable
/*     */   private AudioStream stream;
/*     */   
/*     */   @Nullable
/*     */   static Channel create() {
/*  31 */     int[] $$0 = new int[1];
/*     */     
/*  33 */     AL10.alGenSources($$0);
/*  34 */     if (OpenAlUtil.checkALError("Allocate new source")) {
/*  35 */       return null;
/*     */     }
/*  37 */     return new Channel($$0[0]);
/*     */   }
/*     */   
/*     */   private Channel(int $$0) {
/*  41 */     this.source = $$0;
/*     */   }
/*     */   
/*     */   public void destroy() {
/*  45 */     if (this.initialized.compareAndSet(true, false)) {
/*  46 */       AL10.alSourceStop(this.source);
/*  47 */       OpenAlUtil.checkALError("Stop");
/*  48 */       if (this.stream != null) {
/*     */         try {
/*  50 */           this.stream.close();
/*  51 */         } catch (IOException $$0) {
/*  52 */           LOGGER.error("Failed to close audio stream", $$0);
/*     */         } 
/*  54 */         removeProcessedBuffers();
/*  55 */         this.stream = null;
/*     */       } 
/*     */       
/*  58 */       AL10.alDeleteSources(new int[] { this.source });
/*  59 */       OpenAlUtil.checkALError("Cleanup");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void play() {
/*  64 */     AL10.alSourcePlay(this.source);
/*     */   }
/*     */   
/*     */   private int getState() {
/*  68 */     if (!this.initialized.get()) {
/*  69 */       return 4116;
/*     */     }
/*  71 */     return AL10.alGetSourcei(this.source, 4112);
/*     */   }
/*     */   
/*     */   public void pause() {
/*  75 */     if (getState() == 4114) {
/*  76 */       AL10.alSourcePause(this.source);
/*     */     }
/*     */   }
/*     */   
/*     */   public void unpause() {
/*  81 */     if (getState() == 4115) {
/*  82 */       AL10.alSourcePlay(this.source);
/*     */     }
/*     */   }
/*     */   
/*     */   public void stop() {
/*  87 */     if (this.initialized.get()) {
/*  88 */       AL10.alSourceStop(this.source);
/*  89 */       OpenAlUtil.checkALError("Stop");
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean playing() {
/*  94 */     return (getState() == 4114);
/*     */   }
/*     */   
/*     */   public boolean stopped() {
/*  98 */     return (getState() == 4116);
/*     */   }
/*     */   
/*     */   public void setSelfPosition(Vec3 $$0) {
/* 102 */     AL10.alSourcefv(this.source, 4100, new float[] { (float)$$0.x, (float)$$0.y, (float)$$0.z });
/*     */   }
/*     */   
/*     */   public void setPitch(float $$0) {
/* 106 */     AL10.alSourcef(this.source, 4099, $$0);
/*     */   }
/*     */   
/*     */   public void setLooping(boolean $$0) {
/* 110 */     AL10.alSourcei(this.source, 4103, $$0 ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void setVolume(float $$0) {
/* 114 */     AL10.alSourcef(this.source, 4106, $$0);
/*     */   }
/*     */   
/*     */   public void disableAttenuation() {
/* 118 */     AL10.alSourcei(this.source, 53248, 0);
/*     */   }
/*     */   
/*     */   public void linearAttenuation(float $$0) {
/* 122 */     AL10.alSourcei(this.source, 53248, 53251);
/* 123 */     AL10.alSourcef(this.source, 4131, $$0);
/* 124 */     AL10.alSourcef(this.source, 4129, 1.0F);
/* 125 */     AL10.alSourcef(this.source, 4128, 0.0F);
/*     */   }
/*     */   
/*     */   public void setRelative(boolean $$0) {
/* 129 */     AL10.alSourcei(this.source, 514, $$0 ? 1 : 0);
/*     */   }
/*     */   
/*     */   public void attachStaticBuffer(SoundBuffer $$0) {
/* 133 */     $$0.getAlBuffer().ifPresent($$0 -> AL10.alSourcei(this.source, 4105, $$0));
/*     */   }
/*     */   
/*     */   public void attachBufferStream(AudioStream $$0) {
/* 137 */     this.stream = $$0;
/* 138 */     AudioFormat $$1 = $$0.getFormat();
/* 139 */     this.streamingBufferSize = calculateBufferSize($$1, 1);
/* 140 */     pumpBuffers(4);
/*     */   }
/*     */   
/*     */   private static int calculateBufferSize(AudioFormat $$0, int $$1) {
/* 144 */     return (int)(($$1 * $$0.getSampleSizeInBits()) / 8.0F * $$0.getChannels() * $$0.getSampleRate());
/*     */   }
/*     */   
/*     */   private void pumpBuffers(int $$0) {
/* 148 */     if (this.stream != null) {
/*     */       try {
/* 150 */         for (int $$1 = 0; $$1 < $$0; $$1++) {
/* 151 */           ByteBuffer $$2 = this.stream.read(this.streamingBufferSize);
/* 152 */           if ($$2 != null)
/*     */           {
/* 154 */             (new SoundBuffer($$2, this.stream.getFormat())).releaseAlBuffer().ifPresent($$0 -> AL10.alSourceQueueBuffers(this.source, new int[] { $$0 }));
/*     */           }
/*     */         } 
/* 157 */       } catch (IOException $$3) {
/* 158 */         LOGGER.error("Failed to read from audio stream", $$3);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateStream() {
/* 164 */     if (this.stream != null) {
/* 165 */       int $$0 = removeProcessedBuffers();
/* 166 */       pumpBuffers($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private int removeProcessedBuffers() {
/* 171 */     int $$0 = AL10.alGetSourcei(this.source, 4118);
/*     */     
/* 173 */     if ($$0 > 0) {
/* 174 */       int[] $$1 = new int[$$0];
/* 175 */       AL10.alSourceUnqueueBuffers(this.source, $$1);
/* 176 */       OpenAlUtil.checkALError("Unqueue buffers");
/* 177 */       AL10.alDeleteBuffers($$1);
/* 178 */       OpenAlUtil.checkALError("Remove processed buffers");
/*     */     } 
/*     */     
/* 181 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\Channel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */