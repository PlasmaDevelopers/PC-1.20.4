/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.EntityHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Quaternionfc;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DebugRenderer
/*     */ {
/*     */   public final PathfindingRenderer pathfindingRenderer;
/*     */   public final SimpleDebugRenderer waterDebugRenderer;
/*     */   public final SimpleDebugRenderer chunkBorderRenderer;
/*     */   public final SimpleDebugRenderer heightMapRenderer;
/*     */   public final SimpleDebugRenderer collisionBoxRenderer;
/*     */   public final SimpleDebugRenderer supportBlockRenderer;
/*     */   public final SimpleDebugRenderer neighborsUpdateRenderer;
/*     */   public final StructureRenderer structureRenderer;
/*     */   public final SimpleDebugRenderer lightDebugRenderer;
/*     */   public final SimpleDebugRenderer worldGenAttemptRenderer;
/*     */   public final SimpleDebugRenderer solidFaceRenderer;
/*     */   public final SimpleDebugRenderer chunkRenderer;
/*     */   public final BrainDebugRenderer brainDebugRenderer;
/*     */   public final VillageSectionsDebugRenderer villageSectionsDebugRenderer;
/*     */   public final BeeDebugRenderer beeDebugRenderer;
/*     */   public final RaidDebugRenderer raidDebugRenderer;
/*     */   public final GoalSelectorDebugRenderer goalSelectorRenderer;
/*     */   public final GameTestDebugRenderer gameTestDebugRenderer;
/*     */   public final GameEventListenerRenderer gameEventListenerRenderer;
/*     */   public final LightSectionDebugRenderer skyLightSectionDebugRenderer;
/*     */   public final BreezeDebugRenderer breezeDebugRenderer;
/*     */   private boolean renderChunkborder;
/*     */   
/*     */   public DebugRenderer(Minecraft $$0) {
/*  52 */     this.pathfindingRenderer = new PathfindingRenderer();
/*  53 */     this.waterDebugRenderer = new WaterDebugRenderer($$0);
/*  54 */     this.chunkBorderRenderer = new ChunkBorderRenderer($$0);
/*  55 */     this.heightMapRenderer = new HeightMapRenderer($$0);
/*  56 */     this.collisionBoxRenderer = new CollisionBoxRenderer($$0);
/*  57 */     this.supportBlockRenderer = new SupportBlockRenderer($$0);
/*  58 */     this.neighborsUpdateRenderer = new NeighborsUpdateRenderer($$0);
/*  59 */     this.structureRenderer = new StructureRenderer($$0);
/*  60 */     this.lightDebugRenderer = new LightDebugRenderer($$0);
/*  61 */     this.worldGenAttemptRenderer = new WorldGenAttemptRenderer();
/*  62 */     this.solidFaceRenderer = new SolidFaceRenderer($$0);
/*  63 */     this.chunkRenderer = new ChunkDebugRenderer($$0);
/*  64 */     this.brainDebugRenderer = new BrainDebugRenderer($$0);
/*  65 */     this.villageSectionsDebugRenderer = new VillageSectionsDebugRenderer();
/*  66 */     this.beeDebugRenderer = new BeeDebugRenderer($$0);
/*  67 */     this.raidDebugRenderer = new RaidDebugRenderer($$0);
/*  68 */     this.goalSelectorRenderer = new GoalSelectorDebugRenderer($$0);
/*  69 */     this.gameTestDebugRenderer = new GameTestDebugRenderer();
/*  70 */     this.gameEventListenerRenderer = new GameEventListenerRenderer($$0);
/*  71 */     this.skyLightSectionDebugRenderer = new LightSectionDebugRenderer($$0, LightLayer.SKY);
/*  72 */     this.breezeDebugRenderer = new BreezeDebugRenderer($$0);
/*     */   }
/*     */   
/*     */   public void clear() {
/*  76 */     this.pathfindingRenderer.clear();
/*  77 */     this.waterDebugRenderer.clear();
/*  78 */     this.chunkBorderRenderer.clear();
/*  79 */     this.heightMapRenderer.clear();
/*  80 */     this.collisionBoxRenderer.clear();
/*  81 */     this.supportBlockRenderer.clear();
/*  82 */     this.neighborsUpdateRenderer.clear();
/*  83 */     this.structureRenderer.clear();
/*  84 */     this.lightDebugRenderer.clear();
/*  85 */     this.worldGenAttemptRenderer.clear();
/*  86 */     this.solidFaceRenderer.clear();
/*  87 */     this.chunkRenderer.clear();
/*  88 */     this.brainDebugRenderer.clear();
/*  89 */     this.villageSectionsDebugRenderer.clear();
/*  90 */     this.beeDebugRenderer.clear();
/*  91 */     this.raidDebugRenderer.clear();
/*  92 */     this.goalSelectorRenderer.clear();
/*  93 */     this.gameTestDebugRenderer.clear();
/*  94 */     this.gameEventListenerRenderer.clear();
/*  95 */     this.skyLightSectionDebugRenderer.clear();
/*  96 */     this.breezeDebugRenderer.clear();
/*     */   }
/*     */   
/*     */   public boolean switchRenderChunkborder() {
/* 100 */     this.renderChunkborder = !this.renderChunkborder;
/* 101 */     return this.renderChunkborder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource.BufferSource $$1, double $$2, double $$3, double $$4) {
/* 109 */     if (this.renderChunkborder && !Minecraft.getInstance().showOnlyReducedInfo()) {
/* 110 */       this.chunkBorderRenderer.render($$0, (MultiBufferSource)$$1, $$2, $$3, $$4);
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
/*     */     
/* 185 */     this.gameTestDebugRenderer.render($$0, (MultiBufferSource)$$1, $$2, $$3, $$4);
/*     */   }
/*     */   
/*     */   public static Optional<Entity> getTargetedEntity(@Nullable Entity $$0, int $$1) {
/* 189 */     if ($$0 == null) {
/* 190 */       return Optional.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 195 */     Vec3 $$2 = $$0.getEyePosition();
/* 196 */     Vec3 $$3 = $$0.getViewVector(1.0F).scale($$1);
/* 197 */     Vec3 $$4 = $$2.add($$3);
/*     */     
/* 199 */     AABB $$5 = $$0.getBoundingBox().expandTowards($$3).inflate(1.0D);
/*     */     
/* 201 */     int $$6 = $$1 * $$1;
/* 202 */     Predicate<Entity> $$7 = $$0 -> (!$$0.isSpectator() && $$0.isPickable());
/* 203 */     EntityHitResult $$8 = ProjectileUtil.getEntityHitResult($$0, $$2, $$4, $$5, $$7, $$6);
/* 204 */     if ($$8 == null) {
/* 205 */       return Optional.empty();
/*     */     }
/*     */     
/* 208 */     if ($$2.distanceToSqr($$8.getLocation()) > $$6)
/*     */     {
/* 210 */       return Optional.empty();
/*     */     }
/*     */     
/* 213 */     return Optional.of($$8.getEntity());
/*     */   }
/*     */   
/*     */   public static void renderFilledUnitCube(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, float $$3, float $$4, float $$5, float $$6) {
/* 217 */     renderFilledBox($$0, $$1, $$2, $$2.offset(1, 1, 1), $$3, $$4, $$5, $$6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderFilledBox(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, BlockPos $$3, float $$4, float $$5, float $$6, float $$7) {
/* 224 */     Camera $$8 = (Minecraft.getInstance()).gameRenderer.getMainCamera();
/* 225 */     if (!$$8.isInitialized()) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     Vec3 $$9 = $$8.getPosition().reverse();
/* 230 */     AABB $$10 = AABB.encapsulatingFullBlocks($$2, $$3).move($$9);
/* 231 */     renderFilledBox($$0, $$1, $$10, $$4, $$5, $$6, $$7);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderFilledBox(PoseStack $$0, MultiBufferSource $$1, BlockPos $$2, float $$3, float $$4, float $$5, float $$6, float $$7) {
/* 238 */     Camera $$8 = (Minecraft.getInstance()).gameRenderer.getMainCamera();
/* 239 */     if (!$$8.isInitialized()) {
/*     */       return;
/*     */     }
/*     */     
/* 243 */     Vec3 $$9 = $$8.getPosition().reverse();
/* 244 */     AABB $$10 = (new AABB($$2)).move($$9).inflate($$3);
/* 245 */     renderFilledBox($$0, $$1, $$10, $$4, $$5, $$6, $$7);
/*     */   }
/*     */   
/*     */   public static void renderFilledBox(PoseStack $$0, MultiBufferSource $$1, AABB $$2, float $$3, float $$4, float $$5, float $$6) {
/* 249 */     renderFilledBox($$0, $$1, $$2.minX, $$2.minY, $$2.minZ, $$2.maxX, $$2.maxY, $$2.maxZ, $$3, $$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public static void renderFilledBox(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4, double $$5, double $$6, double $$7, float $$8, float $$9, float $$10, float $$11) {
/* 253 */     VertexConsumer $$12 = $$1.getBuffer(RenderType.debugFilledBox());
/* 254 */     LevelRenderer.addChainedFilledBoxVertices($$0, $$12, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11);
/*     */   }
/*     */   
/*     */   public static void renderFloatingText(PoseStack $$0, MultiBufferSource $$1, String $$2, int $$3, int $$4, int $$5, int $$6) {
/* 258 */     renderFloatingText($$0, $$1, $$2, $$3 + 0.5D, $$4 + 0.5D, $$5 + 0.5D, $$6);
/*     */   }
/*     */   
/*     */   public static void renderFloatingText(PoseStack $$0, MultiBufferSource $$1, String $$2, double $$3, double $$4, double $$5, int $$6) {
/* 262 */     renderFloatingText($$0, $$1, $$2, $$3, $$4, $$5, $$6, 0.02F);
/*     */   }
/*     */   
/*     */   public static void renderFloatingText(PoseStack $$0, MultiBufferSource $$1, String $$2, double $$3, double $$4, double $$5, int $$6, float $$7) {
/* 266 */     renderFloatingText($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, true, 0.0F, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderFloatingText(PoseStack $$0, MultiBufferSource $$1, String $$2, double $$3, double $$4, double $$5, int $$6, float $$7, boolean $$8, float $$9, boolean $$10) {
/* 276 */     Minecraft $$11 = Minecraft.getInstance();
/* 277 */     Camera $$12 = $$11.gameRenderer.getMainCamera();
/*     */     
/* 279 */     if (!$$12.isInitialized() || ($$11.getEntityRenderDispatcher()).options == null) {
/*     */       return;
/*     */     }
/*     */     
/* 283 */     Font $$13 = $$11.font;
/* 284 */     double $$14 = ($$12.getPosition()).x;
/* 285 */     double $$15 = ($$12.getPosition()).y;
/* 286 */     double $$16 = ($$12.getPosition()).z;
/*     */     
/* 288 */     $$0.pushPose();
/* 289 */     $$0.translate((float)($$3 - $$14), (float)($$4 - $$15) + 0.07F, (float)($$5 - $$16));
/* 290 */     $$0.mulPoseMatrix((new Matrix4f()).rotation((Quaternionfc)$$12.rotation()));
/* 291 */     $$0.scale(-$$7, -$$7, $$7);
/*     */     
/* 293 */     float $$17 = $$8 ? (-$$13.width($$2) / 2.0F) : 0.0F;
/* 294 */     $$17 -= $$9 / $$7;
/*     */     
/* 296 */     $$13.drawInBatch($$2, $$17, 0.0F, $$6, false, $$0.last().pose(), $$1, $$10 ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, 0, 15728880);
/*     */     
/* 298 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   public static interface SimpleDebugRenderer {
/*     */     void render(PoseStack param1PoseStack, MultiBufferSource param1MultiBufferSource, double param1Double1, double param1Double2, double param1Double3);
/*     */     
/*     */     default void clear() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\DebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */