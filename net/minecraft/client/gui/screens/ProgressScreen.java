/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.client.GameNarrator;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.ProgressListener;
/*    */ 
/*    */ public class ProgressScreen
/*    */   extends Screen
/*    */   implements ProgressListener {
/*    */   @Nullable
/*    */   private Component header;
/*    */   @Nullable
/*    */   private Component stage;
/*    */   private int progress;
/*    */   private boolean stop;
/*    */   private final boolean clearScreenAfterStop;
/*    */   
/*    */   public ProgressScreen(boolean $$0) {
/* 21 */     super(GameNarrator.NO_TITLE);
/* 22 */     this.clearScreenAfterStop = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 27 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldNarrateNavigation() {
/* 32 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void progressStartNoAbort(Component $$0) {
/* 37 */     progressStart($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void progressStart(Component $$0) {
/* 42 */     this.header = $$0;
/* 43 */     progressStage((Component)Component.translatable("menu.working"));
/*    */   }
/*    */ 
/*    */   
/*    */   public void progressStage(Component $$0) {
/* 48 */     this.stage = $$0;
/* 49 */     progressStagePercentage(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void progressStagePercentage(int $$0) {
/* 54 */     this.progress = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 59 */     this.stop = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 64 */     if (this.stop) {
/* 65 */       if (this.clearScreenAfterStop) {
/* 66 */         this.minecraft.setScreen(null);
/*    */       }
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 72 */     super.render($$0, $$1, $$2, $$3);
/*    */     
/* 74 */     if (this.header != null) {
/* 75 */       $$0.drawCenteredString(this.font, this.header, this.width / 2, 70, 16777215);
/*    */     }
/* 77 */     if (this.stage != null && this.progress != 0)
/* 78 */       $$0.drawCenteredString(this.font, (Component)Component.empty().append(this.stage).append(" " + this.progress + "%"), this.width / 2, 90, 16777215); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\ProgressScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */