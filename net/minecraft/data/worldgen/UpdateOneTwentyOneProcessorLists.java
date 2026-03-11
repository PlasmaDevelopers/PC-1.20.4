/*    */ package net.minecraft.data.worldgen;
/*    */ import java.util.List;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.tags.BlockTags;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.block.CopperBulbBlock;
/*    */ import net.minecraft.world.level.block.state.BlockState;
/*    */ import net.minecraft.world.level.block.state.properties.Property;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.ProtectedBlockProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*    */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
/*    */ 
/*    */ public class UpdateOneTwentyOneProcessorLists {
/* 21 */   public static final ResourceKey<StructureProcessorList> TRIAL_CHAMBERS_COPPER_BULB_DEGRADATION = ResourceKey.create(Registries.PROCESSOR_LIST, new ResourceLocation("trial_chambers_copper_bulb_degradation"));
/*    */   
/*    */   public static void bootstrap(BootstapContext<StructureProcessorList> $$0) {
/* 24 */     register($$0, TRIAL_CHAMBERS_COPPER_BULB_DEGRADATION, (List)List.of(new RuleProcessor(
/* 25 */             List.of(new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WAXED_COPPER_BULB, 0.1F), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)Blocks.OXIDIZED_COPPER_BULB
/* 26 */                 .defaultBlockState().setValue((Property)CopperBulbBlock.LIT, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WAXED_COPPER_BULB, 0.33333334F), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)Blocks.WEATHERED_COPPER_BULB
/* 27 */                 .defaultBlockState().setValue((Property)CopperBulbBlock.LIT, Boolean.valueOf(true))), new ProcessorRule((RuleTest)new RandomBlockMatchTest(Blocks.WAXED_COPPER_BULB, 0.5F), (RuleTest)AlwaysTrueTest.INSTANCE, (BlockState)Blocks.EXPOSED_COPPER_BULB
/* 28 */                 .defaultBlockState().setValue((Property)CopperBulbBlock.LIT, Boolean.valueOf(true))))), new ProtectedBlockProcessor(BlockTags.FEATURES_CANNOT_REPLACE)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static void register(BootstapContext<StructureProcessorList> $$0, ResourceKey<StructureProcessorList> $$1, List<StructureProcessor> $$2) {
/* 35 */     $$0.register($$1, new StructureProcessorList($$2));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\worldgen\UpdateOneTwentyOneProcessorLists.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */