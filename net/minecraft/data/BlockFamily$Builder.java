/*     */ package net.minecraft.data;
/*     */ 
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */   private final BlockFamily family;
/*     */   
/*     */   public Builder(Block $$0) {
/*  95 */     this.family = new BlockFamily($$0);
/*     */   }
/*     */   
/*     */   public BlockFamily getFamily() {
/*  99 */     return this.family;
/*     */   }
/*     */   
/*     */   public Builder button(Block $$0) {
/* 103 */     this.family.variants.put(BlockFamily.Variant.BUTTON, $$0);
/* 104 */     return this;
/*     */   }
/*     */   
/*     */   public Builder chiseled(Block $$0) {
/* 108 */     this.family.variants.put(BlockFamily.Variant.CHISELED, $$0);
/* 109 */     return this;
/*     */   }
/*     */   
/*     */   public Builder mosaic(Block $$0) {
/* 113 */     this.family.variants.put(BlockFamily.Variant.MOSAIC, $$0);
/* 114 */     return this;
/*     */   }
/*     */   
/*     */   public Builder cracked(Block $$0) {
/* 118 */     this.family.variants.put(BlockFamily.Variant.CRACKED, $$0);
/* 119 */     return this;
/*     */   }
/*     */   
/*     */   public Builder cut(Block $$0) {
/* 123 */     this.family.variants.put(BlockFamily.Variant.CUT, $$0);
/* 124 */     return this;
/*     */   }
/*     */   
/*     */   public Builder door(Block $$0) {
/* 128 */     this.family.variants.put(BlockFamily.Variant.DOOR, $$0);
/* 129 */     return this;
/*     */   }
/*     */   
/*     */   public Builder customFence(Block $$0) {
/* 133 */     this.family.variants.put(BlockFamily.Variant.CUSTOM_FENCE, $$0);
/* 134 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder fence(Block $$0) {
/* 139 */     this.family.variants.put(BlockFamily.Variant.FENCE, $$0);
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   public Builder customFenceGate(Block $$0) {
/* 144 */     this.family.variants.put(BlockFamily.Variant.CUSTOM_FENCE_GATE, $$0);
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Builder fenceGate(Block $$0) {
/* 150 */     this.family.variants.put(BlockFamily.Variant.FENCE_GATE, $$0);
/* 151 */     return this;
/*     */   }
/*     */   
/*     */   public Builder sign(Block $$0, Block $$1) {
/* 155 */     this.family.variants.put(BlockFamily.Variant.SIGN, $$0);
/* 156 */     this.family.variants.put(BlockFamily.Variant.WALL_SIGN, $$1);
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public Builder slab(Block $$0) {
/* 161 */     this.family.variants.put(BlockFamily.Variant.SLAB, $$0);
/* 162 */     return this;
/*     */   }
/*     */   
/*     */   public Builder stairs(Block $$0) {
/* 166 */     this.family.variants.put(BlockFamily.Variant.STAIRS, $$0);
/* 167 */     return this;
/*     */   }
/*     */   
/*     */   public Builder pressurePlate(Block $$0) {
/* 171 */     this.family.variants.put(BlockFamily.Variant.PRESSURE_PLATE, $$0);
/* 172 */     return this;
/*     */   }
/*     */   
/*     */   public Builder polished(Block $$0) {
/* 176 */     this.family.variants.put(BlockFamily.Variant.POLISHED, $$0);
/* 177 */     return this;
/*     */   }
/*     */   
/*     */   public Builder trapdoor(Block $$0) {
/* 181 */     this.family.variants.put(BlockFamily.Variant.TRAPDOOR, $$0);
/* 182 */     return this;
/*     */   }
/*     */   
/*     */   public Builder wall(Block $$0) {
/* 186 */     this.family.variants.put(BlockFamily.Variant.WALL, $$0);
/* 187 */     return this;
/*     */   }
/*     */   
/*     */   public Builder dontGenerateModel() {
/* 191 */     this.family.generateModel = false;
/* 192 */     return this;
/*     */   }
/*     */   
/*     */   public Builder dontGenerateRecipe() {
/* 196 */     this.family.generateRecipe = false;
/* 197 */     return this;
/*     */   }
/*     */   
/*     */   public Builder recipeGroupPrefix(String $$0) {
/* 201 */     this.family.recipeGroupPrefix = $$0;
/* 202 */     return this;
/*     */   }
/*     */   
/*     */   public Builder recipeUnlockedBy(String $$0) {
/* 206 */     this.family.recipeUnlockedBy = $$0;
/* 207 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\BlockFamily$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */