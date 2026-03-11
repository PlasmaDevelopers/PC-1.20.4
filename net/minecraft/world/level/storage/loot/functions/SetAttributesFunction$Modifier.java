/*     */ package net.minecraft.world.level.storage.loot.functions;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function6;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.ai.attributes.Attribute;
/*     */ import net.minecraft.world.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
/*     */ import net.minecraft.world.level.storage.loot.providers.number.NumberProviders;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ final class Modifier
/*     */   extends Record
/*     */ {
/*     */   final String name;
/*     */   final Holder<Attribute> attribute;
/*     */   final AttributeModifier.Operation operation;
/*     */   final NumberProvider amount;
/*     */   final List<EquipmentSlot> slots;
/*     */   final Optional<UUID> id;
/*     */   private static final Codec<List<EquipmentSlot>> SLOTS_CODEC;
/*     */   public static final Codec<Modifier> CODEC;
/*     */   
/*     */   public final String toString() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #122	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;
/*     */   }
/*     */   
/*     */   public final int hashCode() {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #122	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;
/*     */   }
/*     */   
/*     */   public final boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #122	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/SetAttributesFunction$Modifier;
/*     */     //   0	8	1	$$0	Ljava/lang/Object;
/*     */   }
/*     */   
/*     */   Modifier(String $$0, Holder<Attribute> $$1, AttributeModifier.Operation $$2, NumberProvider $$3, List<EquipmentSlot> $$4, Optional<UUID> $$5) {
/* 122 */     this.name = $$0; this.attribute = $$1; this.operation = $$2; this.amount = $$3; this.slots = $$4; this.id = $$5; } public String name() { return this.name; } public Holder<Attribute> attribute() { return this.attribute; } public AttributeModifier.Operation operation() { return this.operation; } public NumberProvider amount() { return this.amount; } public List<EquipmentSlot> slots() { return this.slots; } public Optional<UUID> id() { return this.id; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 130 */     SLOTS_CODEC = ExtraCodecs.nonEmptyList(
/* 131 */         Codec.either((Codec)EquipmentSlot.CODEC, EquipmentSlot.CODEC.listOf()).xmap($$0 -> (List)$$0.map(List::of, Function.identity()), $$0 -> ($$0.size() == 1) ? Either.left($$0.get(0)) : Either.right($$0)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(Modifier::name), (App)BuiltInRegistries.ATTRIBUTE.holderByNameCodec().fieldOf("attribute").forGetter(Modifier::attribute), (App)AttributeModifier.Operation.CODEC.fieldOf("operation").forGetter(Modifier::operation), (App)NumberProviders.CODEC.fieldOf("amount").forGetter(Modifier::amount), (App)SLOTS_CODEC.fieldOf("slot").forGetter(Modifier::slots), (App)ExtraCodecs.strictOptionalField(UUIDUtil.STRING_CODEC, "id").forGetter(Modifier::id)).apply((Applicative)$$0, Modifier::new));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\SetAttributesFunction$Modifier.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */