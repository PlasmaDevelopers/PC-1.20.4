/*     */ package net.minecraft.client.gui.screens.achievement;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.stats.Stat;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class ItemStatisticsList
/*     */   extends ObjectSelectionList<StatsScreen.ItemStatisticsList.ItemRow>
/*     */ {
/*     */   protected final List<StatType<Block>> blockColumns;
/*     */   protected final List<StatType<Item>> itemColumns;
/* 202 */   private final ResourceLocation[] iconSprites = new ResourceLocation[] { new ResourceLocation("statistics/block_mined"), new ResourceLocation("statistics/item_broken"), new ResourceLocation("statistics/item_crafted"), new ResourceLocation("statistics/item_used"), new ResourceLocation("statistics/item_picked_up"), new ResourceLocation("statistics/item_dropped") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 210 */   protected int headerPressed = -1;
/* 211 */   protected final Comparator<ItemRow> itemStatSorter = new ItemRowComparator();
/*     */   @Nullable
/*     */   protected StatType<?> sortColumn;
/*     */   protected int sortOrder;
/*     */   
/*     */   public ItemStatisticsList(Minecraft $$0) {
/* 217 */     super($$0, paramStatsScreen.width, paramStatsScreen.height - 96, 32, 20);
/* 218 */     this.blockColumns = Lists.newArrayList();
/* 219 */     this.blockColumns.add(Stats.BLOCK_MINED);
/* 220 */     this.itemColumns = Lists.newArrayList((Object[])new StatType[] { Stats.ITEM_BROKEN, Stats.ITEM_CRAFTED, Stats.ITEM_USED, Stats.ITEM_PICKED_UP, Stats.ITEM_DROPPED });
/*     */     
/* 222 */     setRenderHeader(true, 20);
/*     */     
/* 224 */     Set<Item> $$1 = Sets.newIdentityHashSet();
/* 225 */     for (Item $$2 : BuiltInRegistries.ITEM) {
/* 226 */       boolean $$3 = false;
/*     */       
/* 228 */       for (StatType<Item> $$4 : this.itemColumns) {
/* 229 */         if ($$4.contains($$2) && paramStatsScreen.stats.getValue($$4.get($$2)) > 0) {
/* 230 */           $$3 = true;
/*     */         }
/*     */       } 
/*     */       
/* 234 */       if ($$3) {
/* 235 */         $$1.add($$2);
/*     */       }
/*     */     } 
/*     */     
/* 239 */     for (Block $$5 : BuiltInRegistries.BLOCK) {
/* 240 */       boolean $$6 = false;
/*     */       
/* 242 */       for (StatType<Block> $$7 : this.blockColumns) {
/* 243 */         if ($$7.contains($$5) && paramStatsScreen.stats.getValue($$7.get($$5)) > 0) {
/* 244 */           $$6 = true;
/*     */         }
/*     */       } 
/*     */       
/* 248 */       if ($$6) {
/* 249 */         $$1.add($$5.asItem());
/*     */       }
/*     */     } 
/*     */     
/* 253 */     $$1.remove(Items.AIR);
/* 254 */     for (Item $$8 : $$1) {
/* 255 */       addEntry((AbstractSelectionList.Entry)new ItemRow($$8));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderHeader(GuiGraphics $$0, int $$1, int $$2) {
/* 261 */     if (!this.minecraft.mouseHandler.isLeftPressed()) {
/* 262 */       this.headerPressed = -1;
/*     */     }
/*     */     
/* 265 */     for (int $$3 = 0; $$3 < this.iconSprites.length; $$3++) {
/* 266 */       ResourceLocation $$4 = (this.headerPressed == $$3) ? StatsScreen.SLOT_SPRITE : StatsScreen.HEADER_SPRITE;
/* 267 */       StatsScreen.this.blitSlotIcon($$0, $$1 + StatsScreen.this.getColumnX($$3) - 18, $$2 + 1, $$4);
/*     */     } 
/*     */     
/* 270 */     if (this.sortColumn != null) {
/* 271 */       int $$5 = StatsScreen.this.getColumnX(getColumnIndex(this.sortColumn)) - 36;
/*     */       
/* 273 */       ResourceLocation $$6 = (this.sortOrder == 1) ? StatsScreen.SORT_UP_SPRITE : StatsScreen.SORT_DOWN_SPRITE;
/* 274 */       StatsScreen.this.blitSlotIcon($$0, $$1 + $$5, $$2 + 1, $$6);
/*     */     } 
/*     */     
/* 277 */     for (int $$7 = 0; $$7 < this.iconSprites.length; $$7++) {
/* 278 */       int $$8 = (this.headerPressed == $$7) ? 1 : 0;
/* 279 */       StatsScreen.this.blitSlotIcon($$0, $$1 + StatsScreen.this.getColumnX($$7) - 18 + $$8, $$2 + 1 + $$8, this.iconSprites[$$7]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRowWidth() {
/* 285 */     return 375;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollbarPosition() {
/* 290 */     return this.width / 2 + 140;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean clickedHeader(int $$0, int $$1) {
/* 295 */     this.headerPressed = -1;
/* 296 */     for (int $$2 = 0; $$2 < this.iconSprites.length; $$2++) {
/* 297 */       int $$3 = $$0 - StatsScreen.this.getColumnX($$2);
/* 298 */       if ($$3 >= -36 && $$3 <= 0) {
/* 299 */         this.headerPressed = $$2;
/*     */         break;
/*     */       } 
/*     */     } 
/* 303 */     if (this.headerPressed >= 0) {
/* 304 */       sortByColumn(getColumn(this.headerPressed));
/* 305 */       this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 306 */       return true;
/*     */     } 
/* 308 */     return super.clickedHeader($$0, $$1);
/*     */   }
/*     */   
/*     */   private StatType<?> getColumn(int $$0) {
/* 312 */     return ($$0 < this.blockColumns.size()) ? this.blockColumns.get($$0) : this.itemColumns.get($$0 - this.blockColumns.size());
/*     */   }
/*     */ 
/*     */   
/*     */   private int getColumnIndex(StatType<?> $$0) {
/* 317 */     int $$1 = this.blockColumns.indexOf($$0);
/* 318 */     if ($$1 >= 0) {
/* 319 */       return $$1;
/*     */     }
/* 321 */     int $$2 = this.itemColumns.indexOf($$0);
/* 322 */     if ($$2 >= 0) {
/* 323 */       return $$2 + this.blockColumns.size();
/*     */     }
/* 325 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderDecorations(GuiGraphics $$0, int $$1, int $$2) {
/* 330 */     if ($$2 < getY() || $$2 > getBottom()) {
/*     */       return;
/*     */     }
/*     */     
/* 334 */     ItemRow $$3 = (ItemRow)getHovered();
/* 335 */     int $$4 = (this.width - getRowWidth()) / 2;
/* 336 */     if ($$3 != null) {
/* 337 */       if ($$1 < $$4 + 40 || $$1 > $$4 + 40 + 20) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 342 */       Item $$5 = $$3.getItem();
/* 343 */       $$0.renderTooltip(StatsScreen.access$300(StatsScreen.this), getString($$5), $$1, $$2);
/*     */     } else {
/* 345 */       Component $$6 = null;
/* 346 */       int $$7 = $$1 - $$4;
/* 347 */       for (int $$8 = 0; $$8 < this.iconSprites.length; $$8++) {
/* 348 */         int $$9 = StatsScreen.this.getColumnX($$8);
/* 349 */         if ($$7 >= $$9 - 18 && $$7 <= $$9) {
/* 350 */           $$6 = getColumn($$8).getDisplayName();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 355 */       if ($$6 != null) {
/* 356 */         $$0.renderTooltip(StatsScreen.access$400(StatsScreen.this), $$6, $$1, $$2);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Component getString(Item $$0) {
/* 362 */     return $$0.getDescription();
/*     */   }
/*     */   
/*     */   protected void sortByColumn(StatType<?> $$0) {
/* 366 */     if ($$0 != this.sortColumn) {
/* 367 */       this.sortColumn = $$0;
/* 368 */       this.sortOrder = -1;
/* 369 */     } else if (this.sortOrder == -1) {
/* 370 */       this.sortOrder = 1;
/*     */     } else {
/* 372 */       this.sortColumn = null;
/* 373 */       this.sortOrder = 0;
/*     */     } 
/*     */     
/* 376 */     children().sort(this.itemStatSorter);
/*     */   }
/*     */   
/*     */   private class ItemRowComparator
/*     */     implements Comparator<ItemRow> {
/*     */     public int compare(StatsScreen.ItemStatisticsList.ItemRow $$0, StatsScreen.ItemStatisticsList.ItemRow $$1) {
/*     */       int $$10, $$11;
/* 383 */       Item $$2 = $$0.getItem();
/* 384 */       Item $$3 = $$1.getItem();
/*     */ 
/*     */ 
/*     */       
/* 388 */       if (StatsScreen.ItemStatisticsList.this.sortColumn == null) {
/* 389 */         int $$4 = 0;
/* 390 */         int $$5 = 0;
/* 391 */       } else if (StatsScreen.ItemStatisticsList.this.blockColumns.contains(StatsScreen.ItemStatisticsList.this.sortColumn)) {
/* 392 */         StatType<Block> $$6 = (StatType)StatsScreen.ItemStatisticsList.this.sortColumn;
/* 393 */         int $$7 = ($$2 instanceof BlockItem) ? this.this$1.this$0.stats.getValue($$6, ((BlockItem)$$2).getBlock()) : -1;
/* 394 */         int $$8 = ($$3 instanceof BlockItem) ? this.this$1.this$0.stats.getValue($$6, ((BlockItem)$$3).getBlock()) : -1;
/*     */       } else {
/* 396 */         StatType<Item> $$9 = (StatType)StatsScreen.ItemStatisticsList.this.sortColumn;
/* 397 */         $$10 = this.this$1.this$0.stats.getValue($$9, $$2);
/* 398 */         $$11 = this.this$1.this$0.stats.getValue($$9, $$3);
/*     */       } 
/*     */       
/* 401 */       if ($$10 == $$11) {
/* 402 */         return StatsScreen.ItemStatisticsList.this.sortOrder * Integer.compare(Item.getId($$2), Item.getId($$3));
/*     */       }
/*     */       
/* 405 */       return StatsScreen.ItemStatisticsList.this.sortOrder * Integer.compare($$10, $$11);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ItemRow extends ObjectSelectionList.Entry<ItemRow> {
/*     */     private final Item item;
/*     */     
/*     */     ItemRow(Item $$0) {
/* 413 */       this.item = $$0;
/*     */     }
/*     */     
/*     */     public Item getItem() {
/* 417 */       return this.item;
/*     */     }
/*     */ 
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 422 */       this.this$1.this$0.blitSlot($$0, $$3 + 40, $$2, this.item);
/*     */       
/* 424 */       for (int $$10 = 0; $$10 < this.this$1.this$0.itemStatsList.blockColumns.size(); $$10++) {
/*     */         Stat<Block> $$12;
/* 426 */         if (this.item instanceof BlockItem) {
/* 427 */           Stat<Block> $$11 = ((StatType)this.this$1.this$0.itemStatsList.blockColumns.get($$10)).get(((BlockItem)this.item).getBlock());
/*     */         } else {
/* 429 */           $$12 = null;
/*     */         } 
/* 431 */         renderStat($$0, $$12, $$3 + this.this$1.this$0.getColumnX($$10), $$2, ($$1 % 2 == 0));
/*     */       } 
/* 433 */       for (int $$13 = 0; $$13 < this.this$1.this$0.itemStatsList.itemColumns.size(); $$13++) {
/* 434 */         renderStat($$0, ((StatType)this.this$1.this$0.itemStatsList.itemColumns.get($$13)).get(this.item), $$3 + this.this$1.this$0.getColumnX($$13 + this.this$1.this$0.itemStatsList.blockColumns.size()), $$2, ($$1 % 2 == 0));
/*     */       }
/*     */     }
/*     */     
/*     */     protected void renderStat(GuiGraphics $$0, @Nullable Stat<?> $$1, int $$2, int $$3, boolean $$4) {
/* 439 */       Component $$5 = ($$1 == null) ? StatsScreen.NO_VALUE_DISPLAY : (Component)Component.literal($$1.format(this.this$1.this$0.stats.getValue($$1)));
/* 440 */       $$0.drawString(StatsScreen.access$500(this.this$1.this$0), $$5, $$2 - StatsScreen.access$600(this.this$1.this$0).width((FormattedText)$$5), $$3 + 5, $$4 ? 16777215 : 9474192);
/*     */     }
/*     */ 
/*     */     
/*     */     public Component getNarration() {
/* 445 */       return (Component)Component.translatable("narrator.select", new Object[] { this.item.getDescription() });
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\achievement\StatsScreen$ItemStatisticsList.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */