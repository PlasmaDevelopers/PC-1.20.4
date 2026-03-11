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
/*    */ class null
/*    */   implements TagType.VariableSize<StringTag>
/*    */ {
/*    */   public StringTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 19 */     return StringTag.valueOf(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 24 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static String readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 28 */     $$1.accountBytes(36L);
/*    */ 
/*    */     
/* 31 */     String $$2 = $$0.readUTF();
/* 32 */     $$1.accountBytes(2L, $$2.length());
/* 33 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 38 */     StringTag.skipString($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return "STRING";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 48 */     return "TAG_String";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 53 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\StringTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */