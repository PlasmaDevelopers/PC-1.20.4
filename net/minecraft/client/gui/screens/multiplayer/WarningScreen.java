/*    */ package net.minecraft.client.gui.screens.multiplayer;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Checkbox;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public abstract class WarningScreen
/*    */   extends Screen
/*    */ {
/*    */   private final Component content;
/*    */   @Nullable
/*    */   private final Component check;
/* 19 */   private MultiLineLabel message = MultiLineLabel.EMPTY; private final Component narration; @Nullable
/*    */   protected Checkbox stopShowing;
/*    */   protected WarningScreen(Component $$0, Component $$1, Component $$2) {
/* 22 */     this($$0, $$1, (Component)null, $$2);
/*    */   }
/*    */   
/*    */   protected WarningScreen(Component $$0, Component $$1, @Nullable Component $$2, Component $$3) {
/* 26 */     super($$0);
/* 27 */     this.content = $$1;
/* 28 */     this.check = $$2;
/* 29 */     this.narration = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void initButtons(int paramInt);
/*    */   
/*    */   protected void init() {
/* 36 */     super.init();
/*    */     
/* 38 */     this.message = MultiLineLabel.create(this.font, (FormattedText)this.content, this.width - 100);
/*    */     
/* 40 */     int $$0 = (this.message.getLineCount() + 1) * getLineHeight();
/*    */     
/* 42 */     if (this.check != null) {
/* 43 */       int $$1 = this.font.width((FormattedText)this.check);
/* 44 */       this.stopShowing = Checkbox.builder(this.check, this.font).pos(this.width / 2 - $$1 / 2 - 8, 76 + $$0).build();
/* 45 */       addRenderableWidget((GuiEventListener)this.stopShowing);
/*    */     } 
/*    */     
/* 48 */     initButtons($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getNarrationMessage() {
/* 53 */     return this.narration;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 58 */     super.render($$0, $$1, $$2, $$3);
/* 59 */     renderTitle($$0);
/* 60 */     int $$4 = this.width / 2 - this.message.getWidth() / 2;
/* 61 */     this.message.renderLeftAligned($$0, $$4, 70, getLineHeight(), 16777215);
/*    */   }
/*    */   
/*    */   protected void renderTitle(GuiGraphics $$0) {
/* 65 */     $$0.drawString(this.font, this.title, 25, 30, 16777215);
/*    */   }
/*    */   
/*    */   protected int getLineHeight() {
/* 69 */     Objects.requireNonNull(this.font); return 9 * 2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\WarningScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */