/*     */ package net.minecraft.data;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.BiFunction;
/*     */ import java.util.stream.Collectors;
/*     */ import joptsimple.AbstractOptionSpec;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import joptsimple.OptionSpecBuilder;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.WorldVersion;
/*     */ import net.minecraft.core.HolderLookup;
/*     */ import net.minecraft.core.RegistrySetBuilder;
/*     */ import net.minecraft.data.advancements.packs.UpdateOneTwentyOneAdvancementProvider;
/*     */ import net.minecraft.data.advancements.packs.VanillaAdvancementProvider;
/*     */ import net.minecraft.data.loot.packs.TradeRebalanceLootTableProvider;
/*     */ import net.minecraft.data.loot.packs.UpdateOneTwentyOneLootTableProvider;
/*     */ import net.minecraft.data.loot.packs.VanillaLootTableProvider;
/*     */ import net.minecraft.data.metadata.PackMetadataGenerator;
/*     */ import net.minecraft.data.registries.UpdateOneTwentyOneRegistries;
/*     */ import net.minecraft.data.registries.VanillaRegistries;
/*     */ import net.minecraft.data.structures.NbtToSnbt;
/*     */ import net.minecraft.data.structures.SnbtToNbt;
/*     */ import net.minecraft.data.structures.StructureUpdater;
/*     */ import net.minecraft.data.tags.TagsProvider;
/*     */ import net.minecraft.data.tags.UpdateOneTwentyOneBiomeTagsProvider;
/*     */ import net.minecraft.data.tags.UpdateOneTwentyOneBlockTagsProvider;
/*     */ import net.minecraft.data.tags.UpdateOneTwentyOneItemTagsProvider;
/*     */ import net.minecraft.data.tags.VanillaItemTagsProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.obfuscate.DontObfuscate;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Main
/*     */ {
/*     */   @DontObfuscate
/*     */   public static void main(String[] $$0) throws IOException {
/*  72 */     SharedConstants.tryDetectVersion();
/*     */     
/*  74 */     OptionParser $$1 = new OptionParser();
/*  75 */     AbstractOptionSpec abstractOptionSpec = $$1.accepts("help", "Show the help menu").forHelp();
/*  76 */     OptionSpecBuilder optionSpecBuilder1 = $$1.accepts("server", "Include server generators");
/*  77 */     OptionSpecBuilder optionSpecBuilder2 = $$1.accepts("client", "Include client generators");
/*  78 */     OptionSpecBuilder optionSpecBuilder3 = $$1.accepts("dev", "Include development tools");
/*  79 */     OptionSpecBuilder optionSpecBuilder4 = $$1.accepts("reports", "Include data reports");
/*  80 */     OptionSpecBuilder optionSpecBuilder5 = $$1.accepts("validate", "Validate inputs");
/*  81 */     OptionSpecBuilder optionSpecBuilder6 = $$1.accepts("all", "Include all generators");
/*  82 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = $$1.accepts("output", "Output folder").withRequiredArg().defaultsTo("generated", (Object[])new String[0]);
/*  83 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = $$1.accepts("input", "Input folder").withRequiredArg();
/*  84 */     OptionSet $$11 = $$1.parse($$0);
/*     */     
/*  86 */     if ($$11.has((OptionSpec)abstractOptionSpec) || !$$11.hasOptions()) {
/*  87 */       $$1.printHelpOn(System.out);
/*     */       
/*     */       return;
/*     */     } 
/*  91 */     Path $$12 = Paths.get((String)argumentAcceptingOptionSpec1.value($$11), new String[0]);
/*  92 */     boolean $$13 = $$11.has((OptionSpec)optionSpecBuilder6);
/*  93 */     boolean $$14 = ($$13 || $$11.has((OptionSpec)optionSpecBuilder2));
/*  94 */     boolean $$15 = ($$13 || $$11.has((OptionSpec)optionSpecBuilder1));
/*  95 */     boolean $$16 = ($$13 || $$11.has((OptionSpec)optionSpecBuilder3));
/*  96 */     boolean $$17 = ($$13 || $$11.has((OptionSpec)optionSpecBuilder4));
/*  97 */     boolean $$18 = ($$13 || $$11.has((OptionSpec)optionSpecBuilder5));
/*  98 */     DataGenerator $$19 = createStandardGenerator($$12, (Collection<Path>)$$11.valuesOf((OptionSpec)argumentAcceptingOptionSpec2).stream().map($$0 -> Paths.get($$0, new String[0])).collect(Collectors.toList()), $$14, $$15, $$16, $$17, $$18, SharedConstants.getCurrentVersion(), true);
/*  99 */     $$19.run();
/*     */   }
/*     */   
/*     */   private static <T extends DataProvider> DataProvider.Factory<T> bindRegistries(BiFunction<PackOutput, CompletableFuture<HolderLookup.Provider>, T> $$0, CompletableFuture<HolderLookup.Provider> $$1) {
/* 103 */     return $$2 -> (DataProvider)$$0.apply($$2, $$1);
/*     */   }
/*     */   
/*     */   public static DataGenerator createStandardGenerator(Path $$0, Collection<Path> $$1, boolean $$2, boolean $$3, boolean $$4, boolean $$5, boolean $$6, WorldVersion $$7, boolean $$8) {
/* 107 */     DataGenerator $$9 = new DataGenerator($$0, $$7, $$8);
/*     */ 
/*     */     
/* 110 */     DataGenerator.PackGenerator $$10 = $$9.getVanillaPack(($$2 || $$3));
/* 111 */     $$10.addProvider($$1 -> (new SnbtToNbt($$1, $$0)).addFilter((SnbtToNbt.Filter)new StructureUpdater()));
/*     */ 
/*     */     
/* 114 */     CompletableFuture<HolderLookup.Provider> $$11 = CompletableFuture.supplyAsync(VanillaRegistries::createLookup, Util.backgroundExecutor());
/*     */ 
/*     */     
/* 117 */     DataGenerator.PackGenerator $$12 = $$9.getVanillaPack($$2);
/* 118 */     $$12.addProvider(net.minecraft.data.models.ModelProvider::new);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 125 */     DataGenerator.PackGenerator $$13 = $$9.getVanillaPack($$3);
/*     */     
/* 127 */     $$13.addProvider(bindRegistries(net.minecraft.data.registries.RegistriesDatapackGenerator::new, $$11));
/*     */ 
/*     */     
/* 130 */     $$13.addProvider(bindRegistries(VanillaAdvancementProvider::create, $$11));
/* 131 */     $$13.addProvider(VanillaLootTableProvider::create);
/* 132 */     $$13.addProvider(net.minecraft.data.recipes.packs.VanillaRecipeProvider::new);
/*     */ 
/*     */     
/* 135 */     TagsProvider<Block> $$14 = $$13.<TagsProvider<Block>>addProvider(bindRegistries(net.minecraft.data.tags.VanillaBlockTagsProvider::new, $$11));
/* 136 */     TagsProvider<Item> $$15 = $$13.<TagsProvider<Item>>addProvider($$2 -> new VanillaItemTagsProvider($$2, $$0, $$1.contentsGetter()));
/* 137 */     TagsProvider<Biome> $$16 = $$13.<TagsProvider<Biome>>addProvider(bindRegistries(net.minecraft.data.tags.BiomeTagsProvider::new, $$11));
/*     */     
/* 139 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.BannerPatternTagsProvider::new, $$11));
/* 140 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.CatVariantTagsProvider::new, $$11));
/* 141 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.DamageTypeTagsProvider::new, $$11));
/* 142 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.EntityTypeTagsProvider::new, $$11));
/* 143 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.FlatLevelGeneratorPresetTagsProvider::new, $$11));
/* 144 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.FluidTagsProvider::new, $$11));
/* 145 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.GameEventTagsProvider::new, $$11));
/* 146 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.InstrumentTagsProvider::new, $$11));
/* 147 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.PaintingVariantTagsProvider::new, $$11));
/* 148 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.PoiTypeTagsProvider::new, $$11));
/* 149 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.StructureTagsProvider::new, $$11));
/* 150 */     $$13.addProvider(bindRegistries(net.minecraft.data.tags.WorldPresetTagsProvider::new, $$11));
/*     */ 
/*     */ 
/*     */     
/* 154 */     DataGenerator.PackGenerator $$17 = $$9.getVanillaPack($$4);
/* 155 */     $$17.addProvider($$1 -> new NbtToSnbt($$1, $$0));
/*     */ 
/*     */ 
/*     */     
/* 159 */     DataGenerator.PackGenerator $$18 = $$9.getVanillaPack($$5);
/* 160 */     $$18.addProvider(bindRegistries(net.minecraft.data.info.BiomeParametersDumpReport::new, $$11));
/* 161 */     $$18.addProvider(net.minecraft.data.info.BlockListReport::new);
/* 162 */     $$18.addProvider(bindRegistries(net.minecraft.data.info.CommandsReport::new, $$11));
/* 163 */     $$18.addProvider(net.minecraft.data.info.RegistryDumpReport::new);
/*     */ 
/*     */ 
/*     */     
/* 167 */     DataGenerator.PackGenerator $$19 = $$9.getBuiltinDatapack($$3, "bundle");
/* 168 */     $$19.addProvider(net.minecraft.data.recipes.packs.BundleRecipeProvider::new);
/* 169 */     $$19.addProvider($$0 -> PackMetadataGenerator.forFeaturePack($$0, (Component)Component.translatable("dataPack.bundle.description"), FeatureFlagSet.of(FeatureFlags.BUNDLE)));
/*     */ 
/*     */ 
/*     */     
/* 173 */     DataGenerator.PackGenerator $$20 = $$9.getBuiltinDatapack($$3, "trade_rebalance");
/* 174 */     $$20.addProvider($$0 -> PackMetadataGenerator.forFeaturePack($$0, (Component)Component.translatable("dataPack.trade_rebalance.description"), FeatureFlagSet.of(FeatureFlags.TRADE_REBALANCE)));
/* 175 */     $$20.addProvider(TradeRebalanceLootTableProvider::create);
/* 176 */     $$20.addProvider(bindRegistries(net.minecraft.data.tags.TradeRebalanceStructureTagsProvider::new, $$11));
/*     */ 
/*     */     
/* 179 */     CompletableFuture<RegistrySetBuilder.PatchedRegistries> $$21 = UpdateOneTwentyOneRegistries.createLookup($$11);
/*     */     
/* 181 */     CompletableFuture<HolderLookup.Provider> $$22 = $$21.thenApply(RegistrySetBuilder.PatchedRegistries::full);
/* 182 */     CompletableFuture<HolderLookup.Provider> $$23 = $$21.thenApply(RegistrySetBuilder.PatchedRegistries::patches);
/*     */     
/* 184 */     DataGenerator.PackGenerator $$24 = $$9.getBuiltinDatapack($$3, "update_1_21");
/* 185 */     $$24.addProvider(net.minecraft.data.recipes.packs.UpdateOneTwentyOneRecipeProvider::new);
/* 186 */     TagsProvider<Block> $$25 = $$24.<TagsProvider<Block>>addProvider($$2 -> new UpdateOneTwentyOneBlockTagsProvider($$2, $$0, $$1.contentsGetter()));
/* 187 */     $$24.addProvider($$3 -> new UpdateOneTwentyOneItemTagsProvider($$3, $$0, $$1.contentsGetter(), $$2.contentsGetter()));
/* 188 */     $$24.addProvider($$2 -> new UpdateOneTwentyOneBiomeTagsProvider($$2, $$0, $$1.contentsGetter()));
/* 189 */     $$24.addProvider(UpdateOneTwentyOneLootTableProvider::create);
/* 190 */     $$24.addProvider(bindRegistries(net.minecraft.data.registries.RegistriesDatapackGenerator::new, $$23));
/* 191 */     $$24.addProvider($$0 -> PackMetadataGenerator.forFeaturePack($$0, (Component)Component.translatable("dataPack.update_1_21.description"), FeatureFlagSet.of(FeatureFlags.UPDATE_1_21)));
/* 192 */     $$24.addProvider(bindRegistries(net.minecraft.data.tags.UpdateOneTwentyOneEntityTypeTagsProvider::new, $$22));
/* 193 */     $$24.addProvider(bindRegistries(net.minecraft.data.tags.UpdateOneTwentyOneDamageTypeTagsProvider::new, $$22));
/* 194 */     $$24.addProvider(bindRegistries(UpdateOneTwentyOneAdvancementProvider::create, $$22));
/*     */ 
/*     */     
/* 197 */     return $$9;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\data\Main.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */