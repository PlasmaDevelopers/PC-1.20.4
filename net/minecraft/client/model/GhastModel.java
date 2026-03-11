/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ public class GhastModel<T extends Entity>
/*    */   extends HierarchicalModel<T> {
/*    */   private final ModelPart root;
/* 16 */   private final ModelPart[] tentacles = new ModelPart[9];
/*    */   
/*    */   public GhastModel(ModelPart $$0) {
/* 19 */     this.root = $$0;
/*    */     
/* 21 */     for (int $$1 = 0; $$1 < this.tentacles.length; $$1++) {
/* 22 */       this.tentacles[$$1] = $$0.getChild(createTentacleName($$1));
/*    */     }
/*    */   }
/*    */   
/*    */   private static String createTentacleName(int $$0) {
/* 27 */     return "tentacle" + $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 31 */     MeshDefinition $$0 = new MeshDefinition();
/* 32 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 34 */     $$1.addOrReplaceChild("body", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(0, 0).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F), 
/* 37 */         PartPose.offset(0.0F, 17.6F, 0.0F));
/*    */ 
/*    */     
/* 40 */     RandomSource $$2 = RandomSource.create(1660L);
/* 41 */     for (int $$3 = 0; $$3 < 9; $$3++) {
/* 42 */       float $$4 = ((($$3 % 3) - ($$3 / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 43 */       float $$5 = (($$3 / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
/* 44 */       int $$6 = $$2.nextInt(7) + 8;
/* 45 */       $$1.addOrReplaceChild(createTentacleName($$3), 
/* 46 */           CubeListBuilder.create()
/* 47 */           .texOffs(0, 0).addBox(-1.0F, 0.0F, -1.0F, 2.0F, $$6, 2.0F), 
/* 48 */           PartPose.offset($$4, 24.6F, $$5));
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 57 */     for (int $$6 = 0; $$6 < this.tentacles.length; $$6++) {
/* 58 */       (this.tentacles[$$6]).xRot = 0.2F * Mth.sin($$3 * 0.3F + $$6) + 0.4F;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 64 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\GhastModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */