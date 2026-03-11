/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EndCrystal;
/*     */ import org.joml.Quaternionf;
/*     */ 
/*     */ public class EndCrystalRenderer extends EntityRenderer<EndCrystal> {
/*  25 */   private static final ResourceLocation END_CRYSTAL_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal.png");
/*  26 */   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(END_CRYSTAL_LOCATION);
/*  27 */   private static final float SIN_45 = (float)Math.sin(0.7853981633974483D);
/*     */   
/*     */   private static final String GLASS = "glass";
/*     */   
/*     */   private static final String BASE = "base";
/*     */   private final ModelPart cube;
/*     */   private final ModelPart glass;
/*     */   private final ModelPart base;
/*     */   
/*     */   public EndCrystalRenderer(EntityRendererProvider.Context $$0) {
/*  37 */     super($$0);
/*  38 */     this.shadowRadius = 0.5F;
/*     */     
/*  40 */     ModelPart $$1 = $$0.bakeLayer(ModelLayers.END_CRYSTAL);
/*  41 */     this.glass = $$1.getChild("glass");
/*  42 */     this.cube = $$1.getChild("cube");
/*  43 */     this.base = $$1.getChild("base");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  47 */     MeshDefinition $$0 = new MeshDefinition();
/*  48 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  50 */     $$1.addOrReplaceChild("glass", 
/*  51 */         CubeListBuilder.create()
/*  52 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  55 */     $$1.addOrReplaceChild("cube", 
/*  56 */         CubeListBuilder.create()
/*  57 */         .texOffs(32, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  60 */     $$1.addOrReplaceChild("base", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(0, 16).addBox(-6.0F, 0.0F, -6.0F, 12.0F, 4.0F, 12.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */     
/*  66 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(EndCrystal $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  71 */     $$3.pushPose();
/*     */     
/*  73 */     float $$6 = getY($$0, $$2);
/*  74 */     float $$7 = ($$0.time + $$2) * 3.0F;
/*     */     
/*  76 */     VertexConsumer $$8 = $$4.getBuffer(RENDER_TYPE);
/*     */     
/*  78 */     $$3.pushPose();
/*  79 */     $$3.scale(2.0F, 2.0F, 2.0F);
/*  80 */     $$3.translate(0.0F, -0.5F, 0.0F);
/*  81 */     int $$9 = OverlayTexture.NO_OVERLAY;
/*     */     
/*  83 */     if ($$0.showsBottom()) {
/*  84 */       this.base.render($$3, $$8, $$5, $$9);
/*     */     }
/*     */     
/*  87 */     $$3.mulPose(Axis.YP.rotationDegrees($$7));
/*  88 */     $$3.translate(0.0F, 1.5F + $$6 / 2.0F, 0.0F);
/*  89 */     $$3.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, SIN_45, 0.0F, SIN_45));
/*     */     
/*  91 */     this.glass.render($$3, $$8, $$5, $$9);
/*  92 */     float $$10 = 0.875F;
/*  93 */     $$3.scale(0.875F, 0.875F, 0.875F);
/*  94 */     $$3.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, SIN_45, 0.0F, SIN_45));
/*  95 */     $$3.mulPose(Axis.YP.rotationDegrees($$7));
/*     */     
/*  97 */     this.glass.render($$3, $$8, $$5, $$9);
/*  98 */     $$3.scale(0.875F, 0.875F, 0.875F);
/*  99 */     $$3.mulPose((new Quaternionf()).setAngleAxis(1.0471976F, SIN_45, 0.0F, SIN_45));
/* 100 */     $$3.mulPose(Axis.YP.rotationDegrees($$7));
/*     */     
/* 102 */     this.cube.render($$3, $$8, $$5, $$9);
/* 103 */     $$3.popPose();
/* 104 */     $$3.popPose();
/*     */     
/* 106 */     BlockPos $$11 = $$0.getBeamTarget();
/* 107 */     if ($$11 != null) {
/* 108 */       float $$12 = $$11.getX() + 0.5F;
/* 109 */       float $$13 = $$11.getY() + 0.5F;
/* 110 */       float $$14 = $$11.getZ() + 0.5F;
/* 111 */       float $$15 = (float)($$12 - $$0.getX());
/* 112 */       float $$16 = (float)($$13 - $$0.getY());
/* 113 */       float $$17 = (float)($$14 - $$0.getZ());
/* 114 */       $$3.translate($$15, $$16, $$17);
/* 115 */       EnderDragonRenderer.renderCrystalBeams(-$$15, -$$16 + $$6, -$$17, $$2, $$0.time, $$3, $$4, $$5);
/*     */     } 
/*     */     
/* 118 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public static float getY(EndCrystal $$0, float $$1) {
/* 122 */     float $$2 = $$0.time + $$1;
/* 123 */     float $$3 = Mth.sin($$2 * 0.2F) / 2.0F + 0.5F;
/* 124 */     $$3 = ($$3 * $$3 + $$3) * 0.4F;
/* 125 */     return $$3 - 1.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(EndCrystal $$0) {
/* 130 */     return END_CRYSTAL_LOCATION;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(EndCrystal $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/* 135 */     return (super.shouldRender($$0, $$1, $$2, $$3, $$4) || $$0.getBeamTarget() != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EndCrystalRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */