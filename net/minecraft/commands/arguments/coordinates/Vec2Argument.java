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
/*    */ import net.minecraft.world.phys.Vec2;
/*    */ import net.minecraft.world.phys.Vec3;
/*    */ 
/*    */ public class Vec2Argument implements ArgumentType<Coordinates> {
/* 23 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0 0", "~ ~", "0.1 -0.5", "~1 ~-2" });
/* 24 */   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.translatable("argument.pos2d.incomplete"));
/*    */   
/*    */   private final boolean centerCorrect;
/*    */   
/*    */   public Vec2Argument(boolean $$0) {
/* 29 */     this.centerCorrect = $$0;
/*    */   }
/*    */   
/*    */   public static Vec2Argument vec2() {
/* 33 */     return new Vec2Argument(true);
/*    */   }
/*    */   
/*    */   public static Vec2Argument vec2(boolean $$0) {
/* 37 */     return new Vec2Argument($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Vec2 getVec2(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 42 */     Vec3 $$2 = ((Coordinates)$$0.getArgument($$1, Coordinates.class)).getPosition((CommandSourceStack)$$0.getSource());
/* 43 */     return new Vec2((float)$$2.x, (float)$$2.z);
/*    */   }
/*    */ 
/*    */   
/*    */   public Coordinates parse(StringReader $$0) throws CommandSyntaxException {
/* 48 */     int $$1 = $$0.getCursor();
/* 49 */     if (!$$0.canRead()) {
/* 50 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     }
/* 52 */     WorldCoordinate $$2 = WorldCoordinate.parseDouble($$0, this.centerCorrect);
/* 53 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/* 54 */       $$0.setCursor($$1);
/* 55 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     } 
/* 57 */     $$0.skip();
/* 58 */     WorldCoordinate $$3 = WorldCoordinate.parseDouble($$0, this.centerCorrect);
/* 59 */     return new WorldCoordinates($$2, new WorldCoordinate(true, 0.0D), $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 64 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/* 65 */       Collection<SharedSuggestionProvider.TextCoordinates> $$4; String $$2 = $$1.getRemaining();
/*    */ 
/*    */ 
/*    */       
/* 69 */       if (!$$2.isEmpty() && $$2.charAt(0) == '^') {
/* 70 */         Collection<SharedSuggestionProvider.TextCoordinates> $$3 = Collections.singleton(SharedSuggestionProvider.TextCoordinates.DEFAULT_LOCAL);
/*    */       } else {
/* 72 */         $$4 = ((SharedSuggestionProvider)$$0.getSource()).getAbsoluteCoordinates();
/*    */       } 
/*    */       
/* 75 */       return SharedSuggestionProvider.suggest2DCoordinates($$2, $$4, $$1, Commands.createValidator(this::parse));
/*    */     } 
/* 77 */     return Suggestions.empty();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 83 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\Vec2Argument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */