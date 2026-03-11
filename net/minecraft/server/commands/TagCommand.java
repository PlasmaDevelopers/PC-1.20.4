/*     */ package net.minecraft.server.commands;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.arguments.StringArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class TagCommand {
/*  25 */   private static final SimpleCommandExceptionType ERROR_ADD_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.tag.add.failed"));
/*  26 */   private static final SimpleCommandExceptionType ERROR_REMOVE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.tag.remove.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  29 */     $$0.register(
/*  30 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tag")
/*  31 */         .requires($$0 -> $$0.hasPermission(2)))
/*  32 */         .then((
/*  33 */           (RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.entities())
/*  34 */           .then(
/*  35 */             Commands.literal("add")
/*  36 */             .then(
/*  37 */               Commands.argument("name", (ArgumentType)StringArgumentType.word())
/*  38 */               .executes($$0 -> addTag((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), StringArgumentType.getString($$0, "name"))))))
/*     */ 
/*     */           
/*  41 */           .then(
/*  42 */             Commands.literal("remove")
/*  43 */             .then(
/*  44 */               Commands.argument("name", (ArgumentType)StringArgumentType.word())
/*  45 */               .suggests(($$0, $$1) -> SharedSuggestionProvider.suggest(getTags(EntityArgument.getEntities($$0, "targets")), $$1))
/*  46 */               .executes($$0 -> removeTag((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"), StringArgumentType.getString($$0, "name"))))))
/*     */ 
/*     */           
/*  49 */           .then(
/*  50 */             Commands.literal("list")
/*  51 */             .executes($$0 -> listTags((CommandSourceStack)$$0.getSource(), EntityArgument.getEntities($$0, "targets"))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Collection<String> getTags(Collection<? extends Entity> $$0) {
/*  58 */     Set<String> $$1 = Sets.newHashSet();
/*  59 */     for (Entity $$2 : $$0) {
/*  60 */       $$1.addAll($$2.getTags());
/*     */     }
/*  62 */     return $$1;
/*     */   }
/*     */   
/*     */   private static int addTag(CommandSourceStack $$0, Collection<? extends Entity> $$1, String $$2) throws CommandSyntaxException {
/*  66 */     int $$3 = 0;
/*     */     
/*  68 */     for (Entity $$4 : $$1) {
/*  69 */       if ($$4.addTag($$2)) {
/*  70 */         $$3++;
/*     */       }
/*     */     } 
/*     */     
/*  74 */     if ($$3 == 0) {
/*  75 */       throw ERROR_ADD_FAILED.create();
/*     */     }
/*     */     
/*  78 */     if ($$1.size() == 1) {
/*  79 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.add.success.single", new Object[] { $$0, ((Entity)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/*  81 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.add.success.multiple", new Object[] { $$0, Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/*  84 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int removeTag(CommandSourceStack $$0, Collection<? extends Entity> $$1, String $$2) throws CommandSyntaxException {
/*  88 */     int $$3 = 0;
/*     */     
/*  90 */     for (Entity $$4 : $$1) {
/*  91 */       if ($$4.removeTag($$2)) {
/*  92 */         $$3++;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     if ($$3 == 0) {
/*  97 */       throw ERROR_REMOVE_FAILED.create();
/*     */     }
/*     */     
/* 100 */     if ($$1.size() == 1) {
/* 101 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.remove.success.single", new Object[] { $$0, ((Entity)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/* 103 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.remove.success.multiple", new Object[] { $$0, Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 106 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int listTags(CommandSourceStack $$0, Collection<? extends Entity> $$1) {
/* 110 */     Set<String> $$2 = Sets.newHashSet();
/*     */     
/* 112 */     for (Entity $$3 : $$1) {
/* 113 */       $$2.addAll($$3.getTags());
/*     */     }
/*     */     
/* 116 */     if ($$1.size() == 1) {
/* 117 */       Entity $$4 = $$1.iterator().next();
/*     */       
/* 119 */       if ($$2.isEmpty()) {
/* 120 */         $$0.sendSuccess(() -> Component.translatable("commands.tag.list.single.empty", new Object[] { $$0.getDisplayName() }), false);
/*     */       } else {
/* 122 */         $$0.sendSuccess(() -> Component.translatable("commands.tag.list.single.success", new Object[] { $$0.getDisplayName(), Integer.valueOf($$1.size()), ComponentUtils.formatList($$1) }), false);
/*     */       }
/*     */     
/* 125 */     } else if ($$2.isEmpty()) {
/* 126 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.list.multiple.empty", new Object[] { Integer.valueOf($$0.size()) }), false);
/*     */     } else {
/* 128 */       $$0.sendSuccess(() -> Component.translatable("commands.tag.list.multiple.success", new Object[] { Integer.valueOf($$0.size()), Integer.valueOf($$1.size()), ComponentUtils.formatList($$1) }), false);
/*     */     } 
/*     */ 
/*     */     
/* 132 */     return $$2.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\TagCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */