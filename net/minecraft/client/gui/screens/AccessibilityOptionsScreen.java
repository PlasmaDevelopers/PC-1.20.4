/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.OptionInstance;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.components.AbstractWidget;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.Tooltip;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class AccessibilityOptionsScreen
/*    */   extends SimpleOptionsSubScreen
/*    */ {
/*    */   private static OptionInstance<?>[] options(Options $$0) {
/* 15 */     return (OptionInstance<?>[])new OptionInstance[] { $$0
/* 16 */         .narrator(), $$0.showSubtitles(), $$0
/* 17 */         .highContrast(), $$0.autoJump(), $$0
/* 18 */         .textBackgroundOpacity(), $$0.backgroundForChatOnly(), $$0
/* 19 */         .chatOpacity(), $$0.chatLineSpacing(), $$0
/* 20 */         .chatDelay(), $$0.notificationDisplayTime(), $$0
/* 21 */         .toggleCrouch(), $$0.toggleSprint(), $$0
/* 22 */         .screenEffectScale(), $$0.fovEffectScale(), $$0
/* 23 */         .darknessEffectScale(), $$0.damageTiltStrength(), $$0
/* 24 */         .glintSpeed(), $$0.glintStrength(), $$0
/* 25 */         .hideLightningFlash(), $$0.darkMojangStudiosBackground(), $$0
/* 26 */         .panoramaSpeed(), $$0.hideSplashTexts(), $$0
/* 27 */         .narratorHotkey() };
/*    */   }
/*    */ 
/*    */   
/*    */   public AccessibilityOptionsScreen(Screen $$0, Options $$1) {
/* 32 */     super($$0, $$1, (Component)Component.translatable("options.accessibility.title"), options($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 37 */     super.init();
/* 38 */     AbstractWidget $$0 = this.list.findOption(this.options.highContrast());
/* 39 */     if ($$0 != null && 
/* 40 */       !this.minecraft.getResourcePackRepository().getAvailableIds().contains("high_contrast")) {
/* 41 */       $$0.active = false;
/* 42 */       $$0.setTooltip(Tooltip.create((Component)Component.translatable("options.accessibility.high_contrast.error.tooltip")));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void createFooter() {
/* 49 */     addRenderableWidget(Button.builder((Component)Component.translatable("options.accessibility.link"), ConfirmLinkScreen.confirmLink(this, "https://aka.ms/MinecraftJavaAccessibility"))
/* 50 */         .bounds(this.width / 2 - 155, this.height - 27, 150, 20).build());
/* 51 */     addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 + 5, this.height - 27, 150, 20).build());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\AccessibilityOptionsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */