/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ 
/*     */ import com.mojang.realmsclient.dto.Backup;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
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
/*     */ class Entry
/*     */   extends ObjectSelectionList.Entry<RealmsBackupScreen.Entry>
/*     */ {
/*     */   private static final int Y_PADDING = 2;
/*     */   private static final int X_PADDING = 7;
/* 240 */   private static final WidgetSprites CHANGES_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("backup/changes"), new ResourceLocation("backup/changes_highlighted"));
/*     */ 
/*     */ 
/*     */   
/* 244 */   private static final WidgetSprites RESTORE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("backup/restore"), new ResourceLocation("backup/restore_highlighted"));
/*     */ 
/*     */ 
/*     */   
/*     */   private final Backup backup;
/*     */ 
/*     */   
/* 251 */   private final List<AbstractWidget> children = new ArrayList<>();
/*     */   
/*     */   @Nullable
/*     */   private ImageButton restoreButton;
/*     */   @Nullable
/*     */   private ImageButton changesButton;
/*     */   
/*     */   public Entry(Backup $$0) {
/* 259 */     this.backup = $$0;
/*     */     
/* 261 */     populateChangeList($$0);
/*     */     
/* 263 */     if (!$$0.changeList.isEmpty()) {
/* 264 */       addChangesButton();
/*     */     }
/*     */     
/* 267 */     if (!paramRealmsBackupScreen.serverData.expired) {
/* 268 */       addRestoreButton();
/*     */     }
/*     */   }
/*     */   
/*     */   private void populateChangeList(Backup $$0) {
/* 273 */     int $$1 = RealmsBackupScreen.this.backups.indexOf($$0);
/* 274 */     if ($$1 == RealmsBackupScreen.this.backups.size() - 1) {
/*     */       return;
/*     */     }
/* 277 */     Backup $$2 = RealmsBackupScreen.this.backups.get($$1 + 1);
/* 278 */     for (String $$3 : $$0.metadata.keySet()) {
/* 279 */       if (!$$3.contains("uploaded") && $$2.metadata.containsKey($$3)) {
/* 280 */         if (!((String)$$0.metadata.get($$3)).equals($$2.metadata.get($$3)))
/* 281 */           addToChangeList($$3); 
/*     */         continue;
/*     */       } 
/* 284 */       addToChangeList($$3);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addToChangeList(String $$0) {
/* 290 */     if ($$0.contains("uploaded")) {
/* 291 */       String $$1 = DateFormat.getDateTimeInstance(3, 3).format(this.backup.lastModifiedDate);
/* 292 */       this.backup.changeList.put($$0, $$1);
/* 293 */       this.backup.setUploadedVersion(true);
/*     */     } else {
/* 295 */       this.backup.changeList.put($$0, (String)this.backup.metadata.get($$0));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addChangesButton() {
/* 300 */     int $$0 = 9, $$1 = 9;
/* 301 */     int $$2 = RealmsBackupScreen.this.backupObjectSelectionList.getRowRight() - 9 - 28;
/* 302 */     int $$3 = RealmsBackupScreen.this.backupObjectSelectionList.getRowTop(RealmsBackupScreen.this.backups.indexOf(this.backup)) + 2;
/* 303 */     this.changesButton = new ImageButton($$2, $$3, 9, 9, CHANGES_BUTTON_SPRITES, $$0 -> RealmsBackupScreen.access$300(RealmsBackupScreen.this).setScreen((Screen)new RealmsBackupInfoScreen((Screen)RealmsBackupScreen.this, this.backup)), CommonComponents.EMPTY);
/* 304 */     this.changesButton.setTooltip(Tooltip.create(RealmsBackupScreen.HAS_CHANGES_TOOLTIP));
/* 305 */     this.children.add(this.changesButton);
/*     */   }
/*     */   
/*     */   private void addRestoreButton() {
/* 309 */     int $$0 = 17, $$1 = 10;
/* 310 */     int $$2 = RealmsBackupScreen.this.backupObjectSelectionList.getRowRight() - 17 - 7;
/* 311 */     int $$3 = RealmsBackupScreen.this.backupObjectSelectionList.getRowTop(RealmsBackupScreen.this.backups.indexOf(this.backup)) + 2;
/* 312 */     this.restoreButton = new ImageButton($$2, $$3, 17, 10, RESTORE_BUTTON_SPRITES, $$0 -> RealmsBackupScreen.this.restoreClicked(RealmsBackupScreen.this.backups.indexOf(this.backup)), CommonComponents.EMPTY);
/* 313 */     this.restoreButton.setTooltip(Tooltip.create(RealmsBackupScreen.RESTORE_TOOLTIP));
/* 314 */     this.children.add(this.restoreButton);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 319 */     if (this.restoreButton != null) {
/* 320 */       this.restoreButton.mouseClicked($$0, $$1, $$2);
/*     */     }
/* 322 */     if (this.changesButton != null) {
/* 323 */       this.changesButton.mouseClicked($$0, $$1, $$2);
/*     */     }
/* 325 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 330 */     int $$10 = this.backup.isUploadedVersion() ? -8388737 : 16777215;
/* 331 */     $$0.drawString(RealmsBackupScreen.access$100(RealmsBackupScreen.this), (Component)Component.translatable("mco.backup.entry", new Object[] { RealmsUtil.convertToAgePresentationFromInstant(this.backup.lastModifiedDate) }), $$3, $$2 + 1, $$10, false);
/* 332 */     $$0.drawString(RealmsBackupScreen.access$200(RealmsBackupScreen.this), getMediumDatePresentation(this.backup.lastModifiedDate), $$3, $$2 + 12, 5000268, false);
/* 333 */     this.children.forEach($$5 -> {
/*     */           $$5.setY($$0 + 2);
/*     */           $$5.render($$1, $$2, $$3, $$4);
/*     */         });
/*     */   }
/*     */   
/*     */   private String getMediumDatePresentation(Date $$0) {
/* 340 */     return DateFormat.getDateTimeInstance(3, 3).format($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarration() {
/* 345 */     return (Component)Component.translatable("narrator.select", new Object[] { this.backup.lastModifiedDate.toString() });
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupScreen$Entry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */