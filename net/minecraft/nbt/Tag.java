/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataOutput;
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
/*    */ public interface Tag
/*    */ {
/*    */   public static final int OBJECT_HEADER = 8;
/*    */   public static final int ARRAY_HEADER = 12;
/*    */   public static final int OBJECT_REFERENCE = 4;
/*    */   public static final int STRING_SIZE = 28;
/*    */   public static final byte TAG_END = 0;
/*    */   public static final byte TAG_BYTE = 1;
/*    */   public static final byte TAG_SHORT = 2;
/*    */   public static final byte TAG_INT = 3;
/*    */   public static final byte TAG_LONG = 4;
/*    */   public static final byte TAG_FLOAT = 5;
/*    */   public static final byte TAG_DOUBLE = 6;
/*    */   public static final byte TAG_BYTE_ARRAY = 7;
/*    */   public static final byte TAG_STRING = 8;
/*    */   public static final byte TAG_LIST = 9;
/*    */   public static final byte TAG_COMPOUND = 10;
/*    */   public static final byte TAG_INT_ARRAY = 11;
/*    */   public static final byte TAG_LONG_ARRAY = 12;
/*    */   public static final byte TAG_ANY_NUMERIC = 99;
/*    */   public static final int MAX_DEPTH = 512;
/*    */   
/*    */   void write(DataOutput paramDataOutput) throws IOException;
/*    */   
/*    */   String toString();
/*    */   
/*    */   byte getId();
/*    */   
/*    */   TagType<?> getType();
/*    */   
/*    */   Tag copy();
/*    */   
/*    */   int sizeInBytes();
/*    */   
/*    */   default String getAsString() {
/* 50 */     return (new StringTagVisitor()).visit(this);
/*    */   }
/*    */   
/*    */   void accept(TagVisitor paramTagVisitor);
/*    */   
/*    */   StreamTagVisitor.ValueResult accept(StreamTagVisitor paramStreamTagVisitor);
/*    */   
/*    */   default void acceptAsRoot(StreamTagVisitor $$0) {
/* 58 */     StreamTagVisitor.ValueResult $$1 = $$0.visitRootEntry(getType());
/* 59 */     if ($$1 == StreamTagVisitor.ValueResult.CONTINUE)
/* 60 */       accept($$0); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\Tag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */