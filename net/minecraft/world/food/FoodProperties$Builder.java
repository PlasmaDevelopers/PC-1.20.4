/*    */ package net.minecraft.world.food;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.List;
/*    */ import net.minecraft.world.effect.MobEffectInstance;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Builder
/*    */ {
/*    */   private int nutrition;
/*    */   private float saturationModifier;
/*    */   private boolean isMeat;
/*    */   private boolean canAlwaysEat;
/*    */   private boolean fastFood;
/* 59 */   private final List<Pair<MobEffectInstance, Float>> effects = Lists.newArrayList();
/*    */   
/*    */   public Builder nutrition(int $$0) {
/* 62 */     this.nutrition = $$0;
/* 63 */     return this;
/*    */   }
/*    */   
/*    */   public Builder saturationMod(float $$0) {
/* 67 */     this.saturationModifier = $$0;
/* 68 */     return this;
/*    */   }
/*    */   
/*    */   public Builder meat() {
/* 72 */     this.isMeat = true;
/* 73 */     return this;
/*    */   }
/*    */   
/*    */   public Builder alwaysEat() {
/* 77 */     this.canAlwaysEat = true;
/* 78 */     return this;
/*    */   }
/*    */   
/*    */   public Builder fast() {
/* 82 */     this.fastFood = true;
/* 83 */     return this;
/*    */   }
/*    */   
/*    */   public Builder effect(MobEffectInstance $$0, float $$1) {
/* 87 */     this.effects.add(Pair.of($$0, Float.valueOf($$1)));
/* 88 */     return this;
/*    */   }
/*    */   
/*    */   public FoodProperties build() {
/* 92 */     return new FoodProperties(this.nutrition, this.saturationModifier, this.isMeat, this.canAlwaysEat, this.fastFood, this.effects);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\food\FoodProperties$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */