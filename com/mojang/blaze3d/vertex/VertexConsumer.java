/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.FastColor;
/*     */ import org.joml.Matrix3f;
/*     */ import org.joml.Matrix4f;
/*     */ import org.joml.Vector3f;
/*     */ import org.joml.Vector4f;
/*     */ import org.lwjgl.system.MemoryStack;
/*     */ 
/*     */ public interface VertexConsumer
/*     */ {
/*     */   VertexConsumer vertex(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */   
/*     */   VertexConsumer color(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */   
/*     */   VertexConsumer uv(float paramFloat1, float paramFloat2);
/*     */   
/*     */   VertexConsumer overlayCoords(int paramInt1, int paramInt2);
/*     */   
/*     */   VertexConsumer uv2(int paramInt1, int paramInt2);
/*     */   
/*     */   VertexConsumer normal(float paramFloat1, float paramFloat2, float paramFloat3);
/*     */   
/*     */   void endVertex();
/*     */   
/*     */   default void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/*  31 */     vertex($$0, $$1, $$2);
/*  32 */     color($$3, $$4, $$5, $$6);
/*  33 */     uv($$7, $$8);
/*  34 */     overlayCoords($$9);
/*  35 */     uv2($$10);
/*  36 */     normal($$11, $$12, $$13);
/*  37 */     endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   void defaultColor(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */   
/*     */   void unsetDefaultColor();
/*     */ 
/*     */   
/*     */   default VertexConsumer color(float $$0, float $$1, float $$2, float $$3) {
/*  48 */     return color((int)($$0 * 255.0F), (int)($$1 * 255.0F), (int)($$2 * 255.0F), (int)($$3 * 255.0F));
/*     */   }
/*     */   
/*     */   default VertexConsumer color(int $$0) {
/*  52 */     return color(FastColor.ARGB32.red($$0), FastColor.ARGB32.green($$0), FastColor.ARGB32.blue($$0), FastColor.ARGB32.alpha($$0));
/*     */   }
/*     */   
/*     */   default VertexConsumer uv2(int $$0) {
/*  56 */     return uv2($$0 & 0xFFFF, $$0 >> 16 & 0xFFFF);
/*     */   }
/*     */   
/*     */   default VertexConsumer overlayCoords(int $$0) {
/*  60 */     return overlayCoords($$0 & 0xFFFF, $$0 >> 16 & 0xFFFF);
/*     */   }
/*     */   
/*     */   default void putBulkData(PoseStack.Pose $$0, BakedQuad $$1, float $$2, float $$3, float $$4, int $$5, int $$6) {
/*  64 */     putBulkData($$0, $$1, new float[] { 1.0F, 1.0F, 1.0F, 1.0F }, $$2, $$3, $$4, new int[] { $$5, $$5, $$5, $$5 }, $$6, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   default void putBulkData(PoseStack.Pose $$0, BakedQuad $$1, float[] $$2, float $$3, float $$4, float $$5, int[] $$6, int $$7, boolean $$8) {
/*  71 */     float[] $$9 = { $$2[0], $$2[1], $$2[2], $$2[3] };
/*  72 */     int[] $$10 = { $$6[0], $$6[1], $$6[2], $$6[3] };
/*  73 */     int[] $$11 = $$1.getVertices();
/*  74 */     Vec3i $$12 = $$1.getDirection().getNormal();
/*     */     
/*  76 */     Matrix4f $$13 = $$0.pose();
/*  77 */     Vector3f $$14 = $$0.normal().transform(new Vector3f($$12.getX(), $$12.getY(), $$12.getZ()));
/*     */     
/*  79 */     int $$15 = 8;
/*  80 */     int $$16 = $$11.length / 8;
/*     */     
/*  82 */     MemoryStack $$17 = MemoryStack.stackPush(); 
/*  83 */     try { ByteBuffer $$18 = $$17.malloc(DefaultVertexFormat.BLOCK.getVertexSize());
/*  84 */       IntBuffer $$19 = $$18.asIntBuffer();
/*     */       
/*  86 */       for (int $$20 = 0; $$20 < $$16; $$20++) {
/*  87 */         float $$30, $$31, $$32; $$19.clear();
/*  88 */         $$19.put($$11, $$20 * 8, 8);
/*     */         
/*  90 */         float $$21 = $$18.getFloat(0);
/*  91 */         float $$22 = $$18.getFloat(4);
/*  92 */         float $$23 = $$18.getFloat(8);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  97 */         if ($$8) {
/*  98 */           float $$24 = ($$18.get(12) & 0xFF) / 255.0F;
/*  99 */           float $$25 = ($$18.get(13) & 0xFF) / 255.0F;
/* 100 */           float $$26 = ($$18.get(14) & 0xFF) / 255.0F;
/*     */           
/* 102 */           float $$27 = $$24 * $$9[$$20] * $$3;
/* 103 */           float $$28 = $$25 * $$9[$$20] * $$4;
/* 104 */           float $$29 = $$26 * $$9[$$20] * $$5;
/*     */         } else {
/* 106 */           $$30 = $$9[$$20] * $$3;
/* 107 */           $$31 = $$9[$$20] * $$4;
/* 108 */           $$32 = $$9[$$20] * $$5;
/*     */         } 
/*     */         
/* 111 */         int $$33 = $$10[$$20];
/* 112 */         float $$34 = $$18.getFloat(16);
/* 113 */         float $$35 = $$18.getFloat(20);
/*     */         
/* 115 */         Vector4f $$36 = $$13.transform(new Vector4f($$21, $$22, $$23, 1.0F));
/* 116 */         vertex($$36.x(), $$36.y(), $$36.z(), $$30, $$31, $$32, 1.0F, $$34, $$35, $$7, $$33, $$14.x(), $$14.y(), $$14.z());
/*     */       } 
/* 118 */       if ($$17 != null) $$17.close();  } catch (Throwable throwable) { if ($$17 != null)
/*     */         try { $$17.close(); }
/*     */         catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */           throw throwable; }
/* 122 */      } default VertexConsumer vertex(Matrix4f $$0, float $$1, float $$2, float $$3) { Vector4f $$4 = $$0.transform(new Vector4f($$1, $$2, $$3, 1.0F));
/* 123 */     return vertex($$4.x(), $$4.y(), $$4.z()); }
/*     */ 
/*     */   
/*     */   default VertexConsumer normal(Matrix3f $$0, float $$1, float $$2, float $$3) {
/* 127 */     Vector3f $$4 = $$0.transform(new Vector3f($$1, $$2, $$3));
/* 128 */     return normal($$4.x(), $$4.y(), $$4.z());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexConsumer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */