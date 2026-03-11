/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.blaze3d.platform.MemoryTracker;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntConsumer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.Mth;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.joml.Vector3f;
/*     */ import org.lwjgl.system.MemoryUtil;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class BufferBuilder
/*     */   extends DefaultedVertexConsumer implements BufferVertexConsumer {
/*     */   private static final int MAX_GROWTH_SIZE = 2097152;
/*  19 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private ByteBuffer buffer;
/*     */   
/*     */   private boolean closed;
/*     */   
/*     */   private int renderedBufferCount;
/*     */   
/*     */   private int renderedBufferPointer;
/*     */   
/*     */   private int nextElementByte;
/*     */   
/*     */   private int vertices;
/*     */   
/*     */   @Nullable
/*     */   private VertexFormatElement currentElement;
/*     */   
/*     */   private int elementIndex;
/*     */   private VertexFormat format;
/*     */   private VertexFormat.Mode mode;
/*     */   private boolean fastFormat;
/*     */   private boolean fullFormat;
/*     */   private boolean building;
/*     */   @Nullable
/*     */   private Vector3f[] sortingPoints;
/*     */   @Nullable
/*     */   private VertexSorting sorting;
/*     */   private boolean indexOnly;
/*     */   
/*     */   public BufferBuilder(int $$0) {
/*  49 */     this.buffer = MemoryTracker.create($$0);
/*     */   }
/*     */   
/*     */   private void ensureVertexCapacity() {
/*  53 */     ensureCapacity(this.format.getVertexSize());
/*     */   }
/*     */   
/*     */   private void ensureCapacity(int $$0) {
/*  57 */     if (this.nextElementByte + $$0 <= this.buffer.capacity()) {
/*     */       return;
/*     */     }
/*     */     
/*  61 */     int $$1 = this.buffer.capacity();
/*  62 */     int $$2 = Math.min($$1, 2097152);
/*  63 */     int $$3 = $$1 + $$0;
/*  64 */     int $$4 = Math.max($$1 + $$2, $$3);
/*  65 */     LOGGER.debug("Needed to grow BufferBuilder buffer: Old size {} bytes, new size {} bytes.", Integer.valueOf($$1), Integer.valueOf($$4));
/*     */     
/*  67 */     ByteBuffer $$5 = MemoryTracker.resize(this.buffer, $$4);
/*  68 */     $$5.rewind();
/*     */     
/*  70 */     this.buffer = $$5;
/*     */   }
/*     */   
/*     */   public void setQuadSorting(VertexSorting $$0) {
/*  74 */     if (this.mode != VertexFormat.Mode.QUADS) {
/*     */       return;
/*     */     }
/*     */     
/*  78 */     this.sorting = $$0;
/*  79 */     if (this.sortingPoints == null) {
/*  80 */       this.sortingPoints = makeQuadSortingPoints();
/*     */     }
/*     */   }
/*     */   
/*     */   public SortState getSortState() {
/*  85 */     return new SortState(this.mode, this.vertices, this.sortingPoints, this.sorting);
/*     */   }
/*     */   
/*     */   private void checkOpen() {
/*  89 */     if (this.closed) {
/*  90 */       throw new IllegalStateException("This BufferBuilder has been closed");
/*     */     }
/*     */   }
/*     */   
/*     */   public void restoreSortState(SortState $$0) {
/*  95 */     checkOpen();
/*  96 */     this.buffer.rewind();
/*     */     
/*  98 */     this.mode = $$0.mode;
/*  99 */     this.vertices = $$0.vertices;
/* 100 */     this.nextElementByte = this.renderedBufferPointer;
/*     */     
/* 102 */     this.sortingPoints = $$0.sortingPoints;
/* 103 */     this.sorting = $$0.sorting;
/* 104 */     this.indexOnly = true;
/*     */   }
/*     */   
/*     */   public void begin(VertexFormat.Mode $$0, VertexFormat $$1) {
/* 108 */     if (this.building) {
/* 109 */       throw new IllegalStateException("Already building!");
/*     */     }
/* 111 */     checkOpen();
/* 112 */     this.building = true;
/*     */     
/* 114 */     this.mode = $$0;
/* 115 */     switchFormat($$1);
/*     */     
/* 117 */     this.currentElement = (VertexFormatElement)$$1.getElements().get(0);
/* 118 */     this.elementIndex = 0;
/* 119 */     this.buffer.rewind();
/*     */   }
/*     */   
/*     */   private void switchFormat(VertexFormat $$0) {
/* 123 */     if (this.format == $$0) {
/*     */       return;
/*     */     }
/*     */     
/* 127 */     this.format = $$0;
/*     */     
/* 129 */     boolean $$1 = ($$0 == DefaultVertexFormat.NEW_ENTITY);
/* 130 */     boolean $$2 = ($$0 == DefaultVertexFormat.BLOCK);
/*     */     
/* 132 */     this.fastFormat = ($$1 || $$2);
/* 133 */     this.fullFormat = $$1;
/*     */   }
/*     */   
/*     */   private IntConsumer intConsumer(int $$0, VertexFormat.IndexType $$1) {
/* 137 */     MutableInt $$2 = new MutableInt($$0);
/* 138 */     switch ($$1) { default: throw new IncompatibleClassChangeError();case SHORT: case INT: break; }  return $$1 -> this.buffer.putInt($$0.getAndAdd(4), $$1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Vector3f[] makeQuadSortingPoints() {
/* 145 */     FloatBuffer $$0 = this.buffer.asFloatBuffer();
/* 146 */     int $$1 = this.renderedBufferPointer / 4;
/* 147 */     int $$2 = this.format.getIntegerSize();
/* 148 */     int $$3 = $$2 * this.mode.primitiveStride;
/* 149 */     int $$4 = this.vertices / this.mode.primitiveStride;
/*     */     
/* 151 */     Vector3f[] $$5 = new Vector3f[$$4];
/*     */     
/* 153 */     for (int $$6 = 0; $$6 < $$4; $$6++) {
/* 154 */       float $$7 = $$0.get($$1 + $$6 * $$3 + 0);
/* 155 */       float $$8 = $$0.get($$1 + $$6 * $$3 + 1);
/* 156 */       float $$9 = $$0.get($$1 + $$6 * $$3 + 2);
/*     */       
/* 158 */       float $$10 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 0);
/* 159 */       float $$11 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 1);
/* 160 */       float $$12 = $$0.get($$1 + $$6 * $$3 + $$2 * 2 + 2);
/*     */       
/* 162 */       float $$13 = ($$7 + $$10) / 2.0F;
/* 163 */       float $$14 = ($$8 + $$11) / 2.0F;
/* 164 */       float $$15 = ($$9 + $$12) / 2.0F;
/*     */       
/* 166 */       $$5[$$6] = new Vector3f($$13, $$14, $$15);
/*     */     } 
/*     */     
/* 169 */     return $$5;
/*     */   }
/*     */   
/*     */   private void putSortedQuadIndices(VertexFormat.IndexType $$0) {
/* 173 */     if (this.sortingPoints == null || this.sorting == null) {
/* 174 */       throw new IllegalStateException("Sorting state uninitialized");
/*     */     }
/*     */     
/* 177 */     int[] $$1 = this.sorting.sort(this.sortingPoints);
/*     */     
/* 179 */     IntConsumer $$2 = intConsumer(this.nextElementByte, $$0);
/* 180 */     for (int $$3 : $$1) {
/* 181 */       $$2.accept($$3 * this.mode.primitiveStride + 0);
/* 182 */       $$2.accept($$3 * this.mode.primitiveStride + 1);
/* 183 */       $$2.accept($$3 * this.mode.primitiveStride + 2);
/* 184 */       $$2.accept($$3 * this.mode.primitiveStride + 2);
/* 185 */       $$2.accept($$3 * this.mode.primitiveStride + 3);
/* 186 */       $$2.accept($$3 * this.mode.primitiveStride + 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isCurrentBatchEmpty() {
/* 191 */     return (this.vertices == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RenderedBuffer endOrDiscardIfEmpty() {
/* 199 */     ensureDrawing();
/* 200 */     if (isCurrentBatchEmpty()) {
/* 201 */       reset();
/* 202 */       return null;
/*     */     } 
/* 204 */     RenderedBuffer $$0 = storeRenderedBuffer();
/* 205 */     reset();
/* 206 */     return $$0;
/*     */   }
/*     */   
/*     */   public RenderedBuffer end() {
/* 210 */     ensureDrawing();
/* 211 */     RenderedBuffer $$0 = storeRenderedBuffer();
/* 212 */     reset();
/* 213 */     return $$0;
/*     */   }
/*     */   
/*     */   private void ensureDrawing() {
/* 217 */     if (!this.building)
/* 218 */       throw new IllegalStateException("Not building!"); 
/*     */   }
/*     */   
/*     */   private RenderedBuffer storeRenderedBuffer() {
/*     */     boolean $$6;
/* 223 */     int $$7, $$0 = this.mode.indexCount(this.vertices);
/* 224 */     int $$1 = !this.indexOnly ? (this.vertices * this.format.getVertexSize()) : 0;
/* 225 */     VertexFormat.IndexType $$2 = VertexFormat.IndexType.least(this.vertices);
/*     */ 
/*     */ 
/*     */     
/* 229 */     if (this.sortingPoints != null) {
/* 230 */       int $$3 = Mth.roundToward($$0 * $$2.bytes, 4);
/* 231 */       ensureCapacity($$3);
/*     */       
/* 233 */       putSortedQuadIndices($$2);
/* 234 */       boolean $$4 = false;
/*     */       
/* 236 */       this.nextElementByte += $$3;
/* 237 */       int $$5 = $$1 + $$3;
/*     */     } else {
/* 239 */       $$6 = true;
/* 240 */       $$7 = $$1;
/*     */     } 
/*     */     
/* 243 */     int $$8 = this.renderedBufferPointer;
/* 244 */     this.renderedBufferPointer += $$7;
/* 245 */     this.renderedBufferCount++;
/*     */     
/* 247 */     DrawState $$9 = new DrawState(this.format, this.vertices, $$0, this.mode, $$2, this.indexOnly, $$6);
/* 248 */     return new RenderedBuffer($$8, $$9);
/*     */   }
/*     */   
/*     */   private void reset() {
/* 252 */     this.building = false;
/* 253 */     this.vertices = 0;
/* 254 */     this.currentElement = null;
/* 255 */     this.elementIndex = 0;
/*     */     
/* 257 */     this.sortingPoints = null;
/* 258 */     this.sorting = null;
/* 259 */     this.indexOnly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void putByte(int $$0, byte $$1) {
/* 264 */     this.buffer.put(this.nextElementByte + $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putShort(int $$0, short $$1) {
/* 269 */     this.buffer.putShort(this.nextElementByte + $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void putFloat(int $$0, float $$1) {
/* 274 */     this.buffer.putFloat(this.nextElementByte + $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void endVertex() {
/* 279 */     if (this.elementIndex != 0) {
/* 280 */       throw new IllegalStateException("Not filled all elements of the vertex");
/*     */     }
/* 282 */     this.vertices++;
/* 283 */     ensureVertexCapacity();
/*     */ 
/*     */ 
/*     */     
/* 287 */     if (this.mode == VertexFormat.Mode.LINES || this.mode == VertexFormat.Mode.LINE_STRIP) {
/* 288 */       int $$0 = this.format.getVertexSize();
/* 289 */       this.buffer.put(this.nextElementByte, this.buffer, this.nextElementByte - $$0, $$0);
/*     */       
/* 291 */       this.nextElementByte += $$0;
/* 292 */       this.vertices++;
/* 293 */       ensureVertexCapacity();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextElement() {
/* 299 */     ImmutableList<VertexFormatElement> $$0 = this.format.getElements();
/* 300 */     this.elementIndex = (this.elementIndex + 1) % $$0.size();
/* 301 */     this.nextElementByte += this.currentElement.getByteSize();
/*     */     
/* 303 */     VertexFormatElement $$1 = (VertexFormatElement)$$0.get(this.elementIndex);
/* 304 */     this.currentElement = $$1;
/* 305 */     if ($$1.getUsage() == VertexFormatElement.Usage.PADDING) {
/* 306 */       nextElement();
/*     */     }
/*     */     
/* 309 */     if (this.defaultColorSet && this.currentElement.getUsage() == VertexFormatElement.Usage.COLOR) {
/* 310 */       super.color(this.defaultR, this.defaultG, this.defaultB, this.defaultA);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexConsumer color(int $$0, int $$1, int $$2, int $$3) {
/* 316 */     if (this.defaultColorSet) {
/* 317 */       throw new IllegalStateException();
/*     */     }
/* 319 */     return super.color($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void vertex(float $$0, float $$1, float $$2, float $$3, float $$4, float $$5, float $$6, float $$7, float $$8, int $$9, int $$10, float $$11, float $$12, float $$13) {
/* 324 */     if (this.defaultColorSet) {
/* 325 */       throw new IllegalStateException();
/*     */     }
/*     */     
/* 328 */     if (this.fastFormat) {
/*     */       int $$15;
/* 330 */       putFloat(0, $$0);
/* 331 */       putFloat(4, $$1);
/* 332 */       putFloat(8, $$2);
/*     */       
/* 334 */       putByte(12, (byte)(int)($$3 * 255.0F));
/* 335 */       putByte(13, (byte)(int)($$4 * 255.0F));
/* 336 */       putByte(14, (byte)(int)($$5 * 255.0F));
/* 337 */       putByte(15, (byte)(int)($$6 * 255.0F));
/*     */       
/* 339 */       putFloat(16, $$7);
/* 340 */       putFloat(20, $$8);
/*     */ 
/*     */       
/* 343 */       if (this.fullFormat) {
/* 344 */         putShort(24, (short)($$9 & 0xFFFF));
/* 345 */         putShort(26, (short)($$9 >> 16 & 0xFFFF));
/* 346 */         int $$14 = 28;
/*     */       } else {
/* 348 */         $$15 = 24;
/*     */       } 
/*     */       
/* 351 */       putShort($$15 + 0, (short)($$10 & 0xFFFF));
/* 352 */       putShort($$15 + 2, (short)($$10 >> 16 & 0xFFFF));
/*     */       
/* 354 */       putByte($$15 + 4, BufferVertexConsumer.normalIntValue($$11));
/* 355 */       putByte($$15 + 5, BufferVertexConsumer.normalIntValue($$12));
/* 356 */       putByte($$15 + 6, BufferVertexConsumer.normalIntValue($$13));
/*     */       
/* 358 */       this.nextElementByte += $$15 + 8;
/* 359 */       endVertex();
/*     */       return;
/*     */     } 
/* 362 */     super.vertex($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9, $$10, $$11, $$12, $$13);
/*     */   }
/*     */   
/*     */   void releaseRenderedBuffer() {
/* 366 */     if (this.renderedBufferCount > 0 && 
/* 367 */       --this.renderedBufferCount == 0) {
/* 368 */       clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 374 */     if (this.renderedBufferCount > 0) {
/* 375 */       LOGGER.warn("Clearing BufferBuilder with unused batches");
/*     */     }
/* 377 */     discard();
/*     */   }
/*     */   
/*     */   public void discard() {
/* 381 */     this.renderedBufferCount = 0;
/* 382 */     this.renderedBufferPointer = 0;
/* 383 */     this.nextElementByte = 0;
/*     */   }
/*     */   
/*     */   public void release() {
/* 387 */     if (this.renderedBufferCount > 0) {
/* 388 */       throw new IllegalStateException("BufferBuilder closed with unused batches");
/*     */     }
/* 390 */     if (this.building) {
/* 391 */       throw new IllegalStateException("Cannot close BufferBuilder while it is building");
/*     */     }
/* 393 */     if (this.closed) {
/*     */       return;
/*     */     }
/* 396 */     this.closed = true;
/* 397 */     MemoryTracker.free(this.buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public VertexFormatElement currentElement() {
/* 402 */     if (this.currentElement == null) {
/* 403 */       throw new IllegalStateException("BufferBuilder not started");
/*     */     }
/* 405 */     return this.currentElement;
/*     */   }
/*     */   
/*     */   public boolean building() {
/* 409 */     return this.building;
/*     */   }
/*     */   
/*     */   ByteBuffer bufferSlice(int $$0, int $$1) {
/* 413 */     return MemoryUtil.memSlice(this.buffer, $$0, $$1 - $$0);
/*     */   }
/*     */   
/*     */   public static class SortState
/*     */   {
/*     */     final VertexFormat.Mode mode;
/*     */     final int vertices;
/*     */     @Nullable
/*     */     final Vector3f[] sortingPoints;
/*     */     @Nullable
/*     */     final VertexSorting sorting;
/*     */     
/*     */     SortState(VertexFormat.Mode $$0, int $$1, @Nullable Vector3f[] $$2, @Nullable VertexSorting $$3) {
/* 426 */       this.mode = $$0;
/* 427 */       this.vertices = $$1;
/*     */       
/* 429 */       this.sortingPoints = $$2;
/* 430 */       this.sorting = $$3;
/*     */     }
/*     */   }
/*     */   
/*     */   public class RenderedBuffer {
/*     */     private final int pointer;
/*     */     private final BufferBuilder.DrawState drawState;
/*     */     private boolean released;
/*     */     
/*     */     RenderedBuffer(int $$1, BufferBuilder.DrawState $$2) {
/* 440 */       this.pointer = $$1;
/* 441 */       this.drawState = $$2;
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ByteBuffer vertexBuffer() {
/* 446 */       if (this.drawState.indexOnly()) {
/* 447 */         return null;
/*     */       }
/* 449 */       int $$0 = this.pointer + this.drawState.vertexBufferStart();
/* 450 */       int $$1 = this.pointer + this.drawState.vertexBufferEnd();
/* 451 */       return BufferBuilder.this.bufferSlice($$0, $$1);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public ByteBuffer indexBuffer() {
/* 456 */       if (this.drawState.sequentialIndex()) {
/* 457 */         return null;
/*     */       }
/* 459 */       int $$0 = this.pointer + this.drawState.indexBufferStart();
/* 460 */       int $$1 = this.pointer + this.drawState.indexBufferEnd();
/* 461 */       return BufferBuilder.this.bufferSlice($$0, $$1);
/*     */     }
/*     */     
/*     */     public BufferBuilder.DrawState drawState() {
/* 465 */       return this.drawState;
/*     */     }
/*     */     
/*     */     public boolean isEmpty() {
/* 469 */       return (this.drawState.vertexCount == 0);
/*     */     }
/*     */     
/*     */     public void release() {
/* 473 */       if (this.released) {
/* 474 */         throw new IllegalStateException("Buffer has already been released!");
/*     */       }
/* 476 */       BufferBuilder.this.releaseRenderedBuffer();
/* 477 */       this.released = true;
/*     */     } }
/*     */   public static final class DrawState extends Record { private final VertexFormat format; final int vertexCount; private final int indexCount; private final VertexFormat.Mode mode; private final VertexFormat.IndexType indexType; private final boolean indexOnly; private final boolean sequentialIndex;
/*     */     
/* 481 */     public DrawState(VertexFormat $$0, int $$1, int $$2, VertexFormat.Mode $$3, VertexFormat.IndexType $$4, boolean $$5, boolean $$6) { this.format = $$0; this.vertexCount = $$1; this.indexCount = $$2; this.mode = $$3; this.indexType = $$4; this.indexOnly = $$5; this.sequentialIndex = $$6; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #481	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 481 */       //   0	7	0	this	Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState; } public VertexFormat format() { return this.format; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #481	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #481	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lcom/mojang/blaze3d/vertex/BufferBuilder$DrawState;
/* 481 */       //   0	8	1	$$0	Ljava/lang/Object; } public int vertexCount() { return this.vertexCount; } public int indexCount() { return this.indexCount; } public VertexFormat.Mode mode() { return this.mode; } public VertexFormat.IndexType indexType() { return this.indexType; } public boolean indexOnly() { return this.indexOnly; } public boolean sequentialIndex() { return this.sequentialIndex; }
/*     */      public int vertexBufferSize() {
/* 483 */       return this.vertexCount * this.format.getVertexSize();
/*     */     }
/*     */     
/*     */     public int vertexBufferStart() {
/* 487 */       return 0;
/*     */     }
/*     */     
/*     */     public int vertexBufferEnd() {
/* 491 */       return vertexBufferSize();
/*     */     }
/*     */     
/*     */     public int indexBufferStart() {
/* 495 */       return this.indexOnly ? 0 : vertexBufferEnd();
/*     */     }
/*     */     
/*     */     public int indexBufferEnd() {
/* 499 */       return indexBufferStart() + indexBufferSize();
/*     */     }
/*     */     
/*     */     private int indexBufferSize() {
/* 503 */       return this.sequentialIndex ? 0 : (this.indexCount * this.indexType.bytes);
/*     */     }
/*     */     
/*     */     public int bufferSize() {
/* 507 */       return indexBufferEnd();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\BufferBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */