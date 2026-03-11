/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import com.mojang.brigadier.context.CommandContext;
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.world.level.block.Mirror;
/*    */ 
/*    */ public class TemplateMirrorArgument extends StringRepresentableArgument<Mirror> {
/*    */   private TemplateMirrorArgument() {
/*  9 */     super(Mirror.CODEC, Mirror::values);
/*    */   }
/*    */   
/*    */   public static StringRepresentableArgument<Mirror> templateMirror() {
/* 13 */     return new TemplateMirrorArgument();
/*    */   }
/*    */   
/*    */   public static Mirror getMirror(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 17 */     return (Mirror)$$0.getArgument($$1, Mirror.class);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\TemplateMirrorArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */