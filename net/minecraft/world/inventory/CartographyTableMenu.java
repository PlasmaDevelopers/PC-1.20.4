/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.entity.player.Inventory;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.MapItem;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CartographyTableMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   public static final int MAP_SLOT = 0;
/*     */   public static final int ADDITIONAL_SLOT = 1;
/*     */   public static final int RESULT_SLOT = 2;
/*     */   private static final int INV_SLOT_START = 3;
/*     */   
/*  27 */   public final Container container = (Container)new SimpleContainer(2)
/*     */     {
/*     */       public void setChanged() {
/*  30 */         CartographyTableMenu.this.slotsChanged((Container)this);
/*  31 */         super.setChanged();
/*     */       }
/*     */     }; private static final int INV_SLOT_END = 30; private static final int USE_ROW_SLOT_START = 30; private static final int USE_ROW_SLOT_END = 39; private final ContainerLevelAccess access; long lastSoundTime;
/*  34 */   private final ResultContainer resultContainer = new ResultContainer()
/*     */     {
/*     */       public void setChanged()
/*     */       {
/*  38 */         CartographyTableMenu.this.slotsChanged(this);
/*  39 */         super.setChanged();
/*     */       }
/*     */     };
/*     */   
/*     */   public CartographyTableMenu(int $$0, Inventory $$1) {
/*  44 */     this($$0, $$1, ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public CartographyTableMenu(int $$0, Inventory $$1, final ContainerLevelAccess access) {
/*  48 */     super(MenuType.CARTOGRAPHY_TABLE, $$0);
/*     */     
/*  50 */     this.access = access;
/*     */     
/*  52 */     addSlot(new Slot(this.container, 0, 15, 15)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  55 */             return $$0.is(Items.FILLED_MAP);
/*     */           }
/*     */         });
/*     */     
/*  59 */     addSlot(new Slot(this.container, 1, 15, 52)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  62 */             return ($$0.is(Items.PAPER) || $$0.is(Items.MAP) || $$0.is(Items.GLASS_PANE));
/*     */           }
/*     */         });
/*     */     
/*  66 */     addSlot(new Slot(this.resultContainer, 2, 145, 39)
/*     */         {
/*     */           public boolean mayPlace(ItemStack $$0) {
/*  69 */             return false;
/*     */           }
/*     */ 
/*     */           
/*     */           public void onTake(Player $$0, ItemStack $$1) {
/*  74 */             ((Slot)CartographyTableMenu.this.slots.get(0)).remove(1);
/*  75 */             ((Slot)CartographyTableMenu.this.slots.get(1)).remove(1);
/*     */             
/*  77 */             $$1.getItem().onCraftedBy($$1, $$0.level(), $$0);
/*     */             
/*  79 */             access.execute(($$0, $$1) -> {
/*     */                   long $$2 = $$0.getGameTime();
/*     */                   
/*     */                   if (CartographyTableMenu.this.lastSoundTime != $$2) {
/*     */                     $$0.playSound(null, $$1, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundSource.BLOCKS, 1.0F, 1.0F);
/*     */                     
/*     */                     CartographyTableMenu.this.lastSoundTime = $$2;
/*     */                   } 
/*     */                 });
/*  88 */             super.onTake($$0, $$1);
/*     */           }
/*     */         });
/*     */     
/*  92 */     for (int $$3 = 0; $$3 < 3; $$3++) {
/*  93 */       for (int $$4 = 0; $$4 < 9; $$4++) {
/*  94 */         addSlot(new Slot((Container)$$1, $$4 + $$3 * 9 + 9, 8 + $$4 * 18, 84 + $$3 * 18));
/*     */       }
/*     */     } 
/*  97 */     for (int $$5 = 0; $$5 < 9; $$5++) {
/*  98 */       addSlot(new Slot((Container)$$1, $$5, 8 + $$5 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/* 104 */     return stillValid(this.access, $$0, Blocks.CARTOGRAPHY_TABLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotsChanged(Container $$0) {
/* 109 */     ItemStack $$1 = this.container.getItem(0);
/* 110 */     ItemStack $$2 = this.container.getItem(1);
/* 111 */     ItemStack $$3 = this.resultContainer.getItem(2);
/*     */     
/* 113 */     if (!$$3.isEmpty() && ($$1.isEmpty() || $$2.isEmpty())) {
/* 114 */       this.resultContainer.removeItemNoUpdate(2);
/* 115 */     } else if (!$$1.isEmpty() && !$$2.isEmpty()) {
/* 116 */       setupResultSlot($$1, $$2, $$3);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setupResultSlot(ItemStack $$0, ItemStack $$1, ItemStack $$2) {
/* 121 */     this.access.execute(($$3, $$4) -> {
/*     */           ItemStack $$8;
/*     */           MapItemSavedData $$5 = MapItem.getSavedData($$0, $$3);
/*     */           if ($$5 == null) {
/*     */             return;
/*     */           }
/*     */           if ($$1.is(Items.PAPER) && !$$5.locked && $$5.scale < 4) {
/*     */             ItemStack $$6 = $$0.copyWithCount(1);
/*     */             $$6.getOrCreateTag().putInt("map_scale_direction", 1);
/*     */             broadcastChanges();
/*     */           } else if ($$1.is(Items.GLASS_PANE) && !$$5.locked) {
/*     */             ItemStack $$7 = $$0.copyWithCount(1);
/*     */             $$7.getOrCreateTag().putBoolean("map_to_lock", true);
/*     */             broadcastChanges();
/*     */           } else if ($$1.is(Items.MAP)) {
/*     */             $$8 = $$0.copyWithCount(2);
/*     */             broadcastChanges();
/*     */           } else {
/*     */             this.resultContainer.removeItemNoUpdate(2);
/*     */             broadcastChanges();
/*     */             return;
/*     */           } 
/*     */           if (!ItemStack.matches($$8, $$2)) {
/*     */             this.resultContainer.setItem(2, $$8);
/*     */             broadcastChanges();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeItemForPickAll(ItemStack $$0, Slot $$1) {
/* 155 */     return ($$1.container != this.resultContainer && super.canTakeItemForPickAll($$0, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/* 160 */     ItemStack $$2 = ItemStack.EMPTY;
/* 161 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 162 */     if ($$3 != null && $$3.hasItem()) {
/* 163 */       ItemStack $$4 = $$3.getItem();
/* 164 */       $$2 = $$4.copy();
/*     */       
/* 166 */       if ($$1 == 2) {
/* 167 */         $$4.getItem().onCraftedBy($$4, $$0.level(), $$0);
/* 168 */         if (!moveItemStackTo($$4, 3, 39, true)) {
/* 169 */           return ItemStack.EMPTY;
/*     */         }
/* 171 */         $$3.onQuickCraft($$4, $$2);
/* 172 */       } else if ($$1 == 1 || $$1 == 0) {
/* 173 */         if (!moveItemStackTo($$4, 3, 39, false)) {
/* 174 */           return ItemStack.EMPTY;
/*     */         }
/* 176 */       } else if ($$4.is(Items.FILLED_MAP)) {
/* 177 */         if (!moveItemStackTo($$4, 0, 1, false)) {
/* 178 */           return ItemStack.EMPTY;
/*     */         }
/* 180 */       } else if ($$4.is(Items.PAPER) || $$4.is(Items.MAP) || $$4.is(Items.GLASS_PANE)) {
/* 181 */         if (!moveItemStackTo($$4, 1, 2, false)) {
/* 182 */           return ItemStack.EMPTY;
/*     */         }
/* 184 */       } else if ($$1 >= 3 && $$1 < 30) {
/* 185 */         if (!moveItemStackTo($$4, 30, 39, false)) {
/* 186 */           return ItemStack.EMPTY;
/*     */         }
/* 188 */       } else if ($$1 >= 30 && $$1 < 39 && 
/* 189 */         !moveItemStackTo($$4, 3, 30, false)) {
/* 190 */         return ItemStack.EMPTY;
/*     */       } 
/*     */ 
/*     */       
/* 194 */       if ($$4.isEmpty()) {
/* 195 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       }
/*     */       
/* 198 */       $$3.setChanged();
/*     */       
/* 200 */       if ($$4.getCount() == $$2.getCount()) {
/* 201 */         return ItemStack.EMPTY;
/*     */       }
/* 203 */       $$3.onTake($$0, $$4);
/* 204 */       broadcastChanges();
/*     */     } 
/*     */     
/* 207 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/* 212 */     super.removed($$0);
/*     */     
/* 214 */     this.resultContainer.removeItemNoUpdate(2);
/* 215 */     this.access.execute(($$1, $$2) -> clearContainer($$0, this.container));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\CartographyTableMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */