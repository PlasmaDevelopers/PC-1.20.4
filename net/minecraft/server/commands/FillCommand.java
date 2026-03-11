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
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.blocks.BlockInput;
/*     */ import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
/*     */ import net.minecraft.commands.arguments.blocks.BlockStateArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ 
/*     */ public class FillCommand {
/*     */   private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE;
/*     */   
/*     */   static {
/*  38 */     ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.fill.toobig", new Object[] { $$0, $$1 }));
/*  39 */   } static final BlockInput HOLLOW_CORE = new BlockInput(Blocks.AIR.defaultBlockState(), Collections.emptySet(), null);
/*  40 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.fill.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  43 */     $$0.register(
/*  44 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("fill")
/*  45 */         .requires($$0 -> $$0.hasPermission(2)))
/*  46 */         .then(
/*  47 */           Commands.argument("from", (ArgumentType)BlockPosArgument.blockPos())
/*  48 */           .then(
/*  49 */             Commands.argument("to", (ArgumentType)BlockPosArgument.blockPos())
/*  50 */             .then((
/*  51 */               (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("block", (ArgumentType)BlockStateArgument.block($$1))
/*  52 */               .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, null)))
/*  53 */               .then((
/*  54 */                 (LiteralArgumentBuilder)Commands.literal("replace")
/*  55 */                 .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, null)))
/*  56 */                 .then(
/*  57 */                   Commands.argument("filter", (ArgumentType)BlockPredicateArgument.blockPredicate($$1))
/*  58 */                   .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, BlockPredicateArgument.getBlockPredicate($$0, "filter"))))))
/*     */ 
/*     */               
/*  61 */               .then(
/*  62 */                 Commands.literal("keep")
/*  63 */                 .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, ()))))
/*     */               
/*  65 */               .then(
/*  66 */                 Commands.literal("outline")
/*  67 */                 .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.OUTLINE, null))))
/*     */               
/*  69 */               .then(
/*  70 */                 Commands.literal("hollow")
/*  71 */                 .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.HOLLOW, null))))
/*     */               
/*  73 */               .then(
/*  74 */                 Commands.literal("destroy")
/*  75 */                 .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BoundingBox.fromCorners((Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "from"), (Vec3i)BlockPosArgument.getLoadedBlockPos($$0, "to")), BlockStateArgument.getBlock($$0, "block"), Mode.DESTROY, null)))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int fillBlocks(CommandSourceStack $$0, BoundingBox $$1, BlockInput $$2, Mode $$3, @Nullable Predicate<BlockInWorld> $$4) throws CommandSyntaxException {
/*  84 */     int $$5 = $$1.getXSpan() * $$1.getYSpan() * $$1.getZSpan();
/*  85 */     int $$6 = $$0.getLevel().getGameRules().getInt(GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
/*  86 */     if ($$5 > $$6) {
/*  87 */       throw ERROR_AREA_TOO_LARGE.create(Integer.valueOf($$6), Integer.valueOf($$5));
/*     */     }
/*     */     
/*  90 */     List<BlockPos> $$7 = Lists.newArrayList();
/*  91 */     ServerLevel $$8 = $$0.getLevel();
/*  92 */     int $$9 = 0;
/*     */     
/*  94 */     for (BlockPos $$10 : BlockPos.betweenClosed($$1.minX(), $$1.minY(), $$1.minZ(), $$1.maxX(), $$1.maxY(), $$1.maxZ())) {
/*  95 */       if ($$4 != null && !$$4.test(new BlockInWorld((LevelReader)$$8, $$10, true))) {
/*     */         continue;
/*     */       }
/*  98 */       BlockInput $$11 = $$3.filter.filter($$1, $$10, $$2, $$8);
/*  99 */       if ($$11 == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 103 */       BlockEntity $$12 = $$8.getBlockEntity($$10);
/* 104 */       Clearable.tryClear($$12);
/*     */       
/* 106 */       if (!$$11.place($$8, $$10, 2)) {
/*     */         continue;
/*     */       }
/*     */       
/* 110 */       $$7.add($$10.immutable());
/* 111 */       $$9++;
/*     */     } 
/*     */     
/* 114 */     for (BlockPos $$13 : $$7) {
/* 115 */       Block $$14 = $$8.getBlockState($$13).getBlock();
/* 116 */       $$8.blockUpdated($$13, $$14);
/*     */     } 
/*     */     
/* 119 */     if ($$9 == 0) {
/* 120 */       throw ERROR_FAILED.create();
/*     */     }
/*     */     
/* 123 */     int $$15 = $$9;
/* 124 */     $$0.sendSuccess(() -> Component.translatable("commands.fill.success", new Object[] { Integer.valueOf($$0) }), true);
/*     */     
/* 126 */     return $$9;
/*     */   }
/*     */   private enum Mode { REPLACE, OUTLINE, HOLLOW, DESTROY;
/*     */     static {
/* 130 */       REPLACE = new Mode("REPLACE", 0, ($$0, $$1, $$2, $$3) -> $$2);
/* 131 */       OUTLINE = new Mode("OUTLINE", 1, ($$0, $$1, $$2, $$3) -> 
/* 132 */           ($$1.getX() == $$0.minX() || $$1.getX() == $$0.maxX() || $$1.getY() == $$0.minY() || $$1.getY() == $$0.maxY() || $$1.getZ() == $$0.minZ() || $$1.getZ() == $$0.maxZ()) ? $$2 : null);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       HOLLOW = new Mode("HOLLOW", 2, ($$0, $$1, $$2, $$3) -> 
/* 139 */           ($$1.getX() == $$0.minX() || $$1.getX() == $$0.maxX() || $$1.getY() == $$0.minY() || $$1.getY() == $$0.maxY() || $$1.getZ() == $$0.minZ() || $$1.getZ() == $$0.maxZ()) ? $$2 : FillCommand.HOLLOW_CORE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       DESTROY = new Mode("DESTROY", 3, ($$0, $$1, $$2, $$3) -> {
/*     */             $$3.destroyBlock($$1, true);
/*     */             return $$2;
/*     */           });
/*     */     }
/*     */     public final SetBlockCommand.Filter filter;
/*     */     
/*     */     Mode(SetBlockCommand.Filter $$0) {
/* 153 */       this.filter = $$0;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FillCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */