/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.VillagerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.npc.Villager;
/*    */ 
/*    */ public class VillagerRenderer extends MobRenderer<Villager, VillagerModel<Villager>> {
/* 13 */   private static final ResourceLocation VILLAGER_BASE_SKIN = new ResourceLocation("textures/entity/villager/villager.png");
/*    */   
/*    */   public VillagerRenderer(EntityRendererProvider.Context $$0) {
/* 16 */     super($$0, new VillagerModel($$0.bakeLayer(ModelLayers.VILLAGER)), 0.5F);
/*    */     
/* 18 */     addLayer((RenderLayer<Villager, VillagerModel<Villager>>)new CustomHeadLayer(this, $$0.getModelSet(), $$0.getItemInHandRenderer()));
/* 19 */     addLayer((RenderLayer<Villager, VillagerModel<Villager>>)new VillagerProfessionLayer(this, $$0.getResourceManager(), "villager"));
/* 20 */     addLayer((RenderLayer<Villager, VillagerModel<Villager>>)new CrossedArmsItemLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Villager $$0) {
/* 25 */     return VILLAGER_BASE_SKIN;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void scale(Villager $$0, PoseStack $$1, float $$2) {
/* 31 */     float $$3 = 0.9375F;
/* 32 */     if ($$0.isBaby()) {
/* 33 */       $$3 *= 0.5F;
/* 34 */       this.shadowRadius = 0.25F;
/*    */     } else {
/* 36 */       this.shadowRadius = 0.5F;
/*    */     } 
/* 38 */     $$1.scale($$3, $$3, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\VillagerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */