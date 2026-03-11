/*    */ package net.minecraft.world.entity;
/*    */ 
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum EquipmentSlot implements StringRepresentable {
/*  6 */   MAINHAND(Type.HAND, 0, 0, "mainhand"),
/*  7 */   OFFHAND(Type.HAND, 1, 5, "offhand"),
/*  8 */   FEET(Type.ARMOR, 0, 1, "feet"),
/*  9 */   LEGS(Type.ARMOR, 1, 2, "legs"),
/* 10 */   CHEST(Type.ARMOR, 2, 3, "chest"),
/* 11 */   HEAD(Type.ARMOR, 3, 4, "head"); public static final StringRepresentable.EnumCodec<EquipmentSlot> CODEC;
/*    */   static {
/* 13 */     CODEC = StringRepresentable.fromEnum(EquipmentSlot::values);
/*    */   }
/*    */   private final Type type;
/*    */   private final int index;
/*    */   private final int filterFlag;
/*    */   private final String name;
/*    */   
/*    */   EquipmentSlot(Type $$0, int $$1, int $$2, String $$3) {
/* 21 */     this.type = $$0;
/* 22 */     this.index = $$1;
/* 23 */     this.filterFlag = $$2;
/* 24 */     this.name = $$3;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 28 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getIndex() {
/* 32 */     return this.index;
/*    */   }
/*    */   
/*    */   public int getIndex(int $$0) {
/* 36 */     return $$0 + this.index;
/*    */   }
/*    */   
/*    */   public int getFilterFlag() {
/* 40 */     return this.filterFlag;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 44 */     return this.name;
/*    */   }
/*    */   
/*    */   public boolean isArmor() {
/* 48 */     return (this.type == Type.ARMOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 53 */     return this.name;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 57 */     HAND,
/* 58 */     ARMOR;
/*    */   }
/*    */   
/*    */   public static EquipmentSlot byName(String $$0) {
/* 62 */     EquipmentSlot $$1 = (EquipmentSlot)CODEC.byName($$0);
/* 63 */     if ($$1 != null) {
/* 64 */       return $$1;
/*    */     }
/* 66 */     throw new IllegalArgumentException("Invalid slot '" + $$0 + "'");
/*    */   }
/*    */   
/*    */   public static EquipmentSlot byTypeAndIndex(Type $$0, int $$1) {
/* 70 */     for (EquipmentSlot $$2 : values()) {
/* 71 */       if ($$2.getType() == $$0 && $$2.getIndex() == $$1) {
/* 72 */         return $$2;
/*    */       }
/*    */     } 
/*    */     
/* 76 */     throw new IllegalArgumentException("Invalid slot '" + $$0 + "': " + $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\EquipmentSlot.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */