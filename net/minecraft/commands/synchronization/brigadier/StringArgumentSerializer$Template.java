/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ 
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ 
/*    */ public final class Template
/*    */   implements ArgumentTypeInfo.Template<StringArgumentType>
/*    */ {
/*    */   final StringArgumentType.StringType type;
/*    */   
/*    */   public Template(StringArgumentType.StringType $$1) {
/* 14 */     this.type = $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public StringArgumentType instantiate(CommandBuildContext $$0) {
/* 19 */     switch (StringArgumentSerializer.null.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType[this.type.ordinal()]) { default: throw new IncompatibleClassChangeError();case 1: case 2: case 3: break; }  return 
/*    */ 
/*    */       
/* 22 */       StringArgumentType.greedyString();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ArgumentTypeInfo<StringArgumentType, ?> type() {
/* 28 */     return StringArgumentSerializer.this;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\StringArgumentSerializer$Template.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */