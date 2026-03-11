/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.ResourceArgument;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.QuartPos;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.biome.BiomeResolver;
/*     */ import net.minecraft.world.level.biome.Climate;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ 
/*     */ public class FillBiomeCommand {
/*  43 */   public static final SimpleCommandExceptionType ERROR_NOT_LOADED = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.unloaded")); static {
/*  44 */     ERROR_VOLUME_TOO_LARGE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.fillbiome.toobig", new Object[] { $$0, $$1 }));
/*     */   } private static final Dynamic2CommandExceptionType ERROR_VOLUME_TOO_LARGE;
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  47 */     $$0.register(
/*  48 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fillbiome")
/*  49 */         .requires($$0 -> $$0.hasPermission(2)))
/*  50 */         .then(
/*  51 */           Commands.argument("from", (ArgumentType)BlockPosArgument.blockPos())
/*  52 */           .then(
/*  53 */             Commands.argument("to", (ArgumentType)BlockPosArgument.blockPos())
/*  54 */             .then((
/*  55 */               (RequiredArgumentBuilder)Commands.argument("biome", (ArgumentType)ResourceArgument.resource($$1, Registries.BIOME))
/*  56 */               .executes($$0 -> fill((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "from"), BlockPosArgument.getLoadedBlockPos($$0, "to"), ResourceArgument.getResource($$0, "biome", Registries.BIOME), ())))
/*  57 */               .then(Commands.literal("replace")
/*  58 */                 .then(
/*  59 */                   Commands.argument("filter", (ArgumentType)ResourceOrTagArgument.resourceOrTag($$1, Registries.BIOME))
/*  60 */                   .executes($$0 -> {
/*     */                       Objects.requireNonNull(ResourceOrTagArgument.getResourceOrTag($$0, "filter", Registries.BIOME));
/*     */                       return fill((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "from"), BlockPosArgument.getLoadedBlockPos($$0, "to"), ResourceArgument.getResource($$0, "biome", Registries.BIOME), ResourceOrTagArgument.getResourceOrTag($$0, "filter", Registries.BIOME)::test);
/*     */                     })))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int quantize(int $$0) {
/*  70 */     return QuartPos.toBlock(QuartPos.fromBlock($$0));
/*     */   }
/*     */   
/*     */   private static BlockPos quantize(BlockPos $$0) {
/*  74 */     return new BlockPos(quantize($$0.getX()), quantize($$0.getY()), quantize($$0.getZ()));
/*     */   }
/*     */   
/*     */   private static BiomeResolver makeResolver(MutableInt $$0, ChunkAccess $$1, BoundingBox $$2, Holder<Biome> $$3, Predicate<Holder<Biome>> $$4) {
/*  78 */     return ($$5, $$6, $$7, $$8) -> {
/*     */         int $$9 = QuartPos.toBlock($$5);
/*     */         int $$10 = QuartPos.toBlock($$6);
/*     */         int $$11 = QuartPos.toBlock($$7);
/*     */         Holder<Biome> $$12 = $$0.getNoiseBiome($$5, $$6, $$7);
/*     */         if ($$1.isInside($$9, $$10, $$11) && $$2.test($$12)) {
/*     */           $$3.increment();
/*     */           return $$4;
/*     */         } 
/*     */         return $$12;
/*     */       };
/*     */   }
/*     */   
/*     */   public static Either<Integer, CommandSyntaxException> fill(ServerLevel $$0, BlockPos $$1, BlockPos $$2, Holder<Biome> $$3) {
/*  92 */     return fill($$0, $$1, $$2, $$3, $$0 -> true, $$0 -> {
/*     */         
/*     */         });
/*     */   } public static Either<Integer, CommandSyntaxException> fill(ServerLevel $$0, BlockPos $$1, BlockPos $$2, Holder<Biome> $$3, Predicate<Holder<Biome>> $$4, Consumer<Supplier<Component>> $$5) {
/*  96 */     BlockPos $$6 = quantize($$1);
/*  97 */     BlockPos $$7 = quantize($$2);
/*  98 */     BoundingBox $$8 = BoundingBox.fromCorners((Vec3i)$$6, (Vec3i)$$7);
/*  99 */     int $$9 = $$8.getXSpan() * $$8.getYSpan() * $$8.getZSpan();
/* 100 */     int $$10 = $$0.getGameRules().getInt(GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
/* 101 */     if ($$9 > $$10) {
/* 102 */       return Either.right(ERROR_VOLUME_TOO_LARGE.create(Integer.valueOf($$10), Integer.valueOf($$9)));
/*     */     }
/*     */     
/* 105 */     List<ChunkAccess> $$11 = new ArrayList<>();
/* 106 */     for (int $$12 = SectionPos.blockToSectionCoord($$8.minZ()); $$12 <= SectionPos.blockToSectionCoord($$8.maxZ()); $$12++) {
/* 107 */       for (int $$13 = SectionPos.blockToSectionCoord($$8.minX()); $$13 <= SectionPos.blockToSectionCoord($$8.maxX()); $$13++) {
/* 108 */         ChunkAccess $$14 = $$0.getChunk($$13, $$12, ChunkStatus.FULL, false);
/* 109 */         if ($$14 == null) {
/* 110 */           return Either.right(ERROR_NOT_LOADED.create());
/*     */         }
/* 112 */         $$11.add($$14);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     MutableInt $$15 = new MutableInt(0);
/* 117 */     for (ChunkAccess $$16 : $$11) {
/* 118 */       $$16.fillBiomesFromNoise(makeResolver($$15, $$16, $$8, $$3, $$4), $$0.getChunkSource().randomState().sampler());
/* 119 */       $$16.setUnsaved(true);
/*     */     } 
/* 121 */     ($$0.getChunkSource()).chunkMap.resendBiomesForChunks($$11);
/*     */     
/* 123 */     $$5.accept(() -> Component.translatable("commands.fillbiome.success.count", new Object[] { $$0.getValue(), Integer.valueOf($$1.minX()), Integer.valueOf($$1.minY()), Integer.valueOf($$1.minZ()), Integer.valueOf($$1.maxX()), Integer.valueOf($$1.maxY()), Integer.valueOf($$1.maxZ()) }));
/* 124 */     return Either.left($$15.getValue());
/*     */   }
/*     */   
/*     */   private static int fill(CommandSourceStack $$0, BlockPos $$1, BlockPos $$2, Holder.Reference<Biome> $$3, Predicate<Holder<Biome>> $$4) throws CommandSyntaxException {
/* 128 */     Either<Integer, CommandSyntaxException> $$5 = fill($$0.getLevel(), $$1, $$2, (Holder<Biome>)$$3, $$4, $$1 -> $$0.sendSuccess($$1, true));
/* 129 */     Optional<CommandSyntaxException> $$6 = $$5.right();
/* 130 */     if ($$6.isPresent()) {
/* 131 */       throw (CommandSyntaxException)$$6.get();
/*     */     }
/* 133 */     return ((Integer)$$5.left().get()).intValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FillBiomeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */