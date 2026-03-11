/*    */ package net.minecraft.advancements.critereon;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.world.level.material.Fluid;
/*    */ 
/*    */ public final class FluidPredicate extends Record {
/*    */   private final Optional<TagKey<Fluid>> tag;
/*    */   private final Optional<Holder<Fluid>> fluid;
/*    */   private final Optional<StatePropertiesPredicate> properties;
/*    */   public static final Codec<FluidPredicate> CODEC;
/*    */   
/* 17 */   public FluidPredicate(Optional<TagKey<Fluid>> $$0, Optional<Holder<Fluid>> $$1, Optional<StatePropertiesPredicate> $$2) { this.tag = $$0; this.fluid = $$1; this.properties = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/advancements/critereon/FluidPredicate;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 17 */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/FluidPredicate; } public Optional<TagKey<Fluid>> tag() { return this.tag; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/advancements/critereon/FluidPredicate;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/advancements/critereon/FluidPredicate; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/advancements/critereon/FluidPredicate;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #17	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/advancements/critereon/FluidPredicate;
/* 17 */     //   0	8	1	$$0	Ljava/lang/Object; } public Optional<Holder<Fluid>> fluid() { return this.fluid; } public Optional<StatePropertiesPredicate> properties() { return this.properties; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 23 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ExtraCodecs.strictOptionalField(TagKey.codec(Registries.FLUID), "tag").forGetter(FluidPredicate::tag), (App)ExtraCodecs.strictOptionalField(BuiltInRegistries.FLUID.holderByNameCodec(), "fluid").forGetter(FluidPredicate::fluid), (App)ExtraCodecs.strictOptionalField(StatePropertiesPredicate.CODEC, "state").forGetter(FluidPredicate::properties)).apply((Applicative)$$0, FluidPredicate::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean matches(ServerLevel $$0, BlockPos $$1) {
/* 30 */     if (!$$0.isLoaded($$1)) {
/* 31 */       return false;
/*    */     }
/* 33 */     FluidState $$2 = $$0.getFluidState($$1);
/*    */     
/* 35 */     if (this.tag.isPresent() && !$$2.is(this.tag.get())) {
/* 36 */       return false;
/*    */     }
/* 38 */     if (this.fluid.isPresent() && !$$2.is((Fluid)((Holder)this.fluid.get()).value())) {
/* 39 */       return false;
/*    */     }
/* 41 */     if (this.properties.isPresent() && !((StatePropertiesPredicate)this.properties.get()).matches($$2)) {
/* 42 */       return false;
/*    */     }
/* 44 */     return true;
/*    */   }
/*    */   
/*    */   public static class Builder {
/* 48 */     private Optional<Holder<Fluid>> fluid = Optional.empty();
/* 49 */     private Optional<TagKey<Fluid>> fluids = Optional.empty();
/* 50 */     private Optional<StatePropertiesPredicate> properties = Optional.empty();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public static Builder fluid() {
/* 56 */       return new Builder();
/*    */     }
/*    */     
/*    */     public Builder of(Fluid $$0) {
/* 60 */       this.fluid = Optional.of($$0.builtInRegistryHolder());
/* 61 */       return this;
/*    */     }
/*    */     
/*    */     public Builder of(TagKey<Fluid> $$0) {
/* 65 */       this.fluids = Optional.of($$0);
/* 66 */       return this;
/*    */     }
/*    */     
/*    */     public Builder setProperties(StatePropertiesPredicate $$0) {
/* 70 */       this.properties = Optional.of($$0);
/* 71 */       return this;
/*    */     }
/*    */     
/*    */     public FluidPredicate build() {
/* 75 */       return new FluidPredicate(this.fluids, this.fluid, this.properties);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\critereon\FluidPredicate.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */