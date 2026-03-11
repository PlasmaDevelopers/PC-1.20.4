/*      */ package net.minecraft.client.gui.screens.inventory;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.mojang.blaze3d.platform.InputConstants;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ import java.util.function.Predicate;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.client.HotbarManager;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiGraphics;
/*      */ import net.minecraft.client.gui.components.EditBox;
/*      */ import net.minecraft.client.gui.components.events.GuiEventListener;
/*      */ import net.minecraft.client.player.LocalPlayer;
/*      */ import net.minecraft.client.player.inventory.Hotbar;
/*      */ import net.minecraft.client.searchtree.SearchRegistry;
/*      */ import net.minecraft.client.searchtree.SearchTree;
/*      */ import net.minecraft.core.HolderLookup;
/*      */ import net.minecraft.core.NonNullList;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.Container;
/*      */ import net.minecraft.world.SimpleContainer;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.player.Inventory;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*      */ import net.minecraft.world.inventory.ClickType;
/*      */ import net.minecraft.world.inventory.InventoryMenu;
/*      */ import net.minecraft.world.inventory.Slot;
/*      */ import net.minecraft.world.item.CreativeModeTab;
/*      */ import net.minecraft.world.item.CreativeModeTabs;
/*      */ import net.minecraft.world.item.Item;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.TooltipFlag;
/*      */ 
/*      */ public class CreativeModeInventoryScreen extends EffectRenderingInventoryScreen<CreativeModeInventoryScreen.ItemPickerMenu> {
/*   50 */   private static final ResourceLocation SCROLLER_SPRITE = new ResourceLocation("container/creative_inventory/scroller");
/*   51 */   private static final ResourceLocation SCROLLER_DISABLED_SPRITE = new ResourceLocation("container/creative_inventory/scroller_disabled");
/*      */   
/*   53 */   private static final ResourceLocation[] UNSELECTED_TOP_TABS = new ResourceLocation[] { new ResourceLocation("container/creative_inventory/tab_top_unselected_1"), new ResourceLocation("container/creative_inventory/tab_top_unselected_2"), new ResourceLocation("container/creative_inventory/tab_top_unselected_3"), new ResourceLocation("container/creative_inventory/tab_top_unselected_4"), new ResourceLocation("container/creative_inventory/tab_top_unselected_5"), new ResourceLocation("container/creative_inventory/tab_top_unselected_6"), new ResourceLocation("container/creative_inventory/tab_top_unselected_7") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   62 */   private static final ResourceLocation[] SELECTED_TOP_TABS = new ResourceLocation[] { new ResourceLocation("container/creative_inventory/tab_top_selected_1"), new ResourceLocation("container/creative_inventory/tab_top_selected_2"), new ResourceLocation("container/creative_inventory/tab_top_selected_3"), new ResourceLocation("container/creative_inventory/tab_top_selected_4"), new ResourceLocation("container/creative_inventory/tab_top_selected_5"), new ResourceLocation("container/creative_inventory/tab_top_selected_6"), new ResourceLocation("container/creative_inventory/tab_top_selected_7") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   71 */   private static final ResourceLocation[] UNSELECTED_BOTTOM_TABS = new ResourceLocation[] { new ResourceLocation("container/creative_inventory/tab_bottom_unselected_1"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_2"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_3"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_4"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_5"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_6"), new ResourceLocation("container/creative_inventory/tab_bottom_unselected_7") };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   80 */   private static final ResourceLocation[] SELECTED_BOTTOM_TABS = new ResourceLocation[] { new ResourceLocation("container/creative_inventory/tab_bottom_selected_1"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_2"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_3"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_4"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_5"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_6"), new ResourceLocation("container/creative_inventory/tab_bottom_selected_7") };
/*      */ 
/*      */   
/*      */   private static final String GUI_CREATIVE_TAB_PREFIX = "textures/gui/container/creative_inventory/tab_";
/*      */   
/*      */   private static final String CUSTOM_SLOT_LOCK = "CustomCreativeLock";
/*      */   
/*      */   private static final int NUM_ROWS = 5;
/*      */   
/*      */   private static final int NUM_COLS = 9;
/*      */   
/*      */   private static final int TAB_WIDTH = 26;
/*      */   
/*      */   private static final int TAB_HEIGHT = 32;
/*      */   
/*      */   private static final int SCROLLER_WIDTH = 12;
/*      */   
/*      */   private static final int SCROLLER_HEIGHT = 15;
/*      */   
/*   99 */   static final SimpleContainer CONTAINER = new SimpleContainer(45);
/*  100 */   private static final Component TRASH_SLOT_TOOLTIP = (Component)Component.translatable("inventory.binSlot");
/*      */   
/*      */   private static final int TEXT_COLOR = 16777215;
/*  103 */   private static CreativeModeTab selectedTab = CreativeModeTabs.getDefaultTab();
/*      */   
/*      */   private float scrollOffs;
/*      */   private boolean scrolling;
/*      */   private EditBox searchBox;
/*      */   @Nullable
/*      */   private List<Slot> originalSlots;
/*      */   @Nullable
/*      */   private Slot destroyItemSlot;
/*      */   private CreativeInventoryListener listener;
/*      */   private boolean ignoreTextInput;
/*      */   private boolean hasClickedOutside;
/*  115 */   private final Set<TagKey<Item>> visibleTags = new HashSet<>();
/*      */   private final boolean displayOperatorCreativeTab;
/*      */   
/*      */   public static class ItemPickerMenu extends AbstractContainerMenu {
/*  119 */     public final NonNullList<ItemStack> items = NonNullList.create();
/*      */     
/*      */     private final AbstractContainerMenu inventoryMenu;
/*      */     
/*      */     public ItemPickerMenu(Player $$0) {
/*  124 */       super(null, 0);
/*  125 */       this.inventoryMenu = (AbstractContainerMenu)$$0.inventoryMenu;
/*      */       
/*  127 */       Inventory $$1 = $$0.getInventory();
/*  128 */       for (int $$2 = 0; $$2 < 5; $$2++) {
/*  129 */         for (int $$3 = 0; $$3 < 9; $$3++) {
/*  130 */           addSlot(new CreativeModeInventoryScreen.CustomCreativeSlot((Container)CreativeModeInventoryScreen.CONTAINER, $$2 * 9 + $$3, 9 + $$3 * 18, 18 + $$2 * 18));
/*      */         }
/*      */       } 
/*      */       
/*  134 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/*  135 */         addSlot(new Slot((Container)$$1, $$4, 9 + $$4 * 18, 112));
/*      */       }
/*      */       
/*  138 */       scrollTo(0.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean stillValid(Player $$0) {
/*  143 */       return true;
/*      */     }
/*      */     
/*      */     protected int calculateRowCount() {
/*  147 */       return Mth.positiveCeilDiv(this.items.size(), 9) - 5;
/*      */     }
/*      */     
/*      */     protected int getRowIndexForScroll(float $$0) {
/*  151 */       return Math.max((int)(($$0 * calculateRowCount()) + 0.5D), 0);
/*      */     }
/*      */     
/*      */     protected float getScrollForRowIndex(int $$0) {
/*  155 */       return Mth.clamp($$0 / calculateRowCount(), 0.0F, 1.0F);
/*      */     }
/*      */     
/*      */     protected float subtractInputFromScroll(float $$0, double $$1) {
/*  159 */       return Mth.clamp($$0 - (float)($$1 / calculateRowCount()), 0.0F, 1.0F);
/*      */     }
/*      */     
/*      */     public void scrollTo(float $$0) {
/*  163 */       int $$1 = getRowIndexForScroll($$0);
/*  164 */       for (int $$2 = 0; $$2 < 5; $$2++) {
/*  165 */         for (int $$3 = 0; $$3 < 9; $$3++) {
/*  166 */           int $$4 = $$3 + ($$2 + $$1) * 9;
/*  167 */           if ($$4 >= 0 && $$4 < this.items.size()) {
/*  168 */             CreativeModeInventoryScreen.CONTAINER.setItem($$3 + $$2 * 9, (ItemStack)this.items.get($$4));
/*      */           } else {
/*  170 */             CreativeModeInventoryScreen.CONTAINER.setItem($$3 + $$2 * 9, ItemStack.EMPTY);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     public boolean canScroll() {
/*  177 */       return (this.items.size() > 45);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  182 */       if ($$1 >= this.slots.size() - 9 && $$1 < this.slots.size()) {
/*  183 */         Slot $$2 = (Slot)this.slots.get($$1);
/*      */         
/*  185 */         if ($$2 != null && $$2.hasItem()) {
/*  186 */           $$2.setByPlayer(ItemStack.EMPTY);
/*      */         }
/*      */       } 
/*      */       
/*  190 */       return ItemStack.EMPTY;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/*  195 */       return ($$1.container != CreativeModeInventoryScreen.CONTAINER);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean canDragTo(Slot $$0) {
/*  200 */       return ($$0.container != CreativeModeInventoryScreen.CONTAINER);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getCarried() {
/*  205 */       return this.inventoryMenu.getCarried();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setCarried(ItemStack $$0) {
/*  210 */       this.inventoryMenu.setCarried($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public CreativeModeInventoryScreen(Player $$0, FeatureFlagSet $$1, boolean $$2) {
/*  215 */     super(new ItemPickerMenu($$0), $$0.getInventory(), CommonComponents.EMPTY);
/*  216 */     $$0.containerMenu = this.menu;
/*  217 */     this.imageHeight = 136;
/*  218 */     this.imageWidth = 195;
/*  219 */     this.displayOperatorCreativeTab = $$2;
/*  220 */     CreativeModeTabs.tryRebuildTabContents($$1, hasPermissions($$0), (HolderLookup.Provider)$$0.level().registryAccess());
/*      */   }
/*      */   
/*      */   private boolean hasPermissions(Player $$0) {
/*  224 */     return ($$0.canUseGameMasterBlocks() && this.displayOperatorCreativeTab);
/*      */   }
/*      */   
/*      */   private void tryRefreshInvalidatedTabs(FeatureFlagSet $$0, boolean $$1, HolderLookup.Provider $$2) {
/*  228 */     if (CreativeModeTabs.tryRebuildTabContents($$0, $$1, $$2)) {
/*  229 */       for (CreativeModeTab $$3 : CreativeModeTabs.allTabs()) {
/*  230 */         Collection<ItemStack> $$4 = $$3.getDisplayItems();
/*  231 */         if ($$3 == selectedTab) {
/*  232 */           if ($$3.getType() == CreativeModeTab.Type.CATEGORY && $$4.isEmpty()) {
/*  233 */             selectTab(CreativeModeTabs.getDefaultTab()); continue;
/*      */           } 
/*  235 */           refreshCurrentTabContents($$4);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void refreshCurrentTabContents(Collection<ItemStack> $$0) {
/*  243 */     int $$1 = this.menu.getRowIndexForScroll(this.scrollOffs);
/*  244 */     this.menu.items.clear();
/*  245 */     if (selectedTab.getType() == CreativeModeTab.Type.SEARCH) {
/*  246 */       refreshSearchResults();
/*      */     } else {
/*  248 */       this.menu.items.addAll($$0);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  253 */     this.scrollOffs = this.menu.getScrollForRowIndex($$1);
/*  254 */     this.menu.scrollTo(this.scrollOffs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void containerTick() {
/*  259 */     super.containerTick();
/*      */     
/*  261 */     if (this.minecraft == null) {
/*      */       return;
/*      */     }
/*      */     
/*  265 */     if (this.minecraft.player != null) {
/*  266 */       tryRefreshInvalidatedTabs(this.minecraft.player.connection.enabledFeatures(), hasPermissions((Player)this.minecraft.player), (HolderLookup.Provider)this.minecraft.player.level().registryAccess());
/*      */     }
/*      */     
/*  269 */     if (!this.minecraft.gameMode.hasInfiniteItems()) {
/*  270 */       this.minecraft.setScreen(new InventoryScreen((Player)this.minecraft.player));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void slotClicked(@Nullable Slot $$0, int $$1, int $$2, ClickType $$3) {
/*  276 */     if (isCreativeSlot($$0)) {
/*  277 */       this.searchBox.moveCursorToEnd(false);
/*  278 */       this.searchBox.setHighlightPos(0);
/*      */     } 
/*      */     
/*  281 */     boolean $$4 = ($$3 == ClickType.QUICK_MOVE);
/*  282 */     $$3 = ($$1 == -999 && $$3 == ClickType.PICKUP) ? ClickType.THROW : $$3;
/*      */     
/*  284 */     if ($$0 != null || selectedTab.getType() == CreativeModeTab.Type.INVENTORY || $$3 == ClickType.QUICK_CRAFT) {
/*  285 */       if ($$0 != null && !$$0.mayPickup((Player)this.minecraft.player)) {
/*      */         return;
/*      */       }
/*  288 */       if ($$0 == this.destroyItemSlot && $$4) {
/*  289 */         for (int $$5 = 0; $$5 < this.minecraft.player.inventoryMenu.getItems().size(); $$5++) {
/*  290 */           this.minecraft.gameMode.handleCreativeModeItemAdd(ItemStack.EMPTY, $$5);
/*      */         }
/*  292 */       } else if (selectedTab.getType() == CreativeModeTab.Type.INVENTORY) {
/*      */         
/*  294 */         if ($$0 == this.destroyItemSlot) {
/*  295 */           this.menu.setCarried(ItemStack.EMPTY);
/*  296 */         } else if ($$3 == ClickType.THROW && $$0 != null && $$0.hasItem()) {
/*  297 */           ItemStack $$6 = $$0.remove(($$2 == 0) ? 1 : $$0.getItem().getMaxStackSize());
/*  298 */           ItemStack $$7 = $$0.getItem();
/*  299 */           this.minecraft.player.drop($$6, true);
/*  300 */           this.minecraft.gameMode.handleCreativeModeItemDrop($$6);
/*      */           
/*  302 */           this.minecraft.gameMode.handleCreativeModeItemAdd($$7, ((SlotWrapper)$$0).target.index);
/*  303 */         } else if ($$3 == ClickType.THROW && !this.menu.getCarried().isEmpty()) {
/*  304 */           this.minecraft.player.drop(this.menu.getCarried(), true);
/*  305 */           this.minecraft.gameMode.handleCreativeModeItemDrop(this.menu.getCarried());
/*  306 */           this.menu.setCarried(ItemStack.EMPTY);
/*      */         } else {
/*  308 */           this.minecraft.player.inventoryMenu.clicked(($$0 == null) ? $$1 : ((SlotWrapper)$$0).target.index, $$2, $$3, (Player)this.minecraft.player);
/*  309 */           this.minecraft.player.inventoryMenu.broadcastChanges();
/*      */         }
/*      */       
/*  312 */       } else if ($$3 != ClickType.QUICK_CRAFT && $$0.container == CONTAINER) {
/*      */         
/*  314 */         ItemStack $$8 = this.menu.getCarried();
/*  315 */         ItemStack $$9 = $$0.getItem();
/*      */         
/*  317 */         if ($$3 == ClickType.SWAP) {
/*  318 */           if (!$$9.isEmpty()) {
/*  319 */             this.minecraft.player.getInventory().setItem($$2, $$9.copyWithCount($$9.getMaxStackSize()));
/*  320 */             this.minecraft.player.inventoryMenu.broadcastChanges();
/*      */           } 
/*      */           return;
/*      */         } 
/*  324 */         if ($$3 == ClickType.CLONE) {
/*  325 */           if (this.menu.getCarried().isEmpty() && $$0.hasItem()) {
/*  326 */             ItemStack $$10 = $$0.getItem();
/*  327 */             this.menu.setCarried($$10.copyWithCount($$10.getMaxStackSize()));
/*      */           } 
/*      */           return;
/*      */         } 
/*  331 */         if ($$3 == ClickType.THROW) {
/*  332 */           if (!$$9.isEmpty()) {
/*  333 */             ItemStack $$11 = $$9.copyWithCount(($$2 == 0) ? 1 : $$9.getMaxStackSize());
/*  334 */             this.minecraft.player.drop($$11, true);
/*  335 */             this.minecraft.gameMode.handleCreativeModeItemDrop($$11);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/*      */         
/*  341 */         if (!$$8.isEmpty() && !$$9.isEmpty() && ItemStack.isSameItemSameTags($$8, $$9)) {
/*      */           
/*  343 */           if ($$2 == 0) {
/*  344 */             if ($$4) {
/*  345 */               $$8.setCount($$8.getMaxStackSize());
/*      */             }
/*  347 */             else if ($$8.getCount() < $$8.getMaxStackSize()) {
/*  348 */               $$8.grow(1);
/*      */             } 
/*      */           } else {
/*      */             
/*  352 */             $$8.shrink(1);
/*      */           } 
/*  354 */         } else if ($$9.isEmpty() || !$$8.isEmpty()) {
/*      */           
/*  356 */           if ($$2 == 0) {
/*  357 */             this.menu.setCarried(ItemStack.EMPTY);
/*  358 */           } else if (!this.menu.getCarried().isEmpty()) {
/*  359 */             this.menu.getCarried().shrink(1);
/*      */           } 
/*      */         } else {
/*      */           
/*  363 */           int $$12 = $$4 ? $$9.getMaxStackSize() : $$9.getCount();
/*  364 */           this.menu.setCarried($$9.copyWithCount($$12));
/*      */         } 
/*  366 */       } else if (this.menu != null) {
/*  367 */         ItemStack $$13 = ($$0 == null) ? ItemStack.EMPTY : this.menu.getSlot($$0.index).getItem();
/*  368 */         this.menu.clicked(($$0 == null) ? $$1 : $$0.index, $$2, $$3, (Player)this.minecraft.player);
/*      */         
/*  370 */         if (AbstractContainerMenu.getQuickcraftHeader($$2) == 2) {
/*  371 */           for (int $$14 = 0; $$14 < 9; $$14++) {
/*  372 */             this.minecraft.gameMode.handleCreativeModeItemAdd(this.menu.getSlot(45 + $$14).getItem(), 36 + $$14);
/*      */           }
/*  374 */         } else if ($$0 != null) {
/*  375 */           ItemStack $$15 = this.menu.getSlot($$0.index).getItem();
/*  376 */           this.minecraft.gameMode.handleCreativeModeItemAdd($$15, $$0.index - this.menu.slots.size() + 9 + 36);
/*  377 */           int $$16 = 45 + $$2;
/*  378 */           if ($$3 == ClickType.SWAP) {
/*  379 */             this.minecraft.gameMode.handleCreativeModeItemAdd($$13, $$16 - this.menu.slots.size() + 9 + 36);
/*  380 */           } else if ($$3 == ClickType.THROW && !$$13.isEmpty()) {
/*  381 */             ItemStack $$17 = $$13.copyWithCount(($$2 == 0) ? 1 : $$13.getMaxStackSize());
/*  382 */             this.minecraft.player.drop($$17, true);
/*  383 */             this.minecraft.gameMode.handleCreativeModeItemDrop($$17);
/*      */           } 
/*  385 */           this.minecraft.player.inventoryMenu.broadcastChanges();
/*      */         }
/*      */       
/*      */       }
/*      */     
/*  390 */     } else if (!this.menu.getCarried().isEmpty() && this.hasClickedOutside) {
/*  391 */       if ($$2 == 0) {
/*  392 */         this.minecraft.player.drop(this.menu.getCarried(), true);
/*  393 */         this.minecraft.gameMode.handleCreativeModeItemDrop(this.menu.getCarried());
/*  394 */         this.menu.setCarried(ItemStack.EMPTY);
/*      */       } 
/*  396 */       if ($$2 == 1) {
/*  397 */         ItemStack $$18 = this.menu.getCarried().split(1);
/*  398 */         this.minecraft.player.drop($$18, true);
/*  399 */         this.minecraft.gameMode.handleCreativeModeItemDrop($$18);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isCreativeSlot(@Nullable Slot $$0) {
/*  406 */     return ($$0 != null && $$0.container == CONTAINER);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void init() {
/*  411 */     if (this.minecraft.gameMode.hasInfiniteItems()) {
/*  412 */       super.init();
/*      */       
/*  414 */       Objects.requireNonNull(this.font); this.searchBox = new EditBox(this.font, this.leftPos + 82, this.topPos + 6, 80, 9, (Component)Component.translatable("itemGroup.search"));
/*  415 */       this.searchBox.setMaxLength(50);
/*  416 */       this.searchBox.setBordered(false);
/*  417 */       this.searchBox.setVisible(false);
/*  418 */       this.searchBox.setTextColor(16777215);
/*  419 */       addWidget((GuiEventListener)this.searchBox);
/*      */       
/*  421 */       CreativeModeTab $$0 = selectedTab;
/*  422 */       selectedTab = CreativeModeTabs.getDefaultTab();
/*  423 */       selectTab($$0);
/*      */       
/*  425 */       this.minecraft.player.inventoryMenu.removeSlotListener(this.listener);
/*  426 */       this.listener = new CreativeInventoryListener(this.minecraft);
/*  427 */       this.minecraft.player.inventoryMenu.addSlotListener(this.listener);
/*  428 */       if (!selectedTab.shouldDisplay()) {
/*  429 */         selectTab(CreativeModeTabs.getDefaultTab());
/*      */       }
/*      */     } else {
/*  432 */       this.minecraft.setScreen(new InventoryScreen((Player)this.minecraft.player));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void resize(Minecraft $$0, int $$1, int $$2) {
/*  438 */     int $$3 = this.menu.getRowIndexForScroll(this.scrollOffs);
/*      */     
/*  440 */     String $$4 = this.searchBox.getValue();
/*  441 */     init($$0, $$1, $$2);
/*  442 */     this.searchBox.setValue($$4);
/*      */     
/*  444 */     if (!this.searchBox.getValue().isEmpty()) {
/*  445 */       refreshSearchResults();
/*      */     }
/*      */     
/*  448 */     this.scrollOffs = this.menu.getScrollForRowIndex($$3);
/*  449 */     this.menu.scrollTo(this.scrollOffs);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removed() {
/*  454 */     super.removed();
/*      */     
/*  456 */     if (this.minecraft.player != null && this.minecraft.player.getInventory() != null) {
/*  457 */       this.minecraft.player.inventoryMenu.removeSlotListener(this.listener);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean charTyped(char $$0, int $$1) {
/*  463 */     if (this.ignoreTextInput) {
/*  464 */       return false;
/*      */     }
/*  466 */     if (selectedTab.getType() != CreativeModeTab.Type.SEARCH) {
/*  467 */       return false;
/*      */     }
/*  469 */     String $$2 = this.searchBox.getValue();
/*  470 */     if (this.searchBox.charTyped($$0, $$1)) {
/*  471 */       if (!Objects.equals($$2, this.searchBox.getValue())) {
/*  472 */         refreshSearchResults();
/*      */       }
/*  474 */       return true;
/*      */     } 
/*  476 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/*  481 */     this.ignoreTextInput = false;
/*  482 */     if (selectedTab.getType() != CreativeModeTab.Type.SEARCH) {
/*  483 */       if (this.minecraft.options.keyChat.matches($$0, $$1)) {
/*  484 */         this.ignoreTextInput = true;
/*  485 */         selectTab(CreativeModeTabs.searchTab());
/*  486 */         return true;
/*      */       } 
/*  488 */       return super.keyPressed($$0, $$1, $$2);
/*      */     } 
/*      */ 
/*      */     
/*  492 */     boolean $$3 = (!isCreativeSlot(this.hoveredSlot) || this.hoveredSlot.hasItem());
/*  493 */     boolean $$4 = InputConstants.getKey($$0, $$1).getNumericKeyValue().isPresent();
/*  494 */     if ($$3 && $$4 && checkHotbarKeyPressed($$0, $$1)) {
/*      */ 
/*      */       
/*  497 */       this.ignoreTextInput = true;
/*  498 */       return true;
/*      */     } 
/*      */     
/*  501 */     String $$5 = this.searchBox.getValue();
/*  502 */     if (this.searchBox.keyPressed($$0, $$1, $$2)) {
/*  503 */       if (!Objects.equals($$5, this.searchBox.getValue())) {
/*  504 */         refreshSearchResults();
/*      */       }
/*      */       
/*  507 */       return true;
/*      */     } 
/*  509 */     if (this.searchBox.isFocused() && this.searchBox.isVisible() && $$0 != 256)
/*      */     {
/*  511 */       return true;
/*      */     }
/*  513 */     return super.keyPressed($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean keyReleased(int $$0, int $$1, int $$2) {
/*  518 */     this.ignoreTextInput = false;
/*  519 */     return super.keyReleased($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   private void refreshSearchResults() {
/*  523 */     this.menu.items.clear();
/*  524 */     this.visibleTags.clear();
/*      */     
/*  526 */     String $$0 = this.searchBox.getValue();
/*  527 */     if ($$0.isEmpty()) {
/*  528 */       this.menu.items.addAll(selectedTab.getDisplayItems());
/*      */     } else {
/*      */       SearchTree<ItemStack> $$2;
/*  531 */       if ($$0.startsWith("#")) {
/*  532 */         $$0 = $$0.substring(1);
/*  533 */         SearchTree<ItemStack> $$1 = this.minecraft.getSearchTree(SearchRegistry.CREATIVE_TAGS);
/*  534 */         updateVisibleTags($$0);
/*      */       } else {
/*  536 */         $$2 = this.minecraft.getSearchTree(SearchRegistry.CREATIVE_NAMES);
/*      */       } 
/*  538 */       this.menu.items.addAll($$2.search($$0.toLowerCase(Locale.ROOT)));
/*      */     } 
/*      */     
/*  541 */     this.scrollOffs = 0.0F;
/*  542 */     this.menu.scrollTo(0.0F);
/*      */   }
/*      */   private void updateVisibleTags(String $$0) {
/*      */     Predicate<ResourceLocation> $$5;
/*  546 */     int $$1 = $$0.indexOf(':');
/*      */ 
/*      */     
/*  549 */     if ($$1 == -1) {
/*  550 */       Predicate<ResourceLocation> $$2 = $$1 -> $$1.getPath().contains($$0);
/*      */     } else {
/*  552 */       String $$3 = $$0.substring(0, $$1).trim();
/*  553 */       String $$4 = $$0.substring($$1 + 1).trim();
/*  554 */       $$5 = ($$2 -> ($$2.getNamespace().contains($$0) && $$2.getPath().contains($$1)));
/*      */     } 
/*      */     
/*  557 */     Objects.requireNonNull(this.visibleTags); BuiltInRegistries.ITEM.getTagNames().filter($$1 -> $$0.test($$1.location())).forEach(this.visibleTags::add);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/*  562 */     if (selectedTab.showTitle()) {
/*  563 */       $$0.drawString(this.font, selectedTab.getDisplayName(), 8, 6, 4210752, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/*  569 */     if ($$2 == 0) {
/*  570 */       double $$3 = $$0 - this.leftPos;
/*  571 */       double $$4 = $$1 - this.topPos;
/*      */       
/*  573 */       for (CreativeModeTab $$5 : CreativeModeTabs.tabs()) {
/*  574 */         if (checkTabClicked($$5, $$3, $$4)) {
/*  575 */           return true;
/*      */         }
/*      */       } 
/*      */       
/*  579 */       if (selectedTab.getType() != CreativeModeTab.Type.INVENTORY && insideScrollbar($$0, $$1)) {
/*  580 */         this.scrolling = canScroll();
/*  581 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  585 */     return super.mouseClicked($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/*  590 */     if ($$2 == 0) {
/*  591 */       double $$3 = $$0 - this.leftPos;
/*  592 */       double $$4 = $$1 - this.topPos;
/*      */       
/*  594 */       this.scrolling = false;
/*      */       
/*  596 */       for (CreativeModeTab $$5 : CreativeModeTabs.tabs()) {
/*  597 */         if (checkTabClicked($$5, $$3, $$4)) {
/*  598 */           selectTab($$5);
/*  599 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  604 */     return super.mouseReleased($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   private boolean canScroll() {
/*  608 */     return (selectedTab.canScroll() && this.menu.canScroll());
/*      */   }
/*      */   
/*      */   private void selectTab(CreativeModeTab $$0) {
/*  612 */     CreativeModeTab $$1 = selectedTab;
/*  613 */     selectedTab = $$0;
/*      */     
/*  615 */     this.quickCraftSlots.clear();
/*  616 */     this.menu.items.clear();
/*  617 */     clearDraggingState();
/*  618 */     if (selectedTab.getType() == CreativeModeTab.Type.HOTBAR) {
/*  619 */       HotbarManager $$2 = this.minecraft.getHotbarManager();
/*  620 */       for (int $$3 = 0; $$3 < 9; $$3++) {
/*  621 */         Hotbar $$4 = $$2.get($$3);
/*  622 */         if ($$4.isEmpty()) {
/*  623 */           for (int $$5 = 0; $$5 < 9; $$5++) {
/*  624 */             if ($$5 == $$3) {
/*  625 */               ItemStack $$6 = new ItemStack((ItemLike)Items.PAPER);
/*  626 */               $$6.getOrCreateTagElement("CustomCreativeLock");
/*      */               
/*  628 */               Component $$7 = this.minecraft.options.keyHotbarSlots[$$3].getTranslatedKeyMessage();
/*  629 */               Component $$8 = this.minecraft.options.keySaveHotbarActivator.getTranslatedKeyMessage();
/*  630 */               $$6.setHoverName((Component)Component.translatable("inventory.hotbarInfo", new Object[] { $$8, $$7 }));
/*  631 */               this.menu.items.add($$6);
/*      */             } else {
/*  633 */               this.menu.items.add(ItemStack.EMPTY);
/*      */             } 
/*      */           } 
/*      */         } else {
/*  637 */           this.menu.items.addAll((Collection)$$4);
/*      */         } 
/*      */       } 
/*  640 */     } else if (selectedTab.getType() == CreativeModeTab.Type.CATEGORY) {
/*  641 */       this.menu.items.addAll(selectedTab.getDisplayItems());
/*      */     } 
/*      */     
/*  644 */     if (selectedTab.getType() == CreativeModeTab.Type.INVENTORY) {
/*  645 */       InventoryMenu inventoryMenu = this.minecraft.player.inventoryMenu;
/*      */       
/*  647 */       if (this.originalSlots == null) {
/*  648 */         this.originalSlots = (List<Slot>)ImmutableList.copyOf((Collection)this.menu.slots);
/*      */       }
/*  650 */       this.menu.slots.clear();
/*  651 */       for (int $$10 = 0; $$10 < ((AbstractContainerMenu)inventoryMenu).slots.size(); $$10++) {
/*      */         int $$23, $$25;
/*      */         
/*  654 */         if ($$10 >= 5 && $$10 < 9) {
/*  655 */           int $$11 = $$10 - 5;
/*  656 */           int $$12 = $$11 / 2;
/*  657 */           int $$13 = $$11 % 2;
/*      */           
/*  659 */           int $$14 = 54 + $$12 * 54;
/*  660 */           int $$15 = 6 + $$13 * 27;
/*  661 */         } else if ($$10 >= 0 && $$10 < 5) {
/*  662 */           int $$16 = -2000;
/*  663 */           int $$17 = -2000;
/*  664 */         } else if ($$10 == 45) {
/*  665 */           int $$18 = 35;
/*  666 */           int $$19 = 20;
/*      */         } else {
/*  668 */           int $$20 = $$10 - 9;
/*  669 */           int $$21 = $$20 % 9;
/*  670 */           int $$22 = $$20 / 9;
/*      */           
/*  672 */           $$23 = 9 + $$21 * 18;
/*      */           
/*  674 */           if ($$10 >= 36) {
/*  675 */             int $$24 = 112;
/*      */           } else {
/*  677 */             $$25 = 54 + $$22 * 18;
/*      */           } 
/*      */         } 
/*      */         
/*  681 */         Slot $$26 = new SlotWrapper((Slot)((AbstractContainerMenu)inventoryMenu).slots.get($$10), $$10, $$23, $$25);
/*  682 */         this.menu.slots.add($$26);
/*      */       } 
/*      */       
/*  685 */       this.destroyItemSlot = new Slot((Container)CONTAINER, 0, 173, 112);
/*  686 */       this.menu.slots.add(this.destroyItemSlot);
/*  687 */     } else if ($$1.getType() == CreativeModeTab.Type.INVENTORY) {
/*  688 */       this.menu.slots.clear();
/*  689 */       this.menu.slots.addAll(this.originalSlots);
/*  690 */       this.originalSlots = null;
/*      */     } 
/*      */     
/*  693 */     if (selectedTab.getType() == CreativeModeTab.Type.SEARCH) {
/*  694 */       this.searchBox.setVisible(true);
/*  695 */       this.searchBox.setCanLoseFocus(false);
/*  696 */       this.searchBox.setFocused(true);
/*  697 */       if ($$1 != $$0) {
/*  698 */         this.searchBox.setValue("");
/*      */       }
/*  700 */       refreshSearchResults();
/*      */     } else {
/*  702 */       this.searchBox.setVisible(false);
/*  703 */       this.searchBox.setCanLoseFocus(true);
/*  704 */       this.searchBox.setFocused(false);
/*  705 */       this.searchBox.setValue("");
/*      */     } 
/*      */     
/*  708 */     this.scrollOffs = 0.0F;
/*  709 */     this.menu.scrollTo(0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean mouseScrolled(double $$0, double $$1, double $$2, double $$3) {
/*  714 */     if (!canScroll()) {
/*  715 */       return false;
/*      */     }
/*  717 */     this.scrollOffs = this.menu.subtractInputFromScroll(this.scrollOffs, $$3);
/*  718 */     this.menu.scrollTo(this.scrollOffs);
/*  719 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/*  724 */     boolean $$5 = ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/*  725 */     this.hasClickedOutside = ($$5 && !checkTabClicked(selectedTab, $$0, $$1));
/*  726 */     return this.hasClickedOutside;
/*      */   }
/*      */   
/*      */   protected boolean insideScrollbar(double $$0, double $$1) {
/*  730 */     int $$2 = this.leftPos;
/*  731 */     int $$3 = this.topPos;
/*      */     
/*  733 */     int $$4 = $$2 + 175;
/*  734 */     int $$5 = $$3 + 18;
/*  735 */     int $$6 = $$4 + 14;
/*  736 */     int $$7 = $$5 + 112;
/*  737 */     return ($$0 >= $$4 && $$1 >= $$5 && $$0 < $$6 && $$1 < $$7);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/*  743 */     if (this.scrolling) {
/*  744 */       int $$5 = this.topPos + 18;
/*  745 */       int $$6 = $$5 + 112;
/*      */       
/*  747 */       this.scrollOffs = ((float)$$1 - $$5 - 7.5F) / (($$6 - $$5) - 15.0F);
/*  748 */       this.scrollOffs = Mth.clamp(this.scrollOffs, 0.0F, 1.0F);
/*  749 */       this.menu.scrollTo(this.scrollOffs);
/*      */       
/*  751 */       return true;
/*      */     } 
/*  753 */     return super.mouseDragged($$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  758 */     super.render($$0, $$1, $$2, $$3);
/*      */     
/*  760 */     for (CreativeModeTab $$4 : CreativeModeTabs.tabs()) {
/*  761 */       if (checkTabHovering($$0, $$4, $$1, $$2)) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/*  766 */     if (this.destroyItemSlot != null && selectedTab.getType() == CreativeModeTab.Type.INVENTORY && isHovering(this.destroyItemSlot.x, this.destroyItemSlot.y, 16, 16, $$1, $$2)) {
/*  767 */       $$0.renderTooltip(this.font, TRASH_SLOT_TOOLTIP, $$1, $$2);
/*      */     }
/*      */     
/*  770 */     renderTooltip($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Component> getTooltipFromContainerItem(ItemStack $$0) {
/*  775 */     boolean $$1 = (this.hoveredSlot != null && this.hoveredSlot instanceof CustomCreativeSlot);
/*  776 */     boolean $$2 = (selectedTab.getType() == CreativeModeTab.Type.CATEGORY);
/*  777 */     boolean $$3 = (selectedTab.getType() == CreativeModeTab.Type.SEARCH);
/*      */     
/*  779 */     TooltipFlag.Default $$4 = this.minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL;
/*  780 */     TooltipFlag.Default default_1 = $$1 ? $$4.asCreative() : $$4;
/*  781 */     List<Component> $$6 = $$0.getTooltipLines((Player)this.minecraft.player, (TooltipFlag)default_1);
/*      */     
/*  783 */     if (!$$2 || !$$1) {
/*  784 */       List<Component> $$7 = Lists.newArrayList($$6);
/*      */       
/*  786 */       if ($$3 && $$1) {
/*  787 */         this.visibleTags.forEach($$2 -> {
/*      */               if ($$0.is($$2)) {
/*      */                 $$1.add(1, Component.literal("#" + $$2.location()).withStyle(ChatFormatting.DARK_PURPLE));
/*      */               }
/*      */             });
/*      */       }
/*      */       
/*  794 */       int $$8 = 1;
/*  795 */       for (CreativeModeTab $$9 : CreativeModeTabs.tabs()) {
/*  796 */         if ($$9.getType() != CreativeModeTab.Type.SEARCH && $$9.contains($$0)) {
/*  797 */           $$7.add($$8++, $$9.getDisplayName().copy().withStyle(ChatFormatting.BLUE));
/*      */         }
/*      */       } 
/*      */       
/*  801 */       return $$7;
/*      */     } 
/*      */     
/*  804 */     return $$6;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void renderBg(GuiGraphics $$0, float $$1, int $$2, int $$3) {
/*  809 */     for (CreativeModeTab $$4 : CreativeModeTabs.tabs()) {
/*  810 */       if ($$4 != selectedTab) {
/*  811 */         renderTabButton($$0, $$4);
/*      */       }
/*      */     } 
/*      */     
/*  815 */     $$0.blit(new ResourceLocation("textures/gui/container/creative_inventory/tab_" + selectedTab.getBackgroundSuffix()), this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
/*      */     
/*  817 */     this.searchBox.render($$0, $$2, $$3, $$1);
/*      */     
/*  819 */     int $$5 = this.leftPos + 175;
/*  820 */     int $$6 = this.topPos + 18;
/*  821 */     int $$7 = $$6 + 112;
/*      */     
/*  823 */     if (selectedTab.canScroll()) {
/*  824 */       ResourceLocation $$8 = canScroll() ? SCROLLER_SPRITE : SCROLLER_DISABLED_SPRITE;
/*  825 */       $$0.blitSprite($$8, $$5, $$6 + (int)(($$7 - $$6 - 17) * this.scrollOffs), 12, 15);
/*      */     } 
/*      */     
/*  828 */     renderTabButton($$0, selectedTab);
/*      */     
/*  830 */     if (selectedTab.getType() == CreativeModeTab.Type.INVENTORY) {
/*  831 */       InventoryScreen.renderEntityInInventoryFollowsMouse($$0, this.leftPos + 73, this.topPos + 6, this.leftPos + 105, this.topPos + 49, 20, 0.0625F, $$2, $$3, (LivingEntity)this.minecraft.player);
/*      */     }
/*      */   }
/*      */   
/*      */   private int getTabX(CreativeModeTab $$0) {
/*  836 */     int $$1 = $$0.column();
/*  837 */     int $$2 = 27;
/*  838 */     int $$3 = 27 * $$1;
/*      */     
/*  840 */     if ($$0.isAlignedRight()) {
/*  841 */       $$3 = this.imageWidth - 27 * (7 - $$1) + 1;
/*      */     }
/*  843 */     return $$3;
/*      */   }
/*      */   
/*      */   private int getTabY(CreativeModeTab $$0) {
/*  847 */     int $$1 = 0;
/*  848 */     if ($$0.row() == CreativeModeTab.Row.TOP) {
/*  849 */       $$1 -= 32;
/*      */     } else {
/*  851 */       $$1 += this.imageHeight;
/*      */     } 
/*  853 */     return $$1;
/*      */   }
/*      */   
/*      */   protected boolean checkTabClicked(CreativeModeTab $$0, double $$1, double $$2) {
/*  857 */     int $$3 = getTabX($$0);
/*  858 */     int $$4 = getTabY($$0);
/*  859 */     return ($$1 >= $$3 && $$1 <= ($$3 + 26) && $$2 >= $$4 && $$2 <= ($$4 + 32));
/*      */   }
/*      */   
/*      */   protected boolean checkTabHovering(GuiGraphics $$0, CreativeModeTab $$1, int $$2, int $$3) {
/*  863 */     int $$4 = getTabX($$1);
/*  864 */     int $$5 = getTabY($$1);
/*  865 */     if (isHovering($$4 + 3, $$5 + 3, 21, 27, $$2, $$3)) {
/*  866 */       $$0.renderTooltip(this.font, $$1.getDisplayName(), $$2, $$3);
/*  867 */       return true;
/*      */     } 
/*      */     
/*  870 */     return false;
/*      */   }
/*      */   protected void renderTabButton(GuiGraphics $$0, CreativeModeTab $$1) {
/*      */     ResourceLocation[] $$8;
/*  874 */     boolean $$2 = ($$1 == selectedTab);
/*  875 */     boolean $$3 = ($$1.row() == CreativeModeTab.Row.TOP);
/*  876 */     int $$4 = $$1.column();
/*  877 */     int $$5 = this.leftPos + getTabX($$1);
/*  878 */     int $$6 = this.topPos - ($$3 ? 28 : -(this.imageHeight - 4));
/*      */ 
/*      */     
/*  881 */     if ($$3) {
/*  882 */       ResourceLocation[] $$7 = $$2 ? SELECTED_TOP_TABS : UNSELECTED_TOP_TABS;
/*      */     } else {
/*  884 */       $$8 = $$2 ? SELECTED_BOTTOM_TABS : UNSELECTED_BOTTOM_TABS;
/*      */     } 
/*      */     
/*  887 */     $$0.blitSprite($$8[Mth.clamp($$4, 0, $$8.length)], $$5, $$6, 26, 32);
/*      */     
/*  889 */     $$0.pose().pushPose();
/*  890 */     $$0.pose().translate(0.0F, 0.0F, 100.0F);
/*  891 */     $$5 += 5;
/*  892 */     $$6 += 8 + ($$3 ? 1 : -1);
/*      */     
/*  894 */     ItemStack $$9 = $$1.getIconItem();
/*  895 */     $$0.renderItem($$9, $$5, $$6);
/*  896 */     $$0.renderItemDecorations(this.font, $$9, $$5, $$6);
/*      */     
/*  898 */     $$0.pose().popPose();
/*      */   }
/*      */   
/*      */   public boolean isInventoryOpen() {
/*  902 */     return (selectedTab.getType() == CreativeModeTab.Type.INVENTORY);
/*      */   }
/*      */   
/*      */   private static class SlotWrapper
/*      */     extends Slot {
/*      */     final Slot target;
/*      */     
/*      */     public SlotWrapper(Slot $$0, int $$1, int $$2, int $$3) {
/*  910 */       super($$0.container, $$1, $$2, $$3);
/*  911 */       this.target = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public void onTake(Player $$0, ItemStack $$1) {
/*  916 */       this.target.onTake($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mayPlace(ItemStack $$0) {
/*  921 */       return this.target.mayPlace($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack getItem() {
/*  926 */       return this.target.getItem();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasItem() {
/*  931 */       return this.target.hasItem();
/*      */     }
/*      */ 
/*      */     
/*      */     public void setByPlayer(ItemStack $$0, ItemStack $$1) {
/*  936 */       this.target.setByPlayer($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     public void set(ItemStack $$0) {
/*  941 */       this.target.set($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public void setChanged() {
/*  946 */       this.target.setChanged();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxStackSize() {
/*  951 */       return this.target.getMaxStackSize();
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxStackSize(ItemStack $$0) {
/*  956 */       return this.target.getMaxStackSize($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     @Nullable
/*      */     public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/*  962 */       return this.target.getNoItemIcon();
/*      */     }
/*      */ 
/*      */     
/*      */     public ItemStack remove(int $$0) {
/*  967 */       return this.target.remove($$0);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isActive() {
/*  972 */       return this.target.isActive();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mayPickup(Player $$0) {
/*  977 */       return this.target.mayPickup($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class CustomCreativeSlot extends Slot {
/*      */     public CustomCreativeSlot(Container $$0, int $$1, int $$2, int $$3) {
/*  983 */       super($$0, $$1, $$2, $$3);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean mayPickup(Player $$0) {
/*  988 */       ItemStack $$1 = getItem();
/*  989 */       if (super.mayPickup($$0) && !$$1.isEmpty()) {
/*  990 */         return ($$1.isItemEnabled($$0.level().enabledFeatures()) && $$1
/*  991 */           .getTagElement("CustomCreativeLock") == null);
/*      */       }
/*  993 */       return $$1.isEmpty();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void handleHotbarLoadOrSave(Minecraft $$0, int $$1, boolean $$2, boolean $$3) {
/*  998 */     LocalPlayer $$4 = $$0.player;
/*  999 */     HotbarManager $$5 = $$0.getHotbarManager();
/* 1000 */     Hotbar $$6 = $$5.get($$1);
/*      */     
/* 1002 */     if ($$2) {
/* 1003 */       for (int $$7 = 0; $$7 < Inventory.getSelectionSize(); $$7++) {
/* 1004 */         ItemStack $$8 = (ItemStack)$$6.get($$7);
/* 1005 */         ItemStack $$9 = $$8.isItemEnabled($$4.level().enabledFeatures()) ? $$8.copy() : ItemStack.EMPTY;
/* 1006 */         $$4.getInventory().setItem($$7, $$9);
/* 1007 */         $$0.gameMode.handleCreativeModeItemAdd($$9, 36 + $$7);
/*      */       } 
/* 1009 */       $$4.inventoryMenu.broadcastChanges();
/* 1010 */     } else if ($$3) {
/* 1011 */       for (int $$10 = 0; $$10 < Inventory.getSelectionSize(); $$10++) {
/* 1012 */         $$6.set($$10, $$4.getInventory().getItem($$10).copy());
/*      */       }
/* 1014 */       Component $$11 = $$0.options.keyHotbarSlots[$$1].getTranslatedKeyMessage();
/* 1015 */       Component $$12 = $$0.options.keyLoadHotbarActivator.getTranslatedKeyMessage();
/* 1016 */       MutableComponent mutableComponent = Component.translatable("inventory.hotbarSaved", new Object[] { $$12, $$11 });
/* 1017 */       $$0.gui.setOverlayMessage((Component)mutableComponent, false);
/* 1018 */       $$0.getNarrator().sayNow((Component)mutableComponent);
/*      */       
/* 1020 */       $$5.save();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\CreativeModeInventoryScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */