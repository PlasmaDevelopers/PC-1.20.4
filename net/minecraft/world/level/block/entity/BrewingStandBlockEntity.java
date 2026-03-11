/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.ContainerHelper;
/*     */ import net.minecraft.world.Containers;
/*     */ import net.minecraft.world.WorldlyContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.AbstractContainerMenu;
/*     */ import net.minecraft.world.inventory.BrewingStandMenu;
/*     */ import net.minecraft.world.inventory.ContainerData;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.alchemy.PotionBrewing;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.BrewingStandBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ public class BrewingStandBlockEntity
/*     */   extends BaseContainerBlockEntity implements WorldlyContainer {
/*     */   private static final int INGREDIENT_SLOT = 3;
/*     */   private static final int FUEL_SLOT = 4;
/*  34 */   private static final int[] SLOTS_FOR_UP = new int[] { 3 };
/*     */ 
/*     */   
/*  37 */   private static final int[] SLOTS_FOR_DOWN = new int[] { 0, 1, 2, 3 };
/*     */ 
/*     */   
/*  40 */   private static final int[] SLOTS_FOR_SIDES = new int[] { 0, 1, 2, 4 };
/*     */   
/*     */   public static final int FUEL_USES = 20;
/*     */   
/*     */   public static final int DATA_BREW_TIME = 0;
/*     */   
/*     */   public static final int DATA_FUEL_USES = 1;
/*     */   
/*     */   public static final int NUM_DATA_VALUES = 2;
/*     */   
/*  50 */   private NonNullList<ItemStack> items = NonNullList.withSize(5, ItemStack.EMPTY);
/*     */   
/*     */   int brewTime;
/*     */   private boolean[] lastPotionCount;
/*     */   private Item ingredient;
/*     */   int fuel;
/*     */   
/*  57 */   protected final ContainerData dataAccess = new ContainerData()
/*     */     {
/*     */       public int get(int $$0) {
/*  60 */         switch ($$0) {
/*     */           case 0:
/*  62 */             return BrewingStandBlockEntity.this.brewTime;
/*     */           case 1:
/*  64 */             return BrewingStandBlockEntity.this.fuel;
/*     */         } 
/*  66 */         return 0;
/*     */       }
/*     */ 
/*     */       
/*     */       public void set(int $$0, int $$1) {
/*  71 */         switch ($$0) {
/*     */           case 0:
/*  73 */             BrewingStandBlockEntity.this.brewTime = $$1;
/*     */             break;
/*     */           case 1:
/*  76 */             BrewingStandBlockEntity.this.fuel = $$1;
/*     */             break;
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public int getCount() {
/*  83 */         return 2;
/*     */       }
/*     */     };
/*     */   
/*     */   public BrewingStandBlockEntity(BlockPos $$0, BlockState $$1) {
/*  88 */     super(BlockEntityType.BREWING_STAND, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Component getDefaultName() {
/*  93 */     return (Component)Component.translatable("container.brewing");
/*     */   }
/*     */ 
/*     */   
/*     */   public int getContainerSize() {
/*  98 */     return this.items.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 103 */     for (ItemStack $$0 : this.items) {
/* 104 */       if (!$$0.isEmpty()) {
/* 105 */         return false;
/*     */       }
/*     */     } 
/* 108 */     return true;
/*     */   }
/*     */   
/*     */   public static void serverTick(Level $$0, BlockPos $$1, BlockState $$2, BrewingStandBlockEntity $$3) {
/* 112 */     ItemStack $$4 = (ItemStack)$$3.items.get(4);
/* 113 */     if ($$3.fuel <= 0 && $$4.is(Items.BLAZE_POWDER)) {
/* 114 */       $$3.fuel = 20;
/* 115 */       $$4.shrink(1);
/* 116 */       setChanged($$0, $$1, $$2);
/*     */     } 
/*     */     
/* 119 */     boolean $$5 = isBrewable($$3.items);
/* 120 */     boolean $$6 = ($$3.brewTime > 0);
/* 121 */     ItemStack $$7 = (ItemStack)$$3.items.get(3);
/* 122 */     if ($$6) {
/* 123 */       $$3.brewTime--;
/*     */       
/* 125 */       boolean $$8 = ($$3.brewTime == 0);
/* 126 */       if ($$8 && $$5) {
/*     */         
/* 128 */         doBrew($$0, $$1, $$3.items);
/* 129 */         setChanged($$0, $$1, $$2);
/* 130 */       } else if (!$$5 || !$$7.is($$3.ingredient)) {
/* 131 */         $$3.brewTime = 0;
/* 132 */         setChanged($$0, $$1, $$2);
/*     */       } 
/* 134 */     } else if ($$5 && $$3.fuel > 0) {
/* 135 */       $$3.fuel--;
/* 136 */       $$3.brewTime = 400;
/* 137 */       $$3.ingredient = $$7.getItem();
/* 138 */       setChanged($$0, $$1, $$2);
/*     */     } 
/*     */     
/* 141 */     boolean[] $$9 = $$3.getPotionBits();
/* 142 */     if (!Arrays.equals($$9, $$3.lastPotionCount)) {
/* 143 */       $$3.lastPotionCount = $$9;
/* 144 */       BlockState $$10 = $$2;
/* 145 */       if (!($$10.getBlock() instanceof BrewingStandBlock)) {
/*     */         return;
/*     */       }
/* 148 */       for (int $$11 = 0; $$11 < BrewingStandBlock.HAS_BOTTLE.length; $$11++) {
/* 149 */         $$10 = (BlockState)$$10.setValue((Property)BrewingStandBlock.HAS_BOTTLE[$$11], Boolean.valueOf($$9[$$11]));
/*     */       }
/* 151 */       $$0.setBlock($$1, $$10, 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean[] getPotionBits() {
/* 157 */     boolean[] $$0 = new boolean[3];
/* 158 */     for (int $$1 = 0; $$1 < 3; $$1++) {
/* 159 */       if (!((ItemStack)this.items.get($$1)).isEmpty()) {
/* 160 */         $$0[$$1] = true;
/*     */       }
/*     */     } 
/* 163 */     return $$0;
/*     */   }
/*     */   
/*     */   private static boolean isBrewable(NonNullList<ItemStack> $$0) {
/* 167 */     ItemStack $$1 = (ItemStack)$$0.get(3);
/* 168 */     if ($$1.isEmpty()) {
/* 169 */       return false;
/*     */     }
/*     */     
/* 172 */     if (!PotionBrewing.isIngredient($$1)) {
/* 173 */       return false;
/*     */     }
/*     */     
/* 176 */     for (int $$2 = 0; $$2 < 3; $$2++) {
/* 177 */       ItemStack $$3 = (ItemStack)$$0.get($$2);
/* 178 */       if (!$$3.isEmpty())
/*     */       {
/*     */ 
/*     */         
/* 182 */         if (PotionBrewing.hasMix($$3, $$1))
/* 183 */           return true; 
/*     */       }
/*     */     } 
/* 186 */     return false;
/*     */   }
/*     */   
/*     */   private static void doBrew(Level $$0, BlockPos $$1, NonNullList<ItemStack> $$2) {
/* 190 */     ItemStack $$3 = (ItemStack)$$2.get(3);
/*     */     
/* 192 */     for (int $$4 = 0; $$4 < 3; $$4++) {
/* 193 */       $$2.set($$4, PotionBrewing.mix($$3, (ItemStack)$$2.get($$4)));
/*     */     }
/*     */     
/* 196 */     $$3.shrink(1);
/* 197 */     if ($$3.getItem().hasCraftingRemainingItem()) {
/* 198 */       ItemStack $$5 = new ItemStack((ItemLike)$$3.getItem().getCraftingRemainingItem());
/* 199 */       if ($$3.isEmpty()) {
/* 200 */         $$3 = $$5;
/*     */       } else {
/* 202 */         Containers.dropItemStack($$0, $$1.getX(), $$1.getY(), $$1.getZ(), $$5);
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     $$2.set(3, $$3);
/*     */     
/* 208 */     $$0.levelEvent(1035, $$1, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/* 213 */     super.load($$0);
/*     */     
/* 215 */     this.items = NonNullList.withSize(getContainerSize(), ItemStack.EMPTY);
/* 216 */     ContainerHelper.loadAllItems($$0, this.items);
/*     */     
/* 218 */     this.brewTime = $$0.getShort("BrewTime");
/* 219 */     this.fuel = $$0.getByte("Fuel");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/* 224 */     super.saveAdditional($$0);
/*     */     
/* 226 */     $$0.putShort("BrewTime", (short)this.brewTime);
/* 227 */     ContainerHelper.saveAllItems($$0, this.items);
/*     */     
/* 229 */     $$0.putByte("Fuel", (byte)this.fuel);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(int $$0) {
/* 234 */     if ($$0 >= 0 && $$0 < this.items.size()) {
/* 235 */       return (ItemStack)this.items.get($$0);
/*     */     }
/* 237 */     return ItemStack.EMPTY;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItem(int $$0, int $$1) {
/* 242 */     return ContainerHelper.removeItem((List)this.items, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack removeItemNoUpdate(int $$0) {
/* 247 */     return ContainerHelper.takeItem((List)this.items, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setItem(int $$0, ItemStack $$1) {
/* 252 */     if ($$0 >= 0 && $$0 < this.items.size()) {
/* 253 */       this.items.set($$0, $$1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 259 */     return Container.stillValidBlockEntity(this, $$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItem(int $$0, ItemStack $$1) {
/* 264 */     if ($$0 == 3) {
/* 265 */       return PotionBrewing.isIngredient($$1);
/*     */     }
/*     */     
/* 268 */     if ($$0 == 4) {
/* 269 */       return $$1.is(Items.BLAZE_POWDER);
/*     */     }
/*     */     
/* 272 */     return (($$1.is(Items.POTION) || $$1.is(Items.SPLASH_POTION) || $$1.is(Items.LINGERING_POTION) || $$1.is(Items.GLASS_BOTTLE)) && getItem($$0).isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(Direction $$0) {
/* 277 */     if ($$0 == Direction.UP) {
/* 278 */       return SLOTS_FOR_UP;
/*     */     }
/* 280 */     if ($$0 == Direction.DOWN) {
/* 281 */       return SLOTS_FOR_DOWN;
/*     */     }
/* 283 */     return SLOTS_FOR_SIDES;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceItemThroughFace(int $$0, ItemStack $$1, @Nullable Direction $$2) {
/* 288 */     return canPlaceItem($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemThroughFace(int $$0, ItemStack $$1, Direction $$2) {
/* 293 */     if ($$0 == 3) {
/* 294 */       return $$1.is(Items.GLASS_BOTTLE);
/*     */     }
/* 296 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearContent() {
/* 301 */     this.items.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractContainerMenu createMenu(int $$0, Inventory $$1) {
/* 306 */     return (AbstractContainerMenu)new BrewingStandMenu($$0, $$1, this, this.dataAccess);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\BrewingStandBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */