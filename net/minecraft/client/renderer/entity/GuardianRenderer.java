/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.model.GuardianModel;
/*     */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class GuardianRenderer extends MobRenderer<Guardian, GuardianModel> {
/*  24 */   private static final ResourceLocation GUARDIAN_LOCATION = new ResourceLocation("textures/entity/guardian.png");
/*  25 */   private static final ResourceLocation GUARDIAN_BEAM_LOCATION = new ResourceLocation("textures/entity/guardian_beam.png");
/*     */   
/*  27 */   private static final RenderType BEAM_RENDER_TYPE = RenderType.entityCutoutNoCull(GUARDIAN_BEAM_LOCATION);
/*     */   
/*     */   public GuardianRenderer(EntityRendererProvider.Context $$0) {
/*  30 */     this($$0, 0.5F, ModelLayers.GUARDIAN);
/*     */   }
/*     */   
/*     */   protected GuardianRenderer(EntityRendererProvider.Context $$0, float $$1, ModelLayerLocation $$2) {
/*  34 */     super($$0, new GuardianModel($$0.bakeLayer($$2)), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(Guardian $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/*  39 */     if (super.shouldRender($$0, $$1, $$2, $$3, $$4)) {
/*  40 */       return true;
/*     */     }
/*     */     
/*  43 */     if ($$0.hasActiveAttackTarget()) {
/*  44 */       LivingEntity $$5 = $$0.getActiveAttackTarget();
/*  45 */       if ($$5 != null) {
/*     */         
/*  47 */         Vec3 $$6 = getPosition($$5, $$5.getBbHeight() * 0.5D, 1.0F);
/*  48 */         Vec3 $$7 = getPosition((LivingEntity)$$0, $$0.getEyeHeight(), 1.0F);
/*     */         
/*  50 */         return $$1.isVisible(new AABB($$7.x, $$7.y, $$7.z, $$6.x, $$6.y, $$6.z));
/*     */       } 
/*     */     } 
/*  53 */     return false;
/*     */   }
/*     */   
/*     */   private Vec3 getPosition(LivingEntity $$0, double $$1, float $$2) {
/*  57 */     double $$3 = Mth.lerp($$2, $$0.xOld, $$0.getX());
/*  58 */     double $$4 = Mth.lerp($$2, $$0.yOld, $$0.getY()) + $$1;
/*  59 */     double $$5 = Mth.lerp($$2, $$0.zOld, $$0.getZ());
/*  60 */     return new Vec3($$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Guardian $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  65 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  67 */     LivingEntity $$6 = $$0.getActiveAttackTarget();
/*  68 */     if ($$6 != null) {
/*  69 */       float $$7 = $$0.getAttackAnimationScale($$2);
/*     */       
/*  71 */       float $$8 = $$0.getClientSideAttackTime() + $$2;
/*  72 */       float $$9 = $$8 * 0.5F % 1.0F;
/*  73 */       float $$10 = $$0.getEyeHeight();
/*     */       
/*  75 */       $$3.pushPose();
/*  76 */       $$3.translate(0.0F, $$10, 0.0F);
/*     */       
/*  78 */       Vec3 $$11 = getPosition($$6, $$6.getBbHeight() * 0.5D, $$2);
/*  79 */       Vec3 $$12 = getPosition((LivingEntity)$$0, $$10, $$2);
/*     */       
/*  81 */       Vec3 $$13 = $$11.subtract($$12);
/*  82 */       float $$14 = (float)($$13.length() + 1.0D);
/*  83 */       $$13 = $$13.normalize();
/*     */ 
/*     */       
/*  86 */       float $$15 = (float)Math.acos($$13.y);
/*  87 */       float $$16 = (float)Math.atan2($$13.z, $$13.x);
/*  88 */       $$3.mulPose(Axis.YP.rotationDegrees((1.5707964F - $$16) * 57.295776F));
/*  89 */       $$3.mulPose(Axis.XP.rotationDegrees($$15 * 57.295776F));
/*  90 */       int $$17 = 1;
/*     */       
/*  92 */       float $$18 = $$8 * 0.05F * -1.5F;
/*     */       
/*  94 */       float $$19 = $$7 * $$7;
/*  95 */       int $$20 = 64 + (int)($$19 * 191.0F);
/*  96 */       int $$21 = 32 + (int)($$19 * 191.0F);
/*  97 */       int $$22 = 128 - (int)($$19 * 64.0F);
/*     */       
/*  99 */       float $$23 = 0.2F;
/* 100 */       float $$24 = 0.282F;
/*     */       
/* 102 */       float $$25 = Mth.cos($$18 + 2.3561945F) * 0.282F;
/* 103 */       float $$26 = Mth.sin($$18 + 2.3561945F) * 0.282F;
/* 104 */       float $$27 = Mth.cos($$18 + 0.7853982F) * 0.282F;
/* 105 */       float $$28 = Mth.sin($$18 + 0.7853982F) * 0.282F;
/* 106 */       float $$29 = Mth.cos($$18 + 3.926991F) * 0.282F;
/* 107 */       float $$30 = Mth.sin($$18 + 3.926991F) * 0.282F;
/* 108 */       float $$31 = Mth.cos($$18 + 5.4977875F) * 0.282F;
/* 109 */       float $$32 = Mth.sin($$18 + 5.4977875F) * 0.282F;
/*     */       
/* 111 */       float $$33 = Mth.cos($$18 + 3.1415927F) * 0.2F;
/* 112 */       float $$34 = Mth.sin($$18 + 3.1415927F) * 0.2F;
/* 113 */       float $$35 = Mth.cos($$18 + 0.0F) * 0.2F;
/* 114 */       float $$36 = Mth.sin($$18 + 0.0F) * 0.2F;
/*     */       
/* 116 */       float $$37 = Mth.cos($$18 + 1.5707964F) * 0.2F;
/* 117 */       float $$38 = Mth.sin($$18 + 1.5707964F) * 0.2F;
/* 118 */       float $$39 = Mth.cos($$18 + 4.712389F) * 0.2F;
/* 119 */       float $$40 = Mth.sin($$18 + 4.712389F) * 0.2F;
/*     */       
/* 121 */       float $$41 = $$14;
/*     */       
/* 123 */       float $$42 = 0.0F;
/* 124 */       float $$43 = 0.4999F;
/* 125 */       float $$44 = -1.0F + $$9;
/* 126 */       float $$45 = $$14 * 2.5F + $$44;
/*     */       
/* 128 */       VertexConsumer $$46 = $$4.getBuffer(BEAM_RENDER_TYPE);
/*     */       
/* 130 */       PoseStack.Pose $$47 = $$3.last();
/* 131 */       Matrix4f $$48 = $$47.pose();
/* 132 */       Matrix3f $$49 = $$47.normal();
/*     */       
/* 134 */       vertex($$46, $$48, $$49, $$33, $$41, $$34, $$20, $$21, $$22, 0.4999F, $$45);
/* 135 */       vertex($$46, $$48, $$49, $$33, 0.0F, $$34, $$20, $$21, $$22, 0.4999F, $$44);
/* 136 */       vertex($$46, $$48, $$49, $$35, 0.0F, $$36, $$20, $$21, $$22, 0.0F, $$44);
/* 137 */       vertex($$46, $$48, $$49, $$35, $$41, $$36, $$20, $$21, $$22, 0.0F, $$45);
/*     */       
/* 139 */       vertex($$46, $$48, $$49, $$37, $$41, $$38, $$20, $$21, $$22, 0.4999F, $$45);
/* 140 */       vertex($$46, $$48, $$49, $$37, 0.0F, $$38, $$20, $$21, $$22, 0.4999F, $$44);
/* 141 */       vertex($$46, $$48, $$49, $$39, 0.0F, $$40, $$20, $$21, $$22, 0.0F, $$44);
/* 142 */       vertex($$46, $$48, $$49, $$39, $$41, $$40, $$20, $$21, $$22, 0.0F, $$45);
/*     */       
/* 144 */       float $$50 = 0.0F;
/* 145 */       if ($$0.tickCount % 2 == 0) {
/* 146 */         $$50 = 0.5F;
/*     */       }
/* 148 */       vertex($$46, $$48, $$49, $$25, $$41, $$26, $$20, $$21, $$22, 0.5F, $$50 + 0.5F);
/* 149 */       vertex($$46, $$48, $$49, $$27, $$41, $$28, $$20, $$21, $$22, 1.0F, $$50 + 0.5F);
/* 150 */       vertex($$46, $$48, $$49, $$31, $$41, $$32, $$20, $$21, $$22, 1.0F, $$50);
/* 151 */       vertex($$46, $$48, $$49, $$29, $$41, $$30, $$20, $$21, $$22, 0.5F, $$50);
/*     */       
/* 153 */       $$3.popPose();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void vertex(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, float $$3, float $$4, float $$5, int $$6, int $$7, int $$8, float $$9, float $$10) {
/* 158 */     $$0.vertex($$1, $$3, $$4, $$5).color($$6, $$7, $$8, 255).uv($$9, $$10).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal($$2, 0.0F, 1.0F, 0.0F).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(Guardian $$0) {
/* 163 */     return GUARDIAN_LOCATION;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\GuardianRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */