/*     */ package net.minecraft.world.level.block.entity;
/*     */ import java.util.List;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.IntStream;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.WorldlyContainerHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EntitySelector;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.HopperMenu;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.ChestBlock;
/*     */ import net.minecraft.world.level.block.HopperBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ 
/*     */ public class HopperBlockEntity extends RandomizableContainerBlockEntity implements Hopper {
/*     */   public static final int MOVE_ITEM_SPEED = 8;
/*     */   public static final int HOPPER_CONTAINER_SIZE = 5;
/*  38 */   private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
/*  39 */   private int cooldownTime = -1;
/*     */   private long tickedGameTime;
/*     */   
/*     */   public HopperBlockEntity(BlockPos $$0, BlockState $$1) {
/*  43 */     super(BlockEntityType.HOPPER, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  48 */     super.load($$0);
/*     */     
/*  50 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/*  51 */     if (!tryLoadLootTable($$0)) {
/*  52 */       ContainerHelper.loadAllItems($$0, this.items);
/*     */     }
/*  54 */     this.cooldownTime = $$0.getInt("TransferCooldown");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  59 */     super.saveAdditional($$0);
/*     */     
/*  61 */     if (!trySaveLootTable($$0)) {
/*  62 */       ContainerHelper.saveAllItems($$0, this.items);
/*     */     }
/*     */     
/*  65 */     $$0.putInt("TransferCooldown", this.cooldownTime);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  70 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/*  75 */     unpackLootTable(null);
/*     */ 
/*     */     
/*  78 */     return ContainerHelper.removeItem((List)getItems(), $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/*  83 */     unpackLootTable(null);
/*  84 */     getItems().set($$0, $$1);
/*  85 */     if ($$1.getCount() > getMaxStackSize()) {
/*  86 */       $$1.setCount(getMaxStackSize());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/*  93 */     return (Component)Component.translatable("container.hopper");
/*     */   }
/*     */   
/*     */   public static void pushItemsTick(Level $$0, BlockPos $$1, BlockState $$2, HopperBlockEntity $$3) {
/*  97 */     $$3.cooldownTime--;
/*  98 */     $$3.tickedGameTime = $$0.getGameTime();
/*     */     
/* 100 */     if (!$$3.isOnCooldown()) {
/* 101 */       $$3.setCooldown(0);
/* 102 */       tryMoveItems($$0, $$1, $$2, $$3, () -> suckInItems($$0, $$1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static boolean tryMoveItems(Level $$0, BlockPos $$1, BlockState $$2, HopperBlockEntity $$3, BooleanSupplier $$4) {
/* 107 */     if ($$0.isClientSide) {
/* 108 */       return false;
/*     */     }
/*     */     
/* 111 */     if (!$$3.isOnCooldown() && ((Boolean)$$2.getValue((Property)HopperBlock.ENABLED)).booleanValue()) {
/* 112 */       boolean $$5 = false;
/*     */       
/* 114 */       if (!$$3.isEmpty()) {
/* 115 */         $$5 = ejectItems($$0, $$1, $$2, $$3);
/*     */       }
/* 117 */       if (!$$3.inventoryFull()) {
/* 118 */         $$5 |= $$4.getAsBoolean();
/*     */       }
/*     */       
/* 121 */       if ($$5) {
/* 122 */         $$3.setCooldown(8);
/* 123 */         setChanged($$0, $$1, $$2);
/* 124 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 128 */     return false;
/*     */   }
/*     */   
/*     */   private boolean inventoryFull() {
/* 132 */     for (ItemStack $$0 : this.items) {
/* 133 */       if ($$0.isEmpty() || $$0.getCount() != $$0.getMaxStackSize()) {
/* 134 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 138 */     return true;
/*     */   }
/*     */   
/*     */   private static boolean ejectItems(Level $$0, BlockPos $$1, BlockState $$2, Container $$3) {
/* 142 */     Container $$4 = getAttachedContainer($$0, $$1, $$2);
/* 143 */     if ($$4 == null) {
/* 144 */       return false;
/*     */     }
/*     */     
/* 147 */     Direction $$5 = ((Direction)$$2.getValue((Property)HopperBlock.FACING)).getOpposite();
/* 148 */     if (isFullContainer($$4, $$5)) {
/* 149 */       return false;
/*     */     }
/*     */     
/* 152 */     for (int $$6 = 0; $$6 < $$3.getContainerSize(); $$6++) {
/* 153 */       if (!$$3.getItem($$6).isEmpty()) {
/*     */ 
/*     */ 
/*     */         
/* 157 */         ItemStack $$7 = $$3.getItem($$6).copy();
/* 158 */         ItemStack $$8 = addItem($$3, $$4, $$3.removeItem($$6, 1), $$5);
/*     */         
/* 160 */         if ($$8.isEmpty()) {
/* 161 */           $$4.setChanged();
/* 162 */           return true;
/*     */         } 
/* 164 */         $$3.setItem($$6, $$7);
/*     */       } 
/*     */     } 
/*     */     
/* 168 */     return false;
/*     */   }
/*     */   
/*     */   private static IntStream getSlots(Container $$0, Direction $$1) {
/* 172 */     if ($$0 instanceof WorldlyContainer) {
/* 173 */       return IntStream.of(((WorldlyContainer)$$0).getSlotsForFace($$1));
/*     */     }
/*     */     
/* 176 */     return IntStream.range(0, $$0.getContainerSize());
/*     */   }
/*     */   
/*     */   private static boolean isFullContainer(Container $$0, Direction $$1) {
/* 180 */     return getSlots($$0, $$1).allMatch($$1 -> {
/*     */           ItemStack $$2 = $$0.getItem($$1);
/*     */           return ($$2.getCount() >= $$2.getMaxStackSize());
/*     */         });
/*     */   }
/*     */   
/*     */   private static boolean isEmptyContainer(Container $$0, Direction $$1) {
/* 187 */     return getSlots($$0, $$1).allMatch($$1 -> $$0.getItem($$1).isEmpty());
/*     */   }
/*     */   
/*     */   public static boolean suckInItems(Level $$0, Hopper $$1) {
/* 191 */     Container $$2 = getSourceContainer($$0, $$1);
/*     */     
/* 193 */     if ($$2 != null) {
/* 194 */       Direction $$3 = Direction.DOWN;
/* 195 */       if (isEmptyContainer($$2, $$3)) {
/* 196 */         return false;
/*     */       }
/*     */       
/* 199 */       return getSlots($$2, $$3).anyMatch($$3 -> tryTakeInItemFromSlot($$0, $$1, $$3, $$2));
/*     */     } 
/* 201 */     for (ItemEntity $$4 : getItemsAtAndAbove($$0, $$1)) {
/* 202 */       if (addItem($$1, $$4)) {
/* 203 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 208 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean tryTakeInItemFromSlot(Hopper $$0, Container $$1, int $$2, Direction $$3) {
/* 212 */     ItemStack $$4 = $$1.getItem($$2);
/*     */     
/* 214 */     if (!$$4.isEmpty() && canTakeItemFromContainer($$0, $$1, $$4, $$2, $$3)) {
/* 215 */       ItemStack $$5 = $$4.copy();
/* 216 */       ItemStack $$6 = addItem($$1, $$0, $$1.removeItem($$2, 1), (Direction)null);
/*     */       
/* 218 */       if ($$6.isEmpty()) {
/* 219 */         $$1.setChanged();
/* 220 */         return true;
/*     */       } 
/* 222 */       $$1.setItem($$2, $$5);
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return false;
/*     */   }
/*     */   
/*     */   public static boolean addItem(Container $$0, ItemEntity $$1) {
/* 230 */     boolean $$2 = false;
/*     */     
/* 232 */     ItemStack $$3 = $$1.getItem().copy();
/* 233 */     ItemStack $$4 = addItem((Container)null, $$0, $$3, (Direction)null);
/*     */     
/* 235 */     if ($$4.isEmpty()) {
/* 236 */       $$2 = true;
/*     */       
/* 238 */       $$1.setItem(ItemStack.EMPTY);
/* 239 */       $$1.discard();
/*     */     } else {
/* 241 */       $$1.setItem($$4);
/*     */     } 
/*     */     
/* 244 */     return $$2;
/*     */   }
/*     */   
/*     */   public static ItemStack addItem(@Nullable Container $$0, Container $$1, ItemStack $$2, @Nullable Direction $$3) {
/* 248 */     if ($$1 instanceof WorldlyContainer) { WorldlyContainer $$4 = (WorldlyContainer)$$1; if ($$3 != null)
/* 249 */       { int[] $$5 = $$4.getSlotsForFace($$3);
/*     */         
/* 251 */         for (int $$6 = 0; $$6 < $$5.length && !$$2.isEmpty(); $$6++) {
/* 252 */           $$2 = tryMoveInItem($$0, $$1, $$2, $$5[$$6], $$3);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 261 */         return $$2; }  }  int $$7 = $$1.getContainerSize(); for (int $$8 = 0; $$8 < $$7 && !$$2.isEmpty(); $$8++) $$2 = tryMoveInItem($$0, $$1, $$2, $$8, $$3);  return $$2;
/*     */   }
/*     */   
/*     */   private static boolean canPlaceItemInContainer(Container $$0, ItemStack $$1, int $$2, @Nullable Direction $$3) {
/* 265 */     if (!$$0.canPlaceItem($$2, $$1)) {
/* 266 */       return false;
/*     */     }
/* 268 */     if ($$0 instanceof WorldlyContainer) { WorldlyContainer $$4 = (WorldlyContainer)$$0; if ($$4.canPlaceItemThroughFace($$2, $$1, $$3)); return false; }
/*     */   
/*     */   }
/*     */   private static boolean canTakeItemFromContainer(Container $$0, Container $$1, ItemStack $$2, int $$3, Direction $$4) {
/* 272 */     if (!$$1.canTakeItem($$0, $$3, $$2)) {
/* 273 */       return false;
/*     */     }
/* 275 */     if ($$1 instanceof WorldlyContainer) { WorldlyContainer $$5 = (WorldlyContainer)$$1; if ($$5.canTakeItemThroughFace($$3, $$2, $$4)); return false; }
/*     */   
/*     */   }
/*     */   private static ItemStack tryMoveInItem(@Nullable Container $$0, Container $$1, ItemStack $$2, int $$3, @Nullable Direction $$4) {
/* 279 */     ItemStack $$5 = $$1.getItem($$3);
/*     */     
/* 281 */     if (canPlaceItemInContainer($$1, $$2, $$3, $$4)) {
/* 282 */       boolean $$6 = false;
/* 283 */       boolean $$7 = $$1.isEmpty();
/* 284 */       if ($$5.isEmpty()) {
/* 285 */         $$1.setItem($$3, $$2);
/* 286 */         $$2 = ItemStack.EMPTY;
/* 287 */         $$6 = true;
/* 288 */       } else if (canMergeItems($$5, $$2)) {
/* 289 */         int $$8 = $$2.getMaxStackSize() - $$5.getCount();
/* 290 */         int $$9 = Math.min($$2.getCount(), $$8);
/*     */         
/* 292 */         $$2.shrink($$9);
/* 293 */         $$5.grow($$9);
/* 294 */         $$6 = ($$9 > 0);
/*     */       } 
/* 296 */       if ($$6) {
/* 297 */         if ($$7 && $$1 instanceof HopperBlockEntity) { HopperBlockEntity $$10 = (HopperBlockEntity)$$1;
/* 298 */           if (!$$10.isOnCustomCooldown()) {
/* 299 */             int $$11 = 0;
/* 300 */             if ($$0 instanceof HopperBlockEntity) { HopperBlockEntity $$12 = (HopperBlockEntity)$$0;
/* 301 */               if ($$10.tickedGameTime >= $$12.tickedGameTime)
/*     */               {
/* 303 */                 $$11 = 1;
/*     */               } }
/*     */             
/* 306 */             $$10.setCooldown(8 - $$11);
/*     */           }  }
/*     */         
/* 309 */         $$1.setChanged();
/*     */       } 
/*     */     } 
/* 312 */     return $$2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Container getAttachedContainer(Level $$0, BlockPos $$1, BlockState $$2) {
/* 317 */     Direction $$3 = (Direction)$$2.getValue((Property)HopperBlock.FACING);
/* 318 */     return getContainerAt($$0, $$1.relative($$3));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Container getSourceContainer(Level $$0, Hopper $$1) {
/* 323 */     return getContainerAt($$0, $$1.getLevelX(), $$1.getLevelY() + 1.0D, $$1.getLevelZ());
/*     */   }
/*     */   
/*     */   public static List<ItemEntity> getItemsAtAndAbove(Level $$0, Hopper $$1) {
/* 327 */     return (List<ItemEntity>)$$1.getSuckShape().toAabbs().stream().flatMap($$2 -> $$0.getEntitiesOfClass(ItemEntity.class, $$2.move($$1.getLevelX() - 0.5D, $$1.getLevelY() - 0.5D, $$1.getLevelZ() - 0.5D), EntitySelector.ENTITY_STILL_ALIVE).stream()).collect(Collectors.toList());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Container getContainerAt(Level $$0, BlockPos $$1) {
/* 332 */     return getContainerAt($$0, $$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static Container getContainerAt(Level $$0, double $$1, double $$2, double $$3) {
/* 337 */     Container $$4 = null;
/* 338 */     BlockPos $$5 = BlockPos.containing($$1, $$2, $$3);
/*     */     
/* 340 */     BlockState $$6 = $$0.getBlockState($$5);
/* 341 */     Block $$7 = $$6.getBlock();
/* 342 */     if ($$7 instanceof WorldlyContainerHolder) {
/* 343 */       WorldlyContainer worldlyContainer = ((WorldlyContainerHolder)$$7).getContainer($$6, (LevelAccessor)$$0, $$5);
/* 344 */     } else if ($$6.hasBlockEntity()) {
/* 345 */       BlockEntity $$8 = $$0.getBlockEntity($$5);
/*     */       
/* 347 */       if ($$8 instanceof Container) {
/* 348 */         $$4 = (Container)$$8;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 353 */         if ($$4 instanceof ChestBlockEntity && 
/* 354 */           $$7 instanceof ChestBlock) {
/* 355 */           $$4 = ChestBlock.getContainer((ChestBlock)$$7, $$6, $$0, $$5, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 361 */     if ($$4 == null) {
/* 362 */       List<Entity> $$9 = $$0.getEntities((Entity)null, new AABB($$1 - 0.5D, $$2 - 0.5D, $$3 - 0.5D, $$1 + 0.5D, $$2 + 0.5D, $$3 + 0.5D), EntitySelector.CONTAINER_ENTITY_SELECTOR);
/*     */       
/* 364 */       if (!$$9.isEmpty()) {
/* 365 */         $$4 = (Container)$$9.get($$0.random.nextInt($$9.size()));
/*     */       }
/*     */     } 
/*     */     
/* 369 */     return $$4;
/*     */   }
/*     */   
/*     */   private static boolean canMergeItems(ItemStack $$0, ItemStack $$1) {
/* 373 */     return ($$0.getCount() <= $$0.getMaxStackSize() && ItemStack.isSameItemSameTags($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelX() {
/* 378 */     return this.worldPosition.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelY() {
/* 383 */     return this.worldPosition.getY() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLevelZ() {
/* 388 */     return this.worldPosition.getZ() + 0.5D;
/*     */   }
/*     */   
/*     */   private void setCooldown(int $$0) {
/* 392 */     this.cooldownTime = $$0;
/*     */   }
/*     */   
/*     */   private boolean isOnCooldown() {
/* 396 */     return (this.cooldownTime > 0);
/*     */   }
/*     */   
/*     */   private boolean isOnCustomCooldown() {
/* 400 */     return (this.cooldownTime > 8);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> getItems() {
/* 405 */     return this.items;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setItems(NonNullList<ItemStack> $$0) {
/* 410 */     this.items = $$0;
/*     */   }
/*     */   
/*     */   public static void entityInside(Level $$0, BlockPos $$1, BlockState $$2, Entity $$3, HopperBlockEntity $$4) {
/* 414 */     if ($$3 instanceof ItemEntity) { ItemEntity $$5 = (ItemEntity)$$3; if (!$$5.getItem().isEmpty() && 
/* 415 */         Shapes.joinIsNotEmpty(Shapes.create($$3.getBoundingBox().move(-$$1.getX(), -$$1.getY(), -$$1.getZ())), $$4.getSuckShape(), BooleanOp.AND)) {
/* 416 */         tryMoveItems($$0, $$1, $$2, $$4, () -> addItem($$0, $$1));
/*     */       } }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 423 */     return (AbstractContainerMenu)new HopperMenu($$0, $$1, this);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\HopperBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */