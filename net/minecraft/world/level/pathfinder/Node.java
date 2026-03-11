/*     */ package net.minecraft.world.level.pathfinder;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ public class Node
/*     */ {
/*     */   public final int x;
/*     */   public final int y;
/*     */   public final int z;
/*     */   private final int hash;
/*  16 */   public int heapIdx = -1;
/*     */   
/*     */   public float g;
/*     */   
/*     */   public float h;
/*     */   public float f;
/*     */   @Nullable
/*     */   public Node cameFrom;
/*     */   public boolean closed;
/*     */   public float walkedDistance;
/*     */   public float costMalus;
/*  27 */   public BlockPathTypes type = BlockPathTypes.BLOCKED;
/*     */   
/*     */   public Node(int $$0, int $$1, int $$2) {
/*  30 */     this.x = $$0;
/*  31 */     this.y = $$1;
/*  32 */     this.z = $$2;
/*     */     
/*  34 */     this.hash = createHash($$0, $$1, $$2);
/*     */   }
/*     */   
/*     */   public Node cloneAndMove(int $$0, int $$1, int $$2) {
/*  38 */     Node $$3 = new Node($$0, $$1, $$2);
/*  39 */     $$3.heapIdx = this.heapIdx;
/*  40 */     $$3.g = this.g;
/*  41 */     $$3.h = this.h;
/*  42 */     $$3.f = this.f;
/*  43 */     $$3.cameFrom = this.cameFrom;
/*  44 */     $$3.closed = this.closed;
/*  45 */     $$3.walkedDistance = this.walkedDistance;
/*  46 */     $$3.costMalus = this.costMalus;
/*  47 */     $$3.type = this.type;
/*  48 */     return $$3;
/*     */   }
/*     */   
/*     */   public static int createHash(int $$0, int $$1, int $$2) {
/*  52 */     return $$1 & 0xFF | ($$0 & 0x7FFF) << 8 | ($$2 & 0x7FFF) << 24 | (($$0 < 0) ? Integer.MIN_VALUE : 0) | (($$2 < 0) ? 32768 : 0);
/*     */   }
/*     */   
/*     */   public float distanceTo(Node $$0) {
/*  56 */     float $$1 = ($$0.x - this.x);
/*  57 */     float $$2 = ($$0.y - this.y);
/*  58 */     float $$3 = ($$0.z - this.z);
/*  59 */     return Mth.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*     */   }
/*     */   
/*     */   public float distanceToXZ(Node $$0) {
/*  63 */     float $$1 = ($$0.x - this.x);
/*  64 */     float $$2 = ($$0.z - this.z);
/*  65 */     return Mth.sqrt($$1 * $$1 + $$2 * $$2);
/*     */   }
/*     */   
/*     */   public float distanceTo(BlockPos $$0) {
/*  69 */     float $$1 = ($$0.getX() - this.x);
/*  70 */     float $$2 = ($$0.getY() - this.y);
/*  71 */     float $$3 = ($$0.getZ() - this.z);
/*  72 */     return Mth.sqrt($$1 * $$1 + $$2 * $$2 + $$3 * $$3);
/*     */   }
/*     */   
/*     */   public float distanceToSqr(Node $$0) {
/*  76 */     float $$1 = ($$0.x - this.x);
/*  77 */     float $$2 = ($$0.y - this.y);
/*  78 */     float $$3 = ($$0.z - this.z);
/*  79 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*     */   }
/*     */   
/*     */   public float distanceToSqr(BlockPos $$0) {
/*  83 */     float $$1 = ($$0.getX() - this.x);
/*  84 */     float $$2 = ($$0.getY() - this.y);
/*  85 */     float $$3 = ($$0.getZ() - this.z);
/*  86 */     return $$1 * $$1 + $$2 * $$2 + $$3 * $$3;
/*     */   }
/*     */   
/*     */   public float distanceManhattan(Node $$0) {
/*  90 */     float $$1 = Math.abs($$0.x - this.x);
/*  91 */     float $$2 = Math.abs($$0.y - this.y);
/*  92 */     float $$3 = Math.abs($$0.z - this.z);
/*  93 */     return $$1 + $$2 + $$3;
/*     */   }
/*     */   
/*     */   public float distanceManhattan(BlockPos $$0) {
/*  97 */     float $$1 = Math.abs($$0.getX() - this.x);
/*  98 */     float $$2 = Math.abs($$0.getY() - this.y);
/*  99 */     float $$3 = Math.abs($$0.getZ() - this.z);
/* 100 */     return $$1 + $$2 + $$3;
/*     */   }
/*     */   
/*     */   public BlockPos asBlockPos() {
/* 104 */     return new BlockPos(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Vec3 asVec3() {
/* 108 */     return new Vec3(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 113 */     if ($$0 instanceof Node) { Node $$1 = (Node)$$0;
/* 114 */       return (this.hash == $$1.hash && this.x == $$1.x && this.y == $$1.y && this.z == $$1.z); }
/*     */     
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 121 */     return this.hash;
/*     */   }
/*     */   
/*     */   public boolean inOpenSet() {
/* 125 */     return (this.heapIdx >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 130 */     return "Node{x=" + this.x + ", y=" + this.y + ", z=" + this.z + "}";
/*     */   }
/*     */   
/*     */   public void writeToStream(FriendlyByteBuf $$0) {
/* 134 */     $$0.writeInt(this.x);
/* 135 */     $$0.writeInt(this.y);
/* 136 */     $$0.writeInt(this.z);
/* 137 */     $$0.writeFloat(this.walkedDistance);
/* 138 */     $$0.writeFloat(this.costMalus);
/* 139 */     $$0.writeBoolean(this.closed);
/* 140 */     $$0.writeEnum(this.type);
/* 141 */     $$0.writeFloat(this.f);
/*     */   }
/*     */   
/*     */   public static Node createFromStream(FriendlyByteBuf $$0) {
/* 145 */     Node $$1 = new Node($$0.readInt(), $$0.readInt(), $$0.readInt());
/* 146 */     readContents($$0, $$1);
/* 147 */     return $$1;
/*     */   }
/*     */   
/*     */   protected static void readContents(FriendlyByteBuf $$0, Node $$1) {
/* 151 */     $$1.walkedDistance = $$0.readFloat();
/* 152 */     $$1.costMalus = $$0.readFloat();
/* 153 */     $$1.closed = $$0.readBoolean();
/* 154 */     $$1.type = (BlockPathTypes)$$0.readEnum(BlockPathTypes.class);
/* 155 */     $$1.f = $$0.readFloat();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\pathfinder\Node.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */