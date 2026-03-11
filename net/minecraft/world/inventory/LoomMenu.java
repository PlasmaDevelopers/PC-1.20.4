/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.tags.BannerPatternTags;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.BannerPatternItem;
/*     */ import net.minecraft.world.item.BlockItem;
/*     */ import net.minecraft.world.item.DyeColor;
/*     */ import net.minecraft.world.item.DyeItem;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BannerPattern;
/*     */ import net.minecraft.world.level.block.entity.BlockEntityType;
/*     */ 
/*     */ public class LoomMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   private static final int PATTERN_NOT_SET = -1;
/*     */   private static final int INV_SLOT_START = 4;
/*     */   private static final int INV_SLOT_END = 31;
/*     */   private static final int USE_ROW_SLOT_START = 31;
/*     */   private static final int USE_ROW_SLOT_END = 40;
/*     */   private final ContainerLevelAccess access;
/*  38 */   final DataSlot selectedBannerPatternIndex = DataSlot.standalone();
/*  39 */   private List<Holder<BannerPattern>> selectablePatterns = List.of();
/*     */   Runnable slotUpdateListener = () -> {
/*     */     
/*     */     };
/*     */   final Slot bannerSlot;
/*     */   final Slot dyeSlot;
/*     */   private final Slot patternSlot;
/*     */   private final Slot resultSlot;
/*     */   long lastSoundTime;
/*     */   
/*  49 */   private final Container inputContainer = (Container)new SimpleContainer(3)
/*     */     {
/*     */       public void setChanged() {
/*  52 */         super.setChanged();
/*  53 */         LoomMenu.this.slotsChanged((Container)this);
/*  54 */         LoomMenu.this.slotUpdateListener.run();
/*     */       }
/*     */     };
/*     */   
/*  58 */   private final Container outputContainer = (Container)new SimpleContainer(1)
/*     */     {
/*     */       public void setChanged() {
/*  61 */         super.setChanged();
/*  62 */         LoomMenu.this.slotUpdateListener.run();
/*     */       }
/*     */     };
/*     */   
/*     */   public LoomMenu(int $$0, Inventory $$1) {
/*  67 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public LoomMenu(int $$0, Inventory $$1, final ContainerLevelAccess access) {
/*  71 */     super(MenuType.LOOM, $$0);
/*  72 */     this.access = access;
/*     */     
/*  74 */     this.bannerSlot = addSlot(new Slot(this.inputContainer, 0, 13, 26)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  77 */             return $$0.getItem() instanceof net.minecraft.world.item.BannerItem;
/*     */           }
/*     */         });
/*     */     
/*  81 */     this.dyeSlot = addSlot(new Slot(this.inputContainer, 1, 33, 26)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  84 */             return $$0.getItem() instanceof DyeItem;
/*     */           }
/*     */         });
/*     */     
/*  88 */     this.patternSlot = addSlot(new Slot(this.inputContainer, 2, 23, 45)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  91 */             return $$0.getItem() instanceof BannerPatternItem;
/*     */           }
/*     */         });
/*     */     
/*  95 */     this.resultSlot = addSlot(new Slot(this.outputContainer, 0, 143, 57)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  98 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onTake(Player $$0, ItemStack $$1) {
/* 103 */             LoomMenu.this.bannerSlot.remove(1);
/* 104 */             LoomMenu.this.dyeSlot.remove(1);
/* 105 */             if (!LoomMenu.this.bannerSlot.hasItem() || !LoomMenu.this.dyeSlot.hasItem()) {
/* 106 */               LoomMenu.this.selectedBannerPatternIndex.set(-1);
/*     */             }
/* 108 */             access.execute(($$0, $$1) -> {
/*     */                   long $$2 = $$0.getGameTime();
/*     */                   
/*     */                   if (LoomMenu.this.lastSoundTime != $$2) {
/*     */                     $$0.playSound(null, $$1, SoundEvents.UI_LOOM_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */                     
/*     */                     LoomMenu.this.lastSoundTime = $$2;
/*     */                   } 
/*     */                 });
/*     */             
/* 118 */             super.onTake($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/* 122 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/* 123 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/* 124 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/* 127 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/* 128 */       addSlot(new Slot((Container)$$1, $$5, 8 + $$5 * 18, 142));
/*     */     }
/*     */     
/* 131 */     addDataSlot(this.selectedBannerPatternIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 136 */     return stillValid(this.access, $$0, Blocks.LOOM);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean clickMenuButton(Player $$0, int $$1) {
/* 141 */     if ($$1 >= 0 && $$1 < this.selectablePatterns.size()) {
/* 142 */       this.selectedBannerPatternIndex.set($$1);
/* 143 */       setupResultSlot(this.selectablePatterns.get($$1));
/* 144 */       return true;
/*     */     } 
/* 146 */     return false;
/*     */   }
/*     */   
/*     */   private List<Holder<BannerPattern>> getSelectablePatterns(ItemStack $$0) {
/* 150 */     if ($$0.isEmpty()) {
/* 151 */       return (List<Holder<BannerPattern>>)BuiltInRegistries.BANNER_PATTERN.getTag(BannerPatternTags.NO_ITEM_REQUIRED).map(ImmutableList::copyOf).orElse(ImmutableList.of());
/*     */     }
/* 153 */     Item item = $$0.getItem(); if (item instanceof BannerPatternItem) { BannerPatternItem $$1 = (BannerPatternItem)item;
/* 154 */       return (List<Holder<BannerPattern>>)BuiltInRegistries.BANNER_PATTERN.getTag($$1.getBannerPattern()).map(ImmutableList::copyOf).orElse(ImmutableList.of()); }
/*     */     
/* 156 */     return List.of();
/*     */   }
/*     */   
/*     */   private boolean isValidPatternIndex(int $$0) {
/* 160 */     return ($$0 >= 0 && $$0 < this.selectablePatterns.size());
/*     */   }
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/*     */     Holder<BannerPattern> $$12;
/* 165 */     ItemStack $$1 = this.bannerSlot.getItem();
/* 166 */     ItemStack $$2 = this.dyeSlot.getItem();
/* 167 */     ItemStack $$3 = this.patternSlot.getItem();
/*     */     
/* 169 */     if ($$1.isEmpty() || $$2.isEmpty()) {
/* 170 */       this.resultSlot.set(ItemStack.EMPTY);
/* 171 */       this.selectablePatterns = List.of();
/* 172 */       this.selectedBannerPatternIndex.set(-1);
/*     */       
/*     */       return;
/*     */     } 
/* 176 */     int $$4 = this.selectedBannerPatternIndex.get();
/* 177 */     boolean $$5 = isValidPatternIndex($$4);
/* 178 */     List<Holder<BannerPattern>> $$6 = this.selectablePatterns;
/* 179 */     this.selectablePatterns = getSelectablePatterns($$3);
/*     */     
/* 181 */     if (this.selectablePatterns.size() == 1) {
/*     */       
/* 183 */       this.selectedBannerPatternIndex.set(0);
/* 184 */       Holder<BannerPattern> $$7 = this.selectablePatterns.get(0);
/* 185 */     } else if (!$$5) {
/* 186 */       this.selectedBannerPatternIndex.set(-1);
/* 187 */       Holder<BannerPattern> $$8 = null;
/*     */     } else {
/* 189 */       Holder<BannerPattern> $$9 = $$6.get($$4);
/* 190 */       int $$10 = this.selectablePatterns.indexOf($$9);
/* 191 */       if ($$10 != -1) {
/* 192 */         Holder<BannerPattern> $$11 = $$9;
/* 193 */         this.selectedBannerPatternIndex.set($$10);
/*     */       } else {
/* 195 */         $$12 = null;
/* 196 */         this.selectedBannerPatternIndex.set(-1);
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     if ($$12 != null) {
/* 201 */       CompoundTag $$13 = BlockItem.getBlockEntityData($$1);
/* 202 */       boolean $$14 = ($$13 != null && $$13.contains("Patterns", 9) && !$$1.isEmpty() && $$13.getList("Patterns", 10).size() >= 6);
/* 203 */       if ($$14) {
/* 204 */         this.selectedBannerPatternIndex.set(-1);
/* 205 */         this.resultSlot.set(ItemStack.EMPTY);
/*     */       } else {
/* 207 */         setupResultSlot($$12);
/*     */       } 
/*     */     } else {
/* 210 */       this.resultSlot.set(ItemStack.EMPTY);
/*     */     } 
/* 212 */     broadcastChanges();
/*     */   }
/*     */   
/*     */   public List<Holder<BannerPattern>> getSelectablePatterns() {
/* 216 */     return this.selectablePatterns;
/*     */   }
/*     */   
/*     */   public int getSelectedBannerPatternIndex() {
/* 220 */     return this.selectedBannerPatternIndex.get();
/*     */   }
/*     */   
/*     */   public void registerUpdateListener(Runnable $$0) {
/* 224 */     this.slotUpdateListener = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 229 */     ItemStack $$2 = ItemStack.EMPTY;
/* 230 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 231 */     if ($$3 != null && $$3.hasItem()) {
/* 232 */       ItemStack $$4 = $$3.getItem();
/* 233 */       $$2 = $$4.copy();
/*     */       
/* 235 */       if ($$1 == this.resultSlot.index) {
/* 236 */         if (!moveItemStackTo($$4, 4, 40, true)) {
/* 237 */           return ItemStack.EMPTY;
/*     */         }
/* 239 */         $$3.onQuickCraft($$4, $$2);
/* 240 */       } else if ($$1 == this.dyeSlot.index || $$1 == this.bannerSlot.index || $$1 == this.patternSlot.index) {
/* 241 */         if (!moveItemStackTo($$4, 4, 40, false)) {
/* 242 */           return ItemStack.EMPTY;
/*     */         }
/* 244 */       } else if ($$4.getItem() instanceof net.minecraft.world.item.BannerItem) {
/* 245 */         if (!moveItemStackTo($$4, this.bannerSlot.index, this.bannerSlot.index + 1, false)) {
/* 246 */           return ItemStack.EMPTY;
/*     */         }
/* 248 */       } else if ($$4.getItem() instanceof DyeItem) {
/* 249 */         if (!moveItemStackTo($$4, this.dyeSlot.index, this.dyeSlot.index + 1, false)) {
/* 250 */           return ItemStack.EMPTY;
/*     */         }
/* 252 */       } else if ($$4.getItem() instanceof BannerPatternItem) {
/* 253 */         if (!moveItemStackTo($$4, this.patternSlot.index, this.patternSlot.index + 1, false)) {
/* 254 */           return ItemStack.EMPTY;
/*     */         }
/* 256 */       } else if ($$1 >= 4 && $$1 < 31) {
/* 257 */         if (!moveItemStackTo($$4, 31, 40, false)) {
/* 258 */           return ItemStack.EMPTY;
/*     */         }
/* 260 */       } else if ($$1 >= 31 && $$1 < 40 && 
/* 261 */         !moveItemStackTo($$4, 4, 31, false)) {
/* 262 */         return ItemStack.EMPTY;
/*     */       } 
/*     */ 
/*     */       
/* 266 */       if ($$4.isEmpty()) {
/* 267 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 269 */         $$3.setChanged();
/*     */       } 
/* 271 */       if ($$4.getCount() == $$2.getCount()) {
/* 272 */         return ItemStack.EMPTY;
/*     */       }
/* 274 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 277 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 282 */     super.removed($$0);
/* 283 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.inputContainer));
/*     */   }
/*     */   
/*     */   private void setupResultSlot(Holder<BannerPattern> $$0) {
/* 287 */     ItemStack $$1 = this.bannerSlot.getItem();
/* 288 */     ItemStack $$2 = this.dyeSlot.getItem();
/* 289 */     ItemStack $$3 = ItemStack.EMPTY;
/*     */     
/* 291 */     if (!$$1.isEmpty() && !$$2.isEmpty()) {
/* 292 */       ListTag $$7; $$3 = $$1.copyWithCount(1);
/*     */       
/* 294 */       DyeColor $$4 = ((DyeItem)$$2.getItem()).getDyeColor();
/*     */       
/* 296 */       CompoundTag $$5 = BlockItem.getBlockEntityData($$3);
/*     */       
/* 298 */       if ($$5 != null && $$5.contains("Patterns", 9)) {
/* 299 */         ListTag $$6 = $$5.getList("Patterns", 10);
/*     */       } else {
/* 301 */         $$7 = new ListTag();
/* 302 */         if ($$5 == null) {
/* 303 */           $$5 = new CompoundTag();
/*     */         }
/* 305 */         $$5.put("Patterns", (Tag)$$7);
/*     */       } 
/* 307 */       CompoundTag $$8 = new CompoundTag();
/* 308 */       $$8.putString("Pattern", ((BannerPattern)$$0.value()).getHashname());
/* 309 */       $$8.putInt("Color", $$4.getId());
/* 310 */       $$7.add($$8);
/* 311 */       BlockItem.setBlockEntityData($$3, BlockEntityType.BANNER, $$5);
/*     */     } 
/* 313 */     if (!ItemStack.matches($$3, this.resultSlot.getItem())) {
/* 314 */       this.resultSlot.set($$3);
/*     */     }
/*     */   }
/*     */   
/*     */   public Slot getBannerSlot() {
/* 319 */     return this.bannerSlot;
/*     */   }
/*     */   
/*     */   public Slot getDyeSlot() {
/* 323 */     return this.dyeSlot;
/*     */   }
/*     */   
/*     */   public Slot getPatternSlot() {
/* 327 */     return this.patternSlot;
/*     */   }
/*     */   
/*     */   public Slot getResultSlot() {
/* 331 */     return this.resultSlot;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\LoomMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */