/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.UnmodifiableIterator;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentUtils;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class UnsupportedGraphicsWarningScreen
/*    */   extends Screen {
/*    */   private static final int BUTTON_PADDING = 20;
/*    */   private static final int BUTTON_MARGIN = 5;
/*    */   private static final int BUTTON_HEIGHT = 20;
/*    */   private final Component narrationMessage;
/*    */   private final FormattedText message;
/*    */   private final ImmutableList<ButtonOption> buttonOptions;
/* 23 */   private MultiLineLabel messageLines = MultiLineLabel.EMPTY;
/*    */   private int contentTop;
/*    */   private int buttonWidth;
/*    */   
/*    */   protected UnsupportedGraphicsWarningScreen(Component $$0, List<Component> $$1, ImmutableList<ButtonOption> $$2) {
/* 28 */     super($$0);
/* 29 */     this.message = FormattedText.composite($$1);
/* 30 */     this.narrationMessage = (Component)CommonComponents.joinForNarration(new Component[] { $$0, ComponentUtils.formatList($$1, CommonComponents.EMPTY) });
/* 31 */     this.buttonOptions = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 36 */     return this.narrationMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 41 */     for (UnmodifiableIterator<ButtonOption> unmodifiableIterator1 = this.buttonOptions.iterator(); unmodifiableIterator1.hasNext(); ) { ButtonOption $$0 = unmodifiableIterator1.next();
/* 42 */       this.buttonWidth = Math.max(this.buttonWidth, 20 + this.font.width((FormattedText)$$0.message) + 20); }
/*    */     
/* 44 */     int $$1 = 5 + this.buttonWidth + 5;
/*    */     
/* 46 */     int $$2 = $$1 * this.buttonOptions.size();
/* 47 */     this.messageLines = MultiLineLabel.create(this.font, this.message, $$2);
/*    */     
/* 49 */     Objects.requireNonNull(this.font); int $$3 = this.messageLines.getLineCount() * 9;
/* 50 */     this.contentTop = (int)(this.height / 2.0D - $$3 / 2.0D);
/*    */     
/* 52 */     Objects.requireNonNull(this.font); int $$4 = this.contentTop + $$3 + 9 * 2;
/*    */     
/* 54 */     int $$5 = (int)(this.width / 2.0D - $$2 / 2.0D);
/* 55 */     for (UnmodifiableIterator<ButtonOption> unmodifiableIterator2 = this.buttonOptions.iterator(); unmodifiableIterator2.hasNext(); ) { ButtonOption $$6 = unmodifiableIterator2.next();
/* 56 */       addRenderableWidget(Button.builder($$6.message, $$6.onPress).bounds($$5, $$4, this.buttonWidth, 20).build());
/* 57 */       $$5 += $$1; }
/*    */   
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 63 */     super.render($$0, $$1, $$2, $$3);
/* 64 */     Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, this.title, this.width / 2, this.contentTop - 9 * 2, -1);
/* 65 */     this.messageLines.renderCentered($$0, this.width / 2, this.contentTop);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 70 */     renderDirtBackground($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 75 */     return false;
/*    */   }
/*    */   
/*    */   public static final class ButtonOption {
/*    */     final Component message;
/*    */     final Button.OnPress onPress;
/*    */     
/*    */     public ButtonOption(Component $$0, Button.OnPress $$1) {
/* 83 */       this.message = $$0;
/* 84 */       this.onPress = $$1;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\UnsupportedGraphicsWarningScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */