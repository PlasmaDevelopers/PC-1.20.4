/*    */ package net.minecraft.world.level.validation;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.FileVisitResult;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.LinkOption;
/*    */ import java.nio.file.NoSuchFileException;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.PathMatcher;
/*    */ import java.nio.file.SimpleFileVisitor;
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DirectoryValidator {
/*    */   private final PathMatcher symlinkTargetAllowList;
/*    */   
/*    */   public DirectoryValidator(PathMatcher $$0) {
/* 19 */     this.symlinkTargetAllowList = $$0;
/*    */   }
/*    */   
/*    */   public void validateSymlink(Path $$0, List<ForbiddenSymlinkInfo> $$1) throws IOException {
/* 23 */     Path $$2 = Files.readSymbolicLink($$0);
/* 24 */     if (!this.symlinkTargetAllowList.matches($$2)) {
/* 25 */       $$1.add(new ForbiddenSymlinkInfo($$0, $$2));
/*    */     }
/*    */   }
/*    */   
/*    */   public List<ForbiddenSymlinkInfo> validateSymlink(Path $$0) throws IOException {
/* 30 */     List<ForbiddenSymlinkInfo> $$1 = new ArrayList<>();
/* 31 */     validateSymlink($$0, $$1);
/* 32 */     return $$1;
/*    */   }
/*    */   public List<ForbiddenSymlinkInfo> validateDirectory(Path $$0, boolean $$1) throws IOException {
/*    */     BasicFileAttributes $$3;
/* 36 */     List<ForbiddenSymlinkInfo> $$2 = new ArrayList<>();
/*    */ 
/*    */     
/*    */     try {
/* 40 */       $$3 = Files.readAttributes($$0, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/* 41 */     } catch (NoSuchFileException $$4) {
/* 42 */       return $$2;
/*    */     } 
/*    */     
/* 45 */     if ($$3.isRegularFile()) {
/* 46 */       throw new IOException("Path " + $$0 + " is not a directory");
/*    */     }
/*    */     
/* 49 */     if ($$3.isSymbolicLink()) {
/* 50 */       if ($$1) {
/*    */ 
/*    */         
/* 53 */         $$0 = Files.readSymbolicLink($$0);
/*    */       } else {
/* 55 */         validateSymlink($$0, $$2);
/* 56 */         return $$2;
/*    */       } 
/*    */     }
/*    */     
/* 60 */     validateKnownDirectory($$0, $$2);
/* 61 */     return $$2;
/*    */   }
/*    */   
/*    */   public void validateKnownDirectory(Path $$0, final List<ForbiddenSymlinkInfo> issues) throws IOException {
/* 65 */     Files.walkFileTree($$0, new SimpleFileVisitor<Path>() {
/*    */           private void validateSymlink(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 67 */             if ($$1.isSymbolicLink()) {
/* 68 */               DirectoryValidator.this.validateSymlink($$0, issues);
/*    */             }
/*    */           }
/*    */ 
/*    */           
/*    */           public FileVisitResult preVisitDirectory(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 74 */             validateSymlink($$0, $$1);
/* 75 */             return super.preVisitDirectory($$0, $$1);
/*    */           }
/*    */ 
/*    */           
/*    */           public FileVisitResult visitFile(Path $$0, BasicFileAttributes $$1) throws IOException {
/* 80 */             validateSymlink($$0, $$1);
/* 81 */             return super.visitFile($$0, $$1);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\validation\DirectoryValidator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */