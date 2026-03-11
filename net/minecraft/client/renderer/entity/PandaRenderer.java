/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.PandaModel;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.renderer.entity.layers.PandaHoldsItemLayer;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.animal.Panda;
/*     */ 
/*     */ public class PandaRenderer extends MobRenderer<Panda, PandaModel<Panda>> {
/*     */   static {
/*  17 */     TEXTURES = (Map<Panda.Gene, ResourceLocation>)Util.make(Maps.newEnumMap(Panda.Gene.class), $$0 -> {
/*     */           $$0.put(Panda.Gene.NORMAL, new ResourceLocation("textures/entity/panda/panda.png"));
/*     */           $$0.put(Panda.Gene.LAZY, new ResourceLocation("textures/entity/panda/lazy_panda.png"));
/*     */           $$0.put(Panda.Gene.WORRIED, new ResourceLocation("textures/entity/panda/worried_panda.png"));
/*     */           $$0.put(Panda.Gene.PLAYFUL, new ResourceLocation("textures/entity/panda/playful_panda.png"));
/*     */           $$0.put(Panda.Gene.BROWN, new ResourceLocation("textures/entity/panda/brown_panda.png"));
/*     */           $$0.put(Panda.Gene.WEAK, new ResourceLocation("textures/entity/panda/weak_panda.png"));
/*     */           $$0.put(Panda.Gene.AGGRESSIVE, new ResourceLocation("textures/entity/panda/aggressive_panda.png"));
/*     */         });
/*     */   } private static final Map<Panda.Gene, ResourceLocation> TEXTURES;
/*     */   public PandaRenderer(EntityRendererProvider.Context $$0) {
/*  28 */     super($$0, new PandaModel($$0.bakeLayer(ModelLayers.PANDA)), 0.9F);
/*     */     
/*  30 */     addLayer((RenderLayer<Panda, PandaModel<Panda>>)new PandaHoldsItemLayer(this, $$0.getItemInHandRenderer()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(Panda $$0) {
/*  35 */     return TEXTURES.getOrDefault($$0.getVariant(), TEXTURES.get(Panda.Gene.NORMAL));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setupRotations(Panda $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/*  40 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*     */     
/*  42 */     if ($$0.rollCounter > 0) {
/*  43 */       int $$5 = $$0.rollCounter;
/*  44 */       int $$6 = $$5 + 1;
/*     */       
/*  46 */       float $$7 = 7.0F;
/*  47 */       float $$8 = $$0.isBaby() ? 0.3F : 0.8F;
/*     */       
/*  49 */       if ($$5 < 8) {
/*  50 */         float $$9 = (90 * $$5) / 7.0F;
/*  51 */         float $$10 = (90 * $$6) / 7.0F;
/*  52 */         float $$11 = getAngle($$9, $$10, $$6, $$4, 8.0F);
/*     */         
/*  54 */         $$1.translate(0.0F, ($$8 + 0.2F) * $$11 / 90.0F, 0.0F);
/*  55 */         $$1.mulPose(Axis.XP.rotationDegrees(-$$11));
/*  56 */       } else if ($$5 < 16) {
/*  57 */         float $$12 = ($$5 - 8.0F) / 7.0F;
/*  58 */         float $$13 = 90.0F + 90.0F * $$12;
/*  59 */         float $$14 = 90.0F + 90.0F * ($$6 - 8.0F) / 7.0F;
/*  60 */         float $$15 = getAngle($$13, $$14, $$6, $$4, 16.0F);
/*     */         
/*  62 */         $$1.translate(0.0F, $$8 + 0.2F + ($$8 - 0.2F) * ($$15 - 90.0F) / 90.0F, 0.0F);
/*  63 */         $$1.mulPose(Axis.XP.rotationDegrees(-$$15));
/*  64 */       } else if ($$5 < 24.0F) {
/*  65 */         float $$16 = ($$5 - 16.0F) / 7.0F;
/*  66 */         float $$17 = 180.0F + 90.0F * $$16;
/*  67 */         float $$18 = 180.0F + 90.0F * ($$6 - 16.0F) / 7.0F;
/*  68 */         float $$19 = getAngle($$17, $$18, $$6, $$4, 24.0F);
/*     */         
/*  70 */         $$1.translate(0.0F, $$8 + $$8 * (270.0F - $$19) / 90.0F, 0.0F);
/*  71 */         $$1.mulPose(Axis.XP.rotationDegrees(-$$19));
/*  72 */       } else if ($$5 < 32) {
/*  73 */         float $$20 = ($$5 - 24.0F) / 7.0F;
/*  74 */         float $$21 = 270.0F + 90.0F * $$20;
/*  75 */         float $$22 = 270.0F + 90.0F * ($$6 - 24.0F) / 7.0F;
/*  76 */         float $$23 = getAngle($$21, $$22, $$6, $$4, 32.0F);
/*     */         
/*  78 */         $$1.translate(0.0F, $$8 * (360.0F - $$23) / 90.0F, 0.0F);
/*  79 */         $$1.mulPose(Axis.XP.rotationDegrees(-$$23));
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     float $$24 = $$0.getSitAmount($$4);
/*  84 */     if ($$24 > 0.0F) {
/*  85 */       $$1.translate(0.0F, 0.8F * $$24, 0.0F);
/*  86 */       $$1.mulPose(Axis.XP.rotationDegrees(Mth.lerp($$24, $$0.getXRot(), $$0.getXRot() + 90.0F)));
/*     */ 
/*     */       
/*  89 */       $$1.translate(0.0F, -1.0F * $$24, 0.0F);
/*     */       
/*  91 */       if ($$0.isScared()) {
/*  92 */         float $$25 = (float)(Math.cos($$0.tickCount * 1.25D) * Math.PI * 0.05000000074505806D);
/*     */         
/*  94 */         $$1.mulPose(Axis.YP.rotationDegrees($$25));
/*  95 */         if ($$0.isBaby()) {
/*  96 */           $$1.translate(0.0F, 0.8F, 0.55F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 101 */     float $$26 = $$0.getLieOnBackAmount($$4);
/* 102 */     if ($$26 > 0.0F) {
/* 103 */       float $$27 = $$0.isBaby() ? 0.5F : 1.3F;
/* 104 */       $$1.translate(0.0F, $$27 * $$26, 0.0F);
/* 105 */       $$1.mulPose(Axis.XP.rotationDegrees(Mth.lerp($$26, $$0.getXRot(), $$0.getXRot() + 180.0F)));
/*     */     } 
/*     */   }
/*     */   
/*     */   private float getAngle(float $$0, float $$1, int $$2, float $$3, float $$4) {
/* 110 */     if ($$2 < $$4) {
/* 111 */       return Mth.lerp($$3, $$0, $$1);
/*     */     }
/* 113 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PandaRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */