/*     */ package net.minecraft.server;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.commands.CommandResultCallback;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.execution.ExecutionContext;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.commands.functions.InstantiatedFunction;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ServerFunctionManager {
/*  22 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*  24 */   private static final ResourceLocation TICK_FUNCTION_TAG = new ResourceLocation("tick");
/*  25 */   private static final ResourceLocation LOAD_FUNCTION_TAG = new ResourceLocation("load");
/*     */   
/*     */   private final MinecraftServer server;
/*     */   
/*  29 */   private List<CommandFunction<CommandSourceStack>> ticking = (List<CommandFunction<CommandSourceStack>>)ImmutableList.of();
/*     */   
/*     */   private boolean postReload;
/*     */   private ServerFunctionLibrary library;
/*     */   
/*     */   public ServerFunctionManager(MinecraftServer $$0, ServerFunctionLibrary $$1) {
/*  35 */     this.server = $$0;
/*  36 */     this.library = $$1;
/*  37 */     postReload($$1);
/*     */   }
/*     */   
/*     */   public CommandDispatcher<CommandSourceStack> getDispatcher() {
/*  41 */     return this.server.getCommands().getDispatcher();
/*     */   }
/*     */   
/*     */   public void tick() {
/*  45 */     if (!this.server.tickRateManager().runsNormally()) {
/*     */       return;
/*     */     }
/*  48 */     if (this.postReload) {
/*  49 */       this.postReload = false;
/*  50 */       Collection<CommandFunction<CommandSourceStack>> $$0 = this.library.getTag(LOAD_FUNCTION_TAG);
/*  51 */       executeTagFunctions($$0, LOAD_FUNCTION_TAG);
/*     */     } 
/*  53 */     executeTagFunctions(this.ticking, TICK_FUNCTION_TAG);
/*     */   }
/*     */   
/*     */   private void executeTagFunctions(Collection<CommandFunction<CommandSourceStack>> $$0, ResourceLocation $$1) {
/*  57 */     Objects.requireNonNull($$1); this.server.getProfiler().push($$1::toString);
/*  58 */     for (CommandFunction<CommandSourceStack> $$2 : $$0) {
/*  59 */       execute($$2, getGameLoopSender());
/*     */     }
/*  61 */     this.server.getProfiler().pop();
/*     */   }
/*     */   
/*     */   public void execute(CommandFunction<CommandSourceStack> $$0, CommandSourceStack $$1) {
/*  65 */     ProfilerFiller $$2 = this.server.getProfiler();
/*  66 */     $$2.push(() -> "function " + $$0.id());
/*     */     
/*  68 */     try { InstantiatedFunction<CommandSourceStack> $$3 = $$0.instantiate(null, getDispatcher(), $$1);
/*  69 */       Commands.executeCommandInContext($$1, $$2 -> ExecutionContext.queueInitialFunctionCall($$2, $$0, (ExecutionCommandSource)$$1, CommandResultCallback.EMPTY)); }
/*  70 */     catch (FunctionInstantiationException functionInstantiationException) {  }
/*  71 */     catch (Exception $$4)
/*  72 */     { LOGGER.warn("Failed to execute function {}", $$0.id(), $$4); }
/*     */     finally
/*  74 */     { $$2.pop(); }
/*     */   
/*     */   }
/*     */   
/*     */   public void replaceLibrary(ServerFunctionLibrary $$0) {
/*  79 */     this.library = $$0;
/*  80 */     postReload($$0);
/*     */   }
/*     */   
/*     */   private void postReload(ServerFunctionLibrary $$0) {
/*  84 */     this.ticking = (List<CommandFunction<CommandSourceStack>>)ImmutableList.copyOf($$0.getTag(TICK_FUNCTION_TAG));
/*  85 */     this.postReload = true;
/*     */   }
/*     */   
/*     */   public CommandSourceStack getGameLoopSender() {
/*  89 */     return this.server.createCommandSourceStack().withPermission(2).withSuppressedOutput();
/*     */   }
/*     */   
/*     */   public Optional<CommandFunction<CommandSourceStack>> get(ResourceLocation $$0) {
/*  93 */     return this.library.getFunction($$0);
/*     */   }
/*     */   
/*     */   public Collection<CommandFunction<CommandSourceStack>> getTag(ResourceLocation $$0) {
/*  97 */     return this.library.getTag($$0);
/*     */   }
/*     */   
/*     */   public Iterable<ResourceLocation> getFunctionNames() {
/* 101 */     return this.library.getFunctions().keySet();
/*     */   }
/*     */   
/*     */   public Iterable<ResourceLocation> getTagNames() {
/* 105 */     return this.library.getAvailableTags();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ServerFunctionManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */