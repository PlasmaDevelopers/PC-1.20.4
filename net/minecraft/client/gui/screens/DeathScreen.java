/*     */ package net.minecraft.client.gui.screens;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ public class DeathScreen extends Screen {
/*  19 */   private static final ResourceLocation DRAFT_REPORT_SPRITE = new ResourceLocation("icon/draft_report");
/*     */   
/*     */   private int delayTicker;
/*     */   private final Component causeOfDeath;
/*     */   private final boolean hardcore;
/*     */   private Component deathScore;
/*  25 */   private final List<Button> exitButtons = Lists.newArrayList();
/*     */   
/*     */   @Nullable
/*     */   private Button exitToTitleButton;
/*     */   
/*     */   public DeathScreen(@Nullable Component $$0, boolean $$1) {
/*  31 */     super((Component)Component.translatable($$1 ? "deathScreen.title.hardcore" : "deathScreen.title"));
/*  32 */     this.causeOfDeath = $$0;
/*  33 */     this.hardcore = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  38 */     this.delayTicker = 0;
/*  39 */     this.exitButtons.clear();
/*  40 */     MutableComponent mutableComponent = this.hardcore ? Component.translatable("deathScreen.spectate") : Component.translatable("deathScreen.respawn");
/*  41 */     this.exitButtons.add(addRenderableWidget(Button.builder((Component)mutableComponent, $$0 -> {
/*     */               this.minecraft.player.respawn();
/*     */               $$0.active = false;
/*  44 */             }).bounds(this.width / 2 - 100, this.height / 4 + 72, 200, 20).build()));
/*     */     
/*  46 */     this.exitToTitleButton = addRenderableWidget(Button.builder((Component)Component.translatable("deathScreen.titleScreen"), $$0 -> this.minecraft.getReportingContext().draftReportHandled(this.minecraft, this, this::handleExitToTitleScreen, true))
/*     */         
/*  48 */         .bounds(this.width / 2 - 100, this.height / 4 + 96, 200, 20).build());
/*  49 */     this.exitButtons.add(this.exitToTitleButton);
/*     */     
/*  51 */     setButtonsActive(false);
/*  52 */     this.deathScore = (Component)Component.translatable("deathScreen.score.value", new Object[] { Component.literal(Integer.toString(this.minecraft.player.getScore())).withStyle(ChatFormatting.YELLOW) });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCloseOnEsc() {
/*  57 */     return false;
/*     */   }
/*     */   
/*     */   private void handleExitToTitleScreen() {
/*  61 */     if (this.hardcore) {
/*  62 */       exitToTitleScreen();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  72 */     ConfirmScreen $$0 = new TitleConfirmScreen($$0 -> { if ($$0) { exitToTitleScreen(); } else { this.minecraft.player.respawn(); this.minecraft.setScreen(null); }  }(Component)Component.translatable("deathScreen.quit.confirm"), CommonComponents.EMPTY, (Component)Component.translatable("deathScreen.titleScreen"), (Component)Component.translatable("deathScreen.respawn"));
/*  73 */     this.minecraft.setScreen($$0);
/*  74 */     $$0.setDelay(20);
/*     */   }
/*     */   
/*     */   private void exitToTitleScreen() {
/*  78 */     if (this.minecraft.level != null) {
/*  79 */       this.minecraft.level.disconnect();
/*     */     }
/*  81 */     this.minecraft.disconnect(new GenericDirtMessageScreen((Component)Component.translatable("menu.savingLevel")));
/*  82 */     this.minecraft.setScreen(new TitleScreen());
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  87 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/*  89 */     $$0.pose().pushPose();
/*  90 */     $$0.pose().scale(2.0F, 2.0F, 2.0F);
/*     */     
/*  92 */     $$0.drawCenteredString(this.font, this.title, this.width / 2 / 2, 30, 16777215);
/*     */     
/*  94 */     $$0.pose().popPose();
/*     */     
/*  96 */     if (this.causeOfDeath != null) {
/*  97 */       $$0.drawCenteredString(this.font, this.causeOfDeath, this.width / 2, 85, 16777215);
/*     */     }
/*     */     
/* 100 */     $$0.drawCenteredString(this.font, this.deathScore, this.width / 2, 100, 16777215);
/*     */     
/* 102 */     Objects.requireNonNull(this.font); if (this.causeOfDeath != null && $$2 > 85 && $$2 < 85 + 9) {
/* 103 */       Style $$4 = getClickedComponentStyleAt($$1);
/* 104 */       $$0.renderComponentHoverEffect(this.font, $$4, $$1, $$2);
/*     */     } 
/*     */     
/* 107 */     if (this.exitToTitleButton != null && this.minecraft.getReportingContext().hasDraftReport()) {
/* 108 */       $$0.blitSprite(DRAFT_REPORT_SPRITE, this.exitToTitleButton.getX() + this.exitToTitleButton.getWidth() - 17, this.exitToTitleButton.getY() + 3, 15, 15);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 114 */     $$0.fillGradient(0, 0, this.width, this.height, 1615855616, -1602211792);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Style getClickedComponentStyleAt(int $$0) {
/* 119 */     if (this.causeOfDeath == null) {
/* 120 */       return null;
/*     */     }
/*     */     
/* 123 */     int $$1 = this.minecraft.font.width((FormattedText)this.causeOfDeath);
/* 124 */     int $$2 = this.width / 2 - $$1 / 2;
/* 125 */     int $$3 = this.width / 2 + $$1 / 2;
/* 126 */     if ($$0 < $$2 || $$0 > $$3) {
/* 127 */       return null;
/*     */     }
/* 129 */     return this.minecraft.font.getSplitter().componentStyleAtWidth((FormattedText)this.causeOfDeath, $$0 - $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 134 */     Objects.requireNonNull(this.font); if (this.causeOfDeath != null && $$1 > 85.0D && $$1 < (85 + 9)) {
/* 135 */       Style $$3 = getClickedComponentStyleAt((int)$$0);
/* 136 */       if ($$3 != null && $$3.getClickEvent() != null && $$3.getClickEvent().getAction() == ClickEvent.Action.OPEN_URL) {
/* 137 */         handleComponentClicked($$3);
/* 138 */         return false;
/*     */       } 
/*     */     } 
/* 141 */     return super.mouseClicked($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 151 */     super.tick();
/*     */     
/* 153 */     this.delayTicker++;
/* 154 */     if (this.delayTicker == 20) {
/* 155 */       setButtonsActive(true);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setButtonsActive(boolean $$0) {
/* 160 */     for (Button $$1 : this.exitButtons)
/* 161 */       $$1.active = $$0; 
/*     */   }
/*     */   
/*     */   public static class TitleConfirmScreen
/*     */     extends ConfirmScreen {
/*     */     public TitleConfirmScreen(BooleanConsumer $$0, Component $$1, Component $$2, Component $$3, Component $$4) {
/* 167 */       super($$0, $$1, $$2, $$3, $$4);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\DeathScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */