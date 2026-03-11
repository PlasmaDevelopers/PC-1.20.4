/*     */ package net.minecraft.client.gui.screens.worldselection;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.MultiLineLabel;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
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
/*     */ class PackList
/*     */   extends ObjectSelectionList<ConfirmExperimentalFeaturesScreen.DetailsScreen.PackListEntry>
/*     */ {
/*     */   public PackList(Minecraft $$0, Collection<Pack> $$1) {
/* 108 */     super($$0, paramDetailsScreen.width, paramDetailsScreen.height - 96, 32, (9 + 2) * 3);
/*     */     
/* 110 */     for (Pack $$2 : $$1) {
/* 111 */       String $$3 = FeatureFlags.printMissingFlags(FeatureFlags.VANILLA_SET, $$2.getRequestedFeatures());
/* 112 */       if (!$$3.isEmpty()) {
/* 113 */         MutableComponent mutableComponent1 = ComponentUtils.mergeStyles($$2.getTitle().copy(), Style.EMPTY.withBold(Boolean.valueOf(true)));
/* 114 */         MutableComponent mutableComponent2 = Component.translatable("selectWorld.experimental.details.entry", new Object[] { $$3 });
/* 115 */         addEntry((AbstractSelectionList.Entry)new ConfirmExperimentalFeaturesScreen.DetailsScreen.PackListEntry(paramDetailsScreen, (Component)mutableComponent1, (Component)mutableComponent2, MultiLineLabel.create(ConfirmExperimentalFeaturesScreen.DetailsScreen.access$000(paramDetailsScreen), (FormattedText)mutableComponent2, getRowWidth())));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 122 */     return this.width * 3 / 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\worldselection\ConfirmExperimentalFeaturesScreen$DetailsScreen$PackList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */