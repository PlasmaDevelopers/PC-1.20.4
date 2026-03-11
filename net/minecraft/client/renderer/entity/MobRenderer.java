/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.renderer.LightTexture;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public abstract class MobRenderer<T extends Mob, M extends EntityModel<T>> extends LivingEntityRenderer<T, M> {
/*     */   public MobRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2) {
/*  22 */     super($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean shouldShowName(T $$0) {
/*  27 */     return (super.shouldShowName($$0) && ($$0.shouldShowName() || ($$0.hasCustomName() && $$0 == this.entityRenderDispatcher.crosshairPickEntity)));
/*     */   }
/*     */   public static final int LEASH_RENDER_STEPS = 24;
/*     */   
/*     */   public boolean shouldRender(T $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/*  32 */     if (super.shouldRender($$0, $$1, $$2, $$3, $$4)) {
/*  33 */       return true;
/*     */     }
/*     */     
/*  36 */     Entity $$5 = $$0.getLeashHolder();
/*  37 */     if ($$5 != null) {
/*  38 */       return $$1.isVisible($$5.getBoundingBoxForCulling());
/*     */     }
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  45 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  47 */     Entity $$6 = $$0.getLeashHolder();
/*  48 */     if ($$6 == null) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     renderLeash($$0, $$2, $$3, $$4, $$6);
/*     */   }
/*     */   
/*     */   private <E extends Entity> void renderLeash(T $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, E $$4) {
/*  56 */     $$2.pushPose();
/*     */     
/*  58 */     Vec3 $$5 = $$4.getRopeHoldPosition($$1);
/*     */     
/*  60 */     double $$6 = (Mth.lerp($$1, ((Mob)$$0).yBodyRotO, ((Mob)$$0).yBodyRot) * 0.017453292F) + 1.5707963267948966D;
/*  61 */     Vec3 $$7 = $$0.getLeashOffset($$1);
/*  62 */     double $$8 = Math.cos($$6) * $$7.z + Math.sin($$6) * $$7.x;
/*  63 */     double $$9 = Math.sin($$6) * $$7.z - Math.cos($$6) * $$7.x;
/*     */     
/*  65 */     double $$10 = Mth.lerp($$1, ((Mob)$$0).xo, $$0.getX()) + $$8;
/*  66 */     double $$11 = Mth.lerp($$1, ((Mob)$$0).yo, $$0.getY()) + $$7.y;
/*  67 */     double $$12 = Mth.lerp($$1, ((Mob)$$0).zo, $$0.getZ()) + $$9;
/*     */     
/*  69 */     $$2.translate($$8, $$7.y, $$9);
/*     */     
/*  71 */     float $$13 = (float)($$5.x - $$10);
/*  72 */     float $$14 = (float)($$5.y - $$11);
/*  73 */     float $$15 = (float)($$5.z - $$12);
/*     */     
/*  75 */     float $$16 = 0.025F;
/*     */     
/*  77 */     VertexConsumer $$17 = $$3.getBuffer(RenderType.leash());
/*  78 */     Matrix4f $$18 = $$2.last().pose();
/*     */     
/*  80 */     float $$19 = Mth.invSqrt($$13 * $$13 + $$15 * $$15) * 0.025F / 2.0F;
/*     */ 
/*     */     
/*  83 */     float $$20 = $$15 * $$19;
/*  84 */     float $$21 = $$13 * $$19;
/*     */     
/*  86 */     BlockPos $$22 = BlockPos.containing((Position)$$0.getEyePosition($$1));
/*  87 */     BlockPos $$23 = BlockPos.containing((Position)$$4.getEyePosition($$1));
/*     */     
/*  89 */     int $$24 = getBlockLightLevel($$0, $$22);
/*  90 */     int $$25 = this.entityRenderDispatcher.<E>getRenderer($$4).getBlockLightLevel($$4, $$23);
/*     */     
/*  92 */     int $$26 = $$0.level().getBrightness(LightLayer.SKY, $$22);
/*  93 */     int $$27 = $$0.level().getBrightness(LightLayer.SKY, $$23);
/*     */     
/*  95 */     for (int $$28 = 0; $$28 <= 24; $$28++) {
/*  96 */       addVertexPair($$17, $$18, $$13, $$14, $$15, $$24, $$25, $$26, $$27, 0.025F, 0.025F, $$20, $$21, $$28, false);
/*     */     }
/*  98 */     for (int $$29 = 24; $$29 >= 0; $$29--) {
/*  99 */       addVertexPair($$17, $$18, $$13, $$14, $$15, $$24, $$25, $$26, $$27, 0.025F, 0.0F, $$20, $$21, $$29, true);
/*     */     }
/*     */     
/* 102 */     $$2.popPose();
/*     */   }
/*     */   
/*     */   private static void addVertexPair(VertexConsumer $$0, Matrix4f $$1, float $$2, float $$3, float $$4, int $$5, int $$6, int $$7, int $$8, float $$9, float $$10, float $$11, float $$12, int $$13, boolean $$14) {
/* 106 */     float $$15 = $$13 / 24.0F;
/* 107 */     int $$16 = (int)Mth.lerp($$15, $$5, $$6);
/* 108 */     int $$17 = (int)Mth.lerp($$15, $$7, $$8);
/* 109 */     int $$18 = LightTexture.pack($$16, $$17);
/*     */     
/* 111 */     float $$19 = ($$13 % 2 == ($$14 ? 1 : 0)) ? 0.7F : 1.0F;
/* 112 */     float $$20 = 0.5F * $$19;
/* 113 */     float $$21 = 0.4F * $$19;
/* 114 */     float $$22 = 0.3F * $$19;
/*     */     
/* 116 */     float $$23 = $$2 * $$15;
/* 117 */     float $$24 = ($$3 > 0.0F) ? ($$3 * $$15 * $$15) : ($$3 - $$3 * (1.0F - $$15) * (1.0F - $$15));
/* 118 */     float $$25 = $$4 * $$15;
/*     */     
/* 120 */     $$0.vertex($$1, $$23 - $$11, $$24 + $$10, $$25 + $$12).color($$20, $$21, $$22, 1.0F).uv2($$18).endVertex();
/* 121 */     $$0.vertex($$1, $$23 + $$11, $$24 + $$9 - $$10, $$25 - $$12).color($$20, $$21, $$22, 1.0F).uv2($$18).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\MobRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */