/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.projectile.WindCharge;
/*    */ 
/*    */ public class WindChargeModel extends HierarchicalModel<WindCharge> {
/*    */   private final ModelPart bone;
/*    */   
/*    */   public WindChargeModel(ModelPart $$0) {
/* 18 */     super(RenderType::entityTranslucent);
/* 19 */     this.bone = $$0.getChild("bone");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 23 */     MeshDefinition $$0 = new MeshDefinition();
/* 24 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 26 */     PartDefinition $$2 = $$1.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 28 */     PartDefinition $$3 = $$2.addOrReplaceChild("projectile", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
/* 29 */     PartDefinition $$4 = $$3.addOrReplaceChild("wind", CubeListBuilder.create().texOffs(20, 112).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
/* 30 */         .texOffs(0, 8).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 32 */     $$4.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(32, 24).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.6F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.5708F, 0.0F, 1.5708F));
/* 33 */     $$4.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 40).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));
/* 34 */     $$3.addOrReplaceChild("wind_charge", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 36 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnim(WindCharge $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 45 */     return this.bone;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\WindChargeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */