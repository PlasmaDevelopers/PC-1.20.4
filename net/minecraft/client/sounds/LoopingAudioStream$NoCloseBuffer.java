/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class NoCloseBuffer
/*    */   extends FilterInputStream
/*    */ {
/*    */   NoCloseBuffer(InputStream $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\LoopingAudioStream$NoCloseBuffer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */