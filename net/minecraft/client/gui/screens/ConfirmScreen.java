/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.util.Mth;
/*     */ 
/*     */ public class ConfirmScreen extends Screen {
/*     */   private static final int MARGIN = 20;
/*     */   private final Component message;
/*  18 */   private MultiLineLabel multilineMessage = MultiLineLabel.EMPTY;
/*     */   protected Component yesButton;
/*     */   protected Component noButton;
/*     */   private int delayTicker;
/*     */   protected final BooleanConsumer callback;
/*  23 */   private final List<Button> exitButtons = Lists.newArrayList();
/*     */   
/*     */   public ConfirmScreen(BooleanConsumer $$0, Component $$1, Component $$2) {
/*  26 */     this($$0, $$1, $$2, CommonComponents.GUI_YES, CommonComponents.GUI_NO);
/*     */   }
/*     */   
/*     */   public ConfirmScreen(BooleanConsumer $$0, Component $$1, Component $$2, Component $$3, Component $$4) {
/*  30 */     super($$1);
/*  31 */     this.callback = $$0;
/*  32 */     this.message = $$2;
/*  33 */     this.yesButton = $$3;
/*  34 */     this.noButton = $$4;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  39 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), this.message });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  44 */     super.init();
/*     */     
/*  46 */     this.multilineMessage = MultiLineLabel.create(this.font, (FormattedText)this.message, this.width - 50);
/*     */     
/*  48 */     int $$0 = Mth.clamp(messageTop() + messageHeight() + 20, this.height / 6 + 96, this.height - 24);
/*     */     
/*  50 */     this.exitButtons.clear();
/*  51 */     addButtons($$0);
/*     */   }
/*     */   
/*     */   protected void addButtons(int $$0) {
/*  55 */     addExitButton(Button.builder(this.yesButton, $$0 -> this.callback.accept(true)).bounds(this.width / 2 - 155, $$0, 150, 20).build());
/*  56 */     addExitButton(Button.builder(this.noButton, $$0 -> this.callback.accept(false)).bounds(this.width / 2 - 155 + 160, $$0, 150, 20).build());
/*     */   }
/*     */   
/*     */   protected void addExitButton(Button $$0) {
/*  60 */     this.exitButtons.add(addRenderableWidget($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  65 */     super.render($$0, $$1, $$2, $$3);
/*  66 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, titleTop(), 16777215);
/*  67 */     this.multilineMessage.renderCentered($$0, this.width / 2, messageTop());
/*     */   }
/*     */   
/*     */   private int titleTop() {
/*  71 */     int $$0 = (this.height - messageHeight()) / 2;
/*  72 */     Objects.requireNonNull(this.font); return Mth.clamp($$0 - 20 - 9, 10, 80);
/*     */   }
/*     */   
/*     */   private int messageTop() {
/*  76 */     return titleTop() + 20;
/*     */   }
/*     */   
/*     */   private int messageHeight() {
/*  80 */     Objects.requireNonNull(this.font); return this.multilineMessage.getLineCount() * 9;
/*     */   }
/*     */   
/*     */   public void setDelay(int $$0) {
/*  84 */     this.delayTicker = $$0;
/*     */     
/*  86 */     for (Button $$1 : this.exitButtons) {
/*  87 */       $$1.active = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  93 */     super.tick();
/*     */     
/*  95 */     if (--this.delayTicker == 0) {
/*  96 */       for (Button $$0 : this.exitButtons) {
/*  97 */         $$0.active = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/* 104 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 109 */     if ($$0 == 256) {
/* 110 */       this.callback.accept(false);
/* 111 */       return true;
/*     */     } 
/* 113 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ConfirmScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */