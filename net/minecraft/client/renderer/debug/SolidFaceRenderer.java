/*    */ package net.minecraft.client.renderer.debug;
/*    */ 
/*    */ import com.mojang.blaze3d.vertex.PoseStack;
/*    */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.MultiBufferSource;
/*    */ import net.minecraft.client.renderer.RenderType;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.phys.AABB;
/*    */ import net.minecraft.world.phys.shapes.VoxelShape;
/*    */ import org.joml.Matrix4f;
/*    */ 
/*    */ public class SolidFaceRenderer
/*    */   implements DebugRenderer.SimpleDebugRenderer {
/*    */   public SolidFaceRenderer(Minecraft $$0) {
/* 21 */     this.minecraft = $$0;
/*    */   }
/*    */   private final Minecraft minecraft;
/*    */   
/*    */   public void render(PoseStack $$0, MultiBufferSource $$1, double $$2, double $$3, double $$4) {
/* 26 */     Matrix4f $$5 = $$0.last().pose();
/* 27 */     Level level = this.minecraft.player.level();
/*    */     
/* 29 */     BlockPos $$7 = BlockPos.containing($$2, $$3, $$4);
/*    */ 
/*    */     
/* 32 */     for (BlockPos $$8 : BlockPos.betweenClosed($$7.offset(-6, -6, -6), $$7.offset(6, 6, 6))) {
/* 33 */       BlockState $$9 = level.getBlockState($$8);
/*    */       
/* 35 */       if ($$9.is(Blocks.AIR)) {
/*    */         continue;
/*    */       }
/*    */ 
/*    */       
/* 40 */       VoxelShape $$10 = $$9.getShape((BlockGetter)level, $$8);
/* 41 */       for (AABB $$11 : $$10.toAabbs()) {
/* 42 */         AABB $$12 = $$11.move($$8).inflate(0.002D);
/*    */         
/* 44 */         float $$13 = (float)($$12.minX - $$2);
/* 45 */         float $$14 = (float)($$12.minY - $$3);
/* 46 */         float $$15 = (float)($$12.minZ - $$4);
/*    */         
/* 48 */         float $$16 = (float)($$12.maxX - $$2);
/* 49 */         float $$17 = (float)($$12.maxY - $$3);
/* 50 */         float $$18 = (float)($$12.maxZ - $$4);
/*    */         
/* 52 */         float $$19 = 1.0F;
/* 53 */         float $$20 = 0.0F;
/* 54 */         float $$21 = 0.0F;
/* 55 */         float $$22 = 0.5F;
/*    */         
/* 57 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.WEST)) {
/* 58 */           VertexConsumer $$23 = $$1.getBuffer(RenderType.debugFilledBox());
/* 59 */           $$23.vertex($$5, $$13, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 60 */           $$23.vertex($$5, $$13, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 61 */           $$23.vertex($$5, $$13, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 62 */           $$23.vertex($$5, $$13, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/* 64 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.SOUTH)) {
/* 65 */           VertexConsumer $$24 = $$1.getBuffer(RenderType.debugFilledBox());
/* 66 */           $$24.vertex($$5, $$13, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 67 */           $$24.vertex($$5, $$13, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 68 */           $$24.vertex($$5, $$16, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 69 */           $$24.vertex($$5, $$16, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/* 71 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.EAST)) {
/* 72 */           VertexConsumer $$25 = $$1.getBuffer(RenderType.debugFilledBox());
/* 73 */           $$25.vertex($$5, $$16, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 74 */           $$25.vertex($$5, $$16, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 75 */           $$25.vertex($$5, $$16, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 76 */           $$25.vertex($$5, $$16, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/* 78 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.NORTH)) {
/* 79 */           VertexConsumer $$26 = $$1.getBuffer(RenderType.debugFilledBox());
/* 80 */           $$26.vertex($$5, $$16, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 81 */           $$26.vertex($$5, $$16, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 82 */           $$26.vertex($$5, $$13, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 83 */           $$26.vertex($$5, $$13, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/* 85 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.DOWN)) {
/* 86 */           VertexConsumer $$27 = $$1.getBuffer(RenderType.debugFilledBox());
/* 87 */           $$27.vertex($$5, $$13, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 88 */           $$27.vertex($$5, $$16, $$14, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 89 */           $$27.vertex($$5, $$13, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 90 */           $$27.vertex($$5, $$16, $$14, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/* 92 */         if ($$9.isFaceSturdy((BlockGetter)level, $$8, Direction.UP)) {
/* 93 */           VertexConsumer $$28 = $$1.getBuffer(RenderType.debugFilledBox());
/* 94 */           $$28.vertex($$5, $$13, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 95 */           $$28.vertex($$5, $$13, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 96 */           $$28.vertex($$5, $$16, $$17, $$15).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/* 97 */           $$28.vertex($$5, $$16, $$17, $$18).color(1.0F, 0.0F, 0.0F, 0.5F).endVertex();
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\debug\SolidFaceRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */