/*    */ package net.minecraft.server.commands;
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.AngleArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Position;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class SetWorldSpawnCommand {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     $$0.register(
/* 19 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setworldspawn")
/* 20 */         .requires($$0 -> $$0.hasPermission(2)))
/* 21 */         .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), BlockPos.containing((Position)((CommandSourceStack)$$0.getSource()).getPosition()), 0.0F)))
/* 22 */         .then((
/* 23 */           (RequiredArgumentBuilder)Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 24 */           .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), BlockPosArgument.getSpawnablePos($$0, "pos"), 0.0F)))
/* 25 */           .then(
/* 26 */             Commands.argument("angle", (ArgumentType)AngleArgument.angle())
/* 27 */             .executes($$0 -> setSpawn((CommandSourceStack)$$0.getSource(), BlockPosArgument.getSpawnablePos($$0, "pos"), AngleArgument.getAngle($$0, "angle"))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setSpawn(CommandSourceStack $$0, BlockPos $$1, float $$2) {
/* 34 */     $$0.getLevel().setDefaultSpawnPos($$1, $$2);
/* 35 */     $$0.sendSuccess(() -> Component.translatable("commands.setworldspawn.success", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()), Float.valueOf($$1) }), true);
/* 36 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SetWorldSpawnCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */