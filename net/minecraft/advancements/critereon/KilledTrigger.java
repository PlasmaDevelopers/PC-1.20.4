/*     */ package net.minecraft.advancements.critereon;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.advancements.Criterion;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.damagesource.DamageSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.storage.loot.LootContext;
/*     */ 
/*     */ public class KilledTrigger extends SimpleCriterionTrigger<KilledTrigger.TriggerInstance> {
/*     */   public Codec<TriggerInstance> codec() {
/*  18 */     return TriggerInstance.CODEC;
/*     */   }
/*     */   
/*     */   public void trigger(ServerPlayer $$0, Entity $$1, DamageSource $$2) {
/*  22 */     LootContext $$3 = EntityPredicate.createContext($$0, $$1);
/*  23 */     trigger($$0, $$3 -> $$3.matches($$0, $$1, $$2));
/*     */   }
/*     */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ContextAwarePredicate> entityPredicate; private final Optional<DamageSourcePredicate> killingBlow; public static final Codec<TriggerInstance> CODEC;
/*  26 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ContextAwarePredicate> $$1, Optional<DamageSourcePredicate> $$2) { this.player = $$0; this.entityPredicate = $$1; this.killingBlow = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  26 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #26	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/KilledTrigger$TriggerInstance;
/*  26 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ContextAwarePredicate> entityPredicate() { return this.entityPredicate; } public Optional<DamageSourcePredicate> killingBlow() { return this.killingBlow; }
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  31 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "entity").forGetter(TriggerInstance::entityPredicate), (App)ExtraCodecs.strictOptionalField(DamageSourcePredicate.CODEC, "killing_blow").forGetter(TriggerInstance::killingBlow)).apply((Applicative)$$0, TriggerInstance::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(Optional<EntityPredicate> $$0) {
/*  38 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(EntityPredicate.Builder $$0) {
/*  42 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity() {
/*  46 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(Optional<EntityPredicate> $$0, Optional<DamageSourcePredicate> $$1) {
/*  50 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), $$1));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(EntityPredicate.Builder $$0, Optional<DamageSourcePredicate> $$1) {
/*  54 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), $$1));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(Optional<EntityPredicate> $$0, DamageSourcePredicate.Builder $$1) {
/*  58 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), Optional.of($$1.build())));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntity(EntityPredicate.Builder $$0, DamageSourcePredicate.Builder $$1) {
/*  62 */       return CriteriaTriggers.PLAYER_KILLED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), Optional.of($$1.build())));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> playerKilledEntityNearSculkCatalyst() {
/*  66 */       return CriteriaTriggers.KILL_MOB_NEAR_SCULK_CATALYST.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(Optional<EntityPredicate> $$0) {
/*  70 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(EntityPredicate.Builder $$0) {
/*  74 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer() {
/*  78 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(Optional<EntityPredicate> $$0, Optional<DamageSourcePredicate> $$1) {
/*  82 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), $$1));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(EntityPredicate.Builder $$0, Optional<DamageSourcePredicate> $$1) {
/*  86 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), $$1));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(Optional<EntityPredicate> $$0, DamageSourcePredicate.Builder $$1) {
/*  90 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), Optional.of($$1.build())));
/*     */     }
/*     */     
/*     */     public static Criterion<TriggerInstance> entityKilledPlayer(EntityPredicate.Builder $$0, DamageSourcePredicate.Builder $$1) {
/*  94 */       return CriteriaTriggers.ENTITY_KILLED_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0)), Optional.of($$1.build())));
/*     */     }
/*     */     
/*     */     public boolean matches(ServerPlayer $$0, LootContext $$1, DamageSource $$2) {
/*  98 */       if (this.killingBlow.isPresent() && !((DamageSourcePredicate)this.killingBlow.get()).matches($$0, $$2)) {
/*  99 */         return false;
/*     */       }
/* 101 */       return (this.entityPredicate.isEmpty() || ((ContextAwarePredicate)this.entityPredicate.get()).matches($$1));
/*     */     }
/*     */ 
/*     */     
/*     */     public void validate(CriterionValidator $$0) {
/* 106 */       super.validate($$0);
/* 107 */       $$0.validateEntity(this.entityPredicate, ".entity");
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\KilledTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */