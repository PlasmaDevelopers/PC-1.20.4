/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.EditBox;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class DirectJoinServerScreen extends Screen {
/* 15 */   private static final Component ENTER_IP_LABEL = (Component)Component.translatable("addServer.enterIp");
/*    */   
/*    */   private Button selectButton;
/*    */   private final ServerData serverData;
/*    */   private EditBox ipEdit;
/*    */   private final BooleanConsumer callback;
/*    */   private final Screen lastScreen;
/*    */   
/*    */   public DirectJoinServerScreen(Screen $$0, BooleanConsumer $$1, ServerData $$2) {
/* 24 */     super((Component)Component.translatable("selectServer.direct"));
/* 25 */     this.lastScreen = $$0;
/* 26 */     this.serverData = $$2;
/* 27 */     this.callback = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 32 */     if (this.selectButton.active && getFocused() == this.ipEdit && ($$0 == 257 || $$0 == 335)) {
/* 33 */       onSelect();
/* 34 */       return true;
/*    */     } 
/* 36 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 41 */     this.ipEdit = new EditBox(this.font, this.width / 2 - 100, 116, 200, 20, (Component)Component.translatable("addServer.enterIp"));
/* 42 */     this.ipEdit.setMaxLength(128);
/* 43 */     this.ipEdit.setValue(this.minecraft.options.lastMpIp);
/* 44 */     this.ipEdit.setResponder($$0 -> updateSelectButtonStatus());
/* 45 */     addWidget(this.ipEdit);
/* 46 */     this.selectButton = addRenderableWidget(Button.builder((Component)Component.translatable("selectServer.select"), $$0 -> onSelect()).bounds(this.width / 2 - 100, this.height / 4 + 96 + 12, 200, 20).build());
/* 47 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.callback.accept(false)).bounds(this.width / 2 - 100, this.height / 4 + 120 + 12, 200, 20).build());
/*    */     
/* 49 */     setInitialFocus((GuiEventListener)this.ipEdit);
/*    */     
/* 51 */     updateSelectButtonStatus();
/*    */   }
/*    */ 
/*    */   
/*    */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 56 */     String $$3 = this.ipEdit.getValue();
/* 57 */     init($$0, $$1, $$2);
/* 58 */     this.ipEdit.setValue($$3);
/*    */   }
/*    */   
/*    */   private void onSelect() {
/* 62 */     this.serverData.ip = this.ipEdit.getValue();
/* 63 */     this.callback.accept(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 68 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */ 
/*    */   
/*    */   public void removed() {
/* 73 */     this.minecraft.options.lastMpIp = this.ipEdit.getValue();
/* 74 */     this.minecraft.options.save();
/*    */   }
/*    */   
/*    */   private void updateSelectButtonStatus() {
/* 78 */     this.selectButton.active = ServerAddress.isValidAddress(this.ipEdit.getValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 83 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 85 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/* 86 */     $$0.drawString(this.font, ENTER_IP_LABEL, this.width / 2 - 100 + 1, 100, 10526880);
/*    */     
/* 88 */     this.ipEdit.render($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\DirectJoinServerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */