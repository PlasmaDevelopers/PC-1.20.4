/*    */ package net.minecraft.world.level.validation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.FileVisitResult;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.SimpleFileVisitor;
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ class null
/*    */   extends SimpleFileVisitor<Path>
/*    */ {
/*    */   private void validateSymlink(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 67 */     if ($$1.isSymbolicLink()) {
/* 68 */       DirectoryValidator.this.validateSymlink($$0, issues);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public FileVisitResult preVisitDirectory(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 74 */     validateSymlink($$0, $$1);
/* 75 */     return super.preVisitDirectory($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 80 */     validateSymlink($$0, $$1);
/* 81 */     return super.visitFile($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\DirectoryValidator$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */