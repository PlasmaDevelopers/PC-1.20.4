/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class VertexFormat
/*     */ {
/*     */   private final ImmutableList<VertexFormatElement> elements;
/*     */   private final ImmutableMap<String, VertexFormatElement> elementMapping;
/*  17 */   private final IntList offsets = (IntList)new IntArrayList();
/*     */   
/*     */   private final int vertexSize;
/*     */   @Nullable
/*     */   private VertexBuffer immediateDrawVertexBuffer;
/*     */   
/*     */   public VertexFormat(ImmutableMap<String, VertexFormatElement> $$0) {
/*  24 */     this.elementMapping = $$0;
/*  25 */     this.elements = $$0.values().asList();
/*     */ 
/*     */     
/*  28 */     int $$1 = 0;
/*  29 */     for (UnmodifiableIterator<VertexFormatElement> unmodifiableIterator = $$0.values().iterator(); unmodifiableIterator.hasNext(); ) { VertexFormatElement $$2 = unmodifiableIterator.next();
/*  30 */       this.offsets.add($$1);
/*  31 */       $$1 += $$2.getByteSize(); }
/*     */     
/*  33 */     this.vertexSize = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  38 */     return "format: " + this.elementMapping.size() + " elements: " + (String)this.elementMapping.entrySet().stream().map(Object::toString).collect(Collectors.joining(" "));
/*     */   }
/*     */   
/*     */   public int getIntegerSize() {
/*  42 */     return getVertexSize() / 4;
/*     */   }
/*     */   
/*     */   public int getVertexSize() {
/*  46 */     return this.vertexSize;
/*     */   }
/*     */   
/*     */   public ImmutableList<VertexFormatElement> getElements() {
/*  50 */     return this.elements;
/*     */   }
/*     */   
/*     */   public ImmutableList<String> getElementAttributeNames() {
/*  54 */     return this.elementMapping.keySet().asList();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  59 */     if (this == $$0) {
/*  60 */       return true;
/*     */     }
/*  62 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  63 */       return false;
/*     */     }
/*     */     
/*  66 */     VertexFormat $$1 = (VertexFormat)$$0;
/*     */     
/*  68 */     if (this.vertexSize != $$1.vertexSize) {
/*  69 */       return false;
/*     */     }
/*     */     
/*  72 */     return this.elementMapping.equals($$1.elementMapping);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  77 */     return this.elementMapping.hashCode();
/*     */   }
/*     */   
/*     */   public void setupBufferState() {
/*  81 */     if (!RenderSystem.isOnRenderThread()) {
/*  82 */       RenderSystem.recordRenderCall(this::_setupBufferState);
/*     */       
/*     */       return;
/*     */     } 
/*  86 */     _setupBufferState();
/*     */   }
/*     */   
/*     */   private void _setupBufferState() {
/*  90 */     int $$0 = getVertexSize();
/*  91 */     ImmutableList<VertexFormatElement> immutableList = getElements();
/*  92 */     for (int $$2 = 0; $$2 < immutableList.size(); $$2++) {
/*  93 */       ((VertexFormatElement)immutableList.get($$2)).setupBufferState($$2, this.offsets.getInt($$2), $$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void clearBufferState() {
/*  98 */     if (!RenderSystem.isOnRenderThread()) {
/*  99 */       RenderSystem.recordRenderCall(this::_clearBufferState);
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     _clearBufferState();
/*     */   }
/*     */   
/*     */   private void _clearBufferState() {
/* 107 */     ImmutableList<VertexFormatElement> $$0 = getElements();
/* 108 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 109 */       VertexFormatElement $$2 = (VertexFormatElement)$$0.get($$1);
/* 110 */       $$2.clearBufferState($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public VertexBuffer getImmediateDrawVertexBuffer() {
/* 115 */     VertexBuffer $$0 = this.immediateDrawVertexBuffer;
/* 116 */     if ($$0 == null) {
/* 117 */       this.immediateDrawVertexBuffer = $$0 = new VertexBuffer(VertexBuffer.Usage.DYNAMIC);
/*     */     }
/* 119 */     return $$0;
/*     */   }
/*     */   
/*     */   public enum IndexType {
/* 123 */     SHORT(5123, 2),
/* 124 */     INT(5125, 4);
/*     */     
/*     */     public final int asGLType;
/*     */     public final int bytes;
/*     */     
/*     */     IndexType(int $$0, int $$1) {
/* 130 */       this.asGLType = $$0;
/* 131 */       this.bytes = $$1;
/*     */     }
/*     */     
/*     */     public static IndexType least(int $$0) {
/* 135 */       if (($$0 & 0xFFFF0000) != 0) {
/* 136 */         return INT;
/*     */       }
/* 138 */       return SHORT;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Mode {
/* 143 */     LINES(4, 2, 2, false),
/* 144 */     LINE_STRIP(5, 2, 1, true),
/* 145 */     DEBUG_LINES(1, 2, 2, false),
/* 146 */     DEBUG_LINE_STRIP(3, 2, 1, true),
/* 147 */     TRIANGLES(4, 3, 3, false),
/* 148 */     TRIANGLE_STRIP(5, 3, 1, true),
/* 149 */     TRIANGLE_FAN(6, 3, 1, true),
/* 150 */     QUADS(4, 4, 4, false);
/*     */     
/*     */     public final int asGLMode;
/*     */     public final int primitiveLength;
/*     */     public final int primitiveStride;
/*     */     public final boolean connectedPrimitives;
/*     */     
/*     */     Mode(int $$0, int $$1, int $$2, boolean $$3) {
/* 158 */       this.asGLMode = $$0;
/* 159 */       this.primitiveLength = $$1;
/* 160 */       this.primitiveStride = $$2;
/* 161 */       this.connectedPrimitives = $$3;
/*     */     }
/*     */     
/*     */     public int indexCount(int $$0) {
/*     */       int $$1, $$2;
/* 166 */       switch (this)
/*     */       { case LINE_STRIP:
/*     */         case DEBUG_LINES:
/*     */         case DEBUG_LINE_STRIP:
/*     */         case TRIANGLES:
/*     */         case TRIANGLE_STRIP:
/*     */         case TRIANGLE_FAN:
/* 173 */           $$1 = $$0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 183 */           return $$1;case LINES: case QUADS: $$2 = $$0 / 4 * 6; return $$2; }  int $$3 = 0; return $$3;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexFormat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */