/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<DistancePredicate> distance;
/*    */   private final MinMaxBounds.Ints duration;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 23 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<DistancePredicate> $$1, MinMaxBounds.Ints $$2) { this.player = $$0; this.distance = $$1; this.duration = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #23	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/LevitationTrigger$TriggerInstance;
/* 23 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<DistancePredicate> distance() { return this.distance; } public MinMaxBounds.Ints duration() { return this.duration; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(DistancePredicate.CODEC, "distance").forGetter(TriggerInstance::distance), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "duration", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::duration)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> levitated(DistancePredicate $$0) {
/* 35 */     return CriteriaTriggers.LEVITATION.createCriterion(new TriggerInstance(Optional.empty(), Optional.of($$0), MinMaxBounds.Ints.ANY));
/*    */   }
/*    */   
/*    */   public boolean matches(ServerPlayer $$0, Vec3 $$1, int $$2) {
/* 39 */     if (this.distance.isPresent() && !((DistancePredicate)this.distance.get()).matches($$1.x, $$1.y, $$1.z, $$0.getX(), $$0.getY(), $$0.getZ())) {
/* 40 */       return false;
/*    */     }
/* 42 */     if (!this.duration.matches($$2)) {
/* 43 */       return false;
/*    */     }
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LevitationTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */