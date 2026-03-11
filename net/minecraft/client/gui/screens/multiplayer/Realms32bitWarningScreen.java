/*    */ package net.minecraft.client.gui.screens.multiplayer;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class Realms32bitWarningScreen extends WarningScreen {
/* 10 */   private static final Component TITLE = (Component)Component.translatable("title.32bit.deprecation.realms.header").withStyle(ChatFormatting.BOLD);
/* 11 */   private static final Component CONTENT = (Component)Component.translatable("title.32bit.deprecation.realms");
/* 12 */   private static final Component CHECK = (Component)Component.translatable("title.32bit.deprecation.realms.check");
/* 13 */   private static final Component NARRATION = (Component)TITLE.copy().append("\n").append(CONTENT);
/*    */   
/*    */   private final Screen previous;
/*    */   
/*    */   public Realms32bitWarningScreen(Screen $$0) {
/* 18 */     super(TITLE, CONTENT, CHECK, NARRATION);
/* 19 */     this.previous = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initButtons(int $$0) {
/* 24 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> {
/*    */             if (this.stopShowing.selected()) {
/*    */               this.minecraft.options.skipRealms32bitWarning = true;
/*    */               this.minecraft.options.save();
/*    */             } 
/*    */             this.minecraft.setScreen(this.previous);
/* 30 */           }).bounds(this.width / 2 - 75, 100 + $$0, 150, 20).build());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\Realms32bitWarningScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */