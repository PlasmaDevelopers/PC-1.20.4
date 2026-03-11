/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.world.level.block.Rotation;
/*    */ 
/*    */ public class TemplateRotationArgument extends StringRepresentableArgument<Rotation> {
/*    */   private TemplateRotationArgument() {
/*  9 */     super(Rotation.CODEC, Rotation::values);
/*    */   }
/*    */   
/*    */   public static TemplateRotationArgument templateRotation() {
/* 13 */     return new TemplateRotationArgument();
/*    */   }
/*    */   
/*    */   public static Rotation getRotation(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 17 */     return (Rotation)$$0.getArgument($$1, Rotation.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\TemplateRotationArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */