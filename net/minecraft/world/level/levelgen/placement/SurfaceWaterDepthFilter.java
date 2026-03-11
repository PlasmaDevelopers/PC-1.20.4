/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class SurfaceWaterDepthFilter
/*    */   extends PlacementFilter {
/*    */   static {
/* 15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Codec.INT.fieldOf("max_water_depth").forGetter(())).apply((Applicative)$$0, SurfaceWaterDepthFilter::new));
/*    */   }
/*    */   
/*    */   public static final Codec<SurfaceWaterDepthFilter> CODEC;
/*    */   private final int maxWaterDepth;
/*    */   
/*    */   private SurfaceWaterDepthFilter(int $$0) {
/* 22 */     this.maxWaterDepth = $$0;
/*    */   }
/*    */   
/*    */   public static SurfaceWaterDepthFilter forMaxDepth(int $$0) {
/* 26 */     return new SurfaceWaterDepthFilter($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean shouldPlace(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 31 */     int $$3 = $$0.getHeight(Heightmap.Types.OCEAN_FLOOR, $$2.getX(), $$2.getZ());
/* 32 */     int $$4 = $$0.getHeight(Heightmap.Types.WORLD_SURFACE, $$2.getX(), $$2.getZ());
/*    */     
/* 34 */     return ($$4 - $$3 <= this.maxWaterDepth);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 39 */     return PlacementModifierType.SURFACE_WATER_DEPTH_FILTER;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\SurfaceWaterDepthFilter.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */