/*    */ package net.minecraft.server.packs.resources;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.PackResources;
/*    */ import net.minecraft.server.packs.PackType;
/*    */ import net.minecraft.util.Unit;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ReloadableResourceManager
/*    */   implements ResourceManager, AutoCloseable {
/* 22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private CloseableResourceManager resources;
/* 25 */   private final List<PreparableReloadListener> listeners = Lists.newArrayList();
/*    */   private final PackType type;
/*    */   
/*    */   public ReloadableResourceManager(PackType $$0) {
/* 29 */     this.type = $$0;
/* 30 */     this.resources = new MultiPackResourceManager($$0, List.of());
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 35 */     this.resources.close();
/*    */   }
/*    */   
/*    */   public void registerReloadListener(PreparableReloadListener $$0) {
/* 39 */     this.listeners.add($$0);
/*    */   }
/*    */   
/*    */   public ReloadInstance createReload(Executor $$0, Executor $$1, CompletableFuture<Unit> $$2, List<PackResources> $$3) {
/* 43 */     LOGGER.info("Reloading ResourceManager: {}", LogUtils.defer(() -> $$0.stream().map(PackResources::packId).collect(Collectors.joining(", "))));
/*    */     
/* 45 */     this.resources.close();
/* 46 */     this.resources = new MultiPackResourceManager(this.type, $$3);
/* 47 */     return SimpleReloadInstance.create(this.resources, this.listeners, $$0, $$1, $$2, LOGGER.isDebugEnabled());
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Resource> getResource(ResourceLocation $$0) {
/* 52 */     return this.resources.getResource($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> getNamespaces() {
/* 57 */     return this.resources.getNamespaces();
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Resource> getResourceStack(ResourceLocation $$0) {
/* 62 */     return this.resources.getResourceStack($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<ResourceLocation, Resource> listResources(String $$0, Predicate<ResourceLocation> $$1) {
/* 67 */     return this.resources.listResources($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<ResourceLocation, List<Resource>> listResourceStacks(String $$0, Predicate<ResourceLocation> $$1) {
/* 72 */     return this.resources.listResourceStacks($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<PackResources> listPacks() {
/* 77 */     return this.resources.listPacks();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\resources\ReloadableResourceManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */