/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import com.mojang.math.Axis;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.OverlayTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.PaintingTextureManager;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.decoration.Painting;
/*     */ import net.minecraft.world.entity.decoration.PaintingVariant;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class PaintingRenderer extends EntityRenderer<Painting> {
/*     */   public PaintingRenderer(EntityRendererProvider.Context $$0) {
/*  25 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Painting $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  30 */     $$3.pushPose();
/*  31 */     $$3.mulPose(Axis.YP.rotationDegrees(180.0F - $$1));
/*     */     
/*  33 */     PaintingVariant $$6 = (PaintingVariant)$$0.getVariant().value();
/*     */     
/*  35 */     float $$7 = 0.0625F;
/*  36 */     $$3.scale(0.0625F, 0.0625F, 0.0625F);
/*     */     
/*  38 */     VertexConsumer $$8 = $$4.getBuffer(RenderType.entitySolid(getTextureLocation($$0)));
/*     */     
/*  40 */     PaintingTextureManager $$9 = Minecraft.getInstance().getPaintingTextures();
/*  41 */     renderPainting($$3, $$8, $$0, $$6.getWidth(), $$6.getHeight(), $$9.get($$6), $$9.getBackSprite());
/*  42 */     $$3.popPose();
/*     */     
/*  44 */     super.render($$0, $$1, $$2, $$3, $$4, $$5);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(Painting $$0) {
/*  49 */     return Minecraft.getInstance().getPaintingTextures().getBackSprite().atlasLocation();
/*     */   }
/*     */   
/*     */   private void renderPainting(PoseStack $$0, VertexConsumer $$1, Painting $$2, int $$3, int $$4, TextureAtlasSprite $$5, TextureAtlasSprite $$6) {
/*  53 */     PoseStack.Pose $$7 = $$0.last();
/*  54 */     Matrix4f $$8 = $$7.pose();
/*  55 */     Matrix3f $$9 = $$7.normal();
/*     */     
/*  57 */     float $$10 = -$$3 / 2.0F;
/*  58 */     float $$11 = -$$4 / 2.0F;
/*     */     
/*  60 */     float $$12 = 0.5F;
/*     */ 
/*     */     
/*  63 */     float $$13 = $$6.getU0();
/*  64 */     float $$14 = $$6.getU1();
/*  65 */     float $$15 = $$6.getV0();
/*  66 */     float $$16 = $$6.getV1();
/*     */ 
/*     */     
/*  69 */     float $$17 = $$6.getU0();
/*  70 */     float $$18 = $$6.getU1();
/*  71 */     float $$19 = $$6.getV0();
/*  72 */     float $$20 = $$6.getV(0.0625F);
/*     */ 
/*     */     
/*  75 */     float $$21 = $$6.getU0();
/*  76 */     float $$22 = $$6.getU(0.0625F);
/*  77 */     float $$23 = $$6.getV0();
/*  78 */     float $$24 = $$6.getV1();
/*     */     
/*  80 */     int $$25 = $$3 / 16;
/*  81 */     int $$26 = $$4 / 16;
/*  82 */     double $$27 = 1.0D / $$25;
/*  83 */     double $$28 = 1.0D / $$26;
/*     */     
/*  85 */     for (int $$29 = 0; $$29 < $$25; $$29++) {
/*  86 */       for (int $$30 = 0; $$30 < $$26; $$30++) {
/*  87 */         float $$31 = $$10 + (($$29 + 1) * 16);
/*  88 */         float $$32 = $$10 + ($$29 * 16);
/*  89 */         float $$33 = $$11 + (($$30 + 1) * 16);
/*  90 */         float $$34 = $$11 + ($$30 * 16);
/*     */         
/*  92 */         int $$35 = $$2.getBlockX();
/*  93 */         int $$36 = Mth.floor($$2.getY() + (($$33 + $$34) / 2.0F / 16.0F));
/*  94 */         int $$37 = $$2.getBlockZ();
/*     */         
/*  96 */         Direction $$38 = $$2.getDirection();
/*  97 */         if ($$38 == Direction.NORTH) {
/*  98 */           $$35 = Mth.floor($$2.getX() + (($$31 + $$32) / 2.0F / 16.0F));
/*     */         }
/* 100 */         if ($$38 == Direction.WEST) {
/* 101 */           $$37 = Mth.floor($$2.getZ() - (($$31 + $$32) / 2.0F / 16.0F));
/*     */         }
/* 103 */         if ($$38 == Direction.SOUTH) {
/* 104 */           $$35 = Mth.floor($$2.getX() - (($$31 + $$32) / 2.0F / 16.0F));
/*     */         }
/* 106 */         if ($$38 == Direction.EAST) {
/* 107 */           $$37 = Mth.floor($$2.getZ() + (($$31 + $$32) / 2.0F / 16.0F));
/*     */         }
/*     */         
/* 110 */         int $$39 = LevelRenderer.getLightColor((BlockAndTintGetter)$$2.level(), new BlockPos($$35, $$36, $$37));
/*     */ 
/*     */         
/* 113 */         float $$40 = $$5.getU((float)($$27 * ($$25 - $$29)));
/* 114 */         float $$41 = $$5.getU((float)($$27 * ($$25 - $$29 + 1)));
/* 115 */         float $$42 = $$5.getV((float)($$28 * ($$26 - $$30)));
/* 116 */         float $$43 = $$5.getV((float)($$28 * ($$26 - $$30 + 1)));
/*     */         
/* 118 */         vertex($$8, $$9, $$1, $$31, $$34, $$41, $$42, -0.5F, 0, 0, -1, $$39);
/* 119 */         vertex($$8, $$9, $$1, $$32, $$34, $$40, $$42, -0.5F, 0, 0, -1, $$39);
/* 120 */         vertex($$8, $$9, $$1, $$32, $$33, $$40, $$43, -0.5F, 0, 0, -1, $$39);
/* 121 */         vertex($$8, $$9, $$1, $$31, $$33, $$41, $$43, -0.5F, 0, 0, -1, $$39);
/*     */         
/* 123 */         vertex($$8, $$9, $$1, $$31, $$33, $$14, $$15, 0.5F, 0, 0, 1, $$39);
/* 124 */         vertex($$8, $$9, $$1, $$32, $$33, $$13, $$15, 0.5F, 0, 0, 1, $$39);
/* 125 */         vertex($$8, $$9, $$1, $$32, $$34, $$13, $$16, 0.5F, 0, 0, 1, $$39);
/* 126 */         vertex($$8, $$9, $$1, $$31, $$34, $$14, $$16, 0.5F, 0, 0, 1, $$39);
/*     */         
/* 128 */         vertex($$8, $$9, $$1, $$31, $$33, $$17, $$19, -0.5F, 0, 1, 0, $$39);
/* 129 */         vertex($$8, $$9, $$1, $$32, $$33, $$18, $$19, -0.5F, 0, 1, 0, $$39);
/* 130 */         vertex($$8, $$9, $$1, $$32, $$33, $$18, $$20, 0.5F, 0, 1, 0, $$39);
/* 131 */         vertex($$8, $$9, $$1, $$31, $$33, $$17, $$20, 0.5F, 0, 1, 0, $$39);
/*     */         
/* 133 */         vertex($$8, $$9, $$1, $$31, $$34, $$17, $$19, 0.5F, 0, -1, 0, $$39);
/* 134 */         vertex($$8, $$9, $$1, $$32, $$34, $$18, $$19, 0.5F, 0, -1, 0, $$39);
/* 135 */         vertex($$8, $$9, $$1, $$32, $$34, $$18, $$20, -0.5F, 0, -1, 0, $$39);
/* 136 */         vertex($$8, $$9, $$1, $$31, $$34, $$17, $$20, -0.5F, 0, -1, 0, $$39);
/*     */         
/* 138 */         vertex($$8, $$9, $$1, $$31, $$33, $$22, $$23, 0.5F, -1, 0, 0, $$39);
/* 139 */         vertex($$8, $$9, $$1, $$31, $$34, $$22, $$24, 0.5F, -1, 0, 0, $$39);
/* 140 */         vertex($$8, $$9, $$1, $$31, $$34, $$21, $$24, -0.5F, -1, 0, 0, $$39);
/* 141 */         vertex($$8, $$9, $$1, $$31, $$33, $$21, $$23, -0.5F, -1, 0, 0, $$39);
/*     */         
/* 143 */         vertex($$8, $$9, $$1, $$32, $$33, $$22, $$23, -0.5F, 1, 0, 0, $$39);
/* 144 */         vertex($$8, $$9, $$1, $$32, $$34, $$22, $$24, -0.5F, 1, 0, 0, $$39);
/* 145 */         vertex($$8, $$9, $$1, $$32, $$34, $$21, $$24, 0.5F, 1, 0, 0, $$39);
/* 146 */         vertex($$8, $$9, $$1, $$32, $$33, $$21, $$23, 0.5F, 1, 0, 0, $$39);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void vertex(Matrix4f $$0, Matrix3f $$1, VertexConsumer $$2, float $$3, float $$4, float $$5, float $$6, float $$7, int $$8, int $$9, int $$10, int $$11) {
/* 152 */     $$2.vertex($$0, $$3, $$4, $$7).color(255, 255, 255, 255).uv($$5, $$6).overlayCoords(OverlayTexture.NO_OVERLAY).uv2($$11).normal($$1, $$8, $$9, $$10).endVertex();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\PaintingRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */