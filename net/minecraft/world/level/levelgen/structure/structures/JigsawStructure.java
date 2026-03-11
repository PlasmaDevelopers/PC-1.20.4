/*    */ package net.minecraft.world.level.levelgen.structure.structures;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function9;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.DataResult;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*    */ import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
/*    */ 
/*    */ public final class JigsawStructure extends Structure {
/*    */   public static final int MAX_TOTAL_STRUCTURE_RANGE = 128;
/*    */   public static final int MAX_DEPTH = 20;
/*    */   public static final Codec<JigsawStructure> CODEC;
/*    */   private final Holder<StructureTemplatePool> startPool;
/*    */   private final Optional<ResourceLocation> startJigsawName;
/*    */   private final int maxDepth;
/*    */   private final HeightProvider startHeight;
/*    */   private final boolean useExpansionHack;
/*    */   private final Optional<Heightmap.Types> projectStartToHeightmap;
/*    */   private final int maxDistanceFromCenter;
/*    */   private final List<PoolAliasBinding> poolAliases;
/*    */   
/*    */   static {
/* 40 */     CODEC = ExtraCodecs.validate(RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)settingsCodec($$0), (App)StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(()), (App)ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(()), (App)Codec.intRange(0, 20).fieldOf("size").forGetter(()), (App)HeightProvider.CODEC.fieldOf("start_height").forGetter(()), (App)Codec.BOOL.fieldOf("use_expansion_hack").forGetter(()), (App)Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(()), (App)Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(()), (App)Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(())).apply((Applicative)$$0, JigsawStructure::new)), JigsawStructure::verifyRange).codec();
/*    */   }
/*    */   private static DataResult<JigsawStructure> verifyRange(JigsawStructure $$0) {
/* 43 */     switch ($$0.terrainAdaptation()) { default: throw new IncompatibleClassChangeError();
/*    */       case NONE: 
/* 45 */       case BURY: case BEARD_THIN: case BEARD_BOX: break; }  int $$1 = 12;
/*    */     
/* 47 */     if ($$0.maxDistanceFromCenter + $$1 > 128) {
/* 48 */       return DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128");
/*    */     }
/*    */     
/* 51 */     return DataResult.success($$0);
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
/*    */   public JigsawStructure(Structure.StructureSettings $$0, Holder<StructureTemplatePool> $$1, Optional<ResourceLocation> $$2, int $$3, HeightProvider $$4, boolean $$5, Optional<Heightmap.Types> $$6, int $$7, List<PoolAliasBinding> $$8) {
/* 64 */     super($$0);
/* 65 */     this.startPool = $$1;
/* 66 */     this.startJigsawName = $$2;
/* 67 */     this.maxDepth = $$3;
/* 68 */     this.startHeight = $$4;
/* 69 */     this.useExpansionHack = $$5;
/* 70 */     this.projectStartToHeightmap = $$6;
/* 71 */     this.maxDistanceFromCenter = $$7;
/* 72 */     this.poolAliases = $$8;
/*    */   }
/*    */   
/*    */   public JigsawStructure(Structure.StructureSettings $$0, Holder<StructureTemplatePool> $$1, int $$2, HeightProvider $$3, boolean $$4, Heightmap.Types $$5) {
/* 76 */     this($$0, $$1, Optional.empty(), $$2, $$3, $$4, Optional.of($$5), 80, List.of());
/*    */   }
/*    */   
/*    */   public JigsawStructure(Structure.StructureSettings $$0, Holder<StructureTemplatePool> $$1, int $$2, HeightProvider $$3, boolean $$4) {
/* 80 */     this($$0, $$1, Optional.empty(), $$2, $$3, $$4, Optional.empty(), 80, List.of());
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/* 85 */     ChunkPos $$1 = $$0.chunkPos();
/* 86 */     int $$2 = this.startHeight.sample((RandomSource)$$0.random(), new WorldGenerationContext($$0.chunkGenerator(), $$0.heightAccessor()));
/* 87 */     BlockPos $$3 = new BlockPos($$1.getMinBlockX(), $$2, $$1.getMinBlockZ());
/*    */     
/* 89 */     return JigsawPlacement.addPieces($$0, this.startPool, this.startJigsawName, this.maxDepth, $$3, this.useExpansionHack, this.projectStartToHeightmap, this.maxDistanceFromCenter, PoolAliasLookup.create(this.poolAliases, $$3, $$0.seed()));
/*    */   }
/*    */ 
/*    */   
/*    */   public StructureType<?> type() {
/* 94 */     return StructureType.JIGSAW;
/*    */   }
/*    */   
/*    */   public List<PoolAliasBinding> getPoolAliases() {
/* 98 */     return this.poolAliases;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\JigsawStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */