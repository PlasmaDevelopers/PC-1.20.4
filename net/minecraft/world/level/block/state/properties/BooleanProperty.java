/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import java.util.Optional;
/*    */ 
/*    */ public class BooleanProperty
/*    */   extends Property<Boolean> {
/*    */   private final ImmutableSet<Boolean> values;
/*    */   
/*    */   protected BooleanProperty(String $$0) {
/* 12 */     super($$0, Boolean.class);
/* 13 */     this.values = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Boolean> getPossibleValues() {
/* 18 */     return (Collection<Boolean>)this.values;
/*    */   }
/*    */   
/*    */   public static BooleanProperty create(String $$0) {
/* 22 */     return new BooleanProperty($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Boolean> getValue(String $$0) {
/* 27 */     if ("true".equals($$0) || "false".equals($$0)) {
/* 28 */       return Optional.of(Boolean.valueOf($$0));
/*    */     }
/*    */     
/* 31 */     return Optional.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName(Boolean $$0) {
/* 36 */     return $$0.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 41 */     if (this == $$0) {
/* 42 */       return true;
/*    */     }
/*    */     
/* 45 */     if ($$0 instanceof BooleanProperty) { BooleanProperty $$1 = (BooleanProperty)$$0; if (super.equals($$0)) {
/* 46 */         return this.values.equals($$1.values);
/*    */       } }
/*    */     
/* 49 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int generateHashCode() {
/* 54 */     return 31 * super.generateHashCode() + this.values.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\BooleanProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */