/*    */ package net.minecraft.client.gui.components;
/*    */ 
/*    */ import com.mojang.math.Axis;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.Font;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class SplashRenderer {
/* 10 */   public static final SplashRenderer CHRISTMAS = new SplashRenderer("Merry X-mas!");
/* 11 */   public static final SplashRenderer NEW_YEAR = new SplashRenderer("Happy new year!");
/* 12 */   public static final SplashRenderer HALLOWEEN = new SplashRenderer("OOoooOOOoooo! Spooky!");
/*    */   
/*    */   private static final int WIDTH_OFFSET = 123;
/*    */   
/*    */   private static final int HEIGH_OFFSET = 69;
/*    */   private final String splash;
/*    */   
/*    */   public SplashRenderer(String $$0) {
/* 20 */     this.splash = $$0;
/*    */   }
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, Font $$2, int $$3) {
/* 24 */     $$0.pose().pushPose();
/* 25 */     $$0.pose().translate($$1 / 2.0F + 123.0F, 69.0F, 0.0F);
/* 26 */     $$0.pose().mulPose(Axis.ZP.rotationDegrees(-20.0F));
/*    */     
/* 28 */     float $$4 = 1.8F - Mth.abs(Mth.sin((float)(Util.getMillis() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
/* 29 */     $$4 = $$4 * 100.0F / ($$2.width(this.splash) + 32);
/* 30 */     $$0.pose().scale($$4, $$4, $$4);
/*    */     
/* 32 */     $$0.drawCenteredString($$2, this.splash, 0, -8, 0xFFFF00 | $$3);
/*    */     
/* 34 */     $$0.pose().popPose();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\components\SplashRenderer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */