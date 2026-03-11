/*     */ package net.minecraft.client.renderer.texture;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.gui.screens.RealmsPopupScreen;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.screens.TitleScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class TextureManager implements PreparableReloadListener, Tickable, AutoCloseable {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  32 */   public static final ResourceLocation INTENTIONAL_MISSING_TEXTURE = new ResourceLocation("");
/*     */   
/*  34 */   private final Map<ResourceLocation, AbstractTexture> byPath = Maps.newHashMap();
/*     */   
/*  36 */   private final Set<Tickable> tickableTextures = Sets.newHashSet();
/*  37 */   private final Map<String, Integer> prefixRegister = Maps.newHashMap();
/*     */   
/*     */   private final ResourceManager resourceManager;
/*     */   
/*     */   public TextureManager(ResourceManager $$0) {
/*  42 */     this.resourceManager = $$0;
/*     */   }
/*     */   
/*     */   public void bindForSetup(ResourceLocation $$0) {
/*  46 */     if (!RenderSystem.isOnRenderThread()) {
/*  47 */       RenderSystem.recordRenderCall(() -> _bind($$0));
/*     */     }
/*     */     else {
/*     */       
/*  51 */       _bind($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _bind(ResourceLocation $$0) {
/*  56 */     AbstractTexture $$1 = this.byPath.get($$0);
/*     */ 
/*     */     
/*  59 */     if ($$1 == null) {
/*  60 */       $$1 = new SimpleTexture($$0);
/*  61 */       register($$0, $$1);
/*     */     } 
/*     */     
/*  64 */     $$1.bind();
/*     */   }
/*     */   
/*     */   public void register(ResourceLocation $$0, AbstractTexture $$1) {
/*  68 */     $$1 = loadTexture($$0, $$1);
/*     */     
/*  70 */     AbstractTexture $$2 = this.byPath.put($$0, $$1);
/*  71 */     if ($$2 != $$1) {
/*  72 */       if ($$2 != null && $$2 != MissingTextureAtlasSprite.getTexture()) {
/*  73 */         safeClose($$0, $$2);
/*     */       }
/*  75 */       if ($$1 instanceof Tickable) {
/*  76 */         this.tickableTextures.add((Tickable)$$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void safeClose(ResourceLocation $$0, AbstractTexture $$1) {
/*  82 */     if ($$1 != MissingTextureAtlasSprite.getTexture()) {
/*     */       
/*  84 */       this.tickableTextures.remove($$1);
/*     */       try {
/*  86 */         $$1.close();
/*  87 */       } catch (Exception $$2) {
/*  88 */         LOGGER.warn("Failed to close texture {}", $$0, $$2);
/*     */       } 
/*     */     } 
/*  91 */     $$1.releaseId();
/*     */   }
/*     */   
/*     */   private AbstractTexture loadTexture(ResourceLocation $$0, AbstractTexture $$1) {
/*     */     try {
/*  96 */       $$1.load(this.resourceManager);
/*  97 */       return $$1;
/*  98 */     } catch (IOException $$2) {
/*  99 */       if ($$0 != INTENTIONAL_MISSING_TEXTURE) {
/* 100 */         LOGGER.warn("Failed to load texture: {}", $$0, $$2);
/*     */       }
/* 102 */       return MissingTextureAtlasSprite.getTexture();
/* 103 */     } catch (Throwable $$3) {
/* 104 */       CrashReport $$4 = CrashReport.forThrowable($$3, "Registering texture");
/* 105 */       CrashReportCategory $$5 = $$4.addCategory("Resource location being registered");
/* 106 */       $$5.setDetail("Resource location", $$0);
/* 107 */       $$5.setDetail("Texture object class", () -> $$0.getClass().getName());
/*     */       
/* 109 */       throw new ReportedException($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public AbstractTexture getTexture(ResourceLocation $$0) {
/* 114 */     AbstractTexture $$1 = this.byPath.get($$0);
/*     */     
/* 116 */     if ($$1 == null) {
/* 117 */       $$1 = new SimpleTexture($$0);
/* 118 */       register($$0, $$1);
/*     */     } 
/*     */     
/* 121 */     return $$1;
/*     */   }
/*     */   
/*     */   public AbstractTexture getTexture(ResourceLocation $$0, AbstractTexture $$1) {
/* 125 */     return this.byPath.getOrDefault($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation register(String $$0, DynamicTexture $$1) {
/* 130 */     Integer $$2 = this.prefixRegister.get($$0);
/* 131 */     if ($$2 == null) {
/* 132 */       $$2 = Integer.valueOf(1);
/*     */     } else {
/* 134 */       Integer integer = $$2; $$2 = Integer.valueOf($$2.intValue() + 1);
/*     */     } 
/* 136 */     this.prefixRegister.put($$0, $$2);
/*     */     
/* 138 */     ResourceLocation $$3 = new ResourceLocation(String.format(Locale.ROOT, "dynamic/%s_%d", new Object[] { $$0, $$2 }));
/* 139 */     register($$3, $$1);
/*     */     
/* 141 */     return $$3;
/*     */   }
/*     */   
/*     */   public CompletableFuture<Void> preload(ResourceLocation $$0, Executor $$1) {
/* 145 */     if (!this.byPath.containsKey($$0)) {
/* 146 */       PreloadedTexture $$2 = new PreloadedTexture(this.resourceManager, $$0, $$1);
/* 147 */       this.byPath.put($$0, $$2);
/* 148 */       return $$2.getFuture().thenRunAsync(() -> register($$0, $$1), TextureManager::execute);
/*     */     } 
/* 150 */     return CompletableFuture.completedFuture(null);
/*     */   }
/*     */   
/*     */   private static void execute(Runnable $$0) {
/* 154 */     Minecraft.getInstance().execute(() -> {
/*     */           Objects.requireNonNull($$0);
/*     */           RenderSystem.recordRenderCall($$0::run);
/*     */         });
/*     */   } public void tick() {
/* 159 */     for (Tickable $$0 : this.tickableTextures) {
/* 160 */       $$0.tick();
/*     */     }
/*     */   }
/*     */   
/*     */   public void release(ResourceLocation $$0) {
/* 165 */     AbstractTexture $$1 = this.byPath.remove($$0);
/* 166 */     if ($$1 != null) {
/* 167 */       safeClose($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 173 */     this.byPath.forEach(this::safeClose);
/* 174 */     this.byPath.clear();
/* 175 */     this.tickableTextures.clear();
/* 176 */     this.prefixRegister.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/* 181 */     CompletableFuture<Void> $$6 = new CompletableFuture<>();
/*     */     
/* 183 */     Objects.requireNonNull($$0); TitleScreen.preloadResources(this, $$4).thenCompose($$0::wait).thenAcceptAsync($$3 -> {
/*     */           MissingTextureAtlasSprite.getTexture();
/*     */           
/*     */           RealmsPopupScreen.updateCarouselImages(this.resourceManager);
/*     */           Iterator<Map.Entry<ResourceLocation, AbstractTexture>> $$4 = this.byPath.entrySet().iterator();
/*     */           while ($$4.hasNext()) {
/*     */             Map.Entry<ResourceLocation, AbstractTexture> $$5 = $$4.next();
/*     */             ResourceLocation $$6 = $$5.getKey();
/*     */             AbstractTexture $$7 = $$5.getValue();
/*     */             if ($$7 == MissingTextureAtlasSprite.getTexture() && !$$6.equals(MissingTextureAtlasSprite.getLocation())) {
/*     */               $$4.remove();
/*     */               continue;
/*     */             } 
/*     */             $$7.reset(this, $$0, $$6, $$1);
/*     */           } 
/*     */           Minecraft.getInstance().tell(());
/*     */         }$$0 -> {
/*     */           Objects.requireNonNull($$0);
/*     */           RenderSystem.recordRenderCall($$0::run);
/*     */         });
/* 203 */     return $$6;
/*     */   }
/*     */   
/*     */   public void dumpAllSheets(Path $$0) {
/* 207 */     if (!RenderSystem.isOnRenderThread()) {
/* 208 */       RenderSystem.recordRenderCall(() -> _dumpAllSheets($$0));
/*     */     }
/*     */     else {
/*     */       
/* 212 */       _dumpAllSheets($$0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void _dumpAllSheets(Path $$0) {
/*     */     try {
/* 218 */       Files.createDirectories($$0, (FileAttribute<?>[])new FileAttribute[0]);
/* 219 */     } catch (IOException $$1) {
/* 220 */       LOGGER.error("Failed to create directory {}", $$0, $$1);
/*     */       
/*     */       return;
/*     */     } 
/* 224 */     this.byPath.forEach(($$1, $$2) -> {
/*     */           if ($$2 instanceof Dumpable) {
/*     */             Dumpable $$3 = (Dumpable)$$2; try {
/*     */               $$3.dumpContents($$1, $$0);
/* 228 */             } catch (IOException $$4) {
/*     */               LOGGER.error("Failed to dump texture {}", $$1, $$4);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\texture\TextureManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */