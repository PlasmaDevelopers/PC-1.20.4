/*    */ package net.minecraft.commands.arguments.coordinates;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import com.mojang.brigadier.suggestion.Suggestions;
/*    */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.SharedSuggestionProvider;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class BlockPosArgument implements ArgumentType<Coordinates> {
/* 24 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "~0.5 ~1 ~-5" });
/*    */   
/* 26 */   public static final SimpleCommandExceptionType ERROR_NOT_LOADED = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.unloaded"));
/* 27 */   public static final SimpleCommandExceptionType ERROR_OUT_OF_WORLD = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.outofworld"));
/* 28 */   public static final SimpleCommandExceptionType ERROR_OUT_OF_BOUNDS = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.outofbounds"));
/*    */   
/*    */   public static BlockPosArgument blockPos() {
/* 31 */     return new BlockPosArgument();
/*    */   }
/*    */   
/*    */   public static BlockPos getLoadedBlockPos(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 35 */     ServerLevel $$2 = ((CommandSourceStack)$$0.getSource()).getLevel();
/* 36 */     return getLoadedBlockPos($$0, $$2, $$1);
/*    */   }
/*    */   
/*    */   public static BlockPos getLoadedBlockPos(CommandContext<CommandSourceStack> $$0, ServerLevel $$1, String $$2) throws CommandSyntaxException {
/* 40 */     BlockPos $$3 = getBlockPos($$0, $$2);
/* 41 */     if (!$$1.hasChunkAt($$3)) {
/* 42 */       throw ERROR_NOT_LOADED.create();
/*    */     }
/* 44 */     if (!$$1.isInWorldBounds($$3)) {
/* 45 */       throw ERROR_OUT_OF_WORLD.create();
/*    */     }
/* 47 */     return $$3;
/*    */   }
/*    */   
/*    */   public static BlockPos getBlockPos(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 51 */     return ((Coordinates)$$0.getArgument($$1, Coordinates.class)).getBlockPos((CommandSourceStack)$$0.getSource());
/*    */   }
/*    */   
/*    */   public static BlockPos getSpawnablePos(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/* 55 */     BlockPos $$2 = getBlockPos($$0, $$1);
/* 56 */     if (!Level.isInSpawnableBounds($$2)) {
/* 57 */       throw ERROR_OUT_OF_BOUNDS.create();
/*    */     }
/* 59 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public Coordinates parse(StringReader $$0) throws CommandSyntaxException {
/* 64 */     if ($$0.canRead() && $$0.peek() == '^') {
/* 65 */       return LocalCoordinates.parse($$0);
/*    */     }
/* 67 */     return WorldCoordinates.parseInt($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 73 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 74 */       Collection<SharedSuggestionProvider.TextCoordinates> $$4; String $$2 = $$1.getRemaining();
/*    */ 
/*    */ 
/*    */       
/* 78 */       if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
/* 79 */         Collection<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
/*    */       } else {
/* 81 */         $$4 = ((SharedSuggestionProvider)$$0.getSource()).getRelevantCoordinates();
/*    */       } 
/*    */       
/* 84 */       return SharedSuggestionProvider.suggestCoordinates($$2, $$4, $$1, Commands.createValidator(this::parse));
/*    */     } 
/* 86 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 92 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\BlockPosArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */