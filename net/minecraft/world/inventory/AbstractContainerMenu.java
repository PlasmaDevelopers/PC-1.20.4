/*     */ package net.minecraft.world.inventory;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.HashBasedTable;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.OptionalInt;
/*     */ import java.util.Set;
/*     */ import java.util.function.Supplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public abstract class AbstractContainerMenu {
/*  37 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static final int SLOT_CLICKED_OUTSIDE = -999;
/*     */   
/*     */   public static final int QUICKCRAFT_TYPE_CHARITABLE = 0;
/*     */   
/*     */   public static final int QUICKCRAFT_TYPE_GREEDY = 1;
/*     */   
/*     */   public static final int QUICKCRAFT_TYPE_CLONE = 2;
/*     */   public static final int QUICKCRAFT_HEADER_START = 0;
/*     */   public static final int QUICKCRAFT_HEADER_CONTINUE = 1;
/*     */   public static final int QUICKCRAFT_HEADER_END = 2;
/*     */   public static final int CARRIED_SLOT_SIZE = 2147483647;
/*  50 */   private final NonNullList<ItemStack> lastSlots = NonNullList.create();
/*  51 */   public final NonNullList<Slot> slots = NonNullList.create();
/*  52 */   private final List<DataSlot> dataSlots = Lists.newArrayList();
/*  53 */   private ItemStack carried = ItemStack.EMPTY;
/*     */ 
/*     */   
/*  56 */   private final NonNullList<ItemStack> remoteSlots = NonNullList.create();
/*  57 */   private final IntList remoteDataSlots = (IntList)new IntArrayList();
/*  58 */   private ItemStack remoteCarried = ItemStack.EMPTY;
/*     */   
/*     */   private int stateId;
/*     */   
/*     */   @Nullable
/*     */   private final MenuType<?> menuType;
/*     */   
/*     */   public final int containerId;
/*  66 */   private int quickcraftType = -1;
/*     */   private int quickcraftStatus;
/*  68 */   private final Set<Slot> quickcraftSlots = Sets.newHashSet();
/*     */   
/*  70 */   private final List<ContainerListener> containerListeners = Lists.newArrayList();
/*     */   @Nullable
/*     */   private ContainerSynchronizer synchronizer;
/*     */   private boolean suppressRemoteUpdates;
/*     */   
/*     */   protected AbstractContainerMenu(@Nullable MenuType<?> $$0, int $$1) {
/*  76 */     this.menuType = $$0;
/*  77 */     this.containerId = $$1;
/*     */   }
/*     */   
/*     */   protected static boolean stillValid(ContainerLevelAccess $$0, Player $$1, Block $$2) {
/*  81 */     return ((Boolean)$$0.<Boolean>evaluate(($$2, $$3) -> !$$2.getBlockState($$3).is($$0) ? Boolean.valueOf(false) : Boolean.valueOf(($$1.distanceToSqr($$3.getX() + 0.5D, $$3.getY() + 0.5D, $$3.getZ() + 0.5D) <= 64.0D)), 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  86 */         Boolean.valueOf(true))).booleanValue();
/*     */   }
/*     */   
/*     */   public MenuType<?> getType() {
/*  90 */     if (this.menuType == null) {
/*  91 */       throw new UnsupportedOperationException("Unable to construct this menu by type");
/*     */     }
/*  93 */     return this.menuType;
/*     */   }
/*     */   
/*     */   protected static void checkContainerSize(Container $$0, int $$1) {
/*  97 */     int $$2 = $$0.getContainerSize();
/*  98 */     if ($$2 < $$1) {
/*  99 */       throw new IllegalArgumentException("Container size " + $$2 + " is smaller than expected " + $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static void checkContainerDataCount(ContainerData $$0, int $$1) {
/* 104 */     int $$2 = $$0.getCount();
/* 105 */     if ($$2 < $$1) {
/* 106 */       throw new IllegalArgumentException("Container data count " + $$2 + " is smaller than expected " + $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isValidSlotIndex(int $$0) {
/* 111 */     return ($$0 == -1 || $$0 == -999 || $$0 < this.slots.size());
/*     */   }
/*     */   
/*     */   protected Slot addSlot(Slot $$0) {
/* 115 */     $$0.index = this.slots.size();
/* 116 */     this.slots.add($$0);
/* 117 */     this.lastSlots.add(ItemStack.EMPTY);
/* 118 */     this.remoteSlots.add(ItemStack.EMPTY);
/* 119 */     return $$0;
/*     */   }
/*     */   
/*     */   protected DataSlot addDataSlot(DataSlot $$0) {
/* 123 */     this.dataSlots.add($$0);
/* 124 */     this.remoteDataSlots.add(0);
/* 125 */     return $$0;
/*     */   }
/*     */   
/*     */   protected void addDataSlots(ContainerData $$0) {
/* 129 */     for (int $$1 = 0; $$1 < $$0.getCount(); $$1++) {
/* 130 */       addDataSlot(DataSlot.forContainer($$0, $$1));
/*     */     }
/*     */   }
/*     */   
/*     */   public void addSlotListener(ContainerListener $$0) {
/* 135 */     if (this.containerListeners.contains($$0)) {
/*     */       return;
/*     */     }
/* 138 */     this.containerListeners.add($$0);
/* 139 */     broadcastChanges();
/*     */   }
/*     */   
/*     */   public void setSynchronizer(ContainerSynchronizer $$0) {
/* 143 */     this.synchronizer = $$0;
/* 144 */     sendAllDataToRemote();
/*     */   }
/*     */   
/*     */   public void sendAllDataToRemote() {
/* 148 */     for (int $$0 = 0, $$1 = this.slots.size(); $$0 < $$1; $$0++) {
/* 149 */       this.remoteSlots.set($$0, ((Slot)this.slots.get($$0)).getItem().copy());
/*     */     }
/* 151 */     this.remoteCarried = getCarried().copy();
/* 152 */     for (int $$2 = 0, $$3 = this.dataSlots.size(); $$2 < $$3; $$2++) {
/* 153 */       this.remoteDataSlots.set($$2, ((DataSlot)this.dataSlots.get($$2)).get());
/*     */     }
/* 155 */     if (this.synchronizer != null) {
/* 156 */       this.synchronizer.sendInitialData(this, this.remoteSlots, this.remoteCarried, this.remoteDataSlots.toIntArray());
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeSlotListener(ContainerListener $$0) {
/* 161 */     this.containerListeners.remove($$0);
/*     */   }
/*     */   
/*     */   public NonNullList<ItemStack> getItems() {
/* 165 */     NonNullList<ItemStack> $$0 = NonNullList.create();
/* 166 */     for (Slot $$1 : this.slots) {
/* 167 */       $$0.add($$1.getItem());
/*     */     }
/* 169 */     return $$0;
/*     */   }
/*     */   
/*     */   public void broadcastChanges() {
/* 173 */     for (int $$0 = 0; $$0 < this.slots.size(); $$0++) {
/* 174 */       ItemStack $$1 = ((Slot)this.slots.get($$0)).getItem();
/* 175 */       Objects.requireNonNull($$1); Supplier supplier = Suppliers.memoize($$1::copy);
/* 176 */       triggerSlotListeners($$0, $$1, (Supplier<ItemStack>)supplier);
/* 177 */       synchronizeSlotToRemote($$0, $$1, (Supplier<ItemStack>)supplier);
/*     */     } 
/*     */     
/* 180 */     synchronizeCarriedToRemote();
/*     */     
/* 182 */     for (int $$3 = 0; $$3 < this.dataSlots.size(); $$3++) {
/* 183 */       DataSlot $$4 = this.dataSlots.get($$3);
/* 184 */       int $$5 = $$4.get();
/* 185 */       if ($$4.checkAndClearUpdateFlag()) {
/* 186 */         updateDataSlotListeners($$3, $$5);
/*     */       }
/* 188 */       synchronizeDataSlotToRemote($$3, $$5);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void broadcastFullState() {
/* 193 */     for (int $$0 = 0; $$0 < this.slots.size(); $$0++) {
/* 194 */       ItemStack $$1 = ((Slot)this.slots.get($$0)).getItem();
/* 195 */       Objects.requireNonNull($$1); triggerSlotListeners($$0, $$1, $$1::copy);
/*     */     } 
/*     */     
/* 198 */     for (int $$2 = 0; $$2 < this.dataSlots.size(); $$2++) {
/* 199 */       DataSlot $$3 = this.dataSlots.get($$2);
/* 200 */       if ($$3.checkAndClearUpdateFlag()) {
/* 201 */         updateDataSlotListeners($$2, $$3.get());
/*     */       }
/*     */     } 
/* 204 */     sendAllDataToRemote();
/*     */   }
/*     */   
/*     */   private void updateDataSlotListeners(int $$0, int $$1) {
/* 208 */     for (ContainerListener $$2 : this.containerListeners) {
/* 209 */       $$2.dataChanged(this, $$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private void triggerSlotListeners(int $$0, ItemStack $$1, Supplier<ItemStack> $$2) {
/* 214 */     ItemStack $$3 = (ItemStack)this.lastSlots.get($$0);
/* 215 */     if (!ItemStack.matches($$3, $$1)) {
/* 216 */       ItemStack $$4 = $$2.get();
/* 217 */       this.lastSlots.set($$0, $$4);
/* 218 */       for (ContainerListener $$5 : this.containerListeners) {
/* 219 */         $$5.slotChanged(this, $$0, $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void synchronizeSlotToRemote(int $$0, ItemStack $$1, Supplier<ItemStack> $$2) {
/* 225 */     if (this.suppressRemoteUpdates) {
/*     */       return;
/*     */     }
/*     */     
/* 229 */     ItemStack $$3 = (ItemStack)this.remoteSlots.get($$0);
/* 230 */     if (!ItemStack.matches($$3, $$1)) {
/* 231 */       ItemStack $$4 = $$2.get();
/* 232 */       this.remoteSlots.set($$0, $$4);
/* 233 */       if (this.synchronizer != null) {
/* 234 */         this.synchronizer.sendSlotChange(this, $$0, $$4);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void synchronizeDataSlotToRemote(int $$0, int $$1) {
/* 240 */     if (this.suppressRemoteUpdates) {
/*     */       return;
/*     */     }
/*     */     
/* 244 */     int $$2 = this.remoteDataSlots.getInt($$0);
/* 245 */     if ($$2 != $$1) {
/* 246 */       this.remoteDataSlots.set($$0, $$1);
/* 247 */       if (this.synchronizer != null) {
/* 248 */         this.synchronizer.sendDataChange(this, $$0, $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void synchronizeCarriedToRemote() {
/* 254 */     if (this.suppressRemoteUpdates) {
/*     */       return;
/*     */     }
/*     */     
/* 258 */     if (!ItemStack.matches(getCarried(), this.remoteCarried)) {
/* 259 */       this.remoteCarried = getCarried().copy();
/* 260 */       if (this.synchronizer != null) {
/* 261 */         this.synchronizer.sendCarriedChange(this, this.remoteCarried);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setRemoteSlot(int $$0, ItemStack $$1) {
/* 267 */     this.remoteSlots.set($$0, $$1.copy());
/*     */   }
/*     */   
/*     */   public void setRemoteSlotNoCopy(int $$0, ItemStack $$1) {
/* 271 */     if ($$0 < 0 || $$0 >= this.remoteSlots.size()) {
/* 272 */       LOGGER.debug("Incorrect slot index: {} available slots: {}", Integer.valueOf($$0), Integer.valueOf(this.remoteSlots.size()));
/*     */       return;
/*     */     } 
/* 275 */     this.remoteSlots.set($$0, $$1);
/*     */   }
/*     */   
/*     */   public void setRemoteCarried(ItemStack $$0) {
/* 279 */     this.remoteCarried = $$0.copy();
/*     */   }
/*     */   
/*     */   public boolean clickMenuButton(Player $$0, int $$1) {
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   public Slot getSlot(int $$0) {
/* 287 */     return (Slot)this.slots.get($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clicked(int $$0, int $$1, ClickType $$2, Player $$3) {
/*     */     try {
/* 294 */       doClick($$0, $$1, $$2, $$3);
/* 295 */     } catch (Exception $$4) {
/* 296 */       CrashReport $$5 = CrashReport.forThrowable($$4, "Container click");
/* 297 */       CrashReportCategory $$6 = $$5.addCategory("Click info");
/* 298 */       $$6.setDetail("Menu Type", () -> (this.menuType != null) ? BuiltInRegistries.MENU.getKey(this.menuType).toString() : "<no type>");
/* 299 */       $$6.setDetail("Menu Class", () -> getClass().getCanonicalName());
/* 300 */       $$6.setDetail("Slot Count", Integer.valueOf(this.slots.size()));
/* 301 */       $$6.setDetail("Slot", Integer.valueOf($$0));
/* 302 */       $$6.setDetail("Button", Integer.valueOf($$1));
/* 303 */       $$6.setDetail("Type", $$2);
/* 304 */       throw new ReportedException($$5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doClick(int $$0, int $$1, ClickType $$2, Player $$3) {
/* 310 */     Inventory $$4 = $$3.getInventory();
/*     */     
/* 312 */     if ($$2 == ClickType.QUICK_CRAFT) {
/* 313 */       int $$5 = this.quickcraftStatus;
/* 314 */       this.quickcraftStatus = getQuickcraftHeader($$1);
/*     */       
/* 316 */       if (($$5 != 1 || this.quickcraftStatus != 2) && $$5 != this.quickcraftStatus) {
/* 317 */         resetQuickCraft();
/* 318 */       } else if (getCarried().isEmpty()) {
/* 319 */         resetQuickCraft();
/* 320 */       } else if (this.quickcraftStatus == 0) {
/* 321 */         this.quickcraftType = getQuickcraftType($$1);
/*     */         
/* 323 */         if (isValidQuickcraftType(this.quickcraftType, $$3)) {
/* 324 */           this.quickcraftStatus = 1;
/* 325 */           this.quickcraftSlots.clear();
/*     */         } else {
/* 327 */           resetQuickCraft();
/*     */         } 
/* 329 */       } else if (this.quickcraftStatus == 1) {
/* 330 */         Slot $$6 = (Slot)this.slots.get($$0);
/*     */         
/* 332 */         ItemStack $$7 = getCarried();
/* 333 */         if (canItemQuickReplace($$6, $$7, true) && $$6.mayPlace($$7) && (this.quickcraftType == 2 || $$7.getCount() > this.quickcraftSlots.size()) && canDragTo($$6)) {
/* 334 */           this.quickcraftSlots.add($$6);
/*     */         }
/* 336 */       } else if (this.quickcraftStatus == 2) {
/* 337 */         if (!this.quickcraftSlots.isEmpty()) {
/* 338 */           if (this.quickcraftSlots.size() == 1) {
/*     */             
/* 340 */             int $$8 = ((Slot)this.quickcraftSlots.iterator().next()).index;
/* 341 */             resetQuickCraft();
/* 342 */             doClick($$8, this.quickcraftType, ClickType.PICKUP, $$3);
/*     */             return;
/*     */           } 
/* 345 */           ItemStack $$9 = getCarried().copy();
/* 346 */           if ($$9.isEmpty()) {
/* 347 */             resetQuickCraft();
/*     */             return;
/*     */           } 
/* 350 */           int $$10 = getCarried().getCount();
/*     */           
/* 352 */           for (Slot $$11 : this.quickcraftSlots) {
/* 353 */             ItemStack $$12 = getCarried();
/* 354 */             if ($$11 != null && canItemQuickReplace($$11, $$12, true) && $$11.mayPlace($$12) && (this.quickcraftType == 2 || $$12.getCount() >= this.quickcraftSlots.size()) && canDragTo($$11)) {
/* 355 */               int $$13 = $$11.hasItem() ? $$11.getItem().getCount() : 0;
/* 356 */               int $$14 = Math.min($$9.getMaxStackSize(), $$11.getMaxStackSize($$9));
/* 357 */               int $$15 = Math.min(getQuickCraftPlaceCount(this.quickcraftSlots, this.quickcraftType, $$9) + $$13, $$14);
/*     */               
/* 359 */               $$10 -= $$15 - $$13;
/* 360 */               $$11.setByPlayer($$9.copyWithCount($$15));
/*     */             } 
/*     */           } 
/*     */           
/* 364 */           $$9.setCount($$10);
/* 365 */           setCarried($$9);
/*     */         } 
/*     */         
/* 368 */         resetQuickCraft();
/*     */       } else {
/* 370 */         resetQuickCraft();
/*     */       } 
/* 372 */     } else if (this.quickcraftStatus != 0) {
/* 373 */       resetQuickCraft();
/* 374 */     } else if (($$2 == ClickType.PICKUP || $$2 == ClickType.QUICK_MOVE) && ($$1 == 0 || $$1 == 1)) {
/* 375 */       ClickAction $$16 = ($$1 == 0) ? ClickAction.PRIMARY : ClickAction.SECONDARY;
/* 376 */       if ($$0 == -999) {
/* 377 */         if (!getCarried().isEmpty()) {
/* 378 */           if ($$16 == ClickAction.PRIMARY) {
/* 379 */             $$3.drop(getCarried(), true);
/* 380 */             setCarried(ItemStack.EMPTY);
/*     */           } else {
/* 382 */             $$3.drop(getCarried().split(1), true);
/*     */           } 
/*     */         }
/* 385 */       } else if ($$2 == ClickType.QUICK_MOVE) {
/* 386 */         if ($$0 < 0) {
/*     */           return;
/*     */         }
/* 389 */         Slot $$17 = (Slot)this.slots.get($$0);
/* 390 */         if (!$$17.mayPickup($$3)) {
/*     */           return;
/*     */         }
/*     */         
/* 394 */         ItemStack $$18 = quickMoveStack($$3, $$0);
/* 395 */         while (!$$18.isEmpty() && ItemStack.isSameItem($$17.getItem(), $$18)) {
/* 396 */           $$18 = quickMoveStack($$3, $$0);
/*     */         }
/*     */       } else {
/* 399 */         if ($$0 < 0) {
/*     */           return;
/*     */         }
/*     */         
/* 403 */         Slot $$19 = (Slot)this.slots.get($$0);
/* 404 */         ItemStack $$20 = $$19.getItem();
/* 405 */         ItemStack $$21 = getCarried();
/*     */         
/* 407 */         $$3.updateTutorialInventoryAction($$21, $$19.getItem(), $$16);
/* 408 */         if (!tryItemClickBehaviourOverride($$3, $$16, $$19, $$20, $$21)) {
/* 409 */           if ($$20.isEmpty()) {
/* 410 */             if (!$$21.isEmpty()) {
/* 411 */               int $$22 = ($$16 == ClickAction.PRIMARY) ? $$21.getCount() : 1;
/* 412 */               setCarried($$19.safeInsert($$21, $$22));
/*     */             } 
/* 414 */           } else if ($$19.mayPickup($$3)) {
/*     */             
/* 416 */             if ($$21.isEmpty()) {
/* 417 */               int $$23 = ($$16 == ClickAction.PRIMARY) ? $$20.getCount() : (($$20.getCount() + 1) / 2);
/* 418 */               Optional<ItemStack> $$24 = $$19.tryRemove($$23, 2147483647, $$3);
/* 419 */               $$24.ifPresent($$2 -> {
/*     */                     setCarried($$2);
/*     */                     $$0.onTake($$1, $$2);
/*     */                   });
/* 423 */             } else if ($$19.mayPlace($$21)) {
/*     */               
/* 425 */               if (ItemStack.isSameItemSameTags($$20, $$21)) {
/*     */                 
/* 427 */                 int $$25 = ($$16 == ClickAction.PRIMARY) ? $$21.getCount() : 1;
/* 428 */                 setCarried($$19.safeInsert($$21, $$25));
/*     */               
/*     */               }
/* 431 */               else if ($$21.getCount() <= $$19.getMaxStackSize($$21)) {
/* 432 */                 setCarried($$20);
/* 433 */                 $$19.setByPlayer($$21);
/*     */               }
/*     */             
/* 436 */             } else if (ItemStack.isSameItemSameTags($$20, $$21)) {
/* 437 */               Optional<ItemStack> $$26 = $$19.tryRemove($$20.getCount(), $$21.getMaxStackSize() - $$21.getCount(), $$3);
/* 438 */               $$26.ifPresent($$3 -> {
/*     */                     $$0.grow($$3.getCount());
/*     */                     $$1.onTake($$2, $$3);
/*     */                   });
/*     */             } 
/*     */           } 
/*     */         }
/* 445 */         $$19.setChanged();
/*     */       } 
/* 447 */     } else if ($$2 == ClickType.SWAP && (($$1 >= 0 && $$1 < 9) || $$1 == 40)) {
/*     */ 
/*     */       
/* 450 */       ItemStack $$27 = $$4.getItem($$1);
/* 451 */       Slot $$28 = (Slot)this.slots.get($$0);
/*     */       
/* 453 */       ItemStack $$29 = $$28.getItem();
/* 454 */       if (!$$27.isEmpty() || !$$29.isEmpty())
/*     */       {
/* 456 */         if ($$27.isEmpty()) {
/*     */           
/* 458 */           if ($$28.mayPickup($$3)) {
/* 459 */             $$4.setItem($$1, $$29);
/* 460 */             $$28.onSwapCraft($$29.getCount());
/* 461 */             $$28.setByPlayer(ItemStack.EMPTY);
/* 462 */             $$28.onTake($$3, $$29);
/*     */           } 
/* 464 */         } else if ($$29.isEmpty()) {
/* 465 */           if ($$28.mayPlace($$27)) {
/* 466 */             int $$30 = $$28.getMaxStackSize($$27);
/* 467 */             if ($$27.getCount() > $$30) {
/*     */               
/* 469 */               $$28.setByPlayer($$27.split($$30));
/*     */             } else {
/*     */               
/* 472 */               $$4.setItem($$1, ItemStack.EMPTY);
/* 473 */               $$28.setByPlayer($$27);
/*     */             } 
/*     */           } 
/* 476 */         } else if ($$28.mayPickup($$3) && $$28.mayPlace($$27)) {
/*     */ 
/*     */           
/* 479 */           int $$31 = $$28.getMaxStackSize($$27);
/* 480 */           if ($$27.getCount() > $$31) {
/* 481 */             $$28.setByPlayer($$27.split($$31));
/* 482 */             $$28.onTake($$3, $$29);
/* 483 */             if (!$$4.add($$29)) {
/* 484 */               $$3.drop($$29, true);
/*     */             }
/*     */           } else {
/* 487 */             $$4.setItem($$1, $$29);
/* 488 */             $$28.setByPlayer($$27);
/* 489 */             $$28.onTake($$3, $$29);
/*     */           } 
/*     */         }  } 
/* 492 */     } else if ($$2 == ClickType.CLONE && ($$3.getAbilities()).instabuild && getCarried().isEmpty() && $$0 >= 0) {
/* 493 */       Slot $$32 = (Slot)this.slots.get($$0);
/* 494 */       if ($$32.hasItem()) {
/* 495 */         ItemStack $$33 = $$32.getItem();
/* 496 */         setCarried($$33.copyWithCount($$33.getMaxStackSize()));
/*     */       } 
/* 498 */     } else if ($$2 == ClickType.THROW && getCarried().isEmpty() && $$0 >= 0) {
/* 499 */       Slot $$34 = (Slot)this.slots.get($$0);
/* 500 */       int $$35 = ($$1 == 0) ? 1 : $$34.getItem().getCount();
/* 501 */       ItemStack $$36 = $$34.safeTake($$35, 2147483647, $$3);
/* 502 */       $$3.drop($$36, true);
/* 503 */     } else if ($$2 == ClickType.PICKUP_ALL && $$0 >= 0) {
/* 504 */       Slot $$37 = (Slot)this.slots.get($$0);
/* 505 */       ItemStack $$38 = getCarried();
/*     */       
/* 507 */       if (!$$38.isEmpty() && (!$$37.hasItem() || !$$37.mayPickup($$3))) {
/* 508 */         int $$39 = ($$1 == 0) ? 0 : (this.slots.size() - 1);
/* 509 */         int $$40 = ($$1 == 0) ? 1 : -1;
/*     */         
/* 511 */         for (int $$41 = 0; $$41 < 2; $$41++) {
/*     */           int $$42;
/* 513 */           for ($$42 = $$39; $$42 >= 0 && $$42 < this.slots.size() && $$38.getCount() < $$38.getMaxStackSize(); $$42 += $$40) {
/* 514 */             Slot $$43 = (Slot)this.slots.get($$42);
/*     */             
/* 516 */             if ($$43.hasItem() && canItemQuickReplace($$43, $$38, true) && $$43.mayPickup($$3) && canTakeItemForPickAll($$38, $$43)) {
/* 517 */               ItemStack $$44 = $$43.getItem();
/* 518 */               if ($$41 != 0 || $$44.getCount() != $$44.getMaxStackSize()) {
/*     */ 
/*     */                 
/* 521 */                 ItemStack $$45 = $$43.safeTake($$44.getCount(), $$38.getMaxStackSize() - $$38.getCount(), $$3);
/* 522 */                 $$38.grow($$45.getCount());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private boolean tryItemClickBehaviourOverride(Player $$0, ClickAction $$1, Slot $$2, ItemStack $$3, ItemStack $$4) {
/* 531 */     FeatureFlagSet $$5 = $$0.level().enabledFeatures();
/* 532 */     if ($$4.isItemEnabled($$5) && $$4.overrideStackedOnOther($$2, $$1, $$0)) {
/* 533 */       return true;
/*     */     }
/* 535 */     return ($$3.isItemEnabled($$5) && $$3.overrideOtherStackedOnMe($$4, $$2, $$1, $$0, createCarriedSlotAccess()));
/*     */   }
/*     */   
/*     */   private SlotAccess createCarriedSlotAccess() {
/* 539 */     return new SlotAccess()
/*     */       {
/*     */         public ItemStack get() {
/* 542 */           return AbstractContainerMenu.this.getCarried();
/*     */         }
/*     */ 
/*     */         
/*     */         public boolean set(ItemStack $$0) {
/* 547 */           AbstractContainerMenu.this.setCarried($$0);
/* 548 */           return true;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 554 */     return true;
/*     */   }
/*     */   
/*     */   public void removed(Player $$0) {
/* 558 */     if ($$0 instanceof ServerPlayer) {
/* 559 */       ItemStack $$1 = getCarried();
/* 560 */       if (!$$1.isEmpty()) {
/* 561 */         if (!$$0.isAlive() || ((ServerPlayer)$$0).hasDisconnected()) {
/* 562 */           $$0.drop($$1, false);
/*     */         } else {
/* 564 */           $$0.getInventory().placeItemBackInInventory($$1);
/*     */         } 
/* 566 */         setCarried(ItemStack.EMPTY);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void clearContainer(Player $$0, Container $$1) {
/* 572 */     if (!$$0.isAlive() || ($$0 instanceof ServerPlayer && ((ServerPlayer)$$0).hasDisconnected())) {
/* 573 */       for (int $$2 = 0; $$2 < $$1.getContainerSize(); $$2++) {
/* 574 */         $$0.drop($$1.removeItemNoUpdate($$2), false);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 579 */     for (int $$3 = 0; $$3 < $$1.getContainerSize(); $$3++) {
/* 580 */       Inventory $$4 = $$0.getInventory();
/* 581 */       if ($$4.player instanceof ServerPlayer) {
/* 582 */         $$4.placeItemBackInInventory($$1.removeItemNoUpdate($$3));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 589 */     broadcastChanges();
/*     */   }
/*     */   
/*     */   public void setItem(int $$0, int $$1, ItemStack $$2) {
/* 593 */     getSlot($$0).set($$2);
/* 594 */     this.stateId = $$1;
/*     */   }
/*     */   
/*     */   public void initializeContents(int $$0, List<ItemStack> $$1, ItemStack $$2) {
/* 598 */     for (int $$3 = 0; $$3 < $$1.size(); $$3++) {
/* 599 */       getSlot($$3).set($$1.get($$3));
/*     */     }
/* 601 */     this.carried = $$2;
/* 602 */     this.stateId = $$0;
/*     */   }
/*     */   
/*     */   public void setData(int $$0, int $$1) {
/* 606 */     ((DataSlot)this.dataSlots.get($$0)).set($$1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean moveItemStackTo(ItemStack $$0, int $$1, int $$2, boolean $$3) {
/* 612 */     boolean $$4 = false;
/*     */     
/* 614 */     int $$5 = $$1;
/* 615 */     if ($$3) {
/* 616 */       $$5 = $$2 - 1;
/*     */     }
/*     */ 
/*     */     
/* 620 */     if ($$0.isStackable()) {
/* 621 */       while (!$$0.isEmpty() && ($$3 ? ($$5 >= $$1) : ($$5 < $$2))) {
/* 622 */         Slot $$6 = (Slot)this.slots.get($$5);
/* 623 */         ItemStack $$7 = $$6.getItem();
/* 624 */         if (!$$7.isEmpty() && ItemStack.isSameItemSameTags($$0, $$7)) {
/* 625 */           int $$8 = $$7.getCount() + $$0.getCount();
/* 626 */           if ($$8 <= $$0.getMaxStackSize()) {
/* 627 */             $$0.setCount(0);
/* 628 */             $$7.setCount($$8);
/* 629 */             $$6.setChanged();
/* 630 */             $$4 = true;
/* 631 */           } else if ($$7.getCount() < $$0.getMaxStackSize()) {
/* 632 */             $$0.shrink($$0.getMaxStackSize() - $$7.getCount());
/* 633 */             $$7.setCount($$0.getMaxStackSize());
/* 634 */             $$6.setChanged();
/* 635 */             $$4 = true;
/*     */           } 
/*     */         } 
/*     */         
/* 639 */         if ($$3) {
/* 640 */           $$5--; continue;
/*     */         } 
/* 642 */         $$5++;
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 648 */     if (!$$0.isEmpty()) {
/* 649 */       if ($$3) {
/* 650 */         $$5 = $$2 - 1;
/*     */       } else {
/* 652 */         $$5 = $$1;
/*     */       } 
/* 654 */       while ($$3 ? ($$5 >= $$1) : ($$5 < $$2)) {
/* 655 */         Slot $$9 = (Slot)this.slots.get($$5);
/* 656 */         ItemStack $$10 = $$9.getItem();
/*     */         
/* 658 */         if ($$10.isEmpty() && $$9.mayPlace($$0)) {
/* 659 */           if ($$0.getCount() > $$9.getMaxStackSize()) {
/* 660 */             $$9.setByPlayer($$0.split($$9.getMaxStackSize()));
/*     */           } else {
/* 662 */             $$9.setByPlayer($$0.split($$0.getCount()));
/*     */           } 
/* 664 */           $$9.setChanged();
/* 665 */           $$4 = true;
/*     */           
/*     */           break;
/*     */         } 
/* 669 */         if ($$3) {
/* 670 */           $$5--; continue;
/*     */         } 
/* 672 */         $$5++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 677 */     return $$4;
/*     */   }
/*     */   
/*     */   public static int getQuickcraftType(int $$0) {
/* 681 */     return $$0 >> 2 & 0x3;
/*     */   }
/*     */   
/*     */   public static int getQuickcraftHeader(int $$0) {
/* 685 */     return $$0 & 0x3;
/*     */   }
/*     */   
/*     */   public static int getQuickcraftMask(int $$0, int $$1) {
/* 689 */     return $$0 & 0x3 | ($$1 & 0x3) << 2;
/*     */   }
/*     */   
/*     */   public static boolean isValidQuickcraftType(int $$0, Player $$1) {
/* 693 */     if ($$0 == 0) {
/* 694 */       return true;
/*     */     }
/* 696 */     if ($$0 == 1) {
/* 697 */       return true;
/*     */     }
/* 699 */     if ($$0 == 2 && ($$1.getAbilities()).instabuild) {
/* 700 */       return true;
/*     */     }
/* 702 */     return false;
/*     */   }
/*     */   
/*     */   protected void resetQuickCraft() {
/* 706 */     this.quickcraftStatus = 0;
/* 707 */     this.quickcraftSlots.clear();
/*     */   }
/*     */   
/*     */   public static boolean canItemQuickReplace(@Nullable Slot $$0, ItemStack $$1, boolean $$2) {
/* 711 */     boolean $$3 = ($$0 == null || !$$0.hasItem());
/*     */     
/* 713 */     if (!$$3 && ItemStack.isSameItemSameTags($$1, $$0.getItem())) {
/* 714 */       return ($$0.getItem().getCount() + ($$2 ? 0 : $$1.getCount()) <= $$1.getMaxStackSize());
/*     */     }
/*     */     
/* 717 */     return $$3;
/*     */   }
/*     */   
/*     */   public static int getQuickCraftPlaceCount(Set<Slot> $$0, int $$1, ItemStack $$2) {
/* 721 */     switch ($$1) { case 0: case 1: case 2:  }  return 
/*     */ 
/*     */ 
/*     */       
/* 725 */       $$2.getCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canDragTo(Slot $$0) {
/* 730 */     return true;
/*     */   }
/*     */   
/*     */   public static int getRedstoneSignalFromBlockEntity(@Nullable BlockEntity $$0) {
/* 734 */     if ($$0 instanceof Container) {
/* 735 */       return getRedstoneSignalFromContainer((Container)$$0);
/*     */     }
/*     */     
/* 738 */     return 0;
/*     */   }
/*     */   
/*     */   public static int getRedstoneSignalFromContainer(@Nullable Container $$0) {
/* 742 */     if ($$0 == null) {
/* 743 */       return 0;
/*     */     }
/* 745 */     float $$1 = 0.0F;
/* 746 */     for (int $$2 = 0; $$2 < $$0.getContainerSize(); $$2++) {
/* 747 */       ItemStack $$3 = $$0.getItem($$2);
/* 748 */       if (!$$3.isEmpty()) {
/* 749 */         $$1 += $$3.getCount() / Math.min($$0.getMaxStackSize(), $$3.getMaxStackSize());
/*     */       }
/*     */     } 
/*     */     
/* 753 */     $$1 /= $$0.getContainerSize();
/* 754 */     return Mth.lerpDiscrete($$1, 0, 15);
/*     */   }
/*     */   
/*     */   public void setCarried(ItemStack $$0) {
/* 758 */     this.carried = $$0;
/*     */   }
/*     */   
/*     */   public ItemStack getCarried() {
/* 762 */     return this.carried;
/*     */   }
/*     */   
/*     */   public void suppressRemoteUpdates() {
/* 766 */     this.suppressRemoteUpdates = true;
/*     */   }
/*     */   
/*     */   public void resumeRemoteUpdates() {
/* 770 */     this.suppressRemoteUpdates = false;
/*     */   }
/*     */   
/*     */   public void transferState(AbstractContainerMenu $$0) {
/* 774 */     HashBasedTable hashBasedTable = HashBasedTable.create();
/* 775 */     for (int $$2 = 0; $$2 < $$0.slots.size(); $$2++) {
/* 776 */       Slot $$3 = (Slot)$$0.slots.get($$2);
/* 777 */       hashBasedTable.put($$3.container, Integer.valueOf($$3.getContainerSlot()), Integer.valueOf($$2));
/*     */     } 
/*     */     
/* 780 */     for (int $$4 = 0; $$4 < this.slots.size(); $$4++) {
/* 781 */       Slot $$5 = (Slot)this.slots.get($$4);
/* 782 */       Integer $$6 = (Integer)hashBasedTable.get($$5.container, Integer.valueOf($$5.getContainerSlot()));
/* 783 */       if ($$6 != null) {
/* 784 */         this.lastSlots.set($$4, $$0.lastSlots.get($$6.intValue()));
/* 785 */         this.remoteSlots.set($$4, $$0.remoteSlots.get($$6.intValue()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public OptionalInt findSlot(Container $$0, int $$1) {
/* 791 */     for (int $$2 = 0; $$2 < this.slots.size(); $$2++) {
/* 792 */       Slot $$3 = (Slot)this.slots.get($$2);
/* 793 */       if ($$3.container == $$0 && $$1 == $$3.getContainerSlot()) {
/* 794 */         return OptionalInt.of($$2);
/*     */       }
/*     */     } 
/*     */     
/* 798 */     return OptionalInt.empty();
/*     */   }
/*     */   
/*     */   public int getStateId() {
/* 802 */     return this.stateId;
/*     */   }
/*     */ 
/*     */   
/*     */   public int incrementStateId() {
/* 807 */     this.stateId = this.stateId + 1 & 0x7FFF;
/* 808 */     return this.stateId;
/*     */   }
/*     */   
/*     */   public abstract ItemStack quickMoveStack(Player paramPlayer, int paramInt);
/*     */   
/*     */   public abstract boolean stillValid(Player paramPlayer);
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\AbstractContainerMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */