/*    */ package net.minecraft.world.item;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
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
/*    */ public enum Shape
/*    */ {
/*    */   private static final IntFunction<Shape> BY_ID;
/* 46 */   SMALL_BALL(0, "small_ball"),
/* 47 */   LARGE_BALL(1, "large_ball"),
/* 48 */   STAR(2, "star"),
/* 49 */   CREEPER(3, "creeper"),
/* 50 */   BURST(4, "burst");
/*    */   
/*    */   static {
/* 53 */     BY_ID = ByIdMap.continuous(Shape::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   Shape(int $$0, String $$1) {
/* 59 */     this.id = $$0;
/* 60 */     this.name = $$1;
/*    */   }
/*    */   private final String name;
/*    */   public int getId() {
/* 64 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 68 */     return this.name;
/*    */   }
/*    */   
/*    */   public static Shape byId(int $$0) {
/* 72 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FireworkRocketItem$Shape.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */