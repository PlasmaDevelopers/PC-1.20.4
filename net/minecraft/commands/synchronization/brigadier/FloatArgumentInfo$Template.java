/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.FloatArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<FloatArgumentType>
/*    */ {
/*    */   final float min;
/*    */   final float max;
/*    */   
/*    */   Template(float $$1, float $$2) {
/* 19 */     this.min = $$1;
/* 20 */     this.max = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public FloatArgumentType instantiate(CommandBuildContext $$0) {
/* 25 */     return FloatArgumentType.floatArg(this.min, this.max);
/*    */   }
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<FloatArgumentType, ?> type() {
/* 30 */     return FloatArgumentInfo.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\FloatArgumentInfo$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */