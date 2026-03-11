/*    */ package net.minecraft.server.commands;
/*    */ 
/*    */ import com.mojang.brigadier.CommandDispatcher;
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.LiteralArgumentBuilder;
/*    */ import com.mojang.brigadier.builder.RequiredArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.function.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.blocks.BlockInput;
/*    */ import net.minecraft.commands.arguments.blocks.BlockStateArgument;
/*    */ import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.Clearable;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.block.entity.BlockEntity;
/*    */ import net.minecraft.world.level.block.state.pattern.BlockInWorld;
/*    */ import net.minecraft.world.level.levelgen.structure.BoundingBox;
/*    */ 
/*    */ public class SetBlockCommand
/*    */ {
/* 30 */   private static final SimpleCommandExceptionType ERROR_FAILED = new SimpleCommandExceptionType((Message)Component.translatable("commands.setblock.failed"));
/*    */   
/*    */   public static void register(CommandDispatcher<CommandSourceStack> $$0, CommandBuildContext $$1) {
/* 33 */     $$0.register(
/* 34 */         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("setblock")
/* 35 */         .requires($$0 -> $$0.hasPermission(2)))
/* 36 */         .then(
/* 37 */           Commands.argument("pos", (ArgumentType)BlockPosArgument.blockPos())
/* 38 */           .then((
/* 39 */             (RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument("block", (ArgumentType)BlockStateArgument.block($$1))
/* 40 */             .executes($$0 -> setBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, null)))
/* 41 */             .then(
/* 42 */               Commands.literal("destroy")
/* 43 */               .executes($$0 -> setBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), BlockStateArgument.getBlock($$0, "block"), Mode.DESTROY, null))))
/*    */             
/* 45 */             .then(
/* 46 */               Commands.literal("keep")
/* 47 */               .executes($$0 -> setBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, ()))))
/*    */             
/* 49 */             .then(
/* 50 */               Commands.literal("replace")
/* 51 */               .executes($$0 -> setBlock((CommandSourceStack)$$0.getSource(), BlockPosArgument.getLoadedBlockPos($$0, "pos"), BlockStateArgument.getBlock($$0, "block"), Mode.REPLACE, null))))));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static int setBlock(CommandSourceStack $$0, BlockPos $$1, BlockInput $$2, Mode $$3, @Nullable Predicate<BlockInWorld> $$4) throws CommandSyntaxException {
/*    */     boolean $$8;
/* 59 */     ServerLevel $$5 = $$0.getLevel();
/* 60 */     if ($$4 != null && !$$4.test(new BlockInWorld((LevelReader)$$5, $$1, true))) {
/* 61 */       throw ERROR_FAILED.create();
/*    */     }
/*    */ 
/*    */     
/* 65 */     if ($$3 == Mode.DESTROY) {
/* 66 */       $$5.destroyBlock($$1, true);
/* 67 */       boolean $$6 = (!$$2.getState().isAir() || !$$5.getBlockState($$1).isAir());
/*    */     } else {
/* 69 */       BlockEntity $$7 = $$5.getBlockEntity($$1);
/* 70 */       Clearable.tryClear($$7);
/* 71 */       $$8 = true;
/*    */     } 
/* 73 */     if ($$8 && !$$2.place($$5, $$1, 2)) {
/* 74 */       throw ERROR_FAILED.create();
/*    */     }
/*    */     
/* 77 */     $$5.blockUpdated($$1, $$2.getState().getBlock());
/* 78 */     $$0.sendSuccess(() -> Component.translatable("commands.setblock.success", new Object[] { Integer.valueOf($$0.getX()), Integer.valueOf($$0.getY()), Integer.valueOf($$0.getZ()) }), true);
/* 79 */     return 1;
/*    */   }
/*    */   
/*    */   public enum Mode {
/* 83 */     REPLACE,
/* 84 */     DESTROY;
/*    */   }
/*    */   
/*    */   public static interface Filter {
/*    */     @Nullable
/*    */     BlockInput filter(BoundingBox param1BoundingBox, BlockPos param1BlockPos, BlockInput param1BlockInput, ServerLevel param1ServerLevel);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\SetBlockCommand.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */