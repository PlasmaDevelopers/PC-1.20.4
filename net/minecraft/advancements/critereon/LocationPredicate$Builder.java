/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/* 100 */   private MinMaxBounds.Doubles x = MinMaxBounds.Doubles.ANY;
/* 101 */   private MinMaxBounds.Doubles y = MinMaxBounds.Doubles.ANY;
/* 102 */   private MinMaxBounds.Doubles z = MinMaxBounds.Doubles.ANY;
/*     */   
/* 104 */   private Optional<ResourceKey<Biome>> biome = Optional.empty();
/* 105 */   private Optional<ResourceKey<Structure>> structure = Optional.empty();
/* 106 */   private Optional<ResourceKey<Level>> dimension = Optional.empty();
/* 107 */   private Optional<Boolean> smokey = Optional.empty();
/*     */   
/* 109 */   private Optional<LightPredicate> light = Optional.empty();
/* 110 */   private Optional<BlockPredicate> block = Optional.empty();
/* 111 */   private Optional<FluidPredicate> fluid = Optional.empty();
/*     */   
/*     */   public static Builder location() {
/* 114 */     return new Builder();
/*     */   }
/*     */   
/*     */   public static Builder inBiome(ResourceKey<Biome> $$0) {
/* 118 */     return location().setBiome($$0);
/*     */   }
/*     */   
/*     */   public static Builder inDimension(ResourceKey<Level> $$0) {
/* 122 */     return location().setDimension($$0);
/*     */   }
/*     */   
/*     */   public static Builder inStructure(ResourceKey<Structure> $$0) {
/* 126 */     return location().setStructure($$0);
/*     */   }
/*     */   
/*     */   public static Builder atYLocation(MinMaxBounds.Doubles $$0) {
/* 130 */     return location().setY($$0);
/*     */   }
/*     */   
/*     */   public Builder setX(MinMaxBounds.Doubles $$0) {
/* 134 */     this.x = $$0;
/* 135 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setY(MinMaxBounds.Doubles $$0) {
/* 139 */     this.y = $$0;
/* 140 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setZ(MinMaxBounds.Doubles $$0) {
/* 144 */     this.z = $$0;
/* 145 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setBiome(ResourceKey<Biome> $$0) {
/* 149 */     this.biome = Optional.of($$0);
/* 150 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setStructure(ResourceKey<Structure> $$0) {
/* 154 */     this.structure = Optional.of($$0);
/* 155 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setDimension(ResourceKey<Level> $$0) {
/* 159 */     this.dimension = Optional.of($$0);
/* 160 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setLight(LightPredicate.Builder $$0) {
/* 164 */     this.light = Optional.of($$0.build());
/* 165 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setBlock(BlockPredicate.Builder $$0) {
/* 169 */     this.block = Optional.of($$0.build());
/* 170 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setFluid(FluidPredicate.Builder $$0) {
/* 174 */     this.fluid = Optional.of($$0.build());
/* 175 */     return this;
/*     */   }
/*     */   
/*     */   public Builder setSmokey(boolean $$0) {
/* 179 */     this.smokey = Optional.of(Boolean.valueOf($$0));
/* 180 */     return this;
/*     */   }
/*     */   
/*     */   public LocationPredicate build() {
/* 184 */     Optional<LocationPredicate.PositionPredicate> $$0 = LocationPredicate.PositionPredicate.of(this.x, this.y, this.z);
/* 185 */     return new LocationPredicate($$0, this.biome, this.structure, this.dimension, this.smokey, this.light, this.block, this.fluid);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LocationPredicate$Builder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */