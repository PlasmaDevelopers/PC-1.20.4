/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMultimap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.tags.DamageTypeTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.SlotAccess;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.entity.item.ItemEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.flag.FeatureElement;
/*     */ import net.minecraft.world.flag.FeatureFlag;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.food.FoodProperties;
/*     */ import net.minecraft.world.inventory.ClickAction;
/*     */ import net.minecraft.world.inventory.Slot;
/*     */ import net.minecraft.world.inventory.tooltip.TooltipComponent;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class Item
/*     */   implements FeatureElement, ItemLike
/*     */ {
/*  56 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  57 */   public static final Map<Block, Item> BY_BLOCK = Maps.newHashMap();
/*     */   
/*  59 */   protected static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*  60 */   protected static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
/*     */   
/*     */   public static final int MAX_STACK_SIZE = 64;
/*     */   public static final int EAT_DURATION = 32;
/*     */   public static final int MAX_BAR_WIDTH = 13;
/*  65 */   private final Holder.Reference<Item> builtInRegistryHolder = BuiltInRegistries.ITEM.createIntrusiveHolder(this);
/*     */   private final Rarity rarity; private final int maxStackSize; private final int maxDamage; private final boolean isFireResistant;
/*     */   
/*  68 */   public static int getId(Item $$0) { return ($$0 == null) ? 0 : BuiltInRegistries.ITEM.getId($$0); } @Nullable
/*     */   private final Item craftingRemainingItem; @Nullable
/*     */   private String descriptionId; @Nullable
/*     */   private final FoodProperties foodProperties; private final FeatureFlagSet requiredFeatures; public static Item byId(int $$0) {
/*  72 */     return (Item)BuiltInRegistries.ITEM.byId($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static Item byBlock(Block $$0) {
/*  78 */     return BY_BLOCK.getOrDefault($$0, Items.AIR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item(Properties $$0) {
/*  99 */     this.rarity = $$0.rarity;
/* 100 */     this.craftingRemainingItem = $$0.craftingRemainingItem;
/* 101 */     this.maxDamage = $$0.maxDamage;
/* 102 */     this.maxStackSize = $$0.maxStackSize;
/* 103 */     this.foodProperties = $$0.foodProperties;
/* 104 */     this.isFireResistant = $$0.isFireResistant;
/* 105 */     this.requiredFeatures = $$0.requiredFeatures;
/*     */     
/* 107 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 108 */       String $$1 = getClass().getSimpleName();
/* 109 */       if (!$$1.endsWith("Item")) {
/* 110 */         LOGGER.error("Item classes should end with Item and {} doesn't.", $$1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<Item> builtInRegistryHolder() {
/* 120 */     return this.builtInRegistryHolder;
/*     */   }
/*     */   
/*     */   public static class Properties {
/* 124 */     int maxStackSize = 64;
/*     */     int maxDamage;
/*     */     @Nullable
/*     */     Item craftingRemainingItem;
/* 128 */     Rarity rarity = Rarity.COMMON;
/*     */     @Nullable
/*     */     FoodProperties foodProperties;
/*     */     boolean isFireResistant;
/* 132 */     FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
/*     */     
/*     */     public Properties food(FoodProperties $$0) {
/* 135 */       this.foodProperties = $$0;
/* 136 */       return this;
/*     */     }
/*     */     
/*     */     public Properties stacksTo(int $$0) {
/* 140 */       if (this.maxDamage > 0) {
/* 141 */         throw new RuntimeException("Unable to have damage AND stack.");
/*     */       }
/* 143 */       this.maxStackSize = $$0;
/* 144 */       return this;
/*     */     }
/*     */     
/*     */     public Properties defaultDurability(int $$0) {
/* 148 */       return (this.maxDamage == 0) ? durability($$0) : this;
/*     */     }
/*     */     
/*     */     public Properties durability(int $$0) {
/* 152 */       this.maxDamage = $$0;
/* 153 */       this.maxStackSize = 1;
/* 154 */       return this;
/*     */     }
/*     */     
/*     */     public Properties craftRemainder(Item $$0) {
/* 158 */       this.craftingRemainingItem = $$0;
/* 159 */       return this;
/*     */     }
/*     */     
/*     */     public Properties rarity(Rarity $$0) {
/* 163 */       this.rarity = $$0;
/* 164 */       return this;
/*     */     }
/*     */     
/*     */     public Properties fireResistant() {
/* 168 */       this.isFireResistant = true;
/* 169 */       return this;
/*     */     }
/*     */     
/*     */     public Properties requiredFeatures(FeatureFlag... $$0) {
/* 173 */       this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
/* 174 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUseTick(Level $$0, LivingEntity $$1, ItemStack $$2, int $$3) {}
/*     */ 
/*     */   
/*     */   public void onDestroyed(ItemEntity $$0) {}
/*     */ 
/*     */   
/*     */   public void verifyTagAfterLoad(CompoundTag $$0) {}
/*     */   
/*     */   public boolean canAttackBlock(BlockState $$0, Level $$1, BlockPos $$2, Player $$3) {
/* 188 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item asItem() {
/* 193 */     return this;
/*     */   }
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/* 197 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public float getDestroySpeed(ItemStack $$0, BlockState $$1) {
/* 201 */     return 1.0F;
/*     */   }
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 205 */     if (isEdible()) {
/* 206 */       ItemStack $$3 = $$1.getItemInHand($$2);
/* 207 */       if ($$1.canEat(getFoodProperties().canAlwaysEat())) {
/* 208 */         $$1.startUsingItem($$2);
/* 209 */         return InteractionResultHolder.consume($$3);
/*     */       } 
/* 211 */       return InteractionResultHolder.fail($$3);
/*     */     } 
/* 213 */     return InteractionResultHolder.pass($$1.getItemInHand($$2));
/*     */   }
/*     */   
/*     */   public ItemStack finishUsingItem(ItemStack $$0, Level $$1, LivingEntity $$2) {
/* 217 */     if (isEdible()) {
/* 218 */       return $$2.eat($$1, $$0);
/*     */     }
/* 220 */     return $$0;
/*     */   }
/*     */   
/*     */   public final int getMaxStackSize() {
/* 224 */     return this.maxStackSize;
/*     */   }
/*     */   
/*     */   public final int getMaxDamage() {
/* 228 */     return this.maxDamage;
/*     */   }
/*     */   
/*     */   public boolean canBeDepleted() {
/* 232 */     return (this.maxDamage > 0);
/*     */   }
/*     */   
/*     */   public boolean isBarVisible(ItemStack $$0) {
/* 236 */     return $$0.isDamaged();
/*     */   }
/*     */   
/*     */   public int getBarWidth(ItemStack $$0) {
/* 240 */     return Math.round(13.0F - $$0.getDamageValue() * 13.0F / this.maxDamage);
/*     */   }
/*     */   
/*     */   public int getBarColor(ItemStack $$0) {
/* 244 */     float $$1 = Math.max(0.0F, (this.maxDamage - $$0.getDamageValue()) / this.maxDamage);
/*     */ 
/*     */     
/* 247 */     return Mth.hsvToRgb($$1 / 3.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideStackedOnOther(ItemStack $$0, Slot $$1, ClickAction $$2, Player $$3) {
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean overrideOtherStackedOnMe(ItemStack $$0, ItemStack $$1, Slot $$2, ClickAction $$3, Player $$4, SlotAccess $$5) {
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hurtEnemy(ItemStack $$0, LivingEntity $$1, LivingEntity $$2) {
/* 268 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mineBlock(ItemStack $$0, Level $$1, BlockState $$2, BlockPos $$3, LivingEntity $$4) {
/* 275 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isCorrectToolForDrops(BlockState $$0) {
/* 279 */     return false;
/*     */   }
/*     */   
/*     */   public InteractionResult interactLivingEntity(ItemStack $$0, Player $$1, LivingEntity $$2, InteractionHand $$3) {
/* 283 */     return InteractionResult.PASS;
/*     */   }
/*     */   
/*     */   public Component getDescription() {
/* 287 */     return (Component)Component.translatable(getDescriptionId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 293 */     return BuiltInRegistries.ITEM.getKey(this).getPath();
/*     */   }
/*     */   
/*     */   protected String getOrCreateDescriptionId() {
/* 297 */     if (this.descriptionId == null) {
/* 298 */       this.descriptionId = Util.makeDescriptionId("item", BuiltInRegistries.ITEM.getKey(this));
/*     */     }
/* 300 */     return this.descriptionId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescriptionId() {
/* 307 */     return getOrCreateDescriptionId();
/*     */   }
/*     */   
/*     */   public String getDescriptionId(ItemStack $$0) {
/* 311 */     return getDescriptionId();
/*     */   }
/*     */   
/*     */   public boolean shouldOverrideMultiplayerNbt() {
/* 315 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public final Item getCraftingRemainingItem() {
/* 321 */     return this.craftingRemainingItem;
/*     */   }
/*     */   
/*     */   public boolean hasCraftingRemainingItem() {
/* 325 */     return (this.craftingRemainingItem != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void inventoryTick(ItemStack $$0, Level $$1, Entity $$2, int $$3, boolean $$4) {}
/*     */   
/*     */   public void onCraftedBy(ItemStack $$0, Level $$1, Player $$2) {
/* 332 */     onCraftedPostProcess($$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCraftedPostProcess(ItemStack $$0, Level $$1) {}
/*     */   
/*     */   public boolean isComplex() {
/* 339 */     return false;
/*     */   }
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 343 */     return $$0.getItem().isEdible() ? UseAnim.EAT : UseAnim.NONE;
/*     */   }
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/* 347 */     if ($$0.getItem().isEdible()) {
/* 348 */       return getFoodProperties().isFastFood() ? 16 : 32;
/*     */     }
/* 350 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void releaseUsing(ItemStack $$0, Level $$1, LivingEntity $$2, int $$3) {}
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {}
/*     */   
/*     */   public Optional<TooltipComponent> getTooltipImage(ItemStack $$0) {
/* 360 */     return Optional.empty();
/*     */   }
/*     */   
/*     */   public Component getName(ItemStack $$0) {
/* 364 */     return (Component)Component.translatable(getDescriptionId($$0));
/*     */   }
/*     */   
/*     */   public boolean isFoil(ItemStack $$0) {
/* 368 */     return $$0.isEnchanted();
/*     */   }
/*     */   
/*     */   public Rarity getRarity(ItemStack $$0) {
/* 372 */     if (!$$0.isEnchanted()) {
/* 373 */       return this.rarity;
/*     */     }
/*     */     
/* 376 */     switch (this.rarity) {
/*     */       case COMMON:
/*     */       case UNCOMMON:
/* 379 */         return Rarity.RARE;
/*     */       
/*     */       case RARE:
/* 382 */         return Rarity.EPIC;
/*     */     } 
/*     */ 
/*     */     
/* 386 */     return this.rarity;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnchantable(ItemStack $$0) {
/* 391 */     return (getMaxStackSize() == 1 && canBeDepleted());
/*     */   }
/*     */   
/*     */   protected static BlockHitResult getPlayerPOVHitResult(Level $$0, Player $$1, ClipContext.Fluid $$2) {
/* 395 */     float $$3 = $$1.getXRot();
/* 396 */     float $$4 = $$1.getYRot();
/* 397 */     Vec3 $$5 = $$1.getEyePosition();
/*     */ 
/*     */     
/* 400 */     float $$6 = Mth.cos(-$$4 * 0.017453292F - 3.1415927F);
/* 401 */     float $$7 = Mth.sin(-$$4 * 0.017453292F - 3.1415927F);
/* 402 */     float $$8 = -Mth.cos(-$$3 * 0.017453292F);
/* 403 */     float $$9 = Mth.sin(-$$3 * 0.017453292F);
/*     */     
/* 405 */     float $$10 = $$7 * $$8;
/* 406 */     float $$11 = $$9;
/* 407 */     float $$12 = $$6 * $$8;
/*     */     
/* 409 */     double $$13 = 5.0D;
/* 410 */     Vec3 $$14 = $$5.add($$10 * 5.0D, $$11 * 5.0D, $$12 * 5.0D);
/*     */     
/* 412 */     return $$0.clip(new ClipContext($$5, $$14, ClipContext.Block.OUTLINE, $$2, (Entity)$$1));
/*     */   }
/*     */   
/*     */   public int getEnchantmentValue() {
/* 416 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isValidRepairItem(ItemStack $$0, ItemStack $$1) {
/* 420 */     return false;
/*     */   }
/*     */   
/*     */   public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot $$0) {
/* 424 */     return (Multimap<Attribute, AttributeModifier>)ImmutableMultimap.of();
/*     */   }
/*     */   
/*     */   public boolean useOnRelease(ItemStack $$0) {
/* 428 */     return false;
/*     */   }
/*     */   
/*     */   public ItemStack getDefaultInstance() {
/* 432 */     return new ItemStack(this);
/*     */   }
/*     */   
/*     */   public boolean isEdible() {
/* 436 */     return (this.foodProperties != null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public FoodProperties getFoodProperties() {
/* 441 */     return this.foodProperties;
/*     */   }
/*     */   
/*     */   public SoundEvent getDrinkingSound() {
/* 445 */     return SoundEvents.GENERIC_DRINK;
/*     */   }
/*     */   
/*     */   public SoundEvent getEatingSound() {
/* 449 */     return SoundEvents.GENERIC_EAT;
/*     */   }
/*     */   
/*     */   public boolean isFireResistant() {
/* 453 */     return this.isFireResistant;
/*     */   }
/*     */   
/*     */   public boolean canBeHurtBy(DamageSource $$0) {
/* 457 */     return (!this.isFireResistant || !$$0.is(DamageTypeTags.IS_FIRE));
/*     */   }
/*     */   
/*     */   public boolean canFitInsideContainerItems() {
/* 461 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public FeatureFlagSet requiredFeatures() {
/* 466 */     return this.requiredFeatures;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\Item.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */