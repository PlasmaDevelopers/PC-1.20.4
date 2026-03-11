/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.Packet;
/*    */ import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InBedChatScreen
/*    */   extends ChatScreen
/*    */ {
/*    */   private Button leaveBedButton;
/*    */   
/*    */   public InBedChatScreen() {
/* 19 */     super("");
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 24 */     super.init();
/*    */     
/* 26 */     this.leaveBedButton = Button.builder((Component)Component.translatable("multiplayer.stopSleeping"), $$0 -> sendWakeUp()).bounds(this.width / 2 - 100, this.height - 40, 200, 20).build();
/* 27 */     addRenderableWidget(this.leaveBedButton);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 32 */     if (!this.minecraft.getChatStatus().isChatAllowed(this.minecraft.isLocalServer())) {
/* 33 */       this.leaveBedButton.render($$0, $$1, $$2, $$3);
/*    */       return;
/*    */     } 
/* 36 */     super.render($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 46 */     sendWakeUp();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean charTyped(char $$0, int $$1) {
/* 51 */     if (!this.minecraft.getChatStatus().isChatAllowed(this.minecraft.isLocalServer())) {
/* 52 */       return true;
/*    */     }
/*    */     
/* 55 */     return super.charTyped($$0, $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 61 */     if ($$0 == 256) {
/* 62 */       sendWakeUp();
/*    */     }
/* 64 */     if (!this.minecraft.getChatStatus().isChatAllowed(this.minecraft.isLocalServer())) {
/* 65 */       return true;
/*    */     }
/*    */     
/* 68 */     if ($$0 == 257 || $$0 == 335) {
/* 69 */       if (handleChatInput(this.input.getValue(), true)) {
/* 70 */         this.minecraft.setScreen(null);
/* 71 */         this.input.setValue("");
/* 72 */         this.minecraft.gui.getChat().resetChatScroll();
/*    */       } 
/* 74 */       return true;
/*    */     } 
/* 76 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   private void sendWakeUp() {
/* 80 */     ClientPacketListener $$0 = this.minecraft.player.connection;
/* 81 */     $$0.send((Packet)new ServerboundPlayerCommandPacket((Entity)this.minecraft.player, ServerboundPlayerCommandPacket.Action.STOP_SLEEPING));
/*    */   }
/*    */   
/*    */   public void onPlayerWokeUp() {
/* 85 */     if (this.input.getValue().isEmpty()) {
/* 86 */       this.minecraft.setScreen(null);
/*    */     } else {
/* 88 */       this.minecraft.setScreen(new ChatScreen(this.input.getValue()));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\InBedChatScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */