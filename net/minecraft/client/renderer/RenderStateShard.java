/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalDouble;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.apache.commons.lang3.tuple.Triple;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RenderStateShard
/*     */ {
/*     */   private static final float VIEW_SCALE_Z_EPSILON = 0.99975586F;
/*     */   public static final double MAX_ENCHANTMENT_GLINT_SPEED_MILLIS = 8.0D;
/*     */   protected final String name;
/*     */   private final Runnable setupState;
/*     */   private final Runnable clearState;
/*     */   
/*     */   public RenderStateShard(String $$0, Runnable $$1, Runnable $$2) {
/*  31 */     this.name = $$0;
/*  32 */     this.setupState = $$1;
/*  33 */     this.clearState = $$2;
/*     */   }
/*     */   
/*     */   public void setupRenderState() {
/*  37 */     this.setupState.run();
/*     */   }
/*     */   
/*     */   public void clearRenderState() {
/*  41 */     this.clearState.run();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  46 */     return this.name;
/*     */   }
/*     */   
/*     */   protected static class TransparencyStateShard extends RenderStateShard {
/*     */     public TransparencyStateShard(String $$0, Runnable $$1, Runnable $$2) {
/*  51 */       super($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*  55 */   protected static final TransparencyStateShard NO_TRANSPARENCY = new TransparencyStateShard("no_transparency", () -> RenderSystem.disableBlend(), () -> {
/*     */       
/*     */       });
/*     */ 
/*     */   
/*  60 */   protected static final TransparencyStateShard ADDITIVE_TRANSPARENCY = new TransparencyStateShard("additive_transparency", () -> {
/*     */         RenderSystem.enableBlend();
/*     */         RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/*     */       }() -> {
/*     */         RenderSystem.disableBlend();
/*     */         RenderSystem.defaultBlendFunc();
/*     */       });
/*     */   
/*  68 */   protected static final TransparencyStateShard LIGHTNING_TRANSPARENCY = new TransparencyStateShard("lightning_transparency", () -> {
/*     */         RenderSystem.enableBlend();
/*     */         RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
/*     */       }() -> {
/*     */         RenderSystem.disableBlend();
/*     */         RenderSystem.defaultBlendFunc();
/*     */       });
/*     */   
/*  76 */   protected static final TransparencyStateShard GLINT_TRANSPARENCY = new TransparencyStateShard("glint_transparency", () -> {
/*     */         RenderSystem.enableBlend();
/*     */         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/*     */       }() -> {
/*     */         RenderSystem.disableBlend();
/*     */         RenderSystem.defaultBlendFunc();
/*     */       });
/*     */   
/*  84 */   protected static final TransparencyStateShard CRUMBLING_TRANSPARENCY = new TransparencyStateShard("crumbling_transparency", () -> {
/*     */         RenderSystem.enableBlend();
/*     */         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*     */       }() -> {
/*     */         RenderSystem.disableBlend();
/*     */         RenderSystem.defaultBlendFunc();
/*     */       });
/*     */   
/*  92 */   protected static final TransparencyStateShard TRANSLUCENT_TRANSPARENCY = new TransparencyStateShard("translucent_transparency", () -> {
/*     */         RenderSystem.enableBlend();
/*     */         RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/*     */       }() -> {
/*     */         RenderSystem.disableBlend();
/*     */         RenderSystem.defaultBlendFunc();
/*     */       });
/*     */   
/*     */   protected static class ShaderStateShard extends RenderStateShard {
/*     */     private final Optional<Supplier<ShaderInstance>> shader;
/*     */     
/*     */     public ShaderStateShard(Supplier<ShaderInstance> $$0) {
/* 104 */       super("shader", () -> RenderSystem.setShader($$0), () -> {
/*     */           
/*     */           });
/*     */       
/* 108 */       this.shader = Optional.of($$0);
/*     */     }
/*     */     
/*     */     public ShaderStateShard() {
/* 112 */       super("shader", () -> RenderSystem.setShader(()), () -> {
/*     */           
/*     */           });
/*     */       
/* 116 */       this.shader = Optional.empty();
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 121 */       return this.name + "[" + this.name + "]";
/*     */     }
/*     */   }
/*     */   
/* 125 */   protected static final ShaderStateShard NO_SHADER = new ShaderStateShard();
/* 126 */   protected static final ShaderStateShard POSITION_COLOR_LIGHTMAP_SHADER = new ShaderStateShard(GameRenderer::getPositionColorLightmapShader);
/* 127 */   protected static final ShaderStateShard POSITION_SHADER = new ShaderStateShard(GameRenderer::getPositionShader);
/* 128 */   protected static final ShaderStateShard POSITION_COLOR_TEX_SHADER = new ShaderStateShard(GameRenderer::getPositionColorTexShader);
/* 129 */   protected static final ShaderStateShard POSITION_TEX_SHADER = new ShaderStateShard(GameRenderer::getPositionTexShader);
/* 130 */   protected static final ShaderStateShard POSITION_COLOR_TEX_LIGHTMAP_SHADER = new ShaderStateShard(GameRenderer::getPositionColorTexLightmapShader);
/* 131 */   protected static final ShaderStateShard POSITION_COLOR_SHADER = new ShaderStateShard(GameRenderer::getPositionColorShader);
/*     */   
/* 133 */   protected static final ShaderStateShard RENDERTYPE_SOLID_SHADER = new ShaderStateShard(GameRenderer::getRendertypeSolidShader);
/* 134 */   protected static final ShaderStateShard RENDERTYPE_CUTOUT_MIPPED_SHADER = new ShaderStateShard(GameRenderer::getRendertypeCutoutMippedShader);
/* 135 */   protected static final ShaderStateShard RENDERTYPE_CUTOUT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeCutoutShader);
/* 136 */   protected static final ShaderStateShard RENDERTYPE_TRANSLUCENT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTranslucentShader);
/* 137 */   protected static final ShaderStateShard RENDERTYPE_TRANSLUCENT_MOVING_BLOCK_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTranslucentMovingBlockShader);
/* 138 */   protected static final ShaderStateShard RENDERTYPE_ARMOR_CUTOUT_NO_CULL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeArmorCutoutNoCullShader);
/* 139 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_SOLID_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntitySolidShader);
/* 140 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityCutoutShader);
/* 141 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityCutoutNoCullShader);
/* 142 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_CUTOUT_NO_CULL_Z_OFFSET_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityCutoutNoCullZOffsetShader);
/* 143 */   protected static final ShaderStateShard RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeItemEntityTranslucentCullShader);
/* 144 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityTranslucentCullShader);
/* 145 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityTranslucentShader);
/* 146 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityTranslucentEmissiveShader);
/* 147 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_SMOOTH_CUTOUT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntitySmoothCutoutShader);
/* 148 */   protected static final ShaderStateShard RENDERTYPE_BEACON_BEAM_SHADER = new ShaderStateShard(GameRenderer::getRendertypeBeaconBeamShader);
/* 149 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_DECAL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityDecalShader);
/* 150 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_NO_OUTLINE_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityNoOutlineShader);
/* 151 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_SHADOW_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityShadowShader);
/* 152 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_ALPHA_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityAlphaShader);
/* 153 */   protected static final ShaderStateShard RENDERTYPE_EYES_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEyesShader);
/* 154 */   protected static final ShaderStateShard RENDERTYPE_ENERGY_SWIRL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEnergySwirlShader);
/* 155 */   protected static final ShaderStateShard RENDERTYPE_LEASH_SHADER = new ShaderStateShard(GameRenderer::getRendertypeLeashShader);
/* 156 */   protected static final ShaderStateShard RENDERTYPE_WATER_MASK_SHADER = new ShaderStateShard(GameRenderer::getRendertypeWaterMaskShader);
/* 157 */   protected static final ShaderStateShard RENDERTYPE_OUTLINE_SHADER = new ShaderStateShard(GameRenderer::getRendertypeOutlineShader);
/* 158 */   protected static final ShaderStateShard RENDERTYPE_ARMOR_GLINT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeArmorGlintShader);
/* 159 */   protected static final ShaderStateShard RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeArmorEntityGlintShader);
/* 160 */   protected static final ShaderStateShard RENDERTYPE_GLINT_TRANSLUCENT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGlintTranslucentShader);
/* 161 */   protected static final ShaderStateShard RENDERTYPE_GLINT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGlintShader);
/* 162 */   protected static final ShaderStateShard RENDERTYPE_GLINT_DIRECT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGlintDirectShader);
/* 163 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_GLINT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityGlintShader);
/* 164 */   protected static final ShaderStateShard RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEntityGlintDirectShader);
/* 165 */   protected static final ShaderStateShard RENDERTYPE_CRUMBLING_SHADER = new ShaderStateShard(GameRenderer::getRendertypeCrumblingShader);
/* 166 */   protected static final ShaderStateShard RENDERTYPE_TEXT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextShader);
/* 167 */   protected static final ShaderStateShard RENDERTYPE_TEXT_BACKGROUND_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextBackgroundShader);
/* 168 */   protected static final ShaderStateShard RENDERTYPE_TEXT_INTENSITY_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextIntensityShader);
/* 169 */   protected static final ShaderStateShard RENDERTYPE_TEXT_SEE_THROUGH_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextSeeThroughShader);
/* 170 */   protected static final ShaderStateShard RENDERTYPE_TEXT_BACKGROUND_SEE_THROUGH_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextBackgroundSeeThroughShader);
/* 171 */   protected static final ShaderStateShard RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTextIntensitySeeThroughShader);
/* 172 */   protected static final ShaderStateShard RENDERTYPE_LIGHTNING_SHADER = new ShaderStateShard(GameRenderer::getRendertypeLightningShader);
/* 173 */   protected static final ShaderStateShard RENDERTYPE_TRIPWIRE_SHADER = new ShaderStateShard(GameRenderer::getRendertypeTripwireShader);
/* 174 */   protected static final ShaderStateShard RENDERTYPE_END_PORTAL_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEndPortalShader);
/* 175 */   protected static final ShaderStateShard RENDERTYPE_END_GATEWAY_SHADER = new ShaderStateShard(GameRenderer::getRendertypeEndGatewayShader);
/* 176 */   protected static final ShaderStateShard RENDERTYPE_LINES_SHADER = new ShaderStateShard(GameRenderer::getRendertypeLinesShader);
/* 177 */   protected static final ShaderStateShard RENDERTYPE_GUI_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGuiShader);
/* 178 */   protected static final ShaderStateShard RENDERTYPE_GUI_OVERLAY_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGuiOverlayShader);
/* 179 */   protected static final ShaderStateShard RENDERTYPE_GUI_TEXT_HIGHLIGHT_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGuiTextHighlightShader);
/* 180 */   protected static final ShaderStateShard RENDERTYPE_GUI_GHOST_RECIPE_OVERLAY_SHADER = new ShaderStateShard(GameRenderer::getRendertypeGuiGhostRecipeOverlayShader);
/* 181 */   protected static final ShaderStateShard RENDERTYPE_BREEZE_WIND_SHADER = new ShaderStateShard(GameRenderer::getRendertypeBreezeWindShader);
/*     */   
/*     */   protected static class EmptyTextureStateShard extends RenderStateShard {
/*     */     public EmptyTextureStateShard(Runnable $$0, Runnable $$1) {
/* 185 */       super("texture", $$0, $$1);
/*     */     }
/*     */     
/*     */     EmptyTextureStateShard() {
/* 189 */       super("texture", () -> {
/*     */           
/*     */           }() -> {
/*     */           
/* 193 */           }); } protected Optional<ResourceLocation> cutoutTexture() { return Optional.empty(); }
/*     */   
/*     */   }
/*     */   
/*     */   protected static class MultiTextureStateShard extends EmptyTextureStateShard {
/*     */     private final Optional<ResourceLocation> cutoutTexture;
/*     */     
/*     */     MultiTextureStateShard(ImmutableList<Triple<ResourceLocation, Boolean, Boolean>> $$0) {
/* 201 */       super(() -> {
/*     */             int $$1 = 0; UnmodifiableIterator<Triple<ResourceLocation, Boolean, Boolean>> unmodifiableIterator = $$0.iterator(); while (unmodifiableIterator.hasNext()) {
/*     */               Triple<ResourceLocation, Boolean, Boolean> $$2 = unmodifiableIterator.next(); TextureManager $$3 = Minecraft.getInstance().getTextureManager(); $$3.getTexture((ResourceLocation)$$2.getLeft()).setFilter(((Boolean)$$2.getMiddle()).booleanValue(), ((Boolean)$$2.getRight()).booleanValue());
/*     */               RenderSystem.setShaderTexture($$1++, (ResourceLocation)$$2.getLeft());
/*     */             } 
/*     */           }() -> {
/*     */           
/*     */           });
/* 209 */       this.cutoutTexture = $$0.stream().findFirst().map(Triple::getLeft);
/*     */     }
/*     */ 
/*     */     
/*     */     protected Optional<ResourceLocation> cutoutTexture() {
/* 214 */       return this.cutoutTexture;
/*     */     }
/*     */     
/*     */     public static Builder builder() {
/* 218 */       return new Builder();
/*     */     }
/*     */     
/*     */     public static final class Builder {
/* 222 */       private final ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> builder = new ImmutableList.Builder();
/*     */       
/*     */       public Builder add(ResourceLocation $$0, boolean $$1, boolean $$2) {
/* 225 */         this.builder.add(Triple.of($$0, Boolean.valueOf($$1), Boolean.valueOf($$2)));
/* 226 */         return this;
/*     */       }
/*     */       
/*     */       public RenderStateShard.MultiTextureStateShard build() {
/* 230 */         return new RenderStateShard.MultiTextureStateShard(this.builder.build()); } } } public static final class Builder { public RenderStateShard.MultiTextureStateShard build() { return new RenderStateShard.MultiTextureStateShard(this.builder.build()); }
/*     */      private final ImmutableList.Builder<Triple<ResourceLocation, Boolean, Boolean>> builder;
/*     */     public Builder() {
/*     */       this.builder = new ImmutableList.Builder();
/*     */     }
/*     */     public Builder add(ResourceLocation $$0, boolean $$1, boolean $$2) {
/*     */       this.builder.add(Triple.of($$0, Boolean.valueOf($$1), Boolean.valueOf($$2)));
/*     */       return this;
/*     */     } }
/*     */   protected static class TextureStateShard extends EmptyTextureStateShard { private final Optional<ResourceLocation> texture; private final boolean blur; private final boolean mipmap;
/*     */     public TextureStateShard(ResourceLocation $$0, boolean $$1, boolean $$2) {
/* 241 */       super(() -> {
/*     */             TextureManager $$3 = Minecraft.getInstance().getTextureManager(); $$3.getTexture($$0).setFilter($$1, $$2);
/*     */             RenderSystem.setShaderTexture(0, $$0);
/*     */           }() -> {
/*     */           
/*     */           });
/* 247 */       this.texture = Optional.of($$0);
/* 248 */       this.blur = $$1;
/* 249 */       this.mipmap = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 254 */       return this.name + "[" + this.name + "(blur=" + this.texture + ", mipmap=" + this.blur + ")]";
/*     */     }
/*     */ 
/*     */     
/*     */     protected Optional<ResourceLocation> cutoutTexture() {
/* 259 */       return this.texture;
/*     */     } }
/*     */ 
/*     */   
/* 263 */   protected static final TextureStateShard BLOCK_SHEET_MIPPED = new TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, true);
/* 264 */   protected static final TextureStateShard BLOCK_SHEET = new TextureStateShard(TextureAtlas.LOCATION_BLOCKS, false, false);
/* 265 */   protected static final EmptyTextureStateShard NO_TEXTURE = new EmptyTextureStateShard();
/*     */   
/*     */   protected static class TexturingStateShard extends RenderStateShard {
/*     */     public TexturingStateShard(String $$0, Runnable $$1, Runnable $$2) {
/* 269 */       super($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/* 273 */   protected static final TexturingStateShard DEFAULT_TEXTURING = new TexturingStateShard("default_texturing", () -> {
/*     */       
/*     */       }() -> {
/*     */       
/*     */       });
/*     */   
/*     */   protected static final class OffsetTexturingStateShard extends TexturingStateShard { public OffsetTexturingStateShard(float $$0, float $$1) {
/* 280 */       super("offset_texturing", () -> RenderSystem.setTextureMatrix((new Matrix4f()).translation($$0, $$1, 0.0F)), () -> RenderSystem.resetTextureMatrix());
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void setupGlintTexturing(float $$0) {
/* 290 */     long $$1 = (long)(Util.getMillis() * ((Double)(Minecraft.getInstance()).options.glintSpeed().get()).doubleValue() * 8.0D);
/* 291 */     float $$2 = (float)($$1 % 110000L) / 110000.0F;
/* 292 */     float $$3 = (float)($$1 % 30000L) / 30000.0F;
/*     */ 
/*     */     
/* 295 */     Matrix4f $$4 = (new Matrix4f()).translation(-$$2, $$3, 0.0F);
/*     */ 
/*     */     
/* 298 */     $$4.rotateZ(0.17453292F).scale($$0);
/* 299 */     RenderSystem.setTextureMatrix($$4);
/*     */   }
/*     */   
/* 302 */   protected static final TexturingStateShard GLINT_TEXTURING = new TexturingStateShard("glint_texturing", () -> setupGlintTexturing(8.0F), () -> RenderSystem.resetTextureMatrix());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 308 */   protected static final TexturingStateShard ENTITY_GLINT_TEXTURING = new TexturingStateShard("entity_glint_texturing", () -> setupGlintTexturing(0.16F), () -> RenderSystem.resetTextureMatrix());
/*     */ 
/*     */   
/*     */   private static class BooleanStateShard
/*     */     extends RenderStateShard
/*     */   {
/*     */     private final boolean enabled;
/*     */ 
/*     */     
/*     */     public BooleanStateShard(String $$0, Runnable $$1, Runnable $$2, boolean $$3) {
/* 318 */       super($$0, $$1, $$2);
/* 319 */       this.enabled = $$3;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 324 */       return this.name + "[" + this.name + "]";
/*     */     }
/*     */   }
/*     */   
/*     */   protected static class LightmapStateShard extends BooleanStateShard {
/*     */     public LightmapStateShard(boolean $$0) {
/* 330 */       super("lightmap", () -> { if ($$0) (Minecraft.getInstance()).gameRenderer.lightTexture().turnOnLightLayer();  }() -> { if ($$0) (Minecraft.getInstance()).gameRenderer.lightTexture().turnOffLightLayer();  }$$0);
/*     */     }
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
/* 342 */   protected static final LightmapStateShard LIGHTMAP = new LightmapStateShard(true);
/* 343 */   protected static final LightmapStateShard NO_LIGHTMAP = new LightmapStateShard(false);
/*     */   
/*     */   protected static class OverlayStateShard extends BooleanStateShard {
/*     */     public OverlayStateShard(boolean $$0) {
/* 347 */       super("overlay", () -> { if ($$0) (Minecraft.getInstance()).gameRenderer.overlayTexture().setupOverlayColor();  }() -> { if ($$0) (Minecraft.getInstance()).gameRenderer.overlayTexture().teardownOverlayColor();  }$$0);
/*     */     }
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
/* 359 */   protected static final OverlayStateShard OVERLAY = new OverlayStateShard(true);
/* 360 */   protected static final OverlayStateShard NO_OVERLAY = new OverlayStateShard(false);
/*     */   
/*     */   protected static class CullStateShard extends BooleanStateShard {
/*     */     public CullStateShard(boolean $$0) {
/* 364 */       super("cull", () -> { if (!$$0) RenderSystem.disableCull();  }() -> { if (!$$0) RenderSystem.enableCull();  }$$0);
/*     */     }
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
/* 376 */   protected static final CullStateShard CULL = new CullStateShard(true);
/* 377 */   protected static final CullStateShard NO_CULL = new CullStateShard(false);
/*     */   
/*     */   protected static class DepthTestStateShard extends RenderStateShard {
/*     */     private final String functionName;
/*     */     
/*     */     public DepthTestStateShard(String $$0, int $$1) {
/* 383 */       super("depth_test", () -> {
/*     */             if ($$0 != 519) {
/*     */               RenderSystem.enableDepthTest();
/*     */               RenderSystem.depthFunc($$0);
/*     */             } 
/*     */           }() -> {
/*     */             if ($$0 != 519) {
/*     */               RenderSystem.disableDepthTest();
/*     */               RenderSystem.depthFunc(515);
/*     */             } 
/*     */           });
/* 394 */       this.functionName = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 399 */       return this.name + "[" + this.name + "]";
/*     */     }
/*     */   }
/*     */   
/* 403 */   protected static final DepthTestStateShard NO_DEPTH_TEST = new DepthTestStateShard("always", 519);
/* 404 */   protected static final DepthTestStateShard EQUAL_DEPTH_TEST = new DepthTestStateShard("==", 514);
/* 405 */   protected static final DepthTestStateShard LEQUAL_DEPTH_TEST = new DepthTestStateShard("<=", 515);
/* 406 */   protected static final DepthTestStateShard GREATER_DEPTH_TEST = new DepthTestStateShard(">", 516);
/*     */   
/*     */   protected static class WriteMaskStateShard extends RenderStateShard {
/*     */     private final boolean writeColor;
/*     */     private final boolean writeDepth;
/*     */     
/*     */     public WriteMaskStateShard(boolean $$0, boolean $$1) {
/* 413 */       super("write_mask_state", () -> {
/*     */             if (!$$0) {
/*     */               RenderSystem.depthMask($$0);
/*     */             }
/*     */             if (!$$1) {
/*     */               RenderSystem.colorMask($$1, $$1, $$1, $$1);
/*     */             }
/*     */           }() -> {
/*     */             if (!$$0) {
/*     */               RenderSystem.depthMask(true);
/*     */             }
/*     */             if (!$$1) {
/*     */               RenderSystem.colorMask(true, true, true, true);
/*     */             }
/*     */           });
/* 428 */       this.writeColor = $$0;
/* 429 */       this.writeDepth = $$1;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 434 */       return this.name + "[writeColor=" + this.name + ", writeDepth=" + this.writeColor + "]";
/*     */     }
/*     */   }
/*     */   
/* 438 */   protected static final WriteMaskStateShard COLOR_DEPTH_WRITE = new WriteMaskStateShard(true, true);
/* 439 */   protected static final WriteMaskStateShard COLOR_WRITE = new WriteMaskStateShard(true, false);
/* 440 */   protected static final WriteMaskStateShard DEPTH_WRITE = new WriteMaskStateShard(false, true);
/*     */   
/*     */   protected static class LayeringStateShard extends RenderStateShard {
/*     */     public LayeringStateShard(String $$0, Runnable $$1, Runnable $$2) {
/* 444 */       super($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/* 448 */   protected static final LayeringStateShard NO_LAYERING = new LayeringStateShard("no_layering", () -> {
/*     */       
/*     */       }() -> {
/*     */       
/* 452 */       }); protected static final LayeringStateShard POLYGON_OFFSET_LAYERING = new LayeringStateShard("polygon_offset_layering", () -> {
/*     */         RenderSystem.polygonOffset(-1.0F, -10.0F);
/*     */         RenderSystem.enablePolygonOffset();
/*     */       }() -> {
/*     */         RenderSystem.polygonOffset(0.0F, 0.0F);
/*     */         RenderSystem.disablePolygonOffset();
/*     */       }); protected static final LayeringStateShard VIEW_OFFSET_Z_LAYERING;
/*     */   static {
/* 460 */     VIEW_OFFSET_Z_LAYERING = new LayeringStateShard("view_offset_z_layering", () -> {
/*     */           PoseStack $$0 = RenderSystem.getModelViewStack();
/*     */           $$0.pushPose();
/*     */           $$0.scale(0.99975586F, 0.99975586F, 0.99975586F);
/*     */           RenderSystem.applyModelViewMatrix();
/*     */         }() -> {
/*     */           PoseStack $$0 = RenderSystem.getModelViewStack();
/*     */           $$0.popPose();
/*     */           RenderSystem.applyModelViewMatrix();
/*     */         });
/*     */   }
/*     */   
/*     */   protected static class OutputStateShard extends RenderStateShard { public OutputStateShard(String $$0, Runnable $$1, Runnable $$2) {
/* 473 */       super($$0, $$1, $$2);
/*     */     } }
/*     */ 
/*     */   
/* 477 */   protected static final OutputStateShard MAIN_TARGET = new OutputStateShard("main_target", () -> {
/*     */       
/*     */       }() -> {
/*     */       
/* 481 */       }); protected static final OutputStateShard OUTLINE_TARGET = new OutputStateShard("outline_target", () -> (Minecraft.getInstance()).levelRenderer.entityTarget().bindWrite(false), () -> Minecraft.getInstance().getMainRenderTarget().bindWrite(false));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 487 */   protected static final OutputStateShard TRANSLUCENT_TARGET = new OutputStateShard("translucent_target", () -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           (Minecraft.getInstance()).levelRenderer.getTranslucentTarget().bindWrite(false);
/*     */         }
/*     */       }() -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
/*     */         }
/*     */       });
/*     */   
/* 497 */   protected static final OutputStateShard PARTICLES_TARGET = new OutputStateShard("particles_target", () -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           (Minecraft.getInstance()).levelRenderer.getParticlesTarget().bindWrite(false);
/*     */         }
/*     */       }() -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
/*     */         }
/*     */       });
/*     */   
/* 507 */   protected static final OutputStateShard WEATHER_TARGET = new OutputStateShard("weather_target", () -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           (Minecraft.getInstance()).levelRenderer.getWeatherTarget().bindWrite(false);
/*     */         }
/*     */       }() -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
/*     */         }
/*     */       });
/*     */   
/* 517 */   protected static final OutputStateShard CLOUDS_TARGET = new OutputStateShard("clouds_target", () -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           (Minecraft.getInstance()).levelRenderer.getCloudsTarget().bindWrite(false);
/*     */         }
/*     */       }() -> {
/*     */         if (Minecraft.useShaderTransparency()) {
/*     */           Minecraft.getInstance().getMainRenderTarget().bindWrite(false);
/*     */         }
/*     */       });
/*     */   
/* 527 */   protected static final OutputStateShard ITEM_ENTITY_TARGET = new OutputStateShard("item_entity_target", () -> {
/*     */         if (Minecraft.useShaderTransparency())
/*     */           (Minecraft.getInstance()).levelRenderer.getItemEntityTarget().bindWrite(false); 
/*     */       }() -> {
/*     */         if (Minecraft.useShaderTransparency())
/*     */           Minecraft.getInstance().getMainRenderTarget().bindWrite(false); 
/*     */       });
/*     */   
/*     */   protected static class LineStateShard
/*     */     extends RenderStateShard
/*     */   {
/*     */     private final OptionalDouble width;
/*     */     
/*     */     public LineStateShard(OptionalDouble $$0) {
/* 541 */       super("line_width", () -> {
/*     */             if (!Objects.equals($$0, OptionalDouble.of(1.0D))) {
/*     */               if ($$0.isPresent()) {
/*     */                 RenderSystem.lineWidth((float)$$0.getAsDouble());
/*     */               } else {
/*     */                 RenderSystem.lineWidth(Math.max(2.5F, Minecraft.getInstance().getWindow().getWidth() / 1920.0F * 2.5F));
/*     */               } 
/*     */             }
/*     */           }() -> {
/*     */             if (!Objects.equals($$0, OptionalDouble.of(1.0D))) {
/*     */               RenderSystem.lineWidth(1.0F);
/*     */             }
/*     */           });
/* 554 */       this.width = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 559 */       return this.name + "[" + this.name + "]";
/*     */     }
/*     */   }
/*     */   
/* 563 */   protected static final LineStateShard DEFAULT_LINE = new LineStateShard(OptionalDouble.of(1.0D));
/*     */   
/*     */   protected static class ColorLogicStateShard extends RenderStateShard {
/*     */     public ColorLogicStateShard(String $$0, Runnable $$1, Runnable $$2) {
/* 567 */       super($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/* 571 */   protected static final ColorLogicStateShard NO_COLOR_LOGIC = new ColorLogicStateShard("no_color_logic", () -> RenderSystem.disableColorLogicOp(), () -> {
/*     */       
/*     */       });
/*     */ 
/*     */   
/* 576 */   protected static final ColorLogicStateShard OR_REVERSE_COLOR_LOGIC = new ColorLogicStateShard("or_reverse", () -> {
/*     */         RenderSystem.enableColorLogicOp();
/*     */         RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
/*     */       }() -> RenderSystem.disableColorLogicOp());
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderStateShard.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */