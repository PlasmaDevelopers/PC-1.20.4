/*     */ package net.minecraft.client.gui.screens.achievement;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectListIterator;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Font;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.components.AbstractSelectionList;
/*     */ import net.minecraft.client.gui.components.Button;
/*     */ import net.minecraft.client.gui.components.ObjectSelectionList;
/*     */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.resources.language.I18n;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.stats.Stat;
/*     */ import net.minecraft.stats.StatType;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.stats.StatsCounter;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ public class StatsScreen extends Screen implements StatsUpdateListener {
/*  36 */   static final ResourceLocation SLOT_SPRITE = new ResourceLocation("container/slot");
/*  37 */   static final ResourceLocation HEADER_SPRITE = new ResourceLocation("statistics/header");
/*  38 */   static final ResourceLocation SORT_UP_SPRITE = new ResourceLocation("statistics/sort_up");
/*  39 */   static final ResourceLocation SORT_DOWN_SPRITE = new ResourceLocation("statistics/sort_down");
/*  40 */   private static final Component PENDING_TEXT = (Component)Component.translatable("multiplayer.downloadingStats");
/*  41 */   static final Component NO_VALUE_DISPLAY = (Component)Component.translatable("stats.none"); protected final Screen lastScreen; private GeneralStatisticsList statsList; ItemStatisticsList itemStatsList; private MobsStatisticsList mobsStatsList; final StatsCounter stats; @Nullable
/*     */   private ObjectSelectionList<?> activeList; private boolean isLoading = true; private static final int SLOT_BG_SIZE = 18; private static final int SLOT_STAT_HEIGHT = 20;
/*     */   private static final int SLOT_BG_X = 1;
/*     */   private static final int SLOT_BG_Y = 1;
/*     */   private static final int SLOT_FG_X = 2;
/*     */   private static final int SLOT_FG_Y = 2;
/*     */   private static final int SLOT_LEFT_INSERT = 40;
/*     */   private static final int SLOT_TEXT_OFFSET = 5;
/*     */   private static final int SORT_NONE = 0;
/*     */   private static final int SORT_DOWN = -1;
/*     */   private static final int SORT_UP = 1;
/*     */   
/*     */   public StatsScreen(Screen $$0, StatsCounter $$1) {
/*  54 */     super((Component)Component.translatable("gui.stats"));
/*     */     
/*  56 */     this.lastScreen = $$0;
/*  57 */     this.stats = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  62 */     this.isLoading = true;
/*  63 */     this.minecraft.getConnection().send((Packet)new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.REQUEST_STATS));
/*     */   }
/*     */   
/*     */   public void initLists() {
/*  67 */     this.statsList = new GeneralStatisticsList(this.minecraft);
/*  68 */     this.itemStatsList = new ItemStatisticsList(this.minecraft);
/*  69 */     this.mobsStatsList = new MobsStatisticsList(this.minecraft);
/*     */   }
/*     */   
/*     */   public void initButtons() {
/*  73 */     addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("stat.generalButton"), $$0 -> setActiveList(this.statsList)).bounds(this.width / 2 - 120, this.height - 52, 80, 20).build());
/*  74 */     Button $$0 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("stat.itemsButton"), $$0 -> setActiveList(this.itemStatsList)).bounds(this.width / 2 - 40, this.height - 52, 80, 20).build());
/*  75 */     Button $$1 = (Button)addRenderableWidget((GuiEventListener)Button.builder((Component)Component.translatable("stat.mobsButton"), $$0 -> setActiveList(this.mobsStatsList)).bounds(this.width / 2 + 40, this.height - 52, 80, 20).build());
/*     */     
/*  77 */     addRenderableWidget((GuiEventListener)Button.builder(CommonComponents.GUI_DONE, $$0 -> this.minecraft.setScreen(this.lastScreen)).bounds(this.width / 2 - 100, this.height - 28, 200, 20).build());
/*     */     
/*  79 */     if (this.itemStatsList.children().isEmpty()) {
/*  80 */       $$0.active = false;
/*     */     }
/*  82 */     if (this.mobsStatsList.children().isEmpty()) {
/*  83 */       $$1.active = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  89 */     if (this.isLoading) {
/*  90 */       renderBackground($$0, $$1, $$2, $$3);
/*  91 */       $$0.drawCenteredString(this.font, PENDING_TEXT, this.width / 2, this.height / 2, 16777215);
/*  92 */       Objects.requireNonNull(this.font); $$0.drawCenteredString(this.font, LOADING_SYMBOLS[(int)(Util.getMillis() / 150L % LOADING_SYMBOLS.length)], this.width / 2, this.height / 2 + 9 * 2, 16777215);
/*     */     } else {
/*  94 */       super.render($$0, $$1, $$2, $$3);
/*  95 */       $$0.drawCenteredString(this.font, this.title, this.width / 2, 20, 16777215);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 101 */     renderDirtBackground($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStatsUpdated() {
/* 106 */     if (this.isLoading) {
/* 107 */       initLists();
/* 108 */       initButtons();
/* 109 */       setActiveList(this.statsList);
/* 110 */       this.isLoading = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 116 */     return !this.isLoading;
/*     */   }
/*     */   
/*     */   public void setActiveList(@Nullable ObjectSelectionList<?> $$0) {
/* 120 */     if (this.activeList != null) {
/* 121 */       removeWidget((GuiEventListener)this.activeList);
/*     */     }
/*     */     
/* 124 */     if ($$0 != null) {
/* 125 */       addRenderableWidget((GuiEventListener)$$0);
/* 126 */       this.activeList = $$0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private class GeneralStatisticsList extends ObjectSelectionList<GeneralStatisticsList.Entry> {
/*     */     public GeneralStatisticsList(Minecraft $$0) {
/* 132 */       super($$0, StatsScreen.this.width, StatsScreen.this.height - 96, 32, 10);
/*     */       
/* 134 */       ObjectArrayList<Stat<ResourceLocation>> $$1 = new ObjectArrayList(Stats.CUSTOM.iterator());
/* 135 */       $$1.sort(Comparator.comparing($$0 -> I18n.get(StatsScreen.getTranslationKey($$0), new Object[0])));
/* 136 */       for (ObjectListIterator<Stat<ResourceLocation>> objectListIterator = $$1.iterator(); objectListIterator.hasNext(); ) { Stat<ResourceLocation> $$2 = objectListIterator.next();
/* 137 */         addEntry((AbstractSelectionList.Entry)new Entry($$2)); }
/*     */     
/*     */     }
/*     */     
/*     */     private class Entry extends ObjectSelectionList.Entry<Entry> {
/*     */       private final Stat<ResourceLocation> stat;
/*     */       private final Component statDisplay;
/*     */       
/*     */       Entry(Stat<ResourceLocation> $$0) {
/* 146 */         this.stat = $$0;
/* 147 */         this.statDisplay = (Component)Component.translatable(StatsScreen.getTranslationKey($$0));
/*     */       }
/*     */       
/*     */       private String getValueText() {
/* 151 */         return this.stat.format(StatsScreen.this.stats.getValue(this.stat));
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 156 */         $$0.drawString(StatsScreen.this.font, this.statDisplay, $$3 + 2, $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192);
/* 157 */         String $$10 = getValueText();
/* 158 */         $$0.drawString(StatsScreen.this.font, $$10, $$3 + 2 + 213 - StatsScreen.this.font.width($$10), $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 163 */         return (Component)Component.translatable("narrator.select", new Object[] { Component.empty().append(this.statDisplay).append(CommonComponents.SPACE).append(getValueText()) }); } } } private class Entry extends ObjectSelectionList.Entry<GeneralStatisticsList.Entry> { private final Stat<ResourceLocation> stat; private final Component statDisplay; Entry(Stat<ResourceLocation> $$0) { this.stat = $$0; this.statDisplay = (Component)Component.translatable(StatsScreen.getTranslationKey($$0)); } private String getValueText() { return this.stat.format(StatsScreen.this.stats.getValue(this.stat)); } public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) { $$0.drawString(StatsScreen.this.font, this.statDisplay, $$3 + 2, $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192); String $$10 = getValueText(); $$0.drawString(StatsScreen.this.font, $$10, $$3 + 2 + 213 - StatsScreen.this.font.width($$10), $$2 + 1, ($$1 % 2 == 0) ? 16777215 : 9474192); } public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { Component.empty().append(this.statDisplay).append(CommonComponents.SPACE).append(getValueText()) }); }
/*     */      }
/*     */ 
/*     */ 
/*     */   
/*     */   static String getTranslationKey(Stat<ResourceLocation> $$0) {
/* 169 */     return "stat." + ((ResourceLocation)$$0.getValue()).toString().replace(':', '.');
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getColumnX(int $$0) {
/* 186 */     return 115 + 40 * $$0;
/*     */   }
/*     */   
/*     */   void blitSlot(GuiGraphics $$0, int $$1, int $$2, Item $$3) {
/* 190 */     blitSlotIcon($$0, $$1 + 1, $$2 + 1, SLOT_SPRITE);
/*     */     
/* 192 */     $$0.renderFakeItem($$3.getDefaultInstance(), $$1 + 2, $$2 + 2);
/*     */   }
/*     */   
/*     */   void blitSlotIcon(GuiGraphics $$0, int $$1, int $$2, ResourceLocation $$3) {
/* 196 */     $$0.blitSprite($$3, $$1, $$2, 0, 18, 18);
/*     */   }
/*     */   
/*     */   private class ItemStatisticsList extends ObjectSelectionList<ItemStatisticsList.ItemRow> {
/*     */     protected final List<StatType<Block>> blockColumns;
/*     */     protected final List<StatType<Item>> itemColumns;
/* 202 */     private final ResourceLocation[] iconSprites = new ResourceLocation[] { new ResourceLocation("statistics/block_mined"), new ResourceLocation("statistics/item_broken"), new ResourceLocation("statistics/item_crafted"), new ResourceLocation("statistics/item_used"), new ResourceLocation("statistics/item_picked_up"), new ResourceLocation("statistics/item_dropped") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     protected int headerPressed = -1;
/* 211 */     protected final Comparator<ItemRow> itemStatSorter = new ItemRowComparator();
/*     */     @Nullable
/*     */     protected StatType<?> sortColumn;
/*     */     protected int sortOrder;
/*     */     
/*     */     public ItemStatisticsList(Minecraft $$0) {
/* 217 */       super($$0, StatsScreen.this.width, StatsScreen.this.height - 96, 32, 20);
/* 218 */       this.blockColumns = Lists.newArrayList();
/* 219 */       this.blockColumns.add(Stats.BLOCK_MINED);
/* 220 */       this.itemColumns = Lists.newArrayList((Object[])new StatType[] { Stats.ITEM_BROKEN, Stats.ITEM_CRAFTED, Stats.ITEM_USED, Stats.ITEM_PICKED_UP, Stats.ITEM_DROPPED });
/*     */       
/* 222 */       setRenderHeader(true, 20);
/*     */       
/* 224 */       Set<Item> $$1 = Sets.newIdentityHashSet();
/* 225 */       for (Item $$2 : BuiltInRegistries.ITEM) {
/* 226 */         boolean $$3 = false;
/*     */         
/* 228 */         for (StatType<Item> $$4 : this.itemColumns) {
/* 229 */           if ($$4.contains($$2) && StatsScreen.this.stats.getValue($$4.get($$2)) > 0) {
/* 230 */             $$3 = true;
/*     */           }
/*     */         } 
/*     */         
/* 234 */         if ($$3) {
/* 235 */           $$1.add($$2);
/*     */         }
/*     */       } 
/*     */       
/* 239 */       for (Block $$5 : BuiltInRegistries.BLOCK) {
/* 240 */         boolean $$6 = false;
/*     */         
/* 242 */         for (StatType<Block> $$7 : this.blockColumns) {
/* 243 */           if ($$7.contains($$5) && StatsScreen.this.stats.getValue($$7.get($$5)) > 0) {
/* 244 */             $$6 = true;
/*     */           }
/*     */         } 
/*     */         
/* 248 */         if ($$6) {
/* 249 */           $$1.add($$5.asItem());
/*     */         }
/*     */       } 
/*     */       
/* 253 */       $$1.remove(Items.AIR);
/* 254 */       for (Item $$8 : $$1) {
/* 255 */         addEntry((AbstractSelectionList.Entry)new ItemRow($$8));
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderHeader(GuiGraphics $$0, int $$1, int $$2) {
/* 261 */       if (!this.minecraft.mouseHandler.isLeftPressed()) {
/* 262 */         this.headerPressed = -1;
/*     */       }
/*     */       
/* 265 */       for (int $$3 = 0; $$3 < this.iconSprites.length; $$3++) {
/* 266 */         ResourceLocation $$4 = (this.headerPressed == $$3) ? StatsScreen.SLOT_SPRITE : StatsScreen.HEADER_SPRITE;
/* 267 */         StatsScreen.this.blitSlotIcon($$0, $$1 + StatsScreen.this.getColumnX($$3) - 18, $$2 + 1, $$4);
/*     */       } 
/*     */       
/* 270 */       if (this.sortColumn != null) {
/* 271 */         int $$5 = StatsScreen.this.getColumnX(getColumnIndex(this.sortColumn)) - 36;
/*     */         
/* 273 */         ResourceLocation $$6 = (this.sortOrder == 1) ? StatsScreen.SORT_UP_SPRITE : StatsScreen.SORT_DOWN_SPRITE;
/* 274 */         StatsScreen.this.blitSlotIcon($$0, $$1 + $$5, $$2 + 1, $$6);
/*     */       } 
/*     */       
/* 277 */       for (int $$7 = 0; $$7 < this.iconSprites.length; $$7++) {
/* 278 */         int $$8 = (this.headerPressed == $$7) ? 1 : 0;
/* 279 */         StatsScreen.this.blitSlotIcon($$0, $$1 + StatsScreen.this.getColumnX($$7) - 18 + $$8, $$2 + 1 + $$8, this.iconSprites[$$7]);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public int getRowWidth() {
/* 285 */       return 375;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getScrollbarPosition() {
/* 290 */       return this.width / 2 + 140;
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean clickedHeader(int $$0, int $$1) {
/* 295 */       this.headerPressed = -1;
/* 296 */       for (int $$2 = 0; $$2 < this.iconSprites.length; $$2++) {
/* 297 */         int $$3 = $$0 - StatsScreen.this.getColumnX($$2);
/* 298 */         if ($$3 >= -36 && $$3 <= 0) {
/* 299 */           this.headerPressed = $$2;
/*     */           break;
/*     */         } 
/*     */       } 
/* 303 */       if (this.headerPressed >= 0) {
/* 304 */         sortByColumn(getColumn(this.headerPressed));
/* 305 */         this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, 1.0F));
/* 306 */         return true;
/*     */       } 
/* 308 */       return super.clickedHeader($$0, $$1);
/*     */     }
/*     */     
/*     */     private StatType<?> getColumn(int $$0) {
/* 312 */       return ($$0 < this.blockColumns.size()) ? this.blockColumns.get($$0) : this.itemColumns.get($$0 - this.blockColumns.size());
/*     */     }
/*     */ 
/*     */     
/*     */     private int getColumnIndex(StatType<?> $$0) {
/* 317 */       int $$1 = this.blockColumns.indexOf($$0);
/* 318 */       if ($$1 >= 0) {
/* 319 */         return $$1;
/*     */       }
/* 321 */       int $$2 = this.itemColumns.indexOf($$0);
/* 322 */       if ($$2 >= 0) {
/* 323 */         return $$2 + this.blockColumns.size();
/*     */       }
/* 325 */       return -1;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void renderDecorations(GuiGraphics $$0, int $$1, int $$2) {
/* 330 */       if ($$2 < getY() || $$2 > getBottom()) {
/*     */         return;
/*     */       }
/*     */       
/* 334 */       ItemRow $$3 = (ItemRow)getHovered();
/* 335 */       int $$4 = (this.width - getRowWidth()) / 2;
/* 336 */       if ($$3 != null) {
/* 337 */         if ($$1 < $$4 + 40 || $$1 > $$4 + 40 + 20) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 342 */         Item $$5 = $$3.getItem();
/* 343 */         $$0.renderTooltip(StatsScreen.this.font, getString($$5), $$1, $$2);
/*     */       } else {
/* 345 */         Component $$6 = null;
/* 346 */         int $$7 = $$1 - $$4;
/* 347 */         for (int $$8 = 0; $$8 < this.iconSprites.length; $$8++) {
/* 348 */           int $$9 = StatsScreen.this.getColumnX($$8);
/* 349 */           if ($$7 >= $$9 - 18 && $$7 <= $$9) {
/* 350 */             $$6 = getColumn($$8).getDisplayName();
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 355 */         if ($$6 != null) {
/* 356 */           $$0.renderTooltip(StatsScreen.this.font, $$6, $$1, $$2);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     protected Component getString(Item $$0) {
/* 362 */       return $$0.getDescription();
/*     */     }
/*     */     
/*     */     protected void sortByColumn(StatType<?> $$0) {
/* 366 */       if ($$0 != this.sortColumn) {
/* 367 */         this.sortColumn = $$0;
/* 368 */         this.sortOrder = -1;
/* 369 */       } else if (this.sortOrder == -1) {
/* 370 */         this.sortOrder = 1;
/*     */       } else {
/* 372 */         this.sortColumn = null;
/* 373 */         this.sortOrder = 0;
/*     */       } 
/*     */       
/* 376 */       children().sort(this.itemStatSorter);
/*     */     }
/*     */     
/*     */     private class ItemRowComparator
/*     */       implements Comparator<ItemRow> {
/*     */       public int compare(StatsScreen.ItemStatisticsList.ItemRow $$0, StatsScreen.ItemStatisticsList.ItemRow $$1) {
/*     */         int $$10, $$11;
/* 383 */         Item $$2 = $$0.getItem();
/* 384 */         Item $$3 = $$1.getItem();
/*     */ 
/*     */ 
/*     */         
/* 388 */         if (StatsScreen.ItemStatisticsList.this.sortColumn == null) {
/* 389 */           int $$4 = 0;
/* 390 */           int $$5 = 0;
/* 391 */         } else if (StatsScreen.ItemStatisticsList.this.blockColumns.contains(StatsScreen.ItemStatisticsList.this.sortColumn)) {
/* 392 */           StatType<Block> $$6 = (StatType)StatsScreen.ItemStatisticsList.this.sortColumn;
/* 393 */           int $$7 = ($$2 instanceof BlockItem) ? StatsScreen.this.stats.getValue($$6, ((BlockItem)$$2).getBlock()) : -1;
/* 394 */           int $$8 = ($$3 instanceof BlockItem) ? StatsScreen.this.stats.getValue($$6, ((BlockItem)$$3).getBlock()) : -1;
/*     */         } else {
/* 396 */           StatType<Item> $$9 = (StatType)StatsScreen.ItemStatisticsList.this.sortColumn;
/* 397 */           $$10 = StatsScreen.this.stats.getValue($$9, $$2);
/* 398 */           $$11 = StatsScreen.this.stats.getValue($$9, $$3);
/*     */         } 
/*     */         
/* 401 */         if ($$10 == $$11) {
/* 402 */           return StatsScreen.ItemStatisticsList.this.sortOrder * Integer.compare(Item.getId($$2), Item.getId($$3));
/*     */         }
/*     */         
/* 405 */         return StatsScreen.ItemStatisticsList.this.sortOrder * Integer.compare($$10, $$11);
/*     */       }
/*     */     }
/*     */     
/*     */     private class ItemRow extends ObjectSelectionList.Entry<ItemRow> {
/*     */       private final Item item;
/*     */       
/*     */       ItemRow(Item $$0) {
/* 413 */         this.item = $$0;
/*     */       }
/*     */       
/*     */       public Item getItem() {
/* 417 */         return this.item;
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 422 */         StatsScreen.this.blitSlot($$0, $$3 + 40, $$2, this.item);
/*     */         
/* 424 */         for (int $$10 = 0; $$10 < StatsScreen.this.itemStatsList.blockColumns.size(); $$10++) {
/*     */           Stat<Block> $$12;
/* 426 */           if (this.item instanceof BlockItem) {
/* 427 */             Stat<Block> $$11 = ((StatType)StatsScreen.this.itemStatsList.blockColumns.get($$10)).get(((BlockItem)this.item).getBlock());
/*     */           } else {
/* 429 */             $$12 = null;
/*     */           } 
/* 431 */           renderStat($$0, $$12, $$3 + StatsScreen.this.getColumnX($$10), $$2, ($$1 % 2 == 0));
/*     */         } 
/* 433 */         for (int $$13 = 0; $$13 < StatsScreen.this.itemStatsList.itemColumns.size(); $$13++) {
/* 434 */           renderStat($$0, ((StatType)StatsScreen.this.itemStatsList.itemColumns.get($$13)).get(this.item), $$3 + StatsScreen.this.getColumnX($$13 + StatsScreen.this.itemStatsList.blockColumns.size()), $$2, ($$1 % 2 == 0));
/*     */         }
/*     */       }
/*     */       
/*     */       protected void renderStat(GuiGraphics $$0, @Nullable Stat<?> $$1, int $$2, int $$3, boolean $$4) {
/* 439 */         Component $$5 = ($$1 == null) ? StatsScreen.NO_VALUE_DISPLAY : (Component)Component.literal($$1.format(StatsScreen.this.stats.getValue($$1)));
/* 440 */         $$0.drawString(StatsScreen.this.font, $$5, $$2 - StatsScreen.this.font.width((FormattedText)$$5), $$3 + 5, $$4 ? 16777215 : 9474192);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 445 */         return (Component)Component.translatable("narrator.select", new Object[] { this.item.getDescription() }); } } } private class ItemRowComparator implements Comparator<ItemStatisticsList.ItemRow> { public int compare(StatsScreen.ItemStatisticsList.ItemRow $$0, StatsScreen.ItemStatisticsList.ItemRow $$1) { int $$10, $$11; Item $$2 = $$0.getItem(); Item $$3 = $$1.getItem(); if (this.this$1.sortColumn == null) { int $$4 = 0; int $$5 = 0; } else if (this.this$1.blockColumns.contains(this.this$1.sortColumn)) { StatType<Block> $$6 = (StatType)this.this$1.sortColumn; int $$7 = ($$2 instanceof BlockItem) ? StatsScreen.this.stats.getValue($$6, ((BlockItem)$$2).getBlock()) : -1; int $$8 = ($$3 instanceof BlockItem) ? StatsScreen.this.stats.getValue($$6, ((BlockItem)$$3).getBlock()) : -1; } else { StatType<Item> $$9 = (StatType)this.this$1.sortColumn; $$10 = StatsScreen.this.stats.getValue($$9, $$2); $$11 = StatsScreen.this.stats.getValue($$9, $$3); }  if ($$10 == $$11) return this.this$1.sortOrder * Integer.compare(Item.getId($$2), Item.getId($$3));  return this.this$1.sortOrder * Integer.compare($$10, $$11); } } private class ItemRow extends ObjectSelectionList.Entry<ItemStatisticsList.ItemRow> { private final Item item; public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { this.item.getDescription() }); }
/*     */     ItemRow(Item $$0) { this.item = $$0; } public Item getItem() { return this.item; } public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) { StatsScreen.this.blitSlot($$0, $$3 + 40, $$2, this.item); for (int $$10 = 0; $$10 < StatsScreen.this.itemStatsList.blockColumns.size(); $$10++) { Stat<Block> $$12; if (this.item instanceof BlockItem) { Stat<Block> $$11 = ((StatType)StatsScreen.this.itemStatsList.blockColumns.get($$10)).get(((BlockItem)this.item).getBlock()); } else { $$12 = null; }
/*     */          renderStat($$0, $$12, $$3 + StatsScreen.this.getColumnX($$10), $$2, ($$1 % 2 == 0)); }
/*     */        for (int $$13 = 0; $$13 < StatsScreen.this.itemStatsList.itemColumns.size(); $$13++)
/*     */         renderStat($$0, ((StatType)StatsScreen.this.itemStatsList.itemColumns.get($$13)).get(this.item), $$3 + StatsScreen.this.getColumnX($$13 + StatsScreen.this.itemStatsList.blockColumns.size()), $$2, ($$1 % 2 == 0));  } protected void renderStat(GuiGraphics $$0, @Nullable Stat<?> $$1, int $$2, int $$3, boolean $$4) { Component $$5 = ($$1 == null) ? StatsScreen.NO_VALUE_DISPLAY : (Component)Component.literal($$1.format(StatsScreen.this.stats.getValue($$1))); $$0.drawString(StatsScreen.this.font, $$5, $$2 - StatsScreen.this.font.width((FormattedText)$$5), $$3 + 5, $$4 ? 16777215 : 9474192); } }
/*     */    private class MobsStatisticsList extends ObjectSelectionList<MobsStatisticsList.MobRow>
/*     */   {
/* 452 */     public MobsStatisticsList(Minecraft $$0) { super($$0, StatsScreen.this.width, StatsScreen.this.height - 96, 32, 9 * 4);
/*     */       
/* 454 */       for (EntityType<?> $$1 : (Iterable<EntityType<?>>)BuiltInRegistries.ENTITY_TYPE) {
/* 455 */         if (StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get($$1)) > 0 || StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get($$1)) > 0)
/* 456 */           addEntry((AbstractSelectionList.Entry)new MobRow($$1)); 
/*     */       }  }
/*     */ 
/*     */     
/*     */     private class MobRow
/*     */       extends ObjectSelectionList.Entry<MobRow> {
/*     */       private final Component mobName;
/*     */       private final Component kills;
/*     */       private final boolean hasKills;
/*     */       private final Component killedBy;
/*     */       private final boolean wasKilledBy;
/*     */       
/*     */       public MobRow(EntityType<?> $$0) {
/* 469 */         this.mobName = $$0.getDescription();
/*     */         
/* 471 */         int $$1 = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get($$0));
/* 472 */         if ($$1 == 0) {
/* 473 */           this.kills = (Component)Component.translatable("stat_type.minecraft.killed.none", new Object[] { this.mobName });
/* 474 */           this.hasKills = false;
/*     */         } else {
/* 476 */           this.kills = (Component)Component.translatable("stat_type.minecraft.killed", new Object[] { Integer.valueOf($$1), this.mobName });
/* 477 */           this.hasKills = true;
/*     */         } 
/*     */         
/* 480 */         int $$2 = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get($$0));
/* 481 */         if ($$2 == 0) {
/* 482 */           this.killedBy = (Component)Component.translatable("stat_type.minecraft.killed_by.none", new Object[] { this.mobName });
/* 483 */           this.wasKilledBy = false;
/*     */         } else {
/* 485 */           this.killedBy = (Component)Component.translatable("stat_type.minecraft.killed_by", new Object[] { this.mobName, Integer.valueOf($$2) });
/* 486 */           this.wasKilledBy = true;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/* 492 */         $$0.drawString(StatsScreen.this.font, this.mobName, $$3 + 2, $$2 + 1, 16777215);
/* 493 */         Objects.requireNonNull(StatsScreen.this.font); $$0.drawString(StatsScreen.this.font, this.kills, $$3 + 2 + 10, $$2 + 1 + 9, this.hasKills ? 9474192 : 6316128);
/* 494 */         Objects.requireNonNull(StatsScreen.this.font); $$0.drawString(StatsScreen.this.font, this.killedBy, $$3 + 2 + 10, $$2 + 1 + 9 * 2, this.wasKilledBy ? 9474192 : 6316128);
/*     */       }
/*     */       
/*     */       public Component getNarration()
/*     */       {
/* 499 */         return (Component)Component.translatable("narrator.select", new Object[] { CommonComponents.joinForNarration(new Component[] { this.kills, this.killedBy }) }); } } } private class MobRow extends ObjectSelectionList.Entry<MobsStatisticsList.MobRow> { private final Component mobName; private final Component kills; private final boolean hasKills; public Component getNarration() { return (Component)Component.translatable("narrator.select", new Object[] { CommonComponents.joinForNarration(new Component[] { this.kills, this.killedBy }) }); }
/*     */ 
/*     */     
/*     */     private final Component killedBy;
/*     */     private final boolean wasKilledBy;
/*     */     
/*     */     public MobRow(EntityType<?> $$0) {
/*     */       this.mobName = $$0.getDescription();
/*     */       int $$1 = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED.get($$0));
/*     */       if ($$1 == 0) {
/*     */         this.kills = (Component)Component.translatable("stat_type.minecraft.killed.none", new Object[] { this.mobName });
/*     */         this.hasKills = false;
/*     */       } else {
/*     */         this.kills = (Component)Component.translatable("stat_type.minecraft.killed", new Object[] { Integer.valueOf($$1), this.mobName });
/*     */         this.hasKills = true;
/*     */       } 
/*     */       int $$2 = StatsScreen.this.stats.getValue(Stats.ENTITY_KILLED_BY.get($$0));
/*     */       if ($$2 == 0) {
/*     */         this.killedBy = (Component)Component.translatable("stat_type.minecraft.killed_by.none", new Object[] { this.mobName });
/*     */         this.wasKilledBy = false;
/*     */       } else {
/*     */         this.killedBy = (Component)Component.translatable("stat_type.minecraft.killed_by", new Object[] { this.mobName, Integer.valueOf($$2) });
/*     */         this.wasKilledBy = true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void render(GuiGraphics $$0, int $$1, int $$2, int $$3, int $$4, int $$5, int $$6, int $$7, boolean $$8, float $$9) {
/*     */       $$0.drawString(StatsScreen.this.font, this.mobName, $$3 + 2, $$2 + 1, 16777215);
/*     */       Objects.requireNonNull(StatsScreen.this.font);
/*     */       $$0.drawString(StatsScreen.this.font, this.kills, $$3 + 2 + 10, $$2 + 1 + 9, this.hasKills ? 9474192 : 6316128);
/*     */       Objects.requireNonNull(StatsScreen.this.font);
/*     */       $$0.drawString(StatsScreen.this.font, this.killedBy, $$3 + 2 + 10, $$2 + 1 + 9 * 2, this.wasKilledBy ? 9474192 : 6316128);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\achievement\StatsScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */