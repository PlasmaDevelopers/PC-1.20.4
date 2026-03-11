/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.model.EntityModel;
/*     */ import net.minecraft.client.model.geom.ModelLayers;
/*     */ import net.minecraft.client.model.geom.ModelPart;
/*     */ import net.minecraft.client.model.geom.PartPose;
/*     */ import net.minecraft.client.model.geom.builders.CubeListBuilder;
/*     */ import net.minecraft.client.model.geom.builders.LayerDefinition;
/*     */ import net.minecraft.client.model.geom.builders.MeshDefinition;
/*     */ import net.minecraft.client.model.geom.builders.PartDefinition;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class EnderDragonRenderer
/*     */   extends EntityRenderer<EnderDragon> {
/*  28 */   public static final ResourceLocation CRYSTAL_BEAM_LOCATION = new ResourceLocation("textures/entity/end_crystal/end_crystal_beam.png");
/*  29 */   private static final ResourceLocation DRAGON_EXPLODING_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
/*  30 */   private static final ResourceLocation DRAGON_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon.png");
/*  31 */   private static final ResourceLocation DRAGON_EYES_LOCATION = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
/*     */   
/*  33 */   private static final RenderType RENDER_TYPE = RenderType.entityCutoutNoCull(DRAGON_LOCATION);
/*  34 */   private static final RenderType DECAL = RenderType.entityDecal(DRAGON_LOCATION);
/*  35 */   private static final RenderType EYES = RenderType.eyes(DRAGON_EYES_LOCATION);
/*  36 */   private static final RenderType BEAM = RenderType.entitySmoothCutout(CRYSTAL_BEAM_LOCATION);
/*     */   
/*  38 */   private static final float HALF_SQRT_3 = (float)(Math.sqrt(3.0D) / 2.0D);
/*     */   
/*     */   private final DragonModel model;
/*     */   
/*     */   public EnderDragonRenderer(EntityRendererProvider.Context $$0) {
/*  43 */     super($$0);
/*  44 */     this.shadowRadius = 0.5F;
/*     */     
/*  46 */     this.model = new DragonModel($$0.bakeLayer(ModelLayers.ENDER_DRAGON));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(EnderDragon $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  51 */     $$3.pushPose();
/*     */     
/*  53 */     float $$6 = (float)$$0.getLatencyPos(7, $$2)[0];
/*  54 */     float $$7 = (float)($$0.getLatencyPos(5, $$2)[1] - $$0.getLatencyPos(10, $$2)[1]);
/*  55 */     $$3.mulPose(Axis.YP.rotationDegrees(-$$6));
/*  56 */     $$3.mulPose(Axis.XP.rotationDegrees($$7 * 10.0F));
/*  57 */     $$3.translate(0.0F, 0.0F, 1.0F);
/*     */ 
/*     */ 
/*     */     
/*  61 */     $$3.scale(-1.0F, -1.0F, 1.0F);
/*     */ 
/*     */     
/*  64 */     $$3.translate(0.0F, -1.501F, 0.0F);
/*     */     
/*  66 */     boolean $$8 = ($$0.hurtTime > 0);
/*     */     
/*  68 */     this.model.prepareMobModel($$0, 0.0F, 0.0F, $$2);
/*     */     
/*  70 */     if ($$0.dragonDeathTime > 0) {
/*     */       
/*  72 */       float $$9 = $$0.dragonDeathTime / 200.0F;
/*  73 */       VertexConsumer $$10 = $$4.getBuffer(RenderType.dragonExplosionAlpha(DRAGON_EXPLODING_LOCATION));
/*  74 */       this.model.renderToBuffer($$3, $$10, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, $$9);
/*     */       
/*  76 */       VertexConsumer $$11 = $$4.getBuffer(DECAL);
/*  77 */       this.model.renderToBuffer($$3, $$11, $$5, OverlayTexture.pack(0.0F, $$8), 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } else {
/*  79 */       VertexConsumer $$12 = $$4.getBuffer(RENDER_TYPE);
/*  80 */       this.model.renderToBuffer($$3, $$12, $$5, OverlayTexture.pack(0.0F, $$8), 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     } 
/*     */     
/*  83 */     VertexConsumer $$13 = $$4.getBuffer(EYES);
/*  84 */     this.model.renderToBuffer($$3, $$13, $$5, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
/*     */     
/*  86 */     if ($$0.dragonDeathTime > 0) {
/*  87 */       float $$14 = ($$0.dragonDeathTime + $$2) / 200.0F;
/*  88 */       float $$15 = Math.min(($$14 > 0.8F) ? (($$14 - 0.8F) / 0.2F) : 0.0F, 1.0F);
/*     */       
/*  90 */       RandomSource $$16 = RandomSource.create(432L);
/*     */       
/*  92 */       VertexConsumer $$17 = $$4.getBuffer(RenderType.lightning());
/*     */       
/*  94 */       $$3.pushPose();
/*  95 */       $$3.translate(0.0F, -1.0F, -2.0F);
/*  96 */       for (int $$18 = 0; $$18 < ($$14 + $$14 * $$14) / 2.0F * 60.0F; $$18++) {
/*  97 */         $$3.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
/*  98 */         $$3.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
/*  99 */         $$3.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F));
/* 100 */         $$3.mulPose(Axis.XP.rotationDegrees($$16.nextFloat() * 360.0F));
/* 101 */         $$3.mulPose(Axis.YP.rotationDegrees($$16.nextFloat() * 360.0F));
/* 102 */         $$3.mulPose(Axis.ZP.rotationDegrees($$16.nextFloat() * 360.0F + $$14 * 90.0F));
/*     */         
/* 104 */         float $$19 = $$16.nextFloat() * 20.0F + 5.0F + $$15 * 10.0F;
/* 105 */         float $$20 = $$16.nextFloat() * 2.0F + 1.0F + $$15 * 2.0F;
/*     */         
/* 107 */         Matrix4f $$21 = $$3.last().pose();
/*     */         
/* 109 */         int $$22 = (int)(255.0F * (1.0F - $$15));
/*     */         
/* 111 */         vertex01($$17, $$21, $$22);
/* 112 */         vertex2($$17, $$21, $$19, $$20);
/* 113 */         vertex3($$17, $$21, $$19, $$20);
/*     */         
/* 115 */         vertex01($$17, $$21, $$22);
/* 116 */         vertex3($$17, $$21, $$19, $$20);
/* 117 */         vertex4($$17, $$21, $$19, $$20);
/*     */         
/* 119 */         vertex01($$17, $$21, $$22);
/* 120 */         vertex4($$17, $$21, $$19, $$20);
/* 121 */         vertex2($$17, $$21, $$19, $$20);
/*     */       } 
/* 123 */       $$3.popPose();
/*     */     } 
/*     */     
/* 126 */     $$3.popPose();
/*     */     
/* 128 */     if ($$0.nearestCrystal != null) {
/* 129 */       $$3.pushPose();
/*     */       
/* 131 */       float $$23 = (float)($$0.nearestCrystal.getX() - Mth.lerp($$2, $$0.xo, $$0.getX()));
/* 132 */       float $$24 = (float)($$0.nearestCrystal.getY() - Mth.lerp($$2, $$0.yo, $$0.getY()));
/* 133 */       float $$25 = (float)($$0.nearestCrystal.getZ() - Mth.lerp($$2, $$0.zo, $$0.getZ()));
/*     */       
/* 135 */       renderCrystalBeams($$23, $$24 + EndCrystalRenderer.getY($$0.nearestCrystal, $$2), $$25, $$2, $$0.tickCount, $$3, $$4, $$5);
/* 136 */       $$3.popPose();
/*     */     } 
/*     */     
/* 139 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   private static void vertex01(VertexConsumer $$0, Matrix4f $$1, int $$2) {
/* 143 */     $$0.vertex($$1, 0.0F, 0.0F, 0.0F).color(255, 255, 255, $$2).endVertex();
/*     */   }
/*     */   
/*     */   private static void vertex2(VertexConsumer $$0, Matrix4f $$1, float $$2, float $$3) {
/* 147 */     $$0.vertex($$1, -HALF_SQRT_3 * $$3, $$2, -0.5F * $$3).color(255, 0, 255, 0).endVertex();
/*     */   }
/*     */   
/*     */   private static void vertex3(VertexConsumer $$0, Matrix4f $$1, float $$2, float $$3) {
/* 151 */     $$0.vertex($$1, HALF_SQRT_3 * $$3, $$2, -0.5F * $$3).color(255, 0, 255, 0).endVertex();
/*     */   }
/*     */   
/*     */   private static void vertex4(VertexConsumer $$0, Matrix4f $$1, float $$2, float $$3) {
/* 155 */     $$0.vertex($$1, 0.0F, $$2, 1.0F * $$3).color(255, 0, 255, 0).endVertex();
/*     */   }
/*     */   
/*     */   public static void renderCrystalBeams(float $$0, float $$1, float $$2, float $$3, int $$4, PoseStack $$5, MultiBufferSource $$6, int $$7) {
/* 159 */     float $$8 = Mth.sqrt($$0 * $$0 + $$2 * $$2);
/* 160 */     float $$9 = Mth.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2);
/*     */     
/* 162 */     $$5.pushPose();
/* 163 */     $$5.translate(0.0F, 2.0F, 0.0F);
/* 164 */     $$5.mulPose(Axis.YP.rotation((float)-Math.atan2($$2, $$0) - 1.5707964F));
/* 165 */     $$5.mulPose(Axis.XP.rotation((float)-Math.atan2($$8, $$1) - 1.5707964F));
/*     */     
/* 167 */     VertexConsumer $$10 = $$6.getBuffer(BEAM);
/*     */     
/* 169 */     float $$11 = 0.0F - ($$4 + $$3) * 0.01F;
/* 170 */     float $$12 = Mth.sqrt($$0 * $$0 + $$1 * $$1 + $$2 * $$2) / 32.0F - ($$4 + $$3) * 0.01F;
/*     */     
/* 172 */     int $$13 = 8;
/* 173 */     float $$14 = 0.0F;
/* 174 */     float $$15 = 0.75F;
/* 175 */     float $$16 = 0.0F;
/*     */     
/* 177 */     PoseStack.Pose $$17 = $$5.last();
/* 178 */     Matrix4f $$18 = $$17.pose();
/* 179 */     Matrix3f $$19 = $$17.normal();
/*     */     
/* 181 */     for (int $$20 = 1; $$20 <= 8; $$20++) {
/* 182 */       float $$21 = Mth.sin($$20 * 6.2831855F / 8.0F) * 0.75F;
/* 183 */       float $$22 = Mth.cos($$20 * 6.2831855F / 8.0F) * 0.75F;
/* 184 */       float $$23 = $$20 / 8.0F;
/* 185 */       $$10
/* 186 */         .vertex($$18, $$14 * 0.2F, $$15 * 0.2F, 0.0F)
/* 187 */         .color(0, 0, 0, 255)
/* 188 */         .uv($$16, $$11)
/* 189 */         .overlayCoords(OverlayTexture.NO_OVERLAY)
/* 190 */         .uv2($$7)
/* 191 */         .normal($$19, 0.0F, -1.0F, 0.0F)
/* 192 */         .endVertex();
/*     */       
/* 194 */       $$10
/* 195 */         .vertex($$18, $$14, $$15, $$9)
/* 196 */         .color(255, 255, 255, 255)
/* 197 */         .uv($$16, $$12)
/* 198 */         .overlayCoords(OverlayTexture.NO_OVERLAY)
/* 199 */         .uv2($$7)
/* 200 */         .normal($$19, 0.0F, -1.0F, 0.0F)
/* 201 */         .endVertex();
/*     */       
/* 203 */       $$10
/* 204 */         .vertex($$18, $$21, $$22, $$9)
/* 205 */         .color(255, 255, 255, 255)
/* 206 */         .uv($$23, $$12)
/* 207 */         .overlayCoords(OverlayTexture.NO_OVERLAY)
/* 208 */         .uv2($$7)
/* 209 */         .normal($$19, 0.0F, -1.0F, 0.0F)
/* 210 */         .endVertex();
/*     */       
/* 212 */       $$10
/* 213 */         .vertex($$18, $$21 * 0.2F, $$22 * 0.2F, 0.0F)
/* 214 */         .color(0, 0, 0, 255)
/* 215 */         .uv($$23, $$11)
/* 216 */         .overlayCoords(OverlayTexture.NO_OVERLAY)
/* 217 */         .uv2($$7)
/* 218 */         .normal($$19, 0.0F, -1.0F, 0.0F)
/* 219 */         .endVertex();
/*     */       
/* 221 */       $$14 = $$21;
/* 222 */       $$15 = $$22;
/* 223 */       $$16 = $$23;
/*     */     } 
/*     */     
/* 226 */     $$5.popPose();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(EnderDragon $$0) {
/* 231 */     return DRAGON_LOCATION;
/*     */   }
/*     */   
/*     */   public static LayerDefinition createBodyLayer() {
/* 235 */     MeshDefinition $$0 = new MeshDefinition();
/* 236 */     PartDefinition $$1 = $$0.getRoot();
/*     */     
/* 238 */     float $$2 = -16.0F;
/* 239 */     PartDefinition $$3 = $$1.addOrReplaceChild("head", 
/* 240 */         CubeListBuilder.create()
/* 241 */         .addBox("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 176, 44)
/* 242 */         .addBox("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 112, 30)
/* 243 */         .mirror()
/* 244 */         .addBox("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
/* 245 */         .addBox("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0)
/* 246 */         .mirror()
/* 247 */         .addBox("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0, 0)
/* 248 */         .addBox("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 112, 0), PartPose.ZERO);
/*     */ 
/*     */     
/* 251 */     $$3.addOrReplaceChild("jaw", 
/* 252 */         CubeListBuilder.create()
/* 253 */         .addBox("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 176, 65), 
/* 254 */         PartPose.offset(0.0F, 4.0F, -8.0F));
/*     */     
/* 256 */     $$1.addOrReplaceChild("neck", 
/* 257 */         CubeListBuilder.create()
/* 258 */         .addBox("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 192, 104)
/* 259 */         .addBox("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 48, 0), PartPose.ZERO);
/*     */ 
/*     */     
/* 262 */     $$1.addOrReplaceChild("body", 
/* 263 */         CubeListBuilder.create()
/* 264 */         .addBox("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0, 0)
/* 265 */         .addBox("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 220, 53)
/* 266 */         .addBox("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 220, 53)
/* 267 */         .addBox("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 220, 53), 
/* 268 */         PartPose.offset(0.0F, 4.0F, 8.0F));
/*     */ 
/*     */     
/* 271 */     PartDefinition $$4 = $$1.addOrReplaceChild("left_wing", 
/* 272 */         CubeListBuilder.create()
/* 273 */         .mirror()
/* 274 */         .addBox("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88)
/* 275 */         .addBox("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), 
/* 276 */         PartPose.offset(12.0F, 5.0F, 2.0F));
/*     */     
/* 278 */     $$4.addOrReplaceChild("left_wing_tip", 
/* 279 */         CubeListBuilder.create()
/* 280 */         .mirror()
/* 281 */         .addBox("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136)
/* 282 */         .addBox("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), 
/* 283 */         PartPose.offset(56.0F, 0.0F, 0.0F));
/*     */ 
/*     */     
/* 286 */     PartDefinition $$5 = $$1.addOrReplaceChild("left_front_leg", 
/* 287 */         CubeListBuilder.create()
/* 288 */         .addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), 
/* 289 */         PartPose.offset(12.0F, 20.0F, 2.0F));
/*     */     
/* 291 */     PartDefinition $$6 = $$5.addOrReplaceChild("left_front_leg_tip", 
/* 292 */         CubeListBuilder.create()
/* 293 */         .addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), 
/* 294 */         PartPose.offset(0.0F, 20.0F, -1.0F));
/*     */     
/* 296 */     $$6.addOrReplaceChild("left_front_foot", 
/* 297 */         CubeListBuilder.create()
/* 298 */         .addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), 
/* 299 */         PartPose.offset(0.0F, 23.0F, 0.0F));
/*     */ 
/*     */     
/* 302 */     PartDefinition $$7 = $$1.addOrReplaceChild("left_hind_leg", 
/* 303 */         CubeListBuilder.create()
/* 304 */         .addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), 
/* 305 */         PartPose.offset(16.0F, 16.0F, 42.0F));
/*     */     
/* 307 */     PartDefinition $$8 = $$7.addOrReplaceChild("left_hind_leg_tip", 
/* 308 */         CubeListBuilder.create()
/* 309 */         .addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), 
/* 310 */         PartPose.offset(0.0F, 32.0F, -4.0F));
/*     */     
/* 312 */     $$8.addOrReplaceChild("left_hind_foot", 
/* 313 */         CubeListBuilder.create()
/* 314 */         .addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), 
/* 315 */         PartPose.offset(0.0F, 31.0F, 4.0F));
/*     */ 
/*     */     
/* 318 */     PartDefinition $$9 = $$1.addOrReplaceChild("right_wing", 
/* 319 */         CubeListBuilder.create()
/* 320 */         .addBox("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 112, 88)
/* 321 */         .addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 88), 
/* 322 */         PartPose.offset(-12.0F, 5.0F, 2.0F));
/*     */     
/* 324 */     $$9.addOrReplaceChild("right_wing_tip", 
/* 325 */         CubeListBuilder.create()
/* 326 */         .addBox("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 112, 136)
/* 327 */         .addBox("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, -56, 144), 
/* 328 */         PartPose.offset(-56.0F, 0.0F, 0.0F));
/*     */ 
/*     */     
/* 331 */     PartDefinition $$10 = $$1.addOrReplaceChild("right_front_leg", 
/* 332 */         CubeListBuilder.create()
/* 333 */         .addBox("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 112, 104), 
/* 334 */         PartPose.offset(-12.0F, 20.0F, 2.0F));
/*     */     
/* 336 */     PartDefinition $$11 = $$10.addOrReplaceChild("right_front_leg_tip", 
/* 337 */         CubeListBuilder.create()
/* 338 */         .addBox("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 226, 138), 
/* 339 */         PartPose.offset(0.0F, 20.0F, -1.0F));
/*     */     
/* 341 */     $$11.addOrReplaceChild("right_front_foot", 
/* 342 */         CubeListBuilder.create()
/* 343 */         .addBox("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 144, 104), 
/* 344 */         PartPose.offset(0.0F, 23.0F, 0.0F));
/*     */ 
/*     */     
/* 347 */     PartDefinition $$12 = $$1.addOrReplaceChild("right_hind_leg", 
/* 348 */         CubeListBuilder.create()
/* 349 */         .addBox("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0, 0), 
/* 350 */         PartPose.offset(-16.0F, 16.0F, 42.0F));
/*     */     
/* 352 */     PartDefinition $$13 = $$12.addOrReplaceChild("right_hind_leg_tip", 
/* 353 */         CubeListBuilder.create()
/* 354 */         .addBox("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 196, 0), 
/* 355 */         PartPose.offset(0.0F, 32.0F, -4.0F));
/*     */     
/* 357 */     $$13.addOrReplaceChild("right_hind_foot", 
/* 358 */         CubeListBuilder.create()
/* 359 */         .addBox("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 112, 0), 
/* 360 */         PartPose.offset(0.0F, 31.0F, 4.0F));
/*     */ 
/*     */     
/* 363 */     return LayerDefinition.create($$0, 256, 256);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DragonModel
/*     */     extends EntityModel<EnderDragon>
/*     */   {
/*     */     private final ModelPart head;
/*     */     
/*     */     private final ModelPart neck;
/*     */     
/*     */     private final ModelPart jaw;
/*     */     
/*     */     private final ModelPart body;
/*     */     
/*     */     private final ModelPart leftWing;
/*     */     
/*     */     private final ModelPart leftWingTip;
/*     */     private final ModelPart leftFrontLeg;
/*     */     private final ModelPart leftFrontLegTip;
/*     */     private final ModelPart leftFrontFoot;
/*     */     private final ModelPart leftRearLeg;
/*     */     private final ModelPart leftRearLegTip;
/*     */     private final ModelPart leftRearFoot;
/*     */     private final ModelPart rightWing;
/*     */     private final ModelPart rightWingTip;
/*     */     private final ModelPart rightFrontLeg;
/*     */     private final ModelPart rightFrontLegTip;
/*     */     private final ModelPart rightFrontFoot;
/*     */     private final ModelPart rightRearLeg;
/*     */     private final ModelPart rightRearLegTip;
/*     */     private final ModelPart rightRearFoot;
/*     */     @Nullable
/*     */     private EnderDragon entity;
/*     */     private float a;
/*     */     
/*     */     public DragonModel(ModelPart $$0) {
/* 400 */       this.head = $$0.getChild("head");
/* 401 */       this.jaw = this.head.getChild("jaw");
/* 402 */       this.neck = $$0.getChild("neck");
/* 403 */       this.body = $$0.getChild("body");
/* 404 */       this.leftWing = $$0.getChild("left_wing");
/* 405 */       this.leftWingTip = this.leftWing.getChild("left_wing_tip");
/* 406 */       this.leftFrontLeg = $$0.getChild("left_front_leg");
/* 407 */       this.leftFrontLegTip = this.leftFrontLeg.getChild("left_front_leg_tip");
/* 408 */       this.leftFrontFoot = this.leftFrontLegTip.getChild("left_front_foot");
/* 409 */       this.leftRearLeg = $$0.getChild("left_hind_leg");
/* 410 */       this.leftRearLegTip = this.leftRearLeg.getChild("left_hind_leg_tip");
/* 411 */       this.leftRearFoot = this.leftRearLegTip.getChild("left_hind_foot");
/* 412 */       this.rightWing = $$0.getChild("right_wing");
/* 413 */       this.rightWingTip = this.rightWing.getChild("right_wing_tip");
/* 414 */       this.rightFrontLeg = $$0.getChild("right_front_leg");
/* 415 */       this.rightFrontLegTip = this.rightFrontLeg.getChild("right_front_leg_tip");
/* 416 */       this.rightFrontFoot = this.rightFrontLegTip.getChild("right_front_foot");
/* 417 */       this.rightRearLeg = $$0.getChild("right_hind_leg");
/* 418 */       this.rightRearLegTip = this.rightRearLeg.getChild("right_hind_leg_tip");
/* 419 */       this.rightRearFoot = this.rightRearLegTip.getChild("right_hind_foot");
/*     */     }
/*     */ 
/*     */     
/*     */     public void prepareMobModel(EnderDragon $$0, float $$1, float $$2, float $$3) {
/* 424 */       this.entity = $$0;
/* 425 */       this.a = $$3;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void setupAnim(EnderDragon $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {}
/*     */ 
/*     */     
/*     */     public void renderToBuffer(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, float $$5, float $$6, float $$7) {
/* 434 */       $$0.pushPose();
/* 435 */       float $$8 = Mth.lerp(this.a, this.entity.oFlapTime, this.entity.flapTime);
/* 436 */       this.jaw.xRot = (float)(Math.sin(($$8 * 6.2831855F)) + 1.0D) * 0.2F;
/*     */       
/* 438 */       float $$9 = (float)(Math.sin(($$8 * 6.2831855F - 1.0F)) + 1.0D);
/* 439 */       $$9 = ($$9 * $$9 + $$9 * 2.0F) * 0.05F;
/*     */       
/* 441 */       $$0.translate(0.0F, $$9 - 2.0F, -3.0F);
/* 442 */       $$0.mulPose(Axis.XP.rotationDegrees($$9 * 2.0F));
/*     */       
/* 444 */       float $$10 = 0.0F;
/* 445 */       float $$11 = 20.0F;
/* 446 */       float $$12 = -12.0F;
/*     */       
/* 448 */       float $$13 = 1.5F;
/*     */       
/* 450 */       double[] $$14 = this.entity.getLatencyPos(6, this.a);
/*     */       
/* 452 */       float $$15 = Mth.wrapDegrees((float)(this.entity.getLatencyPos(5, this.a)[0] - this.entity.getLatencyPos(10, this.a)[0]));
/* 453 */       float $$16 = Mth.wrapDegrees((float)(this.entity.getLatencyPos(5, this.a)[0] + ($$15 / 2.0F)));
/*     */       
/* 455 */       float $$17 = $$8 * 6.2831855F;
/* 456 */       for (int $$18 = 0; $$18 < 5; $$18++) {
/* 457 */         double[] $$19 = this.entity.getLatencyPos(5 - $$18, this.a);
/* 458 */         float $$20 = (float)Math.cos(($$18 * 0.45F + $$17)) * 0.15F;
/* 459 */         this.neck.yRot = Mth.wrapDegrees((float)($$19[0] - $$14[0])) * 0.017453292F * 1.5F;
/* 460 */         this.neck.xRot = $$20 + this.entity.getHeadPartYOffset($$18, $$14, $$19) * 0.017453292F * 1.5F * 5.0F;
/* 461 */         this.neck.zRot = -Mth.wrapDegrees((float)($$19[0] - $$16)) * 0.017453292F * 1.5F;
/*     */         
/* 463 */         this.neck.y = $$11;
/* 464 */         this.neck.z = $$12;
/* 465 */         this.neck.x = $$10;
/* 466 */         $$11 += Mth.sin(this.neck.xRot) * 10.0F;
/* 467 */         $$12 -= Mth.cos(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 468 */         $$10 -= Mth.sin(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 469 */         this.neck.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */       } 
/*     */       
/* 472 */       this.head.y = $$11;
/* 473 */       this.head.z = $$12;
/* 474 */       this.head.x = $$10;
/* 475 */       double[] $$21 = this.entity.getLatencyPos(0, this.a);
/* 476 */       this.head.yRot = Mth.wrapDegrees((float)($$21[0] - $$14[0])) * 0.017453292F;
/* 477 */       this.head.xRot = Mth.wrapDegrees(this.entity.getHeadPartYOffset(6, $$14, $$21)) * 0.017453292F * 1.5F * 5.0F;
/* 478 */       this.head.zRot = -Mth.wrapDegrees((float)($$21[0] - $$16)) * 0.017453292F;
/* 479 */       this.head.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/* 480 */       $$0.pushPose();
/* 481 */       $$0.translate(0.0F, 1.0F, 0.0F);
/* 482 */       $$0.mulPose(Axis.ZP.rotationDegrees(-$$15 * 1.5F));
/* 483 */       $$0.translate(0.0F, -1.0F, 0.0F);
/* 484 */       this.body.zRot = 0.0F;
/* 485 */       this.body.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */       
/* 487 */       float $$22 = $$8 * 6.2831855F;
/* 488 */       this.leftWing.xRot = 0.125F - (float)Math.cos($$22) * 0.2F;
/* 489 */       this.leftWing.yRot = -0.25F;
/* 490 */       this.leftWing.zRot = -((float)(Math.sin($$22) + 0.125D)) * 0.8F;
/* 491 */       this.leftWingTip.zRot = (float)(Math.sin(($$22 + 2.0F)) + 0.5D) * 0.75F;
/*     */       
/* 493 */       this.rightWing.xRot = this.leftWing.xRot;
/* 494 */       this.rightWing.yRot = -this.leftWing.yRot;
/* 495 */       this.rightWing.zRot = -this.leftWing.zRot;
/* 496 */       this.rightWingTip.zRot = -this.leftWingTip.zRot;
/*     */       
/* 498 */       renderSide($$0, $$1, $$2, $$3, $$9, this.leftWing, this.leftFrontLeg, this.leftFrontLegTip, this.leftFrontFoot, this.leftRearLeg, this.leftRearLegTip, this.leftRearFoot, $$7);
/* 499 */       renderSide($$0, $$1, $$2, $$3, $$9, this.rightWing, this.rightFrontLeg, this.rightFrontLegTip, this.rightFrontFoot, this.rightRearLeg, this.rightRearLegTip, this.rightRearFoot, $$7);
/*     */       
/* 501 */       $$0.popPose();
/*     */       
/* 503 */       float $$23 = -Mth.sin($$8 * 6.2831855F) * 0.0F;
/* 504 */       $$17 = $$8 * 6.2831855F;
/* 505 */       $$11 = 10.0F;
/* 506 */       $$12 = 60.0F;
/* 507 */       $$10 = 0.0F;
/* 508 */       $$14 = this.entity.getLatencyPos(11, this.a);
/* 509 */       for (int $$24 = 0; $$24 < 12; $$24++) {
/* 510 */         $$21 = this.entity.getLatencyPos(12 + $$24, this.a);
/* 511 */         $$23 += Mth.sin($$24 * 0.45F + $$17) * 0.05F;
/* 512 */         this.neck.yRot = (Mth.wrapDegrees((float)($$21[0] - $$14[0])) * 1.5F + 180.0F) * 0.017453292F;
/* 513 */         this.neck.xRot = $$23 + (float)($$21[1] - $$14[1]) * 0.017453292F * 1.5F * 5.0F;
/* 514 */         this.neck.zRot = Mth.wrapDegrees((float)($$21[0] - $$16)) * 0.017453292F * 1.5F;
/* 515 */         this.neck.y = $$11;
/* 516 */         this.neck.z = $$12;
/* 517 */         this.neck.x = $$10;
/* 518 */         $$11 += Mth.sin(this.neck.xRot) * 10.0F;
/* 519 */         $$12 -= Mth.cos(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 520 */         $$10 -= Mth.sin(this.neck.yRot) * Mth.cos(this.neck.xRot) * 10.0F;
/* 521 */         this.neck.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$7);
/*     */       } 
/* 523 */       $$0.popPose();
/*     */     }
/*     */     
/*     */     private void renderSide(PoseStack $$0, VertexConsumer $$1, int $$2, int $$3, float $$4, ModelPart $$5, ModelPart $$6, ModelPart $$7, ModelPart $$8, ModelPart $$9, ModelPart $$10, ModelPart $$11, float $$12) {
/* 527 */       $$9.xRot = 1.0F + $$4 * 0.1F;
/* 528 */       $$10.xRot = 0.5F + $$4 * 0.1F;
/* 529 */       $$11.xRot = 0.75F + $$4 * 0.1F;
/*     */       
/* 531 */       $$6.xRot = 1.3F + $$4 * 0.1F;
/* 532 */       $$7.xRot = -0.5F - $$4 * 0.1F;
/* 533 */       $$8.xRot = 0.75F + $$4 * 0.1F;
/*     */       
/* 535 */       $$5.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/* 536 */       $$6.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/* 537 */       $$9.render($$0, $$1, $$2, $$3, 1.0F, 1.0F, 1.0F, $$12);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EnderDragonRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */