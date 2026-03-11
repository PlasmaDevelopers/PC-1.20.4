/*    */ package net.minecraft.client.gui.screens;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import net.minecraft.client.gui.GuiGraphics;
/*    */ import net.minecraft.client.gui.components.Button;
/*    */ import net.minecraft.client.gui.components.Checkbox;
/*    */ import net.minecraft.client.gui.components.MultiLineLabel;
/*    */ import net.minecraft.network.chat.CommonComponents;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.FormattedText;
/*    */ 
/*    */ public class BackupConfirmScreen
/*    */   extends Screen {
/*    */   private final Runnable onCancel;
/*    */   protected final Listener onProceed;
/* 16 */   private MultiLineLabel message = MultiLineLabel.EMPTY; private final Component description; private final boolean promptForCacheErase;
/*    */   protected int id;
/*    */   private Checkbox eraseCache;
/*    */   
/*    */   public BackupConfirmScreen(Runnable $$0, Listener $$1, Component $$2, Component $$3, boolean $$4) {
/* 21 */     super($$2);
/* 22 */     this.onCancel = $$0;
/* 23 */     this.onProceed = $$1;
/* 24 */     this.description = $$3;
/* 25 */     this.promptForCacheErase = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init() {
/* 30 */     super.init();
/*    */     
/* 32 */     this.message = MultiLineLabel.create(this.font, (FormattedText)this.description, this.width - 50);
/*    */     
/* 34 */     Objects.requireNonNull(this.font); int $$0 = (this.message.getLineCount() + 1) * 9;
/* 35 */     addRenderableWidget(Button.builder((Component)Component.translatable("selectWorld.backupJoinConfirmButton"), $$0 -> this.onProceed.proceed(true, this.eraseCache.selected())).bounds(this.width / 2 - 155, 100 + $$0, 150, 20).build());
/* 36 */     addRenderableWidget(Button.builder((Component)Component.translatable("selectWorld.backupJoinSkipButton"), $$0 -> this.onProceed.proceed(false, this.eraseCache.selected())).bounds(this.width / 2 - 155 + 160, 100 + $$0, 150, 20).build());
/* 37 */     addRenderableWidget(Button.builder(CommonComponents.GUI_CANCEL, $$0 -> this.onCancel.run()).bounds(this.width / 2 - 155 + 80, 124 + $$0, 150, 20).build());
/* 38 */     this.eraseCache = Checkbox.builder((Component)Component.translatable("selectWorld.backupEraseCache"), this.font).pos(this.width / 2 - 155 + 80, 76 + $$0).build();
/* 39 */     if (this.promptForCacheErase) {
/* 40 */       addRenderableWidget(this.eraseCache);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 46 */     super.render($$0, $$1, $$2, $$3);
/* 47 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 50, 16777215);
/* 48 */     this.message.renderCentered($$0, this.width / 2, 70);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCloseOnEsc() {
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 58 */     if ($$0 == 256) {
/* 59 */       this.onCancel.run();
/* 60 */       return true;
/*    */     } 
/* 62 */     return super.keyPressed($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public static interface Listener {
/*    */     void proceed(boolean param1Boolean1, boolean param1Boolean2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\BackupConfirmScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */