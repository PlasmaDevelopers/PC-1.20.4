/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ import java.util.function.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.DimensionArgument;
/*     */ import net.minecraft.commands.arguments.blocks.BlockPredicateArgument;
/*     */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Vec3i;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.Clearable;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.GameRules;
/*     */ import net.minecraft.world.level.LevelReader;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*     */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*     */ 
/*     */ 
/*     */ public class CloneCommands
/*     */ {
/*  42 */   private static final SimpleCommandExceptionType ERROR_OVERLAP = new SimpleCommandExceptionType((Message)Component.translatable("commands.clone.overlap")); private static final Dynamic2CommandExceptionType ERROR_AREA_TOO_LARGE; static {
/*  43 */     ERROR_AREA_TOO_LARGE = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.clone.toobig", new Object[] { $$0, $$1 }));
/*  44 */   } private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.clone.failed")); public static final Predicate<BlockInWorld> FILTER_AIR; static {
/*  45 */     FILTER_AIR = ($$0 -> !$$0.getState().isAir());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/*  53 */     $$0.register(
/*  54 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("clone")
/*  55 */         .requires($$0 -> $$0.hasPermission(2)))
/*  56 */         .then(
/*  57 */           beginEndDestinationAndModeSuffix($$1, $$0 -> ((CommandSourceStack)$$0.getSource()).getLevel())))
/*     */         
/*  59 */         .then(
/*  60 */           Commands.literal("from")
/*  61 */           .then(
/*  62 */             Commands.argument("sourceDimension", (ArgumentType)DimensionArgument.dimension())
/*  63 */             .then(
/*  64 */               beginEndDestinationAndModeSuffix($$1, $$0 -> DimensionArgument.getDimension($$0, "sourceDimension"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> beginEndDestinationAndModeSuffix(CommandBuildContext $$0, CommandFunction<CommandContext<CommandSourceStack>, ServerLevel> $$1) {
/*  72 */     return Commands.argument("begin", (ArgumentType)BlockPosArgument.blockPos())
/*  73 */       .then((
/*  74 */         (RequiredArgumentBuilder)Commands.argument("end", (ArgumentType)BlockPosArgument.blockPos())
/*  75 */         .then(
/*  76 */           destinationAndModeSuffix($$0, $$1, $$0 -> ((CommandSourceStack)$$0.getSource()).getLevel())))
/*     */         
/*  78 */         .then(
/*  79 */           Commands.literal("to")
/*  80 */           .then(
/*  81 */             Commands.argument("targetDimension", (ArgumentType)DimensionArgument.dimension())
/*  82 */             .then(
/*  83 */               destinationAndModeSuffix($$0, $$1, $$0 -> DimensionArgument.getDimension($$0, "targetDimension"))))));
/*     */   }
/*     */   @FunctionalInterface
/*     */   static interface CommandFunction<T, R> {
/*     */     R apply(T param1T) throws CommandSyntaxException; }
/*     */   private static final class DimensionAndPosition extends Record { private final ServerLevel dimension; private final BlockPos position;
/*     */     
/*  90 */     DimensionAndPosition(ServerLevel $$0, BlockPos $$1) { this.dimension = $$0; this.position = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #90	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  90 */       //   0	7	0	this	Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition; } public ServerLevel dimension() { return this.dimension; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #90	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*  90 */       //   0	7	0	this	Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition; } public BlockPos position() { return this.position; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #90	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/server/commands/CloneCommands$DimensionAndPosition;
/*     */       //   0	8	1	$$0	Ljava/lang/Object; } }
/*     */    private static DimensionAndPosition getLoadedDimensionAndPosition(CommandContext<CommandSourceStack> $$0, ServerLevel $$1, String $$2) throws CommandSyntaxException {
/*  92 */     BlockPos $$3 = BlockPosArgument.getLoadedBlockPos($$0, $$1, $$2);
/*  93 */     return new DimensionAndPosition($$1, $$3);
/*     */   }
/*     */   
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> destinationAndModeSuffix(CommandBuildContext $$0, CommandFunction<CommandContext<CommandSourceStack>, ServerLevel> $$1, CommandFunction<CommandContext<CommandSourceStack>, ServerLevel> $$2) {
/*  97 */     CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$3 = $$1 -> getLoadedDimensionAndPosition($$1, $$0.apply($$1), "begin");
/*  98 */     CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$4 = $$1 -> getLoadedDimensionAndPosition($$1, $$0.apply($$1), "end");
/*  99 */     CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$5 = $$1 -> getLoadedDimensionAndPosition($$1, $$0.apply($$1), "destination");
/*     */     
/* 101 */     return ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("destination", (ArgumentType)BlockPosArgument.blockPos())
/* 102 */       .executes($$3 -> clone((CommandSourceStack)$$3.getSource(), $$0.apply($$3), $$1.apply($$3), $$2.apply($$3), (), Mode.NORMAL)))
/* 103 */       .then(
/* 104 */         wrapWithCloneMode($$3, $$4, $$5, $$0 -> (), 
/* 105 */           Commands.literal("replace")
/* 106 */           .executes($$3 -> clone((CommandSourceStack)$$3.getSource(), $$0.apply($$3), $$1.apply($$3), $$2.apply($$3), (), Mode.NORMAL)))))
/*     */ 
/*     */       
/* 109 */       .then(
/* 110 */         wrapWithCloneMode($$3, $$4, $$5, $$0 -> FILTER_AIR, 
/* 111 */           Commands.literal("masked")
/* 112 */           .executes($$3 -> clone((CommandSourceStack)$$3.getSource(), $$0.apply($$3), $$1.apply($$3), $$2.apply($$3), FILTER_AIR, Mode.NORMAL)))))
/*     */ 
/*     */       
/* 115 */       .then(
/* 116 */         Commands.literal("filtered")
/* 117 */         .then(
/* 118 */           wrapWithCloneMode($$3, $$4, $$5, $$0 -> BlockPredicateArgument.getBlockPredicate($$0, "filter"), 
/* 119 */             Commands.argument("filter", (ArgumentType)BlockPredicateArgument.blockPredicate($$0))
/* 120 */             .executes($$3 -> clone((CommandSourceStack)$$3.getSource(), $$0.apply($$3), $$1.apply($$3), $$2.apply($$3), BlockPredicateArgument.getBlockPredicate($$3, "filter"), Mode.NORMAL)))));
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
/*     */   private static ArgumentBuilder<CommandSourceStack, ?> wrapWithCloneMode(CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$0, CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$1, CommandFunction<CommandContext<CommandSourceStack>, DimensionAndPosition> $$2, CommandFunction<CommandContext<CommandSourceStack>, Predicate<BlockInWorld>> $$3, ArgumentBuilder<CommandSourceStack, ?> $$4) {
/* 133 */     return $$4
/* 134 */       .then(
/* 135 */         Commands.literal("force")
/* 136 */         .executes($$4 -> clone((CommandSourceStack)$$4.getSource(), $$0.apply($$4), $$1.apply($$4), $$2.apply($$4), $$3.apply($$4), Mode.FORCE)))
/*     */       
/* 138 */       .then(
/* 139 */         Commands.literal("move")
/* 140 */         .executes($$4 -> clone((CommandSourceStack)$$4.getSource(), $$0.apply($$4), $$1.apply($$4), $$2.apply($$4), $$3.apply($$4), Mode.MOVE)))
/*     */       
/* 142 */       .then(
/* 143 */         Commands.literal("normal")
/* 144 */         .executes($$4 -> clone((CommandSourceStack)$$4.getSource(), $$0.apply($$4), $$1.apply($$4), $$2.apply($$4), $$3.apply($$4), Mode.NORMAL)));
/*     */   }
/*     */ 
/*     */   
/*     */   private static int clone(CommandSourceStack $$0, DimensionAndPosition $$1, DimensionAndPosition $$2, DimensionAndPosition $$3, Predicate<BlockInWorld> $$4, Mode $$5) throws CommandSyntaxException {
/* 149 */     BlockPos $$6 = $$1.position();
/* 150 */     BlockPos $$7 = $$2.position();
/* 151 */     BoundingBox $$8 = BoundingBox.fromCorners((Vec3i)$$6, (Vec3i)$$7);
/* 152 */     BlockPos $$9 = $$3.position();
/* 153 */     BlockPos $$10 = $$9.offset($$8.getLength());
/* 154 */     BoundingBox $$11 = BoundingBox.fromCorners((Vec3i)$$9, (Vec3i)$$10);
/* 155 */     ServerLevel $$12 = $$1.dimension();
/* 156 */     ServerLevel $$13 = $$3.dimension();
/*     */     
/* 158 */     if (!$$5.canOverlap() && $$12 == $$13 && $$11.intersects($$8)) {
/* 159 */       throw ERROR_OVERLAP.create();
/*     */     }
/* 161 */     int $$14 = $$8.getXSpan() * $$8.getYSpan() * $$8.getZSpan();
/* 162 */     int $$15 = $$0.getLevel().getGameRules().getInt(GameRules.RULE_COMMAND_MODIFICATION_BLOCK_LIMIT);
/* 163 */     if ($$14 > $$15) {
/* 164 */       throw ERROR_AREA_TOO_LARGE.create(Integer.valueOf($$15), Integer.valueOf($$14));
/*     */     }
/* 166 */     if (!$$12.hasChunksAt($$6, $$7) || !$$13.hasChunksAt($$9, $$10)) {
/* 167 */       throw BlockPosArgument.ERROR_NOT_LOADED.create();
/*     */     }
/*     */     
/* 170 */     List<CloneBlockInfo> $$16 = Lists.newArrayList();
/* 171 */     List<CloneBlockInfo> $$17 = Lists.newArrayList();
/* 172 */     List<CloneBlockInfo> $$18 = Lists.newArrayList();
/* 173 */     Deque<BlockPos> $$19 = Lists.newLinkedList();
/*     */     
/* 175 */     BlockPos $$20 = new BlockPos($$11.minX() - $$8.minX(), $$11.minY() - $$8.minY(), $$11.minZ() - $$8.minZ());
/* 176 */     for (int $$21 = $$8.minZ(); $$21 <= $$8.maxZ(); $$21++) {
/* 177 */       for (int $$22 = $$8.minY(); $$22 <= $$8.maxY(); $$22++) {
/* 178 */         for (int $$23 = $$8.minX(); $$23 <= $$8.maxX(); $$23++) {
/* 179 */           BlockPos $$24 = new BlockPos($$23, $$22, $$21);
/* 180 */           BlockPos $$25 = $$24.offset((Vec3i)$$20);
/* 181 */           BlockInWorld $$26 = new BlockInWorld((LevelReader)$$12, $$24, false);
/* 182 */           BlockState $$27 = $$26.getState();
/* 183 */           if ($$4.test($$26)) {
/*     */ 
/*     */ 
/*     */             
/* 187 */             BlockEntity $$28 = $$12.getBlockEntity($$24);
/* 188 */             if ($$28 != null) {
/* 189 */               CompoundTag $$29 = $$28.saveWithoutMetadata();
/* 190 */               $$17.add(new CloneBlockInfo($$25, $$27, $$29));
/* 191 */               $$19.addLast($$24);
/* 192 */             } else if ($$27.isSolidRender((BlockGetter)$$12, $$24) || $$27.isCollisionShapeFullBlock((BlockGetter)$$12, $$24)) {
/* 193 */               $$16.add(new CloneBlockInfo($$25, $$27, null));
/* 194 */               $$19.addLast($$24);
/*     */             } else {
/* 196 */               $$18.add(new CloneBlockInfo($$25, $$27, null));
/* 197 */               $$19.addFirst($$24);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 203 */     if ($$5 == Mode.MOVE) {
/* 204 */       for (BlockPos $$30 : $$19) {
/* 205 */         BlockEntity $$31 = $$12.getBlockEntity($$30);
/* 206 */         Clearable.tryClear($$31);
/* 207 */         $$12.setBlock($$30, Blocks.BARRIER.defaultBlockState(), 2);
/*     */       } 
/* 209 */       for (BlockPos $$32 : $$19) {
/* 210 */         $$12.setBlock($$32, Blocks.AIR.defaultBlockState(), 3);
/*     */       }
/*     */     } 
/*     */     
/* 214 */     List<CloneBlockInfo> $$33 = Lists.newArrayList();
/* 215 */     $$33.addAll($$16);
/* 216 */     $$33.addAll($$17);
/* 217 */     $$33.addAll($$18);
/*     */     
/* 219 */     List<CloneBlockInfo> $$34 = Lists.reverse($$33);
/* 220 */     for (CloneBlockInfo $$35 : $$34) {
/* 221 */       BlockEntity $$36 = $$13.getBlockEntity($$35.pos);
/* 222 */       Clearable.tryClear($$36);
/* 223 */       $$13.setBlock($$35.pos, Blocks.BARRIER.defaultBlockState(), 2);
/*     */     } 
/*     */     
/* 226 */     int $$37 = 0;
/* 227 */     for (CloneBlockInfo $$38 : $$33) {
/* 228 */       if ($$13.setBlock($$38.pos, $$38.state, 2)) {
/* 229 */         $$37++;
/*     */       }
/*     */     } 
/* 232 */     for (CloneBlockInfo $$39 : $$17) {
/* 233 */       BlockEntity $$40 = $$13.getBlockEntity($$39.pos);
/* 234 */       if ($$39.tag != null && $$40 != null) {
/* 235 */         $$40.load($$39.tag);
/* 236 */         $$40.setChanged();
/*     */       } 
/* 238 */       $$13.setBlock($$39.pos, $$39.state, 2);
/*     */     } 
/*     */     
/* 241 */     for (CloneBlockInfo $$41 : $$34) {
/* 242 */       $$13.blockUpdated($$41.pos, $$41.state.getBlock());
/*     */     }
/*     */     
/* 245 */     $$13.getBlockTicks().copyAreaFrom($$12.getBlockTicks(), $$8, (Vec3i)$$20);
/*     */     
/* 247 */     if ($$37 == 0) {
/* 248 */       throw ERROR_FAILED.create();
/*     */     }
/*     */     
/* 251 */     int $$42 = $$37;
/* 252 */     $$0.sendSuccess(() -> Component.translatable("commands.clone.success", new Object[] { Integer.valueOf($$0) }), true);
/*     */     
/* 254 */     return $$37;
/*     */   }
/*     */   
/*     */   private enum Mode {
/* 258 */     FORCE(true),
/* 259 */     MOVE(true),
/* 260 */     NORMAL(false);
/*     */     
/*     */     private final boolean canOverlap;
/*     */ 
/*     */     
/*     */     Mode(boolean $$0) {
/* 266 */       this.canOverlap = $$0;
/*     */     }
/*     */     
/*     */     public boolean canOverlap() {
/* 270 */       return this.canOverlap;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class CloneBlockInfo {
/*     */     public final BlockPos pos;
/*     */     public final BlockState state;
/*     */     @Nullable
/*     */     public final CompoundTag tag;
/*     */     
/*     */     public CloneBlockInfo(BlockPos $$0, BlockState $$1, @Nullable CompoundTag $$2) {
/* 281 */       this.pos = $$0;
/* 282 */       this.state = $$1;
/* 283 */       this.tag = $$2;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\CloneCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */