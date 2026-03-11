/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.function.DoubleSupplier;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.player.LocalPlayer;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.phys.shapes.CollisionContext;
/*    */ 
/*    */ public class SupportBlockRenderer implements DebugRenderer.SimpleDebugRenderer {
/* 21 */   private double lastUpdateTime = Double.MIN_VALUE; private final Minecraft minecraft;
/* 22 */   private List<Entity> surroundEntities = Collections.emptyList();
/*    */   
/*    */   public SupportBlockRenderer(Minecraft $$0) {
/* 25 */     this.minecraft = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 30 */     double $$5 = Util.getNanos();
/* 31 */     if ($$5 - this.lastUpdateTime > 1.0E8D) {
/* 32 */       this.lastUpdateTime = $$5;
/* 33 */       Entity $$6 = this.minecraft.gameRenderer.getMainCamera().getEntity();
/* 34 */       this.surroundEntities = (List<Entity>)ImmutableList.copyOf($$6.level().getEntities($$6, $$6.getBoundingBox().inflate(16.0D)));
/*    */     } 
/*    */     
/* 37 */     LocalPlayer localPlayer = this.minecraft.player;
/* 38 */     if (localPlayer != null && ((Player)localPlayer).mainSupportingBlockPos.isPresent()) {
/* 39 */       drawHighlights($$0, $$1, $$2, $$3, $$4, (Entity)localPlayer, () -> 0.0D, 1.0F, 0.0F, 0.0F);
/*    */     }
/*    */     
/* 42 */     for (Entity $$8 : this.surroundEntities) {
/* 43 */       if ($$8 == localPlayer) {
/*    */         continue;
/*    */       }
/* 46 */       drawHighlights($$0, $$1, $$2, $$3, $$4, $$8, () -> getBias($$0), 0.0F, 1.0F, 0.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   private void drawHighlights(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4, Entity $$5, DoubleSupplier $$6, float $$7, float $$8, float $$9) {
/* 51 */     $$5.mainSupportingBlockPos.ifPresent($$10 -> {
/*    */           double $$11 = $$0.getAsDouble();
/*    */           BlockPos $$12 = $$1.getOnPos();
/*    */           highlightPosition($$12, $$2, $$3, $$4, $$5, $$6, 0.02D + $$11, $$7, $$8, $$9);
/*    */           BlockPos $$13 = $$1.getOnPosLegacy();
/*    */           if (!$$13.equals($$12)) {
/*    */             highlightPosition($$13, $$2, $$3, $$4, $$5, $$6, 0.04D + $$11, 0.0F, 1.0F, 1.0F);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private double getBias(Entity $$0) {
/* 65 */     return 0.02D * (String.valueOf($$0.getId() + 0.132453657D).hashCode() % 1000) / 1000.0D;
/*    */   }
/*    */   
/*    */   private void highlightPosition(BlockPos $$0, PoseStack $$1, double $$2, double $$3, double $$4, MultiBufferSource $$5, double $$6, float $$7, float $$8, float $$9) {
/* 69 */     double $$10 = $$0.getX() - $$2 - 2.0D * $$6;
/* 70 */     double $$11 = $$0.getY() - $$3 - 2.0D * $$6;
/* 71 */     double $$12 = $$0.getZ() - $$4 - 2.0D * $$6;
/* 72 */     double $$13 = $$10 + 1.0D + 4.0D * $$6;
/* 73 */     double $$14 = $$11 + 1.0D + 4.0D * $$6;
/* 74 */     double $$15 = $$12 + 1.0D + 4.0D * $$6;
/* 75 */     LevelRenderer.renderLineBox($$1, $$5.getBuffer(RenderType.lines()), $$10, $$11, $$12, $$13, $$14, $$15, $$7, $$8, $$9, 0.4F);
/* 76 */     LevelRenderer.renderVoxelShape($$1, $$5.getBuffer(RenderType.lines()), this.minecraft.level.getBlockState($$0).getCollisionShape((BlockGetter)this.minecraft.level, $$0, CollisionContext.empty()).move($$0.getX(), $$0.getY(), $$0.getZ()), -$$2, -$$3, -$$4, $$7, $$8, $$9, 1.0F, false);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\SupportBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */