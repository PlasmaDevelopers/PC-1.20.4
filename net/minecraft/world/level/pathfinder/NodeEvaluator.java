/*    */ package net.minecraft.world.level.pathfinder;
/*    */ 
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*    */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.util.Mth;
/*    */ import net.minecraft.world.entity.Mob;
/*    */ import net.minecraft.world.level.BlockGetter;
/*    */ import net.minecraft.world.level.PathNavigationRegion;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class NodeEvaluator
/*    */ {
/*    */   protected PathNavigationRegion level;
/*    */   protected Mob mob;
/* 19 */   protected final Int2ObjectMap<Node> nodes = (Int2ObjectMap<Node>)new Int2ObjectOpenHashMap();
/*    */   
/*    */   protected int entityWidth;
/*    */   
/*    */   protected int entityHeight;
/*    */   
/*    */   protected int entityDepth;
/*    */   
/*    */   protected boolean canPassDoors;
/*    */   protected boolean canOpenDoors;
/*    */   protected boolean canFloat;
/*    */   protected boolean canWalkOverFences;
/*    */   
/*    */   public void prepare(PathNavigationRegion $$0, Mob $$1) {
/* 33 */     this.level = $$0;
/* 34 */     this.mob = $$1;
/* 35 */     this.nodes.clear();
/*    */     
/* 37 */     this.entityWidth = Mth.floor($$1.getBbWidth() + 1.0F);
/* 38 */     this.entityHeight = Mth.floor($$1.getBbHeight() + 1.0F);
/* 39 */     this.entityDepth = Mth.floor($$1.getBbWidth() + 1.0F);
/*    */   }
/*    */   
/*    */   public void done() {
/* 43 */     this.level = null;
/* 44 */     this.mob = null;
/*    */   }
/*    */   
/*    */   protected Node getNode(BlockPos $$0) {
/* 48 */     return getNode($$0.getX(), $$0.getY(), $$0.getZ());
/*    */   }
/*    */   
/*    */   protected Node getNode(int $$0, int $$1, int $$2) {
/* 52 */     return (Node)this.nodes.computeIfAbsent(Node.createHash($$0, $$1, $$2), $$3 -> new Node($$0, $$1, $$2));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Target getTargetFromNode(Node $$0) {
/* 60 */     return new Target($$0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCanPassDoors(boolean $$0) {
/* 70 */     this.canPassDoors = $$0;
/*    */   }
/*    */   
/*    */   public void setCanOpenDoors(boolean $$0) {
/* 74 */     this.canOpenDoors = $$0;
/*    */   }
/*    */   
/*    */   public void setCanFloat(boolean $$0) {
/* 78 */     this.canFloat = $$0;
/*    */   }
/*    */   
/*    */   public void setCanWalkOverFences(boolean $$0) {
/* 82 */     this.canWalkOverFences = $$0;
/*    */   }
/*    */   
/*    */   public boolean canPassDoors() {
/* 86 */     return this.canPassDoors;
/*    */   }
/*    */   
/*    */   public boolean canOpenDoors() {
/* 90 */     return this.canOpenDoors;
/*    */   }
/*    */   
/*    */   public boolean canFloat() {
/* 94 */     return this.canFloat;
/*    */   }
/*    */   
/*    */   public boolean canWalkOverFences() {
/* 98 */     return this.canWalkOverFences;
/*    */   }
/*    */   
/*    */   public abstract Node getStart();
/*    */   
/*    */   public abstract Target getGoal(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   public abstract int getNeighbors(Node[] paramArrayOfNode, Node paramNode);
/*    */   
/*    */   public abstract BlockPathTypes getBlockPathType(BlockGetter paramBlockGetter, int paramInt1, int paramInt2, int paramInt3, Mob paramMob);
/*    */   
/*    */   public abstract BlockPathTypes getBlockPathType(BlockGetter paramBlockGetter, int paramInt1, int paramInt2, int paramInt3);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\NodeEvaluator.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */