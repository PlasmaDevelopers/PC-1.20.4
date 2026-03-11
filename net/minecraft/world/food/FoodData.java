/*     */ package net.minecraft.world.food;
/*     */ 
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.world.Difficulty;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FoodData
/*     */ {
/*  20 */   private int foodLevel = 20;
/*  21 */   private int lastFoodLevel = 20;
/*  22 */   private float saturationLevel = 5.0F;
/*     */   private float exhaustionLevel;
/*     */   
/*     */   public void eat(int $$0, float $$1) {
/*  26 */     this.foodLevel = Math.min($$0 + this.foodLevel, 20);
/*  27 */     this.saturationLevel = Math.min(this.saturationLevel + $$0 * $$1 * 2.0F, this.foodLevel);
/*     */   }
/*     */   private int tickTimer;
/*     */   public void eat(Item $$0, ItemStack $$1) {
/*  31 */     if ($$0.isEdible()) {
/*  32 */       FoodProperties $$2 = $$0.getFoodProperties();
/*  33 */       eat($$2.getNutrition(), $$2.getSaturationModifier());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick(Player $$0) {
/*  38 */     Difficulty $$1 = $$0.level().getDifficulty();
/*     */     
/*  40 */     this.lastFoodLevel = this.foodLevel;
/*     */     
/*  42 */     if (this.exhaustionLevel > 4.0F) {
/*  43 */       this.exhaustionLevel -= 4.0F;
/*     */       
/*  45 */       if (this.saturationLevel > 0.0F) {
/*  46 */         this.saturationLevel = Math.max(this.saturationLevel - 1.0F, 0.0F);
/*  47 */       } else if ($$1 != Difficulty.PEACEFUL) {
/*  48 */         this.foodLevel = Math.max(this.foodLevel - 1, 0);
/*     */       } 
/*     */     } 
/*     */     
/*  52 */     boolean $$2 = $$0.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION);
/*  53 */     if ($$2 && this.saturationLevel > 0.0F && $$0.isHurt() && this.foodLevel >= 20) {
/*  54 */       this.tickTimer++;
/*  55 */       if (this.tickTimer >= 10) {
/*  56 */         float $$3 = Math.min(this.saturationLevel, 6.0F);
/*  57 */         $$0.heal($$3 / 6.0F);
/*  58 */         addExhaustion($$3);
/*  59 */         this.tickTimer = 0;
/*     */       } 
/*  61 */     } else if ($$2 && this.foodLevel >= 18 && $$0.isHurt()) {
/*  62 */       this.tickTimer++;
/*  63 */       if (this.tickTimer >= 80) {
/*  64 */         $$0.heal(1.0F);
/*  65 */         addExhaustion(6.0F);
/*  66 */         this.tickTimer = 0;
/*     */       } 
/*  68 */     } else if (this.foodLevel <= 0) {
/*  69 */       this.tickTimer++;
/*  70 */       if (this.tickTimer >= 80) {
/*  71 */         if ($$0.getHealth() > 10.0F || $$1 == Difficulty.HARD || ($$0.getHealth() > 1.0F && $$1 == Difficulty.NORMAL)) {
/*  72 */           $$0.hurt($$0.damageSources().starve(), 1.0F);
/*     */         }
/*  74 */         this.tickTimer = 0;
/*     */       } 
/*     */     } else {
/*  77 */       this.tickTimer = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void readAdditionalSaveData(CompoundTag $$0) {
/*  82 */     if ($$0.contains("foodLevel", 99)) {
/*  83 */       this.foodLevel = $$0.getInt("foodLevel");
/*  84 */       this.tickTimer = $$0.getInt("foodTickTimer");
/*  85 */       this.saturationLevel = $$0.getFloat("foodSaturationLevel");
/*  86 */       this.exhaustionLevel = $$0.getFloat("foodExhaustionLevel");
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addAdditionalSaveData(CompoundTag $$0) {
/*  91 */     $$0.putInt("foodLevel", this.foodLevel);
/*  92 */     $$0.putInt("foodTickTimer", this.tickTimer);
/*  93 */     $$0.putFloat("foodSaturationLevel", this.saturationLevel);
/*  94 */     $$0.putFloat("foodExhaustionLevel", this.exhaustionLevel);
/*     */   }
/*     */   
/*     */   public int getFoodLevel() {
/*  98 */     return this.foodLevel;
/*     */   }
/*     */   
/*     */   public int getLastFoodLevel() {
/* 102 */     return this.lastFoodLevel;
/*     */   }
/*     */   
/*     */   public boolean needsFood() {
/* 106 */     return (this.foodLevel < 20);
/*     */   }
/*     */   
/*     */   public void addExhaustion(float $$0) {
/* 110 */     this.exhaustionLevel = Math.min(this.exhaustionLevel + $$0, 40.0F);
/*     */   }
/*     */   
/*     */   public float getExhaustionLevel() {
/* 114 */     return this.exhaustionLevel;
/*     */   }
/*     */   
/*     */   public float getSaturationLevel() {
/* 118 */     return this.saturationLevel;
/*     */   }
/*     */   
/*     */   public void setFoodLevel(int $$0) {
/* 122 */     this.foodLevel = $$0;
/*     */   }
/*     */   
/*     */   public void setSaturation(float $$0) {
/* 126 */     this.saturationLevel = $$0;
/*     */   }
/*     */   
/*     */   public void setExhaustion(float $$0) {
/* 130 */     this.exhaustionLevel = $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\food\FoodData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */