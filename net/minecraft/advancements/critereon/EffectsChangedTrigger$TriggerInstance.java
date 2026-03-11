/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<MobEffectsPredicate> effects;
/*    */   private final Optional<ContextAwarePredicate> source;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 26 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<MobEffectsPredicate> $$1, Optional<ContextAwarePredicate> $$2) { this.player = $$0; this.effects = $$1; this.source = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/EffectsChangedTrigger$TriggerInstance;
/* 26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<MobEffectsPredicate> effects() { return this.effects; } public Optional<ContextAwarePredicate> source() { return this.source; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(MobEffectsPredicate.CODEC, "effects").forGetter(TriggerInstance::effects), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "source").forGetter(TriggerInstance::source)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> hasEffects(MobEffectsPredicate.Builder $$0) {
/* 38 */     return CriteriaTriggers.EFFECTS_CHANGED.createCriterion(new TriggerInstance(Optional.empty(), $$0.build(), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> gotEffectsFrom(EntityPredicate.Builder $$0) {
/* 42 */     return CriteriaTriggers.EFFECTS_CHANGED.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.of(EntityPredicate.wrap($$0.build()))));
/*    */   }
/*    */   
/*    */   public boolean matches(ServerPlayer $$0, @Nullable LootContext $$1) {
/* 46 */     if (this.effects.isPresent() && !((MobEffectsPredicate)this.effects.get()).matches((LivingEntity)$$0)) {
/* 47 */       return false;
/*    */     }
/*    */     
/* 50 */     if (this.source.isPresent() && (
/* 51 */       $$1 == null || !((ContextAwarePredicate)this.source.get()).matches($$1))) {
/* 52 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 61 */     super.validate($$0);
/* 62 */     $$0.validateEntity(this.source, ".source");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EffectsChangedTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */