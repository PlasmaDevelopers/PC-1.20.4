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
/*    */ import net.minecraft.world.entity.monster.Phantom;
/*    */ 
/*    */ public class PhantomModel<T extends Phantom>
/*    */   extends HierarchicalModel<T> {
/*    */   private static final String TAIL_BASE = "tail_base";
/*    */   private static final String TAIL_TIP = "tail_tip";
/*    */   private final ModelPart root;
/*    */   private final ModelPart leftWingBase;
/*    */   private final ModelPart leftWingTip;
/*    */   private final ModelPart rightWingBase;
/*    */   private final ModelPart rightWingTip;
/*    */   private final ModelPart tailBase;
/*    */   private final ModelPart tailTip;
/*    */   
/*    */   public PhantomModel(ModelPart $$0) {
/* 26 */     this.root = $$0;
/* 27 */     ModelPart $$1 = $$0.getChild("body");
/* 28 */     this.tailBase = $$1.getChild("tail_base");
/* 29 */     this.tailTip = this.tailBase.getChild("tail_tip");
/* 30 */     this.leftWingBase = $$1.getChild("left_wing_base");
/* 31 */     this.leftWingTip = this.leftWingBase.getChild("left_wing_tip");
/* 32 */     this.rightWingBase = $$1.getChild("right_wing_base");
/* 33 */     this.rightWingTip = this.rightWingBase.getChild("right_wing_tip");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 37 */     MeshDefinition $$0 = new MeshDefinition();
/* 38 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 40 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", 
/* 41 */         CubeListBuilder.create()
/* 42 */         .texOffs(0, 8).addBox(-3.0F, -2.0F, -8.0F, 5.0F, 3.0F, 9.0F), 
/* 43 */         PartPose.rotation(-0.1F, 0.0F, 0.0F));
/*    */     
/* 45 */     PartDefinition $$3 = $$2.addOrReplaceChild("tail_base", 
/* 46 */         CubeListBuilder.create()
/* 47 */         .texOffs(3, 20).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 2.0F, 6.0F), 
/* 48 */         PartPose.offset(0.0F, -2.0F, 1.0F));
/*    */     
/* 50 */     $$3.addOrReplaceChild("tail_tip", 
/* 51 */         CubeListBuilder.create()
/* 52 */         .texOffs(4, 29).addBox(-1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 6.0F), 
/* 53 */         PartPose.offset(0.0F, 0.5F, 6.0F));
/*    */     
/* 55 */     PartDefinition $$4 = $$2.addOrReplaceChild("left_wing_base", 
/* 56 */         CubeListBuilder.create()
/* 57 */         .texOffs(23, 12).addBox(0.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F), 
/* 58 */         PartPose.offsetAndRotation(2.0F, -2.0F, -8.0F, 0.0F, 0.0F, 0.1F));
/*    */     
/* 60 */     $$4.addOrReplaceChild("left_wing_tip", 
/* 61 */         CubeListBuilder.create()
/* 62 */         .texOffs(16, 24).addBox(0.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F), 
/* 63 */         PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F));
/*    */     
/* 65 */     PartDefinition $$5 = $$2.addOrReplaceChild("right_wing_base", 
/* 66 */         CubeListBuilder.create()
/* 67 */         .texOffs(23, 12).mirror().addBox(-6.0F, 0.0F, 0.0F, 6.0F, 2.0F, 9.0F), 
/* 68 */         PartPose.offsetAndRotation(-3.0F, -2.0F, -8.0F, 0.0F, 0.0F, -0.1F));
/*    */     
/* 70 */     $$5.addOrReplaceChild("right_wing_tip", 
/* 71 */         CubeListBuilder.create()
/* 72 */         .texOffs(16, 24).mirror().addBox(-13.0F, 0.0F, 0.0F, 13.0F, 1.0F, 9.0F), 
/* 73 */         PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1F));
/*    */     
/* 75 */     $$2.addOrReplaceChild("head", 
/* 76 */         CubeListBuilder.create()
/* 77 */         .texOffs(0, 0).addBox(-4.0F, -2.0F, -5.0F, 7.0F, 3.0F, 5.0F), 
/* 78 */         PartPose.offsetAndRotation(0.0F, 1.0F, -7.0F, 0.2F, 0.0F, 0.0F));
/*    */ 
/*    */     
/* 81 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 86 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 91 */     float $$6 = ($$0.getUniqueFlapTickOffset() + $$3) * 7.448451F * 0.017453292F;
/* 92 */     float $$7 = 16.0F;
/* 93 */     this.leftWingBase.zRot = Mth.cos($$6) * 16.0F * 0.017453292F;
/* 94 */     this.leftWingTip.zRot = Mth.cos($$6) * 16.0F * 0.017453292F;
/* 95 */     this.rightWingBase.zRot = -this.leftWingBase.zRot;
/* 96 */     this.rightWingTip.zRot = -this.leftWingTip.zRot;
/*    */     
/* 98 */     this.tailBase.xRot = -(5.0F + Mth.cos($$6 * 2.0F) * 5.0F) * 0.017453292F;
/* 99 */     this.tailTip.xRot = -(5.0F + Mth.cos($$6 * 2.0F) * 5.0F) * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\PhantomModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */