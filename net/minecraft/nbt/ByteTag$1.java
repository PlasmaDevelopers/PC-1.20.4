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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements TagType.StaticSize<ByteTag>
/*    */ {
/*    */   public ByteTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 28 */     return ByteTag.valueOf(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 33 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static byte readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 37 */     $$1.accountBytes(9L);
/* 38 */     return $$0.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 43 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 48 */     return "BYTE";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 53 */     return "TAG_Byte";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ByteTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */