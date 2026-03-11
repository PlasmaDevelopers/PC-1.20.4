/*    */ package net.minecraft.commands.arguments.item;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.datafixers.util.Either;
/*    */ import com.mojang.datafixers.util.Pair;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.functions.CommandFunction;
/*    */ import net.minecraft.resources.ResourceLocation;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class null
/*    */   implements FunctionArgument.Result
/*    */ {
/*    */   public Collection<CommandFunction<CommandSourceStack>> create(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 36 */     return FunctionArgument.getFunctionTag($$0, id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Pair<ResourceLocation, Either<CommandFunction<CommandSourceStack>, Collection<CommandFunction<CommandSourceStack>>>> unwrap(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 41 */     return Pair.of(id, Either.right(FunctionArgument.getFunctionTag($$0, id)));
/*    */   }
/*    */ 
/*    */   
/*    */   public Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> unwrapToCollection(CommandContext<CommandSourceStack> $$0) throws CommandSyntaxException {
/* 46 */     return Pair.of(id, FunctionArgument.getFunctionTag($$0, id));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\item\FunctionArgument$1.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */