/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.world.level.material.Fluid;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/* 48 */   private Optional<Holder<Fluid>> fluid = Optional.empty();
/* 49 */   private Optional<TagKey<Fluid>> fluids = Optional.empty();
/* 50 */   private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static Builder fluid() {
/* 56 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder of(Fluid $$0) {
/* 60 */     this.fluid = Optional.of($$0.builtInRegistryHolder());
/* 61 */     return this;
/*    */   }
/*    */   
/*    */   public Builder of(TagKey<Fluid> $$0) {
/* 65 */     this.fluids = Optional.of($$0);
/* 66 */     return this;
/*    */   }
/*    */   
/*    */   public Builder setProperties(StatePropertiesPredicate $$0) {
/* 70 */     this.properties = Optional.of($$0);
/* 71 */     return this;
/*    */   }
/*    */   
/*    */   public FluidPredicate build() {
/* 75 */     return new FluidPredicate(this.fluids, this.fluid, this.properties);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\FluidPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */