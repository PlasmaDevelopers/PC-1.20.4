/*     */ package net.minecraft.world.level.levelgen.structure.pools;
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.Optionull;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.JigsawReplacementProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ public class SinglePoolElement extends StructurePoolElement {
/*     */   private static <T> DataResult<T> encodeTemplate(Either<ResourceLocation, StructureTemplate> $$0, DynamicOps<T> $$1, T $$2) {
/*  43 */     Optional<ResourceLocation> $$3 = $$0.left();
/*  44 */     if ($$3.isEmpty()) {
/*  45 */       return DataResult.error(() -> "Can not serialize a runtime pool element");
/*     */     }
/*  47 */     return ResourceLocation.CODEC.encode($$3.get(), $$1, $$2);
/*     */   }
/*     */   
/*  50 */   private static final Codec<Either<ResourceLocation, StructureTemplate>> TEMPLATE_CODEC = Codec.of(SinglePoolElement::encodeTemplate, ResourceLocation.CODEC
/*     */       
/*  52 */       .map(Either::left)); public static final Codec<SinglePoolElement> CODEC; protected final Either<ResourceLocation, StructureTemplate> template; protected final Holder<StructureProcessorList> processors;
/*     */   
/*     */   static {
/*  55 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)templateCodec(), (App)processorsCodec(), (App)projectionCodec()).apply((Applicative)$$0, SinglePoolElement::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Holder<StructureProcessorList>> processorsCodec() {
/*  62 */     return StructureProcessorType.LIST_CODEC.fieldOf("processors").forGetter($$0 -> $$0.processors);
/*     */   }
/*     */   
/*     */   protected static <E extends SinglePoolElement> RecordCodecBuilder<E, Either<ResourceLocation, StructureTemplate>> templateCodec() {
/*  66 */     return TEMPLATE_CODEC.fieldOf("location").forGetter($$0 -> $$0.template);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SinglePoolElement(Either<ResourceLocation, StructureTemplate> $$0, Holder<StructureProcessorList> $$1, StructureTemplatePool.Projection $$2) {
/*  73 */     super($$2);
/*  74 */     this.template = $$0;
/*  75 */     this.processors = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3i getSize(StructureTemplateManager $$0, Rotation $$1) {
/*  80 */     StructureTemplate $$2 = getTemplate($$0);
/*  81 */     return $$2.getSize($$1);
/*     */   }
/*     */   
/*     */   private StructureTemplate getTemplate(StructureTemplateManager $$0) {
/*  85 */     Objects.requireNonNull($$0); return (StructureTemplate)this.template.map($$0::getOrCreate, Function.identity());
/*     */   }
/*     */   
/*     */   public List<StructureTemplate.StructureBlockInfo> getDataMarkers(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, boolean $$3) {
/*  89 */     StructureTemplate $$4 = getTemplate($$0);
/*  90 */     ObjectArrayList objectArrayList = $$4.filterBlocks($$1, (new StructurePlaceSettings()).setRotation($$2), Blocks.STRUCTURE_BLOCK, $$3);
/*  91 */     List<StructureTemplate.StructureBlockInfo> $$6 = Lists.newArrayList();
/*  92 */     for (StructureTemplate.StructureBlockInfo $$7 : objectArrayList) {
/*  93 */       CompoundTag $$8 = $$7.nbt();
/*  94 */       if ($$8 == null) {
/*     */         continue;
/*     */       }
/*     */       
/*  98 */       StructureMode $$9 = StructureMode.valueOf($$8.getString("mode"));
/*  99 */       if ($$9 != StructureMode.DATA) {
/*     */         continue;
/*     */       }
/*     */       
/* 103 */       $$6.add($$7);
/*     */     } 
/*     */     
/* 106 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, RandomSource $$3) {
/* 111 */     StructureTemplate $$4 = getTemplate($$0);
/* 112 */     ObjectArrayList<StructureTemplate.StructureBlockInfo> $$5 = $$4.filterBlocks($$1, (new StructurePlaceSettings()).setRotation($$2), Blocks.JIGSAW, true);
/* 113 */     Util.shuffle((List)$$5, $$3);
/* 114 */     sortBySelectionPriority((List<StructureTemplate.StructureBlockInfo>)$$5);
/* 115 */     return (List<StructureTemplate.StructureBlockInfo>)$$5;
/*     */   }
/*     */   
/*     */   @VisibleForTesting
/*     */   static void sortBySelectionPriority(List<StructureTemplate.StructureBlockInfo> $$0) {
/* 120 */     $$0.sort(Comparator.<StructureTemplate.StructureBlockInfo>comparingInt($$0 -> ((Integer)Optionull.mapOrDefault($$0.nbt(), (), Integer.valueOf(0))).intValue()).reversed());
/*     */   }
/*     */ 
/*     */   
/*     */   public BoundingBox getBoundingBox(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2) {
/* 125 */     StructureTemplate $$3 = getTemplate($$0);
/* 126 */     return $$3.getBoundingBox((new StructurePlaceSettings()).setRotation($$2), $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean place(StructureTemplateManager $$0, WorldGenLevel $$1, StructureManager $$2, ChunkGenerator $$3, BlockPos $$4, BlockPos $$5, Rotation $$6, BoundingBox $$7, RandomSource $$8, boolean $$9) {
/* 131 */     StructureTemplate $$10 = getTemplate($$0);
/* 132 */     StructurePlaceSettings $$11 = getSettings($$6, $$7, $$9);
/*     */     
/* 134 */     if ($$10.placeInWorld((ServerLevelAccessor)$$1, $$4, $$5, $$11, $$8, 18)) {
/* 135 */       List<StructureTemplate.StructureBlockInfo> $$12 = StructureTemplate.processBlockInfos((ServerLevelAccessor)$$1, $$4, $$5, $$11, getDataMarkers($$0, $$4, $$6, false));
/* 136 */       for (StructureTemplate.StructureBlockInfo $$13 : $$12) {
/* 137 */         handleDataMarker((LevelAccessor)$$1, $$13, $$4, $$6, $$8, $$7);
/*     */       }
/*     */       
/* 140 */       return true;
/*     */     } 
/* 142 */     return false;
/*     */   }
/*     */   
/*     */   protected StructurePlaceSettings getSettings(Rotation $$0, BoundingBox $$1, boolean $$2) {
/* 146 */     StructurePlaceSettings $$3 = new StructurePlaceSettings();
/* 147 */     $$3.setBoundingBox($$1);
/* 148 */     $$3.setRotation($$0);
/* 149 */     $$3.setKnownShape(true);
/* 150 */     $$3.setIgnoreEntities(false);
/* 151 */     $$3.addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_BLOCK);
/* 152 */     $$3.setFinalizeEntities(true);
/* 153 */     if (!$$2) {
/* 154 */       $$3.addProcessor((StructureProcessor)JigsawReplacementProcessor.INSTANCE);
/*     */     }
/* 156 */     Objects.requireNonNull($$3); ((StructureProcessorList)this.processors.value()).list().forEach($$3::addProcessor);
/* 157 */     Objects.requireNonNull($$3); getProjection().getProcessors().forEach($$3::addProcessor);
/* 158 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public StructurePoolElementType<?> getType() {
/* 163 */     return StructurePoolElementType.SINGLE;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 168 */     return "Single[" + this.template + "]";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\SinglePoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */