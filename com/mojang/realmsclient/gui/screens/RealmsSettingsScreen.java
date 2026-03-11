/*    */ package com.mojang.realmsclient.gui.screens;
/*    */ import com.mojang.realmsclient.dto.RealmsServer;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.EditBox;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.gui.screens.Screen;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.MutableComponent;
/*    */ 
/*    */ public class RealmsSettingsScreen extends RealmsScreen {
/*    */   private static final int COMPONENT_WIDTH = 212;
/* 15 */   private static final Component NAME_LABEL = (Component)Component.translatable("mco.configure.world.name");
/* 16 */   private static final Component DESCRIPTION_LABEL = (Component)Component.translatable("mco.configure.world.description");
/*    */   
/*    */   private final RealmsConfigureWorldScreen configureWorldScreen;
/*    */   
/*    */   private final RealmsServer serverData;
/*    */   private EditBox descEdit;
/*    */   private EditBox nameEdit;
/*    */   
/*    */   public RealmsSettingsScreen(RealmsConfigureWorldScreen $$0, RealmsServer $$1) {
/* 25 */     super((Component)Component.translatable("mco.configure.world.settings.title"));
/* 26 */     this.configureWorldScreen = $$0;
/* 27 */     this.serverData = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void init() {
/* 32 */     int $$0 = this.width / 2 - 106;
/*    */     
/* 34 */     String $$1 = (this.serverData.state == RealmsServer.State.OPEN) ? "mco.configure.world.buttons.close" : "mco.configure.world.buttons.open";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 49 */     Button $$2 = Button.builder((Component)Component.translatable($$1), $$0 -> { if (this.serverData.state == RealmsServer.State.OPEN) { MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.close.question.line1"); MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.close.question.line2"); this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen((), RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true)); } else { this.configureWorldScreen.openTheWorld(false, (Screen)this); }  }).bounds(this.width / 2 - 53, row(0), 106, 20).build();
/* 50 */     addRenderableWidget((GuiEventListener)$$2);
/*    */     
/* 52 */     this.nameEdit = new EditBox(this.minecraft.font, $$0, row(4), 212, 20, (Component)Component.translatable("mco.configure.world.name"));
/* 53 */     this.nameEdit.setMaxLength(32);
/* 54 */     this.nameEdit.setValue(this.serverData.getName());
/* 55 */     addRenderableWidget((GuiEventListener)this.nameEdit);
/* 56 */     setInitialFocus((GuiEventListener)this.nameEdit);
/*    */     
/* 58 */     this.descEdit = new EditBox(this.minecraft.font, $$0, row(8), 212, 20, (Component)Component.translatable("mco.configure.world.description"));
/* 59 */     this.descEdit.setMaxLength(32);
/* 60 */     this.descEdit.setValue(this.serverData.getDescription());
/* 61 */     addRenderableWidget((GuiEventListener)this.descEdit);
/*    */     
/* 63 */     Button $$3 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.configure.world.buttons.done"), $$0 -> save()).bounds($$0 - 2, row(12), 106, 20).build());
/* 64 */     this.nameEdit.setResponder($$1 -> $$0.active = !Util.isBlank($$1));
/*    */     
/* 66 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose()).bounds(this.width / 2 + 2, row(12), 106, 20).build());
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 71 */     this.minecraft.setScreen((Screen)this.configureWorldScreen);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 76 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 78 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, -1);
/* 79 */     $$0.drawString(this.font, NAME_LABEL, this.width / 2 - 106, row(3), -1, false);
/* 80 */     $$0.drawString(this.font, DESCRIPTION_LABEL, this.width / 2 - 106, row(7), -1, false);
/*    */   }
/*    */   
/*    */   public void save() {
/* 84 */     this.configureWorldScreen.saveSettings(this.nameEdit.getValue(), this.descEdit.getValue());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsSettingsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */