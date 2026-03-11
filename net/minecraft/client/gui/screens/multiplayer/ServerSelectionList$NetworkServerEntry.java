/*     */ package net.minecraft.client.gui.screens.multiplayer;
/*     */ 
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.server.LanServer;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkServerEntry
/*     */   extends ServerSelectionList.Entry
/*     */ {
/*     */   private static final int ICON_WIDTH = 32;
/* 174 */   private static final Component LAN_SERVER_HEADER = (Component)Component.translatable("lanServer.title");
/* 175 */   private static final Component HIDDEN_ADDRESS_TEXT = (Component)Component.translatable("selectServer.hiddenAddress");
/*     */   
/*     */   private final JoinMultiplayerScreen screen;
/*     */   protected final Minecraft minecraft;
/*     */   protected final LanServer serverData;
/*     */   private long lastClickTime;
/*     */   
/*     */   protected NetworkServerEntry(JoinMultiplayerScreen $$0, LanServer $$1) {
/* 183 */     this.screen = $$0;
/* 184 */     this.serverData = $$1;
/* 185 */     this.minecraft = Minecraft.getInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 190 */     $$0.drawString(this.minecraft.font, LAN_SERVER_HEADER, $$3 + 32 + 3, $$2 + 1, 16777215, false);
/* 191 */     $$0.drawString(this.minecraft.font, this.serverData.getMotd(), $$3 + 32 + 3, $$2 + 12, -8355712, false);
/*     */     
/* 193 */     if (this.minecraft.options.hideServerAddress) {
/* 194 */       $$0.drawString(this.minecraft.font, HIDDEN_ADDRESS_TEXT, $$3 + 32 + 3, $$2 + 12 + 11, 3158064, false);
/*     */     } else {
/* 196 */       $$0.drawString(this.minecraft.font, this.serverData.getAddress(), $$3 + 32 + 3, $$2 + 12 + 11, 3158064, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 202 */     this.screen.setSelected(this);
/* 203 */     if (Util.getMillis() - this.lastClickTime < 250L) {
/* 204 */       this.screen.joinSelectedServer();
/*     */     }
/* 206 */     this.lastClickTime = Util.getMillis();
/* 207 */     return false;
/*     */   }
/*     */   
/*     */   public LanServer getServerData() {
/* 211 */     return this.serverData;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 216 */     return (Component)Component.translatable("narrator.select", new Object[] { getServerNarration() });
/*     */   }
/*     */   
/*     */   public Component getServerNarration() {
/* 220 */     return (Component)Component.empty().append(LAN_SERVER_HEADER).append(CommonComponents.SPACE).append(this.serverData.getMotd());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\multiplayer\ServerSelectionList$NetworkServerEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */