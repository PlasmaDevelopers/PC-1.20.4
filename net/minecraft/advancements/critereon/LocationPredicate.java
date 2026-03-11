/*     */ package net.minecraft.advancements.critereon;
/*     */ 
/*     */ import java.util.Optional;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ 
/*     */ public final class LocationPredicate extends Record {
/*     */   private final Optional<PositionPredicate> position;
/*     */   private final Optional<ResourceKey<Biome>> biome;
/*     */   private final Optional<ResourceKey<Structure>> structure;
/*     */   private final Optional<ResourceKey<Level>> dimension;
/*     */   private final Optional<Boolean> smokey;
/*     */   private final Optional<LightPredicate> light;
/*     */   private final Optional<BlockPredicate> block;
/*     */   private final Optional<FluidPredicate> fluid;
/*     */   public static final Codec<LocationPredicate> CODEC;
/*     */   
/*  17 */   public LocationPredicate(Optional<PositionPredicate> $$0, Optional<ResourceKey<Biome>> $$1, Optional<ResourceKey<Structure>> $$2, Optional<ResourceKey<Level>> $$3, Optional<Boolean> $$4, Optional<LightPredicate> $$5, Optional<BlockPredicate> $$6, Optional<FluidPredicate> $$7) { this.position = $$0; this.biome = $$1; this.structure = $$2; this.dimension = $$3; this.smokey = $$4; this.light = $$5; this.block = $$6; this.fluid = $$7; } public final String toString() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/LocationPredicate;)Ljava/lang/String;
/*     */     //   6: areturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #17	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*  17 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate; } public Optional<PositionPredicate> position() { return this.position; } public final int hashCode() { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/LocationPredicate;)I
/*     */     //   6: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #17	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/LocationPredicate;Ljava/lang/Object;)Z
/*     */     //   7: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #17	-> 0
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate;
/*  17 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<ResourceKey<Biome>> biome() { return this.biome; } public Optional<ResourceKey<Structure>> structure() { return this.structure; } public Optional<ResourceKey<Level>> dimension() { return this.dimension; } public Optional<Boolean> smokey() { return this.smokey; } public Optional<LightPredicate> light() { return this.light; } public Optional<BlockPredicate> block() { return this.block; } public Optional<FluidPredicate> fluid() { return this.fluid; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(PositionPredicate.CODEC, "position").forGetter(LocationPredicate::position), (App)ExtraCodecs.strictOptionalField(ResourceKey.codec(Registries.BIOME), "biome").forGetter(LocationPredicate::biome), (App)ExtraCodecs.strictOptionalField(ResourceKey.codec(Registries.STRUCTURE), "structure").forGetter(LocationPredicate::structure), (App)ExtraCodecs.strictOptionalField(ResourceKey.codec(Registries.DIMENSION), "dimension").forGetter(LocationPredicate::dimension), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "smokey").forGetter(LocationPredicate::smokey), (App)ExtraCodecs.strictOptionalField(LightPredicate.CODEC, "light").forGetter(LocationPredicate::light), (App)ExtraCodecs.strictOptionalField(BlockPredicate.CODEC, "block").forGetter(LocationPredicate::block), (App)ExtraCodecs.strictOptionalField(FluidPredicate.CODEC, "fluid").forGetter(LocationPredicate::fluid)).apply((Applicative)$$0, LocationPredicate::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Optional<LocationPredicate> of(Optional<PositionPredicate> $$0, Optional<ResourceKey<Biome>> $$1, Optional<ResourceKey<Structure>> $$2, Optional<ResourceKey<Level>> $$3, Optional<Boolean> $$4, Optional<LightPredicate> $$5, Optional<BlockPredicate> $$6, Optional<FluidPredicate> $$7) {
/*  40 */     if ($$0.isEmpty() && $$1.isEmpty() && $$2.isEmpty() && $$3.isEmpty() && $$4.isEmpty() && $$5.isEmpty() && $$6.isEmpty() && $$7.isEmpty()) {
/*  41 */       return Optional.empty();
/*     */     }
/*  43 */     return Optional.of(new LocationPredicate($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7));
/*     */   }
/*     */   
/*     */   public boolean matches(ServerLevel $$0, double $$1, double $$2, double $$3) {
/*  47 */     if (this.position.isPresent() && !((PositionPredicate)this.position.get()).matches($$1, $$2, $$3)) {
/*  48 */       return false;
/*     */     }
/*     */     
/*  51 */     if (this.dimension.isPresent() && this.dimension.get() != $$0.dimension()) {
/*  52 */       return false;
/*     */     }
/*     */     
/*  55 */     BlockPos $$4 = BlockPos.containing($$1, $$2, $$3);
/*  56 */     boolean $$5 = $$0.isLoaded($$4);
/*     */     
/*  58 */     if (this.biome.isPresent() && (!$$5 || !$$0.getBiome($$4).is(this.biome.get()))) {
/*  59 */       return false;
/*     */     }
/*  61 */     if (this.structure.isPresent() && (!$$5 || !$$0.structureManager().getStructureWithPieceAt($$4, this.structure.get()).isValid())) {
/*  62 */       return false;
/*     */     }
/*  64 */     if (this.smokey.isPresent() && (!$$5 || ((Boolean)this.smokey.get()).booleanValue() != CampfireBlock.isSmokeyPos((Level)$$0, $$4))) {
/*  65 */       return false;
/*     */     }
/*  67 */     if (this.light.isPresent() && !((LightPredicate)this.light.get()).matches($$0, $$4)) {
/*  68 */       return false;
/*     */     }
/*  70 */     if (this.block.isPresent() && !((BlockPredicate)this.block.get()).matches($$0, $$4)) {
/*  71 */       return false;
/*     */     }
/*  73 */     if (this.fluid.isPresent() && !((FluidPredicate)this.fluid.get()).matches($$0, $$4)) {
/*  74 */       return false;
/*     */     }
/*     */     
/*  77 */     return true;
/*     */   }
/*     */   private static final class PositionPredicate extends Record { private final MinMaxBounds.Doubles x; private final MinMaxBounds.Doubles y; private final MinMaxBounds.Doubles z; public static final Codec<PositionPredicate> CODEC;
/*  80 */     private PositionPredicate(MinMaxBounds.Doubles $$0, MinMaxBounds.Doubles $$1, MinMaxBounds.Doubles $$2) { this.x = $$0; this.y = $$1; this.z = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  80 */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate; } public MinMaxBounds.Doubles x() { return this.x; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #80	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/advancements/critereon/LocationPredicate$PositionPredicate;
/*  80 */       //   0	8	1	$$0	Ljava/lang/Object; } public MinMaxBounds.Doubles y() { return this.y; } public MinMaxBounds.Doubles z() { return this.z; } static {
/*  81 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "x", MinMaxBounds.Doubles.ANY).forGetter(PositionPredicate::x), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "y", MinMaxBounds.Doubles.ANY).forGetter(PositionPredicate::y), (App)ExtraCodecs.strictOptionalField(MinMaxBounds.Doubles.CODEC, "z", MinMaxBounds.Doubles.ANY).forGetter(PositionPredicate::z)).apply((Applicative)$$0, PositionPredicate::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static Optional<PositionPredicate> of(MinMaxBounds.Doubles $$0, MinMaxBounds.Doubles $$1, MinMaxBounds.Doubles $$2) {
/*  88 */       if ($$0.isAny() && $$1.isAny() && $$2.isAny()) {
/*  89 */         return Optional.empty();
/*     */       }
/*  91 */       return Optional.of(new PositionPredicate($$0, $$1, $$2));
/*     */     }
/*     */     
/*     */     public boolean matches(double $$0, double $$1, double $$2) {
/*  95 */       return (this.x.matches($$0) && this.y.matches($$1) && this.z.matches($$2));
/*     */     } }
/*     */   public static class Builder { private MinMaxBounds.Doubles x; private MinMaxBounds.Doubles y; private MinMaxBounds.Doubles z; private Optional<ResourceKey<Biome>> biome; private Optional<ResourceKey<Structure>> structure; private Optional<ResourceKey<Level>> dimension; private Optional<Boolean> smokey; private Optional<LightPredicate> light; private Optional<BlockPredicate> block; private Optional<FluidPredicate> fluid;
/*     */     
/*     */     public Builder() {
/* 100 */       this.x = MinMaxBounds.Doubles.ANY;
/* 101 */       this.y = MinMaxBounds.Doubles.ANY;
/* 102 */       this.z = MinMaxBounds.Doubles.ANY;
/*     */       
/* 104 */       this.biome = Optional.empty();
/* 105 */       this.structure = Optional.empty();
/* 106 */       this.dimension = Optional.empty();
/* 107 */       this.smokey = Optional.empty();
/*     */       
/* 109 */       this.light = Optional.empty();
/* 110 */       this.block = Optional.empty();
/* 111 */       this.fluid = Optional.empty();
/*     */     }
/*     */     public static Builder location() {
/* 114 */       return new Builder();
/*     */     }
/*     */     
/*     */     public static Builder inBiome(ResourceKey<Biome> $$0) {
/* 118 */       return location().setBiome($$0);
/*     */     }
/*     */     
/*     */     public static Builder inDimension(ResourceKey<Level> $$0) {
/* 122 */       return location().setDimension($$0);
/*     */     }
/*     */     
/*     */     public static Builder inStructure(ResourceKey<Structure> $$0) {
/* 126 */       return location().setStructure($$0);
/*     */     }
/*     */     
/*     */     public static Builder atYLocation(MinMaxBounds.Doubles $$0) {
/* 130 */       return location().setY($$0);
/*     */     }
/*     */     
/*     */     public Builder setX(MinMaxBounds.Doubles $$0) {
/* 134 */       this.x = $$0;
/* 135 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setY(MinMaxBounds.Doubles $$0) {
/* 139 */       this.y = $$0;
/* 140 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setZ(MinMaxBounds.Doubles $$0) {
/* 144 */       this.z = $$0;
/* 145 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setBiome(ResourceKey<Biome> $$0) {
/* 149 */       this.biome = Optional.of($$0);
/* 150 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setStructure(ResourceKey<Structure> $$0) {
/* 154 */       this.structure = Optional.of($$0);
/* 155 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setDimension(ResourceKey<Level> $$0) {
/* 159 */       this.dimension = Optional.of($$0);
/* 160 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setLight(LightPredicate.Builder $$0) {
/* 164 */       this.light = Optional.of($$0.build());
/* 165 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setBlock(BlockPredicate.Builder $$0) {
/* 169 */       this.block = Optional.of($$0.build());
/* 170 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setFluid(FluidPredicate.Builder $$0) {
/* 174 */       this.fluid = Optional.of($$0.build());
/* 175 */       return this;
/*     */     }
/*     */     
/*     */     public Builder setSmokey(boolean $$0) {
/* 179 */       this.smokey = Optional.of(Boolean.valueOf($$0));
/* 180 */       return this;
/*     */     }
/*     */     
/*     */     public LocationPredicate build() {
/* 184 */       Optional<LocationPredicate.PositionPredicate> $$0 = LocationPredicate.PositionPredicate.of(this.x, this.y, this.z);
/* 185 */       return new LocationPredicate($$0, this.biome, this.structure, this.dimension, this.smokey, this.light, this.block, this.fluid);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\LocationPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */