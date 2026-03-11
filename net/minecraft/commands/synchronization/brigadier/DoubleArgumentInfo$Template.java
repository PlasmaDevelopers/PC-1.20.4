/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.DoubleArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<DoubleArgumentType>
/*    */ {
/*    */   final double min;
/*    */   final double max;
/*    */   
/*    */   Template(double $$1, double $$2) {
/* 19 */     this.min = $$1;
/* 20 */     this.max = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public DoubleArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */     return DoubleArgumentType.doubleArg(this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<DoubleArgumentType, ?> type() {
/* 30 */     return DoubleArgumentInfo.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\DoubleArgumentInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */