/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.world.entity.monster.warden.WardenSpawnTracker;
/*    */ import net.minecraft.world.entity.player.Player;
/*    */ 
/*    */ public class WardenSpawnTrackerCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 20 */     $$0.register(
/* 21 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("warden_spawn_tracker")
/* 22 */         .requires($$0 -> $$0.hasPermission(2)))
/* 23 */         .then(
/* 24 */           Commands.literal("clear")
/* 25 */           .executes($$0 -> resetTracker((CommandSourceStack)$$0.getSource(), (Collection<? extends Player>)ImmutableList.of(((CommandSourceStack)$$0.getSource()).getPlayerOrException())))))
/*    */         
/* 27 */         .then(
/* 28 */           Commands.literal("set")
/* 29 */           .then(
/* 30 */             Commands.argument("warning_level", (ArgumentType)IntegerArgumentType.integer(0, 4))
/* 31 */             .executes($$0 -> setWarningLevel((CommandSourceStack)$$0.getSource(), (Collection<? extends Player>)ImmutableList.of(((CommandSourceStack)$$0.getSource()).getPlayerOrException()), IntegerArgumentType.getInteger($$0, "warning_level"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setWarningLevel(CommandSourceStack $$0, Collection<? extends Player> $$1, int $$2) {
/* 38 */     for (Player $$3 : $$1) {
/* 39 */       $$3.getWardenSpawnTracker().ifPresent($$1 -> $$1.setWarningLevel($$0));
/*    */     }
/*    */     
/* 42 */     if ($$1.size() == 1) {
/* 43 */       $$0.sendSuccess(() -> Component.translatable("commands.warden_spawn_tracker.set.success.single", new Object[] { ((Player)$$0.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 45 */       $$0.sendSuccess(() -> Component.translatable("commands.warden_spawn_tracker.set.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*    */     } 
/*    */     
/* 48 */     return $$1.size();
/*    */   }
/*    */   
/*    */   private static int resetTracker(CommandSourceStack $$0, Collection<? extends Player> $$1) {
/* 52 */     for (Player $$2 : $$1) {
/* 53 */       $$2.getWardenSpawnTracker().ifPresent(WardenSpawnTracker::reset);
/*    */     }
/*    */     
/* 56 */     if ($$1.size() == 1) {
/* 57 */       $$0.sendSuccess(() -> Component.translatable("commands.warden_spawn_tracker.clear.success.single", new Object[] { ((Player)$$0.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 59 */       $$0.sendSuccess(() -> Component.translatable("commands.warden_spawn_tracker.clear.success.multiple", new Object[] { Integer.valueOf($$0.size()) }), true);
/*    */     } 
/*    */     
/* 62 */     return $$1.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\WardenSpawnTrackerCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */