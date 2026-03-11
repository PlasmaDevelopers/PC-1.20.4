/*    */ package net.minecraft.world.item;
/*    */ import com.mojang.serialization.Codec;
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum ItemDisplayContext implements StringRepresentable {
/*    */   public static final Codec<ItemDisplayContext> CODEC;
/*    */   public static final IntFunction<ItemDisplayContext> BY_ID;
/* 10 */   NONE(0, "none"),
/* 11 */   THIRD_PERSON_LEFT_HAND(1, "thirdperson_lefthand"),
/* 12 */   THIRD_PERSON_RIGHT_HAND(2, "thirdperson_righthand"),
/* 13 */   FIRST_PERSON_LEFT_HAND(3, "firstperson_lefthand"),
/* 14 */   FIRST_PERSON_RIGHT_HAND(4, "firstperson_righthand"),
/* 15 */   HEAD(5, "head"),
/* 16 */   GUI(6, "gui"),
/* 17 */   GROUND(7, "ground"),
/* 18 */   FIXED(8, "fixed");
/*    */   
/*    */   static {
/* 21 */     CODEC = (Codec<ItemDisplayContext>)StringRepresentable.fromEnum(ItemDisplayContext::values);
/* 22 */     BY_ID = ByIdMap.continuous(ItemDisplayContext::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*    */   }
/*    */   private final byte id;
/*    */   private final String name;
/*    */   
/*    */   ItemDisplayContext(int $$0, String $$1) {
/* 28 */     this.name = $$1;
/* 29 */     this.id = (byte)$$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 34 */     return this.name;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 38 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean firstPerson() {
/* 42 */     return (this == FIRST_PERSON_LEFT_HAND || this == FIRST_PERSON_RIGHT_HAND);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\ItemDisplayContext.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */