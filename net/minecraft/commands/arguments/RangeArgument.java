/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import net.minecraft.advancements.critereon.MinMaxBounds;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ 
/*    */ public interface RangeArgument<T extends MinMaxBounds<?>>
/*    */   extends ArgumentType<T> {
/*    */   public static class Ints implements RangeArgument<MinMaxBounds.Ints> {
/* 15 */     private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0..5", "0", "-5", "-100..", "..100" });
/*    */     
/*    */     public static MinMaxBounds.Ints getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 18 */       return (MinMaxBounds.Ints)$$0.getArgument($$1, MinMaxBounds.Ints.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public MinMaxBounds.Ints parse(StringReader $$0) throws CommandSyntaxException {
/* 23 */       return MinMaxBounds.Ints.fromReader($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public Collection<String> getExamples() {
/* 28 */       return EXAMPLES;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Floats implements RangeArgument<MinMaxBounds.Doubles> {
/* 33 */     private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0..5.2", "0", "-5.4", "-100.76..", "..100" });
/*    */     
/*    */     public static MinMaxBounds.Doubles getRange(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 36 */       return (MinMaxBounds.Doubles)$$0.getArgument($$1, MinMaxBounds.Doubles.class);
/*    */     }
/*    */ 
/*    */     
/*    */     public MinMaxBounds.Doubles parse(StringReader $$0) throws CommandSyntaxException {
/* 41 */       return MinMaxBounds.Doubles.fromReader($$0);
/*    */     }
/*    */ 
/*    */     
/*    */     public Collection<String> getExamples() {
/* 46 */       return EXAMPLES;
/*    */     }
/*    */   }
/*    */   
/*    */   static Ints intRange() {
/* 51 */     return new Ints();
/*    */   }
/*    */   
/*    */   static Floats floatRange() {
/* 55 */     return new Floats();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\RangeArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */