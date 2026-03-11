/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function8;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.NoiseColumn;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ 
/*     */ public class RuinedPortalStructure extends Structure {
/*  34 */   private static final String[] STRUCTURE_LOCATION_PORTALS = new String[] { "ruined_portal/portal_1", "ruined_portal/portal_2", "ruined_portal/portal_3", "ruined_portal/portal_4", "ruined_portal/portal_5", "ruined_portal/portal_6", "ruined_portal/portal_7", "ruined_portal/portal_8", "ruined_portal/portal_9", "ruined_portal/portal_10" };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  39 */   private static final String[] STRUCTURE_LOCATION_GIANT_PORTALS = new String[] { "ruined_portal/giant_portal_1", "ruined_portal/giant_portal_2", "ruined_portal/giant_portal_3" }; private static final float PROBABILITY_OF_GIANT_PORTAL = 0.05F; private static final int MIN_Y_INDEX = 15; private final List<Setup> setups; public static final Codec<RuinedPortalStructure> CODEC;
/*     */   public static final class Setup extends Record { private final RuinedPortalPiece.VerticalPlacement placement; private final float airPocketProbability; private final float mossiness; private final boolean overgrown; private final boolean vines;
/*     */     private final boolean canBeCold;
/*     */     private final boolean replaceWithBlackstone;
/*     */     private final float weight;
/*     */     public static final Codec<Setup> CODEC;
/*     */     
/*  46 */     public Setup(RuinedPortalPiece.VerticalPlacement $$0, float $$1, float $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, float $$7) { this.placement = $$0; this.airPocketProbability = $$1; this.mossiness = $$2; this.overgrown = $$3; this.vines = $$4; this.canBeCold = $$5; this.replaceWithBlackstone = $$6; this.weight = $$7; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  46 */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup; } public RuinedPortalPiece.VerticalPlacement placement() { return this.placement; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #46	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/levelgen/structure/structures/RuinedPortalStructure$Setup;
/*  46 */       //   0	8	1	$$0	Ljava/lang/Object; } public float airPocketProbability() { return this.airPocketProbability; } public float mossiness() { return this.mossiness; } public boolean overgrown() { return this.overgrown; } public boolean vines() { return this.vines; } public boolean canBeCold() { return this.canBeCold; } public boolean replaceWithBlackstone() { return this.replaceWithBlackstone; } public float weight() { return this.weight; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*  56 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)RuinedPortalPiece.VerticalPlacement.CODEC.fieldOf("placement").forGetter(Setup::placement), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("air_pocket_probability").forGetter(Setup::airPocketProbability), (App)Codec.floatRange(0.0F, 1.0F).fieldOf("mossiness").forGetter(Setup::mossiness), (App)Codec.BOOL.fieldOf("overgrown").forGetter(Setup::overgrown), (App)Codec.BOOL.fieldOf("vines").forGetter(Setup::vines), (App)Codec.BOOL.fieldOf("can_be_cold").forGetter(Setup::canBeCold), (App)Codec.BOOL.fieldOf("replace_with_blackstone").forGetter(Setup::replaceWithBlackstone), (App)ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(Setup::weight)).apply((Applicative)$$0, Setup::new));
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/*  70 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)settingsCodec($$0), (App)ExtraCodecs.nonEmptyList(Setup.CODEC.listOf()).fieldOf("setups").forGetter(())).apply((Applicative)$$0, RuinedPortalStructure::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public RuinedPortalStructure(Structure.StructureSettings $$0, List<Setup> $$1) {
/*  76 */     super($$0);
/*  77 */     this.setups = $$1;
/*     */   }
/*     */   
/*     */   public RuinedPortalStructure(Structure.StructureSettings $$0, Setup $$1) {
/*  81 */     this($$0, List.of($$1));
/*     */   }
/*     */   
/*     */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/*     */     ResourceLocation $$10;
/*  86 */     RuinedPortalPiece.Properties $$1 = new RuinedPortalPiece.Properties();
/*     */     
/*  88 */     WorldgenRandom $$2 = $$0.random();
/*     */ 
/*     */     
/*  91 */     Setup $$3 = null;
/*  92 */     if (this.setups.size() > 1) {
/*  93 */       float $$4 = 0.0F;
/*  94 */       for (Setup $$5 : this.setups) {
/*  95 */         $$4 += $$5.weight();
/*     */       }
/*  97 */       float $$6 = $$2.nextFloat();
/*  98 */       for (Setup $$7 : this.setups) {
/*  99 */         $$6 -= $$7.weight() / $$4;
/* 100 */         if ($$6 < 0.0F) {
/* 101 */           $$3 = $$7;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 106 */       $$3 = this.setups.get(0);
/*     */     } 
/* 108 */     if ($$3 == null) {
/* 109 */       throw new IllegalStateException();
/*     */     }
/* 111 */     Setup $$8 = $$3;
/*     */     
/* 113 */     $$1.airPocket = sample($$2, $$8.airPocketProbability());
/* 114 */     $$1.mossiness = $$8.mossiness();
/* 115 */     $$1.overgrown = $$8.overgrown();
/* 116 */     $$1.vines = $$8.vines();
/* 117 */     $$1.replaceWithBlackstone = $$8.replaceWithBlackstone();
/*     */ 
/*     */     
/* 120 */     if ($$2.nextFloat() < 0.05F) {
/* 121 */       ResourceLocation $$9 = new ResourceLocation(STRUCTURE_LOCATION_GIANT_PORTALS[$$2.nextInt(STRUCTURE_LOCATION_GIANT_PORTALS.length)]);
/*     */     } else {
/* 123 */       $$10 = new ResourceLocation(STRUCTURE_LOCATION_PORTALS[$$2.nextInt(STRUCTURE_LOCATION_PORTALS.length)]);
/*     */     } 
/*     */     
/* 126 */     StructureTemplate $$11 = $$0.structureTemplateManager().getOrCreate($$10);
/* 127 */     Rotation $$12 = (Rotation)Util.getRandom((Object[])Rotation.values(), (RandomSource)$$2);
/* 128 */     Mirror $$13 = ($$2.nextFloat() < 0.5F) ? Mirror.NONE : Mirror.FRONT_BACK;
/* 129 */     BlockPos $$14 = new BlockPos($$11.getSize().getX() / 2, 0, $$11.getSize().getZ() / 2);
/*     */     
/* 131 */     ChunkGenerator $$15 = $$0.chunkGenerator();
/* 132 */     LevelHeightAccessor $$16 = $$0.heightAccessor();
/* 133 */     RandomState $$17 = $$0.randomState();
/*     */     
/* 135 */     BlockPos $$18 = $$0.chunkPos().getWorldPosition();
/* 136 */     BoundingBox $$19 = $$11.getBoundingBox($$18, $$12, $$14, $$13);
/* 137 */     BlockPos $$20 = $$19.getCenter();
/* 138 */     int $$21 = $$15.getBaseHeight($$20.getX(), $$20.getZ(), RuinedPortalPiece.getHeightMapType($$8.placement()), $$16, $$17) - 1;
/* 139 */     int $$22 = findSuitableY((RandomSource)$$2, $$15, $$8.placement(), $$1.airPocket, $$21, $$19.getYSpan(), $$19, $$16, $$17);
/*     */     
/* 141 */     BlockPos $$23 = new BlockPos($$18.getX(), $$22, $$18.getZ());
/* 142 */     return Optional.of(new Structure.GenerationStub($$23, $$10 -> {
/*     */             if ($$0.canBeCold()) {
/*     */               $$1.cold = isCold($$2, $$3.chunkGenerator().getBiomeSource().getNoiseBiome(QuartPos.fromBlock($$2.getX()), QuartPos.fromBlock($$2.getY()), QuartPos.fromBlock($$2.getZ()), $$4.sampler()));
/*     */             }
/*     */             $$10.addPiece((StructurePiece)new RuinedPortalPiece($$3.structureTemplateManager(), $$2, $$0.placement(), $$1, $$5, $$6, $$7, $$8, $$9));
/*     */           }));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean sample(WorldgenRandom $$0, float $$1) {
/* 153 */     if ($$1 == 0.0F)
/* 154 */       return false; 
/* 155 */     if ($$1 == 1.0F) {
/* 156 */       return true;
/*     */     }
/* 158 */     return ($$0.nextFloat() < $$1);
/*     */   }
/*     */   
/*     */   private static boolean isCold(BlockPos $$0, Holder<Biome> $$1) {
/* 162 */     return ((Biome)$$1.value()).coldEnoughToSnow($$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int findSuitableY(RandomSource $$0, ChunkGenerator $$1, RuinedPortalPiece.VerticalPlacement $$2, boolean $$3, int $$4, int $$5, BoundingBox $$6, LevelHeightAccessor $$7, RandomState $$8) {
/* 169 */     int $$18, $$9 = $$7.getMinBuildHeight() + 15;
/* 170 */     if ($$2 == RuinedPortalPiece.VerticalPlacement.IN_NETHER) {
/* 171 */       if ($$3) {
/*     */         
/* 173 */         int $$10 = Mth.randomBetweenInclusive($$0, 32, 100);
/*     */       }
/* 175 */       else if ($$0.nextFloat() < 0.5F) {
/*     */         
/* 177 */         int $$11 = Mth.randomBetweenInclusive($$0, 27, 29);
/*     */       } else {
/*     */         
/* 180 */         int $$12 = Mth.randomBetweenInclusive($$0, 29, 100);
/*     */       }
/*     */     
/* 183 */     } else if ($$2 == RuinedPortalPiece.VerticalPlacement.IN_MOUNTAIN) {
/* 184 */       int $$13 = $$4 - $$5;
/* 185 */       int $$14 = getRandomWithinInterval($$0, 70, $$13);
/* 186 */     } else if ($$2 == RuinedPortalPiece.VerticalPlacement.UNDERGROUND) {
/* 187 */       int $$15 = $$4 - $$5;
/* 188 */       int $$16 = getRandomWithinInterval($$0, $$9, $$15);
/* 189 */     } else if ($$2 == RuinedPortalPiece.VerticalPlacement.PARTLY_BURIED) {
/* 190 */       int $$17 = $$4 - $$5 + Mth.randomBetweenInclusive($$0, 2, 8);
/*     */     } else {
/* 192 */       $$18 = $$4;
/*     */     } 
/*     */     
/* 195 */     ImmutableList immutableList = ImmutableList.of(new BlockPos($$6
/* 196 */           .minX(), 0, $$6.minZ()), new BlockPos($$6
/* 197 */           .maxX(), 0, $$6.minZ()), new BlockPos($$6
/* 198 */           .minX(), 0, $$6.maxZ()), new BlockPos($$6
/* 199 */           .maxX(), 0, $$6.maxZ()));
/*     */ 
/*     */     
/* 202 */     List<NoiseColumn> $$20 = (List<NoiseColumn>)immutableList.stream().map($$3 -> $$0.getBaseColumn($$3.getX(), $$3.getZ(), $$1, $$2)).collect(Collectors.toList());
/*     */     
/* 204 */     Heightmap.Types $$21 = ($$2 == RuinedPortalPiece.VerticalPlacement.ON_OCEAN_FLOOR) ? Heightmap.Types.OCEAN_FLOOR_WG : Heightmap.Types.WORLD_SURFACE_WG;
/*     */     
/* 206 */     int $$22 = $$18;
/*     */ 
/*     */ 
/*     */     
/* 210 */     label40: while ($$22 > $$9) {
/* 211 */       int $$23 = 0;
/* 212 */       for (NoiseColumn $$24 : $$20) {
/*     */         
/* 214 */         BlockState $$25 = $$24.getBlock($$22);
/*     */         
/* 216 */         $$23++;
/* 217 */         if ($$21.isOpaque().test($$25) && $$23 == 3) {
/*     */           break label40;
/*     */         }
/*     */       } 
/*     */       
/* 222 */       $$22--;
/*     */     } 
/* 224 */     return $$22;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getRandomWithinInterval(RandomSource $$0, int $$1, int $$2) {
/* 229 */     if ($$1 < $$2) {
/* 230 */       return Mth.randomBetweenInclusive($$0, $$1, $$2);
/*     */     }
/* 232 */     return $$2;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureType<?> type() {
/* 238 */     return StructureType.RUINED_PORTAL;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\RuinedPortalStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */