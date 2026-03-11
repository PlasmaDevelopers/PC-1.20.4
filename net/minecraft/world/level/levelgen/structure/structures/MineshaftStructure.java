/*     */ package net.minecraft.world.level.levelgen.structure.structures;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.function.IntFunction;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.WorldgenRandom;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureType;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
/*     */ 
/*     */ public class MineshaftStructure extends Structure {
/*     */   static {
/*  26 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)settingsCodec($$0), (App)Type.CODEC.fieldOf("mineshaft_type").forGetter(())).apply((Applicative)$$0, MineshaftStructure::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<MineshaftStructure> CODEC;
/*     */   private final Type type;
/*     */   
/*     */   public MineshaftStructure(Structure.StructureSettings $$0, Type $$1) {
/*  34 */     super($$0);
/*  35 */     this.type = $$1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext $$0) {
/*  41 */     $$0.random().nextDouble();
/*     */     
/*  43 */     ChunkPos $$1 = $$0.chunkPos();
/*     */     
/*  45 */     BlockPos $$2 = new BlockPos($$1.getMiddleBlockX(), 50, $$1.getMinBlockZ());
/*  46 */     StructurePiecesBuilder $$3 = new StructurePiecesBuilder();
/*  47 */     int $$4 = generatePiecesAndAdjust($$3, $$0);
/*  48 */     return Optional.of(new Structure.GenerationStub($$2.offset(0, $$4, 0), Either.right($$3)));
/*     */   }
/*     */   
/*     */   private int generatePiecesAndAdjust(StructurePiecesBuilder $$0, Structure.GenerationContext $$1) {
/*  52 */     ChunkPos $$2 = $$1.chunkPos();
/*  53 */     WorldgenRandom $$3 = $$1.random();
/*  54 */     ChunkGenerator $$4 = $$1.chunkGenerator();
/*  55 */     MineshaftPieces.MineShaftRoom $$5 = new MineshaftPieces.MineShaftRoom(0, (RandomSource)$$3, $$2.getBlockX(2), $$2.getBlockZ(2), this.type);
/*  56 */     $$0.addPiece($$5);
/*  57 */     $$5.addChildren($$5, (StructurePieceAccessor)$$0, (RandomSource)$$3);
/*     */     
/*  59 */     int $$6 = $$4.getSeaLevel();
/*  60 */     if (this.type == Type.MESA) {
/*     */       
/*  62 */       BlockPos $$7 = $$0.getBoundingBox().getCenter();
/*  63 */       int $$8 = $$4.getBaseHeight($$7.getX(), $$7.getZ(), Heightmap.Types.WORLD_SURFACE_WG, $$1.heightAccessor(), $$1.randomState());
/*  64 */       int $$9 = ($$8 <= $$6) ? $$6 : Mth.randomBetweenInclusive((RandomSource)$$3, $$6, $$8);
/*  65 */       int $$10 = $$9 - $$7.getY();
/*     */       
/*  67 */       $$0.offsetPiecesVertically($$10);
/*  68 */       return $$10;
/*     */     } 
/*  70 */     return $$0.moveBelowSeaLevel($$6, $$4.getMinY(), (RandomSource)$$3, 10);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureType<?> type() {
/*  76 */     return StructureType.MINESHAFT;
/*     */   }
/*     */   
/*     */   public enum Type implements StringRepresentable {
/*  80 */     NORMAL("normal", Blocks.OAK_LOG, (String)Blocks.OAK_PLANKS, Blocks.OAK_FENCE),
/*  81 */     MESA("mesa", Blocks.DARK_OAK_LOG, (String)Blocks.DARK_OAK_PLANKS, Blocks.DARK_OAK_FENCE);
/*     */ 
/*     */     
/*  84 */     public static final Codec<Type> CODEC = (Codec<Type>)StringRepresentable.fromEnum(Type::values);
/*  85 */     private static final IntFunction<Type> BY_ID = ByIdMap.continuous(Enum::ordinal, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO); private final String name; private final BlockState woodState; private final BlockState planksState;
/*     */     private final BlockState fenceState;
/*     */     
/*     */     static {
/*     */     
/*     */     }
/*     */     
/*     */     Type(String $$0, Block $$1, Block $$2, Block $$3) {
/*  93 */       this.name = $$0;
/*  94 */       this.woodState = $$1.defaultBlockState();
/*  95 */       this.planksState = $$2.defaultBlockState();
/*  96 */       this.fenceState = $$3.defaultBlockState();
/*     */     }
/*     */     
/*     */     public String getName() {
/* 100 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Type byId(int $$0) {
/* 104 */       return BY_ID.apply($$0);
/*     */     }
/*     */     
/*     */     public BlockState getWoodState() {
/* 108 */       return this.woodState;
/*     */     }
/*     */     
/*     */     public BlockState getPlanksState() {
/* 112 */       return this.planksState;
/*     */     }
/*     */     
/*     */     public BlockState getFenceState() {
/* 116 */       return this.fenceState;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 121 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\structures\MineshaftStructure.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */