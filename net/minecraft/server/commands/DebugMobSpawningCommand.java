/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.entity.MobCategory;
/*    */ import net.minecraft.world.level.NaturalSpawner;
/*    */ 
/*    */ public class DebugMobSpawningCommand
/*    */ {
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0) {
/* 18 */     LiteralArgumentBuilder<CommandSourceStack> $$1 = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("debugmobspawning").requires($$0 -> $$0.hasPermission(2));
/*    */     
/* 20 */     for (MobCategory $$2 : MobCategory.values()) {
/* 21 */       $$1.then(
/* 22 */           Commands.literal($$2.getName())
/* 23 */           .then(
/* 24 */             Commands.argument("at", (ArgumentType)BlockPosArgument.blockPos())
/* 25 */             .executes($$1 -> spawnMobs((CommandSourceStack)$$1.getSource(), $$0, BlockPosArgument.getLoadedBlockPos($$1, "at")))));
/*    */     }
/*    */ 
/*    */     
/* 29 */     $$0.register($$1);
/*    */   }
/*    */   
/*    */   private static int spawnMobs(CommandSourceStack $$0, MobCategory $$1, BlockPos $$2) {
/* 33 */     NaturalSpawner.spawnCategoryForPosition($$1, $$0.getLevel(), $$2);
/* 34 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\DebugMobSpawningCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */