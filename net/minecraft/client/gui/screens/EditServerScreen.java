/*    */ package net.minecraft.client.gui.screens;
/*    */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.CycleButton;
/*    */ import net.minecraft.client.gui.components.EditBox;
/*    */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*    */ import net.minecraft.client.multiplayer.ServerData;
/*    */ import net.minecraft.client.multiplayer.resolver.ServerAddress;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class EditServerScreen extends Screen {
/* 15 */   private static final Component NAME_LABEL = (Component)Component.translatable("addServer.enterName");
/* 16 */   private static final Component IP_LABEL = (Component)Component.translatable("addServer.enterIp");
/*    */   
/*    */   private Button addButton;
/*    */   private final BooleanConsumer callback;
/*    */   private final ServerData serverData;
/*    */   private EditBox ipEdit;
/*    */   private EditBox nameEdit;
/*    */   private final Screen lastScreen;
/*    */   
/*    */   public EditServerScreen(Screen $$0, BooleanConsumer $$1, ServerData $$2) {
/* 26 */     super((Component)Component.translatable("addServer.title"));
/* 27 */     this.lastScreen = $$0;
/* 28 */     this.callback = $$1;
/* 29 */     this.serverData = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 34 */     this.nameEdit = new EditBox(this.font, this.width / 2 - 100, 66, 200, 20, (Component)Component.translatable("addServer.enterName"));
/* 35 */     this.nameEdit.setValue(this.serverData.name);
/* 36 */     this.nameEdit.setResponder($$0 -> updateAddButtonStatus());
/* 37 */     addWidget(this.nameEdit);
/*    */     
/* 39 */     this.ipEdit = new EditBox(this.font, this.width / 2 - 100, 106, 200, 20, (Component)Component.translatable("addServer.enterIp"));
/* 40 */     this.ipEdit.setMaxLength(128);
/* 41 */     this.ipEdit.setValue(this.serverData.ip);
/* 42 */     this.ipEdit.setResponder($$0 -> updateAddButtonStatus());
/* 43 */     addWidget(this.ipEdit);
/*    */     
/* 45 */     addRenderableWidget(CycleButton.builder(ServerData.ServerPackStatus::getName)
/* 46 */         .withValues((Object[])ServerData.ServerPackStatus.values())
/* 47 */         .withInitialValue(this.serverData.getResourcePackStatus())
/* 48 */         .create(this.width / 2 - 100, this.height / 4 + 72, 200, 20, (Component)Component.translatable("addServer.resourcePack"), ($$0, $$1) -> this.serverData.setResourcePackStatus($$1)));
/*    */     
/* 50 */     this.addButton = addRenderableWidget(Button.builder((Component)Component.translatable("addServer.add"), $$0 -> onAdd()).bounds(this.width / 2 - 100, this.height / 4 + 96 + 18, 200, 20).build());
/* 51 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.callback.accept(false)).bounds(this.width / 2 - 100, this.height / 4 + 120 + 18, 200, 20).build());
/*    */     
/* 53 */     setInitialFocus((GuiEventListener)this.nameEdit);
/* 54 */     updateAddButtonStatus();
/*    */   }
/*    */ 
/*    */   
/*    */   public void resize(Minecraft $$0, int $$1, int $$2) {
/* 59 */     String $$3 = this.ipEdit.getValue();
/* 60 */     String $$4 = this.nameEdit.getValue();
/* 61 */     init($$0, $$1, $$2);
/* 62 */     this.ipEdit.setValue($$3);
/* 63 */     this.nameEdit.setValue($$4);
/*    */   }
/*    */   
/*    */   private void onAdd() {
/* 67 */     this.serverData.name = this.nameEdit.getValue();
/* 68 */     this.serverData.ip = this.ipEdit.getValue();
/* 69 */     this.callback.accept(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 74 */     this.minecraft.setScreen(this.lastScreen);
/*    */   }
/*    */   
/*    */   private void updateAddButtonStatus() {
/* 78 */     this.addButton.active = (ServerAddress.isValidAddress(this.ipEdit.getValue()) && !this.nameEdit.getValue().isEmpty());
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 83 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 85 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 17, 16777215);
/* 86 */     $$0.drawString(this.font, NAME_LABEL, this.width / 2 - 100 + 1, 53, 10526880);
/* 87 */     $$0.drawString(this.font, IP_LABEL, this.width / 2 - 100 + 1, 94, 10526880);
/*    */     
/* 89 */     this.nameEdit.render($$0, $$1, $$2, $$3);
/* 90 */     this.ipEdit.render($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\EditServerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */