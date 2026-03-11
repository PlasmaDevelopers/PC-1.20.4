/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*    */ 
/*    */ public class OceanRuinStructure extends Structure {
/*    */   static {
/* 16 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)settingsCodec($$0), (App)Type.CODEC.fieldOf("biome_temp").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("large_probability").forGetter(()), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("cluster_probability").forGetter(())).apply((Applicative)$$0, OceanRuinStructure::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<OceanRuinStructure> CODEC;
/*    */   
/*    */   public final Type biomeTemp;
/*    */   
/*    */   public final float largeProbability;
/*    */   public final float clusterProbability;
/*    */   
/*    */   public OceanRuinStructure(Structure.StructureSettings $$0, Type $$1, float $$2, float $$3) {
/* 28 */     super($$0);
/* 29 */     this.biomeTemp = $$1;
/* 30 */     this.largeProbability = $$2;
/* 31 */     this.clusterProbability = $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 36 */     return onTopOfChunkCenter($$0, Heightmap.Types.OCEAN_FLOOR_WG, $$1 -> generatePieces($$1, $$0));
/*    */   }
/*    */   
/*    */   private void generatePieces(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/* 40 */     BlockPos $$2 = new BlockPos($$1.chunkPos().getMinBlockX(), 90, $$1.chunkPos().getMinBlockZ());
/* 41 */     Rotation $$3 = Rotation.getRandom((RandomSource)$$1.random());
/* 42 */     OceanRuinPieces.addPieces($$1.structureTemplateManager(), $$2, $$3, (StructurePieceAccessor)$$0, (RandomSource)$$1.random(), this);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 47 */     return StructureType.OCEAN_RUIN;
/*    */   }
/*    */   
/*    */   public enum Type implements StringRepresentable {
/* 51 */     WARM("warm"),
/* 52 */     COLD("cold");
/*    */ 
/*    */     
/* 55 */     public static final Codec<Type> CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values); private final String name;
/*    */     static {
/*    */     
/*    */     }
/*    */     Type(String $$0) {
/* 60 */       this.name = $$0;
/*    */     }
/*    */     
/*    */     public String getName() {
/* 64 */       return this.name;
/*    */     }
/*    */ 
/*    */     
/*    */     public String getSerializedName() {
/* 69 */       return this.name;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\OceanRuinStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */