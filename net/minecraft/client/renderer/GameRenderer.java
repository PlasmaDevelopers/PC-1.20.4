/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import com.mojang.blaze3d.platform.GlStateManager;
/*      */ import com.mojang.blaze3d.platform.Lighting;
/*      */ import com.mojang.blaze3d.platform.NativeImage;
/*      */ import com.mojang.blaze3d.platform.Window;
/*      */ import com.mojang.blaze3d.shaders.Program;
/*      */ import com.mojang.blaze3d.systems.RenderSystem;
/*      */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*      */ import com.mojang.blaze3d.vertex.PoseStack;
/*      */ import com.mojang.blaze3d.vertex.VertexFormat;
/*      */ import com.mojang.blaze3d.vertex.VertexSorting;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.math.Axis;
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.function.Consumer;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.CrashReport;
/*      */ import net.minecraft.CrashReportCategory;
/*      */ import net.minecraft.ReportedException;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.Camera;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.Screenshot;
/*      */ import net.minecraft.client.gui.GuiGraphics;
/*      */ import net.minecraft.client.gui.MapRenderer;
/*      */ import net.minecraft.client.player.AbstractClientPlayer;
/*      */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*      */ import net.minecraft.client.server.IntegratedServer;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.Registry;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*      */ import net.minecraft.server.packs.resources.Resource;
/*      */ import net.minecraft.server.packs.resources.ResourceManager;
/*      */ import net.minecraft.server.packs.resources.ResourceProvider;
/*      */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.util.profiling.ProfilerFiller;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*      */ import net.minecraft.world.item.ItemDisplayContext;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*      */ import net.minecraft.world.level.material.FogType;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.BlockHitResult;
/*      */ import net.minecraft.world.phys.EntityHitResult;
/*      */ import net.minecraft.world.phys.HitResult;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import org.joml.Matrix3f;
/*      */ import org.joml.Matrix3fc;
/*      */ import org.joml.Matrix4f;
/*      */ import org.joml.Matrix4fc;
/*      */ import org.joml.Vector3f;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class GameRenderer
/*      */   implements AutoCloseable
/*      */ {
/*   88 */   private static final ResourceLocation NAUSEA_LOCATION = new ResourceLocation("textures/misc/nausea.png");
/*      */   
/*   90 */   static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final boolean DEPTH_BUFFER_DEBUG = false;
/*      */   
/*      */   public static final float PROJECTION_Z_NEAR = 0.05F;
/*      */   
/*      */   private static final float GUI_Z_NEAR = 1000.0F;
/*      */   
/*      */   private static final int ENTITY_INTERACTION_RANGE = 3;
/*      */   final Minecraft minecraft;
/*      */   private final ResourceManager resourceManager;
/*  101 */   private final RandomSource random = RandomSource.create();
/*      */   
/*      */   private float renderDistance;
/*      */   
/*      */   public final ItemInHandRenderer itemInHandRenderer;
/*      */   
/*      */   private final MapRenderer mapRenderer;
/*      */   
/*      */   private final RenderBuffers renderBuffers;
/*      */   
/*      */   private int confusionAnimationTick;
/*      */   
/*      */   private float fov;
/*      */   
/*      */   private float oldFov;
/*      */   private float darkenWorldAmount;
/*      */   private float darkenWorldAmountO;
/*      */   private boolean renderHand = true;
/*      */   private boolean renderBlockOutline = true;
/*      */   private long lastScreenshotAttempt;
/*      */   private boolean hasWorldScreenshot;
/*  122 */   private long lastActiveTime = Util.getMillis();
/*      */ 
/*      */   
/*      */   private final LightTexture lightTexture;
/*      */   
/*  127 */   private final OverlayTexture overlayTexture = new OverlayTexture();
/*      */ 
/*      */   
/*      */   private boolean panoramicMode;
/*      */ 
/*      */   
/*  133 */   private float zoom = 1.0F;
/*      */   
/*      */   private float zoomX;
/*      */   
/*      */   private float zoomY;
/*      */   
/*      */   public static final int ITEM_ACTIVATION_ANIMATION_LENGTH = 40;
/*      */   
/*      */   @Nullable
/*      */   private ItemStack itemActivationItem;
/*      */   private int itemActivationTicks;
/*      */   private float itemActivationOffX;
/*      */   private float itemActivationOffY;
/*      */   @Nullable
/*      */   PostChain postEffect;
/*  148 */   static final ResourceLocation[] EFFECTS = new ResourceLocation[] { new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), new ResourceLocation("shaders/post/color_convolve.json"), new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), new ResourceLocation("shaders/post/spider.json") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  174 */   public static final int EFFECT_NONE = EFFECTS.length;
/*  175 */   int effectIndex = EFFECT_NONE;
/*      */   private boolean effectActive;
/*  177 */   private final Camera mainCamera = new Camera();
/*      */   
/*      */   public ShaderInstance blitShader;
/*      */   
/*  181 */   private final Map<String, ShaderInstance> shaders = Maps.newHashMap();
/*      */   
/*      */   @Nullable
/*      */   private static ShaderInstance positionShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionColorShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionColorTexShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionTexShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionTexColorShader;
/*      */   @Nullable
/*      */   private static ShaderInstance particleShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionColorLightmapShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionColorTexLightmapShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionTexColorNormalShader;
/*      */   @Nullable
/*      */   private static ShaderInstance positionTexLightmapColorShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeSolidShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeCutoutMippedShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeCutoutShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTranslucentShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTranslucentMovingBlockShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeArmorCutoutNoCullShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntitySolidShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityCutoutShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityCutoutNoCullShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityCutoutNoCullZOffsetShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeItemEntityTranslucentCullShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityTranslucentCullShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityTranslucentShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityTranslucentEmissiveShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntitySmoothCutoutShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeBeaconBeamShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityDecalShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityNoOutlineShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityShadowShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityAlphaShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEyesShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEnergySwirlShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeBreezeWindShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeLeashShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeWaterMaskShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeOutlineShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeArmorGlintShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeArmorEntityGlintShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGlintTranslucentShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGlintShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGlintDirectShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityGlintShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEntityGlintDirectShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextBackgroundShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextIntensityShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextSeeThroughShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextBackgroundSeeThroughShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTextIntensitySeeThroughShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeLightningShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeTripwireShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEndPortalShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeEndGatewayShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeLinesShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeCrumblingShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGuiShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGuiOverlayShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGuiTextHighlightShader;
/*      */   @Nullable
/*      */   private static ShaderInstance rendertypeGuiGhostRecipeOverlayShader;
/*      */   
/*      */   public GameRenderer(Minecraft $$0, ItemInHandRenderer $$1, ResourceManager $$2, RenderBuffers $$3) {
/*  303 */     this.minecraft = $$0;
/*  304 */     this.resourceManager = $$2;
/*  305 */     this.itemInHandRenderer = $$1;
/*  306 */     this.mapRenderer = new MapRenderer($$0.getTextureManager());
/*  307 */     this.lightTexture = new LightTexture(this, $$0);
/*  308 */     this.renderBuffers = $$3;
/*      */     
/*  310 */     this.postEffect = null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void close() {
/*  315 */     this.lightTexture.close();
/*  316 */     this.mapRenderer.close();
/*  317 */     this.overlayTexture.close();
/*  318 */     shutdownEffect();
/*  319 */     shutdownShaders();
/*  320 */     if (this.blitShader != null) {
/*  321 */       this.blitShader.close();
/*      */     }
/*      */   }
/*      */   
/*      */   public void setRenderHand(boolean $$0) {
/*  326 */     this.renderHand = $$0;
/*      */   }
/*      */   
/*      */   public void setRenderBlockOutline(boolean $$0) {
/*  330 */     this.renderBlockOutline = $$0;
/*      */   }
/*      */   
/*      */   public void setPanoramicMode(boolean $$0) {
/*  334 */     this.panoramicMode = $$0;
/*      */   }
/*      */   
/*      */   public boolean isPanoramicMode() {
/*  338 */     return this.panoramicMode;
/*      */   }
/*      */   
/*      */   public void shutdownEffect() {
/*  342 */     if (this.postEffect != null) {
/*  343 */       this.postEffect.close();
/*      */     }
/*  345 */     this.postEffect = null;
/*  346 */     this.effectIndex = EFFECT_NONE;
/*      */   }
/*      */   
/*      */   public void togglePostEffect() {
/*  350 */     this.effectActive = !this.effectActive;
/*      */   }
/*      */   
/*      */   public void checkEntityPostEffect(@Nullable Entity $$0) {
/*  354 */     if (this.postEffect != null) {
/*  355 */       this.postEffect.close();
/*      */     }
/*  357 */     this.postEffect = null;
/*      */     
/*  359 */     if ($$0 instanceof net.minecraft.world.entity.monster.Creeper) {
/*  360 */       loadEffect(new ResourceLocation("shaders/post/creeper.json"));
/*  361 */     } else if ($$0 instanceof net.minecraft.world.entity.monster.Spider) {
/*  362 */       loadEffect(new ResourceLocation("shaders/post/spider.json"));
/*  363 */     } else if ($$0 instanceof net.minecraft.world.entity.monster.EnderMan) {
/*  364 */       loadEffect(new ResourceLocation("shaders/post/invert.json"));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void cycleEffect() {
/*  369 */     if (!(this.minecraft.getCameraEntity() instanceof Player)) {
/*      */       return;
/*      */     }
/*      */     
/*  373 */     if (this.postEffect != null) {
/*  374 */       this.postEffect.close();
/*      */     }
/*      */ 
/*      */     
/*  378 */     this.effectIndex = (this.effectIndex + 1) % (EFFECTS.length + 1);
/*  379 */     if (this.effectIndex == EFFECT_NONE) {
/*  380 */       this.postEffect = null;
/*      */     } else {
/*  382 */       loadEffect(EFFECTS[this.effectIndex]);
/*      */     } 
/*      */   }
/*      */   
/*      */   void loadEffect(ResourceLocation $$0) {
/*  387 */     if (this.postEffect != null) {
/*  388 */       this.postEffect.close();
/*      */     }
/*      */     
/*      */     try {
/*  392 */       this.postEffect = new PostChain(this.minecraft.getTextureManager(), this.resourceManager, this.minecraft.getMainRenderTarget(), $$0);
/*  393 */       this.postEffect.resize(this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
/*  394 */       this.effectActive = true;
/*  395 */     } catch (IOException $$1) {
/*  396 */       LOGGER.warn("Failed to load shader: {}", $$0, $$1);
/*  397 */       this.effectIndex = EFFECT_NONE;
/*  398 */       this.effectActive = false;
/*  399 */     } catch (JsonSyntaxException $$2) {
/*  400 */       LOGGER.warn("Failed to parse shader: {}", $$0, $$2);
/*  401 */       this.effectIndex = EFFECT_NONE;
/*  402 */       this.effectActive = false;
/*      */     } 
/*      */   }
/*      */   public static final class ResourceCache extends Record implements ResourceProvider { private final ResourceProvider original; private final Map<ResourceLocation, Resource> cache;
/*  406 */     public ResourceCache(ResourceProvider $$0, Map<ResourceLocation, Resource> $$1) { this.original = $$0; this.cache = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #406	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*  406 */       //   0	7	0	this	Lnet/minecraft/client/renderer/GameRenderer$ResourceCache; } public ResourceProvider original() { return this.original; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #406	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/client/renderer/GameRenderer$ResourceCache; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #406	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/client/renderer/GameRenderer$ResourceCache;
/*  406 */       //   0	8	1	$$0	Ljava/lang/Object; } public Map<ResourceLocation, Resource> cache() { return this.cache; }
/*      */     
/*      */     public Optional<Resource> getResource(ResourceLocation $$0) {
/*  409 */       Resource $$1 = this.cache.get($$0);
/*  410 */       if ($$1 != null) {
/*  411 */         return Optional.of($$1);
/*      */       }
/*  413 */       return this.original.getResource($$0);
/*      */     } }
/*      */ 
/*      */   
/*      */   public PreparableReloadListener createReloadListener() {
/*  418 */     return (PreparableReloadListener)new SimplePreparableReloadListener<ResourceCache>()
/*      */       {
/*      */         protected GameRenderer.ResourceCache prepare(ResourceManager $$0, ProfilerFiller $$1)
/*      */         {
/*  422 */           Map<ResourceLocation, Resource> $$2 = $$0.listResources("shaders", $$0 -> {
/*      */                 String $$1 = $$0.getPath();
/*  424 */                 return ($$1.endsWith(".json") || $$1.endsWith(Program.Type.FRAGMENT.getExtension()) || $$1.endsWith(Program.Type.VERTEX.getExtension()) || $$1.endsWith(".glsl"));
/*      */               });
/*  426 */           Map<ResourceLocation, Resource> $$3 = new HashMap<>();
/*  427 */           $$2.forEach(($$1, $$2) -> { try { InputStream $$3 = $$2.open(); try { byte[] $$4 = $$3.readAllBytes(); $$0.put($$1, new Resource($$2.source(), ())); if ($$3 != null)
/*  428 */                       $$3.close();  } catch (Throwable throwable) { if ($$3 != null) try { $$3.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */                         throw throwable; }
/*      */                    }
/*  431 */                 catch (Exception $$5)
/*      */                 { GameRenderer.LOGGER.warn("Failed to read resource {}", $$1, $$5); }
/*      */               
/*      */               });
/*      */           
/*  436 */           return new GameRenderer.ResourceCache((ResourceProvider)$$0, $$3);
/*      */         }
/*      */ 
/*      */         
/*      */         protected void apply(GameRenderer.ResourceCache $$0, ResourceManager $$1, ProfilerFiller $$2) {
/*  441 */           GameRenderer.this.reloadShaders($$0);
/*  442 */           if (GameRenderer.this.postEffect != null) {
/*  443 */             GameRenderer.this.postEffect.close();
/*      */           }
/*  445 */           GameRenderer.this.postEffect = null;
/*  446 */           if (GameRenderer.this.effectIndex == GameRenderer.EFFECT_NONE) {
/*  447 */             GameRenderer.this.checkEntityPostEffect(GameRenderer.this.minecraft.getCameraEntity());
/*      */           } else {
/*  449 */             GameRenderer.this.loadEffect(GameRenderer.EFFECTS[GameRenderer.this.effectIndex]);
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public String getName() {
/*  455 */           return "Shader Loader";
/*      */         }
/*      */       };
/*      */   }
/*      */   
/*      */   public void preloadUiShader(ResourceProvider $$0) {
/*  461 */     if (this.blitShader != null) {
/*  462 */       throw new RuntimeException("Blit shader already preloaded");
/*      */     }
/*      */     try {
/*  465 */       this.blitShader = new ShaderInstance($$0, "blit_screen", DefaultVertexFormat.BLIT_SCREEN);
/*  466 */     } catch (IOException $$1) {
/*  467 */       throw new RuntimeException("could not preload blit shader", $$1);
/*      */     } 
/*      */     
/*  470 */     rendertypeGuiShader = preloadShader($$0, "rendertype_gui", DefaultVertexFormat.POSITION_COLOR);
/*  471 */     rendertypeGuiOverlayShader = preloadShader($$0, "rendertype_gui_overlay", DefaultVertexFormat.POSITION_COLOR);
/*  472 */     positionShader = preloadShader($$0, "position", DefaultVertexFormat.POSITION);
/*  473 */     positionColorShader = preloadShader($$0, "position_color", DefaultVertexFormat.POSITION_COLOR);
/*  474 */     positionColorTexShader = preloadShader($$0, "position_color_tex", DefaultVertexFormat.POSITION_COLOR_TEX);
/*  475 */     positionTexShader = preloadShader($$0, "position_tex", DefaultVertexFormat.POSITION_TEX);
/*  476 */     positionTexColorShader = preloadShader($$0, "position_tex_color", DefaultVertexFormat.POSITION_TEX_COLOR);
/*      */ 
/*      */     
/*  479 */     rendertypeTextShader = preloadShader($$0, "rendertype_text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP);
/*      */   }
/*      */   
/*      */   private ShaderInstance preloadShader(ResourceProvider $$0, String $$1, VertexFormat $$2) {
/*      */     try {
/*  484 */       ShaderInstance $$3 = new ShaderInstance($$0, $$1, $$2);
/*  485 */       this.shaders.put($$1, $$3);
/*  486 */       return $$3;
/*  487 */     } catch (Exception $$4) {
/*  488 */       throw new IllegalStateException("could not preload shader " + $$1, $$4);
/*      */     } 
/*      */   }
/*      */   
/*      */   void reloadShaders(ResourceProvider $$0) {
/*  493 */     RenderSystem.assertOnRenderThread();
/*      */ 
/*      */ 
/*      */     
/*  497 */     List<Program> $$1 = Lists.newArrayList();
/*  498 */     $$1.addAll(Program.Type.FRAGMENT.getPrograms().values());
/*  499 */     $$1.addAll(Program.Type.VERTEX.getPrograms().values());
/*  500 */     $$1.forEach(Program::close);
/*      */     
/*  502 */     List<Pair<ShaderInstance, Consumer<ShaderInstance>>> $$2 = Lists.newArrayListWithCapacity(this.shaders.size());
/*      */     
/*      */     try {
/*  505 */       $$2.add(Pair.of(new ShaderInstance($$0, "particle", DefaultVertexFormat.PARTICLE), $$0 -> particleShader = $$0));
/*  506 */       $$2.add(Pair.of(new ShaderInstance($$0, "position", DefaultVertexFormat.POSITION), $$0 -> positionShader = $$0));
/*  507 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_color", DefaultVertexFormat.POSITION_COLOR), $$0 -> positionColorShader = $$0));
/*  508 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_color_lightmap", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP), $$0 -> positionColorLightmapShader = $$0));
/*  509 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_color_tex", DefaultVertexFormat.POSITION_COLOR_TEX), $$0 -> positionColorTexShader = $$0));
/*  510 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_color_tex_lightmap", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), $$0 -> positionColorTexLightmapShader = $$0));
/*  511 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_tex", DefaultVertexFormat.POSITION_TEX), $$0 -> positionTexShader = $$0));
/*  512 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_tex_color", DefaultVertexFormat.POSITION_TEX_COLOR), $$0 -> positionTexColorShader = $$0));
/*  513 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_tex_color_normal", DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL), $$0 -> positionTexColorNormalShader = $$0));
/*  514 */       $$2.add(Pair.of(new ShaderInstance($$0, "position_tex_lightmap_color", DefaultVertexFormat.POSITION_TEX_LIGHTMAP_COLOR), $$0 -> positionTexLightmapColorShader = $$0));
/*      */       
/*  516 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_solid", DefaultVertexFormat.BLOCK), $$0 -> rendertypeSolidShader = $$0));
/*  517 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_cutout_mipped", DefaultVertexFormat.BLOCK), $$0 -> rendertypeCutoutMippedShader = $$0));
/*  518 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_cutout", DefaultVertexFormat.BLOCK), $$0 -> rendertypeCutoutShader = $$0));
/*  519 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_translucent", DefaultVertexFormat.BLOCK), $$0 -> rendertypeTranslucentShader = $$0));
/*  520 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_translucent_moving_block", DefaultVertexFormat.BLOCK), $$0 -> rendertypeTranslucentMovingBlockShader = $$0));
/*  521 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_armor_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeArmorCutoutNoCullShader = $$0));
/*  522 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_solid", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntitySolidShader = $$0));
/*  523 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_cutout", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityCutoutShader = $$0));
/*  524 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityCutoutNoCullShader = $$0));
/*  525 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_cutout_no_cull_z_offset", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityCutoutNoCullZOffsetShader = $$0));
/*  526 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_item_entity_translucent_cull", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeItemEntityTranslucentCullShader = $$0));
/*  527 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_translucent_cull", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityTranslucentCullShader = $$0));
/*  528 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_translucent", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityTranslucentShader = $$0));
/*  529 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_translucent_emissive", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityTranslucentEmissiveShader = $$0));
/*  530 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_smooth_cutout", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntitySmoothCutoutShader = $$0));
/*  531 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_beacon_beam", DefaultVertexFormat.BLOCK), $$0 -> rendertypeBeaconBeamShader = $$0));
/*  532 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_decal", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityDecalShader = $$0));
/*  533 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_no_outline", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityNoOutlineShader = $$0));
/*  534 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_shadow", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityShadowShader = $$0));
/*  535 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_alpha", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEntityAlphaShader = $$0));
/*  536 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_eyes", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEyesShader = $$0));
/*  537 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_energy_swirl", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeEnergySwirlShader = $$0));
/*  538 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_leash", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP), $$0 -> rendertypeLeashShader = $$0));
/*  539 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_water_mask", DefaultVertexFormat.POSITION), $$0 -> rendertypeWaterMaskShader = $$0));
/*  540 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_outline", DefaultVertexFormat.POSITION_COLOR_TEX), $$0 -> rendertypeOutlineShader = $$0));
/*  541 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_armor_glint", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeArmorGlintShader = $$0));
/*  542 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_armor_entity_glint", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeArmorEntityGlintShader = $$0));
/*  543 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_glint_translucent", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeGlintTranslucentShader = $$0));
/*  544 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_glint", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeGlintShader = $$0));
/*  545 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_glint_direct", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeGlintDirectShader = $$0));
/*  546 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_glint", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeEntityGlintShader = $$0));
/*  547 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_entity_glint_direct", DefaultVertexFormat.POSITION_TEX), $$0 -> rendertypeEntityGlintDirectShader = $$0));
/*  548 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), $$0 -> rendertypeTextShader = $$0));
/*  549 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text_background", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP), $$0 -> rendertypeTextBackgroundShader = $$0));
/*  550 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text_intensity", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), $$0 -> rendertypeTextIntensityShader = $$0));
/*  551 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text_see_through", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), $$0 -> rendertypeTextSeeThroughShader = $$0));
/*  552 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text_background_see_through", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP), $$0 -> rendertypeTextBackgroundSeeThroughShader = $$0));
/*  553 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_text_intensity_see_through", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), $$0 -> rendertypeTextIntensitySeeThroughShader = $$0));
/*  554 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_lightning", DefaultVertexFormat.POSITION_COLOR), $$0 -> rendertypeLightningShader = $$0));
/*  555 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_tripwire", DefaultVertexFormat.BLOCK), $$0 -> rendertypeTripwireShader = $$0));
/*  556 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_end_portal", DefaultVertexFormat.POSITION), $$0 -> rendertypeEndPortalShader = $$0));
/*  557 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_end_gateway", DefaultVertexFormat.POSITION), $$0 -> rendertypeEndGatewayShader = $$0));
/*  558 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_lines", DefaultVertexFormat.POSITION_COLOR_NORMAL), $$0 -> rendertypeLinesShader = $$0));
/*  559 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_crumbling", DefaultVertexFormat.BLOCK), $$0 -> rendertypeCrumblingShader = $$0));
/*  560 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_gui", DefaultVertexFormat.POSITION_COLOR), $$0 -> rendertypeGuiShader = $$0));
/*  561 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_gui_overlay", DefaultVertexFormat.POSITION_COLOR), $$0 -> rendertypeGuiOverlayShader = $$0));
/*  562 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_gui_text_highlight", DefaultVertexFormat.POSITION_COLOR), $$0 -> rendertypeGuiTextHighlightShader = $$0));
/*  563 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_gui_ghost_recipe_overlay", DefaultVertexFormat.POSITION_COLOR), $$0 -> rendertypeGuiGhostRecipeOverlayShader = $$0));
/*  564 */       $$2.add(Pair.of(new ShaderInstance($$0, "rendertype_breeze_wind", DefaultVertexFormat.NEW_ENTITY), $$0 -> rendertypeBreezeWindShader = $$0));
/*  565 */     } catch (IOException $$3) {
/*  566 */       $$2.forEach($$0 -> ((ShaderInstance)$$0.getFirst()).close());
/*  567 */       throw new RuntimeException("could not reload shaders", $$3);
/*      */     } 
/*      */     
/*  570 */     shutdownShaders();
/*      */     
/*  572 */     $$2.forEach($$0 -> {
/*      */           ShaderInstance $$1 = (ShaderInstance)$$0.getFirst();
/*      */           this.shaders.put($$1.getName(), $$1);
/*      */           ((Consumer<ShaderInstance>)$$0.getSecond()).accept($$1);
/*      */         });
/*      */   }
/*      */   
/*      */   private void shutdownShaders() {
/*  580 */     RenderSystem.assertOnRenderThread();
/*  581 */     this.shaders.values().forEach(ShaderInstance::close);
/*  582 */     this.shaders.clear();
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ShaderInstance getShader(@Nullable String $$0) {
/*  587 */     if ($$0 == null) {
/*  588 */       return null;
/*      */     }
/*  590 */     return this.shaders.get($$0);
/*      */   }
/*      */   
/*      */   public void tick() {
/*  594 */     tickFov();
/*  595 */     this.lightTexture.tick();
/*      */     
/*  597 */     if (this.minecraft.getCameraEntity() == null) {
/*  598 */       this.minecraft.setCameraEntity((Entity)this.minecraft.player);
/*      */     }
/*  600 */     this.mainCamera.tick();
/*  601 */     this.itemInHandRenderer.tick();
/*  602 */     this.confusionAnimationTick++;
/*      */     
/*  604 */     if (!this.minecraft.level.tickRateManager().runsNormally()) {
/*      */       return;
/*      */     }
/*      */     
/*  608 */     this.minecraft.levelRenderer.tickRain(this.mainCamera);
/*      */     
/*  610 */     this.darkenWorldAmountO = this.darkenWorldAmount;
/*  611 */     if (this.minecraft.gui.getBossOverlay().shouldDarkenScreen()) {
/*  612 */       this.darkenWorldAmount += 0.05F;
/*  613 */       if (this.darkenWorldAmount > 1.0F) {
/*  614 */         this.darkenWorldAmount = 1.0F;
/*      */       }
/*  616 */     } else if (this.darkenWorldAmount > 0.0F) {
/*  617 */       this.darkenWorldAmount -= 0.0125F;
/*      */     } 
/*      */     
/*  620 */     if (this.itemActivationTicks > 0) {
/*  621 */       this.itemActivationTicks--;
/*  622 */       if (this.itemActivationTicks == 0) {
/*  623 */         this.itemActivationItem = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public PostChain currentEffect() {
/*  630 */     return this.postEffect;
/*      */   }
/*      */   
/*      */   public void resize(int $$0, int $$1) {
/*  634 */     if (this.postEffect != null) {
/*  635 */       this.postEffect.resize($$0, $$1);
/*      */     }
/*      */     
/*  638 */     this.minecraft.levelRenderer.resize($$0, $$1);
/*      */   }
/*      */   
/*      */   public void pick(float $$0) {
/*  642 */     Entity $$1 = this.minecraft.getCameraEntity();
/*  643 */     if ($$1 == null) {
/*      */       return;
/*      */     }
/*  646 */     if (this.minecraft.level == null) {
/*      */       return;
/*      */     }
/*      */     
/*  650 */     this.minecraft.getProfiler().push("pick");
/*      */     
/*  652 */     this.minecraft.crosshairPickEntity = null;
/*      */     
/*  654 */     double $$2 = this.minecraft.gameMode.getPickRange();
/*  655 */     this.minecraft.hitResult = $$1.pick($$2, $$0, false);
/*      */     
/*  657 */     Vec3 $$3 = $$1.getEyePosition($$0);
/*      */ 
/*      */     
/*  660 */     boolean $$4 = this.minecraft.gameMode.hasFarPickRange();
/*  661 */     $$2 = $$4 ? 6.0D : $$2;
/*  662 */     boolean $$5 = !$$4;
/*      */     
/*  664 */     double $$6 = (this.minecraft.hitResult != null) ? this.minecraft.hitResult.getLocation().distanceToSqr($$3) : ($$2 * $$2);
/*  665 */     Vec3 $$7 = $$1.getViewVector(1.0F);
/*  666 */     Vec3 $$8 = $$3.add($$7.x * $$2, $$7.y * $$2, $$7.z * $$2);
/*  667 */     float $$9 = 1.0F;
/*      */     
/*  669 */     AABB $$10 = $$1.getBoundingBox().expandTowards($$7.scale($$2)).inflate(1.0D, 1.0D, 1.0D);
/*  670 */     EntityHitResult $$11 = ProjectileUtil.getEntityHitResult($$1, $$3, $$8, $$10, $$0 -> (!$$0.isSpectator() && $$0.isPickable()), $$6);
/*      */     
/*  672 */     if ($$11 != null) {
/*  673 */       Vec3 $$12 = $$11.getLocation();
/*  674 */       double $$13 = $$3.distanceToSqr($$12);
/*  675 */       if ($$5 && $$13 > 9.0D) {
/*  676 */         this.minecraft.hitResult = (HitResult)BlockHitResult.miss($$12, Direction.getNearest($$7.x, $$7.y, $$7.z), BlockPos.containing((Position)$$12));
/*      */       }
/*  678 */       else if ($$13 < $$6 || this.minecraft.hitResult == null) {
/*  679 */         this.minecraft.hitResult = (HitResult)$$11;
/*  680 */         Entity $$14 = $$11.getEntity();
/*  681 */         this.minecraft.crosshairPickEntity = $$14;
/*      */       } 
/*      */     } 
/*      */     
/*  685 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   private void tickFov() {
/*  689 */     float $$0 = 1.0F;
/*  690 */     Entity entity = this.minecraft.getCameraEntity(); if (entity instanceof AbstractClientPlayer) { AbstractClientPlayer $$1 = (AbstractClientPlayer)entity;
/*      */       
/*  692 */       $$0 = $$1.getFieldOfViewModifier(); }
/*      */ 
/*      */     
/*  695 */     this.oldFov = this.fov;
/*  696 */     this.fov += ($$0 - this.fov) * 0.5F;
/*      */     
/*  698 */     if (this.fov > 1.5F) {
/*  699 */       this.fov = 1.5F;
/*      */     }
/*  701 */     if (this.fov < 0.1F) {
/*  702 */       this.fov = 0.1F;
/*      */     }
/*      */   }
/*      */   
/*      */   private double getFov(Camera $$0, float $$1, boolean $$2) {
/*  707 */     if (this.panoramicMode) {
/*  708 */       return 90.0D;
/*      */     }
/*      */     
/*  711 */     double $$3 = 70.0D;
/*  712 */     if ($$2) {
/*  713 */       $$3 = ((Integer)this.minecraft.options.fov().get()).intValue();
/*  714 */       $$3 *= Mth.lerp($$1, this.oldFov, this.fov);
/*      */     } 
/*  716 */     if ($$0.getEntity() instanceof LivingEntity && ((LivingEntity)$$0.getEntity()).isDeadOrDying()) {
/*  717 */       float $$4 = Math.min(((LivingEntity)$$0.getEntity()).deathTime + $$1, 20.0F);
/*      */       
/*  719 */       $$3 /= ((1.0F - 500.0F / ($$4 + 500.0F)) * 2.0F + 1.0F);
/*      */     } 
/*      */     
/*  722 */     FogType $$5 = $$0.getFluidInCamera();
/*  723 */     if ($$5 == FogType.LAVA || $$5 == FogType.WATER) {
/*  724 */       $$3 *= Mth.lerp(((Double)this.minecraft.options.fovEffectScale().get()).doubleValue(), 1.0D, 0.8571428656578064D);
/*      */     }
/*      */     
/*  727 */     return $$3;
/*      */   }
/*      */   
/*      */   private void bobHurt(PoseStack $$0, float $$1) {
/*  731 */     Entity entity = this.minecraft.getCameraEntity(); if (entity instanceof LivingEntity) { LivingEntity $$2 = (LivingEntity)entity;
/*      */       
/*  733 */       float $$3 = $$2.hurtTime - $$1;
/*      */       
/*  735 */       if ($$2.isDeadOrDying()) {
/*  736 */         float $$4 = Math.min($$2.deathTime + $$1, 20.0F);
/*      */         
/*  738 */         $$0.mulPose(Axis.ZP.rotationDegrees(40.0F - 8000.0F / ($$4 + 200.0F)));
/*      */       } 
/*      */       
/*  741 */       if ($$3 < 0.0F) {
/*      */         return;
/*      */       }
/*  744 */       $$3 /= $$2.hurtDuration;
/*  745 */       $$3 = Mth.sin($$3 * $$3 * $$3 * $$3 * 3.1415927F);
/*      */       
/*  747 */       float $$5 = $$2.getHurtDir();
/*      */       
/*  749 */       $$0.mulPose(Axis.YP.rotationDegrees(-$$5));
/*  750 */       float $$6 = (float)(-$$3 * 14.0D * ((Double)this.minecraft.options.damageTiltStrength().get()).doubleValue());
/*  751 */       $$0.mulPose(Axis.ZP.rotationDegrees($$6));
/*  752 */       $$0.mulPose(Axis.YP.rotationDegrees($$5)); }
/*      */   
/*      */   }
/*      */   
/*      */   private void bobView(PoseStack $$0, float $$1) {
/*  757 */     if (!(this.minecraft.getCameraEntity() instanceof Player)) {
/*      */       return;
/*      */     }
/*  760 */     Player $$2 = (Player)this.minecraft.getCameraEntity();
/*      */     
/*  762 */     float $$3 = $$2.walkDist - $$2.walkDistO;
/*  763 */     float $$4 = -($$2.walkDist + $$3 * $$1);
/*  764 */     float $$5 = Mth.lerp($$1, $$2.oBob, $$2.bob);
/*  765 */     $$0.translate(Mth.sin($$4 * 3.1415927F) * $$5 * 0.5F, -Math.abs(Mth.cos($$4 * 3.1415927F) * $$5), 0.0F);
/*  766 */     $$0.mulPose(Axis.ZP.rotationDegrees(Mth.sin($$4 * 3.1415927F) * $$5 * 3.0F));
/*  767 */     $$0.mulPose(Axis.XP.rotationDegrees(Math.abs(Mth.cos($$4 * 3.1415927F - 0.2F) * $$5) * 5.0F));
/*      */   }
/*      */   
/*      */   public void renderZoomed(float $$0, float $$1, float $$2) {
/*  771 */     this.zoom = $$0;
/*  772 */     this.zoomX = $$1;
/*  773 */     this.zoomY = $$2;
/*  774 */     setRenderBlockOutline(false);
/*  775 */     setRenderHand(false);
/*  776 */     renderLevel(1.0F, 0L, new PoseStack());
/*  777 */     this.zoom = 1.0F;
/*      */   }
/*      */   
/*      */   private void renderItemInHand(PoseStack $$0, Camera $$1, float $$2) {
/*  781 */     if (this.panoramicMode) {
/*      */       return;
/*      */     }
/*      */     
/*  785 */     resetProjectionMatrix(getProjectionMatrix(getFov($$1, $$2, false)));
/*  786 */     $$0.setIdentity();
/*      */     
/*  788 */     $$0.pushPose();
/*  789 */     bobHurt($$0, $$2);
/*  790 */     if (((Boolean)this.minecraft.options.bobView().get()).booleanValue()) {
/*  791 */       bobView($$0, $$2);
/*      */     }
/*      */     
/*  794 */     boolean $$3 = (this.minecraft.getCameraEntity() instanceof LivingEntity && ((LivingEntity)this.minecraft.getCameraEntity()).isSleeping());
/*      */     
/*  796 */     if (this.minecraft.options.getCameraType().isFirstPerson() && !$$3 && !this.minecraft.options.hideGui && this.minecraft.gameMode.getPlayerMode() != GameType.SPECTATOR) {
/*  797 */       this.lightTexture.turnOnLightLayer();
/*  798 */       this.itemInHandRenderer.renderHandsWithItems($$2, $$0, this.renderBuffers.bufferSource(), this.minecraft.player, this.minecraft.getEntityRenderDispatcher().getPackedLightCoords((Entity)this.minecraft.player, $$2));
/*  799 */       this.lightTexture.turnOffLightLayer();
/*      */     } 
/*      */     
/*  802 */     $$0.popPose();
/*  803 */     if (this.minecraft.options.getCameraType().isFirstPerson() && !$$3) {
/*  804 */       ScreenEffectRenderer.renderScreenEffect(this.minecraft, $$0);
/*  805 */       bobHurt($$0, $$2);
/*      */     } 
/*  807 */     if (((Boolean)this.minecraft.options.bobView().get()).booleanValue()) {
/*  808 */       bobView($$0, $$2);
/*      */     }
/*      */   }
/*      */   
/*      */   public void resetProjectionMatrix(Matrix4f $$0) {
/*  813 */     RenderSystem.setProjectionMatrix($$0, VertexSorting.DISTANCE_TO_ORIGIN);
/*      */   }
/*      */   
/*      */   public Matrix4f getProjectionMatrix(double $$0) {
/*  817 */     PoseStack $$1 = new PoseStack();
/*  818 */     $$1.last().pose().identity();
/*      */     
/*  820 */     if (this.zoom != 1.0F) {
/*  821 */       $$1.translate(this.zoomX, -this.zoomY, 0.0F);
/*  822 */       $$1.scale(this.zoom, this.zoom, 1.0F);
/*      */     } 
/*      */     
/*  825 */     $$1.last().pose().mul((Matrix4fc)(new Matrix4f()).setPerspective((float)($$0 * 0.01745329238474369D), this.minecraft.getWindow().getWidth() / this.minecraft.getWindow().getHeight(), 0.05F, getDepthFar()));
/*  826 */     return $$1.last().pose();
/*      */   }
/*      */   
/*      */   public float getDepthFar() {
/*  830 */     return this.renderDistance * 4.0F;
/*      */   }
/*      */   
/*      */   public static float getNightVisionScale(LivingEntity $$0, float $$1) {
/*  834 */     MobEffectInstance $$2 = $$0.getEffect(MobEffects.NIGHT_VISION);
/*  835 */     if (!$$2.endsWithin(200)) {
/*  836 */       return 1.0F;
/*      */     }
/*  838 */     return 0.7F + Mth.sin(($$2.getDuration() - $$1) * 3.1415927F * 0.2F) * 0.3F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(float $$0, long $$1, boolean $$2) {
/*  843 */     if (this.minecraft.isWindowActive() || !this.minecraft.options.pauseOnLostFocus || (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() && this.minecraft.mouseHandler.isRightPressed())) {
/*  844 */       this.lastActiveTime = Util.getMillis();
/*      */     }
/*  846 */     else if (Util.getMillis() - this.lastActiveTime > 500L) {
/*  847 */       this.minecraft.pauseGame(false);
/*      */     } 
/*      */ 
/*      */     
/*  851 */     if (this.minecraft.noRender) {
/*      */       return;
/*      */     }
/*      */     
/*  855 */     float $$3 = (this.minecraft.level != null && this.minecraft.level.tickRateManager().runsNormally()) ? $$0 : 1.0F;
/*      */     
/*  857 */     boolean $$4 = this.minecraft.isGameLoadFinished();
/*      */     
/*  859 */     int $$5 = (int)(this.minecraft.mouseHandler.xpos() * this.minecraft.getWindow().getGuiScaledWidth() / this.minecraft.getWindow().getScreenWidth());
/*  860 */     int $$6 = (int)(this.minecraft.mouseHandler.ypos() * this.minecraft.getWindow().getGuiScaledHeight() / this.minecraft.getWindow().getScreenHeight());
/*      */     
/*  862 */     RenderSystem.viewport(0, 0, this.minecraft.getWindow().getWidth(), this.minecraft.getWindow().getHeight());
/*  863 */     if ($$4 && $$2 && this.minecraft.level != null) {
/*  864 */       this.minecraft.getProfiler().push("level");
/*      */       
/*  866 */       renderLevel($$0, $$1, new PoseStack());
/*      */       
/*  868 */       tryTakeScreenshotIfNeeded();
/*      */       
/*  870 */       this.minecraft.levelRenderer.doEntityOutline();
/*      */       
/*  872 */       if (this.postEffect != null && this.effectActive) {
/*  873 */         RenderSystem.disableBlend();
/*  874 */         RenderSystem.disableDepthTest();
/*      */         
/*  876 */         RenderSystem.resetTextureMatrix();
/*      */         
/*  878 */         this.postEffect.process($$3);
/*      */       } 
/*  880 */       this.minecraft.getMainRenderTarget().bindWrite(true);
/*      */     } 
/*      */     
/*  883 */     Window $$7 = this.minecraft.getWindow();
/*  884 */     RenderSystem.clear(256, Minecraft.ON_OSX);
/*      */     
/*  886 */     Matrix4f $$8 = (new Matrix4f()).setOrtho(0.0F, (float)($$7.getWidth() / $$7.getGuiScale()), (float)($$7.getHeight() / $$7.getGuiScale()), 0.0F, 1000.0F, 21000.0F);
/*      */     
/*  888 */     RenderSystem.setProjectionMatrix($$8, VertexSorting.ORTHOGRAPHIC_Z);
/*      */     
/*  890 */     PoseStack $$9 = RenderSystem.getModelViewStack();
/*  891 */     $$9.pushPose();
/*  892 */     $$9.setIdentity();
/*  893 */     $$9.translate(0.0F, 0.0F, -11000.0F);
/*  894 */     RenderSystem.applyModelViewMatrix();
/*      */     
/*  896 */     Lighting.setupFor3DItems();
/*      */     
/*  898 */     GuiGraphics $$10 = new GuiGraphics(this.minecraft, this.renderBuffers.bufferSource());
/*      */     
/*  900 */     if ($$4 && $$2 && this.minecraft.level != null) {
/*  901 */       this.minecraft.getProfiler().popPush("gui");
/*  902 */       if (this.minecraft.player != null) {
/*  903 */         float $$11 = Mth.lerp($$3, this.minecraft.player.oSpinningEffectIntensity, this.minecraft.player.spinningEffectIntensity);
/*  904 */         float $$12 = ((Double)this.minecraft.options.screenEffectScale().get()).floatValue();
/*  905 */         if ($$11 > 0.0F && this.minecraft.player.hasEffect(MobEffects.CONFUSION) && $$12 < 1.0F) {
/*  906 */           renderConfusionOverlay($$10, $$11 * (1.0F - $$12));
/*      */         }
/*      */       } 
/*      */       
/*  910 */       if (!this.minecraft.options.hideGui || this.minecraft.screen != null) {
/*  911 */         renderItemActivationAnimation(this.minecraft.getWindow().getGuiScaledWidth(), this.minecraft.getWindow().getGuiScaledHeight(), $$3);
/*  912 */         this.minecraft.gui.render($$10, $$3);
/*  913 */         RenderSystem.clear(256, Minecraft.ON_OSX);
/*      */       } 
/*  915 */       this.minecraft.getProfiler().pop();
/*      */     } 
/*      */     
/*  918 */     if (this.minecraft.getOverlay() != null) {
/*      */       try {
/*  920 */         this.minecraft.getOverlay().render($$10, $$5, $$6, this.minecraft.getDeltaFrameTime());
/*  921 */       } catch (Throwable $$13) {
/*  922 */         CrashReport $$14 = CrashReport.forThrowable($$13, "Rendering overlay");
/*  923 */         CrashReportCategory $$15 = $$14.addCategory("Overlay render details");
/*      */         
/*  925 */         $$15.setDetail("Overlay name", () -> this.minecraft.getOverlay().getClass().getCanonicalName());
/*      */         
/*  927 */         throw new ReportedException($$14);
/*      */       } 
/*  929 */     } else if ($$4 && this.minecraft.screen != null) {
/*      */       try {
/*  931 */         this.minecraft.screen.renderWithTooltip($$10, $$5, $$6, this.minecraft.getDeltaFrameTime());
/*  932 */       } catch (Throwable $$16) {
/*  933 */         CrashReport $$17 = CrashReport.forThrowable($$16, "Rendering screen");
/*  934 */         CrashReportCategory $$18 = $$17.addCategory("Screen render details");
/*      */         
/*  936 */         $$18.setDetail("Screen name", () -> this.minecraft.screen.getClass().getCanonicalName());
/*  937 */         $$18.setDetail("Mouse location", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%f, %f)", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1), Double.valueOf(this.minecraft.mouseHandler.xpos()), Double.valueOf(this.minecraft.mouseHandler.ypos()) }));
/*  938 */         $$18.setDetail("Screen size", () -> String.format(Locale.ROOT, "Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %f", new Object[] { Integer.valueOf(this.minecraft.getWindow().getGuiScaledWidth()), Integer.valueOf(this.minecraft.getWindow().getGuiScaledHeight()), Integer.valueOf(this.minecraft.getWindow().getWidth()), Integer.valueOf(this.minecraft.getWindow().getHeight()), Double.valueOf(this.minecraft.getWindow().getGuiScale()) }));
/*      */         
/*  940 */         throw new ReportedException($$17);
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  945 */         if (this.minecraft.screen != null) {
/*  946 */           this.minecraft.screen.handleDelayedNarration();
/*      */         }
/*  948 */       } catch (Throwable $$19) {
/*  949 */         CrashReport $$20 = CrashReport.forThrowable($$19, "Narrating screen");
/*  950 */         CrashReportCategory $$21 = $$20.addCategory("Screen details");
/*  951 */         $$21.setDetail("Screen name", () -> this.minecraft.screen.getClass().getCanonicalName());
/*      */         
/*  953 */         throw new ReportedException($$20);
/*      */       } 
/*      */     } 
/*      */     
/*  957 */     if ($$4) {
/*  958 */       this.minecraft.getProfiler().push("toasts");
/*  959 */       this.minecraft.getToasts().render($$10);
/*  960 */       this.minecraft.getProfiler().pop();
/*      */     } 
/*      */     
/*  963 */     $$10.flush();
/*      */     
/*  965 */     $$9.popPose();
/*  966 */     RenderSystem.applyModelViewMatrix();
/*      */   }
/*      */   
/*      */   private void tryTakeScreenshotIfNeeded() {
/*  970 */     if (this.hasWorldScreenshot || !this.minecraft.isLocalServer()) {
/*      */       return;
/*      */     }
/*      */     
/*  974 */     long $$0 = Util.getMillis();
/*  975 */     if ($$0 - this.lastScreenshotAttempt < 1000L) {
/*      */       return;
/*      */     }
/*  978 */     this.lastScreenshotAttempt = $$0;
/*      */     
/*  980 */     IntegratedServer $$1 = this.minecraft.getSingleplayerServer();
/*  981 */     if ($$1 == null || $$1.isStopped()) {
/*      */       return;
/*      */     }
/*      */     
/*  985 */     $$1.getWorldScreenshotFile().ifPresent($$0 -> {
/*      */           if (Files.isRegularFile($$0, new java.nio.file.LinkOption[0])) {
/*      */             this.hasWorldScreenshot = true;
/*      */           } else {
/*      */             takeAutoScreenshot($$0);
/*      */           } 
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void takeAutoScreenshot(Path $$0) {
/*  996 */     if (this.minecraft.levelRenderer.countRenderedSections() > 10 && this.minecraft.levelRenderer.hasRenderedAllSections()) {
/*  997 */       NativeImage $$1 = Screenshot.takeScreenshot(this.minecraft.getMainRenderTarget());
/*  998 */       Util.ioPool().execute(() -> {
/*      */             int $$2 = $$0.getWidth(); int $$3 = $$0.getHeight(); int $$4 = 0; int $$5 = 0; if ($$2 > $$3) {
/*      */               $$4 = ($$2 - $$3) / 2; $$2 = $$3;
/*      */             } else {
/*      */               $$5 = ($$3 - $$2) / 2;
/*      */               $$3 = $$2;
/*      */             } 
/*      */             try {
/*      */               NativeImage $$6 = new NativeImage(64, 64, false);
/*      */               
/*      */               try { $$0.resizeSubRectTo($$4, $$5, $$2, $$3, $$6);
/*      */                 $$6.writeToFile($$1);
/*      */                 $$6.close(); }
/* 1011 */               catch (Throwable throwable) { try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*      */                  throw throwable; }
/*      */             
/* 1014 */             } catch (IOException $$7) {
/*      */               LOGGER.warn("Couldn't save auto screenshot", $$7);
/*      */             } finally {
/*      */               $$0.close();
/*      */             } 
/*      */           });
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean shouldRenderBlockOutline() {
/* 1024 */     if (!this.renderBlockOutline) {
/* 1025 */       return false;
/*      */     }
/*      */     
/* 1028 */     Entity $$0 = this.minecraft.getCameraEntity();
/* 1029 */     boolean $$1 = ($$0 instanceof Player && !this.minecraft.options.hideGui);
/* 1030 */     if ($$1 && !(((Player)$$0).getAbilities()).mayBuild) {
/*      */       
/* 1032 */       ItemStack $$2 = ((LivingEntity)$$0).getMainHandItem();
/* 1033 */       HitResult $$3 = this.minecraft.hitResult;
/* 1034 */       if ($$3 != null && $$3.getType() == HitResult.Type.BLOCK) {
/* 1035 */         BlockPos $$4 = ((BlockHitResult)$$3).getBlockPos();
/* 1036 */         BlockState $$5 = this.minecraft.level.getBlockState($$4);
/* 1037 */         if (this.minecraft.gameMode.getPlayerMode() == GameType.SPECTATOR) {
/* 1038 */           $$1 = ($$5.getMenuProvider((Level)this.minecraft.level, $$4) != null);
/*      */         } else {
/* 1040 */           BlockInWorld $$6 = new BlockInWorld((LevelReader)this.minecraft.level, $$4, false);
/* 1041 */           Registry<Block> $$7 = this.minecraft.level.registryAccess().registryOrThrow(Registries.BLOCK);
/* 1042 */           $$1 = (!$$2.isEmpty() && ($$2.hasAdventureModeBreakTagForBlock($$7, $$6) || $$2.hasAdventureModePlaceTagForBlock($$7, $$6)));
/*      */         } 
/*      */       } 
/*      */     } 
/* 1046 */     return $$1;
/*      */   }
/*      */   
/*      */   public void renderLevel(float $$0, long $$1, PoseStack $$2) {
/* 1050 */     this.lightTexture.updateLightTexture($$0);
/* 1051 */     if (this.minecraft.getCameraEntity() == null) {
/* 1052 */       this.minecraft.setCameraEntity((Entity)this.minecraft.player);
/*      */     }
/*      */     
/* 1055 */     pick($$0);
/*      */     
/* 1057 */     this.minecraft.getProfiler().push("center");
/* 1058 */     boolean $$3 = shouldRenderBlockOutline();
/*      */     
/* 1060 */     this.minecraft.getProfiler().popPush("camera");
/* 1061 */     Camera $$4 = this.mainCamera;
/* 1062 */     Entity $$5 = (this.minecraft.getCameraEntity() == null) ? (Entity)this.minecraft.player : this.minecraft.getCameraEntity();
/* 1063 */     $$4.setup((BlockGetter)this.minecraft.level, $$5, !this.minecraft.options.getCameraType().isFirstPerson(), this.minecraft.options.getCameraType().isMirrored(), this.minecraft.level.tickRateManager().isEntityFrozen($$5) ? 1.0F : $$0);
/*      */     
/* 1065 */     this.renderDistance = (this.minecraft.options.getEffectiveRenderDistance() * 16);
/* 1066 */     PoseStack $$6 = new PoseStack();
/*      */     
/* 1068 */     double $$7 = getFov($$4, $$0, true);
/* 1069 */     $$6.mulPoseMatrix(getProjectionMatrix($$7));
/*      */     
/* 1071 */     bobHurt($$6, $$4.getPartialTickTime());
/* 1072 */     if (((Boolean)this.minecraft.options.bobView().get()).booleanValue()) {
/* 1073 */       bobView($$6, $$4.getPartialTickTime());
/*      */     }
/* 1075 */     float $$8 = ((Double)this.minecraft.options.screenEffectScale().get()).floatValue();
/* 1076 */     float $$9 = Mth.lerp($$0, this.minecraft.player.oSpinningEffectIntensity, this.minecraft.player.spinningEffectIntensity) * $$8 * $$8;
/* 1077 */     if ($$9 > 0.0F) {
/* 1078 */       int $$10 = this.minecraft.player.hasEffect(MobEffects.CONFUSION) ? 7 : 20;
/*      */       
/* 1080 */       float $$11 = 5.0F / ($$9 * $$9 + 5.0F) - $$9 * 0.04F;
/* 1081 */       $$11 *= $$11;
/*      */       
/* 1083 */       Axis $$12 = Axis.of(new Vector3f(0.0F, Mth.SQRT_OF_TWO / 2.0F, Mth.SQRT_OF_TWO / 2.0F));
/* 1084 */       $$6.mulPose($$12.rotationDegrees((this.confusionAnimationTick + $$0) * $$10));
/* 1085 */       $$6.scale(1.0F / $$11, 1.0F, 1.0F);
/* 1086 */       float $$13 = -(this.confusionAnimationTick + $$0) * $$10;
/* 1087 */       $$6.mulPose($$12.rotationDegrees($$13));
/*      */     } 
/*      */     
/* 1090 */     Matrix4f $$14 = $$6.last().pose();
/* 1091 */     resetProjectionMatrix($$14);
/* 1092 */     $$2.mulPose(Axis.XP.rotationDegrees($$4.getXRot()));
/* 1093 */     $$2.mulPose(Axis.YP.rotationDegrees($$4.getYRot() + 180.0F));
/* 1094 */     Matrix3f $$15 = (new Matrix3f((Matrix3fc)$$2.last().normal())).invert();
/* 1095 */     RenderSystem.setInverseViewRotationMatrix($$15);
/*      */     
/* 1097 */     this.minecraft.levelRenderer.prepareCullFrustum($$2, $$4.getPosition(), getProjectionMatrix(Math.max($$7, ((Integer)this.minecraft.options.fov().get()).intValue())));
/* 1098 */     this.minecraft.levelRenderer.renderLevel($$2, $$0, $$1, $$3, $$4, this, this.lightTexture, $$14);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1122 */     this.minecraft.getProfiler().popPush("hand");
/* 1123 */     if (this.renderHand) {
/* 1124 */       RenderSystem.clear(256, Minecraft.ON_OSX);
/* 1125 */       renderItemInHand($$2, $$4, $$0);
/*      */     } 
/* 1127 */     this.minecraft.getProfiler().pop();
/*      */   }
/*      */   
/*      */   public void resetData() {
/* 1131 */     this.itemActivationItem = null;
/* 1132 */     this.mapRenderer.resetData();
/* 1133 */     this.mainCamera.reset();
/* 1134 */     this.hasWorldScreenshot = false;
/*      */   }
/*      */   
/*      */   public MapRenderer getMapRenderer() {
/* 1138 */     return this.mapRenderer;
/*      */   }
/*      */   
/*      */   public void displayItemActivation(ItemStack $$0) {
/* 1142 */     this.itemActivationItem = $$0;
/* 1143 */     this.itemActivationTicks = 40;
/* 1144 */     this.itemActivationOffX = this.random.nextFloat() * 2.0F - 1.0F;
/* 1145 */     this.itemActivationOffY = this.random.nextFloat() * 2.0F - 1.0F;
/*      */   }
/*      */   
/*      */   private void renderItemActivationAnimation(int $$0, int $$1, float $$2) {
/* 1149 */     if (this.itemActivationItem == null || this.itemActivationTicks <= 0) {
/*      */       return;
/*      */     }
/*      */     
/* 1153 */     int $$3 = 40 - this.itemActivationTicks;
/* 1154 */     float $$4 = ($$3 + $$2) / 40.0F;
/* 1155 */     float $$5 = $$4 * $$4;
/* 1156 */     float $$6 = $$4 * $$5;
/* 1157 */     float $$7 = 10.25F * $$6 * $$5 - 24.95F * $$5 * $$5 + 25.5F * $$6 - 13.8F * $$5 + 4.0F * $$4;
/* 1158 */     float $$8 = $$7 * 3.1415927F;
/*      */     
/* 1160 */     float $$9 = this.itemActivationOffX * ($$0 / 4);
/* 1161 */     float $$10 = this.itemActivationOffY * ($$1 / 4);
/*      */     
/* 1163 */     RenderSystem.enableDepthTest();
/* 1164 */     RenderSystem.disableCull();
/* 1165 */     PoseStack $$11 = new PoseStack();
/* 1166 */     $$11.pushPose();
/* 1167 */     $$11.translate(($$0 / 2) + $$9 * Mth.abs(Mth.sin($$8 * 2.0F)), ($$1 / 2) + $$10 * Mth.abs(Mth.sin($$8 * 2.0F)), -50.0F);
/* 1168 */     float $$12 = 50.0F + 175.0F * Mth.sin($$8);
/* 1169 */     $$11.scale($$12, -$$12, $$12);
/* 1170 */     $$11.mulPose(Axis.YP.rotationDegrees(900.0F * Mth.abs(Mth.sin($$8))));
/* 1171 */     $$11.mulPose(Axis.XP.rotationDegrees(6.0F * Mth.cos($$4 * 8.0F)));
/* 1172 */     $$11.mulPose(Axis.ZP.rotationDegrees(6.0F * Mth.cos($$4 * 8.0F)));
/* 1173 */     MultiBufferSource.BufferSource $$13 = this.renderBuffers.bufferSource();
/* 1174 */     this.minecraft.getItemRenderer().renderStatic(this.itemActivationItem, ItemDisplayContext.FIXED, 15728880, OverlayTexture.NO_OVERLAY, $$11, $$13, (Level)this.minecraft.level, 0);
/* 1175 */     $$11.popPose();
/* 1176 */     $$13.endBatch();
/* 1177 */     RenderSystem.enableCull();
/* 1178 */     RenderSystem.disableDepthTest();
/*      */   }
/*      */   
/*      */   private void renderConfusionOverlay(GuiGraphics $$0, float $$1) {
/* 1182 */     int $$2 = $$0.guiWidth();
/* 1183 */     int $$3 = $$0.guiHeight();
/*      */     
/* 1185 */     $$0.pose().pushPose();
/* 1186 */     float $$4 = Mth.lerp($$1, 2.0F, 1.0F);
/* 1187 */     $$0.pose().translate($$2 / 2.0F, $$3 / 2.0F, 0.0F);
/* 1188 */     $$0.pose().scale($$4, $$4, $$4);
/* 1189 */     $$0.pose().translate(-$$2 / 2.0F, -$$3 / 2.0F, 0.0F);
/*      */     
/* 1191 */     float $$5 = 0.2F * $$1;
/* 1192 */     float $$6 = 0.4F * $$1;
/* 1193 */     float $$7 = 0.2F * $$1;
/*      */     
/* 1195 */     RenderSystem.disableDepthTest();
/* 1196 */     RenderSystem.depthMask(false);
/* 1197 */     RenderSystem.enableBlend();
/* 1198 */     RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/* 1199 */     $$0.setColor($$5, $$6, $$7, 1.0F);
/* 1200 */     $$0.blit(NAUSEA_LOCATION, 0, 0, -90, 0.0F, 0.0F, $$2, $$3, $$2, $$3);
/* 1201 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 1202 */     RenderSystem.defaultBlendFunc();
/* 1203 */     RenderSystem.disableBlend();
/* 1204 */     RenderSystem.depthMask(true);
/* 1205 */     RenderSystem.enableDepthTest();
/*      */     
/* 1207 */     $$0.pose().popPose();
/*      */   }
/*      */   
/*      */   public Minecraft getMinecraft() {
/* 1211 */     return this.minecraft;
/*      */   }
/*      */   
/*      */   public float getDarkenWorldAmount(float $$0) {
/* 1215 */     return Mth.lerp($$0, this.darkenWorldAmountO, this.darkenWorldAmount);
/*      */   }
/*      */   
/*      */   public float getRenderDistance() {
/* 1219 */     return this.renderDistance;
/*      */   }
/*      */   
/*      */   public Camera getMainCamera() {
/* 1223 */     return this.mainCamera;
/*      */   }
/*      */   
/*      */   public LightTexture lightTexture() {
/* 1227 */     return this.lightTexture;
/*      */   }
/*      */   
/*      */   public OverlayTexture overlayTexture() {
/* 1231 */     return this.overlayTexture;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionShader() {
/* 1236 */     return positionShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionColorShader() {
/* 1241 */     return positionColorShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionColorTexShader() {
/* 1246 */     return positionColorTexShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionTexShader() {
/* 1251 */     return positionTexShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionTexColorShader() {
/* 1256 */     return positionTexColorShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getParticleShader() {
/* 1261 */     return particleShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionColorLightmapShader() {
/* 1266 */     return positionColorLightmapShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionColorTexLightmapShader() {
/* 1271 */     return positionColorTexLightmapShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionTexColorNormalShader() {
/* 1276 */     return positionTexColorNormalShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getPositionTexLightmapColorShader() {
/* 1281 */     return positionTexLightmapColorShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeSolidShader() {
/* 1286 */     return rendertypeSolidShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeCutoutMippedShader() {
/* 1291 */     return rendertypeCutoutMippedShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeCutoutShader() {
/* 1296 */     return rendertypeCutoutShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTranslucentShader() {
/* 1301 */     return rendertypeTranslucentShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTranslucentMovingBlockShader() {
/* 1306 */     return rendertypeTranslucentMovingBlockShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeArmorCutoutNoCullShader() {
/* 1311 */     return rendertypeArmorCutoutNoCullShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntitySolidShader() {
/* 1316 */     return rendertypeEntitySolidShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityCutoutShader() {
/* 1321 */     return rendertypeEntityCutoutShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityCutoutNoCullShader() {
/* 1326 */     return rendertypeEntityCutoutNoCullShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityCutoutNoCullZOffsetShader() {
/* 1331 */     return rendertypeEntityCutoutNoCullZOffsetShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeItemEntityTranslucentCullShader() {
/* 1336 */     return rendertypeItemEntityTranslucentCullShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityTranslucentCullShader() {
/* 1341 */     return rendertypeEntityTranslucentCullShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityTranslucentShader() {
/* 1346 */     return rendertypeEntityTranslucentShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityTranslucentEmissiveShader() {
/* 1351 */     return rendertypeEntityTranslucentEmissiveShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntitySmoothCutoutShader() {
/* 1356 */     return rendertypeEntitySmoothCutoutShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeBeaconBeamShader() {
/* 1361 */     return rendertypeBeaconBeamShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityDecalShader() {
/* 1366 */     return rendertypeEntityDecalShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityNoOutlineShader() {
/* 1371 */     return rendertypeEntityNoOutlineShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityShadowShader() {
/* 1376 */     return rendertypeEntityShadowShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityAlphaShader() {
/* 1381 */     return rendertypeEntityAlphaShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEyesShader() {
/* 1386 */     return rendertypeEyesShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEnergySwirlShader() {
/* 1391 */     return rendertypeEnergySwirlShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeBreezeWindShader() {
/* 1396 */     return rendertypeBreezeWindShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeLeashShader() {
/* 1401 */     return rendertypeLeashShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeWaterMaskShader() {
/* 1406 */     return rendertypeWaterMaskShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeOutlineShader() {
/* 1411 */     return rendertypeOutlineShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeArmorGlintShader() {
/* 1416 */     return rendertypeArmorGlintShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeArmorEntityGlintShader() {
/* 1421 */     return rendertypeArmorEntityGlintShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGlintTranslucentShader() {
/* 1426 */     return rendertypeGlintTranslucentShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGlintShader() {
/* 1431 */     return rendertypeGlintShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGlintDirectShader() {
/* 1436 */     return rendertypeGlintDirectShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityGlintShader() {
/* 1441 */     return rendertypeEntityGlintShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEntityGlintDirectShader() {
/* 1446 */     return rendertypeEntityGlintDirectShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextShader() {
/* 1451 */     return rendertypeTextShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextBackgroundShader() {
/* 1456 */     return rendertypeTextBackgroundShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextIntensityShader() {
/* 1461 */     return rendertypeTextIntensityShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextSeeThroughShader() {
/* 1466 */     return rendertypeTextSeeThroughShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextBackgroundSeeThroughShader() {
/* 1471 */     return rendertypeTextBackgroundSeeThroughShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTextIntensitySeeThroughShader() {
/* 1476 */     return rendertypeTextIntensitySeeThroughShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeLightningShader() {
/* 1481 */     return rendertypeLightningShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeTripwireShader() {
/* 1486 */     return rendertypeTripwireShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEndPortalShader() {
/* 1491 */     return rendertypeEndPortalShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeEndGatewayShader() {
/* 1496 */     return rendertypeEndGatewayShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeLinesShader() {
/* 1501 */     return rendertypeLinesShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeCrumblingShader() {
/* 1506 */     return rendertypeCrumblingShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGuiShader() {
/* 1511 */     return rendertypeGuiShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGuiOverlayShader() {
/* 1516 */     return rendertypeGuiOverlayShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGuiTextHighlightShader() {
/* 1521 */     return rendertypeGuiTextHighlightShader;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public static ShaderInstance getRendertypeGuiGhostRecipeOverlayShader() {
/* 1526 */     return rendertypeGuiGhostRecipeOverlayShader;
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\GameRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */