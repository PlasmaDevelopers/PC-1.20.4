/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteResourceLoader;
/*     */ import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
/*     */ import net.minecraft.client.resources.metadata.animation.AnimationMetadataSection;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SpriteLoader {
/*  30 */   public static final Set<MetadataSectionSerializer<?>> DEFAULT_METADATA_SECTIONS = (Set)Set.of(AnimationMetadataSection.SERIALIZER);
/*  31 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private final ResourceLocation location;
/*     */   private final int maxSupportedTextureSize;
/*     */   private final int minWidth;
/*     */   private final int minHeight;
/*     */   
/*     */   public SpriteLoader(ResourceLocation $$0, int $$1, int $$2, int $$3) {
/*  39 */     this.location = $$0;
/*  40 */     this.maxSupportedTextureSize = $$1;
/*  41 */     this.minWidth = $$2;
/*  42 */     this.minHeight = $$3;
/*     */   }
/*     */   
/*     */   public static SpriteLoader create(TextureAtlas $$0) {
/*  46 */     return new SpriteLoader($$0.location(), $$0.maxSupportedTextureSize(), $$0.getWidth(), $$0.getHeight());
/*     */   } public Preparations stitch(List<SpriteContents> $$0, int $$1, Executor $$2) {
/*     */     int $$12;
/*     */     CompletableFuture<Void> $$21;
/*  50 */     int $$3 = this.maxSupportedTextureSize;
/*  51 */     Stitcher<SpriteContents> $$4 = new Stitcher<>($$3, $$3, $$1);
/*     */     
/*  53 */     int $$5 = Integer.MAX_VALUE;
/*  54 */     int $$6 = 1 << $$1;
/*     */     
/*  56 */     for (SpriteContents $$7 : $$0) {
/*  57 */       $$5 = Math.min($$5, Math.min($$7.width(), $$7.height()));
/*  58 */       int $$8 = Math.min(Integer.lowestOneBit($$7.width()), Integer.lowestOneBit($$7.height()));
/*  59 */       if ($$8 < $$6) {
/*  60 */         LOGGER.warn("Texture {} with size {}x{} limits mip level from {} to {}", new Object[] { $$7.name(), Integer.valueOf($$7.width()), Integer.valueOf($$7.height()), Integer.valueOf(Mth.log2($$6)), Integer.valueOf(Mth.log2($$8)) });
/*  61 */         $$6 = $$8;
/*     */       } 
/*     */       
/*  64 */       $$4.registerSprite($$7);
/*     */     } 
/*     */ 
/*     */     
/*  68 */     int $$9 = Math.min($$5, $$6);
/*  69 */     int $$10 = Mth.log2($$9);
/*     */ 
/*     */     
/*  72 */     if ($$10 < $$1) {
/*  73 */       LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", new Object[] { this.location, Integer.valueOf($$1), Integer.valueOf($$10), Integer.valueOf($$9) });
/*  74 */       int $$11 = $$10;
/*     */     } else {
/*  76 */       $$12 = $$1;
/*     */     } 
/*     */     
/*     */     try {
/*  80 */       $$4.stitch();
/*  81 */     } catch (StitcherException $$13) {
/*  82 */       CrashReport $$14 = CrashReport.forThrowable($$13, "Stitching");
/*  83 */       CrashReportCategory $$15 = $$14.addCategory("Stitcher");
/*  84 */       $$15.setDetail("Sprites", $$13.getAllSprites().stream().map($$0 -> String.format(Locale.ROOT, "%s[%dx%d]", new Object[] { $$0.name(), Integer.valueOf($$0.width()), Integer.valueOf($$0.height()) })).collect(Collectors.joining(",")));
/*  85 */       $$15.setDetail("Max Texture Size", Integer.valueOf($$3));
/*  86 */       throw new ReportedException($$14);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  91 */     int $$16 = Math.max($$4.getWidth(), this.minWidth);
/*  92 */     int $$17 = Math.max($$4.getHeight(), this.minHeight);
/*  93 */     Map<ResourceLocation, TextureAtlasSprite> $$18 = getStitchedSprites($$4, $$16, $$17);
/*  94 */     TextureAtlasSprite $$19 = $$18.get(MissingTextureAtlasSprite.getLocation());
/*     */ 
/*     */     
/*  97 */     if ($$12 > 0) {
/*  98 */       CompletableFuture<Void> $$20 = CompletableFuture.runAsync(() -> $$0.values().forEach(()), $$2);
/*     */     } else {
/* 100 */       $$21 = CompletableFuture.completedFuture(null);
/*     */     } 
/*     */     
/* 103 */     return new Preparations($$16, $$17, $$12, $$19, $$18, $$21);
/*     */   }
/*     */   
/*     */   public static CompletableFuture<List<SpriteContents>> runSpriteSuppliers(SpriteResourceLoader $$0, List<Function<SpriteResourceLoader, SpriteContents>> $$1, Executor $$2) {
/* 107 */     List<CompletableFuture<SpriteContents>> $$3 = $$1.stream().map($$2 -> CompletableFuture.supplyAsync((), $$1)).toList();
/* 108 */     return Util.sequence($$3).thenApply($$0 -> $$0.stream().filter(Objects::nonNull).toList());
/*     */   }
/*     */   
/*     */   public CompletableFuture<Preparations> loadAndStitch(ResourceManager $$0, ResourceLocation $$1, int $$2, Executor $$3) {
/* 112 */     return loadAndStitch($$0, $$1, $$2, $$3, DEFAULT_METADATA_SECTIONS);
/*     */   }
/*     */   
/*     */   public CompletableFuture<Preparations> loadAndStitch(ResourceManager $$0, ResourceLocation $$1, int $$2, Executor $$3, Collection<MetadataSectionSerializer<?>> $$4) {
/* 116 */     SpriteResourceLoader $$5 = SpriteResourceLoader.create($$4);
/* 117 */     return CompletableFuture.supplyAsync(() -> SpriteSourceList.load($$0, $$1).list($$0), $$3)
/* 118 */       .thenCompose($$2 -> runSpriteSuppliers($$0, $$2, $$1))
/* 119 */       .thenApply($$2 -> stitch($$2, $$0, $$1));
/*     */   }
/*     */   
/*     */   private Map<ResourceLocation, TextureAtlasSprite> getStitchedSprites(Stitcher<SpriteContents> $$0, int $$1, int $$2) {
/* 123 */     Map<ResourceLocation, TextureAtlasSprite> $$3 = new HashMap<>();
/*     */     
/* 125 */     $$0.gatherSprites(($$3, $$4, $$5) -> $$0.put($$3.name(), new TextureAtlasSprite(this.location, $$3, $$1, $$2, $$4, $$5)));
/*     */ 
/*     */ 
/*     */     
/* 129 */     return $$3;
/*     */   }
/*     */   public static final class Preparations extends Record { private final int width; private final int height; private final int mipLevel; private final TextureAtlasSprite missing; private final Map<ResourceLocation, TextureAtlasSprite> regions; private final CompletableFuture<Void> readyForUpload;
/* 132 */     public Preparations(int $$0, int $$1, int $$2, TextureAtlasSprite $$3, Map<ResourceLocation, TextureAtlasSprite> $$4, CompletableFuture<Void> $$5) { this.width = $$0; this.height = $$1; this.mipLevel = $$2; this.missing = $$3; this.regions = $$4; this.readyForUpload = $$5; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 132 */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations; } public int width() { return this.width; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #132	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/client/renderer/texture/SpriteLoader$Preparations;
/* 132 */       //   0	8	1	$$0	Ljava/lang/Object; } public int height() { return this.height; } public int mipLevel() { return this.mipLevel; } public TextureAtlasSprite missing() { return this.missing; } public Map<ResourceLocation, TextureAtlasSprite> regions() { return this.regions; } public CompletableFuture<Void> readyForUpload() { return this.readyForUpload; }
/*     */      public CompletableFuture<Preparations> waitForUpload() {
/* 134 */       return this.readyForUpload.thenApply($$0 -> this);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\SpriteLoader.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */