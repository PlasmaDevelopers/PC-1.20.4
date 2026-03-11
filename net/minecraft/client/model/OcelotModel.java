/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OcelotModel<T extends Entity>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   private static final int CROUCH_STATE = 0;
/*     */   private static final int WALK_STATE = 1;
/*     */   private static final int SPRINT_STATE = 2;
/*     */   protected static final int SITTING_STATE = 3;
/*     */   private static final float XO = 0.0F;
/*     */   private static final float YO = 16.0F;
/*     */   private static final float ZO = -9.0F;
/*     */   private static final float HEAD_WALK_Y = 15.0F;
/*     */   private static final float HEAD_WALK_Z = -9.0F;
/*     */   private static final float BODY_WALK_Y = 12.0F;
/*     */   private static final float BODY_WALK_Z = -10.0F;
/*     */   private static final float TAIL_1_WALK_Y = 15.0F;
/*     */   private static final float TAIL_1_WALK_Z = 8.0F;
/*     */   private static final float TAIL_2_WALK_Y = 20.0F;
/*     */   private static final float TAIL_2_WALK_Z = 14.0F;
/*     */   protected static final float BACK_LEG_Y = 18.0F;
/*     */   protected static final float BACK_LEG_Z = 5.0F;
/*     */   protected static final float FRONT_LEG_Y = 14.1F;
/*     */   private static final float FRONT_LEG_Z = -5.0F;
/*     */   private static final String TAIL_1 = "tail1";
/*     */   private static final String TAIL_2 = "tail2";
/*     */   protected final ModelPart leftHindLeg;
/*     */   protected final ModelPart rightHindLeg;
/*     */   protected final ModelPart leftFrontLeg;
/*     */   protected final ModelPart rightFrontLeg;
/*     */   protected final ModelPart tail1;
/*     */   protected final ModelPart tail2;
/*     */   protected final ModelPart head;
/*     */   protected final ModelPart body;
/*  50 */   protected int state = 1;
/*     */   
/*     */   public OcelotModel(ModelPart $$0) {
/*  53 */     super(true, 10.0F, 4.0F);
/*  54 */     this.head = $$0.getChild("head");
/*  55 */     this.body = $$0.getChild("body");
/*  56 */     this.tail1 = $$0.getChild("tail1");
/*  57 */     this.tail2 = $$0.getChild("tail2");
/*  58 */     this.leftHindLeg = $$0.getChild("left_hind_leg");
/*  59 */     this.rightHindLeg = $$0.getChild("right_hind_leg");
/*  60 */     this.leftFrontLeg = $$0.getChild("left_front_leg");
/*  61 */     this.rightFrontLeg = $$0.getChild("right_front_leg");
/*     */   }
/*     */   
/*     */   public static MeshDefinition createBodyMesh(CubeDeformation $$0) {
/*  65 */     MeshDefinition $$1 = new MeshDefinition();
/*  66 */     PartDefinition $$2 = $$1.getRoot();
/*  67 */     CubeDeformation $$3 = new CubeDeformation(-0.02F);
/*     */     
/*  69 */     $$2.addOrReplaceChild("head", 
/*  70 */         CubeListBuilder.create()
/*  71 */         .addBox("main", -2.5F, -2.0F, -3.0F, 5.0F, 4.0F, 5.0F, $$0)
/*  72 */         .addBox("nose", -1.5F, -0.001F, -4.0F, 3, 2, 2, $$0, 0, 24)
/*  73 */         .addBox("ear1", -2.0F, -3.0F, 0.0F, 1, 1, 2, $$0, 0, 10)
/*  74 */         .addBox("ear2", 1.0F, -3.0F, 0.0F, 1, 1, 2, $$0, 6, 10), 
/*  75 */         PartPose.offset(0.0F, 15.0F, -9.0F));
/*     */     
/*  77 */     $$2.addOrReplaceChild("body", 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(20, 0).addBox(-2.0F, 3.0F, -8.0F, 4.0F, 16.0F, 6.0F, $$0), 
/*  80 */         PartPose.offsetAndRotation(0.0F, 12.0F, -10.0F, 1.5707964F, 0.0F, 0.0F));
/*     */     
/*  82 */     $$2.addOrReplaceChild("tail1", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(0, 15).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, $$0), 
/*  85 */         PartPose.offsetAndRotation(0.0F, 15.0F, 8.0F, 0.9F, 0.0F, 0.0F));
/*     */     
/*  87 */     $$2.addOrReplaceChild("tail2", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(4, 15).addBox(-0.5F, 0.0F, 0.0F, 1.0F, 8.0F, 1.0F, $$3), 
/*  90 */         PartPose.offset(0.0F, 20.0F, 14.0F));
/*     */ 
/*     */     
/*  93 */     CubeListBuilder $$4 = CubeListBuilder.create().texOffs(8, 13).addBox(-1.0F, 0.0F, 1.0F, 2.0F, 6.0F, 2.0F, $$0);
/*  94 */     $$2.addOrReplaceChild("left_hind_leg", $$4, PartPose.offset(1.1F, 18.0F, 5.0F));
/*  95 */     $$2.addOrReplaceChild("right_hind_leg", $$4, PartPose.offset(-1.1F, 18.0F, 5.0F));
/*     */ 
/*     */     
/*  98 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(40, 0).addBox(-1.0F, 0.0F, 0.0F, 2.0F, 10.0F, 2.0F, $$0);
/*  99 */     $$2.addOrReplaceChild("left_front_leg", $$5, PartPose.offset(1.2F, 14.1F, -5.0F));
/* 100 */     $$2.addOrReplaceChild("right_front_leg", $$5, PartPose.offset(-1.2F, 14.1F, -5.0F));
/*     */     
/* 102 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/* 107 */     return (Iterable<ModelPart>)ImmutableList.of(this.head);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 112 */     return (Iterable<ModelPart>)ImmutableList.of(this.body, this.leftHindLeg, this.rightHindLeg, this.leftFrontLeg, this.rightFrontLeg, this.tail1, this.tail2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 117 */     this.head.xRot = $$5 * 0.017453292F;
/* 118 */     this.head.yRot = $$4 * 0.017453292F;
/*     */     
/* 120 */     if (this.state != 3) {
/* 121 */       this.body.xRot = 1.5707964F;
/* 122 */       if (this.state == 2) {
/* 123 */         this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F) * $$2;
/* 124 */         this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F + 0.3F) * $$2;
/* 125 */         this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F + 0.3F) * $$2;
/* 126 */         this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * $$2;
/* 127 */         this.tail2.xRot = 1.7278761F + 0.31415927F * Mth.cos($$1) * $$2;
/*     */       } else {
/* 129 */         this.leftHindLeg.xRot = Mth.cos($$1 * 0.6662F) * $$2;
/* 130 */         this.rightHindLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * $$2;
/* 131 */         this.leftFrontLeg.xRot = Mth.cos($$1 * 0.6662F + 3.1415927F) * $$2;
/* 132 */         this.rightFrontLeg.xRot = Mth.cos($$1 * 0.6662F) * $$2;
/*     */         
/* 134 */         if (this.state == 1) {
/* 135 */           this.tail2.xRot = 1.7278761F + 0.7853982F * Mth.cos($$1) * $$2;
/*     */         } else {
/* 137 */           this.tail2.xRot = 1.7278761F + 0.47123894F * Mth.cos($$1) * $$2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(T $$0, float $$1, float $$2, float $$3) {
/* 145 */     this.body.y = 12.0F;
/* 146 */     this.body.z = -10.0F;
/* 147 */     this.head.y = 15.0F;
/* 148 */     this.head.z = -9.0F;
/* 149 */     this.tail1.y = 15.0F;
/* 150 */     this.tail1.z = 8.0F;
/* 151 */     this.tail2.y = 20.0F;
/* 152 */     this.tail2.z = 14.0F;
/* 153 */     this.leftFrontLeg.y = 14.1F;
/* 154 */     this.leftFrontLeg.z = -5.0F;
/* 155 */     this.rightFrontLeg.y = 14.1F;
/* 156 */     this.rightFrontLeg.z = -5.0F;
/* 157 */     this.leftHindLeg.y = 18.0F;
/* 158 */     this.leftHindLeg.z = 5.0F;
/* 159 */     this.rightHindLeg.y = 18.0F;
/* 160 */     this.rightHindLeg.z = 5.0F;
/* 161 */     this.tail1.xRot = 0.9F;
/*     */     
/* 163 */     if ($$0.isCrouching()) {
/* 164 */       this.body.y++;
/* 165 */       this.head.y += 2.0F;
/* 166 */       this.tail1.y++;
/* 167 */       this.tail2.y += -4.0F;
/* 168 */       this.tail2.z += 2.0F;
/* 169 */       this.tail1.xRot = 1.5707964F;
/* 170 */       this.tail2.xRot = 1.5707964F;
/* 171 */       this.state = 0;
/* 172 */     } else if ($$0.isSprinting()) {
/* 173 */       this.tail2.y = this.tail1.y;
/* 174 */       this.tail2.z += 2.0F;
/* 175 */       this.tail1.xRot = 1.5707964F;
/* 176 */       this.tail2.xRot = 1.5707964F;
/* 177 */       this.state = 2;
/*     */     } else {
/* 179 */       this.state = 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\OcelotModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */