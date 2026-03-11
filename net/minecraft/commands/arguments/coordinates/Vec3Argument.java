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
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class Vec3Argument implements ArgumentType<Coordinates> {
/* 22 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0 0 0", "~ ~ ~", "^ ^ ^", "^1 ^ ^-5", "0.1 -0.5 .9", "~0.5 ~1 ~-5" });
/*    */   
/* 24 */   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos3d.incomplete"));
/* 25 */   public static final SimpleCommandExceptionType ERROR_MIXED_TYPE = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos.mixed"));
/*    */   
/*    */   private final boolean centerCorrect;
/*    */   
/*    */   public Vec3Argument(boolean $$0) {
/* 30 */     this.centerCorrect = $$0;
/*    */   }
/*    */   
/*    */   public static Vec3Argument vec3() {
/* 34 */     return new Vec3Argument(true);
/*    */   }
/*    */   
/*    */   public static Vec3Argument vec3(boolean $$0) {
/* 38 */     return new Vec3Argument($$0);
/*    */   }
/*    */   
/*    */   public static Vec3 getVec3(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 42 */     return ((Coordinates)$$0.getArgument($$1, Coordinates.class)).getPosition((CommandSourceStack)$$0.getSource());
/*    */   }
/*    */   
/*    */   public static Coordinates getCoordinates(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 46 */     return (Coordinates)$$0.getArgument($$1, Coordinates.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Coordinates parse(StringReader $$0) throws CommandSyntaxException {
/* 51 */     if ($$0.canRead() && $$0.peek() == '^') {
/* 52 */       return LocalCoordinates.parse($$0);
/*    */     }
/* 54 */     return WorldCoordinates.parseDouble($$0, this.centerCorrect);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 60 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 61 */       Collection<SharedSuggestionProvider.TextCoordinates> $$4; String $$2 = $$1.getRemaining();
/*    */ 
/*    */ 
/*    */       
/* 65 */       if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
/* 66 */         Collection<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
/*    */       } else {
/* 68 */         $$4 = ((SharedSuggestionProvider)$$0.getSource()).getAbsoluteCoordinates();
/*    */       } 
/*    */       
/* 71 */       return SharedSuggestionProvider.suggestCoordinates($$2, $$4, $$1, Commands.createValidator(this::parse));
/*    */     } 
/* 73 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 79 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\Vec3Argument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */