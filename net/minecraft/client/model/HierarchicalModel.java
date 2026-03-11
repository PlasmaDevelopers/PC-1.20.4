/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.animation.AnimationDefinition;
/*    */ import net.minecraft.client.animation.KeyframeAnimations;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.AnimationState;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ 
/*    */ public abstract class HierarchicalModel<E extends Entity>
/*    */   extends EntityModel<E>
/*    */ {
/* 20 */   private static final Vector3f ANIMATION_VECTOR_CACHE = new Vector3f();
/*    */   
/*    */   public HierarchicalModel() {
/* 23 */     this(RenderType::entityCutoutNoCull);
/*    */   }
/*    */   
/*    */   public HierarchicalModel(Function<ResourceLocation, RenderType> $$0) {
/* 27 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 32 */     root().render($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Optional<ModelPart> getAnyDescendantWithName(String $$0) {
/* 38 */     if ($$0.equals("root")) {
/* 39 */       return Optional.of(root());
/*    */     }
/* 41 */     return root().getAllParts().filter($$1 -> $$1.hasChild($$0)).findFirst().map($$1 -> $$1.getChild($$0));
/*    */   }
/*    */   
/*    */   protected void animate(AnimationState $$0, AnimationDefinition $$1, float $$2) {
/* 45 */     animate($$0, $$1, $$2, 1.0F);
/*    */   }
/*    */   
/*    */   protected void animateWalk(AnimationDefinition $$0, float $$1, float $$2, float $$3, float $$4) {
/* 49 */     long $$5 = (long)($$1 * 50.0F * $$3);
/* 50 */     float $$6 = Math.min($$2 * $$4, 1.0F);
/* 51 */     KeyframeAnimations.animate(this, $$0, $$5, $$6, ANIMATION_VECTOR_CACHE);
/*    */   }
/*    */   
/*    */   protected void animate(AnimationState $$0, AnimationDefinition $$1, float $$2, float $$3) {
/* 55 */     $$0.updateTime($$2, $$3);
/* 56 */     $$0.ifStarted($$1 -> KeyframeAnimations.animate(this, $$0, $$1.getAccumulatedTime(), 1.0F, ANIMATION_VECTOR_CACHE));
/*    */   }
/*    */   
/*    */   protected void applyStatic(AnimationDefinition $$0) {
/* 60 */     KeyframeAnimations.animate(this, $$0, 0L, 1.0F, ANIMATION_VECTOR_CACHE);
/*    */   }
/*    */   
/*    */   public abstract ModelPart root();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HierarchicalModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */