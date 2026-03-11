/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.Mob;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.player.StackedContents;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.crafting.Recipe;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ 
/*     */ public class InventoryMenu
/*     */   extends RecipeBookMenu<CraftingContainer>
/*     */ {
/*     */   public static final int CONTAINER_ID = 0;
/*     */   public static final int RESULT_SLOT = 0;
/*     */   public static final int CRAFT_SLOT_START = 1;
/*     */   public static final int CRAFT_SLOT_END = 5;
/*     */   public static final int ARMOR_SLOT_START = 5;
/*     */   public static final int ARMOR_SLOT_END = 9;
/*     */   public static final int INV_SLOT_START = 9;
/*     */   public static final int INV_SLOT_END = 36;
/*     */   public static final int USE_ROW_SLOT_START = 36;
/*     */   public static final int USE_ROW_SLOT_END = 45;
/*     */   public static final int SHIELD_SLOT = 45;
/*  30 */   public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");
/*     */   
/*  32 */   public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
/*  33 */   public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
/*  34 */   public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
/*  35 */   public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
/*  36 */   public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
/*     */ 
/*     */   
/*  39 */   static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[] { EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[] { EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  53 */   private final CraftingContainer craftSlots = new TransientCraftingContainer(this, 2, 2);
/*  54 */   private final ResultContainer resultSlots = new ResultContainer();
/*     */   public final boolean active;
/*     */   private final Player owner;
/*     */   
/*     */   public InventoryMenu(Inventory $$0, boolean $$1, final Player owner) {
/*  59 */     super((MenuType<?>)null, 0);
/*  60 */     this.active = $$1;
/*  61 */     this.owner = owner;
/*  62 */     addSlot(new ResultSlot($$0.player, this.craftSlots, this.resultSlots, 0, 154, 28));
/*     */     
/*  64 */     for (int $$3 = 0; $$3 < 2; $$3++) {
/*  65 */       for (int $$4 = 0; $$4 < 2; $$4++) {
/*  66 */         addSlot(new Slot(this.craftSlots, $$4 + $$3 * 2, 98 + $$4 * 18, 18 + $$3 * 18));
/*     */       }
/*     */     } 
/*     */     
/*  70 */     for (int $$5 = 0; $$5 < 4; $$5++) {
/*  71 */       final EquipmentSlot slot = SLOT_IDS[$$5];
/*  72 */       addSlot(new Slot((Container)$$0, 39 - $$5, 8, 8 + $$5 * 18)
/*     */           {
/*     */             public void setByPlayer(ItemStack $$0, ItemStack $$1) {
/*  75 */               InventoryMenu.onEquipItem(owner, slot, $$0, $$1);
/*  76 */               super.setByPlayer($$0, $$1);
/*     */             }
/*     */ 
/*     */             
/*     */             public int getMaxStackSize() {
/*  81 */               return 1;
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean mayPlace(ItemStack $$0) {
/*  86 */               return (slot == Mob.getEquipmentSlotForItem($$0));
/*     */             }
/*     */ 
/*     */             
/*     */             public boolean mayPickup(Player $$0) {
/*  91 */               ItemStack $$1 = getItem();
/*  92 */               if (!$$1.isEmpty() && !$$0.isCreative() && EnchantmentHelper.hasBindingCurse($$1)) {
/*  93 */                 return false;
/*     */               }
/*  95 */               return super.mayPickup($$0);
/*     */             }
/*     */ 
/*     */             
/*     */             public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/* 100 */               return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.TEXTURE_EMPTY_SLOTS[slot.getIndex()]);
/*     */             }
/*     */           });
/*     */     } 
/* 104 */     for (int $$7 = 0; $$7 < 3; $$7++) {
/* 105 */       for (int $$8 = 0; $$8 < 9; $$8++) {
/* 106 */         addSlot(new Slot((Container)$$0, $$8 + ($$7 + 1) * 9, 8 + $$8 * 18, 84 + $$7 * 18));
/*     */       }
/*     */     } 
/* 109 */     for (int $$9 = 0; $$9 < 9; $$9++) {
/* 110 */       addSlot(new Slot((Container)$$0, $$9, 8 + $$9 * 18, 142));
/*     */     }
/*     */     
/* 113 */     addSlot(new Slot((Container)$$0, 40, 77, 62)
/*     */         {
/*     */           public void setByPlayer(ItemStack $$0, ItemStack $$1) {
/* 116 */             InventoryMenu.onEquipItem(owner, EquipmentSlot.OFFHAND, $$0, $$1);
/* 117 */             super.setByPlayer($$0, $$1);
/*     */           }
/*     */ 
/*     */           
/*     */           public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/* 122 */             return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   static void onEquipItem(Player $$0, EquipmentSlot $$1, ItemStack $$2, ItemStack $$3) {
/* 128 */     $$0.onEquipItem($$1, $$3, $$2);
/*     */   }
/*     */   
/*     */   public static boolean isHotbarSlot(int $$0) {
/* 132 */     return (($$0 >= 36 && $$0 < 45) || $$0 == 45);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fillCraftSlotsStackedContents(StackedContents $$0) {
/* 137 */     this.craftSlots.fillStackedContents($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearCraftingContent() {
/* 142 */     this.resultSlots.clearContent();
/* 143 */     this.craftSlots.clearContent();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean recipeMatches(RecipeHolder<? extends Recipe<CraftingContainer>> $$0) {
/* 148 */     return $$0.value().matches(this.craftSlots, this.owner.level());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 155 */     CraftingMenu.slotChangedCraftingGrid(this, this.owner.level(), this.owner, this.craftSlots, this.resultSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 160 */     super.removed($$0);
/*     */     
/* 162 */     this.resultSlots.clearContent();
/*     */     
/* 164 */     if (($$0.level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/* 168 */     clearContainer($$0, this.craftSlots);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 178 */     ItemStack $$2 = ItemStack.EMPTY;
/* 179 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 180 */     if ($$3.hasItem()) {
/* 181 */       ItemStack $$4 = $$3.getItem();
/* 182 */       $$2 = $$4.copy();
/*     */       
/* 184 */       EquipmentSlot $$5 = Mob.getEquipmentSlotForItem($$2);
/*     */       
/* 186 */       if ($$1 == 0) {
/* 187 */         if (!moveItemStackTo($$4, 9, 45, true)) {
/* 188 */           return ItemStack.EMPTY;
/*     */         }
/* 190 */         $$3.onQuickCraft($$4, $$2);
/* 191 */       } else if ($$1 >= 1 && $$1 < 5) {
/* 192 */         if (!moveItemStackTo($$4, 9, 45, false)) {
/* 193 */           return ItemStack.EMPTY;
/*     */         }
/* 195 */       } else if ($$1 >= 5 && $$1 < 9) {
/* 196 */         if (!moveItemStackTo($$4, 9, 45, false)) {
/* 197 */           return ItemStack.EMPTY;
/*     */         }
/* 199 */       } else if ($$5.getType() == EquipmentSlot.Type.ARMOR && !((Slot)this.slots.get(8 - $$5.getIndex())).hasItem()) {
/* 200 */         int $$6 = 8 - $$5.getIndex();
/* 201 */         if (!moveItemStackTo($$4, $$6, $$6 + 1, false)) {
/* 202 */           return ItemStack.EMPTY;
/*     */         }
/* 204 */       } else if ($$5 == EquipmentSlot.OFFHAND && !((Slot)this.slots.get(45)).hasItem()) {
/* 205 */         if (!moveItemStackTo($$4, 45, 46, false)) {
/* 206 */           return ItemStack.EMPTY;
/*     */         }
/* 208 */       } else if ($$1 >= 9 && $$1 < 36) {
/* 209 */         if (!moveItemStackTo($$4, 36, 45, false)) {
/* 210 */           return ItemStack.EMPTY;
/*     */         }
/* 212 */       } else if ($$1 >= 36 && $$1 < 45) {
/* 213 */         if (!moveItemStackTo($$4, 9, 36, false)) {
/* 214 */           return ItemStack.EMPTY;
/*     */         }
/*     */       }
/* 217 */       else if (!moveItemStackTo($$4, 9, 45, false)) {
/* 218 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 221 */       if ($$4.isEmpty()) {
/* 222 */         $$3.setByPlayer(ItemStack.EMPTY, $$2);
/*     */       } else {
/* 224 */         $$3.setChanged();
/*     */       } 
/* 226 */       if ($$4.getCount() == $$2.getCount())
/*     */       {
/* 228 */         return ItemStack.EMPTY;
/*     */       }
/* 230 */       $$3.onTake($$0, $$4);
/* 231 */       if ($$1 == 0) {
/* 232 */         $$0.drop($$4, false);
/*     */       }
/*     */     } 
/*     */     
/* 236 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 241 */     return ($$1.container != this.resultSlots && super.canTakeItemForPickAll($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getResultSlotIndex() {
/* 246 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridWidth() {
/* 251 */     return this.craftSlots.getWidth();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGridHeight() {
/* 256 */     return this.craftSlots.getHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/* 261 */     return 5;
/*     */   }
/*     */   
/*     */   public CraftingContainer getCraftSlots() {
/* 265 */     return this.craftSlots;
/*     */   }
/*     */ 
/*     */   
/*     */   public RecipeBookType getRecipeBookType() {
/* 270 */     return RecipeBookType.CRAFTING;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldMoveToInventory(int $$0) {
/* 275 */     return ($$0 != getResultSlotIndex());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\InventoryMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */