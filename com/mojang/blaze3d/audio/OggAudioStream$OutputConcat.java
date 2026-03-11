/*    */ package com.mojang.blaze3d.audio;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.util.Mth;
/*    */ import org.lwjgl.BufferUtils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class OutputConcat
/*    */ {
/* 26 */   private final List<ByteBuffer> buffers = Lists.newArrayList();
/*    */   
/*    */   private final int bufferSize;
/*    */   int byteCount;
/*    */   private ByteBuffer currentBuffer;
/*    */   
/*    */   public OutputConcat(int $$0) {
/* 33 */     this.bufferSize = $$0 + 1 & 0xFFFFFFFE;
/* 34 */     createNewBuffer();
/*    */   }
/*    */   
/*    */   private void createNewBuffer() {
/* 38 */     this.currentBuffer = BufferUtils.createByteBuffer(this.bufferSize);
/*    */   }
/*    */   
/*    */   public void put(float $$0) {
/* 42 */     if (this.currentBuffer.remaining() == 0) {
/* 43 */       this.currentBuffer.flip();
/* 44 */       this.buffers.add(this.currentBuffer);
/* 45 */       createNewBuffer();
/*    */     } 
/*    */     
/* 48 */     int $$1 = Mth.clamp((int)($$0 * 32767.5F - 0.5F), -32768, 32767);
/* 49 */     this.currentBuffer.putShort((short)$$1);
/* 50 */     this.byteCount += 2;
/*    */   }
/*    */   
/*    */   public ByteBuffer get() {
/* 54 */     this.currentBuffer.flip();
/*    */     
/* 56 */     if (this.buffers.isEmpty()) {
/* 57 */       return this.currentBuffer;
/*    */     }
/*    */     
/* 60 */     ByteBuffer $$0 = BufferUtils.createByteBuffer(this.byteCount);
/* 61 */     Objects.requireNonNull($$0); this.buffers.forEach($$0::put);
/* 62 */     $$0.put(this.currentBuffer);
/* 63 */     $$0.flip();
/* 64 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\OggAudioStream$OutputConcat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */