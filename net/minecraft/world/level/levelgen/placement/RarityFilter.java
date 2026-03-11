/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ public class RarityFilter extends PlacementFilter {
/*    */   public static final Codec<RarityFilter> CODEC;
/*    */   
/*    */   static {
/* 12 */     CODEC = ExtraCodecs.POSITIVE_INT.fieldOf("chance").xmap(RarityFilter::new, $$0 -> Integer.valueOf($$0.chance)).codec();
/*    */   }
/*    */   private final int chance;
/*    */   
/*    */   private RarityFilter(int $$0) {
/* 17 */     this.chance = $$0;
/*    */   }
/*    */   
/*    */   public static RarityFilter onAverageOnceEvery(int $$0) {
/* 21 */     return new RarityFilter($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 26 */     return ($$1.nextFloat() < 1.0F / this.chance);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 31 */     return PlacementModifierType.RARITY_FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\RarityFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */