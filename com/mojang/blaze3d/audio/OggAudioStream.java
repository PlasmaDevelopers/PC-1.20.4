/*     */ package com.mojang.blaze3d.audio;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Objects;
/*     */ import javax.sound.sampled.AudioFormat;
/*     */ import net.minecraft.client.sounds.AudioStream;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.PointerBuffer;
/*     */ import org.lwjgl.stb.STBVorbis;
/*     */ import org.lwjgl.stb.STBVorbisInfo;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ 
/*     */ public class OggAudioStream implements AudioStream {
/*     */   private static final int EXPECTED_MAX_FRAME_SIZE = 8192;
/*     */   private long handle;
/*     */   private final AudioFormat audioFormat;
/*     */   private final InputStream input;
/*     */   
/*     */   private static class OutputConcat {
/*  26 */     private final List<ByteBuffer> buffers = Lists.newArrayList();
/*     */     
/*     */     private final int bufferSize;
/*     */     int byteCount;
/*     */     private ByteBuffer currentBuffer;
/*     */     
/*     */     public OutputConcat(int $$0) {
/*  33 */       this.bufferSize = $$0 + 1 & 0xFFFFFFFE;
/*  34 */       createNewBuffer();
/*     */     }
/*     */     
/*     */     private void createNewBuffer() {
/*  38 */       this.currentBuffer = BufferUtils.createByteBuffer(this.bufferSize);
/*     */     }
/*     */     
/*     */     public void put(float $$0) {
/*  42 */       if (this.currentBuffer.remaining() == 0) {
/*  43 */         this.currentBuffer.flip();
/*  44 */         this.buffers.add(this.currentBuffer);
/*  45 */         createNewBuffer();
/*     */       } 
/*     */       
/*  48 */       int $$1 = Mth.clamp((int)($$0 * 32767.5F - 0.5F), -32768, 32767);
/*  49 */       this.currentBuffer.putShort((short)$$1);
/*  50 */       this.byteCount += 2;
/*     */     }
/*     */     
/*     */     public ByteBuffer get() {
/*  54 */       this.currentBuffer.flip();
/*     */       
/*  56 */       if (this.buffers.isEmpty()) {
/*  57 */         return this.currentBuffer;
/*     */       }
/*     */       
/*  60 */       ByteBuffer $$0 = BufferUtils.createByteBuffer(this.byteCount);
/*  61 */       Objects.requireNonNull($$0); this.buffers.forEach($$0::put);
/*  62 */       $$0.put(this.currentBuffer);
/*  63 */       $$0.flip();
/*  64 */       return $$0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  71 */   private ByteBuffer buffer = MemoryUtil.memAlloc(8192);
/*     */   
/*     */   public OggAudioStream(InputStream $$0) throws IOException {
/*  74 */     this.input = $$0;
/*     */     
/*  76 */     this.buffer.limit(0);
/*  77 */     MemoryStack $$1 = MemoryStack.stackPush(); 
/*  78 */     try { IntBuffer $$2 = $$1.mallocInt(1);
/*  79 */       IntBuffer $$3 = $$1.mallocInt(1);
/*  80 */       while (this.handle == 0L) {
/*  81 */         if (!refillFromStream()) {
/*  82 */           throw new IOException("Failed to find Ogg header");
/*     */         }
/*  84 */         int $$4 = this.buffer.position();
/*  85 */         this.buffer.position(0);
/*  86 */         this.handle = STBVorbis.stb_vorbis_open_pushdata(this.buffer, $$2, $$3, null);
/*  87 */         this.buffer.position($$4);
/*  88 */         int $$5 = $$3.get(0);
/*     */         
/*  90 */         if ($$5 == 1) {
/*  91 */           forwardBuffer(); continue;
/*  92 */         }  if ($$5 != 0) {
/*  93 */           throw new IOException("Failed to read Ogg file " + $$5);
/*     */         }
/*     */       } 
/*  96 */       this.buffer.position(this.buffer.position() + $$2.get(0));
/*     */       
/*  98 */       STBVorbisInfo $$6 = STBVorbisInfo.mallocStack($$1);
/*     */       
/* 100 */       STBVorbis.stb_vorbis_get_info(this.handle, $$6);
/* 101 */       this.audioFormat = new AudioFormat($$6.sample_rate(), 16, $$6.channels(), true, false);
/* 102 */       if ($$1 != null) $$1.close();  } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 106 */      } private boolean refillFromStream() throws IOException { int $$0 = this.buffer.limit();
/* 107 */     int $$1 = this.buffer.capacity() - $$0;
/* 108 */     if ($$1 == 0) {
/* 109 */       return true;
/*     */     }
/*     */     
/* 112 */     byte[] $$2 = new byte[$$1];
/* 113 */     int $$3 = this.input.read($$2);
/* 114 */     if ($$3 == -1) {
/* 115 */       return false;
/*     */     }
/* 117 */     int $$4 = this.buffer.position();
/* 118 */     this.buffer.limit($$0 + $$3);
/* 119 */     this.buffer.position($$0);
/* 120 */     this.buffer.put($$2, 0, $$3);
/* 121 */     this.buffer.position($$4);
/* 122 */     return true; }
/*     */ 
/*     */ 
/*     */   
/*     */   private void forwardBuffer() {
/* 127 */     boolean $$0 = (this.buffer.position() == 0);
/* 128 */     boolean $$1 = (this.buffer.position() == this.buffer.limit());
/*     */     
/* 130 */     if ($$1 && !$$0) {
/* 131 */       this.buffer.position(0);
/* 132 */       this.buffer.limit(0);
/*     */     } else {
/* 134 */       ByteBuffer $$2 = MemoryUtil.memAlloc($$0 ? (2 * this.buffer.capacity()) : this.buffer.capacity());
/* 135 */       $$2.put(this.buffer);
/* 136 */       MemoryUtil.memFree(this.buffer);
/*     */       
/* 138 */       $$2.flip();
/* 139 */       this.buffer = $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean readFrame(OutputConcat $$0) throws IOException {
/* 144 */     if (this.handle == 0L) {
/* 145 */       return false;
/*     */     }
/*     */     
/* 148 */     MemoryStack $$1 = MemoryStack.stackPush(); 
/* 149 */     try { PointerBuffer $$2 = $$1.mallocPointer(1);
/* 150 */       IntBuffer $$3 = $$1.mallocInt(1);
/* 151 */       IntBuffer $$4 = $$1.mallocInt(1);
/*     */       
/*     */       while (true)
/* 154 */       { int $$5 = STBVorbis.stb_vorbis_decode_frame_pushdata(this.handle, this.buffer, $$3, $$2, $$4);
/* 155 */         this.buffer.position(this.buffer.position() + $$5);
/*     */         
/* 157 */         int $$6 = STBVorbis.stb_vorbis_get_error(this.handle);
/* 158 */         if ($$6 == 1) {
/* 159 */           forwardBuffer();
/* 160 */           if (!refillFromStream()) {
/*     */             break;
/*     */           }
/*     */           
/*     */           continue;
/*     */         } 
/* 166 */         if ($$6 != 0) {
/* 167 */           throw new IOException("Failed to read Ogg file " + $$6);
/*     */         }
/*     */         
/* 170 */         int $$7 = $$4.get(0);
/* 171 */         if ($$7 == 0) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 176 */         int $$8 = $$3.get(0);
/* 177 */         PointerBuffer $$9 = $$2.getPointerBuffer($$8);
/* 178 */         if ($$8 == 1)
/* 179 */         { convertMono($$9.getFloatBuffer(0, $$7), $$0);
/* 180 */           boolean bool1 = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 190 */           if ($$1 != null) $$1.close();  return bool1; }  if ($$8 == 2) { convertStereo($$9.getFloatBuffer(0, $$7), $$9.getFloatBuffer(1, $$7), $$0); boolean bool1 = true; if ($$1 != null) $$1.close();  return bool1; }  throw new IllegalStateException("Invalid number of channels: " + $$8); }  boolean bool = false; if ($$1 != null) $$1.close();  return bool; } catch (Throwable throwable) { if ($$1 != null)
/*     */         try { $$1.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 194 */      } private void convertMono(FloatBuffer $$0, OutputConcat $$1) { while ($$0.hasRemaining()) {
/* 195 */       $$1.put($$0.get());
/*     */     } }
/*     */ 
/*     */   
/*     */   private void convertStereo(FloatBuffer $$0, FloatBuffer $$1, OutputConcat $$2) {
/* 200 */     while ($$0.hasRemaining() && $$1.hasRemaining()) {
/* 201 */       $$2.put($$0.get());
/* 202 */       $$2.put($$1.get());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 208 */     if (this.handle != 0L) {
/* 209 */       STBVorbis.stb_vorbis_close(this.handle);
/* 210 */       this.handle = 0L;
/*     */     } 
/* 212 */     MemoryUtil.memFree(this.buffer);
/* 213 */     this.input.close();
/*     */   }
/*     */ 
/*     */   
/*     */   public AudioFormat getFormat() {
/* 218 */     return this.audioFormat;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer read(int $$0) throws IOException {
/* 223 */     OutputConcat $$1 = new OutputConcat($$0 + 8192);
/* 224 */     while (readFrame($$1) && $$1.byteCount < $$0);
/*     */ 
/*     */     
/* 227 */     return $$1.get();
/*     */   }
/*     */   
/*     */   public ByteBuffer readAll() throws IOException {
/* 231 */     OutputConcat $$0 = new OutputConcat(16384);
/* 232 */     while (readFrame($$0));
/*     */ 
/*     */     
/* 235 */     return $$0.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\OggAudioStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */