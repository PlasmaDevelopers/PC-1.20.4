/*    */ package net.minecraft.resources;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.packs.resources.Resource;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ 
/*    */ public class FileToIdConverter
/*    */ {
/*    */   private final String prefix;
/*    */   private final String extension;
/*    */   
/*    */   public FileToIdConverter(String $$0, String $$1) {
/* 14 */     this.prefix = $$0;
/* 15 */     this.extension = $$1;
/*    */   }
/*    */   
/*    */   public static FileToIdConverter json(String $$0) {
/* 19 */     return new FileToIdConverter($$0, ".json");
/*    */   }
/*    */   
/*    */   public ResourceLocation idToFile(ResourceLocation $$0) {
/* 23 */     return $$0.withPath(this.prefix + "/" + this.prefix + $$0.getPath());
/*    */   }
/*    */   
/*    */   public ResourceLocation fileToId(ResourceLocation $$0) {
/* 27 */     String $$1 = $$0.getPath();
/* 28 */     return $$0.withPath($$1.substring(this.prefix.length() + 1, $$1.length() - this.extension.length()));
/*    */   }
/*    */   
/*    */   public Map<ResourceLocation, Resource> listMatchingResources(ResourceManager $$0) {
/* 32 */     return $$0.listResources(this.prefix, $$0 -> $$0.getPath().endsWith(this.extension));
/*    */   }
/*    */   
/*    */   public Map<ResourceLocation, List<Resource>> listMatchingResourceStacks(ResourceManager $$0) {
/* 36 */     return $$0.listResourceStacks(this.prefix, $$0 -> $$0.getPath().endsWith(this.extension));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\resources\FileToIdConverter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */