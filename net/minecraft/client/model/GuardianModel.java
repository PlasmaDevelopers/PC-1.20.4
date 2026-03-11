/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.monster.Guardian;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GuardianModel extends HierarchicalModel<Guardian> {
/*  17 */   private static final float[] SPIKE_X_ROT = new float[] { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F };
/*  18 */   private static final float[] SPIKE_Y_ROT = new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F };
/*  19 */   private static final float[] SPIKE_Z_ROT = new float[] { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F };
/*  20 */   private static final float[] SPIKE_X = new float[] { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F };
/*  21 */   private static final float[] SPIKE_Y = new float[] { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F };
/*  22 */   private static final float[] SPIKE_Z = new float[] { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F };
/*     */   
/*     */   private static final String EYE = "eye";
/*     */   
/*     */   private static final String TAIL_0 = "tail0";
/*     */   private static final String TAIL_1 = "tail1";
/*     */   private static final String TAIL_2 = "tail2";
/*     */   private final ModelPart root;
/*     */   private final ModelPart head;
/*     */   private final ModelPart eye;
/*     */   private final ModelPart[] spikeParts;
/*     */   private final ModelPart[] tailParts;
/*     */   
/*     */   public GuardianModel(ModelPart $$0) {
/*  36 */     this.root = $$0;
/*  37 */     this.spikeParts = new ModelPart[12];
/*     */     
/*  39 */     this.head = $$0.getChild("head");
/*     */     
/*  41 */     for (int $$1 = 0; $$1 < this.spikeParts.length; $$1++) {
/*  42 */       this.spikeParts[$$1] = this.head.getChild(createSpikeName($$1));
/*     */     }
/*     */     
/*  45 */     this.eye = this.head.getChild("eye");
/*  46 */     this.tailParts = new ModelPart[3];
/*  47 */     this.tailParts[0] = this.head.getChild("tail0");
/*  48 */     this.tailParts[1] = this.tailParts[0].getChild("tail1");
/*  49 */     this.tailParts[2] = this.tailParts[1].getChild("tail2");
/*     */   }
/*     */   
/*     */   private static String createSpikeName(int $$0) {
/*  53 */     return "spike" + $$0;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  57 */     MeshDefinition $$0 = new MeshDefinition();
/*  58 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  60 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/*  61 */         CubeListBuilder.create()
/*  62 */         .texOffs(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12.0F, 12.0F, 16.0F)
/*  63 */         .texOffs(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F)
/*  64 */         .texOffs(0, 28).addBox(6.0F, 10.0F, -6.0F, 2.0F, 12.0F, 12.0F, true)
/*  65 */         .texOffs(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12.0F, 2.0F, 12.0F)
/*  66 */         .texOffs(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12.0F, 2.0F, 12.0F), PartPose.ZERO);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -4.5F, -1.0F, 2.0F, 9.0F, 2.0F);
/*  72 */     for (int $$4 = 0; $$4 < 12; $$4++) {
/*  73 */       float $$5 = getSpikeX($$4, 0.0F, 0.0F);
/*  74 */       float $$6 = getSpikeY($$4, 0.0F, 0.0F);
/*  75 */       float $$7 = getSpikeZ($$4, 0.0F, 0.0F);
/*  76 */       float $$8 = 3.1415927F * SPIKE_X_ROT[$$4];
/*  77 */       float $$9 = 3.1415927F * SPIKE_Y_ROT[$$4];
/*  78 */       float $$10 = 3.1415927F * SPIKE_Z_ROT[$$4];
/*  79 */       $$2.addOrReplaceChild(createSpikeName($$4), $$3, PartPose.offsetAndRotation($$5, $$6, $$7, $$8, $$9, $$10));
/*     */     } 
/*     */     
/*  82 */     $$2.addOrReplaceChild("eye", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(8, 0).addBox(-1.0F, 15.0F, 0.0F, 2.0F, 2.0F, 1.0F), 
/*  85 */         PartPose.offset(0.0F, 0.0F, -8.25F));
/*     */     
/*  87 */     PartDefinition $$11 = $$2.addOrReplaceChild("tail0", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(40, 0).addBox(-2.0F, 14.0F, 7.0F, 4.0F, 4.0F, 8.0F), PartPose.ZERO);
/*     */     
/*  91 */     PartDefinition $$12 = $$11.addOrReplaceChild("tail1", 
/*  92 */         CubeListBuilder.create()
/*  93 */         .texOffs(0, 54).addBox(0.0F, 14.0F, 0.0F, 3.0F, 3.0F, 7.0F), 
/*  94 */         PartPose.offset(-1.5F, 0.5F, 14.0F));
/*     */     
/*  96 */     $$12.addOrReplaceChild("tail2", 
/*  97 */         CubeListBuilder.create()
/*  98 */         .texOffs(41, 32).addBox(0.0F, 14.0F, 0.0F, 2.0F, 2.0F, 6.0F)
/*  99 */         .texOffs(25, 19).addBox(1.0F, 10.5F, 3.0F, 1.0F, 9.0F, 9.0F), 
/* 100 */         PartPose.offset(0.5F, 0.5F, 6.0F));
/*     */ 
/*     */     
/* 103 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 108 */     return this.root;
/*     */   }
/*     */   
/*     */   public void setupAnim(Guardian $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/*     */     LivingEntity livingEntity;
/* 113 */     float $$6 = $$3 - $$0.tickCount;
/*     */     
/* 115 */     this.head.yRot = $$4 * 0.017453292F;
/* 116 */     this.head.xRot = $$5 * 0.017453292F;
/*     */     
/* 118 */     float $$7 = (1.0F - $$0.getSpikesAnimation($$6)) * 0.55F;
/* 119 */     setupSpikes($$3, $$7);
/*     */     
/* 121 */     Entity $$8 = Minecraft.getInstance().getCameraEntity();
/* 122 */     if ($$0.hasActiveAttackTarget()) {
/* 123 */       livingEntity = $$0.getActiveAttackTarget();
/*     */     }
/* 125 */     if (livingEntity != null) {
/* 126 */       Vec3 $$9 = livingEntity.getEyePosition(0.0F);
/* 127 */       Vec3 $$10 = $$0.getEyePosition(0.0F);
/* 128 */       double $$11 = $$9.y - $$10.y;
/* 129 */       if ($$11 > 0.0D) {
/* 130 */         this.eye.y = 0.0F;
/*     */       } else {
/* 132 */         this.eye.y = 1.0F;
/*     */       } 
/*     */       
/* 135 */       Vec3 $$12 = $$0.getViewVector(0.0F);
/* 136 */       $$12 = new Vec3($$12.x, 0.0D, $$12.z);
/* 137 */       Vec3 $$13 = (new Vec3($$10.x - $$9.x, 0.0D, $$10.z - $$9.z)).normalize().yRot(1.5707964F);
/* 138 */       double $$14 = $$12.dot($$13);
/* 139 */       this.eye.x = Mth.sqrt((float)Math.abs($$14)) * 2.0F * (float)Math.signum($$14);
/*     */     } 
/* 141 */     this.eye.visible = true;
/*     */     
/* 143 */     float $$15 = $$0.getTailAnimation($$6);
/* 144 */     (this.tailParts[0]).yRot = Mth.sin($$15) * 3.1415927F * 0.05F;
/* 145 */     (this.tailParts[1]).yRot = Mth.sin($$15) * 3.1415927F * 0.1F;
/* 146 */     (this.tailParts[2]).yRot = Mth.sin($$15) * 3.1415927F * 0.15F;
/*     */   }
/*     */   
/*     */   private void setupSpikes(float $$0, float $$1) {
/* 150 */     for (int $$2 = 0; $$2 < 12; $$2++) {
/* 151 */       (this.spikeParts[$$2]).x = getSpikeX($$2, $$0, $$1);
/* 152 */       (this.spikeParts[$$2]).y = getSpikeY($$2, $$0, $$1);
/* 153 */       (this.spikeParts[$$2]).z = getSpikeZ($$2, $$0, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static float getSpikeOffset(int $$0, float $$1, float $$2) {
/* 158 */     return 1.0F + Mth.cos($$1 * 1.5F + $$0) * 0.01F - $$2;
/*     */   }
/*     */   
/*     */   private static float getSpikeX(int $$0, float $$1, float $$2) {
/* 162 */     return SPIKE_X[$$0] * getSpikeOffset($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static float getSpikeY(int $$0, float $$1, float $$2) {
/* 166 */     return 16.0F + SPIKE_Y[$$0] * getSpikeOffset($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private static float getSpikeZ(int $$0, float $$1, float $$2) {
/* 170 */     return SPIKE_Z[$$0] * getSpikeOffset($$0, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\GuardianModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */