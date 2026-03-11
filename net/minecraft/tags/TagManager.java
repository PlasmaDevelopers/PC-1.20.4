/*    */ package net.minecraft.tags;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.util.profiling.ProfilerFiller;
/*    */ 
/*    */ public class TagManager implements PreparableReloadListener {
/* 22 */   private static final Map<ResourceKey<? extends Registry<?>>, String> CUSTOM_REGISTRY_DIRECTORIES = Map.of(Registries.BLOCK, "tags/blocks", Registries.ENTITY_TYPE, "tags/entity_types", Registries.FLUID, "tags/fluids", Registries.GAME_EVENT, "tags/game_events", Registries.ITEM, "tags/items");
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final RegistryAccess registryAccess;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 32 */   private List<LoadResult<?>> results = List.of();
/*    */   
/*    */   public TagManager(RegistryAccess $$0) {
/* 35 */     this.registryAccess = $$0;
/*    */   }
/*    */   
/*    */   public List<LoadResult<?>> getResult() {
/* 39 */     return this.results;
/*    */   }
/*    */   
/*    */   public static String getTagDir(ResourceKey<? extends Registry<?>> $$0) {
/* 43 */     String $$1 = CUSTOM_REGISTRY_DIRECTORIES.get($$0);
/* 44 */     if ($$1 != null) {
/* 45 */       return $$1;
/*    */     }
/*    */ 
/*    */     
/* 49 */     return "tags/" + $$0.location().getPath();
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 54 */     List<? extends CompletableFuture<? extends LoadResult<?>>> $$6 = this.registryAccess.registries().map($$2 -> createLoader($$0, $$1, $$2)).toList();
/*    */ 
/*    */     
/* 57 */     Objects.requireNonNull($$0); return CompletableFuture.allOf((CompletableFuture<?>[])$$6.toArray($$0 -> new CompletableFuture[$$0])).thenCompose($$0::wait)
/* 58 */       .thenAcceptAsync($$1 -> this.results = (List<LoadResult<?>>)$$0.stream().map(CompletableFuture::join).collect(Collectors.toUnmodifiableList()), $$5);
/*    */   }
/*    */   
/*    */   private <T> CompletableFuture<LoadResult<T>> createLoader(ResourceManager $$0, Executor $$1, RegistryAccess.RegistryEntry<T> $$2) {
/* 62 */     ResourceKey<? extends Registry<T>> $$3 = $$2.key();
/* 63 */     Registry<T> $$4 = $$2.value();
/* 64 */     TagLoader<Holder<T>> $$5 = new TagLoader<>($$2 -> $$0.getHolder(ResourceKey.create($$1, $$2)), getTagDir($$3));
/* 65 */     return CompletableFuture.supplyAsync(() -> new LoadResult($$0, $$1.loadAndBuild($$2)), $$1);
/*    */   }
/*    */   public static final class LoadResult<T> extends Record { private final ResourceKey<? extends Registry<T>> key; private final Map<ResourceLocation, Collection<Holder<T>>> tags;
/* 68 */     public LoadResult(ResourceKey<? extends Registry<T>> $$0, Map<ResourceLocation, Collection<Holder<T>>> $$1) { this.key = $$0; this.tags = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/tags/TagManager$LoadResult;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #68	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/tags/TagManager$LoadResult;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 68 */       //   0	7	0	this	Lnet/minecraft/tags/TagManager$LoadResult<TT;>; } public ResourceKey<? extends Registry<T>> key() { return this.key; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/tags/TagManager$LoadResult;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #68	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/tags/TagManager$LoadResult;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/*    */       //   0	7	0	this	Lnet/minecraft/tags/TagManager$LoadResult<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/tags/TagManager$LoadResult;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #68	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/tags/TagManager$LoadResult;
/*    */       //   0	8	1	$$0	Ljava/lang/Object;
/*    */       // Local variable type table:
/*    */       //   start	length	slot	name	signature
/* 68 */       //   0	8	0	this	Lnet/minecraft/tags/TagManager$LoadResult<TT;>; } public Map<ResourceLocation, Collection<Holder<T>>> tags() { return this.tags; }
/*    */      }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\tags\TagManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */