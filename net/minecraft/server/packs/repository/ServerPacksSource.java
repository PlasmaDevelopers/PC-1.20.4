/*    */ package net.minecraft.server.packs.repository;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import java.nio.file.Path;
/*    */ import java.util.Optional;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.SharedConstants;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.BuiltInMetadata;
/*    */ import net.minecraft.server.packs.FeatureFlagsMetadataSection;
/*    */ import net.minecraft.server.packs.PackResources;
/*    */ import net.minecraft.server.packs.PackType;
/*    */ import net.minecraft.server.packs.VanillaPackResources;
/*    */ import net.minecraft.server.packs.VanillaPackResourcesBuilder;
/*    */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*    */ import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
/*    */ import net.minecraft.world.flag.FeatureFlags;
/*    */ import net.minecraft.world.level.storage.LevelResource;
/*    */ import net.minecraft.world.level.storage.LevelStorageSource;
/*    */ import net.minecraft.world.level.validation.DirectoryValidator;
/*    */ 
/*    */ public class ServerPacksSource extends BuiltInPackSource {
/* 24 */   private static final PackMetadataSection VERSION_METADATA_SECTION = new PackMetadataSection(
/* 25 */       (Component)Component.translatable("dataPack.vanilla.description"), 
/* 26 */       SharedConstants.getCurrentVersion().getPackVersion(PackType.SERVER_DATA), 
/* 27 */       Optional.empty());
/*    */ 
/*    */   
/* 30 */   private static final FeatureFlagsMetadataSection FEATURE_FLAGS_METADATA_SECTION = new FeatureFlagsMetadataSection(FeatureFlags.DEFAULT_FLAGS);
/*    */ 
/*    */ 
/*    */   
/* 34 */   private static final BuiltInMetadata BUILT_IN_METADATA = BuiltInMetadata.of((MetadataSectionSerializer)PackMetadataSection.TYPE, VERSION_METADATA_SECTION, (MetadataSectionSerializer)FeatureFlagsMetadataSection.TYPE, FEATURE_FLAGS_METADATA_SECTION);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 39 */   private static final Component VANILLA_NAME = (Component)Component.translatable("dataPack.vanilla.name");
/*    */   
/* 41 */   private static final ResourceLocation PACKS_DIR = new ResourceLocation("minecraft", "datapacks");
/*    */   
/*    */   public ServerPacksSource(DirectoryValidator $$0) {
/* 44 */     super(PackType.SERVER_DATA, createVanillaPackSource(), PACKS_DIR, $$0);
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public static VanillaPackResources createVanillaPackSource() {
/* 49 */     return (new VanillaPackResourcesBuilder())
/* 50 */       .setMetadata(BUILT_IN_METADATA)
/* 51 */       .exposeNamespace(new String[] { "minecraft"
/* 52 */         }).applyDevelopmentConfig()
/* 53 */       .pushJarResources()
/* 54 */       .build();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Component getPackTitle(String $$0) {
/* 59 */     return (Component)Component.literal($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Pack createVanillaPack(PackResources $$0) {
/* 65 */     return Pack.readMetaAndCreate("vanilla", VANILLA_NAME, false, fixedResources($$0), PackType.SERVER_DATA, Pack.Position.BOTTOM, PackSource.BUILT_IN);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Pack createBuiltinPack(String $$0, Pack.ResourcesSupplier $$1, Component $$2) {
/* 71 */     return Pack.readMetaAndCreate($$0, $$2, false, $$1, PackType.SERVER_DATA, Pack.Position.TOP, PackSource.FEATURE);
/*    */   }
/*    */   
/*    */   public static PackRepository createPackRepository(Path $$0, DirectoryValidator $$1) {
/* 75 */     return new PackRepository(new RepositorySource[] { new ServerPacksSource($$1), new FolderRepositorySource($$0, PackType.SERVER_DATA, PackSource.WORLD, $$1) });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static PackRepository createVanillaTrustedRepository() {
/* 82 */     return new PackRepository(new RepositorySource[] { new ServerPacksSource(new DirectoryValidator($$0 -> true)) });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static PackRepository createPackRepository(LevelStorageSource.LevelStorageAccess $$0) {
/* 88 */     return createPackRepository($$0.getLevelPath(LevelResource.DATAPACK_DIR), $$0.parent().getWorldDirValidator());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\packs\repository\ServerPacksSource.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */