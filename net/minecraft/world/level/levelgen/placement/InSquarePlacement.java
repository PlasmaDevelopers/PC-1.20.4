/*    */ package net.minecraft.world.level.levelgen.placement;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InSquarePlacement
/*    */   extends PlacementModifier
/*    */ {
/* 16 */   private static final InSquarePlacement INSTANCE = new InSquarePlacement();
/*    */   
/* 18 */   public static final Codec<InSquarePlacement> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/*    */   public static InSquarePlacement spread() {
/* 21 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public Stream<BlockPos> getPositions(PlacementContext $$0, RandomSource $$1, BlockPos $$2) {
/* 26 */     int $$3 = $$1.nextInt(16) + $$2.getX();
/* 27 */     int $$4 = $$1.nextInt(16) + $$2.getZ();
/*    */     
/* 29 */     return Stream.of(new BlockPos($$3, $$2.getY(), $$4));
/*    */   }
/*    */ 
/*    */   
/*    */   public PlacementModifierType<?> type() {
/* 34 */     return PlacementModifierType.IN_SQUARE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\placement\InSquarePlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */