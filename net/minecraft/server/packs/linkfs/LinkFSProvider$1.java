/*    */ package net.minecraft.server.packs.linkfs;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.nio.file.DirectoryIteratorException;
/*    */ import java.nio.file.DirectoryStream;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Iterator;
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
/*    */   implements DirectoryStream<Path>
/*    */ {
/*    */   public Iterator<Path> iterator() {
/* 81 */     return directoryContents.children().values()
/* 82 */       .stream()
/* 83 */       .filter($$1 -> {
/*    */           try {
/*    */             return $$0.accept($$1);
/* 86 */           } catch (IOException $$2) {
/*    */             
/*    */             throw new DirectoryIteratorException($$2);
/*    */           } 
/* 90 */         }).map($$0 -> $$0)
/* 91 */       .iterator();
/*    */   }
/*    */   
/*    */   public void close() {}
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\linkfs\LinkFSProvider$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */