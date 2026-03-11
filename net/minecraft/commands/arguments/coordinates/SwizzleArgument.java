/*    */ package net.minecraft.commands.arguments.coordinates;
/*    */ 
/*    */ import com.mojang.brigadier.Message;
/*    */ import com.mojang.brigadier.StringReader;
/*    */ import com.mojang.brigadier.arguments.ArgumentType;
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*    */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.EnumSet;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.core.Direction;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class SwizzleArgument implements ArgumentType<EnumSet<Direction.Axis>> {
/* 17 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "xyz", "x" });
/* 18 */   private static final SimpleCommandExceptionType ERROR_INVALID = new SimpleCommandExceptionType((Message)Component.translatable("arguments.swizzle.invalid"));
/*    */   
/*    */   public static SwizzleArgument swizzle() {
/* 21 */     return new SwizzleArgument();
/*    */   }
/*    */ 
/*    */   
/*    */   public static EnumSet<Direction.Axis> getSwizzle(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 26 */     return (EnumSet<Direction.Axis>)$$0.getArgument($$1, EnumSet.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumSet<Direction.Axis> parse(StringReader $$0) throws CommandSyntaxException {
/* 31 */     EnumSet<Direction.Axis> $$1 = EnumSet.noneOf(Direction.Axis.class);
/*    */     
/* 33 */     while ($$0.canRead() && $$0.peek() != ' ') {
/* 34 */       Direction.Axis $$3, $$4, $$5; char $$2 = $$0.read();
/*    */ 
/*    */       
/* 37 */       switch ($$2) {
/*    */         case 'x':
/* 39 */           $$3 = Direction.Axis.X;
/*    */           break;
/*    */         case 'y':
/* 42 */           $$4 = Direction.Axis.Y;
/*    */           break;
/*    */         case 'z':
/* 45 */           $$5 = Direction.Axis.Z;
/*    */           break;
/*    */         default:
/* 48 */           throw ERROR_INVALID.create();
/*    */       } 
/*    */       
/* 51 */       if ($$1.contains($$5)) {
/* 52 */         throw ERROR_INVALID.create();
/*    */       }
/* 54 */       $$1.add($$5);
/*    */     } 
/*    */     
/* 57 */     return $$1;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 62 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\SwizzleArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */