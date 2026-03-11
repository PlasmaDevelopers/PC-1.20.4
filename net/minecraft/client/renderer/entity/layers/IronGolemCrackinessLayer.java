/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import com.google.common.collect.ImmutableMap;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.IronGolemModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.RenderLayerParent;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.IronGolem;
/*    */ 
/*    */ public class IronGolemCrackinessLayer extends RenderLayer<IronGolem, IronGolemModel<IronGolem>> {
/* 14 */   private static final Map<IronGolem.Crackiness, ResourceLocation> resourceLocations = (Map<IronGolem.Crackiness, ResourceLocation>)ImmutableMap.of(IronGolem.Crackiness.LOW, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_low.png"), IronGolem.Crackiness.MEDIUM, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_medium.png"), IronGolem.Crackiness.HIGH, new ResourceLocation("textures/entity/iron_golem/iron_golem_crackiness_high.png"));
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IronGolemCrackinessLayer(RenderLayerParent<IronGolem, IronGolemModel<IronGolem>> $$0) {
/* 21 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, int $$2, IronGolem $$3, float $$4, float $$5, float $$6, float $$7, float $$8, float $$9) {
/* 26 */     if ($$3.isInvisible()) {
/*    */       return;
/*    */     }
/* 29 */     IronGolem.Crackiness $$10 = $$3.getCrackiness();
/* 30 */     if ($$10 == IronGolem.Crackiness.NONE) {
/*    */       return;
/*    */     }
/* 33 */     ResourceLocation $$11 = resourceLocations.get($$10);
/* 34 */     renderColoredCutoutModel((EntityModel<IronGolem>)getParentModel(), $$11, $$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\IronGolemCrackinessLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */