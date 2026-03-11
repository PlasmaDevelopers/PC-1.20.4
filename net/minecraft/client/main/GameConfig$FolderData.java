/*    */ package net.minecraft.client.main;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.resources.IndexedAssetSource;
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
/*    */ public class FolderData
/*    */ {
/*    */   public final File gameDirectory;
/*    */   public final File resourcePackDirectory;
/*    */   public final File assetDirectory;
/*    */   @Nullable
/*    */   public final String assetIndex;
/*    */   
/*    */   public FolderData(File $$0, File $$1, File $$2, @Nullable String $$3) {
/* 67 */     this.gameDirectory = $$0;
/* 68 */     this.resourcePackDirectory = $$1;
/* 69 */     this.assetDirectory = $$2;
/* 70 */     this.assetIndex = $$3;
/*    */   }
/*    */   
/*    */   public Path getExternalAssetSource() {
/* 74 */     return (this.assetIndex == null) ? this.assetDirectory.toPath() : IndexedAssetSource.createIndexFs(this.assetDirectory.toPath(), this.assetIndex);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\main\GameConfig$FolderData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */