/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.monster.Slime;
/*    */ 
/*    */ public class LavaSlimeModel<T extends Slime> extends HierarchicalModel<T> {
/*    */   private static final int SEGMENT_COUNT = 8;
/*    */   private final ModelPart root;
/* 17 */   private final ModelPart[] bodyCubes = new ModelPart[8];
/*    */   
/*    */   public LavaSlimeModel(ModelPart $$0) {
/* 20 */     this.root = $$0;
/* 21 */     Arrays.setAll(this.bodyCubes, $$1 -> $$0.getChild(getSegmentName($$1)));
/*    */   }
/*    */   
/*    */   private static String getSegmentName(int $$0) {
/* 25 */     return "cube" + $$0;
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 29 */     MeshDefinition $$0 = new MeshDefinition();
/* 30 */     PartDefinition $$1 = $$0.getRoot();
/*    */     
/* 32 */     for (int $$2 = 0; $$2 < 8; $$2++) {
/* 33 */       int $$3 = 0;
/* 34 */       int $$4 = $$2;
/* 35 */       if ($$2 == 2) {
/* 36 */         $$3 = 24;
/* 37 */         $$4 = 10;
/* 38 */       } else if ($$2 == 3) {
/* 39 */         $$3 = 24;
/* 40 */         $$4 = 19;
/*    */       } 
/* 42 */       $$1.addOrReplaceChild(getSegmentName($$2), 
/* 43 */           CubeListBuilder.create()
/* 44 */           .texOffs($$3, $$4).addBox(-4.0F, (16 + $$2), -4.0F, 8.0F, 1.0F, 8.0F), PartPose.ZERO);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 49 */     $$1.addOrReplaceChild("inside_cube", 
/* 50 */         CubeListBuilder.create()
/* 51 */         .texOffs(0, 16).addBox(-2.0F, 18.0F, -2.0F, 4.0F, 4.0F, 4.0F), PartPose.ZERO);
/*    */ 
/*    */     
/* 54 */     return LayerDefinition.create($$0, 64, 32);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*    */ 
/*    */   
/*    */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 63 */     float $$4 = Mth.lerp($$3, ((Slime)$$0).oSquish, ((Slime)$$0).squish);
/* 64 */     if ($$4 < 0.0F) {
/* 65 */       $$4 = 0.0F;
/*    */     }
/*    */     
/* 68 */     for (int $$5 = 0; $$5 < this.bodyCubes.length; $$5++) {
/* 69 */       (this.bodyCubes[$$5]).y = -(4 - $$5) * $$4 * 1.7F;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 75 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\LavaSlimeModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */