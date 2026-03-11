/*    */ package net.minecraft.data.registries;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.Encoder;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.RegistryDataLoader;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class RegistriesDatapackGenerator implements DataProvider {
/* 23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final PackOutput output;
/*    */   private final CompletableFuture<HolderLookup.Provider> registries;
/*    */   
/*    */   public RegistriesDatapackGenerator(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 29 */     this.registries = $$1;
/* 30 */     this.output = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 35 */     return this.registries.thenCompose($$1 -> {
/*    */           RegistryOps registryOps = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, $$1);
/*    */           return CompletableFuture.allOf((CompletableFuture<?>[])RegistryDataLoader.WORLDGEN_REGISTRIES.stream().flatMap(()).toArray(()));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private <T> Optional<CompletableFuture<?>> dumpRegistryCap(CachedOutput $$0, HolderLookup.Provider $$1, DynamicOps<JsonElement> $$2, RegistryDataLoader.RegistryData<T> $$3) {
/* 44 */     ResourceKey<? extends Registry<T>> $$4 = $$3.key();
/* 45 */     return $$1.lookup($$4).map($$4 -> {
/*    */           PackOutput.PathProvider $$5 = this.output.createPathProvider(PackOutput.Target.DATA_PACK, $$0.location().getPath());
/*    */           return CompletableFuture.allOf((CompletableFuture<?>[])$$4.listElements().map(()).toArray(()));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static <E> CompletableFuture<?> dumpValue(Path $$0, CachedOutput $$1, DynamicOps<JsonElement> $$2, Encoder<E> $$3, E $$4) {
/* 55 */     Optional<JsonElement> $$5 = $$3.encodeStart($$2, $$4).resultOrPartial($$1 -> LOGGER.error("Couldn't serialize element {}: {}", $$0, $$1));
/* 56 */     if ($$5.isPresent()) {
/* 57 */       return DataProvider.saveStable($$1, $$5.get(), $$0);
/*    */     }
/* 59 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 64 */     return "Registries";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\registries\RegistriesDatapackGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */