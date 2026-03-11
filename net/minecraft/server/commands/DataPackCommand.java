/*     */ package net.minecraft.server.commands;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionProvider;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.stream.Collectors;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.server.packs.repository.Pack;
/*     */ import net.minecraft.server.packs.repository.PackRepository;
/*     */ import net.minecraft.world.flag.FeatureFlagSet;
/*     */ import net.minecraft.world.flag.FeatureFlags;
/*     */ 
/*     */ public class DataPackCommand {
/*     */   static {
/*  31 */     ERROR_UNKNOWN_PACK = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.datapack.unknown", new Object[] { $$0 }));
/*  32 */     ERROR_PACK_ALREADY_ENABLED = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.datapack.enable.failed", new Object[] { $$0 }));
/*  33 */     ERROR_PACK_ALREADY_DISABLED = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("commands.datapack.disable.failed", new Object[] { $$0 }));
/*  34 */     ERROR_PACK_FEATURES_NOT_ENABLED = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("commands.datapack.enable.failed.no_flags", new Object[] { $$0, $$1 }));
/*     */     
/*  36 */     SELECTED_PACKS = (($$0, $$1) -> SharedSuggestionProvider.suggest(((CommandSourceStack)$$0.getSource()).getServer().getPackRepository().getSelectedIds().stream().map(StringArgumentType::escapeIfRequired), $$1));
/*  37 */     UNSELECTED_PACKS = (($$0, $$1) -> {
/*     */         PackRepository $$2 = ((CommandSourceStack)$$0.getSource()).getServer().getPackRepository();
/*     */         Collection<String> $$3 = $$2.getSelectedIds();
/*     */         FeatureFlagSet $$4 = ((CommandSourceStack)$$0.getSource()).enabledFeatures();
/*     */         return SharedSuggestionProvider.suggest($$2.getAvailablePacks().stream().filter(()).map(Pack::getId).filter(()).map(StringArgumentType::escapeIfRequired), $$1);
/*     */       });
/*     */   }
/*     */   
/*     */   private static final DynamicCommandExceptionType ERROR_UNKNOWN_PACK;
/*     */   private static final DynamicCommandExceptionType ERROR_PACK_ALREADY_ENABLED;
/*     */   private static final DynamicCommandExceptionType ERROR_PACK_ALREADY_DISABLED;
/*     */   private static final Dynamic2CommandExceptionType ERROR_PACK_FEATURES_NOT_ENABLED;
/*     */   private static final SuggestionProvider<CommandSourceStack> SELECTED_PACKS;
/*     */   private static final SuggestionProvider<CommandSourceStack> UNSELECTED_PACKS;
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  53 */     $$0.register(
/*  54 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("datapack")
/*  55 */         .requires($$0 -> $$0.hasPermission(2)))
/*  56 */         .then(
/*  57 */           Commands.literal("enable")
/*  58 */           .then((
/*  59 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("name", (ArgumentType)StringArgumentType.string())
/*  60 */             .suggests(UNSELECTED_PACKS)
/*  61 */             .executes($$0 -> enablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", true), ())))
/*  62 */             .then(
/*  63 */               Commands.literal("after")
/*  64 */               .then(
/*  65 */                 Commands.argument("existing", (ArgumentType)StringArgumentType.string())
/*  66 */                 .suggests(SELECTED_PACKS)
/*  67 */                 .executes($$0 -> enablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", true), ())))))
/*     */ 
/*     */             
/*  70 */             .then(
/*  71 */               Commands.literal("before")
/*  72 */               .then(
/*  73 */                 Commands.argument("existing", (ArgumentType)StringArgumentType.string())
/*  74 */                 .suggests(SELECTED_PACKS)
/*  75 */                 .executes($$0 -> enablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", true), ())))))
/*     */ 
/*     */             
/*  78 */             .then(
/*  79 */               Commands.literal("last")
/*  80 */               .executes($$0 -> enablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", true), List::add))))
/*     */             
/*  82 */             .then(
/*  83 */               Commands.literal("first")
/*  84 */               .executes($$0 -> enablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", true), ()))))))
/*     */ 
/*     */ 
/*     */         
/*  88 */         .then(
/*  89 */           Commands.literal("disable")
/*  90 */           .then(
/*  91 */             Commands.argument("name", (ArgumentType)StringArgumentType.string())
/*  92 */             .suggests(SELECTED_PACKS)
/*  93 */             .executes($$0 -> disablePack((CommandSourceStack)$$0.getSource(), getPack($$0, "name", false))))))
/*     */ 
/*     */         
/*  96 */         .then((
/*  97 */           (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list")
/*  98 */           .executes($$0 -> listPacks((CommandSourceStack)$$0.getSource())))
/*  99 */           .then(
/* 100 */             Commands.literal("available")
/* 101 */             .executes($$0 -> listAvailablePacks((CommandSourceStack)$$0.getSource()))))
/*     */           
/* 103 */           .then(
/* 104 */             Commands.literal("enabled")
/* 105 */             .executes($$0 -> listEnabledPacks((CommandSourceStack)$$0.getSource())))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int enablePack(CommandSourceStack $$0, Pack $$1, Inserter $$2) throws CommandSyntaxException {
/* 112 */     PackRepository $$3 = $$0.getServer().getPackRepository();
/*     */     
/* 114 */     List<Pack> $$4 = Lists.newArrayList($$3.getSelectedPacks());
/* 115 */     $$2.apply($$4, $$1);
/*     */     
/* 117 */     $$0.sendSuccess(() -> Component.translatable("commands.datapack.modify.enable", new Object[] { $$0.getChatLink(true) }), true);
/* 118 */     ReloadCommand.reloadPacks((Collection<String>)$$4.stream().map(Pack::getId).collect(Collectors.toList()), $$0);
/* 119 */     return $$4.size();
/*     */   }
/*     */   
/*     */   private static int disablePack(CommandSourceStack $$0, Pack $$1) {
/* 123 */     PackRepository $$2 = $$0.getServer().getPackRepository();
/*     */     
/* 125 */     List<Pack> $$3 = Lists.newArrayList($$2.getSelectedPacks());
/* 126 */     $$3.remove($$1);
/*     */     
/* 128 */     $$0.sendSuccess(() -> Component.translatable("commands.datapack.modify.disable", new Object[] { $$0.getChatLink(true) }), true);
/* 129 */     ReloadCommand.reloadPacks((Collection<String>)$$3.stream().map(Pack::getId).collect(Collectors.toList()), $$0);
/* 130 */     return $$3.size();
/*     */   }
/*     */   
/*     */   private static int listPacks(CommandSourceStack $$0) {
/* 134 */     return listEnabledPacks($$0) + listAvailablePacks($$0);
/*     */   }
/*     */   
/*     */   private static int listAvailablePacks(CommandSourceStack $$0) {
/* 138 */     PackRepository $$1 = $$0.getServer().getPackRepository();
/* 139 */     $$1.reload();
/*     */     
/* 141 */     Collection<Pack> $$2 = $$1.getSelectedPacks();
/* 142 */     Collection<Pack> $$3 = $$1.getAvailablePacks();
/* 143 */     FeatureFlagSet $$4 = $$0.enabledFeatures();
/* 144 */     List<Pack> $$5 = $$3.stream().filter($$2 -> (!$$0.contains($$2) && $$2.getRequestedFeatures().isSubsetOf($$1))).toList();
/* 145 */     if ($$5.isEmpty()) {
/* 146 */       $$0.sendSuccess(() -> Component.translatable("commands.datapack.list.available.none"), false);
/*     */     } else {
/* 148 */       $$0.sendSuccess(() -> Component.translatable("commands.datapack.list.available.success", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, ()) }), false);
/*     */     } 
/*     */     
/* 151 */     return $$5.size();
/*     */   }
/*     */   
/*     */   private static int listEnabledPacks(CommandSourceStack $$0) {
/* 155 */     PackRepository $$1 = $$0.getServer().getPackRepository();
/* 156 */     $$1.reload();
/*     */     
/* 158 */     Collection<? extends Pack> $$2 = $$1.getSelectedPacks();
/* 159 */     if ($$2.isEmpty()) {
/* 160 */       $$0.sendSuccess(() -> Component.translatable("commands.datapack.list.enabled.none"), false);
/*     */     } else {
/* 162 */       $$0.sendSuccess(() -> Component.translatable("commands.datapack.list.enabled.success", new Object[] { Integer.valueOf($$0.size()), ComponentUtils.formatList($$0, ()) }), false);
/*     */     } 
/*     */     
/* 165 */     return $$2.size();
/*     */   }
/*     */   
/*     */   private static Pack getPack(CommandContext<CommandSourceStack> $$0, String $$1, boolean $$2) throws CommandSyntaxException {
/* 169 */     String $$3 = StringArgumentType.getString($$0, $$1);
/* 170 */     PackRepository $$4 = ((CommandSourceStack)$$0.getSource()).getServer().getPackRepository();
/* 171 */     Pack $$5 = $$4.getPack($$3);
/* 172 */     if ($$5 == null) {
/* 173 */       throw ERROR_UNKNOWN_PACK.create($$3);
/*     */     }
/* 175 */     boolean $$6 = $$4.getSelectedPacks().contains($$5);
/* 176 */     if ($$2 && $$6) {
/* 177 */       throw ERROR_PACK_ALREADY_ENABLED.create($$3);
/*     */     }
/* 179 */     if (!$$2 && !$$6) {
/* 180 */       throw ERROR_PACK_ALREADY_DISABLED.create($$3);
/*     */     }
/* 182 */     FeatureFlagSet $$7 = ((CommandSourceStack)$$0.getSource()).enabledFeatures();
/* 183 */     FeatureFlagSet $$8 = $$5.getRequestedFeatures();
/* 184 */     if (!$$8.isSubsetOf($$7)) {
/* 185 */       throw ERROR_PACK_FEATURES_NOT_ENABLED.create($$3, FeatureFlags.printMissingFlags($$7, $$8));
/*     */     }
/* 187 */     return $$5;
/*     */   }
/*     */   
/*     */   private static interface Inserter {
/*     */     void apply(List<Pack> param1List, Pack param1Pack) throws CommandSyntaxException;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DataPackCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */