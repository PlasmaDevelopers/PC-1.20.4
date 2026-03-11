/*    */ package net.minecraft.world.entity.animal.horse;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ 
/*    */ public enum Markings
/*    */ {
/*  8 */   NONE(0),
/*  9 */   WHITE(1),
/* 10 */   WHITE_FIELD(2),
/* 11 */   WHITE_DOTS(3),
/* 12 */   BLACK_DOTS(4); private static final IntFunction<Markings> BY_ID;
/*    */   
/*    */   static {
/* 15 */     BY_ID = ByIdMap.continuous(Markings::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   private final int id;
/*    */   Markings(int $$0) {
/* 19 */     this.id = $$0;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 23 */     return this.id;
/*    */   }
/*    */   
/*    */   public static Markings byId(int $$0) {
/* 27 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\animal\horse\Markings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */