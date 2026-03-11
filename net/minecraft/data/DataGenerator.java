/*    */ package net.minecraft.data;
/*    */ 
/*    */ import com.google.common.base.Stopwatch;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.io.IOException;
/*    */ import java.nio.file.Path;
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.WorldVersion;
/*    */ import net.minecraft.server.Bootstrap;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class DataGenerator {
/* 18 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */   
/*    */   private final Path rootOutputFolder;
/*    */   
/*    */   private final PackOutput vanillaPackOutput;
/*    */   
/* 24 */   final Set<String> allProviderIds = new HashSet<>();
/* 25 */   final Map<String, DataProvider> providersToRun = new LinkedHashMap<>();
/*    */   private final WorldVersion version;
/*    */   private final boolean alwaysGenerate;
/*    */   
/*    */   static {
/* 30 */     Bootstrap.bootStrap();
/*    */   }
/*    */   
/*    */   public DataGenerator(Path $$0, WorldVersion $$1, boolean $$2) {
/* 34 */     this.rootOutputFolder = $$0;
/* 35 */     this.vanillaPackOutput = new PackOutput(this.rootOutputFolder);
/* 36 */     this.version = $$1;
/* 37 */     this.alwaysGenerate = $$2;
/*    */   }
/*    */   
/*    */   public void run() throws IOException {
/* 41 */     HashCache $$0 = new HashCache(this.rootOutputFolder, this.allProviderIds, this.version);
/*    */     
/* 43 */     Stopwatch $$1 = Stopwatch.createStarted();
/* 44 */     Stopwatch $$2 = Stopwatch.createUnstarted();
/* 45 */     this.providersToRun.forEach(($$2, $$3) -> {
/*    */           if (!this.alwaysGenerate && !$$0.shouldRunInThisVersion($$2)) {
/*    */             LOGGER.debug("Generator {} already run for version {}", $$2, this.version.getName()); return;
/*    */           } 
/*    */           LOGGER.info("Starting provider: {}", $$2);
/*    */           $$1.start();
/*    */           Objects.requireNonNull($$3);
/*    */           $$0.applyUpdate($$0.generateUpdate($$2, $$3::run).join());
/*    */           $$1.stop();
/*    */           LOGGER.info("{} finished after {} ms", $$2, Long.valueOf($$1.elapsed(TimeUnit.MILLISECONDS)));
/*    */           $$1.reset();
/*    */         });
/* 57 */     LOGGER.info("All providers took: {} ms", Long.valueOf($$1.elapsed(TimeUnit.MILLISECONDS)));
/*    */     
/* 59 */     $$0.purgeStaleAndWrite();
/*    */   }
/*    */   
/*    */   public PackGenerator getVanillaPack(boolean $$0) {
/* 63 */     return new PackGenerator($$0, "vanilla", this.vanillaPackOutput);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public PackGenerator getBuiltinDatapack(boolean $$0, String $$1) {
/* 69 */     Path $$2 = this.vanillaPackOutput.getOutputFolder(PackOutput.Target.DATA_PACK).resolve("minecraft").resolve("datapacks").resolve($$1);
/* 70 */     return new PackGenerator($$0, $$1, new PackOutput($$2));
/*    */   }
/*    */   
/*    */   public class PackGenerator {
/*    */     private final boolean toRun;
/*    */     private final String providerPrefix;
/*    */     private final PackOutput output;
/*    */     
/*    */     PackGenerator(boolean $$1, String $$2, PackOutput $$3) {
/* 79 */       this.toRun = $$1;
/* 80 */       this.providerPrefix = $$2;
/* 81 */       this.output = $$3;
/*    */     }
/*    */     
/*    */     public <T extends DataProvider> T addProvider(DataProvider.Factory<T> $$0) {
/* 85 */       T $$1 = $$0.create(this.output);
/* 86 */       String $$2 = this.providerPrefix + "/" + this.providerPrefix;
/* 87 */       if (!DataGenerator.this.allProviderIds.add($$2)) {
/* 88 */         throw new IllegalStateException("Duplicate provider: " + $$2);
/*    */       }
/* 90 */       if (this.toRun) {
/* 91 */         DataGenerator.this.providersToRun.put($$2, (DataProvider)$$1);
/*    */       }
/* 93 */       return $$1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\DataGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */