/*    */ package net.minecraft.client.gui.screens.controls;
/*    */ 
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.MouseSettingsScreen;
/*    */ import net.minecraft.client.gui.screens.OptionsSubScreen;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ControlsScreen extends OptionsSubScreen {
/*    */   private static final int ROW_SPACING = 24;
/*    */   
/*    */   public ControlsScreen(Screen $$0, Options $$1) {
/* 17 */     super($$0, $$1, (Component)Component.translatable("controls.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 22 */     super.init();
/*    */     
/* 24 */     int $$0 = this.width / 2 - 155;
/* 25 */     int $$1 = $$0 + 160;
/* 26 */     int $$2 = this.height / 6 - 12;
/*    */     
/* 28 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("options.mouse_settings"), $$0 -> this.minecraft.setScreen((Screen)new MouseSettingsScreen((Screen)this, this.options))).bounds($$0, $$2, 150, 20).build());
/* 29 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("controls.keybinds"), $$0 -> this.minecraft.setScreen((Screen)new KeyBindsScreen((Screen)this, this.options))).bounds($$1, $$2, 150, 20).build());
/* 30 */     $$2 += 24;
/*    */     
/* 32 */     addRenderableWidget((GuiEventListener)this.options.toggleCrouch().createButton(this.options, $$0, $$2, 150));
/* 33 */     addRenderableWidget((GuiEventListener)this.options.toggleSprint().createButton(this.options, $$1, $$2, 150));
/* 34 */     $$2 += 24;
/*    */     
/* 36 */     addRenderableWidget((GuiEventListener)this.options.autoJump().createButton(this.options, $$0, $$2, 150));
/* 37 */     addRenderableWidget((GuiEventListener)this.options.operatorItemsTab().createButton(this.options, $$1, $$2, 150));
/* 38 */     $$2 += 24;
/*    */     
/* 40 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, $$2, 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 45 */     super.render($$0, $$1, $$2, $$3);
/* 46 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\controls\ControlsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */