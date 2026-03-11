/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public abstract class AgeableListModel<E extends Entity>
/*    */   extends EntityModel<E>
/*    */ {
/*    */   private final boolean scaleHead;
/*    */   private final float babyYHeadOffset;
/*    */   private final float babyZHeadOffset;
/*    */   private final float babyHeadScale;
/*    */   private final float babyBodyScale;
/*    */   private final float bodyYOffset;
/*    */   
/*    */   protected AgeableListModel(boolean $$0, float $$1, float $$2) {
/* 22 */     this($$0, $$1, $$2, 2.0F, 2.0F, 24.0F);
/*    */   }
/*    */   
/*    */   protected AgeableListModel(boolean $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 26 */     this(RenderType::entityCutoutNoCull, $$0, $$1, $$2, $$3, $$4, $$5);
/*    */   }
/*    */   
/*    */   protected AgeableListModel(Function<ResourceLocation, RenderType> $$0, boolean $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
/* 30 */     super($$0);
/* 31 */     this.scaleHead = $$1;
/* 32 */     this.babyYHeadOffset = $$2;
/* 33 */     this.babyZHeadOffset = $$3;
/* 34 */     this.babyHeadScale = $$4;
/* 35 */     this.babyBodyScale = $$5;
/* 36 */     this.bodyYOffset = $$6;
/*    */   }
/*    */   
/*    */   protected AgeableListModel() {
/* 40 */     this(false, 5.0F, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 45 */     if (this.young) {
/* 46 */       $$0.pushPose();
/* 47 */       if (this.scaleHead) {
/* 48 */         float $$8 = 1.5F / this.babyHeadScale;
/* 49 */         $$0.scale($$8, $$8, $$8);
/*    */       } 
/* 51 */       $$0.translate(0.0F, this.babyYHeadOffset / 16.0F, this.babyZHeadOffset / 16.0F);
/* 52 */       headParts().forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 53 */       $$0.popPose();
/*    */       
/* 55 */       $$0.pushPose();
/* 56 */       float $$9 = 1.0F / this.babyBodyScale;
/* 57 */       $$0.scale($$9, $$9, $$9);
/* 58 */       $$0.translate(0.0F, this.bodyYOffset / 16.0F, 0.0F);
/* 59 */       bodyParts().forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 60 */       $$0.popPose();
/*    */     } else {
/* 62 */       headParts().forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/* 63 */       bodyParts().forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract Iterable<ModelPart> headParts();
/*    */   
/*    */   protected abstract Iterable<ModelPart> bodyParts();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AgeableListModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */