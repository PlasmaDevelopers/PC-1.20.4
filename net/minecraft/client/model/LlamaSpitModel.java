/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class LlamaSpitModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private static final String MAIN = "main";
/*    */   private final ModelPart root;
/*    */   
/*    */   public LlamaSpitModel(ModelPart $$0) {
/* 17 */     this.root = $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 21 */     MeshDefinition $$0 = new MeshDefinition();
/* 22 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 24 */     int $$2 = 2;
/* 25 */     $$1.addOrReplaceChild("main", 
/* 26 */         CubeListBuilder.create()
/* 27 */         .texOffs(0, 0)
/* 28 */         .addBox(-4.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F)
/* 29 */         .addBox(0.0F, -4.0F, 0.0F, 2.0F, 2.0F, 2.0F)
/* 30 */         .addBox(0.0F, 0.0F, -4.0F, 2.0F, 2.0F, 2.0F)
/*    */         
/* 32 */         .addBox(0.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F)
/* 33 */         .addBox(2.0F, 0.0F, 0.0F, 2.0F, 2.0F, 2.0F)
/* 34 */         .addBox(0.0F, 2.0F, 0.0F, 2.0F, 2.0F, 2.0F)
/* 35 */         .addBox(0.0F, 0.0F, 2.0F, 2.0F, 2.0F, 2.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 38 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 47 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\LlamaSpitModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */