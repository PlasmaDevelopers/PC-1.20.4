/*    */ package net.minecraft.commands.synchronization;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Wrapper
/*    */   implements SuggestionProvider<SharedSuggestionProvider>
/*    */ {
/*    */   private final SuggestionProvider<SharedSuggestionProvider> delegate;
/*    */   final ResourceLocation name;
/*    */   
/*    */   public Wrapper(ResourceLocation $$0, SuggestionProvider<SharedSuggestionProvider> $$1) {
/* 63 */     this.delegate = $$1;
/* 64 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompletableFuture<Suggestions> getSuggestions(CommandContext<SharedSuggestionProvider> $$0, SuggestionsBuilder $$1) throws CommandSyntaxException {
/* 69 */     return this.delegate.getSuggestions($$0, $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\SuggestionProviders$Wrapper.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */