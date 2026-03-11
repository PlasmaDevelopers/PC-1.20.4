/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.renderer.LevelRenderer;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ 
/*    */ public class WorldGenAttemptRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/* 14 */   private final List<BlockPos> toRender = Lists.newArrayList();
/* 15 */   private final List<Float> scales = Lists.newArrayList();
/* 16 */   private final List<Float> alphas = Lists.newArrayList();
/* 17 */   private final List<Float> reds = Lists.newArrayList();
/* 18 */   private final List<Float> greens = Lists.newArrayList();
/* 19 */   private final List<Float> blues = Lists.newArrayList();
/*    */   
/*    */   public void addPos(BlockPos $$0, float $$1, float $$2, float $$3, float $$4, float $$5) {
/* 22 */     this.toRender.add($$0);
/* 23 */     this.scales.add(Float.valueOf($$1));
/* 24 */     this.alphas.add(Float.valueOf($$5));
/* 25 */     this.reds.add(Float.valueOf($$2));
/* 26 */     this.greens.add(Float.valueOf($$3));
/* 27 */     this.blues.add(Float.valueOf($$4));
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 32 */     VertexConsumer $$5 = $$1.getBuffer(RenderType.debugFilledBox());
/*    */     
/* 34 */     for (int $$6 = 0; $$6 < this.toRender.size(); $$6++) {
/* 35 */       BlockPos $$7 = this.toRender.get($$6);
/* 36 */       Float $$8 = this.scales.get($$6);
/*    */       
/* 38 */       float $$9 = $$8.floatValue() / 2.0F;
/* 39 */       LevelRenderer.addChainedFilledBoxVertices($$0, $$5, ($$7
/*    */ 
/*    */           
/* 42 */           .getX() + 0.5F - $$9) - $$2, ($$7
/* 43 */           .getY() + 0.5F - $$9) - $$3, ($$7
/* 44 */           .getZ() + 0.5F - $$9) - $$4, ($$7
/* 45 */           .getX() + 0.5F + $$9) - $$2, ($$7
/* 46 */           .getY() + 0.5F + $$9) - $$3, ($$7
/* 47 */           .getZ() + 0.5F + $$9) - $$4, ((Float)this.reds
/* 48 */           .get($$6)).floatValue(), ((Float)this.greens
/* 49 */           .get($$6)).floatValue(), ((Float)this.blues
/* 50 */           .get($$6)).floatValue(), ((Float)this.alphas
/* 51 */           .get($$6)).floatValue());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\WorldGenAttemptRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */