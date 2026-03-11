/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.model.ParrotModel;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.model.geom.EntityModelSet;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.ParrotRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.entity.animal.Parrot;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class ParrotOnShoulderLayer<T extends Player> extends RenderLayer<T, PlayerModel<T>> {
/*    */   private final ParrotModel model;
/*    */   
/*    */   public ParrotOnShoulderLayer(RenderLayerParent<T, PlayerModel<T>> $$0, EntityModelSet $$1) {
/* 23 */     super($$0);
/* 24 */     this.model = new ParrotModel($$1.bakeLayer(ModelLayers.PARROT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 29 */     render($$0, $$1, $$2, $$3, $$4, $$5, $$8, $$9, true);
/* 30 */     render($$0, $$1, $$2, $$3, $$4, $$5, $$8, $$9, false);
/*    */   }
/*    */   
/*    */   private void render(PoseStack $$0, MultiBufferSource $$1, int $$2, T $$3, float $$4, float $$5, float $$6, float $$7, boolean $$8) {
/* 34 */     CompoundTag $$9 = $$8 ? $$3.getShoulderEntityLeft() : $$3.getShoulderEntityRight();
/* 35 */     EntityType.byString($$9.getString("id")).filter($$0 -> ($$0 == EntityType.PARROT)).ifPresent($$10 -> {
/*    */           $$0.pushPose();
/*    */           $$0.translate($$1 ? 0.4F : -0.4F, $$2.isCrouching() ? -1.3F : -1.5F, 0.0F);
/*    */           Parrot.Variant $$11 = Parrot.Variant.byId($$3.getInt("Variant"));
/*    */           VertexConsumer $$12 = $$4.getBuffer(this.model.renderType(ParrotRenderer.getVariantTexture($$11)));
/*    */           this.model.renderOnShoulder($$0, $$12, $$5, OverlayTexture.NO_OVERLAY, $$6, $$7, $$8, $$9, $$2.tickCount);
/*    */           $$0.popPose();
/*    */         });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\ParrotOnShoulderLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */