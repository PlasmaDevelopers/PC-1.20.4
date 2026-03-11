/*    */ package net.minecraft.commands.synchronization;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.core.registries.BuiltInRegistries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.world.entity.EntityType;
/*    */ 
/*    */ public class SuggestionProviders {
/* 21 */   private static final Map<ResourceLocation, SuggestionProvider<SharedSuggestionProvider>> PROVIDERS_BY_NAME = Maps.newHashMap();
/* 22 */   private static final ResourceLocation DEFAULT_NAME = new ResourceLocation("ask_server"); public static final SuggestionProvider<SharedSuggestionProvider> ASK_SERVER; public static final SuggestionProvider<CommandSourceStack> ALL_RECIPES;
/*    */   static {
/* 24 */     ASK_SERVER = register(DEFAULT_NAME, ($$0, $$1) -> ((SharedSuggestionProvider)$$0.getSource()).customSuggestion($$0));
/* 25 */     ALL_RECIPES = register(new ResourceLocation("all_recipes"), ($$0, $$1) -> SharedSuggestionProvider.suggestResource(((SharedSuggestionProvider)$$0.getSource()).getRecipeNames(), $$1));
/* 26 */     AVAILABLE_SOUNDS = register(new ResourceLocation("available_sounds"), ($$0, $$1) -> SharedSuggestionProvider.suggestResource(((SharedSuggestionProvider)$$0.getSource()).getAvailableSounds(), $$1));
/* 27 */     SUMMONABLE_ENTITIES = register(new ResourceLocation("summonable_entities"), ($$0, $$1) -> SharedSuggestionProvider.suggestResource(BuiltInRegistries.ENTITY_TYPE.stream().filter(()), $$1, EntityType::getKey, ()));
/*    */   }
/*    */   public static final SuggestionProvider<CommandSourceStack> AVAILABLE_SOUNDS; public static final SuggestionProvider<CommandSourceStack> SUMMONABLE_ENTITIES;
/*    */   public static <S extends SharedSuggestionProvider> SuggestionProvider<S> register(ResourceLocation $$0, SuggestionProvider<SharedSuggestionProvider> $$1) {
/* 31 */     if (PROVIDERS_BY_NAME.containsKey($$0)) {
/* 32 */       throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + $$0);
/*    */     }
/* 34 */     PROVIDERS_BY_NAME.put($$0, $$1);
/* 35 */     return new Wrapper($$0, $$1);
/*    */   }
/*    */   
/*    */   public static SuggestionProvider<SharedSuggestionProvider> getProvider(ResourceLocation $$0) {
/* 39 */     return PROVIDERS_BY_NAME.getOrDefault($$0, ASK_SERVER);
/*    */   }
/*    */   
/*    */   public static ResourceLocation getName(SuggestionProvider<SharedSuggestionProvider> $$0) {
/* 43 */     if ($$0 instanceof Wrapper) {
/* 44 */       return ((Wrapper)$$0).name;
/*    */     }
/* 46 */     return DEFAULT_NAME;
/*    */   }
/*    */ 
/*    */   
/*    */   public static SuggestionProvider<SharedSuggestionProvider> safelySwap(SuggestionProvider<SharedSuggestionProvider> $$0) {
/* 51 */     if ($$0 instanceof Wrapper) {
/* 52 */       return $$0;
/*    */     }
/* 54 */     return ASK_SERVER;
/*    */   }
/*    */   
/*    */   protected static class Wrapper
/*    */     implements SuggestionProvider<SharedSuggestionProvider> {
/*    */     private final SuggestionProvider<SharedSuggestionProvider> delegate;
/*    */     final ResourceLocation name;
/*    */     
/*    */     public Wrapper(ResourceLocation $$0, SuggestionProvider<SharedSuggestionProvider> $$1) {
/* 63 */       this.delegate = $$1;
/* 64 */       this.name = $$0;
/*    */     }
/*    */ 
/*    */     
/*    */     public CompletableFuture<Suggestions> getSuggestions(CommandContext<SharedSuggestionProvider> $$0, SuggestionsBuilder $$1) throws CommandSyntaxException {
/* 69 */       return this.delegate.getSuggestions($$0, $$1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\SuggestionProviders.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */