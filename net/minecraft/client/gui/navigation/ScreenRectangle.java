/*    */ package net.minecraft.client.gui.navigation;public final class ScreenRectangle extends Record { private final ScreenPosition position;
/*    */   private final int width;
/*    */   private final int height;
/*    */   
/*  5 */   public ScreenRectangle(ScreenPosition $$0, int $$1, int $$2) { this.position = $$0; this.width = $$1; this.height = $$2; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/client/gui/navigation/ScreenRectangle;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*  5 */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/ScreenRectangle; } public ScreenPosition position() { return this.position; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/client/gui/navigation/ScreenRectangle;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/client/gui/navigation/ScreenRectangle; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/client/gui/navigation/ScreenRectangle;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #5	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/client/gui/navigation/ScreenRectangle;
/*  5 */     //   0	8	1	$$0	Ljava/lang/Object; } public int width() { return this.width; } public int height() { return this.height; }
/*  6 */    private static final ScreenRectangle EMPTY = new ScreenRectangle(0, 0, 0, 0);
/*    */   
/*    */   public ScreenRectangle(int $$0, int $$1, int $$2, int $$3) {
/*  9 */     this(new ScreenPosition($$0, $$1), $$2, $$3);
/*    */   }
/*    */   
/*    */   public static ScreenRectangle empty() {
/* 13 */     return EMPTY;
/*    */   }
/*    */   
/*    */   public static ScreenRectangle of(ScreenAxis $$0, int $$1, int $$2, int $$3, int $$4) {
/* 17 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case HORIZONTAL: case VERTICAL: break; }  return 
/*    */       
/* 19 */       new ScreenRectangle($$2, $$1, $$4, $$3);
/*    */   }
/*    */ 
/*    */   
/*    */   public ScreenRectangle step(ScreenDirection $$0) {
/* 24 */     return new ScreenRectangle(this.position.step($$0), this.width, this.height);
/*    */   }
/*    */   
/*    */   public int getLength(ScreenAxis $$0) {
/* 28 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case HORIZONTAL: case VERTICAL: break; }  return 
/*    */       
/* 30 */       this.height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBoundInDirection(ScreenDirection $$0) {
/* 35 */     ScreenAxis $$1 = $$0.getAxis();
/* 36 */     if ($$0.isPositive()) {
/* 37 */       return this.position.getCoordinate($$1) + getLength($$1) - 1;
/*    */     }
/* 39 */     return this.position.getCoordinate($$1);
/*    */   }
/*    */   
/*    */   public ScreenRectangle getBorder(ScreenDirection $$0) {
/* 43 */     int $$1 = getBoundInDirection($$0);
/*    */     
/* 45 */     ScreenAxis $$2 = $$0.getAxis().orthogonal();
/* 46 */     int $$3 = getBoundInDirection($$2.getNegative());
/* 47 */     int $$4 = getLength($$2);
/*    */     
/* 49 */     return of($$0.getAxis(), $$1, $$3, 1, $$4).step($$0);
/*    */   }
/*    */   
/*    */   public boolean overlaps(ScreenRectangle $$0) {
/* 53 */     return (overlapsInAxis($$0, ScreenAxis.HORIZONTAL) && overlapsInAxis($$0, ScreenAxis.VERTICAL));
/*    */   }
/*    */   
/*    */   public boolean overlapsInAxis(ScreenRectangle $$0, ScreenAxis $$1) {
/* 57 */     int $$2 = getBoundInDirection($$1.getNegative());
/* 58 */     int $$3 = $$0.getBoundInDirection($$1.getNegative());
/* 59 */     int $$4 = getBoundInDirection($$1.getPositive());
/* 60 */     int $$5 = $$0.getBoundInDirection($$1.getPositive());
/* 61 */     return (Math.max($$2, $$3) <= Math.min($$4, $$5));
/*    */   }
/*    */   
/*    */   public int getCenterInAxis(ScreenAxis $$0) {
/* 65 */     return (getBoundInDirection($$0.getPositive()) + getBoundInDirection($$0.getNegative())) / 2;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public ScreenRectangle intersection(ScreenRectangle $$0) {
/* 70 */     int $$1 = Math.max(left(), $$0.left());
/* 71 */     int $$2 = Math.max(top(), $$0.top());
/* 72 */     int $$3 = Math.min(right(), $$0.right());
/* 73 */     int $$4 = Math.min(bottom(), $$0.bottom());
/* 74 */     if ($$1 >= $$3 || $$2 >= $$4) {
/* 75 */       return null;
/*    */     }
/* 77 */     return new ScreenRectangle($$1, $$2, $$3 - $$1, $$4 - $$2);
/*    */   }
/*    */   
/*    */   public int top() {
/* 81 */     return this.position.y();
/*    */   }
/*    */   
/*    */   public int bottom() {
/* 85 */     return this.position.y() + this.height;
/*    */   }
/*    */   
/*    */   public int left() {
/* 89 */     return this.position.x();
/*    */   }
/*    */   
/*    */   public int right() {
/* 93 */     return this.position.x() + this.width;
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\gui\navigation\ScreenRectangle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */