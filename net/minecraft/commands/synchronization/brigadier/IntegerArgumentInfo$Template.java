/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<IntegerArgumentType>
/*    */ {
/*    */   final int min;
/*    */   final int max;
/*    */   
/*    */   Template(int $$1, int $$2) {
/* 19 */     this.min = $$1;
/* 20 */     this.max = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public IntegerArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */     return IntegerArgumentType.integer(this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<IntegerArgumentType, ?> type() {
/* 30 */     return IntegerArgumentInfo.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\IntegerArgumentInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */