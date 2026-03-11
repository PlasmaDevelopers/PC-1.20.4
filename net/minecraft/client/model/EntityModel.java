/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public abstract class EntityModel<T extends Entity>
/*    */   extends Model {
/*    */   public float attackTime;
/*    */   public boolean riding;
/*    */   public boolean young = true;
/*    */   
/*    */   protected EntityModel() {
/* 15 */     this(RenderType::entityCutoutNoCull);
/*    */   }
/*    */   
/*    */   protected EntityModel(Function<ResourceLocation, RenderType> $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract void setupAnim(T paramT, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5);
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {}
/*    */   
/*    */   public void copyPropertiesTo(EntityModel<T> $$0) {
/* 29 */     $$0.attackTime = this.attackTime;
/* 30 */     $$0.riding = this.riding;
/* 31 */     $$0.young = this.young;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\EntityModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */