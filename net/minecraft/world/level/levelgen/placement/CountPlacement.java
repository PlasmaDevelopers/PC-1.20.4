/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.valueproviders.ConstantInt;
/*    */ import net.minecraft.util.valueproviders.IntProvider;
/*    */ 
/*    */ public class CountPlacement extends RepeatingPlacement {
/*    */   public static final Codec<CountPlacement> CODEC;
/*    */   
/*    */   static {
/* 14 */     CODEC = IntProvider.codec(0, 256).fieldOf("count").xmap(CountPlacement::new, $$0 -> $$0.count).codec();
/*    */   }
/*    */   private final IntProvider count;
/*    */   
/*    */   private CountPlacement(IntProvider $$0) {
/* 19 */     this.count = $$0;
/*    */   }
/*    */   
/*    */   public static CountPlacement of(IntProvider $$0) {
/* 23 */     return new CountPlacement($$0);
/*    */   }
/*    */   
/*    */   public static CountPlacement of(int $$0) {
/* 27 */     return of((IntProvider)ConstantInt.of($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int count(RandomSource $$0, BlockPos $$1) {
/* 32 */     return this.count.sample($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 37 */     return PlacementModifierType.COUNT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\CountPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */