/*    */ package net.minecraft.commands.synchronization;
/*    */ 
/*    */ import java.util.function.Function;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<A>
/*    */ {
/*    */   private final Function<CommandBuildContext, A> constructor;
/*    */   
/*    */   public Template(Function<CommandBuildContext, A> $$1) {
/* 16 */     this.constructor = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public A instantiate(CommandBuildContext $$0) {
/* 21 */     return this.constructor.apply($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<A, ?> type() {
/* 26 */     return SingletonArgumentInfo.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\SingletonArgumentInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */