/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.blaze3d.vertex.BufferBuilder;
/*     */ import com.mojang.blaze3d.vertex.DefaultVertexFormat;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.Tesselator;
/*     */ import com.mojang.blaze3d.vertex.VertexFormat;
/*     */ import com.mojang.blaze3d.vertex.VertexSorting;
/*     */ import com.mojang.math.Axis;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CubeMap
/*     */ {
/*     */   private static final int SIDES = 6;
/*  23 */   private final ResourceLocation[] images = new ResourceLocation[6];
/*     */   
/*     */   public CubeMap(ResourceLocation $$0) {
/*  26 */     for (int $$1 = 0; $$1 < 6; $$1++) {
/*  27 */       this.images[$$1] = $$0.withPath($$0.getPath() + "_" + $$0.getPath() + ".png");
/*     */     }
/*     */   }
/*     */   
/*     */   public void render(Minecraft $$0, float $$1, float $$2, float $$3) {
/*  32 */     Tesselator $$4 = Tesselator.getInstance();
/*  33 */     BufferBuilder $$5 = $$4.getBuilder();
/*     */     
/*  35 */     Matrix4f $$6 = (new Matrix4f()).setPerspective(1.4835298F, $$0.getWindow().getWidth() / $$0.getWindow().getHeight(), 0.05F, 10.0F);
/*     */     
/*  37 */     RenderSystem.backupProjectionMatrix();
/*  38 */     RenderSystem.setProjectionMatrix($$6, VertexSorting.DISTANCE_TO_ORIGIN);
/*     */     
/*  40 */     PoseStack $$7 = RenderSystem.getModelViewStack();
/*  41 */     $$7.pushPose();
/*  42 */     $$7.setIdentity();
/*  43 */     $$7.mulPose(Axis.XP.rotationDegrees(180.0F));
/*  44 */     RenderSystem.applyModelViewMatrix();
/*     */     
/*  46 */     RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
/*     */     
/*  48 */     RenderSystem.enableBlend();
/*  49 */     RenderSystem.disableCull();
/*  50 */     RenderSystem.depthMask(false);
/*  51 */     int $$8 = 2;
/*     */     
/*  53 */     for (int $$9 = 0; $$9 < 4; $$9++) {
/*  54 */       $$7.pushPose();
/*  55 */       float $$10 = (($$9 % 2) / 2.0F - 0.5F) / 256.0F;
/*  56 */       float $$11 = (($$9 / 2) / 2.0F - 0.5F) / 256.0F;
/*  57 */       float $$12 = 0.0F;
/*     */       
/*  59 */       $$7.translate($$10, $$11, 0.0F);
/*     */       
/*  61 */       $$7.mulPose(Axis.XP.rotationDegrees($$1));
/*  62 */       $$7.mulPose(Axis.YP.rotationDegrees($$2));
/*     */       
/*  64 */       RenderSystem.applyModelViewMatrix();
/*     */       
/*  66 */       for (int $$13 = 0; $$13 < 6; $$13++) {
/*  67 */         RenderSystem.setShaderTexture(0, this.images[$$13]);
/*  68 */         $$5.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR);
/*  69 */         int $$14 = Math.round(255.0F * $$3) / ($$9 + 1);
/*  70 */         if ($$13 == 0) {
/*  71 */           $$5.vertex(-1.0D, -1.0D, 1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*  72 */           $$5.vertex(-1.0D, 1.0D, 1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  73 */           $$5.vertex(1.0D, 1.0D, 1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  74 */           $$5.vertex(1.0D, -1.0D, 1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/*  76 */         if ($$13 == 1) {
/*  77 */           $$5.vertex(1.0D, -1.0D, 1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*  78 */           $$5.vertex(1.0D, 1.0D, 1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  79 */           $$5.vertex(1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  80 */           $$5.vertex(1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/*  82 */         if ($$13 == 2) {
/*  83 */           $$5.vertex(1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*  84 */           $$5.vertex(1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  85 */           $$5.vertex(-1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  86 */           $$5.vertex(-1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/*  88 */         if ($$13 == 3) {
/*  89 */           $$5.vertex(-1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*  90 */           $$5.vertex(-1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  91 */           $$5.vertex(-1.0D, 1.0D, 1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  92 */           $$5.vertex(-1.0D, -1.0D, 1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/*  94 */         if ($$13 == 4) {
/*  95 */           $$5.vertex(-1.0D, -1.0D, -1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*  96 */           $$5.vertex(-1.0D, -1.0D, 1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  97 */           $$5.vertex(1.0D, -1.0D, 1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/*  98 */           $$5.vertex(1.0D, -1.0D, -1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/* 100 */         if ($$13 == 5) {
/* 101 */           $$5.vertex(-1.0D, 1.0D, 1.0D).uv(0.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/* 102 */           $$5.vertex(-1.0D, 1.0D, -1.0D).uv(0.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/* 103 */           $$5.vertex(1.0D, 1.0D, -1.0D).uv(1.0F, 1.0F).color(255, 255, 255, $$14).endVertex();
/* 104 */           $$5.vertex(1.0D, 1.0D, 1.0D).uv(1.0F, 0.0F).color(255, 255, 255, $$14).endVertex();
/*     */         } 
/* 106 */         $$4.end();
/*     */       } 
/* 108 */       $$7.popPose();
/* 109 */       RenderSystem.applyModelViewMatrix();
/*     */       
/* 111 */       RenderSystem.colorMask(true, true, true, false);
/*     */     } 
/* 113 */     RenderSystem.colorMask(true, true, true, true);
/*     */     
/* 115 */     RenderSystem.restoreProjectionMatrix();
/*     */     
/* 117 */     $$7.popPose();
/* 118 */     RenderSystem.applyModelViewMatrix();
/*     */     
/* 120 */     RenderSystem.depthMask(true);
/* 121 */     RenderSystem.enableCull();
/*     */     
/* 123 */     RenderSystem.enableDepthTest();
/*     */   }
/*     */   
/*     */   public CompletableFuture<Void> preload(TextureManager $$0, Executor $$1) {
/* 127 */     CompletableFuture[] arrayOfCompletableFuture = new CompletableFuture[6];
/* 128 */     for (int $$3 = 0; $$3 < arrayOfCompletableFuture.length; $$3++) {
/* 129 */       arrayOfCompletableFuture[$$3] = $$0.preload(this.images[$$3], $$1);
/*     */     }
/* 131 */     return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\CubeMap.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */