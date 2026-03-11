/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.gson.JsonObject;
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
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class EntityArgument
/*     */   implements ArgumentType<EntitySelector> {
/*  30 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "Player", "0123", "@e", "@e[type=foo]", "dd12be42-52a9-4a91-a8a1-11c01849e498" });
/*  31 */   public static final SimpleCommandExceptionType ERROR_NOT_SINGLE_ENTITY = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.toomany"));
/*  32 */   public static final SimpleCommandExceptionType ERROR_NOT_SINGLE_PLAYER = new SimpleCommandExceptionType((Message)Component.translatable("argument.player.toomany"));
/*  33 */   public static final SimpleCommandExceptionType ERROR_ONLY_PLAYERS_ALLOWED = new SimpleCommandExceptionType((Message)Component.translatable("argument.player.entities"));
/*  34 */   public static final SimpleCommandExceptionType NO_ENTITIES_FOUND = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.notfound.entity"));
/*  35 */   public static final SimpleCommandExceptionType NO_PLAYERS_FOUND = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.notfound.player"));
/*  36 */   public static final SimpleCommandExceptionType ERROR_SELECTORS_NOT_ALLOWED = new SimpleCommandExceptionType((Message)Component.translatable("argument.entity.selector.not_allowed"));
/*     */   
/*     */   final boolean single;
/*     */   final boolean playersOnly;
/*     */   
/*     */   protected EntityArgument(boolean $$0, boolean $$1) {
/*  42 */     this.single = $$0;
/*  43 */     this.playersOnly = $$1;
/*     */   }
/*     */   
/*     */   public static EntityArgument entity() {
/*  47 */     return new EntityArgument(true, false);
/*     */   }
/*     */   
/*     */   public static Entity getEntity(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  51 */     return ((EntitySelector)$$0.getArgument($$1, EntitySelector.class)).findSingleEntity((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static EntityArgument entities() {
/*  55 */     return new EntityArgument(false, false);
/*     */   }
/*     */   
/*     */   public static Collection<? extends Entity> getEntities(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  59 */     Collection<? extends Entity> $$2 = getOptionalEntities($$0, $$1);
/*  60 */     if ($$2.isEmpty()) {
/*  61 */       throw NO_ENTITIES_FOUND.create();
/*     */     }
/*  63 */     return $$2;
/*     */   }
/*     */   
/*     */   public static Collection<? extends Entity> getOptionalEntities(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  67 */     return ((EntitySelector)$$0.getArgument($$1, EntitySelector.class)).findEntities((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static Collection<ServerPlayer> getOptionalPlayers(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  71 */     return ((EntitySelector)$$0.getArgument($$1, EntitySelector.class)).findPlayers((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static EntityArgument player() {
/*  75 */     return new EntityArgument(true, true);
/*     */   }
/*     */   
/*     */   public static ServerPlayer getPlayer(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  79 */     return ((EntitySelector)$$0.getArgument($$1, EntitySelector.class)).findSinglePlayer((CommandSourceStack)$$0.getSource());
/*     */   }
/*     */   
/*     */   public static EntityArgument players() {
/*  83 */     return new EntityArgument(false, true);
/*     */   }
/*     */   
/*     */   public static Collection<ServerPlayer> getPlayers(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  87 */     List<ServerPlayer> $$2 = ((EntitySelector)$$0.getArgument($$1, EntitySelector.class)).findPlayers((CommandSourceStack)$$0.getSource());
/*  88 */     if ($$2.isEmpty()) {
/*  89 */       throw NO_PLAYERS_FOUND.create();
/*     */     }
/*  91 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntitySelector parse(StringReader $$0) throws CommandSyntaxException {
/*  96 */     int $$1 = 0;
/*  97 */     EntitySelectorParser $$2 = new EntitySelectorParser($$0);
/*  98 */     EntitySelector $$3 = $$2.parse();
/*  99 */     if ($$3.getMaxResults() > 1 && this.single) {
/* 100 */       if (this.playersOnly) {
/* 101 */         $$0.setCursor(0);
/* 102 */         throw ERROR_NOT_SINGLE_PLAYER.createWithContext($$0);
/*     */       } 
/* 104 */       $$0.setCursor(0);
/* 105 */       throw ERROR_NOT_SINGLE_ENTITY.createWithContext($$0);
/*     */     } 
/*     */     
/* 108 */     if ($$3.includesEntities() && this.playersOnly && !$$3.isSelfSelector()) {
/* 109 */       $$0.setCursor(0);
/* 110 */       throw ERROR_ONLY_PLAYERS_ALLOWED.createWithContext($$0);
/*     */     } 
/*     */     
/* 113 */     return $$3;
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/* 118 */     Object object = $$0.getSource(); if (object instanceof SharedSuggestionProvider) { SharedSuggestionProvider $$2 = (SharedSuggestionProvider)object;
/* 119 */       StringReader $$3 = new StringReader($$1.getInput());
/* 120 */       $$3.setCursor($$1.getStart());
/* 121 */       EntitySelectorParser $$4 = new EntitySelectorParser($$3, $$2.hasPermission(2));
/*     */       try {
/* 123 */         $$4.parse();
/* 124 */       } catch (CommandSyntaxException commandSyntaxException) {}
/*     */       
/* 126 */       return $$4.fillSuggestions($$1, $$1 -> {
/*     */             Collection<String> $$2 = $$0.getOnlinePlayerNames();
/*     */             Iterable<String> $$3 = this.playersOnly ? $$2 : Iterables.concat($$2, $$0.getSelectedEntities());
/*     */             SharedSuggestionProvider.suggest($$3, $$1);
/*     */           }); }
/*     */     
/* 132 */     return Suggestions.empty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 138 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info implements ArgumentTypeInfo<EntityArgument, Info.Template> {
/*     */     private static final byte FLAG_SINGLE = 1;
/*     */     private static final byte FLAG_PLAYERS_ONLY = 2;
/*     */     
/*     */     public final class Template implements ArgumentTypeInfo.Template<EntityArgument> {
/*     */       final boolean single;
/*     */       final boolean playersOnly;
/*     */       
/*     */       Template(boolean $$1, boolean $$2) {
/* 150 */         this.single = $$1;
/* 151 */         this.playersOnly = $$2;
/*     */       }
/*     */ 
/*     */       
/*     */       public EntityArgument instantiate(CommandBuildContext $$0) {
/* 156 */         return new EntityArgument(this.single, this.playersOnly);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<EntityArgument, ?> type() {
/* 161 */         return EntityArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 167 */       int $$2 = 0;
/* 168 */       if ($$0.single) {
/* 169 */         $$2 |= 0x1;
/*     */       }
/* 171 */       if ($$0.playersOnly) {
/* 172 */         $$2 |= 0x2;
/*     */       }
/* 174 */       $$1.writeByte($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 179 */       byte $$1 = $$0.readByte();
/* 180 */       return new Template((($$1 & 0x1) != 0), (($$1 & 0x2) != 0));
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 185 */       $$1.addProperty("amount", $$0.single ? "single" : "multiple");
/* 186 */       $$1.addProperty("type", $$0.playersOnly ? "players" : "entities");
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(EntityArgument $$0) {
/* 191 */       return new Template($$0.single, $$0.playersOnly);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<EntityArgument> {
/*     */     final boolean single;
/*     */     final boolean playersOnly;
/*     */     
/*     */     Template(boolean $$1, boolean $$2) {
/*     */       this.single = $$1;
/*     */       this.playersOnly = $$2;
/*     */     }
/*     */     
/*     */     public EntityArgument instantiate(CommandBuildContext $$0) {
/*     */       return new EntityArgument(this.single, this.playersOnly);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<EntityArgument, ?> type() {
/*     */       return EntityArgument.Info.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\EntityArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */