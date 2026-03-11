/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.SkullModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.WitherSkull;
/*    */ 
/*    */ public class WitherSkullRenderer
/*    */   extends EntityRenderer<WitherSkull> {
/* 22 */   private static final ResourceLocation WITHER_INVULNERABLE_LOCATION = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
/* 23 */   private static final ResourceLocation WITHER_LOCATION = new ResourceLocation("textures/entity/wither/wither.png");
/*    */   
/*    */   private final SkullModel model;
/*    */   
/*    */   public WitherSkullRenderer(EntityRendererProvider.Context $$0) {
/* 28 */     super($$0);
/* 29 */     this.model = new SkullModel($$0.bakeLayer(ModelLayers.WITHER_SKULL));
/*    */   }
/*    */   
/*    */   public static LayerDefinition createSkullLayer() {
/* 33 */     MeshDefinition $$0 = new MeshDefinition();
/* 34 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 36 */     $$1.addOrReplaceChild("head", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 35).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 42 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getBlockLightLevel(WitherSkull $$0, BlockPos $$1) {
/* 47 */     return 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(WitherSkull $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 52 */     $$3.pushPose();
/*    */     
/* 54 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/*    */     
/* 56 */     float $$6 = Mth.rotLerp($$2, $$0.yRotO, $$0.getYRot());
/* 57 */     float $$7 = Mth.lerp($$2, $$0.xRotO, $$0.getXRot());
/*    */     
/* 59 */     VertexConsumer $$8 = $$4.getBuffer(this.model.renderType(getTextureLocation($$0)));
/* 60 */     this.model.setupAnim(0.0F, $$6, $$7);
/* 61 */     this.model.renderToBuffer($$3, $$8, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 63 */     $$3.popPose();
/*    */     
/* 65 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(WitherSkull $$0) {
/* 70 */     return $$0.isDangerous() ? WITHER_INVULNERABLE_LOCATION : WITHER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WitherSkullRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */