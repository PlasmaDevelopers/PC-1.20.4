/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<DamagePredicate> damage;
/*    */   private final Optional<ContextAwarePredicate> entity;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 26 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<DamagePredicate> $$1, Optional<ContextAwarePredicate> $$2) { this.player = $$0; this.damage = $$1; this.entity = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerHurtEntityTrigger$TriggerInstance;
/* 26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<DamagePredicate> damage() { return this.damage; } public Optional<ContextAwarePredicate> entity() { return this.entity; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(DamagePredicate.CODEC, "damage").forGetter(TriggerInstance::damage), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "entity").forGetter(TriggerInstance::entity)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntity() {
/* 38 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntityWithDamage(Optional<DamagePredicate> $$0) {
/* 42 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), $$0, Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntityWithDamage(DamagePredicate.Builder $$0) {
/* 46 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0.build()), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntity(Optional<EntityPredicate> $$0) {
/* 50 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), EntityPredicate.wrap($$0)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntity(Optional<DamagePredicate> $$0, Optional<EntityPredicate> $$1) {
/* 54 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), $$0, EntityPredicate.wrap($$1)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> playerHurtEntity(DamagePredicate.Builder $$0, Optional<EntityPredicate> $$1) {
/* 58 */     return CriteriaTriggers.PLAYER_HURT_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0.build()), EntityPredicate.wrap($$1)));
/*    */   }
/*    */   
/*    */   public boolean matches(ServerPlayer $$0, LootContext $$1, DamageSource $$2, float $$3, float $$4, boolean $$5) {
/* 62 */     if (this.damage.isPresent() && !((DamagePredicate)this.damage.get()).matches($$0, $$2, $$3, $$4, $$5)) {
/* 63 */       return false;
/*    */     }
/* 65 */     if (this.entity.isPresent() && !((ContextAwarePredicate)this.entity.get()).matches($$1)) {
/* 66 */       return false;
/*    */     }
/* 68 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 73 */     super.validate($$0);
/* 74 */     $$0.validateEntity(this.entity, ".entity");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerHurtEntityTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */