/*    */ package com.mojang.blaze3d.shaders;
/*    */ 
/*    */ import com.mojang.blaze3d.preprocessor.GlslPreprocessor;
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class EffectProgram
/*    */   extends Program {
/* 10 */   private static final GlslPreprocessor PREPROCESSOR = new GlslPreprocessor()
/*    */     {
/*    */       public String applyImport(boolean $$0, String $$1) {
/* 13 */         return "#error Import statement not supported";
/*    */       }
/*    */     };
/*    */   
/*    */   private int references;
/*    */   
/*    */   private EffectProgram(Program.Type $$0, int $$1, String $$2) {
/* 20 */     super($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public void attachToEffect(Effect $$0) {
/* 24 */     RenderSystem.assertOnRenderThread();
/* 25 */     this.references++;
/* 26 */     attachToShader($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void close() {
/* 31 */     RenderSystem.assertOnRenderThread();
/* 32 */     this.references--;
/*    */     
/* 34 */     if (this.references <= 0) {
/* 35 */       super.close();
/*    */     }
/*    */   }
/*    */   
/*    */   public static EffectProgram compileShader(Program.Type $$0, String $$1, InputStream $$2, String $$3) throws IOException {
/* 40 */     RenderSystem.assertOnRenderThread();
/* 41 */     int $$4 = compileShaderInternal($$0, $$1, $$2, $$3, PREPROCESSOR);
/*    */     
/* 43 */     EffectProgram $$5 = new EffectProgram($$0, $$4, $$1);
/* 44 */     $$0.getPrograms().put($$1, $$5);
/* 45 */     return $$5;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\shaders\EffectProgram.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */