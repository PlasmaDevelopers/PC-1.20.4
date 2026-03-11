/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.util.VisibleForDebug;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Path
/*     */ {
/*     */   private final List<Node> nodes;
/*     */   @Nullable
/*     */   private DebugData debugData;
/*     */   private int nextNodeIndex;
/*     */   private final BlockPos target;
/*     */   private final float distToTarget;
/*     */   private final boolean reached;
/*     */   
/*     */   public Path(List<Node> $$0, BlockPos $$1, boolean $$2) {
/*  34 */     this.nodes = $$0;
/*  35 */     this.target = $$1;
/*     */     
/*  37 */     this.distToTarget = $$0.isEmpty() ? Float.MAX_VALUE : ((Node)this.nodes.get(this.nodes.size() - 1)).distanceManhattan(this.target);
/*     */     
/*  39 */     this.reached = $$2;
/*     */   }
/*     */   
/*     */   public void advance() {
/*  43 */     this.nextNodeIndex++;
/*     */   }
/*     */   
/*     */   public boolean notStarted() {
/*  47 */     return (this.nextNodeIndex <= 0);
/*     */   }
/*     */   
/*     */   public boolean isDone() {
/*  51 */     return (this.nextNodeIndex >= this.nodes.size());
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Node getEndNode() {
/*  56 */     if (!this.nodes.isEmpty()) {
/*  57 */       return this.nodes.get(this.nodes.size() - 1);
/*     */     }
/*  59 */     return null;
/*     */   }
/*     */   
/*     */   public Node getNode(int $$0) {
/*  63 */     return this.nodes.get($$0);
/*     */   }
/*     */   
/*     */   public void truncateNodes(int $$0) {
/*  67 */     if (this.nodes.size() > $$0) {
/*  68 */       this.nodes.subList($$0, this.nodes.size()).clear();
/*     */     }
/*     */   }
/*     */   
/*     */   public void replaceNode(int $$0, Node $$1) {
/*  73 */     this.nodes.set($$0, $$1);
/*     */   }
/*     */   
/*     */   public int getNodeCount() {
/*  77 */     return this.nodes.size();
/*     */   }
/*     */   
/*     */   public int getNextNodeIndex() {
/*  81 */     return this.nextNodeIndex;
/*     */   }
/*     */   
/*     */   public void setNextNodeIndex(int $$0) {
/*  85 */     this.nextNodeIndex = $$0;
/*     */   }
/*     */   
/*     */   public Vec3 getEntityPosAtNode(Entity $$0, int $$1) {
/*  89 */     Node $$2 = this.nodes.get($$1);
/*  90 */     double $$3 = $$2.x + (int)($$0.getBbWidth() + 1.0F) * 0.5D;
/*  91 */     double $$4 = $$2.y;
/*  92 */     double $$5 = $$2.z + (int)($$0.getBbWidth() + 1.0F) * 0.5D;
/*  93 */     return new Vec3($$3, $$4, $$5);
/*     */   }
/*     */   
/*     */   public BlockPos getNodePos(int $$0) {
/*  97 */     return ((Node)this.nodes.get($$0)).asBlockPos();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3 getNextEntityPos(Entity $$0) {
/* 104 */     return getEntityPosAtNode($$0, this.nextNodeIndex);
/*     */   }
/*     */   
/*     */   public BlockPos getNextNodePos() {
/* 108 */     return ((Node)this.nodes.get(this.nextNodeIndex)).asBlockPos();
/*     */   }
/*     */   
/*     */   public Node getNextNode() {
/* 112 */     return this.nodes.get(this.nextNodeIndex);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public Node getPreviousNode() {
/* 117 */     return (this.nextNodeIndex > 0) ? this.nodes.get(this.nextNodeIndex - 1) : null;
/*     */   }
/*     */   
/*     */   public boolean sameAs(@Nullable Path $$0) {
/* 121 */     if ($$0 == null) {
/* 122 */       return false;
/*     */     }
/* 124 */     if ($$0.nodes.size() != this.nodes.size()) {
/* 125 */       return false;
/*     */     }
/*     */     
/* 128 */     for (int $$1 = 0; $$1 < this.nodes.size(); $$1++) {
/* 129 */       Node $$2 = this.nodes.get($$1);
/* 130 */       Node $$3 = $$0.nodes.get($$1);
/*     */       
/* 132 */       if ($$2.x != $$3.x || $$2.y != $$3.y || $$2.z != $$3.z) {
/* 133 */         return false;
/*     */       }
/*     */     } 
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canReach() {
/* 143 */     return this.reached;
/*     */   }
/*     */   
/*     */   @VisibleForDebug
/*     */   void setDebug(Node[] $$0, Node[] $$1, Set<Target> $$2) {
/* 148 */     this.debugData = new DebugData($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public DebugData debugData() {
/* 153 */     return this.debugData;
/*     */   }
/*     */   
/*     */   public void writeToStream(FriendlyByteBuf $$0) {
/* 157 */     if (this.debugData == null || this.debugData.targetNodes.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 161 */     $$0.writeBoolean(this.reached);
/* 162 */     $$0.writeInt(this.nextNodeIndex);
/* 163 */     $$0.writeBlockPos(this.target);
/* 164 */     $$0.writeCollection(this.nodes, ($$0, $$1) -> $$1.writeToStream($$0));
/* 165 */     this.debugData.write($$0);
/*     */   }
/*     */   
/*     */   public static Path createFromStream(FriendlyByteBuf $$0) {
/* 169 */     boolean $$1 = $$0.readBoolean();
/* 170 */     int $$2 = $$0.readInt();
/* 171 */     BlockPos $$3 = $$0.readBlockPos();
/* 172 */     List<Node> $$4 = $$0.readList(Node::createFromStream);
/* 173 */     DebugData $$5 = DebugData.read($$0);
/*     */     
/* 175 */     Path $$6 = new Path($$4, $$3, $$1);
/* 176 */     $$6.debugData = $$5;
/* 177 */     $$6.nextNodeIndex = $$2;
/*     */     
/* 179 */     return $$6;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 184 */     return "Path(length=" + this.nodes.size() + ")";
/*     */   }
/*     */   
/*     */   public BlockPos getTarget() {
/* 188 */     return this.target;
/*     */   }
/*     */   
/*     */   public float getDistToTarget() {
/* 192 */     return this.distToTarget;
/*     */   }
/*     */   
/*     */   static Node[] readNodeArray(FriendlyByteBuf $$0) {
/* 196 */     Node[] $$1 = new Node[$$0.readVarInt()];
/* 197 */     for (int $$2 = 0; $$2 < $$1.length; $$2++) {
/* 198 */       $$1[$$2] = Node.createFromStream($$0);
/*     */     }
/* 200 */     return $$1;
/*     */   }
/*     */   
/*     */   static void writeNodeArray(FriendlyByteBuf $$0, Node[] $$1) {
/* 204 */     $$0.writeVarInt($$1.length);
/* 205 */     for (Node $$2 : $$1) {
/* 206 */       $$2.writeToStream($$0);
/*     */     }
/*     */   }
/*     */   
/*     */   public Path copy() {
/* 211 */     Path $$0 = new Path(this.nodes, this.target, this.reached);
/* 212 */     $$0.debugData = this.debugData;
/* 213 */     $$0.nextNodeIndex = this.nextNodeIndex;
/* 214 */     return $$0;
/*     */   }
/*     */   public static final class DebugData extends Record { private final Node[] openSet; private final Node[] closedSet; final Set<Target> targetNodes;
/* 217 */     public DebugData(Node[] $$0, Node[] $$1, Set<Target> $$2) { this.openSet = $$0; this.closedSet = $$1; this.targetNodes = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/pathfinder/Path$DebugData;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #217	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 217 */       //   0	7	0	this	Lnet/minecraft/world/level/pathfinder/Path$DebugData; } public Node[] openSet() { return this.openSet; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/pathfinder/Path$DebugData;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #217	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/pathfinder/Path$DebugData; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/pathfinder/Path$DebugData;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #217	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/pathfinder/Path$DebugData;
/* 217 */       //   0	8	1	$$0	Ljava/lang/Object; } public Node[] closedSet() { return this.closedSet; } public Set<Target> targetNodes() { return this.targetNodes; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(FriendlyByteBuf $$0) {
/* 224 */       $$0.writeCollection(this.targetNodes, ($$0, $$1) -> $$1.writeToStream($$0));
/* 225 */       Path.writeNodeArray($$0, this.openSet);
/* 226 */       Path.writeNodeArray($$0, this.closedSet);
/*     */     }
/*     */     
/*     */     public static DebugData read(FriendlyByteBuf $$0) {
/* 230 */       HashSet<Target> $$1 = (HashSet<Target>)$$0.readCollection(HashSet::new, Target::createFromStream);
/* 231 */       Node[] $$2 = Path.readNodeArray($$0);
/* 232 */       Node[] $$3 = Path.readNodeArray($$0);
/* 233 */       return new DebugData($$2, $$3, $$1);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\Path.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */