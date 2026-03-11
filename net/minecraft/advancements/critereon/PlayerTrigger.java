/*    */ package net.minecraft.advancements.critereon;
/*    */ 
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
/*    */ import net.minecraft.world.item.Item;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public class PlayerTrigger extends SimpleCriterionTrigger<PlayerTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 19 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0) {
/* 23 */     trigger($$0, $$0 -> true);
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; public static final Codec<TriggerInstance> CODEC;
/* 26 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0) { this.player = $$0; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 26 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; }
/*    */     public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance; }
/*    */     public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #26	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/PlayerTrigger$TriggerInstance;
/*    */       //   0	8	1	$$0	Ljava/lang/Object; } static {
/* 29 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> located(LocationPredicate.Builder $$0) {
/* 34 */       return CriteriaTriggers.LOCATION.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap(EntityPredicate.Builder.entity().located($$0)))));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> located(EntityPredicate.Builder $$0) {
/* 38 */       return CriteriaTriggers.LOCATION.createCriterion(new TriggerInstance(Optional.of(EntityPredicate.wrap($$0.build()))));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> located(Optional<EntityPredicate> $$0) {
/* 42 */       return CriteriaTriggers.LOCATION.createCriterion(new TriggerInstance(EntityPredicate.wrap($$0)));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> sleptInBed() {
/* 46 */       return CriteriaTriggers.SLEPT_IN_BED.createCriterion(new TriggerInstance(Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> raidWon() {
/* 50 */       return CriteriaTriggers.RAID_WIN.createCriterion(new TriggerInstance(Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> avoidVibration() {
/* 54 */       return CriteriaTriggers.AVOID_VIBRATION.createCriterion(new TriggerInstance(Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> tick() {
/* 58 */       return CriteriaTriggers.TICK.createCriterion(new TriggerInstance(Optional.empty()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> walkOnBlockWithEquipment(Block $$0, Item $$1) {
/* 62 */       return located(EntityPredicate.Builder.entity()
/* 63 */           .equipment(
/* 64 */             EntityEquipmentPredicate.Builder.equipment().feet(ItemPredicate.Builder.item().of(new ItemLike[] { (ItemLike)$$1
/* 65 */                 }))).steppingOn(
/* 66 */             LocationPredicate.Builder.location().setBlock(BlockPredicate.Builder.block().of(new Block[] { $$0 }))));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\PlayerTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */