/*   */ package com.mojang.blaze3d.pipeline;
/*   */ 
/*   */ import com.mojang.blaze3d.systems.RenderSystem;
/*   */ 
/*   */ public class TextureTarget extends RenderTarget {
/*   */   public TextureTarget(int $$0, int $$1, boolean $$2, boolean $$3) {
/* 7 */     super($$2);
/* 8 */     RenderSystem.assertOnRenderThreadOrInit();
/* 9 */     resize($$0, $$1, $$3);
/*   */   }
/*   */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\pipeline\TextureTarget.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */