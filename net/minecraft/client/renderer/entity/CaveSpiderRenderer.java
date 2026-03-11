/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.monster.CaveSpider;
/*    */ 
/*    */ public class CaveSpiderRenderer extends SpiderRenderer<CaveSpider> {
/*  9 */   private static final ResourceLocation CAVE_SPIDER_LOCATION = new ResourceLocation("textures/entity/spider/cave_spider.png");
/*    */   private static final float SCALE = 0.7F;
/*    */   
/*    */   public CaveSpiderRenderer(EntityRendererProvider.Context $$0) {
/* 13 */     super($$0, ModelLayers.CAVE_SPIDER);
/* 14 */     this.shadowRadius *= 0.7F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void scale(CaveSpider $$0, PoseStack $$1, float $$2) {
/* 19 */     $$1.scale(0.7F, 0.7F, 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(CaveSpider $$0) {
/* 24 */     return CAVE_SPIDER_LOCATION;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\CaveSpiderRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */