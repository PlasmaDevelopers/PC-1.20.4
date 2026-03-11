/*    */ package net.minecraft.world.level.pathfinder;
/*    */ 
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ 
/*    */ public class Target extends Node {
/*  6 */   private float bestHeuristic = Float.MAX_VALUE;
/*    */   private Node bestNode;
/*    */   private boolean reached;
/*    */   
/*    */   public Target(Node $$0) {
/* 11 */     super($$0.x, $$0.y, $$0.z);
/*    */   }
/*    */   
/*    */   public Target(int $$0, int $$1, int $$2) {
/* 15 */     super($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   public void updateBest(float $$0, Node $$1) {
/* 19 */     if ($$0 < this.bestHeuristic) {
/* 20 */       this.bestHeuristic = $$0;
/* 21 */       this.bestNode = $$1;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Node getBestNode() {
/* 26 */     return this.bestNode;
/*    */   }
/*    */   
/*    */   public void setReached() {
/* 30 */     this.reached = true;
/*    */   }
/*    */   
/*    */   public boolean isReached() {
/* 34 */     return this.reached;
/*    */   }
/*    */   
/*    */   public static Target createFromStream(FriendlyByteBuf $$0) {
/* 38 */     Target $$1 = new Target($$0.readInt(), $$0.readInt(), $$0.readInt());
/* 39 */     readContents($$0, $$1);
/* 40 */     return $$1;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\Target.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */