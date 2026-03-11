/*     */ package net.minecraft.server.packs.repository;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.VanillaPackResources;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class BuiltInPackSource implements RepositorySource {
/*  24 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final String VANILLA_ID = "vanilla";
/*     */   
/*     */   private final PackType packType;
/*     */   private final VanillaPackResources vanillaPack;
/*     */   private final ResourceLocation packDir;
/*     */   private final DirectoryValidator validator;
/*     */   
/*     */   public BuiltInPackSource(PackType $$0, VanillaPackResources $$1, ResourceLocation $$2, DirectoryValidator $$3) {
/*  34 */     this.packType = $$0;
/*  35 */     this.vanillaPack = $$1;
/*  36 */     this.packDir = $$2;
/*  37 */     this.validator = $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadPacks(Consumer<Pack> $$0) {
/*  42 */     Pack $$1 = createVanillaPack((PackResources)this.vanillaPack);
/*  43 */     if ($$1 != null) {
/*  44 */       $$0.accept($$1);
/*     */     }
/*  46 */     listBundledPacks($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VanillaPackResources getVanillaPack() {
/*  55 */     return this.vanillaPack;
/*     */   }
/*     */   
/*     */   private void listBundledPacks(Consumer<Pack> $$0) {
/*  59 */     Map<String, Function<String, Pack>> $$1 = new HashMap<>();
/*     */     
/*  61 */     Objects.requireNonNull($$1); populatePackList($$1::put);
/*     */     
/*  63 */     $$1.forEach(($$1, $$2) -> {
/*     */           Pack $$3 = $$2.apply($$1);
/*     */           if ($$3 != null) {
/*     */             $$0.accept($$3);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void populatePackList(BiConsumer<String, Function<String, Pack>> $$0) {
/*  72 */     this.vanillaPack.listRawPaths(this.packType, this.packDir, $$1 -> discoverPacksInPath($$1, $$0));
/*     */   }
/*     */   
/*     */   protected void discoverPacksInPath(@Nullable Path $$0, BiConsumer<String, Function<String, Pack>> $$1) {
/*  76 */     if ($$0 != null && Files.isDirectory($$0, new java.nio.file.LinkOption[0])) {
/*     */       try {
/*  78 */         FolderRepositorySource.discoverPacks($$0, this.validator, true, ($$1, $$2) -> $$0.accept(pathToId($$1), ()));
/*     */       
/*     */       }
/*  81 */       catch (IOException $$2) {
/*  82 */         LOGGER.warn("Failed to discover packs in {}", $$0, $$2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private static String pathToId(Path $$0) {
/*  88 */     return StringUtils.removeEnd($$0.getFileName().toString(), ".zip");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static Pack.ResourcesSupplier fixedResources(final PackResources instance) {
/*  95 */     return new Pack.ResourcesSupplier()
/*     */       {
/*     */         public PackResources openPrimary(String $$0) {
/*  98 */           return instance;
/*     */         }
/*     */ 
/*     */         
/*     */         public PackResources openFull(String $$0, Pack.Info $$1) {
/* 103 */           return instance;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected abstract Pack createVanillaPack(PackResources paramPackResources);
/*     */   
/*     */   protected abstract Component getPackTitle(String paramString);
/*     */   
/*     */   @Nullable
/*     */   protected abstract Pack createBuiltinPack(String paramString, Pack.ResourcesSupplier paramResourcesSupplier, Component paramComponent);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\BuiltInPackSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */