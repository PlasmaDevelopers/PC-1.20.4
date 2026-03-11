/*    */ package net.minecraft.world.level.storage.loot.functions;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class BinomialWithBonusCount
/*    */   extends Record
/*    */   implements ApplyBonusCount.Formula
/*    */ {
/*    */   private final int extraRounds;
/*    */   private final float probability;
/*    */   private static final Codec<BinomialWithBonusCount> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #38	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #38	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #38	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/level/storage/loot/functions/ApplyBonusCount$BinomialWithBonusCount;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   BinomialWithBonusCount(int $$0, float $$1) {
/* 38 */     this.extraRounds = $$0; this.probability = $$1; } public int extraRounds() { return this.extraRounds; } public float probability() { return this.probability; } static {
/* 39 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("extra").forGetter(BinomialWithBonusCount::extraRounds), (App)Codec.FLOAT.fieldOf("probability").forGetter(BinomialWithBonusCount::probability)).apply((Applicative)$$0, BinomialWithBonusCount::new));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 44 */   public static final ApplyBonusCount.FormulaType TYPE = new ApplyBonusCount.FormulaType(new ResourceLocation("binomial_with_bonus_count"), (Codec)CODEC);
/*    */ 
/*    */   
/*    */   public int calculateNewCount(RandomSource $$0, int $$1, int $$2) {
/* 48 */     for (int $$3 = 0; $$3 < $$2 + this.extraRounds; $$3++) {
/* 49 */       if ($$0.nextFloat() < this.probability) {
/* 50 */         $$1++;
/*    */       }
/*    */     } 
/* 53 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public ApplyBonusCount.FormulaType getType() {
/* 58 */     return TYPE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\storage\loot\functions\ApplyBonusCount$BinomialWithBonusCount.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */