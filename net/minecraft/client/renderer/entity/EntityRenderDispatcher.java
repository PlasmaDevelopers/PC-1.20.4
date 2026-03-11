/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.model.geom.EntityModelSet;
/*     */ import net.minecraft.client.player.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.ItemInHandRenderer;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.LightTexture;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.Sheets;
/*     */ import net.minecraft.client.renderer.block.BlockRenderDispatcher;
/*     */ import net.minecraft.client.renderer.culling.Frustum;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.boss.EnderDragonPart;
/*     */ import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class EntityRenderDispatcher
/*     */   implements ResourceManagerReloadListener {
/*  58 */   private static final RenderType SHADOW_RENDER_TYPE = RenderType.entityShadow(new ResourceLocation("textures/misc/shadow.png"));
/*     */   
/*     */   private static final float MAX_SHADOW_RADIUS = 32.0F;
/*     */   private static final float SHADOW_POWER_FALLOFF_Y = 0.5F;
/*  62 */   private Map<EntityType<?>, EntityRenderer<?>> renderers = (Map<EntityType<?>, EntityRenderer<?>>)ImmutableMap.of();
/*  63 */   private Map<PlayerSkin.Model, EntityRenderer<? extends Player>> playerRenderers = Map.of();
/*     */   
/*     */   public final TextureManager textureManager;
/*     */   
/*     */   private Level level;
/*     */   public Camera camera;
/*     */   private Quaternionf cameraOrientation;
/*     */   public Entity crosshairPickEntity;
/*     */   private final ItemRenderer itemRenderer;
/*     */   private final BlockRenderDispatcher blockRenderDispatcher;
/*     */   private final ItemInHandRenderer itemInHandRenderer;
/*     */   private final Font font;
/*     */   public final Options options;
/*     */   private final EntityModelSet entityModels;
/*     */   private boolean shouldRenderShadow = true;
/*     */   private boolean renderHitBoxes;
/*     */   
/*     */   public <E extends Entity> int getPackedLightCoords(E $$0, float $$1) {
/*  81 */     return getRenderer($$0).getPackedLightCoords($$0, $$1);
/*     */   }
/*     */   
/*     */   public EntityRenderDispatcher(Minecraft $$0, TextureManager $$1, ItemRenderer $$2, BlockRenderDispatcher $$3, Font $$4, Options $$5, EntityModelSet $$6) {
/*  85 */     this.textureManager = $$1;
/*  86 */     this.itemRenderer = $$2;
/*  87 */     this.itemInHandRenderer = new ItemInHandRenderer($$0, this, $$2);
/*  88 */     this.blockRenderDispatcher = $$3;
/*  89 */     this.font = $$4;
/*  90 */     this.options = $$5;
/*  91 */     this.entityModels = $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T extends Entity> EntityRenderer<? super T> getRenderer(T $$0) {
/*  96 */     if ($$0 instanceof AbstractClientPlayer) { AbstractClientPlayer $$1 = (AbstractClientPlayer)$$0;
/*  97 */       PlayerSkin.Model $$2 = $$1.getSkin().model();
/*  98 */       EntityRenderer<? extends Player> $$3 = this.playerRenderers.get($$2);
/*  99 */       if ($$3 != null) {
/* 100 */         return (EntityRenderer)$$3;
/*     */       }
/* 102 */       return (EntityRenderer<? super T>)this.playerRenderers.get(PlayerSkin.Model.WIDE); }
/*     */     
/* 104 */     return (EntityRenderer<? super T>)this.renderers.get($$0.getType());
/*     */   }
/*     */   
/*     */   public void prepare(Level $$0, Camera $$1, Entity $$2) {
/* 108 */     this.level = $$0;
/* 109 */     this.camera = $$1;
/* 110 */     this.cameraOrientation = $$1.rotation();
/* 111 */     this.crosshairPickEntity = $$2;
/*     */   }
/*     */   
/*     */   public void overrideCameraOrientation(Quaternionf $$0) {
/* 115 */     this.cameraOrientation = $$0;
/*     */   }
/*     */   
/*     */   public void setRenderShadow(boolean $$0) {
/* 119 */     this.shouldRenderShadow = $$0;
/*     */   }
/*     */   
/*     */   public void setRenderHitBoxes(boolean $$0) {
/* 123 */     this.renderHitBoxes = $$0;
/*     */   }
/*     */   
/*     */   public boolean shouldRenderHitBoxes() {
/* 127 */     return this.renderHitBoxes;
/*     */   }
/*     */   
/*     */   public <E extends Entity> boolean shouldRender(E $$0, Frustum $$1, double $$2, double $$3, double $$4) {
/* 131 */     EntityRenderer<? super E> $$5 = getRenderer($$0);
/* 132 */     return $$5.shouldRender($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public <E extends Entity> void render(E $$0, double $$1, double $$2, double $$3, float $$4, float $$5, PoseStack $$6, MultiBufferSource $$7, int $$8) {
/* 136 */     EntityRenderer<? super E> $$9 = getRenderer($$0);
/*     */     
/*     */     try {
/* 139 */       Vec3 $$10 = $$9.getRenderOffset($$0, $$5);
/* 140 */       double $$11 = $$1 + $$10.x();
/* 141 */       double $$12 = $$2 + $$10.y();
/* 142 */       double $$13 = $$3 + $$10.z();
/*     */       
/* 144 */       $$6.pushPose();
/* 145 */       $$6.translate($$11, $$12, $$13);
/* 146 */       $$9.render($$0, $$4, $$5, $$6, $$7, $$8);
/*     */       
/* 148 */       if ($$0.displayFireAnimation()) {
/* 149 */         renderFlame($$6, $$7, (Entity)$$0, Mth.rotationAroundAxis(Mth.Y_AXIS, this.cameraOrientation, new Quaternionf()));
/*     */       }
/*     */       
/* 152 */       $$6.translate(-$$10.x(), -$$10.y(), -$$10.z());
/*     */       
/* 154 */       if (((Boolean)this.options.entityShadows().get()).booleanValue() && this.shouldRenderShadow && $$9.shadowRadius > 0.0F && !$$0.isInvisible()) {
/* 155 */         double $$14 = distanceToSqr($$0.getX(), $$0.getY(), $$0.getZ());
/* 156 */         float $$15 = (float)((1.0D - $$14 / 256.0D) * $$9.shadowStrength);
/* 157 */         if ($$15 > 0.0F) {
/* 158 */           renderShadow($$6, $$7, (Entity)$$0, $$15, $$5, (LevelReader)this.level, Math.min($$9.shadowRadius, 32.0F));
/*     */         }
/*     */       } 
/*     */       
/* 162 */       if (this.renderHitBoxes && !$$0.isInvisible() && !Minecraft.getInstance().showOnlyReducedInfo()) {
/* 163 */         renderHitbox($$6, $$7.getBuffer(RenderType.lines()), (Entity)$$0, $$5);
/*     */       }
/* 165 */       $$6.popPose();
/* 166 */     } catch (Throwable $$16) {
/* 167 */       CrashReport $$17 = CrashReport.forThrowable($$16, "Rendering entity in world");
/* 168 */       CrashReportCategory $$18 = $$17.addCategory("Entity being rendered");
/* 169 */       $$0.fillCrashReportCategory($$18);
/*     */       
/* 171 */       CrashReportCategory $$19 = $$17.addCategory("Renderer details");
/* 172 */       $$19.setDetail("Assigned renderer", $$9);
/* 173 */       $$19.setDetail("Location", CrashReportCategory.formatLocation((LevelHeightAccessor)this.level, $$1, $$2, $$3));
/* 174 */       $$19.setDetail("Rotation", Float.valueOf($$4));
/* 175 */       $$19.setDetail("Delta", Float.valueOf($$5));
/*     */       
/* 177 */       throw new ReportedException($$17);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderHitbox(PoseStack $$0, VertexConsumer $$1, Entity $$2, float $$3) {
/* 182 */     AABB $$4 = $$2.getBoundingBox().move(-$$2.getX(), -$$2.getY(), -$$2.getZ());
/* 183 */     LevelRenderer.renderLineBox($$0, $$1, $$4, 1.0F, 1.0F, 1.0F, 1.0F);
/* 184 */     if ($$2 instanceof EnderDragon) {
/* 185 */       double $$5 = -Mth.lerp($$3, $$2.xOld, $$2.getX());
/* 186 */       double $$6 = -Mth.lerp($$3, $$2.yOld, $$2.getY());
/* 187 */       double $$7 = -Mth.lerp($$3, $$2.zOld, $$2.getZ());
/*     */       
/* 189 */       for (EnderDragonPart $$8 : ((EnderDragon)$$2).getSubEntities()) {
/* 190 */         $$0.pushPose();
/* 191 */         double $$9 = $$5 + Mth.lerp($$3, $$8.xOld, $$8.getX());
/* 192 */         double $$10 = $$6 + Mth.lerp($$3, $$8.yOld, $$8.getY());
/* 193 */         double $$11 = $$7 + Mth.lerp($$3, $$8.zOld, $$8.getZ());
/*     */         
/* 195 */         $$0.translate($$9, $$10, $$11);
/* 196 */         LevelRenderer.renderLineBox($$0, $$1, $$8.getBoundingBox().move(-$$8.getX(), -$$8.getY(), -$$8.getZ()), 0.25F, 1.0F, 0.0F, 1.0F);
/* 197 */         $$0.popPose();
/*     */       } 
/*     */     } 
/* 200 */     if ($$2 instanceof net.minecraft.world.entity.LivingEntity) {
/* 201 */       float $$12 = 0.01F;
/*     */       
/* 203 */       LevelRenderer.renderLineBox($$0, $$1, $$4.minX, ($$2
/*     */ 
/*     */ 
/*     */           
/* 207 */           .getEyeHeight() - 0.01F), $$4.minZ, $$4.maxX, ($$2
/*     */ 
/*     */           
/* 210 */           .getEyeHeight() + 0.01F), $$4.maxZ, 1.0F, 0.0F, 0.0F, 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 216 */     Entity $$13 = $$2.getVehicle();
/* 217 */     if ($$13 != null) {
/* 218 */       float $$14 = Math.min($$13.getBbWidth(), $$2.getBbWidth()) / 2.0F;
/* 219 */       float $$15 = 0.0625F;
/* 220 */       Vec3 $$16 = $$13.getPassengerRidingPosition($$2).subtract($$2.position());
/* 221 */       LevelRenderer.renderLineBox($$0, $$1, $$16.x - $$14, $$16.y, $$16.z - $$14, $$16.x + $$14, $$16.y + 0.0625D, $$16.z + $$14, 1.0F, 1.0F, 0.0F, 1.0F);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     Vec3 $$17 = $$2.getViewVector($$3);
/* 236 */     Matrix4f $$18 = $$0.last().pose();
/* 237 */     Matrix3f $$19 = $$0.last().normal();
/* 238 */     $$1.vertex($$18, 0.0F, $$2.getEyeHeight(), 0.0F).color(0, 0, 255, 255).normal($$19, (float)$$17.x, (float)$$17.y, (float)$$17.z).endVertex();
/* 239 */     $$1.vertex($$18, (float)($$17.x * 2.0D), (float)($$2.getEyeHeight() + $$17.y * 2.0D), (float)($$17.z * 2.0D)).color(0, 0, 255, 255).normal($$19, (float)$$17.x, (float)$$17.y, (float)$$17.z).endVertex();
/*     */   }
/*     */   
/*     */   private void renderFlame(PoseStack $$0, MultiBufferSource $$1, Entity $$2, Quaternionf $$3) {
/* 243 */     TextureAtlasSprite $$4 = ModelBakery.FIRE_0.sprite();
/* 244 */     TextureAtlasSprite $$5 = ModelBakery.FIRE_1.sprite();
/*     */     
/* 246 */     $$0.pushPose();
/*     */     
/* 248 */     float $$6 = $$2.getBbWidth() * 1.4F;
/* 249 */     $$0.scale($$6, $$6, $$6);
/*     */     
/* 251 */     float $$7 = 0.5F;
/* 252 */     float $$8 = 0.0F;
/*     */     
/* 254 */     float $$9 = $$2.getBbHeight() / $$6;
/* 255 */     float $$10 = 0.0F;
/*     */     
/* 257 */     $$0.mulPose($$3);
/*     */     
/* 259 */     $$0.translate(0.0F, 0.0F, -0.3F + (int)$$9 * 0.02F);
/* 260 */     float $$11 = 0.0F;
/* 261 */     int $$12 = 0;
/* 262 */     VertexConsumer $$13 = $$1.getBuffer(Sheets.cutoutBlockSheet());
/*     */     
/* 264 */     PoseStack.Pose $$14 = $$0.last();
/*     */     
/* 266 */     while ($$9 > 0.0F) {
/* 267 */       TextureAtlasSprite $$15 = ($$12 % 2 == 0) ? $$4 : $$5;
/*     */       
/* 269 */       float $$16 = $$15.getU0();
/* 270 */       float $$17 = $$15.getV0();
/* 271 */       float $$18 = $$15.getU1();
/* 272 */       float $$19 = $$15.getV1();
/* 273 */       if ($$12 / 2 % 2 == 0) {
/* 274 */         float $$20 = $$18;
/* 275 */         $$18 = $$16;
/* 276 */         $$16 = $$20;
/*     */       } 
/* 278 */       fireVertex($$14, $$13, $$7 - 0.0F, 0.0F - $$10, $$11, $$18, $$19);
/* 279 */       fireVertex($$14, $$13, -$$7 - 0.0F, 0.0F - $$10, $$11, $$16, $$19);
/* 280 */       fireVertex($$14, $$13, -$$7 - 0.0F, 1.4F - $$10, $$11, $$16, $$17);
/* 281 */       fireVertex($$14, $$13, $$7 - 0.0F, 1.4F - $$10, $$11, $$18, $$17);
/* 282 */       $$9 -= 0.45F;
/* 283 */       $$10 -= 0.45F;
/* 284 */       $$7 *= 0.9F;
/* 285 */       $$11 += 0.03F;
/* 286 */       $$12++;
/*     */     } 
/* 288 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   private static void fireVertex(PoseStack.Pose $$0, VertexConsumer $$1, float $$2, float $$3, float $$4, float $$5, float $$6) {
/* 292 */     $$1.vertex($$0.pose(), $$2, $$3, $$4).color(255, 255, 255, 255).uv($$5, $$6).overlayCoords(0, 10).uv2(240).normal($$0.normal(), 0.0F, 1.0F, 0.0F).endVertex();
/*     */   }
/*     */   
/*     */   private static void renderShadow(PoseStack $$0, MultiBufferSource $$1, Entity $$2, float $$3, float $$4, LevelReader $$5, float $$6) {
/* 296 */     float $$7 = $$6;
/* 297 */     if ($$2 instanceof Mob) { Mob $$8 = (Mob)$$2;
/* 298 */       if ($$8.isBaby()) {
/* 299 */         $$7 *= 0.5F;
/*     */       } }
/*     */ 
/*     */     
/* 303 */     double $$9 = Mth.lerp($$4, $$2.xOld, $$2.getX());
/* 304 */     double $$10 = Mth.lerp($$4, $$2.yOld, $$2.getY());
/* 305 */     double $$11 = Mth.lerp($$4, $$2.zOld, $$2.getZ());
/*     */     
/* 307 */     float $$12 = Math.min($$3 / 0.5F, $$7);
/*     */     
/* 309 */     int $$13 = Mth.floor($$9 - $$7);
/* 310 */     int $$14 = Mth.floor($$9 + $$7);
/* 311 */     int $$15 = Mth.floor($$10 - $$12);
/* 312 */     int $$16 = Mth.floor($$10);
/* 313 */     int $$17 = Mth.floor($$11 - $$7);
/* 314 */     int $$18 = Mth.floor($$11 + $$7);
/*     */     
/* 316 */     PoseStack.Pose $$19 = $$0.last();
/*     */     
/* 318 */     VertexConsumer $$20 = $$1.getBuffer(SHADOW_RENDER_TYPE);
/* 319 */     BlockPos.MutableBlockPos $$21 = new BlockPos.MutableBlockPos();
/* 320 */     for (int $$22 = $$17; $$22 <= $$18; $$22++) {
/* 321 */       for (int $$23 = $$13; $$23 <= $$14; $$23++) {
/* 322 */         $$21.set($$23, 0, $$22);
/* 323 */         ChunkAccess $$24 = $$5.getChunk((BlockPos)$$21);
/* 324 */         for (int $$25 = $$15; $$25 <= $$16; $$25++) {
/* 325 */           $$21.setY($$25);
/* 326 */           float $$26 = $$3 - (float)($$10 - $$21.getY()) * 0.5F;
/* 327 */           renderBlockShadow($$19, $$20, $$24, $$5, (BlockPos)$$21, $$9, $$10, $$11, $$7, $$26);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderBlockShadow(PoseStack.Pose $$0, VertexConsumer $$1, ChunkAccess $$2, LevelReader $$3, BlockPos $$4, double $$5, double $$6, double $$7, float $$8, float $$9) {
/* 334 */     BlockPos $$10 = $$4.below();
/* 335 */     BlockState $$11 = $$2.getBlockState($$10);
/* 336 */     if ($$11.getRenderShape() == RenderShape.INVISIBLE || $$3.getMaxLocalRawBrightness($$4) <= 3) {
/*     */       return;
/*     */     }
/*     */     
/* 340 */     if (!$$11.isCollisionShapeFullBlock((BlockGetter)$$2, $$10)) {
/*     */       return;
/*     */     }
/*     */     
/* 344 */     VoxelShape $$12 = $$11.getShape((BlockGetter)$$2, $$10);
/* 345 */     if ($$12.isEmpty()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 350 */     float $$13 = LightTexture.getBrightness($$3.dimensionType(), $$3.getMaxLocalRawBrightness($$4));
/* 351 */     float $$14 = $$9 * 0.5F * $$13;
/* 352 */     if ($$14 >= 0.0F) {
/* 353 */       if ($$14 > 1.0F) {
/* 354 */         $$14 = 1.0F;
/*     */       }
/*     */ 
/*     */       
/* 358 */       AABB $$15 = $$12.bounds();
/*     */       
/* 360 */       double $$16 = $$4.getX() + $$15.minX;
/* 361 */       double $$17 = $$4.getX() + $$15.maxX;
/* 362 */       double $$18 = $$4.getY() + $$15.minY;
/* 363 */       double $$19 = $$4.getZ() + $$15.minZ;
/* 364 */       double $$20 = $$4.getZ() + $$15.maxZ;
/*     */       
/* 366 */       float $$21 = (float)($$16 - $$5);
/* 367 */       float $$22 = (float)($$17 - $$5);
/* 368 */       float $$23 = (float)($$18 - $$6);
/* 369 */       float $$24 = (float)($$19 - $$7);
/* 370 */       float $$25 = (float)($$20 - $$7);
/*     */       
/* 372 */       float $$26 = -$$21 / 2.0F / $$8 + 0.5F;
/* 373 */       float $$27 = -$$22 / 2.0F / $$8 + 0.5F;
/* 374 */       float $$28 = -$$24 / 2.0F / $$8 + 0.5F;
/* 375 */       float $$29 = -$$25 / 2.0F / $$8 + 0.5F;
/*     */       
/* 377 */       shadowVertex($$0, $$1, $$14, $$21, $$23, $$24, $$26, $$28);
/* 378 */       shadowVertex($$0, $$1, $$14, $$21, $$23, $$25, $$26, $$29);
/* 379 */       shadowVertex($$0, $$1, $$14, $$22, $$23, $$25, $$27, $$29);
/* 380 */       shadowVertex($$0, $$1, $$14, $$22, $$23, $$24, $$27, $$28);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void shadowVertex(PoseStack.Pose $$0, VertexConsumer $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7) {
/* 385 */     Vector3f $$8 = $$0.pose().transformPosition($$3, $$4, $$5, new Vector3f());
/* 386 */     $$1.vertex($$8.x(), $$8.y(), $$8.z(), 1.0F, 1.0F, 1.0F, $$2, $$6, $$7, OverlayTexture.NO_OVERLAY, 15728880, 0.0F, 1.0F, 0.0F);
/*     */   }
/*     */   
/*     */   public void setLevel(@Nullable Level $$0) {
/* 390 */     this.level = $$0;
/* 391 */     if ($$0 == null) {
/* 392 */       this.camera = null;
/*     */     }
/*     */   }
/*     */   
/*     */   public double distanceToSqr(Entity $$0) {
/* 397 */     return this.camera.getPosition().distanceToSqr($$0.position());
/*     */   }
/*     */   
/*     */   public double distanceToSqr(double $$0, double $$1, double $$2) {
/* 401 */     return this.camera.getPosition().distanceToSqr($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public Quaternionf cameraOrientation() {
/* 405 */     return this.cameraOrientation;
/*     */   }
/*     */   
/*     */   public ItemInHandRenderer getItemInHandRenderer() {
/* 409 */     return this.itemInHandRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onResourceManagerReload(ResourceManager $$0) {
/* 414 */     EntityRendererProvider.Context $$1 = new EntityRendererProvider.Context(this, this.itemRenderer, this.blockRenderDispatcher, this.itemInHandRenderer, $$0, this.entityModels, this.font);
/* 415 */     this.renderers = EntityRenderers.createEntityRenderers($$1);
/* 416 */     this.playerRenderers = EntityRenderers.createPlayerRenderers($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\EntityRenderDispatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */