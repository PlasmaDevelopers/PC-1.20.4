/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.model.MinecartModel;
/*     */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MinecartRenderer<T extends AbstractMinecart> extends EntityRenderer<T> {
/*  20 */   private static final ResourceLocation MINECART_LOCATION = new ResourceLocation("textures/entity/minecart.png");
/*     */   
/*     */   protected final EntityModel<T> model;
/*     */   private final BlockRenderDispatcher blockRenderer;
/*     */   
/*     */   public MinecartRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1) {
/*  26 */     super($$0);
/*  27 */     this.shadowRadius = 0.7F;
/*  28 */     this.model = (EntityModel<T>)new MinecartModel($$0.bakeLayer($$1));
/*  29 */     this.blockRenderer = $$0.getBlockRenderDispatcher();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(T $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  34 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */     
/*  36 */     $$3.pushPose();
/*     */     
/*  38 */     long $$6 = $$0.getId() * 493286711L;
/*  39 */     $$6 = $$6 * $$6 * 4392167121L + $$6 * 98761L;
/*     */     
/*  41 */     float $$7 = (((float)($$6 >> 16L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  42 */     float $$8 = (((float)($$6 >> 20L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*  43 */     float $$9 = (((float)($$6 >> 24L & 0x7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
/*     */     
/*  45 */     $$3.translate($$7, $$8, $$9);
/*     */     
/*  47 */     double $$10 = Mth.lerp($$2, ((AbstractMinecart)$$0).xOld, $$0.getX());
/*  48 */     double $$11 = Mth.lerp($$2, ((AbstractMinecart)$$0).yOld, $$0.getY());
/*  49 */     double $$12 = Mth.lerp($$2, ((AbstractMinecart)$$0).zOld, $$0.getZ());
/*     */     
/*  51 */     double $$13 = 0.30000001192092896D;
/*     */     
/*  53 */     Vec3 $$14 = $$0.getPos($$10, $$11, $$12);
/*     */     
/*  55 */     float $$15 = Mth.lerp($$2, ((AbstractMinecart)$$0).xRotO, $$0.getXRot());
/*     */     
/*  57 */     if ($$14 != null) {
/*  58 */       Vec3 $$16 = $$0.getPosOffs($$10, $$11, $$12, 0.30000001192092896D);
/*  59 */       Vec3 $$17 = $$0.getPosOffs($$10, $$11, $$12, -0.30000001192092896D);
/*  60 */       if ($$16 == null) {
/*  61 */         $$16 = $$14;
/*     */       }
/*  63 */       if ($$17 == null) {
/*  64 */         $$17 = $$14;
/*     */       }
/*     */       
/*  67 */       $$3.translate($$14.x - $$10, ($$16.y + $$17.y) / 2.0D - $$11, $$14.z - $$12);
/*     */       
/*  69 */       Vec3 $$18 = $$17.add(-$$16.x, -$$16.y, -$$16.z);
/*  70 */       if ($$18.length() != 0.0D) {
/*  71 */         $$18 = $$18.normalize();
/*  72 */         $$1 = (float)(Math.atan2($$18.z, $$18.x) * 180.0D / Math.PI);
/*  73 */         $$15 = (float)(Math.atan($$18.y) * 73.0D);
/*     */       } 
/*     */     } 
/*  76 */     $$3.translate(0.0F, 0.375F, 0.0F);
/*     */     
/*  78 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F - $$1));
/*  79 */     $$3.mulPose(Axis.ZP.rotationDegrees(-$$15));
/*  80 */     float $$19 = $$0.getHurtTime() - $$2;
/*  81 */     float $$20 = $$0.getDamage() - $$2;
/*  82 */     if ($$20 < 0.0F) {
/*  83 */       $$20 = 0.0F;
/*     */     }
/*  85 */     if ($$19 > 0.0F) {
/*  86 */       $$3.mulPose(Axis.XP.rotationDegrees(Mth.sin($$19) * $$19 * $$20 / 10.0F * $$0.getHurtDir()));
/*     */     }
/*  88 */     int $$21 = $$0.getDisplayOffset();
/*     */     
/*  90 */     BlockState $$22 = $$0.getDisplayBlockState();
/*  91 */     if ($$22.getRenderShape() != RenderShape.INVISIBLE) {
/*  92 */       $$3.pushPose();
/*     */       
/*  94 */       float $$23 = 0.75F;
/*     */       
/*  96 */       $$3.scale(0.75F, 0.75F, 0.75F);
/*  97 */       $$3.translate(-0.5F, ($$21 - 8) / 16.0F, 0.5F);
/*  98 */       $$3.mulPose(Axis.YP.rotationDegrees(90.0F));
/*  99 */       renderMinecartContents($$0, $$2, $$22, $$3, $$4, $$5);
/*     */       
/* 101 */       $$3.popPose();
/*     */     } 
/*     */     
/* 104 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/* 105 */     this.model.setupAnim((Entity)$$0, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
/* 106 */     VertexConsumer $$24 = $$4.getBuffer(this.model.renderType(getTextureLocation($$0)));
/* 107 */     this.model.renderToBuffer($$3, $$24, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/* 108 */     $$3.popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(T $$0) {
/* 113 */     return MINECART_LOCATION;
/*     */   }
/*     */   
/*     */   protected void renderMinecartContents(T $$0, float $$1, BlockState $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 117 */     this.blockRenderer.renderSingleBlock($$2, $$3, $$4, $$5, OverlayTexture.NO_OVERLAY);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\MinecartRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */