/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.alchemy.Potion;
/*    */ 
/*    */ public class BrewedPotionTrigger extends SimpleCriterionTrigger<BrewedPotionTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 18 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, Holder<Potion> $$1) {
/* 22 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<Holder<Potion>> potion; public static final Codec<TriggerInstance> CODEC;
/* 25 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<Holder<Potion>> $$1) { this.player = $$0; this.potion = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 25 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #25	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/BrewedPotionTrigger$TriggerInstance;
/* 25 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Holder<Potion>> potion() { return this.potion; }
/*    */ 
/*    */     
/*    */     static {
/* 29 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(BuiltInRegistries.POTION.holderByNameCodec(), "potion").forGetter(TriggerInstance::potion)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> brewedPotion() {
/* 35 */       return CriteriaTriggers.BREWED_POTION.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty()));
/*    */     }
/*    */     
/*    */     public boolean matches(Holder<Potion> $$0) {
/* 39 */       if (this.potion.isPresent() && !((Holder)this.potion.get()).equals($$0)) {
/* 40 */         return false;
/*    */       }
/* 42 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\BrewedPotionTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */