/*    */ package net.minecraft.commands.arguments;
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
/*    */ import net.minecraft.commands.arguments.coordinates.WorldCoordinate;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.Mth;
/*    */ 
/*    */ public class AngleArgument implements ArgumentType<AngleArgument.SingleAngle> {
/* 17 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0", "~", "~-5" });
/* 18 */   public static final SimpleCommandExceptionType ERROR_NOT_COMPLETE = new SimpleCommandExceptionType((Message)Component.translatable("argument.angle.incomplete"));
/* 19 */   public static final SimpleCommandExceptionType ERROR_INVALID_ANGLE = new SimpleCommandExceptionType((Message)Component.translatable("argument.angle.invalid"));
/*    */   
/*    */   public static AngleArgument angle() {
/* 22 */     return new AngleArgument();
/*    */   }
/*    */   
/*    */   public static float getAngle(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 26 */     return ((SingleAngle)$$0.getArgument($$1, SingleAngle.class)).getAngle((CommandSourceStack)$$0.getSource());
/*    */   }
/*    */ 
/*    */   
/*    */   public SingleAngle parse(StringReader $$0) throws CommandSyntaxException {
/* 31 */     if (!$$0.canRead()) {
/* 32 */       throw ERROR_NOT_COMPLETE.createWithContext($$0);
/*    */     }
/*    */     
/* 35 */     boolean $$1 = WorldCoordinate.isRelative($$0);
/* 36 */     float $$2 = ($$0.canRead() && $$0.peek() != ' ') ? $$0.readFloat() : 0.0F;
/* 37 */     if (Float.isNaN($$2) || Float.isInfinite($$2)) {
/* 38 */       throw ERROR_INVALID_ANGLE.createWithContext($$0);
/*    */     }
/* 40 */     return new SingleAngle($$2, $$1);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getExamples() {
/* 45 */     return EXAMPLES;
/*    */   }
/*    */   
/*    */   public static final class SingleAngle {
/*    */     private final float angle;
/*    */     private final boolean isRelative;
/*    */     
/*    */     SingleAngle(float $$0, boolean $$1) {
/* 53 */       this.angle = $$0;
/* 54 */       this.isRelative = $$1;
/*    */     }
/*    */     
/*    */     public float getAngle(CommandSourceStack $$0) {
/* 58 */       return Mth.wrapDegrees(this.isRelative ? (this.angle + ($$0.getRotation()).y) : this.angle);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\AngleArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */