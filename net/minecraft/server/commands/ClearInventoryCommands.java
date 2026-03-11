/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.item.ItemPredicateArgument;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.Container;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ public class ClearInventoryCommands {
/*    */   private static final DynamicCommandExceptionType ERROR_SINGLE;
/*    */   
/*    */   static {
/* 27 */     ERROR_SINGLE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("clear.failed.single", new Object[] { $$0 }));
/* 28 */     ERROR_MULTIPLE = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("clear.failed.multiple", new Object[] { $$0 }));
/*    */   } private static final DynamicCommandExceptionType ERROR_MULTIPLE;
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 31 */     $$0.register(
/* 32 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("clear")
/* 33 */         .requires($$0 -> $$0.hasPermission(2)))
/* 34 */         .executes($$0 -> clearInventory((CommandSourceStack)$$0.getSource(), Collections.singleton(((CommandSourceStack)$$0.getSource()).getPlayerOrException()), (), -1)))
/* 35 */         .then((
/* 36 */           (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 37 */           .executes($$0 -> clearInventory((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), (), -1)))
/* 38 */           .then((
/* 39 */             (RequiredArgumentBuilder)Commands.argument("item", (ArgumentType)ItemPredicateArgument.itemPredicate($$1))
/* 40 */             .executes($$0 -> clearInventory((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ItemPredicateArgument.getItemPredicate($$0, "item"), -1)))
/* 41 */             .then(
/* 42 */               Commands.argument("maxCount", (ArgumentType)IntegerArgumentType.integer(0))
/* 43 */               .executes($$0 -> clearInventory((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), ItemPredicateArgument.getItemPredicate($$0, "item"), IntegerArgumentType.getInteger($$0, "maxCount")))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int clearInventory(CommandSourceStack $$0, Collection<ServerPlayer> $$1, Predicate<ItemStack> $$2, int $$3) throws CommandSyntaxException {
/* 51 */     int $$4 = 0;
/*    */     
/* 53 */     for (ServerPlayer $$5 : $$1) {
/* 54 */       $$4 += $$5.getInventory().clearOrCountMatchingItems($$2, $$3, (Container)$$5.inventoryMenu.getCraftSlots());
/*    */       
/* 56 */       $$5.containerMenu.broadcastChanges();
/*    */ 
/*    */       
/* 59 */       $$5.inventoryMenu.slotsChanged((Container)$$5.getInventory());
/*    */     } 
/*    */     
/* 62 */     if ($$4 == 0) {
/* 63 */       if ($$1.size() == 1) {
/* 64 */         throw ERROR_SINGLE.create(((ServerPlayer)$$1.iterator().next()).getName());
/*    */       }
/* 66 */       throw ERROR_MULTIPLE.create(Integer.valueOf($$1.size()));
/*    */     } 
/*    */ 
/*    */     
/* 70 */     int $$6 = $$4;
/* 71 */     if ($$3 == 0) {
/* 72 */       if ($$1.size() == 1) {
/* 73 */         $$0.sendSuccess(() -> Component.translatable("commands.clear.test.single", new Object[] { Integer.valueOf($$0), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*    */       } else {
/* 75 */         $$0.sendSuccess(() -> Component.translatable("commands.clear.test.multiple", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1.size()) }), true);
/*    */       }
/*    */     
/* 78 */     } else if ($$1.size() == 1) {
/* 79 */       $$0.sendSuccess(() -> Component.translatable("commands.clear.success.single", new Object[] { Integer.valueOf($$0), ((ServerPlayer)$$1.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 81 */       $$0.sendSuccess(() -> Component.translatable("commands.clear.success.multiple", new Object[] { Integer.valueOf($$0), Integer.valueOf($$1.size()) }), true);
/*    */     } 
/*    */ 
/*    */     
/* 85 */     return $$4;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\ClearInventoryCommands.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */