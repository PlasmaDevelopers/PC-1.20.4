/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.HorseModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ import net.minecraft.world.entity.animal.horse.Horse;
/*    */ import net.minecraft.world.item.DyeableHorseArmorItem;
/*    */ import net.minecraft.world.item.HorseArmorItem;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class HorseArmorLayer extends RenderLayer<Horse, HorseModel<Horse>> {
/*    */   public HorseArmorLayer(RenderLayerParent<Horse, HorseModel<Horse>> $$0, EntityModelSet $$1) {
/* 21 */     super($$0);
/* 22 */     this.model = new HorseModel($$1.bakeLayer(ModelLayers.HORSE_ARMOR));
/*    */   }
/*    */   private final HorseModel<Horse> model;
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, Horse $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/*    */     float $$16, $$17, $$18;
/* 27 */     ItemStack $$10 = $$3.getArmor();
/* 28 */     if (!($$10.getItem() instanceof HorseArmorItem)) {
/*    */       return;
/*    */     }
/*    */     
/* 32 */     HorseArmorItem $$11 = (HorseArmorItem)$$10.getItem();
/* 33 */     getParentModel().copyPropertiesTo((EntityModel)this.model);
/* 34 */     this.model.prepareMobModel((AbstractHorse)$$3, $$4, $$5, $$6);
/* 35 */     this.model.setupAnim((AbstractHorse)$$3, $$4, $$5, $$7, $$8, $$9);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     if ($$11 instanceof DyeableHorseArmorItem) {
/* 42 */       int $$12 = ((DyeableHorseArmorItem)$$11).getColor($$10);
/* 43 */       float $$13 = ($$12 >> 16 & 0xFF) / 255.0F;
/* 44 */       float $$14 = ($$12 >> 8 & 0xFF) / 255.0F;
/* 45 */       float $$15 = ($$12 & 0xFF) / 255.0F;
/*    */     } else {
/* 47 */       $$16 = 1.0F;
/* 48 */       $$17 = 1.0F;
/* 49 */       $$18 = 1.0F;
/*    */     } 
/* 51 */     VertexConsumer $$19 = $$1.getBuffer(RenderType.entityCutoutNoCull($$11.getTexture()));
/* 52 */     this.model.renderToBuffer($$0, $$19, $$2, OverlayTexture.NO_OVERLAY, $$16, $$17, $$18, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\HorseArmorLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */