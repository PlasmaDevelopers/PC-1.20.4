/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeDeformation;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LerpingModel;
/*     */ import net.minecraft.world.entity.animal.axolotl.Axolotl;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class AxolotlModel<T extends Axolotl & LerpingModel>
/*     */   extends AgeableListModel<T>
/*     */ {
/*     */   public static final float SWIMMING_LEG_XROT = 1.8849558F;
/*     */   private final ModelPart tail;
/*     */   private final ModelPart leftHindLeg;
/*     */   private final ModelPart rightHindLeg;
/*     */   private final ModelPart leftFrontLeg;
/*     */   private final ModelPart rightFrontLeg;
/*     */   private final ModelPart body;
/*     */   private final ModelPart head;
/*     */   private final ModelPart topGills;
/*     */   private final ModelPart leftGills;
/*     */   private final ModelPart rightGills;
/*     */   
/*     */   public AxolotlModel(ModelPart $$0) {
/*  34 */     super(true, 8.0F, 3.35F);
/*  35 */     this.body = $$0.getChild("body");
/*  36 */     this.head = this.body.getChild("head");
/*  37 */     this.rightHindLeg = this.body.getChild("right_hind_leg");
/*  38 */     this.leftHindLeg = this.body.getChild("left_hind_leg");
/*  39 */     this.rightFrontLeg = this.body.getChild("right_front_leg");
/*  40 */     this.leftFrontLeg = this.body.getChild("left_front_leg");
/*  41 */     this.tail = this.body.getChild("tail");
/*  42 */     this.topGills = this.head.getChild("top_gills");
/*  43 */     this.leftGills = this.head.getChild("left_gills");
/*  44 */     this.rightGills = this.head.getChild("right_gills");
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/*  48 */     MeshDefinition $$0 = new MeshDefinition();
/*  49 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/*  51 */     PartDefinition $$2 = $$1.addOrReplaceChild("body", 
/*  52 */         CubeListBuilder.create()
/*  53 */         .texOffs(0, 11).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 10.0F)
/*  54 */         .texOffs(2, 17).addBox(0.0F, -3.0F, -8.0F, 0.0F, 5.0F, 9.0F), 
/*  55 */         PartPose.offset(0.0F, 20.0F, 5.0F));
/*     */ 
/*     */     
/*  58 */     CubeDeformation $$3 = new CubeDeformation(0.001F);
/*  59 */     PartDefinition $$4 = $$2.addOrReplaceChild("head", 
/*  60 */         CubeListBuilder.create()
/*  61 */         .texOffs(0, 1).addBox(-4.0F, -3.0F, -5.0F, 8.0F, 5.0F, 5.0F, $$3), 
/*  62 */         PartPose.offset(0.0F, 0.0F, -9.0F));
/*     */ 
/*     */ 
/*     */     
/*  66 */     CubeListBuilder $$5 = CubeListBuilder.create().texOffs(3, 37).addBox(-4.0F, -3.0F, 0.0F, 8.0F, 3.0F, 0.0F, $$3);
/*     */     
/*  68 */     CubeListBuilder $$6 = CubeListBuilder.create().texOffs(0, 40).addBox(-3.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, $$3);
/*     */     
/*  70 */     CubeListBuilder $$7 = CubeListBuilder.create().texOffs(11, 40).addBox(0.0F, -5.0F, 0.0F, 3.0F, 7.0F, 0.0F, $$3);
/*     */     
/*  72 */     $$4.addOrReplaceChild("top_gills", $$5, PartPose.offset(0.0F, -3.0F, -1.0F));
/*  73 */     $$4.addOrReplaceChild("left_gills", $$6, PartPose.offset(-4.0F, 0.0F, -1.0F));
/*  74 */     $$4.addOrReplaceChild("right_gills", $$7, PartPose.offset(4.0F, 0.0F, -1.0F));
/*     */ 
/*     */     
/*  77 */     CubeListBuilder $$8 = CubeListBuilder.create().texOffs(2, 13).addBox(-1.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, $$3);
/*     */     
/*  79 */     CubeListBuilder $$9 = CubeListBuilder.create().texOffs(2, 13).addBox(-2.0F, 0.0F, 0.0F, 3.0F, 5.0F, 0.0F, $$3);
/*     */     
/*  81 */     $$2.addOrReplaceChild("right_hind_leg", $$9, PartPose.offset(-3.5F, 1.0F, -1.0F));
/*  82 */     $$2.addOrReplaceChild("left_hind_leg", $$8, PartPose.offset(3.5F, 1.0F, -1.0F));
/*  83 */     $$2.addOrReplaceChild("right_front_leg", $$9, PartPose.offset(-3.5F, 1.0F, -8.0F));
/*  84 */     $$2.addOrReplaceChild("left_front_leg", $$8, PartPose.offset(3.5F, 1.0F, -8.0F));
/*  85 */     $$2.addOrReplaceChild("tail", 
/*  86 */         CubeListBuilder.create()
/*  87 */         .texOffs(2, 19).addBox(0.0F, -3.0F, 0.0F, 0.0F, 5.0F, 12.0F), 
/*  88 */         PartPose.offset(0.0F, 0.0F, 1.0F));
/*     */ 
/*     */     
/*  91 */     return LayerDefinition.create($$0, 64, 64);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> headParts() {
/*  96 */     return (Iterable<ModelPart>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   protected Iterable<ModelPart> bodyParts() {
/* 101 */     return (Iterable<ModelPart>)ImmutableList.of(this.body);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnim(T $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 106 */     setupInitialAnimationValues($$0, $$4, $$5);
/*     */     
/* 108 */     if ($$0.isPlayingDead()) {
/* 109 */       setupPlayDeadAnimation($$4);
/*     */       
/* 111 */       saveAnimationValues($$0);
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 117 */     boolean $$6 = ($$2 > 1.0E-5F || $$0.getXRot() != ((Axolotl)$$0).xRotO || $$0.getYRot() != ((Axolotl)$$0).yRotO);
/*     */     
/* 119 */     if ($$0.isInWaterOrBubble()) {
/* 120 */       if ($$6) {
/* 121 */         setupSwimmingAnimation($$3, $$5);
/*     */       } else {
/* 123 */         setupWaterHoveringAnimation($$3);
/*     */       } 
/*     */       
/* 126 */       saveAnimationValues($$0);
/*     */       
/*     */       return;
/*     */     } 
/* 130 */     if ($$0.onGround()) {
/* 131 */       if ($$6) {
/* 132 */         setupGroundCrawlingAnimation($$3, $$4);
/*     */       } else {
/* 134 */         setupLayStillOnGroundAnimation($$3, $$4);
/*     */       } 
/*     */     }
/*     */     
/* 138 */     saveAnimationValues($$0);
/*     */   }
/*     */   
/*     */   private void saveAnimationValues(T $$0) {
/* 142 */     Map<String, Vector3f> $$1 = $$0.getModelRotationValues();
/* 143 */     $$1.put("body", getRotationVector(this.body));
/* 144 */     $$1.put("head", getRotationVector(this.head));
/* 145 */     $$1.put("right_hind_leg", getRotationVector(this.rightHindLeg));
/* 146 */     $$1.put("left_hind_leg", getRotationVector(this.leftHindLeg));
/* 147 */     $$1.put("right_front_leg", getRotationVector(this.rightFrontLeg));
/* 148 */     $$1.put("left_front_leg", getRotationVector(this.leftFrontLeg));
/* 149 */     $$1.put("tail", getRotationVector(this.tail));
/* 150 */     $$1.put("top_gills", getRotationVector(this.topGills));
/* 151 */     $$1.put("left_gills", getRotationVector(this.leftGills));
/* 152 */     $$1.put("right_gills", getRotationVector(this.rightGills));
/*     */   }
/*     */   
/*     */   private Vector3f getRotationVector(ModelPart $$0) {
/* 156 */     return new Vector3f($$0.xRot, $$0.yRot, $$0.zRot);
/*     */   }
/*     */   
/*     */   private void setRotationFromVector(ModelPart $$0, Vector3f $$1) {
/* 160 */     $$0.setRotation($$1.x(), $$1.y(), $$1.z());
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupInitialAnimationValues(T $$0, float $$1, float $$2) {
/* 165 */     this.body.x = 0.0F;
/* 166 */     this.head.y = 0.0F;
/* 167 */     this.body.y = 20.0F;
/*     */     
/* 169 */     Map<String, Vector3f> $$3 = $$0.getModelRotationValues();
/* 170 */     if ($$3.isEmpty()) {
/* 171 */       this.body.setRotation($$2 * 0.017453292F, $$1 * 0.017453292F, 0.0F);
/* 172 */       this.head.setRotation(0.0F, 0.0F, 0.0F);
/* 173 */       this.leftHindLeg.setRotation(0.0F, 0.0F, 0.0F);
/* 174 */       this.rightHindLeg.setRotation(0.0F, 0.0F, 0.0F);
/* 175 */       this.leftFrontLeg.setRotation(0.0F, 0.0F, 0.0F);
/* 176 */       this.rightFrontLeg.setRotation(0.0F, 0.0F, 0.0F);
/* 177 */       this.leftGills.setRotation(0.0F, 0.0F, 0.0F);
/* 178 */       this.rightGills.setRotation(0.0F, 0.0F, 0.0F);
/* 179 */       this.topGills.setRotation(0.0F, 0.0F, 0.0F);
/* 180 */       this.tail.setRotation(0.0F, 0.0F, 0.0F);
/*     */     } else {
/* 182 */       setRotationFromVector(this.body, $$3.get("body"));
/* 183 */       setRotationFromVector(this.head, $$3.get("head"));
/* 184 */       setRotationFromVector(this.leftHindLeg, $$3.get("left_hind_leg"));
/* 185 */       setRotationFromVector(this.rightHindLeg, $$3.get("right_hind_leg"));
/* 186 */       setRotationFromVector(this.leftFrontLeg, $$3.get("left_front_leg"));
/* 187 */       setRotationFromVector(this.rightFrontLeg, $$3.get("right_front_leg"));
/* 188 */       setRotationFromVector(this.leftGills, $$3.get("left_gills"));
/* 189 */       setRotationFromVector(this.rightGills, $$3.get("right_gills"));
/* 190 */       setRotationFromVector(this.topGills, $$3.get("top_gills"));
/* 191 */       setRotationFromVector(this.tail, $$3.get("tail"));
/*     */     } 
/*     */   }
/*     */   
/*     */   private float lerpTo(float $$0, float $$1) {
/* 196 */     return lerpTo(0.05F, $$0, $$1);
/*     */   }
/*     */   
/*     */   private float lerpTo(float $$0, float $$1, float $$2) {
/* 200 */     return Mth.rotLerp($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   private void lerpPart(ModelPart $$0, float $$1, float $$2, float $$3) {
/* 204 */     $$0.setRotation(lerpTo($$0.xRot, $$1), lerpTo($$0.yRot, $$2), lerpTo($$0.zRot, $$3));
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupLayStillOnGroundAnimation(float $$0, float $$1) {
/* 209 */     float $$2 = $$0 * 0.09F;
/* 210 */     float $$3 = Mth.sin($$2);
/* 211 */     float $$4 = Mth.cos($$2);
/* 212 */     float $$5 = $$3 * $$3 - 2.0F * $$3;
/* 213 */     float $$6 = $$4 * $$4 - 3.0F * $$3;
/*     */     
/* 215 */     this.head.xRot = lerpTo(this.head.xRot, -0.09F * $$5);
/* 216 */     this.head.yRot = lerpTo(this.head.yRot, 0.0F);
/* 217 */     this.head.zRot = lerpTo(this.head.zRot, -0.2F);
/*     */     
/* 219 */     this.tail.yRot = lerpTo(this.tail.yRot, -0.1F + 0.1F * $$5);
/*     */     
/* 221 */     this.topGills.xRot = lerpTo(this.topGills.xRot, 0.6F + 0.05F * $$6);
/* 222 */     this.leftGills.yRot = lerpTo(this.leftGills.yRot, -this.topGills.xRot);
/* 223 */     this.rightGills.yRot = lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
/*     */     
/* 225 */     lerpPart(this.leftHindLeg, 1.1F, 1.0F, 0.0F);
/* 226 */     lerpPart(this.leftFrontLeg, 0.8F, 2.3F, -0.5F);
/*     */     
/* 228 */     applyMirrorLegRotations();
/*     */ 
/*     */     
/* 231 */     this.body.xRot = lerpTo(0.2F, this.body.xRot, 0.0F);
/*     */ 
/*     */     
/* 234 */     this.body.yRot = lerpTo(this.body.yRot, $$1 * 0.017453292F);
/* 235 */     this.body.zRot = lerpTo(this.body.zRot, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupGroundCrawlingAnimation(float $$0, float $$1) {
/* 240 */     float $$2 = $$0 * 0.11F;
/* 241 */     float $$3 = Mth.cos($$2);
/* 242 */     float $$4 = ($$3 * $$3 - 2.0F * $$3) / 5.0F;
/* 243 */     float $$5 = 0.7F * $$3;
/*     */     
/* 245 */     this.head.xRot = lerpTo(this.head.xRot, 0.0F);
/* 246 */     this.head.yRot = lerpTo(this.head.yRot, 0.09F * $$3);
/* 247 */     this.head.zRot = lerpTo(this.head.zRot, 0.0F);
/*     */     
/* 249 */     this.tail.yRot = lerpTo(this.tail.yRot, this.head.yRot);
/*     */     
/* 251 */     this.topGills.xRot = lerpTo(this.topGills.xRot, 0.6F - 0.08F * ($$3 * $$3 + 2.0F * Mth.sin($$2)));
/* 252 */     this.leftGills.yRot = lerpTo(this.leftGills.yRot, -this.topGills.xRot);
/* 253 */     this.rightGills.yRot = lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
/*     */     
/* 255 */     lerpPart(this.leftHindLeg, 0.9424779F, 1.5F - $$4, -0.1F);
/* 256 */     lerpPart(this.leftFrontLeg, 1.0995574F, 1.5707964F - $$5, 0.0F);
/* 257 */     lerpPart(this.rightHindLeg, this.leftHindLeg.xRot, -1.0F - $$4, 0.0F);
/* 258 */     lerpPart(this.rightFrontLeg, this.leftFrontLeg.xRot, -1.5707964F - $$5, 0.0F);
/*     */ 
/*     */     
/* 261 */     this.body.xRot = lerpTo(0.2F, this.body.xRot, 0.0F);
/*     */ 
/*     */     
/* 264 */     this.body.yRot = lerpTo(this.body.yRot, $$1 * 0.017453292F);
/* 265 */     this.body.zRot = lerpTo(this.body.zRot, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupWaterHoveringAnimation(float $$0) {
/* 270 */     float $$1 = $$0 * 0.075F;
/* 271 */     float $$2 = Mth.cos($$1);
/* 272 */     float $$3 = Mth.sin($$1) * 0.15F;
/*     */     
/* 274 */     this.body.xRot = lerpTo(this.body.xRot, -0.15F + 0.075F * $$2);
/* 275 */     this.body.y -= $$3;
/*     */     
/* 277 */     this.head.xRot = lerpTo(this.head.xRot, -this.body.xRot);
/*     */     
/* 279 */     this.topGills.xRot = lerpTo(this.topGills.xRot, 0.2F * $$2);
/* 280 */     this.leftGills.yRot = lerpTo(this.leftGills.yRot, -0.3F * $$2 - 0.19F);
/* 281 */     this.rightGills.yRot = lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
/*     */     
/* 283 */     lerpPart(this.leftHindLeg, 2.3561945F - $$2 * 0.11F, 0.47123894F, 1.7278761F);
/* 284 */     lerpPart(this.leftFrontLeg, 0.7853982F - $$2 * 0.2F, 2.042035F, 0.0F);
/*     */     
/* 286 */     applyMirrorLegRotations();
/*     */     
/* 288 */     this.tail.yRot = lerpTo(this.tail.yRot, 0.5F * $$2);
/*     */ 
/*     */     
/* 291 */     this.head.yRot = lerpTo(this.head.yRot, 0.0F);
/* 292 */     this.head.zRot = lerpTo(this.head.zRot, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupSwimmingAnimation(float $$0, float $$1) {
/* 297 */     float $$2 = $$0 * 0.33F;
/* 298 */     float $$3 = Mth.sin($$2);
/* 299 */     float $$4 = Mth.cos($$2);
/* 300 */     float $$5 = 0.13F * $$3;
/*     */     
/* 302 */     this.body.xRot = lerpTo(0.1F, this.body.xRot, $$1 * 0.017453292F + $$5);
/* 303 */     this.head.xRot = -$$5 * 1.8F;
/* 304 */     this.body.y -= 0.45F * $$4;
/*     */     
/* 306 */     this.topGills.xRot = lerpTo(this.topGills.xRot, -0.5F * $$3 - 0.8F);
/* 307 */     this.leftGills.yRot = lerpTo(this.leftGills.yRot, 0.3F * $$3 + 0.9F);
/* 308 */     this.rightGills.yRot = lerpTo(this.rightGills.yRot, -this.leftGills.yRot);
/*     */     
/* 310 */     this.tail.yRot = lerpTo(this.tail.yRot, 0.3F * Mth.cos($$2 * 0.9F));
/*     */     
/* 312 */     lerpPart(this.leftHindLeg, 1.8849558F, -0.4F * $$3, 1.5707964F);
/* 313 */     lerpPart(this.leftFrontLeg, 1.8849558F, -0.2F * $$4 - 0.1F, 1.5707964F);
/*     */     
/* 315 */     applyMirrorLegRotations();
/*     */ 
/*     */     
/* 318 */     this.head.yRot = lerpTo(this.head.yRot, 0.0F);
/* 319 */     this.head.zRot = lerpTo(this.head.zRot, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupPlayDeadAnimation(float $$0) {
/* 324 */     lerpPart(this.leftHindLeg, 1.4137167F, 1.0995574F, 0.7853982F);
/* 325 */     lerpPart(this.leftFrontLeg, 0.7853982F, 2.042035F, 0.0F);
/*     */     
/* 327 */     this.body.xRot = lerpTo(this.body.xRot, -0.15F);
/* 328 */     this.body.zRot = lerpTo(this.body.zRot, 0.35F);
/*     */     
/* 330 */     applyMirrorLegRotations();
/*     */ 
/*     */     
/* 333 */     this.body.yRot = lerpTo(this.body.yRot, $$0 * 0.017453292F);
/*     */     
/* 335 */     this.head.xRot = lerpTo(this.head.xRot, 0.0F);
/* 336 */     this.head.yRot = lerpTo(this.head.yRot, 0.0F);
/* 337 */     this.head.zRot = lerpTo(this.head.zRot, 0.0F);
/*     */     
/* 339 */     this.tail.yRot = lerpTo(this.tail.yRot, 0.0F);
/*     */     
/* 341 */     lerpPart(this.topGills, 0.0F, 0.0F, 0.0F);
/* 342 */     lerpPart(this.leftGills, 0.0F, 0.0F, 0.0F);
/* 343 */     lerpPart(this.rightGills, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyMirrorLegRotations() {
/* 350 */     lerpPart(this.rightHindLeg, this.leftHindLeg.xRot, -this.leftHindLeg.yRot, -this.leftHindLeg.zRot);
/* 351 */     lerpPart(this.rightFrontLeg, this.leftFrontLeg.xRot, -this.leftFrontLeg.yRot, -this.leftFrontLeg.zRot);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\model\AxolotlModel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */