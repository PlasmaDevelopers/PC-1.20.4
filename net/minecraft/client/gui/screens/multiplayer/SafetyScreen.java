/*    */ package net.minecraft.client.gui.screens.multiplayer;
/*    */ import net.minecraft.ChatFormatting;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class SafetyScreen extends WarningScreen {
/* 10 */   private static final Component TITLE = (Component)Component.translatable("multiplayerWarning.header").withStyle(ChatFormatting.BOLD);
/* 11 */   private static final Component CONTENT = (Component)Component.translatable("multiplayerWarning.message");
/* 12 */   private static final Component CHECK = (Component)Component.translatable("multiplayerWarning.check");
/* 13 */   private static final Component NARRATION = (Component)TITLE.copy().append("\n").append(CONTENT);
/*    */   
/*    */   private final Screen previous;
/*    */   
/*    */   public SafetyScreen(Screen $$0) {
/* 18 */     super(TITLE, CONTENT, CHECK, NARRATION);
/* 19 */     this.previous = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void initButtons(int $$0) {
/* 24 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_PROCEED, $$0 -> {
/*    */             if (this.stopShowing.selected()) {
/*    */               this.minecraft.options.skipMultiplayerWarning = true;
/*    */               this.minecraft.options.save();
/*    */             } 
/*    */             this.minecraft.setScreen(new JoinMultiplayerScreen(this.previous));
/* 30 */           }).bounds(this.width / 2 - 155, 100 + $$0, 150, 20).build());
/* 31 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen(this.previous))
/* 32 */         .bounds(this.width / 2 - 155 + 160, 100 + $$0, 150, 20).build());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\SafetyScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */