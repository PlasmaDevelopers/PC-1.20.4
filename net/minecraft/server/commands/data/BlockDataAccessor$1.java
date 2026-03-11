/*    */ package net.minecraft.server.commands.data;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements DataCommands.DataProvider
/*    */ {
/*    */   public DataAccessor access(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 32 */     BlockPos $$1 = BlockPosArgument.getLoadedBlockPos($$0, argPrefix + "Pos");
/* 33 */     BlockEntity $$2 = ((CommandSourceStack)$$0.getSource()).getLevel().getBlockEntity($$1);
/* 34 */     if ($$2 == null) {
/* 35 */       throw BlockDataAccessor.ERROR_NOT_A_BLOCK_ENTITY.create();
/*    */     }
/* 37 */     return new BlockDataAccessor($$2, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> $$0, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> $$1) {
/* 42 */     return $$0.then(Commands.literal("block").then($$1.apply(Commands.argument(argPrefix + "Pos", (ArgumentType)BlockPosArgument.blockPos()))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\BlockDataAccessor$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */