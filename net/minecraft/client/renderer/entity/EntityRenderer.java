/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.renderer.LightTexture;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public abstract class EntityRenderer<T extends Entity>
/*     */ {
/*     */   protected static final float NAMETAG_SCALE = 0.025F;
/*     */   protected final EntityRenderDispatcher entityRenderDispatcher;
/*     */   private final Font font;
/*     */   protected float shadowRadius;
/*  26 */   protected float shadowStrength = 1.0F;
/*     */   
/*     */   protected EntityRenderer(EntityRendererProvider.Context $$0) {
/*  29 */     this.entityRenderDispatcher = $$0.getEntityRenderDispatcher();
/*  30 */     this.font = $$0.getFont();
/*     */   }
/*     */   
/*     */   public final int getPackedLightCoords(T $$0, float $$1) {
/*  34 */     BlockPos $$2 = BlockPos.containing((Position)$$0.getLightProbePosition($$1));
/*  35 */     return LightTexture.pack(getBlockLightLevel($$0, $$2), getSkyLightLevel($$0, $$2));
/*     */   }
/*     */   
/*     */   protected int getSkyLightLevel(T $$0, BlockPos $$1) {
/*  39 */     return $$0.level().getBrightness(LightLayer.SKY, $$1);
/*     */   }
/*     */   
/*     */   protected int getBlockLightLevel(T $$0, BlockPos $$1) {
/*  43 */     if ($$0.isOnFire()) {
/*  44 */       return 15;
/*     */     }
/*  46 */     return $$0.level().getBrightness(LightLayer.BLOCK, $$1);
/*     */   }
/*     */   
/*     */   public boolean shouldRender(T $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/*  50 */     if (!$$0.shouldRender($$2, $$3, $$4)) {
/*  51 */       return false;
/*     */     }
/*  53 */     if (((Entity)$$0).noCulling) {
/*  54 */       return true;
/*     */     }
/*  56 */     AABB $$5 = $$0.getBoundingBoxForCulling().inflate(0.5D);
/*  57 */     if ($$5.hasNaN() || $$5.getSize() == 0.0D) {
/*  58 */       $$5 = new AABB($$0.getX() - 2.0D, $$0.getY() - 2.0D, $$0.getZ() - 2.0D, $$0.getX() + 2.0D, $$0.getY() + 2.0D, $$0.getZ() + 2.0D);
/*     */     }
/*  60 */     return $$1.isVisible($$5);
/*     */   }
/*     */   
/*     */   public Vec3 getRenderOffset(T $$0, float $$1) {
/*  64 */     return Vec3.ZERO;
/*     */   }
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  68 */     if (!shouldShowName($$0)) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     renderNameTag($$0, $$0.getDisplayName(), $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   protected boolean shouldShowName(T $$0) {
/*  76 */     return ($$0.shouldShowName() || ($$0.hasCustomName() && $$0 == this.entityRenderDispatcher.crosshairPickEntity));
/*     */   }
/*     */   
/*     */   public abstract ResourceLocation getTextureLocation(T paramT);
/*     */   
/*     */   public Font getFont() {
/*  82 */     return this.font;
/*     */   }
/*     */   
/*     */   protected void renderNameTag(T $$0, Component $$1, PoseStack $$2, MultiBufferSource $$3, int $$4) {
/*  86 */     double $$5 = this.entityRenderDispatcher.distanceToSqr((Entity)$$0);
/*     */     
/*  88 */     if ($$5 > 4096.0D) {
/*     */       return;
/*     */     }
/*     */     
/*  92 */     boolean $$6 = !$$0.isDiscrete();
/*     */     
/*  94 */     float $$7 = $$0.getNameTagOffsetY();
/*  95 */     int $$8 = "deadmau5".equals($$1.getString()) ? -10 : 0;
/*     */     
/*  97 */     $$2.pushPose();
/*     */     
/*  99 */     $$2.translate(0.0F, $$7, 0.0F);
/* 100 */     $$2.mulPose(this.entityRenderDispatcher.cameraOrientation());
/*     */     
/* 102 */     $$2.scale(-0.025F, -0.025F, 0.025F);
/*     */     
/* 104 */     Matrix4f $$9 = $$2.last().pose();
/*     */     
/* 106 */     float $$10 = (Minecraft.getInstance()).options.getBackgroundOpacity(0.25F);
/* 107 */     int $$11 = (int)($$10 * 255.0F) << 24;
/*     */     
/* 109 */     Font $$12 = getFont();
/*     */     
/* 111 */     float $$13 = (-$$12.width((FormattedText)$$1) / 2);
/*     */     
/* 113 */     $$12.drawInBatch($$1, $$13, $$8, 553648127, false, $$9, $$3, $$6 ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, $$11, $$4);
/*     */     
/* 115 */     if ($$6) {
/* 116 */       $$12.drawInBatch($$1, $$13, $$8, -1, false, $$9, $$3, Font.DisplayMode.NORMAL, 0, $$4);
/*     */     }
/*     */     
/* 119 */     $$2.popPose();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EntityRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */