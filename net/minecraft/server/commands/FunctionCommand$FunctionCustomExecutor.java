/*     */ package net.minecraft.server.commands;
/*     */ 
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.context.ContextChain;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.arguments.item.FunctionArgument;
/*     */ import net.minecraft.commands.execution.ChainModifiers;
/*     */ import net.minecraft.commands.execution.CustomCommandExecutor;
/*     */ import net.minecraft.commands.execution.ExecutionControl;
/*     */ import net.minecraft.commands.functions.CommandFunction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ abstract class FunctionCustomExecutor
/*     */   extends CustomCommandExecutor.WithErrorHandling<CommandSourceStack>
/*     */   implements CustomCommandExecutor.CommandAdapter<CommandSourceStack>
/*     */ {
/*     */   public void runGuarded(CommandSourceStack $$0, ContextChain<CommandSourceStack> $$1, ChainModifiers $$2, ExecutionControl<CommandSourceStack> $$3) throws CommandSyntaxException {
/* 126 */     CommandContext<CommandSourceStack> $$4 = $$1.getTopContext().copyFor($$0);
/*     */     
/* 128 */     Pair<ResourceLocation, Collection<CommandFunction<CommandSourceStack>>> $$5 = FunctionArgument.getFunctionCollection($$4, "name");
/* 129 */     Collection<CommandFunction<CommandSourceStack>> $$6 = (Collection<CommandFunction<CommandSourceStack>>)$$5.getSecond();
/* 130 */     if ($$6.isEmpty()) {
/* 131 */       throw FunctionCommand.ERROR_NO_FUNCTIONS.create(Component.translationArg((ResourceLocation)$$5.getFirst()));
/*     */     }
/*     */     
/* 134 */     CompoundTag $$7 = arguments($$4);
/*     */     
/* 136 */     CommandSourceStack $$8 = FunctionCommand.modifySenderForExecution($$0);
/*     */     
/* 138 */     if ($$6.size() == 1) {
/* 139 */       $$0.sendSuccess(() -> Component.translatable("commands.function.scheduled.single", new Object[] { Component.translationArg(((CommandFunction)$$0.iterator().next()).id()) }), true);
/*     */     } else {
/* 141 */       $$0.sendSuccess(() -> Component.translatable("commands.function.scheduled.multiple", new Object[] { ComponentUtils.formatList($$0.stream().map(CommandFunction::id).toList(), Component::translationArg) }), true);
/*     */     } 
/*     */     
/* 144 */     FunctionCommand.queueFunctions($$6, $$7, $$0, $$8, $$3, FunctionCommand.FULL_CONTEXT_CALLBACKS, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected abstract CompoundTag arguments(CommandContext<CommandSourceStack> paramCommandContext) throws CommandSyntaxException;
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\commands\FunctionCommand$FunctionCustomExecutor.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */