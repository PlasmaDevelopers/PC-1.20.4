/*     */ package net.minecraft.advancements;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class TreeNodePosition
/*     */ {
/*     */   private final AdvancementNode node;
/*     */   @Nullable
/*     */   private final TreeNodePosition parent;
/*     */   @Nullable
/*     */   private final TreeNodePosition previousSibling;
/*     */   private final int childIndex;
/*  15 */   private final List<TreeNodePosition> children = Lists.newArrayList();
/*     */   private TreeNodePosition ancestor;
/*     */   @Nullable
/*     */   private TreeNodePosition thread;
/*     */   private int x;
/*     */   private float y;
/*     */   private float mod;
/*     */   private float change;
/*     */   private float shift;
/*     */   
/*     */   public TreeNodePosition(AdvancementNode $$0, @Nullable TreeNodePosition $$1, @Nullable TreeNodePosition $$2, int $$3, int $$4) {
/*  26 */     if ($$0.advancement().display().isEmpty()) {
/*  27 */       throw new IllegalArgumentException("Can't position an invisible advancement!");
/*     */     }
/*  29 */     this.node = $$0;
/*  30 */     this.parent = $$1;
/*  31 */     this.previousSibling = $$2;
/*  32 */     this.childIndex = $$3;
/*  33 */     this.ancestor = this;
/*  34 */     this.x = $$4;
/*  35 */     this.y = -1.0F;
/*     */     
/*  37 */     TreeNodePosition $$5 = null;
/*  38 */     for (AdvancementNode $$6 : $$0.children()) {
/*  39 */       $$5 = addChild($$6, $$5);
/*     */     }
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private TreeNodePosition addChild(AdvancementNode $$0, @Nullable TreeNodePosition $$1) {
/*  45 */     if ($$0.advancement().display().isPresent()) {
/*  46 */       $$1 = new TreeNodePosition($$0, this, $$1, this.children.size() + 1, this.x + 1);
/*  47 */       this.children.add($$1);
/*     */     } else {
/*  49 */       for (AdvancementNode $$2 : $$0.children()) {
/*  50 */         $$1 = addChild($$2, $$1);
/*     */       }
/*     */     } 
/*  53 */     return $$1;
/*     */   }
/*     */   
/*     */   private void firstWalk() {
/*  57 */     if (this.children.isEmpty()) {
/*  58 */       if (this.previousSibling != null) {
/*  59 */         this.previousSibling.y++;
/*     */       } else {
/*  61 */         this.y = 0.0F;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  66 */     TreeNodePosition $$0 = null;
/*  67 */     for (TreeNodePosition $$1 : this.children) {
/*  68 */       $$1.firstWalk();
/*  69 */       $$0 = $$1.apportion(($$0 == null) ? $$1 : $$0);
/*     */     } 
/*  71 */     executeShifts();
/*     */     
/*  73 */     float $$2 = (((TreeNodePosition)this.children.get(0)).y + ((TreeNodePosition)this.children.get(this.children.size() - 1)).y) / 2.0F;
/*  74 */     if (this.previousSibling != null) {
/*  75 */       this.previousSibling.y++;
/*  76 */       this.mod = this.y - $$2;
/*     */     } else {
/*  78 */       this.y = $$2;
/*     */     } 
/*     */   }
/*     */   
/*     */   private float secondWalk(float $$0, int $$1, float $$2) {
/*  83 */     this.y += $$0;
/*  84 */     this.x = $$1;
/*     */     
/*  86 */     if (this.y < $$2) {
/*  87 */       $$2 = this.y;
/*     */     }
/*     */     
/*  90 */     for (TreeNodePosition $$3 : this.children) {
/*  91 */       $$2 = $$3.secondWalk($$0 + this.mod, $$1 + 1, $$2);
/*     */     }
/*     */     
/*  94 */     return $$2;
/*     */   }
/*     */   
/*     */   private void thirdWalk(float $$0) {
/*  98 */     this.y += $$0;
/*  99 */     for (TreeNodePosition $$1 : this.children) {
/* 100 */       $$1.thirdWalk($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   private void executeShifts() {
/* 105 */     float $$0 = 0.0F;
/* 106 */     float $$1 = 0.0F;
/* 107 */     for (int $$2 = this.children.size() - 1; $$2 >= 0; $$2--) {
/* 108 */       TreeNodePosition $$3 = this.children.get($$2);
/* 109 */       $$3.y += $$0;
/* 110 */       $$3.mod += $$0;
/* 111 */       $$1 += $$3.change;
/* 112 */       $$0 += $$3.shift + $$1;
/*     */     } 
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private TreeNodePosition previousOrThread() {
/* 118 */     if (this.thread != null) {
/* 119 */       return this.thread;
/*     */     }
/* 121 */     if (!this.children.isEmpty()) {
/* 122 */       return this.children.get(0);
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private TreeNodePosition nextOrThread() {
/* 129 */     if (this.thread != null) {
/* 130 */       return this.thread;
/*     */     }
/* 132 */     if (!this.children.isEmpty()) {
/* 133 */       return this.children.get(this.children.size() - 1);
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */   
/*     */   private TreeNodePosition apportion(TreeNodePosition $$0) {
/* 139 */     if (this.previousSibling == null) {
/* 140 */       return $$0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     TreeNodePosition $$1 = this;
/* 146 */     TreeNodePosition $$2 = this;
/* 147 */     TreeNodePosition $$3 = this.previousSibling;
/* 148 */     TreeNodePosition $$4 = this.parent.children.get(0);
/* 149 */     float $$5 = this.mod;
/* 150 */     float $$6 = this.mod;
/* 151 */     float $$7 = $$3.mod;
/* 152 */     float $$8 = $$4.mod;
/*     */     
/* 154 */     while ($$3.nextOrThread() != null && $$1.previousOrThread() != null) {
/* 155 */       $$3 = $$3.nextOrThread();
/* 156 */       $$1 = $$1.previousOrThread();
/* 157 */       $$4 = $$4.previousOrThread();
/* 158 */       $$2 = $$2.nextOrThread();
/* 159 */       $$2.ancestor = this;
/* 160 */       float $$9 = $$3.y + $$7 - $$1.y + $$5 + 1.0F;
/* 161 */       if ($$9 > 0.0F) {
/* 162 */         $$3.getAncestor(this, $$0).moveSubtree(this, $$9);
/* 163 */         $$5 += $$9;
/* 164 */         $$6 += $$9;
/*     */       } 
/* 166 */       $$7 += $$3.mod;
/* 167 */       $$5 += $$1.mod;
/* 168 */       $$8 += $$4.mod;
/* 169 */       $$6 += $$2.mod;
/*     */     } 
/* 171 */     if ($$3.nextOrThread() != null && $$2.nextOrThread() == null) {
/* 172 */       $$2.thread = $$3.nextOrThread();
/* 173 */       $$2.mod += $$7 - $$6;
/*     */     } else {
/* 175 */       if ($$1.previousOrThread() != null && $$4.previousOrThread() == null) {
/* 176 */         $$4.thread = $$1.previousOrThread();
/* 177 */         $$4.mod += $$5 - $$8;
/*     */       } 
/* 179 */       $$0 = this;
/*     */     } 
/*     */     
/* 182 */     return $$0;
/*     */   }
/*     */   
/*     */   private void moveSubtree(TreeNodePosition $$0, float $$1) {
/* 186 */     float $$2 = ($$0.childIndex - this.childIndex);
/* 187 */     if ($$2 != 0.0F) {
/* 188 */       $$0.change -= $$1 / $$2;
/* 189 */       this.change += $$1 / $$2;
/*     */     } 
/* 191 */     $$0.shift += $$1;
/* 192 */     $$0.y += $$1;
/* 193 */     $$0.mod += $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private TreeNodePosition getAncestor(TreeNodePosition $$0, TreeNodePosition $$1) {
/* 198 */     if (this.ancestor != null && $$0.parent.children.contains(this.ancestor)) {
/* 199 */       return this.ancestor;
/*     */     }
/* 201 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void finalizePosition() {
/* 206 */     this.node.advancement().display().ifPresent($$0 -> $$0.setLocation(this.x, this.y));
/*     */     
/* 208 */     if (!this.children.isEmpty()) {
/* 209 */       for (TreeNodePosition $$0 : this.children) {
/* 210 */         $$0.finalizePosition();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void run(AdvancementNode $$0) {
/* 216 */     if ($$0.advancement().display().isEmpty()) {
/* 217 */       throw new IllegalArgumentException("Can't position children of an invisible root!");
/*     */     }
/* 219 */     TreeNodePosition $$1 = new TreeNodePosition($$0, null, null, 1, 0);
/* 220 */     $$1.firstWalk();
/* 221 */     float $$2 = $$1.secondWalk(0.0F, 0, $$1.y);
/* 222 */     if ($$2 < 0.0F) {
/* 223 */       $$1.thirdWalk(-$$2);
/*     */     }
/* 225 */     $$1.finalizePosition();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\TreeNodePosition.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */