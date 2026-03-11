/*    */ package net.minecraft.commands.functions;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*    */ import it.unimi.dsi.fastutil.ints.IntList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.commands.ExecutionCommandSource;
/*    */ import net.minecraft.commands.execution.UnboundEntryAction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ class FunctionBuilder<T extends ExecutionCommandSource<T>>
/*    */ {
/*    */   @Nullable
/* 15 */   private List<UnboundEntryAction<T>> plainEntries = new ArrayList<>();
/*    */   
/*    */   @Nullable
/*    */   private List<MacroFunction.Entry<T>> macroEntries;
/* 19 */   private final List<String> macroArguments = new ArrayList<>();
/*    */   
/*    */   public void addCommand(UnboundEntryAction<T> $$0) {
/* 22 */     if (this.macroEntries != null) {
/* 23 */       this.macroEntries.add(new MacroFunction.PlainTextEntry<>($$0));
/*    */     } else {
/* 25 */       this.plainEntries.add($$0);
/*    */     } 
/*    */   }
/*    */   
/*    */   private int getArgumentIndex(String $$0) {
/* 30 */     int $$1 = this.macroArguments.indexOf($$0);
/* 31 */     if ($$1 == -1) {
/* 32 */       $$1 = this.macroArguments.size();
/* 33 */       this.macroArguments.add($$0);
/*    */     } 
/* 35 */     return $$1;
/*    */   }
/*    */   
/*    */   private IntList convertToIndices(List<String> $$0) {
/* 39 */     IntArrayList $$1 = new IntArrayList($$0.size());
/* 40 */     for (String $$2 : $$0) {
/* 41 */       $$1.add(getArgumentIndex($$2));
/*    */     }
/* 43 */     return (IntList)$$1;
/*    */   }
/*    */   
/*    */   public void addMacro(String $$0, int $$1) {
/* 47 */     StringTemplate $$2 = StringTemplate.fromString($$0, $$1);
/*    */     
/* 49 */     if (this.plainEntries != null) {
/* 50 */       this.macroEntries = new ArrayList<>(this.plainEntries.size() + 1);
/* 51 */       for (UnboundEntryAction<T> $$3 : this.plainEntries) {
/* 52 */         this.macroEntries.add(new MacroFunction.PlainTextEntry<>($$3));
/*    */       }
/* 54 */       this.plainEntries = null;
/*    */     } 
/*    */     
/* 57 */     this.macroEntries.add(new MacroFunction.MacroEntry<>($$2, convertToIndices($$2.variables())));
/*    */   }
/*    */   
/*    */   public CommandFunction<T> build(ResourceLocation $$0) {
/* 61 */     if (this.macroEntries != null) {
/* 62 */       return new MacroFunction<>($$0, this.macroEntries, this.macroArguments);
/*    */     }
/*    */     
/* 65 */     return new PlainTextFunction<>($$0, this.plainEntries);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\FunctionBuilder.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */