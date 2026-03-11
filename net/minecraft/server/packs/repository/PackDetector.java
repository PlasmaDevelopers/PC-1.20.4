/*    */ package net.minecraft.server.packs.repository;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Files;
/*    */ import java.nio.file.LinkOption;
/*    */ import java.nio.file.NoSuchFileException;
/*    */ import java.nio.file.Path;
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.validation.DirectoryValidator;
/*    */ import net.minecraft.world.level.validation.ForbiddenSymlinkInfo;
/*    */ 
/*    */ 
/*    */ public abstract class PackDetector<T>
/*    */ {
/*    */   private final DirectoryValidator validator;
/*    */   
/*    */   protected PackDetector(DirectoryValidator $$0) {
/* 20 */     this.validator = $$0;
/*    */   }
/*    */   @Nullable
/*    */   public T detectPackResources(Path $$0, List<ForbiddenSymlinkInfo> $$1) throws IOException {
/*    */     BasicFileAttributes $$3, $$5;
/* 25 */     Path $$2 = $$0;
/*    */     
/*    */     try {
/* 28 */       $$3 = Files.readAttributes($$0, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/* 29 */     } catch (NoSuchFileException $$4) {
/* 30 */       return null;
/*    */     } 
/*    */     
/* 33 */     if ($$3.isSymbolicLink()) {
/* 34 */       this.validator.validateSymlink($$0, $$1);
/* 35 */       if (!$$1.isEmpty()) {
/* 36 */         return null;
/*    */       }
/* 38 */       $$2 = Files.readSymbolicLink($$0);
/* 39 */       $$5 = Files.readAttributes($$2, BasicFileAttributes.class, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
/*    */     } 
/*    */     
/* 42 */     if ($$5.isDirectory()) {
/* 43 */       this.validator.validateKnownDirectory($$2, $$1);
/* 44 */       if (!$$1.isEmpty()) {
/* 45 */         return null;
/*    */       }
/* 47 */       if (!Files.isRegularFile($$2.resolve("pack.mcmeta"), new LinkOption[0])) {
/* 48 */         return null;
/*    */       }
/* 50 */       return createDirectoryPack($$2);
/* 51 */     }  if ($$5.isRegularFile() && $$2.getFileName().toString().endsWith(".zip")) {
/* 52 */       return createZipPack($$2);
/*    */     }
/* 54 */     return null;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   protected abstract T createZipPack(Path paramPath) throws IOException;
/*    */   
/*    */   @Nullable
/*    */   protected abstract T createDirectoryPack(Path paramPath) throws IOException;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\PackDetector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */