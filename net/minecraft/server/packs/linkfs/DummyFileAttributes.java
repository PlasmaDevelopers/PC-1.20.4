/*    */ package net.minecraft.server.packs.linkfs;
/*    */ 
/*    */ import java.nio.file.attribute.BasicFileAttributes;
/*    */ import java.nio.file.attribute.FileTime;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ abstract class DummyFileAttributes implements BasicFileAttributes {
/*  8 */   private static final FileTime EPOCH = FileTime.fromMillis(0L);
/*    */ 
/*    */   
/*    */   public FileTime lastModifiedTime() {
/* 12 */     return EPOCH;
/*    */   }
/*    */ 
/*    */   
/*    */   public FileTime lastAccessTime() {
/* 17 */     return EPOCH;
/*    */   }
/*    */ 
/*    */   
/*    */   public FileTime creationTime() {
/* 22 */     return EPOCH;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSymbolicLink() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isOther() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long size() {
/* 37 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Object fileKey() {
/* 43 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\DummyFileAttributes.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */