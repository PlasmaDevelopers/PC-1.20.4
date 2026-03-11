/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ public class EmptyPoolElement
/*    */   extends StructurePoolElement {
/* 19 */   public static final Codec<EmptyPoolElement> CODEC = Codec.unit(() -> INSTANCE);
/*    */   
/* 21 */   public static final EmptyPoolElement INSTANCE = new EmptyPoolElement();
/*    */   
/*    */   private EmptyPoolElement() {
/* 24 */     super(StructureTemplatePool.Projection.TERRAIN_MATCHING);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3i getSize(StructureTemplateManager $$0, Rotation $$1) {
/* 29 */     return Vec3i.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, RandomSource $$3) {
/* 34 */     return Collections.emptyList();
/*    */   }
/*    */ 
/*    */   
/*    */   public BoundingBox getBoundingBox(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2) {
/* 39 */     throw new IllegalStateException("Invalid call to EmtyPoolElement.getBoundingBox, filter me!");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(StructureTemplateManager $$0, WorldGenLevel $$1, StructureManager $$2, ChunkGenerator $$3, BlockPos $$4, BlockPos $$5, Rotation $$6, BoundingBox $$7, RandomSource $$8, boolean $$9) {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePoolElementType<?> getType() {
/* 49 */     return StructurePoolElementType.EMPTY;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 54 */     return "Empty";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\EmptyPoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */