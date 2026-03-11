/*    */ package net.minecraft.client.renderer.entity;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.EntityModel;
/*    */ import net.minecraft.client.model.PufferfishBigModel;
/*    */ import net.minecraft.client.model.geom.ModelLayers;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.animal.Pufferfish;
/*    */ 
/*    */ public class PufferfishRenderer extends MobRenderer<Pufferfish, EntityModel<Pufferfish>> {
/* 15 */   private static final ResourceLocation PUFFER_LOCATION = new ResourceLocation("textures/entity/fish/pufferfish.png");
/*    */   
/*    */   private int puffStateO;
/*    */   private final EntityModel<Pufferfish> small;
/*    */   private final EntityModel<Pufferfish> mid;
/*    */   private final EntityModel<Pufferfish> big;
/*    */   
/*    */   public PufferfishRenderer(EntityRendererProvider.Context $$0) {
/* 23 */     super($$0, new PufferfishBigModel($$0.bakeLayer(ModelLayers.PUFFERFISH_BIG)), 0.2F);
/* 24 */     this.puffStateO = 3;
/* 25 */     this.big = getModel();
/* 26 */     this.mid = (EntityModel<Pufferfish>)new PufferfishMidModel($$0.bakeLayer(ModelLayers.PUFFERFISH_MEDIUM));
/* 27 */     this.small = (EntityModel<Pufferfish>)new PufferfishSmallModel($$0.bakeLayer(ModelLayers.PUFFERFISH_SMALL));
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation getTextureLocation(Pufferfish $$0) {
/* 32 */     return PUFFER_LOCATION;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Pufferfish $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/* 37 */     int $$6 = $$0.getPuffState();
/* 38 */     if ($$6 != this.puffStateO) {
/* 39 */       if ($$6 == 0) {
/* 40 */         this.model = this.small;
/* 41 */       } else if ($$6 == 1) {
/* 42 */         this.model = this.mid;
/*    */       } else {
/* 44 */         this.model = this.big;
/*    */       } 
/*    */     }
/*    */     
/* 48 */     this.puffStateO = $$6;
/* 49 */     this.shadowRadius = 0.1F + 0.1F * $$6;
/* 50 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setupRotations(Pufferfish $$0, PoseStack $$1, float $$2, float $$3, float $$4) {
/* 55 */     $$1.translate(0.0F, Mth.cos($$2 * 0.05F) * 0.08F, 0.0F);
/* 56 */     super.setupRotations($$0, $$1, $$2, $$3, $$4);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PufferfishRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */