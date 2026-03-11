/*     */ package net.minecraft.client.gui.screens;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.levelgen.flat.FlatLayerInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class DetailsList
/*     */   extends ObjectSelectionList<CreateFlatWorldScreen.DetailsList.Entry>
/*     */ {
/*     */   public DetailsList() {
/* 122 */     super(paramCreateFlatWorldScreen.minecraft, paramCreateFlatWorldScreen.width, paramCreateFlatWorldScreen.height - 103, 43, 24);
/*     */     
/* 124 */     for (int $$0 = 0; $$0 < paramCreateFlatWorldScreen.generator.getLayersInfo().size(); $$0++) {
/* 125 */       addEntry((AbstractSelectionList.Entry)new Entry());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(@Nullable Entry $$0) {
/* 131 */     super.setSelected((AbstractSelectionList.Entry)$$0);
/* 132 */     CreateFlatWorldScreen.this.updateButtonValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 137 */     return this.width - 70;
/*     */   }
/*     */   
/*     */   public void resetRows() {
/* 141 */     int $$0 = children().indexOf(getSelected());
/* 142 */     clearEntries();
/* 143 */     for (int $$1 = 0; $$1 < CreateFlatWorldScreen.this.generator.getLayersInfo().size(); $$1++) {
/* 144 */       addEntry((AbstractSelectionList.Entry)new Entry());
/*     */     }
/*     */     
/* 147 */     List<Entry> $$2 = children();
/* 148 */     if ($$0 >= 0 && $$0 < $$2.size())
/* 149 */       setSelected($$2.get($$0)); 
/*     */   }
/*     */   
/*     */   private class Entry
/*     */     extends ObjectSelectionList.Entry<Entry> {
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       MutableComponent mutableComponent;
/* 156 */       FlatLayerInfo $$10 = this.this$1.this$0.generator.getLayersInfo().get(this.this$1.this$0.generator.getLayersInfo().size() - $$1 - 1);
/* 157 */       BlockState $$11 = $$10.getBlockState();
/*     */       
/* 159 */       ItemStack $$12 = getDisplayItem($$11);
/* 160 */       blitSlot($$0, $$3, $$2, $$12);
/* 161 */       $$0.drawString(this.this$1.this$0.font, $$12.getHoverName(), $$3 + 18 + 5, $$2 + 3, 16777215, false);
/*     */ 
/*     */ 
/*     */       
/* 165 */       if ($$1 == 0) {
/* 166 */         mutableComponent = Component.translatable("createWorld.customize.flat.layer.top", new Object[] { Integer.valueOf($$10.getHeight()) });
/* 167 */       } else if ($$1 == this.this$1.this$0.generator.getLayersInfo().size() - 1) {
/* 168 */         mutableComponent = Component.translatable("createWorld.customize.flat.layer.bottom", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */       } else {
/* 170 */         mutableComponent = Component.translatable("createWorld.customize.flat.layer", new Object[] { Integer.valueOf($$10.getHeight()) });
/*     */       } 
/*     */       
/* 173 */       $$0.drawString(this.this$1.this$0.font, (Component)mutableComponent, $$3 + 2 + 213 - this.this$1.this$0.font.width((FormattedText)mutableComponent), $$2 + 3, 16777215, false);
/*     */     }
/*     */     
/*     */     private ItemStack getDisplayItem(BlockState $$0) {
/* 177 */       Item $$1 = $$0.getBlock().asItem();
/* 178 */       if ($$1 == Items.AIR) {
/* 179 */         if ($$0.is(Blocks.WATER)) {
/* 180 */           $$1 = Items.WATER_BUCKET;
/* 181 */         } else if ($$0.is(Blocks.LAVA)) {
/* 182 */           $$1 = Items.LAVA_BUCKET;
/*     */         } 
/*     */       }
/* 185 */       return new ItemStack((ItemLike)$$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 190 */       FlatLayerInfo $$0 = this.this$1.this$0.generator.getLayersInfo().get(this.this$1.this$0.generator.getLayersInfo().size() - CreateFlatWorldScreen.DetailsList.this.children().indexOf(this) - 1);
/* 191 */       ItemStack $$1 = getDisplayItem($$0.getBlockState());
/* 192 */       if (!$$1.isEmpty()) {
/* 193 */         return (Component)Component.translatable("narrator.select", new Object[] { $$1.getHoverName() });
/*     */       }
/* 195 */       return CommonComponents.EMPTY;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 201 */       CreateFlatWorldScreen.DetailsList.this.setSelected(this);
/* 202 */       return true;
/*     */     }
/*     */     
/*     */     private void blitSlot(GuiGraphics $$0, int $$1, int $$2, ItemStack $$3) {
/* 206 */       blitSlotBg($$0, $$1 + 1, $$2 + 1);
/*     */       
/* 208 */       if (!$$3.isEmpty()) {
/* 209 */         $$0.renderFakeItem($$3, $$1 + 2, $$2 + 2);
/*     */       }
/*     */     }
/*     */     
/*     */     private void blitSlotBg(GuiGraphics $$0, int $$1, int $$2) {
/* 214 */       $$0.blitSprite(CreateFlatWorldScreen.SLOT_SPRITE, $$1, $$2, 0, 18, 18);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\CreateFlatWorldScreen$DetailsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */