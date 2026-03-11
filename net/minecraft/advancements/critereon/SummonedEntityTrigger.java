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
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public class SummonedEntityTrigger extends SimpleCriterionTrigger<SummonedEntityTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 17 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, Entity $$1) {
/* 21 */     LootContext $$2 = EntityPredicate.createContext($$0, $$1);
/* 22 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ContextAwarePredicate> entity; public static final Codec<TriggerInstance> CODEC;
/* 25 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ContextAwarePredicate> $$1) { this.player = $$0; this.entity = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 25 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/SummonedEntityTrigger$TriggerInstance;
/* 25 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ContextAwarePredicate> entity() { return this.entity; }
/*    */ 
/*    */     
/*    */     static {
/* 29 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "entity").forGetter(TriggerInstance::entity)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> summonedEntity(EntityPredicate.Builder $$0) {
/* 35 */       return CriteriaTriggers.SUMMONED_ENTITY.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(EntityPredicate.wrap($$0))));
/*    */     }
/*    */     
/*    */     public boolean matches(LootContext $$0) {
/* 39 */       return (this.entity.isEmpty() || ((ContextAwarePredicate)this.entity.get()).matches($$0));
/*    */     }
/*    */ 
/*    */     
/*    */     public void validate(CriterionValidator $$0) {
/* 44 */       super.validate($$0);
/* 45 */       $$0.validateEntity(this.entity, ".entity");
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\SummonedEntityTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */