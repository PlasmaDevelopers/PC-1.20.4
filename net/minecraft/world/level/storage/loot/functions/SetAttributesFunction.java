/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function6;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
/*     */ import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
/*     */ 
/*     */ public class SetAttributesFunction extends LootItemConditionalFunction {
/*     */   public static final Codec<SetAttributesFunction> CODEC;
/*     */   private final List<Modifier> modifiers;
/*     */   
/*     */   static {
/*  32 */     CODEC = RecordCodecBuilder.create($$0 -> commonFields($$0).and((App)ExtraCodecs.nonEmptyList(Modifier.CODEC.listOf()).fieldOf("modifiers").forGetter(())).apply((Applicative)$$0, SetAttributesFunction::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   SetAttributesFunction(List<LootItemCondition> $$0, List<Modifier> $$1) {
/*  39 */     super($$0);
/*  40 */     this.modifiers = List.copyOf($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public LootItemFunctionType getType() {
/*  45 */     return LootItemFunctions.SET_ATTRIBUTES;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<LootContextParam<?>> getReferencedContextParams() {
/*  50 */     return (Set<LootContextParam<?>>)this.modifiers.stream().flatMap($$0 -> $$0.amount.getReferencedContextParams().stream()).collect(ImmutableSet.toImmutableSet());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack run(ItemStack $$0, LootContext $$1) {
/*  55 */     RandomSource $$2 = $$1.getRandom();
/*  56 */     for (Modifier $$3 : this.modifiers) {
/*  57 */       UUID $$4 = $$3.id.orElseGet(UUID::randomUUID);
/*  58 */       EquipmentSlot $$5 = (EquipmentSlot)Util.getRandom($$3.slots, $$2);
/*  59 */       $$0.addAttributeModifier((Attribute)$$3.attribute.value(), new AttributeModifier($$4, $$3.name, $$3.amount.getFloat($$1), $$3.operation), $$5);
/*     */     } 
/*  61 */     return $$0;
/*     */   }
/*     */   
/*     */   public static class ModifierBuilder
/*     */   {
/*     */     private final String name;
/*     */     private final Holder<Attribute> attribute;
/*     */     private final AttributeModifier.Operation operation;
/*     */     private final NumberProvider amount;
/*  70 */     private Optional<UUID> id = Optional.empty();
/*  71 */     private final Set<EquipmentSlot> slots = EnumSet.noneOf(EquipmentSlot.class);
/*     */     
/*     */     public ModifierBuilder(String $$0, Holder<Attribute> $$1, AttributeModifier.Operation $$2, NumberProvider $$3) {
/*  74 */       this.name = $$0;
/*  75 */       this.attribute = $$1;
/*  76 */       this.operation = $$2;
/*  77 */       this.amount = $$3;
/*     */     }
/*     */     
/*     */     public ModifierBuilder forSlot(EquipmentSlot $$0) {
/*  81 */       this.slots.add($$0);
/*  82 */       return this;
/*     */     }
/*     */     
/*     */     public ModifierBuilder withUuid(UUID $$0) {
/*  86 */       this.id = Optional.of($$0);
/*  87 */       return this;
/*     */     }
/*     */     
/*     */     public SetAttributesFunction.Modifier build() {
/*  91 */       return new SetAttributesFunction.Modifier(this.name, this.attribute, this.operation, this.amount, List.copyOf(this.slots), this.id);
/*     */     } }
/*     */   public static class Builder extends LootItemConditionalFunction.Builder<Builder> { private final List<SetAttributesFunction.Modifier> modifiers;
/*     */     
/*     */     public Builder() {
/*  96 */       this.modifiers = Lists.newArrayList();
/*     */     }
/*     */     
/*     */     protected Builder getThis() {
/* 100 */       return this;
/*     */     }
/*     */     
/*     */     public Builder withModifier(SetAttributesFunction.ModifierBuilder $$0) {
/* 104 */       this.modifiers.add($$0.build());
/* 105 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public LootItemFunction build() {
/* 110 */       return new SetAttributesFunction(getConditions(), this.modifiers);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static ModifierBuilder modifier(String $$0, Holder<Attribute> $$1, AttributeModifier.Operation $$2, NumberProvider $$3) {
/* 115 */     return new ModifierBuilder($$0, $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   public static Builder setAttributes() {
/* 119 */     return new Builder();
/*     */   }
/*     */   private static final class Modifier extends Record { final String name; final Holder<Attribute> attribute; final AttributeModifier.Operation operation; final NumberProvider amount; final List<EquipmentSlot> slots; final Optional<UUID> id; private static final Codec<List<EquipmentSlot>> SLOTS_CODEC; public static final Codec<Modifier> CODEC;
/* 122 */     Modifier(String $$0, Holder<Attribute> $$1, AttributeModifier.Operation $$2, NumberProvider $$3, List<EquipmentSlot> $$4, Optional<UUID> $$5) { this.name = $$0; this.attribute = $$1; this.operation = $$2; this.amount = $$3; this.slots = $$4; this.id = $$5; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 122 */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier; } public String name() { return this.name; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #122	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;
/* 122 */       //   0	8	1	$$0	Ljava/lang/Object; } public Holder<Attribute> attribute() { return this.attribute; } public AttributeModifier.Operation operation() { return this.operation; } public NumberProvider amount() { return this.amount; } public List<EquipmentSlot> slots() { return this.slots; } public Optional<UUID> id() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/* 130 */       SLOTS_CODEC = ExtraCodecs.nonEmptyList(
/* 131 */           Codec.either((Codec)EquipmentSlot.CODEC, EquipmentSlot.CODEC.listOf()).xmap($$0 -> (List)$$0.map(List::of, Function.identity()), $$0 -> ($$0.size() == 1) ? Either.left($$0.get(0)) : Either.right($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 137 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(Modifier::name), (App)BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(Modifier::attribute), (App)AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(Modifier::operation), (App)NumberProviders.CODEC.fieldOf("amount").forGetter(Modifier::amount), (App)SLOTS_CODEC.fieldOf("slot").forGetter(Modifier::slots), (App)ExtraCodecs.strictOptionalField(UUIDUtil.STRING_CODEC, "id").forGetter(Modifier::id)).apply((Applicative)$$0, Modifier::new));
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetAttributesFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */