/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
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
/*    */ final class ExactMatcher
/*    */   extends Record
/*    */   implements StatePropertiesPredicate.ValueMatcher
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #55	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$ExactMatcher;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   ExactMatcher(String $$0) {
/* 55 */     this.value = $$0; } public String value() { return this.value; }
/* 56 */    public static final Codec<ExactMatcher> CODEC = Codec.STRING.xmap(ExactMatcher::new, ExactMatcher::value);
/*    */ 
/*    */   
/*    */   public <T extends Comparable<T>> boolean match(StateHolder<?, ?> $$0, Property<T> $$1) {
/* 60 */     Comparable<Comparable> comparable = $$0.getValue($$1);
/* 61 */     Optional<T> $$3 = $$1.getValue(this.value);
/* 62 */     return ($$3.isPresent() && comparable.compareTo((Comparable)$$3.get()) == 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate$ExactMatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */