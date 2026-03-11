/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class ShipwreckStructure extends Structure {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)settingsCodec($$0), (App)Codec.BOOL.fieldOf("is_beached").forGetter(())).apply((Applicative)$$0, ShipwreckStructure::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ShipwreckStructure> CODEC;
/*    */   public final boolean isBeached;
/*    */   
/*    */   public ShipwreckStructure(Structure.StructureSettings $$0, boolean $$1) {
/* 24 */     super($$0);
/* 25 */     this.isBeached = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 30 */     Heightmap.Types $$1 = this.isBeached ? Heightmap.Types.WORLD_SURFACE_WG : Heightmap.Types.OCEAN_FLOOR_WG;
/* 31 */     return onTopOfChunkCenter($$0, $$1, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 35 */     Rotation $$2 = Rotation.getRandom((RandomSource)$$1.random());
/* 36 */     BlockPos $$3 = new BlockPos($$1.chunkPos().getMinBlockX(), 90, $$1.chunkPos().getMinBlockZ());
/* 37 */     ShipwreckPieces.addPieces($$1.structureTemplateManager(), $$3, $$2, (StructurePieceAccessor)$$0, (RandomSource)$$1.random(), this.isBeached);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 42 */     return StructureType.SHIPWRECK;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\ShipwreckStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */