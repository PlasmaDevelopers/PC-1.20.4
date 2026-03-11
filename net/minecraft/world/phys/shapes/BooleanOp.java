/*    */ package net.minecraft.world.phys.shapes;
/*    */ 
/*    */ public interface BooleanOp {
/*    */   static {
/*  5 */     NOT_OR = (($$0, $$1) -> (!$$0 && !$$1));
/*  6 */     ONLY_SECOND = (($$0, $$1) -> ($$1 && !$$0));
/*  7 */     NOT_FIRST = (($$0, $$1) -> !$$0);
/*  8 */     ONLY_FIRST = (($$0, $$1) -> ($$0 && !$$1));
/*  9 */     NOT_SECOND = (($$0, $$1) -> !$$1);
/* 10 */     NOT_SAME = (($$0, $$1) -> ($$0 != $$1));
/* 11 */     NOT_AND = (($$0, $$1) -> (!$$0 || !$$1));
/* 12 */     AND = (($$0, $$1) -> ($$0 && $$1));
/* 13 */     SAME = (($$0, $$1) -> ($$0 == $$1));
/* 14 */     SECOND = (($$0, $$1) -> $$1);
/* 15 */     CAUSES = (($$0, $$1) -> (!$$0 || $$1));
/* 16 */     FIRST = (($$0, $$1) -> $$0);
/* 17 */     CAUSED_BY = (($$0, $$1) -> ($$0 || !$$1));
/* 18 */     OR = (($$0, $$1) -> ($$0 || $$1));
/*    */   }
/*    */   
/*    */   public static final BooleanOp FALSE = ($$0, $$1) -> false;
/*    */   public static final BooleanOp NOT_OR;
/*    */   public static final BooleanOp ONLY_SECOND;
/*    */   public static final BooleanOp NOT_FIRST;
/*    */   public static final BooleanOp ONLY_FIRST;
/*    */   public static final BooleanOp NOT_SECOND;
/*    */   public static final BooleanOp NOT_SAME;
/*    */   public static final BooleanOp NOT_AND;
/*    */   public static final BooleanOp AND;
/*    */   public static final BooleanOp SAME;
/*    */   public static final BooleanOp SECOND;
/*    */   public static final BooleanOp CAUSES;
/*    */   public static final BooleanOp FIRST;
/*    */   public static final BooleanOp CAUSED_BY;
/*    */   public static final BooleanOp OR;
/*    */   public static final BooleanOp TRUE = ($$0, $$1) -> true;
/*    */   
/*    */   boolean apply(boolean paramBoolean1, boolean paramBoolean2);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\phys\shapes\BooleanOp.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */