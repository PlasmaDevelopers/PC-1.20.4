/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public abstract class AgeableHierarchicalModel<E extends Entity>
/*    */   extends HierarchicalModel<E>
/*    */ {
/*    */   private final float youngScaleFactor;
/*    */   private final float bodyYOffset;
/*    */   
/*    */   public AgeableHierarchicalModel(float $$0, float $$1) {
/* 17 */     this($$0, $$1, RenderType::entityCutoutNoCull);
/*    */   }
/*    */   
/*    */   public AgeableHierarchicalModel(float $$0, float $$1, Function<ResourceLocation, RenderType> $$2) {
/* 21 */     super($$2);
/* 22 */     this.bodyYOffset = $$1;
/* 23 */     this.youngScaleFactor = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 28 */     if (this.young) {
/* 29 */       $$0.pushPose();
/* 30 */       $$0.scale(this.youngScaleFactor, this.youngScaleFactor, this.youngScaleFactor);
/* 31 */       $$0.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
/*    */       
/* 33 */       root().render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/* 34 */       $$0.popPose();
/*    */     } else {
/* 36 */       root().render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AgeableHierarchicalModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */