/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class DelegateDataOutput implements DataOutput {
/*    */   private final DataOutput parent;
/*    */   
/*    */   public DelegateDataOutput(DataOutput $$0) {
/* 10 */     this.parent = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(int $$0) throws IOException {
/* 15 */     this.parent.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] $$0) throws IOException {
/* 20 */     this.parent.write($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(byte[] $$0, int $$1, int $$2) throws IOException {
/* 25 */     this.parent.write($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBoolean(boolean $$0) throws IOException {
/* 30 */     this.parent.writeBoolean($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeByte(int $$0) throws IOException {
/* 35 */     this.parent.writeByte($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeShort(int $$0) throws IOException {
/* 40 */     this.parent.writeShort($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeChar(int $$0) throws IOException {
/* 45 */     this.parent.writeChar($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeInt(int $$0) throws IOException {
/* 50 */     this.parent.writeInt($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeLong(long $$0) throws IOException {
/* 55 */     this.parent.writeLong($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeFloat(float $$0) throws IOException {
/* 60 */     this.parent.writeFloat($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeDouble(double $$0) throws IOException {
/* 65 */     this.parent.writeDouble($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeBytes(String $$0) throws IOException {
/* 70 */     this.parent.writeBytes($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeChars(String $$0) throws IOException {
/* 75 */     this.parent.writeChars($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeUTF(String $$0) throws IOException {
/* 80 */     this.parent.writeUTF($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\DelegateDataOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */