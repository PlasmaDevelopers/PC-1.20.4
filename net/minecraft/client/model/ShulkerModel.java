/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Shulker;
/*    */ 
/*    */ public class ShulkerModel<T extends Shulker>
/*    */   extends ListModel<T> {
/*    */   private static final String LID = "lid";
/*    */   private static final String BASE = "base";
/*    */   private final ModelPart base;
/*    */   private final ModelPart lid;
/*    */   private final ModelPart head;
/*    */   
/*    */   public ShulkerModel(ModelPart $$0) {
/* 24 */     super(RenderType::entityCutoutNoCullZOffset);
/* 25 */     this.lid = $$0.getChild("lid");
/* 26 */     this.base = $$0.getChild("base");
/* 27 */     this.head = $$0.getChild("head");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 31 */     MeshDefinition $$0 = new MeshDefinition();
/* 32 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 34 */     $$1.addOrReplaceChild("lid", 
/* 35 */         CubeListBuilder.create()
/* 36 */         .texOffs(0, 0).addBox(-8.0F, -16.0F, -8.0F, 16.0F, 12.0F, 16.0F), 
/* 37 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*    */     
/* 39 */     $$1.addOrReplaceChild("base", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(0, 28).addBox(-8.0F, -8.0F, -8.0F, 16.0F, 8.0F, 16.0F), 
/* 42 */         PartPose.offset(0.0F, 24.0F, 0.0F));
/*    */     
/* 44 */     $$1.addOrReplaceChild("head", 
/* 45 */         CubeListBuilder.create()
/* 46 */         .texOffs(0, 52).addBox(-3.0F, 0.0F, -3.0F, 6.0F, 6.0F, 6.0F), 
/* 47 */         PartPose.offset(0.0F, 12.0F, 0.0F));
/*    */ 
/*    */     
/* 50 */     return LayerDefinition.create($$0, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 55 */     float $$6 = $$3 - ((Shulker)$$0).tickCount;
/* 56 */     float $$7 = (0.5F + $$0.getClientPeekAmount($$6)) * 3.1415927F;
/* 57 */     float $$8 = -1.0F + Mth.sin($$7);
/* 58 */     float $$9 = 0.0F;
/* 59 */     if ($$7 > 3.1415927F) {
/* 60 */       $$9 = Mth.sin($$3 * 0.1F) * 0.7F;
/*    */     }
/* 62 */     this.lid.setPos(0.0F, 16.0F + Mth.sin($$7) * 8.0F + $$9, 0.0F);
/*    */ 
/*    */     
/* 65 */     if ($$0.getClientPeekAmount($$6) > 0.3F) {
/* 66 */       this.lid.yRot = $$8 * $$8 * $$8 * $$8 * 3.1415927F * 0.125F;
/*    */     } else {
/* 68 */       this.lid.yRot = 0.0F;
/*    */     } 
/*    */     
/* 71 */     this.head.xRot = $$5 * 0.017453292F;
/* 72 */     this.head.yRot = (((Shulker)$$0).yHeadRot - 180.0F - ((Shulker)$$0).yBodyRot) * 0.017453292F;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterable<ModelPart> parts() {
/* 77 */     return (Iterable<ModelPart>)ImmutableList.of(this.base, this.lid);
/*    */   }
/*    */   
/*    */   public ModelPart getLid() {
/* 81 */     return this.lid;
/*    */   }
/*    */   
/*    */   public ModelPart getHead() {
/* 85 */     return this.head;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ShulkerModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */