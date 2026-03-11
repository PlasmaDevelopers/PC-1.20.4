/*    */ package net.minecraft.world.entity;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum HumanoidArm implements OptionEnum, StringRepresentable {
/*    */   public static final Codec<HumanoidArm> CODEC;
/*    */   public static final IntFunction<HumanoidArm> BY_ID;
/* 11 */   LEFT(0, "left", "options.mainHand.left"),
/* 12 */   RIGHT(1, "right", "options.mainHand.right");
/*    */   
/*    */   static {
/* 15 */     CODEC = (Codec<HumanoidArm>)StringRepresentable.fromEnum(HumanoidArm::values);
/*    */     
/* 17 */     BY_ID = ByIdMap.continuous(HumanoidArm::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*    */   }
/*    */   private final int id;
/*    */   private final String name;
/*    */   private final String translationKey;
/*    */   
/*    */   HumanoidArm(int $$0, String $$1, String $$2) {
/* 24 */     this.id = $$0;
/* 25 */     this.name = $$1;
/* 26 */     this.translationKey = $$2;
/*    */   }
/*    */   
/*    */   public HumanoidArm getOpposite() {
/* 30 */     if (this == LEFT) {
/* 31 */       return RIGHT;
/*    */     }
/* 33 */     return LEFT;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 38 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 43 */     return this.translationKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 48 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\HumanoidArm.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */