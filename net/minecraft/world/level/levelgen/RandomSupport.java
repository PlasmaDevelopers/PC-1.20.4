/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import com.google.common.base.Charsets;
/*    */ import com.google.common.hash.HashFunction;
/*    */ import com.google.common.hash.Hashing;
/*    */ import com.google.common.primitives.Longs;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class RandomSupport
/*    */ {
/*    */   public static final long GOLDEN_RATIO_64 = -7046029254386353131L;
/*    */   public static final long SILVER_RATIO_64 = 7640891576956012809L;
/* 18 */   private static final HashFunction MD5_128 = Hashing.md5();
/* 19 */   private static final AtomicLong SEED_UNIQUIFIER = new AtomicLong(8682522807148012L);
/*    */   
/*    */   @VisibleForTesting
/*    */   public static long mixStafford13(long $$0) {
/* 23 */     $$0 = ($$0 ^ $$0 >>> 30L) * -4658895280553007687L;
/* 24 */     $$0 = ($$0 ^ $$0 >>> 27L) * -7723592293110705685L;
/* 25 */     return $$0 ^ $$0 >>> 31L;
/*    */   }
/*    */   
/*    */   public static Seed128bit upgradeSeedTo128bitUnmixed(long $$0) {
/* 29 */     long $$1 = $$0 ^ 0x6A09E667F3BCC909L;
/* 30 */     long $$2 = $$1 + -7046029254386353131L;
/* 31 */     return new Seed128bit($$1, $$2);
/*    */   }
/*    */   
/*    */   public static Seed128bit upgradeSeedTo128bit(long $$0) {
/* 35 */     return upgradeSeedTo128bitUnmixed($$0).mixed();
/*    */   }
/*    */   
/*    */   public static Seed128bit seedFromHashOf(String $$0) {
/* 39 */     byte[] $$1 = MD5_128.hashString($$0, Charsets.UTF_8).asBytes();
/*    */     
/* 41 */     long $$2 = Longs.fromBytes($$1[0], $$1[1], $$1[2], $$1[3], $$1[4], $$1[5], $$1[6], $$1[7]);
/* 42 */     long $$3 = Longs.fromBytes($$1[8], $$1[9], $$1[10], $$1[11], $$1[12], $$1[13], $$1[14], $$1[15]);
/* 43 */     return new Seed128bit($$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long generateUniqueSeed() {
/* 50 */     return SEED_UNIQUIFIER.updateAndGet($$0 -> $$0 * 1181783497276652981L) ^ System.nanoTime();
/*    */   }
/*    */   public static final class Seed128bit extends Record { private final long seedLo; private final long seedHi;
/* 53 */     public Seed128bit(long $$0, long $$1) { this.seedLo = $$0; this.seedHi = $$1; } public final String toString() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;)Ljava/lang/String;
/*    */       //   6: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/* 53 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit; } public long seedLo() { return this.seedLo; } public final int hashCode() { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;)I
/*    */       //   6: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit; } public final boolean equals(Object $$0) { // Byte code:
/*    */       //   0: aload_0
/*    */       //   1: aload_1
/*    */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;Ljava/lang/Object;)Z
/*    */       //   7: ireturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #53	-> 0
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/RandomSupport$Seed128bit;
/* 53 */       //   0	8	1	$$0	Ljava/lang/Object; } public long seedHi() { return this.seedHi; }
/*    */      public Seed128bit xor(long $$0, long $$1) {
/* 55 */       return new Seed128bit(this.seedLo ^ $$0, this.seedHi ^ $$1);
/*    */     }
/*    */     
/*    */     public Seed128bit xor(Seed128bit $$0) {
/* 59 */       return xor($$0.seedLo, $$0.seedHi);
/*    */     }
/*    */     
/*    */     public Seed128bit mixed() {
/* 63 */       return new Seed128bit(RandomSupport.mixStafford13(this.seedLo), RandomSupport.mixStafford13(this.seedHi));
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\RandomSupport.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */