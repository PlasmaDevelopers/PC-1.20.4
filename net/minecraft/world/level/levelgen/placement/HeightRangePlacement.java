/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.TrapezoidHeight;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
/*    */ 
/*    */ public class HeightRangePlacement extends PlacementModifier {
/*    */   static {
/* 18 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)HeightProvider.CODEC.fieldOf("height").forGetter(())).apply((Applicative)$$0, HeightRangePlacement::new));
/*    */   }
/*    */   
/*    */   public static final Codec<HeightRangePlacement> CODEC;
/*    */   private final HeightProvider height;
/*    */   
/*    */   private HeightRangePlacement(HeightProvider $$0) {
/* 25 */     this.height = $$0;
/*    */   }
/*    */   
/*    */   public static HeightRangePlacement of(HeightProvider $$0) {
/* 29 */     return new HeightRangePlacement($$0);
/*    */   }
/*    */   
/*    */   public static HeightRangePlacement uniform(VerticalAnchor $$0, VerticalAnchor $$1) {
/* 33 */     return of((HeightProvider)UniformHeight.of($$0, $$1));
/*    */   }
/*    */   
/*    */   public static HeightRangePlacement triangle(VerticalAnchor $$0, VerticalAnchor $$1) {
/* 37 */     return of((HeightProvider)TrapezoidHeight.of($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 42 */     return Stream.of($$2.atY(this.height.sample($$1, $$0)));
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 47 */     return PlacementModifierType.HEIGHT_RANGE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\HeightRangePlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */