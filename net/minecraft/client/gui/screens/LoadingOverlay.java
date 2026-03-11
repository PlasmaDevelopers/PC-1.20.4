/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.platform.NativeImage;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.IntSupplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.resources.metadata.texture.TextureMetadataSection;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.PackType;
/*     */ import net.minecraft.server.packs.VanillaPackResources;
/*     */ import net.minecraft.server.packs.resources.IoSupplier;
/*     */ import net.minecraft.server.packs.resources.ReloadInstance;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class LoadingOverlay
/*     */   extends Overlay {
/*  30 */   static final ResourceLocation MOJANG_STUDIOS_LOGO_LOCATION = new ResourceLocation("textures/gui/title/mojangstudios.png");
/*  31 */   private static final int LOGO_BACKGROUND_COLOR = FastColor.ARGB32.color(255, 239, 50, 61);
/*  32 */   private static final int LOGO_BACKGROUND_COLOR_DARK = FastColor.ARGB32.color(255, 0, 0, 0);
/*     */   
/*     */   private static final IntSupplier BRAND_BACKGROUND = () -> ((Boolean)(Minecraft.getInstance()).options.darkMojangStudiosBackground().get()).booleanValue() ? LOGO_BACKGROUND_COLOR_DARK : LOGO_BACKGROUND_COLOR;
/*     */   
/*     */   private static final int LOGO_SCALE = 240;
/*     */   
/*     */   private static final float LOGO_QUARTER_FLOAT = 60.0F;
/*     */   
/*     */   private static final int LOGO_QUARTER = 60;
/*     */   private static final int LOGO_HALF = 120;
/*     */   private static final float LOGO_OVERLAP = 0.0625F;
/*     */   private static final float SMOOTHING = 0.95F;
/*     */   public static final long FADE_OUT_TIME = 1000L;
/*     */   public static final long FADE_IN_TIME = 500L;
/*     */   private final Minecraft minecraft;
/*     */   private final ReloadInstance reload;
/*     */   private final Consumer<Optional<Throwable>> onFinish;
/*     */   private final boolean fadeIn;
/*     */   private float currentProgress;
/*  51 */   private long fadeOutStart = -1L;
/*  52 */   private long fadeInStart = -1L;
/*     */   
/*     */   public LoadingOverlay(Minecraft $$0, ReloadInstance $$1, Consumer<Optional<Throwable>> $$2, boolean $$3) {
/*  55 */     this.minecraft = $$0;
/*  56 */     this.reload = $$1;
/*  57 */     this.onFinish = $$2;
/*  58 */     this.fadeIn = $$3;
/*     */   }
/*     */   
/*     */   public static void registerTextures(Minecraft $$0) {
/*  62 */     $$0.getTextureManager().register(MOJANG_STUDIOS_LOGO_LOCATION, (AbstractTexture)new LogoTexture());
/*     */   }
/*     */   
/*     */   private static int replaceAlpha(int $$0, int $$1) {
/*  66 */     return $$0 & 0xFFFFFF | $$1 << 24;
/*     */   }
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*     */     float $$17;
/*  71 */     int $$4 = $$0.guiWidth();
/*  72 */     int $$5 = $$0.guiHeight();
/*  73 */     long $$6 = Util.getMillis();
/*     */     
/*  75 */     if (this.fadeIn && this.fadeInStart == -1L) {
/*  76 */       this.fadeInStart = $$6;
/*     */     }
/*     */     
/*  79 */     float $$7 = (this.fadeOutStart > -1L) ? ((float)($$6 - this.fadeOutStart) / 1000.0F) : -1.0F;
/*  80 */     float $$8 = (this.fadeInStart > -1L) ? ((float)($$6 - this.fadeInStart) / 500.0F) : -1.0F;
/*     */ 
/*     */     
/*  83 */     if ($$7 >= 1.0F) {
/*  84 */       if (this.minecraft.screen != null) {
/*  85 */         this.minecraft.screen.render($$0, 0, 0, $$3);
/*     */       }
/*  87 */       int $$9 = Mth.ceil((1.0F - Mth.clamp($$7 - 1.0F, 0.0F, 1.0F)) * 255.0F);
/*  88 */       $$0.fill(RenderType.guiOverlay(), 0, 0, $$4, $$5, replaceAlpha(BRAND_BACKGROUND.getAsInt(), $$9));
/*  89 */       float $$10 = 1.0F - Mth.clamp($$7 - 1.0F, 0.0F, 1.0F);
/*  90 */     } else if (this.fadeIn) {
/*  91 */       if (this.minecraft.screen != null && $$8 < 1.0F) {
/*  92 */         this.minecraft.screen.render($$0, $$1, $$2, $$3);
/*     */       }
/*  94 */       int $$11 = Mth.ceil(Mth.clamp($$8, 0.15D, 1.0D) * 255.0D);
/*  95 */       $$0.fill(RenderType.guiOverlay(), 0, 0, $$4, $$5, replaceAlpha(BRAND_BACKGROUND.getAsInt(), $$11));
/*  96 */       float $$12 = Mth.clamp($$8, 0.0F, 1.0F);
/*     */     } else {
/*  98 */       int $$13 = BRAND_BACKGROUND.getAsInt();
/*  99 */       float $$14 = ($$13 >> 16 & 0xFF) / 255.0F;
/* 100 */       float $$15 = ($$13 >> 8 & 0xFF) / 255.0F;
/* 101 */       float $$16 = ($$13 & 0xFF) / 255.0F;
/*     */ 
/*     */       
/* 104 */       GlStateManager._clearColor($$14, $$15, $$16, 1.0F);
/* 105 */       GlStateManager._clear(16384, Minecraft.ON_OSX);
/* 106 */       $$17 = 1.0F;
/*     */     } 
/*     */     
/* 109 */     int $$18 = (int)($$0.guiWidth() * 0.5D);
/* 110 */     int $$19 = (int)($$0.guiHeight() * 0.5D);
/*     */     
/* 112 */     double $$20 = Math.min($$0.guiWidth() * 0.75D, $$0.guiHeight()) * 0.25D;
/* 113 */     int $$21 = (int)($$20 * 0.5D);
/* 114 */     double $$22 = $$20 * 4.0D;
/* 115 */     int $$23 = (int)($$22 * 0.5D);
/*     */     
/* 117 */     RenderSystem.disableDepthTest();
/* 118 */     RenderSystem.depthMask(false);
/*     */     
/* 120 */     RenderSystem.enableBlend();
/* 121 */     RenderSystem.blendFunc(770, 1);
/*     */     
/* 123 */     $$0.setColor(1.0F, 1.0F, 1.0F, $$17);
/* 124 */     $$0.blit(MOJANG_STUDIOS_LOGO_LOCATION, $$18 - $$23, $$19 - $$21, $$23, (int)$$20, -0.0625F, 0.0F, 120, 60, 120, 120);
/* 125 */     $$0.blit(MOJANG_STUDIOS_LOGO_LOCATION, $$18, $$19 - $$21, $$23, (int)$$20, 0.0625F, 60.0F, 120, 60, 120, 120);
/* 126 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/* 128 */     RenderSystem.defaultBlendFunc();
/* 129 */     RenderSystem.disableBlend();
/* 130 */     RenderSystem.depthMask(true);
/* 131 */     RenderSystem.enableDepthTest();
/*     */     
/* 133 */     int $$24 = (int)($$0.guiHeight() * 0.8325D);
/*     */     
/* 135 */     float $$25 = this.reload.getActualProgress();
/* 136 */     this.currentProgress = Mth.clamp(this.currentProgress * 0.95F + $$25 * 0.050000012F, 0.0F, 1.0F);
/*     */     
/* 138 */     if ($$7 < 1.0F) {
/* 139 */       drawProgressBar($$0, $$4 / 2 - $$23, $$24 - 5, $$4 / 2 + $$23, $$24 + 5, 1.0F - Mth.clamp($$7, 0.0F, 1.0F));
/*     */     }
/*     */     
/* 142 */     if ($$7 >= 2.0F) {
/* 143 */       this.minecraft.setOverlay(null);
/*     */     }
/*     */     
/* 146 */     if (this.fadeOutStart == -1L && this.reload.isDone() && (!this.fadeIn || $$8 >= 2.0F)) {
/*     */       try {
/* 148 */         this.reload.checkExceptions();
/* 149 */         this.onFinish.accept(Optional.empty());
/* 150 */       } catch (Throwable $$26) {
/* 151 */         this.onFinish.accept(Optional.of($$26));
/*     */       } 
/* 153 */       this.fadeOutStart = Util.getMillis();
/* 154 */       if (this.minecraft.screen != null)
/*     */       {
/* 156 */         this.minecraft.screen.init(this.minecraft, $$0.guiWidth(), $$0.guiHeight());
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void drawProgressBar(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, float $$5) {
/* 162 */     int $$6 = Mth.ceil(($$3 - $$1 - 2) * this.currentProgress);
/* 163 */     int $$7 = Math.round($$5 * 255.0F);
/* 164 */     int $$8 = FastColor.ARGB32.color($$7, 255, 255, 255);
/*     */ 
/*     */     
/* 167 */     $$0.fill($$1 + 2, $$2 + 2, $$1 + $$6, $$4 - 2, $$8);
/*     */     
/* 169 */     $$0.fill($$1 + 1, $$2, $$3 - 1, $$2 + 1, $$8);
/* 170 */     $$0.fill($$1 + 1, $$4, $$3 - 1, $$4 - 1, $$8);
/* 171 */     $$0.fill($$1, $$2, $$1 + 1, $$4, $$8);
/* 172 */     $$0.fill($$3, $$2, $$3 - 1, $$4, $$8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 177 */     return true;
/*     */   }
/*     */   
/*     */   private static class LogoTexture extends SimpleTexture {
/*     */     public LogoTexture() {
/* 182 */       super(LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION);
/*     */     }
/*     */ 
/*     */     
/*     */     protected SimpleTexture.TextureImage getTextureImage(ResourceManager $$0) {
/* 187 */       VanillaPackResources $$1 = Minecraft.getInstance().getVanillaPackResources();
/* 188 */       IoSupplier<InputStream> $$2 = $$1.getResource(PackType.CLIENT_RESOURCES, LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION);
/* 189 */       if ($$2 == null)
/* 190 */         return new SimpleTexture.TextureImage(new FileNotFoundException(LoadingOverlay.MOJANG_STUDIOS_LOGO_LOCATION.toString())); 
/*     */       
/* 192 */       try { InputStream $$3 = (InputStream)$$2.get(); 
/* 193 */         try { SimpleTexture.TextureImage textureImage = new SimpleTexture.TextureImage(new TextureMetadataSection(true, true), NativeImage.read($$3));
/* 194 */           if ($$3 != null) $$3.close();  return textureImage; } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$4)
/* 195 */       { return new SimpleTexture.TextureImage($$4); }
/*     */     
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\LoadingOverlay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */