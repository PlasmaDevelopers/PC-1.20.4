/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import com.mojang.blaze3d.platform.InputConstants;
/*    */ import java.util.Arrays;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.OptionsList;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class MouseSettingsScreen
/*    */   extends OptionsSubScreen {
/*    */   private OptionsList list;
/*    */   
/*    */   private static OptionInstance<?>[] options(Options $$0) {
/* 19 */     return (OptionInstance<?>[])new OptionInstance[] { $$0
/* 20 */         .sensitivity(), $$0
/* 21 */         .invertYMouse(), $$0
/* 22 */         .mouseWheelSensitivity(), $$0
/* 23 */         .discreteMouseScroll(), $$0
/* 24 */         .touchscreen() };
/*    */   }
/*    */ 
/*    */   
/*    */   public MouseSettingsScreen(Screen $$0, Options $$1) {
/* 29 */     super($$0, $$1, (Component)Component.translatable("options.mouse_settings.title"));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 34 */     this.list = addRenderableWidget(new OptionsList(this.minecraft, this.width, this.height - 64, 32, 25));
/*    */     
/* 36 */     if (InputConstants.isRawMouseInputSupported()) {
/* 37 */       this.list.addSmall((OptionInstance[])Stream.concat(Arrays.stream((Object[])options(this.options)), Stream.of(this.options.rawMouseInput())).toArray($$0 -> new OptionInstance[$$0]));
/*    */     } else {
/* 39 */       this.list.addSmall((OptionInstance[])options(this.options));
/*    */     } 
/*    */     
/* 42 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*    */             this.options.save();
/*    */             this.minecraft.setScreen(this.lastScreen);
/* 45 */           }).bounds(this.width / 2 - 100, this.height - 27, 200, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 50 */     super.render($$0, $$1, $$2, $$3);
/* 51 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 5, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 56 */     renderDirtBackground($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\MouseSettingsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */