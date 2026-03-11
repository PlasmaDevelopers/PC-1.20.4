/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.ZombieModel;
/*    */ import net.minecraft.client.model.geom.ModelLayerLocation;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.world.entity.monster.Zombie;
/*    */ 
/*    */ public class ZombieRenderer
/*    */   extends AbstractZombieRenderer<Zombie, ZombieModel<Zombie>> {
/*    */   public ZombieRenderer(EntityRendererProvider.Context $$0) {
/* 11 */     this($$0, ModelLayers.ZOMBIE, ModelLayers.ZOMBIE_INNER_ARMOR, ModelLayers.ZOMBIE_OUTER_ARMOR);
/*    */   }
/*    */   
/*    */   public ZombieRenderer(EntityRendererProvider.Context $$0, ModelLayerLocation $$1, ModelLayerLocation $$2, ModelLayerLocation $$3) {
/* 15 */     super($$0, new ZombieModel($$0.bakeLayer($$1)), new ZombieModel($$0
/* 16 */           .bakeLayer($$2)), new ZombieModel($$0
/* 17 */           .bakeLayer($$3)));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\ZombieRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */