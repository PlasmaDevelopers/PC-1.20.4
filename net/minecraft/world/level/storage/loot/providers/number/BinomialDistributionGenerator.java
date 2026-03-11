/*    */ package net.minecraft.world.level.storage.loot.providers.number;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.world.level.storage.loot.LootContext;
/*    */ 
/*    */ public final class BinomialDistributionGenerator extends Record implements NumberProvider {
/*    */   private final NumberProvider n;
/*    */   private final NumberProvider p;
/*    */   public static final Codec<BinomialDistributionGenerator> CODEC;
/*    */   
/* 12 */   public BinomialDistributionGenerator(NumberProvider $$0, NumberProvider $$1) { this.n = $$0; this.p = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 12 */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator; } public NumberProvider n() { return this.n; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/providers/number/BinomialDistributionGenerator;
/* 12 */     //   0	8	1	$$0	Ljava/lang/Object; } public NumberProvider p() { return this.p; }
/*    */ 
/*    */   
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)NumberProviders.CODEC.fieldOf("n").forGetter(BinomialDistributionGenerator::n), (App)NumberProviders.CODEC.fieldOf("p").forGetter(BinomialDistributionGenerator::p)).apply((Applicative)$$0, BinomialDistributionGenerator::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LootNumberProviderType getType() {
/* 23 */     return NumberProviders.BINOMIAL;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getInt(LootContext $$0) {
/* 30 */     int $$1 = this.n.getInt($$0);
/* 31 */     float $$2 = this.p.getFloat($$0);
/* 32 */     RandomSource $$3 = $$0.getRandom();
/* 33 */     int $$4 = 0;
/* 34 */     for (int $$5 = 0; $$5 < $$1; $$5++) {
/* 35 */       if ($$3.nextFloat() < $$2) {
/* 36 */         $$4++;
/*    */       }
/*    */     } 
/*    */     
/* 40 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getFloat(LootContext $$0) {
/* 45 */     return getInt($$0);
/*    */   }
/*    */   
/*    */   public static BinomialDistributionGenerator binomial(int $$0, float $$1) {
/* 49 */     return new BinomialDistributionGenerator(ConstantValue.exactly($$0), ConstantValue.exactly($$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<LootContextParam<?>> getReferencedContextParams() {
/* 54 */     return (Set<LootContextParam<?>>)Sets.union(this.n.getReferencedContextParams(), this.p.getReferencedContextParams());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\providers\number\BinomialDistributionGenerator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */