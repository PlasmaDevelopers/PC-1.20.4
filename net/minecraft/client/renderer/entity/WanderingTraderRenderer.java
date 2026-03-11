/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.VillagerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.npc.WanderingTrader;
/*    */ 
/*    */ public class WanderingTraderRenderer extends MobRenderer<WanderingTrader, VillagerModel<WanderingTrader>> {
/* 12 */   private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/wandering_trader.png");
/*    */   
/*    */   public WanderingTraderRenderer(EntityRendererProvider.Context $$0) {
/* 15 */     super($$0, new VillagerModel($$0.bakeLayer(ModelLayers.WANDERING_TRADER)), 0.5F);
/*    */     
/* 17 */     addLayer((RenderLayer<WanderingTrader, VillagerModel<WanderingTrader>>)new CustomHeadLayer(this, $$0.getModelSet(), $$0.getItemInHandRenderer()));
/* 18 */     addLayer((RenderLayer<WanderingTrader, VillagerModel<WanderingTrader>>)new CrossedArmsItemLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(WanderingTrader $$0) {
/* 23 */     return VILLAGER_BASE_SKIN;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(WanderingTrader $$0, PoseStack $$1, float $$2) {
/* 28 */     float $$3 = 0.9375F;
/* 29 */     $$1.scale(0.9375F, 0.9375F, 0.9375F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\WanderingTraderRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */