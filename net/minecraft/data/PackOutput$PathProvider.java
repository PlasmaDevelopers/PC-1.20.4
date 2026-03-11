/*    */ package net.minecraft.data;
/*    */ 
/*    */ import java.nio.file.Path;
/*    */ import net.minecraft.resources.ResourceLocation;
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
/*    */ public class PathProvider
/*    */ {
/*    */   private final Path root;
/*    */   private final String kind;
/*    */   
/*    */   PathProvider(PackOutput $$0, PackOutput.Target $$1, String $$2) {
/* 40 */     this.root = $$0.getOutputFolder($$1);
/* 41 */     this.kind = $$2;
/*    */   }
/*    */   
/*    */   public Path file(ResourceLocation $$0, String $$1) {
/* 45 */     return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + "." + $$0.getPath());
/*    */   }
/*    */   
/*    */   public Path json(ResourceLocation $$0) {
/* 49 */     return this.root.resolve($$0.getNamespace()).resolve(this.kind).resolve($$0.getPath() + ".json");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\PackOutput$PathProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */