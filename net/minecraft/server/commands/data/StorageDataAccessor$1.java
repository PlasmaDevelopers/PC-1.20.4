/*    */ package net.minecraft.server.commands.data;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.builder.ArgumentBuilder;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.Commands;
/*    */ import net.minecraft.commands.arguments.ResourceLocationArgument;
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
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements DataCommands.DataProvider
/*    */ {
/*    */   public DataAccessor access(CommandContext<CommandSourceStack> $$0) {
/* 31 */     return new StorageDataAccessor(StorageDataAccessor.getGlobalTags($$0), ResourceLocationArgument.getId($$0, arg));
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentBuilder<CommandSourceStack, ?> wrap(ArgumentBuilder<CommandSourceStack, ?> $$0, Function<ArgumentBuilder<CommandSourceStack, ?>, ArgumentBuilder<CommandSourceStack, ?>> $$1) {
/* 36 */     return $$0.then(Commands.literal("storage").then($$1.apply(Commands.argument(arg, (ArgumentType)ResourceLocationArgument.id()).suggests(StorageDataAccessor.SUGGEST_STORAGE))));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\data\StorageDataAccessor$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */