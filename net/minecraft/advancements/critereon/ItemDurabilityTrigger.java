/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ItemDurabilityTrigger extends SimpleCriterionTrigger<ItemDurabilityTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 16 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, ItemStack $$1, int $$2) {
/* 20 */     trigger($$0, $$2 -> $$2.matches($$0, $$1));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ItemPredicate> item; private final MinMaxBounds.Ints durability; private final MinMaxBounds.Ints delta; public static final Codec<TriggerInstance> CODEC;
/* 23 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1, MinMaxBounds.Ints $$2, MinMaxBounds.Ints $$3) { this.player = $$0; this.item = $$1; this.durability = $$2; this.delta = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 23 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/ItemDurabilityTrigger$TriggerInstance;
/* 23 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> item() { return this.item; } public MinMaxBounds.Ints durability() { return this.durability; } public MinMaxBounds.Ints delta() { return this.delta; }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 29 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "durability", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::durability), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "delta", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::delta)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> changedDurability(Optional<ItemPredicate> $$0, MinMaxBounds.Ints $$1) {
/* 37 */       return changedDurability(Optional.empty(), $$0, $$1);
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> changedDurability(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1, MinMaxBounds.Ints $$2) {
/* 41 */       return CriteriaTriggers.ITEM_DURABILITY_CHANGED.createCriterion(new TriggerInstance($$0, $$1, $$2, MinMaxBounds.Ints.ANY));
/*    */     }
/*    */     
/*    */     public boolean matches(ItemStack $$0, int $$1) {
/* 45 */       if (this.item.isPresent() && !((ItemPredicate)this.item.get()).matches($$0)) {
/* 46 */         return false;
/*    */       }
/* 48 */       if (!this.durability.matches($$0.getMaxDamage() - $$1)) {
/* 49 */         return false;
/*    */       }
/* 51 */       if (!this.delta.matches($$0.getDamageValue() - $$1)) {
/* 52 */         return false;
/*    */       }
/* 54 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ItemDurabilityTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */