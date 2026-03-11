/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.world.flag.FeatureFlag;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.food.FoodProperties;
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
/*     */ 
/*     */ 
/*     */ public class Properties
/*     */ {
/* 124 */   int maxStackSize = 64;
/*     */   int maxDamage;
/*     */   @Nullable
/*     */   Item craftingRemainingItem;
/* 128 */   Rarity rarity = Rarity.COMMON;
/*     */   @Nullable
/*     */   FoodProperties foodProperties;
/*     */   boolean isFireResistant;
/* 132 */   FeatureFlagSet requiredFeatures = FeatureFlags.VANILLA_SET;
/*     */   
/*     */   public Properties food(FoodProperties $$0) {
/* 135 */     this.foodProperties = $$0;
/* 136 */     return this;
/*     */   }
/*     */   
/*     */   public Properties stacksTo(int $$0) {
/* 140 */     if (this.maxDamage > 0) {
/* 141 */       throw new RuntimeException("Unable to have damage AND stack.");
/*     */     }
/* 143 */     this.maxStackSize = $$0;
/* 144 */     return this;
/*     */   }
/*     */   
/*     */   public Properties defaultDurability(int $$0) {
/* 148 */     return (this.maxDamage == 0) ? durability($$0) : this;
/*     */   }
/*     */   
/*     */   public Properties durability(int $$0) {
/* 152 */     this.maxDamage = $$0;
/* 153 */     this.maxStackSize = 1;
/* 154 */     return this;
/*     */   }
/*     */   
/*     */   public Properties craftRemainder(Item $$0) {
/* 158 */     this.craftingRemainingItem = $$0;
/* 159 */     return this;
/*     */   }
/*     */   
/*     */   public Properties rarity(Rarity $$0) {
/* 163 */     this.rarity = $$0;
/* 164 */     return this;
/*     */   }
/*     */   
/*     */   public Properties fireResistant() {
/* 168 */     this.isFireResistant = true;
/* 169 */     return this;
/*     */   }
/*     */   
/*     */   public Properties requiredFeatures(FeatureFlag... $$0) {
/* 173 */     this.requiredFeatures = FeatureFlags.REGISTRY.subset($$0);
/* 174 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\Item$Properties.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */