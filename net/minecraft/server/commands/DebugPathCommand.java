/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.protocol.game.DebugPackets;
/*    */ import net.minecraft.world.entity.Entity;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.pathfinder.Path;
/*    */ 
/*    */ public class DebugPathCommand {
/* 23 */   private static final SimpleCommandExceptionType ERROR_NOT_MOB = new SimpleCommandExceptionType((Message)Component.literal("Source is not a mob"));
/* 24 */   private static final SimpleCommandExceptionType ERROR_NO_PATH = new SimpleCommandExceptionType((Message)Component.literal("Path not found"));
/* 25 */   private static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.literal("Target not reached"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 28 */     $$0.register(
/* 29 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("debugpath")
/* 30 */         .requires($$0 -> $$0.hasPermission(2)))
/* 31 */         .then(
/* 32 */           Commands.argument("to", (ArgumentType)BlockPosArgument.blockPos())
/* 33 */           .executes($$0 -> fillBlocks((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "to")))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private static int fillBlocks(CommandSourceStack $$0, BlockPos $$1) throws CommandSyntaxException {
/* 39 */     Entity $$2 = $$0.getEntity();
/* 40 */     if (!($$2 instanceof Mob)) {
/* 41 */       throw ERROR_NOT_MOB.create();
/*    */     }
/*    */     
/* 44 */     Mob $$3 = (Mob)$$2;
/*    */     
/* 46 */     GroundPathNavigation groundPathNavigation = new GroundPathNavigation($$3, (Level)$$0.getLevel());
/* 47 */     Path $$5 = groundPathNavigation.createPath($$1, 0);
/* 48 */     DebugPackets.sendPathFindingPacket((Level)$$0.getLevel(), $$3, $$5, groundPathNavigation.getMaxDistanceToWaypoint());
/*    */     
/* 50 */     if ($$5 == null) {
/* 51 */       throw ERROR_NO_PATH.create();
/*    */     }
/* 53 */     if (!$$5.canReach()) {
/* 54 */       throw ERROR_NOT_COMPLETE.create();
/*    */     }
/*    */     
/* 57 */     $$0.sendSuccess(() -> Component.literal("Made path"), true);
/* 58 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugPathCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */