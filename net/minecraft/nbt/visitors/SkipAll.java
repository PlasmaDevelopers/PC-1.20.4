/*    */ package net.minecraft.nbt.visitors;
/*    */ 
/*    */ import net.minecraft.nbt.StreamTagVisitor;
/*    */ import net.minecraft.nbt.TagType;
/*    */ 
/*    */ public interface SkipAll extends StreamTagVisitor {
/*  7 */   public static final SkipAll INSTANCE = new SkipAll() {
/*    */     
/*    */     };
/*    */   
/*    */   default StreamTagVisitor.ValueResult visitEnd() {
/* 12 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(String $$0) {
/* 17 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(byte $$0) {
/* 22 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(short $$0) {
/* 27 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(int $$0) {
/* 32 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(long $$0) {
/* 37 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(float $$0) {
/* 42 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(double $$0) {
/* 47 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(byte[] $$0) {
/* 52 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(int[] $$0) {
/* 57 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visit(long[] $$0) {
/* 62 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visitList(TagType<?> $$0, int $$1) {
/* 67 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.EntryResult visitElement(TagType<?> $$0, int $$1) {
/* 72 */     return StreamTagVisitor.EntryResult.SKIP;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0) {
/* 77 */     return StreamTagVisitor.EntryResult.SKIP;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.EntryResult visitEntry(TagType<?> $$0, String $$1) {
/* 82 */     return StreamTagVisitor.EntryResult.SKIP;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visitContainerEnd() {
/* 87 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ 
/*    */   
/*    */   default StreamTagVisitor.ValueResult visitRootEntry(TagType<?> $$0) {
/* 92 */     return StreamTagVisitor.ValueResult.CONTINUE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\nbt\visitors\SkipAll.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */