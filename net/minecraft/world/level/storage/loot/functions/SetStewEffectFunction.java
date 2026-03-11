/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.BiFunction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ 
/*     */ public class SetStewEffectFunction extends LootItemConditionalFunction {
/*     */   private static final Codec<List<EffectEntry>> EFFECTS_LIST;
/*     */   public static final Codec<SetStewEffectFunction> CODEC;
/*     */   private final List<EffectEntry> effects;
/*     */   
/*     */   static {
/*  29 */     EFFECTS_LIST = ExtraCodecs.validate(EffectEntry.CODEC.listOf(), $$0 -> {
/*     */           ObjectOpenHashSet<Holder<MobEffect>> objectOpenHashSet = new ObjectOpenHashSet();
/*     */           
/*     */           for (EffectEntry $$2 : $$0) {
/*     */             if (!objectOpenHashSet.add($$2.effect())) {
/*     */               return DataResult.error(());
/*     */             }
/*     */           } 
/*     */           return DataResult.success($$0);
/*     */         });
/*  39 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)ExtraCodecs.strictOptionalField(EFFECTS_LIST, "effects", List.of()).forGetter(())).apply((Applicative)$$0, SetStewEffectFunction::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SetStewEffectFunction(List<LootItemCondition> $$0, List<EffectEntry> $$1) {
/*  46 */     super($$0);
/*  47 */     this.effects = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  52 */     return LootItemFunctions.SET_STEW_EFFECT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  57 */     return (Set<LootContextParam<?>>)this.effects.stream().flatMap($$0 -> $$0.duration().getReferencedContextParams().stream()).collect(ImmutableSet.toImmutableSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  62 */     if (!$$0.is(Items.SUSPICIOUS_STEW) || this.effects.isEmpty()) {
/*  63 */       return $$0;
/*     */     }
/*     */     
/*  66 */     EffectEntry $$2 = (EffectEntry)Util.getRandom(this.effects, $$1.getRandom());
/*     */     
/*  68 */     MobEffect $$3 = (MobEffect)$$2.effect().value();
/*  69 */     int $$4 = $$2.duration().getInt($$1);
/*  70 */     if (!$$3.isInstantenous()) {
/*  71 */       $$4 *= 20;
/*     */     }
/*     */     
/*  74 */     SuspiciousStewItem.appendMobEffects($$0, List.of(new SuspiciousEffectHolder.EffectEntry($$3, $$4)));
/*  75 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> {
/*  79 */     private final ImmutableList.Builder<SetStewEffectFunction.EffectEntry> effects = ImmutableList.builder();
/*     */ 
/*     */     
/*     */     protected Builder getThis() {
/*  83 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withEffect(MobEffect $$0, NumberProvider $$1) {
/*  87 */       this.effects.add(new SetStewEffectFunction.EffectEntry((Holder<MobEffect>)$$0.builtInRegistryHolder(), $$1));
/*  88 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/*  93 */       return new SetStewEffectFunction(getConditions(), (List<SetStewEffectFunction.EffectEntry>)this.effects.build());
/*     */     }
/*     */   }
/*     */   
/*     */   public static Builder stewEffect() {
/*  98 */     return new Builder();
/*     */   }
/*     */   private static final class EffectEntry extends Record { private final Holder<MobEffect> effect; private final NumberProvider duration; public static final Codec<EffectEntry> CODEC;
/* 101 */     EffectEntry(Holder<MobEffect> $$0, NumberProvider $$1) { this.effect = $$0; this.duration = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #101	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 101 */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry; } public Holder<MobEffect> effect() { return this.effect; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #101	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #101	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetStewEffectFunction$EffectEntry;
/* 101 */       //   0	8	1	$$0	Ljava/lang/Object; } public NumberProvider duration() { return this.duration; } static {
/* 102 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.MOB_EFFECT.holderByNameCodec().fieldOf("type").forGetter(EffectEntry::effect), (App)NumberProviders.CODEC.fieldOf("duration").forGetter(EffectEntry::duration)).apply((Applicative)$$0, EffectEntry::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetStewEffectFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */