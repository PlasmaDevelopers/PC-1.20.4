/*    */ package net.minecraft.world.level.levelgen.structure.placement;
/*    */ import com.mojang.datafixers.Products;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function9;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ 
/*    */ public class ConcentricRingsStructurePlacement extends StructurePlacement {
/*    */   public static final Codec<ConcentricRingsStructurePlacement> CODEC;
/*    */   private final int distance;
/*    */   
/*    */   private static Products.P9<RecordCodecBuilder.Mu<ConcentricRingsStructurePlacement>, Vec3i, StructurePlacement.FrequencyReductionMethod, Float, Integer, Optional<StructurePlacement.ExclusionZone>, Integer, Integer, Integer, HolderSet<Biome>> codec(RecordCodecBuilder.Instance<ConcentricRingsStructurePlacement> $$0) {
/* 19 */     Products.P5<RecordCodecBuilder.Mu<ConcentricRingsStructurePlacement>, Vec3i, StructurePlacement.FrequencyReductionMethod, Float, Integer, Optional<StructurePlacement.ExclusionZone>> $$1 = placementCodec($$0);
/* 20 */     Products.P4<RecordCodecBuilder.Mu<ConcentricRingsStructurePlacement>, Integer, Integer, Integer, HolderSet<Biome>> $$2 = $$0.group(
/* 21 */         (App)Codec.intRange(0, 1023).fieldOf("distance").forGetter(ConcentricRingsStructurePlacement::distance), 
/* 22 */         (App)Codec.intRange(0, 1023).fieldOf("spread").forGetter(ConcentricRingsStructurePlacement::spread), 
/* 23 */         (App)Codec.intRange(1, 4095).fieldOf("count").forGetter(ConcentricRingsStructurePlacement::count), 
/* 24 */         (App)RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("preferred_biomes").forGetter(ConcentricRingsStructurePlacement::preferredBiomes));
/*    */     
/* 26 */     return new Products.P9($$1.t1(), $$1.t2(), $$1.t3(), $$1.t4(), $$1.t5(), $$2.t1(), $$2.t2(), $$2.t3(), $$2.t4());
/*    */   } private final int spread; private final int count; private final HolderSet<Biome> preferredBiomes;
/*    */   static {
/* 29 */     CODEC = RecordCodecBuilder.create($$0 -> codec($$0).apply((Applicative)$$0, ConcentricRingsStructurePlacement::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ConcentricRingsStructurePlacement(Vec3i $$0, StructurePlacement.FrequencyReductionMethod $$1, float $$2, int $$3, Optional<StructurePlacement.ExclusionZone> $$4, int $$5, int $$6, int $$7, HolderSet<Biome> $$8) {
/* 37 */     super($$0, $$1, $$2, $$3, $$4);
/* 38 */     this.distance = $$5;
/* 39 */     this.spread = $$6;
/* 40 */     this.count = $$7;
/* 41 */     this.preferredBiomes = $$8;
/*    */   }
/*    */   
/*    */   public ConcentricRingsStructurePlacement(int $$0, int $$1, int $$2, HolderSet<Biome> $$3) {
/* 45 */     this(Vec3i.ZERO, StructurePlacement.FrequencyReductionMethod.DEFAULT, 1.0F, 0, Optional.empty(), $$0, $$1, $$2, $$3);
/*    */   }
/*    */   
/*    */   public int distance() {
/* 49 */     return this.distance;
/*    */   }
/*    */   
/*    */   public int spread() {
/* 53 */     return this.spread;
/*    */   }
/*    */   
/*    */   public int count() {
/* 57 */     return this.count;
/*    */   }
/*    */   
/*    */   public HolderSet<Biome> preferredBiomes() {
/* 61 */     return this.preferredBiomes;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isPlacementChunk(ChunkGeneratorStructureState $$0, int $$1, int $$2) {
/* 66 */     List<ChunkPos> $$3 = $$0.getRingPositionsFor(this);
/* 67 */     if ($$3 == null) {
/* 68 */       return false;
/*    */     }
/* 70 */     return $$3.contains(new ChunkPos($$1, $$2));
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePlacementType<?> type() {
/* 75 */     return StructurePlacementType.CONCENTRIC_RINGS;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\placement\ConcentricRingsStructurePlacement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */