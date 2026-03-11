/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
/*     */ 
/*     */ public class VertexFormatElement
/*     */ {
/*     */   private final Type type;
/*     */   private final Usage usage;
/*     */   private final int index;
/*     */   private final int count;
/*     */   private final int byteSize;
/*     */   
/*     */   public VertexFormatElement(int $$0, Type $$1, Usage $$2, int $$3) {
/*  14 */     if (supportsUsage($$0, $$2)) {
/*  15 */       this.usage = $$2;
/*     */     } else {
/*  17 */       throw new IllegalStateException("Multiple vertex elements of the same type other than UVs are not supported");
/*     */     } 
/*     */     
/*  20 */     this.type = $$1;
/*  21 */     this.index = $$0;
/*  22 */     this.count = $$3;
/*     */     
/*  24 */     this.byteSize = $$1.getSize() * this.count;
/*     */   }
/*     */   
/*     */   private boolean supportsUsage(int $$0, Usage $$1) {
/*  28 */     return ($$0 == 0 || $$1 == Usage.UV);
/*     */   }
/*     */   
/*     */   public final Type getType() {
/*  32 */     return this.type;
/*     */   }
/*     */   
/*     */   public final Usage getUsage() {
/*  36 */     return this.usage;
/*     */   }
/*     */   
/*     */   public final int getCount() {
/*  40 */     return this.count;
/*     */   }
/*     */   
/*     */   public final int getIndex() {
/*  44 */     return this.index;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  49 */     return "" + this.count + "," + this.count + "," + this.usage.getName();
/*     */   }
/*     */   
/*     */   public final int getByteSize() {
/*  53 */     return this.byteSize;
/*     */   }
/*     */   
/*     */   public final boolean isPosition() {
/*  57 */     return (this.usage == Usage.POSITION);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  62 */     if (this == $$0) {
/*  63 */       return true;
/*     */     }
/*  65 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  66 */       return false;
/*     */     }
/*     */     
/*  69 */     VertexFormatElement $$1 = (VertexFormatElement)$$0;
/*     */     
/*  71 */     if (this.count != $$1.count) {
/*  72 */       return false;
/*     */     }
/*  74 */     if (this.index != $$1.index) {
/*  75 */       return false;
/*     */     }
/*  77 */     if (this.type != $$1.type) {
/*  78 */       return false;
/*     */     }
/*  80 */     return (this.usage == $$1.usage);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  85 */     int $$0 = this.type.hashCode();
/*  86 */     $$0 = 31 * $$0 + this.usage.hashCode();
/*  87 */     $$0 = 31 * $$0 + this.index;
/*  88 */     $$0 = 31 * $$0 + this.count;
/*  89 */     return $$0;
/*     */   }
/*     */   
/*     */   public void setupBufferState(int $$0, long $$1, int $$2) {
/*  93 */     this.usage.setupBufferState(this.count, this.type.getGlType(), $$2, $$1, this.index, $$0);
/*     */   }
/*     */   
/*     */   public void clearBufferState(int $$0) {
/*  97 */     this.usage.clearBufferState(this.index, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Usage
/*     */   {
/*     */     GENERIC,
/* 129 */     PADDING("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }(String)(($$0, $$1) -> {  })), UV("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }(String)(($$0, $$1) -> {  })), COLOR("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }(String)(($$0, $$1) -> {  })), NORMAL("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }(String)(($$0, $$1) -> {  })), POSITION("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }(String)(($$0, $$1) -> {
/*     */       
/* 131 */       })); private final ClearState clearState; private final SetupState setupState; static { GENERIC = new Usage("GENERIC", 5, "Generic", ($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */             GlStateManager._enableVertexAttribArray($$5);
/*     */             GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3);
/*     */           }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Usage(String $$0, SetupState $$1, ClearState $$2) {
/* 143 */       this.name = $$0;
/* 144 */       this.setupState = $$1;
/* 145 */       this.clearState = $$2;
/*     */     } static { POSITION = new Usage("POSITION", 0, "Position", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); NORMAL = new Usage("NORMAL", 1, "Normal", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, true, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); COLOR = new Usage("COLOR", 2, "Vertex Color", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, true, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); UV = new Usage("UV", 3, "UV", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); if ($$1 == 5126) { GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3); }
/*     */             else { GlStateManager._vertexAttribIPointer($$5, $$0, $$1, $$2, $$3); }
/*     */           
/* 149 */           }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); } void setupBufferState(int $$0, int $$1, int $$2, long $$3, int $$4, int $$5) { this.setupState.setupBufferState($$0, $$1, $$2, $$3, $$4, $$5); }
/*     */ 
/*     */     
/*     */     public void clearBufferState(int $$0, int $$1) {
/* 153 */       this.clearState.clearBufferState($$0, $$1);
/*     */     }
/*     */     
/*     */     public String getName() {
/* 157 */       return this.name;
/*     */     }
/*     */     
/*     */     @FunctionalInterface
/*     */     private static interface SetupState {
/*     */       void setupBufferState(int param2Int1, int param2Int2, int param2Int3, long param2Long, int param2Int4, int param2Int5); }
/*     */     
/*     */     @FunctionalInterface
/*     */     private static interface ClearState {
/*     */       void clearBufferState(int param2Int1, int param2Int2);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Type {
/* 171 */     FLOAT(4, "Float", 5126),
/* 172 */     UBYTE(1, "Unsigned Byte", 5121),
/* 173 */     BYTE(1, "Byte", 5120),
/* 174 */     USHORT(2, "Unsigned Short", 5123),
/* 175 */     SHORT(2, "Short", 5122),
/* 176 */     UINT(4, "Unsigned Int", 5125),
/* 177 */     INT(4, "Int", 5124);
/*     */     
/*     */     private final int size;
/*     */     private final String name;
/*     */     private final int glType;
/*     */     
/*     */     Type(int $$0, String $$1, int $$2) {
/* 184 */       this.size = $$0;
/* 185 */       this.name = $$1;
/* 186 */       this.glType = $$2;
/*     */     }
/*     */     
/*     */     public int getSize() {
/* 190 */       return this.size;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 194 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getGlType() {
/* 198 */       return this.glType;
/*     */     }
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface ClearState {
/*     */     void clearBufferState(int param1Int1, int param1Int2);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface SetupState {
/*     */     void setupBufferState(int param1Int1, int param1Int2, int param1Int3, long param1Long, int param1Int4, int param1Int5);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexFormatElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */