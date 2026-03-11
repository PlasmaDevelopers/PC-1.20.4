/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Optional;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class IntegerProperty
/*    */   extends Property<Integer> {
/*    */   private final ImmutableSet<Integer> values;
/*    */   private final int min;
/*    */   private final int max;
/*    */   
/*    */   protected IntegerProperty(String $$0, int $$1, int $$2) {
/* 16 */     super($$0, Integer.class);
/* 17 */     if ($$1 < 0) {
/* 18 */       throw new IllegalArgumentException("Min value of " + $$0 + " must be 0 or greater");
/*    */     }
/* 20 */     if ($$2 <= $$1) {
/* 21 */       throw new IllegalArgumentException("Max value of " + $$0 + " must be greater than min (" + $$1 + ")");
/*    */     }
/* 23 */     this.min = $$1;
/* 24 */     this.max = $$2;
/* 25 */     Set<Integer> $$3 = Sets.newHashSet();
/* 26 */     for (int $$4 = $$1; $$4 <= $$2; $$4++) {
/* 27 */       $$3.add(Integer.valueOf($$4));
/*    */     }
/*    */     
/* 30 */     this.values = ImmutableSet.copyOf($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Integer> getPossibleValues() {
/* 35 */     return (Collection<Integer>)this.values;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 40 */     if (this == $$0) {
/* 41 */       return true;
/*    */     }
/*    */     
/* 44 */     if ($$0 instanceof IntegerProperty) { IntegerProperty $$1 = (IntegerProperty)$$0; if (super.equals($$0)) {
/* 45 */         return this.values.equals($$1.values);
/*    */       } }
/*    */     
/* 48 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int generateHashCode() {
/* 53 */     return 31 * super.generateHashCode() + this.values.hashCode();
/*    */   }
/*    */   
/*    */   public static IntegerProperty create(String $$0, int $$1, int $$2) {
/* 57 */     return new IntegerProperty($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Integer> getValue(String $$0) {
/*    */     try {
/* 63 */       Integer $$1 = Integer.valueOf($$0);
/*    */       
/* 65 */       return ($$1.intValue() >= this.min && $$1.intValue() <= this.max) ? Optional.<Integer>of($$1) : Optional.<Integer>empty();
/* 66 */     } catch (NumberFormatException $$2) {
/* 67 */       return Optional.empty();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName(Integer $$0) {
/* 73 */     return $$0.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\IntegerProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */