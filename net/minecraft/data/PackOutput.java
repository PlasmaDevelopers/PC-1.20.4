/*    */ package net.minecraft.data;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class PackOutput
/*    */ {
/*    */   private final Path outputFolder;
/*    */   
/*    */   public PackOutput(Path $$0) {
/* 11 */     this.outputFolder = $$0;
/*    */   }
/*    */   
/*    */   public Path getOutputFolder() {
/* 15 */     return this.outputFolder;
/*    */   }
/*    */   
/*    */   public Path getOutputFolder(Target $$0) {
/* 19 */     return getOutputFolder().resolve($$0.directory);
/*    */   }
/*    */   
/*    */   public enum Target {
/* 23 */     DATA_PACK("data"),
/* 24 */     RESOURCE_PACK("assets"),
/* 25 */     REPORTS("reports");
/*    */     
/*    */     final String directory;
/*    */ 
/*    */     
/*    */     Target(String $$0) {
/* 31 */       this.directory = $$0;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class PathProvider {
/*    */     private final Path root;
/*    */     private final String kind;
/*    */     
/*    */     PathProvider(PackOutput $$0, PackOutput.Target $$1, String $$2) {
/* 40 */       this.root = $$0.getOutputFolder($$1);
/* 41 */       this.kind = $$2;
/*    */     }
/*    */     
/*    */     public Path file(ResourceLocation $$0, String $$1) {
/* 45 */       return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + "." + $$0.getPath());
/*    */     }
/*    */     
/*    */     public Path json(ResourceLocation $$0) {
/* 49 */       return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + ".json");
/*    */     }
/*    */   }
/*    */   
/*    */   public PathProvider createPathProvider(Target $$0, String $$1) {
/* 54 */     return new PathProvider(this, $$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\PackOutput.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */