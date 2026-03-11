/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ 
/*     */ public class InstrumentItem
/*     */   extends Item {
/*     */   private static final String TAG_INSTRUMENT = "instrument";
/*     */   private final TagKey<Instrument> instruments;
/*     */   
/*     */   public InstrumentItem(Item.Properties $$0, TagKey<Instrument> $$1) {
/*  36 */     super($$0);
/*  37 */     this.instruments = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/*  42 */     super.appendHoverText($$0, $$1, $$2, $$3);
/*     */     
/*  44 */     Optional<ResourceKey<Instrument>> $$4 = getInstrument($$0).flatMap(Holder::unwrapKey);
/*  45 */     if ($$4.isPresent()) {
/*  46 */       MutableComponent $$5 = Component.translatable(Util.makeDescriptionId("instrument", ((ResourceKey)$$4.get()).location()));
/*  47 */       $$2.add($$5.withStyle(ChatFormatting.GRAY));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static ItemStack create(Item $$0, Holder<Instrument> $$1) {
/*  52 */     ItemStack $$2 = new ItemStack($$0);
/*  53 */     setSoundVariantId($$2, $$1);
/*  54 */     return $$2;
/*     */   }
/*     */   
/*     */   public static void setRandom(ItemStack $$0, TagKey<Instrument> $$1, RandomSource $$2) {
/*  58 */     Optional<Holder<Instrument>> $$3 = BuiltInRegistries.INSTRUMENT.getTag($$1).flatMap($$1 -> $$1.getRandomElement($$0));
/*  59 */     $$3.ifPresent($$1 -> setSoundVariantId($$0, $$1));
/*     */   }
/*     */   
/*     */   private static void setSoundVariantId(ItemStack $$0, Holder<Instrument> $$1) {
/*  63 */     CompoundTag $$2 = $$0.getOrCreateTag();
/*  64 */     $$2.putString("instrument", ((ResourceKey)$$1.unwrapKey().orElseThrow(() -> new IllegalStateException("Invalid instrument"))).location().toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  69 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*  70 */     Optional<? extends Holder<Instrument>> $$4 = getInstrument($$3);
/*  71 */     if ($$4.isPresent()) {
/*  72 */       Instrument $$5 = (Instrument)((Holder)$$4.get()).value();
/*  73 */       $$1.startUsingItem($$2);
/*  74 */       play($$0, $$1, $$5);
/*  75 */       $$1.getCooldowns().addCooldown(this, $$5.useDuration());
/*  76 */       $$1.awardStat(Stats.ITEM_USED.get(this));
/*  77 */       return InteractionResultHolder.consume($$3);
/*     */     } 
/*  79 */     return InteractionResultHolder.fail($$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/*  84 */     Optional<? extends Holder<Instrument>> $$1 = getInstrument($$0);
/*  85 */     return ((Integer)$$1.<Integer>map($$0 -> Integer.valueOf(((Instrument)$$0.value()).useDuration())).orElse(Integer.valueOf(0))).intValue();
/*     */   }
/*     */   
/*     */   private Optional<? extends Holder<Instrument>> getInstrument(ItemStack $$0) {
/*  89 */     CompoundTag $$1 = $$0.getTag();
/*  90 */     if ($$1 != null && $$1.contains("instrument", 8)) {
/*  91 */       ResourceLocation $$2 = ResourceLocation.tryParse($$1.getString("instrument"));
/*  92 */       if ($$2 != null) {
/*  93 */         return BuiltInRegistries.INSTRUMENT.getHolder(ResourceKey.create(Registries.INSTRUMENT, $$2));
/*     */       }
/*     */     } 
/*  96 */     Iterator<Holder<Instrument>> $$3 = BuiltInRegistries.INSTRUMENT.getTagOrEmpty(this.instruments).iterator();
/*  97 */     if ($$3.hasNext()) {
/*  98 */       return Optional.of($$3.next());
/*     */     }
/* 100 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/* 105 */     return UseAnim.TOOT_HORN;
/*     */   }
/*     */   
/*     */   private static void play(Level $$0, Player $$1, Instrument $$2) {
/* 109 */     SoundEvent $$3 = (SoundEvent)$$2.soundEvent().value();
/* 110 */     float $$4 = $$2.range() / 16.0F;
/* 111 */     $$0.playSound($$1, (Entity)$$1, $$3, SoundSource.RECORDS, $$4, 1.0F);
/* 112 */     $$0.gameEvent(GameEvent.INSTRUMENT_PLAY, $$1.position(), GameEvent.Context.of((Entity)$$1));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\InstrumentItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */