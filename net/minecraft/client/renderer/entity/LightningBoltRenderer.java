/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.mojang.blaze3d.vertex.PoseStack;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import net.minecraft.client.renderer.MultiBufferSource;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlas;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LightningBolt;
/*     */ import org.joml.Matrix4f;
/*     */ 
/*     */ public class LightningBoltRenderer extends EntityRenderer<LightningBolt> {
/*     */   public LightningBoltRenderer(EntityRendererProvider.Context $$0) {
/*  15 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(LightningBolt $$0, float $$1, float $$2, PoseStack $$3, MultiBufferSource $$4, int $$5) {
/*  20 */     float[] $$6 = new float[8];
/*  21 */     float[] $$7 = new float[8];
/*  22 */     float $$8 = 0.0F;
/*  23 */     float $$9 = 0.0F;
/*     */     
/*  25 */     RandomSource $$10 = RandomSource.create($$0.seed);
/*  26 */     for (int $$11 = 7; $$11 >= 0; $$11--) {
/*  27 */       $$6[$$11] = $$8;
/*  28 */       $$7[$$11] = $$9;
/*  29 */       $$8 += ($$10.nextInt(11) - 5);
/*  30 */       $$9 += ($$10.nextInt(11) - 5);
/*     */     } 
/*     */ 
/*     */     
/*  34 */     VertexConsumer $$12 = $$4.getBuffer(RenderType.lightning());
/*  35 */     Matrix4f $$13 = $$3.last().pose();
/*  36 */     for (int $$14 = 0; $$14 < 4; $$14++) {
/*  37 */       RandomSource $$15 = RandomSource.create($$0.seed);
/*  38 */       for (int $$16 = 0; $$16 < 3; $$16++) {
/*  39 */         int $$17 = 7;
/*  40 */         int $$18 = 0;
/*  41 */         if ($$16 > 0) {
/*  42 */           $$17 = 7 - $$16;
/*     */         }
/*  44 */         if ($$16 > 0) {
/*  45 */           $$18 = $$17 - 2;
/*     */         }
/*  47 */         float $$19 = $$6[$$17] - $$8;
/*  48 */         float $$20 = $$7[$$17] - $$9;
/*  49 */         for (int $$21 = $$17; $$21 >= $$18; $$21--) {
/*  50 */           float $$22 = $$19;
/*  51 */           float $$23 = $$20;
/*  52 */           if ($$16 == 0) {
/*  53 */             $$19 += ($$15.nextInt(11) - 5);
/*  54 */             $$20 += ($$15.nextInt(11) - 5);
/*     */           } else {
/*  56 */             $$19 += ($$15.nextInt(31) - 15);
/*  57 */             $$20 += ($$15.nextInt(31) - 15);
/*     */           } 
/*     */           
/*  60 */           float $$24 = 0.5F;
/*  61 */           float $$25 = 0.45F;
/*  62 */           float $$26 = 0.45F;
/*  63 */           float $$27 = 0.5F;
/*     */           
/*  65 */           float $$28 = 0.1F + $$14 * 0.2F;
/*  66 */           if ($$16 == 0) {
/*  67 */             $$28 *= $$21 * 0.1F + 1.0F;
/*     */           }
/*     */           
/*  70 */           float $$29 = 0.1F + $$14 * 0.2F;
/*  71 */           if ($$16 == 0) {
/*  72 */             $$29 *= ($$21 - 1.0F) * 0.1F + 1.0F;
/*     */           }
/*     */           
/*  75 */           quad($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45F, 0.45F, 0.5F, $$28, $$29, false, false, true, false);
/*  76 */           quad($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45F, 0.45F, 0.5F, $$28, $$29, true, false, true, true);
/*  77 */           quad($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45F, 0.45F, 0.5F, $$28, $$29, true, true, false, true);
/*  78 */           quad($$13, $$12, $$19, $$20, $$21, $$22, $$23, 0.45F, 0.45F, 0.5F, $$28, $$29, false, true, false, false);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void quad(Matrix4f $$0, VertexConsumer $$1, float $$2, float $$3, int $$4, float $$5, float $$6, float $$7, float $$8, float $$9, float $$10, float $$11, boolean $$12, boolean $$13, boolean $$14, boolean $$15) {
/*  85 */     $$1
/*  86 */       .vertex($$0, $$2 + (
/*     */         
/*  88 */         $$12 ? $$11 : -$$11), ($$4 * 16), $$3 + (
/*     */         
/*  90 */         $$13 ? $$11 : -$$11))
/*     */       
/*  92 */       .color($$7, $$8, $$9, 0.3F)
/*  93 */       .endVertex();
/*     */     
/*  95 */     $$1
/*  96 */       .vertex($$0, $$5 + (
/*     */         
/*  98 */         $$12 ? $$10 : -$$10), (($$4 + 1) * 16), $$6 + (
/*     */         
/* 100 */         $$13 ? $$10 : -$$10))
/*     */       
/* 102 */       .color($$7, $$8, $$9, 0.3F)
/* 103 */       .endVertex();
/*     */     
/* 105 */     $$1
/* 106 */       .vertex($$0, $$5 + (
/*     */         
/* 108 */         $$14 ? $$10 : -$$10), (($$4 + 1) * 16), $$6 + (
/*     */         
/* 110 */         $$15 ? $$10 : -$$10))
/*     */       
/* 112 */       .color($$7, $$8, $$9, 0.3F)
/* 113 */       .endVertex();
/*     */     
/* 115 */     $$1
/* 116 */       .vertex($$0, $$2 + (
/*     */         
/* 118 */         $$14 ? $$11 : -$$11), ($$4 * 16), $$3 + (
/*     */         
/* 120 */         $$15 ? $$11 : -$$11))
/*     */       
/* 122 */       .color($$7, $$8, $$9, 0.3F)
/* 123 */       .endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation(LightningBolt $$0) {
/* 128 */     return TextureAtlas.LOCATION_BLOCKS;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\entity\LightningBoltRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */