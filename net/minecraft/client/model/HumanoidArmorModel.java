/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public class HumanoidArmorModel<T extends LivingEntity>
/*    */   extends HumanoidModel<T> {
/*    */   public HumanoidArmorModel(ModelPart $$0) {
/* 14 */     super($$0);
/*    */   }
/*    */   
/*    */   public static MeshDefinition createBodyLayer(CubeDeformation $$0) {
/* 18 */     MeshDefinition $$1 = HumanoidModel.createMesh($$0, 0.0F);
/* 19 */     PartDefinition $$2 = $$1.getRoot();
/* 20 */     $$2.addOrReplaceChild("right_leg", 
/* 21 */         CubeListBuilder.create()
/* 22 */         .texOffs(0, 16).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(-0.1F)), 
/* 23 */         PartPose.offset(-1.9F, 12.0F, 0.0F));
/*    */     
/* 25 */     $$2.addOrReplaceChild("left_leg", 
/* 26 */         CubeListBuilder.create()
/* 27 */         .texOffs(0, 16).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, $$0.extend(-0.1F)), 
/* 28 */         PartPose.offset(1.9F, 12.0F, 0.0F));
/*    */     
/* 30 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\HumanoidArmorModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */