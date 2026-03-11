/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.core.RegistryAccess;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.inventory.ContainerData;
/*     */ import net.minecraft.world.inventory.RecipeCraftingHolder;
/*     */ import net.minecraft.world.inventory.StackedContentsCompatible;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.AbstractCookingRecipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.crafting.RecipeManager;
/*     */ import net.minecraft.world.item.crafting.RecipeType;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.AbstractFurnaceBlock;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractFurnaceBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, RecipeCraftingHolder, StackedContentsCompatible {
/*     */   protected static final int SLOT_INPUT = 0;
/*     */   protected static final int SLOT_FUEL = 1;
/*     */   protected static final int SLOT_RESULT = 2;
/*     */   public static final int DATA_LIT_TIME = 0;
/*  56 */   private static final int[] SLOTS_FOR_UP = new int[] { 0 };
/*     */ 
/*     */   
/*  59 */   private static final int[] SLOTS_FOR_DOWN = new int[] { 2, 1 };
/*     */ 
/*     */   
/*  62 */   private static final int[] SLOTS_FOR_SIDES = new int[] { 1 };
/*     */   
/*     */   public static final int DATA_LIT_DURATION = 1;
/*     */   
/*     */   public static final int DATA_COOKING_PROGRESS = 2;
/*     */   
/*     */   public static final int DATA_COOKING_TOTAL_TIME = 3;
/*     */   
/*     */   public static final int NUM_DATA_VALUES = 4;
/*     */   
/*     */   public static final int BURN_TIME_STANDARD = 200;
/*     */   public static final int BURN_COOL_SPEED = 2;
/*  74 */   protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
/*     */   int litTime;
/*     */   int litDuration;
/*     */   int cookingProgress;
/*     */   int cookingTotalTime;
/*     */   
/*  80 */   protected final ContainerData dataAccess = new ContainerData()
/*     */     {
/*     */       public int get(int $$0) {
/*  83 */         switch ($$0) {
/*     */           case 0:
/*  85 */             return AbstractFurnaceBlockEntity.this.litTime;
/*     */           case 1:
/*  87 */             return AbstractFurnaceBlockEntity.this.litDuration;
/*     */           case 2:
/*  89 */             return AbstractFurnaceBlockEntity.this.cookingProgress;
/*     */           case 3:
/*  91 */             return AbstractFurnaceBlockEntity.this.cookingTotalTime;
/*     */         } 
/*     */ 
/*     */         
/*  95 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int $$0, int $$1) {
/* 100 */         switch ($$0) {
/*     */           case 0:
/* 102 */             AbstractFurnaceBlockEntity.this.litTime = $$1;
/*     */             break;
/*     */           case 1:
/* 105 */             AbstractFurnaceBlockEntity.this.litDuration = $$1;
/*     */             break;
/*     */           case 2:
/* 108 */             AbstractFurnaceBlockEntity.this.cookingProgress = $$1;
/*     */             break;
/*     */           case 3:
/* 111 */             AbstractFurnaceBlockEntity.this.cookingTotalTime = $$1;
/*     */             break;
/*     */         } 
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       public int getCount() {
/* 120 */         return 4;
/*     */       }
/*     */     };
/*     */   
/* 124 */   private final Object2IntOpenHashMap<ResourceLocation> recipesUsed = new Object2IntOpenHashMap();
/*     */   
/*     */   private final RecipeManager.CachedCheck<Container, ? extends AbstractCookingRecipe> quickCheck;
/*     */   
/*     */   protected AbstractFurnaceBlockEntity(BlockEntityType<?> $$0, BlockPos $$1, BlockState $$2, RecipeType<? extends AbstractCookingRecipe> $$3) {
/* 129 */     super($$0, $$1, $$2);
/* 130 */     this.quickCheck = RecipeManager.createCheck($$3);
/*     */   }
/*     */   
/*     */   public static Map<Item, Integer> getFuel() {
/* 134 */     Map<Item, Integer> $$0 = Maps.newLinkedHashMap();
/*     */     
/* 136 */     add($$0, (ItemLike)Items.LAVA_BUCKET, 20000);
/* 137 */     add($$0, (ItemLike)Blocks.COAL_BLOCK, 16000);
/* 138 */     add($$0, (ItemLike)Items.BLAZE_ROD, 2400);
/* 139 */     add($$0, (ItemLike)Items.COAL, 1600);
/* 140 */     add($$0, (ItemLike)Items.CHARCOAL, 1600);
/* 141 */     add($$0, ItemTags.LOGS, 300);
/* 142 */     add($$0, ItemTags.BAMBOO_BLOCKS, 300);
/* 143 */     add($$0, ItemTags.PLANKS, 300);
/* 144 */     add($$0, (ItemLike)Blocks.BAMBOO_MOSAIC, 300);
/* 145 */     add($$0, ItemTags.WOODEN_STAIRS, 300);
/* 146 */     add($$0, (ItemLike)Blocks.BAMBOO_MOSAIC_STAIRS, 300);
/* 147 */     add($$0, ItemTags.WOODEN_SLABS, 150);
/* 148 */     add($$0, (ItemLike)Blocks.BAMBOO_MOSAIC_SLAB, 150);
/* 149 */     add($$0, ItemTags.WOODEN_TRAPDOORS, 300);
/* 150 */     add($$0, ItemTags.WOODEN_PRESSURE_PLATES, 300);
/* 151 */     add($$0, ItemTags.WOODEN_FENCES, 300);
/* 152 */     add($$0, ItemTags.FENCE_GATES, 300);
/* 153 */     add($$0, (ItemLike)Blocks.NOTE_BLOCK, 300);
/* 154 */     add($$0, (ItemLike)Blocks.BOOKSHELF, 300);
/* 155 */     add($$0, (ItemLike)Blocks.CHISELED_BOOKSHELF, 300);
/* 156 */     add($$0, (ItemLike)Blocks.LECTERN, 300);
/* 157 */     add($$0, (ItemLike)Blocks.JUKEBOX, 300);
/* 158 */     add($$0, (ItemLike)Blocks.CHEST, 300);
/* 159 */     add($$0, (ItemLike)Blocks.TRAPPED_CHEST, 300);
/* 160 */     add($$0, (ItemLike)Blocks.CRAFTING_TABLE, 300);
/* 161 */     add($$0, (ItemLike)Blocks.DAYLIGHT_DETECTOR, 300);
/* 162 */     add($$0, ItemTags.BANNERS, 300);
/* 163 */     add($$0, (ItemLike)Items.BOW, 300);
/* 164 */     add($$0, (ItemLike)Items.FISHING_ROD, 300);
/* 165 */     add($$0, (ItemLike)Blocks.LADDER, 300);
/* 166 */     add($$0, ItemTags.SIGNS, 200);
/* 167 */     add($$0, ItemTags.HANGING_SIGNS, 800);
/* 168 */     add($$0, (ItemLike)Items.WOODEN_SHOVEL, 200);
/* 169 */     add($$0, (ItemLike)Items.WOODEN_SWORD, 200);
/* 170 */     add($$0, (ItemLike)Items.WOODEN_HOE, 200);
/* 171 */     add($$0, (ItemLike)Items.WOODEN_AXE, 200);
/* 172 */     add($$0, (ItemLike)Items.WOODEN_PICKAXE, 200);
/* 173 */     add($$0, ItemTags.WOODEN_DOORS, 200);
/* 174 */     add($$0, ItemTags.BOATS, 1200);
/* 175 */     add($$0, ItemTags.WOOL, 100);
/* 176 */     add($$0, ItemTags.WOODEN_BUTTONS, 100);
/* 177 */     add($$0, (ItemLike)Items.STICK, 100);
/* 178 */     add($$0, ItemTags.SAPLINGS, 100);
/* 179 */     add($$0, (ItemLike)Items.BOWL, 100);
/* 180 */     add($$0, ItemTags.WOOL_CARPETS, 67);
/* 181 */     add($$0, (ItemLike)Blocks.DRIED_KELP_BLOCK, 4001);
/* 182 */     add($$0, (ItemLike)Items.CROSSBOW, 300);
/* 183 */     add($$0, (ItemLike)Blocks.BAMBOO, 50);
/* 184 */     add($$0, (ItemLike)Blocks.DEAD_BUSH, 100);
/* 185 */     add($$0, (ItemLike)Blocks.SCAFFOLDING, 50);
/* 186 */     add($$0, (ItemLike)Blocks.LOOM, 300);
/* 187 */     add($$0, (ItemLike)Blocks.BARREL, 300);
/* 188 */     add($$0, (ItemLike)Blocks.CARTOGRAPHY_TABLE, 300);
/* 189 */     add($$0, (ItemLike)Blocks.FLETCHING_TABLE, 300);
/* 190 */     add($$0, (ItemLike)Blocks.SMITHING_TABLE, 300);
/* 191 */     add($$0, (ItemLike)Blocks.COMPOSTER, 300);
/* 192 */     add($$0, (ItemLike)Blocks.AZALEA, 100);
/* 193 */     add($$0, (ItemLike)Blocks.FLOWERING_AZALEA, 100);
/* 194 */     add($$0, (ItemLike)Blocks.MANGROVE_ROOTS, 300);
/*     */     
/* 196 */     return $$0;
/*     */   }
/*     */   
/*     */   private static boolean isNeverAFurnaceFuel(Item $$0) {
/* 200 */     return $$0.builtInRegistryHolder().is(ItemTags.NON_FLAMMABLE_WOOD);
/*     */   }
/*     */   
/*     */   private static void add(Map<Item, Integer> $$0, TagKey<Item> $$1, int $$2) {
/* 204 */     for (Holder<Item> $$3 : (Iterable<Holder<Item>>)BuiltInRegistries.ITEM.getTagOrEmpty($$1)) {
/* 205 */       if (!isNeverAFurnaceFuel((Item)$$3.value())) {
/* 206 */         $$0.put((Item)$$3.value(), Integer.valueOf($$2));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void add(Map<Item, Integer> $$0, ItemLike $$1, int $$2) {
/* 212 */     Item $$3 = $$1.asItem();
/* 213 */     if (isNeverAFurnaceFuel($$3)) {
/* 214 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 215 */         throw (IllegalStateException)Util.pauseInIde(new IllegalStateException("A developer tried to explicitly make fire resistant item " + $$3.getName(null).getString() + " a furnace fuel. That will not work!"));
/*     */       }
/*     */       return;
/*     */     } 
/* 219 */     $$0.put($$3, Integer.valueOf($$2));
/*     */   }
/*     */   
/*     */   private boolean isLit() {
/* 223 */     return (this.litTime > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 228 */     super.load($$0);
/*     */     
/* 230 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/* 231 */     ContainerHelper.loadAllItems($$0, this.items);
/*     */     
/* 233 */     this.litTime = $$0.getShort("BurnTime");
/* 234 */     this.cookingProgress = $$0.getShort("CookTime");
/* 235 */     this.cookingTotalTime = $$0.getShort("CookTimeTotal");
/* 236 */     this.litDuration = getBurnDuration((ItemStack)this.items.get(1));
/*     */     
/* 238 */     CompoundTag $$1 = $$0.getCompound("RecipesUsed");
/* 239 */     for (String $$2 : $$1.getAllKeys()) {
/* 240 */       this.recipesUsed.put(new ResourceLocation($$2), $$1.getInt($$2));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 246 */     super.saveAdditional($$0);
/* 247 */     $$0.putShort("BurnTime", (short)this.litTime);
/* 248 */     $$0.putShort("CookTime", (short)this.cookingProgress);
/* 249 */     $$0.putShort("CookTimeTotal", (short)this.cookingTotalTime);
/*     */     
/* 251 */     ContainerHelper.saveAllItems($$0, this.items);
/*     */     
/* 253 */     CompoundTag $$1 = new CompoundTag();
/* 254 */     this.recipesUsed.forEach(($$1, $$2) -> $$0.putInt($$1.toString(), $$2.intValue()));
/* 255 */     $$0.put("RecipesUsed", (Tag)$$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, AbstractFurnaceBlockEntity $$3) {
/* 260 */     boolean $$4 = $$3.isLit();
/* 261 */     boolean $$5 = false;
/*     */     
/* 263 */     if ($$3.isLit())
/*     */     {
/* 265 */       $$3.litTime--;
/*     */     }
/*     */     
/* 268 */     ItemStack $$6 = (ItemStack)$$3.items.get(1);
/* 269 */     boolean $$7 = !((ItemStack)$$3.items.get(0)).isEmpty();
/* 270 */     boolean $$8 = !$$6.isEmpty();
/* 271 */     if ($$3.isLit() || ($$8 && $$7)) {
/*     */       RecipeHolder<?> $$10;
/*     */       
/* 274 */       if ($$7) {
/* 275 */         RecipeHolder<?> $$9 = $$3.quickCheck.getRecipeFor($$3, $$0).orElse(null);
/*     */       } else {
/* 277 */         $$10 = null;
/*     */       } 
/* 279 */       int $$11 = $$3.getMaxStackSize();
/* 280 */       if (!$$3.isLit() && canBurn($$0.registryAccess(), $$10, $$3.items, $$11)) {
/*     */         
/* 282 */         $$3.litTime = $$3.getBurnDuration($$6);
/* 283 */         $$3.litDuration = $$3.litTime;
/*     */         
/* 285 */         if ($$3.isLit()) {
/* 286 */           $$5 = true;
/*     */           
/* 288 */           if ($$8) {
/* 289 */             Item $$12 = $$6.getItem();
/* 290 */             $$6.shrink(1);
/* 291 */             if ($$6.isEmpty()) {
/* 292 */               Item $$13 = $$12.getCraftingRemainingItem();
/* 293 */               $$3.items.set(1, ($$13 == null) ? ItemStack.EMPTY : new ItemStack((ItemLike)$$13));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 299 */       if ($$3.isLit() && canBurn($$0.registryAccess(), $$10, $$3.items, $$11)) {
/* 300 */         $$3.cookingProgress++;
/*     */         
/* 302 */         if ($$3.cookingProgress == $$3.cookingTotalTime) {
/* 303 */           $$3.cookingProgress = 0;
/* 304 */           $$3.cookingTotalTime = getTotalCookTime($$0, $$3);
/* 305 */           if (burn($$0.registryAccess(), $$10, $$3.items, $$11)) {
/* 306 */             $$3.setRecipeUsed($$10);
/*     */           }
/* 308 */           $$5 = true;
/*     */         } 
/*     */       } else {
/* 311 */         $$3.cookingProgress = 0;
/*     */       } 
/* 313 */     } else if (!$$3.isLit() && $$3.cookingProgress > 0) {
/* 314 */       $$3.cookingProgress = Mth.clamp($$3.cookingProgress - 2, 0, $$3.cookingTotalTime);
/*     */     } 
/*     */     
/* 317 */     if ($$4 != $$3.isLit()) {
/* 318 */       $$5 = true;
/* 319 */       $$2 = (BlockState)$$2.setValue((Property)AbstractFurnaceBlock.LIT, Boolean.valueOf($$3.isLit()));
/* 320 */       $$0.setBlock($$1, $$2, 3);
/*     */     } 
/*     */     
/* 323 */     if ($$5) {
/* 324 */       setChanged($$0, $$1, $$2);
/*     */     }
/*     */   }
/*     */   
/*     */   private static boolean canBurn(RegistryAccess $$0, @Nullable RecipeHolder<?> $$1, NonNullList<ItemStack> $$2, int $$3) {
/* 329 */     if (((ItemStack)$$2.get(0)).isEmpty() || $$1 == null) {
/* 330 */       return false;
/*     */     }
/* 332 */     ItemStack $$4 = $$1.value().getResultItem($$0);
/* 333 */     if ($$4.isEmpty()) {
/* 334 */       return false;
/*     */     }
/*     */     
/* 337 */     ItemStack $$5 = (ItemStack)$$2.get(2);
/* 338 */     if ($$5.isEmpty()) {
/* 339 */       return true;
/*     */     }
/* 341 */     if (!ItemStack.isSameItem($$5, $$4)) {
/* 342 */       return false;
/*     */     }
/* 344 */     if ($$5.getCount() < $$3 && $$5.getCount() < $$5.getMaxStackSize()) {
/* 345 */       return true;
/*     */     }
/* 347 */     return ($$5.getCount() < $$4.getMaxStackSize());
/*     */   }
/*     */   
/*     */   private static boolean burn(RegistryAccess $$0, @Nullable RecipeHolder<?> $$1, NonNullList<ItemStack> $$2, int $$3) {
/* 351 */     if ($$1 == null || !canBurn($$0, $$1, $$2, $$3)) {
/* 352 */       return false;
/*     */     }
/*     */     
/* 355 */     ItemStack $$4 = (ItemStack)$$2.get(0);
/* 356 */     ItemStack $$5 = $$1.value().getResultItem($$0);
/* 357 */     ItemStack $$6 = (ItemStack)$$2.get(2);
/* 358 */     if ($$6.isEmpty()) {
/* 359 */       $$2.set(2, $$5.copy());
/* 360 */     } else if ($$6.is($$5.getItem())) {
/* 361 */       $$6.grow(1);
/*     */     } 
/*     */     
/* 364 */     if ($$4.is(Blocks.WET_SPONGE.asItem()) && !((ItemStack)$$2.get(1)).isEmpty() && ((ItemStack)$$2.get(1)).is(Items.BUCKET)) {
/* 365 */       $$2.set(1, new ItemStack((ItemLike)Items.WATER_BUCKET));
/*     */     }
/*     */     
/* 368 */     $$4.shrink(1);
/* 369 */     return true;
/*     */   }
/*     */   
/*     */   protected int getBurnDuration(ItemStack $$0) {
/* 373 */     if ($$0.isEmpty()) {
/* 374 */       return 0;
/*     */     }
/*     */     
/* 377 */     Item $$1 = $$0.getItem();
/* 378 */     return ((Integer)getFuel().getOrDefault($$1, Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   private static int getTotalCookTime(Level $$0, AbstractFurnaceBlockEntity $$1) {
/* 382 */     return ((Integer)$$1.quickCheck.getRecipeFor($$1, $$0).map($$0 -> Integer.valueOf(((AbstractCookingRecipe)$$0.value()).getCookingTime())).orElse(Integer.valueOf(200))).intValue();
/*     */   }
/*     */   
/*     */   public static boolean isFuel(ItemStack $$0) {
/* 386 */     return getFuel().containsKey($$0.getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(Direction $$0) {
/* 391 */     if ($$0 == Direction.DOWN)
/* 392 */       return SLOTS_FOR_DOWN; 
/* 393 */     if ($$0 == Direction.UP) {
/* 394 */       return SLOTS_FOR_UP;
/*     */     }
/* 396 */     return SLOTS_FOR_SIDES;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 402 */     return canPlaceItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 407 */     if ($$2 == Direction.DOWN && $$0 == 1) {
/* 408 */       return ($$1.is(Items.WATER_BUCKET) || $$1.is(Items.BUCKET));
/*     */     }
/*     */     
/* 411 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/* 416 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 421 */     for (ItemStack $$0 : this.items) {
/* 422 */       if (!$$0.isEmpty()) {
/* 423 */         return false;
/*     */       }
/*     */     } 
/* 426 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/* 431 */     return (ItemStack)this.items.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/* 436 */     return ContainerHelper.removeItem((List)this.items, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 441 */     return ContainerHelper.takeItem((List)this.items, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 446 */     ItemStack $$2 = (ItemStack)this.items.get($$0);
/* 447 */     boolean $$3 = (!$$1.isEmpty() && ItemStack.isSameItemSameTags($$2, $$1));
/* 448 */     this.items.set($$0, $$1);
/* 449 */     if ($$1.getCount() > getMaxStackSize()) {
/* 450 */       $$1.setCount(getMaxStackSize());
/*     */     }
/*     */     
/* 453 */     if ($$0 == 0 && !$$3) {
/* 454 */       this.cookingTotalTime = getTotalCookTime(this.level, this);
/* 455 */       this.cookingProgress = 0;
/* 456 */       setChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 462 */     return Container.stillValidBlockEntity(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 467 */     if ($$0 == 2) {
/* 468 */       return false;
/*     */     }
/* 470 */     if ($$0 == 1) {
/* 471 */       ItemStack $$2 = (ItemStack)this.items.get(1);
/* 472 */       return (isFuel($$1) || ($$1.is(Items.BUCKET) && !$$2.is(Items.BUCKET)));
/*     */     } 
/* 474 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 479 */     this.items.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRecipeUsed(@Nullable RecipeHolder<?> $$0) {
/* 484 */     if ($$0 != null) {
/* 485 */       ResourceLocation $$1 = $$0.id();
/* 486 */       this.recipesUsed.addTo($$1, 1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RecipeHolder<?> getRecipeUsed() {
/* 493 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void awardUsedRecipes(Player $$0, List<ItemStack> $$1) {}
/*     */ 
/*     */   
/*     */   public void awardUsedRecipesAndPopExperience(ServerPlayer $$0) {
/* 502 */     List<RecipeHolder<?>> $$1 = getRecipesToAwardAndPopExperience($$0.serverLevel(), $$0.position());
/* 503 */     $$0.awardRecipes($$1);
/* 504 */     for (RecipeHolder<?> $$2 : $$1) {
/* 505 */       if ($$2 != null) {
/* 506 */         $$0.triggerRecipeCrafted($$2, (List)this.items);
/*     */       }
/*     */     } 
/* 509 */     this.recipesUsed.clear();
/*     */   }
/*     */   
/*     */   public List<RecipeHolder<?>> getRecipesToAwardAndPopExperience(ServerLevel $$0, Vec3 $$1) {
/* 513 */     List<RecipeHolder<?>> $$2 = Lists.newArrayList();
/* 514 */     for (ObjectIterator<Object2IntMap.Entry<ResourceLocation>> objectIterator = this.recipesUsed.object2IntEntrySet().iterator(); objectIterator.hasNext(); ) { Object2IntMap.Entry<ResourceLocation> $$3 = objectIterator.next();
/* 515 */       $$0.getRecipeManager().byKey((ResourceLocation)$$3.getKey()).ifPresent($$4 -> {
/*     */             $$0.add($$4);
/*     */             createExperience($$1, $$2, $$3.getIntValue(), ((AbstractCookingRecipe)$$4.value()).getExperience());
/*     */           }); }
/*     */     
/* 520 */     return $$2;
/*     */   }
/*     */   
/*     */   private static void createExperience(ServerLevel $$0, Vec3 $$1, int $$2, float $$3) {
/* 524 */     int $$4 = Mth.floor($$2 * $$3);
/* 525 */     float $$5 = Mth.frac($$2 * $$3);
/* 526 */     if ($$5 != 0.0F && Math.random() < $$5) {
/* 527 */       $$4++;
/*     */     }
/*     */     
/* 530 */     ExperienceOrb.award($$0, $$1, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillStackedContents(StackedContents $$0) {
/* 535 */     for (ItemStack $$1 : this.items)
/* 536 */       $$0.accountStack($$1); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\AbstractFurnaceBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */