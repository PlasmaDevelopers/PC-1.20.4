/*    */ package net.minecraft.commands.arguments;
/*    */ 
/*    */ import net.minecraft.commands.CommandSourceStack;
/*    */ import net.minecraft.util.Mth;
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
/*    */ 
/*    */ 
/*    */ public final class SingleAngle
/*    */ {
/*    */   private final float angle;
/*    */   private final boolean isRelative;
/*    */   
/*    */   SingleAngle(float $$0, boolean $$1) {
/* 53 */     this.angle = $$0;
/* 54 */     this.isRelative = $$1;
/*    */   }
/*    */   
/*    */   public float getAngle(CommandSourceStack $$0) {
/* 58 */     return Mth.wrapDegrees(this.isRelative ? (this.angle + ($$0.getRotation()).y) : this.angle);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\AngleArgument$SingleAngle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */