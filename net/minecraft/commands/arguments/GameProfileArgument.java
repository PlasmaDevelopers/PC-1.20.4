/*     */ package net.minecraft.commands.arguments;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ 
/*     */ public class GameProfileArgument implements ArgumentType<GameProfileArgument.Result> {
/*  27 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e" });
/*  28 */   public static final SimpleCommandExceptionType ERROR_UNKNOWN_PLAYER = new SimpleCommandExceptionType((Message)Component.translatable("argument.player.unknown"));
/*     */   
/*     */   public static Collection<GameProfile> getGameProfiles(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  31 */     return ((Result)$$0.getArgument($$1, Result.class)).getNames((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static GameProfileArgument gameProfile() {
/*  35 */     return new GameProfileArgument();
/*     */   }
/*     */ 
/*     */   
/*     */   public Result parse(StringReader $$0) throws CommandSyntaxException {
/*  40 */     if ($$0.canRead() && $$0.peek() == '@') {
/*  41 */       EntitySelectorParser $$1 = new EntitySelectorParser($$0);
/*  42 */       EntitySelector $$2 = $$1.parse();
/*  43 */       if ($$2.includesEntities()) {
/*  44 */         throw EntityArgument.ERROR_ONLY_PLAYERS_ALLOWED.create();
/*     */       }
/*  46 */       return new SelectorResult($$2);
/*     */     } 
/*     */     
/*  49 */     int $$3 = $$0.getCursor();
/*  50 */     while ($$0.canRead() && $$0.peek() != ' ') {
/*  51 */       $$0.skip();
/*     */     }
/*  53 */     String $$4 = $$0.getString().substring($$3, $$0.getCursor());
/*  54 */     return $$1 -> {
/*     */         Optional<GameProfile> $$2 = $$1.getServer().getProfileCache().get($$0);
/*     */         Objects.requireNonNull(ERROR_UNKNOWN_PLAYER);
/*     */         return Collections.singleton($$2.<Throwable>orElseThrow(ERROR_UNKNOWN_PLAYER::create));
/*     */       };
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Result {
/*     */     Collection<GameProfile> getNames(CommandSourceStack param1CommandSourceStack) throws CommandSyntaxException; }
/*     */   
/*     */   public static class SelectorResult implements Result {
/*     */     private final EntitySelector selector;
/*     */     
/*     */     public SelectorResult(EntitySelector $$0) {
/*  69 */       this.selector = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<GameProfile> getNames(CommandSourceStack $$0) throws CommandSyntaxException {
/*  74 */       List<ServerPlayer> $$1 = this.selector.findPlayers($$0);
/*  75 */       if ($$1.isEmpty()) {
/*  76 */         throw EntityArgument.NO_PLAYERS_FOUND.create();
/*     */       }
/*  78 */       List<GameProfile> $$2 = Lists.newArrayList();
/*  79 */       for (ServerPlayer $$3 : $$1) {
/*  80 */         $$2.add($$3.getGameProfile());
/*     */       }
/*  82 */       return $$2;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/*  88 */     if ($$0.getSource() instanceof SharedSuggestionProvider) {
/*  89 */       StringReader $$2 = new StringReader($$1.getInput());
/*  90 */       $$2.setCursor($$1.getStart());
/*  91 */       EntitySelectorParser $$3 = new EntitySelectorParser($$2);
/*     */       try {
/*  93 */         $$3.parse();
/*  94 */       } catch (CommandSyntaxException commandSyntaxException) {}
/*     */       
/*  96 */       return $$3.fillSuggestions($$1, $$1 -> SharedSuggestionProvider.suggest(((SharedSuggestionProvider)$$0.getSource()).getOnlinePlayerNames(), $$1));
/*     */     } 
/*  98 */     return Suggestions.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 104 */     return EXAMPLES;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\GameProfileArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */