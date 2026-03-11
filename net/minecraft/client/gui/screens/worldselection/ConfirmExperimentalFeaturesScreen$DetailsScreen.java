/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DetailsScreen
/*     */   extends Screen
/*     */ {
/*     */   private PackList packList;
/*     */   
/*     */   DetailsScreen() {
/*  83 */     super((Component)Component.translatable("selectWorld.experimental.details.title"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/*  88 */     this.minecraft.setScreen(ConfirmExperimentalFeaturesScreen.this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  93 */     super.init();
/*     */     
/*  95 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_BACK, $$0 -> onClose()).bounds(this.width / 2 - 100, this.height / 4 + 120 + 24, 200, 20).build());
/*     */     
/*  97 */     this.packList = (PackList)addRenderableWidget((GuiEventListener)new PackList(this.minecraft, ConfirmExperimentalFeaturesScreen.this.enabledPacks));
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 102 */     super.render($$0, $$1, $$2, $$3);
/* 103 */     $$0.drawCenteredString(this.font, this.title, this.width / 2, 10, 16777215);
/*     */   }
/*     */   
/*     */   private class PackList extends ObjectSelectionList<PackListEntry> {
/*     */     public PackList(Minecraft $$0, Collection<Pack> $$1) {
/* 108 */       super($$0, ConfirmExperimentalFeaturesScreen.DetailsScreen.this.width, ConfirmExperimentalFeaturesScreen.DetailsScreen.this.height - 96, 32, (9 + 2) * 3);
/*     */       
/* 110 */       for (Pack $$2 : $$1) {
/* 111 */         String $$3 = FeatureFlags.printMissingFlags(FeatureFlags.VANILLA_SET, $$2.getRequestedFeatures());
/* 112 */         if (!$$3.isEmpty()) {
/* 113 */           MutableComponent mutableComponent1 = ComponentUtils.mergeStyles($$2.getTitle().copy(), Style.EMPTY.withBold(Boolean.valueOf(true)));
/* 114 */           MutableComponent mutableComponent2 = Component.translatable("selectWorld.experimental.details.entry", new Object[] { $$3 });
/* 115 */           addEntry((AbstractSelectionList.Entry)new ConfirmExperimentalFeaturesScreen.DetailsScreen.PackListEntry((Component)mutableComponent1, (Component)mutableComponent2, MultiLineLabel.create(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.font, (FormattedText)mutableComponent2, getRowWidth())));
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 122 */       return this.width * 3 / 4;
/*     */     }
/*     */   }
/*     */   
/*     */   private class PackListEntry extends ObjectSelectionList.Entry<PackListEntry> {
/*     */     private final Component packId;
/*     */     private final Component message;
/*     */     private final MultiLineLabel splitMessage;
/*     */     
/*     */     PackListEntry(Component $$0, Component $$1, MultiLineLabel $$2) {
/* 132 */       this.packId = $$0;
/* 133 */       this.message = $$1;
/* 134 */       this.splitMessage = $$2;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 139 */       $$0.drawString(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.minecraft.font, this.packId, $$3, $$2, 16777215);
/* 140 */       Objects.requireNonNull(ConfirmExperimentalFeaturesScreen.DetailsScreen.this.font); this.splitMessage.renderLeftAligned($$0, $$3, $$2 + 12, 9, 16777215);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 145 */       return (Component)Component.translatable("narrator.select", new Object[] { CommonComponents.joinForNarration(new Component[] { this.packId, this.message }) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\ConfirmExperimentalFeaturesScreen$DetailsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */