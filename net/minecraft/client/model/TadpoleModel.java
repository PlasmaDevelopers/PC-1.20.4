/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.frog.Tadpole;
/*    */ 
/*    */ public class TadpoleModel<T extends Tadpole> extends AgeableListModel<T> {
/*    */   private final ModelPart root;
/*    */   private final ModelPart tail;
/*    */   
/*    */   public TadpoleModel(ModelPart $$0) {
/* 19 */     super(true, 8.0F, 3.35F);
/* 20 */     this.root = $$0;
/* 21 */     this.tail = $$0.getChild("tail");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 25 */     MeshDefinition $$0 = new MeshDefinition();
/* 26 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 28 */     float $$2 = 0.0F;
/* 29 */     float $$3 = 22.0F;
/* 30 */     float $$4 = -3.0F;
/* 31 */     $$1.addOrReplaceChild("body", 
/* 32 */         CubeListBuilder.create()
/* 33 */         .texOffs(0, 0).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 3.0F), 
/* 34 */         PartPose.offset(0.0F, 22.0F, -3.0F));
/*    */     
/* 36 */     $$1.addOrReplaceChild("tail", 
/* 37 */         CubeListBuilder.create()
/* 38 */         .texOffs(0, 0).addBox(0.0F, -1.0F, 0.0F, 0.0F, 2.0F, 7.0F), 
/* 39 */         PartPose.offset(0.0F, 22.0F, 0.0F));
/*    */ 
/*    */     
/* 42 */     return LayerDefinition.create($$0, 16, 16);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> headParts() {
/* 47 */     return (Iterable<ModelPart>)ImmutableList.of(this.root);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Iterable<ModelPart> bodyParts() {
/* 52 */     return (Iterable<ModelPart>)ImmutableList.of(this.tail);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 57 */     float $$6 = $$0.isInWater() ? 1.0F : 1.5F;
/* 58 */     this.tail.yRot = -$$6 * 0.25F * Mth.sin(0.3F * $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\TadpoleModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */