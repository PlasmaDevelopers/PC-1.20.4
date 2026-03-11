/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.random.WeightedRandomList;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.biome.MobSpawnSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*    */ import net.minecraft.world.level.levelgen.structure.StructurePieceAccessor;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class NetherFortressStructure extends Structure {
/* 18 */   public static final WeightedRandomList<MobSpawnSettings.SpawnerData> FORTRESS_ENEMIES = WeightedRandomList.create((WeightedEntry[])new MobSpawnSettings.SpawnerData[] { new MobSpawnSettings.SpawnerData(EntityType.BLAZE, 10, 2, 3), new MobSpawnSettings.SpawnerData(EntityType.ZOMBIFIED_PIGLIN, 5, 4, 4), new MobSpawnSettings.SpawnerData(EntityType.WITHER_SKELETON, 8, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.SKELETON, 2, 5, 5), new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE, 3, 4, 4) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 26 */   public static final Codec<NetherFortressStructure> CODEC = simpleCodec(NetherFortressStructure::new);
/*    */   
/*    */   public NetherFortressStructure(Structure.StructureSettings $$0) {
/* 29 */     super($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 34 */     ChunkPos $$1 = $$0.chunkPos();
/*    */     
/* 36 */     BlockPos $$2 = new BlockPos($$1.getMinBlockX(), 64, $$1.getMinBlockZ());
/* 37 */     return Optional.of(new Structure.GenerationStub($$2, $$1 -> generatePieces($$1, $$0)));
/*    */   }
/*    */ 
/*    */   
/*    */   private static void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 42 */     NetherFortressPieces.StartPiece $$2 = new NetherFortressPieces.StartPiece((RandomSource)$$1.random(), $$1.chunkPos().getBlockX(2), $$1.chunkPos().getBlockZ(2));
/* 43 */     $$0.addPiece($$2);
/* 44 */     $$2.addChildren($$2, (StructurePieceAccessor)$$0, (RandomSource)$$1.random());
/*    */     
/* 46 */     List<StructurePiece> $$3 = $$2.pendingChildren;
/* 47 */     while (!$$3.isEmpty()) {
/* 48 */       int $$4 = $$1.random().nextInt($$3.size());
/* 49 */       StructurePiece $$5 = $$3.remove($$4);
/* 50 */       $$5.addChildren($$2, (StructurePieceAccessor)$$0, (RandomSource)$$1.random());
/*    */     } 
/*    */ 
/*    */     
/* 54 */     $$0.moveInsideHeights((RandomSource)$$1.random(), 48, 70);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 59 */     return StructureType.FORTRESS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\NetherFortressStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */