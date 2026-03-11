/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.item.ItemArgument;
/*    */ import net.minecraft.commands.arguments.item.ItemInput;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.sounds.SoundEvents;
/*    */ import net.minecraft.sounds.SoundSource;
/*    */ import net.minecraft.world.entity.item.ItemEntity;
/*    */ import net.minecraft.world.item.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GiveCommand
/*    */ {
/*    */   public static final int MAX_ALLOWED_ITEMSTACKS = 100;
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 32 */     $$0.register(
/* 33 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("give")
/* 34 */         .requires($$0 -> $$0.hasPermission(2)))
/* 35 */         .then(
/* 36 */           Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 37 */           .then((
/* 38 */             (RequiredArgumentBuilder)Commands.argument("item", (ArgumentType)ItemArgument.item($$1))
/* 39 */             .executes($$0 -> giveItem((CommandSourceStack)$$0.getSource(), ItemArgument.getItem($$0, "item"), EntityArgument.getPlayers($$0, "targets"), 1)))
/* 40 */             .then(
/* 41 */               Commands.argument("count", (ArgumentType)IntegerArgumentType.integer(1))
/* 42 */               .executes($$0 -> giveItem((CommandSourceStack)$$0.getSource(), ItemArgument.getItem($$0, "item"), EntityArgument.getPlayers($$0, "targets"), IntegerArgumentType.getInteger($$0, "count")))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int giveItem(CommandSourceStack $$0, ItemInput $$1, Collection<ServerPlayer> $$2, int $$3) throws CommandSyntaxException {
/* 50 */     int $$4 = $$1.getItem().getMaxStackSize();
/* 51 */     int $$5 = $$4 * 100;
/* 52 */     ItemStack $$6 = $$1.createItemStack($$3, false);
/* 53 */     if ($$3 > $$5) {
/* 54 */       $$0.sendFailure((Component)Component.translatable("commands.give.failed.toomanyitems", new Object[] { Integer.valueOf($$5), $$6.getDisplayName() }));
/* 55 */       return 0;
/*    */     } 
/* 57 */     for (ServerPlayer $$7 : $$2) {
/* 58 */       int $$8 = $$3;
/* 59 */       while ($$8 > 0) {
/* 60 */         int $$9 = Math.min($$4, $$8);
/* 61 */         $$8 -= $$9;
/*    */         
/* 63 */         ItemStack $$10 = $$1.createItemStack($$9, false);
/* 64 */         boolean $$11 = $$7.getInventory().add($$10);
/*    */         
/* 66 */         if (!$$11 || !$$10.isEmpty()) {
/* 67 */           ItemEntity $$12 = $$7.drop($$10, false);
/* 68 */           if ($$12 != null) {
/* 69 */             $$12.setNoPickUpDelay();
/* 70 */             $$12.setTarget($$7.getUUID());
/*    */           } 
/*    */           continue;
/*    */         } 
/* 74 */         $$10.setCount(1);
/* 75 */         ItemEntity $$13 = $$7.drop($$10, false);
/* 76 */         if ($$13 != null) {
/* 77 */           $$13.makeFakeItem();
/*    */         }
/* 79 */         $$7.level().playSound(null, $$7.getX(), $$7.getY(), $$7.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, (($$7.getRandom().nextFloat() - $$7.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
/* 80 */         $$7.containerMenu.broadcastChanges();
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 85 */     if ($$2.size() == 1) {
/* 86 */       $$0.sendSuccess(() -> Component.translatable("commands.give.success.single", new Object[] { Integer.valueOf($$0), $$1.getDisplayName(), ((ServerPlayer)$$2.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 88 */       $$0.sendSuccess(() -> Component.translatable("commands.give.success.single", new Object[] { Integer.valueOf($$0), $$1.getDisplayName(), Integer.valueOf($$2.size()) }), true);
/*    */     } 
/*    */     
/* 91 */     return $$2.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\GiveCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */