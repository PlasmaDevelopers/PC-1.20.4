/*     */ package net.minecraft.world.level.levelgen.structure.pools;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.data.worldgen.Pools;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.SequencedPriorityIterator;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.block.JigsawBlock;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.RandomState;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.shapes.BooleanOp;
/*     */ import net.minecraft.world.phys.shapes.Shapes;
/*     */ import net.minecraft.world.phys.shapes.VoxelShape;
/*     */ import org.apache.commons.lang3.mutable.MutableObject;
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
/*     */ final class Placer
/*     */ {
/*     */   private final Registry<StructureTemplatePool> pools;
/*     */   private final int maxDepth;
/*     */   private final ChunkGenerator chunkGenerator;
/*     */   private final StructureTemplateManager structureTemplateManager;
/*     */   private final List<? super PoolElementStructurePiece> pieces;
/*     */   private final RandomSource random;
/*  62 */   final SequencedPriorityIterator<JigsawPlacement.PieceState> placing = new SequencedPriorityIterator();
/*     */   
/*     */   Placer(Registry<StructureTemplatePool> $$0, int $$1, ChunkGenerator $$2, StructureTemplateManager $$3, List<? super PoolElementStructurePiece> $$4, RandomSource $$5) {
/*  65 */     this.pools = $$0;
/*  66 */     this.maxDepth = $$1;
/*  67 */     this.chunkGenerator = $$2;
/*  68 */     this.structureTemplateManager = $$3;
/*  69 */     this.pieces = $$4;
/*  70 */     this.random = $$5;
/*     */   }
/*     */   
/*     */   void tryPlacingChildren(PoolElementStructurePiece $$0, MutableObject<VoxelShape> $$1, int $$2, boolean $$3, LevelHeightAccessor $$4, RandomState $$5, PoolAliasLookup $$6) {
/*  74 */     StructurePoolElement $$7 = $$0.getElement();
/*  75 */     BlockPos $$8 = $$0.getPosition();
/*  76 */     Rotation $$9 = $$0.getRotation();
/*     */     
/*  78 */     StructureTemplatePool.Projection $$10 = $$7.getProjection();
/*  79 */     boolean $$11 = ($$10 == StructureTemplatePool.Projection.RIGID);
/*     */     
/*  81 */     MutableObject<VoxelShape> $$12 = new MutableObject();
/*     */     
/*  83 */     BoundingBox $$13 = $$0.getBoundingBox();
/*  84 */     int $$14 = $$13.minY();
/*     */     
/*  86 */     for (StructureTemplate.StructureBlockInfo $$15 : $$7.getShuffledJigsawBlocks(this.structureTemplateManager, $$8, $$9, this.random)) {
/*  87 */       MutableObject<VoxelShape> $$27; Direction $$16 = JigsawBlock.getFrontFacing($$15.state());
/*     */       
/*  89 */       BlockPos $$17 = $$15.pos();
/*  90 */       BlockPos $$18 = $$17.relative($$16);
/*     */       
/*  92 */       int $$19 = $$17.getY() - $$14;
/*  93 */       int $$20 = -1;
/*     */       
/*  95 */       ResourceKey<StructureTemplatePool> $$21 = readPoolKey($$15, $$6);
/*  96 */       Optional<? extends Holder<StructureTemplatePool>> $$22 = this.pools.getHolder($$21);
/*     */       
/*  98 */       if ($$22.isEmpty()) {
/*  99 */         JigsawPlacement.LOGGER.warn("Empty or non-existent pool: {}", $$21.location());
/*     */         
/*     */         continue;
/*     */       } 
/* 103 */       Holder<StructureTemplatePool> $$23 = $$22.get();
/* 104 */       if (((StructureTemplatePool)$$23.value()).size() == 0 && !$$23.is(Pools.EMPTY)) {
/* 105 */         JigsawPlacement.LOGGER.warn("Empty or non-existent pool: {}", $$21.location());
/*     */         
/*     */         continue;
/*     */       } 
/* 109 */       Holder<StructureTemplatePool> $$24 = ((StructureTemplatePool)$$23.value()).getFallback();
/*     */       
/* 111 */       if (((StructureTemplatePool)$$24.value()).size() == 0 && !$$24.is(Pools.EMPTY)) {
/* 112 */         JigsawPlacement.LOGGER.warn("Empty or non-existent fallback pool: {}", $$24.unwrapKey().map($$0 -> $$0.location().toString()).orElse("<unregistered>"));
/*     */ 
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 118 */       boolean $$25 = $$13.isInside((Vec3i)$$18);
/* 119 */       if ($$25) {
/* 120 */         MutableObject<VoxelShape> $$26 = $$12;
/* 121 */         if ($$12.getValue() == null) {
/* 122 */           $$12.setValue(Shapes.create(AABB.of($$13)));
/*     */         }
/*     */       } else {
/* 125 */         $$27 = $$1;
/*     */       } 
/*     */ 
/*     */       
/* 129 */       List<StructurePoolElement> $$28 = Lists.newArrayList();
/* 130 */       if ($$2 != this.maxDepth) {
/* 131 */         $$28.addAll(((StructureTemplatePool)$$23.value()).getShuffledTemplates(this.random));
/*     */       }
/* 133 */       $$28.addAll(((StructureTemplatePool)$$24.value()).getShuffledTemplates(this.random));
/* 134 */       int $$29 = ($$15.nbt() != null) ? $$15.nbt().getInt("placement_priority") : 0;
/*     */ 
/*     */       
/* 137 */       for (StructurePoolElement $$30 : $$28) {
/* 138 */         if ($$30 == EmptyPoolElement.INSTANCE) {
/*     */           break;
/*     */         }
/*     */         
/* 142 */         for (Rotation $$31 : Rotation.getShuffled(this.random)) {
/* 143 */           int $$35; List<StructureTemplate.StructureBlockInfo> $$32 = $$30.getShuffledJigsawBlocks(this.structureTemplateManager, BlockPos.ZERO, $$31, this.random);
/* 144 */           BoundingBox $$33 = $$30.getBoundingBox(this.structureTemplateManager, BlockPos.ZERO, $$31);
/*     */ 
/*     */           
/* 147 */           if (!$$3 || $$33.getYSpan() > 16) {
/* 148 */             int $$34 = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 160 */             $$35 = $$32.stream().mapToInt($$2 -> { if (!$$0.isInside((Vec3i)$$2.pos().relative(JigsawBlock.getFrontFacing($$2.state())))) return 0;  ResourceKey<StructureTemplatePool> $$3 = readPoolKey($$2, $$1); Optional<? extends Holder<StructureTemplatePool>> $$4 = this.pools.getHolder($$3); Optional<Holder<StructureTemplatePool>> $$5 = $$4.map(()); int $$6 = ((Integer)$$4.<Integer>map(()).orElse(Integer.valueOf(0))).intValue(); int $$7 = ((Integer)$$5.<Integer>map(()).orElse(Integer.valueOf(0))).intValue(); return Math.max($$6, $$7); }).max().orElse(0);
/*     */           } 
/*     */           
/* 163 */           for (StructureTemplate.StructureBlockInfo $$36 : $$32) {
/* 164 */             int $$46, $$53, $$57; if (!JigsawBlock.canAttach($$15, $$36)) {
/*     */               continue;
/*     */             }
/*     */             
/* 168 */             BlockPos $$37 = $$36.pos();
/*     */             
/* 170 */             BlockPos $$38 = $$18.subtract((Vec3i)$$37);
/* 171 */             BoundingBox $$39 = $$30.getBoundingBox(this.structureTemplateManager, $$38, $$31);
/* 172 */             int $$40 = $$39.minY();
/*     */             
/* 174 */             StructureTemplatePool.Projection $$41 = $$30.getProjection();
/* 175 */             boolean $$42 = ($$41 == StructureTemplatePool.Projection.RIGID);
/*     */ 
/*     */             
/* 178 */             int $$43 = $$37.getY();
/*     */             
/* 180 */             int $$44 = $$19 - $$43 + JigsawBlock.getFrontFacing($$15.state()).getStepY();
/*     */ 
/*     */             
/* 183 */             if ($$11 && $$42) {
/* 184 */               int $$45 = $$14 + $$44;
/*     */             } else {
/* 186 */               if ($$20 == -1) {
/* 187 */                 $$20 = this.chunkGenerator.getFirstFreeHeight($$17.getX(), $$17.getZ(), Heightmap.Types.WORLD_SURFACE_WG, $$4, $$5);
/*     */               }
/* 189 */               $$46 = $$20 - $$43;
/*     */             } 
/*     */             
/* 192 */             int $$47 = $$46 - $$40;
/*     */             
/* 194 */             BoundingBox $$48 = $$39.moved(0, $$47, 0);
/* 195 */             BlockPos $$49 = $$38.offset(0, $$47, 0);
/*     */             
/* 197 */             if ($$35 > 0) {
/* 198 */               int $$50 = Math.max($$35 + 1, $$48.maxY() - $$48.minY());
/* 199 */               $$48.encapsulate(new BlockPos($$48.minX(), $$48.minY() + $$50, $$48.minZ()));
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 204 */             if (Shapes.joinIsNotEmpty((VoxelShape)$$27.getValue(), Shapes.create(AABB.of($$48).deflate(0.25D)), BooleanOp.ONLY_SECOND)) {
/*     */               continue;
/*     */             }
/*     */             
/* 208 */             $$27.setValue(Shapes.joinUnoptimized((VoxelShape)$$27.getValue(), Shapes.create(AABB.of($$48)), BooleanOp.ONLY_FIRST));
/*     */             
/* 210 */             int $$51 = $$0.getGroundLevelDelta();
/*     */             
/* 212 */             if ($$42) {
/*     */               
/* 214 */               int $$52 = $$51 - $$44;
/*     */             } else {
/* 216 */               $$53 = $$30.getGroundLevelDelta();
/*     */             } 
/*     */             
/* 219 */             PoolElementStructurePiece $$54 = new PoolElementStructurePiece(this.structureTemplateManager, $$30, $$49, $$53, $$31, $$48);
/*     */ 
/*     */             
/* 222 */             if ($$11) {
/* 223 */               int $$55 = $$14 + $$19;
/* 224 */             } else if ($$42) {
/* 225 */               int $$56 = $$46 + $$43;
/*     */             } else {
/* 227 */               if ($$20 == -1) {
/* 228 */                 $$20 = this.chunkGenerator.getFirstFreeHeight($$17.getX(), $$17.getZ(), Heightmap.Types.WORLD_SURFACE_WG, $$4, $$5);
/*     */               }
/* 230 */               $$57 = $$20 + $$44 / 2;
/*     */             } 
/*     */             
/* 233 */             $$0.addJunction(new JigsawJunction($$18
/* 234 */                   .getX(), $$57 - $$19 + $$51, $$18
/*     */                   
/* 236 */                   .getZ(), $$44, $$41));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 241 */             $$54.addJunction(new JigsawJunction($$17
/* 242 */                   .getX(), $$57 - $$43 + $$53, $$17
/*     */                   
/* 244 */                   .getZ(), -$$44, $$10));
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 249 */             this.pieces.add($$54);
/* 250 */             if ($$2 + 1 <= this.maxDepth) {
/* 251 */               JigsawPlacement.PieceState $$58 = new JigsawPlacement.PieceState($$54, $$27, $$2 + 1);
/* 252 */               this.placing.add($$58, $$29);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceKey<StructureTemplatePool> readPoolKey(StructureTemplate.StructureBlockInfo $$0, PoolAliasLookup $$1) {
/* 262 */     CompoundTag $$2 = Objects.<CompoundTag>requireNonNull($$0.nbt(), () -> "" + $$0 + " nbt was null");
/* 263 */     ResourceKey<StructureTemplatePool> $$3 = Pools.createKey($$2.getString("pool"));
/* 264 */     return $$1.lookup($$3);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\JigsawPlacement$Placer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */