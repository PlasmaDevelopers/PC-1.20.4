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
/*    */   implements TagType<EndTag>
/*    */ {
/*    */   private IOException createException() {
/* 58 */     return new IOException("Invalid tag id: " + id);
/*    */   }
/*    */ 
/*    */   
/*    */   public EndTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 63 */     throw createException();
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 68 */     throw createException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, int $$1, NbtAccounter $$2) throws IOException {
/* 73 */     throw createException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 78 */     throw createException();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 83 */     return "INVALID[" + id + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 88 */     return "UNKNOWN_" + id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TagType$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */