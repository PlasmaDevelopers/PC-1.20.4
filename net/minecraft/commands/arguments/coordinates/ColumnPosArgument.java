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
/*    */ import net.minecraft.server.level.ColumnPos;
/*    */ 
/*    */ public class ColumnPosArgument implements ArgumentType<Coordinates> {
/* 23 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0 0", "~ ~", "~1 ~-2", "^ ^", "^-1 ^0" });
/* 24 */   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos2d.incomplete"));
/*    */   
/*    */   public static ColumnPosArgument columnPos() {
/* 27 */     return new ColumnPosArgument();
/*    */   }
/*    */   
/*    */   public static ColumnPos getColumnPos(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 31 */     BlockPos $$2 = ((Coordinates)$$0.getArgument($$1, Coordinates.class)).getBlockPos((CommandSourceStack)$$0.getSource());
/* 32 */     return new ColumnPos($$2.getX(), $$2.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   public Coordinates parse(StringReader $$0) throws CommandSyntaxException {
/* 37 */     int $$1 = $$0.getCursor();
/* 38 */     if (!$$0.canRead()) {
/* 39 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     }
/* 41 */     WorldCoordinate $$2 = WorldCoordinate.parseInt($$0);
/* 42 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/* 43 */       $$0.setCursor($$1);
/* 44 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     } 
/* 46 */     $$0.skip();
/* 47 */     WorldCoordinate $$3 = WorldCoordinate.parseInt($$0);
/* 48 */     return new WorldCoordinates($$2, new WorldCoordinate(true, 0.0D), $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 53 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 54 */       Collection<SharedSuggestionProvider.TextCoordinates> $$4; String $$2 = $$1.getRemaining();
/*    */ 
/*    */ 
/*    */       
/* 58 */       if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
/* 59 */         Collection<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
/*    */       } else {
/* 61 */         $$4 = ((SharedSuggestionProvider)$$0.getSource()).getRelevantCoordinates();
/*    */       } 
/*    */       
/* 64 */       return SharedSuggestionProvider.suggest2DCoordinates($$2, $$4, $$1, Commands.createValidator(this::parse));
/*    */     } 
/* 66 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 72 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\ColumnPosArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */