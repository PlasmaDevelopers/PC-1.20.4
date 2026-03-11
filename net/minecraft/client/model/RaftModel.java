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
/*    */ import net.minecraft.world.entity.vehicle.Boat;
/*    */ 
/*    */ public class RaftModel extends ListModel<Boat> {
/*    */   private static final String LEFT_PADDLE = "left_paddle";
/*    */   private static final String RIGHT_PADDLE = "right_paddle";
/*    */   private static final String BOTTOM = "bottom";
/*    */   private final ModelPart leftPaddle;
/*    */   private final ModelPart rightPaddle;
/*    */   private final ImmutableList<ModelPart> parts;
/*    */   
/*    */   public RaftModel(ModelPart $$0) {
/* 23 */     this.leftPaddle = $$0.getChild("left_paddle");
/* 24 */     this.rightPaddle = $$0.getChild("right_paddle");
/* 25 */     this.parts = createPartsBuilder($$0).build();
/*    */   }
/*    */   
/*    */   protected ImmutableList.Builder<ModelPart> createPartsBuilder(ModelPart $$0) {
/* 29 */     ImmutableList.Builder<ModelPart> $$1 = new ImmutableList.Builder();
/* 30 */     $$1.add((Object[])new ModelPart[] { $$0
/* 31 */           .getChild("bottom"), this.leftPaddle, this.rightPaddle });
/*    */ 
/*    */ 
/*    */     
/* 35 */     return $$1;
/*    */   }
/*    */   
/*    */   public static void createChildren(PartDefinition $$0) {
/* 39 */     $$0.addOrReplaceChild("bottom", 
/* 40 */         CubeListBuilder.create()
/* 41 */         .texOffs(0, 0).addBox(-14.0F, -11.0F, -4.0F, 28.0F, 20.0F, 4.0F)
/* 42 */         .texOffs(0, 0).addBox(-14.0F, -9.0F, -8.0F, 28.0F, 16.0F, 4.0F), 
/* 43 */         PartPose.offsetAndRotation(0.0F, -2.1F, 1.0F, 1.5708F, 0.0F, 0.0F));
/*    */ 
/*    */     
/* 46 */     int $$1 = 20;
/* 47 */     int $$2 = 7;
/* 48 */     int $$3 = 6;
/* 49 */     float $$4 = -5.0F;
/*    */     
/* 51 */     $$0.addOrReplaceChild("left_paddle", 
/* 52 */         CubeListBuilder.create()
/* 53 */         .texOffs(0, 24)
/* 54 */         .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
/* 55 */         .addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
/* 56 */         PartPose.offsetAndRotation(3.0F, -4.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
/*    */     
/* 58 */     $$0.addOrReplaceChild("right_paddle", 
/* 59 */         CubeListBuilder.create()
/* 60 */         .texOffs(40, 24)
/* 61 */         .addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F)
/* 62 */         .addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), 
/* 63 */         PartPose.offsetAndRotation(3.0F, -4.0F, -9.0F, 0.0F, 3.1415927F, 0.19634955F));
/*    */   }
/*    */ 
/*    */   
/*    */   public static LayerDefinition createBodyModel() {
/* 68 */     MeshDefinition $$0 = new MeshDefinition();
/* 69 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 71 */     createChildren($$1);
/*    */     
/* 73 */     return LayerDefinition.create($$0, 128, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(Boat $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 78 */     animatePaddle($$0, 0, this.leftPaddle, $$1);
/* 79 */     animatePaddle($$0, 1, this.rightPaddle, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ImmutableList<ModelPart> parts() {
/* 84 */     return this.parts;
/*    */   }
/*    */   
/*    */   private static void animatePaddle(Boat $$0, int $$1, ModelPart $$2, float $$3) {
/* 88 */     float $$4 = $$0.getRowingTime($$1, $$3);
/*    */     
/* 90 */     $$2.xRot = Mth.clampedLerp(-1.0471976F, -0.2617994F, (Mth.sin(-$$4) + 1.0F) / 2.0F);
/* 91 */     $$2.yRot = Mth.clampedLerp(-0.7853982F, 0.7853982F, (Mth.sin(-$$4 + 1.0F) + 1.0F) / 2.0F);
/*    */     
/* 93 */     if ($$1 == 1)
/* 94 */       $$2.yRot = 3.1415927F - $$2.yRot; 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\RaftModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */