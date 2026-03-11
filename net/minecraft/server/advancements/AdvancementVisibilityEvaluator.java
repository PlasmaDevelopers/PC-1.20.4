/*    */ package net.minecraft.server.advancements;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.Stack;
/*    */ import it.unimi.dsi.fastutil.objects.ObjectArrayList;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.advancements.AdvancementNode;
/*    */ import net.minecraft.advancements.DisplayInfo;
/*    */ 
/*    */ public class AdvancementVisibilityEvaluator
/*    */ {
/*    */   private static final int VISIBILITY_DEPTH = 2;
/*    */   
/*    */   private enum VisibilityRule {
/* 16 */     SHOW,
/* 17 */     HIDE,
/* 18 */     NO_CHANGE;
/*    */   }
/*    */   
/*    */   private static VisibilityRule evaluateVisibilityRule(Advancement $$0, boolean $$1) {
/* 22 */     Optional<DisplayInfo> $$2 = $$0.display();
/* 23 */     if ($$2.isEmpty()) {
/* 24 */       return VisibilityRule.HIDE;
/*    */     }
/* 26 */     if ($$1) {
/* 27 */       return VisibilityRule.SHOW;
/*    */     }
/* 29 */     if (((DisplayInfo)$$2.get()).isHidden()) {
/* 30 */       return VisibilityRule.HIDE;
/*    */     }
/* 32 */     return VisibilityRule.NO_CHANGE;
/*    */   }
/*    */   
/*    */   private static boolean evaluateVisiblityForUnfinishedNode(Stack<VisibilityRule> $$0) {
/* 36 */     for (int $$1 = 0; $$1 <= 2; $$1++) {
/* 37 */       VisibilityRule $$2 = (VisibilityRule)$$0.peek($$1);
/* 38 */       if ($$2 == VisibilityRule.SHOW)
/* 39 */         return true; 
/* 40 */       if ($$2 == VisibilityRule.HIDE) {
/* 41 */         return false;
/*    */       }
/*    */     } 
/* 44 */     return false;
/*    */   }
/*    */   
/*    */   private static boolean evaluateVisibility(AdvancementNode $$0, Stack<VisibilityRule> $$1, Predicate<AdvancementNode> $$2, Output $$3) {
/* 48 */     boolean $$4 = $$2.test($$0);
/* 49 */     VisibilityRule $$5 = evaluateVisibilityRule($$0.advancement(), $$4);
/*    */     
/* 51 */     boolean $$6 = $$4;
/* 52 */     $$1.push($$5);
/* 53 */     for (AdvancementNode $$7 : $$0.children()) {
/* 54 */       $$6 |= evaluateVisibility($$7, $$1, $$2, $$3);
/*    */     }
/*    */     
/* 57 */     boolean $$8 = ($$6 || evaluateVisiblityForUnfinishedNode($$1));
/* 58 */     $$1.pop();
/*    */     
/* 60 */     $$3.accept($$0, $$8);
/* 61 */     return $$6;
/*    */   }
/*    */   
/*    */   public static void evaluateVisibility(AdvancementNode $$0, Predicate<AdvancementNode> $$1, Output $$2) {
/* 65 */     AdvancementNode $$3 = $$0.root();
/*    */     
/* 67 */     ObjectArrayList objectArrayList = new ObjectArrayList();
/*    */     
/* 69 */     for (int $$5 = 0; $$5 <= 2; $$5++) {
/* 70 */       objectArrayList.push(VisibilityRule.NO_CHANGE);
/*    */     }
/* 72 */     evaluateVisibility($$3, (Stack<VisibilityRule>)objectArrayList, $$1, $$2);
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface Output {
/*    */     void accept(AdvancementNode param1AdvancementNode, boolean param1Boolean);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\advancements\AdvancementVisibilityEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */