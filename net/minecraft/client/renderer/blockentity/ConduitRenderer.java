/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.client.resources.model.Material;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.ConduitBlockEntity;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class ConduitRenderer implements BlockEntityRenderer<ConduitBlockEntity> {
/*  25 */   public static final Material SHELL_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/base"));
/*  26 */   public static final Material ACTIVE_SHELL_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/cage"));
/*  27 */   public static final Material WIND_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind"));
/*  28 */   public static final Material VERTICAL_WIND_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/wind_vertical"));
/*  29 */   public static final Material OPEN_EYE_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/open_eye"));
/*  30 */   public static final Material CLOSED_EYE_TEXTURE = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("entity/conduit/closed_eye"));
/*     */   
/*     */   private final ModelPart eye;
/*     */   private final ModelPart wind;
/*     */   private final ModelPart shell;
/*     */   private final ModelPart cage;
/*     */   private final BlockEntityRenderDispatcher renderer;
/*     */   
/*     */   public ConduitRenderer(BlockEntityRendererProvider.Context $$0) {
/*  39 */     this.renderer = $$0.getBlockEntityRenderDispatcher();
/*  40 */     this.eye = $$0.bakeLayer(ModelLayers.CONDUIT_EYE);
/*  41 */     this.wind = $$0.bakeLayer(ModelLayers.CONDUIT_WIND);
/*  42 */     this.shell = $$0.bakeLayer(ModelLayers.CONDUIT_SHELL);
/*  43 */     this.cage = $$0.bakeLayer(ModelLayers.CONDUIT_CAGE);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createEyeLayer() {
/*  47 */     MeshDefinition $$0 = new MeshDefinition();
/*  48 */     PartDefinition $$1 = $$0.getRoot();
/*  49 */     $$1.addOrReplaceChild("eye", 
/*  50 */         CubeListBuilder.create()
/*  51 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, 0.0F, 8.0F, 8.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.ZERO);
/*     */ 
/*     */     
/*  54 */     return LayerDefinition.create($$0, 16, 16);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createWindLayer() {
/*  58 */     MeshDefinition $$0 = new MeshDefinition();
/*  59 */     PartDefinition $$1 = $$0.getRoot();
/*  60 */     $$1.addOrReplaceChild("wind", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  65 */     return LayerDefinition.create($$0, 64, 32);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createShellLayer() {
/*  69 */     MeshDefinition $$0 = new MeshDefinition();
/*  70 */     PartDefinition $$1 = $$0.getRoot();
/*  71 */     $$1.addOrReplaceChild("shell", 
/*  72 */         CubeListBuilder.create()
/*  73 */         .texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  76 */     return LayerDefinition.create($$0, 32, 16);
/*     */   }
/*     */   
/*     */   public static LayerDefinition createCageLayer() {
/*  80 */     MeshDefinition $$0 = new MeshDefinition();
/*  81 */     PartDefinition $$1 = $$0.getRoot();
/*  82 */     $$1.addOrReplaceChild("shell", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*     */ 
/*     */     
/*  87 */     return LayerDefinition.create($$0, 32, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(ConduitBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  92 */     float $$6 = $$0.tickCount + $$1;
/*     */     
/*  94 */     if (!$$0.isActive()) {
/*  95 */       float $$7 = $$0.getActiveRotation(0.0F);
/*     */       
/*  97 */       VertexConsumer $$8 = SHELL_TEXTURE.buffer($$3, RenderType::entitySolid);
/*  98 */       $$2.pushPose();
/*  99 */       $$2.translate(0.5F, 0.5F, 0.5F);
/* 100 */       $$2.mulPose((new Quaternionf()).rotationY($$7 * 0.017453292F));
/* 101 */       this.shell.render($$2, $$8, $$4, $$5);
/* 102 */       $$2.popPose();
/*     */       
/*     */       return;
/*     */     } 
/* 106 */     float $$9 = $$0.getActiveRotation($$1) * 57.295776F;
/* 107 */     float $$10 = Mth.sin($$6 * 0.1F) / 2.0F + 0.5F;
/* 108 */     $$10 = $$10 * $$10 + $$10;
/*     */     
/* 110 */     $$2.pushPose();
/* 111 */     $$2.translate(0.5F, 0.3F + $$10 * 0.2F, 0.5F);
/* 112 */     Vector3f $$11 = (new Vector3f(0.5F, 1.0F, 0.5F)).normalize();
/* 113 */     $$2.mulPose((new Quaternionf()).rotationAxis($$9 * 0.017453292F, (Vector3fc)$$11));
/* 114 */     this.cage.render($$2, ACTIVE_SHELL_TEXTURE.buffer($$3, RenderType::entityCutoutNoCull), $$4, $$5);
/* 115 */     $$2.popPose();
/*     */     
/* 117 */     int $$12 = $$0.tickCount / 66 % 3;
/*     */     
/* 119 */     $$2.pushPose();
/* 120 */     $$2.translate(0.5F, 0.5F, 0.5F);
/*     */     
/* 122 */     if ($$12 == 1) {
/* 123 */       $$2.mulPose((new Quaternionf()).rotationX(1.5707964F));
/* 124 */     } else if ($$12 == 2) {
/* 125 */       $$2.mulPose((new Quaternionf()).rotationZ(1.5707964F));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 130 */     VertexConsumer $$13 = (($$12 == 1) ? VERTICAL_WIND_TEXTURE : WIND_TEXTURE).buffer($$3, RenderType::entityCutoutNoCull);
/* 131 */     this.wind.render($$2, $$13, $$4, $$5);
/* 132 */     $$2.popPose();
/* 133 */     $$2.pushPose();
/* 134 */     $$2.translate(0.5F, 0.5F, 0.5F);
/* 135 */     $$2.scale(0.875F, 0.875F, 0.875F);
/* 136 */     $$2.mulPose((new Quaternionf()).rotationXYZ(3.1415927F, 0.0F, 3.1415927F));
/* 137 */     this.wind.render($$2, $$13, $$4, $$5);
/* 138 */     $$2.popPose();
/* 139 */     Camera $$14 = this.renderer.camera;
/*     */     
/* 141 */     $$2.pushPose();
/* 142 */     $$2.translate(0.5F, 0.3F + $$10 * 0.2F, 0.5F);
/* 143 */     $$2.scale(0.5F, 0.5F, 0.5F);
/* 144 */     float $$15 = -$$14.getYRot();
/* 145 */     $$2.mulPose((new Quaternionf()).rotationYXZ($$15 * 0.017453292F, $$14.getXRot() * 0.017453292F, 3.1415927F));
/* 146 */     float $$16 = 1.3333334F;
/* 147 */     $$2.scale(1.3333334F, 1.3333334F, 1.3333334F);
/* 148 */     this.eye.render($$2, ($$0.isHunting() ? OPEN_EYE_TEXTURE : CLOSED_EYE_TEXTURE).buffer($$3, RenderType::entityCutoutNoCull), $$4, $$5);
/* 149 */     $$2.popPose();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\ConduitRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */