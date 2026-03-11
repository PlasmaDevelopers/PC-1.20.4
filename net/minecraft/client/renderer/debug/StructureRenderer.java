/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.Camera;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.network.protocol.common.custom.StructuresDebugPayload;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ 
/*    */ public class StructureRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   private final Minecraft minecraft;
/* 24 */   private final Map<ResourceKey<Level>, Map<String, BoundingBox>> postMainBoxes = Maps.newIdentityHashMap();
/* 25 */   private final Map<ResourceKey<Level>, Map<String, StructuresDebugPayload.PieceInfo>> postPieces = Maps.newIdentityHashMap();
/*    */   
/*    */   private static final int MAX_RENDER_DIST = 500;
/*    */   
/*    */   public StructureRenderer(Minecraft $$0) {
/* 30 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 35 */     Camera $$5 = this.minecraft.gameRenderer.getMainCamera();
/* 36 */     ResourceKey<Level> $$6 = this.minecraft.level.dimension();
/*    */     
/* 38 */     BlockPos $$7 = BlockPos.containing(($$5.getPosition()).x, 0.0D, ($$5.getPosition()).z);
/*    */     
/* 40 */     VertexConsumer $$8 = $$1.getBuffer(RenderType.lines());
/*    */     
/* 42 */     if (this.postMainBoxes.containsKey($$6)) {
/* 43 */       for (BoundingBox $$9 : ((Map)this.postMainBoxes.get($$6)).values()) {
/* 44 */         if ($$7.closerThan((Vec3i)$$9.getCenter(), 500.0D)) {
/* 45 */           LevelRenderer.renderLineBox($$0, $$8, $$9.minX() - $$2, $$9.minY() - $$3, $$9.minZ() - $$4, ($$9.maxX() + 1) - $$2, ($$9.maxY() + 1) - $$3, ($$9.maxZ() + 1) - $$4, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 50 */     Map<String, StructuresDebugPayload.PieceInfo> $$10 = this.postPieces.get($$6);
/* 51 */     if ($$10 != null) {
/* 52 */       for (StructuresDebugPayload.PieceInfo $$11 : $$10.values()) {
/* 53 */         BoundingBox $$12 = $$11.boundingBox();
/*    */         
/* 55 */         if ($$7.closerThan((Vec3i)$$12.getCenter(), 500.0D)) {
/* 56 */           if ($$11.isStart()) {
/* 57 */             LevelRenderer.renderLineBox($$0, $$8, $$12.minX() - $$2, $$12.minY() - $$3, $$12.minZ() - $$4, ($$12.maxX() + 1) - $$2, ($$12.maxY() + 1) - $$3, ($$12.maxZ() + 1) - $$4, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F, 1.0F, 0.0F); continue;
/*    */           } 
/* 59 */           LevelRenderer.renderLineBox($$0, $$8, $$12.minX() - $$2, $$12.minY() - $$3, $$12.minZ() - $$4, ($$12.maxX() + 1) - $$2, ($$12.maxY() + 1) - $$3, ($$12.maxZ() + 1) - $$4, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F, 1.0F);
/*    */         } 
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void addBoundingBox(BoundingBox $$0, List<StructuresDebugPayload.PieceInfo> $$1, ResourceKey<Level> $$2) {
/* 67 */     ((Map<String, BoundingBox>)this.postMainBoxes.computeIfAbsent($$2, $$0 -> new HashMap<>())).put($$0.toString(), $$0);
/*    */     
/* 69 */     Map<String, StructuresDebugPayload.PieceInfo> $$3 = this.postPieces.computeIfAbsent($$2, $$0 -> new HashMap<>());
/*    */     
/* 71 */     for (StructuresDebugPayload.PieceInfo $$4 : $$1) {
/* 72 */       $$3.put($$4.boundingBox().toString(), $$4);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 78 */     this.postMainBoxes.clear();
/* 79 */     this.postPieces.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\StructureRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */