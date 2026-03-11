/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class LogoRenderer {
/*  8 */   public static final ResourceLocation MINECRAFT_LOGO = new ResourceLocation("textures/gui/title/minecraft.png");
/*  9 */   public static final ResourceLocation EASTER_EGG_LOGO = new ResourceLocation("textures/gui/title/minceraft.png");
/* 10 */   public static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation("textures/gui/title/edition.png");
/*    */   
/*    */   public static final int LOGO_WIDTH = 256;
/*    */   
/*    */   public static final int LOGO_HEIGHT = 44;
/*    */   
/*    */   private static final int LOGO_TEXTURE_WIDTH = 256;
/*    */   
/*    */   private static final int LOGO_TEXTURE_HEIGHT = 64;
/*    */   private static final int EDITION_WIDTH = 128;
/*    */   private static final int EDITION_HEIGHT = 14;
/*    */   private static final int EDITION_TEXTURE_WIDTH = 128;
/*    */   private static final int EDITION_TEXTURE_HEIGHT = 16;
/*    */   public static final int DEFAULT_HEIGHT_OFFSET = 30;
/*    */   private static final int EDITION_LOGO_OVERLAP = 7;
/* 25 */   private final boolean showEasterEgg = (RandomSource.create().nextFloat() < 1.0E-4D);
/*    */   
/*    */   private final boolean keepLogoThroughFade;
/*    */   
/*    */   public LogoRenderer(boolean $$0) {
/* 30 */     this.keepLogoThroughFade = $$0;
/*    */   }
/*    */   
/*    */   public void renderLogo(GuiGraphics $$0, int $$1, float $$2) {
/* 34 */     renderLogo($$0, $$1, $$2, 30);
/*    */   }
/*    */   
/*    */   public void renderLogo(GuiGraphics $$0, int $$1, float $$2, int $$3) {
/* 38 */     $$0.setColor(1.0F, 1.0F, 1.0F, this.keepLogoThroughFade ? 1.0F : $$2);
/* 39 */     int $$4 = $$1 / 2 - 128;
/* 40 */     $$0.blit(this.showEasterEgg ? EASTER_EGG_LOGO : MINECRAFT_LOGO, $$4, $$3, 0.0F, 0.0F, 256, 44, 256, 64);
/*    */     
/* 42 */     int $$5 = $$1 / 2 - 64;
/* 43 */     int $$6 = $$3 + 44 - 7;
/* 44 */     $$0.blit(MINECRAFT_EDITION, $$5, $$6, 0.0F, 0.0F, 128, 14, 128, 16);
/*    */     
/* 46 */     $$0.setColor(1.0F, 1.0F, 1.0F, 1.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\LogoRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */