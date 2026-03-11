/*    */ package com.mojang.blaze3d.vertex;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Tesselator
/*    */ {
/*    */   private static final int MAX_BYTES = 786432;
/*    */   private final BufferBuilder builder;
/*    */   @Nullable
/*    */   private static Tesselator instance;
/*    */   
/*    */   public static void init() {
/* 17 */     RenderSystem.assertOnGameThreadOrInit();
/* 18 */     if (instance != null) {
/* 19 */       throw new IllegalStateException("Tesselator has already been initialized");
/*    */     }
/* 21 */     instance = new Tesselator();
/*    */   }
/*    */   
/*    */   public static Tesselator getInstance() {
/* 25 */     RenderSystem.assertOnGameThreadOrInit();
/* 26 */     if (instance == null) {
/* 27 */       throw new IllegalStateException("Tesselator has not been initialized");
/*    */     }
/* 29 */     return instance;
/*    */   }
/*    */   
/*    */   public Tesselator(int $$0) {
/* 33 */     this.builder = new BufferBuilder($$0);
/*    */   }
/*    */   
/*    */   public Tesselator() {
/* 37 */     this(786432);
/*    */   }
/*    */   
/*    */   public void end() {
/* 41 */     BufferUploader.drawWithShader(this.builder.end());
/*    */   }
/*    */   
/*    */   public BufferBuilder getBuilder() {
/* 45 */     return this.builder;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\vertex\Tesselator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */