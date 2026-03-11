/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import com.mojang.blaze3d.systems.RenderSystem;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.resources.PlayerSkin;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class PlayerFaceRenderer {
/*    */   public static final int SKIN_HEAD_U = 8;
/*    */   public static final int SKIN_HEAD_V = 8;
/*    */   public static final int SKIN_HEAD_WIDTH = 8;
/*    */   public static final int SKIN_HEAD_HEIGHT = 8;
/*    */   public static final int SKIN_HAT_U = 40;
/*    */   public static final int SKIN_HAT_V = 8;
/*    */   public static final int SKIN_HAT_WIDTH = 8;
/*    */   public static final int SKIN_HAT_HEIGHT = 8;
/*    */   public static final int SKIN_TEX_WIDTH = 64;
/*    */   public static final int SKIN_TEX_HEIGHT = 64;
/*    */   
/*    */   public static void draw(GuiGraphics $$0, PlayerSkin $$1, int $$2, int $$3, int $$4) {
/* 21 */     draw($$0, $$1.texture(), $$2, $$3, $$4);
/*    */   }
/*    */   
/*    */   public static void draw(GuiGraphics $$0, ResourceLocation $$1, int $$2, int $$3, int $$4) {
/* 25 */     draw($$0, $$1, $$2, $$3, $$4, true, false);
/*    */   }
/*    */   
/*    */   public static void draw(GuiGraphics $$0, ResourceLocation $$1, int $$2, int $$3, int $$4, boolean $$5, boolean $$6) {
/* 29 */     int $$7 = 8 + ($$6 ? 8 : 0);
/* 30 */     int $$8 = 8 * ($$6 ? -1 : 1);
/* 31 */     $$0.blit($$1, $$2, $$3, $$4, $$4, 8.0F, $$7, 8, $$8, 64, 64);
/*    */     
/* 33 */     if ($$5) {
/* 34 */       drawHat($$0, $$1, $$2, $$3, $$4, $$6);
/*    */     }
/*    */   }
/*    */   
/*    */   private static void drawHat(GuiGraphics $$0, ResourceLocation $$1, int $$2, int $$3, int $$4, boolean $$5) {
/* 39 */     int $$6 = 8 + ($$5 ? 8 : 0);
/* 40 */     int $$7 = 8 * ($$5 ? -1 : 1);
/*    */     
/* 42 */     RenderSystem.enableBlend();
/* 43 */     $$0.blit($$1, $$2, $$3, $$4, $$4, 40.0F, $$6, 8, $$7, 64, 64);
/* 44 */     RenderSystem.disableBlend();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\PlayerFaceRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */