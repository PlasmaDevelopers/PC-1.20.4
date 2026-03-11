/*    */ package net.minecraft.world.entity.ai.attributes;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
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
/*    */ public enum Operation
/*    */   implements StringRepresentable
/*    */ {
/*    */   private static final Operation[] OPERATIONS;
/*    */   public static final Codec<Operation> CODEC;
/* 21 */   ADDITION("addition", 0),
/* 22 */   MULTIPLY_BASE("multiply_base", 1),
/* 23 */   MULTIPLY_TOTAL("multiply_total", 2);
/*    */   static {
/* 25 */     OPERATIONS = new Operation[] { ADDITION, MULTIPLY_BASE, MULTIPLY_TOTAL };
/*    */     
/* 27 */     CODEC = (Codec<Operation>)StringRepresentable.fromEnum(Operation::values);
/*    */   }
/*    */   private final String name;
/*    */   private final int value;
/*    */   
/*    */   Operation(String $$0, int $$1) {
/* 33 */     this.name = $$0;
/* 34 */     this.value = $$1;
/*    */   }
/*    */   
/*    */   public int toValue() {
/* 38 */     return this.value;
/*    */   }
/*    */   
/*    */   public static Operation fromValue(int $$0) {
/* 42 */     if ($$0 < 0 || $$0 >= OPERATIONS.length) {
/* 43 */       throw new IllegalArgumentException("No operation with value " + $$0);
/*    */     }
/*    */     
/* 46 */     return OPERATIONS[$$0];
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 51 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\attributes\AttributeModifier$Operation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */