/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ public class UsedEnderEyeTrigger extends SimpleCriterionTrigger<UsedEnderEyeTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 14 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, BlockPos $$1) {
/* 18 */     double $$2 = $$0.getX() - $$1.getX();
/* 19 */     double $$3 = $$0.getZ() - $$1.getZ();
/* 20 */     double $$4 = $$2 * $$2 + $$3 * $$3;
/* 21 */     trigger($$0, $$1 -> $$1.matches($$0));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final MinMaxBounds.Doubles distance; public static final Codec<TriggerInstance> CODEC;
/* 24 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, MinMaxBounds.Doubles $$1) { this.player = $$0; this.distance = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #24	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 24 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #24	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #24	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/UsedEnderEyeTrigger$TriggerInstance;
/* 24 */       //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Doubles distance() { return this.distance; }
/*    */ 
/*    */     
/*    */     static {
/* 28 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "distance", MinMaxBounds.Doubles.ANY).forGetter(TriggerInstance::distance)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public boolean matches(double $$0) {
/* 34 */       return this.distance.matchesSqr($$0);
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\UsedEnderEyeTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */