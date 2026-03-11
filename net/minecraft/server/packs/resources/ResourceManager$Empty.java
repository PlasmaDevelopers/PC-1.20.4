/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.PackResources;
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
/*    */ public enum Empty
/*    */   implements ResourceManager
/*    */ {
/* 39 */   INSTANCE;
/*    */ 
/*    */   
/*    */   public Set<String> getNamespaces() {
/* 43 */     return Set.of();
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Resource> getResource(ResourceLocation $$0) {
/* 48 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Resource> getResourceStack(ResourceLocation $$0) {
/* 53 */     return List.of();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<ResourceLocation, Resource> listResources(String $$0, Predicate<ResourceLocation> $$1) {
/* 58 */     return Map.of();
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<ResourceLocation, List<Resource>> listResourceStacks(String $$0, Predicate<ResourceLocation> $$1) {
/* 63 */     return Map.of();
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<PackResources> listPacks() {
/* 68 */     return Stream.of(new PackResources[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ResourceManager$Empty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */