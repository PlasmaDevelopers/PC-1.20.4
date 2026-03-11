/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Renderable;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class RealmsLabel implements Renderable {
/*    */   private final Component text;
/*    */   private final int x;
/*    */   private final int y;
/*    */   private final int color;
/*    */   
/*    */   public RealmsLabel(Component $$0, int $$1, int $$2, int $$3) {
/* 15 */     this.text = $$0;
/* 16 */     this.x = $$1;
/* 17 */     this.y = $$2;
/* 18 */     this.color = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 23 */     $$0.drawCenteredString((Minecraft.getInstance()).font, this.text, this.x, this.y, this.color);
/*    */   }
/*    */   
/*    */   public Component getText() {
/* 27 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\realms\RealmsLabel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */