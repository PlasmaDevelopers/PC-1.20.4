/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<ContextAwarePredicate> parent;
/*    */   private final Optional<ContextAwarePredicate> partner;
/*    */   private final Optional<ContextAwarePredicate> child;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #30	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/BredAnimalsTrigger$TriggerInstance;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/* 30 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ContextAwarePredicate> $$1, Optional<ContextAwarePredicate> $$2, Optional<ContextAwarePredicate> $$3) { this.player = $$0; this.parent = $$1; this.partner = $$2; this.child = $$3; } public Optional<ContextAwarePredicate> player() { return this.player; } public Optional<ContextAwarePredicate> parent() { return this.parent; } public Optional<ContextAwarePredicate> partner() { return this.partner; } public Optional<ContextAwarePredicate> child() { return this.child; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 36 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "parent").forGetter(TriggerInstance::parent), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "partner").forGetter(TriggerInstance::partner), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "child").forGetter(TriggerInstance::child)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> bredAnimals() {
/* 44 */     return CriteriaTriggers.BRED_ANIMALS.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> bredAnimals(EntityPredicate.Builder $$0) {
/* 48 */     return CriteriaTriggers.BRED_ANIMALS.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(EntityPredicate.wrap($$0))));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> bredAnimals(Optional<EntityPredicate> $$0, Optional<EntityPredicate> $$1, Optional<EntityPredicate> $$2) {
/* 52 */     return CriteriaTriggers.BRED_ANIMALS.createCriterion(new TriggerInstance(Optional.empty(), EntityPredicate.wrap($$0), EntityPredicate.wrap($$1), EntityPredicate.wrap($$2)));
/*    */   }
/*    */   
/*    */   public boolean matches(LootContext $$0, LootContext $$1, @Nullable LootContext $$2) {
/* 56 */     if (this.child.isPresent() && ($$2 == null || !((ContextAwarePredicate)this.child.get()).matches($$2))) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     return ((matches(this.parent, $$0) && matches(this.partner, $$1)) || (matches(this.parent, $$1) && matches(this.partner, $$0)));
/*    */   }
/*    */   
/*    */   private static boolean matches(Optional<ContextAwarePredicate> $$0, LootContext $$1) {
/* 64 */     return ($$0.isEmpty() || ((ContextAwarePredicate)$$0.get()).matches($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 69 */     super.validate($$0);
/* 70 */     $$0.validateEntity(this.parent, ".parent");
/* 71 */     $$0.validateEntity(this.partner, ".partner");
/* 72 */     $$0.validateEntity(this.child, ".child");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\BredAnimalsTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */