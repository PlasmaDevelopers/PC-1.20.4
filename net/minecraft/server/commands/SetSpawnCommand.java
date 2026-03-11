/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.AngleArgument;
/*    */ import net.minecraft.commands.arguments.EntityArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.server.level.ServerPlayer;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ public class SetSpawnCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 26 */     $$0.register(
/* 27 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("spawnpoint")
/* 28 */         .requires($$0 -> $$0.hasPermission(2)))
/* 29 */         .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), Collections.singleton(((CommandSourceStack)$$0.getSource()).getPlayerOrException()), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()), 0.0F)))
/* 30 */         .then((
/* 31 */           (RequiredArgumentBuilder)Commands.argument("targets", (ArgumentType)EntityArgument.players())
/* 32 */           .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()), 0.0F)))
/* 33 */           .then((
/* 34 */             (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 35 */             .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), BlockPosArgument.getSpawnablePos($$0, "pos"), 0.0F)))
/* 36 */             .then(
/* 37 */               Commands.argument("angle", (ArgumentType)AngleArgument.angle())
/* 38 */               .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), EntityArgument.getPlayers($$0, "targets"), BlockPosArgument.getSpawnablePos($$0, "pos"), AngleArgument.getAngle($$0, "angle")))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setSpawn(CommandSourceStack $$0, Collection<ServerPlayer> $$1, BlockPos $$2, float $$3) {
/* 46 */     ResourceKey<Level> $$4 = $$0.getLevel().dimension();
/* 47 */     for (ServerPlayer $$5 : $$1) {
/* 48 */       $$5.setRespawnPosition($$4, $$2, $$3, true, false);
/*    */     }
/*    */     
/* 51 */     String $$6 = $$4.location().toString();
/* 52 */     if ($$1.size() == 1) {
/* 53 */       $$0.sendSuccess(() -> Component.translatable("commands.spawnpoint.success.single", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()), Float.valueOf($$1), $$2, ((ServerPlayer)$$3.iterator().next()).getDisplayName() }), true);
/*    */     } else {
/* 55 */       $$0.sendSuccess(() -> Component.translatable("commands.spawnpoint.success.multiple", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()), Float.valueOf($$1), $$2, Integer.valueOf($$3.size()) }), true);
/*    */     } 
/*    */     
/* 58 */     return $$1.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SetSpawnCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */