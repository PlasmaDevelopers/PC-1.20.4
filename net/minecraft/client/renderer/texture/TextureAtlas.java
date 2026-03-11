/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.TextureUtil;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.world.inventory.InventoryMenu;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TextureAtlas
/*     */   extends AbstractTexture
/*     */   implements Dumpable, Tickable {
/*  26 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  31 */   public static final ResourceLocation LOCATION_BLOCKS = InventoryMenu.BLOCK_ATLAS;
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*  36 */   public static final ResourceLocation LOCATION_PARTICLES = new ResourceLocation("textures/atlas/particles.png");
/*     */ 
/*     */   
/*  39 */   private List<SpriteContents> sprites = List.of();
/*  40 */   private List<TextureAtlasSprite.Ticker> animatedTextures = List.of();
/*  41 */   private Map<ResourceLocation, TextureAtlasSprite> texturesByName = Map.of();
/*     */   
/*     */   @Nullable
/*     */   private TextureAtlasSprite missingSprite;
/*     */   private final ResourceLocation location;
/*     */   private final int maxSupportedTextureSize;
/*     */   private int width;
/*     */   private int height;
/*     */   private int mipLevel;
/*     */   
/*     */   public TextureAtlas(ResourceLocation $$0) {
/*  52 */     this.location = $$0;
/*  53 */     this.maxSupportedTextureSize = RenderSystem.maxSupportedTextureSize();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(ResourceManager $$0) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void upload(SpriteLoader.Preparations $$0) {
/*  63 */     LOGGER.info("Created: {}x{}x{} {}-atlas", new Object[] { Integer.valueOf($$0.width()), Integer.valueOf($$0.height()), Integer.valueOf($$0.mipLevel()), this.location });
/*  64 */     TextureUtil.prepareImage(getId(), $$0.mipLevel(), $$0.width(), $$0.height());
/*  65 */     this.width = $$0.width();
/*  66 */     this.height = $$0.height();
/*  67 */     this.mipLevel = $$0.mipLevel();
/*     */     
/*  69 */     clearTextureData();
/*     */     
/*  71 */     this.texturesByName = Map.copyOf($$0.regions());
/*  72 */     this.missingSprite = this.texturesByName.get(MissingTextureAtlasSprite.getLocation());
/*  73 */     if (this.missingSprite == null) {
/*  74 */       throw new IllegalStateException("Atlas '" + this.location + "' (" + this.texturesByName.size() + " sprites) has no missing texture sprite");
/*     */     }
/*     */     
/*  77 */     List<SpriteContents> $$1 = new ArrayList<>();
/*  78 */     List<TextureAtlasSprite.Ticker> $$2 = new ArrayList<>();
/*     */     
/*  80 */     for (TextureAtlasSprite $$3 : $$0.regions().values()) {
/*  81 */       $$1.add($$3.contents());
/*     */       
/*     */       try {
/*  84 */         $$3.uploadFirstFrame();
/*  85 */       } catch (Throwable $$4) {
/*  86 */         CrashReport $$5 = CrashReport.forThrowable($$4, "Stitching texture atlas");
/*  87 */         CrashReportCategory $$6 = $$5.addCategory("Texture being stitched together");
/*     */         
/*  89 */         $$6.setDetail("Atlas path", this.location);
/*  90 */         $$6.setDetail("Sprite", $$3);
/*     */         
/*  92 */         throw new ReportedException($$5);
/*     */       } 
/*     */       
/*  95 */       TextureAtlasSprite.Ticker $$7 = $$3.createTicker();
/*  96 */       if ($$7 != null) {
/*  97 */         $$2.add($$7);
/*     */       }
/*     */     } 
/*     */     
/* 101 */     this.sprites = List.copyOf($$1);
/* 102 */     this.animatedTextures = List.copyOf($$2);
/*     */   }
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
/*     */   public void dumpContents(ResourceLocation $$0, Path $$1) throws IOException {
/* 117 */     String $$2 = $$0.toDebugFileName();
/* 118 */     TextureUtil.writeAsPNG($$1, $$2, getId(), this.mipLevel, this.width, this.height);
/* 119 */     dumpSpriteNames($$1, $$2, this.texturesByName);
/*     */   }
/*     */   
/*     */   private static void dumpSpriteNames(Path $$0, String $$1, Map<ResourceLocation, TextureAtlasSprite> $$2) {
/* 123 */     Path $$3 = $$0.resolve($$1 + ".txt"); 
/* 124 */     try { Writer $$4 = Files.newBufferedWriter($$3, new java.nio.file.OpenOption[0]); 
/* 125 */       try { for (Map.Entry<ResourceLocation, TextureAtlasSprite> $$5 : (Iterable<Map.Entry<ResourceLocation, TextureAtlasSprite>>)$$2.entrySet().stream().sorted(Map.Entry.comparingByKey()).toList()) {
/* 126 */           TextureAtlasSprite $$6 = $$5.getValue();
/* 127 */           $$4.write(String.format(Locale.ROOT, "%s\tx=%d\ty=%d\tw=%d\th=%d%n", new Object[] { $$5.getKey(), Integer.valueOf($$6.getX()), Integer.valueOf($$6.getY()), Integer.valueOf($$6.contents().width()), Integer.valueOf($$6.contents().height()) }));
/*     */         } 
/* 129 */         if ($$4 != null) $$4.close();  } catch (Throwable throwable) { if ($$4 != null) try { $$4.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$7)
/* 130 */     { LOGGER.warn("Failed to write file {}", $$3, $$7); }
/*     */   
/*     */   }
/*     */   
/*     */   public void cycleAnimationFrames() {
/* 135 */     bind();
/* 136 */     for (TextureAtlasSprite.Ticker $$0 : this.animatedTextures) {
/* 137 */       $$0.tickAndUpload();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 143 */     if (!RenderSystem.isOnRenderThread()) {
/* 144 */       RenderSystem.recordRenderCall(this::cycleAnimationFrames);
/*     */     } else {
/* 146 */       cycleAnimationFrames();
/*     */     } 
/*     */   }
/*     */   
/*     */   public TextureAtlasSprite getSprite(ResourceLocation $$0) {
/* 151 */     TextureAtlasSprite $$1 = this.texturesByName.getOrDefault($$0, this.missingSprite);
/* 152 */     if ($$1 == null) {
/* 153 */       throw new IllegalStateException("Tried to lookup sprite, but atlas is not initialized");
/*     */     }
/* 155 */     return $$1;
/*     */   }
/*     */   
/*     */   public void clearTextureData() {
/* 159 */     this.sprites.forEach(SpriteContents::close);
/* 160 */     this.animatedTextures.forEach(TextureAtlasSprite.Ticker::close);
/*     */     
/* 162 */     this.sprites = List.of();
/* 163 */     this.animatedTextures = List.of();
/* 164 */     this.texturesByName = Map.of();
/* 165 */     this.missingSprite = null;
/*     */   }
/*     */   
/*     */   public ResourceLocation location() {
/* 169 */     return this.location;
/*     */   }
/*     */   
/*     */   public int maxSupportedTextureSize() {
/* 173 */     return this.maxSupportedTextureSize;
/*     */   }
/*     */   
/*     */   int getWidth() {
/* 177 */     return this.width;
/*     */   }
/*     */   
/*     */   int getHeight() {
/* 181 */     return this.height;
/*     */   }
/*     */   
/*     */   public void updateFilter(SpriteLoader.Preparations $$0) {
/* 185 */     setFilter(false, ($$0.mipLevel() > 0));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\TextureAtlas.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */