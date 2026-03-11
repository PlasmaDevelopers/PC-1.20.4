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
/*    */ public interface StaticSize<T extends Tag>
/*    */   extends TagType<T>
/*    */ {
/*    */   default void skip(DataInput $$0, NbtAccounter $$1) throws IOException {
/* 35 */     $$0.skipBytes(size());
/*    */   }
/*    */ 
/*    */   
/*    */   default void skip(DataInput $$0, int $$1, NbtAccounter $$2) throws IOException {
/* 40 */     $$0.skipBytes(size() * $$1);
/*    */   }
/*    */   
/*    */   int size();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\TagType$StaticSize.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */