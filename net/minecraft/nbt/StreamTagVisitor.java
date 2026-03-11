/*    */ package net.minecraft.nbt;public interface StreamTagVisitor { ValueResult visitEnd(); ValueResult visit(String paramString); ValueResult visit(byte paramByte);
/*    */   ValueResult visit(short paramShort);
/*    */   ValueResult visit(int paramInt);
/*    */   ValueResult visit(long paramLong);
/*    */   ValueResult visit(float paramFloat);
/*    */   ValueResult visit(double paramDouble);
/*    */   ValueResult visit(byte[] paramArrayOfbyte);
/*    */   ValueResult visit(int[] paramArrayOfint);
/*    */   ValueResult visit(long[] paramArrayOflong);
/*    */   ValueResult visitList(TagType<?> paramTagType, int paramInt);
/*    */   EntryResult visitEntry(TagType<?> paramTagType);
/*    */   EntryResult visitEntry(TagType<?> paramTagType, String paramString);
/*    */   EntryResult visitElement(TagType<?> paramTagType, int paramInt);
/*    */   ValueResult visitContainerEnd();
/*    */   ValueResult visitRootEntry(TagType<?> paramTagType);
/* 16 */   public enum ValueResult { CONTINUE,
/*    */ 
/*    */ 
/*    */     
/* 20 */     BREAK,
/*    */ 
/*    */ 
/*    */     
/* 24 */     HALT; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum EntryResult
/*    */   {
/* 31 */     ENTER,
/*    */ 
/*    */ 
/*    */     
/* 35 */     SKIP,
/*    */ 
/*    */ 
/*    */     
/* 39 */     BREAK,
/*    */ 
/*    */ 
/*    */     
/* 43 */     HALT;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\StreamTagVisitor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */