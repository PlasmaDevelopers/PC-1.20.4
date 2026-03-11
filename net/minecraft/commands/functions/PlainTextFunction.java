/*    */ package net.minecraft.commands.functions;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.FunctionInstantiationException;
/*    */ import net.minecraft.commands.execution.UnboundEntryAction;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public final class PlainTextFunction<T> extends Record implements CommandFunction<T>, InstantiatedFunction<T> {
/*    */   private final ResourceLocation id;
/*    */   private final List<UnboundEntryAction<T>> entries;
/*    */   
/* 12 */   public PlainTextFunction(ResourceLocation $$0, List<UnboundEntryAction<T>> $$1) { this.id = $$0; this.entries = $$1; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/commands/functions/PlainTextFunction;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/functions/PlainTextFunction;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 12 */     //   0	7	0	this	Lnet/minecraft/commands/functions/PlainTextFunction<TT;>; } public ResourceLocation id() { return this.id; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/commands/functions/PlainTextFunction;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/commands/functions/PlainTextFunction;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/commands/functions/PlainTextFunction<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/commands/functions/PlainTextFunction;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #12	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/commands/functions/PlainTextFunction;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 12 */     //   0	8	0	this	Lnet/minecraft/commands/functions/PlainTextFunction<TT;>; } public List<UnboundEntryAction<T>> entries() { return this.entries; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public InstantiatedFunction<T> instantiate(@Nullable CompoundTag $$0, CommandDispatcher<T> $$1, T $$2) throws FunctionInstantiationException {
/* 20 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\functions\PlainTextFunction.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */