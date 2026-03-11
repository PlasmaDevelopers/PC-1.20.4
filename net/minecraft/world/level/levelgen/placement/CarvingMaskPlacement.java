/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.levelgen.GenerationStep;
/*    */ 
/*    */ public class CarvingMaskPlacement extends PlacementModifier {
/*    */   public static final Codec<CarvingMaskPlacement> CODEC;
/*    */   
/*    */   static {
/* 15 */     CODEC = GenerationStep.Carving.CODEC.fieldOf("step").xmap(CarvingMaskPlacement::new, $$0 -> $$0.step).codec();
/*    */   }
/*    */   private final GenerationStep.Carving step;
/*    */   
/*    */   private CarvingMaskPlacement(GenerationStep.Carving $$0) {
/* 20 */     this.step = $$0;
/*    */   }
/*    */   
/*    */   public static CarvingMaskPlacement forStep(GenerationStep.Carving $$0) {
/* 24 */     return new CarvingMaskPlacement($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 29 */     ChunkPos $$3 = new ChunkPos($$2);
/* 30 */     return $$0.getCarvingMask($$3, this.step).stream($$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 35 */     return PlacementModifierType.CARVING_MASK_PLACEMENT;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\CarvingMaskPlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */