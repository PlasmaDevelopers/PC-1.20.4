/*    */ package net.minecraft.world.level.levelgen.structure.pools;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockIgnoreProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*    */ 
/*    */ public class LegacySinglePoolElement extends SinglePoolElement {
/*    */   static {
/* 20 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)templateCodec(), (App)processorsCodec(), (App)projectionCodec()).apply((Applicative)$$0, LegacySinglePoolElement::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<LegacySinglePoolElement> CODEC;
/*    */   
/*    */   protected LegacySinglePoolElement(Either<ResourceLocation, StructureTemplate> $$0, Holder<StructureProcessorList> $$1, StructureTemplatePool.Projection $$2) {
/* 27 */     super($$0, $$1, $$2);
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructurePlaceSettings getSettings(Rotation $$0, BoundingBox $$1, boolean $$2) {
/* 32 */     StructurePlaceSettings $$3 = super.getSettings($$0, $$1, $$2);
/* 33 */     $$3.popProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_BLOCK);
/* 34 */     $$3.addProcessor((StructureProcessor)BlockIgnoreProcessor.STRUCTURE_AND_AIR);
/* 35 */     return $$3;
/*    */   }
/*    */ 
/*    */   
/*    */   public StructurePoolElementType<?> getType() {
/* 40 */     return StructurePoolElementType.LEGACY;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 45 */     return "LegacySingle[" + this.template + "]";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\structure\pools\LegacySinglePoolElement.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */