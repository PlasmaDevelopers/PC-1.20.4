/*    */ package com.mojang.blaze3d.audio;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.OptionalInt;
/*    */ import javax.annotation.Nullable;
/*    */ import javax.sound.sampled.AudioFormat;
/*    */ import org.lwjgl.openal.AL10;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SoundBuffer
/*    */ {
/*    */   @Nullable
/*    */   private ByteBuffer data;
/*    */   private final AudioFormat format;
/*    */   private boolean hasAlBuffer;
/*    */   private int alBuffer;
/*    */   
/*    */   public SoundBuffer(ByteBuffer $$0, AudioFormat $$1) {
/* 20 */     this.data = $$0;
/* 21 */     this.format = $$1;
/*    */   }
/*    */   
/*    */   OptionalInt getAlBuffer() {
/* 25 */     if (!this.hasAlBuffer) {
/* 26 */       if (this.data == null) {
/* 27 */         return OptionalInt.empty();
/*    */       }
/* 29 */       int $$0 = OpenAlUtil.audioFormatToOpenAl(this.format);
/* 30 */       int[] $$1 = new int[1];
/* 31 */       AL10.alGenBuffers($$1);
/* 32 */       if (OpenAlUtil.checkALError("Creating buffer")) {
/* 33 */         return OptionalInt.empty();
/*    */       }
/* 35 */       AL10.alBufferData($$1[0], $$0, this.data, (int)this.format.getSampleRate());
/* 36 */       if (OpenAlUtil.checkALError("Assigning buffer data")) {
/* 37 */         return OptionalInt.empty();
/*    */       }
/* 39 */       this.alBuffer = $$1[0];
/* 40 */       this.hasAlBuffer = true;
/* 41 */       this.data = null;
/*    */     } 
/*    */     
/* 44 */     return OptionalInt.of(this.alBuffer);
/*    */   }
/*    */   
/*    */   public void discardAlBuffer() {
/* 48 */     if (this.hasAlBuffer) {
/* 49 */       AL10.alDeleteBuffers(new int[] { this.alBuffer });
/* 50 */       if (OpenAlUtil.checkALError("Deleting stream buffers")) {
/*    */         return;
/*    */       }
/*    */     } 
/* 54 */     this.hasAlBuffer = false;
/*    */   }
/*    */   
/*    */   public OptionalInt releaseAlBuffer() {
/* 58 */     OptionalInt $$0 = getAlBuffer();
/* 59 */     this.hasAlBuffer = false;
/* 60 */     return $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\SoundBuffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */