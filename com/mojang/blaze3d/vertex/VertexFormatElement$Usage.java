/*     */ package com.mojang.blaze3d.vertex;
/*     */ 
/*     */ import com.mojang.blaze3d.platform.GlStateManager;
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
/*     */ public enum Usage
/*     */ {
/*     */   GENERIC,
/* 129 */   PADDING("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }($$0, $$1) -> {  }), UV("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }($$0, $$1) -> {  }), COLOR("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }($$0, $$1) -> {  }), NORMAL("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> {  }($$0, $$1) -> {  }), POSITION("Padding", ($$0, $$1, $$2, $$3, $$4, $$5) -> { 
/*     */     }($$0, $$1) -> { 
/* 131 */     }); private final ClearState clearState; private final SetupState setupState; static { GENERIC = new Usage("GENERIC", 5, "Generic", ($$0, $$1, $$2, $$3, $$4, $$5) -> {
/*     */           GlStateManager._enableVertexAttribArray($$5);
/*     */           GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3);
/*     */         }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String name;
/*     */ 
/*     */   
/*     */   Usage(String $$0, SetupState $$1, ClearState $$2) {
/* 143 */     this.name = $$0;
/* 144 */     this.setupState = $$1;
/* 145 */     this.clearState = $$2;
/*     */   } static { POSITION = new Usage("POSITION", 0, "Position", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); NORMAL = new Usage("NORMAL", 1, "Normal", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, true, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); COLOR = new Usage("COLOR", 2, "Vertex Color", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); GlStateManager._vertexAttribPointer($$5, $$0, $$1, true, $$2, $$3); }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); UV = new Usage("UV", 3, "UV", ($$0, $$1, $$2, $$3, $$4, $$5) -> { GlStateManager._enableVertexAttribArray($$5); if ($$1 == 5126) { GlStateManager._vertexAttribPointer($$5, $$0, $$1, false, $$2, $$3); }
/*     */           else { GlStateManager._vertexAttribIPointer($$5, $$0, $$1, $$2, $$3); }
/*     */         
/* 149 */         }($$0, $$1) -> GlStateManager._disableVertexAttribArray($$1)); } void setupBufferState(int $$0, int $$1, int $$2, long $$3, int $$4, int $$5) { this.setupState.setupBufferState($$0, $$1, $$2, $$3, $$4, $$5); }
/*     */ 
/*     */   
/*     */   public void clearBufferState(int $$0, int $$1) {
/* 153 */     this.clearState.clearBufferState($$0, $$1);
/*     */   }
/*     */   
/*     */   public String getName() {
/* 157 */     return this.name;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface SetupState {
/*     */     void setupBufferState(int param2Int1, int param2Int2, int param2Int3, long param2Long, int param2Int4, int param2Int5);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   private static interface ClearState {
/*     */     void clearBufferState(int param2Int1, int param2Int2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\VertexFormatElement$Usage.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */