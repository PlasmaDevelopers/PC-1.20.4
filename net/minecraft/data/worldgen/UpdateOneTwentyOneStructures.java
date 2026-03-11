/*    */ package net.minecraft.data.worldgen;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.HolderGetter;
/*    */ import net.minecraft.core.HolderSet;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.tags.BiomeTags;
/*    */ import net.minecraft.util.random.WeightedRandomList;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ import net.minecraft.world.level.biome.Biome;
/*    */ import net.minecraft.world.level.levelgen.GenerationStep;
/*    */ import net.minecraft.world.level.levelgen.VerticalAnchor;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
/*    */ import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
/*    */ import net.minecraft.world.level.levelgen.structure.Structure;
/*    */ import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
/*    */ import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
/*    */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*    */ import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
/*    */ 
/*    */ public class UpdateOneTwentyOneStructures {
/*    */   public static void bootstrap(BootstapContext<Structure> $$0) {
/* 25 */     HolderGetter<Biome> $$1 = $$0.lookup(Registries.BIOME);
/* 26 */     HolderGetter<StructureTemplatePool> $$2 = $$0.lookup(Registries.TEMPLATE_POOL);
/* 27 */     $$0.register(BuiltinStructures.TRIAL_CHAMBERS, new JigsawStructure(
/* 28 */           Structures.structure((HolderSet<Biome>)$$1
/* 29 */             .getOrThrow(BiomeTags.HAS_TRIAL_CHAMBERS), 
/* 30 */             (Map<MobCategory, StructureSpawnOverride>)Arrays.<MobCategory>stream(MobCategory.values()).collect(Collectors.toMap($$0 -> $$0, $$0 -> new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE, WeightedRandomList.create()))), GenerationStep.Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BURY), (Holder)$$2
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 36 */           .getOrThrow(TrialChambersStructurePools.START), 
/* 37 */           Optional.empty(), 20, 
/*    */           
/* 39 */           (HeightProvider)UniformHeight.of(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(-20)), false, 
/*    */           
/* 41 */           Optional.empty(), 116, TrialChambersStructurePools.ALIAS_BINDINGS));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\UpdateOneTwentyOneStructures.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */