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
/*    */   implements TagType.StaticSize<ShortTag>
/*    */ {
/*    */   public ShortTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 30 */     return ShortTag.valueOf(readAccounted($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 35 */     return $$1.visit(readAccounted($$0, $$2));
/*    */   }
/*    */   
/*    */   private static short readAccounted(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 39 */     $$1.accountBytes(10L);
/* 40 */     return $$0.readShort();
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 45 */     return 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 50 */     return "SHORT";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPrettyName() {
/* 55 */     return "TAG_Short";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValue() {
/* 60 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\ShortTag$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */