/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.ResourceLocationException;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.ResourceKeyArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.arguments.TemplateMirrorArgument;
/*     */ import net.minecraft.commands.arguments.TemplateRotationArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.LevelHeightAccessor;
/*     */ import net.minecraft.world.level.ServerLevelAccessor;
/*     */ import net.minecraft.world.level.WorldGenLevel;
/*     */ import net.minecraft.world.level.block.Mirror;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.chunk.ChunkGenerator;
/*     */ import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import net.minecraft.world.level.levelgen.structure.StructureStart;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
/*     */ import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.BlockRotProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
/*     */ import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlaceCommand
/*     */ {
/*  59 */   private static final SimpleCommandExceptionType ERROR_FEATURE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.place.feature.failed"));
/*  60 */   private static final SimpleCommandExceptionType ERROR_JIGSAW_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.place.jigsaw.failed")); private static final DynamicCommandExceptionType ERROR_TEMPLATE_INVALID;
/*  61 */   private static final SimpleCommandExceptionType ERROR_STRUCTURE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.place.structure.failed")); static {
/*  62 */     ERROR_TEMPLATE_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.place.template.invalid", new Object[] { $$0 }));
/*  63 */   } private static final SimpleCommandExceptionType ERROR_TEMPLATE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.place.template.failed")); private static final SuggestionProvider<CommandSourceStack> SUGGEST_TEMPLATES;
/*     */   static {
/*  65 */     SUGGEST_TEMPLATES = (($$0, $$1) -> {
/*     */         StructureTemplateManager $$2 = ((CommandSourceStack)$$0.getSource()).getLevel().getStructureManager();
/*     */         return SharedSuggestionProvider.suggestResource($$2.listTemplates(), $$1);
/*     */       });
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  71 */     $$0.register(
/*  72 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("place")
/*  73 */         .requires($$0 -> $$0.hasPermission(2)))
/*  74 */         .then(
/*  75 */           Commands.literal("feature")
/*  76 */           .then((
/*  77 */             (RequiredArgumentBuilder)Commands.argument("feature", (ArgumentType)ResourceKeyArgument.key(Registries.CONFIGURED_FEATURE))
/*  78 */             .executes($$0 -> placeFeature((CommandSourceStack)$$0.getSource(), ResourceKeyArgument.getConfiguredFeature($$0, "feature"), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()))))
/*  79 */             .then(
/*  80 */               Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/*  81 */               .executes($$0 -> placeFeature((CommandSourceStack)$$0.getSource(), ResourceKeyArgument.getConfiguredFeature($$0, "feature"), BlockPosArgument.getLoadedBlockPos($$0, "pos")))))))
/*     */ 
/*     */ 
/*     */         
/*  85 */         .then(
/*  86 */           Commands.literal("jigsaw")
/*  87 */           .then(
/*  88 */             Commands.argument("pool", (ArgumentType)ResourceKeyArgument.key(Registries.TEMPLATE_POOL))
/*  89 */             .then(
/*  90 */               Commands.argument("target", (ArgumentType)ResourceLocationArgument.id())
/*  91 */               .then((
/*  92 */                 (RequiredArgumentBuilder)Commands.argument("max_depth", (ArgumentType)IntegerArgumentType.integer(1, 7))
/*  93 */                 .executes($$0 -> placeJigsaw((CommandSourceStack)$$0.getSource(), (Holder<StructureTemplatePool>)ResourceKeyArgument.getStructureTemplatePool($$0, "pool"), ResourceLocationArgument.getId($$0, "target"), IntegerArgumentType.getInteger($$0, "max_depth"), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()))))
/*  94 */                 .then(
/*  95 */                   Commands.argument("position", (ArgumentType)BlockPosArgument.blockPos())
/*  96 */                   .executes($$0 -> placeJigsaw((CommandSourceStack)$$0.getSource(), (Holder<StructureTemplatePool>)ResourceKeyArgument.getStructureTemplatePool($$0, "pool"), ResourceLocationArgument.getId($$0, "target"), IntegerArgumentType.getInteger($$0, "max_depth"), BlockPosArgument.getLoadedBlockPos($$0, "position")))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 102 */         .then(
/* 103 */           Commands.literal("structure")
/* 104 */           .then((
/* 105 */             (RequiredArgumentBuilder)Commands.argument("structure", (ArgumentType)ResourceKeyArgument.key(Registries.STRUCTURE))
/* 106 */             .executes($$0 -> placeStructure((CommandSourceStack)$$0.getSource(), ResourceKeyArgument.getStructure($$0, "structure"), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()))))
/* 107 */             .then(
/* 108 */               Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 109 */               .executes($$0 -> placeStructure((CommandSourceStack)$$0.getSource(), ResourceKeyArgument.getStructure($$0, "structure"), BlockPosArgument.getLoadedBlockPos($$0, "pos")))))))
/*     */ 
/*     */ 
/*     */         
/* 113 */         .then(
/* 114 */           Commands.literal("template")
/* 115 */           .then((
/* 116 */             (RequiredArgumentBuilder)Commands.argument("template", (ArgumentType)ResourceLocationArgument.id())
/* 117 */             .suggests(SUGGEST_TEMPLATES)
/* 118 */             .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()), Rotation.NONE, Mirror.NONE, 1.0F, 0)))
/* 119 */             .then((
/* 120 */               (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 121 */               .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), Rotation.NONE, Mirror.NONE, 1.0F, 0)))
/* 122 */               .then((
/* 123 */                 (RequiredArgumentBuilder)Commands.argument("rotation", (ArgumentType)TemplateRotationArgument.templateRotation())
/* 124 */                 .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), TemplateRotationArgument.getRotation($$0, "rotation"), Mirror.NONE, 1.0F, 0)))
/* 125 */                 .then((
/* 126 */                   (RequiredArgumentBuilder)Commands.argument("mirror", (ArgumentType)TemplateMirrorArgument.templateMirror())
/* 127 */                   .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), TemplateRotationArgument.getRotation($$0, "rotation"), TemplateMirrorArgument.getMirror($$0, "mirror"), 1.0F, 0)))
/* 128 */                   .then((
/* 129 */                     (RequiredArgumentBuilder)Commands.argument("integrity", (ArgumentType)FloatArgumentType.floatArg(0.0F, 1.0F))
/* 130 */                     .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), TemplateRotationArgument.getRotation($$0, "rotation"), TemplateMirrorArgument.getMirror($$0, "mirror"), FloatArgumentType.getFloat($$0, "integrity"), 0)))
/* 131 */                     .then(
/* 132 */                       Commands.argument("seed", (ArgumentType)IntegerArgumentType.integer())
/* 133 */                       .executes($$0 -> placeTemplate((CommandSourceStack)$$0.getSource(), ResourceLocationArgument.getId($$0, "template"), BlockPosArgument.getLoadedBlockPos($$0, "pos"), TemplateRotationArgument.getRotation($$0, "rotation"), TemplateMirrorArgument.getMirror($$0, "mirror"), FloatArgumentType.getFloat($$0, "integrity"), IntegerArgumentType.getInteger($$0, "seed")))))))))));
/*     */   }
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
/*     */   public static int placeFeature(CommandSourceStack $$0, Holder.Reference<ConfiguredFeature<?, ?>> $$1, BlockPos $$2) throws CommandSyntaxException {
/* 146 */     ServerLevel $$3 = $$0.getLevel();
/* 147 */     ConfiguredFeature<?, ?> $$4 = (ConfiguredFeature<?, ?>)$$1.value();
/*     */     
/* 149 */     ChunkPos $$5 = new ChunkPos($$2);
/* 150 */     checkLoaded($$3, new ChunkPos($$5.x - 1, $$5.z - 1), new ChunkPos($$5.x + 1, $$5.z + 1));
/*     */     
/* 152 */     if (!$$4.place((WorldGenLevel)$$3, $$3.getChunkSource().getGenerator(), $$3.getRandom(), $$2)) {
/* 153 */       throw ERROR_FEATURE_FAILED.create();
/*     */     }
/* 155 */     String $$6 = $$1.key().location().toString();
/* 156 */     $$0.sendSuccess(() -> Component.translatable("commands.place.feature.success", new Object[] { $$0, Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()) }), true);
/* 157 */     return 1;
/*     */   }
/*     */   
/*     */   public static int placeJigsaw(CommandSourceStack $$0, Holder<StructureTemplatePool> $$1, ResourceLocation $$2, int $$3, BlockPos $$4) throws CommandSyntaxException {
/* 161 */     ServerLevel $$5 = $$0.getLevel();
/* 162 */     if (!JigsawPlacement.generateJigsaw($$5, $$1, $$2, $$3, $$4, false)) {
/* 163 */       throw ERROR_JIGSAW_FAILED.create();
/*     */     }
/* 165 */     $$0.sendSuccess(() -> Component.translatable("commands.place.jigsaw.success", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()) }), true);
/* 166 */     return 1;
/*     */   }
/*     */   
/*     */   public static int placeStructure(CommandSourceStack $$0, Holder.Reference<Structure> $$1, BlockPos $$2) throws CommandSyntaxException {
/* 170 */     ServerLevel $$3 = $$0.getLevel();
/* 171 */     Structure $$4 = (Structure)$$1.value();
/* 172 */     ChunkGenerator $$5 = $$3.getChunkSource().getGenerator();
/*     */     
/* 174 */     StructureStart $$6 = $$4.generate($$0.registryAccess(), $$5, $$5.getBiomeSource(), $$3.getChunkSource().randomState(), $$3.getStructureManager(), $$3.getSeed(), new ChunkPos($$2), 0, (LevelHeightAccessor)$$3, $$0 -> true);
/* 175 */     if (!$$6.isValid()) {
/* 176 */       throw ERROR_STRUCTURE_FAILED.create();
/*     */     }
/* 178 */     BoundingBox $$7 = $$6.getBoundingBox();
/* 179 */     ChunkPos $$8 = new ChunkPos(SectionPos.blockToSectionCoord($$7.minX()), SectionPos.blockToSectionCoord($$7.minZ()));
/* 180 */     ChunkPos $$9 = new ChunkPos(SectionPos.blockToSectionCoord($$7.maxX()), SectionPos.blockToSectionCoord($$7.maxZ()));
/*     */     
/* 182 */     checkLoaded($$3, $$8, $$9);
/* 183 */     ChunkPos.rangeClosed($$8, $$9).forEach($$3 -> $$0.placeInChunk((WorldGenLevel)$$1, $$1.structureManager(), $$2, $$1.getRandom(), new BoundingBox($$3.getMinBlockX(), $$1.getMinBuildHeight(), $$3.getMinBlockZ(), $$3.getMaxBlockX(), $$1.getMaxBuildHeight(), $$3.getMaxBlockZ()), $$3));
/*     */ 
/*     */ 
/*     */     
/* 187 */     String $$10 = $$1.key().location().toString();
/* 188 */     $$0.sendSuccess(() -> Component.translatable("commands.place.structure.success", new Object[] { $$0, Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()) }), true);
/* 189 */     return 1;
/*     */   }
/*     */   public static int placeTemplate(CommandSourceStack $$0, ResourceLocation $$1, BlockPos $$2, Rotation $$3, Mirror $$4, float $$5, int $$6) throws CommandSyntaxException {
/*     */     Optional<StructureTemplate> $$9;
/* 193 */     ServerLevel $$7 = $$0.getLevel();
/* 194 */     StructureTemplateManager $$8 = $$7.getStructureManager();
/*     */     
/*     */     try {
/* 197 */       $$9 = $$8.get($$1);
/* 198 */     } catch (ResourceLocationException $$10) {
/* 199 */       throw ERROR_TEMPLATE_INVALID.create($$1);
/*     */     } 
/* 201 */     if ($$9.isEmpty()) {
/* 202 */       throw ERROR_TEMPLATE_INVALID.create($$1);
/*     */     }
/* 204 */     StructureTemplate $$12 = $$9.get();
/* 205 */     checkLoaded($$7, new ChunkPos($$2), new ChunkPos($$2.offset($$12.getSize())));
/*     */     
/* 207 */     StructurePlaceSettings $$13 = (new StructurePlaceSettings()).setMirror($$4).setRotation($$3);
/* 208 */     if ($$5 < 1.0F) {
/* 209 */       $$13.clearProcessors().addProcessor((StructureProcessor)new BlockRotProcessor($$5)).setRandom(StructureBlockEntity.createRandom($$6));
/*     */     }
/*     */     
/* 212 */     boolean $$14 = $$12.placeInWorld((ServerLevelAccessor)$$7, $$2, $$2, $$13, StructureBlockEntity.createRandom($$6), 2);
/* 213 */     if (!$$14) {
/* 214 */       throw ERROR_TEMPLATE_FAILED.create();
/*     */     }
/* 216 */     $$0.sendSuccess(() -> Component.translatable("commands.place.template.success", new Object[] { Component.translationArg($$0), Integer.valueOf($$1.getX()), Integer.valueOf($$1.getY()), Integer.valueOf($$1.getZ()) }), true);
/* 217 */     return 1;
/*     */   }
/*     */   
/*     */   private static void checkLoaded(ServerLevel $$0, ChunkPos $$1, ChunkPos $$2) throws CommandSyntaxException {
/* 221 */     if (ChunkPos.rangeClosed($$1, $$2).filter($$1 -> !$$0.isLoaded($$1.getWorldPosition())).findAny().isPresent())
/* 222 */       throw BlockPosArgument.ERROR_NOT_LOADED.create(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\PlaceCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */