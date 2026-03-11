/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class SquidModel<T extends Entity>
/*    */   extends HierarchicalModel<T>
/*    */ {
/* 16 */   private final ModelPart[] tentacles = new ModelPart[8];
/*    */   private final ModelPart root;
/*    */   
/*    */   public SquidModel(ModelPart $$0) {
/* 20 */     this.root = $$0;
/* 21 */     Arrays.setAll(this.tentacles, $$1 -> $$0.getChild(createTentacleName($$1)));
/*    */   }
/*    */   
/*    */   private static String createTentacleName(int $$0) {
/* 25 */     return "tentacle" + $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 29 */     MeshDefinition $$0 = new MeshDefinition();
/* 30 */     PartDefinition $$1 = $$0.getRoot();
/* 31 */     CubeDeformation $$2 = new CubeDeformation(0.02F);
/*    */     
/* 33 */     int $$3 = -16;
/* 34 */     $$1.addOrReplaceChild("body", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(0, 0).addBox(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F, $$2), 
/* 37 */         PartPose.offset(0.0F, 8.0F, 0.0F));
/*    */ 
/*    */     
/* 40 */     int $$4 = 8;
/*    */     
/* 42 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F);
/*    */     
/* 44 */     for (int $$6 = 0; $$6 < 8; $$6++) {
/* 45 */       double $$7 = $$6 * Math.PI * 2.0D / 8.0D;
/* 46 */       float $$8 = (float)Math.cos($$7) * 5.0F;
/* 47 */       float $$9 = 15.0F;
/* 48 */       float $$10 = (float)Math.sin($$7) * 5.0F;
/*    */       
/* 50 */       $$7 = $$6 * Math.PI * -2.0D / 8.0D + 1.5707963267948966D;
/* 51 */       float $$11 = (float)$$7;
/*    */       
/* 53 */       $$1.addOrReplaceChild(createTentacleName($$6), $$5, PartPose.offsetAndRotation($$8, 15.0F, $$10, 0.0F, $$11, 0.0F));
/*    */     } 
/*    */     
/* 56 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 61 */     for (ModelPart $$6 : this.tentacles)
/*    */     {
/* 63 */       $$6.xRot = $$3;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 69 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SquidModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */