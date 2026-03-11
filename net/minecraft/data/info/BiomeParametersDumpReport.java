/*    */ package net.minecraft.data.info;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DynamicOps;
/*    */ import com.mojang.serialization.Encoder;
/*    */ import com.mojang.serialization.JsonOps;
/*    */ import com.mojang.serialization.MapCodec;
/*    */ import java.nio.file.Path;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionStage;
/*    */ import net.minecraft.core.HolderLookup;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.resources.RegistryOps;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.biome.Climate;
/*    */ import net.minecraft.world.level.biome.MultiNoiseBiomeSourceParameterList;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class BiomeParametersDumpReport implements DataProvider {
/* 30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Path topPath;
/*    */   
/*    */   private final CompletableFuture<HolderLookup.Provider> registries;
/* 35 */   private static final MapCodec<ResourceKey<Biome>> ENTRY_CODEC = ResourceKey.codec(Registries.BIOME).fieldOf("biome");
/*    */   
/* 37 */   private static final Codec<Climate.ParameterList<ResourceKey<Biome>>> CODEC = Climate.ParameterList.codec(ENTRY_CODEC).fieldOf("biomes").codec();
/*    */   
/*    */   public BiomeParametersDumpReport(PackOutput $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 40 */     this.topPath = $$0.getOutputFolder(PackOutput.Target.REPORTS).resolve("biome_parameters");
/* 41 */     this.registries = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 46 */     return this.registries.thenCompose($$1 -> {
/*    */           RegistryOps registryOps = RegistryOps.create((DynamicOps)JsonOps.INSTANCE, $$1);
/*    */           List<CompletableFuture<?>> $$3 = new ArrayList<>();
/*    */           MultiNoiseBiomeSourceParameterList.knownPresets().forEach(());
/*    */           return CompletableFuture.allOf((CompletableFuture<?>[])$$3.toArray(()));
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static <E> CompletableFuture<?> dumpValue(Path $$0, CachedOutput $$1, DynamicOps<JsonElement> $$2, Encoder<E> $$3, E $$4) {
/* 57 */     Optional<JsonElement> $$5 = $$3.encodeStart($$2, $$4).resultOrPartial($$1 -> LOGGER.error("Couldn't serialize element {}: {}", $$0, $$1));
/* 58 */     if ($$5.isPresent()) {
/* 59 */       return DataProvider.saveStable($$1, $$5.get(), $$0);
/*    */     }
/* 61 */     return CompletableFuture.completedFuture(null);
/*    */   }
/*    */   
/*    */   private Path createPath(ResourceLocation $$0) {
/* 65 */     return this.topPath.resolve($$0.getNamespace()).resolve($$0.getPath() + ".json");
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 70 */     return "Biome Parameters";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\info\BiomeParametersDumpReport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */