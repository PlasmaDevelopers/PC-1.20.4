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
/*    */ class null
/*    */   implements TagType<EndTag>
/*    */ {
/*    */   public EndTag load(DataInput $$0, NbtAccounter $$1) {
/* 15 */     $$1.accountBytes(8L);
/* 16 */     return EndTag.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) {
/* 21 */     $$2.accountBytes(8L);
/* 22 */     return $$1.visitEnd();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, int $$1, NbtAccounter $$2) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, NbtAccounter $$1) {}
/*    */ 
/*    */   
/*    */   public String getName() {
/* 35 */     return "END";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 40 */     return "TAG_End";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\EndTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */