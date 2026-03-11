/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.ExperienceOrb;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.Enchantment;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class GrindstoneMenu
/*     */   extends AbstractContainerMenu {
/*     */   public static final int MAX_NAME_LENGTH = 35;
/*     */   public static final int INPUT_SLOT = 0;
/*     */   public static final int ADDITIONAL_SLOT = 1;
/*     */   public static final int RESULT_SLOT = 2;
/*     */   private static final int INV_SLOT_START = 3;
/*     */   private static final int INV_SLOT_END = 30;
/*     */   private static final int USE_ROW_SLOT_START = 30;
/*     */   private static final int USE_ROW_SLOT_END = 39;
/*  33 */   private final Container resultSlots = new ResultContainer();
/*  34 */   final Container repairSlots = (Container)new SimpleContainer(2)
/*     */     {
/*     */       public void setChanged() {
/*  37 */         super.setChanged();
/*  38 */         GrindstoneMenu.this.slotsChanged((Container)this);
/*     */       }
/*     */     };
/*     */   
/*     */   private final ContainerLevelAccess access;
/*     */   
/*     */   public GrindstoneMenu(int $$0, Inventory $$1) {
/*  45 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public GrindstoneMenu(int $$0, Inventory $$1, final ContainerLevelAccess access) {
/*  49 */     super(MenuType.GRINDSTONE, $$0);
/*  50 */     this.access = access;
/*     */     
/*  52 */     addSlot(new Slot(this.repairSlots, 0, 49, 19)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  55 */             return ($$0.isDamageableItem() || $$0.is(Items.ENCHANTED_BOOK) || $$0.isEnchanted());
/*     */           }
/*     */         });
/*  58 */     addSlot(new Slot(this.repairSlots, 1, 49, 40)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  61 */             return ($$0.isDamageableItem() || $$0.is(Items.ENCHANTED_BOOK) || $$0.isEnchanted());
/*     */           }
/*     */         });
/*  64 */     addSlot(new Slot(this.resultSlots, 2, 129, 34)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  67 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onTake(Player $$0, ItemStack $$1) {
/*  72 */             access.execute(($$0, $$1) -> {
/*     */                   if ($$0 instanceof ServerLevel) {
/*     */                     ExperienceOrb.award((ServerLevel)$$0, Vec3.atCenterOf((Vec3i)$$1), getExperienceAmount($$0));
/*     */                   }
/*     */                   
/*     */                   $$0.levelEvent(1042, $$1, 0);
/*     */                 });
/*  79 */             GrindstoneMenu.this.repairSlots.setItem(0, ItemStack.EMPTY);
/*  80 */             GrindstoneMenu.this.repairSlots.setItem(1, ItemStack.EMPTY);
/*     */           }
/*     */           
/*     */           private int getExperienceAmount(Level $$0) {
/*  84 */             int $$1 = 0;
/*  85 */             $$1 += getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(0));
/*  86 */             $$1 += getExperienceFromItem(GrindstoneMenu.this.repairSlots.getItem(1));
/*     */             
/*  88 */             if ($$1 > 0) {
/*  89 */               int $$2 = (int)Math.ceil($$1 / 2.0D);
/*  90 */               return $$2 + $$0.random.nextInt($$2);
/*     */             } 
/*     */             
/*  93 */             return 0;
/*     */           }
/*     */           
/*     */           private int getExperienceFromItem(ItemStack $$0) {
/*  97 */             int $$1 = 0;
/*  98 */             Map<Enchantment, Integer> $$2 = EnchantmentHelper.getEnchantments($$0);
/*  99 */             for (Map.Entry<Enchantment, Integer> $$3 : $$2.entrySet()) {
/* 100 */               Enchantment $$4 = $$3.getKey();
/* 101 */               Integer $$5 = $$3.getValue();
/*     */               
/* 103 */               if (!$$4.isCurse()) {
/* 104 */                 $$1 += $$4.getMinCost($$5.intValue());
/*     */               }
/*     */             } 
/*     */             
/* 108 */             return $$1;
/*     */           }
/*     */         });
/*     */     
/* 112 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/* 113 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/* 114 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/* 117 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/* 118 */       addSlot(new Slot((Container)$$1, $$5, 8 + $$5 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 124 */     super.slotsChanged($$0);
/*     */     
/* 126 */     if ($$0 == this.repairSlots) {
/* 127 */       createResult();
/*     */     }
/*     */   }
/*     */   
/*     */   private void createResult() {
/* 132 */     ItemStack $$0 = this.repairSlots.getItem(0);
/* 133 */     ItemStack $$1 = this.repairSlots.getItem(1);
/*     */     
/* 135 */     boolean $$2 = (!$$0.isEmpty() || !$$1.isEmpty());
/* 136 */     boolean $$3 = (!$$0.isEmpty() && !$$1.isEmpty());
/*     */     
/* 138 */     if ($$2) {
/* 139 */       int $$13; ItemStack $$14; boolean $$4 = ((!$$0.isEmpty() && !$$0.is(Items.ENCHANTED_BOOK) && !$$0.isEnchanted()) || (!$$1.isEmpty() && !$$1.is(Items.ENCHANTED_BOOK) && !$$1.isEnchanted()));
/* 140 */       if ($$0.getCount() > 1 || $$1.getCount() > 1 || (!$$3 && $$4)) {
/* 141 */         this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 142 */         broadcastChanges();
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 147 */       int $$5 = 1;
/*     */       
/* 149 */       if ($$3) {
/* 150 */         if ($$0.is($$1.getItem())) {
/* 151 */           Item $$6 = $$0.getItem();
/* 152 */           int $$7 = $$6.getMaxDamage() - $$0.getDamageValue();
/* 153 */           int $$8 = $$6.getMaxDamage() - $$1.getDamageValue();
/* 154 */           int $$9 = $$7 + $$8 + $$6.getMaxDamage() * 5 / 100;
/* 155 */           int $$10 = Math.max($$6.getMaxDamage() - $$9, 0);
/*     */           
/* 157 */           ItemStack $$11 = mergeEnchants($$0, $$1);
/*     */           
/* 159 */           if (!$$11.isDamageableItem()) {
/* 160 */             if (!ItemStack.matches($$0, $$1)) {
/* 161 */               this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 162 */               broadcastChanges();
/*     */               
/*     */               return;
/*     */             } 
/* 166 */             $$5 = 2;
/*     */           } 
/*     */         } else {
/* 169 */           this.resultSlots.setItem(0, ItemStack.EMPTY);
/* 170 */           broadcastChanges();
/*     */           return;
/*     */         } 
/*     */       } else {
/* 174 */         boolean $$12 = !$$0.isEmpty();
/* 175 */         $$13 = $$12 ? $$0.getDamageValue() : $$1.getDamageValue();
/* 176 */         $$14 = $$12 ? $$0 : $$1;
/*     */       } 
/*     */       
/* 179 */       this.resultSlots.setItem(0, removeNonCurses($$14, $$13, $$5));
/*     */     } else {
/* 181 */       this.resultSlots.setItem(0, ItemStack.EMPTY);
/*     */     } 
/*     */     
/* 184 */     broadcastChanges();
/*     */   }
/*     */   
/*     */   private ItemStack mergeEnchants(ItemStack $$0, ItemStack $$1) {
/* 188 */     ItemStack $$2 = $$0.copy();
/*     */     
/* 190 */     Map<Enchantment, Integer> $$3 = EnchantmentHelper.getEnchantments($$1);
/* 191 */     for (Map.Entry<Enchantment, Integer> $$4 : $$3.entrySet()) {
/* 192 */       Enchantment $$5 = $$4.getKey();
/* 193 */       if (!$$5.isCurse() || EnchantmentHelper.getItemEnchantmentLevel($$5, $$2) == 0) {
/* 194 */         $$2.enchant($$5, ((Integer)$$4.getValue()).intValue());
/*     */       }
/*     */     } 
/*     */     
/* 198 */     return $$2;
/*     */   }
/*     */   
/*     */   private ItemStack removeNonCurses(ItemStack $$0, int $$1, int $$2) {
/* 202 */     ItemStack $$3 = $$0.copyWithCount($$2);
/* 203 */     $$3.removeTagKey("Enchantments");
/* 204 */     $$3.removeTagKey("StoredEnchantments");
/*     */     
/* 206 */     if ($$1 > 0) {
/* 207 */       $$3.setDamageValue($$1);
/*     */     } else {
/* 209 */       $$3.removeTagKey("Damage");
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 217 */     Map<Enchantment, Integer> $$4 = (Map<Enchantment, Integer>)EnchantmentHelper.getEnchantments($$0).entrySet().stream().filter($$0 -> ((Enchantment)$$0.getKey()).isCurse()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
/*     */     
/* 219 */     EnchantmentHelper.setEnchantments($$4, $$3);
/* 220 */     $$3.setRepairCost(0);
/*     */     
/* 222 */     if ($$3.is(Items.ENCHANTED_BOOK) && $$4.size() == 0) {
/* 223 */       $$3 = new ItemStack((ItemLike)Items.BOOK);
/* 224 */       if ($$0.hasCustomHoverName()) {
/* 225 */         $$3.setHoverName($$0.getHoverName());
/*     */       }
/*     */     } 
/*     */     
/* 229 */     for (int $$5 = 0; $$5 < $$4.size(); $$5++) {
/* 230 */       $$3.setRepairCost(AnvilMenu.calculateIncreasedRepairCost($$3.getBaseRepairCost()));
/*     */     }
/*     */     
/* 233 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 238 */     super.removed($$0);
/* 239 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.repairSlots));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 244 */     return stillValid(this.access, $$0, Blocks.GRINDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 249 */     ItemStack $$2 = ItemStack.EMPTY;
/* 250 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 251 */     if ($$3 != null && $$3.hasItem()) {
/* 252 */       ItemStack $$4 = $$3.getItem();
/* 253 */       $$2 = $$4.copy();
/*     */       
/* 255 */       ItemStack $$5 = this.repairSlots.getItem(0);
/* 256 */       ItemStack $$6 = this.repairSlots.getItem(1);
/*     */       
/* 258 */       if ($$1 == 2) {
/* 259 */         if (!moveItemStackTo($$4, 3, 39, true)) {
/* 260 */           return ItemStack.EMPTY;
/*     */         }
/* 262 */         $$3.onQuickCraft($$4, $$2);
/* 263 */       } else if ($$1 == 0 || $$1 == 1) {
/* 264 */         if (!moveItemStackTo($$4, 3, 39, false)) {
/* 265 */           return ItemStack.EMPTY;
/*     */         }
/* 267 */       } else if ($$5.isEmpty() || $$6.isEmpty()) {
/* 268 */         if (!moveItemStackTo($$4, 0, 2, false)) {
/* 269 */           return ItemStack.EMPTY;
/*     */         }
/* 271 */       } else if ($$1 >= 3 && $$1 < 30) {
/* 272 */         if (!moveItemStackTo($$4, 30, 39, false)) {
/* 273 */           return ItemStack.EMPTY;
/*     */         }
/* 275 */       } else if ($$1 >= 30 && $$1 < 39 && 
/* 276 */         !moveItemStackTo($$4, 3, 30, false)) {
/* 277 */         return ItemStack.EMPTY;
/*     */       } 
/*     */ 
/*     */       
/* 281 */       if ($$4.isEmpty()) {
/* 282 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 284 */         $$3.setChanged();
/*     */       } 
/*     */       
/* 287 */       if ($$4.getCount() == $$2.getCount()) {
/* 288 */         return ItemStack.EMPTY;
/*     */       }
/* 290 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\GrindstoneMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */