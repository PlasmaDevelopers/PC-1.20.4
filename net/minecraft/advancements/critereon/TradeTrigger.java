/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.npc.AbstractVillager;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public class TradeTrigger extends SimpleCriterionTrigger<TradeTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 18 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, AbstractVillager $$1, ItemStack $$2) {
/* 22 */     LootContext $$3 = EntityPredicate.createContext($$0, (Entity)$$1);
/* 23 */     trigger($$0, $$2 -> $$2.matches($$0, $$1));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ContextAwarePredicate> villager; private final Optional<ItemPredicate> item; public static final Codec<TriggerInstance> CODEC;
/* 26 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ContextAwarePredicate> $$1, Optional<ItemPredicate> $$2) { this.player = $$0; this.villager = $$1; this.item = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 26 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/TradeTrigger$TriggerInstance;
/* 26 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ContextAwarePredicate> villager() { return this.villager; } public Optional<ItemPredicate> item() { return this.item; }
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 31 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "villager").forGetter(TriggerInstance::villager), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> tradedWithVillager() {
/* 38 */       return CriteriaTriggers.TRADE.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> tradedWithVillager(EntityPredicate.Builder $$0) {
/* 42 */       return CriteriaTriggers.TRADE.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0)), Optional.empty(), Optional.empty()));
/*    */     }
/*    */     
/*    */     public boolean matches(LootContext $$0, ItemStack $$1) {
/* 46 */       if (this.villager.isPresent() && !((ContextAwarePredicate)this.villager.get()).matches($$0)) {
/* 47 */         return false;
/*    */       }
/* 49 */       if (this.item.isPresent() && !((ItemPredicate)this.item.get()).matches($$1)) {
/* 50 */         return false;
/*    */       }
/* 52 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void validate(CriterionValidator $$0) {
/* 57 */       super.validate($$0);
/* 58 */       $$0.validateEntity(this.villager, ".villager");
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\TradeTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */