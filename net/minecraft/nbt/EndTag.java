/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class EndTag
/*    */   implements Tag
/*    */ {
/*    */   private static final int SELF_SIZE_IN_BYTES = 8;
/*    */   
/* 12 */   public static final TagType<EndTag> TYPE = new TagType<EndTag>()
/*    */     {
/*    */       public EndTag load(DataInput $$0, NbtAccounter $$1) {
/* 15 */         $$1.accountBytes(8L);
/* 16 */         return EndTag.INSTANCE;
/*    */       }
/*    */ 
/*    */       
/*    */       public StreamTagVisitor.ValueResult parse(DataInput $$0, StreamTagVisitor $$1, NbtAccounter $$2) {
/* 21 */         $$2.accountBytes(8L);
/* 22 */         return $$1.visitEnd();
/*    */       }
/*    */ 
/*    */ 
/*    */       
/*    */       public void skip(DataInput $$0, int $$1, NbtAccounter $$2) {}
/*    */ 
/*    */ 
/*    */       
/*    */       public void skip(DataInput $$0, NbtAccounter $$1) {}
/*    */ 
/*    */       
/*    */       public String getName() {
/* 35 */         return "END";
/*    */       }
/*    */ 
/*    */       
/*    */       public String getPrettyName() {
/* 40 */         return "TAG_End";
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean isValue() {
/* 45 */         return true;
/*    */       }
/*    */     };
/*    */   
/* 49 */   public static final EndTag INSTANCE = new EndTag();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void write(DataOutput $$0) throws IOException {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int sizeInBytes() {
/* 60 */     return 8;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 65 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public TagType<EndTag> getType() {
/* 70 */     return TYPE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 75 */     return getAsString();
/*    */   }
/*    */ 
/*    */   
/*    */   public EndTag copy() {
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void accept(TagVisitor $$0) {
/* 85 */     $$0.visitEnd(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public StreamTagVisitor.ValueResult accept(StreamTagVisitor $$0) {
/* 90 */     return $$0.visitEnd();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\EndTag.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */