/*    */ package net.minecraft.server.packs.linkfs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.FileStore;
/*    */ import java.nio.file.attribute.BasicFileAttributeView;
/*    */ import java.nio.file.attribute.FileAttributeView;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ class LinkFSFileStore
/*    */   extends FileStore {
/*    */   private final String name;
/*    */   
/*    */   public LinkFSFileStore(String $$0) {
/* 14 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 19 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String type() {
/* 24 */     return "index";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getTotalSpace() {
/* 34 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getUsableSpace() {
/* 39 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getUnallocatedSpace() {
/* 44 */     return 0L;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean supportsFileAttributeView(Class<? extends FileAttributeView> $$0) {
/* 49 */     return ($$0 == BasicFileAttributeView.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean supportsFileAttributeView(String $$0) {
/* 54 */     return "basic".equals($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public <V extends java.nio.file.attribute.FileStoreAttributeView> V getFileStoreAttributeView(Class<V> $$0) {
/* 60 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Object getAttribute(String $$0) throws IOException {
/* 65 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\LinkFSFileStore.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */