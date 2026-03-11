/*     */ package net.minecraft.server;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionException;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.commands.CommandSource;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.resources.FileToIdConverter;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.tags.TagLoader;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.world.phys.Vec2;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerFunctionLibrary implements PreparableReloadListener {
/*  34 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  36 */   private static final FileToIdConverter LISTER = new FileToIdConverter("functions", ".mcfunction");
/*     */   
/*  38 */   private volatile Map<ResourceLocation, CommandFunction<CommandSourceStack>> functions = (Map<ResourceLocation, CommandFunction<CommandSourceStack>>)ImmutableMap.of();
/*  39 */   private final TagLoader<CommandFunction<CommandSourceStack>> tagsLoader = new TagLoader(this::getFunction, "tags/functions");
/*  40 */   private volatile Map<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> tags = Map.of();
/*     */   
/*     */   private final int functionCompilationLevel;
/*     */   private final CommandDispatcher<CommandSourceStack> dispatcher;
/*     */   
/*     */   public Optional<CommandFunction<CommandSourceStack>> getFunction(ResourceLocation $$0) {
/*  46 */     return Optional.ofNullable(this.functions.get($$0));
/*     */   }
/*     */   
/*     */   public Map<ResourceLocation, CommandFunction<CommandSourceStack>> getFunctions() {
/*  50 */     return this.functions;
/*     */   }
/*     */   
/*     */   public Collection<CommandFunction<CommandSourceStack>> getTag(ResourceLocation $$0) {
/*  54 */     return this.tags.getOrDefault($$0, List.of());
/*     */   }
/*     */   
/*     */   public Iterable<ResourceLocation> getAvailableTags() {
/*  58 */     return this.tags.keySet();
/*     */   }
/*     */   
/*     */   public ServerFunctionLibrary(int $$0, CommandDispatcher<CommandSourceStack> $$1) {
/*  62 */     this.functionCompilationLevel = $$0;
/*  63 */     this.dispatcher = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Void> reload(PreparableReloadListener.PreparationBarrier $$0, ResourceManager $$1, ProfilerFiller $$2, ProfilerFiller $$3, Executor $$4, Executor $$5) {
/*  68 */     CompletableFuture<Map<ResourceLocation, List<TagLoader.EntryWithSource>>> $$6 = CompletableFuture.supplyAsync(() -> this.tagsLoader.load($$0), $$4);
/*     */ 
/*     */ 
/*     */     
/*  72 */     CompletableFuture<Map<ResourceLocation, CompletableFuture<CommandFunction<CommandSourceStack>>>> $$7 = CompletableFuture.supplyAsync(() -> LISTER.listMatchingResources($$0), $$4).thenCompose($$1 -> {
/*     */           Map<ResourceLocation, CompletableFuture<CommandFunction<CommandSourceStack>>> $$2 = Maps.newHashMap();
/*     */ 
/*     */           
/*     */           CommandSourceStack $$3 = new CommandSourceStack(CommandSource.NULL, Vec3.ZERO, Vec2.ZERO, null, this.functionCompilationLevel, "", CommonComponents.EMPTY, null, null);
/*     */ 
/*     */           
/*     */           for (Map.Entry<ResourceLocation, Resource> $$4 : (Iterable<Map.Entry<ResourceLocation, Resource>>)$$1.entrySet()) {
/*     */             ResourceLocation $$5 = $$4.getKey();
/*     */ 
/*     */             
/*     */             ResourceLocation $$6 = LISTER.fileToId($$5);
/*     */             
/*     */             $$2.put($$6, CompletableFuture.supplyAsync((), $$0));
/*     */           } 
/*     */           
/*     */           CompletableFuture[] arrayOfCompletableFuture = (CompletableFuture[])$$2.values().toArray((Object[])new CompletableFuture[0]);
/*     */           
/*     */           return CompletableFuture.allOf((CompletableFuture<?>[])arrayOfCompletableFuture).handle(());
/*     */         });
/*     */     
/*  93 */     Objects.requireNonNull($$0); return $$6.thenCombine($$7, Pair::of).thenCompose($$0::wait)
/*  94 */       .thenAcceptAsync($$0 -> {
/*     */           Map<ResourceLocation, CompletableFuture<CommandFunction<CommandSourceStack>>> $$1 = (Map<ResourceLocation, CompletableFuture<CommandFunction<CommandSourceStack>>>)$$0.getSecond();
/*     */           ImmutableMap.Builder<ResourceLocation, CommandFunction<CommandSourceStack>> $$2 = ImmutableMap.builder();
/*     */           $$1.forEach(());
/*     */           this.functions = (Map<ResourceLocation, CommandFunction<CommandSourceStack>>)$$2.build();
/*     */           this.tags = this.tagsLoader.build((Map)$$0.getFirst());
/*     */         }$$5);
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
/*     */   private static List<String> readLines(Resource $$0) {
/*     */     
/* 114 */     try { BufferedReader $$1 = $$0.openAsReader(); 
/* 115 */       try { List<String> list = $$1.lines().toList();
/* 116 */         if ($$1 != null) $$1.close();  return list; } catch (Throwable throwable) { if ($$1 != null) try { $$1.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException $$2)
/* 117 */     { throw new CompletionException($$2); }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerFunctionLibrary.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */