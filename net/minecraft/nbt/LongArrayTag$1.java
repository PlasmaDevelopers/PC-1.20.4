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
/*    */ class null
/*    */   implements TagType.VariableSize<LongArrayTag>
/*    */ {
/*    */   public LongArrayTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 24 */     return new LongArrayTag(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 29 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static long[] readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 33 */     $$1.accountBytes(24L);
/* 34 */     int $$2 = $$0.readInt();
/* 35 */     $$1.accountBytes(8L, $$2);
/* 36 */     long[] $$3 = new long[$$2];
/* 37 */     for (int $$4 = 0; $$4 < $$2; $$4++) {
/* 38 */       $$3[$$4] = $$0.readLong();
/*    */     }
/* 40 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 45 */     $$0.skipBytes($$0.readInt() * 8);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return "LONG[]";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 55 */     return "TAG_Long_Array";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\LongArrayTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */