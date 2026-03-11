/*    */ package net.minecraft.util.random;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
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
/*    */ public class Wrapper<T>
/*    */   implements WeightedEntry
/*    */ {
/*    */   private final T data;
/*    */   private final Weight weight;
/*    */   
/*    */   Wrapper(T $$0, Weight $$1) {
/* 35 */     this.data = $$0;
/* 36 */     this.weight = $$1;
/*    */   }
/*    */   
/*    */   public T getData() {
/* 40 */     return this.data;
/*    */   }
/*    */ 
/*    */   
/*    */   public Weight getWeight() {
/* 45 */     return this.weight;
/*    */   }
/*    */   
/*    */   public static <E> Codec<Wrapper<E>> codec(Codec<E> $$0) {
/* 49 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)$$0.fieldOf("data").forGetter(Wrapper::getData), (App)Weight.CODEC.fieldOf("weight").forGetter(Wrapper::getWeight)).apply((Applicative)$$1, Wrapper::new));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\random\WeightedEntry$Wrapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */