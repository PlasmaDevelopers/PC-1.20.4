/*    */ package net.minecraft.world;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.RandomSupport;
/*    */ import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
/*    */ 
/*    */ public class RandomSequence {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)XoroshiroRandomSource.CODEC.fieldOf("source").forGetter(())).apply((Applicative)$$0, RandomSequence::new));
/*    */   }
/*    */   
/*    */   public static final Codec<RandomSequence> CODEC;
/*    */   private final XoroshiroRandomSource source;
/*    */   
/*    */   public RandomSequence(XoroshiroRandomSource $$0) {
/* 20 */     this.source = $$0;
/*    */   }
/*    */   
/*    */   public RandomSequence(long $$0, ResourceLocation $$1) {
/* 24 */     this(createSequence($$0, Optional.of($$1)));
/*    */   }
/*    */   
/*    */   public RandomSequence(long $$0, Optional<ResourceLocation> $$1) {
/* 28 */     this(createSequence($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   private static XoroshiroRandomSource createSequence(long $$0, Optional<ResourceLocation> $$1) {
/* 33 */     RandomSupport.Seed128bit $$2 = RandomSupport.upgradeSeedTo128bitUnmixed($$0);
/* 34 */     if ($$1.isPresent()) {
/* 35 */       $$2 = $$2.xor(seedForKey($$1.get()));
/*    */     }
/* 37 */     return new XoroshiroRandomSource($$2.mixed());
/*    */   }
/*    */   
/*    */   public static RandomSupport.Seed128bit seedForKey(ResourceLocation $$0) {
/* 41 */     return RandomSupport.seedFromHashOf($$0.toString());
/*    */   }
/*    */   
/*    */   public RandomSource random() {
/* 45 */     return (RandomSource)this.source;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\RandomSequence.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */