/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class UniformGenerator extends Record implements NumberProvider {
/*    */   private final NumberProvider min;
/*    */   private final NumberProvider max;
/*    */   public static final Codec<UniformGenerator> CODEC;
/*    */   
/* 12 */   public UniformGenerator(NumberProvider $$0, NumberProvider $$1) { this.min = $$0; this.max = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator; } public NumberProvider min() { return this.min; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/UniformGenerator;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public NumberProvider max() { return this.max; }
/*    */ 
/*    */   
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)NumberProviders.CODEC.fieldOf("min").forGetter(UniformGenerator::min), (App)NumberProviders.CODEC.fieldOf("max").forGetter(UniformGenerator::max)).apply((Applicative)$$0, UniformGenerator::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootNumberProviderType getType() {
/* 23 */     return NumberProviders.UNIFORM;
/*    */   }
/*    */   
/*    */   public static UniformGenerator between(float $$0, float $$1) {
/* 27 */     return new UniformGenerator(ConstantValue.exactly($$0), ConstantValue.exactly($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getInt(LootContext $$0) {
/* 32 */     return Mth.nextInt($$0.getRandom(), this.min.getInt($$0), this.max.getInt($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(LootContext $$0) {
/* 37 */     return Mth.nextFloat($$0.getRandom(), this.min.getFloat($$0), this.max.getFloat($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 42 */     return (Set<LootContextParam<?>>)Sets.union(this.min.getReferencedContextParams(), this.max.getReferencedContextParams());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\UniformGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */