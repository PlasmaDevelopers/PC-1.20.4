/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.LongArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<LongArgumentType>
/*    */ {
/*    */   final long min;
/*    */   final long max;
/*    */   
/*    */   Template(long $$1, long $$2) {
/* 19 */     this.min = $$1;
/* 20 */     this.max = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public LongArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */     return LongArgumentType.longArg(this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<LongArgumentType, ?> type() {
/* 30 */     return LongArgumentInfo.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\LongArgumentInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */