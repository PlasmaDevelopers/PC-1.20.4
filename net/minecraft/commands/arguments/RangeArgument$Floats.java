/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Floats
/*    */   implements RangeArgument<MinMaxBounds.Doubles>
/*    */ {
/* 33 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0..5.2", "0", "-5.4", "-100.76..", "..100" });
/*    */   
/*    */   public static MinMaxBounds.Doubles getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 36 */     return (MinMaxBounds.Doubles)$$0.getArgument($$1, MinMaxBounds.Doubles.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public MinMaxBounds.Doubles parse(StringReader $$0) throws CommandSyntaxException {
/* 41 */     return MinMaxBounds.Doubles.fromReader($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 46 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\RangeArgument$Floats.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */