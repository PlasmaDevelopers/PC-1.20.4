/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.util.ExtraCodecs;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class RangedMatcher
/*    */   extends Record
/*    */   implements StatePropertiesPredicate.ValueMatcher
/*    */ {
/*    */   private final Optional<String> minValue;
/*    */   private final Optional<String> maxValue;
/*    */   public static final Codec<RangedMatcher> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #66	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #66	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #66	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$RangedMatcher;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   private RangedMatcher(Optional<String> $$0, Optional<String> $$1) {
/* 66 */     this.minValue = $$0; this.maxValue = $$1; } public Optional<String> minValue() { return this.minValue; } public Optional<String> maxValue() { return this.maxValue; } static {
/* 67 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "min").forGetter(RangedMatcher::minValue), (App)ExtraCodecs.strictOptionalField((Codec)Codec.STRING, "max").forGetter(RangedMatcher::maxValue)).apply((Applicative)$$0, RangedMatcher::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public <T extends Comparable<T>> boolean match(StateHolder<?, ?> $$0, Property<T> $$1) {
/* 74 */     Comparable<Comparable> comparable = $$0.getValue($$1);
/*    */     
/* 76 */     if (this.minValue.isPresent()) {
/* 77 */       Optional<T> $$3 = $$1.getValue(this.minValue.get());
/* 78 */       if ($$3.isEmpty() || comparable.compareTo((Comparable)$$3.get()) < 0) {
/* 79 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 83 */     if (this.maxValue.isPresent()) {
/* 84 */       Optional<T> $$4 = $$1.getValue(this.maxValue.get());
/* 85 */       if ($$4.isEmpty() || comparable.compareTo((Comparable)$$4.get()) > 0) {
/* 86 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 90 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate$RangedMatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */