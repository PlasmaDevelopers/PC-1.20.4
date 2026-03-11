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
/*    */ public abstract class ListModel<E extends Entity>
/*    */   extends EntityModel<E> {
/*    */   public ListModel() {
/* 14 */     this(RenderType::entityCutoutNoCull);
/*    */   }
/*    */   
/*    */   public ListModel(Function<ResourceLocation, RenderType> $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 23 */     parts().forEach($$8 -> $$8.render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*    */   }
/*    */   
/*    */   public abstract Iterable<ModelPart> parts();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ListModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */