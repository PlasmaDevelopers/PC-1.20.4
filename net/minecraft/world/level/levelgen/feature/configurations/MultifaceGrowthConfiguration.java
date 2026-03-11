/*    */ package net.minecraft.world.level.levelgen.feature.configurations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function7;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Block;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.MultifaceBlock;
/*    */ 
/*    */ public class MultifaceGrowthConfiguration implements FeatureConfiguration {
/*    */   public static final Codec<MultifaceGrowthConfiguration> CODEC;
/*    */   
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").flatXmap(MultifaceGrowthConfiguration::apply, DataResult::success).orElse(Blocks.GLOW_LICHEN).forGetter(()), (App)Codec.intRange(1, 64).fieldOf("search_range").orElse(Integer.valueOf(10)).forGetter(()), (App)Codec.BOOL.fieldOf("can_place_on_floor").orElse(Boolean.valueOf(false)).forGetter(()), (App)Codec.BOOL.fieldOf("can_place_on_ceiling").orElse(Boolean.valueOf(false)).forGetter(()), (App)Codec.BOOL.fieldOf("can_place_on_wall").orElse(Boolean.valueOf(false)).forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("chance_of_spreading").orElse(Float.valueOf(0.5F)).forGetter(()), (App)RegistryCodecs.homogeneousList(Registries.BLOCK).fieldOf("can_be_placed_on").forGetter(())).apply((Applicative)$$0, MultifaceGrowthConfiguration::new));
/*    */   }
/*    */   public final MultifaceBlock placeBlock; public final int searchRange;
/*    */   public final boolean canPlaceOnFloor;
/*    */   public final boolean canPlaceOnCeiling;
/*    */   public final boolean canPlaceOnWall;
/*    */   public final float chanceOfSpreading;
/*    */   public final HolderSet<Block> canBePlacedOn;
/*    */   private final ObjectArrayList<Direction> validDirections;
/*    */   
/*    */   private static DataResult<MultifaceBlock> apply(Block $$0) {
/* 32 */     MultifaceBlock $$1 = (MultifaceBlock)$$0; return ($$0 instanceof MultifaceBlock) ? 
/* 33 */       DataResult.success($$1) : 
/* 34 */       DataResult.error(() -> "Growth block should be a multiface block");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MultifaceGrowthConfiguration(MultifaceBlock $$0, int $$1, boolean $$2, boolean $$3, boolean $$4, float $$5, HolderSet<Block> $$6) {
/* 49 */     this.placeBlock = $$0;
/* 50 */     this.searchRange = $$1;
/* 51 */     this.canPlaceOnFloor = $$2;
/* 52 */     this.canPlaceOnCeiling = $$3;
/* 53 */     this.canPlaceOnWall = $$4;
/* 54 */     this.chanceOfSpreading = $$5;
/* 55 */     this.canBePlacedOn = $$6;
/*    */     
/* 57 */     this.validDirections = new ObjectArrayList(6);
/* 58 */     if ($$3) {
/* 59 */       this.validDirections.add(Direction.UP);
/*    */     }
/* 61 */     if ($$2) {
/* 62 */       this.validDirections.add(Direction.DOWN);
/*    */     }
/* 64 */     if ($$4) {
/* 65 */       Objects.requireNonNull(this.validDirections); Direction.Plane.HORIZONTAL.forEach(this.validDirections::add);
/*    */     } 
/*    */   }
/*    */   
/*    */   public List<Direction> getShuffledDirectionsExcept(RandomSource $$0, Direction $$1) {
/* 70 */     return Util.toShuffledList(this.validDirections.stream().filter($$1 -> ($$1 != $$0)), $$0);
/*    */   }
/*    */   
/*    */   public List<Direction> getShuffledDirections(RandomSource $$0) {
/* 74 */     return Util.shuffledCopy(this.validDirections, $$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\configurations\MultifaceGrowthConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */