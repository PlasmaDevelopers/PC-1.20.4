/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.ConstantInt;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ 
/*    */ public class RandomOffsetPlacement extends PlacementModifier {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)IntProvider.codec(-16, 16).fieldOf("xz_spread").forGetter(()), (App)IntProvider.codec(-16, 16).fieldOf("y_spread").forGetter(())).apply((Applicative)$$0, RandomOffsetPlacement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<RandomOffsetPlacement> CODEC;
/*    */   private final IntProvider xzSpread;
/*    */   private final IntProvider ySpread;
/*    */   
/*    */   public static RandomOffsetPlacement of(IntProvider $$0, IntProvider $$1) {
/* 25 */     return new RandomOffsetPlacement($$0, $$1);
/*    */   }
/*    */   
/*    */   public static RandomOffsetPlacement vertical(IntProvider $$0) {
/* 29 */     return new RandomOffsetPlacement((IntProvider)ConstantInt.of(0), $$0);
/*    */   }
/*    */   
/*    */   public static RandomOffsetPlacement horizontal(IntProvider $$0) {
/* 33 */     return new RandomOffsetPlacement($$0, (IntProvider)ConstantInt.of(0));
/*    */   }
/*    */   
/*    */   private RandomOffsetPlacement(IntProvider $$0, IntProvider $$1) {
/* 37 */     this.xzSpread = $$0;
/* 38 */     this.ySpread = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 43 */     int $$3 = $$2.getX() + this.xzSpread.sample($$1);
/* 44 */     int $$4 = $$2.getY() + this.ySpread.sample($$1);
/* 45 */     int $$5 = $$2.getZ() + this.xzSpread.sample($$1);
/* 46 */     return Stream.of(new BlockPos($$3, $$4, $$5));
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 51 */     return PlacementModifierType.RANDOM_OFFSET;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\RandomOffsetPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */