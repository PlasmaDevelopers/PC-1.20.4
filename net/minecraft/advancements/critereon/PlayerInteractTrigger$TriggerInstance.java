/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<ItemPredicate> item;
/*    */   private final Optional<ContextAwarePredicate> entity;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 26 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1, Optional<ContextAwarePredicate> $$2) { this.player = $$0; this.item = $$1; this.entity = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #26	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerInteractTrigger$TriggerInstance;
/* 26 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> item() { return this.item; } public Optional<ContextAwarePredicate> entity() { return this.entity; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 31 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "entity").forGetter(TriggerInstance::entity)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> itemUsedOnEntity(Optional<ContextAwarePredicate> $$0, ItemPredicate.Builder $$1, Optional<ContextAwarePredicate> $$2) {
/* 38 */     return CriteriaTriggers.PLAYER_INTERACTED_WITH_ENTITY.createCriterion(new TriggerInstance($$0, Optional.of($$1.build()), $$2));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> itemUsedOnEntity(ItemPredicate.Builder $$0, Optional<ContextAwarePredicate> $$1) {
/* 42 */     return itemUsedOnEntity(Optional.empty(), $$0, $$1);
/*    */   }
/*    */   
/*    */   public boolean matches(ItemStack $$0, LootContext $$1) {
/* 46 */     if (this.item.isPresent() && !((ItemPredicate)this.item.get()).matches($$0)) {
/* 47 */       return false;
/*    */     }
/*    */     
/* 50 */     return (this.entity.isEmpty() || ((ContextAwarePredicate)this.entity.get()).matches($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void validate(CriterionValidator $$0) {
/* 55 */     super.validate($$0);
/* 56 */     $$0.validateEntity(this.entity, ".entity");
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerInteractTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */