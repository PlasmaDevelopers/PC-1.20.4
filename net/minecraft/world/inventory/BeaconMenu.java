/*     */ package net.minecraft.world.inventory;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.ItemTags;
/*     */ import net.minecraft.world.Container;
/*     */ import net.minecraft.world.SimpleContainer;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BeaconMenu
/*     */   extends AbstractContainerMenu
/*     */ {
/*     */   private static final int PAYMENT_SLOT = 0;
/*     */   private static final int SLOT_COUNT = 1;
/*     */   private static final int DATA_COUNT = 3;
/*     */   private static final int INV_SLOT_START = 1;
/*     */   private static final int INV_SLOT_END = 28;
/*     */   private static final int USE_ROW_SLOT_START = 28;
/*     */   private static final int USE_ROW_SLOT_END = 37;
/*     */   private static final int NO_EFFECT = 0;
/*     */   
/*  29 */   private final Container beacon = (Container)new SimpleContainer(1)
/*     */     {
/*     */       public boolean canPlaceItem(int $$0, ItemStack $$1) {
/*  32 */         return $$1.is(ItemTags.BEACON_PAYMENT_ITEMS);
/*     */       }
/*     */ 
/*     */       
/*     */       public int getMaxStackSize() {
/*  37 */         return 1;
/*     */       }
/*     */     };
/*     */   
/*     */   private final PaymentSlot paymentSlot;
/*     */   private final ContainerLevelAccess access;
/*     */   private final ContainerData beaconData;
/*     */   
/*     */   public BeaconMenu(int $$0, Container $$1) {
/*  46 */     this($$0, $$1, new SimpleContainerData(3), ContainerLevelAccess.NULL);
/*     */   }
/*     */   
/*     */   public BeaconMenu(int $$0, Container $$1, ContainerData $$2, ContainerLevelAccess $$3) {
/*  50 */     super(MenuType.BEACON, $$0);
/*  51 */     checkContainerDataCount($$2, 3);
/*  52 */     this.beaconData = $$2;
/*  53 */     this.access = $$3;
/*     */     
/*  55 */     this.paymentSlot = new PaymentSlot(this.beacon, 0, 136, 110);
/*  56 */     addSlot(this.paymentSlot);
/*     */     
/*  58 */     addDataSlots($$2);
/*     */     
/*  60 */     int $$4 = 36;
/*  61 */     int $$5 = 137;
/*     */     
/*  63 */     for (int $$6 = 0; $$6 < 3; $$6++) {
/*  64 */       for (int $$7 = 0; $$7 < 9; $$7++) {
/*  65 */         addSlot(new Slot($$1, $$7 + $$6 * 9 + 9, 36 + $$7 * 18, 137 + $$6 * 18));
/*     */       }
/*     */     } 
/*  68 */     for (int $$8 = 0; $$8 < 9; $$8++) {
/*  69 */       addSlot(new Slot($$1, $$8, 36 + $$8 * 18, 195));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removed(Player $$0) {
/*  75 */     super.removed($$0);
/*  76 */     if (($$0.level()).isClientSide) {
/*     */       return;
/*     */     }
/*     */     
/*  80 */     ItemStack $$1 = this.paymentSlot.remove(this.paymentSlot.getMaxStackSize());
/*  81 */     if (!$$1.isEmpty()) {
/*  82 */       $$0.drop($$1, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean stillValid(Player $$0) {
/*  88 */     return stillValid(this.access, $$0, Blocks.BEACON);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(int $$0, int $$1) {
/*  93 */     super.setData($$0, $$1);
/*  94 */     broadcastChanges();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack quickMoveStack(Player $$0, int $$1) {
/*  99 */     ItemStack $$2 = ItemStack.EMPTY;
/* 100 */     Slot $$3 = (Slot)this.slots.get($$1);
/* 101 */     if ($$3 != null && $$3.hasItem()) {
/* 102 */       ItemStack $$4 = $$3.getItem();
/* 103 */       $$2 = $$4.copy();
/*     */       
/* 105 */       if ($$1 == 0) {
/* 106 */         if (!moveItemStackTo($$4, 1, 37, true)) {
/* 107 */           return ItemStack.EMPTY;
/*     */         }
/* 109 */         $$3.onQuickCraft($$4, $$2);
/* 110 */       } else if (!this.paymentSlot.hasItem() && this.paymentSlot.mayPlace($$4) && $$4.getCount() == 1) {
/* 111 */         if (!moveItemStackTo($$4, 0, 1, false)) {
/* 112 */           return ItemStack.EMPTY;
/*     */         }
/* 114 */       } else if ($$1 >= 1 && $$1 < 28) {
/* 115 */         if (!moveItemStackTo($$4, 28, 37, false)) {
/* 116 */           return ItemStack.EMPTY;
/*     */         }
/* 118 */       } else if ($$1 >= 28 && $$1 < 37) {
/* 119 */         if (!moveItemStackTo($$4, 1, 28, false)) {
/* 120 */           return ItemStack.EMPTY;
/*     */         }
/*     */       }
/* 123 */       else if (!moveItemStackTo($$4, 1, 37, false)) {
/* 124 */         return ItemStack.EMPTY;
/*     */       } 
/*     */       
/* 127 */       if ($$4.isEmpty()) {
/* 128 */         $$3.setByPlayer(ItemStack.EMPTY);
/*     */       } else {
/* 130 */         $$3.setChanged();
/*     */       } 
/* 132 */       if ($$4.getCount() == $$2.getCount()) {
/* 133 */         return ItemStack.EMPTY;
/*     */       }
/* 135 */       $$3.onTake($$0, $$4);
/*     */     } 
/*     */     
/* 138 */     return $$2;
/*     */   }
/*     */   
/*     */   public int getLevels() {
/* 142 */     return this.beaconData.get(0);
/*     */   }
/*     */   
/*     */   public static int encodeEffect(@Nullable MobEffect $$0) {
/* 146 */     return ($$0 == null) ? 0 : (BuiltInRegistries.MOB_EFFECT.getId($$0) + 1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static MobEffect decodeEffect(int $$0) {
/* 151 */     return ($$0 == 0) ? null : (MobEffect)BuiltInRegistries.MOB_EFFECT.byId($$0 - 1);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MobEffect getPrimaryEffect() {
/* 156 */     return decodeEffect(this.beaconData.get(1));
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MobEffect getSecondaryEffect() {
/* 161 */     return decodeEffect(this.beaconData.get(2));
/*     */   }
/*     */   
/*     */   public void updateEffects(Optional<MobEffect> $$0, Optional<MobEffect> $$1) {
/* 165 */     if (this.paymentSlot.hasItem()) {
/* 166 */       this.beaconData.set(1, encodeEffect($$0.orElse(null)));
/* 167 */       this.beaconData.set(2, encodeEffect($$1.orElse(null)));
/* 168 */       this.paymentSlot.remove(1);
/* 169 */       this.access.execute(Level::blockEntityChanged);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasPayment() {
/* 174 */     return !this.beacon.getItem(0).isEmpty();
/*     */   }
/*     */   
/*     */   private class PaymentSlot extends Slot {
/*     */     public PaymentSlot(Container $$0, int $$1, int $$2, int $$3) {
/* 179 */       super($$0, $$1, $$2, $$3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mayPlace(ItemStack $$0) {
/* 184 */       return $$0.is(ItemTags.BEACON_PAYMENT_ITEMS);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMaxStackSize() {
/* 189 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\inventory\BeaconMenu.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */