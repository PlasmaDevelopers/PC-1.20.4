/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Builder
/*     */ {
/* 122 */   private final ImmutableList.Builder<StatePropertiesPredicate.PropertyMatcher> matchers = ImmutableList.builder();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Builder properties() {
/* 128 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder hasProperty(Property<?> $$0, String $$1) {
/* 132 */     this.matchers.add(new StatePropertiesPredicate.PropertyMatcher($$0.getName(), new StatePropertiesPredicate.ExactMatcher($$1)));
/* 133 */     return this;
/*     */   }
/*     */   
/*     */   public Builder hasProperty(Property<Integer> $$0, int $$1) {
/* 137 */     return hasProperty($$0, Integer.toString($$1));
/*     */   }
/*     */   
/*     */   public Builder hasProperty(Property<Boolean> $$0, boolean $$1) {
/* 141 */     return hasProperty($$0, Boolean.toString($$1));
/*     */   }
/*     */   
/*     */   public <T extends Comparable<T> & StringRepresentable> Builder hasProperty(Property<T> $$0, T $$1) {
/* 145 */     return hasProperty($$0, ((StringRepresentable)$$1).getSerializedName());
/*     */   }
/*     */   
/*     */   public Optional<StatePropertiesPredicate> build() {
/* 149 */     return Optional.of(new StatePropertiesPredicate((List<StatePropertiesPredicate.PropertyMatcher>)this.matchers.build()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\StatePropertiesPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */