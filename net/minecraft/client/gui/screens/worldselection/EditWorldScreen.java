/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractWidget;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.EditBox;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.components.toasts.SystemToast;
/*     */ import net.minecraft.client.gui.components.toasts.Toast;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LinearLayout;
/*     */ import net.minecraft.client.gui.layouts.SpacerElement;
/*     */ import net.minecraft.client.gui.screens.BackupConfirmScreen;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.storage.LevelResource;
/*     */ import net.minecraft.world.level.storage.LevelStorageSource;
/*     */ import net.minecraft.world.level.storage.LevelSummary;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class EditWorldScreen
/*     */   extends Screen {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  40 */   private static final Component NAME_LABEL = (Component)Component.translatable("selectWorld.enterName").withStyle(ChatFormatting.GRAY);
/*  41 */   private static final Component RESET_ICON_BUTTON = (Component)Component.translatable("selectWorld.edit.resetIcon");
/*  42 */   private static final Component FOLDER_BUTTON = (Component)Component.translatable("selectWorld.edit.openFolder");
/*  43 */   private static final Component BACKUP_BUTTON = (Component)Component.translatable("selectWorld.edit.backup");
/*  44 */   private static final Component BACKUP_FOLDER_BUTTON = (Component)Component.translatable("selectWorld.edit.backupFolder");
/*  45 */   private static final Component OPTIMIZE_BUTTON = (Component)Component.translatable("selectWorld.edit.optimize");
/*  46 */   private static final Component OPTIMIZE_TITLE = (Component)Component.translatable("optimizeWorld.confirm.title");
/*  47 */   private static final Component OPTIMIIZE_DESCRIPTION = (Component)Component.translatable("optimizeWorld.confirm.description");
/*  48 */   private static final Component SAVE_BUTTON = (Component)Component.translatable("selectWorld.edit.save");
/*     */   
/*     */   private static final int DEFAULT_WIDTH = 200;
/*     */   
/*     */   private static final int VERTICAL_SPACING = 4;
/*     */   private static final int HALF_WIDTH = 98;
/*  54 */   private final LinearLayout layout = LinearLayout.vertical().spacing(5);
/*     */   
/*     */   private final BooleanConsumer callback;
/*     */   private final LevelStorageSource.LevelStorageAccess levelAccess;
/*     */   
/*     */   public static EditWorldScreen create(Minecraft $$0, LevelStorageSource.LevelStorageAccess $$1, BooleanConsumer $$2) throws IOException {
/*  60 */     LevelSummary $$3 = $$1.getSummary($$1.getDataTag());
/*  61 */     return new EditWorldScreen($$0, $$1, $$3.getLevelName(), $$2);
/*     */   }
/*     */   
/*     */   private EditWorldScreen(Minecraft $$0, LevelStorageSource.LevelStorageAccess $$1, String $$2, BooleanConsumer $$3) {
/*  65 */     super((Component)Component.translatable("selectWorld.edit.title"));
/*  66 */     this.callback = $$3;
/*  67 */     this.levelAccess = $$1;
/*     */     
/*  69 */     Font $$4 = $$0.font;
/*  70 */     this.layout.addChild((LayoutElement)new SpacerElement(200, 20));
/*  71 */     this.layout.addChild((LayoutElement)new StringWidget(NAME_LABEL, $$4));
/*  72 */     EditBox $$5 = (EditBox)this.layout.addChild((LayoutElement)new EditBox($$4, 200, 20, NAME_LABEL));
/*  73 */     $$5.setValue($$2);
/*     */ 
/*     */     
/*  76 */     LinearLayout $$6 = LinearLayout.horizontal().spacing(4);
/*  77 */     Button $$7 = (Button)$$6.addChild(
/*  78 */         (LayoutElement)Button.builder(SAVE_BUTTON, $$1 -> onRename($$0.getValue()))
/*  79 */         .width(98)
/*  80 */         .build());
/*  81 */     $$6.addChild(
/*  82 */         (LayoutElement)Button.builder(CommonComponents.GUI_CANCEL, $$0 -> onClose())
/*  83 */         .width(98)
/*  84 */         .build());
/*     */ 
/*     */     
/*  87 */     $$5.setResponder($$1 -> $$0.active = !Util.isBlank($$1));
/*  88 */     ((Button)this.layout.addChild(
/*  89 */         (LayoutElement)Button.builder(RESET_ICON_BUTTON, $$1 -> {
/*     */             $$0.getIconFile().ifPresent(());
/*     */             
/*     */             $$1.active = false;
/*  93 */           }).width(200)
/*  94 */         .build()))
/*  95 */       .active = $$1.getIconFile().filter($$0 -> Files.isRegularFile($$0, new java.nio.file.LinkOption[0])).isPresent();
/*     */     
/*  97 */     this.layout.addChild(
/*  98 */         (LayoutElement)Button.builder(FOLDER_BUTTON, $$1 -> Util.getPlatform().openFile($$0.getLevelPath(LevelResource.ROOT).toFile()))
/*     */ 
/*     */         
/* 101 */         .width(200)
/* 102 */         .build());
/*     */     
/* 104 */     this.layout.addChild(
/* 105 */         (LayoutElement)Button.builder(BACKUP_BUTTON, $$1 -> {
/*     */             boolean $$2 = makeBackupAndShowToast($$0);
/*     */ 
/*     */             
/*     */             this.callback.accept(!$$2);
/* 110 */           }).width(200)
/* 111 */         .build());
/*     */     
/* 113 */     this.layout.addChild(
/* 114 */         (LayoutElement)Button.builder(BACKUP_FOLDER_BUTTON, $$1 -> {
/*     */             LevelStorageSource $$2 = $$0.getLevelSource();
/*     */             Path $$3 = $$2.getBackupPath();
/*     */             try {
/*     */               FileUtil.createDirectoriesSafe($$3);
/* 119 */             } catch (IOException $$4) {
/*     */               throw new RuntimeException($$4);
/*     */             } 
/*     */             
/*     */             Util.getPlatform().openFile($$3.toFile());
/* 124 */           }).width(200)
/* 125 */         .build());
/*     */     
/* 127 */     this.layout.addChild(
/* 128 */         (LayoutElement)Button.builder(OPTIMIZE_BUTTON, $$2 -> $$0.setScreen((Screen)new BackupConfirmScreen((), (), OPTIMIZE_TITLE, OPTIMIIZE_DESCRIPTION, true)))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         .width(200)
/* 138 */         .build());
/*     */ 
/*     */     
/* 141 */     this.layout.addChild((LayoutElement)new SpacerElement(200, 20));
/* 142 */     this.layout.addChild((LayoutElement)$$6);
/*     */     
/* 144 */     setInitialFocus((GuiEventListener)$$5);
/*     */     
/* 146 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/* 151 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/* 156 */     this.layout.arrangeElements();
/* 157 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 162 */     this.callback.accept(false);
/*     */   }
/*     */   
/*     */   private void onRename(String $$0) {
/*     */     try {
/* 167 */       this.levelAccess.renameLevel($$0);
/* 168 */     } catch (IOException|net.minecraft.nbt.NbtException|net.minecraft.nbt.ReportedNbtException $$1) {
/* 169 */       LOGGER.error("Failed to access world '{}'", this.levelAccess.getLevelId(), $$1);
/* 170 */       SystemToast.onWorldAccessFailure(this.minecraft, this.levelAccess.getLevelId());
/*     */     } 
/* 172 */     this.callback.accept(true);
/*     */   }
/*     */   
/*     */   public static boolean makeBackupAndShowToast(LevelStorageSource.LevelStorageAccess $$0) {
/* 176 */     long $$1 = 0L;
/* 177 */     IOException $$2 = null;
/*     */     try {
/* 179 */       $$1 = $$0.makeWorldBackup();
/* 180 */     } catch (IOException $$3) {
/* 181 */       $$2 = $$3;
/*     */     } 
/*     */     
/* 184 */     if ($$2 != null) {
/* 185 */       MutableComponent mutableComponent3 = Component.translatable("selectWorld.edit.backupFailed");
/* 186 */       MutableComponent mutableComponent4 = Component.literal($$2.getMessage());
/* 187 */       Minecraft.getInstance().getToasts().addToast((Toast)new SystemToast(SystemToast.SystemToastId.WORLD_BACKUP, (Component)mutableComponent3, (Component)mutableComponent4));
/* 188 */       return false;
/*     */     } 
/* 190 */     MutableComponent mutableComponent1 = Component.translatable("selectWorld.edit.backupCreated", new Object[] { $$0.getLevelId() });
/* 191 */     MutableComponent mutableComponent2 = Component.translatable("selectWorld.edit.backupSize", new Object[] { Integer.valueOf(Mth.ceil($$1 / 1048576.0D)) });
/* 192 */     Minecraft.getInstance().getToasts().addToast((Toast)new SystemToast(SystemToast.SystemToastId.WORLD_BACKUP, (Component)mutableComponent1, (Component)mutableComponent2));
/* 193 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 198 */     super.render($$0, $$1, $$2, $$3);
/* 199 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 15, 16777215);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\EditWorldScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */