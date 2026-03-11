/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import java.nio.file.FileSystems;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiConsumer;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.BuiltInMetadata;
/*     */ import net.minecraft.server.packs.PackResources;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.VanillaPackResources;
/*     */ import net.minecraft.server.packs.VanillaPackResourcesBuilder;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
/*     */ import net.minecraft.server.packs.repository.BuiltInPackSource;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackSource;
/*     */ import net.minecraft.world.level.validation.DirectoryValidator;
/*     */ 
/*     */ public class ClientPackSource
/*     */   extends BuiltInPackSource {
/*  28 */   private static final PackMetadataSection VERSION_METADATA_SECTION = new PackMetadataSection(
/*  29 */       (Component)Component.translatable("resourcePack.vanilla.description"), 
/*  30 */       SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES), 
/*  31 */       Optional.empty());
/*     */ 
/*     */   
/*  34 */   private static final BuiltInMetadata BUILT_IN_METADATA = BuiltInMetadata.of((MetadataSectionSerializer)PackMetadataSection.TYPE, VERSION_METADATA_SECTION);
/*     */   
/*  36 */   private static final Component VANILLA_NAME = (Component)Component.translatable("resourcePack.vanilla.name");
/*     */   
/*     */   public static final String HIGH_CONTRAST_PACK = "high_contrast";
/*     */   
/*  40 */   private static final Map<String, Component> SPECIAL_PACK_NAMES = (Map)Map.of("programmer_art", 
/*  41 */       Component.translatable("resourcePack.programmer_art.name"), "high_contrast", 
/*  42 */       Component.translatable("resourcePack.high_contrast.name"));
/*     */ 
/*     */   
/*  45 */   private static final ResourceLocation PACKS_DIR = new ResourceLocation("minecraft", "resourcepacks");
/*     */   
/*     */   @Nullable
/*     */   private final Path externalAssetDir;
/*     */   
/*     */   public ClientPackSource(Path $$0, DirectoryValidator $$1) {
/*  51 */     super(PackType.CLIENT_RESOURCES, createVanillaPackSource($$0), PACKS_DIR, $$1);
/*  52 */     this.externalAssetDir = findExplodedAssetPacks($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Path findExplodedAssetPacks(Path $$0) {
/*  57 */     if (SharedConstants.IS_RUNNING_IN_IDE && $$0.getFileSystem() == FileSystems.getDefault()) {
/*  58 */       Path $$1 = $$0.getParent().resolve("resourcepacks");
/*  59 */       if (Files.isDirectory($$1, new java.nio.file.LinkOption[0])) {
/*  60 */         return $$1;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static VanillaPackResources createVanillaPackSource(Path $$0) {
/*  69 */     VanillaPackResourcesBuilder $$1 = (new VanillaPackResourcesBuilder()).setMetadata(BUILT_IN_METADATA).exposeNamespace(new String[] { "minecraft", "realms" });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     return $$1
/*  76 */       .applyDevelopmentConfig()
/*  77 */       .pushJarResources()
/*  78 */       .pushAssetPath(PackType.CLIENT_RESOURCES, $$0)
/*  79 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getPackTitle(String $$0) {
/*  84 */     Component $$1 = SPECIAL_PACK_NAMES.get($$0);
/*  85 */     return ($$1 != null) ? $$1 : (Component)Component.literal($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Pack createVanillaPack(PackResources $$0) {
/*  91 */     return Pack.readMetaAndCreate("vanilla", VANILLA_NAME, true, fixedResources($$0), PackType.CLIENT_RESOURCES, Pack.Position.BOTTOM, PackSource.BUILT_IN);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Pack createBuiltinPack(String $$0, Pack.ResourcesSupplier $$1, Component $$2) {
/*  97 */     return Pack.readMetaAndCreate($$0, $$2, false, $$1, PackType.CLIENT_RESOURCES, Pack.Position.TOP, PackSource.BUILT_IN);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void populatePackList(BiConsumer<String, Function<String, Pack>> $$0) {
/* 102 */     super.populatePackList($$0);
/*     */     
/* 104 */     if (this.externalAssetDir != null)
/* 105 */       discoverPacksInPath(this.externalAssetDir, $$0); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\resources\ClientPackSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */