/*     */ package net.minecraft.client.gui.screens.inventory;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.blaze3d.platform.InputConstants;
/*     */ import com.mojang.blaze3d.systems.RenderSystem;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.GuiGraphics;
/*     */ import net.minecraft.client.gui.screens.Screen;
/*     */ import net.minecraft.client.renderer.RenderType;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.ClickType;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public abstract class AbstractContainerScreen<T extends AbstractContainerMenu> extends Screen implements MenuAccess<T> {
/*  28 */   public static final ResourceLocation INVENTORY_LOCATION = new ResourceLocation("textures/gui/container/inventory.png");
/*     */   
/*     */   private static final float SNAPBACK_SPEED = 100.0F;
/*     */   
/*     */   private static final int QUICKDROP_DELAY = 500;
/*     */   public static final int SLOT_ITEM_BLIT_OFFSET = 100;
/*     */   private static final int HOVER_ITEM_BLIT_OFFSET = 200;
/*  35 */   protected int imageWidth = 176;
/*  36 */   protected int imageHeight = 166;
/*     */   
/*     */   protected int titleLabelX;
/*     */   
/*     */   protected int titleLabelY;
/*     */   
/*     */   protected int inventoryLabelX;
/*     */   
/*     */   protected int inventoryLabelY;
/*     */   protected final T menu;
/*     */   protected final Component playerInventoryTitle;
/*     */   @Nullable
/*     */   protected Slot hoveredSlot;
/*     */   @Nullable
/*     */   private Slot clickedSlot;
/*     */   @Nullable
/*     */   private Slot snapbackEnd;
/*     */   @Nullable
/*     */   private Slot quickdropSlot;
/*     */   @Nullable
/*     */   private Slot lastClickSlot;
/*     */   protected int leftPos;
/*     */   protected int topPos;
/*     */   private boolean isSplittingStack;
/*  60 */   private ItemStack draggingItem = ItemStack.EMPTY;
/*     */   private int snapbackStartX;
/*     */   private int snapbackStartY;
/*     */   private long snapbackTime;
/*  64 */   private ItemStack snapbackItem = ItemStack.EMPTY;
/*     */   
/*     */   private long quickdropTime;
/*  67 */   protected final Set<Slot> quickCraftSlots = Sets.newHashSet();
/*     */   protected boolean isQuickCrafting;
/*     */   private int quickCraftingType;
/*     */   private int quickCraftingButton;
/*     */   private boolean skipNextRelease;
/*     */   private int quickCraftingRemainder;
/*     */   private long lastClickTime;
/*     */   private int lastClickButton;
/*     */   private boolean doubleclick;
/*  76 */   private ItemStack lastQuickMoved = ItemStack.EMPTY;
/*     */   
/*     */   public AbstractContainerScreen(T $$0, Inventory $$1, Component $$2) {
/*  79 */     super($$2);
/*  80 */     this.menu = $$0;
/*  81 */     this.playerInventoryTitle = $$1.getDisplayName();
/*  82 */     this.skipNextRelease = true;
/*  83 */     this.titleLabelX = 8;
/*  84 */     this.titleLabelY = 6;
/*  85 */     this.inventoryLabelX = 8;
/*  86 */     this.inventoryLabelY = this.imageHeight - 94;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  91 */     this.leftPos = (this.width - this.imageWidth) / 2;
/*  92 */     this.topPos = (this.height - this.imageHeight) / 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/*  97 */     int $$4 = this.leftPos;
/*  98 */     int $$5 = this.topPos;
/*     */     
/* 100 */     super.render($$0, $$1, $$2, $$3);
/*     */     
/* 102 */     RenderSystem.disableDepthTest();
/*     */     
/* 104 */     $$0.pose().pushPose();
/* 105 */     $$0.pose().translate($$4, $$5, 0.0F);
/*     */     
/* 107 */     this.hoveredSlot = null;
/*     */     
/* 109 */     for (int $$6 = 0; $$6 < ((AbstractContainerMenu)this.menu).slots.size(); $$6++) {
/* 110 */       Slot $$7 = (Slot)((AbstractContainerMenu)this.menu).slots.get($$6);
/*     */       
/* 112 */       if ($$7.isActive()) {
/* 113 */         renderSlot($$0, $$7);
/*     */       }
/*     */       
/* 116 */       if (isHovering($$7, $$1, $$2) && $$7.isActive()) {
/* 117 */         this.hoveredSlot = $$7;
/*     */         
/* 119 */         int $$8 = $$7.x;
/* 120 */         int $$9 = $$7.y;
/* 121 */         if (this.hoveredSlot.isHighlightable()) {
/* 122 */           renderSlotHighlight($$0, $$8, $$9, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 127 */     renderLabels($$0, $$1, $$2);
/*     */     
/* 129 */     ItemStack $$10 = this.draggingItem.isEmpty() ? this.menu.getCarried() : this.draggingItem;
/*     */     
/* 131 */     if (!$$10.isEmpty()) {
/* 132 */       int $$11 = 8;
/* 133 */       int $$12 = this.draggingItem.isEmpty() ? 8 : 16;
/* 134 */       String $$13 = null;
/*     */       
/* 136 */       if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
/* 137 */         $$10 = $$10.copyWithCount(Mth.ceil($$10.getCount() / 2.0F));
/* 138 */       } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
/* 139 */         $$10 = $$10.copyWithCount(this.quickCraftingRemainder);
/*     */         
/* 141 */         if ($$10.isEmpty()) {
/* 142 */           $$13 = "" + ChatFormatting.YELLOW + "0";
/*     */         }
/*     */       } 
/* 145 */       renderFloatingItem($$0, $$10, $$1 - $$4 - 8, $$2 - $$5 - $$12, $$13);
/*     */     } 
/*     */     
/* 148 */     if (!this.snapbackItem.isEmpty()) {
/* 149 */       float $$14 = (float)(Util.getMillis() - this.snapbackTime) / 100.0F;
/*     */       
/* 151 */       if ($$14 >= 1.0F) {
/* 152 */         $$14 = 1.0F;
/* 153 */         this.snapbackItem = ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 156 */       int $$15 = this.snapbackEnd.x - this.snapbackStartX;
/* 157 */       int $$16 = this.snapbackEnd.y - this.snapbackStartY;
/* 158 */       int $$17 = this.snapbackStartX + (int)($$15 * $$14);
/* 159 */       int $$18 = this.snapbackStartY + (int)($$16 * $$14);
/*     */       
/* 161 */       renderFloatingItem($$0, this.snapbackItem, $$17, $$18, (String)null);
/*     */     } 
/*     */     
/* 164 */     $$0.pose().popPose();
/*     */     
/* 166 */     RenderSystem.enableDepthTest();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderBackground(GuiGraphics $$0, int $$1, int $$2, float $$3) {
/* 171 */     super.renderBackground($$0, $$1, $$2, $$3);
/* 172 */     renderBg($$0, $$3, $$1, $$2);
/*     */   }
/*     */   
/*     */   public static void renderSlotHighlight(GuiGraphics $$0, int $$1, int $$2, int $$3) {
/* 176 */     $$0.fillGradient(RenderType.guiOverlay(), $$1, $$2, $$1 + 16, $$2 + 16, -2130706433, -2130706433, $$3);
/*     */   }
/*     */   
/*     */   protected void renderTooltip(GuiGraphics $$0, int $$1, int $$2) {
/* 180 */     if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
/* 181 */       ItemStack $$3 = this.hoveredSlot.getItem();
/* 182 */       $$0.renderTooltip(this.font, getTooltipFromContainerItem($$3), $$3.getTooltipImage(), $$1, $$2);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected List<Component> getTooltipFromContainerItem(ItemStack $$0) {
/* 187 */     return getTooltipFromItem(this.minecraft, $$0);
/*     */   }
/*     */   
/*     */   private void renderFloatingItem(GuiGraphics $$0, ItemStack $$1, int $$2, int $$3, String $$4) {
/* 191 */     $$0.pose().pushPose();
/* 192 */     $$0.pose().translate(0.0F, 0.0F, 232.0F);
/* 193 */     $$0.renderItem($$1, $$2, $$3);
/* 194 */     $$0.renderItemDecorations(this.font, $$1, $$2, $$3 - (this.draggingItem.isEmpty() ? 0 : 8), $$4);
/* 195 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   protected void renderLabels(GuiGraphics $$0, int $$1, int $$2) {
/* 199 */     $$0.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
/* 200 */     $$0.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
/*     */   }
/*     */   
/*     */   protected abstract void renderBg(GuiGraphics paramGuiGraphics, float paramFloat, int paramInt1, int paramInt2);
/*     */   
/*     */   protected void renderSlot(GuiGraphics $$0, Slot $$1) {
/* 206 */     int $$2 = $$1.x;
/* 207 */     int $$3 = $$1.y;
/* 208 */     ItemStack $$4 = $$1.getItem();
/* 209 */     boolean $$5 = false;
/* 210 */     boolean $$6 = ($$1 == this.clickedSlot && !this.draggingItem.isEmpty() && !this.isSplittingStack);
/* 211 */     ItemStack $$7 = this.menu.getCarried();
/* 212 */     String $$8 = null;
/*     */     
/* 214 */     if ($$1 == this.clickedSlot && !this.draggingItem.isEmpty() && this.isSplittingStack && !$$4.isEmpty()) {
/* 215 */       $$4 = $$4.copyWithCount($$4.getCount() / 2);
/* 216 */     } else if (this.isQuickCrafting && this.quickCraftSlots.contains($$1) && !$$7.isEmpty()) {
/* 217 */       if (this.quickCraftSlots.size() == 1) {
/*     */         return;
/*     */       }
/* 220 */       if (AbstractContainerMenu.canItemQuickReplace($$1, $$7, true) && this.menu.canDragTo($$1)) {
/* 221 */         $$5 = true;
/*     */         
/* 223 */         int $$9 = Math.min($$7.getMaxStackSize(), $$1.getMaxStackSize($$7));
/* 224 */         int $$10 = $$1.getItem().isEmpty() ? 0 : $$1.getItem().getCount();
/* 225 */         int $$11 = AbstractContainerMenu.getQuickCraftPlaceCount(this.quickCraftSlots, this.quickCraftingType, $$7) + $$10;
/* 226 */         if ($$11 > $$9) {
/* 227 */           $$11 = $$9;
/* 228 */           $$8 = ChatFormatting.YELLOW.toString() + ChatFormatting.YELLOW.toString();
/*     */         } 
/*     */         
/* 231 */         $$4 = $$7.copyWithCount($$11);
/*     */       } else {
/* 233 */         this.quickCraftSlots.remove($$1);
/* 234 */         recalculateQuickCraftRemaining();
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     $$0.pose().pushPose();
/* 239 */     $$0.pose().translate(0.0F, 0.0F, 100.0F);
/* 240 */     if ($$4.isEmpty() && $$1.isActive()) {
/* 241 */       Pair<ResourceLocation, ResourceLocation> $$12 = $$1.getNoItemIcon();
/* 242 */       if ($$12 != null) {
/* 243 */         TextureAtlasSprite $$13 = this.minecraft.getTextureAtlas((ResourceLocation)$$12.getFirst()).apply((ResourceLocation)$$12.getSecond());
/* 244 */         $$0.blit($$2, $$3, 0, 16, 16, $$13);
/* 245 */         $$6 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 249 */     if (!$$6) {
/* 250 */       if ($$5) {
/* 251 */         $$0.fill($$2, $$3, $$2 + 16, $$3 + 16, -2130706433);
/*     */       }
/* 253 */       int $$14 = $$1.x + $$1.y * this.imageWidth;
/* 254 */       if ($$1.isFake()) {
/* 255 */         $$0.renderFakeItem($$4, $$2, $$3, $$14);
/*     */       } else {
/* 257 */         $$0.renderItem($$4, $$2, $$3, $$14);
/*     */       } 
/* 259 */       $$0.renderItemDecorations(this.font, $$4, $$2, $$3, $$8);
/*     */     } 
/* 261 */     $$0.pose().popPose();
/*     */   }
/*     */   
/*     */   private void recalculateQuickCraftRemaining() {
/* 265 */     ItemStack $$0 = this.menu.getCarried();
/* 266 */     if ($$0.isEmpty() || !this.isQuickCrafting) {
/*     */       return;
/*     */     }
/*     */     
/* 270 */     if (this.quickCraftingType == 2) {
/* 271 */       this.quickCraftingRemainder = $$0.getMaxStackSize();
/*     */       
/*     */       return;
/*     */     } 
/* 275 */     this.quickCraftingRemainder = $$0.getCount();
/*     */     
/* 277 */     for (Slot $$1 : this.quickCraftSlots) {
/* 278 */       ItemStack $$2 = $$1.getItem();
/* 279 */       int $$3 = $$2.isEmpty() ? 0 : $$2.getCount();
/* 280 */       int $$4 = Math.min($$0.getMaxStackSize(), $$1.getMaxStackSize($$0));
/* 281 */       int $$5 = Math.min(AbstractContainerMenu.getQuickCraftPlaceCount(this.quickCraftSlots, this.quickCraftingType, $$0) + $$3, $$4);
/* 282 */       this.quickCraftingRemainder -= $$5 - $$3;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private Slot findSlot(double $$0, double $$1) {
/* 288 */     for (int $$2 = 0; $$2 < ((AbstractContainerMenu)this.menu).slots.size(); $$2++) {
/* 289 */       Slot $$3 = (Slot)((AbstractContainerMenu)this.menu).slots.get($$2);
/* 290 */       if (isHovering($$3, $$0, $$1) && $$3.isActive()) {
/* 291 */         return $$3;
/*     */       }
/*     */     } 
/* 294 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(double $$0, double $$1, int $$2) {
/* 299 */     if (super.mouseClicked($$0, $$1, $$2)) {
/* 300 */       return true;
/*     */     }
/* 302 */     boolean $$3 = (this.minecraft.options.keyPickItem.matchesMouse($$2) && this.minecraft.gameMode.hasInfiniteItems());
/* 303 */     Slot $$4 = findSlot($$0, $$1);
/* 304 */     long $$5 = Util.getMillis();
/* 305 */     this.doubleclick = (this.lastClickSlot == $$4 && $$5 - this.lastClickTime < 250L && this.lastClickButton == $$2);
/* 306 */     this.skipNextRelease = false;
/*     */     
/* 308 */     if ($$2 == 0 || $$2 == 1 || $$3) {
/* 309 */       int $$6 = this.leftPos;
/* 310 */       int $$7 = this.topPos;
/* 311 */       boolean $$8 = hasClickedOutside($$0, $$1, $$6, $$7, $$2);
/*     */       
/* 313 */       int $$9 = -1;
/* 314 */       if ($$4 != null) {
/* 315 */         $$9 = $$4.index;
/*     */       }
/*     */       
/* 318 */       if ($$8) {
/* 319 */         $$9 = -999;
/*     */       }
/*     */       
/* 322 */       if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue() && $$8 && this.menu.getCarried().isEmpty()) {
/* 323 */         onClose();
/* 324 */         return true;
/*     */       } 
/*     */       
/* 327 */       if ($$9 != -1) {
/* 328 */         if (((Boolean)this.minecraft.options.touchscreen().get()).booleanValue()) {
/* 329 */           if ($$4 != null && $$4.hasItem()) {
/* 330 */             this.clickedSlot = $$4;
/* 331 */             this.draggingItem = ItemStack.EMPTY;
/* 332 */             this.isSplittingStack = ($$2 == 1);
/*     */           } else {
/* 334 */             this.clickedSlot = null;
/*     */           } 
/* 336 */         } else if (!this.isQuickCrafting) {
/* 337 */           if (this.menu.getCarried().isEmpty()) {
/* 338 */             if ($$3) {
/* 339 */               slotClicked($$4, $$9, $$2, ClickType.CLONE);
/*     */             } else {
/* 341 */               boolean $$10 = ($$9 != -999 && (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)));
/* 342 */               ClickType $$11 = ClickType.PICKUP;
/* 343 */               if ($$10) {
/* 344 */                 this.lastQuickMoved = ($$4 != null && $$4.hasItem()) ? $$4.getItem().copy() : ItemStack.EMPTY;
/* 345 */                 $$11 = ClickType.QUICK_MOVE;
/* 346 */               } else if ($$9 == -999) {
/* 347 */                 $$11 = ClickType.THROW;
/*     */               } 
/* 349 */               slotClicked($$4, $$9, $$2, $$11);
/*     */             } 
/* 351 */             this.skipNextRelease = true;
/*     */           } else {
/* 353 */             this.isQuickCrafting = true;
/* 354 */             this.quickCraftingButton = $$2;
/* 355 */             this.quickCraftSlots.clear();
/*     */             
/* 357 */             if ($$2 == 0) {
/* 358 */               this.quickCraftingType = 0;
/* 359 */             } else if ($$2 == 1) {
/* 360 */               this.quickCraftingType = 1;
/* 361 */             } else if ($$3) {
/* 362 */               this.quickCraftingType = 2;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } else {
/* 368 */       checkHotbarMouseClicked($$2);
/*     */     } 
/*     */     
/* 371 */     this.lastClickSlot = $$4;
/* 372 */     this.lastClickTime = $$5;
/* 373 */     this.lastClickButton = $$2;
/*     */     
/* 375 */     return true;
/*     */   }
/*     */   
/*     */   private void checkHotbarMouseClicked(int $$0) {
/* 379 */     if (this.hoveredSlot != null && this.menu.getCarried().isEmpty()) {
/* 380 */       if (this.minecraft.options.keySwapOffhand.matchesMouse($$0)) {
/* 381 */         slotClicked(this.hoveredSlot, this.hoveredSlot.index, 40, ClickType.SWAP);
/*     */         return;
/*     */       } 
/* 384 */       for (int $$1 = 0; $$1 < 9; $$1++) {
/* 385 */         if (this.minecraft.options.keyHotbarSlots[$$1].matchesMouse($$0)) {
/* 386 */           slotClicked(this.hoveredSlot, this.hoveredSlot.index, $$1, ClickType.SWAP);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean hasClickedOutside(double $$0, double $$1, int $$2, int $$3, int $$4) {
/* 393 */     return ($$0 < $$2 || $$1 < $$3 || $$0 >= ($$2 + this.imageWidth) || $$1 >= ($$3 + this.imageHeight));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseDragged(double $$0, double $$1, int $$2, double $$3, double $$4) {
/* 398 */     Slot $$5 = findSlot($$0, $$1);
/* 399 */     ItemStack $$6 = this.menu.getCarried();
/*     */     
/* 401 */     if (this.clickedSlot != null && ((Boolean)this.minecraft.options.touchscreen().get()).booleanValue()) {
/* 402 */       if ($$2 == 0 || $$2 == 1) {
/* 403 */         if (this.draggingItem.isEmpty()) {
/* 404 */           if ($$5 != this.clickedSlot && !this.clickedSlot.getItem().isEmpty()) {
/* 405 */             this.draggingItem = this.clickedSlot.getItem().copy();
/*     */           }
/* 407 */         } else if (this.draggingItem.getCount() > 1 && $$5 != null && AbstractContainerMenu.canItemQuickReplace($$5, this.draggingItem, false)) {
/* 408 */           long $$7 = Util.getMillis();
/*     */           
/* 410 */           if (this.quickdropSlot == $$5) {
/* 411 */             if ($$7 - this.quickdropTime > 500L) {
/* 412 */               slotClicked(this.clickedSlot, this.clickedSlot.index, 0, ClickType.PICKUP);
/* 413 */               slotClicked($$5, $$5.index, 1, ClickType.PICKUP);
/* 414 */               slotClicked(this.clickedSlot, this.clickedSlot.index, 0, ClickType.PICKUP);
/* 415 */               this.quickdropTime = $$7 + 750L;
/* 416 */               this.draggingItem.shrink(1);
/*     */             } 
/*     */           } else {
/* 419 */             this.quickdropSlot = $$5;
/* 420 */             this.quickdropTime = $$7;
/*     */           } 
/*     */         } 
/*     */       }
/* 424 */     } else if (this.isQuickCrafting && $$5 != null && !$$6.isEmpty() && ($$6.getCount() > this.quickCraftSlots.size() || this.quickCraftingType == 2) && AbstractContainerMenu.canItemQuickReplace($$5, $$6, true) && $$5.mayPlace($$6) && this.menu.canDragTo($$5)) {
/* 425 */       this.quickCraftSlots.add($$5);
/* 426 */       recalculateQuickCraftRemaining();
/*     */     } 
/*     */     
/* 429 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean mouseReleased(double $$0, double $$1, int $$2) {
/* 434 */     Slot $$3 = findSlot($$0, $$1);
/* 435 */     int $$4 = this.leftPos;
/* 436 */     int $$5 = this.topPos;
/* 437 */     boolean $$6 = hasClickedOutside($$0, $$1, $$4, $$5, $$2);
/*     */     
/* 439 */     int $$7 = -1;
/* 440 */     if ($$3 != null) {
/* 441 */       $$7 = $$3.index;
/*     */     }
/*     */     
/* 444 */     if ($$6) {
/* 445 */       $$7 = -999;
/*     */     }
/*     */     
/* 448 */     if (this.doubleclick && $$3 != null && $$2 == 0 && this.menu.canTakeItemForPickAll(ItemStack.EMPTY, $$3)) {
/* 449 */       if (hasShiftDown()) {
/* 450 */         if (!this.lastQuickMoved.isEmpty()) {
/* 451 */           for (Slot $$8 : ((AbstractContainerMenu)this.menu).slots) {
/* 452 */             if ($$8 != null && $$8.mayPickup((Player)this.minecraft.player) && $$8.hasItem() && $$8.container == $$3.container && AbstractContainerMenu.canItemQuickReplace($$8, this.lastQuickMoved, true)) {
/* 453 */               slotClicked($$8, $$8.index, $$2, ClickType.QUICK_MOVE);
/*     */             }
/*     */           } 
/*     */         }
/*     */       } else {
/* 458 */         slotClicked($$3, $$7, $$2, ClickType.PICKUP_ALL);
/*     */       } 
/* 460 */       this.doubleclick = false;
/* 461 */       this.lastClickTime = 0L;
/*     */     } else {
/* 463 */       if (this.isQuickCrafting && this.quickCraftingButton != $$2) {
/* 464 */         this.isQuickCrafting = false;
/* 465 */         this.quickCraftSlots.clear();
/* 466 */         this.skipNextRelease = true;
/* 467 */         return true;
/* 468 */       }  if (this.skipNextRelease) {
/* 469 */         this.skipNextRelease = false;
/* 470 */         return true;
/*     */       } 
/*     */       
/* 473 */       if (this.clickedSlot != null && ((Boolean)this.minecraft.options.touchscreen().get()).booleanValue()) {
/* 474 */         if ($$2 == 0 || $$2 == 1) {
/* 475 */           if (this.draggingItem.isEmpty() && $$3 != this.clickedSlot) {
/* 476 */             this.draggingItem = this.clickedSlot.getItem();
/*     */           }
/*     */           
/* 479 */           boolean $$9 = AbstractContainerMenu.canItemQuickReplace($$3, this.draggingItem, false);
/*     */           
/* 481 */           if ($$7 != -1 && !this.draggingItem.isEmpty() && $$9) {
/* 482 */             slotClicked(this.clickedSlot, this.clickedSlot.index, $$2, ClickType.PICKUP);
/* 483 */             slotClicked($$3, $$7, 0, ClickType.PICKUP);
/*     */             
/* 485 */             if (this.menu.getCarried().isEmpty()) {
/* 486 */               this.snapbackItem = ItemStack.EMPTY;
/*     */             } else {
/* 488 */               slotClicked(this.clickedSlot, this.clickedSlot.index, $$2, ClickType.PICKUP);
/* 489 */               this.snapbackStartX = Mth.floor($$0 - $$4);
/* 490 */               this.snapbackStartY = Mth.floor($$1 - $$5);
/* 491 */               this.snapbackEnd = this.clickedSlot;
/* 492 */               this.snapbackItem = this.draggingItem;
/* 493 */               this.snapbackTime = Util.getMillis();
/*     */             } 
/* 495 */           } else if (!this.draggingItem.isEmpty()) {
/* 496 */             this.snapbackStartX = Mth.floor($$0 - $$4);
/* 497 */             this.snapbackStartY = Mth.floor($$1 - $$5);
/* 498 */             this.snapbackEnd = this.clickedSlot;
/* 499 */             this.snapbackItem = this.draggingItem;
/* 500 */             this.snapbackTime = Util.getMillis();
/*     */           } 
/*     */           
/* 503 */           clearDraggingState();
/*     */         } 
/* 505 */       } else if (this.isQuickCrafting && !this.quickCraftSlots.isEmpty()) {
/* 506 */         slotClicked((Slot)null, -999, AbstractContainerMenu.getQuickcraftMask(0, this.quickCraftingType), ClickType.QUICK_CRAFT);
/*     */         
/* 508 */         for (Slot $$10 : this.quickCraftSlots) {
/* 509 */           slotClicked($$10, $$10.index, AbstractContainerMenu.getQuickcraftMask(1, this.quickCraftingType), ClickType.QUICK_CRAFT);
/*     */         }
/*     */         
/* 512 */         slotClicked((Slot)null, -999, AbstractContainerMenu.getQuickcraftMask(2, this.quickCraftingType), ClickType.QUICK_CRAFT);
/* 513 */       } else if (!this.menu.getCarried().isEmpty()) {
/* 514 */         if (this.minecraft.options.keyPickItem.matchesMouse($$2)) {
/* 515 */           slotClicked($$3, $$7, $$2, ClickType.CLONE);
/*     */         } else {
/* 517 */           boolean $$11 = ($$7 != -999 && (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 340) || InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 344)));
/* 518 */           if ($$11) {
/* 519 */             this.lastQuickMoved = ($$3 != null && $$3.hasItem()) ? $$3.getItem().copy() : ItemStack.EMPTY;
/*     */           }
/* 521 */           slotClicked($$3, $$7, $$2, $$11 ? ClickType.QUICK_MOVE : ClickType.PICKUP);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 526 */     if (this.menu.getCarried().isEmpty()) {
/* 527 */       this.lastClickTime = 0L;
/*     */     }
/*     */     
/* 530 */     this.isQuickCrafting = false;
/*     */     
/* 532 */     return true;
/*     */   }
/*     */   
/*     */   public void clearDraggingState() {
/* 536 */     this.draggingItem = ItemStack.EMPTY;
/* 537 */     this.clickedSlot = null;
/*     */   }
/*     */   
/*     */   private boolean isHovering(Slot $$0, double $$1, double $$2) {
/* 541 */     return isHovering($$0.x, $$0.y, 16, 16, $$1, $$2);
/*     */   }
/*     */   
/*     */   protected boolean isHovering(int $$0, int $$1, int $$2, int $$3, double $$4, double $$5) {
/* 545 */     int $$6 = this.leftPos;
/* 546 */     int $$7 = this.topPos;
/* 547 */     $$4 -= $$6;
/* 548 */     $$5 -= $$7;
/*     */     
/* 550 */     return ($$4 >= ($$0 - 1) && $$4 < ($$0 + $$2 + 1) && $$5 >= ($$1 - 1) && $$5 < ($$1 + $$3 + 1));
/*     */   }
/*     */   
/*     */   protected void slotClicked(Slot $$0, int $$1, int $$2, ClickType $$3) {
/* 554 */     if ($$0 != null) {
/* 555 */       $$1 = $$0.index;
/*     */     }
/* 557 */     this.minecraft.gameMode.handleInventoryMouseClick(((AbstractContainerMenu)this.menu).containerId, $$1, $$2, $$3, (Player)this.minecraft.player);
/*     */   }
/*     */   
/*     */   protected void handleSlotStateChanged(int $$0, int $$1, boolean $$2) {
/* 561 */     this.minecraft.gameMode.handleSlotStateChanged($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean keyPressed(int $$0, int $$1, int $$2) {
/* 566 */     if (super.keyPressed($$0, $$1, $$2)) {
/* 567 */       return true;
/*     */     }
/* 569 */     if (this.minecraft.options.keyInventory.matches($$0, $$1)) {
/* 570 */       onClose();
/* 571 */       return true;
/*     */     } 
/*     */     
/* 574 */     checkHotbarKeyPressed($$0, $$1);
/*     */     
/* 576 */     if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
/* 577 */       if (this.minecraft.options.keyPickItem.matches($$0, $$1)) {
/* 578 */         slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
/* 579 */       } else if (this.minecraft.options.keyDrop.matches($$0, $$1)) {
/* 580 */         slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
/*     */       } 
/*     */     }
/* 583 */     return true;
/*     */   }
/*     */   
/*     */   protected boolean checkHotbarKeyPressed(int $$0, int $$1) {
/* 587 */     if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
/* 588 */       if (this.minecraft.options.keySwapOffhand.matches($$0, $$1)) {
/* 589 */         slotClicked(this.hoveredSlot, this.hoveredSlot.index, 40, ClickType.SWAP);
/* 590 */         return true;
/*     */       } 
/* 592 */       for (int $$2 = 0; $$2 < 9; $$2++) {
/* 593 */         if (this.minecraft.options.keyHotbarSlots[$$2].matches($$0, $$1)) {
/* 594 */           slotClicked(this.hoveredSlot, this.hoveredSlot.index, $$2, ClickType.SWAP);
/* 595 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 600 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed() {
/* 605 */     if (this.minecraft.player == null) {
/*     */       return;
/*     */     }
/* 608 */     this.menu.removed((Player)this.minecraft.player);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPauseScreen() {
/* 613 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final void tick() {
/* 618 */     super.tick();
/*     */ 
/*     */     
/* 621 */     if (!this.minecraft.player.isAlive() || this.minecraft.player.isRemoved()) {
/* 622 */       this.minecraft.player.closeContainer();
/*     */     } else {
/* 624 */       containerTick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void containerTick() {}
/*     */ 
/*     */   
/*     */   public T getMenu() {
/* 633 */     return this.menu;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 638 */     this.minecraft.player.closeContainer();
/* 639 */     super.onClose();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\screens\inventory\AbstractContainerScreen.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */