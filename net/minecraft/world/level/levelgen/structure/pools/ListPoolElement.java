/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.function.BiFunction;
/*    */ import java.util.stream.Collectors;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.RandomSource;
/*    */ import net.minecraft.world.level.StructureManager;
/*    */ import net.minecraft.world.level.WorldGenLevel;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*    */ 
/*    */ public class ListPoolElement extends StructurePoolElement {
/*    */   static {
/* 22 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)StructurePoolElement.CODEC.listOf().fieldOf("elements").forGetter(()), (App)projectionCodec()).apply((Applicative)$$0, ListPoolElement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<ListPoolElement> CODEC;
/*    */   private final List<StructurePoolElement> elements;
/*    */   
/*    */   public ListPoolElement(List<StructurePoolElement> $$0, StructureTemplatePool.Projection $$1) {
/* 30 */     super($$1);
/* 31 */     if ($$0.isEmpty()) {
/* 32 */       throw new IllegalArgumentException("Elements are empty");
/*    */     }
/* 34 */     this.elements = $$0;
/* 35 */     setProjectionOnEachElement($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3i getSize(StructureTemplateManager $$0, Rotation $$1) {
/* 40 */     int $$2 = 0;
/* 41 */     int $$3 = 0;
/* 42 */     int $$4 = 0;
/* 43 */     for (StructurePoolElement $$5 : this.elements) {
/* 44 */       Vec3i $$6 = $$5.getSize($$0, $$1);
/* 45 */       $$2 = Math.max($$2, $$6.getX());
/* 46 */       $$3 = Math.max($$3, $$6.getY());
/* 47 */       $$4 = Math.max($$4, $$6.getZ());
/*    */     } 
/*    */     
/* 50 */     return new Vec3i($$2, $$3, $$4);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<StructureTemplate.StructureBlockInfo> getShuffledJigsawBlocks(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2, RandomSource $$3) {
/* 55 */     return ((StructurePoolElement)this.elements.get(0)).getShuffledJigsawBlocks($$0, $$1, $$2, $$3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BoundingBox getBoundingBox(StructureTemplateManager $$0, BlockPos $$1, Rotation $$2) {
/* 62 */     Stream<BoundingBox> $$3 = this.elements.stream().filter($$0 -> ($$0 != EmptyPoolElement.INSTANCE)).map($$3 -> $$3.getBoundingBox($$0, $$1, $$2));
/*    */     
/* 64 */     Objects.requireNonNull($$3); return (BoundingBox)BoundingBox.encapsulatingBoxes($$3::iterator).orElseThrow(() -> new IllegalStateException("Unable to calculate boundingbox for ListPoolElement"));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean place(StructureTemplateManager $$0, WorldGenLevel $$1, StructureManager $$2, ChunkGenerator $$3, BlockPos $$4, BlockPos $$5, Rotation $$6, BoundingBox $$7, RandomSource $$8, boolean $$9) {
/* 69 */     for (StructurePoolElement $$10 : this.elements) {
/* 70 */       if (!$$10.place($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8, $$9)) {
/* 71 */         return false;
/*    */       }
/*    */     } 
/* 74 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePoolElementType<?> getType() {
/* 79 */     return StructurePoolElementType.LIST;
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePoolElement setProjection(StructureTemplatePool.Projection $$0) {
/* 84 */     super.setProjection($$0);
/* 85 */     setProjectionOnEachElement($$0);
/* 86 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 91 */     return "List[" + (String)this.elements.stream().map(Object::toString).collect(Collectors.joining(", ")) + "]";
/*    */   }
/*    */   
/*    */   private void setProjectionOnEachElement(StructureTemplatePool.Projection $$0) {
/* 95 */     this.elements.forEach($$1 -> $$1.setProjection($$0));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\ListPoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */