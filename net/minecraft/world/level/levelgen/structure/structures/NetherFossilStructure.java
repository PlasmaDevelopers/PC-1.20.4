/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.NoiseColumn;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.levelgen.WorldGenerationContext;
/*    */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class NetherFossilStructure extends Structure {
/*    */   static {
/* 21 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)settingsCodec($$0), (App)HeightProvider.CODEC.fieldOf("height").forGetter(())).apply((Applicative)$$0, NetherFossilStructure::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<NetherFossilStructure> CODEC;
/*    */   public final HeightProvider height;
/*    */   
/*    */   public NetherFossilStructure(Structure.StructureSettings $$0, HeightProvider $$1) {
/* 29 */     super($$0);
/* 30 */     this.height = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 35 */     WorldgenRandom $$1 = $$0.random();
/* 36 */     int $$2 = $$0.chunkPos().getMinBlockX() + $$1.nextInt(16);
/* 37 */     int $$3 = $$0.chunkPos().getMinBlockZ() + $$1.nextInt(16);
/*    */     
/* 39 */     int $$4 = $$0.chunkGenerator().getSeaLevel();
/*    */ 
/*    */     
/* 42 */     WorldGenerationContext $$5 = new WorldGenerationContext($$0.chunkGenerator(), $$0.heightAccessor());
/*    */     
/* 44 */     int $$6 = this.height.sample((RandomSource)$$1, $$5);
/*    */     
/* 46 */     NoiseColumn $$7 = $$0.chunkGenerator().getBaseColumn($$2, $$3, $$0.heightAccessor(), $$0.randomState());
/*    */     
/* 48 */     BlockPos.MutableBlockPos $$8 = new BlockPos.MutableBlockPos($$2, $$6, $$3);
/* 49 */     while ($$6 > $$4) {
/* 50 */       BlockState $$9 = $$7.getBlock($$6);
/*    */       
/* 52 */       $$6--;
/*    */       
/* 54 */       BlockState $$10 = $$7.getBlock($$6);
/* 55 */       if ($$9.isAir() && ($$10.is(Blocks.SOUL_SAND) || $$10.isFaceSturdy((BlockGetter)EmptyBlockGetter.INSTANCE, (BlockPos)$$8.setY($$6), Direction.UP))) {
/*    */         break;
/*    */       }
/*    */     } 
/*    */     
/* 60 */     if ($$6 <= $$4) {
/* 61 */       return Optional.empty();
/*    */     }
/*    */     
/* 64 */     BlockPos $$11 = new BlockPos($$2, $$6, $$3);
/* 65 */     return Optional.of(new Structure.GenerationStub($$11, $$3 -> NetherFossilPieces.addPieces($$0.structureTemplateManager(), (StructurePieceAccessor)$$3, (RandomSource)$$1, $$2)));
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 70 */     return StructureType.NETHER_FOSSIL;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFossilStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */