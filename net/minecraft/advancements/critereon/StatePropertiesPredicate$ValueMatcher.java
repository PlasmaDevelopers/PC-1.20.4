/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.world.level.block.state.StateHolder;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ interface ValueMatcher
/*    */ {
/*    */   public static final Codec<ValueMatcher> CODEC;
/*    */   
/*    */   static {
/* 40 */     CODEC = Codec.either(StatePropertiesPredicate.ExactMatcher.CODEC, StatePropertiesPredicate.RangedMatcher.CODEC).xmap($$0 -> (ValueMatcher)$$0.map((), ()), $$0 -> {
/*    */           if ($$0 instanceof StatePropertiesPredicate.ExactMatcher) {
/*    */             StatePropertiesPredicate.ExactMatcher $$1 = (StatePropertiesPredicate.ExactMatcher)$$0;
/*    */             return Either.left($$1);
/*    */           } 
/*    */           if ($$0 instanceof StatePropertiesPredicate.RangedMatcher) {
/*    */             StatePropertiesPredicate.RangedMatcher $$2 = (StatePropertiesPredicate.RangedMatcher)$$0;
/*    */             return Either.right($$2);
/*    */           } 
/*    */           throw new UnsupportedOperationException();
/*    */         });
/*    */   }
/*    */   
/*    */   <T extends Comparable<T>> boolean match(StateHolder<?, ?> paramStateHolder, Property<T> paramProperty);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate$ValueMatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */