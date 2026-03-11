/*    */ package net.minecraft.world.level.storage.loot.parameters;
/*    */ 
/*    */ import com.google.common.base.Joiner;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ import net.minecraft.world.level.storage.loot.LootContextUser;
/*    */ import net.minecraft.world.level.storage.loot.ValidationContext;
/*    */ 
/*    */ public class LootContextParamSet
/*    */ {
/*    */   private final Set<LootContextParam<?>> required;
/*    */   private final Set<LootContextParam<?>> all;
/*    */   
/*    */   LootContextParamSet(Set<LootContextParam<?>> $$0, Set<LootContextParam<?>> $$1) {
/* 17 */     this.required = (Set<LootContextParam<?>>)ImmutableSet.copyOf($$0);
/* 18 */     this.all = (Set<LootContextParam<?>>)ImmutableSet.copyOf((Collection)Sets.union($$0, $$1));
/*    */   }
/*    */   
/*    */   public boolean isAllowed(LootContextParam<?> $$0) {
/* 22 */     return this.all.contains($$0);
/*    */   }
/*    */   
/*    */   public Set<LootContextParam<?>> getRequired() {
/* 26 */     return this.required;
/*    */   }
/*    */   
/*    */   public Set<LootContextParam<?>> getAllowed() {
/* 30 */     return this.all;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return "[" + Joiner.on(", ").join(this.all.stream().map($$0 -> (this.required.contains($$0) ? "!" : "") + (this.required.contains($$0) ? "!" : "")).iterator()) + "]";
/*    */   }
/*    */   
/*    */   public void validateUser(ValidationContext $$0, LootContextUser $$1) {
/* 39 */     Set<LootContextParam<?>> $$2 = $$1.getReferencedContextParams();
/* 40 */     Sets.SetView setView = Sets.difference($$2, this.all);
/* 41 */     if (!setView.isEmpty()) {
/* 42 */       $$0.reportProblem("Parameters " + setView + " are not provided in this context");
/*    */     }
/*    */   }
/*    */   
/*    */   public static Builder builder() {
/* 47 */     return new Builder();
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 51 */     private final Set<LootContextParam<?>> required = Sets.newIdentityHashSet();
/* 52 */     private final Set<LootContextParam<?>> optional = Sets.newIdentityHashSet();
/*    */     
/*    */     public Builder required(LootContextParam<?> $$0) {
/* 55 */       if (this.optional.contains($$0)) {
/* 56 */         throw new IllegalArgumentException("Parameter " + $$0.getName() + " is already optional");
/*    */       }
/* 58 */       this.required.add($$0);
/* 59 */       return this;
/*    */     }
/*    */     
/*    */     public Builder optional(LootContextParam<?> $$0) {
/* 63 */       if (this.required.contains($$0)) {
/* 64 */         throw new IllegalArgumentException("Parameter " + $$0.getName() + " is already required");
/*    */       }
/* 66 */       this.optional.add($$0);
/* 67 */       return this;
/*    */     }
/*    */     
/*    */     public LootContextParamSet build() {
/* 71 */       return new LootContextParamSet(this.required, this.optional);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\parameters\LootContextParamSet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */