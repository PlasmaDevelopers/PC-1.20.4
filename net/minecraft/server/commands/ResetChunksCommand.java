/*     */ package net.minecraft.server.commands;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.BoolArgumentType;
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Unit;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import net.minecraft.Util;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerChunkCache;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.util.thread.ProcessorMailbox;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ import net.minecraft.world.level.chunk.ChunkAccess;
/*     */ import net.minecraft.world.level.chunk.ChunkStatus;
/*     */ import net.minecraft.world.level.chunk.ImposterProtoChunk;
/*     */ import net.minecraft.world.level.chunk.LevelChunk;
/*     */ import net.minecraft.world.level.levelgen.Heightmap;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ResetChunksCommand {
/*  39 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  42 */     $$0.register(
/*  43 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("resetchunks")
/*  44 */         .requires($$0 -> $$0.hasPermission(2)))
/*  45 */         .executes($$0 -> resetChunks((CommandSourceStack)$$0.getSource(), 0, true)))
/*  46 */         .then((
/*  47 */           (RequiredArgumentBuilder)Commands.argument("range", (ArgumentType)IntegerArgumentType.integer(0, 5))
/*  48 */           .executes($$0 -> resetChunks((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "range"), true)))
/*  49 */           .then(
/*  50 */             Commands.argument("skipOldChunks", (ArgumentType)BoolArgumentType.bool())
/*  51 */             .executes($$0 -> resetChunks((CommandSourceStack)$$0.getSource(), IntegerArgumentType.getInteger($$0, "range"), BoolArgumentType.getBool($$0, "skipOldChunks"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int resetChunks(CommandSourceStack $$0, int $$1, boolean $$2) {
/*  58 */     ServerLevel $$3 = $$0.getLevel();
/*  59 */     ServerChunkCache $$4 = $$3.getChunkSource();
/*  60 */     $$4.chunkMap.debugReloadGenerator();
/*  61 */     Vec3 $$5 = $$0.getPosition();
/*     */     
/*  63 */     ChunkPos $$6 = new ChunkPos(BlockPos.containing((Position)$$5));
/*     */     
/*  65 */     int $$7 = $$6.z - $$1;
/*  66 */     int $$8 = $$6.z + $$1;
/*     */     
/*  68 */     int $$9 = $$6.x - $$1;
/*  69 */     int $$10 = $$6.x + $$1;
/*     */     
/*  71 */     for (int $$11 = $$7; $$11 <= $$8; $$11++) {
/*  72 */       for (int $$12 = $$9; $$12 <= $$10; $$12++) {
/*  73 */         ChunkPos $$13 = new ChunkPos($$12, $$11);
/*  74 */         LevelChunk $$14 = $$4.getChunk($$12, $$11, false);
/*  75 */         if ($$14 != null && (!$$2 || !$$14.isOldNoiseGeneration()))
/*     */         {
/*     */           
/*  78 */           for (BlockPos $$15 : BlockPos.betweenClosed($$13.getMinBlockX(), $$3.getMinBuildHeight(), $$13.getMinBlockZ(), $$13.getMaxBlockX(), $$3.getMaxBuildHeight() - 1, $$13.getMaxBlockZ())) {
/*  79 */             $$3.setBlock($$15, Blocks.AIR.defaultBlockState(), 16);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } 
/*  84 */     ProcessorMailbox<Runnable> $$16 = ProcessorMailbox.create(Util.backgroundExecutor(), "worldgen-resetchunks");
/*  85 */     long $$17 = System.currentTimeMillis();
/*     */ 
/*     */ 
/*     */     
/*  89 */     int $$18 = ($$1 * 2 + 1) * ($$1 * 2 + 1);
/*     */     
/*  91 */     for (UnmodifiableIterator<ChunkStatus> unmodifiableIterator = ImmutableList.of(ChunkStatus.BIOMES, ChunkStatus.NOISE, ChunkStatus.SURFACE, ChunkStatus.CARVERS, ChunkStatus.FEATURES, ChunkStatus.INITIALIZE_LIGHT).iterator(); unmodifiableIterator.hasNext(); ) { ChunkStatus $$19 = unmodifiableIterator.next();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  98 */       long $$20 = System.currentTimeMillis();
/*  99 */       Objects.requireNonNull($$16); CompletableFuture<Unit> $$21 = CompletableFuture.supplyAsync(() -> Unit.INSTANCE, $$16::tell);
/*     */       
/* 101 */       for (int $$22 = $$6.z - $$1; $$22 <= $$6.z + $$1; $$22++) {
/* 102 */         for (int $$23 = $$6.x - $$1; $$23 <= $$6.x + $$1; $$23++) {
/* 103 */           ChunkPos $$24 = new ChunkPos($$23, $$22);
/* 104 */           LevelChunk $$25 = $$4.getChunk($$23, $$22, false);
/* 105 */           if ($$25 != null && (!$$2 || !$$25.isOldNoiseGeneration())) {
/*     */ 
/*     */ 
/*     */             
/* 109 */             List<ChunkAccess> $$26 = Lists.newArrayList();
/* 110 */             int $$27 = Math.max(1, $$19.getRange());
/* 111 */             for (int $$28 = $$24.z - $$27; $$28 <= $$24.z + $$27; $$28++) {
/* 112 */               for (int $$29 = $$24.x - $$27; $$29 <= $$24.x + $$27; $$29++) {
/* 113 */                 ChunkAccess $$33, $$30 = $$4.getChunk($$29, $$28, $$19.getParent(), true);
/*     */                 
/* 115 */                 if ($$30 instanceof ImposterProtoChunk) {
/* 116 */                   ImposterProtoChunk imposterProtoChunk = new ImposterProtoChunk(((ImposterProtoChunk)$$30).getWrapped(), true);
/* 117 */                 } else if ($$30 instanceof LevelChunk) {
/* 118 */                   ImposterProtoChunk imposterProtoChunk = new ImposterProtoChunk((LevelChunk)$$30, true);
/*     */                 } else {
/* 120 */                   $$33 = $$30;
/*     */                 } 
/* 122 */                 $$26.add($$33);
/*     */               } 
/*     */             } 
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
/* 135 */             Objects.requireNonNull($$16); $$21 = $$21.thenComposeAsync($$5 -> { Objects.requireNonNull($$1); return $$0.generate($$1::tell, $$2, $$3.getGenerator(), $$2.getStructureManager(), $$3.getLightEngine(), (), $$4).thenApply(()); }$$16::tell);
/*     */           } 
/*     */         } 
/*     */       } 
/* 139 */       Objects.requireNonNull($$21); $$0.getServer().managedBlock($$21::isDone);
/* 140 */       LOGGER.debug("" + $$19 + " took " + $$19 + " ms"); }
/*     */ 
/*     */ 
/*     */     
/* 144 */     long $$34 = System.currentTimeMillis();
/* 145 */     for (int $$35 = $$6.z - $$1; $$35 <= $$6.z + $$1; $$35++) {
/* 146 */       for (int $$36 = $$6.x - $$1; $$36 <= $$6.x + $$1; $$36++) {
/* 147 */         ChunkPos $$37 = new ChunkPos($$36, $$35);
/*     */         
/* 149 */         LevelChunk $$38 = $$4.getChunk($$36, $$35, false);
/* 150 */         if ($$38 != null && (!$$2 || !$$38.isOldNoiseGeneration()))
/*     */         {
/*     */ 
/*     */           
/* 154 */           for (BlockPos $$39 : BlockPos.betweenClosed($$37.getMinBlockX(), $$3.getMinBuildHeight(), $$37.getMinBlockZ(), $$37.getMaxBlockX(), $$3.getMaxBuildHeight() - 1, $$37.getMaxBlockZ()))
/* 155 */             $$4.blockChanged($$39); 
/*     */         }
/*     */       } 
/*     */     } 
/* 159 */     LOGGER.debug("blockChanged took " + System.currentTimeMillis() - $$34 + " ms");
/*     */     
/* 161 */     long $$40 = System.currentTimeMillis() - $$17;
/* 162 */     $$0.sendSuccess(() -> Component.literal(String.format(Locale.ROOT, "%d chunks have been reset. This took %d ms for %d chunks, or %02f ms per chunk", new Object[] { Integer.valueOf($$0), Long.valueOf($$1), Integer.valueOf($$0), Float.valueOf((float)$$1 / $$0) })), true);
/*     */     
/* 164 */     return 1;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ResetChunksCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */