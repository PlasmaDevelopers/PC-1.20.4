/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.model.ZombieVillagerModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.ZombieVillager;
/*    */ 
/*    */ public class ZombieVillagerRenderer extends HumanoidMobRenderer<ZombieVillager, ZombieVillagerModel<ZombieVillager>> {
/* 11 */   private static final ResourceLocation ZOMBIE_VILLAGER_LOCATION = new ResourceLocation("textures/entity/zombie_villager/zombie_villager.png");
/*    */   
/*    */   public ZombieVillagerRenderer(EntityRendererProvider.Context $$0) {
/* 14 */     super($$0, new ZombieVillagerModel($$0.bakeLayer(ModelLayers.ZOMBIE_VILLAGER)), 0.5F);
/*    */     
/* 16 */     addLayer((RenderLayer<ZombieVillager, ZombieVillagerModel<ZombieVillager>>)new HumanoidArmorLayer(this, (HumanoidModel)new ZombieVillagerModel($$0
/* 17 */             .bakeLayer(ModelLayers.ZOMBIE_VILLAGER_INNER_ARMOR)), (HumanoidModel)new ZombieVillagerModel($$0
/* 18 */             .bakeLayer(ModelLayers.ZOMBIE_VILLAGER_OUTER_ARMOR)), $$0
/* 19 */           .getModelManager()));
/*    */     
/* 21 */     addLayer((RenderLayer<ZombieVillager, ZombieVillagerModel<ZombieVillager>>)new VillagerProfessionLayer(this, $$0.getResourceManager(), "zombie_villager"));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(ZombieVillager $$0) {
/* 26 */     return ZOMBIE_VILLAGER_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isShaking(ZombieVillager $$0) {
/* 31 */     return (super.isShaking($$0) || $$0.isConverting());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ZombieVillagerRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */