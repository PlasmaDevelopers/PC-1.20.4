/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.tags.BlockTags;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.EnchantedBookItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.AnvilBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class AnvilMenu
/*     */   extends ItemCombinerMenu
/*     */ {
/*     */   public static final int INPUT_SLOT = 0;
/*     */   public static final int ADDITIONAL_SLOT = 1;
/*     */   public static final int RESULT_SLOT = 2;
/*  29 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final boolean DEBUG_COST = false;
/*     */   
/*     */   public static final int MAX_NAME_LENGTH = 50;
/*     */   
/*     */   private int repairItemCountCost;
/*     */   @Nullable
/*     */   private String itemName;
/*  38 */   private final DataSlot cost = DataSlot.standalone();
/*     */ 
/*     */   
/*     */   private static final int COST_FAIL = 0;
/*     */   
/*     */   private static final int COST_BASE = 1;
/*     */   
/*     */   private static final int COST_ADDED_BASE = 1;
/*     */   
/*     */   private static final int COST_REPAIR_MATERIAL = 1;
/*     */   
/*     */   private static final int COST_REPAIR_SACRIFICE = 2;
/*     */   
/*     */   private static final int COST_INCOMPATIBLE_PENALTY = 1;
/*     */   
/*     */   private static final int COST_RENAME = 1;
/*     */   
/*     */   private static final int INPUT_SLOT_X_PLACEMENT = 27;
/*     */   
/*     */   private static final int ADDITIONAL_SLOT_X_PLACEMENT = 76;
/*     */   
/*     */   private static final int RESULT_SLOT_X_PLACEMENT = 134;
/*     */   
/*     */   private static final int SLOT_Y_PLACEMENT = 47;
/*     */ 
/*     */   
/*     */   public AnvilMenu(int $$0, Inventory $$1) {
/*  65 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public AnvilMenu(int $$0, Inventory $$1, ContainerLevelAccess $$2) {
/*  69 */     super(MenuType.ANVIL, $$0, $$1, $$2);
/*     */     
/*  71 */     addDataSlot(this.cost);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemCombinerMenuSlotDefinition createInputSlotDefinitions() {
/*  76 */     return ItemCombinerMenuSlotDefinition.create()
/*  77 */       .withSlot(0, 27, 47, $$0 -> true)
/*  78 */       .withSlot(1, 76, 47, $$0 -> true)
/*  79 */       .withResultSlot(2, 134, 47)
/*  80 */       .build();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isValidBlock(BlockState $$0) {
/*  85 */     return $$0.is(BlockTags.ANVIL);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean mayPickup(Player $$0, boolean $$1) {
/*  90 */     return ((($$0.getAbilities()).instabuild || $$0.experienceLevel >= this.cost.get()) && this.cost.get() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onTake(Player $$0, ItemStack $$1) {
/*  95 */     if (!($$0.getAbilities()).instabuild) {
/*  96 */       $$0.giveExperienceLevels(-this.cost.get());
/*     */     }
/*  98 */     this.inputSlots.setItem(0, ItemStack.EMPTY);
/*     */     
/* 100 */     if (this.repairItemCountCost > 0) {
/* 101 */       ItemStack $$2 = this.inputSlots.getItem(1);
/* 102 */       if (!$$2.isEmpty() && $$2.getCount() > this.repairItemCountCost) {
/* 103 */         $$2.shrink(this.repairItemCountCost);
/* 104 */         this.inputSlots.setItem(1, $$2);
/*     */       } else {
/* 106 */         this.inputSlots.setItem(1, ItemStack.EMPTY);
/*     */       } 
/*     */     } else {
/* 109 */       this.inputSlots.setItem(1, ItemStack.EMPTY);
/*     */     } 
/* 111 */     this.cost.set(0);
/*     */     
/* 113 */     this.access.execute(($$1, $$2) -> {
/*     */           BlockState $$3 = $$1.getBlockState($$2);
/*     */           if (!($$0.getAbilities()).instabuild && $$3.is(BlockTags.ANVIL) && $$0.getRandom().nextFloat() < 0.12F) {
/*     */             BlockState $$4 = AnvilBlock.damage($$3);
/*     */             if ($$4 == null) {
/*     */               $$1.removeBlock($$2, false);
/*     */               $$1.levelEvent(1029, $$2, 0);
/*     */             } else {
/*     */               $$1.setBlock($$2, $$4, 2);
/*     */               $$1.levelEvent(1030, $$2, 0);
/*     */             } 
/*     */           } else {
/*     */             $$1.levelEvent(1030, $$2, 0);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void createResult() {
/* 132 */     ItemStack $$0 = this.inputSlots.getItem(0);
/* 133 */     this.cost.set(1);
/* 134 */     int $$1 = 0;
/* 135 */     int $$2 = 0;
/* 136 */     int $$3 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 142 */     if ($$0.isEmpty()) {
/* 143 */       this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 144 */       this.cost.set(0);
/*     */       
/*     */       return;
/*     */     } 
/* 148 */     ItemStack $$4 = $$0.copy();
/* 149 */     ItemStack $$5 = this.inputSlots.getItem(1);
/* 150 */     Map<Enchantment, Integer> $$6 = EnchantmentHelper.getEnchantments($$4);
/*     */     
/* 152 */     $$2 += $$0.getBaseRepairCost() + ($$5.isEmpty() ? 0 : $$5.getBaseRepairCost());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 157 */     this.repairItemCountCost = 0;
/*     */     
/* 159 */     if (!$$5.isEmpty()) {
/* 160 */       boolean $$7 = ($$5.is(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantments($$5).isEmpty());
/*     */       
/* 162 */       if ($$4.isDamageableItem() && $$4.getItem().isValidRepairItem($$0, $$5)) {
/* 163 */         int $$8 = Math.min($$4.getDamageValue(), $$4.getMaxDamage() / 4);
/* 164 */         if ($$8 <= 0) {
/* 165 */           this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 166 */           this.cost.set(0);
/*     */           return;
/*     */         } 
/* 169 */         int $$9 = 0;
/* 170 */         while ($$8 > 0 && $$9 < $$5.getCount()) {
/* 171 */           int $$10 = $$4.getDamageValue() - $$8;
/* 172 */           $$4.setDamageValue($$10);
/* 173 */           $$1++;
/*     */           
/* 175 */           $$8 = Math.min($$4.getDamageValue(), $$4.getMaxDamage() / 4);
/* 176 */           $$9++;
/*     */         } 
/* 178 */         this.repairItemCountCost = $$9;
/*     */       } else {
/* 180 */         if (!$$7 && (!$$4.is($$5.getItem()) || !$$4.isDamageableItem())) {
/* 181 */           this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 182 */           this.cost.set(0);
/*     */           return;
/*     */         } 
/* 185 */         if ($$4.isDamageableItem() && !$$7) {
/* 186 */           int $$11 = $$0.getMaxDamage() - $$0.getDamageValue();
/* 187 */           int $$12 = $$5.getMaxDamage() - $$5.getDamageValue();
/* 188 */           int $$13 = $$12 + $$4.getMaxDamage() * 12 / 100;
/* 189 */           int $$14 = $$11 + $$13;
/* 190 */           int $$15 = $$4.getMaxDamage() - $$14;
/* 191 */           if ($$15 < 0) {
/* 192 */             $$15 = 0;
/*     */           }
/*     */           
/* 195 */           if ($$15 < $$4.getDamageValue()) {
/* 196 */             $$4.setDamageValue($$15);
/* 197 */             $$1 += 2;
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 204 */         Map<Enchantment, Integer> $$16 = EnchantmentHelper.getEnchantments($$5);
/* 205 */         boolean $$17 = false;
/* 206 */         boolean $$18 = false;
/*     */         
/* 208 */         for (Enchantment $$19 : $$16.keySet()) {
/* 209 */           if ($$19 == null) {
/*     */             continue;
/*     */           }
/* 212 */           int $$20 = ((Integer)$$6.getOrDefault($$19, Integer.valueOf(0))).intValue();
/* 213 */           int $$21 = ((Integer)$$16.get($$19)).intValue();
/* 214 */           $$21 = ($$20 == $$21) ? ($$21 + 1) : Math.max($$21, $$20);
/*     */           
/* 216 */           boolean $$22 = $$19.canEnchant($$0);
/* 217 */           if ((this.player.getAbilities()).instabuild || $$0.is(Items.ENCHANTED_BOOK)) {
/* 218 */             $$22 = true;
/*     */           }
/*     */           
/* 221 */           for (Enchantment $$23 : $$6.keySet()) {
/* 222 */             if ($$23 != $$19 && !$$19.isCompatibleWith($$23)) {
/* 223 */               $$22 = false;
/* 224 */               $$1++;
/*     */             } 
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 231 */           if (!$$22) {
/* 232 */             $$18 = true;
/*     */             continue;
/*     */           } 
/* 235 */           $$17 = true;
/* 236 */           if ($$21 > $$19.getMaxLevel()) {
/* 237 */             $$21 = $$19.getMaxLevel();
/*     */           }
/* 239 */           $$6.put($$19, Integer.valueOf($$21));
/* 240 */           int $$24 = 0;
/*     */           
/* 242 */           switch ($$19.getRarity()) {
/*     */             case COMMON:
/* 244 */               $$24 = 1;
/*     */               break;
/*     */             case UNCOMMON:
/* 247 */               $$24 = 2;
/*     */               break;
/*     */             case RARE:
/* 250 */               $$24 = 4;
/*     */               break;
/*     */             case VERY_RARE:
/* 253 */               $$24 = 8;
/*     */               break;
/*     */           } 
/*     */           
/* 257 */           if ($$7) {
/* 258 */             $$24 = Math.max(1, $$24 / 2);
/*     */           }
/*     */           
/* 261 */           $$1 += $$24 * $$21;
/*     */           
/* 263 */           if ($$0.getCount() > 1) {
/* 264 */             $$1 = 40;
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 271 */         if ($$18 && !$$17) {
/*     */           
/* 273 */           this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 274 */           this.cost.set(0);
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 280 */     if (this.itemName == null || Util.isBlank(this.itemName)) {
/* 281 */       if ($$0.hasCustomHoverName()) {
/* 282 */         $$3 = 1;
/*     */         
/* 284 */         $$1 += $$3;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 289 */         $$4.resetHoverName();
/*     */       } 
/* 291 */     } else if (!this.itemName.equals($$0.getHoverName().getString())) {
/* 292 */       $$3 = 1;
/*     */       
/* 294 */       $$1 += $$3;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 299 */       $$4.setHoverName((Component)Component.literal(this.itemName));
/*     */     } 
/*     */     
/* 302 */     this.cost.set($$2 + $$1);
/* 303 */     if ($$1 <= 0)
/*     */     {
/*     */ 
/*     */       
/* 307 */       $$4 = ItemStack.EMPTY;
/*     */     }
/* 309 */     if ($$3 == $$1 && $$3 > 0 && this.cost.get() >= 40)
/*     */     {
/*     */ 
/*     */       
/* 313 */       this.cost.set(39);
/*     */     }
/* 315 */     if (this.cost.get() >= 40 && !(this.player.getAbilities()).instabuild)
/*     */     {
/*     */ 
/*     */       
/* 319 */       $$4 = ItemStack.EMPTY;
/*     */     }
/*     */     
/* 322 */     if (!$$4.isEmpty()) {
/* 323 */       int $$25 = $$4.getBaseRepairCost();
/* 324 */       if (!$$5.isEmpty() && $$25 < $$5.getBaseRepairCost()) {
/* 325 */         $$25 = $$5.getBaseRepairCost();
/*     */       }
/*     */       
/* 328 */       if ($$3 != $$1 || $$3 == 0) {
/* 329 */         $$25 = calculateIncreasedRepairCost($$25);
/*     */       }
/*     */       
/* 332 */       $$4.setRepairCost($$25);
/* 333 */       EnchantmentHelper.setEnchantments($$6, $$4);
/*     */     } 
/*     */     
/* 336 */     this.resultSlots.setItem(0, $$4);
/*     */     
/* 338 */     broadcastChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calculateIncreasedRepairCost(int $$0) {
/* 346 */     return $$0 * 2 + 1;
/*     */   }
/*     */   
/*     */   public boolean setItemName(String $$0) {
/* 350 */     String $$1 = validateName($$0);
/* 351 */     if ($$1 == null || $$1.equals(this.itemName)) {
/* 352 */       return false;
/*     */     }
/*     */     
/* 355 */     this.itemName = $$1;
/*     */     
/* 357 */     if (getSlot(2).hasItem()) {
/* 358 */       ItemStack $$2 = getSlot(2).getItem();
/*     */       
/* 360 */       if (Util.isBlank($$1)) {
/* 361 */         $$2.resetHoverName();
/*     */       } else {
/* 363 */         $$2.setHoverName((Component)Component.literal($$1));
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     createResult();
/* 368 */     return true;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static String validateName(String $$0) {
/* 373 */     String $$1 = SharedConstants.filterText($$0);
/* 374 */     if ($$1.length() <= 50) {
/* 375 */       return $$1;
/*     */     }
/* 377 */     return null;
/*     */   }
/*     */   
/*     */   public int getCost() {
/* 381 */     return this.cost.get();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\AnvilMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */