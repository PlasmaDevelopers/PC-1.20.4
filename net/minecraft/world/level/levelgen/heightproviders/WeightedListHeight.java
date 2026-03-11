/*    */ package net.minecraft.world.level.levelgen.heightproviders;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.SimpleWeightedRandomList;
/*    */ 
/*    */ public class WeightedListHeight extends HeightProvider {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)SimpleWeightedRandomList.wrappedCodec(HeightProvider.CODEC).fieldOf("distribution").forGetter(())).apply((Applicative)$$0, WeightedListHeight::new));
/*    */   }
/*    */   
/*    */   public static final Codec<WeightedListHeight> CODEC;
/*    */   private final SimpleWeightedRandomList<HeightProvider> distribution;
/*    */   
/*    */   public WeightedListHeight(SimpleWeightedRandomList<HeightProvider> $$0) {
/* 17 */     this.distribution = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int sample(RandomSource $$0, WorldGenerationContext $$1) {
/* 22 */     return ((HeightProvider)this.distribution.getRandomValue($$0).orElseThrow(IllegalStateException::new)).sample($$0, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public HeightProviderType<?> getType() {
/* 27 */     return HeightProviderType.WEIGHTED_LIST;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\heightproviders\WeightedListHeight.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */