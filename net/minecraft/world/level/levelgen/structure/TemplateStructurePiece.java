/*     */ package net.minecraft.world.level.levelgen.structure;
/*     */ 
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.commands.arguments.blocks.BlockStateParser;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.StructureManager;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
/*     */ import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ public abstract class TemplateStructurePiece
/*     */   extends StructurePiece
/*     */ {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   protected final String templateName;
/*     */   
/*     */   protected StructureTemplate template;
/*     */   protected StructurePlaceSettings placeSettings;
/*     */   protected BlockPos templatePosition;
/*     */   
/*     */   public TemplateStructurePiece(StructurePieceType $$0, int $$1, StructureTemplateManager $$2, ResourceLocation $$3, String $$4, StructurePlaceSettings $$5, BlockPos $$6) {
/*  43 */     super($$0, $$1, $$2.getOrCreate($$3).getBoundingBox($$5, $$6));
/*  44 */     setOrientation(Direction.NORTH);
/*     */     
/*  46 */     this.templateName = $$4;
/*  47 */     this.templatePosition = $$6;
/*  48 */     this.template = $$2.getOrCreate($$3);
/*  49 */     this.placeSettings = $$5;
/*     */   }
/*     */   
/*     */   public TemplateStructurePiece(StructurePieceType $$0, CompoundTag $$1, StructureTemplateManager $$2, Function<ResourceLocation, StructurePlaceSettings> $$3) {
/*  53 */     super($$0, $$1);
/*  54 */     setOrientation(Direction.NORTH);
/*     */     
/*  56 */     this.templateName = $$1.getString("Template");
/*  57 */     this.templatePosition = new BlockPos($$1.getInt("TPX"), $$1.getInt("TPY"), $$1.getInt("TPZ"));
/*  58 */     ResourceLocation $$4 = makeTemplateLocation();
/*  59 */     this.template = $$2.getOrCreate($$4);
/*  60 */     this.placeSettings = $$3.apply($$4);
/*     */ 
/*     */     
/*  63 */     this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
/*     */   }
/*     */   
/*     */   protected ResourceLocation makeTemplateLocation() {
/*  67 */     return new ResourceLocation(this.templateName);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(StructurePieceSerializationContext $$0, CompoundTag $$1) {
/*  72 */     $$1.putInt("TPX", this.templatePosition.getX());
/*  73 */     $$1.putInt("TPY", this.templatePosition.getY());
/*  74 */     $$1.putInt("TPZ", this.templatePosition.getZ());
/*  75 */     $$1.putString("Template", this.templateName);
/*     */   }
/*     */ 
/*     */   
/*     */   public void postProcess(WorldGenLevel $$0, StructureManager $$1, ChunkGenerator $$2, RandomSource $$3, BoundingBox $$4, ChunkPos $$5, BlockPos $$6) {
/*  80 */     this.placeSettings.setBoundingBox($$4);
/*     */     
/*  82 */     this.boundingBox = this.template.getBoundingBox(this.placeSettings, this.templatePosition);
/*  83 */     if (this.template.placeInWorld((ServerLevelAccessor)$$0, this.templatePosition, $$6, this.placeSettings, $$3, 2)) {
/*  84 */       List<StructureTemplate.StructureBlockInfo> $$7 = this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.STRUCTURE_BLOCK);
/*  85 */       for (StructureTemplate.StructureBlockInfo $$8 : $$7) {
/*  86 */         if ($$8.nbt() == null) {
/*     */           continue;
/*     */         }
/*     */         
/*  90 */         StructureMode $$9 = StructureMode.valueOf($$8.nbt().getString("mode"));
/*  91 */         if ($$9 != StructureMode.DATA) {
/*     */           continue;
/*     */         }
/*     */         
/*  95 */         handleDataMarker($$8.nbt().getString("metadata"), $$8.pos(), (ServerLevelAccessor)$$0, $$3, $$4);
/*     */       } 
/*     */       
/*  98 */       List<StructureTemplate.StructureBlockInfo> $$10 = this.template.filterBlocks(this.templatePosition, this.placeSettings, Blocks.JIGSAW);
/*  99 */       for (StructureTemplate.StructureBlockInfo $$11 : $$10) {
/* 100 */         if ($$11.nbt() == null) {
/*     */           continue;
/*     */         }
/*     */         
/* 104 */         String $$12 = $$11.nbt().getString("final_state");
/* 105 */         BlockState $$13 = Blocks.AIR.defaultBlockState();
/*     */         try {
/* 107 */           $$13 = BlockStateParser.parseForBlock($$0.holderLookup(Registries.BLOCK), $$12, true).blockState();
/* 108 */         } catch (CommandSyntaxException $$14) {
/* 109 */           LOGGER.error("Error while parsing blockstate {} in jigsaw block @ {}", $$12, $$11.pos());
/*     */         } 
/*     */         
/* 112 */         $$0.setBlock($$11.pos(), $$13, 3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void handleDataMarker(String paramString, BlockPos paramBlockPos, ServerLevelAccessor paramServerLevelAccessor, RandomSource paramRandomSource, BoundingBox paramBoundingBox);
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void move(int $$0, int $$1, int $$2) {
/* 125 */     super.move($$0, $$1, $$2);
/* 126 */     this.templatePosition = this.templatePosition.offset($$0, $$1, $$2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotation getRotation() {
/* 131 */     return this.placeSettings.getRotation();
/*     */   }
/*     */   
/*     */   public StructureTemplate template() {
/* 135 */     return this.template;
/*     */   }
/*     */   
/*     */   public BlockPos templatePosition() {
/* 139 */     return this.templatePosition;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings placeSettings() {
/* 143 */     return this.placeSettings;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\TemplateStructurePiece.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */