/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.FishingHook;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class FishingHookRenderer extends EntityRenderer<FishingHook> {
/*  22 */   private static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/entity/fishing_hook.png");
/*  23 */   private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);
/*     */   
/*     */   private static final double VIEW_BOBBING_SCALE = 960.0D;
/*     */   
/*     */   public FishingHookRenderer(EntityRendererProvider.Context $$0) {
/*  28 */     super($$0);
/*     */   }
/*     */   public void render(FishingHook $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*     */     double $$26, $$27, $$28;
/*     */     float $$29;
/*  33 */     Player $$6 = $$0.getPlayerOwner();
/*  34 */     if ($$6 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  38 */     $$3.pushPose();
/*     */     
/*  40 */     $$3.pushPose();
/*  41 */     $$3.scale(0.5F, 0.5F, 0.5F);
/*     */     
/*  43 */     $$3.mulPose(this.entityRenderDispatcher.cameraOrientation());
/*  44 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F));
/*     */     
/*  46 */     PoseStack.Pose $$7 = $$3.last();
/*  47 */     Matrix4f $$8 = $$7.pose();
/*  48 */     Matrix3f $$9 = $$7.normal();
/*     */     
/*  50 */     VertexConsumer $$10 = $$4.getBuffer(RENDER_TYPE);
/*     */     
/*  52 */     vertex($$10, $$8, $$9, $$5, 0.0F, 0, 0, 1);
/*  53 */     vertex($$10, $$8, $$9, $$5, 1.0F, 0, 1, 1);
/*  54 */     vertex($$10, $$8, $$9, $$5, 1.0F, 1, 1, 0);
/*  55 */     vertex($$10, $$8, $$9, $$5, 0.0F, 1, 0, 0);
/*     */     
/*  57 */     $$3.popPose();
/*     */     
/*  59 */     int $$11 = ($$6.getMainArm() == HumanoidArm.RIGHT) ? 1 : -1;
/*  60 */     ItemStack $$12 = $$6.getMainHandItem();
/*  61 */     if (!$$12.is(Items.FISHING_ROD)) {
/*  62 */       $$11 = -$$11;
/*     */     }
/*     */     
/*  65 */     float $$13 = $$6.getAttackAnim($$2);
/*  66 */     float $$14 = Mth.sin(Mth.sqrt($$13) * 3.1415927F);
/*     */     
/*  68 */     float $$15 = Mth.lerp($$2, $$6.yBodyRotO, $$6.yBodyRot) * 0.017453292F;
/*  69 */     double $$16 = Mth.sin($$15);
/*  70 */     double $$17 = Mth.cos($$15);
/*  71 */     double $$18 = $$11 * 0.35D;
/*  72 */     double $$19 = 0.8D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  79 */     if ((this.entityRenderDispatcher.options != null && !this.entityRenderDispatcher.options.getCameraType().isFirstPerson()) || $$6 != (Minecraft.getInstance()).player) {
/*     */       
/*  81 */       double $$20 = Mth.lerp($$2, $$6.xo, $$6.getX()) - $$17 * $$18 - $$16 * 0.8D;
/*  82 */       double $$21 = $$6.yo + $$6.getEyeHeight() + ($$6.getY() - $$6.yo) * $$2 - 0.45D;
/*  83 */       double $$22 = Mth.lerp($$2, $$6.zo, $$6.getZ()) - $$16 * $$18 + $$17 * 0.8D;
/*     */ 
/*     */       
/*  86 */       float $$23 = $$6.isCrouching() ? -0.1875F : 0.0F;
/*     */     } else {
/*     */       
/*  89 */       double $$24 = 960.0D / ((Integer)this.entityRenderDispatcher.options.fov().get()).intValue();
/*     */       
/*  91 */       Vec3 $$25 = this.entityRenderDispatcher.camera.getNearPlane().getPointOnPlane($$11 * 0.525F, -0.1F);
/*  92 */       $$25 = $$25.scale($$24);
/*  93 */       $$25 = $$25.yRot($$14 * 0.5F);
/*  94 */       $$25 = $$25.xRot(-$$14 * 0.7F);
/*     */       
/*  96 */       $$26 = Mth.lerp($$2, $$6.xo, $$6.getX()) + $$25.x;
/*  97 */       $$27 = Mth.lerp($$2, $$6.yo, $$6.getY()) + $$25.y;
/*  98 */       $$28 = Mth.lerp($$2, $$6.zo, $$6.getZ()) + $$25.z;
/*     */       
/* 100 */       $$29 = $$6.getEyeHeight();
/*     */     } 
/*     */     
/* 103 */     double $$30 = Mth.lerp($$2, $$0.xo, $$0.getX());
/* 104 */     double $$31 = Mth.lerp($$2, $$0.yo, $$0.getY()) + 0.25D;
/* 105 */     double $$32 = Mth.lerp($$2, $$0.zo, $$0.getZ());
/*     */     
/* 107 */     float $$33 = (float)($$26 - $$30);
/* 108 */     float $$34 = (float)($$27 - $$31) + $$29;
/* 109 */     float $$35 = (float)($$28 - $$32);
/*     */     
/* 111 */     VertexConsumer $$36 = $$4.getBuffer(RenderType.lineStrip());
/*     */     
/* 113 */     PoseStack.Pose $$37 = $$3.last();
/*     */     
/* 115 */     int $$38 = 16;
/* 116 */     for (int $$39 = 0; $$39 <= 16; $$39++) {
/* 117 */       stringVertex($$33, $$34, $$35, $$36, $$37, fraction($$39, 16), fraction($$39 + 1, 16));
/*     */     }
/* 119 */     $$3.popPose();
/* 120 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static float fraction(int $$0, int $$1) {
/* 124 */     return $$0 / $$1;
/*     */   }
/*     */   
/*     */   private static void vertex(VertexConsumer $$0, Matrix4f $$1, Matrix3f $$2, int $$3, float $$4, int $$5, int $$6, int $$7) {
/* 128 */     $$0.vertex($$1, $$4 - 0.5F, $$5 - 0.5F, 0.0F).color(255, 255, 255, 255).uv($$6, $$7).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$3).normal($$2, 0.0F, 1.0F, 0.0F).endVertex();
/*     */   }
/*     */   
/*     */   private static void stringVertex(float $$0, float $$1, float $$2, VertexConsumer $$3, PoseStack.Pose $$4, float $$5, float $$6) {
/* 132 */     float $$7 = $$0 * $$5;
/* 133 */     float $$8 = $$1 * ($$5 * $$5 + $$5) * 0.5F + 0.25F;
/* 134 */     float $$9 = $$2 * $$5;
/*     */     
/* 136 */     float $$10 = $$0 * $$6 - $$7;
/* 137 */     float $$11 = $$1 * ($$6 * $$6 + $$6) * 0.5F + 0.25F - $$8;
/* 138 */     float $$12 = $$2 * $$6 - $$9;
/* 139 */     float $$13 = Mth.sqrt($$10 * $$10 + $$11 * $$11 + $$12 * $$12);
/* 140 */     $$10 /= $$13;
/* 141 */     $$11 /= $$13;
/* 142 */     $$12 /= $$13;
/*     */     
/* 144 */     $$3.vertex($$4.pose(), $$7, $$8, $$9).color(0, 0, 0, 255).normal($$4.normal(), $$10, $$11, $$12).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(FishingHook $$0) {
/* 149 */     return TEXTURE_LOCATION;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\FishingHookRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */