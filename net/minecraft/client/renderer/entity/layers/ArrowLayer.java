/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import net.minecraft.client.model.PlayerModel;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
/*    */ import net.minecraft.client.renderer.entity.EntityRendererProvider;
/*    */ import net.minecraft.client.renderer.entity.LivingEntityRenderer;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ import net.minecraft.world.entity.projectile.Arrow;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ArrowLayer<T extends LivingEntity, M extends PlayerModel<T>> extends StuckInBodyLayer<T, M> {
/*    */   private final EntityRenderDispatcher dispatcher;
/*    */   
/*    */   public ArrowLayer(EntityRendererProvider.Context $$0, LivingEntityRenderer<T, M> $$1) {
/* 19 */     super($$1);
/* 20 */     this.dispatcher = $$0.getEntityRenderDispatcher();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int numStuck(T $$0) {
/* 25 */     return $$0.getArrowCount();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderStuckItem(PoseStack $$0, MultiBufferSource $$1, int $$2, Entity $$3, float $$4, float $$5, float $$6, float $$7) {
/* 30 */     float $$8 = Mth.sqrt($$4 * $$4 + $$6 * $$6);
/* 31 */     Arrow $$9 = new Arrow($$3.level(), $$3.getX(), $$3.getY(), $$3.getZ(), ItemStack.EMPTY);
/* 32 */     $$9.setYRot((float)(Math.atan2($$4, $$6) * 57.2957763671875D));
/* 33 */     $$9.setXRot((float)(Math.atan2($$5, $$8) * 57.2957763671875D));
/* 34 */     $$9.yRotO = $$9.getYRot();
/* 35 */     $$9.xRotO = $$9.getXRot();
/* 36 */     this.dispatcher.render((Entity)$$9, 0.0D, 0.0D, 0.0D, 0.0F, $$7, $$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\layers\ArrowLayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */