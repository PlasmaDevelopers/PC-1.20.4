/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.JigsawBlock;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.placement.PlacedFeature;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ public class FeaturePoolElement extends StructurePoolElement {
/*    */   static {
/* 28 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)PlacedFeature.CODEC.fieldOf("feature").forGetter(()), (App)projectionCodec()).apply((Applicative)$$0, FeaturePoolElement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<FeaturePoolElement> CODEC;
/*    */   private final Holder<PlacedFeature> feature;
/*    */   private final CompoundTag defaultJigsawNBT;
/*    */   
/*    */   protected FeaturePoolElement(Holder<PlacedFeature> $$0, StructureTemplatePool.Projection $$1) {
/* 37 */     super($$1);
/* 38 */     this.feature = $$0;
/* 39 */     this.defaultJigsawNBT = fillDefaultJigsawNBT();
/*    */   }
/*    */   
/*    */   private CompoundTag fillDefaultJigsawNBT() {
/* 43 */     CompoundTag $$0 = new CompoundTag();
/* 44 */     $$0.putString("name", "minecraft:bottom");
/* 45 */     $$0.putString("final_state", "minecraft:air");
/*    */ 
/*    */     
/* 48 */     $$0.putString("pool", "minecraft:empty");
/* 49 */     $$0.putString("target", "minecraft:empty");
/* 50 */     $$0.putString("joint", JigsawBlockEntity.JointType.ROLLABLE.getSerializedName());
/*    */     
/* 52 */     return $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3i getSize(StructureTemplateManager $$0, Rotation $$1) {
/* 57 */     return Vec3i.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, RandomSource $$3) {
/* 62 */     List<StructureTemplate.StructureBlockInfo> $$4 = Lists.newArrayList();
/* 63 */     $$4.add(new StructureTemplate.StructureBlockInfo($$1, (BlockState)Blocks.JIGSAW.defaultBlockState().setValue((Property)JigsawBlock.ORIENTATION, (Comparable)FrontAndTop.fromFrontAndTop(Direction.DOWN, Direction.SOUTH)), this.defaultJigsawNBT));
/* 64 */     return $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public BoundingBox getBoundingBox(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2) {
/* 69 */     Vec3i $$3 = getSize($$0, $$2);
/* 70 */     return new BoundingBox($$1.getX(), $$1.getY(), $$1.getZ(), $$1.getX() + $$3.getX(), $$1.getY() + $$3.getY(), $$1.getZ() + $$3.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(StructureTemplateManager $$0, WorldGenLevel $$1, StructureManager $$2, ChunkGenerator $$3, BlockPos $$4, BlockPos $$5, Rotation $$6, BoundingBox $$7, RandomSource $$8, boolean $$9) {
/* 75 */     return ((PlacedFeature)this.feature.value()).place($$1, $$3, $$8, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePoolElementType<?> getType() {
/* 80 */     return StructurePoolElementType.FEATURE;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 85 */     return "Feature[" + this.feature + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\FeaturePoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */