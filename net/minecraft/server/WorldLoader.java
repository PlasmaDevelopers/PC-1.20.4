/*     */ package net.minecraft.server;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.LayeredRegistryAccess;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.resources.RegistryDataLoader;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.server.packs.resources.CloseableResourceManager;
/*     */ import net.minecraft.server.packs.resources.MultiPackResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.level.WorldDataConfiguration;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class WorldLoader
/*     */ {
/*  25 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static <D, R> CompletableFuture<R> load(InitConfig $$0, WorldDataSupplier<D> $$1, ResultFactory<D, R> $$2, Executor $$3, Executor $$4) {
/*     */     try {
/*  29 */       Pair<WorldDataConfiguration, CloseableResourceManager> $$5 = $$0.packConfig.createResourceManager();
/*  30 */       CloseableResourceManager $$6 = (CloseableResourceManager)$$5.getSecond();
/*  31 */       LayeredRegistryAccess<RegistryLayer> $$7 = RegistryLayer.createRegistryAccess();
/*     */ 
/*     */       
/*  34 */       LayeredRegistryAccess<RegistryLayer> $$8 = loadAndReplaceLayer((ResourceManager)$$6, $$7, RegistryLayer.WORLDGEN, RegistryDataLoader.WORLDGEN_REGISTRIES);
/*     */ 
/*     */       
/*  37 */       RegistryAccess.Frozen $$9 = $$8.getAccessForLoading(RegistryLayer.DIMENSIONS);
/*  38 */       RegistryAccess.Frozen $$10 = RegistryDataLoader.load((ResourceManager)$$6, (RegistryAccess)$$9, RegistryDataLoader.DIMENSION_REGISTRIES);
/*     */       
/*  40 */       WorldDataConfiguration $$11 = (WorldDataConfiguration)$$5.getFirst();
/*     */       
/*  42 */       DataLoadOutput<D> $$12 = $$1.get(new DataLoadContext((ResourceManager)$$6, $$11, $$9, $$10));
/*     */ 
/*     */       
/*  45 */       LayeredRegistryAccess<RegistryLayer> $$13 = $$8.replaceFrom(RegistryLayer.DIMENSIONS, new RegistryAccess.Frozen[] { $$12.finalDimensions });
/*     */ 
/*     */       
/*  48 */       RegistryAccess.Frozen $$14 = $$13.getAccessForLoading(RegistryLayer.RELOADABLE);
/*     */       
/*  50 */       return ReloadableServerResources.loadResources((ResourceManager)$$6, $$14, $$11.enabledFeatures(), $$0.commandSelection(), $$0.functionCompilationLevel(), $$3, $$4)
/*  51 */         .whenComplete(($$1, $$2) -> {
/*     */             
/*     */             if ($$2 != null) {
/*     */               $$0.close();
/*     */             }
/*  56 */           }).thenApplyAsync($$5 -> {
/*     */             $$5.updateRegistryTags((RegistryAccess)$$0);
/*     */             return $$1.create($$2, $$5, $$3, $$4.cookie);
/*     */           }$$4);
/*  60 */     } catch (Exception $$15) {
/*  61 */       return CompletableFuture.failedFuture($$15);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static RegistryAccess.Frozen loadLayer(ResourceManager $$0, LayeredRegistryAccess<RegistryLayer> $$1, RegistryLayer $$2, List<RegistryDataLoader.RegistryData<?>> $$3) {
/*  66 */     RegistryAccess.Frozen $$4 = $$1.getAccessForLoading($$2);
/*  67 */     return RegistryDataLoader.load($$0, (RegistryAccess)$$4, $$3);
/*     */   }
/*     */   
/*     */   private static LayeredRegistryAccess<RegistryLayer> loadAndReplaceLayer(ResourceManager $$0, LayeredRegistryAccess<RegistryLayer> $$1, RegistryLayer $$2, List<RegistryDataLoader.RegistryData<?>> $$3) {
/*  71 */     RegistryAccess.Frozen $$4 = loadLayer($$0, $$1, $$2, $$3);
/*  72 */     return $$1.replaceFrom($$2, new RegistryAccess.Frozen[] { $$4 });
/*     */   }
/*     */   public static final class DataLoadContext extends Record { private final ResourceManager resources; private final WorldDataConfiguration dataConfiguration; private final RegistryAccess.Frozen datapackWorldgen; private final RegistryAccess.Frozen datapackDimensions;
/*  75 */     public DataLoadContext(ResourceManager $$0, WorldDataConfiguration $$1, RegistryAccess.Frozen $$2, RegistryAccess.Frozen $$3) { this.resources = $$0; this.dataConfiguration = $$1; this.datapackWorldgen = $$2; this.datapackDimensions = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/WorldLoader$DataLoadContext;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  75 */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext; } public ResourceManager resources() { return this.resources; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/WorldLoader$DataLoadContext;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/WorldLoader$DataLoadContext;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #75	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/WorldLoader$DataLoadContext;
/*  75 */       //   0	8	1	$$0	Ljava/lang/Object; } public WorldDataConfiguration dataConfiguration() { return this.dataConfiguration; } public RegistryAccess.Frozen datapackWorldgen() { return this.datapackWorldgen; } public RegistryAccess.Frozen datapackDimensions() { return this.datapackDimensions; }
/*     */      } public static final class DataLoadOutput<D> extends Record { final D cookie; final RegistryAccess.Frozen finalDimensions;
/*  77 */     public DataLoadOutput(D $$0, RegistryAccess.Frozen $$1) { this.cookie = $$0; this.finalDimensions = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/WorldLoader$DataLoadOutput;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #77	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput<TD;>; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/WorldLoader$DataLoadOutput;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #77	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput<TD;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/WorldLoader$DataLoadOutput;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #77	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*  77 */       //   0	8	0	this	Lnet/minecraft/server/WorldLoader$DataLoadOutput<TD;>; } public D cookie() { return this.cookie; } public RegistryAccess.Frozen finalDimensions() { return this.finalDimensions; }
/*     */      }
/*     */ 
/*     */   
/*     */   public static final class PackConfig
/*     */     extends Record
/*     */   {
/*     */     private final PackRepository packRepository;
/*     */     private final WorldDataConfiguration initialDataConfig;
/*     */     private final boolean safeMode;
/*     */     private final boolean initMode;
/*     */     
/*  89 */     public PackConfig(PackRepository $$0, WorldDataConfiguration $$1, boolean $$2, boolean $$3) { this.packRepository = $$0; this.initialDataConfig = $$1; this.safeMode = $$2; this.initMode = $$3; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/WorldLoader$PackConfig;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$PackConfig; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/WorldLoader$PackConfig;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$PackConfig; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/WorldLoader$PackConfig;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #89	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/WorldLoader$PackConfig;
/*  89 */       //   0	8	1	$$0	Ljava/lang/Object; } public PackRepository packRepository() { return this.packRepository; } public WorldDataConfiguration initialDataConfig() { return this.initialDataConfig; } public boolean safeMode() { return this.safeMode; } public boolean initMode() { return this.initMode; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Pair<WorldDataConfiguration, CloseableResourceManager> createResourceManager() {
/*  96 */       FeatureFlagSet $$0 = this.initMode ? FeatureFlags.REGISTRY.allFlags() : this.initialDataConfig.enabledFeatures();
/*  97 */       WorldDataConfiguration $$1 = MinecraftServer.configurePackRepository(this.packRepository, this.initialDataConfig.dataPacks(), this.safeMode, $$0);
/*  98 */       if (!this.initMode)
/*     */       {
/* 100 */         $$1 = $$1.expandFeatures(this.initialDataConfig.enabledFeatures());
/*     */       }
/*     */       
/* 103 */       List<PackResources> $$2 = this.packRepository.openAllSelected();
/* 104 */       MultiPackResourceManager multiPackResourceManager = new MultiPackResourceManager(PackType.SERVER_DATA, $$2);
/* 105 */       return Pair.of($$1, multiPackResourceManager);
/*     */     } }
/*     */   public static final class InitConfig extends Record { final WorldLoader.PackConfig packConfig; private final Commands.CommandSelection commandSelection; private final int functionCompilationLevel;
/*     */     
/* 109 */     public InitConfig(WorldLoader.PackConfig $$0, Commands.CommandSelection $$1, int $$2) { this.packConfig = $$0; this.commandSelection = $$1; this.functionCompilationLevel = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/WorldLoader$InitConfig;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #109	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$InitConfig; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/WorldLoader$InitConfig;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #109	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/server/WorldLoader$InitConfig; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/WorldLoader$InitConfig;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #109	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/WorldLoader$InitConfig;
/* 109 */       //   0	8	1	$$0	Ljava/lang/Object; } public WorldLoader.PackConfig packConfig() { return this.packConfig; } public Commands.CommandSelection commandSelection() { return this.commandSelection; } public int functionCompilationLevel() { return this.functionCompilationLevel; }
/*     */      }
/*     */ 
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface WorldDataSupplier<D> {
/*     */     WorldLoader.DataLoadOutput<D> get(WorldLoader.DataLoadContext param1DataLoadContext);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface ResultFactory<D, R> {
/*     */     R create(CloseableResourceManager param1CloseableResourceManager, ReloadableServerResources param1ReloadableServerResources, LayeredRegistryAccess<RegistryLayer> param1LayeredRegistryAccess, D param1D);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\WorldLoader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */