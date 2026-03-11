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
/*    */ public class ShulkerBulletModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/*    */   private static final String MAIN = "main";
/*    */   private final ModelPart root;
/*    */   private final ModelPart main;
/*    */   
/*    */   public ShulkerBulletModel(ModelPart $$0) {
/* 19 */     this.root = $$0;
/* 20 */     this.main = $$0.getChild("main");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 24 */     MeshDefinition $$0 = new MeshDefinition();
/* 25 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 27 */     $$1.addOrReplaceChild("main", 
/* 28 */         CubeListBuilder.create()
/* 29 */         .texOffs(0, 0).addBox(-4.0F, -4.0F, -1.0F, 8.0F, 8.0F, 2.0F)
/* 30 */         .texOffs(0, 10).addBox(-1.0F, -4.0F, -4.0F, 2.0F, 8.0F, 8.0F)
/* 31 */         .texOffs(20, 0).addBox(-4.0F, -1.0F, -4.0F, 8.0F, 2.0F, 8.0F), PartPose.ZERO);
/*    */ 
/*    */ 
/*    */     
/* 35 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 40 */     return this.root;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 45 */     this.main.yRot = $$4 * 0.017453292F;
/* 46 */     this.main.xRot = $$5 * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ShulkerBulletModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */