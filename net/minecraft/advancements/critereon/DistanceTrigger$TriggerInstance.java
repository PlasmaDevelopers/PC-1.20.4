/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<LocationPredicate> startPosition;
/*    */   private final Optional<DistancePredicate> distance;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 25 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<LocationPredicate> $$1, Optional<DistancePredicate> $$2) { this.player = $$0; this.startPosition = $$1; this.distance = $$2; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #25	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/DistanceTrigger$TriggerInstance;
/* 25 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<LocationPredicate> startPosition() { return this.startPosition; } public Optional<DistancePredicate> distance() { return this.distance; }
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 30 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(LocationPredicate.CODEC, "start_position").forGetter(TriggerInstance::startPosition), (App)ExtraCodecs.strictOptionalField(DistancePredicate.CODEC, "distance").forGetter(TriggerInstance::distance)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> fallFromHeight(EntityPredicate.Builder $$0, DistancePredicate $$1, LocationPredicate.Builder $$2) {
/* 37 */     return CriteriaTriggers.FALL_FROM_HEIGHT.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0)), Optional.of($$2.build()), Optional.of($$1)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> rideEntityInLava(EntityPredicate.Builder $$0, DistancePredicate $$1) {
/* 41 */     return CriteriaTriggers.RIDE_ENTITY_IN_LAVA_TRIGGER.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0)), Optional.empty(), Optional.of($$1)));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> travelledThroughNether(DistancePredicate $$0) {
/* 45 */     return CriteriaTriggers.NETHER_TRAVEL.createCriterion(new TriggerInstance(Optional.empty(), Optional.empty(), Optional.of($$0)));
/*    */   }
/*    */   
/*    */   public boolean matches(ServerLevel $$0, Vec3 $$1, Vec3 $$2) {
/* 49 */     if (this.startPosition.isPresent() && !((LocationPredicate)this.startPosition.get()).matches($$0, $$1.x, $$1.y, $$1.z)) {
/* 50 */       return false;
/*    */     }
/* 52 */     if (this.distance.isPresent() && !((DistancePredicate)this.distance.get()).matches($$1.x, $$1.y, $$1.z, $$2.x, $$2.y, $$2.z)) {
/* 53 */       return false;
/*    */     }
/* 55 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DistanceTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */