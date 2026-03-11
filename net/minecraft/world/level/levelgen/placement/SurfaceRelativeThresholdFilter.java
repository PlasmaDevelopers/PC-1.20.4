/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class SurfaceRelativeThresholdFilter extends PlacementFilter {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Heightmap.Types.CODEC.fieldOf("heightmap").forGetter(()), (App)Codec.INT.optionalFieldOf("min_inclusive", Integer.valueOf(-2147483648)).forGetter(()), (App)Codec.INT.optionalFieldOf("max_inclusive", Integer.valueOf(2147483647)).forGetter(())).apply((Applicative)$$0, SurfaceRelativeThresholdFilter::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SurfaceRelativeThresholdFilter> CODEC;
/*    */   
/*    */   private final Heightmap.Types heightmap;
/*    */   private final int minInclusive;
/*    */   private final int maxInclusive;
/*    */   
/*    */   private SurfaceRelativeThresholdFilter(Heightmap.Types $$0, int $$1, int $$2) {
/* 25 */     this.heightmap = $$0;
/* 26 */     this.minInclusive = $$1;
/* 27 */     this.maxInclusive = $$2;
/*    */   }
/*    */   
/*    */   public static SurfaceRelativeThresholdFilter of(Heightmap.Types $$0, int $$1, int $$2) {
/* 31 */     return new SurfaceRelativeThresholdFilter($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 36 */     long $$3 = $$0.getHeight(this.heightmap, $$2.getX(), $$2.getZ());
/*    */     
/* 38 */     long $$4 = $$3 + this.minInclusive;
/* 39 */     long $$5 = $$3 + this.maxInclusive;
/*    */     
/* 41 */     return ($$4 <= $$2.getY() && $$2.getY() <= $$5);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 46 */     return PlacementModifierType.SURFACE_RELATIVE_THRESHOLD_FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\SurfaceRelativeThresholdFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */