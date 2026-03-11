/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import java.util.function.BooleanSupplier;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class ReceivingLevelScreen
/*    */   extends Screen {
/* 10 */   private static final Component DOWNLOADING_TERRAIN_TEXT = (Component)Component.translatable("multiplayer.downloadingTerrain");
/*    */   private static final long CHUNK_LOADING_START_WAIT_LIMIT_MS = 30000L;
/*    */   private final long createdAt;
/*    */   private final BooleanSupplier levelReceived;
/*    */   
/*    */   public ReceivingLevelScreen(BooleanSupplier $$0) {
/* 16 */     super(GameNarrator.NO_TITLE);
/* 17 */     this.levelReceived = $$0;
/* 18 */     this.createdAt = System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 23 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldNarrateNavigation() {
/* 28 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 33 */     super.render($$0, $$1, $$2, $$3);
/* 34 */     $$0.drawCenteredString(this.font, DOWNLOADING_TERRAIN_TEXT, this.width / 2, this.height / 2 - 50, 16777215);
/*    */   }
/*    */ 
/*    */   
/*    */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 39 */     renderDirtBackground($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void tick() {
/* 45 */     if (this.levelReceived.getAsBoolean() || System.currentTimeMillis() > this.createdAt + 30000L) {
/* 46 */       onClose();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClose() {
/* 52 */     this.minecraft.getNarrator().sayNow((Component)Component.translatable("narrator.ready_to_play"));
/* 53 */     super.onClose();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isPauseScreen() {
/* 58 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ReceivingLevelScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */