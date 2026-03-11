/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.NonNullList;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.inventory.ClickAction;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.inventory.tooltip.BundleTooltip;
/*     */ import net.minecraft.world.inventory.tooltip.TooltipComponent;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class BundleItem
/*     */   extends Item
/*     */ {
/*     */   private static final String TAG_ITEMS = "Items";
/*     */   public static final int MAX_WEIGHT = 64;
/*     */   private static final int BUNDLE_IN_BUNDLE_WEIGHT = 4;
/*  34 */   private static final int BAR_COLOR = Mth.color(0.4F, 0.4F, 1.0F);
/*     */   
/*     */   public BundleItem(Item.Properties $$0) {
/*  37 */     super($$0);
/*     */   }
/*     */   
/*     */   public static float getFullnessDisplay(ItemStack $$0) {
/*  41 */     return getContentWeight($$0) / 64.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean overrideStackedOnOther(ItemStack $$0, Slot $$1, ClickAction $$2, Player $$3) {
/*  46 */     if ($$2 != ClickAction.SECONDARY) {
/*  47 */       return false;
/*     */     }
/*  49 */     ItemStack $$4 = $$1.getItem();
/*  50 */     if ($$4.isEmpty()) {
/*  51 */       playRemoveOneSound((Entity)$$3);
/*  52 */       removeOne($$0).ifPresent($$2 -> add($$0, $$1.safeInsert($$2)));
/*  53 */     } else if ($$4.getItem().canFitInsideContainerItems()) {
/*  54 */       int $$5 = (64 - getContentWeight($$0)) / getWeight($$4);
/*  55 */       int $$6 = add($$0, $$1.safeTake($$4.getCount(), $$5, $$3));
/*  56 */       if ($$6 > 0) {
/*  57 */         playInsertSound((Entity)$$3);
/*     */       }
/*     */     } 
/*  60 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean overrideOtherStackedOnMe(ItemStack $$0, ItemStack $$1, Slot $$2, ClickAction $$3, Player $$4, SlotAccess $$5) {
/*  65 */     if ($$3 != ClickAction.SECONDARY || !$$2.allowModification($$4)) {
/*  66 */       return false;
/*     */     }
/*  68 */     if ($$1.isEmpty()) {
/*  69 */       removeOne($$0).ifPresent($$2 -> {
/*     */             playRemoveOneSound((Entity)$$0);
/*     */             $$1.set($$2);
/*     */           });
/*     */     } else {
/*  74 */       int $$6 = add($$0, $$1);
/*  75 */       if ($$6 > 0) {
/*  76 */         playInsertSound((Entity)$$4);
/*  77 */         $$1.shrink($$6);
/*     */       } 
/*     */     } 
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  85 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*  86 */     if (dropContents($$3, $$1)) {
/*  87 */       playDropContentsSound((Entity)$$1);
/*  88 */       $$1.awardStat(Stats.ITEM_USED.get(this));
/*  89 */       return InteractionResultHolder.sidedSuccess($$3, $$0.isClientSide());
/*     */     } 
/*  91 */     return InteractionResultHolder.fail($$3);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBarVisible(ItemStack $$0) {
/*  97 */     return (getContentWeight($$0) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBarWidth(ItemStack $$0) {
/* 103 */     return Math.min(1 + 12 * getContentWeight($$0) / 64, 13);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBarColor(ItemStack $$0) {
/* 108 */     return BAR_COLOR;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int add(ItemStack $$0, ItemStack $$1) {
/* 113 */     if ($$1.isEmpty() || !$$1.getItem().canFitInsideContainerItems()) {
/* 114 */       return 0;
/*     */     }
/*     */     
/* 117 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 118 */     if (!$$2.contains("Items")) {
/* 119 */       $$2.put("Items", (Tag)new ListTag());
/*     */     }
/*     */     
/* 122 */     int $$3 = getContentWeight($$0);
/* 123 */     int $$4 = getWeight($$1);
/* 124 */     int $$5 = Math.min($$1.getCount(), (64 - $$3) / $$4);
/*     */     
/* 126 */     if ($$5 == 0) {
/* 127 */       return 0;
/*     */     }
/*     */     
/* 130 */     ListTag $$6 = $$2.getList("Items", 10);
/* 131 */     Optional<CompoundTag> $$7 = getMatchingItem($$1, $$6);
/*     */     
/* 133 */     if ($$7.isPresent()) {
/* 134 */       CompoundTag $$8 = $$7.get();
/* 135 */       ItemStack $$9 = ItemStack.of($$8);
/* 136 */       $$9.grow($$5);
/* 137 */       $$9.save($$8);
/*     */       
/* 139 */       $$6.remove($$8);
/* 140 */       $$6.add(0, (Tag)$$8);
/*     */     } else {
/* 142 */       ItemStack $$10 = $$1.copyWithCount($$5);
/* 143 */       CompoundTag $$11 = new CompoundTag();
/* 144 */       $$10.save($$11);
/* 145 */       $$6.add(0, (Tag)$$11);
/*     */     } 
/* 147 */     return $$5;
/*     */   }
/*     */   
/*     */   private static Optional<CompoundTag> getMatchingItem(ItemStack $$0, ListTag $$1) {
/* 151 */     if ($$0.is(Items.BUNDLE)) {
/* 152 */       return Optional.empty();
/*     */     }
/*     */     
/* 155 */     Objects.requireNonNull(CompoundTag.class);
/* 156 */     Objects.requireNonNull(CompoundTag.class); return $$1.stream().filter(CompoundTag.class::isInstance).map(CompoundTag.class::cast)
/* 157 */       .filter($$1 -> ItemStack.isSameItemSameTags(ItemStack.of($$1), $$0))
/* 158 */       .findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getWeight(ItemStack $$0) {
/* 163 */     if ($$0.is(Items.BUNDLE))
/* 164 */       return 4 + getContentWeight($$0); 
/* 165 */     if (($$0.is(Items.BEEHIVE) || $$0.is(Items.BEE_NEST)) && $$0.hasTag()) {
/* 166 */       CompoundTag $$1 = BlockItem.getBlockEntityData($$0);
/* 167 */       if ($$1 != null && !$$1.getList("Bees", 10).isEmpty()) {
/* 168 */         return 64;
/*     */       }
/*     */     } 
/*     */     
/* 172 */     return 64 / $$0.getMaxStackSize();
/*     */   }
/*     */   
/*     */   private static int getContentWeight(ItemStack $$0) {
/* 176 */     return getContents($$0).mapToInt($$0 -> getWeight($$0) * $$0.getCount()).sum();
/*     */   }
/*     */   
/*     */   private static Optional<ItemStack> removeOne(ItemStack $$0) {
/* 180 */     CompoundTag $$1 = $$0.getOrCreateTag();
/* 181 */     if (!$$1.contains("Items")) {
/* 182 */       return Optional.empty();
/*     */     }
/* 184 */     ListTag $$2 = $$1.getList("Items", 10);
/* 185 */     if ($$2.isEmpty()) {
/* 186 */       return Optional.empty();
/*     */     }
/* 188 */     int $$3 = 0;
/* 189 */     CompoundTag $$4 = $$2.getCompound(0);
/* 190 */     ItemStack $$5 = ItemStack.of($$4);
/* 191 */     $$2.remove(0);
/* 192 */     if ($$2.isEmpty()) {
/* 193 */       $$0.removeTagKey("Items");
/*     */     }
/* 195 */     return Optional.of($$5);
/*     */   }
/*     */   
/*     */   private static boolean dropContents(ItemStack $$0, Player $$1) {
/* 199 */     CompoundTag $$2 = $$0.getOrCreateTag();
/* 200 */     if (!$$2.contains("Items")) {
/* 201 */       return false;
/*     */     }
/* 203 */     if ($$1 instanceof net.minecraft.server.level.ServerPlayer) {
/* 204 */       ListTag $$3 = $$2.getList("Items", 10);
/* 205 */       for (int $$4 = 0; $$4 < $$3.size(); $$4++) {
/* 206 */         CompoundTag $$5 = $$3.getCompound($$4);
/* 207 */         ItemStack $$6 = ItemStack.of($$5);
/* 208 */         $$1.drop($$6, true);
/*     */       } 
/*     */     } 
/* 211 */     $$0.removeTagKey("Items");
/* 212 */     return true;
/*     */   }
/*     */   
/*     */   private static Stream<ItemStack> getContents(ItemStack $$0) {
/* 216 */     CompoundTag $$1 = $$0.getTag();
/* 217 */     if ($$1 == null) {
/* 218 */       return Stream.empty();
/*     */     }
/* 220 */     ListTag $$2 = $$1.getList("Items", 10);
/* 221 */     Objects.requireNonNull(CompoundTag.class); return $$2.stream().map(CompoundTag.class::cast).map(ItemStack::of);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<TooltipComponent> getTooltipImage(ItemStack $$0) {
/* 226 */     NonNullList<ItemStack> $$1 = NonNullList.create();
/* 227 */     Objects.requireNonNull($$1); getContents($$0).forEach($$1::add);
/* 228 */     return (Optional)Optional.of(new BundleTooltip($$1, getContentWeight($$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 233 */     $$2.add(Component.translatable("item.minecraft.bundle.fullness", new Object[] { Integer.valueOf(getContentWeight($$0)), Integer.valueOf(64) }).withStyle(ChatFormatting.GRAY));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDestroyed(ItemEntity $$0) {
/* 238 */     ItemUtils.onContainerDestroyed($$0, getContents($$0.getItem()));
/*     */   }
/*     */   
/*     */   private void playRemoveOneSound(Entity $$0) {
/* 242 */     $$0.playSound(SoundEvents.BUNDLE_REMOVE_ONE, 0.8F, 0.8F + $$0.level().getRandom().nextFloat() * 0.4F);
/*     */   }
/*     */   
/*     */   private void playInsertSound(Entity $$0) {
/* 246 */     $$0.playSound(SoundEvents.BUNDLE_INSERT, 0.8F, 0.8F + $$0.level().getRandom().nextFloat() * 0.4F);
/*     */   }
/*     */   
/*     */   private void playDropContentsSound(Entity $$0) {
/* 250 */     $$0.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + $$0.level().getRandom().nextFloat() * 0.4F);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BundleItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */