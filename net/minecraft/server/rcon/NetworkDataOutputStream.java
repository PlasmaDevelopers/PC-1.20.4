/*    */ package net.minecraft.server.rcon;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class NetworkDataOutputStream {
/*    */   private final ByteArrayOutputStream outputStream;
/*    */   private final DataOutputStream dataOutputStream;
/*    */   
/*    */   public NetworkDataOutputStream(int $$0) {
/* 12 */     this.outputStream = new ByteArrayOutputStream($$0);
/* 13 */     this.dataOutputStream = new DataOutputStream(this.outputStream);
/*    */   }
/*    */   
/*    */   public void writeBytes(byte[] $$0) throws IOException {
/* 17 */     this.dataOutputStream.write($$0, 0, $$0.length);
/*    */   }
/*    */   
/*    */   public void writeString(String $$0) throws IOException {
/* 21 */     this.dataOutputStream.writeBytes($$0);
/* 22 */     this.dataOutputStream.write(0);
/*    */   }
/*    */   
/*    */   public void write(int $$0) throws IOException {
/* 26 */     this.dataOutputStream.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeShort(short $$0) throws IOException {
/* 31 */     this.dataOutputStream.writeShort(Short.reverseBytes($$0));
/*    */   }
/*    */   
/*    */   public void writeInt(int $$0) throws IOException {
/* 35 */     this.dataOutputStream.writeInt(Integer.reverseBytes($$0));
/*    */   }
/*    */   
/*    */   public void writeFloat(float $$0) throws IOException {
/* 39 */     this.dataOutputStream.writeInt(Integer.reverseBytes(Float.floatToIntBits($$0)));
/*    */   }
/*    */   
/*    */   public byte[] toByteArray() {
/* 43 */     return this.outputStream.toByteArray();
/*    */   }
/*    */   
/*    */   public void reset() {
/* 47 */     this.outputStream.reset();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\rcon\NetworkDataOutputStream.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */