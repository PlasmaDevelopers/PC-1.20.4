/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public class KilledByCrossbowTrigger extends SimpleCriterionTrigger<KilledByCrossbowTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 24 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, Collection<Entity> $$1) {
/* 28 */     List<LootContext> $$2 = Lists.newArrayList();
/* 29 */     Set<EntityType<?>> $$3 = Sets.newHashSet();
/* 30 */     for (Entity $$4 : $$1) {
/* 31 */       $$3.add($$4.getType());
/* 32 */       $$2.add(EntityPredicate.createContext($$0, $$4));
/*    */     } 
/*    */     
/* 35 */     trigger($$0, $$2 -> $$2.matches($$0, $$1.size()));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final List<ContextAwarePredicate> victims; private final MinMaxBounds.Ints uniqueEntityTypes; public static final Codec<TriggerInstance> CODEC;
/* 38 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, List<ContextAwarePredicate> $$1, MinMaxBounds.Ints $$2) { this.player = $$0; this.victims = $$1; this.uniqueEntityTypes = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #38	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 38 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #38	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #38	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/KilledByCrossbowTrigger$TriggerInstance;
/* 38 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<ContextAwarePredicate> victims() { return this.victims; } public MinMaxBounds.Ints uniqueEntityTypes() { return this.uniqueEntityTypes; }
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 43 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC.listOf(), "victims", List.of()).forGetter(TriggerInstance::victims), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "unique_entity_types", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::uniqueEntityTypes)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> crossbowKilled(EntityPredicate.Builder... $$0) {
/* 50 */       return CriteriaTriggers.KILLED_BY_CROSSBOW.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), MinMaxBounds.Ints.ANY));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> crossbowKilled(MinMaxBounds.Ints $$0) {
/* 54 */       return CriteriaTriggers.KILLED_BY_CROSSBOW.createCriterion(new TriggerInstance(Optional.empty(), List.of(), $$0));
/*    */     }
/*    */     
/*    */     public boolean matches(Collection<LootContext> $$0, int $$1) {
/* 58 */       if (!this.victims.isEmpty()) {
/* 59 */         List<LootContext> $$2 = Lists.newArrayList($$0);
/* 60 */         for (ContextAwarePredicate $$3 : this.victims) {
/* 61 */           boolean $$4 = false;
/* 62 */           for (Iterator<LootContext> $$5 = $$2.iterator(); $$5.hasNext(); ) {
/* 63 */             LootContext $$6 = $$5.next();
/* 64 */             if ($$3.matches($$6)) {
/* 65 */               $$5.remove();
/* 66 */               $$4 = true;
/*    */               
/*    */               break;
/*    */             } 
/*    */           } 
/* 71 */           if (!$$4) {
/* 72 */             return false;
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 77 */       return this.uniqueEntityTypes.matches($$1);
/*    */     }
/*    */ 
/*    */     
/*    */     public void validate(CriterionValidator $$0) {
/* 82 */       super.validate($$0);
/* 83 */       $$0.validateEntities(this.victims, ".victims");
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\KilledByCrossbowTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */