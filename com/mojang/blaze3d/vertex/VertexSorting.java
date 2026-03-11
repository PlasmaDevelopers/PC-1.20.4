/*    */ package com.mojang.blaze3d.vertex;
/*    */ import com.google.common.primitives.Floats;
/*    */ import it.unimi.dsi.fastutil.ints.IntArrays;
/*    */ import java.util.Objects;
/*    */ import org.joml.Vector3f;
/*    */ 
/*    */ public interface VertexSorting {
/*  8 */   public static final VertexSorting DISTANCE_TO_ORIGIN = byDistance(0.0F, 0.0F, 0.0F); static {
/*  9 */     ORTHOGRAPHIC_Z = byDistance($$0 -> -$$0.z());
/*    */   } public static final VertexSorting ORTHOGRAPHIC_Z;
/*    */   static VertexSorting byDistance(float $$0, float $$1, float $$2) {
/* 12 */     return byDistance(new Vector3f($$0, $$1, $$2));
/*    */   }
/*    */   
/*    */   static VertexSorting byDistance(Vector3f $$0) {
/* 16 */     Objects.requireNonNull($$0); return byDistance($$0::distanceSquared);
/*    */   }
/*    */   
/*    */   static VertexSorting byDistance(DistanceFunction $$0) {
/* 20 */     return $$1 -> {
/*    */         float[] $$2 = new float[$$1.length];
/*    */         int[] $$3 = new int[$$1.length];
/*    */         for (int $$4 = 0; $$4 < $$1.length; $$4++) {
/*    */           $$2[$$4] = $$0.apply($$1[$$4]);
/*    */           $$3[$$4] = $$4;
/*    */         } 
/*    */         IntArrays.mergeSort($$3, ());
/*    */         return $$3;
/*    */       };
/*    */   }
/*    */   
/*    */   int[] sort(Vector3f[] paramArrayOfVector3f);
/*    */   
/*    */   public static interface DistanceFunction {
/*    */     float apply(Vector3f param1Vector3f);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexSorting.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */