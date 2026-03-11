/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.MultiLineTextWidget;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.layouts.GridLayout;
/*     */ import net.minecraft.client.gui.layouts.LayoutElement;
/*     */ import net.minecraft.client.gui.layouts.LayoutSettings;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ 
/*     */ public class ConfirmExperimentalFeaturesScreen extends Screen {
/*  25 */   private static final Component TITLE = (Component)Component.translatable("selectWorld.experimental.title");
/*  26 */   private static final Component MESSAGE = (Component)Component.translatable("selectWorld.experimental.message");
/*  27 */   private static final Component DETAILS_BUTTON = (Component)Component.translatable("selectWorld.experimental.details");
/*     */   
/*     */   private static final int COLUMN_SPACING = 10;
/*     */   
/*     */   private static final int DETAILS_BUTTON_WIDTH = 100;
/*     */   private final BooleanConsumer callback;
/*     */   final Collection<Pack> enabledPacks;
/*  34 */   private final GridLayout layout = (new GridLayout()).columnSpacing(10).rowSpacing(20);
/*     */   
/*     */   public ConfirmExperimentalFeaturesScreen(Collection<Pack> $$0, BooleanConsumer $$1) {
/*  37 */     super(TITLE);
/*  38 */     this.enabledPacks = $$0;
/*  39 */     this.callback = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getNarrationMessage() {
/*  44 */     return (Component)CommonComponents.joinForNarration(new Component[] { super.getNarrationMessage(), MESSAGE });
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  49 */     super.init();
/*     */ 
/*     */     
/*  52 */     GridLayout.RowHelper $$0 = this.layout.createRowHelper(2);
/*     */     
/*  54 */     LayoutSettings $$1 = $$0.newCellSettings().alignHorizontallyCenter();
/*  55 */     $$0.addChild((LayoutElement)new StringWidget(this.title, this.font), 2, $$1);
/*  56 */     MultiLineTextWidget $$2 = (MultiLineTextWidget)$$0.addChild((LayoutElement)(new MultiLineTextWidget(MESSAGE, this.font)).setCentered(true), 2, $$1);
/*  57 */     $$2.setMaxWidth(310);
/*     */     
/*  59 */     $$0.addChild((LayoutElement)Button.builder(DETAILS_BUTTON, $$0 -> this.minecraft.setScreen(new DetailsScreen())).width(100).build(), 2, $$1);
/*     */     
/*  61 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_PROCEED, $$0 -> this.callback.accept(true)).build());
/*  62 */     $$0.addChild((LayoutElement)Button.builder(CommonComponents.GUI_BACK, $$0 -> this.callback.accept(false)).build());
/*     */     
/*  64 */     this.layout.visitWidgets($$1 -> (AbstractWidget)$$0.addRenderableWidget($$1));
/*  65 */     this.layout.arrangeElements();
/*  66 */     repositionElements();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void repositionElements() {
/*  71 */     FrameLayout.alignInRectangle((LayoutElement)this.layout, 0, 0, this.width, this.height, 0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  76 */     this.callback.accept(false);
/*     */   }
/*     */   
/*     */   private class DetailsScreen extends Screen {
/*     */     private PackList packList;
/*     */     
/*     */     DetailsScreen() {
/*  83 */       super((Component)Component.translatable("selectWorld.experimental.details.title"));
/*     */     }
/*     */ 
/*     */     
/*     */     public void onClose() {
/*  88 */       this.minecraft.setScreen(ConfirmExperimentalFeaturesScreen.this);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void init() {
/*  93 */       super.init();
/*     */       
/*  95 */       addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).bounds(this.width / 2 - 100, this.height / 4 + 120 + 24, 200, 20).build());
/*     */       
/*  97 */       this.packList = (PackList)addRenderableWidget((GuiEventListener)new PackList(this.minecraft, ConfirmExperimentalFeaturesScreen.this.enabledPacks));
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 102 */       super.render($$0, $$1, $$2, $$3);
/* 103 */       $$0.drawCenteredString(this.font, this.title, this.width / 2, 10, 16777215);
/*     */     }
/*     */     
/*     */     private class PackList extends ObjectSelectionList<PackListEntry> {
/*     */       public PackList(Minecraft $$0, Collection<Pack> $$1) {
/* 108 */         super($$0, ConfirmExperimentalFeaturesScreen.DetailsScreen.this.width, ConfirmExperimentalFeaturesScreen.DetailsScreen.this.height - 96, 32, (9 + 2) * 3);
/*     */         
/* 110 */         for (Pack $$2 : $$1) {
/* 111 */           String $$3 = FeatureFlags.printMissingFlags(FeatureFlags.VANILLA_SET, $$2.getRequestedFeatures());
/* 112 */           if (!$$3.isEmpty()) {
/* 113 */             MutableComponent mutableComponent1 = ComponentUtils.mergeStyles($$2.getTitle().copy(), Style.EMPTY.withBold(Boolean.valueOf(true)));
/* 114 */             MutableComponent mutableComponent2 = Component.translatable("selectWorld.experimental.details.entry", new Object[] { $$3 });
/* 115 */             addEntry((AbstractSelectionList.Entry)new ConfirmExperimentalFeaturesScreen.DetailsScreen.PackListEntry((Component)mutableComponent1, (Component)mutableComponent2, MultiLineLabel.create(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.font, (FormattedText)mutableComponent2, getRowWidth())));
/*     */           } 
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public int getRowWidth() {
/* 122 */         return this.width * 3 / 4;
/*     */       }
/*     */     }
/*     */     
/*     */     private class PackListEntry extends ObjectSelectionList.Entry<PackListEntry> {
/*     */       private final Component packId;
/*     */       private final Component message;
/*     */       private final MultiLineLabel splitMessage;
/*     */       
/*     */       PackListEntry(Component $$0, Component $$1, MultiLineLabel $$2) {
/* 132 */         this.packId = $$0;
/* 133 */         this.message = $$1;
/* 134 */         this.splitMessage = $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 139 */         $$0.drawString(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.minecraft.font, this.packId, $$3, $$2, 16777215);
/* 140 */         Objects.requireNonNull(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.font); this.splitMessage.renderLeftAligned($$0, $$3, $$2 + 12, 9, 16777215);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 145 */         return (Component)Component.translatable("narrator.select", new Object[] { CommonComponents.joinForNarration(new Component[] { this.packId, this.message }) }); } } } private class PackList extends ObjectSelectionList<DetailsScreen.PackListEntry> { public PackList(Minecraft $$0, Collection<Pack> $$1) { super($$0, ((ConfirmExperimentalFeaturesScreen.DetailsScreen)ConfirmExperimentalFeaturesScreen.this).width, ((ConfirmExperimentalFeaturesScreen.DetailsScreen)ConfirmExperimentalFeaturesScreen.this).height - 96, 32, (9 + 2) * 3); for (Pack $$2 : $$1) { String $$3 = FeatureFlags.printMissingFlags(FeatureFlags.VANILLA_SET, $$2.getRequestedFeatures()); if (!$$3.isEmpty()) { MutableComponent mutableComponent1 = ComponentUtils.mergeStyles($$2.getTitle().copy(), Style.EMPTY.withBold(Boolean.valueOf(true))); MutableComponent mutableComponent2 = Component.translatable("selectWorld.experimental.details.entry", new Object[] { $$3 }); addEntry((AbstractSelectionList.Entry)new ConfirmExperimentalFeaturesScreen.DetailsScreen.PackListEntry((Component)mutableComponent1, (Component)mutableComponent2, MultiLineLabel.create(((ConfirmExperimentalFeaturesScreen.DetailsScreen)ConfirmExperimentalFeaturesScreen.this).font, (FormattedText)mutableComponent2, getRowWidth()))); }  }  } public int getRowWidth() { return this.width * 3 / 4; } } private class PackListEntry extends ObjectSelectionList.Entry<DetailsScreen.PackListEntry> { public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { CommonComponents.joinForNarration(new Component[] { this.packId, this.message }) }); }
/*     */ 
/*     */     
/*     */     private final Component packId;
/*     */     private final Component message;
/*     */     private final MultiLineLabel splitMessage;
/*     */     
/*     */     PackListEntry(Component $$0, Component $$1, MultiLineLabel $$2) {
/*     */       this.packId = $$0;
/*     */       this.message = $$1;
/*     */       this.splitMessage = $$2;
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       $$0.drawString(this.this$1.minecraft.font, this.packId, $$3, $$2, 16777215);
/*     */       Objects.requireNonNull(this.this$1.font);
/*     */       this.splitMessage.renderLeftAligned($$0, $$3, $$2 + 12, 9, 16777215);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\ConfirmExperimentalFeaturesScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */