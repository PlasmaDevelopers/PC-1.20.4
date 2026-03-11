/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.util.FastColor;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class ChunkBorderRenderer
/*     */   implements DebugRenderer.SimpleDebugRenderer {
/*     */   private final Minecraft minecraft;
/*  16 */   private static final int CELL_BORDER = FastColor.ARGB32.color(255, 0, 155, 155);
/*  17 */   private static final int YELLOW = FastColor.ARGB32.color(255, 255, 255, 0);
/*     */   
/*     */   public ChunkBorderRenderer(Minecraft $$0) {
/*  20 */     this.minecraft = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/*  25 */     Entity $$5 = this.minecraft.gameRenderer.getMainCamera().getEntity();
/*     */     
/*  27 */     float $$6 = (float)(this.minecraft.level.getMinBuildHeight() - $$3);
/*  28 */     float $$7 = (float)(this.minecraft.level.getMaxBuildHeight() - $$3);
/*     */     
/*  30 */     ChunkPos $$8 = $$5.chunkPosition();
/*  31 */     float $$9 = (float)($$8.getMinBlockX() - $$2);
/*  32 */     float $$10 = (float)($$8.getMinBlockZ() - $$4);
/*     */     
/*  34 */     VertexConsumer $$11 = $$1.getBuffer(RenderType.debugLineStrip(1.0D));
/*     */     
/*  36 */     Matrix4f $$12 = $$0.last().pose();
/*     */ 
/*     */     
/*  39 */     for (int $$13 = -16; $$13 <= 32; $$13 += 16) {
/*  40 */       for (int $$14 = -16; $$14 <= 32; $$14 += 16) {
/*  41 */         $$11.vertex($$12, $$9 + $$13, $$6, $$10 + $$14).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
/*  42 */         $$11.vertex($$12, $$9 + $$13, $$6, $$10 + $$14).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  43 */         $$11.vertex($$12, $$9 + $$13, $$7, $$10 + $$14).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*  44 */         $$11.vertex($$12, $$9 + $$13, $$7, $$10 + $$14).color(1.0F, 0.0F, 0.0F, 0.0F).endVertex();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  49 */     for (int $$15 = 2; $$15 < 16; $$15 += 2) {
/*  50 */       int $$16 = ($$15 % 4 == 0) ? CELL_BORDER : YELLOW;
/*  51 */       $$11.vertex($$12, $$9 + $$15, $$6, $$10).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  52 */       $$11.vertex($$12, $$9 + $$15, $$6, $$10).color($$16).endVertex();
/*  53 */       $$11.vertex($$12, $$9 + $$15, $$7, $$10).color($$16).endVertex();
/*  54 */       $$11.vertex($$12, $$9 + $$15, $$7, $$10).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */       
/*  56 */       $$11.vertex($$12, $$9 + $$15, $$6, $$10 + 16.0F).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  57 */       $$11.vertex($$12, $$9 + $$15, $$6, $$10 + 16.0F).color($$16).endVertex();
/*  58 */       $$11.vertex($$12, $$9 + $$15, $$7, $$10 + 16.0F).color($$16).endVertex();
/*  59 */       $$11.vertex($$12, $$9 + $$15, $$7, $$10 + 16.0F).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */ 
/*     */     
/*  63 */     for (int $$17 = 2; $$17 < 16; $$17 += 2) {
/*  64 */       int $$18 = ($$17 % 4 == 0) ? CELL_BORDER : YELLOW;
/*  65 */       $$11.vertex($$12, $$9, $$6, $$10 + $$17).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  66 */       $$11.vertex($$12, $$9, $$6, $$10 + $$17).color($$18).endVertex();
/*  67 */       $$11.vertex($$12, $$9, $$7, $$10 + $$17).color($$18).endVertex();
/*  68 */       $$11.vertex($$12, $$9, $$7, $$10 + $$17).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */       
/*  70 */       $$11.vertex($$12, $$9 + 16.0F, $$6, $$10 + $$17).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  71 */       $$11.vertex($$12, $$9 + 16.0F, $$6, $$10 + $$17).color($$18).endVertex();
/*  72 */       $$11.vertex($$12, $$9 + 16.0F, $$7, $$10 + $$17).color($$18).endVertex();
/*  73 */       $$11.vertex($$12, $$9 + 16.0F, $$7, $$10 + $$17).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */ 
/*     */     
/*  77 */     for (int $$19 = this.minecraft.level.getMinBuildHeight(); $$19 <= this.minecraft.level.getMaxBuildHeight(); $$19 += 2) {
/*  78 */       float $$20 = (float)($$19 - $$3);
/*  79 */       int $$21 = ($$19 % 8 == 0) ? CELL_BORDER : YELLOW;
/*  80 */       $$11.vertex($$12, $$9, $$20, $$10).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*  81 */       $$11.vertex($$12, $$9, $$20, $$10).color($$21).endVertex();
/*  82 */       $$11.vertex($$12, $$9, $$20, $$10 + 16.0F).color($$21).endVertex();
/*  83 */       $$11.vertex($$12, $$9 + 16.0F, $$20, $$10 + 16.0F).color($$21).endVertex();
/*  84 */       $$11.vertex($$12, $$9 + 16.0F, $$20, $$10).color($$21).endVertex();
/*  85 */       $$11.vertex($$12, $$9, $$20, $$10).color($$21).endVertex();
/*  86 */       $$11.vertex($$12, $$9, $$20, $$10).color(1.0F, 1.0F, 0.0F, 0.0F).endVertex();
/*     */     } 
/*     */     
/*  89 */     $$11 = $$1.getBuffer(RenderType.debugLineStrip(2.0D));
/*     */ 
/*     */     
/*  92 */     for (int $$22 = 0; $$22 <= 16; $$22 += 16) {
/*  93 */       for (int $$23 = 0; $$23 <= 16; $$23 += 16) {
/*  94 */         $$11.vertex($$12, $$9 + $$22, $$6, $$10 + $$23).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*  95 */         $$11.vertex($$12, $$9 + $$22, $$6, $$10 + $$23).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/*  96 */         $$11.vertex($$12, $$9 + $$22, $$7, $$10 + $$23).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/*  97 */         $$11.vertex($$12, $$9 + $$22, $$7, $$10 + $$23).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 102 */     for (int $$24 = this.minecraft.level.getMinBuildHeight(); $$24 <= this.minecraft.level.getMaxBuildHeight(); $$24 += 16) {
/* 103 */       float $$25 = (float)($$24 - $$3);
/* 104 */       $$11.vertex($$12, $$9, $$25, $$10).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/* 105 */       $$11.vertex($$12, $$9, $$25, $$10).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 106 */       $$11.vertex($$12, $$9, $$25, $$10 + 16.0F).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 107 */       $$11.vertex($$12, $$9 + 16.0F, $$25, $$10 + 16.0F).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 108 */       $$11.vertex($$12, $$9 + 16.0F, $$25, $$10).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 109 */       $$11.vertex($$12, $$9, $$25, $$10).color(0.25F, 0.25F, 1.0F, 1.0F).endVertex();
/* 110 */       $$11.vertex($$12, $$9, $$25, $$10).color(0.25F, 0.25F, 1.0F, 0.0F).endVertex();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\ChunkBorderRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */