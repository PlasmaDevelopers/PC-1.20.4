/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ import java.util.Set;
/*    */ import net.minecraft.core.Direction;
/*    */ 
/*    */ public class VisibilitySet
/*    */ {
/*  9 */   private static final int FACINGS = (Direction.values()).length;
/*    */   
/* 11 */   private final BitSet data = new BitSet(FACINGS * FACINGS);
/*    */   
/*    */   public void add(Set<Direction> $$0) {
/* 14 */     for (Direction $$1 : $$0) {
/* 15 */       for (Direction $$2 : $$0) {
/* 16 */         set($$1, $$2, true);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void set(Direction $$0, Direction $$1, boolean $$2) {
/* 22 */     this.data.set($$0.ordinal() + $$1.ordinal() * FACINGS, $$2);
/* 23 */     this.data.set($$1.ordinal() + $$0.ordinal() * FACINGS, $$2);
/*    */   }
/*    */   
/*    */   public void setAll(boolean $$0) {
/* 27 */     this.data.set(0, this.data.size(), $$0);
/*    */   }
/*    */   
/*    */   public boolean visibilityBetween(Direction $$0, Direction $$1) {
/* 31 */     return this.data.get($$0.ordinal() + $$1.ordinal() * FACINGS);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     StringBuilder $$0 = new StringBuilder();
/* 37 */     $$0.append(' ');
/* 38 */     for (Direction $$1 : Direction.values()) {
/* 39 */       $$0.append(' ').append($$1.toString().toUpperCase().charAt(0));
/*    */     }
/* 41 */     $$0.append('\n');
/*    */     
/* 43 */     for (Direction $$2 : Direction.values()) {
/* 44 */       $$0.append($$2.toString().toUpperCase().charAt(0));
/* 45 */       for (Direction $$3 : Direction.values()) {
/* 46 */         if ($$2 == $$3) {
/* 47 */           $$0.append("  ");
/*    */         } else {
/* 49 */           boolean $$4 = visibilityBetween($$2, $$3);
/* 50 */           $$0.append(' ').append($$4 ? 89 : 110);
/*    */         } 
/*    */       } 
/* 53 */       $$0.append('\n');
/*    */     } 
/*    */     
/* 56 */     return $$0.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\VisibilitySet.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */