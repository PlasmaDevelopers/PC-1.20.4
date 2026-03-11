/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.ItemLike;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<ItemPredicate> item;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #24	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #24	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 24 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1) { this.player = $$0; this.item = $$1; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #24	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/ShotCrossbowTrigger$TriggerInstance;
/* 24 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> item() { return this.item; }
/*    */ 
/*    */   
/*    */   static {
/* 28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> shotCrossbow(Optional<ItemPredicate> $$0) {
/* 34 */     return CriteriaTriggers.SHOT_CROSSBOW.createCriterion(new TriggerInstance(Optional.empty(), $$0));
/*    */   }
/*    */   
/*    */   public static Criterion<TriggerInstance> shotCrossbow(ItemLike $$0) {
/* 38 */     return CriteriaTriggers.SHOT_CROSSBOW.createCriterion(new TriggerInstance(Optional.empty(), Optional.of(ItemPredicate.Builder.item().of(new ItemLike[] { $$0 }).build())));
/*    */   }
/*    */   
/*    */   public boolean matches(ItemStack $$0) {
/* 42 */     return (this.item.isEmpty() || ((ItemPredicate)this.item.get()).matches($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\ShotCrossbowTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */