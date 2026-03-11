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
/*     */ import it.unimi.dsi.fastutil.longs.LongSet;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.coordinates.ColumnPosArgument;
/*     */ import net.minecraft.core.SectionPos;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.resources.ResourceKey;
/*     */ import net.minecraft.server.level.ColumnPos;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.Level;
/*     */ 
/*     */ public class ForceLoadCommand {
/*     */   private static final int MAX_CHUNK_LIMIT = 256;
/*     */   
/*     */   static {
/*  28 */     ERROR_TOO_MANY_CHUNKS = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.forceload.toobig", new Object[] { $$0, $$1 }));
/*  29 */     ERROR_NOT_TICKING = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.forceload.query.failure", new Object[] { $$0, $$1 }));
/*  30 */   } private static final Dynamic2CommandExceptionType ERROR_TOO_MANY_CHUNKS; private static final Dynamic2CommandExceptionType ERROR_NOT_TICKING; private static final SimpleCommandExceptionType ERROR_ALL_ADDED = new SimpleCommandExceptionType((Message)Component.translatable("commands.forceload.added.failure"));
/*  31 */   private static final SimpleCommandExceptionType ERROR_NONE_REMOVED = new SimpleCommandExceptionType((Message)Component.translatable("commands.forceload.removed.failure"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  34 */     $$0.register(
/*  35 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("forceload")
/*  36 */         .requires($$0 -> $$0.hasPermission(2)))
/*  37 */         .then(
/*  38 */           Commands.literal("add")
/*  39 */           .then((
/*  40 */             (RequiredArgumentBuilder)Commands.argument("from", (ArgumentType)ColumnPosArgument.columnPos())
/*  41 */             .executes($$0 -> changeForceLoad((CommandSourceStack)$$0.getSource(), ColumnPosArgument.getColumnPos($$0, "from"), ColumnPosArgument.getColumnPos($$0, "from"), true)))
/*  42 */             .then(
/*  43 */               Commands.argument("to", (ArgumentType)ColumnPosArgument.columnPos())
/*  44 */               .executes($$0 -> changeForceLoad((CommandSourceStack)$$0.getSource(), ColumnPosArgument.getColumnPos($$0, "from"), ColumnPosArgument.getColumnPos($$0, "to"), true))))))
/*     */         
/*  46 */         .then((
/*  47 */           (LiteralArgumentBuilder)Commands.literal("remove")
/*  48 */           .then((
/*  49 */             (RequiredArgumentBuilder)Commands.argument("from", (ArgumentType)ColumnPosArgument.columnPos())
/*  50 */             .executes($$0 -> changeForceLoad((CommandSourceStack)$$0.getSource(), ColumnPosArgument.getColumnPos($$0, "from"), ColumnPosArgument.getColumnPos($$0, "from"), false)))
/*  51 */             .then(
/*  52 */               Commands.argument("to", (ArgumentType)ColumnPosArgument.columnPos())
/*  53 */               .executes($$0 -> changeForceLoad((CommandSourceStack)$$0.getSource(), ColumnPosArgument.getColumnPos($$0, "from"), ColumnPosArgument.getColumnPos($$0, "to"), false)))))
/*  54 */           .then(
/*  55 */             Commands.literal("all")
/*  56 */             .executes($$0 -> removeAll((CommandSourceStack)$$0.getSource())))))
/*     */ 
/*     */         
/*  59 */         .then((
/*  60 */           (LiteralArgumentBuilder)Commands.literal("query")
/*  61 */           .executes($$0 -> listForceLoad((CommandSourceStack)$$0.getSource())))
/*  62 */           .then(
/*  63 */             Commands.argument("pos", (ArgumentType)ColumnPosArgument.columnPos())
/*  64 */             .executes($$0 -> queryForceLoad((CommandSourceStack)$$0.getSource(), ColumnPosArgument.getColumnPos($$0, "pos"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int queryForceLoad(CommandSourceStack $$0, ColumnPos $$1) throws CommandSyntaxException {
/*  71 */     ChunkPos $$2 = $$1.toChunkPos();
/*  72 */     ServerLevel $$3 = $$0.getLevel();
/*  73 */     ResourceKey<Level> $$4 = $$3.dimension();
/*  74 */     boolean $$5 = $$3.getForcedChunks().contains($$2.toLong());
/*     */     
/*  76 */     if ($$5) {
/*  77 */       $$0.sendSuccess(() -> Component.translatable("commands.forceload.query.success", new Object[] { Component.translationArg($$0), Component.translationArg($$1.location()) }), false);
/*  78 */       return 1;
/*     */     } 
/*  80 */     throw ERROR_NOT_TICKING.create($$2, $$4.location());
/*     */   }
/*     */ 
/*     */   
/*     */   private static int listForceLoad(CommandSourceStack $$0) {
/*  85 */     ServerLevel $$1 = $$0.getLevel();
/*  86 */     ResourceKey<Level> $$2 = $$1.dimension();
/*  87 */     LongSet $$3 = $$1.getForcedChunks();
/*  88 */     int $$4 = $$3.size();
/*     */     
/*  90 */     if ($$4 > 0) {
/*  91 */       String $$5 = Joiner.on(", ").join($$3.stream().sorted().map(ChunkPos::new).map(ChunkPos::toString).iterator());
/*     */       
/*  93 */       if ($$4 == 1) {
/*  94 */         $$0.sendSuccess(() -> Component.translatable("commands.forceload.list.single", new Object[] { Component.translationArg($$0.location()), $$1 }), false);
/*     */       } else {
/*  96 */         $$0.sendSuccess(() -> Component.translatable("commands.forceload.list.multiple", new Object[] { Integer.valueOf($$0), Component.translationArg($$1.location()), $$2 }), false);
/*     */       } 
/*     */     } else {
/*  99 */       $$0.sendFailure((Component)Component.translatable("commands.forceload.added.none", new Object[] { Component.translationArg($$2.location()) }));
/*     */     } 
/* 101 */     return $$4;
/*     */   }
/*     */   
/*     */   private static int removeAll(CommandSourceStack $$0) {
/* 105 */     ServerLevel $$1 = $$0.getLevel();
/* 106 */     ResourceKey<Level> $$2 = $$1.dimension();
/* 107 */     LongSet $$3 = $$1.getForcedChunks();
/* 108 */     $$3.forEach($$1 -> $$0.setChunkForced(ChunkPos.getX($$1), ChunkPos.getZ($$1), false));
/* 109 */     $$0.sendSuccess(() -> Component.translatable("commands.forceload.removed.all", new Object[] { Component.translationArg($$0.location()) }), true);
/* 110 */     return 0;
/*     */   }
/*     */   
/*     */   private static int changeForceLoad(CommandSourceStack $$0, ColumnPos $$1, ColumnPos $$2, boolean $$3) throws CommandSyntaxException {
/* 114 */     int $$4 = Math.min($$1.x(), $$2.x());
/* 115 */     int $$5 = Math.min($$1.z(), $$2.z());
/* 116 */     int $$6 = Math.max($$1.x(), $$2.x());
/* 117 */     int $$7 = Math.max($$1.z(), $$2.z());
/*     */     
/* 119 */     if ($$4 < -30000000 || $$5 < -30000000 || $$6 >= 30000000 || $$7 >= 30000000)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 124 */       throw BlockPosArgument.ERROR_OUT_OF_WORLD.create();
/*     */     }
/*     */     
/* 127 */     int $$8 = SectionPos.blockToSectionCoord($$4);
/* 128 */     int $$9 = SectionPos.blockToSectionCoord($$5);
/* 129 */     int $$10 = SectionPos.blockToSectionCoord($$6);
/* 130 */     int $$11 = SectionPos.blockToSectionCoord($$7);
/*     */     
/* 132 */     long $$12 = (($$10 - $$8) + 1L) * (($$11 - $$9) + 1L);
/*     */     
/* 134 */     if ($$12 > 256L) {
/* 135 */       throw ERROR_TOO_MANY_CHUNKS.create(Integer.valueOf(256), Long.valueOf($$12));
/*     */     }
/*     */     
/* 138 */     ServerLevel $$13 = $$0.getLevel();
/* 139 */     ResourceKey<Level> $$14 = $$13.dimension();
/*     */     
/* 141 */     ChunkPos $$15 = null;
/* 142 */     int $$16 = 0;
/* 143 */     for (int $$17 = $$8; $$17 <= $$10; $$17++) {
/* 144 */       for (int $$18 = $$9; $$18 <= $$11; $$18++) {
/* 145 */         boolean $$19 = $$13.setChunkForced($$17, $$18, $$3);
/* 146 */         if ($$19) {
/* 147 */           $$16++;
/* 148 */           if ($$15 == null) {
/* 149 */             $$15 = new ChunkPos($$17, $$18);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 155 */     ChunkPos $$20 = $$15;
/* 156 */     if ($$16 == 0)
/* 157 */       throw ($$3 ? ERROR_ALL_ADDED : ERROR_NONE_REMOVED).create(); 
/* 158 */     if ($$16 == 1) {
/* 159 */       $$0.sendSuccess(() -> Component.translatable("commands.forceload." + ($$0 ? "added" : "removed") + ".single", new Object[] { Component.translationArg($$1), Component.translationArg($$2.location()) }), true);
/*     */     } else {
/* 161 */       ChunkPos $$21 = new ChunkPos($$8, $$9);
/* 162 */       ChunkPos $$22 = new ChunkPos($$10, $$11);
/* 163 */       $$0.sendSuccess(() -> Component.translatable("commands.forceload." + ($$0 ? "added" : "removed") + ".multiple", new Object[] { Component.translationArg($$1), Component.translationArg($$2.location()), Component.translationArg($$3), Component.translationArg($$4) }), true);
/*     */     } 
/*     */     
/* 166 */     return $$16;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ForceLoadCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */