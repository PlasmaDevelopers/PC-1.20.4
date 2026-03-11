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
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class UsingItemTrigger extends SimpleCriterionTrigger<UsingItemTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 16 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, ItemStack $$1) {
/* 20 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ItemPredicate> item; public static final Codec<TriggerInstance> CODEC;
/* 23 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1) { this.player = $$0; this.item = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 23 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #23	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/UsingItemTrigger$TriggerInstance;
/* 23 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> item() { return this.item; }
/*    */ 
/*    */     
/*    */     static {
/* 27 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> lookingAt(EntityPredicate.Builder $$0, ItemPredicate.Builder $$1) {
/* 33 */       return CriteriaTriggers.USING_ITEM.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0)), Optional.of($$1.build())));
/*    */     }
/*    */     
/*    */     public boolean matches(ItemStack $$0) {
/* 37 */       if (this.item.isPresent() && !((ItemPredicate)this.item.get()).matches($$0)) {
/* 38 */         return false;
/*    */       }
/* 40 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\UsingItemTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */