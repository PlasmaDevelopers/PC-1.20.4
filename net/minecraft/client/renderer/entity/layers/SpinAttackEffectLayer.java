/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class SpinAttackEffectLayer<T extends LivingEntity> extends RenderLayer<T, PlayerModel<T>> {
/* 23 */   public static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/trident_riptide.png");
/*    */   
/*    */   public static final String BOX = "box";
/*    */   private final ModelPart box;
/*    */   
/*    */   public SpinAttackEffectLayer(RenderLayerParent<T, PlayerModel<T>> $$0, EntityModelSet $$1) {
/* 29 */     super($$0);
/*    */     
/* 31 */     ModelPart $$2 = $$1.bakeLayer(ModelLayers.PLAYER_SPIN_ATTACK);
/* 32 */     this.box = $$2.getChild("box");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createLayer() {
/* 36 */     MeshDefinition $$0 = new MeshDefinition();
/* 37 */     PartDefinition $$1 = $$0.getRoot();
/* 38 */     $$1.addOrReplaceChild("box", 
/* 39 */         CubeListBuilder.create()
/* 40 */         .texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 32.0F, 16.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 44 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 49 */     if (!$$3.isAutoSpinAttack()) {
/*    */       return;
/*    */     }
/*    */     
/* 53 */     VertexConsumer $$10 = $$1.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
/* 54 */     for (int $$11 = 0; $$11 < 3; $$11++) {
/* 55 */       $$0.pushPose();
/* 56 */       float $$12 = $$7 * -(45 + $$11 * 5);
/* 57 */       $$0.mulPose(Axis.YP.rotationDegrees($$12));
/* 58 */       float $$13 = 0.75F * $$11;
/* 59 */       $$0.scale($$13, $$13, $$13);
/* 60 */       $$0.translate(0.0F, -0.2F + 0.6F * $$11, 0.0F);
/* 61 */       this.box.render($$0, $$10, $$2, OverlayTexture.NO_OVERLAY);
/* 62 */       $$0.popPose();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\SpinAttackEffectLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */