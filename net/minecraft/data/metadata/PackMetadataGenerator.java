/*    */ package net.minecraft.data.metadata;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.function.Supplier;
/*    */ import net.minecraft.DetectedVersion;
/*    */ import net.minecraft.data.CachedOutput;
/*    */ import net.minecraft.data.DataProvider;
/*    */ import net.minecraft.data.PackOutput;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.packs.FeatureFlagsMetadataSection;
/*    */ import net.minecraft.server.packs.PackType;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionType;
/*    */ import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
/*    */ import net.minecraft.world.flag.FeatureFlagSet;
/*    */ 
/*    */ public class PackMetadataGenerator
/*    */   implements DataProvider
/*    */ {
/*    */   private final PackOutput output;
/* 25 */   private final Map<String, Supplier<JsonElement>> elements = new HashMap<>();
/*    */   
/*    */   public PackMetadataGenerator(PackOutput $$0) {
/* 28 */     this.output = $$0;
/*    */   }
/*    */   
/*    */   public <T> PackMetadataGenerator add(MetadataSectionType<T> $$0, T $$1) {
/* 32 */     this.elements.put($$0.getMetadataSectionName(), () -> $$0.toJson($$1));
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<?> run(CachedOutput $$0) {
/* 38 */     JsonObject $$1 = new JsonObject();
/* 39 */     this.elements.forEach(($$1, $$2) -> $$0.add($$1, $$2.get()));
/* 40 */     return DataProvider.saveStable($$0, (JsonElement)$$1, this.output.getOutputFolder().resolve("pack.mcmeta"));
/*    */   }
/*    */ 
/*    */   
/*    */   public final String getName() {
/* 45 */     return "Pack Metadata";
/*    */   }
/*    */   
/*    */   public static PackMetadataGenerator forFeaturePack(PackOutput $$0, Component $$1) {
/* 49 */     return (new PackMetadataGenerator($$0))
/* 50 */       .add(PackMetadataSection.TYPE, new PackMetadataSection($$1, DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static PackMetadataGenerator forFeaturePack(PackOutput $$0, Component $$1, FeatureFlagSet $$2) {
/* 54 */     return forFeaturePack($$0, $$1)
/* 55 */       .add(FeatureFlagsMetadataSection.TYPE, new FeatureFlagsMetadataSection($$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\metadata\PackMetadataGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */