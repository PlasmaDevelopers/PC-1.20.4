/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public abstract class Model
/*    */ {
/*    */   protected final Function<ResourceLocation, RenderType> renderType;
/*    */   
/*    */   public Model(Function<ResourceLocation, RenderType> $$0) {
/* 14 */     this.renderType = $$0;
/*    */   }
/*    */   
/*    */   public final RenderType renderType(ResourceLocation $$0) {
/* 18 */     return this.renderType.apply($$0);
/*    */   }
/*    */   
/*    */   public abstract void renderToBuffer(PoseStack paramPoseStack, VertexConsumer paramVertexConsumer, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\Model.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */