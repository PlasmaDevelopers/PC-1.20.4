/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.world.damagesource.DamageType;
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
/* 48 */   private final ImmutableList.Builder<TagPredicate<DamageType>> tags = ImmutableList.builder();
/* 49 */   private Optional<EntityPredicate> directEntity = Optional.empty();
/* 50 */   private Optional<EntityPredicate> sourceEntity = Optional.empty();
/*    */   
/*    */   public static Builder damageType() {
/* 53 */     return new Builder();
/*    */   }
/*    */   
/*    */   public Builder tag(TagPredicate<DamageType> $$0) {
/* 57 */     this.tags.add($$0);
/* 58 */     return this;
/*    */   }
/*    */   
/*    */   public Builder direct(EntityPredicate.Builder $$0) {
/* 62 */     this.directEntity = Optional.of($$0.build());
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public Builder source(EntityPredicate.Builder $$0) {
/* 67 */     this.sourceEntity = Optional.of($$0.build());
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public DamageSourcePredicate build() {
/* 72 */     return new DamageSourcePredicate((List<TagPredicate<DamageType>>)this.tags.build(), this.directEntity, this.sourceEntity);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\DamageSourcePredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */