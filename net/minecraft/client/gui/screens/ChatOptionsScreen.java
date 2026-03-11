/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ChatOptionsScreen
/*    */   extends SimpleOptionsSubScreen {
/*    */   public ChatOptionsScreen(Screen $$0, Options $$1) {
/* 10 */     super($$0, $$1, (Component)Component.translatable("options.chat.title"), (OptionInstance<?>[])new OptionInstance[] { $$1
/* 11 */           .chatVisibility(), $$1.chatColors(), $$1
/* 12 */           .chatLinks(), $$1.chatLinksPrompt(), $$1
/* 13 */           .chatOpacity(), $$1.textBackgroundOpacity(), $$1
/* 14 */           .chatScale(), $$1.chatLineSpacing(), $$1
/* 15 */           .chatDelay(), $$1.chatWidth(), $$1
/* 16 */           .chatHeightFocused(), $$1.chatHeightUnfocused(), $$1
/* 17 */           .narrator(), $$1.autoSuggestions(), $$1
/* 18 */           .hideMatchedNames(), $$1.reducedDebugInfo(), $$1
/* 19 */           .onlyShowSecureChat() });
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ChatOptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */