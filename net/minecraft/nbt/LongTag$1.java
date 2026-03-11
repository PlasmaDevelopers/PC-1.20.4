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
/*    */ class null
/*    */   implements TagType.StaticSize<LongTag>
/*    */ {
/*    */   public LongTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 30 */     return LongTag.valueOf(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 35 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static long readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 39 */     $$1.accountBytes(16L);
/* 40 */     return $$0.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return "LONG";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 55 */     return "TAG_Long";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\LongTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */