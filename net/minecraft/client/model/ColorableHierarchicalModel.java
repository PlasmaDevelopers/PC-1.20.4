/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public abstract class ColorableHierarchicalModel<E extends Entity> extends HierarchicalModel<E> {
/*  8 */   private float r = 1.0F;
/*  9 */   private float g = 1.0F;
/* 10 */   private float b = 1.0F;
/*    */   
/*    */   public void setColor(float $$0, float $$1, float $$2) {
/* 13 */     this.r = $$0;
/* 14 */     this.g = $$1;
/* 15 */     this.b = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 20 */     super.renderToBuffer($$0, $$1, $$2, $$3, this.r * $$4, this.g * $$5, this.b * $$6, $$7);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ColorableHierarchicalModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */