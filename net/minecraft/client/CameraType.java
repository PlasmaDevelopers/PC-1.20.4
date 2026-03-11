/*    */ package net.minecraft.client;
/*    */ public enum CameraType {
/*    */   private static final CameraType[] VALUES;
/*  4 */   FIRST_PERSON(true, false),
/*  5 */   THIRD_PERSON_BACK(false, false),
/*  6 */   THIRD_PERSON_FRONT(false, true);
/*    */   
/*    */   static {
/*  9 */     VALUES = values();
/*    */   }
/*    */   private final boolean firstPerson;
/*    */   private final boolean mirrored;
/*    */   
/*    */   CameraType(boolean $$0, boolean $$1) {
/* 15 */     this.firstPerson = $$0;
/* 16 */     this.mirrored = $$1;
/*    */   }
/*    */   
/*    */   public boolean isFirstPerson() {
/* 20 */     return this.firstPerson;
/*    */   }
/*    */   
/*    */   public boolean isMirrored() {
/* 24 */     return this.mirrored;
/*    */   }
/*    */   
/*    */   public CameraType cycle() {
/* 28 */     return VALUES[(ordinal() + 1) % VALUES.length];
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\CameraType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */