/*     */ package com.mojang.realmsclient.gui.screens;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.realmsclient.client.RealmsClient;
/*     */ import com.mojang.realmsclient.dto.Backup;
/*     */ import com.mojang.realmsclient.dto.RealmsServer;
/*     */ import com.mojang.realmsclient.exception.RealmsServiceException;
/*     */ import com.mojang.realmsclient.util.RealmsUtil;
/*     */ import com.mojang.realmsclient.util.task.LongRunningTask;
/*     */ import java.text.DateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ImageButton;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.components.WidgetSprites;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.realms.RealmsObjectSelectionList;
/*     */ import net.minecraft.realms.RealmsScreen;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class RealmsBackupScreen extends RealmsScreen {
/*  35 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  37 */   static final Component RESTORE_TOOLTIP = (Component)Component.translatable("mco.backup.button.restore");
/*     */   
/*  39 */   static final Component HAS_CHANGES_TOOLTIP = (Component)Component.translatable("mco.backup.changes.tooltip");
/*  40 */   private static final Component TITLE = (Component)Component.translatable("mco.configure.world.backup");
/*  41 */   private static final Component NO_BACKUPS_LABEL = (Component)Component.translatable("mco.backup.nobackups");
/*     */   
/*     */   private final RealmsConfigureWorldScreen lastScreen;
/*     */   
/*  45 */   List<Backup> backups = Collections.emptyList();
/*     */   
/*     */   BackupObjectSelectionList backupObjectSelectionList;
/*     */   
/*  49 */   int selectedBackup = -1;
/*     */   
/*     */   private final int slotId;
/*     */   
/*     */   private Button downloadButton;
/*     */   
/*     */   private Button restoreButton;
/*     */   private Button changesButton;
/*  57 */   Boolean noBackups = Boolean.valueOf(false);
/*     */   
/*     */   final RealmsServer serverData;
/*     */   
/*     */   private static final String UPLOADED_KEY = "uploaded";
/*     */   
/*     */   public RealmsBackupScreen(RealmsConfigureWorldScreen $$0, RealmsServer $$1, int $$2) {
/*  64 */     super(TITLE);
/*  65 */     this.lastScreen = $$0;
/*  66 */     this.serverData = $$1;
/*  67 */     this.slotId = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void init() {
/*  72 */     (new Thread("Realms-fetch-backups")
/*     */       {
/*     */         public void run() {
/*  75 */           RealmsClient $$0 = RealmsClient.create();
/*     */           try {
/*  77 */             List<Backup> $$1 = ($$0.backupsFor(RealmsBackupScreen.this.serverData.id)).backups;
/*  78 */             RealmsBackupScreen.this.minecraft.execute(() -> {
/*     */                   RealmsBackupScreen.this.backups = $$0;
/*     */                   RealmsBackupScreen.this.noBackups = Boolean.valueOf(RealmsBackupScreen.this.backups.isEmpty());
/*     */                   RealmsBackupScreen.this.backupObjectSelectionList.clear();
/*     */                   for (Backup $$1 : RealmsBackupScreen.this.backups) {
/*     */                     RealmsBackupScreen.this.backupObjectSelectionList.addEntry($$1);
/*     */                   }
/*     */                 });
/*  86 */           } catch (RealmsServiceException $$2) {
/*  87 */             RealmsBackupScreen.LOGGER.error("Couldn't request backups", (Throwable)$$2);
/*     */           } 
/*     */         }
/*  90 */       }).start();
/*     */     
/*  92 */     this.downloadButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.backup.button.download"), $$0 -> downloadClicked())
/*  93 */         .bounds(this.width - 135, row(1), 120, 20).build());
/*  94 */     this.restoreButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.backup.button.restore"), $$0 -> restoreClicked(this.selectedBackup))
/*  95 */         .bounds(this.width - 135, row(3), 120, 20).build());
/*  96 */     this.changesButton = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("mco.backup.changes.tooltip"), $$0 -> {
/*     */             this.minecraft.setScreen((Screen)new RealmsBackupInfoScreen((Screen)this, this.backups.get(this.selectedBackup)));
/*     */             this.selectedBackup = -1;
/*  99 */           }).bounds(this.width - 135, row(5), 120, 20).build());
/* 100 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.minecraft.setScreen((Screen)this.lastScreen))
/* 101 */         .bounds(this.width - 100, this.height - 35, 85, 20).build());
/*     */     
/* 103 */     this.backupObjectSelectionList = (BackupObjectSelectionList)addRenderableWidget((GuiEventListener)new BackupObjectSelectionList());
/* 104 */     magicalSpecialHackyFocus((GuiEventListener)this.backupObjectSelectionList);
/*     */     
/* 106 */     updateButtonStates();
/*     */   }
/*     */   
/*     */   void updateButtonStates() {
/* 110 */     this.restoreButton.visible = shouldRestoreButtonBeVisible();
/* 111 */     this.changesButton.visible = shouldChangesButtonBeVisible();
/*     */   }
/*     */   
/*     */   private boolean shouldChangesButtonBeVisible() {
/* 115 */     if (this.selectedBackup == -1) {
/* 116 */       return false;
/*     */     }
/*     */     
/* 119 */     return !((Backup)this.backups.get(this.selectedBackup)).changeList.isEmpty();
/*     */   }
/*     */   
/*     */   private boolean shouldRestoreButtonBeVisible() {
/* 123 */     if (this.selectedBackup == -1) {
/* 124 */       return false;
/*     */     }
/* 126 */     return !this.serverData.expired;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 131 */     if ($$0 == 256) {
/* 132 */       this.minecraft.setScreen((Screen)this.lastScreen);
/* 133 */       return true;
/*     */     } 
/* 135 */     return super.keyPressed($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   void restoreClicked(int $$0) {
/* 139 */     if ($$0 >= 0 && $$0 < this.backups.size() && !this.serverData.expired) {
/* 140 */       this.selectedBackup = $$0;
/* 141 */       Date $$1 = ((Backup)this.backups.get($$0)).lastModifiedDate;
/* 142 */       String $$2 = DateFormat.getDateTimeInstance(3, 3).format($$1);
/* 143 */       Component $$3 = RealmsUtil.convertToAgePresentationFromInstant($$1);
/* 144 */       MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.restore.question.line1", new Object[] { $$2, $$3 });
/* 145 */       MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.restore.question.line2");
/* 146 */       this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$0 -> { if ($$0) { restore(); } else { this.selectedBackup = -1; this.minecraft.setScreen((Screen)this); }  }RealmsLongConfirmationScreen.Type.WARNING, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void downloadClicked() {
/* 158 */     MutableComponent mutableComponent1 = Component.translatable("mco.configure.world.restore.download.question.line1");
/* 159 */     MutableComponent mutableComponent2 = Component.translatable("mco.configure.world.restore.download.question.line2");
/* 160 */     this.minecraft.setScreen((Screen)new RealmsLongConfirmationScreen($$0 -> { if ($$0) { downloadWorldData(); } else { this.minecraft.setScreen((Screen)this); }  }RealmsLongConfirmationScreen.Type.INFO, (Component)mutableComponent1, (Component)mutableComponent2, true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void downloadWorldData() {
/* 170 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)this.lastScreen.getNewScreen(), new LongRunningTask[] { (LongRunningTask)new DownloadTask(this.serverData.id, this.slotId, this.serverData.name + " (" + this.serverData.name + ")", (Screen)this) }));
/*     */   }
/*     */   
/*     */   private void restore() {
/* 174 */     Backup $$0 = this.backups.get(this.selectedBackup);
/* 175 */     this.selectedBackup = -1;
/* 176 */     this.minecraft.setScreen((Screen)new RealmsLongRunningMcoTaskScreen((Screen)this.lastScreen.getNewScreen(), new LongRunningTask[] { (LongRunningTask)new RestoreTask($$0, this.serverData.id, this.lastScreen) }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 181 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 183 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 12, -1);
/*     */     
/* 185 */     if (this.noBackups.booleanValue()) {
/* 186 */       $$0.drawString(this.font, NO_BACKUPS_LABEL, 20, this.height / 2 - 10, -1, false);
/*     */     }
/*     */     
/* 189 */     this.downloadButton.active = !this.noBackups.booleanValue();
/*     */   }
/*     */   
/*     */   private class BackupObjectSelectionList extends RealmsObjectSelectionList<Entry> {
/*     */     public BackupObjectSelectionList() {
/* 194 */       super(RealmsBackupScreen.this.width - 150, RealmsBackupScreen.this.height - 47, 32, 36);
/*     */     }
/*     */     
/*     */     public void addEntry(Backup $$0) {
/* 198 */       addEntry(new RealmsBackupScreen.Entry($$0));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 203 */       return (int)(this.width * 0.93D);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxPosition() {
/* 208 */       return getItemCount() * 36;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getScrollbarPosition() {
/* 213 */       return this.width - 5;
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectItem(int $$0) {
/* 218 */       super.selectItem($$0);
/* 219 */       selectInviteListItem($$0);
/*     */     }
/*     */     
/*     */     public void selectInviteListItem(int $$0) {
/* 223 */       RealmsBackupScreen.this.selectedBackup = $$0;
/* 224 */       RealmsBackupScreen.this.updateButtonStates();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSelected(@Nullable RealmsBackupScreen.Entry $$0) {
/* 229 */       super.setSelected((AbstractSelectionList.Entry)$$0);
/*     */       
/* 231 */       RealmsBackupScreen.this.selectedBackup = children().indexOf($$0);
/* 232 */       RealmsBackupScreen.this.updateButtonStates();
/*     */     }
/*     */   }
/*     */   
/*     */   private class Entry
/*     */     extends ObjectSelectionList.Entry<Entry> {
/*     */     private static final int Y_PADDING = 2;
/*     */     private static final int X_PADDING = 7;
/* 240 */     private static final WidgetSprites CHANGES_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("backup/changes"), new ResourceLocation("backup/changes_highlighted"));
/*     */ 
/*     */ 
/*     */     
/* 244 */     private static final WidgetSprites RESTORE_BUTTON_SPRITES = new WidgetSprites(new ResourceLocation("backup/restore"), new ResourceLocation("backup/restore_highlighted"));
/*     */ 
/*     */ 
/*     */     
/*     */     private final Backup backup;
/*     */ 
/*     */     
/* 251 */     private final List<AbstractWidget> children = new ArrayList<>();
/*     */     
/*     */     @Nullable
/*     */     private ImageButton restoreButton;
/*     */     @Nullable
/*     */     private ImageButton changesButton;
/*     */     
/*     */     public Entry(Backup $$0) {
/* 259 */       this.backup = $$0;
/*     */       
/* 261 */       populateChangeList($$0);
/*     */       
/* 263 */       if (!$$0.changeList.isEmpty()) {
/* 264 */         addChangesButton();
/*     */       }
/*     */       
/* 267 */       if (!RealmsBackupScreen.this.serverData.expired) {
/* 268 */         addRestoreButton();
/*     */       }
/*     */     }
/*     */     
/*     */     private void populateChangeList(Backup $$0) {
/* 273 */       int $$1 = RealmsBackupScreen.this.backups.indexOf($$0);
/* 274 */       if ($$1 == RealmsBackupScreen.this.backups.size() - 1) {
/*     */         return;
/*     */       }
/* 277 */       Backup $$2 = RealmsBackupScreen.this.backups.get($$1 + 1);
/* 278 */       for (String $$3 : $$0.metadata.keySet()) {
/* 279 */         if (!$$3.contains("uploaded") && $$2.metadata.containsKey($$3)) {
/* 280 */           if (!((String)$$0.metadata.get($$3)).equals($$2.metadata.get($$3)))
/* 281 */             addToChangeList($$3); 
/*     */           continue;
/*     */         } 
/* 284 */         addToChangeList($$3);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private void addToChangeList(String $$0) {
/* 290 */       if ($$0.contains("uploaded")) {
/* 291 */         String $$1 = DateFormat.getDateTimeInstance(3, 3).format(this.backup.lastModifiedDate);
/* 292 */         this.backup.changeList.put($$0, $$1);
/* 293 */         this.backup.setUploadedVersion(true);
/*     */       } else {
/* 295 */         this.backup.changeList.put($$0, (String)this.backup.metadata.get($$0));
/*     */       } 
/*     */     }
/*     */     
/*     */     private void addChangesButton() {
/* 300 */       int $$0 = 9, $$1 = 9;
/* 301 */       int $$2 = RealmsBackupScreen.this.backupObjectSelectionList.getRowRight() - 9 - 28;
/* 302 */       int $$3 = RealmsBackupScreen.this.backupObjectSelectionList.getRowTop(RealmsBackupScreen.this.backups.indexOf(this.backup)) + 2;
/* 303 */       this.changesButton = new ImageButton($$2, $$3, 9, 9, CHANGES_BUTTON_SPRITES, $$0 -> RealmsBackupScreen.this.minecraft.setScreen((Screen)new RealmsBackupInfoScreen((Screen)RealmsBackupScreen.this, this.backup)), CommonComponents.EMPTY);
/* 304 */       this.changesButton.setTooltip(Tooltip.create(RealmsBackupScreen.HAS_CHANGES_TOOLTIP));
/* 305 */       this.children.add(this.changesButton);
/*     */     }
/*     */     
/*     */     private void addRestoreButton() {
/* 309 */       int $$0 = 17, $$1 = 10;
/* 310 */       int $$2 = RealmsBackupScreen.this.backupObjectSelectionList.getRowRight() - 17 - 7;
/* 311 */       int $$3 = RealmsBackupScreen.this.backupObjectSelectionList.getRowTop(RealmsBackupScreen.this.backups.indexOf(this.backup)) + 2;
/* 312 */       this.restoreButton = new ImageButton($$2, $$3, 17, 10, RESTORE_BUTTON_SPRITES, $$0 -> RealmsBackupScreen.this.restoreClicked(RealmsBackupScreen.this.backups.indexOf(this.backup)), CommonComponents.EMPTY);
/* 313 */       this.restoreButton.setTooltip(Tooltip.create(RealmsBackupScreen.RESTORE_TOOLTIP));
/* 314 */       this.children.add(this.restoreButton);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 319 */       if (this.restoreButton != null) {
/* 320 */         this.restoreButton.mouseClicked($$0, $$1, $$2);
/*     */       }
/* 322 */       if (this.changesButton != null) {
/* 323 */         this.changesButton.mouseClicked($$0, $$1, $$2);
/*     */       }
/* 325 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 330 */       int $$10 = this.backup.isUploadedVersion() ? -8388737 : 16777215;
/* 331 */       $$0.drawString(RealmsBackupScreen.this.font, (Component)Component.translatable("mco.backup.entry", new Object[] { RealmsUtil.convertToAgePresentationFromInstant(this.backup.lastModifiedDate) }), $$3, $$2 + 1, $$10, false);
/* 332 */       $$0.drawString(RealmsBackupScreen.this.font, getMediumDatePresentation(this.backup.lastModifiedDate), $$3, $$2 + 12, 5000268, false);
/* 333 */       this.children.forEach($$5 -> {
/*     */             $$5.setY($$0 + 2);
/*     */             $$5.render($$1, $$2, $$3, $$4);
/*     */           });
/*     */     }
/*     */     
/*     */     private String getMediumDatePresentation(Date $$0) {
/* 340 */       return DateFormat.getDateTimeInstance(3, 3).format($$0);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 345 */       return (Component)Component.translatable("narrator.select", new Object[] { this.backup.lastModifiedDate.toString() });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\realmsclient\gui\screens\RealmsBackupScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */