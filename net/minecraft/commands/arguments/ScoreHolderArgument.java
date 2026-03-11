/*     */ package net.minecraft.commands.arguments;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Supplier;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.level.ServerLevel;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ 
/*     */ public class ScoreHolderArgument implements ArgumentType<ScoreHolderArgument.Result> {
/*     */   static {
/*  34 */     SUGGEST_SCORE_HOLDERS = (($$0, $$1) -> {
/*     */         StringReader $$2 = new StringReader($$1.getInput());
/*     */         $$2.setCursor($$1.getStart());
/*     */         EntitySelectorParser $$3 = new EntitySelectorParser($$2);
/*     */         try {
/*     */           $$3.parse();
/*  40 */         } catch (CommandSyntaxException commandSyntaxException) {}
/*     */         return $$3.fillSuggestions($$1, ());
/*     */       });
/*     */   }
/*     */   public static final SuggestionProvider<CommandSourceStack> SUGGEST_SCORE_HOLDERS;
/*  45 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "Player", "0123", "*", "@e" });
/*  46 */   private static final SimpleCommandExceptionType ERROR_NO_RESULTS = new SimpleCommandExceptionType((Message)Component.translatable("argument.scoreHolder.empty"));
/*     */   
/*     */   final boolean multiple;
/*     */   
/*     */   public ScoreHolderArgument(boolean $$0) {
/*  51 */     this.multiple = $$0;
/*     */   }
/*     */   
/*     */   public static ScoreHolder getName(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  55 */     return getNames($$0, $$1).iterator().next();
/*     */   }
/*     */   
/*     */   public static Collection<ScoreHolder> getNames(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  59 */     return getNames($$0, $$1, Collections::emptyList);
/*     */   }
/*     */   
/*     */   public static Collection<ScoreHolder> getNamesWithDefaultWildcard(CommandContext<CommandSourceStack> $$0, String $$1) throws CommandSyntaxException {
/*  63 */     Objects.requireNonNull(((CommandSourceStack)$$0.getSource()).getServer().getScoreboard()); return getNames($$0, $$1, ((CommandSourceStack)$$0.getSource()).getServer().getScoreboard()::getTrackedPlayers);
/*     */   }
/*     */   
/*     */   public static Collection<ScoreHolder> getNames(CommandContext<CommandSourceStack> $$0, String $$1, Supplier<Collection<ScoreHolder>> $$2) throws CommandSyntaxException {
/*  67 */     Collection<ScoreHolder> $$3 = ((Result)$$0.getArgument($$1, Result.class)).getNames((CommandSourceStack)$$0.getSource(), $$2);
/*  68 */     if ($$3.isEmpty()) {
/*  69 */       throw EntityArgument.NO_ENTITIES_FOUND.create();
/*     */     }
/*  71 */     return $$3;
/*     */   }
/*     */   
/*     */   public static ScoreHolderArgument scoreHolder() {
/*  75 */     return new ScoreHolderArgument(false);
/*     */   }
/*     */   
/*     */   public static ScoreHolderArgument scoreHolders() {
/*  79 */     return new ScoreHolderArgument(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Result parse(StringReader $$0) throws CommandSyntaxException {
/*  84 */     if ($$0.canRead() && $$0.peek() == '@') {
/*  85 */       EntitySelectorParser $$1 = new EntitySelectorParser($$0);
/*  86 */       EntitySelector $$2 = $$1.parse();
/*  87 */       if (!this.multiple && $$2.getMaxResults() > 1) {
/*  88 */         throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
/*     */       }
/*  90 */       return new SelectorResult($$2);
/*     */     } 
/*  92 */     int $$3 = $$0.getCursor();
/*  93 */     while ($$0.canRead() && $$0.peek() != ' ') {
/*  94 */       $$0.skip();
/*     */     }
/*  96 */     String $$4 = $$0.getString().substring($$3, $$0.getCursor());
/*  97 */     if ($$4.equals("*")) {
/*  98 */       return ($$0, $$1) -> {
/*     */           Collection<ScoreHolder> $$2 = $$1.get();
/*     */           
/*     */           if ($$2.isEmpty()) {
/*     */             throw ERROR_NO_RESULTS.create();
/*     */           }
/*     */           return $$2;
/*     */         };
/*     */     }
/* 107 */     List<ScoreHolder> $$5 = List.of(ScoreHolder.forNameOnly($$4));
/*     */ 
/*     */     
/* 110 */     if ($$4.startsWith("#")) {
/* 111 */       return ($$1, $$2) -> $$0;
/*     */     }
/*     */     
/*     */     try {
/* 115 */       UUID $$6 = UUID.fromString($$4);
/*     */       
/* 117 */       return ($$2, $$3) -> {
/*     */           Entity entity;
/*     */           
/*     */           MinecraftServer $$4 = $$2.getServer();
/*     */           
/*     */           ScoreHolder $$5 = null;
/*     */           
/*     */           List<ScoreHolder> $$6 = null;
/*     */           
/*     */           for (ServerLevel $$7 : $$4.getAllLevels()) {
/*     */             Entity $$8 = $$7.getEntity($$0);
/*     */             
/*     */             if ($$8 != null) {
/*     */               if ($$5 == null) {
/*     */                 entity = $$8;
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               
/*     */               if ($$6 == null) {
/*     */                 $$6 = new ArrayList<>();
/*     */                 
/*     */                 $$6.add(entity);
/*     */               } 
/*     */               $$6.add($$8);
/*     */             } 
/*     */           } 
/*     */           return ($$6 != null) ? $$6 : ((entity != null) ? List.of(entity) : $$1);
/*     */         };
/* 146 */     } catch (IllegalArgumentException illegalArgumentException) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       return ($$2, $$3) -> {
/*     */           MinecraftServer $$4 = $$2.getServer();
/*     */           ServerPlayer $$5 = $$4.getPlayerList().getPlayerByName($$0);
/*     */           return ($$5 != null) ? List.of($$5) : $$1;
/*     */         };
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/* 166 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Result {
/*     */     Collection<ScoreHolder> getNames(CommandSourceStack param1CommandSourceStack, Supplier<Collection<ScoreHolder>> param1Supplier) throws CommandSyntaxException;
/*     */   }
/*     */   
/*     */   public static class SelectorResult implements Result {
/*     */     private final EntitySelector selector;
/*     */     
/*     */     public SelectorResult(EntitySelector $$0) {
/* 178 */       this.selector = $$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public Collection<ScoreHolder> getNames(CommandSourceStack $$0, Supplier<Collection<ScoreHolder>> $$1) throws CommandSyntaxException {
/* 183 */       List<? extends Entity> $$2 = this.selector.findEntities($$0);
/* 184 */       if ($$2.isEmpty()) {
/* 185 */         throw EntityArgument.NO_ENTITIES_FOUND.create();
/*     */       }
/* 187 */       return (Collection)List.copyOf($$2);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Info implements ArgumentTypeInfo<ScoreHolderArgument, Info.Template> {
/*     */     private static final byte FLAG_MULTIPLE = 1;
/*     */     
/*     */     public final class Template implements ArgumentTypeInfo.Template<ScoreHolderArgument> {
/*     */       final boolean multiple;
/*     */       
/*     */       Template(boolean $$1) {
/* 198 */         this.multiple = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public ScoreHolderArgument instantiate(CommandBuildContext $$0) {
/* 203 */         return new ScoreHolderArgument(this.multiple);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<ScoreHolderArgument, ?> type() {
/* 208 */         return ScoreHolderArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 214 */       int $$2 = 0;
/* 215 */       if ($$0.multiple) {
/* 216 */         $$2 |= 0x1;
/*     */       }
/* 218 */       $$1.writeByte($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 223 */       byte $$1 = $$0.readByte();
/* 224 */       boolean $$2 = (($$1 & 0x1) != 0);
/* 225 */       return new Template($$2);
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 230 */       $$1.addProperty("amount", $$0.multiple ? "multiple" : "single");
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(ScoreHolderArgument $$0) {
/* 235 */       return new Template($$0.multiple);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<ScoreHolderArgument> {
/*     */     final boolean multiple;
/*     */     
/*     */     Template(boolean $$1) {
/*     */       this.multiple = $$1;
/*     */     }
/*     */     
/*     */     public ScoreHolderArgument instantiate(CommandBuildContext $$0) {
/*     */       return new ScoreHolderArgument(this.multiple);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<ScoreHolderArgument, ?> type() {
/*     */       return ScoreHolderArgument.Info.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ScoreHolderArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */