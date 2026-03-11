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
/*    */ public class EvokerFangsModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private static final String BASE = "base";
/*    */   private static final String UPPER_JAW = "upper_jaw";
/*    */   private static final String LOWER_JAW = "lower_jaw";
/*    */   private final ModelPart root;
/*    */   private final ModelPart base;
/*    */   private final ModelPart upperJaw;
/*    */   private final ModelPart lowerJaw;
/*    */   
/*    */   public EvokerFangsModel(ModelPart $$0) {
/* 23 */     this.root = $$0;
/* 24 */     this.base = $$0.getChild("base");
/* 25 */     this.upperJaw = $$0.getChild("upper_jaw");
/* 26 */     this.lowerJaw = $$0.getChild("lower_jaw");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 30 */     MeshDefinition $$0 = new MeshDefinition();
/* 31 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 33 */     $$1.addOrReplaceChild("base", 
/* 34 */         CubeListBuilder.create()
/* 35 */         .texOffs(0, 0).addBox(0.0F, 0.0F, 0.0F, 10.0F, 12.0F, 10.0F), 
/* 36 */         PartPose.offset(-5.0F, 24.0F, -5.0F));
/*    */ 
/*    */     
/* 39 */     CubeListBuilder $$2 = CubeListBuilder.create().texOffs(40, 0).addBox(0.0F, 0.0F, 0.0F, 4.0F, 14.0F, 8.0F);
/* 40 */     $$1.addOrReplaceChild("upper_jaw", $$2, PartPose.offset(1.5F, 24.0F, -4.0F));
/* 41 */     $$1.addOrReplaceChild("lower_jaw", $$2, PartPose.offsetAndRotation(-1.5F, 24.0F, 4.0F, 0.0F, 3.1415927F, 0.0F));
/*    */     
/* 43 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 48 */     float $$6 = $$1 * 2.0F;
/* 49 */     if ($$6 > 1.0F) {
/* 50 */       $$6 = 1.0F;
/*    */     }
/* 52 */     $$6 = 1.0F - $$6 * $$6 * $$6;
/* 53 */     this.upperJaw.zRot = 3.1415927F - $$6 * 0.35F * 3.1415927F;
/* 54 */     this.lowerJaw.zRot = 3.1415927F + $$6 * 0.35F * 3.1415927F;
/*    */     
/* 56 */     float $$7 = ($$1 + Mth.sin($$1 * 2.7F)) * 0.6F * 12.0F;
/* 57 */     this.upperJaw.y = 24.0F - $$7;
/* 58 */     this.lowerJaw.y = this.upperJaw.y;
/* 59 */     this.base.y = this.upperJaw.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 64 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\EvokerFangsModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */