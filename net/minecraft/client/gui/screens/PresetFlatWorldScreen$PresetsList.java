/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import java.util.Set;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.navigation.CommonInputs;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.tags.FlatLevelGeneratorPresetTags;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLevelGeneratorPreset;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PresetsList
/*     */   extends ObjectSelectionList<PresetFlatWorldScreen.PresetsList.Entry>
/*     */ {
/*     */   public PresetsList(RegistryAccess $$0, FeatureFlagSet $$1) {
/* 242 */     super(paramPresetFlatWorldScreen.minecraft, paramPresetFlatWorldScreen.width, paramPresetFlatWorldScreen.height - 117, 80, 24);
/* 243 */     for (Holder<FlatLevelGeneratorPreset> $$2 : (Iterable<Holder<FlatLevelGeneratorPreset>>)$$0.registryOrThrow(Registries.FLAT_LEVEL_GENERATOR_PRESET).getTagOrEmpty(FlatLevelGeneratorPresetTags.VISIBLE)) {
/* 244 */       Set<Block> $$3 = (Set<Block>)((FlatLevelGeneratorPreset)$$2.value()).settings().getLayersInfo().stream().map($$0 -> $$0.getBlockState().getBlock()).filter($$1 -> !$$1.isEnabled($$0)).collect(Collectors.toSet());
/* 245 */       if (!$$3.isEmpty()) {
/* 246 */         PresetFlatWorldScreen.LOGGER.info("Discarding flat world preset {} since it contains experimental blocks {}", $$2.unwrapKey().map($$0 -> $$0.location().toString()).orElse("<unknown>"), $$3); continue;
/*     */       } 
/* 248 */       addEntry((AbstractSelectionList.Entry)new Entry($$2));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable Entry $$0) {
/* 255 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 256 */     PresetFlatWorldScreen.this.updateButtonValidity(($$0 != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 261 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 262 */       return true;
/*     */     }
/* 264 */     if (CommonInputs.selected($$0) && 
/* 265 */       getSelected() != null) {
/* 266 */       ((Entry)getSelected()).select();
/*     */     }
/*     */     
/* 269 */     return false;
/*     */   }
/*     */   
/*     */   public class Entry extends ObjectSelectionList.Entry<Entry> {
/* 273 */     private static final ResourceLocation STATS_ICON_LOCATION = new ResourceLocation("textures/gui/container/stats_icons.png");
/*     */     
/*     */     private final FlatLevelGeneratorPreset preset;
/*     */     private final Component name;
/*     */     
/*     */     public Entry(Holder<FlatLevelGeneratorPreset> $$1) {
/* 279 */       this.preset = (FlatLevelGeneratorPreset)$$1.value();
/* 280 */       this
/*     */         
/* 282 */         .name = $$1.unwrapKey().map($$0 -> Component.translatable($$0.location().toLanguageKey("flat_world_preset"))).orElse(PresetFlatWorldScreen.UNKNOWN_PRESET);
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 287 */       blitSlot($$0, $$3, $$2, (Item)this.preset.displayItem().value());
/* 288 */       $$0.drawString(this.this$1.this$0.font, this.name, $$3 + 18 + 5, $$2 + 6, 16777215, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 293 */       select();
/* 294 */       return true;
/*     */     }
/*     */     
/*     */     void select() {
/* 298 */       PresetFlatWorldScreen.PresetsList.this.setSelected(this);
/* 299 */       this.this$1.this$0.settings = this.preset.settings();
/* 300 */       this.this$1.this$0.export.setValue(PresetFlatWorldScreen.save(this.this$1.this$0.settings));
/* 301 */       this.this$1.this$0.export.moveCursorToStart(false);
/*     */     }
/*     */     
/*     */     private void blitSlot(GuiGraphics $$0, int $$1, int $$2, Item $$3) {
/* 305 */       blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */       
/* 307 */       $$0.renderFakeItem(new ItemStack((ItemLike)$$3), $$1 + 2, $$2 + 2);
/*     */     }
/*     */     
/*     */     private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) {
/* 311 */       $$0.blitSprite(PresetFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 316 */       return (Component)Component.translatable("narrator.select", new Object[] { this.name });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\PresetFlatWorldScreen$PresetsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */