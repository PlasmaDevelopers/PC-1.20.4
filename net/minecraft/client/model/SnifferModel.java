/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.animation.definitions.SnifferAnimation;
/*    */ import net.minecraft.client.model.geom.ModelPart;
/*    */ import net.minecraft.client.model.geom.PartPose;
/*    */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*    */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*    */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*    */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*    */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.animal.sniffer.Sniffer;
/*    */ 
/*    */ 
/*    */ public class SnifferModel<T extends Sniffer>
/*    */   extends AgeableHierarchicalModel<T>
/*    */ {
/*    */   private static final float WALK_ANIMATION_SPEED_MAX = 9.0F;
/*    */   private static final float WALK_ANIMATION_SCALE_FACTOR = 100.0F;
/*    */   private final ModelPart root;
/*    */   private final ModelPart head;
/*    */   
/*    */   public SnifferModel(ModelPart $$0) {
/* 24 */     super(0.5F, 24.0F);
/* 25 */     this.root = $$0.getChild("root");
/* 26 */     this
/*    */ 
/*    */       
/* 29 */       .head = this.root.getChild("bone").getChild("body").getChild("head");
/*    */   }
/*    */   
/*    */   public static LayerDefinition createBodyLayer() {
/* 33 */     MeshDefinition $$0 = new MeshDefinition();
/* 34 */     PartDefinition $$1 = $$0.getRoot().addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 5.0F, 0.0F));
/*    */     
/* 36 */     PartDefinition $$2 = $$1.addOrReplaceChild("bone", CubeListBuilder.create(), 
/* 37 */         PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 39 */     PartDefinition $$3 = $$2.addOrReplaceChild("body", CubeListBuilder.create()
/* 40 */         .texOffs(62, 68).addBox(-12.5F, -14.0F, -20.0F, 25.0F, 29.0F, 40.0F, new CubeDeformation(0.0F))
/* 41 */         .texOffs(62, 0).addBox(-12.5F, -14.0F, -20.0F, 25.0F, 24.0F, 40.0F, new CubeDeformation(0.5F))
/* 42 */         .texOffs(87, 68).addBox(-12.5F, 12.0F, -20.0F, 25.0F, 0.0F, 40.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
/*    */     
/* 44 */     $$2.addOrReplaceChild("right_front_leg", CubeListBuilder.create()
/* 45 */         .texOffs(32, 87).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 10.0F, -15.0F));
/* 46 */     $$2.addOrReplaceChild("right_mid_leg", CubeListBuilder.create()
/* 47 */         .texOffs(32, 105).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 10.0F, 0.0F));
/* 48 */     $$2.addOrReplaceChild("right_hind_leg", CubeListBuilder.create()
/* 49 */         .texOffs(32, 123).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-7.5F, 10.0F, 15.0F));
/* 50 */     $$2.addOrReplaceChild("left_front_leg", CubeListBuilder.create()
/* 51 */         .texOffs(0, 87).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 10.0F, -15.0F));
/* 52 */     $$2.addOrReplaceChild("left_mid_leg", CubeListBuilder.create()
/* 53 */         .texOffs(0, 105).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 10.0F, 0.0F));
/* 54 */     $$2.addOrReplaceChild("left_hind_leg", CubeListBuilder.create()
/* 55 */         .texOffs(0, 123).addBox(-3.5F, -1.0F, -4.0F, 7.0F, 10.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(7.5F, 10.0F, 15.0F));
/*    */     
/* 57 */     PartDefinition $$4 = $$3.addOrReplaceChild("head", CubeListBuilder.create()
/* 58 */         .texOffs(8, 15).addBox(-6.5F, -7.5F, -11.5F, 13.0F, 18.0F, 11.0F, new CubeDeformation(0.0F))
/* 59 */         .texOffs(8, 4).addBox(-6.5F, 7.5F, -11.5F, 13.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.5F, -19.48F));
/*    */     
/* 61 */     $$4.addOrReplaceChild("left_ear", CubeListBuilder.create()
/* 62 */         .texOffs(2, 0).addBox(0.0F, 0.0F, -3.0F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(6.51F, -7.5F, -4.51F));
/* 63 */     $$4.addOrReplaceChild("right_ear", CubeListBuilder.create()
/* 64 */         .texOffs(48, 0).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 19.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.51F, -7.5F, -4.51F));
/* 65 */     $$4.addOrReplaceChild("nose", CubeListBuilder.create()
/* 66 */         .texOffs(10, 45).addBox(-6.5F, -2.0F, -9.0F, 13.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.5F, -11.5F));
/* 67 */     $$4.addOrReplaceChild("lower_beak", CubeListBuilder.create()
/* 68 */         .texOffs(10, 57).addBox(-6.5F, -7.0F, -8.0F, 13.0F, 12.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.5F, -12.5F));
/*    */     
/* 70 */     return LayerDefinition.create($$0, 192, 192);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 75 */     root().getAllParts().forEach(ModelPart::resetPose);
/*    */     
/* 77 */     this.head.xRot = $$5 * 0.017453292F;
/* 78 */     this.head.yRot = $$4 * 0.017453292F;
/*    */     
/* 80 */     if ($$0.isSearching()) {
/* 81 */       animateWalk(SnifferAnimation.SNIFFER_SNIFF_SEARCH, $$1, $$2, 9.0F, 100.0F);
/*    */     } else {
/* 83 */       animateWalk(SnifferAnimation.SNIFFER_WALK, $$1, $$2, 9.0F, 100.0F);
/*    */     } 
/*    */     
/* 86 */     animate(((Sniffer)$$0).diggingAnimationState, SnifferAnimation.SNIFFER_DIG, $$3);
/* 87 */     animate(((Sniffer)$$0).sniffingAnimationState, SnifferAnimation.SNIFFER_LONGSNIFF, $$3);
/* 88 */     animate(((Sniffer)$$0).risingAnimationState, SnifferAnimation.SNIFFER_STAND_UP, $$3);
/* 89 */     animate(((Sniffer)$$0).feelingHappyAnimationState, SnifferAnimation.SNIFFER_HAPPY, $$3);
/* 90 */     animate(((Sniffer)$$0).scentingAnimationState, SnifferAnimation.SNIFFER_SNIFFSNIFF, $$3);
/*    */     
/* 92 */     if (this.young) {
/* 93 */       applyStatic(SnifferAnimation.BABY_TRANSFORM);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPart root() {
/* 99 */     return this.root;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\SnifferModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */