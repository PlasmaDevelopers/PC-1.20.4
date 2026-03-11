/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.IOException;
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
/*    */ class null
/*    */   implements TagType.VariableSize<ByteArrayTag>
/*    */ {
/*    */   public ByteArrayTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 23 */     return new ByteArrayTag(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 28 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static byte[] readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 32 */     $$1.accountBytes(24L);
/* 33 */     int $$2 = $$0.readInt();
/* 34 */     $$1.accountBytes(1L, $$2);
/* 35 */     byte[] $$3 = new byte[$$2];
/* 36 */     $$0.readFully($$3);
/* 37 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 42 */     $$0.skipBytes($$0.readInt() * 1);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 47 */     return "BYTE[]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 52 */     return "TAG_Byte_Array";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ByteArrayTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */