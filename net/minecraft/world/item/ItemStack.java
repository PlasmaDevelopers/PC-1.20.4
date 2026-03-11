/*      */ package net.minecraft.world.item;
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*      */ import com.mojang.datafixers.kinds.App;
/*      */ import com.mojang.datafixers.kinds.Applicative;
/*      */ import com.mojang.datafixers.util.Function3;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.DataResult;
/*      */ import com.mojang.serialization.MapCodec;
/*      */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.DecimalFormatSymbols;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import java.util.function.BiFunction;
/*      */ import java.util.function.Consumer;
/*      */ import java.util.function.Predicate;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.ChatFormatting;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.commands.arguments.blocks.BlockStateParser;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Holder;
/*      */ import net.minecraft.core.HolderSet;
/*      */ import net.minecraft.core.Registry;
/*      */ import net.minecraft.core.registries.BuiltInRegistries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.ListTag;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.nbt.TagParser;
/*      */ import net.minecraft.network.chat.CommonComponents;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.ComponentUtils;
/*      */ import net.minecraft.network.chat.HoverEvent;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.chat.Style;
/*      */ import net.minecraft.resources.ResourceLocation;
/*      */ import net.minecraft.server.level.ServerPlayer;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.stats.Stats;
/*      */ import net.minecraft.tags.TagKey;
/*      */ import net.minecraft.util.ExtraCodecs;
/*      */ import net.minecraft.util.RandomSource;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.InteractionResult;
/*      */ import net.minecraft.world.InteractionResultHolder;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.MobType;
/*      */ import net.minecraft.world.entity.SlotAccess;
/*      */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*      */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*      */ import net.minecraft.world.entity.decoration.ItemFrame;
/*      */ import net.minecraft.world.entity.item.ItemEntity;
/*      */ import net.minecraft.world.entity.player.Player;
/*      */ import net.minecraft.world.flag.FeatureFlagSet;
/*      */ import net.minecraft.world.inventory.ClickAction;
/*      */ import net.minecraft.world.inventory.Slot;
/*      */ import net.minecraft.world.inventory.tooltip.TooltipComponent;
/*      */ import net.minecraft.world.item.context.UseOnContext;
/*      */ import net.minecraft.world.item.enchantment.DigDurabilityEnchantment;
/*      */ import net.minecraft.world.item.enchantment.Enchantment;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.item.enchantment.Enchantments;
/*      */ import net.minecraft.world.level.ItemLike;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.LevelReader;
/*      */ import net.minecraft.world.level.block.Block;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*      */ 
/*      */ public final class ItemStack {
/*      */   public static final Codec<ItemStack> CODEC;
/*      */   
/*      */   static {
/*   85 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("id").forGetter(ItemStack::getItemHolder), (App)Codec.INT.fieldOf("Count").forGetter(ItemStack::getCount), (App)CompoundTag.CODEC.optionalFieldOf("tag").forGetter(())).apply((Applicative)$$0, ItemStack::new));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   91 */     ITEM_NON_AIR_CODEC = ExtraCodecs.validate(BuiltInRegistries.ITEM.byNameCodec(), $$0 -> ($$0 == Items.AIR) ? DataResult.error(()) : DataResult.success($$0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   96 */     ADVANCEMENT_ICON_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ITEM.holderByNameCodec().fieldOf("item").forGetter(ItemStack::getItemHolder), (App)ExtraCodecs.strictOptionalField(TagParser.AS_CODEC, "nbt").forGetter(())).apply((Applicative)$$0, ()));
/*      */ 
/*      */ 
/*      */     
/*  100 */     ITEM_WITH_COUNT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ITEM_NON_AIR_CODEC.fieldOf("item").forGetter(ItemStack::getItem), (App)ExtraCodecs.strictOptionalField(ExtraCodecs.POSITIVE_INT, "count", Integer.valueOf(1)).forGetter(ItemStack::getCount)).apply((Applicative)$$0, ItemStack::new));
/*      */   }
/*      */   private static final Codec<Item> ITEM_NON_AIR_CODEC; public static final Codec<ItemStack> ADVANCEMENT_ICON_CODEC;
/*      */   public static final Codec<ItemStack> ITEM_WITH_COUNT_CODEC;
/*  104 */   public static final Codec<ItemStack> SINGLE_ITEM_CODEC = ITEM_NON_AIR_CODEC.xmap(ItemStack::new, ItemStack::getItem); public static final MapCodec<ItemStack> RESULT_CODEC; static {
/*  105 */     RESULT_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)BuiltInRegistries.ITEM.byNameCodec().fieldOf("result").forGetter(ItemStack::getItem), (App)Codec.INT.fieldOf("count").forGetter(ItemStack::getCount)).apply((Applicative)$$0, ItemStack::new));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  110 */   private static final Logger LOGGER = LogUtils.getLogger();
/*  111 */   public static final ItemStack EMPTY = new ItemStack((Void)null); public static final DecimalFormat ATTRIBUTE_MODIFIER_FORMAT; public static final String TAG_ENCH = "Enchantments"; public static final String TAG_DISPLAY = "display"; public static final String TAG_DISPLAY_NAME = "Name"; public static final String TAG_LORE = "Lore"; public static final String TAG_DAMAGE = "Damage";
/*      */   static {
/*  113 */     ATTRIBUTE_MODIFIER_FORMAT = (DecimalFormat)Util.make(new DecimalFormat("#.##"), $$0 -> $$0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static final String TAG_COLOR = "color";
/*      */ 
/*      */   
/*      */   private static final String TAG_UNBREAKABLE = "Unbreakable";
/*      */   
/*      */   private static final String TAG_REPAIR_COST = "RepairCost";
/*      */   
/*      */   private static final String TAG_CAN_DESTROY_BLOCK_LIST = "CanDestroy";
/*      */   
/*      */   private static final String TAG_CAN_PLACE_ON_BLOCK_LIST = "CanPlaceOn";
/*      */   
/*      */   private static final String TAG_HIDE_FLAGS = "HideFlags";
/*      */   
/*  131 */   private static final Component DISABLED_ITEM_TOOLTIP = (Component)Component.translatable("item.disabled").withStyle(ChatFormatting.RED); private static final int DONT_HIDE_TOOLTIP = 0;
/*      */   
/*      */   public Optional<TooltipComponent> getTooltipImage() {
/*  134 */     return getItem().getTooltipImage(this);
/*      */   }
/*      */   
/*      */   public enum TooltipPart {
/*  138 */     ENCHANTMENTS,
/*  139 */     MODIFIERS,
/*  140 */     UNBREAKABLE,
/*  141 */     CAN_DESTROY,
/*  142 */     CAN_PLACE,
/*  143 */     ADDITIONAL,
/*  144 */     DYE,
/*  145 */     UPGRADES;
/*      */     
/*      */     TooltipPart() {
/*  148 */       this.mask = 1 << ordinal();
/*      */     } private final int mask;
/*      */     public int getMask() {
/*  151 */       return this.mask;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*  157 */   private static final Style LORE_STYLE = Style.EMPTY.withColor(ChatFormatting.DARK_PURPLE).withItalic(Boolean.valueOf(true));
/*      */   
/*      */   private int count;
/*      */   
/*      */   private int popTime;
/*      */   
/*      */   @Deprecated
/*      */   @Nullable
/*      */   private final Item item;
/*      */   @Nullable
/*      */   private CompoundTag tag;
/*      */   @Nullable
/*      */   private Entity entityRepresentation;
/*      */   @Nullable
/*      */   private AdventureModeCheck adventureBreakCheck;
/*      */   @Nullable
/*      */   private AdventureModeCheck adventurePlaceCheck;
/*      */   
/*      */   public ItemStack(ItemLike $$0) {
/*  176 */     this($$0, 1);
/*      */   }
/*      */   
/*      */   public ItemStack(Holder<Item> $$0) {
/*  180 */     this((ItemLike)$$0.value(), 1);
/*      */   }
/*      */   
/*      */   public ItemStack(Holder<Item> $$0, int $$1, Optional<CompoundTag> $$2) {
/*  184 */     this($$0, $$1);
/*  185 */     $$2.ifPresent(this::setTag);
/*      */   }
/*      */   
/*      */   public ItemStack(Holder<Item> $$0, int $$1) {
/*  189 */     this((ItemLike)$$0.value(), $$1);
/*      */   }
/*      */   
/*      */   public ItemStack(ItemLike $$0, int $$1) {
/*  193 */     this.item = $$0.asItem();
/*  194 */     this.count = $$1;
/*      */     
/*  196 */     if (this.item.canBeDepleted()) {
/*  197 */       setDamageValue(getDamageValue());
/*      */     }
/*      */   }
/*      */   
/*      */   private ItemStack(@Nullable Void $$0) {
/*  202 */     this.item = null;
/*      */   }
/*      */   
/*      */   private ItemStack(CompoundTag $$0) {
/*  206 */     this.item = (Item)BuiltInRegistries.ITEM.get(new ResourceLocation($$0.getString("id")));
/*  207 */     this.count = $$0.getByte("Count");
/*      */     
/*  209 */     if ($$0.contains("tag", 10)) {
/*  210 */       this.tag = $$0.getCompound("tag").copy();
/*  211 */       getItem().verifyTagAfterLoad(this.tag);
/*      */     } 
/*      */     
/*  214 */     if (getItem().canBeDepleted()) {
/*  215 */       setDamageValue(getDamageValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public static ItemStack of(CompoundTag $$0) {
/*      */     try {
/*  221 */       return new ItemStack($$0);
/*  222 */     } catch (RuntimeException $$1) {
/*  223 */       LOGGER.debug("Tried to load invalid item: {}", $$0, $$1);
/*  224 */       return EMPTY;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isEmpty() {
/*  229 */     return (this == EMPTY || this.item == Items.AIR || this.count <= 0);
/*      */   }
/*      */   
/*      */   public boolean isItemEnabled(FeatureFlagSet $$0) {
/*  233 */     return (isEmpty() || getItem().isEnabled($$0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack split(int $$0) {
/*  241 */     int $$1 = Math.min($$0, getCount());
/*      */     
/*  243 */     ItemStack $$2 = copyWithCount($$1);
/*  244 */     shrink($$1);
/*      */     
/*  246 */     return $$2;
/*      */   }
/*      */   
/*      */   public ItemStack copyAndClear() {
/*  250 */     if (isEmpty()) {
/*  251 */       return EMPTY;
/*      */     }
/*  253 */     ItemStack $$0 = copy();
/*  254 */     setCount(0);
/*  255 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getItem() {
/*  260 */     return isEmpty() ? Items.AIR : this.item;
/*      */   }
/*      */   
/*      */   public Holder<Item> getItemHolder() {
/*  264 */     return (Holder<Item>)getItem().builtInRegistryHolder();
/*      */   }
/*      */   
/*      */   public boolean is(TagKey<Item> $$0) {
/*  268 */     return getItem().builtInRegistryHolder().is($$0);
/*      */   }
/*      */   
/*      */   public boolean is(Item $$0) {
/*  272 */     return (getItem() == $$0);
/*      */   }
/*      */   
/*      */   public boolean is(Predicate<Holder<Item>> $$0) {
/*  276 */     return $$0.test(getItem().builtInRegistryHolder());
/*      */   }
/*      */   
/*      */   public boolean is(Holder<Item> $$0) {
/*  280 */     return (getItem().builtInRegistryHolder() == $$0);
/*      */   }
/*      */   
/*      */   public boolean is(HolderSet<Item> $$0) {
/*  284 */     return $$0.contains(getItemHolder());
/*      */   }
/*      */   
/*      */   public Stream<TagKey<Item>> getTags() {
/*  288 */     return getItem().builtInRegistryHolder().tags();
/*      */   }
/*      */   
/*      */   public InteractionResult useOn(UseOnContext $$0) {
/*  292 */     Player $$1 = $$0.getPlayer();
/*  293 */     BlockPos $$2 = $$0.getClickedPos();
/*  294 */     BlockInWorld $$3 = new BlockInWorld((LevelReader)$$0.getLevel(), $$2, false);
/*  295 */     if ($$1 != null && !($$1.getAbilities()).mayBuild && !hasAdventureModePlaceTagForBlock($$0.getLevel().registryAccess().registryOrThrow(Registries.BLOCK), $$3)) {
/*  296 */       return InteractionResult.PASS;
/*      */     }
/*      */     
/*  299 */     Item $$4 = getItem();
/*  300 */     InteractionResult $$5 = $$4.useOn($$0);
/*  301 */     if ($$1 != null && $$5.shouldAwardStats()) {
/*  302 */       $$1.awardStat(Stats.ITEM_USED.get($$4));
/*      */     }
/*  304 */     return $$5;
/*      */   }
/*      */   
/*      */   public float getDestroySpeed(BlockState $$0) {
/*  308 */     return getItem().getDestroySpeed(this, $$0);
/*      */   }
/*      */   
/*      */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  312 */     return getItem().use($$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public ItemStack finishUsingItem(Level $$0, LivingEntity $$1) {
/*  316 */     return getItem().finishUsingItem(this, $$0, $$1);
/*      */   }
/*      */   
/*      */   public CompoundTag save(CompoundTag $$0) {
/*  320 */     ResourceLocation $$1 = BuiltInRegistries.ITEM.getKey(getItem());
/*  321 */     $$0.putString("id", ($$1 == null) ? "minecraft:air" : $$1.toString());
/*  322 */     $$0.putByte("Count", (byte)this.count);
/*  323 */     if (this.tag != null) {
/*  324 */       $$0.put("tag", (Tag)this.tag.copy());
/*      */     }
/*  326 */     return $$0;
/*      */   }
/*      */   
/*      */   public int getMaxStackSize() {
/*  330 */     return getItem().getMaxStackSize();
/*      */   }
/*      */   
/*      */   public boolean isStackable() {
/*  334 */     return (getMaxStackSize() > 1 && (!isDamageableItem() || !isDamaged()));
/*      */   }
/*      */   
/*      */   public boolean isDamageableItem() {
/*  338 */     if (isEmpty() || getItem().getMaxDamage() <= 0) {
/*  339 */       return false;
/*      */     }
/*  341 */     CompoundTag $$0 = getTag();
/*  342 */     return ($$0 == null || !$$0.getBoolean("Unbreakable"));
/*      */   }
/*      */   
/*      */   public boolean isDamaged() {
/*  346 */     return (isDamageableItem() && getDamageValue() > 0);
/*      */   }
/*      */   
/*      */   public int getDamageValue() {
/*  350 */     return (this.tag == null) ? 0 : this.tag.getInt("Damage");
/*      */   }
/*      */   
/*      */   public void setDamageValue(int $$0) {
/*  354 */     getOrCreateTag().putInt("Damage", Math.max(0, $$0));
/*      */   }
/*      */   
/*      */   public int getMaxDamage() {
/*  358 */     return getItem().getMaxDamage();
/*      */   }
/*      */   
/*      */   public boolean hurt(int $$0, RandomSource $$1, @Nullable ServerPlayer $$2) {
/*  362 */     if (!isDamageableItem()) {
/*  363 */       return false;
/*      */     }
/*      */     
/*  366 */     if ($$0 > 0) {
/*  367 */       int $$3 = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.UNBREAKING, this);
/*      */       
/*  369 */       int $$4 = 0;
/*  370 */       for (int $$5 = 0; $$3 > 0 && $$5 < $$0; $$5++) {
/*  371 */         if (DigDurabilityEnchantment.shouldIgnoreDurabilityDrop(this, $$3, $$1)) {
/*  372 */           $$4++;
/*      */         }
/*      */       } 
/*  375 */       $$0 -= $$4;
/*      */       
/*  377 */       if ($$0 <= 0) {
/*  378 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  382 */     if ($$2 != null && $$0 != 0) {
/*  383 */       CriteriaTriggers.ITEM_DURABILITY_CHANGED.trigger($$2, this, getDamageValue() + $$0);
/*      */     }
/*      */     
/*  386 */     int $$6 = getDamageValue() + $$0;
/*      */     
/*  388 */     setDamageValue($$6);
/*      */     
/*  390 */     return ($$6 >= getMaxDamage());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public <T extends LivingEntity> void hurtAndBreak(int $$0, T $$1, Consumer<T> $$2) {
/*  396 */     if (($$1.level()).isClientSide || ($$1 instanceof Player && (((Player)$$1).getAbilities()).instabuild)) {
/*      */       return;
/*      */     }
/*  399 */     if (!isDamageableItem()) {
/*      */       return;
/*      */     }
/*      */     
/*  403 */     if (hurt($$0, $$1.getRandom(), ($$1 instanceof ServerPlayer) ? (ServerPlayer)$$1 : null)) {
/*  404 */       $$2.accept($$1);
/*      */       
/*  406 */       Item $$3 = getItem();
/*  407 */       shrink(1);
/*  408 */       if ($$1 instanceof Player) {
/*  409 */         ((Player)$$1).awardStat(Stats.ITEM_BROKEN.get($$3));
/*      */       }
/*      */ 
/*      */       
/*  413 */       setDamageValue(0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean isBarVisible() {
/*  418 */     return getItem().isBarVisible(this);
/*      */   }
/*      */   
/*      */   public int getBarWidth() {
/*  422 */     return getItem().getBarWidth(this);
/*      */   }
/*      */   
/*      */   public int getBarColor() {
/*  426 */     return getItem().getBarColor(this);
/*      */   }
/*      */   
/*      */   public boolean overrideStackedOnOther(Slot $$0, ClickAction $$1, Player $$2) {
/*  430 */     return getItem().overrideStackedOnOther(this, $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean overrideOtherStackedOnMe(ItemStack $$0, Slot $$1, ClickAction $$2, Player $$3, SlotAccess $$4) {
/*  434 */     return getItem().overrideOtherStackedOnMe(this, $$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */   
/*      */   public void hurtEnemy(LivingEntity $$0, Player $$1) {
/*  438 */     Item $$2 = getItem();
/*  439 */     if ($$2.hurtEnemy(this, $$0, (LivingEntity)$$1)) {
/*  440 */       $$1.awardStat(Stats.ITEM_USED.get($$2));
/*      */     }
/*      */   }
/*      */   
/*      */   public void mineBlock(Level $$0, BlockState $$1, BlockPos $$2, Player $$3) {
/*  445 */     Item $$4 = getItem();
/*  446 */     if ($$4.mineBlock(this, $$0, $$1, $$2, (LivingEntity)$$3)) {
/*  447 */       $$3.awardStat(Stats.ITEM_USED.get($$4));
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean isCorrectToolForDrops(BlockState $$0) {
/*  452 */     return getItem().isCorrectToolForDrops($$0);
/*      */   }
/*      */   
/*      */   public InteractionResult interactLivingEntity(Player $$0, LivingEntity $$1, InteractionHand $$2) {
/*  456 */     return getItem().interactLivingEntity(this, $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public ItemStack copy() {
/*  460 */     if (isEmpty()) {
/*  461 */       return EMPTY;
/*      */     }
/*  463 */     ItemStack $$0 = new ItemStack(getItem(), this.count);
/*  464 */     $$0.setPopTime(getPopTime());
/*  465 */     if (this.tag != null) {
/*  466 */       $$0.tag = this.tag.copy();
/*      */     }
/*  468 */     return $$0;
/*      */   }
/*      */   
/*      */   public ItemStack copyWithCount(int $$0) {
/*  472 */     if (isEmpty()) {
/*  473 */       return EMPTY;
/*      */     }
/*  475 */     ItemStack $$1 = copy();
/*  476 */     $$1.setCount($$0);
/*  477 */     return $$1;
/*      */   }
/*      */   
/*      */   public static boolean matches(ItemStack $$0, ItemStack $$1) {
/*  481 */     if ($$0 == $$1) {
/*  482 */       return true;
/*      */     }
/*  484 */     if ($$0.getCount() != $$1.getCount()) {
/*  485 */       return false;
/*      */     }
/*  487 */     return isSameItemSameTags($$0, $$1);
/*      */   }
/*      */   
/*      */   public static boolean isSameItem(ItemStack $$0, ItemStack $$1) {
/*  491 */     return $$0.is($$1.getItem());
/*      */   }
/*      */   
/*      */   public static boolean isSameItemSameTags(ItemStack $$0, ItemStack $$1) {
/*  495 */     if (!$$0.is($$1.getItem())) {
/*  496 */       return false;
/*      */     }
/*  498 */     if ($$0.isEmpty() && $$1.isEmpty()) {
/*  499 */       return true;
/*      */     }
/*  501 */     return Objects.equals($$0.tag, $$1.tag);
/*      */   }
/*      */   
/*      */   public String getDescriptionId() {
/*  505 */     return getItem().getDescriptionId(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/*  510 */     return "" + getCount() + " " + getCount();
/*      */   }
/*      */   
/*      */   public void inventoryTick(Level $$0, Entity $$1, int $$2, boolean $$3) {
/*  514 */     if (this.popTime > 0) {
/*  515 */       this.popTime--;
/*      */     }
/*  517 */     if (getItem() != null) {
/*  518 */       getItem().inventoryTick(this, $$0, $$1, $$2, $$3);
/*      */     }
/*      */   }
/*      */   
/*      */   public void onCraftedBy(Level $$0, Player $$1, int $$2) {
/*  523 */     $$1.awardStat(Stats.ITEM_CRAFTED.get(getItem()), $$2);
/*  524 */     getItem().onCraftedBy(this, $$0, $$1);
/*      */   }
/*      */   
/*      */   public void onCraftedBySystem(Level $$0) {
/*  528 */     getItem().onCraftedPostProcess(this, $$0);
/*      */   }
/*      */   
/*      */   public int getUseDuration() {
/*  532 */     return getItem().getUseDuration(this);
/*      */   }
/*      */   
/*      */   public UseAnim getUseAnimation() {
/*  536 */     return getItem().getUseAnimation(this);
/*      */   }
/*      */   
/*      */   public void releaseUsing(Level $$0, LivingEntity $$1, int $$2) {
/*  540 */     getItem().releaseUsing(this, $$0, $$1, $$2);
/*      */   }
/*      */   
/*      */   public boolean useOnRelease() {
/*  544 */     return getItem().useOnRelease(this);
/*      */   }
/*      */   
/*      */   public boolean hasTag() {
/*  548 */     return (!isEmpty() && this.tag != null && !this.tag.isEmpty());
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public CompoundTag getTag() {
/*  553 */     return this.tag;
/*      */   }
/*      */   
/*      */   public CompoundTag getOrCreateTag() {
/*  557 */     if (this.tag == null) {
/*  558 */       setTag(new CompoundTag());
/*      */     }
/*      */     
/*  561 */     return this.tag;
/*      */   }
/*      */   
/*      */   public CompoundTag getOrCreateTagElement(String $$0) {
/*  565 */     if (this.tag == null || !this.tag.contains($$0, 10)) {
/*  566 */       CompoundTag $$1 = new CompoundTag();
/*  567 */       addTagElement($$0, (Tag)$$1);
/*  568 */       return $$1;
/*      */     } 
/*  570 */     return this.tag.getCompound($$0);
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public CompoundTag getTagElement(String $$0) {
/*  575 */     if (this.tag == null || !this.tag.contains($$0, 10)) {
/*  576 */       return null;
/*      */     }
/*  578 */     return this.tag.getCompound($$0);
/*      */   }
/*      */   
/*      */   public void removeTagKey(String $$0) {
/*  582 */     if (this.tag != null && this.tag.contains($$0)) {
/*  583 */       this.tag.remove($$0);
/*  584 */       if (this.tag.isEmpty()) {
/*  585 */         this.tag = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public ListTag getEnchantmentTags() {
/*  591 */     if (this.tag != null) {
/*  592 */       return this.tag.getList("Enchantments", 10);
/*      */     }
/*  594 */     return new ListTag();
/*      */   }
/*      */   
/*      */   public void setTag(@Nullable CompoundTag $$0) {
/*  598 */     this.tag = $$0;
/*      */     
/*  600 */     if (getItem().canBeDepleted()) {
/*  601 */       setDamageValue(getDamageValue());
/*      */     }
/*      */     
/*  604 */     if ($$0 != null) {
/*  605 */       getItem().verifyTagAfterLoad($$0);
/*      */     }
/*      */   }
/*      */   
/*      */   public Component getHoverName() {
/*  610 */     CompoundTag $$0 = getTagElement("display");
/*  611 */     if ($$0 != null && 
/*  612 */       $$0.contains("Name", 8)) {
/*      */       try {
/*  614 */         MutableComponent mutableComponent = Component.Serializer.fromJson($$0.getString("Name"));
/*  615 */         if (mutableComponent != null) {
/*  616 */           return (Component)mutableComponent;
/*      */         }
/*  618 */         $$0.remove("Name");
/*      */       }
/*  620 */       catch (Exception $$2) {
/*  621 */         $$0.remove("Name");
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  626 */     return getItem().getName(this);
/*      */   }
/*      */   
/*      */   public ItemStack setHoverName(@Nullable Component $$0) {
/*  630 */     CompoundTag $$1 = getOrCreateTagElement("display");
/*  631 */     if ($$0 != null) {
/*  632 */       $$1.putString("Name", Component.Serializer.toJson($$0));
/*      */     } else {
/*  634 */       $$1.remove("Name");
/*      */     } 
/*  636 */     return this;
/*      */   }
/*      */   
/*      */   public void resetHoverName() {
/*  640 */     CompoundTag $$0 = getTagElement("display");
/*  641 */     if ($$0 != null) {
/*  642 */       $$0.remove("Name");
/*      */       
/*  644 */       if ($$0.isEmpty()) {
/*  645 */         removeTagKey("display");
/*      */       }
/*      */     } 
/*      */     
/*  649 */     if (this.tag != null && this.tag.isEmpty()) {
/*  650 */       this.tag = null;
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean hasCustomHoverName() {
/*  655 */     CompoundTag $$0 = getTagElement("display");
/*  656 */     return ($$0 != null && $$0.contains("Name", 8));
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Component> getTooltipLines(@Nullable Player $$0, TooltipFlag $$1) {
/*  661 */     List<Component> $$2 = Lists.newArrayList();
/*      */     
/*  663 */     MutableComponent $$3 = Component.empty().append(getHoverName()).withStyle((getRarity()).color);
/*  664 */     if (hasCustomHoverName()) {
/*  665 */       $$3.withStyle(ChatFormatting.ITALIC);
/*      */     }
/*  667 */     $$2.add($$3);
/*      */     
/*  669 */     if (!$$1.isAdvanced() && !hasCustomHoverName() && is(Items.FILLED_MAP)) {
/*  670 */       Integer $$4 = MapItem.getMapId(this);
/*  671 */       if ($$4 != null) {
/*  672 */         $$2.add(MapItem.getTooltipForId(this));
/*      */       }
/*      */     } 
/*      */     
/*  676 */     int $$5 = getHideFlags();
/*      */     
/*  678 */     if (shouldShowInTooltip($$5, TooltipPart.ADDITIONAL)) {
/*  679 */       getItem().appendHoverText(this, ($$0 == null) ? null : $$0.level(), $$2, $$1);
/*      */     }
/*      */     
/*  682 */     if (hasTag()) {
/*  683 */       if (shouldShowInTooltip($$5, TooltipPart.UPGRADES) && $$0 != null) {
/*  684 */         ArmorTrim.appendUpgradeHoverText(this, $$0.level().registryAccess(), $$2);
/*      */       }
/*      */       
/*  687 */       if (shouldShowInTooltip($$5, TooltipPart.ENCHANTMENTS)) {
/*  688 */         appendEnchantmentNames($$2, getEnchantmentTags());
/*      */       }
/*      */       
/*  691 */       if (this.tag.contains("display", 10)) {
/*  692 */         CompoundTag $$6 = this.tag.getCompound("display");
/*      */         
/*  694 */         if (shouldShowInTooltip($$5, TooltipPart.DYE) && $$6.contains("color", 99)) {
/*  695 */           if ($$1.isAdvanced()) {
/*  696 */             $$2.add(Component.translatable("item.color", new Object[] { String.format(Locale.ROOT, "#%06X", new Object[] { Integer.valueOf($$6.getInt("color")) }) }).withStyle(ChatFormatting.GRAY));
/*      */           } else {
/*  698 */             $$2.add(Component.translatable("item.dyed").withStyle(new ChatFormatting[] { ChatFormatting.GRAY, ChatFormatting.ITALIC }));
/*      */           } 
/*      */         }
/*      */         
/*  702 */         if ($$6.getTagType("Lore") == 9) {
/*  703 */           ListTag $$7 = $$6.getList("Lore", 8);
/*  704 */           for (int $$8 = 0; $$8 < $$7.size(); $$8++) {
/*  705 */             String $$9 = $$7.getString($$8);
/*      */             try {
/*  707 */               MutableComponent $$10 = Component.Serializer.fromJson($$9);
/*  708 */               if ($$10 != null) {
/*  709 */                 $$2.add(ComponentUtils.mergeStyles($$10, LORE_STYLE));
/*      */               }
/*  711 */             } catch (Exception $$11) {
/*  712 */               $$6.remove("Lore");
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  719 */     if (shouldShowInTooltip($$5, TooltipPart.MODIFIERS)) {
/*  720 */       for (EquipmentSlot $$12 : EquipmentSlot.values()) {
/*      */         
/*  722 */         Multimap<Attribute, AttributeModifier> $$13 = getAttributeModifiers($$12);
/*  723 */         if (!$$13.isEmpty()) {
/*  724 */           $$2.add(CommonComponents.EMPTY);
/*  725 */           $$2.add(Component.translatable("item.modifiers." + $$12.getName()).withStyle(ChatFormatting.GRAY));
/*  726 */           for (Map.Entry<Attribute, AttributeModifier> $$14 : (Iterable<Map.Entry<Attribute, AttributeModifier>>)$$13.entries()) {
/*  727 */             double $$20; AttributeModifier $$15 = $$14.getValue();
/*  728 */             double $$16 = $$15.getAmount();
/*      */             
/*  730 */             boolean $$17 = false;
/*      */             
/*  732 */             if ($$0 != null) {
/*  733 */               if ($$15.getId() == Item.BASE_ATTACK_DAMAGE_UUID) {
/*  734 */                 $$16 += $$0.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
/*  735 */                 $$16 += EnchantmentHelper.getDamageBonus(this, MobType.UNDEFINED);
/*  736 */                 $$17 = true;
/*  737 */               } else if ($$15.getId() == Item.BASE_ATTACK_SPEED_UUID) {
/*  738 */                 $$16 += $$0.getAttributeBaseValue(Attributes.ATTACK_SPEED);
/*  739 */                 $$17 = true;
/*      */               } 
/*      */             }
/*      */             
/*  743 */             if ($$15.getOperation() == AttributeModifier.Operation.MULTIPLY_BASE || $$15.getOperation() == AttributeModifier.Operation.MULTIPLY_TOTAL) {
/*  744 */               double $$18 = $$16 * 100.0D;
/*  745 */             } else if (((Attribute)$$14.getKey()).equals(Attributes.KNOCKBACK_RESISTANCE)) {
/*  746 */               double $$19 = $$16 * 10.0D;
/*      */             } else {
/*  748 */               $$20 = $$16;
/*      */             } 
/*      */             
/*  751 */             if ($$17) {
/*  752 */               $$2.add(
/*  753 */                   CommonComponents.space().append(
/*  754 */                     (Component)Component.translatable("attribute.modifier.equals." + $$15.getOperation().toValue(), new Object[] { ATTRIBUTE_MODIFIER_FORMAT
/*  755 */                         .format($$20), 
/*  756 */                         Component.translatable(((Attribute)$$14.getKey()).getDescriptionId())
/*      */                       
/*  758 */                       })).withStyle(ChatFormatting.DARK_GREEN)); continue;
/*      */             } 
/*  760 */             if ($$16 > 0.0D) {
/*  761 */               $$2.add(
/*  762 */                   Component.translatable("attribute.modifier.plus." + $$15.getOperation().toValue(), new Object[] { ATTRIBUTE_MODIFIER_FORMAT
/*  763 */                       .format($$20), 
/*  764 */                       Component.translatable(((Attribute)$$14.getKey()).getDescriptionId())
/*  765 */                     }).withStyle(ChatFormatting.BLUE)); continue;
/*      */             } 
/*  767 */             if ($$16 < 0.0D) {
/*  768 */               $$20 *= -1.0D;
/*  769 */               $$2.add(
/*  770 */                   Component.translatable("attribute.modifier.take." + $$15.getOperation().toValue(), new Object[] { ATTRIBUTE_MODIFIER_FORMAT
/*  771 */                       .format($$20), 
/*  772 */                       Component.translatable(((Attribute)$$14.getKey()).getDescriptionId())
/*  773 */                     }).withStyle(ChatFormatting.RED));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  781 */     if (hasTag()) {
/*  782 */       if (shouldShowInTooltip($$5, TooltipPart.UNBREAKABLE) && this.tag.getBoolean("Unbreakable")) {
/*  783 */         $$2.add(Component.translatable("item.unbreakable").withStyle(ChatFormatting.BLUE));
/*      */       }
/*      */       
/*  786 */       if (shouldShowInTooltip($$5, TooltipPart.CAN_DESTROY) && this.tag.contains("CanDestroy", 9)) {
/*  787 */         ListTag $$21 = this.tag.getList("CanDestroy", 8);
/*  788 */         if (!$$21.isEmpty()) {
/*  789 */           $$2.add(CommonComponents.EMPTY);
/*  790 */           $$2.add(Component.translatable("item.canBreak").withStyle(ChatFormatting.GRAY));
/*  791 */           for (int $$22 = 0; $$22 < $$21.size(); $$22++) {
/*  792 */             $$2.addAll(expandBlockState($$21.getString($$22)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  797 */       if (shouldShowInTooltip($$5, TooltipPart.CAN_PLACE) && this.tag.contains("CanPlaceOn", 9)) {
/*  798 */         ListTag $$23 = this.tag.getList("CanPlaceOn", 8);
/*  799 */         if (!$$23.isEmpty()) {
/*  800 */           $$2.add(CommonComponents.EMPTY);
/*  801 */           $$2.add(Component.translatable("item.canPlace").withStyle(ChatFormatting.GRAY));
/*  802 */           for (int $$24 = 0; $$24 < $$23.size(); $$24++) {
/*  803 */             $$2.addAll(expandBlockState($$23.getString($$24)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  809 */     if ($$1.isAdvanced()) {
/*  810 */       if (isDamaged()) {
/*  811 */         $$2.add(Component.translatable("item.durability", new Object[] { Integer.valueOf(getMaxDamage() - getDamageValue()), Integer.valueOf(getMaxDamage()) }));
/*      */       }
/*  813 */       $$2.add(Component.literal(BuiltInRegistries.ITEM.getKey(getItem()).toString()).withStyle(ChatFormatting.DARK_GRAY));
/*  814 */       if (hasTag()) {
/*  815 */         $$2.add(Component.translatable("item.nbt_tags", new Object[] { Integer.valueOf(this.tag.getAllKeys().size()) }).withStyle(ChatFormatting.DARK_GRAY));
/*      */       }
/*      */     } 
/*      */     
/*  819 */     if ($$0 != null && !getItem().isEnabled($$0.level().enabledFeatures())) {
/*  820 */       $$2.add(DISABLED_ITEM_TOOLTIP);
/*      */     }
/*      */     
/*  823 */     return $$2;
/*      */   }
/*      */   
/*      */   private static boolean shouldShowInTooltip(int $$0, TooltipPart $$1) {
/*  827 */     return (($$0 & $$1.getMask()) == 0);
/*      */   }
/*      */   
/*      */   private int getHideFlags() {
/*  831 */     if (hasTag() && this.tag.contains("HideFlags", 99)) {
/*  832 */       return this.tag.getInt("HideFlags");
/*      */     }
/*  834 */     return 0;
/*      */   }
/*      */   
/*      */   public void hideTooltipPart(TooltipPart $$0) {
/*  838 */     CompoundTag $$1 = getOrCreateTag();
/*  839 */     $$1.putInt("HideFlags", $$1.getInt("HideFlags") | $$0.getMask());
/*      */   }
/*      */   
/*      */   public static void appendEnchantmentNames(List<Component> $$0, ListTag $$1) {
/*  843 */     for (int $$2 = 0; $$2 < $$1.size(); $$2++) {
/*  844 */       CompoundTag $$3 = $$1.getCompound($$2);
/*      */       
/*  846 */       BuiltInRegistries.ENCHANTMENT.getOptional(EnchantmentHelper.getEnchantmentId($$3))
/*  847 */         .ifPresent($$2 -> $$0.add($$2.getFullname(EnchantmentHelper.getEnchantmentLevel($$1))));
/*      */     } 
/*      */   }
/*      */   
/*      */   private static Collection<Component> expandBlockState(String $$0) {
/*      */     try {
/*  853 */       return (Collection<Component>)BlockStateParser.parseForTesting((HolderLookup)BuiltInRegistries.BLOCK.asLookup(), $$0, true).map($$0 -> Lists.newArrayList((Object[])new Component[] { (Component)$$0.blockState().getBlock().getName().withStyle(ChatFormatting.DARK_GRAY) }, ), $$0 -> (List)$$0.tag().stream().map(()).collect(Collectors.toList()));
/*      */ 
/*      */     
/*      */     }
/*  857 */     catch (CommandSyntaxException commandSyntaxException) {
/*      */       
/*  859 */       return Lists.newArrayList((Object[])new Component[] { (Component)Component.literal("missingno").withStyle(ChatFormatting.DARK_GRAY) });
/*      */     } 
/*      */   }
/*      */   public boolean hasFoil() {
/*  863 */     return getItem().isFoil(this);
/*      */   }
/*      */   
/*      */   public Rarity getRarity() {
/*  867 */     return getItem().getRarity(this);
/*      */   }
/*      */   
/*      */   public boolean isEnchantable() {
/*  871 */     if (!getItem().isEnchantable(this)) {
/*  872 */       return false;
/*      */     }
/*  874 */     if (isEnchanted()) {
/*  875 */       return false;
/*      */     }
/*  877 */     return true;
/*      */   }
/*      */   
/*      */   public void enchant(Enchantment $$0, int $$1) {
/*  881 */     getOrCreateTag();
/*  882 */     if (!this.tag.contains("Enchantments", 9)) {
/*  883 */       this.tag.put("Enchantments", (Tag)new ListTag());
/*      */     }
/*  885 */     ListTag $$2 = this.tag.getList("Enchantments", 10);
/*  886 */     $$2.add(EnchantmentHelper.storeEnchantment(EnchantmentHelper.getEnchantmentId($$0), (byte)$$1));
/*      */   }
/*      */   
/*      */   public boolean isEnchanted() {
/*  890 */     if (this.tag != null && this.tag.contains("Enchantments", 9)) {
/*  891 */       return !this.tag.getList("Enchantments", 10).isEmpty();
/*      */     }
/*  893 */     return false;
/*      */   }
/*      */   
/*      */   public void addTagElement(String $$0, Tag $$1) {
/*  897 */     getOrCreateTag().put($$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean isFramed() {
/*  901 */     return this.entityRepresentation instanceof ItemFrame;
/*      */   }
/*      */   
/*      */   public void setEntityRepresentation(@Nullable Entity $$0) {
/*  905 */     this.entityRepresentation = $$0;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public ItemFrame getFrame() {
/*  910 */     return (this.entityRepresentation instanceof ItemFrame) ? (ItemFrame)getEntityRepresentation() : null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntityRepresentation() {
/*  915 */     return !isEmpty() ? this.entityRepresentation : null;
/*      */   }
/*      */   
/*      */   public int getBaseRepairCost() {
/*  919 */     if (hasTag() && this.tag.contains("RepairCost", 3)) {
/*  920 */       return this.tag.getInt("RepairCost");
/*      */     }
/*  922 */     return 0;
/*      */   }
/*      */   
/*      */   public void setRepairCost(int $$0) {
/*  926 */     if ($$0 > 0) {
/*  927 */       getOrCreateTag().putInt("RepairCost", $$0);
/*      */     } else {
/*  929 */       removeTagKey("RepairCost");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot $$0) {
/*      */     Multimap<Attribute, AttributeModifier> $$7;
/*  936 */     if (hasTag() && this.tag.contains("AttributeModifiers", 9))
/*  937 */     { HashMultimap hashMultimap = HashMultimap.create();
/*  938 */       ListTag $$2 = this.tag.getList("AttributeModifiers", 10);
/*      */       
/*  940 */       for (int $$3 = 0; $$3 < $$2.size(); $$3++) {
/*  941 */         CompoundTag $$4 = $$2.getCompound($$3);
/*  942 */         if (!$$4.contains("Slot", 8) || 
/*  943 */           $$4.getString("Slot").equals($$0.getName())) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  948 */           Optional<Attribute> $$5 = BuiltInRegistries.ATTRIBUTE.getOptional(ResourceLocation.tryParse($$4.getString("AttributeName")));
/*  949 */           if (!$$5.isEmpty()) {
/*      */ 
/*      */ 
/*      */             
/*  953 */             AttributeModifier $$6 = AttributeModifier.load($$4);
/*  954 */             if ($$6 != null)
/*      */             {
/*      */ 
/*      */               
/*  958 */               if ($$6.getId().getLeastSignificantBits() != 0L && $$6.getId().getMostSignificantBits() != 0L)
/*  959 */                 hashMultimap.put($$5.get(), $$6);  } 
/*      */           } 
/*      */         } 
/*      */       }  }
/*  963 */     else { $$7 = getItem().getDefaultAttributeModifiers($$0); }
/*      */ 
/*      */     
/*  966 */     return $$7;
/*      */   }
/*      */   
/*      */   public void addAttributeModifier(Attribute $$0, AttributeModifier $$1, @Nullable EquipmentSlot $$2) {
/*  970 */     getOrCreateTag();
/*  971 */     if (!this.tag.contains("AttributeModifiers", 9)) {
/*  972 */       this.tag.put("AttributeModifiers", (Tag)new ListTag());
/*      */     }
/*  974 */     ListTag $$3 = this.tag.getList("AttributeModifiers", 10);
/*  975 */     CompoundTag $$4 = $$1.save();
/*  976 */     $$4.putString("AttributeName", BuiltInRegistries.ATTRIBUTE.getKey($$0).toString());
/*  977 */     if ($$2 != null) {
/*  978 */       $$4.putString("Slot", $$2.getName());
/*      */     }
/*  980 */     $$3.add($$4);
/*      */   }
/*      */   
/*      */   public Component getDisplayName() {
/*  984 */     MutableComponent $$0 = Component.empty().append(getHoverName());
/*  985 */     if (hasCustomHoverName()) {
/*  986 */       $$0.withStyle(ChatFormatting.ITALIC);
/*      */     }
/*      */     
/*  989 */     MutableComponent $$1 = ComponentUtils.wrapInSquareBrackets((Component)$$0);
/*      */     
/*  991 */     if (!isEmpty()) {
/*  992 */       $$1.withStyle((getRarity()).color).withStyle($$0 -> $$0.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackInfo(this))));
/*      */     }
/*      */     
/*  995 */     return (Component)$$1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAdventureModePlaceTagForBlock(Registry<Block> $$0, BlockInWorld $$1) {
/* 1006 */     if (this.adventurePlaceCheck == null) {
/* 1007 */       this.adventurePlaceCheck = new AdventureModeCheck("CanPlaceOn");
/*      */     }
/* 1009 */     return this.adventurePlaceCheck.test(this, $$0, $$1);
/*      */   }
/*      */   
/*      */   public boolean hasAdventureModeBreakTagForBlock(Registry<Block> $$0, BlockInWorld $$1) {
/* 1013 */     if (this.adventureBreakCheck == null) {
/* 1014 */       this.adventureBreakCheck = new AdventureModeCheck("CanDestroy");
/*      */     }
/* 1016 */     return this.adventureBreakCheck.test(this, $$0, $$1);
/*      */   }
/*      */   
/*      */   public int getPopTime() {
/* 1020 */     return this.popTime;
/*      */   }
/*      */   
/*      */   public void setPopTime(int $$0) {
/* 1024 */     this.popTime = $$0;
/*      */   }
/*      */   
/*      */   public int getCount() {
/* 1028 */     return isEmpty() ? 0 : this.count;
/*      */   }
/*      */   
/*      */   public void setCount(int $$0) {
/* 1032 */     this.count = $$0;
/*      */   }
/*      */   
/*      */   public void grow(int $$0) {
/* 1036 */     setCount(getCount() + $$0);
/*      */   }
/*      */   
/*      */   public void shrink(int $$0) {
/* 1040 */     grow(-$$0);
/*      */   }
/*      */   
/*      */   public void onUseTick(Level $$0, LivingEntity $$1, int $$2) {
/* 1044 */     getItem().onUseTick($$0, $$1, this, $$2);
/*      */   }
/*      */   
/*      */   public void onDestroyed(ItemEntity $$0) {
/* 1048 */     getItem().onDestroyed($$0);
/*      */   }
/*      */   
/*      */   public boolean isEdible() {
/* 1052 */     return getItem().isEdible();
/*      */   }
/*      */   
/*      */   public SoundEvent getDrinkingSound() {
/* 1056 */     return getItem().getDrinkingSound();
/*      */   }
/*      */   
/*      */   public SoundEvent getEatingSound() {
/* 1060 */     return getItem().getEatingSound();
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemStack.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */