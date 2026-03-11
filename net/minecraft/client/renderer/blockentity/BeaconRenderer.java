/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.entity.BeaconBlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class BeaconRenderer implements BlockEntityRenderer<BeaconBlockEntity> {
/*  20 */   public static final ResourceLocation BEAM_LOCATION = new ResourceLocation("textures/entity/beacon_beam.png");
/*     */   
/*     */   public static final int MAX_RENDER_Y = 1024;
/*     */ 
/*     */   
/*     */   public BeaconRenderer(BlockEntityRendererProvider.Context $$0) {}
/*     */   
/*     */   public void render(BeaconBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*  28 */     long $$6 = $$0.getLevel().getGameTime();
/*     */     
/*  30 */     List<BeaconBlockEntity.BeaconBeamSection> $$7 = $$0.getBeamSections();
/*  31 */     int $$8 = 0;
/*  32 */     for (int $$9 = 0; $$9 < $$7.size(); $$9++) {
/*  33 */       BeaconBlockEntity.BeaconBeamSection $$10 = $$7.get($$9);
/*  34 */       renderBeaconBeam($$2, $$3, $$1, $$6, $$8, ($$9 == $$7.size() - 1) ? 1024 : $$10.getHeight(), $$10.getColor());
/*  35 */       $$8 += $$10.getHeight();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderBeaconBeam(PoseStack $$0, MultiBufferSource $$1, float $$2, long $$3, int $$4, int $$5, float[] $$6) {
/*  40 */     renderBeaconBeam($$0, $$1, BEAM_LOCATION, $$2, 1.0F, $$3, $$4, $$5, $$6, 0.2F, 0.25F);
/*     */   }
/*     */   
/*     */   public static void renderBeaconBeam(PoseStack $$0, MultiBufferSource $$1, ResourceLocation $$2, float $$3, float $$4, long $$5, int $$6, int $$7, float[] $$8, float $$9, float $$10) {
/*  44 */     int $$11 = $$6 + $$7;
/*     */     
/*  46 */     $$0.pushPose();
/*  47 */     $$0.translate(0.5D, 0.0D, 0.5D);
/*     */     
/*  49 */     float $$12 = Math.floorMod($$5, 40) + $$3;
/*     */     
/*  51 */     float $$13 = ($$7 < 0) ? $$12 : -$$12;
/*  52 */     float $$14 = Mth.frac($$13 * 0.2F - Mth.floor($$13 * 0.1F));
/*     */     
/*  54 */     float $$15 = $$8[0];
/*  55 */     float $$16 = $$8[1];
/*  56 */     float $$17 = $$8[2];
/*     */     
/*  58 */     $$0.pushPose();
/*     */     
/*  60 */     $$0.mulPose(Axis.YP.rotationDegrees($$12 * 2.25F - 45.0F));
/*     */     
/*  62 */     float $$18 = 0.0F;
/*  63 */     float $$19 = $$9;
/*  64 */     float $$20 = $$9;
/*  65 */     float $$21 = 0.0F;
/*     */     
/*  67 */     float $$22 = -$$9;
/*  68 */     float $$23 = 0.0F;
/*  69 */     float $$24 = 0.0F;
/*  70 */     float $$25 = -$$9;
/*     */     
/*  72 */     float $$26 = 0.0F;
/*  73 */     float $$27 = 1.0F;
/*  74 */     float $$28 = -1.0F + $$14;
/*  75 */     float $$29 = $$7 * $$4 * 0.5F / $$9 + $$28;
/*     */     
/*  77 */     renderPart($$0, $$1.getBuffer(RenderType.beaconBeam($$2, false)), $$15, $$16, $$17, 1.0F, $$6, $$11, 0.0F, $$19, $$20, 0.0F, $$22, 0.0F, 0.0F, $$25, 0.0F, 1.0F, $$29, $$28);
/*     */     
/*  79 */     $$0.popPose();
/*     */     
/*  81 */     float $$30 = -$$10;
/*  82 */     float $$31 = -$$10;
/*  83 */     float $$32 = $$10;
/*  84 */     float $$33 = -$$10;
/*     */     
/*  86 */     float $$34 = -$$10;
/*  87 */     float $$35 = $$10;
/*  88 */     float $$36 = $$10;
/*  89 */     float $$37 = $$10;
/*     */     
/*  91 */     float $$38 = 0.0F;
/*  92 */     float $$39 = 1.0F;
/*  93 */     float $$40 = -1.0F + $$14;
/*  94 */     float $$41 = $$7 * $$4 + $$40;
/*     */     
/*  96 */     renderPart($$0, $$1.getBuffer(RenderType.beaconBeam($$2, true)), $$15, $$16, $$17, 0.125F, $$6, $$11, $$30, $$31, $$32, $$33, $$34, $$35, $$36, $$37, 0.0F, 1.0F, $$41, $$40);
/*     */     
/*  98 */     $$0.popPose();
/*     */   }
/*     */   
/*     */   private static void renderPart(PoseStack $$0, VertexConsumer $$1, float $$2, float $$3, float $$4, float $$5, int $$6, int $$7, float $$8, float $$9, float $$10, float $$11, float $$12, float $$13, float $$14, float $$15, float $$16, float $$17, float $$18, float $$19) {
/* 102 */     PoseStack.Pose $$20 = $$0.last();
/* 103 */     Matrix4f $$21 = $$20.pose();
/* 104 */     Matrix3f $$22 = $$20.normal();
/* 105 */     renderQuad($$21, $$22, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$16, $$17, $$18, $$19);
/* 106 */     renderQuad($$21, $$22, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$14, $$15, $$12, $$13, $$16, $$17, $$18, $$19);
/* 107 */     renderQuad($$21, $$22, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$10, $$11, $$14, $$15, $$16, $$17, $$18, $$19);
/* 108 */     renderQuad($$21, $$22, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$12, $$13, $$8, $$9, $$16, $$17, $$18, $$19);
/*     */   }
/*     */   
/*     */   private static void renderQuad(Matrix4f $$0, Matrix3f $$1, VertexConsumer $$2, float $$3, float $$4, float $$5, float $$6, int $$7, int $$8, float $$9, float $$10, float $$11, float $$12, float $$13, float $$14, float $$15, float $$16) {
/* 112 */     addVertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$8, $$9, $$10, $$14, $$15);
/* 113 */     addVertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$9, $$10, $$14, $$16);
/* 114 */     addVertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$11, $$12, $$13, $$16);
/* 115 */     addVertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$8, $$11, $$12, $$13, $$15);
/*     */   }
/*     */   
/*     */   private static void addVertex(Matrix4f $$0, Matrix3f $$1, VertexConsumer $$2, float $$3, float $$4, float $$5, float $$6, int $$7, float $$8, float $$9, float $$10, float $$11) {
/* 119 */     $$2.vertex($$0, $$8, $$7, $$9).color($$3, $$4, $$5, $$6).uv($$10, $$11).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(15728880).normal($$1, 0.0F, 1.0F, 0.0F).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderOffScreen(BeaconBlockEntity $$0) {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getViewDistance() {
/* 129 */     return 256;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(BeaconBlockEntity $$0, Vec3 $$1) {
/* 134 */     return Vec3.atCenterOf((Vec3i)$$0.getBlockPos()).multiply(1.0D, 0.0D, 1.0D).closerThan((Position)$$1.multiply(1.0D, 0.0D, 1.0D), getViewDistance());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\BeaconRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */