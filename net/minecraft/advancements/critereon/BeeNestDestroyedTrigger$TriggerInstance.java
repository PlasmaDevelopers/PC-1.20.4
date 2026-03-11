/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ 
/*    */ public final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance {
/*    */   private final Optional<ContextAwarePredicate> player;
/*    */   private final Optional<Holder<Block>> block;
/*    */   private final Optional<ItemPredicate> item;
/*    */   private final MinMaxBounds.Ints beesInside;
/*    */   public static final Codec<TriggerInstance> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;
/*    */   }
/*    */   
/* 27 */   public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<Holder<Block>> $$1, Optional<ItemPredicate> $$2, MinMaxBounds.Ints $$3) { this.player = $$0; this.block = $$1; this.item = $$2; this.beesInside = $$3; } public Optional<ContextAwarePredicate> player() { return this.player; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/BeeNestDestroyedTrigger$TriggerInstance;
/* 27 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Holder<Block>> block() { return this.block; } public Optional<ItemPredicate> item() { return this.item; } public MinMaxBounds.Ints beesInside() { return this.beesInside; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 33 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(BuiltInRegistries.BLOCK.holderByNameCodec(), "block").forGetter(TriggerInstance::block), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Ints.CODEC, "num_bees_inside", MinMaxBounds.Ints.ANY).forGetter(TriggerInstance::beesInside)).apply((Applicative)$$0, TriggerInstance::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Criterion<TriggerInstance> destroyedBeeNest(Block $$0, ItemPredicate.Builder $$1, MinMaxBounds.Ints $$2) {
/* 41 */     return CriteriaTriggers.BEE_NEST_DESTROYED.createCriterion(new TriggerInstance(Optional.empty(), (Optional)Optional.of($$0.builtInRegistryHolder()), Optional.of($$1.build()), $$2));
/*    */   }
/*    */   
/*    */   public boolean matches(BlockState $$0, ItemStack $$1, int $$2) {
/* 45 */     if (this.block.isPresent() && !$$0.is(this.block.get())) {
/* 46 */       return false;
/*    */     }
/* 48 */     if (this.item.isPresent() && !((ItemPredicate)this.item.get()).matches($$1)) {
/* 49 */       return false;
/*    */     }
/* 51 */     return this.beesInside.matches($$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\BeeNestDestroyedTrigger$TriggerInstance.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */