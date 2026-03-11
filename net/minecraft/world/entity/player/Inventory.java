/*     */ package net.minecraft.world.entity.player;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.CrashReport;
/*     */ import net.minecraft.CrashReportCategory;
/*     */ import net.minecraft.ReportedException;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundContainerSetSlotPacket;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class Inventory
/*     */   implements Container, Nameable {
/*     */   public static final int POP_TIME_DURATION = 5;
/*     */   public static final int INVENTORY_SIZE = 36;
/*     */   private static final int SELECTION_SIZE = 9;
/*     */   public static final int SLOT_OFFHAND = 40;
/*     */   public static final int NOT_FOUND_INDEX = -1;
/*  36 */   public static final int[] ALL_ARMOR_SLOTS = new int[] { 0, 1, 2, 3 };
/*  37 */   public static final int[] HELMET_SLOT_ONLY = new int[] { 3 };
/*     */   
/*  39 */   public final NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
/*  40 */   public final NonNullList<ItemStack> armor = NonNullList.withSize(4, ItemStack.EMPTY);
/*  41 */   public final NonNullList<ItemStack> offhand = NonNullList.withSize(1, ItemStack.EMPTY);
/*  42 */   private final List<NonNullList<ItemStack>> compartments = (List<NonNullList<ItemStack>>)ImmutableList.of(this.items, this.armor, this.offhand);
/*     */   
/*     */   public int selected;
/*     */   
/*     */   public final Player player;
/*     */   private int timesChanged;
/*     */   
/*     */   public Inventory(Player $$0) {
/*  50 */     this.player = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getSelected() {
/*  56 */     if (isHotbarSlot(this.selected)) {
/*  57 */       return (ItemStack)this.items.get(this.selected);
/*     */     }
/*  59 */     return ItemStack.EMPTY;
/*     */   }
/*     */   
/*     */   public static int getSelectionSize() {
/*  63 */     return 9;
/*     */   }
/*     */   
/*     */   private boolean hasRemainingSpaceForItem(ItemStack $$0, ItemStack $$1) {
/*  67 */     return (!$$0.isEmpty() && 
/*  68 */       ItemStack.isSameItemSameTags($$0, $$1) && $$0
/*  69 */       .isStackable() && $$0
/*  70 */       .getCount() < $$0.getMaxStackSize() && $$0
/*  71 */       .getCount() < getMaxStackSize());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFreeSlot() {
/*  76 */     for (int $$0 = 0; $$0 < this.items.size(); $$0++) {
/*  77 */       if (((ItemStack)this.items.get($$0)).isEmpty()) {
/*  78 */         return $$0;
/*     */       }
/*     */     } 
/*  81 */     return -1;
/*     */   }
/*     */   
/*     */   public void setPickedItem(ItemStack $$0) {
/*  85 */     int $$1 = findSlotMatchingItem($$0);
/*  86 */     if (isHotbarSlot($$1)) {
/*  87 */       this.selected = $$1;
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     if ($$1 == -1) {
/*  92 */       this.selected = getSuitableHotbarSlot();
/*     */ 
/*     */       
/*  95 */       if (!((ItemStack)this.items.get(this.selected)).isEmpty()) {
/*  96 */         int $$2 = getFreeSlot();
/*  97 */         if ($$2 != -1) {
/*  98 */           this.items.set($$2, this.items.get(this.selected));
/*     */         }
/*     */       } 
/*     */       
/* 102 */       this.items.set(this.selected, $$0);
/*     */     } else {
/* 104 */       pickSlot($$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void pickSlot(int $$0) {
/* 109 */     this.selected = getSuitableHotbarSlot();
/*     */ 
/*     */     
/* 112 */     ItemStack $$1 = (ItemStack)this.items.get(this.selected);
/* 113 */     this.items.set(this.selected, this.items.get($$0));
/* 114 */     this.items.set($$0, $$1);
/*     */   }
/*     */   
/*     */   public static boolean isHotbarSlot(int $$0) {
/* 118 */     return ($$0 >= 0 && $$0 < 9);
/*     */   }
/*     */   
/*     */   public int findSlotMatchingItem(ItemStack $$0) {
/* 122 */     for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
/* 123 */       if (!((ItemStack)this.items.get($$1)).isEmpty() && ItemStack.isSameItemSameTags($$0, (ItemStack)this.items.get($$1))) {
/* 124 */         return $$1;
/*     */       }
/*     */     } 
/* 127 */     return -1;
/*     */   }
/*     */   
/*     */   public int findSlotMatchingUnusedItem(ItemStack $$0) {
/* 131 */     for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
/* 132 */       ItemStack $$2 = (ItemStack)this.items.get($$1);
/* 133 */       if (!((ItemStack)this.items.get($$1)).isEmpty() && 
/* 134 */         ItemStack.isSameItemSameTags($$0, (ItemStack)this.items.get($$1)) && 
/* 135 */         !((ItemStack)this.items.get($$1)).isDamaged() && 
/* 136 */         !$$2.isEnchanted() && 
/* 137 */         !$$2.hasCustomHoverName())
/*     */       {
/* 139 */         return $$1;
/*     */       }
/*     */     } 
/* 142 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSuitableHotbarSlot() {
/* 147 */     for (int $$0 = 0; $$0 < 9; $$0++) {
/* 148 */       int $$1 = (this.selected + $$0) % 9;
/*     */       
/* 150 */       if (((ItemStack)this.items.get($$1)).isEmpty()) {
/* 151 */         return $$1;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 156 */     for (int $$2 = 0; $$2 < 9; $$2++) {
/* 157 */       int $$3 = (this.selected + $$2) % 9;
/*     */       
/* 159 */       if (!((ItemStack)this.items.get($$3)).isEnchanted()) {
/* 160 */         return $$3;
/*     */       }
/*     */     } 
/*     */     
/* 164 */     return this.selected;
/*     */   }
/*     */   
/*     */   public void swapPaint(double $$0) {
/* 168 */     int $$1 = (int)Math.signum($$0);
/* 169 */     this.selected -= $$1;
/*     */     
/* 171 */     while (this.selected < 0) {
/* 172 */       this.selected += 9;
/*     */     }
/* 174 */     while (this.selected >= 9) {
/* 175 */       this.selected -= 9;
/*     */     }
/*     */   }
/*     */   
/*     */   public int clearOrCountMatchingItems(Predicate<ItemStack> $$0, int $$1, Container $$2) {
/* 180 */     int $$3 = 0;
/* 181 */     boolean $$4 = ($$1 == 0);
/*     */     
/* 183 */     $$3 += ContainerHelper.clearOrCountMatchingItems(this, $$0, $$1 - $$3, $$4);
/* 184 */     $$3 += ContainerHelper.clearOrCountMatchingItems($$2, $$0, $$1 - $$3, $$4);
/*     */     
/* 186 */     ItemStack $$5 = this.player.containerMenu.getCarried();
/* 187 */     $$3 += ContainerHelper.clearOrCountMatchingItems($$5, $$0, $$1 - $$3, $$4);
/* 188 */     if ($$5.isEmpty()) {
/* 189 */       this.player.containerMenu.setCarried(ItemStack.EMPTY);
/*     */     }
/* 191 */     return $$3;
/*     */   }
/*     */   
/*     */   private int addResource(ItemStack $$0) {
/* 195 */     int $$1 = getSlotWithRemainingSpace($$0);
/* 196 */     if ($$1 == -1) {
/* 197 */       $$1 = getFreeSlot();
/*     */     }
/* 199 */     if ($$1 == -1) {
/* 200 */       return $$0.getCount();
/*     */     }
/* 202 */     return addResource($$1, $$0);
/*     */   }
/*     */   
/*     */   private int addResource(int $$0, ItemStack $$1) {
/* 206 */     Item $$2 = $$1.getItem();
/* 207 */     int $$3 = $$1.getCount();
/*     */     
/* 209 */     ItemStack $$4 = getItem($$0);
/* 210 */     if ($$4.isEmpty()) {
/* 211 */       $$4 = new ItemStack((ItemLike)$$2, 0);
/* 212 */       if ($$1.hasTag()) {
/* 213 */         $$4.setTag($$1.getTag().copy());
/*     */       }
/* 215 */       setItem($$0, $$4);
/*     */     } 
/*     */     
/* 218 */     int $$5 = $$3;
/* 219 */     if ($$5 > $$4.getMaxStackSize() - $$4.getCount()) {
/* 220 */       $$5 = $$4.getMaxStackSize() - $$4.getCount();
/*     */     }
/* 222 */     if ($$5 > getMaxStackSize() - $$4.getCount()) {
/* 223 */       $$5 = getMaxStackSize() - $$4.getCount();
/*     */     }
/*     */     
/* 226 */     if ($$5 == 0) {
/* 227 */       return $$3;
/*     */     }
/*     */     
/* 230 */     $$3 -= $$5;
/* 231 */     $$4.grow($$5);
/* 232 */     $$4.setPopTime(5);
/*     */     
/* 234 */     return $$3;
/*     */   }
/*     */   
/*     */   public int getSlotWithRemainingSpace(ItemStack $$0) {
/* 238 */     if (hasRemainingSpaceForItem(getItem(this.selected), $$0)) {
/* 239 */       return this.selected;
/*     */     }
/* 241 */     if (hasRemainingSpaceForItem(getItem(40), $$0)) {
/* 242 */       return 40;
/*     */     }
/* 244 */     for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
/* 245 */       if (hasRemainingSpaceForItem((ItemStack)this.items.get($$1), $$0)) {
/* 246 */         return $$1;
/*     */       }
/*     */     } 
/* 249 */     return -1;
/*     */   }
/*     */   
/*     */   public void tick() {
/* 253 */     for (NonNullList<ItemStack> $$0 : this.compartments) {
/* 254 */       for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 255 */         if (!((ItemStack)$$0.get($$1)).isEmpty()) {
/* 256 */           ((ItemStack)$$0.get($$1)).inventoryTick(this.player.level(), (Entity)this.player, $$1, (this.selected == $$1));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean add(ItemStack $$0) {
/* 263 */     return add(-1, $$0);
/*     */   }
/*     */   
/*     */   public boolean add(int $$0, ItemStack $$1) {
/* 267 */     if ($$1.isEmpty()) {
/* 268 */       return false;
/*     */     }
/*     */     
/*     */     try {
/* 272 */       if (!$$1.isDamaged()) {
/*     */         int $$2;
/*     */         do {
/* 275 */           $$2 = $$1.getCount();
/* 276 */           if ($$0 == -1) {
/* 277 */             $$1.setCount(addResource($$1));
/*     */           } else {
/* 279 */             $$1.setCount(addResource($$0, $$1));
/*     */           } 
/* 281 */         } while (!$$1.isEmpty() && $$1.getCount() < $$2);
/* 282 */         if ($$1.getCount() == $$2 && (this.player.getAbilities()).instabuild) {
/*     */           
/* 284 */           $$1.setCount(0);
/* 285 */           return true;
/*     */         } 
/* 287 */         return ($$1.getCount() < $$2);
/*     */       } 
/*     */       
/* 290 */       if ($$0 == -1) {
/* 291 */         $$0 = getFreeSlot();
/*     */       }
/* 293 */       if ($$0 >= 0) {
/* 294 */         this.items.set($$0, $$1.copyAndClear());
/* 295 */         ((ItemStack)this.items.get($$0)).setPopTime(5);
/* 296 */         return true;
/* 297 */       }  if ((this.player.getAbilities()).instabuild) {
/*     */         
/* 299 */         $$1.setCount(0);
/* 300 */         return true;
/*     */       } 
/* 302 */       return false;
/* 303 */     } catch (Throwable $$3) {
/* 304 */       CrashReport $$4 = CrashReport.forThrowable($$3, "Adding item to inventory");
/* 305 */       CrashReportCategory $$5 = $$4.addCategory("Item being added");
/*     */       
/* 307 */       $$5.setDetail("Item ID", Integer.valueOf(Item.getId($$1.getItem())));
/* 308 */       $$5.setDetail("Item data", Integer.valueOf($$1.getDamageValue()));
/* 309 */       $$5.setDetail("Item name", () -> $$0.getHoverName().getString());
/*     */       
/* 311 */       throw new ReportedException($$4);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void placeItemBackInInventory(ItemStack $$0) {
/* 316 */     placeItemBackInInventory($$0, true);
/*     */   }
/*     */   
/*     */   public void placeItemBackInInventory(ItemStack $$0, boolean $$1) {
/* 320 */     while (!$$0.isEmpty()) {
/* 321 */       int $$2 = getSlotWithRemainingSpace($$0);
/* 322 */       if ($$2 == -1) {
/* 323 */         $$2 = getFreeSlot();
/*     */       }
/*     */       
/* 326 */       if ($$2 == -1) {
/* 327 */         this.player.drop($$0, false);
/*     */         
/*     */         break;
/*     */       } 
/* 331 */       int $$3 = $$0.getMaxStackSize() - getItem($$2).getCount();
/*     */       
/* 333 */       if (add($$2, $$0.split($$3)) && $$1 && this.player instanceof ServerPlayer) {
/* 334 */         ((ServerPlayer)this.player).connection.send((Packet)new ClientboundContainerSetSlotPacket(-2, 0, $$2, getItem($$2)));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*     */     NonNullList<ItemStack> nonNullList;
/* 341 */     List<ItemStack> $$2 = null;
/*     */     
/* 343 */     for (NonNullList<ItemStack> $$3 : this.compartments) {
/* 344 */       if ($$0 < $$3.size()) {
/* 345 */         nonNullList = $$3;
/*     */         break;
/*     */       } 
/* 348 */       $$0 -= $$3.size();
/*     */     } 
/*     */ 
/*     */     
/* 352 */     if (nonNullList != null && !((ItemStack)nonNullList.get($$0)).isEmpty()) {
/* 353 */       return ContainerHelper.removeItem((List)nonNullList, $$0, $$1);
/*     */     }
/* 355 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeItem(ItemStack $$0) {
/* 364 */     for (NonNullList<ItemStack> $$1 : this.compartments) {
/* 365 */       for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/* 366 */         if ($$1.get($$2) == $$0) {
/* 367 */           $$1.set($$2, ItemStack.EMPTY);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 376 */     NonNullList<ItemStack> $$1 = null;
/*     */     
/* 378 */     for (NonNullList<ItemStack> $$2 : this.compartments) {
/* 379 */       if ($$0 < $$2.size()) {
/* 380 */         $$1 = $$2;
/*     */         break;
/*     */       } 
/* 383 */       $$0 -= $$2.size();
/*     */     } 
/*     */     
/* 386 */     if ($$1 != null && !((ItemStack)$$1.get($$0)).isEmpty()) {
/* 387 */       ItemStack $$3 = (ItemStack)$$1.get($$0);
/* 388 */       $$1.set($$0, ItemStack.EMPTY);
/* 389 */       return $$3;
/*     */     } 
/* 391 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 396 */     NonNullList<ItemStack> $$2 = null;
/*     */     
/* 398 */     for (NonNullList<ItemStack> $$3 : this.compartments) {
/* 399 */       if ($$0 < $$3.size()) {
/* 400 */         $$2 = $$3;
/*     */         break;
/*     */       } 
/* 403 */       $$0 -= $$3.size();
/*     */     } 
/*     */ 
/*     */     
/* 407 */     if ($$2 != null) {
/* 408 */       $$2.set($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(BlockState $$0) {
/* 413 */     return ((ItemStack)this.items.get(this.selected)).getDestroySpeed($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ListTag save(ListTag $$0) {
/* 418 */     for (int $$1 = 0; $$1 < this.items.size(); $$1++) {
/* 419 */       if (!((ItemStack)this.items.get($$1)).isEmpty()) {
/* 420 */         CompoundTag $$2 = new CompoundTag();
/* 421 */         $$2.putByte("Slot", (byte)$$1);
/* 422 */         ((ItemStack)this.items.get($$1)).save($$2);
/* 423 */         $$0.add($$2);
/*     */       } 
/*     */     } 
/* 426 */     for (int $$3 = 0; $$3 < this.armor.size(); $$3++) {
/* 427 */       if (!((ItemStack)this.armor.get($$3)).isEmpty()) {
/* 428 */         CompoundTag $$4 = new CompoundTag();
/* 429 */         $$4.putByte("Slot", (byte)($$3 + 100));
/* 430 */         ((ItemStack)this.armor.get($$3)).save($$4);
/* 431 */         $$0.add($$4);
/*     */       } 
/*     */     } 
/* 434 */     for (int $$5 = 0; $$5 < this.offhand.size(); $$5++) {
/* 435 */       if (!((ItemStack)this.offhand.get($$5)).isEmpty()) {
/* 436 */         CompoundTag $$6 = new CompoundTag();
/* 437 */         $$6.putByte("Slot", (byte)($$5 + 150));
/* 438 */         ((ItemStack)this.offhand.get($$5)).save($$6);
/* 439 */         $$0.add($$6);
/*     */       } 
/*     */     } 
/* 442 */     return $$0;
/*     */   }
/*     */   
/*     */   public void load(ListTag $$0) {
/* 446 */     this.items.clear();
/* 447 */     this.armor.clear();
/* 448 */     this.offhand.clear();
/* 449 */     for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 450 */       CompoundTag $$2 = $$0.getCompound($$1);
/* 451 */       int $$3 = $$2.getByte("Slot") & 0xFF;
/* 452 */       ItemStack $$4 = ItemStack.of($$2);
/* 453 */       if (!$$4.isEmpty()) {
/* 454 */         if ($$3 >= 0 && $$3 < this.items.size()) {
/* 455 */           this.items.set($$3, $$4);
/* 456 */         } else if ($$3 >= 100 && $$3 < this.armor.size() + 100) {
/* 457 */           this.armor.set($$3 - 100, $$4);
/* 458 */         } else if ($$3 >= 150 && $$3 < this.offhand.size() + 150) {
/* 459 */           this.offhand.set($$3 - 150, $$4);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 467 */     return this.items.size() + this.armor.size() + this.offhand.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 472 */     for (ItemStack $$0 : this.items) {
/* 473 */       if (!$$0.isEmpty()) {
/* 474 */         return false;
/*     */       }
/*     */     } 
/* 477 */     for (ItemStack $$1 : this.armor) {
/* 478 */       if (!$$1.isEmpty()) {
/* 479 */         return false;
/*     */       }
/*     */     } 
/* 482 */     for (ItemStack $$2 : this.offhand) {
/* 483 */       if (!$$2.isEmpty()) {
/* 484 */         return false;
/*     */       }
/*     */     } 
/* 487 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/*     */     NonNullList<ItemStack> nonNullList;
/* 492 */     List<ItemStack> $$1 = null;
/*     */     
/* 494 */     for (NonNullList<ItemStack> $$2 : this.compartments) {
/* 495 */       if ($$0 < $$2.size()) {
/* 496 */         nonNullList = $$2;
/*     */         break;
/*     */       } 
/* 499 */       $$0 -= $$2.size();
/*     */     } 
/*     */ 
/*     */     
/* 503 */     return (nonNullList == null) ? ItemStack.EMPTY : nonNullList.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 508 */     return (Component)Component.translatable("container.inventory");
/*     */   }
/*     */   
/*     */   public ItemStack getArmor(int $$0) {
/* 512 */     return (ItemStack)this.armor.get($$0);
/*     */   }
/*     */   
/*     */   public void hurtArmor(DamageSource $$0, float $$1, int[] $$2) {
/* 516 */     if ($$1 <= 0.0F) {
/*     */       return;
/*     */     }
/*     */     
/* 520 */     $$1 /= 4.0F;
/* 521 */     if ($$1 < 1.0F) {
/* 522 */       $$1 = 1.0F;
/*     */     }
/* 524 */     for (int $$3 : $$2) {
/* 525 */       ItemStack $$4 = (ItemStack)this.armor.get($$3);
/* 526 */       if (!$$0.is(DamageTypeTags.IS_FIRE) || !$$4.getItem().isFireResistant())
/*     */       {
/*     */         
/* 529 */         if ($$4.getItem() instanceof net.minecraft.world.item.ArmorItem)
/* 530 */           $$4.hurtAndBreak((int)$$1, this.player, $$1 -> $$1.broadcastBreakEvent(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, $$0))); 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dropAll() {
/* 536 */     for (List<ItemStack> $$0 : this.compartments) {
/* 537 */       for (int $$1 = 0; $$1 < $$0.size(); $$1++) {
/* 538 */         ItemStack $$2 = $$0.get($$1);
/* 539 */         if (!$$2.isEmpty()) {
/* 540 */           this.player.drop($$2, true, false);
/* 541 */           $$0.set($$1, ItemStack.EMPTY);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChanged() {
/* 549 */     this.timesChanged++;
/*     */   }
/*     */   
/*     */   public int getTimesChanged() {
/* 553 */     return this.timesChanged;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 558 */     if (this.player.isRemoved()) {
/* 559 */       return false;
/*     */     }
/* 561 */     if ($$0.distanceToSqr((Entity)this.player) > 64.0D) {
/* 562 */       return false;
/*     */     }
/* 564 */     return true;
/*     */   }
/*     */   
/*     */   public boolean contains(ItemStack $$0) {
/* 568 */     for (List<ItemStack> $$1 : this.compartments) {
/* 569 */       for (ItemStack $$2 : $$1) {
/* 570 */         if (!$$2.isEmpty() && ItemStack.isSameItemSameTags($$2, $$0)) {
/* 571 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 575 */     return false;
/*     */   }
/*     */   
/*     */   public boolean contains(TagKey<Item> $$0) {
/* 579 */     for (List<ItemStack> $$1 : this.compartments) {
/* 580 */       for (ItemStack $$2 : $$1) {
/* 581 */         if (!$$2.isEmpty() && $$2.is($$0)) {
/* 582 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/* 586 */     return false;
/*     */   }
/*     */   
/*     */   public void replaceWith(Inventory $$0) {
/* 590 */     for (int $$1 = 0; $$1 < getContainerSize(); $$1++) {
/* 591 */       setItem($$1, $$0.getItem($$1));
/*     */     }
/* 593 */     this.selected = $$0.selected;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 598 */     for (List<ItemStack> $$0 : this.compartments) {
/* 599 */       $$0.clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void fillStackedContents(StackedContents $$0) {
/* 604 */     for (ItemStack $$1 : this.items) {
/* 605 */       $$0.accountSimpleStack($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   public ItemStack removeFromSelected(boolean $$0) {
/* 610 */     ItemStack $$1 = getSelected();
/* 611 */     if ($$1.isEmpty()) {
/* 612 */       return ItemStack.EMPTY;
/*     */     }
/* 614 */     return removeItem(this.selected, $$0 ? $$1.getCount() : 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\player\Inventory.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */