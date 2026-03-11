/*    */ package net.minecraft.client.gui.screens;
/*    */ import java.util.Objects;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.Options;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class DemoIntroScreen extends Screen {
/* 13 */   private static final ResourceLocation DEMO_BACKGROUND_LOCATION = new ResourceLocation("textures/gui/demo_background.png"); private MultiLineLabel movementMessage;
/*    */   
/*    */   public DemoIntroScreen() {
/* 16 */     super((Component)Component.translatable("demo.help.title"));
/*    */ 
/*    */     
/* 19 */     this.movementMessage = MultiLineLabel.EMPTY;
/* 20 */     this.durationMessage = MultiLineLabel.EMPTY;
/*    */   }
/*    */   private MultiLineLabel durationMessage;
/*    */   protected void init() {
/* 24 */     int $$0 = -16;
/*    */     
/* 26 */     addRenderableWidget(Button.builder((Component)Component.translatable("demo.help.buy"), $$0 -> {
/*    */             $$0.active = false;
/*    */             Util.getPlatform().openUri("https://aka.ms/BuyMinecraftJava");
/* 29 */           }).bounds(this.width / 2 - 116, this.height / 2 + 62 + -16, 114, 20).build());
/* 30 */     addRenderableWidget(Button.builder((Component)Component.translatable("demo.help.later"), $$0 -> {
/*    */             this.minecraft.setScreen(null);
/*    */             this.minecraft.mouseHandler.grabMouse();
/* 33 */           }).bounds(this.width / 2 + 2, this.height / 2 + 62 + -16, 114, 20).build());
/*    */     
/* 35 */     Options $$1 = this.minecraft.options;
/* 36 */     this.movementMessage = MultiLineLabel.create(this.font, new Component[] {
/* 37 */           (Component)Component.translatable("demo.help.movementShort", new Object[] { $$1.keyUp.getTranslatedKeyMessage(), $$1.keyLeft.getTranslatedKeyMessage(), $$1.keyDown.getTranslatedKeyMessage(), $$1.keyRight.getTranslatedKeyMessage()
/* 38 */             }), (Component)Component.translatable("demo.help.movementMouse"), 
/* 39 */           (Component)Component.translatable("demo.help.jump", new Object[] { $$1.keyJump.getTranslatedKeyMessage()
/* 40 */             }), (Component)Component.translatable("demo.help.inventory", new Object[] { $$1.keyInventory.getTranslatedKeyMessage() })
/*    */         });
/*    */     
/* 43 */     this.durationMessage = MultiLineLabel.create(this.font, (FormattedText)Component.translatable("demo.help.fullWrapped"), 218);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 48 */     super.renderBackground($$0, $$1, $$2, $$3);
/*    */     
/* 50 */     int $$4 = (this.width - 248) / 2;
/* 51 */     int $$5 = (this.height - 166) / 2;
/* 52 */     $$0.blit(DEMO_BACKGROUND_LOCATION, $$4, $$5, 0, 0, 248, 166);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 57 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 59 */     int $$4 = (this.width - 248) / 2 + 10;
/*    */     
/* 61 */     int $$5 = (this.height - 166) / 2 + 8;
/*    */     
/* 63 */     $$0.drawString(this.font, this.title, $$4, $$5, 2039583, false);
/*    */     
/* 65 */     $$5 = this.movementMessage.renderLeftAlignedNoShadow($$0, $$4, $$5 + 12, 12, 5197647);
/* 66 */     Objects.requireNonNull(this.font); this.durationMessage.renderLeftAlignedNoShadow($$0, $$4, $$5 + 20, 9, 2039583);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\DemoIntroScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */