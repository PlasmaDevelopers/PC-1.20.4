/*     */ package net.minecraft.server.commands;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.advancements.Advancement;
/*     */ import net.minecraft.advancements.AdvancementHolder;
/*     */ import net.minecraft.advancements.AdvancementNode;
/*     */ import net.minecraft.advancements.AdvancementProgress;
/*     */ import net.minecraft.advancements.AdvancementTree;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ 
/*     */ public class AdvancementCommands {
/*     */   private static final DynamicCommandExceptionType ERROR_NO_ACTION_PERFORMED;
/*     */   private static final Dynamic2CommandExceptionType ERROR_CRITERION_NOT_FOUND;
/*     */   private static final SuggestionProvider<CommandSourceStack> SUGGEST_ADVANCEMENTS;
/*     */   
/*     */   static {
/*  34 */     ERROR_NO_ACTION_PERFORMED = new DynamicCommandExceptionType($$0 -> (Message)$$0);
/*  35 */     ERROR_CRITERION_NOT_FOUND = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatable("commands.advancement.criterionNotFound", new Object[] { $$0, $$1 }));
/*     */     
/*  37 */     SUGGEST_ADVANCEMENTS = (($$0, $$1) -> {
/*     */         Collection<AdvancementHolder> $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getAdvancements().getAllAdvancements();
/*     */         return SharedSuggestionProvider.suggestResource($$2.stream().map(AdvancementHolder::id), $$1);
/*     */       });
/*     */   }
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  43 */     $$0.register(
/*  44 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("advancement")
/*  45 */         .requires($$0 -> $$0.hasPermission(2)))
/*  46 */         .then(
/*  47 */           Commands.literal("grant")
/*  48 */           .then((
/*  49 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  50 */             .then(
/*  51 */               Commands.literal("only")
/*  52 */               .then((
/*  53 */                 (RequiredArgumentBuilder)Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/*  54 */                 .suggests(SUGGEST_ADVANCEMENTS)
/*  55 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.ONLY))))
/*  56 */                 .then(
/*  57 */                   Commands.argument("criterion", (ArgumentType)StringArgumentType.greedyString())
/*  58 */                   .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(ResourceLocationArgument.getAdvancement($$0, "advancement").value().criteria().keySet(), $$1))
/*  59 */                   .executes($$0 -> performCriterion((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, ResourceLocationArgument.getAdvancement($$0, "advancement"), StringArgumentType.getString($$0, "criterion")))))))
/*     */ 
/*     */ 
/*     */             
/*  63 */             .then(
/*  64 */               Commands.literal("from")
/*  65 */               .then(
/*  66 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/*  67 */                 .suggests(SUGGEST_ADVANCEMENTS)
/*  68 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.FROM))))))
/*     */ 
/*     */             
/*  71 */             .then(
/*  72 */               Commands.literal("until")
/*  73 */               .then(
/*  74 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/*  75 */                 .suggests(SUGGEST_ADVANCEMENTS)
/*  76 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.UNTIL))))))
/*     */ 
/*     */             
/*  79 */             .then(
/*  80 */               Commands.literal("through")
/*  81 */               .then(
/*  82 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/*  83 */                 .suggests(SUGGEST_ADVANCEMENTS)
/*  84 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.THROUGH))))))
/*     */ 
/*     */             
/*  87 */             .then(
/*  88 */               Commands.literal("everything")
/*  89 */               .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.GRANT, ((CommandSourceStack)$$0.getSource()).getServer().getAdvancements().getAllAdvancements()))))))
/*     */ 
/*     */ 
/*     */         
/*  93 */         .then(
/*  94 */           Commands.literal("revoke")
/*  95 */           .then((
/*  96 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  97 */             .then(
/*  98 */               Commands.literal("only")
/*  99 */               .then((
/* 100 */                 (RequiredArgumentBuilder)Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/* 101 */                 .suggests(SUGGEST_ADVANCEMENTS)
/* 102 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.ONLY))))
/* 103 */                 .then(
/* 104 */                   Commands.argument("criterion", (ArgumentType)StringArgumentType.greedyString())
/* 105 */                   .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(ResourceLocationArgument.getAdvancement($$0, "advancement").value().criteria().keySet(), $$1))
/* 106 */                   .executes($$0 -> performCriterion((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, ResourceLocationArgument.getAdvancement($$0, "advancement"), StringArgumentType.getString($$0, "criterion")))))))
/*     */ 
/*     */ 
/*     */             
/* 110 */             .then(
/* 111 */               Commands.literal("from")
/* 112 */               .then(
/* 113 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/* 114 */                 .suggests(SUGGEST_ADVANCEMENTS)
/* 115 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.FROM))))))
/*     */ 
/*     */             
/* 118 */             .then(
/* 119 */               Commands.literal("until")
/* 120 */               .then(
/* 121 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/* 122 */                 .suggests(SUGGEST_ADVANCEMENTS)
/* 123 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.UNTIL))))))
/*     */ 
/*     */             
/* 126 */             .then(
/* 127 */               Commands.literal("through")
/* 128 */               .then(
/* 129 */                 Commands.argument("advancement", (ArgumentType)ResourceLocationArgument.id())
/* 130 */                 .suggests(SUGGEST_ADVANCEMENTS)
/* 131 */                 .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, getAdvancements($$0, ResourceLocationArgument.getAdvancement($$0, "advancement"), Mode.THROUGH))))))
/*     */ 
/*     */             
/* 134 */             .then(
/* 135 */               Commands.literal("everything")
/* 136 */               .executes($$0 -> perform((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Action.REVOKE, ((CommandSourceStack)$$0.getSource()).getServer().getAdvancements().getAllAdvancements()))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int perform(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Action $$2, Collection<AdvancementHolder> $$3) throws CommandSyntaxException {
/* 144 */     int $$4 = 0;
/* 145 */     for (ServerPlayer $$5 : $$1) {
/* 146 */       $$4 += $$2.perform($$5, $$3);
/*     */     }
/*     */     
/* 149 */     if ($$4 == 0) {
/* 150 */       if ($$3.size() == 1) {
/* 151 */         if ($$1.size() == 1) {
/* 152 */           throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".one.to.one.failure", new Object[] { Advancement.name((AdvancementHolder)$$3.iterator().next()), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }));
/*     */         }
/* 154 */         throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".one.to.many.failure", new Object[] { Advancement.name((AdvancementHolder)$$3.iterator().next()), Integer.valueOf($$1.size()) }));
/*     */       } 
/*     */       
/* 157 */       if ($$1.size() == 1) {
/* 158 */         throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".many.to.one.failure", new Object[] { Integer.valueOf($$3.size()), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }));
/*     */       }
/* 160 */       throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".many.to.many.failure", new Object[] { Integer.valueOf($$3.size()), Integer.valueOf($$1.size()) }));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 165 */     if ($$3.size() == 1) {
/* 166 */       if ($$1.size() == 1) {
/* 167 */         $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".one.to.one.success", new Object[] { Advancement.name($$1.iterator().next()), ((ServerPlayer)$$2.iterator().next()).getDisplayName() }), true);
/*     */       } else {
/* 169 */         $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".one.to.many.success", new Object[] { Advancement.name($$1.iterator().next()), Integer.valueOf($$2.size()) }), true);
/*     */       }
/*     */     
/* 172 */     } else if ($$1.size() == 1) {
/* 173 */       $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".many.to.one.success", new Object[] { Integer.valueOf($$1.size()), ((ServerPlayer)$$2.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 175 */       $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".many.to.many.success", new Object[] { Integer.valueOf($$1.size()), Integer.valueOf($$2.size()) }), true);
/*     */     } 
/*     */ 
/*     */     
/* 179 */     return $$4;
/*     */   }
/*     */   
/*     */   private static int performCriterion(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Action $$2, AdvancementHolder $$3, String $$4) throws CommandSyntaxException {
/* 183 */     int $$5 = 0;
/*     */     
/* 185 */     Advancement $$6 = $$3.value();
/* 186 */     if (!$$6.criteria().containsKey($$4)) {
/* 187 */       throw ERROR_CRITERION_NOT_FOUND.create(Advancement.name($$3), $$4);
/*     */     }
/*     */     
/* 190 */     for (ServerPlayer $$7 : $$1) {
/* 191 */       if ($$2.performCriterion($$7, $$3, $$4)) {
/* 192 */         $$5++;
/*     */       }
/*     */     } 
/*     */     
/* 196 */     if ($$5 == 0) {
/* 197 */       if ($$1.size() == 1) {
/* 198 */         throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".criterion.to.one.failure", new Object[] { $$4, Advancement.name($$3), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }));
/*     */       }
/* 200 */       throw ERROR_NO_ACTION_PERFORMED.create(Component.translatable($$2.getKey() + ".criterion.to.many.failure", new Object[] { $$4, Advancement.name($$3), Integer.valueOf($$1.size()) }));
/*     */     } 
/*     */ 
/*     */     
/* 204 */     if ($$1.size() == 1) {
/* 205 */       $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".criterion.to.one.success", new Object[] { $$1, Advancement.name($$2), ((ServerPlayer)$$3.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 207 */       $$0.sendSuccess(() -> Component.translatable($$0.getKey() + ".criterion.to.many.success", new Object[] { $$1, Advancement.name($$2), Integer.valueOf($$3.size()) }), true);
/*     */     } 
/*     */     
/* 210 */     return $$5;
/*     */   }
/*     */   
/*     */   private static List<AdvancementHolder> getAdvancements(CommandContext<CommandSourceStack> $$0, AdvancementHolder $$1, Mode $$2) {
/* 214 */     AdvancementTree $$3 = ((CommandSourceStack)$$0.getSource()).getServer().getAdvancements().tree();
/* 215 */     AdvancementNode $$4 = $$3.get($$1);
/* 216 */     if ($$4 == null) {
/* 217 */       return List.of($$1);
/*     */     }
/* 219 */     List<AdvancementHolder> $$5 = new ArrayList<>();
/* 220 */     if ($$2.parents) {
/* 221 */       AdvancementNode $$6 = $$4.parent();
/* 222 */       while ($$6 != null) {
/* 223 */         $$5.add($$6.holder());
/* 224 */         $$6 = $$6.parent();
/*     */       } 
/*     */     } 
/* 227 */     $$5.add($$1);
/* 228 */     if ($$2.children) {
/* 229 */       addChildren($$4, $$5);
/*     */     }
/* 231 */     return $$5;
/*     */   }
/*     */   
/*     */   private static void addChildren(AdvancementNode $$0, List<AdvancementHolder> $$1) {
/* 235 */     for (AdvancementNode $$2 : $$0.children()) {
/* 236 */       $$1.add($$2.holder());
/* 237 */       addChildren($$2, $$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private enum Action {
/* 242 */     GRANT("grant")
/*     */     {
/*     */       protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) {
/* 245 */         AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1);
/* 246 */         if ($$2.isDone()) {
/* 247 */           return false;
/*     */         }
/* 249 */         for (String $$3 : $$2.getRemainingCriteria()) {
/* 250 */           $$0.getAdvancements().award($$1, $$3);
/*     */         }
/* 252 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) {
/* 257 */         return $$0.getAdvancements().award($$1, $$2);
/*     */       }
/*     */     },
/* 260 */     REVOKE("revoke")
/*     */     {
/*     */       protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) {
/* 263 */         AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1);
/* 264 */         if (!$$2.hasProgress()) {
/* 265 */           return false;
/*     */         }
/* 267 */         for (String $$3 : $$2.getCompletedCriteria()) {
/* 268 */           $$0.getAdvancements().revoke($$1, $$3);
/*     */         }
/* 270 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) {
/* 275 */         return $$0.getAdvancements().revoke($$1, $$2);
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final String key;
/*     */     
/*     */     Action(String $$0) {
/* 283 */       this.key = "commands.advancement." + $$0;
/*     */     }
/*     */     
/*     */     public int perform(ServerPlayer $$0, Iterable<AdvancementHolder> $$1) {
/* 287 */       int $$2 = 0;
/* 288 */       for (AdvancementHolder $$3 : $$1) {
/* 289 */         if (perform($$0, $$3)) {
/* 290 */           $$2++;
/*     */         }
/*     */       } 
/* 293 */       return $$2;
/*     */     }
/*     */     
/*     */     protected abstract boolean perform(ServerPlayer param1ServerPlayer, AdvancementHolder param1AdvancementHolder);
/*     */     
/*     */     protected abstract boolean performCriterion(ServerPlayer param1ServerPlayer, AdvancementHolder param1AdvancementHolder, String param1String);
/*     */     
/*     */     protected String getKey() {
/* 301 */       return this.key;
/*     */     }
/*     */   } enum null { protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) { AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1); if ($$2.isDone()) return false;  for (String $$3 : $$2.getRemainingCriteria()) $$0.getAdvancements().award($$1, $$3);  return true; } protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) { return $$0.getAdvancements().award($$1, $$2); } } enum null {
/*     */     protected boolean perform(ServerPlayer $$0, AdvancementHolder $$1) { AdvancementProgress $$2 = $$0.getAdvancements().getOrStartProgress($$1); if (!$$2.hasProgress()) return false;  for (String $$3 : $$2.getCompletedCriteria()) $$0.getAdvancements().revoke($$1, $$3);  return true; } protected boolean performCriterion(ServerPlayer $$0, AdvancementHolder $$1, String $$2) { return $$0.getAdvancements().revoke($$1, $$2); }
/*     */   } private enum Mode {
/* 306 */     ONLY(false, false),
/* 307 */     THROUGH(true, true),
/* 308 */     FROM(false, true),
/* 309 */     UNTIL(true, false),
/* 310 */     EVERYTHING(true, true);
/*     */     
/*     */     final boolean parents;
/*     */     
/*     */     final boolean children;
/*     */     
/*     */     Mode(boolean $$0, boolean $$1) {
/* 317 */       this.parents = $$0;
/* 318 */       this.children = $$1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\AdvancementCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */