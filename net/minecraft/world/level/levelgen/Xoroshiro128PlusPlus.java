/*    */ package net.minecraft.world.level.levelgen;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import java.util.stream.LongStream;
/*    */ import net.minecraft.Util;
/*    */ 
/*    */ 
/*    */ public class Xoroshiro128PlusPlus
/*    */ {
/*    */   private long seedLo;
/*    */   private long seedHi;
/*    */   public static final Codec<Xoroshiro128PlusPlus> CODEC;
/*    */   
/*    */   static {
/* 16 */     CODEC = Codec.LONG_STREAM.comapFlatMap($$0 -> Util.fixedSize($$0, 2).map(()), $$0 -> LongStream.of(new long[] { $$0.seedLo, $$0.seedHi }));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Xoroshiro128PlusPlus(RandomSupport.Seed128bit $$0) {
/* 22 */     this($$0.seedLo(), $$0.seedHi());
/*    */   }
/*    */   
/*    */   public Xoroshiro128PlusPlus(long $$0, long $$1) {
/* 26 */     this.seedLo = $$0;
/* 27 */     this.seedHi = $$1;
/* 28 */     if ((this.seedLo | this.seedHi) == 0L) {
/* 29 */       this.seedLo = -7046029254386353131L;
/* 30 */       this.seedHi = 7640891576956012809L;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public long nextLong() {
/* 36 */     long $$0 = this.seedLo;
/* 37 */     long $$1 = this.seedHi;
/* 38 */     long $$2 = Long.rotateLeft($$0 + $$1, 17) + $$0;
/*    */     
/* 40 */     $$1 ^= $$0;
/* 41 */     this.seedLo = Long.rotateLeft($$0, 49) ^ $$1 ^ $$1 << 21L;
/* 42 */     this.seedHi = Long.rotateLeft($$1, 28);
/*    */     
/* 44 */     return $$2;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\Xoroshiro128PlusPlus.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */