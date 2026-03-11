/*     */ package net.minecraft.server.commands;
/*     */ import com.google.common.base.Stopwatch;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.time.Duration;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Predicate;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagArgument;
/*     */ import net.minecraft.commands.arguments.ResourceOrTagKeyArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.HolderSet;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.core.registries.Registries;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.ai.village.poi.PoiType;
/*     */ import net.minecraft.world.level.biome.Biome;
/*     */ import net.minecraft.world.level.levelgen.structure.Structure;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class LocateCommand {
/*  45 */   private static final Logger LOGGER = LogUtils.getLogger(); private static final DynamicCommandExceptionType ERROR_STRUCTURE_NOT_FOUND; private static final DynamicCommandExceptionType ERROR_STRUCTURE_INVALID;
/*     */   static {
/*  47 */     ERROR_STRUCTURE_NOT_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.locate.structure.not_found", new Object[] { $$0 }));
/*  48 */     ERROR_STRUCTURE_INVALID = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.locate.structure.invalid", new Object[] { $$0 }));
/*     */     
/*  50 */     ERROR_BIOME_NOT_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.locate.biome.not_found", new Object[] { $$0 }));
/*     */     
/*  52 */     ERROR_POI_NOT_FOUND = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.locate.poi.not_found", new Object[] { $$0 }));
/*     */   }
/*     */   private static final DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND;
/*     */   private static final DynamicCommandExceptionType ERROR_POI_NOT_FOUND;
/*     */   private static final int MAX_STRUCTURE_SEARCH_RADIUS = 100;
/*     */   private static final int MAX_BIOME_SEARCH_RADIUS = 6400;
/*     */   private static final int BIOME_SAMPLE_RESOLUTION_HORIZONTAL = 32;
/*     */   private static final int BIOME_SAMPLE_RESOLUTION_VERTICAL = 64;
/*     */   private static final int POI_SEARCH_RADIUS = 256;
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  63 */     $$0.register(
/*  64 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("locate")
/*  65 */         .requires($$0 -> $$0.hasPermission(2)))
/*  66 */         .then(
/*  67 */           Commands.literal("structure")
/*  68 */           .then(
/*  69 */             Commands.argument("structure", (ArgumentType)ResourceOrTagKeyArgument.resourceOrTagKey(Registries.STRUCTURE))
/*  70 */             .executes($$0 -> locateStructure((CommandSourceStack)$$0.getSource(), ResourceOrTagKeyArgument.getResourceOrTagKey($$0, "structure", Registries.STRUCTURE, ERROR_STRUCTURE_INVALID))))))
/*     */         
/*  72 */         .then(
/*  73 */           Commands.literal("biome")
/*  74 */           .then(
/*  75 */             Commands.argument("biome", (ArgumentType)ResourceOrTagArgument.resourceOrTag($$1, Registries.BIOME))
/*  76 */             .executes($$0 -> locateBiome((CommandSourceStack)$$0.getSource(), ResourceOrTagArgument.getResourceOrTag($$0, "biome", Registries.BIOME))))))
/*     */         
/*  78 */         .then(
/*  79 */           Commands.literal("poi")
/*  80 */           .then(
/*  81 */             Commands.argument("poi", (ArgumentType)ResourceOrTagArgument.resourceOrTag($$1, Registries.POINT_OF_INTEREST_TYPE))
/*  82 */             .executes($$0 -> locatePoi((CommandSourceStack)$$0.getSource(), ResourceOrTagArgument.getResourceOrTag($$0, "poi", Registries.POINT_OF_INTEREST_TYPE))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Optional<? extends HolderSet.ListBacked<Structure>> getHolders(ResourceOrTagKeyArgument.Result<Structure> $$0, Registry<Structure> $$1) {
/*  90 */     Objects.requireNonNull($$1); return (Optional<? extends HolderSet.ListBacked<Structure>>)$$0.unwrap().map($$1 -> $$0.getHolder($$1).map(()), $$1::getTag);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int locateStructure(CommandSourceStack $$0, ResourceOrTagKeyArgument.Result<Structure> $$1) throws CommandSyntaxException {
/*  95 */     Registry<Structure> $$2 = $$0.getLevel().registryAccess().registryOrThrow(Registries.STRUCTURE);
/*     */     
/*  97 */     HolderSet<Structure> $$3 = (HolderSet<Structure>)getHolders($$1, $$2).<Throwable>orElseThrow(() -> ERROR_STRUCTURE_INVALID.create($$0.asPrintable()));
/*     */     
/*  99 */     BlockPos $$4 = BlockPos.containing((Position)$$0.getPosition());
/* 100 */     ServerLevel $$5 = $$0.getLevel();
/* 101 */     Stopwatch $$6 = Stopwatch.createStarted(Util.TICKER);
/* 102 */     Pair<BlockPos, Holder<Structure>> $$7 = $$5.getChunkSource().getGenerator().findNearestMapStructure($$5, $$3, $$4, 100, false);
/* 103 */     $$6.stop();
/* 104 */     if ($$7 == null) {
/* 105 */       throw ERROR_STRUCTURE_NOT_FOUND.create($$1.asPrintable());
/*     */     }
/*     */     
/* 108 */     return showLocateResult($$0, $$1, $$4, $$7, "commands.locate.structure.success", false, $$6.elapsed());
/*     */   }
/*     */   
/*     */   private static int locateBiome(CommandSourceStack $$0, ResourceOrTagArgument.Result<Biome> $$1) throws CommandSyntaxException {
/* 112 */     BlockPos $$2 = BlockPos.containing((Position)$$0.getPosition());
/* 113 */     Stopwatch $$3 = Stopwatch.createStarted(Util.TICKER);
/* 114 */     Pair<BlockPos, Holder<Biome>> $$4 = $$0.getLevel().findClosestBiome3d((Predicate)$$1, $$2, 6400, 32, 64);
/* 115 */     $$3.stop();
/* 116 */     if ($$4 == null) {
/* 117 */       throw ERROR_BIOME_NOT_FOUND.create($$1.asPrintable());
/*     */     }
/* 119 */     return showLocateResult($$0, $$1, $$2, $$4, "commands.locate.biome.success", true, $$3.elapsed());
/*     */   }
/*     */   
/*     */   private static int locatePoi(CommandSourceStack $$0, ResourceOrTagArgument.Result<PoiType> $$1) throws CommandSyntaxException {
/* 123 */     BlockPos $$2 = BlockPos.containing((Position)$$0.getPosition());
/* 124 */     ServerLevel $$3 = $$0.getLevel();
/* 125 */     Stopwatch $$4 = Stopwatch.createStarted(Util.TICKER);
/* 126 */     Optional<Pair<Holder<PoiType>, BlockPos>> $$5 = $$3.getPoiManager().findClosestWithType((Predicate)$$1, $$2, 256, PoiManager.Occupancy.ANY);
/* 127 */     $$4.stop();
/*     */     
/* 129 */     if ($$5.isEmpty()) {
/* 130 */       throw ERROR_POI_NOT_FOUND.create($$1.asPrintable());
/*     */     }
/*     */     
/* 133 */     return showLocateResult($$0, $$1, $$2, ((Pair)$$5.get()).swap(), "commands.locate.poi.success", false, $$4.elapsed());
/*     */   }
/*     */   
/*     */   private static String getElementName(Pair<BlockPos, ? extends Holder<?>> $$0) {
/* 137 */     return ((Holder)$$0.getSecond()).unwrapKey().map($$0 -> $$0.location().toString()).orElse("[unregistered]");
/*     */   }
/*     */   
/*     */   public static int showLocateResult(CommandSourceStack $$0, ResourceOrTagArgument.Result<?> $$1, BlockPos $$2, Pair<BlockPos, ? extends Holder<?>> $$3, String $$4, boolean $$5, Duration $$6) {
/* 141 */     String $$7 = (String)$$1.unwrap().map($$1 -> $$0.asPrintable(), $$2 -> $$0.asPrintable() + " (" + $$0.asPrintable() + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 146 */     return showLocateResult($$0, $$2, $$3, $$4, $$5, $$7, $$6);
/*     */   }
/*     */   
/*     */   public static int showLocateResult(CommandSourceStack $$0, ResourceOrTagKeyArgument.Result<?> $$1, BlockPos $$2, Pair<BlockPos, ? extends Holder<?>> $$3, String $$4, boolean $$5, Duration $$6) {
/* 150 */     String $$7 = (String)$$1.unwrap().map($$0 -> $$0.location().toString(), $$1 -> "#" + $$1.location() + " (" + getElementName($$0) + ")");
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 155 */     return showLocateResult($$0, $$2, $$3, $$4, $$5, $$7, $$6);
/*     */   }
/*     */   
/*     */   private static int showLocateResult(CommandSourceStack $$0, BlockPos $$1, Pair<BlockPos, ? extends Holder<?>> $$2, String $$3, boolean $$4, String $$5, Duration $$6) {
/* 159 */     BlockPos $$7 = (BlockPos)$$2.getFirst();
/*     */ 
/*     */ 
/*     */     
/* 163 */     int $$8 = $$4 ? Mth.floor(Mth.sqrt((float)$$1.distSqr((Vec3i)$$7))) : Mth.floor(dist($$1.getX(), $$1.getZ(), $$7.getX(), $$7.getZ()));
/* 164 */     String $$9 = $$4 ? String.valueOf($$7.getY()) : "~";
/* 165 */     MutableComponent mutableComponent = ComponentUtils.wrapInSquareBrackets((Component)Component.translatable("chat.coordinates", new Object[] { Integer.valueOf($$7.getX()), $$9, Integer.valueOf($$7.getZ()) })).withStyle($$2 -> $$2.withColor(ChatFormatting.GREEN).withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/tp @s " + $$0.getX() + " " + $$1 + " " + $$0.getZ())).withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("chat.coordinates.tooltip"))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 171 */     $$0.sendSuccess(() -> Component.translatable($$0, new Object[] { $$1, $$2, Integer.valueOf($$3) }), false);
/* 172 */     LOGGER.info("Locating element " + $$5 + " took " + $$6.toMillis() + " ms");
/* 173 */     return $$8;
/*     */   }
/*     */   
/*     */   private static float dist(int $$0, int $$1, int $$2, int $$3) {
/* 177 */     int $$4 = $$2 - $$0;
/* 178 */     int $$5 = $$3 - $$1;
/* 179 */     return Mth.sqrt(($$4 * $$4 + $$5 * $$5));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\LocateCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */