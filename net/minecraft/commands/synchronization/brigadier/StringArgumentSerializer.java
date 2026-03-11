/*    */ package net.minecraft.commands.synchronization.brigadier;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.arguments.StringArgumentType;
/*    */ import net.minecraft.commands.CommandBuildContext;
/*    */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class StringArgumentSerializer implements ArgumentTypeInfo<StringArgumentType, StringArgumentSerializer.Template> {
/*    */   public final class Template implements ArgumentTypeInfo.Template<StringArgumentType> {
/*    */     final StringArgumentType.StringType type;
/*    */     
/*    */     public Template(StringArgumentType.StringType $$1) {
/* 14 */       this.type = $$1;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public StringArgumentType instantiate(CommandBuildContext $$0) {
/*    */       // Byte code:
/*    */       //   0: getstatic net/minecraft/commands/synchronization/brigadier/StringArgumentSerializer$1.$SwitchMap$com$mojang$brigadier$arguments$StringArgumentType$StringType : [I
/*    */       //   3: aload_0
/*    */       //   4: getfield type : Lcom/mojang/brigadier/arguments/StringArgumentType$StringType;
/*    */       //   7: invokevirtual ordinal : ()I
/*    */       //   10: iaload
/*    */       //   11: tableswitch default -> 36, 1 -> 44, 2 -> 50, 3 -> 56
/*    */       //   36: new java/lang/IncompatibleClassChangeError
/*    */       //   39: dup
/*    */       //   40: invokespecial <init> : ()V
/*    */       //   43: athrow
/*    */       //   44: invokestatic word : ()Lcom/mojang/brigadier/arguments/StringArgumentType;
/*    */       //   47: goto -> 59
/*    */       //   50: invokestatic string : ()Lcom/mojang/brigadier/arguments/StringArgumentType;
/*    */       //   53: goto -> 59
/*    */       //   56: invokestatic greedyString : ()Lcom/mojang/brigadier/arguments/StringArgumentType;
/*    */       //   59: areturn
/*    */       // Line number table:
/*    */       //   Java source line number -> byte code offset
/*    */       //   #19	-> 0
/*    */       //   #20	-> 44
/*    */       //   #21	-> 50
/*    */       //   #22	-> 56
/*    */       //   #19	-> 59
/*    */       // Local variable table:
/*    */       //   start	length	slot	name	descriptor
/*    */       //   0	60	0	this	Lnet/minecraft/commands/synchronization/brigadier/StringArgumentSerializer$Template;
/*    */       //   0	60	1	$$0	Lnet/minecraft/commands/CommandBuildContext;
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     public ArgumentTypeInfo<StringArgumentType, ?> type() {
/* 28 */       return StringArgumentSerializer.this;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 34 */     $$1.writeEnum((Enum)$$0.type);
/*    */   }
/*    */ 
/*    */   
/*    */   public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 39 */     StringArgumentType.StringType $$1 = (StringArgumentType.StringType)$$0.readEnum(StringArgumentType.StringType.class);
/* 40 */     return new Template($$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void serializeToJson(Template $$0, JsonObject $$1) {
/* 45 */     switch ($$0.type) { default: throw new IncompatibleClassChangeError();case SINGLE_WORD: case QUOTABLE_PHRASE: case GREEDY_PHRASE: break; }  $$1.addProperty("type", 
/*    */ 
/*    */         
/* 48 */         "greedy");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Template unpack(StringArgumentType $$0) {
/* 54 */     return new Template($$0.getType());
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\synchronization\brigadier\StringArgumentSerializer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */