/*    */ package net.minecraft.client.renderer.entity;
/*    */ 
/*    */ import net.minecraft.client.model.SnifferModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*    */ 
/*    */ public class SnifferRenderer extends MobRenderer<Sniffer, SnifferModel<Sniffer>> {
/* 10 */   private static final ResourceLocation SNIFFER_LOCATION = new ResourceLocation("textures/entity/sniffer/sniffer.png");
/*    */   
/*    */   public SnifferRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, new SnifferModel($$0.bakeLayer(ModelLayers.SNIFFER)), 1.1F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Sniffer $$0) {
/* 18 */     return SNIFFER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\SnifferRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */