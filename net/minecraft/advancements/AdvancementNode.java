/*    */ package net.minecraft.advancements;
/*    */ 
/*    */ import com.google.common.annotations.VisibleForTesting;
/*    */ import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class AdvancementNode
/*    */ {
/*    */   private final AdvancementHolder holder;
/*    */   @Nullable
/*    */   private final AdvancementNode parent;
/* 13 */   private final Set<AdvancementNode> children = (Set<AdvancementNode>)new ReferenceOpenHashSet();
/*    */   
/*    */   @VisibleForTesting
/*    */   public AdvancementNode(AdvancementHolder $$0, @Nullable AdvancementNode $$1) {
/* 17 */     this.holder = $$0;
/* 18 */     this.parent = $$1;
/*    */   }
/*    */   
/*    */   public Advancement advancement() {
/* 22 */     return this.holder.value();
/*    */   }
/*    */   
/*    */   public AdvancementHolder holder() {
/* 26 */     return this.holder;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AdvancementNode parent() {
/* 31 */     return this.parent;
/*    */   }
/*    */   
/*    */   public AdvancementNode root() {
/* 35 */     return getRoot(this);
/*    */   }
/*    */   
/*    */   public static AdvancementNode getRoot(AdvancementNode $$0) {
/* 39 */     AdvancementNode $$1 = $$0;
/*    */     while (true) {
/* 41 */       AdvancementNode $$2 = $$1.parent();
/* 42 */       if ($$2 == null) {
/* 43 */         return $$1;
/*    */       }
/* 45 */       $$1 = $$2;
/*    */     } 
/*    */   }
/*    */   
/*    */   public Iterable<AdvancementNode> children() {
/* 50 */     return this.children;
/*    */   }
/*    */   
/*    */   @VisibleForTesting
/*    */   public void addChild(AdvancementNode $$0) {
/* 55 */     this.children.add($$0);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 60 */     if (this == $$0) {
/* 61 */       return true;
/*    */     }
/* 63 */     if ($$0 instanceof AdvancementNode) { AdvancementNode $$1 = (AdvancementNode)$$0; if (this.holder.equals($$1.holder)); }  return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     return this.holder.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 73 */     return this.holder.id().toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\AdvancementNode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */