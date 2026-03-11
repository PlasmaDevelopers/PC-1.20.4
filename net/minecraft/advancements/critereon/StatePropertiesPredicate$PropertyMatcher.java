/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.level.block.state.StateDefinition;
/*    */ import net.minecraft.world.level.block.state.StateHolder;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ 
/*    */ final class PropertyMatcher
/*    */   extends Record
/*    */ {
/*    */   private final String name;
/*    */   private final StatePropertiesPredicate.ValueMatcher valueMatcher;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #27	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/StatePropertiesPredicate$PropertyMatcher;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   PropertyMatcher(String $$0, StatePropertiesPredicate.ValueMatcher $$1) {
/* 27 */     this.name = $$0; this.valueMatcher = $$1; } public String name() { return this.name; } public StatePropertiesPredicate.ValueMatcher valueMatcher() { return this.valueMatcher; }
/*    */    public <S extends StateHolder<?, S>> boolean match(StateDefinition<?, S> $$0, S $$1) {
/* 29 */     Property<?> $$2 = $$0.getProperty(this.name);
/* 30 */     return ($$2 != null && this.valueMatcher.match((StateHolder<?, ?>)$$1, $$2));
/*    */   }
/*    */   
/*    */   public Optional<String> checkState(StateDefinition<?, ?> $$0) {
/* 34 */     Property<?> $$1 = $$0.getProperty(this.name);
/* 35 */     return ($$1 != null) ? Optional.<String>empty() : Optional.<String>of(this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate$PropertyMatcher.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */