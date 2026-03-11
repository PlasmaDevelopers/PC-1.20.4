/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ 
/*    */ import com.google.common.cache.CacheBuilder;
/*    */ import com.google.common.cache.CacheLoader;
/*    */ import com.google.common.cache.LoadingCache;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntMaps;
/*    */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import java.util.Optional;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import org.apache.commons.lang3.mutable.MutableInt;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class FeatureCountTracker {
/* 23 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   private static final class FeatureData extends Record { private final ConfiguredFeature<?, ?> feature; private final Optional<PlacedFeature> topFeature;
/* 25 */     FeatureData(ConfiguredFeature<?, ?> $$0, Optional<PlacedFeature> $$1) { this.feature = $$0; this.topFeature = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 25 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData; } public ConfiguredFeature<?, ?> feature() { return this.feature; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$FeatureData;
/* 25 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<PlacedFeature> topFeature() { return this.topFeature; }
/*    */      } private static final class LevelData extends Record { private final Object2IntMap<FeatureCountTracker.FeatureData> featureData; private final MutableInt chunksWithFeatures;
/* 27 */     LevelData(Object2IntMap<FeatureCountTracker.FeatureData> $$0, MutableInt $$1) { this.featureData = $$0; this.chunksWithFeatures = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/feature/FeatureCountTracker$LevelData;
/* 27 */       //   0	8	1	$$0	Ljava/lang/Object; } public Object2IntMap<FeatureCountTracker.FeatureData> featureData() { return this.featureData; } public MutableInt chunksWithFeatures() { return this.chunksWithFeatures; }
/*    */      }
/* 29 */   private static final LoadingCache<ServerLevel, LevelData> data = CacheBuilder.newBuilder().weakKeys().expireAfterAccess(5L, TimeUnit.MINUTES).build(new CacheLoader<ServerLevel, LevelData>()
/*    */       {
/*    */         public FeatureCountTracker.LevelData load(ServerLevel $$0) {
/* 32 */           return new FeatureCountTracker.LevelData(Object2IntMaps.synchronize((Object2IntMap)new Object2IntOpenHashMap()), new MutableInt(0));
/*    */         }
/*    */       });
/*    */   
/*    */   public static void chunkDecorated(ServerLevel $$0) {
/*    */     try {
/* 38 */       ((LevelData)data.get($$0)).chunksWithFeatures().increment();
/* 39 */     } catch (Exception $$1) {
/* 40 */       LOGGER.error("Failed to increment chunk count", $$1);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void featurePlaced(ServerLevel $$0, ConfiguredFeature<?, ?> $$1, Optional<PlacedFeature> $$2) {
/*    */     try {
/* 46 */       ((LevelData)data.get($$0)).featureData().computeInt(new FeatureData($$1, $$2), ($$0, $$1) -> Integer.valueOf(($$1 == null) ? 1 : ($$1.intValue() + 1)));
/* 47 */     } catch (Exception $$3) {
/* 48 */       LOGGER.error("Failed to increment feature count", $$3);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void clearCounts() {
/* 53 */     data.invalidateAll();
/* 54 */     LOGGER.debug("Cleared feature counts");
/*    */   }
/*    */   
/*    */   public static void logCounts() {
/* 58 */     LOGGER.debug("Logging feature counts:");
/* 59 */     data.asMap().forEach(($$0, $$1) -> {
/*    */           String $$2 = $$0.dimension().location().toString();
/*    */           boolean $$3 = $$0.getServer().isRunning();
/*    */           Registry<PlacedFeature> $$4 = $$0.registryAccess().registryOrThrow(Registries.PLACED_FEATURE);
/*    */           String $$5 = ($$3 ? "running" : "dead") + " " + ($$3 ? "running" : "dead");
/*    */           Integer $$6 = $$1.chunksWithFeatures().getValue();
/*    */           LOGGER.debug($$5 + " total_chunks: " + $$5);
/*    */           $$1.featureData().forEach(());
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\FeatureCountTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */