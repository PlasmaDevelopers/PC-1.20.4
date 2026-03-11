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
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.network.chat.Component;
/*    */ 
/*    */ public class RotationArgument implements ArgumentType<Coordinates> {
/* 15 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0 0", "~ ~", "~-5 ~5" });
/* 16 */   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.translatable("argument.rotation.incomplete"));
/*    */   
/*    */   public static RotationArgument rotation() {
/* 19 */     return new RotationArgument();
/*    */   }
/*    */   
/*    */   public static Coordinates getRotation(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 23 */     return (Coordinates)$$0.getArgument($$1, Coordinates.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Coordinates parse(StringReader $$0) throws CommandSyntaxException {
/* 28 */     int $$1 = $$0.getCursor();
/* 29 */     if (!$$0.canRead()) {
/* 30 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     }
/* 32 */     WorldCoordinate $$2 = WorldCoordinate.parseDouble($$0, false);
/* 33 */     if (!$$0.canRead() || $$0.peek() != ' ') {
/* 34 */       $$0.setCursor($$1);
/* 35 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     } 
/* 37 */     $$0.skip();
/* 38 */     WorldCoordinate $$3 = WorldCoordinate.parseDouble($$0, false);
/* 39 */     return new WorldCoordinates($$3, $$2, new WorldCoordinate(true, 0.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 44 */     return EXAMPLES;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\coordinates\RotationArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */