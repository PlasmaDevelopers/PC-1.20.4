/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public interface TagType<T extends Tag> {
/*    */   T load(DataInput paramDataInput, NbtAccounter paramNbtAccounter) throws IOException;
/*    */   
/*    */   StreamTagVisitor.ValueResult parse(DataInput paramDataInput, StreamTagVisitor paramStreamTagVisitor, NbtAccounter paramNbtAccounter) throws IOException;
/*    */   
/*    */   default void parseRoot(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 12 */     switch ($$1.visitRootEntry(this)) { case CONTINUE:
/* 13 */         parse($$0, $$1, $$2);
/*    */         break;
/*    */       case BREAK:
/* 16 */         skip($$0, $$2);
/*    */         break; }
/*    */   
/*    */   }
/*    */   void skip(DataInput paramDataInput, int paramInt, NbtAccounter paramNbtAccounter) throws IOException;
/*    */   
/*    */   void skip(DataInput paramDataInput, NbtAccounter paramNbtAccounter) throws IOException;
/*    */   
/*    */   default boolean isValue() {
/* 25 */     return false;
/*    */   }
/*    */   
/*    */   String getName();
/*    */   
/*    */   String getPrettyName();
/*    */   
/*    */   public static interface StaticSize<T extends Tag>
/*    */     extends TagType<T> {
/*    */     default void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 35 */       $$0.skipBytes(size());
/*    */     }
/*    */ 
/*    */     
/*    */     default void skip(DataInput $$0, int $$1, NbtAccounter $$2) throws IOException {
/* 40 */       $$0.skipBytes(size() * $$1);
/*    */     }
/*    */     
/*    */     int size();
/*    */   }
/*    */   
/*    */   public static interface VariableSize<T extends Tag>
/*    */     extends TagType<T> {
/*    */     default void skip(DataInput $$0, int $$1, NbtAccounter $$2) throws IOException {
/* 49 */       for (int $$3 = 0; $$3 < $$1; $$3++) {
/* 50 */         skip($$0, $$2);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   static TagType<EndTag> createInvalid(final int id) {
/* 56 */     return new TagType<EndTag>() {
/*    */         private IOException createException() {
/* 58 */           return new IOException("Invalid tag id: " + id);
/*    */         }
/*    */ 
/*    */         
/*    */         public EndTag load(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 63 */           throw createException();
/*    */         }
/*    */ 
/*    */         
/*    */         public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) throws IOException {
/* 68 */           throw createException();
/*    */         }
/*    */ 
/*    */         
/*    */         public void skip(DataInput $$0, int $$1, NbtAccounter $$2) throws IOException {
/* 73 */           throw createException();
/*    */         }
/*    */ 
/*    */         
/*    */         public void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 78 */           throw createException();
/*    */         }
/*    */ 
/*    */         
/*    */         public String getName() {
/* 83 */           return "INVALID[" + id + "]";
/*    */         }
/*    */ 
/*    */         
/*    */         public String getPrettyName() {
/* 88 */           return "UNKNOWN_" + id;
/*    */         }
/*    */       };
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TagType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */