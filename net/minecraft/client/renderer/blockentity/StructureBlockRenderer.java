/*     */ package net.minecraft.client.renderer.blockentity;
/*     */ 
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ 
/*     */ public class StructureBlockRenderer implements BlockEntityRenderer<StructureBlockEntity> {
/*     */   public void render(StructureBlockEntity $$0, float $$1, PoseStack $$2, MultiBufferSource $$3, int $$4, int $$5) {
/*     */     double $$12, $$14, $$16, $$13, $$15, $$17, $$18, $$22, $$26, $$30, $$19, $$23, $$27, $$31, $$20, $$24, $$28, $$32, $$21, $$25, $$29, $$33;
/*  23 */     if (!(Minecraft.getInstance()).player.canUseGameMasterBlocks() && !(Minecraft.getInstance()).player.isSpectator()) {
/*     */       return;
/*     */     }
/*     */     
/*  27 */     BlockPos $$6 = $$0.getStructurePos();
/*  28 */     Vec3i $$7 = $$0.getStructureSize();
/*     */     
/*  30 */     if ($$7.getX() < 1 || $$7.getY() < 1 || $$7.getZ() < 1) {
/*     */       return;
/*     */     }
/*  33 */     if ($$0.getMode() != StructureMode.SAVE && $$0.getMode() != StructureMode.LOAD) {
/*     */       return;
/*     */     }
/*     */     
/*  37 */     double $$8 = $$6.getX();
/*  38 */     double $$9 = $$6.getZ();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  43 */     double $$10 = $$6.getY();
/*     */ 
/*     */ 
/*     */     
/*  47 */     double $$11 = $$10 + $$7.getY();
/*     */ 
/*     */     
/*  50 */     switch ($$0.getMirror()) {
/*     */       case CLOCKWISE_90:
/*  52 */         $$12 = $$7.getX();
/*  53 */         $$13 = -$$7.getZ();
/*     */         break;
/*     */       case CLOCKWISE_180:
/*  56 */         $$14 = -$$7.getX();
/*  57 */         $$15 = $$7.getZ();
/*     */         break;
/*     */       default:
/*  60 */         $$16 = $$7.getX();
/*  61 */         $$17 = $$7.getZ();
/*     */         break;
/*     */     } 
/*     */     
/*  65 */     switch ($$0.getRotation()) {
/*     */       case CLOCKWISE_90:
/*  67 */         $$18 = ($$17 < 0.0D) ? $$8 : ($$8 + 1.0D);
/*  68 */         $$19 = ($$16 < 0.0D) ? ($$9 + 1.0D) : $$9;
/*  69 */         $$20 = $$18 - $$17;
/*  70 */         $$21 = $$19 + $$16;
/*     */         break;
/*     */       case CLOCKWISE_180:
/*  73 */         $$22 = ($$16 < 0.0D) ? $$8 : ($$8 + 1.0D);
/*  74 */         $$23 = ($$17 < 0.0D) ? $$9 : ($$9 + 1.0D);
/*  75 */         $$24 = $$22 - $$16;
/*  76 */         $$25 = $$23 - $$17;
/*     */         break;
/*     */       case COUNTERCLOCKWISE_90:
/*  79 */         $$26 = ($$17 < 0.0D) ? ($$8 + 1.0D) : $$8;
/*  80 */         $$27 = ($$16 < 0.0D) ? $$9 : ($$9 + 1.0D);
/*  81 */         $$28 = $$26 + $$17;
/*  82 */         $$29 = $$27 - $$16;
/*     */         break;
/*     */       default:
/*  85 */         $$30 = ($$16 < 0.0D) ? ($$8 + 1.0D) : $$8;
/*  86 */         $$31 = ($$17 < 0.0D) ? ($$9 + 1.0D) : $$9;
/*  87 */         $$32 = $$30 + $$16;
/*  88 */         $$33 = $$31 + $$17;
/*     */         break;
/*     */     } 
/*     */     
/*  92 */     float $$34 = 1.0F;
/*  93 */     float $$35 = 0.9F;
/*  94 */     float $$36 = 0.5F;
/*     */     
/*  96 */     VertexConsumer $$37 = $$3.getBuffer(RenderType.lines());
/*     */     
/*  98 */     if ($$0.getMode() == StructureMode.SAVE || $$0.getShowBoundingBox()) {
/*  99 */       LevelRenderer.renderLineBox($$2, $$37, $$30, $$10, $$31, $$32, $$11, $$33, 0.9F, 0.9F, 0.9F, 1.0F, 0.5F, 0.5F, 0.5F);
/*     */     }
/*     */     
/* 102 */     if ($$0.getMode() == StructureMode.SAVE && $$0.getShowAir())
/* 103 */       renderInvisibleBlocks($$0, $$37, $$6, $$2); 
/*     */   }
/*     */   public StructureBlockRenderer(BlockEntityRendererProvider.Context $$0) {}
/*     */   
/*     */   private void renderInvisibleBlocks(StructureBlockEntity $$0, VertexConsumer $$1, BlockPos $$2, PoseStack $$3) {
/* 108 */     Level level = $$0.getLevel();
/* 109 */     BlockPos $$5 = $$0.getBlockPos();
/* 110 */     BlockPos $$6 = $$5.offset((Vec3i)$$2);
/*     */     
/* 112 */     for (BlockPos $$7 : BlockPos.betweenClosed($$6, $$6.offset($$0.getStructureSize()).offset(-1, -1, -1))) {
/* 113 */       BlockState $$8 = level.getBlockState($$7);
/* 114 */       boolean $$9 = $$8.isAir();
/* 115 */       boolean $$10 = $$8.is(Blocks.STRUCTURE_VOID);
/* 116 */       boolean $$11 = $$8.is(Blocks.BARRIER);
/* 117 */       boolean $$12 = $$8.is(Blocks.LIGHT);
/* 118 */       boolean $$13 = ($$10 || $$11 || $$12);
/* 119 */       if ($$9 || $$13) {
/* 120 */         float $$14 = $$9 ? 0.05F : 0.0F;
/*     */         
/* 122 */         double $$15 = (($$7.getX() - $$5.getX()) + 0.45F - $$14);
/* 123 */         double $$16 = (($$7.getY() - $$5.getY()) + 0.45F - $$14);
/* 124 */         double $$17 = (($$7.getZ() - $$5.getZ()) + 0.45F - $$14);
/* 125 */         double $$18 = (($$7.getX() - $$5.getX()) + 0.55F + $$14);
/* 126 */         double $$19 = (($$7.getY() - $$5.getY()) + 0.55F + $$14);
/* 127 */         double $$20 = (($$7.getZ() - $$5.getZ()) + 0.55F + $$14);
/*     */         
/* 129 */         if ($$9) {
/* 130 */           LevelRenderer.renderLineBox($$3, $$1, $$15, $$16, $$17, $$18, $$19, $$20, 0.5F, 0.5F, 1.0F, 1.0F, 0.5F, 0.5F, 1.0F); continue;
/* 131 */         }  if ($$10) {
/* 132 */           LevelRenderer.renderLineBox($$3, $$1, $$15, $$16, $$17, $$18, $$19, $$20, 1.0F, 0.75F, 0.75F, 1.0F, 1.0F, 0.75F, 0.75F); continue;
/* 133 */         }  if ($$11) {
/* 134 */           LevelRenderer.renderLineBox($$3, $$1, $$15, $$16, $$17, $$18, $$19, $$20, 1.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.0F, 0.0F); continue;
/* 135 */         }  if ($$12) {
/* 136 */           LevelRenderer.renderLineBox($$3, $$1, $$15, $$16, $$17, $$18, $$19, $$20, 1.0F, 1.0F, 0.0F, 1.0F, 1.0F, 1.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRenderOffScreen(StructureBlockEntity $$0) {
/* 144 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getViewDistance() {
/* 149 */     return 96;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\blockentity\StructureBlockRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */