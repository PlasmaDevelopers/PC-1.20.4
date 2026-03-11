/*    */ package net.minecraft.world.level.levelgen.feature;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.util.Function5;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
/*    */ 
/*    */ public class FossilFeatureConfiguration implements FeatureConfiguration {
/*    */   static {
/* 14 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.listOf().fieldOf("fossil_structures").forGetter(()), (App)ResourceLocation.CODEC.listOf().fieldOf("overlay_structures").forGetter(()), (App)StructureProcessorType.LIST_CODEC.fieldOf("fossil_processors").forGetter(()), (App)StructureProcessorType.LIST_CODEC.fieldOf("overlay_processors").forGetter(()), (App)Codec.intRange(0, 7).fieldOf("max_empty_corners_allowed").forGetter(())).apply((Applicative)$$0, FossilFeatureConfiguration::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<FossilFeatureConfiguration> CODEC;
/*    */   
/*    */   public final List<ResourceLocation> fossilStructures;
/*    */   
/*    */   public final List<ResourceLocation> overlayStructures;
/*    */   
/*    */   public final Holder<StructureProcessorList> fossilProcessors;
/*    */   public final Holder<StructureProcessorList> overlayProcessors;
/*    */   public final int maxEmptyCornersAllowed;
/*    */   
/*    */   public FossilFeatureConfiguration(List<ResourceLocation> $$0, List<ResourceLocation> $$1, Holder<StructureProcessorList> $$2, Holder<StructureProcessorList> $$3, int $$4) {
/* 29 */     if ($$0.isEmpty()) {
/* 30 */       throw new IllegalArgumentException("Fossil structure lists need at least one entry");
/*    */     }
/* 32 */     if ($$0.size() != $$1.size()) {
/* 33 */       throw new IllegalArgumentException("Fossil structure lists must be equal lengths");
/*    */     }
/* 35 */     this.fossilStructures = $$0;
/* 36 */     this.overlayStructures = $$1;
/* 37 */     this.fossilProcessors = $$2;
/* 38 */     this.overlayProcessors = $$3;
/* 39 */     this.maxEmptyCornersAllowed = $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\levelgen\feature\FossilFeatureConfiguration.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */