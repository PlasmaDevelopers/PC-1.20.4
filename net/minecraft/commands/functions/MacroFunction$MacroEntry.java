/*     */ package net.minecraft.commands.functions;
/*     */ 
/*     */ import com.mojang.brigadier.CommandDispatcher;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import java.util.List;
/*     */ import net.minecraft.commands.ExecutionCommandSource;
/*     */ import net.minecraft.commands.FunctionInstantiationException;
/*     */ import net.minecraft.commands.execution.UnboundEntryAction;
/*     */ import net.minecraft.network.chat.Component;
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
/*     */ class MacroEntry<T extends ExecutionCommandSource<T>>
/*     */   implements MacroFunction.Entry<T>
/*     */ {
/*     */   private final StringTemplate template;
/*     */   private final IntList parameters;
/*     */   
/*     */   public MacroEntry(StringTemplate $$0, IntList $$1) {
/* 143 */     this.template = $$0;
/* 144 */     this.parameters = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public IntList parameters() {
/* 149 */     return this.parameters;
/*     */   }
/*     */ 
/*     */   
/*     */   public UnboundEntryAction<T> instantiate(List<String> $$0, CommandDispatcher<T> $$1, T $$2, ResourceLocation $$3) throws FunctionInstantiationException {
/* 154 */     String $$4 = this.template.substitute($$0);
/*     */     try {
/* 156 */       return CommandFunction.parseCommand($$1, $$2, new StringReader($$4));
/* 157 */     } catch (CommandSyntaxException $$5) {
/* 158 */       throw new FunctionInstantiationException(Component.translatable("commands.function.error.parse", new Object[] { Component.translationArg($$3), $$4, $$5.getMessage() }));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\MacroFunction$MacroEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */