/*      */ package net.minecraft.client.renderer;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*      */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*      */ import com.mojang.blaze3d.vertex.VertexFormat;
/*      */ import java.util.Optional;
/*      */ import java.util.OptionalDouble;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Function;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.client.renderer.blockentity.TheEndPortalRenderer;
/*      */ import net.minecraft.client.renderer.entity.ItemRenderer;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ 
/*      */ public abstract class RenderType extends RenderStateShard {
/*      */   private static final int MEGABYTE = 1048576;
/*      */   public static final int BIG_BUFFER_SIZE = 4194304;
/*      */   public static final int SMALL_BUFFER_SIZE = 786432;
/*      */   public static final int TRANSIENT_BUFFER_SIZE = 1536;
/*      */   
/*      */   private enum OutlineProperty {
/*   22 */     NONE("none"),
/*   23 */     IS_OUTLINE("is_outline"),
/*   24 */     AFFECTS_OUTLINE("affects_outline");
/*      */     
/*      */     private final String name;
/*      */ 
/*      */     
/*      */     OutlineProperty(String $$0) {
/*   30 */       this.name = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/*   35 */       return this.name;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   47 */   private static final RenderType SOLID = create("solid", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 4194304, true, false, CompositeState.builder()
/*   48 */       .setLightmapState(LIGHTMAP)
/*   49 */       .setShaderState(RENDERTYPE_SOLID_SHADER)
/*   50 */       .setTextureState(BLOCK_SHEET_MIPPED)
/*   51 */       .createCompositeState(true));
/*      */   
/*      */   public static RenderType solid() {
/*   54 */     return SOLID;
/*      */   }
/*      */   
/*   57 */   private static final RenderType CUTOUT_MIPPED = create("cutout_mipped", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 4194304, true, false, CompositeState.builder()
/*   58 */       .setLightmapState(LIGHTMAP)
/*   59 */       .setShaderState(RENDERTYPE_CUTOUT_MIPPED_SHADER)
/*   60 */       .setTextureState(BLOCK_SHEET_MIPPED)
/*   61 */       .createCompositeState(true));
/*      */   
/*      */   public static RenderType cutoutMipped() {
/*   64 */     return CUTOUT_MIPPED;
/*      */   }
/*      */   
/*   67 */   private static final RenderType CUTOUT = create("cutout", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 786432, true, false, CompositeState.builder()
/*   68 */       .setLightmapState(LIGHTMAP)
/*   69 */       .setShaderState(RENDERTYPE_CUTOUT_SHADER)
/*   70 */       .setTextureState(BLOCK_SHEET)
/*   71 */       .createCompositeState(true));
/*      */   
/*      */   public static RenderType cutout() {
/*   74 */     return CUTOUT;
/*      */   }
/*      */   
/*      */   private static CompositeState translucentState(RenderStateShard.ShaderStateShard $$0) {
/*   78 */     return CompositeState.builder()
/*   79 */       .setLightmapState(LIGHTMAP)
/*   80 */       .setShaderState($$0)
/*   81 */       .setTextureState(BLOCK_SHEET_MIPPED)
/*   82 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*   83 */       .setOutputState(TRANSLUCENT_TARGET)
/*   84 */       .createCompositeState(true);
/*      */   }
/*      */   
/*   87 */   private static final RenderType TRANSLUCENT = create("translucent", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 786432, true, true, translucentState(RENDERTYPE_TRANSLUCENT_SHADER));
/*      */   
/*      */   public static RenderType translucent() {
/*   90 */     return TRANSLUCENT;
/*      */   }
/*      */   
/*      */   private static CompositeState translucentMovingBlockState() {
/*   94 */     return CompositeState.builder()
/*   95 */       .setLightmapState(LIGHTMAP)
/*   96 */       .setShaderState(RENDERTYPE_TRANSLUCENT_MOVING_BLOCK_SHADER)
/*   97 */       .setTextureState(BLOCK_SHEET_MIPPED)
/*   98 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*   99 */       .setOutputState(ITEM_ENTITY_TARGET)
/*  100 */       .createCompositeState(true);
/*      */   }
/*      */   
/*  103 */   private static final RenderType TRANSLUCENT_MOVING_BLOCK = create("translucent_moving_block", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 786432, false, true, translucentMovingBlockState()); private static final Function<ResourceLocation, RenderType> ARMOR_CUTOUT_NO_CULL; private static final Function<ResourceLocation, RenderType> ENTITY_SOLID; private static final Function<ResourceLocation, RenderType> ENTITY_CUTOUT; private static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_CUTOUT_NO_CULL; private static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_CUTOUT_NO_CULL_Z_OFFSET; private static final Function<ResourceLocation, RenderType> ITEM_ENTITY_TRANSLUCENT_CULL; private static final Function<ResourceLocation, RenderType> ENTITY_TRANSLUCENT_CULL; private static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT; private static final BiFunction<ResourceLocation, Boolean, RenderType> ENTITY_TRANSLUCENT_EMISSIVE; private static final Function<ResourceLocation, RenderType> ENTITY_SMOOTH_CUTOUT; private static final BiFunction<ResourceLocation, Boolean, RenderType> BEACON_BEAM; private static final Function<ResourceLocation, RenderType> ENTITY_DECAL; private static final Function<ResourceLocation, RenderType> ENTITY_NO_OUTLINE; private static final Function<ResourceLocation, RenderType> ENTITY_SHADOW; private static final Function<ResourceLocation, RenderType> DRAGON_EXPLOSION_ALPHA; private static final BiFunction<ResourceLocation, RenderStateShard.TransparencyStateShard, RenderType> EYES;
/*      */   
/*      */   public static RenderType translucentMovingBlock() {
/*  106 */     return TRANSLUCENT_MOVING_BLOCK;
/*      */   }
/*      */   
/*  109 */   static { ARMOR_CUTOUT_NO_CULL = Util.memoize($$0 -> createArmorCutoutNoCull("armor_cutout_no_cull", $$0, false));
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
/*  134 */     ENTITY_SOLID = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_SOLID_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_solid", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  150 */     ENTITY_CUTOUT = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_CUTOUT_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(NO_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  166 */     ENTITY_CUTOUT_NO_CULL = Util.memoize(($$0, $$1) -> {
/*      */           CompositeState $$2 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_CUTOUT_NO_CULL_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState($$1.booleanValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_cutout_no_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, $$2);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  187 */     ENTITY_CUTOUT_NO_CULL_Z_OFFSET = Util.memoize(($$0, $$1) -> {
/*      */           CompositeState $$2 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_CUTOUT_NO_CULL_Z_OFFSET_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState($$1.booleanValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_cutout_no_cull_z_offset", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, $$2);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  209 */     ITEM_ENTITY_TRANSLUCENT_CULL = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setOutputState(ITEM_ENTITY_TARGET).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("item_entity_translucent_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  227 */     ENTITY_TRANSLUCENT_CULL = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_CULL_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_translucent_cull", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  243 */     ENTITY_TRANSLUCENT = Util.memoize(($$0, $$1) -> {
/*      */           CompositeState $$2 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState($$1.booleanValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_translucent", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, $$2);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  264 */     ENTITY_TRANSLUCENT_EMISSIVE = Util.memoize(($$0, $$1) -> {
/*      */           CompositeState $$2 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_TRANSLUCENT_EMISSIVE_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setWriteMaskState(COLOR_WRITE).setOverlayState(OVERLAY).createCompositeState($$1.booleanValue());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_translucent_emissive", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, true, $$2);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  285 */     ENTITY_SMOOTH_CUTOUT = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_SMOOTH_CUTOUT_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setCullState(NO_CULL).setLightmapState(LIGHTMAP).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_smooth_cutout", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  300 */     BEACON_BEAM = Util.memoize(($$0, $$1) -> {
/*      */           CompositeState $$2 = CompositeState.builder().setShaderState(RENDERTYPE_BEACON_BEAM_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState($$1.booleanValue() ? TRANSLUCENT_TRANSPARENCY : NO_TRANSPARENCY).setWriteMaskState($$1.booleanValue() ? COLOR_WRITE : COLOR_DEPTH_WRITE).createCompositeState(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("beacon_beam", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, false, true, $$2);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  315 */     ENTITY_DECAL = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_DECAL_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setDepthTestState(EQUAL_DEPTH_TEST).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).createCompositeState(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_decal", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  332 */     ENTITY_NO_OUTLINE = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_NO_OUTLINE_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).createCompositeState(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_no_outline", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  350 */     ENTITY_SHADOW = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_SHADOW_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setCullState(CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setWriteMaskState(COLOR_WRITE).setDepthTestState(LEQUAL_DEPTH_TEST).setLayeringState(VIEW_OFFSET_Z_LAYERING).createCompositeState(false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_shadow", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, false, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  370 */     DRAGON_EXPLOSION_ALPHA = Util.memoize($$0 -> {
/*      */           CompositeState $$1 = CompositeState.builder().setShaderState(RENDERTYPE_ENTITY_ALPHA_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setCullState(NO_CULL).createCompositeState(true);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("entity_alpha", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, $$1);
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  384 */     EYES = Util.memoize(($$0, $$1) -> { RenderStateShard.TextureStateShard $$2 = new RenderStateShard.TextureStateShard($$0, false, false); return create("eyes", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_EYES_SHADER).setTextureState($$2).setTransparencyState($$1).setWriteMaskState(COLOR_WRITE).createCompositeState(false)); }); } private static CompositeRenderType createArmorCutoutNoCull(String $$0, ResourceLocation $$1, boolean $$2) { CompositeState $$3 = CompositeState.builder().setShaderState(RENDERTYPE_ARMOR_CUTOUT_NO_CULL_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$1, false, false)).setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).setLightmapState(LIGHTMAP).setOverlayState(OVERLAY).setLayeringState(VIEW_OFFSET_Z_LAYERING).setDepthTestState($$2 ? EQUAL_DEPTH_TEST : LEQUAL_DEPTH_TEST).createCompositeState(true); return create($$0, DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, true, false, $$3); } public static RenderType armorCutoutNoCull(ResourceLocation $$0) { return ARMOR_CUTOUT_NO_CULL.apply($$0); } public static RenderType createArmorDecalCutoutNoCull(ResourceLocation $$0) { return createArmorCutoutNoCull("armor_decal_cutout_no_cull", $$0, true); } public static RenderType entitySolid(ResourceLocation $$0) { return ENTITY_SOLID.apply($$0); } public static RenderType entityCutout(ResourceLocation $$0) { return ENTITY_CUTOUT.apply($$0); } public static RenderType entityCutoutNoCull(ResourceLocation $$0, boolean $$1) { return ENTITY_CUTOUT_NO_CULL.apply($$0, Boolean.valueOf($$1)); } public static RenderType entityCutoutNoCull(ResourceLocation $$0) { return entityCutoutNoCull($$0, true); } public static RenderType entityCutoutNoCullZOffset(ResourceLocation $$0, boolean $$1) { return ENTITY_CUTOUT_NO_CULL_Z_OFFSET.apply($$0, Boolean.valueOf($$1)); } public static RenderType entityCutoutNoCullZOffset(ResourceLocation $$0) { return entityCutoutNoCullZOffset($$0, true); } public static RenderType itemEntityTranslucentCull(ResourceLocation $$0) { return ITEM_ENTITY_TRANSLUCENT_CULL.apply($$0); } public static RenderType entityTranslucentCull(ResourceLocation $$0) { return ENTITY_TRANSLUCENT_CULL.apply($$0); }
/*      */   public static RenderType entityTranslucent(ResourceLocation $$0, boolean $$1) { return ENTITY_TRANSLUCENT.apply($$0, Boolean.valueOf($$1)); }
/*      */   public static RenderType entityTranslucent(ResourceLocation $$0) { return entityTranslucent($$0, true); }
/*      */   public static RenderType entityTranslucentEmissive(ResourceLocation $$0, boolean $$1) { return ENTITY_TRANSLUCENT_EMISSIVE.apply($$0, Boolean.valueOf($$1)); }
/*      */   public static RenderType entityTranslucentEmissive(ResourceLocation $$0) { return entityTranslucentEmissive($$0, true); }
/*      */   public static RenderType entitySmoothCutout(ResourceLocation $$0) { return ENTITY_SMOOTH_CUTOUT.apply($$0); }
/*      */   public static RenderType beaconBeam(ResourceLocation $$0, boolean $$1) { return BEACON_BEAM.apply($$0, Boolean.valueOf($$1)); }
/*      */   public static RenderType entityDecal(ResourceLocation $$0) { return ENTITY_DECAL.apply($$0); }
/*      */   public static RenderType entityNoOutline(ResourceLocation $$0) { return ENTITY_NO_OUTLINE.apply($$0); }
/*      */   public static RenderType entityShadow(ResourceLocation $$0) { return ENTITY_SHADOW.apply($$0); }
/*      */   public static RenderType dragonExplosionAlpha(ResourceLocation $$0) { return DRAGON_EXPLOSION_ALPHA.apply($$0); }
/*  395 */   public static RenderType eyes(ResourceLocation $$0) { return EYES.apply($$0, ADDITIVE_TRANSPARENCY); }
/*      */ 
/*      */   
/*      */   public static RenderType breezeEyes(ResourceLocation $$0) {
/*  399 */     return ENTITY_TRANSLUCENT_EMISSIVE.apply($$0, Boolean.valueOf(false));
/*      */   }
/*      */   
/*      */   public static RenderType breezeWind(ResourceLocation $$0, float $$1, float $$2) {
/*  403 */     return create("breeze_wind", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  404 */         .setShaderState(RENDERTYPE_BREEZE_WIND_SHADER)
/*  405 */         .setTextureState(new RenderStateShard.TextureStateShard($$0, false, false))
/*  406 */         .setTexturingState(new RenderStateShard.OffsetTexturingStateShard($$1, $$2))
/*  407 */         .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  408 */         .setCullState(NO_CULL)
/*  409 */         .setLightmapState(LIGHTMAP)
/*  410 */         .setOverlayState(NO_OVERLAY)
/*  411 */         .createCompositeState(false));
/*      */   }
/*      */ 
/*      */   
/*      */   public static RenderType energySwirl(ResourceLocation $$0, float $$1, float $$2) {
/*  416 */     return create("energy_swirl", DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  417 */         .setShaderState(RENDERTYPE_ENERGY_SWIRL_SHADER)
/*  418 */         .setTextureState(new RenderStateShard.TextureStateShard($$0, false, false))
/*  419 */         .setTexturingState(new RenderStateShard.OffsetTexturingStateShard($$1, $$2))
/*  420 */         .setTransparencyState(ADDITIVE_TRANSPARENCY)
/*  421 */         .setCullState(NO_CULL)
/*  422 */         .setLightmapState(LIGHTMAP)
/*  423 */         .setOverlayState(OVERLAY)
/*  424 */         .createCompositeState(false));
/*      */   }
/*      */   
/*  427 */   private static final RenderType LEASH = create("leash", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, VertexFormat.Mode.TRIANGLE_STRIP, 1536, CompositeState.builder()
/*  428 */       .setShaderState(RENDERTYPE_LEASH_SHADER)
/*  429 */       .setTextureState(NO_TEXTURE)
/*  430 */       .setCullState(NO_CULL)
/*  431 */       .setLightmapState(LIGHTMAP)
/*  432 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType leash() {
/*  435 */     return LEASH;
/*      */   }
/*      */   
/*  438 */   private static final RenderType WATER_MASK = create("water_mask", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  439 */       .setShaderState(RENDERTYPE_WATER_MASK_SHADER)
/*  440 */       .setTextureState(NO_TEXTURE)
/*  441 */       .setWriteMaskState(DEPTH_WRITE)
/*  442 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType waterMask() {
/*  445 */     return WATER_MASK;
/*      */   }
/*      */   
/*      */   public static RenderType outline(ResourceLocation $$0) {
/*  449 */     return CompositeRenderType.OUTLINE.apply($$0, NO_CULL);
/*      */   }
/*      */   
/*  452 */   private static final RenderType ARMOR_GLINT = create("armor_glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  453 */       .setShaderState(RENDERTYPE_ARMOR_GLINT_SHADER)
/*  454 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
/*  455 */       .setWriteMaskState(COLOR_WRITE)
/*  456 */       .setCullState(NO_CULL)
/*  457 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  458 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  459 */       .setTexturingState(GLINT_TEXTURING)
/*  460 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  461 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType armorGlint() {
/*  464 */     return ARMOR_GLINT;
/*      */   }
/*      */   
/*  467 */   private static final RenderType ARMOR_ENTITY_GLINT = create("armor_entity_glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  468 */       .setShaderState(RENDERTYPE_ARMOR_ENTITY_GLINT_SHADER)
/*  469 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
/*  470 */       .setWriteMaskState(COLOR_WRITE)
/*  471 */       .setCullState(NO_CULL)
/*  472 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  473 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  474 */       .setTexturingState(ENTITY_GLINT_TEXTURING)
/*  475 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  476 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType armorEntityGlint() {
/*  479 */     return ARMOR_ENTITY_GLINT;
/*      */   }
/*      */   
/*  482 */   private static final RenderType GLINT_TRANSLUCENT = create("glint_translucent", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  483 */       .setShaderState(RENDERTYPE_GLINT_TRANSLUCENT_SHADER)
/*  484 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
/*  485 */       .setWriteMaskState(COLOR_WRITE)
/*  486 */       .setCullState(NO_CULL)
/*  487 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  488 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  489 */       .setTexturingState(GLINT_TEXTURING)
/*  490 */       .setOutputState(ITEM_ENTITY_TARGET)
/*  491 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType glintTranslucent() {
/*  494 */     return GLINT_TRANSLUCENT;
/*      */   }
/*      */   
/*  497 */   private static final RenderType GLINT = create("glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  498 */       .setShaderState(RENDERTYPE_GLINT_SHADER)
/*  499 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
/*  500 */       .setWriteMaskState(COLOR_WRITE)
/*  501 */       .setCullState(NO_CULL)
/*  502 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  503 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  504 */       .setTexturingState(GLINT_TEXTURING)
/*  505 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType glint() {
/*  508 */     return GLINT;
/*      */   }
/*      */   
/*  511 */   private static final RenderType GLINT_DIRECT = create("glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  512 */       .setShaderState(RENDERTYPE_GLINT_DIRECT_SHADER)
/*  513 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ITEM, true, false))
/*  514 */       .setWriteMaskState(COLOR_WRITE)
/*  515 */       .setCullState(NO_CULL)
/*  516 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  517 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  518 */       .setTexturingState(GLINT_TEXTURING)
/*  519 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType glintDirect() {
/*  522 */     return GLINT_DIRECT;
/*      */   }
/*      */   
/*  525 */   private static final RenderType ENTITY_GLINT = create("entity_glint", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  526 */       .setShaderState(RENDERTYPE_ENTITY_GLINT_SHADER)
/*  527 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
/*  528 */       .setWriteMaskState(COLOR_WRITE)
/*  529 */       .setCullState(NO_CULL)
/*  530 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  531 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  532 */       .setOutputState(ITEM_ENTITY_TARGET)
/*  533 */       .setTexturingState(ENTITY_GLINT_TEXTURING)
/*  534 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType entityGlint() {
/*  537 */     return ENTITY_GLINT;
/*      */   }
/*      */   
/*  540 */   private static final RenderType ENTITY_GLINT_DIRECT = create("entity_glint_direct", DefaultVertexFormat.POSITION_TEX, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  541 */       .setShaderState(RENDERTYPE_ENTITY_GLINT_DIRECT_SHADER)
/*  542 */       .setTextureState(new RenderStateShard.TextureStateShard(ItemRenderer.ENCHANTED_GLINT_ENTITY, true, false))
/*  543 */       .setWriteMaskState(COLOR_WRITE)
/*  544 */       .setCullState(NO_CULL)
/*  545 */       .setDepthTestState(EQUAL_DEPTH_TEST)
/*  546 */       .setTransparencyState(GLINT_TRANSPARENCY)
/*  547 */       .setTexturingState(ENTITY_GLINT_TEXTURING)
/*  548 */       .createCompositeState(false)); private static final Function<ResourceLocation, RenderType> CRUMBLING; private static final Function<ResourceLocation, RenderType> TEXT;
/*      */   
/*      */   public static RenderType entityGlintDirect() {
/*  551 */     return ENTITY_GLINT_DIRECT;
/*      */   }
/*      */   static {
/*  554 */     CRUMBLING = Util.memoize($$0 -> {
/*      */           RenderStateShard.TextureStateShard $$1 = new RenderStateShard.TextureStateShard($$0, false, false);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           return create("crumbling", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_CRUMBLING_SHADER).setTextureState($$1).setTransparencyState(CRUMBLING_TRANSPARENCY).setWriteMaskState(COLOR_WRITE).setLayeringState(POLYGON_OFFSET_LAYERING).createCompositeState(false));
/*      */         });
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  569 */     TEXT = Util.memoize($$0 -> create("text", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 786432, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).createCompositeState(false)));
/*      */   }
/*      */ 
/*      */   
/*      */   public static RenderType crumbling(ResourceLocation $$0) {
/*      */     return CRUMBLING.apply($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static RenderType text(ResourceLocation $$0) {
/*  579 */     return TEXT.apply($$0);
/*      */   }
/*      */ 
/*      */   
/*  583 */   private static final RenderType TEXT_BACKGROUND = create("text_background", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  584 */       .setShaderState(RENDERTYPE_TEXT_BACKGROUND_SHADER)
/*  585 */       .setTextureState(NO_TEXTURE)
/*  586 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  587 */       .setLightmapState(LIGHTMAP)
/*  588 */       .createCompositeState(false)); private static final Function<ResourceLocation, RenderType> TEXT_INTENSITY; private static final Function<ResourceLocation, RenderType> TEXT_POLYGON_OFFSET; private static final Function<ResourceLocation, RenderType> TEXT_INTENSITY_POLYGON_OFFSET; private static final Function<ResourceLocation, RenderType> TEXT_SEE_THROUGH;
/*      */   
/*      */   public static RenderType textBackground() {
/*  591 */     return TEXT_BACKGROUND;
/*      */   }
/*      */   static {
/*  594 */     TEXT_INTENSITY = Util.memoize($$0 -> create("text_intensity", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 786432, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_INTENSITY_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).createCompositeState(false)));
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
/*  607 */     TEXT_POLYGON_OFFSET = Util.memoize($$0 -> create("text_polygon_offset", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setLayeringState(POLYGON_OFFSET_LAYERING).createCompositeState(false)));
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
/*  621 */     TEXT_INTENSITY_POLYGON_OFFSET = Util.memoize($$0 -> create("text_intensity_polygon_offset", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_INTENSITY_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setLayeringState(POLYGON_OFFSET_LAYERING).createCompositeState(false)));
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
/*  635 */     TEXT_SEE_THROUGH = Util.memoize($$0 -> create("text_see_through", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_SEE_THROUGH_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setDepthTestState(NO_DEPTH_TEST).setWriteMaskState(COLOR_WRITE).createCompositeState(false)));
/*      */   }
/*      */   public static RenderType textIntensity(ResourceLocation $$0) {
/*      */     return TEXT_INTENSITY.apply($$0);
/*      */   }
/*      */   public static RenderType textPolygonOffset(ResourceLocation $$0) {
/*      */     return TEXT_POLYGON_OFFSET.apply($$0);
/*      */   }
/*      */   public static RenderType textIntensityPolygonOffset(ResourceLocation $$0) {
/*      */     return TEXT_INTENSITY_POLYGON_OFFSET.apply($$0);
/*      */   }
/*      */   public static RenderType textSeeThrough(ResourceLocation $$0) {
/*  647 */     return TEXT_SEE_THROUGH.apply($$0);
/*      */   }
/*      */ 
/*      */   
/*  651 */   private static final RenderType TEXT_BACKGROUND_SEE_THROUGH = create("text_background_see_through", DefaultVertexFormat.POSITION_COLOR_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  652 */       .setShaderState(RENDERTYPE_TEXT_BACKGROUND_SEE_THROUGH_SHADER)
/*  653 */       .setTextureState(NO_TEXTURE)
/*  654 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  655 */       .setLightmapState(LIGHTMAP)
/*  656 */       .setDepthTestState(NO_DEPTH_TEST)
/*  657 */       .setWriteMaskState(COLOR_WRITE)
/*  658 */       .createCompositeState(false)); private static final Function<ResourceLocation, RenderType> TEXT_INTENSITY_SEE_THROUGH;
/*      */   
/*      */   public static RenderType textBackgroundSeeThrough() {
/*  661 */     return TEXT_BACKGROUND_SEE_THROUGH;
/*      */   }
/*      */   static {
/*  664 */     TEXT_INTENSITY_SEE_THROUGH = Util.memoize($$0 -> create("text_intensity_see_through", DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder().setShaderState(RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setTransparencyState(TRANSLUCENT_TRANSPARENCY).setLightmapState(LIGHTMAP).setDepthTestState(NO_DEPTH_TEST).setWriteMaskState(COLOR_WRITE).createCompositeState(false)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderType textIntensitySeeThrough(ResourceLocation $$0) {
/*  676 */     return TEXT_INTENSITY_SEE_THROUGH.apply($$0);
/*      */   }
/*      */   
/*  679 */   private static final RenderType LIGHTNING = create("lightning", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  680 */       .setShaderState(RENDERTYPE_LIGHTNING_SHADER)
/*  681 */       .setWriteMaskState(COLOR_DEPTH_WRITE)
/*  682 */       .setTransparencyState(LIGHTNING_TRANSPARENCY)
/*  683 */       .setOutputState(WEATHER_TARGET)
/*  684 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType lightning() {
/*  687 */     return LIGHTNING;
/*      */   }
/*      */   
/*      */   private static CompositeState tripwireState() {
/*  691 */     return CompositeState.builder()
/*  692 */       .setLightmapState(LIGHTMAP)
/*  693 */       .setShaderState(RENDERTYPE_TRIPWIRE_SHADER)
/*  694 */       .setTextureState(BLOCK_SHEET_MIPPED)
/*  695 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  696 */       .setOutputState(WEATHER_TARGET)
/*  697 */       .createCompositeState(true);
/*      */   }
/*      */   
/*  700 */   private static final RenderType TRIPWIRE = create("tripwire", DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 1536, true, true, tripwireState());
/*      */   
/*      */   public static RenderType tripwire() {
/*  703 */     return TRIPWIRE;
/*      */   }
/*      */   
/*  706 */   private static final RenderType END_PORTAL = create("end_portal", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 1536, false, false, CompositeState.builder()
/*  707 */       .setShaderState(RENDERTYPE_END_PORTAL_SHADER)
/*  708 */       .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
/*  709 */         .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
/*  710 */         .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
/*  711 */         .build())
/*  712 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType endPortal() {
/*  715 */     return END_PORTAL;
/*      */   }
/*      */   
/*  718 */   private static final RenderType END_GATEWAY = create("end_gateway", DefaultVertexFormat.POSITION, VertexFormat.Mode.QUADS, 1536, false, false, CompositeState.builder()
/*  719 */       .setShaderState(RENDERTYPE_END_GATEWAY_SHADER)
/*  720 */       .setTextureState(RenderStateShard.MultiTextureStateShard.builder()
/*  721 */         .add(TheEndPortalRenderer.END_SKY_LOCATION, false, false)
/*  722 */         .add(TheEndPortalRenderer.END_PORTAL_LOCATION, false, false)
/*  723 */         .build())
/*  724 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType endGateway() {
/*  727 */     return END_GATEWAY;
/*      */   }
/*      */   
/*  730 */   public static final CompositeRenderType LINES = create("lines", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINES, 1536, CompositeState.builder()
/*  731 */       .setShaderState(RENDERTYPE_LINES_SHADER)
/*  732 */       .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
/*  733 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  734 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  735 */       .setOutputState(ITEM_ENTITY_TARGET)
/*  736 */       .setWriteMaskState(COLOR_DEPTH_WRITE)
/*  737 */       .setCullState(NO_CULL)
/*  738 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType lines() {
/*  741 */     return LINES;
/*      */   }
/*      */   
/*  744 */   public static final CompositeRenderType LINE_STRIP = create("line_strip", DefaultVertexFormat.POSITION_COLOR_NORMAL, VertexFormat.Mode.LINE_STRIP, 1536, CompositeState.builder()
/*  745 */       .setShaderState(RENDERTYPE_LINES_SHADER)
/*  746 */       .setLineState(new RenderStateShard.LineStateShard(OptionalDouble.empty()))
/*  747 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  748 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  749 */       .setOutputState(ITEM_ENTITY_TARGET)
/*  750 */       .setWriteMaskState(COLOR_DEPTH_WRITE)
/*  751 */       .setCullState(NO_CULL)
/*  752 */       .createCompositeState(false)); private static final Function<Double, CompositeRenderType> DEBUG_LINE_STRIP;
/*      */   
/*      */   public static RenderType lineStrip() {
/*  755 */     return LINE_STRIP;
/*      */   }
/*      */   static {
/*  758 */     DEBUG_LINE_STRIP = Util.memoize($$0 -> create("debug_line_strip", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.DEBUG_LINE_STRIP, 1536, CompositeState.builder().setShaderState(POSITION_COLOR_SHADER).setLineState(new RenderStateShard.LineStateShard(OptionalDouble.of($$0.doubleValue()))).setTransparencyState(NO_TRANSPARENCY).setCullState(NO_CULL).createCompositeState(false)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderType debugLineStrip(double $$0) {
/*  768 */     return DEBUG_LINE_STRIP.apply(Double.valueOf($$0));
/*      */   }
/*      */   
/*  771 */   private static final CompositeRenderType DEBUG_FILLED_BOX = create("debug_filled_box", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_STRIP, 1536, false, true, CompositeState.builder()
/*  772 */       .setShaderState(POSITION_COLOR_SHADER)
/*  773 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  774 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  775 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType debugFilledBox() {
/*  778 */     return DEBUG_FILLED_BOX;
/*      */   }
/*      */   
/*  781 */   private static final CompositeRenderType DEBUG_QUADS = create("debug_quads", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  782 */       .setShaderState(POSITION_COLOR_SHADER)
/*  783 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  784 */       .setCullState(NO_CULL)
/*  785 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType debugQuads() {
/*  788 */     return DEBUG_QUADS;
/*      */   }
/*      */   
/*  791 */   private static final CompositeRenderType DEBUG_SECTION_QUADS = create("debug_section_quads", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, false, true, CompositeState.builder()
/*  792 */       .setShaderState(POSITION_COLOR_SHADER)
/*  793 */       .setLayeringState(VIEW_OFFSET_Z_LAYERING)
/*  794 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  795 */       .setCullState(CULL)
/*  796 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType debugSectionQuads() {
/*  799 */     return DEBUG_SECTION_QUADS;
/*      */   }
/*      */   
/*  802 */   private static final CompositeRenderType GUI = create("gui", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 786432, CompositeState.builder()
/*  803 */       .setShaderState(RENDERTYPE_GUI_SHADER)
/*  804 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  805 */       .setDepthTestState(LEQUAL_DEPTH_TEST)
/*  806 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType gui() {
/*  809 */     return GUI;
/*      */   }
/*      */   
/*  812 */   private static final CompositeRenderType GUI_OVERLAY = create("gui_overlay", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  813 */       .setShaderState(RENDERTYPE_GUI_OVERLAY_SHADER)
/*  814 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  815 */       .setDepthTestState(NO_DEPTH_TEST)
/*  816 */       .setWriteMaskState(COLOR_WRITE)
/*  817 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType guiOverlay() {
/*  820 */     return GUI_OVERLAY;
/*      */   }
/*      */   
/*  823 */   private static final CompositeRenderType GUI_TEXT_HIGHLIGHT = create("gui_text_highlight", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  824 */       .setShaderState(RENDERTYPE_GUI_TEXT_HIGHLIGHT_SHADER)
/*  825 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  826 */       .setDepthTestState(NO_DEPTH_TEST)
/*  827 */       .setColorLogicState(OR_REVERSE_COLOR_LOGIC)
/*  828 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType guiTextHighlight() {
/*  831 */     return GUI_TEXT_HIGHLIGHT;
/*      */   }
/*      */   
/*  834 */   private static final CompositeRenderType GUI_GHOST_RECIPE_OVERLAY = create("gui_ghost_recipe_overlay", DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.QUADS, 1536, CompositeState.builder()
/*  835 */       .setShaderState(RENDERTYPE_GUI_GHOST_RECIPE_OVERLAY_SHADER)
/*  836 */       .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
/*  837 */       .setDepthTestState(GREATER_DEPTH_TEST)
/*  838 */       .setWriteMaskState(COLOR_WRITE)
/*  839 */       .createCompositeState(false));
/*      */   
/*      */   public static RenderType guiGhostRecipeOverlay() {
/*  842 */     return GUI_GHOST_RECIPE_OVERLAY;
/*      */   }
/*      */   
/*  845 */   private static final ImmutableList<RenderType> CHUNK_BUFFER_LAYERS = ImmutableList.of(solid(), cutoutMipped(), cutout(), translucent(), tripwire());
/*      */   
/*      */   private final VertexFormat format;
/*      */   private final VertexFormat.Mode mode;
/*      */   private final int bufferSize;
/*      */   private final boolean affectsCrumbling;
/*      */   private final boolean sortOnUpload;
/*      */   private final Optional<RenderType> asOptional;
/*      */   
/*      */   public RenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, Runnable $$6, Runnable $$7) {
/*  855 */     super($$0, $$6, $$7);
/*  856 */     this.format = $$1;
/*  857 */     this.mode = $$2;
/*  858 */     this.bufferSize = $$3;
/*  859 */     this.affectsCrumbling = $$4;
/*  860 */     this.sortOnUpload = $$5;
/*  861 */     this.asOptional = Optional.of(this);
/*      */   }
/*      */   
/*      */   static CompositeRenderType create(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, CompositeState $$4) {
/*  865 */     return create($$0, $$1, $$2, $$3, false, false, $$4);
/*      */   }
/*      */   
/*      */   private static CompositeRenderType create(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, CompositeState $$6) {
/*  869 */     return new CompositeRenderType($$0, $$1, $$2, $$3, $$4, $$5, $$6);
/*      */   }
/*      */   
/*      */   public void end(BufferBuilder $$0, VertexSorting $$1) {
/*  873 */     if (!$$0.building()) {
/*      */       return;
/*      */     }
/*  876 */     if (this.sortOnUpload) {
/*  877 */       $$0.setQuadSorting($$1);
/*      */     }
/*  879 */     BufferBuilder.RenderedBuffer $$2 = $$0.end();
/*  880 */     setupRenderState();
/*  881 */     BufferUploader.drawWithShader($$2);
/*  882 */     clearRenderState();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  887 */     return this.name;
/*      */   }
/*      */   
/*      */   public static List<RenderType> chunkBufferLayers() {
/*  891 */     return (List<RenderType>)CHUNK_BUFFER_LAYERS;
/*      */   }
/*      */   
/*      */   public int bufferSize() {
/*  895 */     return this.bufferSize;
/*      */   }
/*      */   
/*      */   public VertexFormat format() {
/*  899 */     return this.format;
/*      */   }
/*      */   
/*      */   public VertexFormat.Mode mode() {
/*  903 */     return this.mode;
/*      */   }
/*      */   
/*      */   public Optional<RenderType> outline() {
/*  907 */     return Optional.empty();
/*      */   }
/*      */   
/*      */   public boolean isOutline() {
/*  911 */     return false;
/*      */   }
/*      */   
/*      */   public boolean affectsCrumbling() {
/*  915 */     return this.affectsCrumbling;
/*      */   }
/*      */   
/*      */   public boolean canConsolidateConsecutiveGeometry() {
/*  919 */     return !this.mode.connectedPrimitives;
/*      */   }
/*      */   
/*      */   public Optional<RenderType> asOptional() {
/*  923 */     return this.asOptional;
/*      */   }
/*      */   
/*      */   protected static final class CompositeState
/*      */   {
/*      */     final RenderStateShard.EmptyTextureStateShard textureState;
/*      */     private final RenderStateShard.ShaderStateShard shaderState;
/*      */     private final RenderStateShard.TransparencyStateShard transparencyState;
/*      */     private final RenderStateShard.DepthTestStateShard depthTestState;
/*      */     final RenderStateShard.CullStateShard cullState;
/*      */     private final RenderStateShard.LightmapStateShard lightmapState;
/*      */     private final RenderStateShard.OverlayStateShard overlayState;
/*      */     private final RenderStateShard.LayeringStateShard layeringState;
/*      */     private final RenderStateShard.OutputStateShard outputState;
/*      */     private final RenderStateShard.TexturingStateShard texturingState;
/*      */     private final RenderStateShard.WriteMaskStateShard writeMaskState;
/*      */     private final RenderStateShard.LineStateShard lineState;
/*      */     private final RenderStateShard.ColorLogicStateShard colorLogicState;
/*      */     final RenderType.OutlineProperty outlineProperty;
/*      */     final ImmutableList<RenderStateShard> states;
/*      */     
/*      */     CompositeState(RenderStateShard.EmptyTextureStateShard $$0, RenderStateShard.ShaderStateShard $$1, RenderStateShard.TransparencyStateShard $$2, RenderStateShard.DepthTestStateShard $$3, RenderStateShard.CullStateShard $$4, RenderStateShard.LightmapStateShard $$5, RenderStateShard.OverlayStateShard $$6, RenderStateShard.LayeringStateShard $$7, RenderStateShard.OutputStateShard $$8, RenderStateShard.TexturingStateShard $$9, RenderStateShard.WriteMaskStateShard $$10, RenderStateShard.LineStateShard $$11, RenderStateShard.ColorLogicStateShard $$12, RenderType.OutlineProperty $$13) {
/*  945 */       this.textureState = $$0;
/*  946 */       this.shaderState = $$1;
/*  947 */       this.transparencyState = $$2;
/*  948 */       this.depthTestState = $$3;
/*  949 */       this.cullState = $$4;
/*  950 */       this.lightmapState = $$5;
/*  951 */       this.overlayState = $$6;
/*  952 */       this.layeringState = $$7;
/*  953 */       this.outputState = $$8;
/*  954 */       this.texturingState = $$9;
/*  955 */       this.writeMaskState = $$10;
/*  956 */       this.lineState = $$11;
/*  957 */       this.colorLogicState = $$12;
/*  958 */       this.outlineProperty = $$13;
/*      */       
/*  960 */       this.states = ImmutableList.of(this.textureState, this.shaderState, this.transparencyState, this.depthTestState, this.cullState, this.lightmapState, this.overlayState, this.layeringState, this.outputState, this.texturingState, this.writeMaskState, this.colorLogicState, (Object[])new RenderStateShard[] { this.lineState });
/*      */     }
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
/*      */     public String toString() {
/*  979 */       return "CompositeState[" + this.states + ", outlineProperty=" + this.outlineProperty + "]";
/*      */     }
/*      */     
/*      */     public static CompositeStateBuilder builder() {
/*  983 */       return new CompositeStateBuilder();
/*      */     }
/*      */     
/*      */     public static class CompositeStateBuilder {
/*  987 */       private RenderStateShard.EmptyTextureStateShard textureState = RenderStateShard.NO_TEXTURE;
/*  988 */       private RenderStateShard.ShaderStateShard shaderState = RenderStateShard.NO_SHADER;
/*  989 */       private RenderStateShard.TransparencyStateShard transparencyState = RenderStateShard.NO_TRANSPARENCY;
/*  990 */       private RenderStateShard.DepthTestStateShard depthTestState = RenderStateShard.LEQUAL_DEPTH_TEST;
/*  991 */       private RenderStateShard.CullStateShard cullState = RenderStateShard.CULL;
/*  992 */       private RenderStateShard.LightmapStateShard lightmapState = RenderStateShard.NO_LIGHTMAP;
/*  993 */       private RenderStateShard.OverlayStateShard overlayState = RenderStateShard.NO_OVERLAY;
/*  994 */       private RenderStateShard.LayeringStateShard layeringState = RenderStateShard.NO_LAYERING;
/*  995 */       private RenderStateShard.OutputStateShard outputState = RenderStateShard.MAIN_TARGET;
/*  996 */       private RenderStateShard.TexturingStateShard texturingState = RenderStateShard.DEFAULT_TEXTURING;
/*  997 */       private RenderStateShard.WriteMaskStateShard writeMaskState = RenderStateShard.COLOR_DEPTH_WRITE;
/*  998 */       private RenderStateShard.LineStateShard lineState = RenderStateShard.DEFAULT_LINE;
/*  999 */       private RenderStateShard.ColorLogicStateShard colorLogicState = RenderStateShard.NO_COLOR_LOGIC;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public CompositeStateBuilder setTextureState(RenderStateShard.EmptyTextureStateShard $$0) {
/* 1005 */         this.textureState = $$0;
/* 1006 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setShaderState(RenderStateShard.ShaderStateShard $$0) {
/* 1010 */         this.shaderState = $$0;
/* 1011 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setTransparencyState(RenderStateShard.TransparencyStateShard $$0) {
/* 1015 */         this.transparencyState = $$0;
/* 1016 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setDepthTestState(RenderStateShard.DepthTestStateShard $$0) {
/* 1020 */         this.depthTestState = $$0;
/* 1021 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setCullState(RenderStateShard.CullStateShard $$0) {
/* 1025 */         this.cullState = $$0;
/* 1026 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setLightmapState(RenderStateShard.LightmapStateShard $$0) {
/* 1030 */         this.lightmapState = $$0;
/* 1031 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setOverlayState(RenderStateShard.OverlayStateShard $$0) {
/* 1035 */         this.overlayState = $$0;
/* 1036 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setLayeringState(RenderStateShard.LayeringStateShard $$0) {
/* 1040 */         this.layeringState = $$0;
/* 1041 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setOutputState(RenderStateShard.OutputStateShard $$0) {
/* 1045 */         this.outputState = $$0;
/* 1046 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setTexturingState(RenderStateShard.TexturingStateShard $$0) {
/* 1050 */         this.texturingState = $$0;
/* 1051 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setWriteMaskState(RenderStateShard.WriteMaskStateShard $$0) {
/* 1055 */         this.writeMaskState = $$0;
/* 1056 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setLineState(RenderStateShard.LineStateShard $$0) {
/* 1060 */         this.lineState = $$0;
/* 1061 */         return this;
/*      */       }
/*      */       
/*      */       public CompositeStateBuilder setColorLogicState(RenderStateShard.ColorLogicStateShard $$0) {
/* 1065 */         this.colorLogicState = $$0;
/* 1066 */         return this;
/*      */       }
/*      */       
/*      */       public RenderType.CompositeState createCompositeState(boolean $$0) {
/* 1070 */         return createCompositeState($$0 ? RenderType.OutlineProperty.AFFECTS_OUTLINE : RenderType.OutlineProperty.NONE);
/*      */       }
/*      */       
/*      */       public RenderType.CompositeState createCompositeState(RenderType.OutlineProperty $$0) {
/* 1074 */         return new RenderType.CompositeState(this.textureState, this.shaderState, this.transparencyState, this.depthTestState, this.cullState, this.lightmapState, this.overlayState, this.layeringState, this.outputState, this.texturingState, this.writeMaskState, this.lineState, this.colorLogicState, $$0); } } } public static class CompositeStateBuilder { private RenderStateShard.EmptyTextureStateShard textureState = RenderStateShard.NO_TEXTURE; private RenderStateShard.ShaderStateShard shaderState = RenderStateShard.NO_SHADER; private RenderStateShard.TransparencyStateShard transparencyState = RenderStateShard.NO_TRANSPARENCY; private RenderStateShard.DepthTestStateShard depthTestState = RenderStateShard.LEQUAL_DEPTH_TEST; private RenderStateShard.CullStateShard cullState = RenderStateShard.CULL; private RenderStateShard.LightmapStateShard lightmapState = RenderStateShard.NO_LIGHTMAP; private RenderStateShard.OverlayStateShard overlayState = RenderStateShard.NO_OVERLAY; private RenderStateShard.LayeringStateShard layeringState = RenderStateShard.NO_LAYERING; private RenderStateShard.OutputStateShard outputState = RenderStateShard.MAIN_TARGET; private RenderStateShard.TexturingStateShard texturingState = RenderStateShard.DEFAULT_TEXTURING; private RenderStateShard.WriteMaskStateShard writeMaskState = RenderStateShard.COLOR_DEPTH_WRITE; private RenderStateShard.LineStateShard lineState = RenderStateShard.DEFAULT_LINE; private RenderStateShard.ColorLogicStateShard colorLogicState = RenderStateShard.NO_COLOR_LOGIC; public RenderType.CompositeState createCompositeState(RenderType.OutlineProperty $$0) { return new RenderType.CompositeState(this.textureState, this.shaderState, this.transparencyState, this.depthTestState, this.cullState, this.lightmapState, this.overlayState, this.layeringState, this.outputState, this.texturingState, this.writeMaskState, this.lineState, this.colorLogicState, $$0); }
/*      */     public CompositeStateBuilder setTextureState(RenderStateShard.EmptyTextureStateShard $$0) { this.textureState = $$0; return this; }
/*      */     public CompositeStateBuilder setShaderState(RenderStateShard.ShaderStateShard $$0) { this.shaderState = $$0; return this; }
/*      */     public CompositeStateBuilder setTransparencyState(RenderStateShard.TransparencyStateShard $$0) { this.transparencyState = $$0; return this; } public CompositeStateBuilder setDepthTestState(RenderStateShard.DepthTestStateShard $$0) { this.depthTestState = $$0; return this; } public CompositeStateBuilder setCullState(RenderStateShard.CullStateShard $$0) { this.cullState = $$0; return this; } public CompositeStateBuilder setLightmapState(RenderStateShard.LightmapStateShard $$0) { this.lightmapState = $$0; return this; } public CompositeStateBuilder setOverlayState(RenderStateShard.OverlayStateShard $$0) { this.overlayState = $$0; return this; } public CompositeStateBuilder setLayeringState(RenderStateShard.LayeringStateShard $$0) { this.layeringState = $$0; return this; } public CompositeStateBuilder setOutputState(RenderStateShard.OutputStateShard $$0) { this.outputState = $$0; return this; } public CompositeStateBuilder setTexturingState(RenderStateShard.TexturingStateShard $$0) { this.texturingState = $$0; return this; } public CompositeStateBuilder setWriteMaskState(RenderStateShard.WriteMaskStateShard $$0) { this.writeMaskState = $$0; return this; } public CompositeStateBuilder setLineState(RenderStateShard.LineStateShard $$0) { this.lineState = $$0; return this; } public CompositeStateBuilder setColorLogicState(RenderStateShard.ColorLogicStateShard $$0) { this.colorLogicState = $$0; return this; } public RenderType.CompositeState createCompositeState(boolean $$0) { return createCompositeState($$0 ? RenderType.OutlineProperty.AFFECTS_OUTLINE : RenderType.OutlineProperty.NONE); } }
/*      */    private static final class CompositeRenderType extends RenderType
/*      */   {
/* 1080 */     static final BiFunction<ResourceLocation, RenderStateShard.CullStateShard, RenderType> OUTLINE; private final RenderType.CompositeState state; private final Optional<RenderType> outline; private final boolean isOutline; static { OUTLINE = Util.memoize(($$0, $$1) -> RenderType.create("outline", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 1536, RenderType.CompositeState.builder().setShaderState(RENDERTYPE_OUTLINE_SHADER).setTextureState(new RenderStateShard.TextureStateShard($$0, false, false)).setCullState($$1).setDepthTestState(NO_DEPTH_TEST).setOutputState(OUTLINE_TARGET).createCompositeState(RenderType.OutlineProperty.IS_OUTLINE))); }
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
/*      */     CompositeRenderType(String $$0, VertexFormat $$1, VertexFormat.Mode $$2, int $$3, boolean $$4, boolean $$5, RenderType.CompositeState $$6) {
/* 1095 */       super($$0, $$1, $$2, $$3, $$4, $$5, () -> $$0.states.forEach(RenderStateShard::setupRenderState), () -> $$0.states.forEach(RenderStateShard::clearRenderState));
/* 1096 */       this.state = $$6;
/* 1097 */       this.outline = ($$6.outlineProperty == RenderType.OutlineProperty.AFFECTS_OUTLINE) ? $$6.textureState.cutoutTexture().<RenderType>map($$1 -> (RenderType)OUTLINE.apply($$1, $$0.cullState)) : Optional.<RenderType>empty();
/* 1098 */       this.isOutline = ($$6.outlineProperty == RenderType.OutlineProperty.IS_OUTLINE);
/*      */     }
/*      */ 
/*      */     
/*      */     public Optional<RenderType> outline() {
/* 1103 */       return this.outline;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isOutline() {
/* 1108 */       return this.isOutline;
/*      */     }
/*      */     
/*      */     protected final RenderType.CompositeState state() {
/* 1112 */       return this.state;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1117 */       return "RenderType[" + this.name + ":" + this.state + "]";
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\RenderType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */