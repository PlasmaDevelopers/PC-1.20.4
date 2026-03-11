/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class DolphinModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/*    */   private final ModelPart root;
/*    */   private final ModelPart body;
/*    */   private final ModelPart tail;
/*    */   private final ModelPart tailFin;
/*    */   
/*    */   public DolphinModel(ModelPart $$0) {
/* 21 */     this.root = $$0;
/* 22 */     this.body = $$0.getChild("body");
/* 23 */     this.tail = this.body.getChild("tail");
/* 24 */     this.tailFin = this.tail.getChild("tail_fin");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 28 */     MeshDefinition $$0 = new MeshDefinition();
/* 29 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 31 */     float $$2 = 18.0F;
/* 32 */     float $$3 = -8.0F;
/*    */     
/* 34 */     PartDefinition $$4 = $$1.addOrReplaceChild("body", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(22, 0).addBox(-4.0F, -7.0F, 0.0F, 8.0F, 7.0F, 13.0F), 
/* 37 */         PartPose.offset(0.0F, 22.0F, -5.0F));
/*    */     
/* 39 */     $$4.addOrReplaceChild("back_fin", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(51, 0).addBox(-0.5F, 0.0F, 8.0F, 1.0F, 4.0F, 5.0F), 
/* 42 */         PartPose.rotation(1.0471976F, 0.0F, 0.0F));
/*    */     
/* 44 */     $$4.addOrReplaceChild("left_fin", 
/* 45 */         CubeListBuilder.create()
/* 46 */         .texOffs(48, 20).mirror().addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F), 
/* 47 */         PartPose.offsetAndRotation(2.0F, -2.0F, 4.0F, 1.0471976F, 0.0F, 2.0943952F));
/*    */     
/* 49 */     $$4.addOrReplaceChild("right_fin", 
/* 50 */         CubeListBuilder.create()
/* 51 */         .texOffs(48, 20).addBox(-0.5F, -4.0F, 0.0F, 1.0F, 4.0F, 7.0F), 
/* 52 */         PartPose.offsetAndRotation(-2.0F, -2.0F, 4.0F, 1.0471976F, 0.0F, -2.0943952F));
/*    */     
/* 54 */     PartDefinition $$5 = $$4.addOrReplaceChild("tail", 
/* 55 */         CubeListBuilder.create()
/* 56 */         .texOffs(0, 19).addBox(-2.0F, -2.5F, 0.0F, 4.0F, 5.0F, 11.0F), 
/* 57 */         PartPose.offsetAndRotation(0.0F, -2.5F, 11.0F, -0.10471976F, 0.0F, 0.0F));
/*    */     
/* 59 */     $$5.addOrReplaceChild("tail_fin", 
/* 60 */         CubeListBuilder.create()
/* 61 */         .texOffs(19, 20).addBox(-5.0F, -0.5F, 0.0F, 10.0F, 1.0F, 6.0F), 
/* 62 */         PartPose.offset(0.0F, 0.0F, 9.0F));
/*    */     
/* 64 */     PartDefinition $$6 = $$4.addOrReplaceChild("head", 
/* 65 */         CubeListBuilder.create()
/* 66 */         .texOffs(0, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 6.0F), 
/* 67 */         PartPose.offset(0.0F, -4.0F, -3.0F));
/*    */     
/* 69 */     $$6.addOrReplaceChild("nose", 
/* 70 */         CubeListBuilder.create()
/* 71 */         .texOffs(0, 13).addBox(-1.0F, 2.0F, -7.0F, 2.0F, 2.0F, 4.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 75 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 80 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 85 */     this.body.xRot = $$5 * 0.017453292F;
/* 86 */     this.body.yRot = $$4 * 0.017453292F;
/*    */     
/* 88 */     if ($$0.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D) {
/* 89 */       this.body.xRot += -0.05F - 0.05F * Mth.cos($$3 * 0.3F);
/* 90 */       this.tail.xRot = -0.1F * Mth.cos($$3 * 0.3F);
/* 91 */       this.tailFin.xRot = -0.2F * Mth.cos($$3 * 0.3F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\DolphinModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */