/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class TropicalFishModelA<T extends Entity>
/*    */   extends ColorableHierarchicalModel<T> {
/*    */   private final ModelPart root;
/*    */   private final ModelPart tail;
/*    */   
/*    */   public TropicalFishModelA(ModelPart $$0) {
/* 19 */     this.root = $$0;
/* 20 */     this.tail = $$0.getChild("tail");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer(CubeDeformation $$0) {
/* 24 */     MeshDefinition $$1 = new MeshDefinition();
/* 25 */     PartDefinition $$2 = $$1.getRoot();
/*    */     
/* 27 */     int $$3 = 22;
/*    */     
/* 29 */     $$2.addOrReplaceChild("body", 
/* 30 */         CubeListBuilder.create()
/* 31 */         .texOffs(0, 0).addBox(-1.0F, -1.5F, -3.0F, 2.0F, 3.0F, 6.0F, $$0), 
/* 32 */         PartPose.offset(0.0F, 22.0F, 0.0F));
/*    */     
/* 34 */     $$2.addOrReplaceChild("tail", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(22, -6).addBox(0.0F, -1.5F, 0.0F, 0.0F, 3.0F, 6.0F, $$0), 
/* 37 */         PartPose.offset(0.0F, 22.0F, 3.0F));
/*    */     
/* 39 */     $$2.addOrReplaceChild("right_fin", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(2, 16).addBox(-2.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, $$0), 
/* 42 */         PartPose.offsetAndRotation(-1.0F, 22.5F, 0.0F, 0.0F, 0.7853982F, 0.0F));
/*    */     
/* 44 */     $$2.addOrReplaceChild("left_fin", 
/* 45 */         CubeListBuilder.create()
/* 46 */         .texOffs(2, 12).addBox(0.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, $$0), 
/* 47 */         PartPose.offsetAndRotation(1.0F, 22.5F, 0.0F, 0.0F, -0.7853982F, 0.0F));
/*    */     
/* 49 */     $$2.addOrReplaceChild("top_fin", 
/* 50 */         CubeListBuilder.create()
/* 51 */         .texOffs(10, -5).addBox(0.0F, -3.0F, 0.0F, 0.0F, 3.0F, 6.0F, $$0), 
/* 52 */         PartPose.offset(0.0F, 20.5F, -3.0F));
/*    */ 
/*    */     
/* 55 */     return LayerDefinition.create($$1, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 60 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 65 */     float $$6 = 1.0F;
/* 66 */     if (!$$0.isInWater()) {
/* 67 */       $$6 = 1.5F;
/*    */     }
/* 69 */     this.tail.yRot = -$$6 * 0.45F * Mth.sin(0.6F * $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\TropicalFishModelA.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */