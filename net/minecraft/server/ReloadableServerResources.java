/*    */ package net.minecraft.server;
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.Registry;
/*    */ import net.minecraft.core.RegistryAccess;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.PreparableReloadListener;
/*    */ import net.minecraft.server.packs.resources.ResourceManager;
/*    */ import net.minecraft.server.packs.resources.SimpleReloadInstance;
/*    */ import net.minecraft.tags.TagKey;
/*    */ import net.minecraft.tags.TagManager;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.flag.FeatureFlagSet;
/*    */ import net.minecraft.world.item.crafting.RecipeManager;
/*    */ import net.minecraft.world.level.block.Blocks;
/*    */ import net.minecraft.world.level.storage.loot.LootDataManager;
/*    */ import org.slf4j.Logger;
/*    */ 
/*    */ public class ReloadableServerResources {
/* 29 */   private static final Logger LOGGER = LogUtils.getLogger();
/* 30 */   private static final CompletableFuture<Unit> DATA_RELOAD_INITIAL_TASK = CompletableFuture.completedFuture(Unit.INSTANCE);
/*    */   
/*    */   private final CommandBuildContext.Configurable commandBuildContext;
/*    */   private final Commands commands;
/* 34 */   private final RecipeManager recipes = new RecipeManager();
/*    */   private final TagManager tagManager;
/* 36 */   private final LootDataManager lootData = new LootDataManager();
/* 37 */   private final ServerAdvancementManager advancements = new ServerAdvancementManager(this.lootData);
/*    */   private final ServerFunctionLibrary functionLibrary;
/*    */   
/*    */   public ReloadableServerResources(RegistryAccess.Frozen $$0, FeatureFlagSet $$1, Commands.CommandSelection $$2, int $$3) {
/* 41 */     this.tagManager = new TagManager((RegistryAccess)$$0);
/* 42 */     this.commandBuildContext = CommandBuildContext.configurable((RegistryAccess)$$0, $$1);
/* 43 */     this.commands = new Commands($$2, (CommandBuildContext)this.commandBuildContext);
/* 44 */     this.commandBuildContext.missingTagAccessPolicy(CommandBuildContext.MissingTagAccessPolicy.CREATE_NEW);
/* 45 */     this.functionLibrary = new ServerFunctionLibrary($$3, this.commands.getDispatcher());
/*    */   }
/*    */   
/*    */   public ServerFunctionLibrary getFunctionLibrary() {
/* 49 */     return this.functionLibrary;
/*    */   }
/*    */   
/*    */   public LootDataManager getLootData() {
/* 53 */     return this.lootData;
/*    */   }
/*    */   
/*    */   public RecipeManager getRecipeManager() {
/* 57 */     return this.recipes;
/*    */   }
/*    */   
/*    */   public Commands getCommands() {
/* 61 */     return this.commands;
/*    */   }
/*    */   
/*    */   public ServerAdvancementManager getAdvancements() {
/* 65 */     return this.advancements;
/*    */   }
/*    */   
/*    */   public List<PreparableReloadListener> listeners() {
/* 69 */     return (List)List.of(this.tagManager, this.lootData, this.recipes, this.functionLibrary, this.advancements);
/*    */   }
/*    */   
/*    */   public static CompletableFuture<ReloadableServerResources> loadResources(ResourceManager $$0, RegistryAccess.Frozen $$1, FeatureFlagSet $$2, Commands.CommandSelection $$3, int $$4, Executor $$5, Executor $$6) {
/* 73 */     ReloadableServerResources $$7 = new ReloadableServerResources($$1, $$2, $$3, $$4);
/* 74 */     return SimpleReloadInstance.create($$0, $$7.listeners(), $$5, $$6, DATA_RELOAD_INITIAL_TASK, LOGGER.isDebugEnabled()).done()
/* 75 */       .whenComplete(($$1, $$2) -> $$0.commandBuildContext.missingTagAccessPolicy(CommandBuildContext.MissingTagAccessPolicy.FAIL))
/* 76 */       .thenApply($$1 -> $$0);
/*    */   }
/*    */   
/*    */   public void updateRegistryTags(RegistryAccess $$0) {
/* 80 */     this.tagManager.getResult().forEach($$1 -> updateRegistryTags($$0, $$1));
/* 81 */     Blocks.rebuildCache();
/*    */   }
/*    */   
/*    */   private static <T> void updateRegistryTags(RegistryAccess $$0, TagManager.LoadResult<T> $$1) {
/* 85 */     ResourceKey<? extends Registry<T>> $$2 = $$1.key();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 90 */     Map<TagKey<T>, List<Holder<T>>> $$3 = (Map<TagKey<T>, List<Holder<T>>>)$$1.tags().entrySet().stream().collect(
/* 91 */         Collectors.toUnmodifiableMap($$1 -> TagKey.create($$0, (ResourceLocation)$$1.getKey()), $$0 -> List.copyOf((Collection)$$0.getValue())));
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 96 */     $$0.registryOrThrow($$2).bindTags($$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\ReloadableServerResources.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */