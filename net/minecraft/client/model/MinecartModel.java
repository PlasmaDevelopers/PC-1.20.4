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
/*    */ public class MinecartModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   
/*    */   public MinecartModel(ModelPart $$0) {
/* 16 */     this.root = $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 20 */     MeshDefinition $$0 = new MeshDefinition();
/* 21 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 23 */     int $$2 = 20;
/* 24 */     int $$3 = 8;
/* 25 */     int $$4 = 16;
/* 26 */     int $$5 = 4;
/*    */     
/* 28 */     $$1.addOrReplaceChild("bottom", 
/* 29 */         CubeListBuilder.create()
/* 30 */         .texOffs(0, 10).addBox(-10.0F, -8.0F, -1.0F, 20.0F, 16.0F, 2.0F), 
/* 31 */         PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 1.5707964F, 0.0F, 0.0F));
/*    */ 
/*    */ 
/*    */     
/* 35 */     $$1.addOrReplaceChild("front", 
/* 36 */         CubeListBuilder.create()
/* 37 */         .texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), 
/* 38 */         PartPose.offsetAndRotation(-9.0F, 4.0F, 0.0F, 0.0F, 4.712389F, 0.0F));
/*    */     
/* 40 */     $$1.addOrReplaceChild("back", 
/* 41 */         CubeListBuilder.create()
/* 42 */         .texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), 
/* 43 */         PartPose.offsetAndRotation(9.0F, 4.0F, 0.0F, 0.0F, 1.5707964F, 0.0F));
/*    */     
/* 45 */     $$1.addOrReplaceChild("left", 
/* 46 */         CubeListBuilder.create()
/* 47 */         .texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), 
/* 48 */         PartPose.offsetAndRotation(0.0F, 4.0F, -7.0F, 0.0F, 3.1415927F, 0.0F));
/*    */     
/* 50 */     $$1.addOrReplaceChild("right", 
/* 51 */         CubeListBuilder.create()
/* 52 */         .texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 16.0F, 8.0F, 2.0F), 
/* 53 */         PartPose.offset(0.0F, 4.0F, 7.0F));
/*    */ 
/*    */     
/* 56 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 65 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\MinecartModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */