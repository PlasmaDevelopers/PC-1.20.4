/*     */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ 
/*     */ public class StructurePlaceSettings {
/*  16 */   private Mirror mirror = Mirror.NONE;
/*  17 */   private Rotation rotation = Rotation.NONE;
/*  18 */   private BlockPos rotationPivot = BlockPos.ZERO;
/*     */   private boolean ignoreEntities;
/*     */   @Nullable
/*     */   private BoundingBox boundingBox;
/*     */   private boolean keepLiquids = true;
/*     */   @Nullable
/*     */   private RandomSource random;
/*     */   private int palette;
/*  26 */   private final List<StructureProcessor> processors = Lists.newArrayList();
/*     */   private boolean knownShape;
/*     */   private boolean finalizeEntities;
/*     */   
/*     */   public StructurePlaceSettings copy() {
/*  31 */     StructurePlaceSettings $$0 = new StructurePlaceSettings();
/*  32 */     $$0.mirror = this.mirror;
/*  33 */     $$0.rotation = this.rotation;
/*  34 */     $$0.rotationPivot = this.rotationPivot;
/*  35 */     $$0.ignoreEntities = this.ignoreEntities;
/*  36 */     $$0.boundingBox = this.boundingBox;
/*  37 */     $$0.keepLiquids = this.keepLiquids;
/*  38 */     $$0.random = this.random;
/*  39 */     $$0.palette = this.palette;
/*  40 */     $$0.processors.addAll(this.processors);
/*  41 */     $$0.knownShape = this.knownShape;
/*  42 */     $$0.finalizeEntities = this.finalizeEntities;
/*  43 */     return $$0;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setMirror(Mirror $$0) {
/*  47 */     this.mirror = $$0;
/*  48 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setRotation(Rotation $$0) {
/*  52 */     this.rotation = $$0;
/*  53 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setRotationPivot(BlockPos $$0) {
/*  57 */     this.rotationPivot = $$0;
/*  58 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setIgnoreEntities(boolean $$0) {
/*  62 */     this.ignoreEntities = $$0;
/*  63 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setBoundingBox(BoundingBox $$0) {
/*  67 */     this.boundingBox = $$0;
/*  68 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setRandom(@Nullable RandomSource $$0) {
/*  72 */     this.random = $$0;
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setKeepLiquids(boolean $$0) {
/*  77 */     this.keepLiquids = $$0;
/*  78 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setKnownShape(boolean $$0) {
/*  82 */     this.knownShape = $$0;
/*  83 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings clearProcessors() {
/*  87 */     this.processors.clear();
/*  88 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings addProcessor(StructureProcessor $$0) {
/*  92 */     this.processors.add($$0);
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings popProcessor(StructureProcessor $$0) {
/*  97 */     this.processors.remove($$0);
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public Mirror getMirror() {
/* 102 */     return this.mirror;
/*     */   }
/*     */   
/*     */   public Rotation getRotation() {
/* 106 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public BlockPos getRotationPivot() {
/* 110 */     return this.rotationPivot;
/*     */   }
/*     */   
/*     */   public RandomSource getRandom(@Nullable BlockPos $$0) {
/* 114 */     if (this.random != null) {
/* 115 */       return this.random;
/*     */     }
/*     */     
/* 118 */     if ($$0 == null) {
/* 119 */       return RandomSource.create(Util.getMillis());
/*     */     }
/*     */     
/* 122 */     return RandomSource.create(Mth.getSeed((Vec3i)$$0));
/*     */   }
/*     */   
/*     */   public boolean isIgnoreEntities() {
/* 126 */     return this.ignoreEntities;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public BoundingBox getBoundingBox() {
/* 131 */     return this.boundingBox;
/*     */   }
/*     */   
/*     */   public boolean getKnownShape() {
/* 135 */     return this.knownShape;
/*     */   }
/*     */   
/*     */   public List<StructureProcessor> getProcessors() {
/* 139 */     return this.processors;
/*     */   }
/*     */   
/*     */   public boolean shouldKeepLiquids() {
/* 143 */     return this.keepLiquids;
/*     */   }
/*     */   
/*     */   public StructureTemplate.Palette getRandomPalette(List<StructureTemplate.Palette> $$0, @Nullable BlockPos $$1) {
/* 147 */     int $$2 = $$0.size();
/* 148 */     if ($$2 == 0)
/*     */     {
/* 150 */       throw new IllegalStateException("No palettes");
/*     */     }
/* 152 */     return $$0.get(getRandom($$1).nextInt($$2));
/*     */   }
/*     */   
/*     */   public StructurePlaceSettings setFinalizeEntities(boolean $$0) {
/* 156 */     this.finalizeEntities = $$0;
/* 157 */     return this;
/*     */   }
/*     */   
/*     */   public boolean shouldFinalizeEntities() {
/* 161 */     return this.finalizeEntities;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\StructurePlaceSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */