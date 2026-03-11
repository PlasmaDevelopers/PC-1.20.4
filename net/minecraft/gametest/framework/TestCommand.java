/*     */ package net.minecraft.gametest.framework;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.FileUtil;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.blocks.BlockInput;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.data.CachedOutput;
/*     */ import net.minecraft.data.structures.NbtToSnbt;
/*     */ import net.minecraft.nbt.NbtIo;
/*     */ import net.minecraft.nbt.NbtUtils;
/*     */ import net.minecraft.network.chat.ClickEvent;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.HoverEvent;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.network.protocol.game.DebugPackets;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.Rotation;
/*     */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TestCommand
/*     */ {
/*  62 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   private static final int DEFAULT_CLEAR_RADIUS = 200;
/*     */   
/*     */   private static final int MAX_CLEAR_RADIUS = 1024;
/*     */   private static final int STRUCTURE_BLOCK_NEARBY_SEARCH_RADIUS = 15;
/*     */   private static final int STRUCTURE_BLOCK_FULL_SEARCH_RADIUS = 200;
/*     */   private static final int TEST_POS_Z_OFFSET_FROM_PLAYER = 3;
/*     */   private static final int SHOW_POS_DURATION_MS = 10000;
/*     */   private static final int DEFAULT_X_SIZE = 5;
/*     */   private static final int DEFAULT_Y_SIZE = 5;
/*     */   private static final int DEFAULT_Z_SIZE = 5;
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  76 */     $$0.register(
/*  77 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("test")
/*  78 */         .then((
/*  79 */           (LiteralArgumentBuilder)Commands.literal("runthis")
/*  80 */           .executes($$0 -> runNearbyTest((CommandSourceStack)$$0.getSource(), false)))
/*  81 */           .then(
/*  82 */             Commands.literal("untilFailed")
/*  83 */             .executes($$0 -> runNearbyTest((CommandSourceStack)$$0.getSource(), true)))))
/*     */ 
/*     */         
/*  86 */         .then(
/*  87 */           Commands.literal("resetthis")
/*  88 */           .executes($$0 -> resetNearbyTest((CommandSourceStack)$$0.getSource()))))
/*     */         
/*  90 */         .then(
/*  91 */           Commands.literal("runthese")
/*  92 */           .executes($$0 -> runAllNearbyTests((CommandSourceStack)$$0.getSource(), false))))
/*     */         
/*  94 */         .then((
/*  95 */           (LiteralArgumentBuilder)Commands.literal("runfailed")
/*  96 */           .executes($$0 -> runLastFailedTests((CommandSourceStack)$$0.getSource(), false, 0, 8)))
/*  97 */           .then(((RequiredArgumentBuilder)Commands.argument("onlyRequiredTests", (ArgumentType)BoolArgumentType.bool())
/*  98 */             .executes($$0 -> runLastFailedTests((CommandSourceStack)$$0.getSource(), BoolArgumentType.getBool($$0, "onlyRequiredTests"), 0, 8)))
/*  99 */             .then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", (ArgumentType)IntegerArgumentType.integer())
/* 100 */               .executes($$0 -> runLastFailedTests((CommandSourceStack)$$0.getSource(), BoolArgumentType.getBool($$0, "onlyRequiredTests"), IntegerArgumentType.getInteger($$0, "rotationSteps"), 8)))
/* 101 */               .then(Commands.argument("testsPerRow", (ArgumentType)IntegerArgumentType.integer())
/* 102 */                 .executes($$0 -> runLastFailedTests((CommandSourceStack)$$0.getSource(), BoolArgumentType.getBool($$0, "onlyRequiredTests"), IntegerArgumentType.getInteger($$0, "rotationSteps"), IntegerArgumentType.getInteger($$0, "testsPerRow"))))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 107 */         .then(
/* 108 */           Commands.literal("run")
/* 109 */           .then((
/* 110 */             (RequiredArgumentBuilder)Commands.argument("testName", TestFunctionArgument.testFunctionArgument())
/* 111 */             .executes($$0 -> runTest((CommandSourceStack)$$0.getSource(), TestFunctionArgument.getTestFunction($$0, "testName"), 0)))
/* 112 */             .then(Commands.argument("rotationSteps", (ArgumentType)IntegerArgumentType.integer())
/* 113 */               .executes($$0 -> runTest((CommandSourceStack)$$0.getSource(), TestFunctionArgument.getTestFunction($$0, "testName"), IntegerArgumentType.getInteger($$0, "rotationSteps")))))))
/*     */ 
/*     */ 
/*     */         
/* 117 */         .then((
/* 118 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("runall")
/* 119 */           .executes($$0 -> runAllTests((CommandSourceStack)$$0.getSource(), 0, 8)))
/* 120 */           .then((
/* 121 */             (RequiredArgumentBuilder)Commands.argument("testClassName", TestClassNameArgument.testClassName())
/* 122 */             .executes($$0 -> runAllTestsInClass((CommandSourceStack)$$0.getSource(), TestClassNameArgument.getTestClassName($$0, "testClassName"), 0, 8)))
/* 123 */             .then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", (ArgumentType)IntegerArgumentType.integer())
/* 124 */               .executes($$0 -> runAllTestsInClass((CommandSourceStack)$$0.getSource(), TestClassNameArgument.getTestClassName($$0, "testClassName"), IntegerArgumentType.getInteger($$0, "rotationSteps"), 8)))
/* 125 */               .then(Commands.argument("testsPerRow", (ArgumentType)IntegerArgumentType.integer())
/* 126 */                 .executes($$0 -> runAllTestsInClass((CommandSourceStack)$$0.getSource(), TestClassNameArgument.getTestClassName($$0, "testClassName"), IntegerArgumentType.getInteger($$0, "rotationSteps"), IntegerArgumentType.getInteger($$0, "testsPerRow")))))))
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 131 */           .then(((RequiredArgumentBuilder)Commands.argument("rotationSteps", (ArgumentType)IntegerArgumentType.integer())
/* 132 */             .executes($$0 -> runAllTests((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "rotationSteps"), 8)))
/* 133 */             .then(Commands.argument("testsPerRow", (ArgumentType)IntegerArgumentType.integer())
/* 134 */               .executes($$0 -> runAllTests((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "rotationSteps"), IntegerArgumentType.getInteger($$0, "testsPerRow")))))))
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 139 */         .then(
/* 140 */           Commands.literal("export")
/* 141 */           .then(
/* 142 */             Commands.argument("testName", (ArgumentType)StringArgumentType.word())
/* 143 */             .executes($$0 -> exportTestStructure((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "testName"))))))
/*     */ 
/*     */         
/* 146 */         .then(
/* 147 */           Commands.literal("exportthis")
/* 148 */           .executes($$0 -> exportNearestTestStructure((CommandSourceStack)$$0.getSource()))))
/*     */         
/* 150 */         .then(
/* 151 */           Commands.literal("exportthese")
/* 152 */           .executes($$0 -> exportAllNearbyTests((CommandSourceStack)$$0.getSource()))))
/*     */         
/* 154 */         .then(
/* 155 */           Commands.literal("import")
/* 156 */           .then(
/* 157 */             Commands.argument("testName", (ArgumentType)StringArgumentType.word())
/* 158 */             .executes($$0 -> importTestStructure((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "testName"))))))
/*     */ 
/*     */         
/* 161 */         .then((
/* 162 */           (LiteralArgumentBuilder)Commands.literal("pos")
/* 163 */           .executes($$0 -> showPos((CommandSourceStack)$$0.getSource(), "pos")))
/* 164 */           .then(
/* 165 */             Commands.argument("var", (ArgumentType)StringArgumentType.word())
/* 166 */             .executes($$0 -> showPos((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "var"))))))
/*     */ 
/*     */         
/* 169 */         .then(
/* 170 */           Commands.literal("create")
/* 171 */           .then((
/* 172 */             (RequiredArgumentBuilder)Commands.argument("testName", (ArgumentType)StringArgumentType.word())
/* 173 */             .executes($$0 -> createNewStructure((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "testName"), 5, 5, 5)))
/* 174 */             .then((
/* 175 */               (RequiredArgumentBuilder)Commands.argument("width", (ArgumentType)IntegerArgumentType.integer())
/* 176 */               .executes($$0 -> createNewStructure((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "testName"), IntegerArgumentType.getInteger($$0, "width"), IntegerArgumentType.getInteger($$0, "width"), IntegerArgumentType.getInteger($$0, "width"))))
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 181 */               .then(
/* 182 */                 Commands.argument("height", (ArgumentType)IntegerArgumentType.integer())
/* 183 */                 .then(
/* 184 */                   Commands.argument("depth", (ArgumentType)IntegerArgumentType.integer())
/* 185 */                   .executes($$0 -> createNewStructure((CommandSourceStack)$$0.getSource(), StringArgumentType.getString($$0, "testName"), IntegerArgumentType.getInteger($$0, "width"), IntegerArgumentType.getInteger($$0, "height"), IntegerArgumentType.getInteger($$0, "depth")))))))))
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
/* 196 */         .then((
/* 197 */           (LiteralArgumentBuilder)Commands.literal("clearall")
/* 198 */           .executes($$0 -> clearAllTests((CommandSourceStack)$$0.getSource(), 200)))
/* 199 */           .then(
/* 200 */             Commands.argument("radius", (ArgumentType)IntegerArgumentType.integer())
/* 201 */             .executes($$0 -> clearAllTests((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "radius"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int createNewStructure(CommandSourceStack $$0, String $$1, int $$2, int $$3, int $$4) {
/* 208 */     if ($$2 > 48 || $$3 > 48 || $$4 > 48) {
/* 209 */       throw new IllegalArgumentException("The structure must be less than 48 blocks big in each axis");
/*     */     }
/*     */     
/* 212 */     ServerLevel $$5 = $$0.getLevel();
/* 213 */     BlockPos $$6 = createTestPositionAround($$0).below();
/*     */     
/* 215 */     StructureUtils.createNewEmptyStructureBlock($$1.toLowerCase(), $$6, new Vec3i($$2, $$3, $$4), Rotation.NONE, $$5);
/*     */     
/* 217 */     for (int $$7 = 0; $$7 < $$2; $$7++) {
/* 218 */       for (int $$8 = 0; $$8 < $$4; $$8++) {
/* 219 */         BlockPos $$9 = new BlockPos($$6.getX() + $$7, $$6.getY() + 1, $$6.getZ() + $$8);
/* 220 */         Block $$10 = Blocks.POLISHED_ANDESITE;
/* 221 */         BlockInput $$11 = new BlockInput($$10.defaultBlockState(), Collections.emptySet(), null);
/* 222 */         $$11.place($$5, $$9, 2);
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     StructureUtils.addCommandBlockAndButtonToStartTest($$6, new BlockPos(1, 0, -1), Rotation.NONE, $$5);
/*     */     
/* 228 */     return 0;
/*     */   }
/*     */   
/*     */   private static int showPos(CommandSourceStack $$0, String $$1) throws CommandSyntaxException {
/* 232 */     BlockHitResult $$2 = (BlockHitResult)$$0.getPlayerOrException().pick(10.0D, 1.0F, false);
/*     */     
/* 234 */     BlockPos $$3 = $$2.getBlockPos();
/* 235 */     ServerLevel $$4 = $$0.getLevel();
/*     */     
/* 237 */     Optional<BlockPos> $$5 = StructureUtils.findStructureBlockContainingPos($$3, 15, $$4);
/* 238 */     if ($$5.isEmpty())
/*     */     {
/* 240 */       $$5 = StructureUtils.findStructureBlockContainingPos($$3, 200, $$4);
/*     */     }
/*     */     
/* 243 */     if ($$5.isEmpty()) {
/* 244 */       $$0.sendFailure((Component)Component.literal("Can't find a structure block that contains the targeted pos " + $$3));
/* 245 */       return 0;
/*     */     } 
/* 247 */     StructureBlockEntity $$6 = (StructureBlockEntity)$$4.getBlockEntity($$5.get());
/*     */     
/* 249 */     BlockPos $$7 = $$3.subtract((Vec3i)$$5.get());
/* 250 */     String $$8 = "" + $$7.getX() + ", " + $$7.getX() + ", " + $$7.getY();
/* 251 */     String $$9 = $$6.getMetaData();
/*     */ 
/*     */     
/* 254 */     MutableComponent mutableComponent = Component.literal($$8).setStyle(Style.EMPTY
/* 255 */         .withBold(Boolean.valueOf(true))
/* 256 */         .withColor(ChatFormatting.GREEN)
/* 257 */         .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.literal("Click to copy to clipboard")))
/* 258 */         .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, "final BlockPos " + $$1 + " = new BlockPos(" + $$8 + ");")));
/*     */     
/* 260 */     $$0.sendSuccess(() -> Component.literal("Position relative to " + $$0 + ": ").append($$1), false);
/*     */     
/* 262 */     DebugPackets.sendGameTestAddMarker($$4, new BlockPos((Vec3i)$$3), $$8, -2147418368, 10000);
/*     */     
/* 264 */     return 1;
/*     */   }
/*     */   
/*     */   private static int runNearbyTest(CommandSourceStack $$0, boolean $$1) {
/* 268 */     BlockPos $$2 = BlockPos.containing((Position)$$0.getPosition());
/* 269 */     ServerLevel $$3 = $$0.getLevel();
/* 270 */     BlockPos $$4 = StructureUtils.findNearestStructureBlock($$2, 15, $$3);
/* 271 */     if ($$4 == null) {
/* 272 */       say($$3, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
/* 273 */       return 0;
/*     */     } 
/*     */     
/* 276 */     GameTestRunner.clearMarkers($$3);
/* 277 */     runTest($$3, $$4, null, $$1);
/* 278 */     return 1;
/*     */   }
/*     */   
/*     */   private static int resetNearbyTest(CommandSourceStack $$0) {
/* 282 */     BlockPos $$1 = BlockPos.containing((Position)$$0.getPosition());
/* 283 */     ServerLevel $$2 = $$0.getLevel();
/* 284 */     BlockPos $$3 = StructureUtils.findNearestStructureBlock($$1, 15, $$2);
/* 285 */     if ($$3 == null) {
/* 286 */       say($$2, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
/* 287 */       return 0;
/*     */     } 
/*     */     
/* 290 */     StructureBlockEntity $$4 = (StructureBlockEntity)$$2.getBlockEntity($$3);
/*     */ 
/*     */     
/* 293 */     $$4.placeStructure($$2);
/*     */     
/* 295 */     String $$5 = $$4.getMetaData();
/* 296 */     TestFunction $$6 = GameTestRegistry.getTestFunction($$5);
/* 297 */     say($$2, "Reset succeded for: " + $$6, ChatFormatting.GREEN);
/* 298 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static int runAllNearbyTests(CommandSourceStack $$0, boolean $$1) {
/* 304 */     BlockPos $$2 = BlockPos.containing((Position)$$0.getPosition());
/* 305 */     ServerLevel $$3 = $$0.getLevel();
/* 306 */     Collection<BlockPos> $$4 = StructureUtils.findStructureBlocks($$2, 200, $$3);
/*     */     
/* 308 */     if ($$4.isEmpty()) {
/* 309 */       say($$3, "Couldn't find any structure blocks within 200 block radius", ChatFormatting.RED);
/* 310 */       return 1;
/*     */     } 
/*     */     
/* 313 */     GameTestRunner.clearMarkers($$3);
/*     */     
/* 315 */     say($$0, "Running " + $$4.size() + " tests...");
/*     */     
/* 317 */     MultipleTestTracker $$5 = new MultipleTestTracker();
/* 318 */     $$4.forEach($$3 -> runTest($$0, $$3, $$1, $$2));
/*     */     
/* 320 */     return 1;
/*     */   }
/*     */   
/*     */   private static void runTest(ServerLevel $$0, BlockPos $$1, @Nullable MultipleTestTracker $$2, boolean $$3) {
/* 324 */     StructureBlockEntity $$4 = (StructureBlockEntity)$$0.getBlockEntity($$1);
/* 325 */     String $$5 = $$4.getMetaData();
/*     */     
/* 327 */     Optional<TestFunction> $$6 = GameTestRegistry.findTestFunction($$5);
/* 328 */     if ($$6.isEmpty()) {
/* 329 */       say($$0, "Test function for test " + $$5 + " could not be found", ChatFormatting.RED);
/*     */       return;
/*     */     } 
/* 332 */     TestFunction $$7 = $$6.get();
/* 333 */     GameTestInfo $$8 = new GameTestInfo($$7, $$4.getRotation(), $$0);
/* 334 */     $$8.setRerunUntilFailed($$3);
/* 335 */     if ($$2 != null) {
/* 336 */       $$2.addTestToTrack($$8);
/* 337 */       $$8.addListener(new TestSummaryDisplayer($$0, $$2));
/*     */     } 
/* 339 */     if (!verifyStructureExists($$0, $$8)) {
/*     */       return;
/*     */     }
/* 342 */     runTestPreparation($$7, $$0);
/* 343 */     BoundingBox $$9 = StructureUtils.getStructureBoundingBox($$4);
/* 344 */     BlockPos $$10 = new BlockPos($$9.minX(), $$9.minY(), $$9.minZ());
/* 345 */     GameTestRunner.runTest($$8, $$10, GameTestTicker.SINGLETON);
/*     */   }
/*     */   
/*     */   private static boolean verifyStructureExists(ServerLevel $$0, GameTestInfo $$1) {
/* 349 */     if ($$0.getStructureManager().get(new ResourceLocation($$1.getStructureName())).isEmpty()) {
/* 350 */       say($$0, "Test structure " + $$1.getStructureName() + " could not be found", ChatFormatting.RED);
/* 351 */       return false;
/*     */     } 
/* 353 */     return true;
/*     */   }
/*     */   
/*     */   static void showTestSummaryIfAllDone(ServerLevel $$0, MultipleTestTracker $$1) {
/* 357 */     if ($$1.isDone()) {
/* 358 */       say($$0, "GameTest done! " + $$1.getTotalCount() + " tests were run", ChatFormatting.WHITE);
/* 359 */       if ($$1.hasFailedRequired()) {
/* 360 */         say($$0, "" + $$1.getFailedRequiredCount() + " required tests failed :(", ChatFormatting.RED);
/*     */       } else {
/* 362 */         say($$0, "All required tests passed :)", ChatFormatting.GREEN);
/*     */       } 
/* 364 */       if ($$1.hasFailedOptional()) {
/* 365 */         say($$0, "" + $$1.getFailedOptionalCount() + " optional tests failed", ChatFormatting.GRAY);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int clearAllTests(CommandSourceStack $$0, int $$1) {
/* 371 */     ServerLevel $$2 = $$0.getLevel();
/* 372 */     GameTestRunner.clearMarkers($$2);
/* 373 */     BlockPos $$3 = BlockPos.containing(($$0.getPosition()).x, $$0.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, BlockPos.containing((Position)$$0.getPosition())).getY(), ($$0.getPosition()).z);
/* 374 */     GameTestRunner.clearAllTests($$2, $$3, GameTestTicker.SINGLETON, Mth.clamp($$1, 0, 1024));
/* 375 */     return 1;
/*     */   }
/*     */   
/*     */   private static int runTest(CommandSourceStack $$0, TestFunction $$1, int $$2) {
/* 379 */     ServerLevel $$3 = $$0.getLevel();
/* 380 */     BlockPos $$4 = createTestPositionAround($$0);
/* 381 */     GameTestRunner.clearMarkers($$3);
/* 382 */     runTestPreparation($$1, $$3);
/* 383 */     Rotation $$5 = StructureUtils.getRotationForRotationSteps($$2);
/* 384 */     GameTestInfo $$6 = new GameTestInfo($$1, $$5, $$3);
/* 385 */     if (!verifyStructureExists($$3, $$6)) {
/* 386 */       return 0;
/*     */     }
/* 388 */     GameTestRunner.runTest($$6, $$4, GameTestTicker.SINGLETON);
/* 389 */     return 1;
/*     */   }
/*     */   
/*     */   private static BlockPos createTestPositionAround(CommandSourceStack $$0) {
/* 393 */     BlockPos $$1 = BlockPos.containing((Position)$$0.getPosition());
/* 394 */     int $$2 = $$0.getLevel().getHeightmapPos(Heightmap.Types.WORLD_SURFACE, $$1).getY();
/* 395 */     return new BlockPos($$1.getX(), $$2 + 1, $$1.getZ() + 3);
/*     */   }
/*     */   
/*     */   private static void runTestPreparation(TestFunction $$0, ServerLevel $$1) {
/* 399 */     Consumer<ServerLevel> $$2 = GameTestRegistry.getBeforeBatchFunction($$0.getBatchName());
/* 400 */     if ($$2 != null) {
/* 401 */       $$2.accept($$1);
/*     */     }
/*     */   }
/*     */   
/*     */   private static int runAllTests(CommandSourceStack $$0, int $$1, int $$2) {
/* 406 */     GameTestRunner.clearMarkers($$0.getLevel());
/* 407 */     Collection<TestFunction> $$3 = GameTestRegistry.getAllTestFunctions();
/* 408 */     say($$0, "Running all " + $$3.size() + " tests...");
/* 409 */     GameTestRegistry.forgetFailedTests();
/* 410 */     runTests($$0, $$3, $$1, $$2);
/* 411 */     return 1;
/*     */   }
/*     */   
/*     */   private static int runAllTestsInClass(CommandSourceStack $$0, String $$1, int $$2, int $$3) {
/* 415 */     Collection<TestFunction> $$4 = GameTestRegistry.getTestFunctionsForClassName($$1);
/* 416 */     GameTestRunner.clearMarkers($$0.getLevel());
/* 417 */     say($$0, "Running " + $$4.size() + " tests from " + $$1 + "...");
/* 418 */     GameTestRegistry.forgetFailedTests();
/* 419 */     runTests($$0, $$4, $$2, $$3);
/* 420 */     return 1;
/*     */   }
/*     */   
/*     */   private static int runLastFailedTests(CommandSourceStack $$0, boolean $$1, int $$2, int $$3) {
/*     */     Collection<TestFunction> $$5;
/* 425 */     if ($$1) {
/* 426 */       Collection<TestFunction> $$4 = (Collection<TestFunction>)GameTestRegistry.getLastFailedTests().stream().filter(TestFunction::isRequired).collect(Collectors.toList());
/*     */     } else {
/* 428 */       $$5 = GameTestRegistry.getLastFailedTests();
/*     */     } 
/* 430 */     if ($$5.isEmpty()) {
/* 431 */       say($$0, "No failed tests to rerun");
/* 432 */       return 0;
/*     */     } 
/* 434 */     GameTestRunner.clearMarkers($$0.getLevel());
/* 435 */     say($$0, "Rerunning " + $$5.size() + " failed tests (" + ($$1 ? "only required tests" : "including optional tests") + ")");
/* 436 */     runTests($$0, $$5, $$2, $$3);
/* 437 */     return 1;
/*     */   }
/*     */   
/*     */   private static void runTests(CommandSourceStack $$0, Collection<TestFunction> $$1, int $$2, int $$3) {
/* 441 */     BlockPos $$4 = createTestPositionAround($$0);
/* 442 */     ServerLevel $$5 = $$0.getLevel();
/* 443 */     Rotation $$6 = StructureUtils.getRotationForRotationSteps($$2);
/* 444 */     Collection<GameTestInfo> $$7 = GameTestRunner.runTests($$1, $$4, $$6, $$5, GameTestTicker.SINGLETON, $$3);
/* 445 */     MultipleTestTracker $$8 = new MultipleTestTracker($$7);
/* 446 */     $$8.addListener(new TestSummaryDisplayer($$5, $$8));
/* 447 */     $$8.addFailureListener($$0 -> GameTestRegistry.rememberFailedTest($$0.getTestFunction()));
/*     */   }
/*     */   
/*     */   private static void say(CommandSourceStack $$0, String $$1) {
/* 451 */     $$0.sendSuccess(() -> Component.literal($$0), false);
/*     */   }
/*     */   
/*     */   private static int exportNearestTestStructure(CommandSourceStack $$0) {
/* 455 */     BlockPos $$1 = BlockPos.containing((Position)$$0.getPosition());
/* 456 */     ServerLevel $$2 = $$0.getLevel();
/* 457 */     BlockPos $$3 = StructureUtils.findNearestStructureBlock($$1, 15, $$2);
/* 458 */     if ($$3 == null) {
/* 459 */       say($$2, "Couldn't find any structure block within 15 radius", ChatFormatting.RED);
/* 460 */       return 0;
/*     */     } 
/* 462 */     StructureBlockEntity $$4 = (StructureBlockEntity)$$2.getBlockEntity($$3);
/* 463 */     return saveAndExportTestStructure($$0, $$4);
/*     */   }
/*     */   
/*     */   private static int exportAllNearbyTests(CommandSourceStack $$0) {
/* 467 */     BlockPos $$1 = BlockPos.containing((Position)$$0.getPosition());
/* 468 */     ServerLevel $$2 = $$0.getLevel();
/* 469 */     Collection<BlockPos> $$3 = StructureUtils.findStructureBlocks($$1, 200, $$2);
/*     */     
/* 471 */     if ($$3.isEmpty()) {
/* 472 */       say($$2, "Couldn't find any structure blocks within 200 block radius", ChatFormatting.RED);
/* 473 */       return 1;
/*     */     } 
/*     */     
/* 476 */     boolean $$4 = true;
/*     */     
/* 478 */     for (BlockPos $$5 : $$3) {
/* 479 */       StructureBlockEntity $$6 = (StructureBlockEntity)$$2.getBlockEntity($$5);
/* 480 */       if (saveAndExportTestStructure($$0, $$6) != 0) {
/* 481 */         $$4 = false;
/*     */       }
/*     */     } 
/*     */     
/* 485 */     return $$4 ? 0 : 1;
/*     */   }
/*     */   
/*     */   private static int saveAndExportTestStructure(CommandSourceStack $$0, StructureBlockEntity $$1) {
/* 489 */     String $$2 = $$1.getStructureName();
/* 490 */     if (!$$1.saveStructure(true)) {
/* 491 */       say($$0, "Failed to save structure " + $$2);
/*     */     }
/* 493 */     return exportTestStructure($$0, $$2);
/*     */   }
/*     */   
/*     */   private static int exportTestStructure(CommandSourceStack $$0, String $$1) {
/* 497 */     Path $$2 = Paths.get(StructureUtils.testStructuresDir, new String[0]);
/*     */     
/* 499 */     ResourceLocation $$3 = new ResourceLocation($$1);
/* 500 */     Path $$4 = $$0.getLevel().getStructureManager().getPathToGeneratedStructure($$3, ".nbt");
/* 501 */     Path $$5 = NbtToSnbt.convertStructure(CachedOutput.NO_CACHE, $$4, $$3.getPath(), $$2);
/* 502 */     if ($$5 == null) {
/* 503 */       say($$0, "Failed to export " + $$4);
/* 504 */       return 1;
/*     */     } 
/*     */     
/*     */     try {
/* 508 */       FileUtil.createDirectoriesSafe($$5.getParent());
/* 509 */     } catch (IOException $$6) {
/* 510 */       say($$0, "Could not create folder " + $$5.getParent());
/* 511 */       LOGGER.error("Could not create export folder", $$6);
/* 512 */       return 1;
/*     */     } 
/*     */     
/* 515 */     say($$0, "Exported " + $$1 + " to " + $$5.toAbsolutePath());
/* 516 */     return 0;
/*     */   }
/*     */   
/*     */   private static int importTestStructure(CommandSourceStack $$0, String $$1) {
/* 520 */     Path $$2 = Paths.get(StructureUtils.testStructuresDir, new String[] { $$1 + ".snbt" });
/*     */     
/* 522 */     ResourceLocation $$3 = new ResourceLocation($$1);
/* 523 */     Path $$4 = $$0.getLevel().getStructureManager().getPathToGeneratedStructure($$3, ".nbt");
/*     */     
/*     */     try {
/* 526 */       BufferedReader $$5 = Files.newBufferedReader($$2);
/* 527 */       String $$6 = IOUtils.toString($$5);
/* 528 */       Files.createDirectories($$4.getParent(), (FileAttribute<?>[])new FileAttribute[0]);
/* 529 */       OutputStream $$7 = Files.newOutputStream($$4, new java.nio.file.OpenOption[0]); 
/* 530 */       try { NbtIo.writeCompressed(NbtUtils.snbtToStructure($$6), $$7);
/* 531 */         if ($$7 != null) $$7.close();  } catch (Throwable throwable) { if ($$7 != null)
/* 532 */           try { $$7.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  say($$0, "Imported to " + $$4.toAbsolutePath());
/* 533 */       return 0;
/* 534 */     } catch (IOException|CommandSyntaxException $$8) {
/* 535 */       LOGGER.error("Failed to load structure {}", $$1, $$8);
/* 536 */       return 1;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void say(ServerLevel $$0, String $$1, ChatFormatting $$2) {
/* 541 */     $$0.getPlayers($$0 -> true).forEach($$2 -> $$2.sendSystemMessage((Component)Component.literal($$0).withStyle($$1)));
/*     */   }
/*     */   
/*     */   private static class TestSummaryDisplayer
/*     */     implements GameTestListener
/*     */   {
/*     */     private final ServerLevel level;
/*     */     private final MultipleTestTracker tracker;
/*     */     
/*     */     public TestSummaryDisplayer(ServerLevel $$0, MultipleTestTracker $$1) {
/* 551 */       this.level = $$0;
/* 552 */       this.tracker = $$1;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void testStructureLoaded(GameTestInfo $$0) {}
/*     */ 
/*     */     
/*     */     public void testPassed(GameTestInfo $$0) {
/* 561 */       TestCommand.showTestSummaryIfAllDone(this.level, this.tracker);
/*     */     }
/*     */ 
/*     */     
/*     */     public void testFailed(GameTestInfo $$0) {
/* 566 */       TestCommand.showTestSummaryIfAllDone(this.level, this.tracker);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\gametest\framework\TestCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */