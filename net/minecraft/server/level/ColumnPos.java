/*    */ package net.minecraft.server.level;public final class ColumnPos extends Record { private final int x;
/*    */   private final int z;
/*    */   private static final long COORD_BITS = 32L;
/*    */   private static final long COORD_MASK = 4294967295L;
/*    */   
/*  6 */   public ColumnPos(int $$0, int $$1) { this.x = $$0; this.z = $$1; } public int x() { return this.x; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/server/level/ColumnPos;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #6	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/server/level/ColumnPos;
/*  6 */     //   0	8	1	$$0	Ljava/lang/Object; } public int z() { return this.z; }
/*    */ 
/*    */ 
/*    */   
/*    */   public ChunkPos toChunkPos() {
/* 11 */     return new ChunkPos(SectionPos.blockToSectionCoord(this.x), SectionPos.blockToSectionCoord(this.z));
/*    */   }
/*    */   
/*    */   public long toLong() {
/* 15 */     return asLong(this.x, this.z);
/*    */   }
/*    */   
/*    */   public static long asLong(int $$0, int $$1) {
/* 19 */     return $$0 & 0xFFFFFFFFL | ($$1 & 0xFFFFFFFFL) << 32L;
/*    */   }
/*    */   
/*    */   public static int getX(long $$0) {
/* 23 */     return (int)($$0 & 0xFFFFFFFFL);
/*    */   }
/*    */   
/*    */   public static int getZ(long $$0) {
/* 27 */     return (int)($$0 >>> 32L & 0xFFFFFFFFL);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return "[" + this.x + ", " + this.z + "]";
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 37 */     return ChunkPos.hash(this.x, this.z);
/*    */   } }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ColumnPos.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */