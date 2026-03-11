/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.SheepFurModel;
/*    */ import net.minecraft.client.model.SheepModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Sheep;
/*    */ import net.minecraft.world.item.DyeColor;
/*    */ 
/*    */ public class SheepFurLayer extends RenderLayer<Sheep, SheepModel<Sheep>> {
/* 19 */   private static final ResourceLocation SHEEP_FUR_LOCATION = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
/*    */   
/*    */   private final SheepFurModel<Sheep> model;
/*    */   
/*    */   public SheepFurLayer(RenderLayerParent<Sheep, SheepModel<Sheep>> $$0, EntityModelSet $$1) {
/* 24 */     super($$0);
/* 25 */     this.model = new SheepFurModel($$1.bakeLayer(ModelLayers.SHEEP_FUR));
/*    */   }
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Sheep $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*    */     float $$25, $$26, $$27;
/* 30 */     if ($$3.isSheared()) {
/*    */       return;
/*    */     }
/*    */     
/* 34 */     if ($$3.isInvisible()) {
/* 35 */       Minecraft $$10 = Minecraft.getInstance();
/* 36 */       boolean $$11 = $$10.shouldEntityAppearGlowing((Entity)$$3);
/* 37 */       if ($$11) {
/* 38 */         getParentModel().copyPropertiesTo((EntityModel)this.model);
/* 39 */         this.model.prepareMobModel($$3, $$4, $$5, $$6);
/* 40 */         this.model.setupAnim($$3, $$4, $$5, $$7, $$8, $$9);
/*    */         
/* 42 */         VertexConsumer $$12 = $$1.getBuffer(RenderType.outline(SHEEP_FUR_LOCATION));
/* 43 */         this.model.renderToBuffer($$0, $$12, $$2, LivingEntityRenderer.getOverlayCoords((LivingEntity)$$3, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/*    */       return;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 53 */     if ($$3.hasCustomName() && "jeb_".equals($$3.getName().getString())) {
/*    */       
/* 55 */       int $$13 = 25;
/* 56 */       int $$14 = $$3.tickCount / 25 + $$3.getId();
/* 57 */       int $$15 = (DyeColor.values()).length;
/* 58 */       int $$16 = $$14 % $$15;
/* 59 */       int $$17 = ($$14 + 1) % $$15;
/* 60 */       float $$18 = (($$3.tickCount % 25) + $$6) / 25.0F;
/* 61 */       float[] $$19 = Sheep.getColorArray(DyeColor.byId($$16));
/* 62 */       float[] $$20 = Sheep.getColorArray(DyeColor.byId($$17));
/* 63 */       float $$21 = $$19[0] * (1.0F - $$18) + $$20[0] * $$18;
/* 64 */       float $$22 = $$19[1] * (1.0F - $$18) + $$20[1] * $$18;
/* 65 */       float $$23 = $$19[2] * (1.0F - $$18) + $$20[2] * $$18;
/*    */     } else {
/* 67 */       float[] $$24 = Sheep.getColorArray($$3.getColor());
/* 68 */       $$25 = $$24[0];
/* 69 */       $$26 = $$24[1];
/* 70 */       $$27 = $$24[2];
/*    */     } 
/*    */     
/* 73 */     coloredCutoutModelCopyLayerRender((EntityModel<Sheep>)getParentModel(), (EntityModel<Sheep>)this.model, SHEEP_FUR_LOCATION, $$0, $$1, $$2, $$3, $$4, $$5, $$7, $$8, $$9, $$6, $$25, $$26, $$27);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\SheepFurLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */