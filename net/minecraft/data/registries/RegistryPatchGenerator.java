/*    */ package net.minecraft.data.registries;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.core.Cloner;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.core.RegistrySetBuilder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.RegistryDataLoader;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ 
/*    */ public class RegistryPatchGenerator {
/*    */   public static CompletableFuture<RegistrySetBuilder.PatchedRegistries> createLookup(CompletableFuture<HolderLookup.Provider> $$0, RegistrySetBuilder $$1) {
/* 18 */     return $$0.thenApply($$1 -> {
/*    */           RegistryAccess.Frozen $$2 = RegistryAccess.fromRegistryOfRegistries(BuiltInRegistries.REGISTRY);
/*    */           Cloner.Factory $$3 = new Cloner.Factory();
/*    */           RegistryDataLoader.WORLDGEN_REGISTRIES.forEach(());
/*    */           RegistrySetBuilder.PatchedRegistries $$4 = $$0.buildPatch((RegistryAccess)$$2, $$1, $$3);
/*    */           HolderLookup.Provider $$5 = $$4.full();
/*    */           Optional<HolderLookup.RegistryLookup<Biome>> $$6 = $$5.lookup(Registries.BIOME);
/*    */           Optional<HolderLookup.RegistryLookup<PlacedFeature>> $$7 = $$5.lookup(Registries.PLACED_FEATURE);
/*    */           if ($$6.isPresent() || $$7.isPresent())
/*    */             VanillaRegistries.validateThatAllBiomeFeaturesHaveBiomeFilter((HolderGetter<PlacedFeature>)$$7.orElseGet(()), (HolderLookup<Biome>)$$6.orElseGet(())); 
/*    */           return $$4;
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\registries\RegistryPatchGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */