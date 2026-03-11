/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class EndCityStructure extends Structure {
/* 16 */   public static final Codec<EndCityStructure> CODEC = simpleCodec(EndCityStructure::new);
/*    */   
/*    */   public EndCityStructure(Structure.StructureSettings $$0) {
/* 19 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 24 */     Rotation $$1 = Rotation.getRandom((RandomSource)$$0.random());
/* 25 */     BlockPos $$2 = getLowestYIn5by5BoxOffset7Blocks($$0, $$1);
/*    */ 
/*    */     
/* 28 */     if ($$2.getY() < 60) {
/* 29 */       return Optional.empty();
/*    */     }
/*    */     
/* 32 */     return Optional.of(new Structure.GenerationStub($$2, $$3 -> generatePieces($$3, $$0, $$1, $$2)));
/*    */   }
/*    */   
/*    */   private void generatePieces(StructurePiecesBuilder $$0, BlockPos $$1, Rotation $$2, Structure.GenerationContext $$3) {
/* 36 */     List<StructurePiece> $$4 = Lists.newArrayList();
/* 37 */     EndCityPieces.startHouseTower($$3.structureTemplateManager(), $$1, $$2, $$4, (RandomSource)$$3.random());
/*    */     
/* 39 */     Objects.requireNonNull($$0); $$4.forEach($$0::addPiece);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 44 */     return StructureType.END_CITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\EndCityStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */