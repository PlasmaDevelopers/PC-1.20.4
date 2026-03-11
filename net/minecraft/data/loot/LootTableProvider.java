/*     */ package net.minecraft.data.loot;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
/*     */ import java.nio.file.Path;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.DataProvider;
/*     */ import net.minecraft.data.PackOutput;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ProblemReporter;
/*     */ import net.minecraft.world.RandomSequence;
/*     */ import net.minecraft.world.level.levelgen.RandomSupport;
/*     */ import net.minecraft.world.level.storage.loot.LootDataId;
/*     */ import net.minecraft.world.level.storage.loot.LootDataResolver;
/*     */ import net.minecraft.world.level.storage.loot.LootDataType;
/*     */ import net.minecraft.world.level.storage.loot.LootTable;
/*     */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LootTableProvider
/*     */   implements DataProvider {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger(); private final PackOutput.PathProvider pathProvider; private final Set<ResourceLocation> requiredTables; private final List<SubProviderEntry> subProviders;
/*     */   public static final class SubProviderEntry extends Record { private final Supplier<LootTableSubProvider> provider; final LootContextParamSet paramSet;
/*  36 */     public SubProviderEntry(Supplier<LootTableSubProvider> $$0, LootContextParamSet $$1) { this.provider = $$0; this.paramSet = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  36 */       //   0	7	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry; } public Supplier<LootTableSubProvider> provider() { return this.provider; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #36	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/data/loot/LootTableProvider$SubProviderEntry;
/*  36 */       //   0	8	1	$$0	Ljava/lang/Object; } public LootContextParamSet paramSet() { return this.paramSet; }
/*     */      }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LootTableProvider(PackOutput $$0, Set<ResourceLocation> $$1, List<SubProviderEntry> $$2) {
/*  43 */     this.pathProvider = $$0.createPathProvider(PackOutput.Target.DATA_PACK, "loot_tables");
/*  44 */     this.subProviders = $$2;
/*  45 */     this.requiredTables = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<?> run(CachedOutput $$0) {
/*  50 */     final Map<ResourceLocation, LootTable> tables = Maps.newHashMap();
/*     */     
/*  52 */     Object2ObjectOpenHashMap object2ObjectOpenHashMap = new Object2ObjectOpenHashMap();
/*     */     
/*  54 */     this.subProviders.forEach($$2 -> ((LootTableSubProvider)$$2.provider().get()).generate(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  67 */     ProblemReporter.Collector $$3 = new ProblemReporter.Collector();
/*  68 */     ValidationContext $$4 = new ValidationContext((ProblemReporter)$$3, LootContextParamSets.ALL_PARAMS, new LootDataResolver()
/*     */         {
/*     */           @Nullable
/*     */           public <T> T getElement(LootDataId<T> $$0)
/*     */           {
/*  73 */             if ($$0.type() == LootDataType.TABLE) {
/*  74 */               return (T)tables.get($$0.location());
/*     */             }
/*  76 */             return null;
/*     */           }
/*     */         });
/*     */     
/*  80 */     Sets.SetView setView = Sets.difference(this.requiredTables, $$1.keySet());
/*     */     
/*  82 */     for (ResourceLocation $$6 : setView) {
/*  83 */       $$3.report("Missing built-in table: " + $$6);
/*     */     }
/*     */     
/*  86 */     $$1.forEach(($$1, $$2) -> $$2.validate($$0.setParams($$2.getParamSet()).enterElement("{" + $$1 + "}", new LootDataId(LootDataType.TABLE, $$1))));
/*     */     
/*  88 */     Multimap<String, String> $$7 = $$3.get();
/*  89 */     if (!$$7.isEmpty()) {
/*  90 */       $$7.forEach(($$0, $$1) -> LOGGER.warn("Found validation problem in {}: {}", $$0, $$1));
/*  91 */       throw new IllegalStateException("Failed to validate loot tables, see logs");
/*     */     } 
/*     */     
/*  94 */     return CompletableFuture.allOf((CompletableFuture<?>[])$$1.entrySet().stream()
/*  95 */         .map($$1 -> {
/*     */             ResourceLocation $$2 = (ResourceLocation)$$1.getKey();
/*     */             
/*     */             LootTable $$3 = (LootTable)$$1.getValue();
/*     */             Path $$4 = this.pathProvider.json($$2);
/*     */             return DataProvider.saveStable($$0, LootTable.CODEC, $$3, $$4);
/* 101 */           }).toArray($$0 -> new CompletableFuture[$$0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getName() {
/* 106 */     return "Loot Tables";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\loot\LootTableProvider.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */