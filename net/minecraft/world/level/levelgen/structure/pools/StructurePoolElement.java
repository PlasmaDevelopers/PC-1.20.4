/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ 
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.LevelAccessor;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ public abstract class StructurePoolElement
/*    */ {
/* 29 */   public static final Codec<StructurePoolElement> CODEC = BuiltInRegistries.STRUCTURE_POOL_ELEMENT.byNameCodec().dispatch("element_type", StructurePoolElement::getType, StructurePoolElementType::codec);
/*    */   
/* 31 */   private static final Holder<StructureProcessorList> EMPTY = Holder.direct(new StructureProcessorList(List.of()));
/*    */   
/*    */   protected static <E extends StructurePoolElement> RecordCodecBuilder<E, StructureTemplatePool.Projection> projectionCodec() {
/* 34 */     return StructureTemplatePool.Projection.CODEC.fieldOf("projection").forGetter(StructurePoolElement::getProjection);
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   private volatile StructureTemplatePool.Projection projection;
/*    */   
/*    */   protected StructurePoolElement(StructureTemplatePool.Projection $$0) {
/* 41 */     this.projection = $$0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleDataMarker(LevelAccessor $$0, StructureTemplate.StructureBlockInfo $$1, BlockPos $$2, Rotation $$3, RandomSource $$4, BoundingBox $$5) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StructurePoolElement setProjection(StructureTemplatePool.Projection $$0) {
/* 58 */     this.projection = $$0;
/* 59 */     return this;
/*    */   }
/*    */   
/*    */   public StructureTemplatePool.Projection getProjection() {
/* 63 */     StructureTemplatePool.Projection $$0 = this.projection;
/* 64 */     if ($$0 == null) {
/* 65 */       throw new IllegalStateException();
/*    */     }
/* 67 */     return $$0;
/*    */   }
/*    */   
/*    */   public int getGroundLevelDelta() {
/* 71 */     return 1;
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, EmptyPoolElement> empty() {
/* 75 */     return $$0 -> EmptyPoolElement.INSTANCE;
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String $$0) {
/* 79 */     return $$1 -> new LegacySinglePoolElement(Either.left(new ResourceLocation($$0)), EMPTY, $$1);
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, LegacySinglePoolElement> legacy(String $$0, Holder<StructureProcessorList> $$1) {
/* 83 */     return $$2 -> new LegacySinglePoolElement(Either.left(new ResourceLocation($$0)), $$1, $$2);
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String $$0) {
/* 87 */     return $$1 -> new SinglePoolElement(Either.left(new ResourceLocation($$0)), EMPTY, $$1);
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, SinglePoolElement> single(String $$0, Holder<StructureProcessorList> $$1) {
/* 91 */     return $$2 -> new SinglePoolElement(Either.left(new ResourceLocation($$0)), $$1, $$2);
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, FeaturePoolElement> feature(Holder<PlacedFeature> $$0) {
/* 95 */     return $$1 -> new FeaturePoolElement($$0, $$1);
/*    */   }
/*    */   
/*    */   public static Function<StructureTemplatePool.Projection, ListPoolElement> list(List<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>> $$0) {
/* 99 */     return $$1 -> new ListPoolElement((List<StructurePoolElement>)$$0.stream().map(()).collect(Collectors.toList()), $$1);
/*    */   }
/*    */   
/*    */   public abstract Vec3i getSize(StructureTemplateManager paramStructureTemplateManager, Rotation paramRotation);
/*    */   
/*    */   public abstract List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager paramStructureTemplateManager, BlockPos paramBlockPos, Rotation paramRotation, RandomSource paramRandomSource);
/*    */   
/*    */   public abstract BoundingBox getBoundingBox(StructureTemplateManager paramStructureTemplateManager, BlockPos paramBlockPos, Rotation paramRotation);
/*    */   
/*    */   public abstract boolean place(StructureTemplateManager paramStructureTemplateManager, WorldGenLevel paramWorldGenLevel, StructureManager paramStructureManager, ChunkGenerator paramChunkGenerator, BlockPos paramBlockPos1, BlockPos paramBlockPos2, Rotation paramRotation, BoundingBox paramBoundingBox, RandomSource paramRandomSource, boolean paramBoolean);
/*    */   
/*    */   public abstract StructurePoolElementType<?> getType();
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\StructurePoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */