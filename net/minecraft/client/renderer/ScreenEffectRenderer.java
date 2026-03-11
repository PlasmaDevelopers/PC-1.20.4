/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.BufferUploader;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.math.Axis;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.player.LocalPlayer;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.ModelBakery;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class ScreenEffectRenderer {
/*  27 */   private static final ResourceLocation UNDERWATER_LOCATION = new ResourceLocation("textures/misc/underwater.png");
/*     */   
/*     */   public static void renderScreenEffect(Minecraft $$0, PoseStack $$1) {
/*  30 */     LocalPlayer localPlayer = $$0.player;
/*  31 */     if (!((Player)localPlayer).noPhysics) {
/*  32 */       BlockState $$3 = getViewBlockingState((Player)localPlayer);
/*  33 */       if ($$3 != null) {
/*  34 */         renderTex($$0.getBlockRenderer().getBlockModelShaper().getParticleIcon($$3), $$1);
/*     */       }
/*     */     } 
/*     */     
/*  38 */     if (!$$0.player.isSpectator()) {
/*  39 */       if ($$0.player.isEyeInFluid(FluidTags.WATER)) {
/*  40 */         renderWater($$0, $$1);
/*     */       }
/*     */       
/*  43 */       if ($$0.player.isOnFire()) {
/*  44 */         renderFire($$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static BlockState getViewBlockingState(Player $$0) {
/*  51 */     BlockPos.MutableBlockPos $$1 = new BlockPos.MutableBlockPos();
/*  52 */     for (int $$2 = 0; $$2 < 8; $$2++) {
/*  53 */       double $$3 = $$0.getX() + (((($$2 >> 0) % 2) - 0.5F) * $$0.getBbWidth() * 0.8F);
/*  54 */       double $$4 = $$0.getEyeY() + (((($$2 >> 1) % 2) - 0.5F) * 0.1F);
/*  55 */       double $$5 = $$0.getZ() + (((($$2 >> 2) % 2) - 0.5F) * $$0.getBbWidth() * 0.8F);
/*     */       
/*  57 */       $$1.set($$3, $$4, $$5);
/*  58 */       BlockState $$6 = $$0.level().getBlockState((BlockPos)$$1);
/*  59 */       if ($$6.getRenderShape() != RenderShape.INVISIBLE && $$6.isViewBlocking((BlockGetter)$$0.level(), (BlockPos)$$1)) {
/*  60 */         return $$6;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */   private static void renderTex(TextureAtlasSprite $$0, PoseStack $$1) {
/*  67 */     RenderSystem.setShaderTexture(0, $$0.atlasLocation());
/*  68 */     RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
/*     */     
/*  70 */     BufferBuilder $$2 = Tesselator.getInstance().getBuilder();
/*     */     
/*  72 */     float $$3 = 0.1F;
/*     */     
/*  74 */     float $$4 = -1.0F;
/*  75 */     float $$5 = 1.0F;
/*  76 */     float $$6 = -1.0F;
/*  77 */     float $$7 = 1.0F;
/*  78 */     float $$8 = -0.5F;
/*     */     
/*  80 */     float $$9 = $$0.getU0();
/*  81 */     float $$10 = $$0.getU1();
/*  82 */     float $$11 = $$0.getV0();
/*  83 */     float $$12 = $$0.getV1();
/*     */     
/*  85 */     Matrix4f $$13 = $$1.last().pose();
/*     */     
/*  87 */     $$2.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
/*  88 */     $$2.vertex($$13, -1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv($$10, $$12).endVertex();
/*  89 */     $$2.vertex($$13, 1.0F, -1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv($$9, $$12).endVertex();
/*  90 */     $$2.vertex($$13, 1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv($$9, $$11).endVertex();
/*  91 */     $$2.vertex($$13, -1.0F, 1.0F, -0.5F).color(0.1F, 0.1F, 0.1F, 1.0F).uv($$10, $$11).endVertex();
/*  92 */     BufferUploader.drawWithShader($$2.end());
/*     */   }
/*     */   
/*     */   private static void renderWater(Minecraft $$0, PoseStack $$1) {
/*  96 */     RenderSystem.setShader(GameRenderer::getPositionTexShader);
/*     */     
/*  98 */     RenderSystem.setShaderTexture(0, UNDERWATER_LOCATION);
/*     */     
/* 100 */     BufferBuilder $$2 = Tesselator.getInstance().getBuilder();
/*     */ 
/*     */     
/* 103 */     BlockPos $$3 = BlockPos.containing($$0.player.getX(), $$0.player.getEyeY(), $$0.player.getZ());
/* 104 */     float $$4 = LightTexture.getBrightness($$0.player.level().dimensionType(), $$0.player.level().getMaxLocalRawBrightness($$3));
/*     */     
/* 106 */     RenderSystem.enableBlend();
/* 107 */     RenderSystem.setShaderColor($$4, $$4, $$4, 0.1F);
/*     */     
/* 109 */     float $$5 = 4.0F;
/*     */     
/* 111 */     float $$6 = -1.0F;
/* 112 */     float $$7 = 1.0F;
/* 113 */     float $$8 = -1.0F;
/* 114 */     float $$9 = 1.0F;
/* 115 */     float $$10 = -0.5F;
/*     */     
/* 117 */     float $$11 = -$$0.player.getYRot() / 64.0F;
/* 118 */     float $$12 = $$0.player.getXRot() / 64.0F;
/*     */     
/* 120 */     Matrix4f $$13 = $$1.last().pose();
/*     */     
/* 122 */     $$2.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
/* 123 */     $$2.vertex($$13, -1.0F, -1.0F, -0.5F).uv(4.0F + $$11, 4.0F + $$12).endVertex();
/* 124 */     $$2.vertex($$13, 1.0F, -1.0F, -0.5F).uv(0.0F + $$11, 4.0F + $$12).endVertex();
/* 125 */     $$2.vertex($$13, 1.0F, 1.0F, -0.5F).uv(0.0F + $$11, 0.0F + $$12).endVertex();
/* 126 */     $$2.vertex($$13, -1.0F, 1.0F, -0.5F).uv(4.0F + $$11, 0.0F + $$12).endVertex();
/* 127 */     BufferUploader.drawWithShader($$2.end());
/*     */     
/* 129 */     RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 130 */     RenderSystem.disableBlend();
/*     */   }
/*     */   
/*     */   private static void renderFire(Minecraft $$0, PoseStack $$1) {
/* 134 */     BufferBuilder $$2 = Tesselator.getInstance().getBuilder();
/*     */     
/* 136 */     RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
/*     */     
/* 138 */     RenderSystem.depthFunc(519);
/* 139 */     RenderSystem.depthMask(false);
/* 140 */     RenderSystem.enableBlend();
/*     */     
/* 142 */     TextureAtlasSprite $$3 = ModelBakery.FIRE_1.sprite();
/* 143 */     RenderSystem.setShaderTexture(0, $$3.atlasLocation());
/*     */     
/* 145 */     float $$4 = $$3.getU0();
/* 146 */     float $$5 = $$3.getU1();
/* 147 */     float $$6 = ($$4 + $$5) / 2.0F;
/*     */     
/* 149 */     float $$7 = $$3.getV0();
/* 150 */     float $$8 = $$3.getV1();
/* 151 */     float $$9 = ($$7 + $$8) / 2.0F;
/*     */     
/* 153 */     float $$10 = $$3.uvShrinkRatio();
/*     */     
/* 155 */     float $$11 = Mth.lerp($$10, $$4, $$6);
/* 156 */     float $$12 = Mth.lerp($$10, $$5, $$6);
/* 157 */     float $$13 = Mth.lerp($$10, $$7, $$9);
/* 158 */     float $$14 = Mth.lerp($$10, $$8, $$9);
/*     */     
/* 160 */     float $$15 = 1.0F;
/* 161 */     for (int $$16 = 0; $$16 < 2; $$16++) {
/* 162 */       $$1.pushPose();
/*     */       
/* 164 */       float $$17 = -0.5F;
/* 165 */       float $$18 = 0.5F;
/* 166 */       float $$19 = -0.5F;
/* 167 */       float $$20 = 0.5F;
/* 168 */       float $$21 = -0.5F;
/* 169 */       $$1.translate(-($$16 * 2 - 1) * 0.24F, -0.3F, 0.0F);
/* 170 */       $$1.mulPose(Axis.YP.rotationDegrees(($$16 * 2 - 1) * 10.0F));
/*     */       
/* 172 */       Matrix4f $$22 = $$1.last().pose();
/*     */       
/* 174 */       $$2.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
/* 175 */       $$2.vertex($$22, -0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv($$12, $$14).endVertex();
/* 176 */       $$2.vertex($$22, 0.5F, -0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv($$11, $$14).endVertex();
/* 177 */       $$2.vertex($$22, 0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv($$11, $$13).endVertex();
/* 178 */       $$2.vertex($$22, -0.5F, 0.5F, -0.5F).color(1.0F, 1.0F, 1.0F, 0.9F).uv($$12, $$13).endVertex();
/* 179 */       BufferUploader.drawWithShader($$2.end());
/* 180 */       $$1.popPose();
/*     */     } 
/* 182 */     RenderSystem.disableBlend();
/* 183 */     RenderSystem.depthMask(true);
/* 184 */     RenderSystem.depthFunc(515);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\ScreenEffectRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */