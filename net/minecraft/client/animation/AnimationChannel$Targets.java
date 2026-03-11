/*    */ package net.minecraft.client.animation;
/*    */ 
/*    */ import net.minecraft.client.model.geom.ModelPart;
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
/*    */ public class Targets
/*    */ {
/* 21 */   public static final AnimationChannel.Target POSITION = ModelPart::offsetPos;
/* 22 */   public static final AnimationChannel.Target ROTATION = ModelPart::offsetRotation;
/* 23 */   public static final AnimationChannel.Target SCALE = ModelPart::offsetScale;
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\animation\AnimationChannel$Targets.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */