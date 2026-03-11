/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.commands.ParserUtils;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.network.chat.ComponentSerialization;
/*    */ 
/*    */ public class ComponentArgument implements ArgumentType<Component> {
/* 17 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "\"hello world\"", "\"\"", "\"{\"text\":\"hello world\"}", "[\"\"]" }); static {
/* 18 */     ERROR_INVALID_JSON = new DynamicCommandExceptionType($$0 -> Component.translatableEscape("argument.component.invalid", new Object[] { $$0 }));
/*    */   }
/*    */   
/*    */   public static final DynamicCommandExceptionType ERROR_INVALID_JSON;
/*    */   
/*    */   public static Component getComponent(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 24 */     return (Component)$$0.getArgument($$1, Component.class);
/*    */   }
/*    */   
/*    */   public static ComponentArgument textComponent() {
/* 28 */     return new ComponentArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component parse(StringReader $$0) throws CommandSyntaxException {
/*    */     try {
/* 34 */       return (Component)ParserUtils.parseJson($$0, ComponentSerialization.CODEC);
/* 35 */     } catch (Exception $$1) {
/* 36 */       String $$2 = ($$1.getCause() != null) ? $$1.getCause().getMessage() : $$1.getMessage();
/* 37 */       throw ERROR_INVALID_JSON.createWithContext($$0, $$2);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 43 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\ComponentArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */