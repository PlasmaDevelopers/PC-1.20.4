/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.ParserUtils;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.Style;
/*    */ 
/*    */ public class StyleArgument implements ArgumentType<Style> {
/* 17 */   private static final Collection<String> EXAMPLES = List.of("{\"bold\": true}\n");
/*    */   public static final DynamicCommandExceptionType ERROR_INVALID_JSON;
/*    */   
/*    */   static {
/* 21 */     ERROR_INVALID_JSON = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.style.invalid", new Object[] { $$0 }));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Style getStyle(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 27 */     return (Style)$$0.getArgument($$1, Style.class);
/*    */   }
/*    */   
/*    */   public static StyleArgument style() {
/* 31 */     return new StyleArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public Style parse(StringReader $$0) throws CommandSyntaxException {
/*    */     try {
/* 37 */       return (Style)ParserUtils.parseJson($$0, Style.Serializer.CODEC);
/* 38 */     } catch (Exception $$1) {
/* 39 */       String $$2 = ($$1.getCause() != null) ? $$1.getCause().getMessage() : $$1.getMessage();
/* 40 */       throw ERROR_INVALID_JSON.createWithContext($$0, $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 46 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\StyleArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */