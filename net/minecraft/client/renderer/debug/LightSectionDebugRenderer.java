/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.time.Duration;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.world.level.LightLayer;
/*     */ import net.minecraft.world.level.lighting.LayerLightSectionStorage;
/*     */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*     */ import net.minecraft.world.phys.shapes.BitSetDiscreteVoxelShape;
/*     */ import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector4f;
/*     */ 
/*     */ public class LightSectionDebugRenderer
/*     */   implements DebugRenderer.SimpleDebugRenderer {
/*  23 */   private static final Duration REFRESH_INTERVAL = Duration.ofMillis(500L);
/*     */   
/*     */   private static final int RADIUS = 10;
/*  26 */   private static final Vector4f LIGHT_AND_BLOCKS_COLOR = new Vector4f(1.0F, 1.0F, 0.0F, 0.25F);
/*  27 */   private static final Vector4f LIGHT_ONLY_COLOR = new Vector4f(0.25F, 0.125F, 0.0F, 0.125F);
/*     */   
/*     */   private final Minecraft minecraft;
/*     */   private final LightLayer lightLayer;
/*  31 */   private Instant lastUpdateTime = Instant.now();
/*     */   @Nullable
/*     */   private SectionData data;
/*     */   
/*     */   public LightSectionDebugRenderer(Minecraft $$0, LightLayer $$1) {
/*  36 */     this.minecraft = $$0;
/*  37 */     this.lightLayer = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/*  42 */     Instant $$5 = Instant.now();
/*  43 */     if (this.data == null || Duration.between(this.lastUpdateTime, $$5).compareTo(REFRESH_INTERVAL) > 0) {
/*  44 */       this.lastUpdateTime = $$5;
/*  45 */       this.data = new SectionData(this.minecraft.level.getLightEngine(), SectionPos.of(this.minecraft.player.blockPosition()), 10, this.lightLayer);
/*     */     } 
/*     */     
/*  48 */     renderEdges($$0, this.data.lightAndBlocksShape, this.data.minPos, $$1, $$2, $$3, $$4, LIGHT_AND_BLOCKS_COLOR);
/*  49 */     renderEdges($$0, this.data.lightShape, this.data.minPos, $$1, $$2, $$3, $$4, LIGHT_ONLY_COLOR);
/*     */     
/*  51 */     VertexConsumer $$6 = $$1.getBuffer(RenderType.debugSectionQuads());
/*  52 */     renderFaces($$0, this.data.lightAndBlocksShape, this.data.minPos, $$6, $$2, $$3, $$4, LIGHT_AND_BLOCKS_COLOR);
/*  53 */     renderFaces($$0, this.data.lightShape, this.data.minPos, $$6, $$2, $$3, $$4, LIGHT_ONLY_COLOR);
/*     */   }
/*     */   
/*     */   private static void renderFaces(PoseStack $$0, DiscreteVoxelShape $$1, SectionPos $$2, VertexConsumer $$3, double $$4, double $$5, double $$6, Vector4f $$7) {
/*  57 */     $$1.forAllFaces(($$7, $$8, $$9, $$10) -> {
/*     */           int $$11 = $$8 + $$0.getX();
/*     */           int $$12 = $$9 + $$0.getY();
/*     */           int $$13 = $$10 + $$0.getZ();
/*     */           renderFace($$1, $$2, $$7, $$3, $$4, $$5, $$11, $$12, $$13, $$6);
/*     */         });
/*     */   }
/*     */   
/*     */   private static void renderEdges(PoseStack $$0, DiscreteVoxelShape $$1, SectionPos $$2, MultiBufferSource $$3, double $$4, double $$5, double $$6, Vector4f $$7) {
/*  66 */     $$1.forAllEdges(($$7, $$8, $$9, $$10, $$11, $$12) -> { int $$13 = $$7 + $$0.getX(); int $$14 = $$8 + $$0.getY(); int $$15 = $$9 + $$0.getZ(); int $$16 = $$10 + $$0.getX(); int $$17 = $$11 + $$0.getY(); int $$18 = $$12 + $$0.getZ(); VertexConsumer $$19 = $$1.getBuffer(RenderType.debugLineStrip(1.0D)); renderEdge($$2, $$19, $$3, $$4, $$5, $$13, $$14, $$15, $$16, $$17, $$18, $$6); }true);
/*     */   }
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
/*     */   private static void renderFace(PoseStack $$0, VertexConsumer $$1, Direction $$2, double $$3, double $$4, double $$5, int $$6, int $$7, int $$8, Vector4f $$9) {
/*  79 */     float $$10 = (float)(SectionPos.sectionToBlockCoord($$6) - $$3);
/*  80 */     float $$11 = (float)(SectionPos.sectionToBlockCoord($$7) - $$4);
/*  81 */     float $$12 = (float)(SectionPos.sectionToBlockCoord($$8) - $$5);
/*  82 */     float $$13 = $$10 + 16.0F;
/*  83 */     float $$14 = $$11 + 16.0F;
/*  84 */     float $$15 = $$12 + 16.0F;
/*  85 */     float $$16 = $$9.x();
/*  86 */     float $$17 = $$9.y();
/*  87 */     float $$18 = $$9.z();
/*  88 */     float $$19 = $$9.w();
/*  89 */     Matrix4f $$20 = $$0.last().pose();
/*  90 */     switch ($$2) {
/*     */       case DOWN:
/*  92 */         $$1.vertex($$20, $$10, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*  93 */         $$1.vertex($$20, $$13, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*  94 */         $$1.vertex($$20, $$13, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/*  95 */         $$1.vertex($$20, $$10, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */       case UP:
/*  98 */         $$1.vertex($$20, $$10, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*  99 */         $$1.vertex($$20, $$10, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 100 */         $$1.vertex($$20, $$13, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 101 */         $$1.vertex($$20, $$13, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */       case NORTH:
/* 104 */         $$1.vertex($$20, $$10, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 105 */         $$1.vertex($$20, $$10, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 106 */         $$1.vertex($$20, $$13, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 107 */         $$1.vertex($$20, $$13, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */       case SOUTH:
/* 110 */         $$1.vertex($$20, $$10, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 111 */         $$1.vertex($$20, $$13, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 112 */         $$1.vertex($$20, $$13, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 113 */         $$1.vertex($$20, $$10, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */       case WEST:
/* 116 */         $$1.vertex($$20, $$10, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 117 */         $$1.vertex($$20, $$10, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 118 */         $$1.vertex($$20, $$10, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 119 */         $$1.vertex($$20, $$10, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */       case EAST:
/* 122 */         $$1.vertex($$20, $$13, $$11, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 123 */         $$1.vertex($$20, $$13, $$14, $$12).color($$16, $$17, $$18, $$19).endVertex();
/* 124 */         $$1.vertex($$20, $$13, $$14, $$15).color($$16, $$17, $$18, $$19).endVertex();
/* 125 */         $$1.vertex($$20, $$13, $$11, $$15).color($$16, $$17, $$18, $$19).endVertex();
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void renderEdge(PoseStack $$0, VertexConsumer $$1, double $$2, double $$3, double $$4, int $$5, int $$6, int $$7, int $$8, int $$9, int $$10, Vector4f $$11) {
/* 131 */     float $$12 = (float)(SectionPos.sectionToBlockCoord($$5) - $$2);
/* 132 */     float $$13 = (float)(SectionPos.sectionToBlockCoord($$6) - $$3);
/* 133 */     float $$14 = (float)(SectionPos.sectionToBlockCoord($$7) - $$4);
/* 134 */     float $$15 = (float)(SectionPos.sectionToBlockCoord($$8) - $$2);
/* 135 */     float $$16 = (float)(SectionPos.sectionToBlockCoord($$9) - $$3);
/* 136 */     float $$17 = (float)(SectionPos.sectionToBlockCoord($$10) - $$4);
/*     */     
/* 138 */     Matrix4f $$18 = $$0.last().pose();
/* 139 */     $$1.vertex($$18, $$12, $$13, $$14).color($$11.x(), $$11.y(), $$11.z(), 1.0F).endVertex();
/* 140 */     $$1.vertex($$18, $$15, $$16, $$17).color($$11.x(), $$11.y(), $$11.z(), 1.0F).endVertex();
/*     */   }
/*     */   
/*     */   private static final class SectionData {
/*     */     final DiscreteVoxelShape lightAndBlocksShape;
/*     */     final DiscreteVoxelShape lightShape;
/*     */     final SectionPos minPos;
/*     */     
/*     */     SectionData(LevelLightEngine $$0, SectionPos $$1, int $$2, LightLayer $$3) {
/* 149 */       int $$4 = $$2 * 2 + 1;
/*     */       
/* 151 */       this.lightAndBlocksShape = (DiscreteVoxelShape)new BitSetDiscreteVoxelShape($$4, $$4, $$4);
/* 152 */       this.lightShape = (DiscreteVoxelShape)new BitSetDiscreteVoxelShape($$4, $$4, $$4);
/*     */       
/* 154 */       for (int $$5 = 0; $$5 < $$4; $$5++) {
/* 155 */         for (int $$6 = 0; $$6 < $$4; $$6++) {
/* 156 */           for (int $$7 = 0; $$7 < $$4; $$7++) {
/* 157 */             SectionPos $$8 = SectionPos.of($$1.x() + $$7 - $$2, $$1.y() + $$6 - $$2, $$1.z() + $$5 - $$2);
/* 158 */             LayerLightSectionStorage.SectionType $$9 = $$0.getDebugSectionType($$3, $$8);
/* 159 */             if ($$9 == LayerLightSectionStorage.SectionType.LIGHT_AND_DATA) {
/* 160 */               this.lightAndBlocksShape.fill($$7, $$6, $$5);
/* 161 */               this.lightShape.fill($$7, $$6, $$5);
/* 162 */             } else if ($$9 == LayerLightSectionStorage.SectionType.LIGHT_ONLY) {
/* 163 */               this.lightShape.fill($$7, $$6, $$5);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 169 */       this.minPos = SectionPos.of($$1.x() - $$2, $$1.y() - $$2, $$1.z() - $$2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\LightSectionDebugRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */