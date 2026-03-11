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
/*    */ class null
/*    */   implements TagType.StaticSize<FloatTag>
/*    */ {
/*    */   public FloatTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 22 */     return FloatTag.valueOf(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 27 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static float readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 31 */     $$1.accountBytes(12L);
/* 32 */     return $$0.readFloat();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 37 */     return 4;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 42 */     return "FLOAT";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 47 */     return "TAG_Float";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 52 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\FloatTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */