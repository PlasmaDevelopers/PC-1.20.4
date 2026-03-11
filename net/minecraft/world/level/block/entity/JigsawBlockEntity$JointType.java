/*    */ package net.minecraft.world.level.block.entity;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.network.chat.Component;
/*    */ import net.minecraft.util.StringRepresentable;
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
/*    */ public enum JointType
/*    */   implements StringRepresentable
/*    */ {
/* 24 */   ROLLABLE("rollable"),
/* 25 */   ALIGNED("aligned");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   JointType(String $$0) {
/* 30 */     this.name = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 35 */     return this.name;
/*    */   }
/*    */   
/*    */   public static Optional<JointType> byName(String $$0) {
/* 39 */     return Arrays.<JointType>stream(values()).filter($$1 -> $$1.getSerializedName().equals($$0)).findFirst();
/*    */   }
/*    */   
/*    */   public Component getTranslatedName() {
/* 43 */     return (Component)Component.translatable("jigsaw_block.joint." + this.name);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\JigsawBlockEntity$JointType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */