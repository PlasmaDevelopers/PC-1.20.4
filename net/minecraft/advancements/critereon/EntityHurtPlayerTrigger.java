/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.damagesource.DamageSource;
/*    */ 
/*    */ public class EntityHurtPlayerTrigger extends SimpleCriterionTrigger<EntityHurtPlayerTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 16 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, DamageSource $$1, float $$2, float $$3, boolean $$4) {
/* 20 */     trigger($$0, $$5 -> $$5.matches($$0, $$1, $$2, $$3, $$4));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<DamagePredicate> damage; public static final Codec<TriggerInstance> CODEC;
/* 23 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<DamagePredicate> $$1) { this.player = $$0; this.damage = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 23 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/EntityHurtPlayerTrigger$TriggerInstance;
/* 23 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<DamagePredicate> damage() { return this.damage; }
/*    */ 
/*    */     
/*    */     static {
/* 27 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(DamagePredicate.CODEC, "damage").forGetter(TriggerInstance::damage)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> entityHurtPlayer() {
/* 33 */       return CriteriaTriggers.ENTITY_HURT_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> entityHurtPlayer(DamagePredicate $$0) {
/* 37 */       return CriteriaTriggers.ENTITY_HURT_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0)));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> entityHurtPlayer(DamagePredicate.Builder $$0) {
/* 41 */       return CriteriaTriggers.ENTITY_HURT_PLAYER.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0.build())));
/*    */     }
/*    */     
/*    */     public boolean matches(ServerPlayer $$0, DamageSource $$1, float $$2, float $$3, boolean $$4) {
/* 45 */       if (this.damage.isPresent() && !((DamagePredicate)this.damage.get()).matches($$0, $$1, $$2, $$3, $$4)) {
/* 46 */         return false;
/*    */       }
/* 48 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityHurtPlayerTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */