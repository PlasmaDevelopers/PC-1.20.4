/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class StartRidingTrigger extends SimpleCriterionTrigger<StartRidingTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 15 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0) {
/* 19 */     trigger($$0, $$0 -> true);
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; public static final Codec<TriggerInstance> CODEC;
/* 22 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0) { this.player = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 22 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #22	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/StartRidingTrigger$TriggerInstance;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 25 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> playerStartsRiding(EntityPredicate.Builder $$0) {
/* 30 */       return CriteriaTriggers.START_RIDING_TRIGGER.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0))));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StartRidingTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */