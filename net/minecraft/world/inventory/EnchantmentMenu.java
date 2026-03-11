/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.List;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.EnchantedBookItem;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.world.item.enchantment.EnchantmentInstance;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.EnchantmentTableBlock;
/*     */ 
/*     */ public class EnchantmentMenu
/*     */   extends AbstractContainerMenu {
/*  32 */   static final ResourceLocation EMPTY_SLOT_LAPIS_LAZULI = new ResourceLocation("item/empty_slot_lapis_lazuli");
/*     */   
/*  34 */   private final Container enchantSlots = (Container)new SimpleContainer(2)
/*     */     {
/*     */       public void setChanged() {
/*  37 */         super.setChanged();
/*  38 */         EnchantmentMenu.this.slotsChanged((Container)this);
/*     */       }
/*     */     };
/*     */   
/*     */   private final ContainerLevelAccess access;
/*  43 */   private final RandomSource random = RandomSource.create();
/*  44 */   private final DataSlot enchantmentSeed = DataSlot.standalone();
/*     */   
/*  46 */   public final int[] costs = new int[3];
/*  47 */   public final int[] enchantClue = new int[] { -1, -1, -1 };
/*  48 */   public final int[] levelClue = new int[] { -1, -1, -1 };
/*     */   
/*     */   public EnchantmentMenu(int $$0, Inventory $$1) {
/*  51 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public EnchantmentMenu(int $$0, Inventory $$1, ContainerLevelAccess $$2) {
/*  55 */     super(MenuType.ENCHANTMENT, $$0);
/*  56 */     this.access = $$2;
/*  57 */     addSlot(new Slot(this.enchantSlots, 0, 15, 47)
/*     */         {
/*     */           public int getMaxStackSize() {
/*  60 */             return 1;
/*     */           }
/*     */         });
/*     */     
/*  64 */     addSlot(new Slot(this.enchantSlots, 1, 35, 47)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  67 */             return $$0.is(Items.LAPIS_LAZULI);
/*     */           }
/*     */ 
/*     */           
/*     */           public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
/*  72 */             return Pair.of(InventoryMenu.BLOCK_ATLAS, EnchantmentMenu.EMPTY_SLOT_LAPIS_LAZULI);
/*     */           }
/*     */         });
/*     */     
/*  76 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/*  77 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/*  78 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/*  81 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/*  82 */       addSlot(new Slot((Container)$$1, $$5, 8 + $$5 * 18, 142));
/*     */     }
/*     */     
/*  85 */     addDataSlot(DataSlot.shared(this.costs, 0));
/*  86 */     addDataSlot(DataSlot.shared(this.costs, 1));
/*  87 */     addDataSlot(DataSlot.shared(this.costs, 2));
/*     */     
/*  89 */     addDataSlot(this.enchantmentSeed).set($$1.player.getEnchantmentSeed());
/*     */     
/*  91 */     addDataSlot(DataSlot.shared(this.enchantClue, 0));
/*  92 */     addDataSlot(DataSlot.shared(this.enchantClue, 1));
/*  93 */     addDataSlot(DataSlot.shared(this.enchantClue, 2));
/*     */     
/*  95 */     addDataSlot(DataSlot.shared(this.levelClue, 0));
/*  96 */     addDataSlot(DataSlot.shared(this.levelClue, 1));
/*  97 */     addDataSlot(DataSlot.shared(this.levelClue, 2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 102 */     if ($$0 == this.enchantSlots) {
/* 103 */       ItemStack $$1 = $$0.getItem(0);
/*     */       
/* 105 */       if ($$1.isEmpty() || !$$1.isEnchantable()) {
/* 106 */         for (int $$2 = 0; $$2 < 3; $$2++) {
/* 107 */           this.costs[$$2] = 0;
/* 108 */           this.enchantClue[$$2] = -1;
/* 109 */           this.levelClue[$$2] = -1;
/*     */         } 
/*     */       } else {
/* 112 */         this.access.execute(($$1, $$2) -> {
/*     */               int $$3 = 0;
/*     */               for (BlockPos $$4 : EnchantmentTableBlock.BOOKSHELF_OFFSETS) {
/*     */                 if (EnchantmentTableBlock.isValidBookShelf($$1, $$2, $$4)) {
/*     */                   $$3++;
/*     */                 }
/*     */               } 
/*     */               this.random.setSeed(this.enchantmentSeed.get());
/*     */               for (int $$5 = 0; $$5 < 3; $$5++) {
/*     */                 this.costs[$$5] = EnchantmentHelper.getEnchantmentCost(this.random, $$5, $$3, $$0);
/*     */                 this.enchantClue[$$5] = -1;
/*     */                 this.levelClue[$$5] = -1;
/*     */                 if (this.costs[$$5] < $$5 + 1) {
/*     */                   this.costs[$$5] = 0;
/*     */                 }
/*     */               } 
/*     */               for (int $$6 = 0; $$6 < 3; $$6++) {
/*     */                 if (this.costs[$$6] > 0) {
/*     */                   List<EnchantmentInstance> $$7 = getEnchantmentList($$0, $$6, this.costs[$$6]);
/*     */                   if ($$7 != null && !$$7.isEmpty()) {
/*     */                     EnchantmentInstance $$8 = $$7.get(this.random.nextInt($$7.size()));
/*     */                     this.enchantClue[$$6] = BuiltInRegistries.ENCHANTMENT.getId($$8.enchantment);
/*     */                     this.levelClue[$$6] = $$8.level;
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */               broadcastChanges();
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickMenuButton(Player $$0, int $$1) {
/* 150 */     if ($$1 < 0 || $$1 >= this.costs.length) {
/* 151 */       Util.logAndPauseIfInIde("" + $$0.getName() + " pressed invalid button id: " + $$0.getName());
/* 152 */       return false;
/*     */     } 
/*     */     
/* 155 */     ItemStack $$2 = this.enchantSlots.getItem(0);
/* 156 */     ItemStack $$3 = this.enchantSlots.getItem(1);
/*     */     
/* 158 */     int $$4 = $$1 + 1;
/*     */     
/* 160 */     if (($$3.isEmpty() || $$3.getCount() < $$4) && !($$0.getAbilities()).instabuild) {
/* 161 */       return false;
/*     */     }
/*     */     
/* 164 */     if (this.costs[$$1] > 0 && !$$2.isEmpty() && (($$0.experienceLevel >= $$4 && $$0.experienceLevel >= this.costs[$$1]) || ($$0.getAbilities()).instabuild)) {
/* 165 */       this.access.execute(($$5, $$6) -> {
/*     */             ItemStack $$7 = $$0;
/*     */             
/*     */             List<EnchantmentInstance> $$8 = getEnchantmentList($$7, $$1, this.costs[$$1]);
/*     */             
/*     */             if (!$$8.isEmpty()) {
/*     */               $$2.onEnchantmentPerformed($$7, $$3);
/*     */               
/*     */               boolean $$9 = $$7.is(Items.BOOK);
/*     */               
/*     */               if ($$9) {
/*     */                 $$7 = new ItemStack((ItemLike)Items.ENCHANTED_BOOK);
/*     */                 
/*     */                 CompoundTag $$10 = $$0.getTag();
/*     */                 
/*     */                 if ($$10 != null) {
/*     */                   $$7.setTag($$10.copy());
/*     */                 }
/*     */                 this.enchantSlots.setItem(0, $$7);
/*     */               } 
/*     */               for (EnchantmentInstance $$11 : $$8) {
/*     */                 if ($$9) {
/*     */                   EnchantedBookItem.addEnchantment($$7, $$11);
/*     */                   continue;
/*     */                 } 
/*     */                 $$7.enchant($$11.enchantment, $$11.level);
/*     */               } 
/*     */               if (!($$2.getAbilities()).instabuild) {
/*     */                 $$4.shrink($$3);
/*     */                 if ($$4.isEmpty()) {
/*     */                   this.enchantSlots.setItem(1, ItemStack.EMPTY);
/*     */                 }
/*     */               } 
/*     */               $$2.awardStat(Stats.ENCHANT_ITEM);
/*     */               if ($$2 instanceof ServerPlayer) {
/*     */                 CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)$$2, $$7, $$3);
/*     */               }
/*     */               this.enchantSlots.setChanged();
/*     */               this.enchantmentSeed.set($$2.getEnchantmentSeed());
/*     */               slotsChanged(this.enchantSlots);
/*     */               $$5.playSound(null, $$6, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, $$5.random.nextFloat() * 0.1F + 0.9F);
/*     */             } 
/*     */           });
/* 208 */       return true;
/*     */     } 
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   private List<EnchantmentInstance> getEnchantmentList(ItemStack $$0, int $$1, int $$2) {
/* 214 */     this.random.setSeed((this.enchantmentSeed.get() + $$1));
/*     */     
/* 216 */     List<EnchantmentInstance> $$3 = EnchantmentHelper.selectEnchantment(this.random, $$0, $$2, false);
/*     */     
/* 218 */     if ($$0.is(Items.BOOK) && $$3.size() > 1)
/*     */     {
/* 220 */       $$3.remove(this.random.nextInt($$3.size()));
/*     */     }
/* 222 */     return $$3;
/*     */   }
/*     */   
/*     */   public int getGoldCount() {
/* 226 */     ItemStack $$0 = this.enchantSlots.getItem(1);
/* 227 */     if ($$0.isEmpty()) {
/* 228 */       return 0;
/*     */     }
/* 230 */     return $$0.getCount();
/*     */   }
/*     */   
/*     */   public int getEnchantmentSeed() {
/* 234 */     return this.enchantmentSeed.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 239 */     super.removed($$0);
/* 240 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.enchantSlots));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 245 */     return stillValid(this.access, $$0, Blocks.ENCHANTING_TABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 250 */     ItemStack $$2 = ItemStack.EMPTY;
/* 251 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 252 */     if ($$3 != null && $$3.hasItem()) {
/* 253 */       ItemStack $$4 = $$3.getItem();
/* 254 */       $$2 = $$4.copy();
/*     */       
/* 256 */       if ($$1 == 0) {
/* 257 */         if (!moveItemStackTo($$4, 2, 38, true)) {
/* 258 */           return ItemStack.EMPTY;
/*     */         }
/* 260 */       } else if ($$1 == 1) {
/* 261 */         if (!moveItemStackTo($$4, 2, 38, true)) {
/* 262 */           return ItemStack.EMPTY;
/*     */         }
/* 264 */       } else if ($$4.is(Items.LAPIS_LAZULI)) {
/* 265 */         if (!moveItemStackTo($$4, 1, 2, true)) {
/* 266 */           return ItemStack.EMPTY;
/*     */         }
/* 268 */       } else if (!((Slot)this.slots.get(0)).hasItem() && ((Slot)this.slots.get(0)).mayPlace($$4)) {
/* 269 */         ItemStack $$5 = $$4.copyWithCount(1);
/* 270 */         $$4.shrink(1);
/* 271 */         ((Slot)this.slots.get(0)).setByPlayer($$5);
/*     */       } else {
/* 273 */         return ItemStack.EMPTY;
/*     */       } 
/* 275 */       if ($$4.isEmpty()) {
/* 276 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 278 */         $$3.setChanged();
/*     */       } 
/* 280 */       if ($$4.getCount() == $$2.getCount()) {
/* 281 */         return ItemStack.EMPTY;
/*     */       }
/* 283 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 286 */     return $$2;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\EnchantmentMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */