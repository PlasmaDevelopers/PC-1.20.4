/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.HumanoidModel;
/*    */ import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.ElytraLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ 
/*    */ public abstract class HumanoidMobRenderer<T extends Mob, M extends HumanoidModel<T>> extends MobRenderer<T, M> {
/*    */   public HumanoidMobRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2) {
/* 11 */     this($$0, $$1, $$2, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */   
/*    */   public HumanoidMobRenderer(EntityRendererProvider.Context $$0, M $$1, float $$2, float $$3, float $$4, float $$5) {
/* 15 */     super($$0, $$1, $$2);
/*    */     
/* 17 */     addLayer((RenderLayer<T, M>)new CustomHeadLayer(this, $$0.getModelSet(), $$3, $$4, $$5, $$0.getItemInHandRenderer()));
/* 18 */     addLayer((RenderLayer<T, M>)new ElytraLayer(this, $$0.getModelSet()));
/* 19 */     addLayer((RenderLayer<T, M>)new ItemInHandLayer(this, $$0.getItemInHandRenderer()));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\HumanoidMobRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */