/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
/*    */ import net.minecraft.world.entity.animal.horse.AbstractHorse;
/*    */ 
/*    */ public class ChestedHorseModel<T extends AbstractChestedHorse> extends HorseModel<T> {
/*    */   private final ModelPart leftChest;
/*    */   private final ModelPart rightChest;
/*    */   
/*    */   public ChestedHorseModel(ModelPart $$0) {
/* 19 */     super($$0);
/* 20 */     this.leftChest = this.body.getChild("left_chest");
/* 21 */     this.rightChest = this.body.getChild("right_chest");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 25 */     MeshDefinition $$0 = HorseModel.createBodyMesh(CubeDeformation.NONE);
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     PartDefinition $$2 = $$1.getChild("body");
/*    */     
/* 30 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(26, 21).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 8.0F, 3.0F);
/* 31 */     $$2.addOrReplaceChild("left_chest", $$3, PartPose.offsetAndRotation(6.0F, -8.0F, 0.0F, 0.0F, -1.5707964F, 0.0F));
/* 32 */     $$2.addOrReplaceChild("right_chest", $$3, PartPose.offsetAndRotation(-6.0F, -8.0F, 0.0F, 0.0F, 1.5707964F, 0.0F));
/*    */     
/* 34 */     PartDefinition $$4 = $$1.getChild("head_parts").getChild("head");
/*    */     
/* 36 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(0, 12).addBox(-1.0F, -7.0F, 0.0F, 2.0F, 7.0F, 1.0F);
/* 37 */     $$4.addOrReplaceChild("left_ear", $$5, PartPose.offsetAndRotation(1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, 0.2617994F));
/* 38 */     $$4.addOrReplaceChild("right_ear", $$5, PartPose.offsetAndRotation(-1.25F, -10.0F, 4.0F, 0.2617994F, 0.0F, -0.2617994F));
/*    */     
/* 40 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 45 */     super.setupAnim($$0, $$1, $$2, $$3, $$4, $$5);
/* 46 */     if ($$0.hasChest()) {
/* 47 */       this.leftChest.visible = true;
/* 48 */       this.rightChest.visible = true;
/*    */     } else {
/* 50 */       this.leftChest.visible = false;
/* 51 */       this.rightChest.visible = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ChestedHorseModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */