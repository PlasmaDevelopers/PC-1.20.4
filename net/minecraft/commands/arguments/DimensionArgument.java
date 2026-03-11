/*    */ package net.minecraft.commands.arguments;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Collection;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class DimensionArgument implements ArgumentType<ResourceLocation> {
/*    */   private static final Collection<String> EXAMPLES;
/*    */   
/*    */   static {
/* 25 */     EXAMPLES = (Collection<String>)Stream.<ResourceKey>of(new ResourceKey[] { Level.OVERWORLD, Level.NETHER }).map($$0 -> $$0.location().toString()).collect(Collectors.toList());
/*    */     
/* 27 */     ERROR_INVALID_VALUE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.dimension.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   private static final DynamicCommandExceptionType ERROR_INVALID_VALUE;
/*    */   public ResourceLocation parse(StringReader $$0) throws CommandSyntaxException {
/* 31 */     return ResourceLocation.read($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 36 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 37 */       return SharedSuggestionProvider.suggestResource(((SharedSuggestionProvider)$$0.getSource()).levels().stream().map(ResourceKey::location), $$1);
/*    */     }
/* 39 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 44 */     return EXAMPLES;
/*    */   }
/*    */   
/*    */   public static DimensionArgument dimension() {
/* 48 */     return new DimensionArgument();
/*    */   }
/*    */   
/*    */   public static ServerLevel getDimension(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 52 */     ResourceLocation $$2 = (ResourceLocation)$$0.getArgument($$1, ResourceLocation.class);
/* 53 */     ResourceKey<Level> $$3 = ResourceKey.create(Registries.DIMENSION, $$2);
/* 54 */     ServerLevel $$4 = ((CommandSourceStack)$$0.getSource()).getServer().getLevel($$3);
/* 55 */     if ($$4 == null) {
/* 56 */       throw ERROR_INVALID_VALUE.create($$2);
/*    */     }
/* 58 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\DimensionArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */