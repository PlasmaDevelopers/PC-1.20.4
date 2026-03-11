/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.math.Axis;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.CatModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.entity.layers.CatCollarLayer;
/*    */ import net.minecraft.client.renderer.entity.layers.RenderLayer;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.animal.Cat;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ 
/*    */ public class CatRenderer extends MobRenderer<Cat, CatModel<Cat>> {
/*    */   public CatRenderer(EntityRendererProvider.Context $$0) {
/* 20 */     super($$0, new CatModel($$0.bakeLayer(ModelLayers.CAT)), 0.4F);
/*    */     
/* 22 */     addLayer((RenderLayer<Cat, CatModel<Cat>>)new CatCollarLayer(this, $$0.getModelSet()));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Cat $$0) {
/* 27 */     return $$0.getResourceLocation();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(Cat $$0, PoseStack $$1, float $$2) {
/* 32 */     super.scale($$0, $$1, $$2);
/* 33 */     $$1.scale(0.8F, 0.8F, 0.8F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Cat $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 38 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */     
/* 40 */     float $$5 = $$0.getLieDownAmount($$4);
/* 41 */     if ($$5 > 0.0F) {
/* 42 */       $$1.translate(0.4F * $$5, 0.15F * $$5, 0.1F * $$5);
/* 43 */       $$1.mulPose(Axis.ZP.rotationDegrees(Mth.rotLerp($$5, 0.0F, 90.0F)));
/*    */ 
/*    */       
/* 46 */       BlockPos $$6 = $$0.blockPosition();
/* 47 */       List<Player> $$7 = $$0.level().getEntitiesOfClass(Player.class, (new AABB($$6)).inflate(2.0D, 2.0D, 2.0D));
/* 48 */       for (Player $$8 : $$7) {
/* 49 */         if ($$8.isSleeping()) {
/* 50 */           $$1.translate(0.15F * $$5, 0.0F, 0.0F);
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CatRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */