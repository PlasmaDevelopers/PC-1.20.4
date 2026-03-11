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
/*    */ public class Ints
/*    */   implements RangeArgument<MinMaxBounds.Ints>
/*    */ {
/* 15 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0..5", "0", "-5", "-100..", "..100" });
/*    */   
/*    */   public static MinMaxBounds.Ints getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 18 */     return (MinMaxBounds.Ints)$$0.getArgument($$1, MinMaxBounds.Ints.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public MinMaxBounds.Ints parse(StringReader $$0) throws CommandSyntaxException {
/* 23 */     return MinMaxBounds.Ints.fromReader($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 28 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\RangeArgument$Ints.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */