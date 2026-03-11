/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.StringWidget;
/*     */ import net.minecraft.client.gui.layouts.FrameLayout;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class NoticeWithLinkScreen extends Screen {
/*  15 */   private static final Component SYMLINK_WORLD_TITLE = (Component)Component.translatable("symlink_warning.title.world").withStyle(ChatFormatting.BOLD);
/*  16 */   private static final Component SYMLINK_WORLD_MESSAGE_TEXT = (Component)Component.translatable("symlink_warning.message.world", new Object[] { "https://aka.ms/MinecraftSymLinks" });
/*     */   
/*  18 */   private static final Component SYMLINK_PACK_TITLE = (Component)Component.translatable("symlink_warning.title.pack").withStyle(ChatFormatting.BOLD);
/*  19 */   private static final Component SYMLINK_PACK_MESSAGE_TEXT = (Component)Component.translatable("symlink_warning.message.pack", new Object[] { "https://aka.ms/MinecraftSymLinks" });
/*     */   
/*     */   private final Component message;
/*     */   private final String url;
/*     */   private final Runnable onClose;
/*  24 */   private final GridLayout layout = (new GridLayout()).rowSpacing(10);
/*     */   
/*     */   public NoticeWithLinkScreen(Component $$0, Component $$1, String $$2, Runnable $$3) {
/*  27 */     super($$0);
/*  28 */     this.message = $$1;
/*  29 */     this.url = $$2;
/*  30 */     this.onClose = $$3;
/*     */   }
/*     */   
/*     */   public static Screen createWorldSymlinkWarningScreen(Runnable $$0) {
/*  34 */     return new NoticeWithLinkScreen(SYMLINK_WORLD_TITLE, SYMLINK_WORLD_MESSAGE_TEXT, "https://aka.ms/MinecraftSymLinks", $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Screen createPackSymlinkWarningScreen(Runnable $$0) {
/*  43 */     return new NoticeWithLinkScreen(SYMLINK_PACK_TITLE, SYMLINK_PACK_MESSAGE_TEXT, "https://aka.ms/MinecraftSymLinks", $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void init() {
/*  53 */     super.init();
/*     */     
/*  55 */     this.layout.defaultCellSetting().alignHorizontallyCenter();
/*  56 */     GridLayout.RowHelper $$0 = this.layout.createRowHelper(1);
/*     */     
/*  58 */     $$0.addChild((LayoutElement)new StringWidget(this.title, this.font));
/*  59 */     $$0.addChild((LayoutElement)(new MultiLineTextWidget(this.message, this.font)).setMaxWidth(this.width - 50).setCentered(true));
/*     */     
/*  61 */     int $$1 = 120;
/*     */     
/*  63 */     GridLayout $$2 = (new GridLayout()).columnSpacing(5);
/*  64 */     GridLayout.RowHelper $$3 = $$2.createRowHelper(3);
/*  65 */     $$3.addChild(
/*  66 */         (LayoutElement)Button.builder(CommonComponents.GUI_OPEN_IN_BROWSER, $$0 -> Util.getPlatform().openUri(this.url))
/*     */ 
/*     */ 
/*     */         
/*  70 */         .size(120, 20)
/*  71 */         .build());
/*     */     
/*  73 */     $$3.addChild(
/*  74 */         (LayoutElement)Button.builder(CommonComponents.GUI_COPY_LINK_TO_CLIPBOARD, $$0 -> this.minecraft.keyboardHandler.setClipboard(this.url))
/*     */ 
/*     */ 
/*     */         
/*  78 */         .size(120, 20)
/*  79 */         .build());
/*     */     
/*  81 */     $$3.addChild(
/*  82 */         (LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose())
/*     */ 
/*     */ 
/*     */         
/*  86 */         .size(120, 20)
/*  87 */         .build());
/*     */ 
/*     */     
/*  90 */     $$0.addChild((LayoutElement)$$2);
/*     */     
/*  92 */     repositionElements();
/*  93 */     this.layout.visitWidgets(this::addRenderableWidget);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  98 */     this.layout.arrangeElements();
/*  99 */     FrameLayout.centerInRectangle((LayoutElement)this.layout, getRectangle());
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/* 104 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), this.message });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 109 */     this.onClose.run();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\NoticeWithLinkScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */