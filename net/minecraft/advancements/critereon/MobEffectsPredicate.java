/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.effect.MobEffect;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.LivingEntity;
/*    */ 
/*    */ public final class MobEffectsPredicate extends Record {
/*    */   private final Map<Holder<MobEffect>, MobEffectInstancePredicate> effectMap;
/*    */   
/* 18 */   public MobEffectsPredicate(Map<Holder<MobEffect>, MobEffectInstancePredicate> $$0) { this.effectMap = $$0; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 18 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate; } public Map<Holder<MobEffect>, MobEffectInstancePredicate> effectMap() { return this.effectMap; }
/*    */   public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate; } public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/* 21 */   } public static final Codec<MobEffectsPredicate> CODEC = Codec.unboundedMap(BuiltInRegistries.MOB_EFFECT.holderByNameCodec(), MobEffectInstancePredicate.CODEC).xmap(MobEffectsPredicate::new, MobEffectsPredicate::effectMap);
/*    */   
/*    */   public boolean matches(Entity $$0) {
/* 24 */     if ($$0 instanceof LivingEntity) { LivingEntity $$1 = (LivingEntity)$$0; if (matches($$1.getActiveEffectsMap())); }  return false;
/*    */   }
/*    */   
/*    */   public boolean matches(LivingEntity $$0) {
/* 28 */     return matches($$0.getActiveEffectsMap());
/*    */   }
/*    */   
/*    */   public boolean matches(Map<MobEffect, MobEffectInstance> $$0) {
/* 32 */     for (Map.Entry<Holder<MobEffect>, MobEffectInstancePredicate> $$1 : this.effectMap.entrySet()) {
/* 33 */       MobEffectInstance $$2 = $$0.get(((Holder)$$1.getKey()).value());
/* 34 */       if (!((MobEffectInstancePredicate)$$1.getValue()).matches($$2)) {
/* 35 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 39 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 43 */     private final ImmutableMap.Builder<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate> effectMap = ImmutableMap.builder();
/*    */     
/*    */     public static Builder effects() {
/* 46 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder and(MobEffect $$0) {
/* 50 */       this.effectMap.put($$0.builtInRegistryHolder(), new MobEffectsPredicate.MobEffectInstancePredicate());
/* 51 */       return this;
/*    */     }
/*    */     
/*    */     public Builder and(MobEffect $$0, MobEffectsPredicate.MobEffectInstancePredicate $$1) {
/* 55 */       this.effectMap.put($$0.builtInRegistryHolder(), $$1);
/* 56 */       return this;
/*    */     }
/*    */     
/*    */     public Optional<MobEffectsPredicate> build() {
/* 60 */       return Optional.of(new MobEffectsPredicate((Map<Holder<MobEffect>, MobEffectsPredicate.MobEffectInstancePredicate>)this.effectMap.build()));
/*    */     } }
/*    */   public static final class MobEffectInstancePredicate extends Record { private final MinMaxBounds.Ints amplifier; private final MinMaxBounds.Ints duration; private final Optional<Boolean> ambient; private final Optional<Boolean> visible; public static final Codec<MobEffectInstancePredicate> CODEC;
/*    */     
/* 64 */     public MobEffectInstancePredicate(MinMaxBounds.Ints $$0, MinMaxBounds.Ints $$1, Optional<Boolean> $$2, Optional<Boolean> $$3) { this.amplifier = $$0; this.duration = $$1; this.ambient = $$2; this.visible = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #64	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #64	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #64	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/MobEffectsPredicate$MobEffectInstancePredicate;
/* 64 */       //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Ints amplifier() { return this.amplifier; } public MinMaxBounds.Ints duration() { return this.duration; } public Optional<Boolean> ambient() { return this.ambient; } public Optional<Boolean> visible() { return this.visible; }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 70 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "amplifier", MinMaxBounds.Ints.ANY).forGetter(MobEffectInstancePredicate::amplifier), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "duration", MinMaxBounds.Ints.ANY).forGetter(MobEffectInstancePredicate::duration), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "ambient").forGetter(MobEffectInstancePredicate::ambient), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "visible").forGetter(MobEffectInstancePredicate::visible)).apply((Applicative)$$0, MobEffectInstancePredicate::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public MobEffectInstancePredicate() {
/* 78 */       this(MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, Optional.empty(), Optional.empty());
/*    */     }
/*    */     
/*    */     public boolean matches(@Nullable MobEffectInstance $$0) {
/* 82 */       if ($$0 == null) {
/* 83 */         return false;
/*    */       }
/* 85 */       if (!this.amplifier.matches($$0.getAmplifier())) {
/* 86 */         return false;
/*    */       }
/* 88 */       if (!this.duration.matches($$0.getDuration())) {
/* 89 */         return false;
/*    */       }
/* 91 */       if (this.ambient.isPresent() && ((Boolean)this.ambient.get()).booleanValue() != $$0.isAmbient()) {
/* 92 */         return false;
/*    */       }
/* 94 */       if (this.visible.isPresent() && ((Boolean)this.visible.get()).booleanValue() != $$0.isVisible()) {
/* 95 */         return false;
/*    */       }
/* 97 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\MobEffectsPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */