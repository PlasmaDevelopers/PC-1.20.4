/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import java.util.Optional;
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
/*  64 */   private Optional<ItemPredicate> head = Optional.empty();
/*  65 */   private Optional<ItemPredicate> chest = Optional.empty();
/*  66 */   private Optional<ItemPredicate> legs = Optional.empty();
/*  67 */   private Optional<ItemPredicate> feet = Optional.empty();
/*  68 */   private Optional<ItemPredicate> mainhand = Optional.empty();
/*  69 */   private Optional<ItemPredicate> offhand = Optional.empty();
/*     */   
/*     */   public static Builder equipment() {
/*  72 */     return new Builder();
/*     */   }
/*     */   
/*     */   public Builder head(ItemPredicate.Builder $$0) {
/*  76 */     this.head = Optional.of($$0.build());
/*  77 */     return this;
/*     */   }
/*     */   
/*     */   public Builder chest(ItemPredicate.Builder $$0) {
/*  81 */     this.chest = Optional.of($$0.build());
/*  82 */     return this;
/*     */   }
/*     */   
/*     */   public Builder legs(ItemPredicate.Builder $$0) {
/*  86 */     this.legs = Optional.of($$0.build());
/*  87 */     return this;
/*     */   }
/*     */   
/*     */   public Builder feet(ItemPredicate.Builder $$0) {
/*  91 */     this.feet = Optional.of($$0.build());
/*  92 */     return this;
/*     */   }
/*     */   
/*     */   public Builder mainhand(ItemPredicate.Builder $$0) {
/*  96 */     this.mainhand = Optional.of($$0.build());
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public Builder offhand(ItemPredicate.Builder $$0) {
/* 101 */     this.offhand = Optional.of($$0.build());
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public EntityEquipmentPredicate build() {
/* 106 */     return new EntityEquipmentPredicate(this.head, this.chest, this.legs, this.feet, this.mainhand, this.offhand);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\EntityEquipmentPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */