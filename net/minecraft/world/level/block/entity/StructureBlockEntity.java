/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.StringUtil;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.StructureBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.level.block.state.properties.StructureMode;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ public class StructureBlockEntity
/*     */   extends BlockEntity {
/*     */   private static final int SCAN_CORNER_BLOCKS_RANGE = 5;
/*     */   public static final int MAX_OFFSET_PER_AXIS = 48;
/*     */   public static final int MAX_SIZE_PER_AXIS = 48;
/*     */   public static final String AUTHOR_TAG = "author";
/*     */   @Nullable
/*     */   private ResourceLocation structureName;
/*  45 */   private String author = "";
/*  46 */   private String metaData = "";
/*  47 */   private BlockPos structurePos = new BlockPos(0, 1, 0);
/*  48 */   private Vec3i structureSize = Vec3i.ZERO;
/*  49 */   private Mirror mirror = Mirror.NONE;
/*  50 */   private Rotation rotation = Rotation.NONE;
/*     */   private StructureMode mode;
/*     */   private boolean ignoreEntities = true;
/*     */   private boolean powered;
/*     */   private boolean showAir;
/*     */   private boolean showBoundingBox = true;
/*  56 */   private float integrity = 1.0F;
/*     */   private long seed;
/*     */   
/*     */   public StructureBlockEntity(BlockPos $$0, BlockState $$1) {
/*  60 */     super(BlockEntityType.STRUCTURE_BLOCK, $$0, $$1);
/*  61 */     this.mode = (StructureMode)$$1.getValue((Property)StructureBlock.MODE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  66 */     super.saveAdditional($$0);
/*  67 */     $$0.putString("name", getStructureName());
/*  68 */     $$0.putString("author", this.author);
/*  69 */     $$0.putString("metadata", this.metaData);
/*  70 */     $$0.putInt("posX", this.structurePos.getX());
/*  71 */     $$0.putInt("posY", this.structurePos.getY());
/*  72 */     $$0.putInt("posZ", this.structurePos.getZ());
/*  73 */     $$0.putInt("sizeX", this.structureSize.getX());
/*  74 */     $$0.putInt("sizeY", this.structureSize.getY());
/*  75 */     $$0.putInt("sizeZ", this.structureSize.getZ());
/*  76 */     $$0.putString("rotation", this.rotation.toString());
/*  77 */     $$0.putString("mirror", this.mirror.toString());
/*  78 */     $$0.putString("mode", this.mode.toString());
/*  79 */     $$0.putBoolean("ignoreEntities", this.ignoreEntities);
/*  80 */     $$0.putBoolean("powered", this.powered);
/*  81 */     $$0.putBoolean("showair", this.showAir);
/*  82 */     $$0.putBoolean("showboundingbox", this.showBoundingBox);
/*  83 */     $$0.putFloat("integrity", this.integrity);
/*  84 */     $$0.putLong("seed", this.seed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  89 */     super.load($$0);
/*  90 */     setStructureName($$0.getString("name"));
/*  91 */     this.author = $$0.getString("author");
/*  92 */     this.metaData = $$0.getString("metadata");
/*  93 */     int $$1 = Mth.clamp($$0.getInt("posX"), -48, 48);
/*  94 */     int $$2 = Mth.clamp($$0.getInt("posY"), -48, 48);
/*  95 */     int $$3 = Mth.clamp($$0.getInt("posZ"), -48, 48);
/*  96 */     this.structurePos = new BlockPos($$1, $$2, $$3);
/*  97 */     int $$4 = Mth.clamp($$0.getInt("sizeX"), 0, 48);
/*  98 */     int $$5 = Mth.clamp($$0.getInt("sizeY"), 0, 48);
/*  99 */     int $$6 = Mth.clamp($$0.getInt("sizeZ"), 0, 48);
/* 100 */     this.structureSize = new Vec3i($$4, $$5, $$6);
/*     */     try {
/* 102 */       this.rotation = Rotation.valueOf($$0.getString("rotation"));
/* 103 */     } catch (IllegalArgumentException $$7) {
/* 104 */       this.rotation = Rotation.NONE;
/*     */     } 
/*     */     try {
/* 107 */       this.mirror = Mirror.valueOf($$0.getString("mirror"));
/* 108 */     } catch (IllegalArgumentException $$8) {
/* 109 */       this.mirror = Mirror.NONE;
/*     */     } 
/*     */     try {
/* 112 */       this.mode = StructureMode.valueOf($$0.getString("mode"));
/* 113 */     } catch (IllegalArgumentException $$9) {
/* 114 */       this.mode = StructureMode.DATA;
/*     */     } 
/* 116 */     this.ignoreEntities = $$0.getBoolean("ignoreEntities");
/* 117 */     this.powered = $$0.getBoolean("powered");
/* 118 */     this.showAir = $$0.getBoolean("showair");
/* 119 */     this.showBoundingBox = $$0.getBoolean("showboundingbox");
/* 120 */     if ($$0.contains("integrity")) {
/* 121 */       this.integrity = $$0.getFloat("integrity");
/*     */     } else {
/* 123 */       this.integrity = 1.0F;
/*     */     } 
/* 125 */     this.seed = $$0.getLong("seed");
/* 126 */     updateBlockState();
/*     */   }
/*     */   
/*     */   private void updateBlockState() {
/* 130 */     if (this.level == null) {
/*     */       return;
/*     */     }
/* 133 */     BlockPos $$0 = getBlockPos();
/* 134 */     BlockState $$1 = this.level.getBlockState($$0);
/* 135 */     if ($$1.is(Blocks.STRUCTURE_BLOCK)) {
/* 136 */       this.level.setBlock($$0, (BlockState)$$1.setValue((Property)StructureBlock.MODE, (Comparable)this.mode), 2);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientboundBlockEntityDataPacket getUpdatePacket() {
/* 142 */     return ClientboundBlockEntityDataPacket.create(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompoundTag getUpdateTag() {
/* 147 */     return saveWithoutMetadata();
/*     */   }
/*     */   
/*     */   public boolean usedBy(Player $$0) {
/* 151 */     if (!$$0.canUseGameMasterBlocks()) {
/* 152 */       return false;
/*     */     }
/* 154 */     if (($$0.getCommandSenderWorld()).isClientSide) {
/* 155 */       $$0.openStructureBlock(this);
/*     */     }
/* 157 */     return true;
/*     */   }
/*     */   
/*     */   public String getStructureName() {
/* 161 */     return (this.structureName == null) ? "" : this.structureName.toString();
/*     */   }
/*     */   
/*     */   public boolean hasStructureName() {
/* 165 */     return (this.structureName != null);
/*     */   }
/*     */   
/*     */   public void setStructureName(@Nullable String $$0) {
/* 169 */     setStructureName(StringUtil.isNullOrEmpty($$0) ? null : ResourceLocation.tryParse($$0));
/*     */   }
/*     */   
/*     */   public void setStructureName(@Nullable ResourceLocation $$0) {
/* 173 */     this.structureName = $$0;
/*     */   }
/*     */   
/*     */   public void createdBy(LivingEntity $$0) {
/* 177 */     this.author = $$0.getName().getString();
/*     */   }
/*     */   
/*     */   public BlockPos getStructurePos() {
/* 181 */     return this.structurePos;
/*     */   }
/*     */   
/*     */   public void setStructurePos(BlockPos $$0) {
/* 185 */     this.structurePos = $$0;
/*     */   }
/*     */   
/*     */   public Vec3i getStructureSize() {
/* 189 */     return this.structureSize;
/*     */   }
/*     */   
/*     */   public void setStructureSize(Vec3i $$0) {
/* 193 */     this.structureSize = $$0;
/*     */   }
/*     */   
/*     */   public Mirror getMirror() {
/* 197 */     return this.mirror;
/*     */   }
/*     */   
/*     */   public void setMirror(Mirror $$0) {
/* 201 */     this.mirror = $$0;
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/* 205 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public void setRotation(Rotation $$0) {
/* 209 */     this.rotation = $$0;
/*     */   }
/*     */   
/*     */   public String getMetaData() {
/* 213 */     return this.metaData;
/*     */   }
/*     */   
/*     */   public void setMetaData(String $$0) {
/* 217 */     this.metaData = $$0;
/*     */   }
/*     */   
/*     */   public StructureMode getMode() {
/* 221 */     return this.mode;
/*     */   }
/*     */   
/*     */   public void setMode(StructureMode $$0) {
/* 225 */     this.mode = $$0;
/* 226 */     BlockState $$1 = this.level.getBlockState(getBlockPos());
/* 227 */     if ($$1.is(Blocks.STRUCTURE_BLOCK)) {
/* 228 */       this.level.setBlock(getBlockPos(), (BlockState)$$1.setValue((Property)StructureBlock.MODE, (Comparable)$$0), 2);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isIgnoreEntities() {
/* 233 */     return this.ignoreEntities;
/*     */   }
/*     */   
/*     */   public void setIgnoreEntities(boolean $$0) {
/* 237 */     this.ignoreEntities = $$0;
/*     */   }
/*     */   
/*     */   public float getIntegrity() {
/* 241 */     return this.integrity;
/*     */   }
/*     */   
/*     */   public void setIntegrity(float $$0) {
/* 245 */     this.integrity = $$0;
/*     */   }
/*     */   
/*     */   public long getSeed() {
/* 249 */     return this.seed;
/*     */   }
/*     */   
/*     */   public void setSeed(long $$0) {
/* 253 */     this.seed = $$0;
/*     */   }
/*     */   
/*     */   public boolean detectSize() {
/* 257 */     if (this.mode != StructureMode.SAVE) {
/* 258 */       return false;
/*     */     }
/* 260 */     BlockPos $$0 = getBlockPos();
/* 261 */     int $$1 = 80;
/* 262 */     BlockPos $$2 = new BlockPos($$0.getX() - 80, this.level.getMinBuildHeight(), $$0.getZ() - 80);
/* 263 */     BlockPos $$3 = new BlockPos($$0.getX() + 80, this.level.getMaxBuildHeight() - 1, $$0.getZ() + 80);
/*     */     
/* 265 */     Stream<BlockPos> $$4 = getRelatedCorners($$2, $$3);
/*     */     
/* 267 */     return calculateEnclosingBoundingBox($$0, $$4).filter($$1 -> {
/*     */           int $$2 = $$1.maxX() - $$1.minX();
/*     */           int $$3 = $$1.maxY() - $$1.minY();
/*     */           int $$4 = $$1.maxZ() - $$1.minZ();
/*     */           if ($$2 > 1 && $$3 > 1 && $$4 > 1) {
/*     */             this.structurePos = new BlockPos($$1.minX() - $$0.getX() + 1, $$1.minY() - $$0.getY() + 1, $$1.minZ() - $$0.getZ() + 1);
/*     */             this.structureSize = new Vec3i($$2 - 1, $$3 - 1, $$4 - 1);
/*     */             setChanged();
/*     */             BlockState $$5 = this.level.getBlockState($$0);
/*     */             this.level.sendBlockUpdated($$0, $$5, $$5, 3);
/*     */             return true;
/*     */           } 
/*     */           return false;
/* 280 */         }).isPresent();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Stream<BlockPos> getRelatedCorners(BlockPos $$0, BlockPos $$1) {
/* 286 */     Objects.requireNonNull(this.level); return BlockPos.betweenClosedStream($$0, $$1).filter($$0 -> this.level.getBlockState($$0).is(Blocks.STRUCTURE_BLOCK)).map(this.level::getBlockEntity)
/* 287 */       .filter($$0 -> $$0 instanceof StructureBlockEntity)
/* 288 */       .map($$0 -> (StructureBlockEntity)$$0)
/* 289 */       .filter($$0 -> ($$0.mode == StructureMode.CORNER && Objects.equals(this.structureName, $$0.structureName)))
/* 290 */       .map(BlockEntity::getBlockPos);
/*     */   }
/*     */   
/*     */   private static Optional<BoundingBox> calculateEnclosingBoundingBox(BlockPos $$0, Stream<BlockPos> $$1) {
/* 294 */     Iterator<BlockPos> $$2 = $$1.iterator();
/* 295 */     if (!$$2.hasNext()) {
/* 296 */       return Optional.empty();
/*     */     }
/*     */     
/* 299 */     BlockPos $$3 = $$2.next();
/* 300 */     BoundingBox $$4 = new BoundingBox($$3);
/* 301 */     if ($$2.hasNext()) {
/* 302 */       Objects.requireNonNull($$4); $$2.forEachRemaining($$4::encapsulate);
/*     */     } else {
/*     */       
/* 305 */       $$4.encapsulate($$0);
/*     */     } 
/* 307 */     return Optional.of($$4);
/*     */   }
/*     */   
/*     */   public boolean saveStructure() {
/* 311 */     if (this.mode != StructureMode.SAVE) {
/* 312 */       return false;
/*     */     }
/* 314 */     return saveStructure(true);
/*     */   }
/*     */   public boolean saveStructure(boolean $$0) {
/*     */     StructureTemplate $$4;
/* 318 */     if (this.structureName == null) {
/* 319 */       return false;
/*     */     }
/* 321 */     BlockPos $$1 = getBlockPos().offset((Vec3i)this.structurePos);
/*     */     
/* 323 */     ServerLevel $$2 = (ServerLevel)this.level;
/* 324 */     StructureTemplateManager $$3 = $$2.getStructureManager();
/*     */     
/*     */     try {
/* 327 */       $$4 = $$3.getOrCreate(this.structureName);
/* 328 */     } catch (ResourceLocationException $$5) {
/* 329 */       return false;
/*     */     } 
/*     */     
/* 332 */     $$4.fillFromWorld(this.level, $$1, this.structureSize, !this.ignoreEntities, Blocks.STRUCTURE_VOID);
/* 333 */     $$4.setAuthor(this.author);
/* 334 */     if ($$0) {
/*     */       try {
/* 336 */         return $$3.save(this.structureName);
/* 337 */       } catch (ResourceLocationException $$7) {
/* 338 */         return false;
/*     */       } 
/*     */     }
/* 341 */     return true;
/*     */   }
/*     */   
/*     */   public static RandomSource createRandom(long $$0) {
/* 345 */     if ($$0 == 0L) {
/* 346 */       return RandomSource.create(Util.getMillis());
/*     */     }
/* 348 */     return RandomSource.create($$0);
/*     */   }
/*     */   
/*     */   public boolean placeStructureIfSameSize(ServerLevel $$0) {
/* 352 */     if (this.mode != StructureMode.LOAD || this.structureName == null) {
/* 353 */       return false;
/*     */     }
/* 355 */     StructureTemplate $$1 = $$0.getStructureManager().get(this.structureName).orElse(null);
/* 356 */     if ($$1 == null) {
/* 357 */       return false;
/*     */     }
/*     */     
/* 360 */     if ($$1.getSize().equals(this.structureSize)) {
/* 361 */       placeStructure($$0, $$1);
/* 362 */       return true;
/*     */     } 
/* 364 */     loadStructureInfo($$1);
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean loadStructureInfo(ServerLevel $$0) {
/* 370 */     StructureTemplate $$1 = getStructureTemplate($$0);
/* 371 */     if ($$1 == null) {
/* 372 */       return false;
/*     */     }
/* 374 */     loadStructureInfo($$1);
/* 375 */     return true;
/*     */   }
/*     */   
/*     */   private void loadStructureInfo(StructureTemplate $$0) {
/* 379 */     this.author = !StringUtil.isNullOrEmpty($$0.getAuthor()) ? $$0.getAuthor() : "";
/* 380 */     this.structureSize = $$0.getSize();
/* 381 */     setChanged();
/*     */   }
/*     */   
/*     */   public void placeStructure(ServerLevel $$0) {
/* 385 */     StructureTemplate $$1 = getStructureTemplate($$0);
/* 386 */     if ($$1 != null) {
/* 387 */       placeStructure($$0, $$1);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private StructureTemplate getStructureTemplate(ServerLevel $$0) {
/* 393 */     if (this.structureName == null) {
/* 394 */       return null;
/*     */     }
/* 396 */     return $$0.getStructureManager().get(this.structureName).orElse(null);
/*     */   }
/*     */   
/*     */   private void placeStructure(ServerLevel $$0, StructureTemplate $$1) {
/* 400 */     loadStructureInfo($$1);
/*     */     
/* 402 */     StructurePlaceSettings $$2 = (new StructurePlaceSettings()).setMirror(this.mirror).setRotation(this.rotation).setIgnoreEntities(this.ignoreEntities);
/* 403 */     if (this.integrity < 1.0F) {
/* 404 */       $$2.clearProcessors().addProcessor((StructureProcessor)new BlockRotProcessor(Mth.clamp(this.integrity, 0.0F, 1.0F))).setRandom(createRandom(this.seed));
/*     */     }
/* 406 */     BlockPos $$3 = getBlockPos().offset((Vec3i)this.structurePos);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 413 */     $$1.placeInWorld((ServerLevelAccessor)$$0, $$3, $$3, $$2, createRandom(this.seed), 2);
/*     */   }
/*     */   
/*     */   public void unloadStructure() {
/* 417 */     if (this.structureName == null) {
/*     */       return;
/*     */     }
/* 420 */     ServerLevel $$0 = (ServerLevel)this.level;
/* 421 */     StructureTemplateManager $$1 = $$0.getStructureManager();
/* 422 */     $$1.remove(this.structureName);
/*     */   }
/*     */   
/*     */   public boolean isStructureLoadable() {
/* 426 */     if (this.mode != StructureMode.LOAD || this.level.isClientSide || this.structureName == null) {
/* 427 */       return false;
/*     */     }
/* 429 */     ServerLevel $$0 = (ServerLevel)this.level;
/* 430 */     StructureTemplateManager $$1 = $$0.getStructureManager();
/*     */     try {
/* 432 */       return $$1.get(this.structureName).isPresent();
/* 433 */     } catch (ResourceLocationException $$2) {
/* 434 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/* 439 */     return this.powered;
/*     */   }
/*     */   
/*     */   public void setPowered(boolean $$0) {
/* 443 */     this.powered = $$0;
/*     */   }
/*     */   
/*     */   public boolean getShowAir() {
/* 447 */     return this.showAir;
/*     */   }
/*     */   
/*     */   public void setShowAir(boolean $$0) {
/* 451 */     this.showAir = $$0;
/*     */   }
/*     */   
/*     */   public boolean getShowBoundingBox() {
/* 455 */     return this.showBoundingBox;
/*     */   }
/*     */   
/*     */   public void setShowBoundingBox(boolean $$0) {
/* 459 */     this.showBoundingBox = $$0;
/*     */   }
/*     */   
/*     */   public enum UpdateType {
/* 463 */     UPDATE_DATA,
/* 464 */     SAVE_AREA,
/* 465 */     LOAD_AREA,
/* 466 */     SCAN_AREA;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\StructureBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */