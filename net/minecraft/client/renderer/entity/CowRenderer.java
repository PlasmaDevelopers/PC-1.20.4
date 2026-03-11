/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.CowModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.Cow;
/*    */ 
/*    */ public class CowRenderer extends MobRenderer<Cow, CowModel<Cow>> {
/*  9 */   private static final ResourceLocation COW_LOCATION = new ResourceLocation("textures/entity/cow/cow.png");
/*    */   
/*    */   public CowRenderer(EntityRendererProvider.Context $$0) {
/* 12 */     super($$0, new CowModel($$0.bakeLayer(ModelLayers.COW)), 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Cow $$0) {
/* 17 */     return COW_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CowRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */