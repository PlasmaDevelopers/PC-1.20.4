/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Collection;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.entity.projectile.FishingHook;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
/*    */ 
/*    */ public class FishingRodHookedTrigger extends SimpleCriterionTrigger<FishingRodHookedTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 22 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, ItemStack $$1, FishingHook $$2, Collection<ItemStack> $$3) {
/* 26 */     LootContext $$4 = EntityPredicate.createContext($$0, ($$2.getHookedIn() != null) ? $$2.getHookedIn() : (Entity)$$2);
/* 27 */     trigger($$0, $$3 -> $$3.matches($$0, $$1, $$2));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final Optional<ItemPredicate> rod; private final Optional<ContextAwarePredicate> entity; private final Optional<ItemPredicate> item; public static final Codec<TriggerInstance> CODEC;
/* 30 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, Optional<ItemPredicate> $$1, Optional<ContextAwarePredicate> $$2, Optional<ItemPredicate> $$3) { this.player = $$0; this.rod = $$1; this.entity = $$2; this.item = $$3; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 30 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #30	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/FishingRodHookedTrigger$TriggerInstance;
/* 30 */       //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ItemPredicate> rod() { return this.rod; } public Optional<ContextAwarePredicate> entity() { return this.entity; } public Optional<ItemPredicate> item() { return this.item; }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 36 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "rod").forGetter(TriggerInstance::rod), (App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "entity").forGetter(TriggerInstance::entity), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC, "item").forGetter(TriggerInstance::item)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> fishedItem(Optional<ItemPredicate> $$0, Optional<EntityPredicate> $$1, Optional<ItemPredicate> $$2) {
/* 44 */       return CriteriaTriggers.FISHING_ROD_HOOKED.createCriterion(new TriggerInstance(Optional.empty(), $$0, EntityPredicate.wrap($$1), $$2));
/*    */     }
/*    */     
/*    */     public boolean matches(ItemStack $$0, LootContext $$1, Collection<ItemStack> $$2) {
/* 48 */       if (this.rod.isPresent() && !((ItemPredicate)this.rod.get()).matches($$0)) {
/* 49 */         return false;
/*    */       }
/* 51 */       if (this.entity.isPresent() && !((ContextAwarePredicate)this.entity.get()).matches($$1)) {
/* 52 */         return false;
/*    */       }
/* 54 */       if (this.item.isPresent()) {
/* 55 */         boolean $$3 = false;
/*    */         
/* 57 */         Entity $$4 = (Entity)$$1.getParamOrNull(LootContextParams.THIS_ENTITY);
/* 58 */         if ($$4 instanceof ItemEntity) { ItemEntity $$5 = (ItemEntity)$$4;
/* 59 */           if (((ItemPredicate)this.item.get()).matches($$5.getItem())) {
/* 60 */             $$3 = true;
/*    */           } }
/*    */         
/* 63 */         for (ItemStack $$6 : $$2) {
/* 64 */           if (((ItemPredicate)this.item.get()).matches($$6)) {
/* 65 */             $$3 = true;
/*    */             break;
/*    */           } 
/*    */         } 
/* 69 */         if (!$$3) {
/* 70 */           return false;
/*    */         }
/*    */       } 
/* 73 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public void validate(CriterionValidator $$0) {
/* 78 */       super.validate($$0);
/* 79 */       $$0.validateEntity(this.entity, ".entity");
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\FishingRodHookedTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */