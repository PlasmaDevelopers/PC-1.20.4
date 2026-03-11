/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*     */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.Commands;
/*     */ import net.minecraft.commands.arguments.EntityArgument;
/*     */ import net.minecraft.commands.arguments.ResourceLocationArgument;
/*     */ import net.minecraft.commands.synchronization.SuggestionProviders;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.world.item.crafting.RecipeHolder;
/*     */ 
/*     */ public class RecipeCommand
/*     */ {
/*  24 */   private static final SimpleCommandExceptionType ERROR_GIVE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.recipe.give.failed"));
/*  25 */   private static final SimpleCommandExceptionType ERROR_TAKE_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.recipe.take.failed"));
/*     */   
/*     */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/*  28 */     $$0.register(
/*  29 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("recipe")
/*  30 */         .requires($$0 -> $$0.hasPermission(2)))
/*  31 */         .then(
/*  32 */           Commands.literal("give")
/*  33 */           .then((
/*  34 */             (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  35 */             .then(
/*  36 */               Commands.argument("recipe", (ArgumentType)ResourceLocationArgument.id())
/*  37 */               .suggests(SuggestionProviders.ALL_RECIPES)
/*  38 */               .executes($$0 -> giveRecipes((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Collections.singleton(ResourceLocationArgument.getRecipe($$0, "recipe"))))))
/*     */             
/*  40 */             .then(
/*  41 */               Commands.literal("*")
/*  42 */               .executes($$0 -> giveRecipes((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getServer().getRecipeManager().getRecipes()))))))
/*     */ 
/*     */ 
/*     */         
/*  46 */         .then(
/*  47 */           Commands.literal("take")
/*  48 */           .then((
/*  49 */             (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/*  50 */             .then(
/*  51 */               Commands.argument("recipe", (ArgumentType)ResourceLocationArgument.id())
/*  52 */               .suggests(SuggestionProviders.ALL_RECIPES)
/*  53 */               .executes($$0 -> takeRecipes((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), Collections.singleton(ResourceLocationArgument.getRecipe($$0, "recipe"))))))
/*     */             
/*  55 */             .then(
/*  56 */               Commands.literal("*")
/*  57 */               .executes($$0 -> takeRecipes((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ((CommandSourceStack)$$0.getSource()).getServer().getRecipeManager().getRecipes()))))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int giveRecipes(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Collection<RecipeHolder<?>> $$2) throws CommandSyntaxException {
/*  65 */     int $$3 = 0;
/*     */     
/*  67 */     for (ServerPlayer $$4 : $$1) {
/*  68 */       $$3 += $$4.awardRecipes($$2);
/*     */     }
/*     */     
/*  71 */     if ($$3 == 0) {
/*  72 */       throw ERROR_GIVE_FAILED.create();
/*     */     }
/*     */     
/*  75 */     if ($$1.size() == 1) {
/*  76 */       $$0.sendSuccess(() -> Component.translatable("commands.recipe.give.success.single", new Object[] { Integer.valueOf($$0.size()), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/*  78 */       $$0.sendSuccess(() -> Component.translatable("commands.recipe.give.success.multiple", new Object[] { Integer.valueOf($$0.size()), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/*  81 */     return $$3;
/*     */   }
/*     */   
/*     */   private static int takeRecipes(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Collection<RecipeHolder<?>> $$2) throws CommandSyntaxException {
/*  85 */     int $$3 = 0;
/*     */     
/*  87 */     for (ServerPlayer $$4 : $$1) {
/*  88 */       $$3 += $$4.resetRecipes($$2);
/*     */     }
/*     */     
/*  91 */     if ($$3 == 0) {
/*  92 */       throw ERROR_TAKE_FAILED.create();
/*     */     }
/*     */     
/*  95 */     if ($$1.size() == 1) {
/*  96 */       $$0.sendSuccess(() -> Component.translatable("commands.recipe.take.success.single", new Object[] { Integer.valueOf($$0.size()), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*     */     } else {
/*  98 */       $$0.sendSuccess(() -> Component.translatable("commands.recipe.take.success.multiple", new Object[] { Integer.valueOf($$0.size()), Integer.valueOf($$1.size()) }), true);
/*     */     } 
/*     */     
/* 101 */     return $$3;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\RecipeCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */