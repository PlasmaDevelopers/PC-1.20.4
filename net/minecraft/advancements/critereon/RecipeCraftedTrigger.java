/*    */ package net.minecraft.advancements.critereon;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.advancements.CriteriaTriggers;
/*    */ import net.minecraft.advancements.Criterion;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class RecipeCraftedTrigger extends SimpleCriterionTrigger<RecipeCraftedTrigger.TriggerInstance> {
/*    */   public Codec<TriggerInstance> codec() {
/* 20 */     return TriggerInstance.CODEC;
/*    */   }
/*    */   
/*    */   public void trigger(ServerPlayer $$0, ResourceLocation $$1, List<ItemStack> $$2) {
/* 24 */     trigger($$0, $$2 -> $$2.matches($$0, $$1));
/*    */   }
/*    */   public static final class TriggerInstance extends Record implements SimpleCriterionTrigger.SimpleInstance { private final Optional<ContextAwarePredicate> player; private final ResourceLocation recipeId; private final List<ItemPredicate> ingredients; public static final Codec<TriggerInstance> CODEC;
/* 27 */     public TriggerInstance(Optional<ContextAwarePredicate> $$0, ResourceLocation $$1, List<ItemPredicate> $$2) { this.player = $$0; this.recipeId = $$1; this.ingredients = $$2; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 27 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance; } public Optional<ContextAwarePredicate> player() { return this.player; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #27	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/RecipeCraftedTrigger$TriggerInstance;
/* 27 */       //   0	8	1	$$0	Ljava/lang/Object; } public ResourceLocation recipeId() { return this.recipeId; } public List<ItemPredicate> ingredients() { return this.ingredients; }
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/* 32 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(EntityPredicate.ADVANCEMENT_CODEC, "player").forGetter(TriggerInstance::player), (App)ResourceLocation.CODEC.fieldOf("recipe_id").forGetter(TriggerInstance::recipeId), (App)ExtraCodecs.strictOptionalField(ItemPredicate.CODEC.listOf(), "ingredients", List.of()).forGetter(TriggerInstance::ingredients)).apply((Applicative)$$0, TriggerInstance::new));
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Criterion<TriggerInstance> craftedItem(ResourceLocation $$0, List<ItemPredicate.Builder> $$1) {
/* 39 */       return CriteriaTriggers.RECIPE_CRAFTED.createCriterion(new TriggerInstance(Optional.empty(), $$0, $$1.stream().map(ItemPredicate.Builder::build).toList()));
/*    */     }
/*    */     
/*    */     public static Criterion<TriggerInstance> craftedItem(ResourceLocation $$0) {
/* 43 */       return CriteriaTriggers.RECIPE_CRAFTED.createCriterion(new TriggerInstance(Optional.empty(), $$0, List.of()));
/*    */     }
/*    */     
/*    */     boolean matches(ResourceLocation $$0, List<ItemStack> $$1) {
/* 47 */       if (!$$0.equals(this.recipeId)) {
/* 48 */         return false;
/*    */       }
/*    */       
/* 51 */       List<ItemStack> $$2 = new ArrayList<>($$1);
/* 52 */       for (ItemPredicate $$3 : this.ingredients) {
/* 53 */         boolean $$4 = false;
/* 54 */         for (Iterator<ItemStack> $$5 = $$2.iterator(); $$5.hasNext();) {
/* 55 */           if ($$3.matches($$5.next())) {
/* 56 */             $$5.remove();
/* 57 */             $$4 = true;
/*    */             break;
/*    */           } 
/*    */         } 
/* 61 */         if (!$$4) {
/* 62 */           return false;
/*    */         }
/*    */       } 
/* 65 */       return true;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\RecipeCraftedTrigger.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */