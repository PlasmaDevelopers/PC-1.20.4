/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.CycleButton;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.player.PlayerModelPart;
/*    */ 
/*    */ public class SkinCustomizationScreen extends OptionsSubScreen {
/*    */   public SkinCustomizationScreen(Screen $$0, Options $$1) {
/* 13 */     super($$0, $$1, (Component)Component.translatable("options.skinCustomisation.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 18 */     int $$0 = 0;
/*    */     
/* 20 */     for (PlayerModelPart $$1 : PlayerModelPart.values()) {
/* 21 */       addRenderableWidget(CycleButton.onOffBuilder(this.options.isModelPartEnabled($$1)).create(this.width / 2 - 155 + $$0 % 2 * 160, this.height / 6 + 24 * ($$0 >> 1), 150, 20, $$1.getName(), ($$1, $$2) -> this.options.toggleModelPart($$0, $$2.booleanValue())));
/*    */ 
/*    */       
/* 24 */       $$0++;
/*    */     } 
/*    */     
/* 27 */     addRenderableWidget(this.options.mainHand().createButton(this.options, this.width / 2 - 155 + $$0 % 2 * 160, this.height / 6 + 24 * ($$0 >> 1), 150));
/* 28 */     $$0++;
/*    */     
/* 30 */     if ($$0 % 2 == 1) {
/* 31 */       $$0++;
/*    */     }
/*    */     
/* 34 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, this.height / 6 + 24 * ($$0 >> 1), 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 39 */     super.render($$0, $$1, $$2, $$3);
/* 40 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\SkinCustomizationScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */