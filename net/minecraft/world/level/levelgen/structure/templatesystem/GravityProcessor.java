/*    */ package net.minecraft.world.level.levelgen.structure.templatesystem;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.function.BiFunction;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.levelgen.Heightmap;
/*    */ 
/*    */ public class GravityProcessor extends StructureProcessor {
/*    */   static {
/* 13 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)Heightmap.Types.CODEC.fieldOf("heightmap").orElse(Heightmap.Types.WORLD_SURFACE_WG).forGetter(()), (App)Codec.INT.fieldOf("offset").orElse(Integer.valueOf(0)).forGetter(())).apply((Applicative)$$0, GravityProcessor::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<GravityProcessor> CODEC;
/*    */   private final Heightmap.Types heightmap;
/*    */   private final int offset;
/*    */   
/*    */   public GravityProcessor(Heightmap.Types $$0, int $$1) {
/* 22 */     this.heightmap = $$0;
/* 23 */     this.offset = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public StructureTemplate.StructureBlockInfo processBlock(LevelReader $$0, BlockPos $$1, BlockPos $$2, StructureTemplate.StructureBlockInfo $$3, StructureTemplate.StructureBlockInfo $$4, StructurePlaceSettings $$5) {
/*    */     Heightmap.Types $$9;
/* 30 */     if ($$0 instanceof net.minecraft.server.level.ServerLevel) {
/*    */       
/* 32 */       if (this.heightmap == Heightmap.Types.WORLD_SURFACE_WG) {
/* 33 */         Heightmap.Types $$6 = Heightmap.Types.WORLD_SURFACE;
/* 34 */       } else if (this.heightmap == Heightmap.Types.OCEAN_FLOOR_WG) {
/* 35 */         Heightmap.Types $$7 = Heightmap.Types.OCEAN_FLOOR;
/*    */       } else {
/* 37 */         Heightmap.Types $$8 = this.heightmap;
/*    */       } 
/*    */     } else {
/* 40 */       $$9 = this.heightmap;
/*    */     } 
/* 42 */     BlockPos $$10 = $$4.pos();
/* 43 */     int $$11 = $$0.getHeight($$9, $$10.getX(), $$10.getZ()) + this.offset;
/* 44 */     int $$12 = $$3.pos().getY();
/* 45 */     return new StructureTemplate.StructureBlockInfo(new BlockPos($$10.getX(), $$11 + $$12, $$10.getZ()), $$4.state(), $$4.nbt());
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureProcessorType<?> getType() {
/* 50 */     return StructureProcessorType.GRAVITY;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\templatesystem\GravityProcessor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */