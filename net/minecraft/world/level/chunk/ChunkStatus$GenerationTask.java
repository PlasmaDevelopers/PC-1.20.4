package net.minecraft.world.level.chunk;

import com.mojang.datafixers.util.Either;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

interface GenerationTask {
  CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>> doWork(ChunkStatus paramChunkStatus, Executor paramExecutor, ServerLevel paramServerLevel, ChunkGenerator paramChunkGenerator, StructureTemplateManager paramStructureTemplateManager, ThreadedLevelLightEngine paramThreadedLevelLightEngine, Function<ChunkAccess, CompletableFuture<Either<ChunkAccess, ChunkHolder.ChunkLoadingFailure>>> paramFunction, List<ChunkAccess> paramList, ChunkAccess paramChunkAccess);
}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\ChunkStatus$GenerationTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */