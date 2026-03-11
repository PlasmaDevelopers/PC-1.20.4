/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.animal.Parrot;
/*     */ 
/*     */ public class ParrotModel
/*     */   extends HierarchicalModel<Parrot> {
/*     */   private static final String FEATHER = "feather";
/*     */   private final ModelPart root;
/*     */   private final ModelPart body;
/*     */   private final ModelPart tail;
/*     */   private final ModelPart leftWing;
/*     */   private final ModelPart rightWing;
/*     */   private final ModelPart head;
/*     */   private final ModelPart feather;
/*     */   private final ModelPart leftLeg;
/*     */   private final ModelPart rightLeg;
/*     */   
/*     */   public enum State {
/*  29 */     FLYING,
/*  30 */     STANDING,
/*  31 */     SITTING,
/*  32 */     PARTY,
/*  33 */     ON_SHOULDER;
/*     */   }
/*     */   
/*     */   public ParrotModel(ModelPart $$0) {
/*  37 */     this.root = $$0;
/*  38 */     this.body = $$0.getChild("body");
/*  39 */     this.tail = $$0.getChild("tail");
/*  40 */     this.leftWing = $$0.getChild("left_wing");
/*  41 */     this.rightWing = $$0.getChild("right_wing");
/*  42 */     this.head = $$0.getChild("head");
/*  43 */     this.feather = this.head.getChild("feather");
/*  44 */     this.leftLeg = $$0.getChild("left_leg");
/*  45 */     this.rightLeg = $$0.getChild("right_leg");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  49 */     MeshDefinition $$0 = new MeshDefinition();
/*  50 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  52 */     $$1.addOrReplaceChild("body", 
/*  53 */         CubeListBuilder.create()
/*  54 */         .texOffs(2, 8).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F), 
/*  55 */         PartPose.offset(0.0F, 16.5F, -3.0F));
/*     */     
/*  57 */     $$1.addOrReplaceChild("tail", 
/*  58 */         CubeListBuilder.create()
/*  59 */         .texOffs(22, 1).addBox(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F), 
/*  60 */         PartPose.offset(0.0F, 21.07F, 1.16F));
/*     */     
/*  62 */     $$1.addOrReplaceChild("left_wing", 
/*  63 */         CubeListBuilder.create()
/*  64 */         .texOffs(19, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), 
/*  65 */         PartPose.offset(1.5F, 16.94F, -2.76F));
/*     */     
/*  67 */     $$1.addOrReplaceChild("right_wing", 
/*  68 */         CubeListBuilder.create()
/*  69 */         .texOffs(19, 8).addBox(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F), 
/*  70 */         PartPose.offset(-1.5F, 16.94F, -2.76F));
/*     */     
/*  72 */     PartDefinition $$2 = $$1.addOrReplaceChild("head", 
/*  73 */         CubeListBuilder.create()
/*  74 */         .texOffs(2, 2).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F), 
/*  75 */         PartPose.offset(0.0F, 15.69F, -2.76F));
/*     */     
/*  77 */     $$2.addOrReplaceChild("head2", 
/*  78 */         CubeListBuilder.create()
/*  79 */         .texOffs(10, 0).addBox(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F), 
/*  80 */         PartPose.offset(0.0F, -2.0F, -1.0F));
/*     */     
/*  82 */     $$2.addOrReplaceChild("beak1", 
/*  83 */         CubeListBuilder.create()
/*  84 */         .texOffs(11, 7).addBox(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F), 
/*  85 */         PartPose.offset(0.0F, -0.5F, -1.5F));
/*     */     
/*  87 */     $$2.addOrReplaceChild("beak2", 
/*  88 */         CubeListBuilder.create()
/*  89 */         .texOffs(16, 7).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F), 
/*  90 */         PartPose.offset(0.0F, -1.75F, -2.45F));
/*     */     
/*  92 */     $$2.addOrReplaceChild("feather", 
/*  93 */         CubeListBuilder.create()
/*  94 */         .texOffs(2, 18).addBox(0.0F, -4.0F, -2.0F, 0.0F, 5.0F, 4.0F), 
/*  95 */         PartPose.offset(0.0F, -2.15F, 0.15F));
/*     */ 
/*     */     
/*  98 */     CubeListBuilder $$3 = CubeListBuilder.create().texOffs(14, 18).addBox(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
/*  99 */     $$1.addOrReplaceChild("left_leg", $$3, PartPose.offset(1.0F, 22.0F, -1.05F));
/* 100 */     $$1.addOrReplaceChild("right_leg", $$3, PartPose.offset(-1.0F, 22.0F, -1.05F));
/*     */     
/* 102 */     return LayerDefinition.create($$0, 32, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPart root() {
/* 107 */     return this.root;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(Parrot $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 112 */     setupAnim(getState($$0), $$0.tickCount, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareMobModel(Parrot $$0, float $$1, float $$2, float $$3) {
/* 117 */     prepare(getState($$0));
/*     */   }
/*     */   
/*     */   public void renderOnShoulder(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7, int $$8) {
/* 121 */     prepare(State.ON_SHOULDER);
/* 122 */     setupAnim(State.ON_SHOULDER, $$8, $$4, $$5, 0.0F, $$6, $$7);
/* 123 */     this.root.render($$0, $$1, $$2, $$3);
/*     */   }
/*     */   private void setupAnim(State $$0, int $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
/*     */     float $$7, $$8;
/* 127 */     this.head.xRot = $$6 * 0.017453292F;
/* 128 */     this.head.yRot = $$5 * 0.017453292F;
/* 129 */     this.head.zRot = 0.0F;
/*     */     
/* 131 */     this.head.x = 0.0F;
/* 132 */     this.body.x = 0.0F;
/*     */     
/* 134 */     this.tail.x = 0.0F;
/*     */     
/* 136 */     this.rightWing.x = -1.5F;
/* 137 */     this.leftWing.x = 1.5F;
/*     */     
/* 139 */     switch ($$0) {
/*     */       case SITTING:
/*     */         return;
/*     */       case PARTY:
/* 143 */         $$7 = Mth.cos($$1);
/* 144 */         $$8 = Mth.sin($$1);
/* 145 */         this.head.x = $$7;
/* 146 */         this.head.y = 15.69F + $$8;
/*     */         
/* 148 */         this.head.xRot = 0.0F;
/* 149 */         this.head.yRot = 0.0F;
/* 150 */         this.head.zRot = Mth.sin($$1) * 0.4F;
/*     */         
/* 152 */         this.body.x = $$7;
/* 153 */         this.body.y = 16.5F + $$8;
/*     */         
/* 155 */         this.leftWing.zRot = -0.0873F - $$4;
/* 156 */         this.leftWing.x = 1.5F + $$7;
/* 157 */         this.leftWing.y = 16.94F + $$8;
/*     */         
/* 159 */         this.rightWing.zRot = 0.0873F + $$4;
/* 160 */         this.rightWing.x = -1.5F + $$7;
/* 161 */         this.rightWing.y = 16.94F + $$8;
/*     */         
/* 163 */         this.tail.x = $$7;
/* 164 */         this.tail.y = 21.07F + $$8;
/*     */ 
/*     */       
/*     */       case STANDING:
/* 168 */         this.leftLeg.xRot += Mth.cos($$2 * 0.6662F) * 1.4F * $$3;
/* 169 */         this.rightLeg.xRot += Mth.cos($$2 * 0.6662F + 3.1415927F) * 1.4F * $$3;
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 174 */     float $$9 = $$4 * 0.3F;
/* 175 */     this.head.y = 15.69F + $$9;
/*     */     
/* 177 */     this.tail.xRot = 1.015F + Mth.cos($$2 * 0.6662F) * 0.3F * $$3;
/* 178 */     this.tail.y = 21.07F + $$9;
/*     */     
/* 180 */     this.body.y = 16.5F + $$9;
/*     */     
/* 182 */     this.leftWing.zRot = -0.0873F - $$4;
/* 183 */     this.leftWing.y = 16.94F + $$9;
/*     */     
/* 185 */     this.rightWing.zRot = 0.0873F + $$4;
/* 186 */     this.rightWing.y = 16.94F + $$9;
/*     */     
/* 188 */     this.leftLeg.y = 22.0F + $$9;
/* 189 */     this.rightLeg.y = 22.0F + $$9;
/*     */   }
/*     */ 
/*     */   
/*     */   private void prepare(State $$0) {
/*     */     float $$1;
/* 195 */     this.feather.xRot = -0.2214F;
/* 196 */     this.body.xRot = 0.4937F;
/*     */     
/* 198 */     this.leftWing.xRot = -0.6981F;
/* 199 */     this.leftWing.yRot = -3.1415927F;
/*     */     
/* 201 */     this.rightWing.xRot = -0.6981F;
/* 202 */     this.rightWing.yRot = -3.1415927F;
/*     */     
/* 204 */     this.leftLeg.xRot = -0.0299F;
/* 205 */     this.rightLeg.xRot = -0.0299F;
/* 206 */     this.leftLeg.y = 22.0F;
/* 207 */     this.rightLeg.y = 22.0F;
/*     */     
/* 209 */     this.leftLeg.zRot = 0.0F;
/* 210 */     this.rightLeg.zRot = 0.0F;
/*     */     
/* 212 */     switch ($$0) {
/*     */       case FLYING:
/* 214 */         this.leftLeg.xRot += 0.6981317F;
/* 215 */         this.rightLeg.xRot += 0.6981317F;
/*     */         break;
/*     */       case SITTING:
/* 218 */         $$1 = 1.9F;
/*     */         
/* 220 */         this.head.y = 17.59F;
/*     */         
/* 222 */         this.tail.xRot = 1.5388988F;
/* 223 */         this.tail.y = 22.97F;
/*     */         
/* 225 */         this.body.y = 18.4F;
/*     */         
/* 227 */         this.leftWing.zRot = -0.0873F;
/* 228 */         this.leftWing.y = 18.84F;
/*     */         
/* 230 */         this.rightWing.zRot = 0.0873F;
/* 231 */         this.rightWing.y = 18.84F;
/*     */         
/* 233 */         this.leftLeg.y += 1.9F;
/* 234 */         this.rightLeg.y += 1.9F;
/*     */         
/* 236 */         this.leftLeg.xRot += 1.5707964F;
/* 237 */         this.rightLeg.xRot += 1.5707964F;
/*     */         break;
/*     */       case PARTY:
/* 240 */         this.leftLeg.zRot = -0.34906584F;
/* 241 */         this.rightLeg.zRot = 0.34906584F;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static State getState(Parrot $$0) {
/* 252 */     if ($$0.isPartyParrot())
/* 253 */       return State.PARTY; 
/* 254 */     if ($$0.isInSittingPose())
/* 255 */       return State.SITTING; 
/* 256 */     if ($$0.isFlying()) {
/* 257 */       return State.FLYING;
/*     */     }
/* 259 */     return State.STANDING;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\ParrotModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */