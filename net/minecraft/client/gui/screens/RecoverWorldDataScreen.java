/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.io.IOException;
/*     */ import java.time.Instant;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.Tooltip;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
/*     */ import net.minecraft.client.gui.screens.worldselection.WorldSelectionList;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecoverWorldDataScreen
/*     */   extends Screen
/*     */ {
/*  30 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int SCREEN_SIDE_MARGIN = 25;
/*     */   
/*  34 */   private static final Component TITLE = (Component)Component.translatable("recover_world.title").withStyle(ChatFormatting.BOLD);
/*  35 */   private static final Component BUGTRACKER_BUTTON = (Component)Component.translatable("recover_world.bug_tracker");
/*  36 */   private static final Component RESTORE_BUTTON = (Component)Component.translatable("recover_world.restore");
/*  37 */   private static final Component NO_FALLBACK_TOOLTIP = (Component)Component.translatable("recover_world.no_fallback");
/*  38 */   private static final Component DONE_TITLE = (Component)Component.translatable("recover_world.done.title");
/*  39 */   private static final Component DONE_SUCCESS = (Component)Component.translatable("recover_world.done.success");
/*  40 */   private static final Component DONE_FAILED = (Component)Component.translatable("recover_world.done.failed");
/*  41 */   private static final Component NO_ISSUES = (Component)Component.translatable("recover_world.issue.none").withStyle(ChatFormatting.GREEN);
/*  42 */   private static final Component MISSING_FILE = (Component)Component.translatable("recover_world.issue.missing_file").withStyle(ChatFormatting.RED);
/*     */   
/*     */   private final BooleanConsumer callback;
/*  45 */   private final LinearLayout layout = LinearLayout.vertical().spacing(10);
/*     */   private final Component message;
/*     */   private final MultiLineTextWidget messageWidget;
/*     */   private final MultiLineTextWidget issuesWidget;
/*     */   private final LevelStorageSource.LevelStorageAccess storageAccess;
/*     */   
/*     */   public RecoverWorldDataScreen(Minecraft $$0, BooleanConsumer $$1, LevelStorageSource.LevelStorageAccess $$2) {
/*  52 */     super(TITLE);
/*  53 */     this.callback = $$1;
/*  54 */     this.message = (Component)Component.translatable("recover_world.message", new Object[] { Component.literal($$2.getLevelId()).withStyle(ChatFormatting.GRAY) });
/*  55 */     this.messageWidget = new MultiLineTextWidget(this.message, $$0.font);
/*  56 */     this.storageAccess = $$2;
/*  57 */     Exception $$3 = collectIssue($$2, false);
/*  58 */     Exception $$4 = collectIssue($$2, true);
/*     */ 
/*     */ 
/*     */     
/*  62 */     MutableComponent mutableComponent = Component.empty().append(buildInfo($$2, false, $$3)).append("\n").append(buildInfo($$2, true, $$4));
/*  63 */     this.issuesWidget = new MultiLineTextWidget((Component)mutableComponent, $$0.font);
/*  64 */     boolean $$6 = ($$3 != null && $$4 == null);
/*     */     
/*  66 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*     */     
/*  68 */     this.layout.addChild((LayoutElement)new StringWidget(this.title, $$0.font));
/*  69 */     this.layout.addChild((LayoutElement)this.messageWidget.setCentered(true));
/*  70 */     this.layout.addChild((LayoutElement)this.issuesWidget);
/*     */     
/*  72 */     LinearLayout $$7 = LinearLayout.horizontal().spacing(5);
/*  73 */     $$7.addChild(
/*  74 */         (LayoutElement)Button.builder(BUGTRACKER_BUTTON, 
/*     */           
/*  76 */           ConfirmLinkScreen.confirmLink(this, "https://aka.ms/snapshotbugs?ref=game"))
/*     */         
/*  78 */         .size(120, 20)
/*  79 */         .build());
/*     */     
/*  81 */     ((Button)$$7.addChild(
/*  82 */         (LayoutElement)Button.builder(RESTORE_BUTTON, $$1 -> attemptRestore($$0))
/*     */ 
/*     */ 
/*     */         
/*  86 */         .size(120, 20)
/*  87 */         .tooltip($$6 ? null : Tooltip.create(NO_FALLBACK_TOOLTIP))
/*  88 */         .build())).active = $$6;
/*     */     
/*  90 */     this.layout.addChild((LayoutElement)$$7);
/*  91 */     this.layout.addChild(
/*  92 */         (LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose())
/*     */ 
/*     */ 
/*     */         
/*  96 */         .size(120, 20)
/*  97 */         .build());
/*     */ 
/*     */     
/* 100 */     this.layout.visitWidgets(this::addRenderableWidget);
/*     */   }
/*     */ 
/*     */   
/*     */   private void attemptRestore(Minecraft $$0) {
/* 105 */     Exception $$1 = collectIssue(this.storageAccess, false);
/* 106 */     Exception $$2 = collectIssue(this.storageAccess, true);
/* 107 */     if ($$1 == null || $$2 != null) {
/* 108 */       LOGGER.error("Failed to recover world, files not as expected. level.dat: {}, level.dat_old: {}", ($$1 != null) ? $$1.getMessage() : "no issues", ($$2 != null) ? $$2.getMessage() : "no issues");
/* 109 */       $$0.setScreen(new AlertScreen(() -> this.callback.accept(false), DONE_TITLE, DONE_FAILED));
/*     */       return;
/*     */     } 
/* 112 */     $$0.forceSetScreen(new GenericDirtMessageScreen((Component)Component.translatable("recover_world.restoring")));
/* 113 */     EditWorldScreen.makeBackupAndShowToast(this.storageAccess);
/* 114 */     if (this.storageAccess.restoreLevelDataFromOld()) {
/* 115 */       $$0.setScreen(new ConfirmScreen(this.callback, DONE_TITLE, DONE_SUCCESS, CommonComponents.GUI_CONTINUE, CommonComponents.GUI_BACK));
/*     */     } else {
/* 117 */       $$0.setScreen(new AlertScreen(() -> this.callback.accept(false), DONE_TITLE, DONE_FAILED));
/*     */     } 
/*     */   }
/*     */   
/*     */   private Component buildInfo(LevelStorageSource.LevelStorageAccess $$0, boolean $$1, @Nullable Exception $$2) {
/* 122 */     if ($$1 && $$2 instanceof java.io.FileNotFoundException) {
/* 123 */       return (Component)Component.empty();
/*     */     }
/* 125 */     MutableComponent $$3 = Component.empty();
/* 126 */     Instant $$4 = $$0.getFileModificationTime($$1);
/* 127 */     MutableComponent $$5 = ($$4 != null) ? Component.literal(WorldSelectionList.DATE_FORMAT.format($$4)) : Component.translatable("recover_world.state_entry.unknown");
/* 128 */     $$3.append((Component)Component.translatable("recover_world.state_entry", new Object[] { $$5.withStyle(ChatFormatting.GRAY) }));
/* 129 */     if ($$2 == null) {
/* 130 */       $$3.append(NO_ISSUES);
/* 131 */     } else if ($$2 instanceof java.io.FileNotFoundException) {
/* 132 */       $$3.append(MISSING_FILE);
/* 133 */     } else if ($$2 instanceof net.minecraft.nbt.ReportedNbtException) {
/* 134 */       $$3.append((Component)Component.literal($$2.getCause().toString()).withStyle(ChatFormatting.RED));
/*     */     } else {
/* 136 */       $$3.append((Component)Component.literal($$2.toString()).withStyle(ChatFormatting.RED));
/*     */     } 
/* 138 */     return (Component)$$3;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Exception collectIssue(LevelStorageSource.LevelStorageAccess $$0, boolean $$1) {
/*     */     try {
/* 144 */       if (!$$1) {
/* 145 */         $$0.getSummary($$0.getDataTag());
/*     */       } else {
/* 147 */         $$0.getSummary($$0.getDataTagFallback());
/*     */       } 
/* 149 */     } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$2) {
/* 150 */       return $$2;
/*     */     } 
/* 152 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 157 */     super.init();
/* 158 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/* 163 */     this.issuesWidget.setMaxWidth(this.width - 50);
/* 164 */     this.messageWidget.setMaxWidth(this.width - 50);
/* 165 */     this.layout.arrangeElements();
/* 166 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 171 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), this.message });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 176 */     this.callback.accept(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\RecoverWorldDataScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */