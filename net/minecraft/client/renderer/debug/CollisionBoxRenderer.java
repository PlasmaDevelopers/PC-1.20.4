/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ 
/*    */ public class CollisionBoxRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   private final Minecraft minecraft;
/* 19 */   private double lastUpdateTime = Double.MIN_VALUE;
/* 20 */   private List<VoxelShape> shapes = Collections.emptyList();
/*    */   
/*    */   public CollisionBoxRenderer(Minecraft $$0) {
/* 23 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 28 */     double $$5 = Util.getNanos();
/* 29 */     if ($$5 - this.lastUpdateTime > 1.0E8D) {
/* 30 */       this.lastUpdateTime = $$5;
/* 31 */       Entity $$6 = this.minecraft.gameRenderer.getMainCamera().getEntity();
/* 32 */       this.shapes = (List<VoxelShape>)ImmutableList.copyOf($$6.level().getCollisions($$6, $$6.getBoundingBox().inflate(6.0D)));
/*    */     } 
/*    */     
/* 35 */     VertexConsumer $$7 = $$1.getBuffer(RenderType.lines());
/* 36 */     for (VoxelShape $$8 : this.shapes)
/* 37 */       LevelRenderer.renderVoxelShape($$0, $$7, $$8, -$$2, -$$3, -$$4, 1.0F, 1.0F, 1.0F, 1.0F, true); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\CollisionBoxRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */