/*     */ package net.minecraft.client.renderer.debug;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.pathfinder.Node;
/*     */ import net.minecraft.world.level.pathfinder.Path;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ 
/*     */ public class PathfindingRenderer
/*     */   implements DebugRenderer.SimpleDebugRenderer {
/*  19 */   private final Map<Integer, Path> pathMap = Maps.newHashMap();
/*  20 */   private final Map<Integer, Float> pathMaxDist = Maps.newHashMap();
/*  21 */   private final Map<Integer, Long> creationMap = Maps.newHashMap();
/*     */   
/*     */   private static final long TIMEOUT = 5000L;
/*     */   
/*     */   private static final float MAX_RENDER_DIST = 80.0F;
/*     */   
/*     */   private static final boolean SHOW_OPEN_CLOSED = true;
/*     */   
/*     */   private static final boolean SHOW_OPEN_CLOSED_COST_MALUS = false;
/*     */   
/*     */   private static final boolean SHOW_OPEN_CLOSED_NODE_TYPE_WITH_TEXT = false;
/*     */   
/*     */   private static final boolean SHOW_OPEN_CLOSED_NODE_TYPE_WITH_BOX = true;
/*     */   private static final boolean SHOW_GROUND_LABELS = true;
/*     */   private static final float TEXT_SCALE = 0.02F;
/*     */   
/*     */   public void addPath(int $$0, Path $$1, float $$2) {
/*  38 */     this.pathMap.put(Integer.valueOf($$0), $$1);
/*  39 */     this.creationMap.put(Integer.valueOf($$0), Long.valueOf(Util.getMillis()));
/*  40 */     this.pathMaxDist.put(Integer.valueOf($$0), Float.valueOf($$2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/*  45 */     if (this.pathMap.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/*  49 */     long $$5 = Util.getMillis();
/*  50 */     for (Integer $$6 : this.pathMap.keySet()) {
/*  51 */       Path $$7 = this.pathMap.get($$6);
/*  52 */       float $$8 = ((Float)this.pathMaxDist.get($$6)).floatValue();
/*  53 */       renderPath($$0, $$1, $$7, $$8, true, true, $$2, $$3, $$4);
/*     */     } 
/*     */     
/*  56 */     for (Integer $$9 : (Integer[])this.creationMap.keySet().toArray((Object[])new Integer[0])) {
/*  57 */       if ($$5 - ((Long)this.creationMap.get($$9)).longValue() > 5000L) {
/*  58 */         this.pathMap.remove($$9);
/*  59 */         this.creationMap.remove($$9);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void renderPath(PoseStack $$0, MultiBufferSource $$1, Path $$2, float $$3, boolean $$4, boolean $$5, double $$6, double $$7, double $$8) {
/*  65 */     renderPathLine($$0, $$1.getBuffer(RenderType.debugLineStrip(6.0D)), $$2, $$6, $$7, $$8);
/*     */     
/*  67 */     BlockPos $$9 = $$2.getTarget();
/*  68 */     if (distanceToCamera($$9, $$6, $$7, $$8) <= 80.0F) {
/*  69 */       DebugRenderer.renderFilledBox($$0, $$1, (new AABB(($$9.getX() + 0.25F), ($$9.getY() + 0.25F), $$9.getZ() + 0.25D, ($$9.getX() + 0.75F), ($$9.getY() + 0.75F), ($$9.getZ() + 0.75F))).move(-$$6, -$$7, -$$8), 0.0F, 1.0F, 0.0F, 0.5F);
/*     */       
/*  71 */       for (int $$10 = 0; $$10 < $$2.getNodeCount(); $$10++) {
/*  72 */         Node $$11 = $$2.getNode($$10);
/*  73 */         if (distanceToCamera($$11.asBlockPos(), $$6, $$7, $$8) <= 80.0F) {
/*  74 */           float $$12 = ($$10 == $$2.getNextNodeIndex()) ? 1.0F : 0.0F;
/*  75 */           float $$13 = ($$10 == $$2.getNextNodeIndex()) ? 0.0F : 1.0F;
/*  76 */           DebugRenderer.renderFilledBox($$0, $$1, (new AABB(($$11.x + 0.5F - $$3), ($$11.y + 0.01F * $$10), ($$11.z + 0.5F - $$3), ($$11.x + 0.5F + $$3), ($$11.y + 0.25F + 0.01F * $$10), ($$11.z + 0.5F + $$3)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  83 */               .move(-$$6, -$$7, -$$8), $$12, 0.0F, $$13, 0.5F);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  88 */     Path.DebugData $$14 = $$2.debugData();
/*  89 */     if ($$4 && $$14 != null) {
/*  90 */       for (Node $$15 : $$14.closedSet()) {
/*  91 */         if (distanceToCamera($$15.asBlockPos(), $$6, $$7, $$8) <= 80.0F)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  99 */           DebugRenderer.renderFilledBox($$0, $$1, (new AABB(($$15.x + 0.5F - $$3 / 2.0F), ($$15.y + 0.01F), ($$15.z + 0.5F - $$3 / 2.0F), ($$15.x + 0.5F + $$3 / 2.0F), $$15.y + 0.1D, ($$15.z + 0.5F + $$3 / 2.0F)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 106 */               .move(-$$6, -$$7, -$$8), 1.0F, 0.8F, 0.8F, 0.5F);
/*     */         }
/*     */       } 
/*     */       
/* 110 */       for (Node $$16 : $$14.openSet()) {
/* 111 */         if (distanceToCamera($$16.asBlockPos(), $$6, $$7, $$8) <= 80.0F)
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 119 */           DebugRenderer.renderFilledBox($$0, $$1, (new AABB(($$16.x + 0.5F - $$3 / 2.0F), ($$16.y + 0.01F), ($$16.z + 0.5F - $$3 / 2.0F), ($$16.x + 0.5F + $$3 / 2.0F), $$16.y + 0.1D, ($$16.z + 0.5F + $$3 / 2.0F)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 126 */               .move(-$$6, -$$7, -$$8), 0.8F, 1.0F, 1.0F, 0.5F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 132 */     if ($$5) {
/* 133 */       for (int $$17 = 0; $$17 < $$2.getNodeCount(); $$17++) {
/* 134 */         Node $$18 = $$2.getNode($$17);
/* 135 */         if (distanceToCamera($$18.asBlockPos(), $$6, $$7, $$8) <= 80.0F) {
/* 136 */           DebugRenderer.renderFloatingText($$0, $$1, String.valueOf($$18.type), $$18.x + 0.5D, $$18.y + 0.75D, $$18.z + 0.5D, -1, 0.02F, true, 0.0F, true);
/* 137 */           DebugRenderer.renderFloatingText($$0, $$1, String.format(Locale.ROOT, "%.2f", new Object[] { Float.valueOf($$18.costMalus) }), $$18.x + 0.5D, $$18.y + 0.25D, $$18.z + 0.5D, -1, 0.02F, true, 0.0F, true);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void renderPathLine(PoseStack $$0, VertexConsumer $$1, Path $$2, double $$3, double $$4, double $$5) {
/* 144 */     for (int $$6 = 0; $$6 < $$2.getNodeCount(); $$6++) {
/* 145 */       Node $$7 = $$2.getNode($$6);
/*     */       
/* 147 */       if (distanceToCamera($$7.asBlockPos(), $$3, $$4, $$5) <= 80.0F) {
/*     */ 
/*     */ 
/*     */         
/* 151 */         float $$8 = $$6 / $$2.getNodeCount() * 0.33F;
/* 152 */         int $$9 = ($$6 == 0) ? 0 : Mth.hsvToRgb($$8, 0.9F, 0.9F);
/* 153 */         int $$10 = $$9 >> 16 & 0xFF;
/* 154 */         int $$11 = $$9 >> 8 & 0xFF;
/* 155 */         int $$12 = $$9 & 0xFF;
/*     */         
/* 157 */         $$1.vertex($$0.last().pose(), (float)($$7.x - $$3 + 0.5D), (float)($$7.y - $$4 + 0.5D), (float)($$7.z - $$5 + 0.5D)).color($$10, $$11, $$12, 255).endVertex();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private static float distanceToCamera(BlockPos $$0, double $$1, double $$2, double $$3) {
/* 162 */     return (float)(Math.abs($$0.getX() - $$1) + Math.abs($$0.getY() - $$2) + Math.abs($$0.getZ() - $$3));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\PathfindingRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */