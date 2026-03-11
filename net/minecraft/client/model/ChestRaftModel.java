/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ 
/*    */ public class ChestRaftModel
/*    */   extends RaftModel {
/*    */   private static final String CHEST_BOTTOM = "chest_bottom";
/*    */   private static final String CHEST_LID = "chest_lid";
/*    */   private static final String CHEST_LOCK = "chest_lock";
/*    */   
/*    */   public ChestRaftModel(ModelPart $$0) {
/* 18 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart $$0) {
/* 23 */     ImmutableList.Builder<ModelPart> $$1 = super.createPartsBuilder($$0);
/* 24 */     $$1.add($$0.getChild("chest_bottom"));
/* 25 */     $$1.add($$0.getChild("chest_lid"));
/* 26 */     $$1.add($$0.getChild("chest_lock"));
/* 27 */     return $$1;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyModel() {
/* 31 */     MeshDefinition $$0 = new MeshDefinition();
/* 32 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 34 */     RaftModel.createChildren($$1);
/*    */     
/* 36 */     $$1.addOrReplaceChild("chest_bottom", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F), 
/* 39 */         PartPose.offsetAndRotation(-2.0F, -10.1F, -6.0F, 0.0F, -1.5707964F, 0.0F));
/*    */ 
/*    */     
/* 42 */     $$1.addOrReplaceChild("chest_lid", 
/* 43 */         CubeListBuilder.create()
/* 44 */         .texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F), 
/* 45 */         PartPose.offsetAndRotation(-2.0F, -14.1F, -6.0F, 0.0F, -1.5707964F, 0.0F));
/*    */ 
/*    */     
/* 48 */     $$1.addOrReplaceChild("chest_lock", 
/* 49 */         CubeListBuilder.create()
/* 50 */         .texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F), 
/* 51 */         PartPose.offsetAndRotation(-1.0F, -11.1F, -1.0F, 0.0F, -1.5707964F, 0.0F));
/*    */ 
/*    */     
/* 54 */     return LayerDefinition.create($$0, 128, 128);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ChestRaftModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */