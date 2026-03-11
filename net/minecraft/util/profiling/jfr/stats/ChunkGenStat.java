/*    */ package net.minecraft.util.profiling.jfr.stats;
/*    */ 
/*    */ 
/*    */ public final class ChunkGenStat extends Record implements TimedStat {
/*    */   private final Duration duration;
/*    */   private final ChunkPos chunkPos;
/*    */   private final ColumnPos worldPos;
/*    */   private final ChunkStatus status;
/*    */   private final String level;
/*    */   
/* 11 */   public ChunkGenStat(Duration $$0, ChunkPos $$1, ColumnPos $$2, ChunkStatus $$3, String $$4) { this.duration = $$0; this.chunkPos = $$1; this.worldPos = $$2; this.status = $$3; this.level = $$4; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/* 11 */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat; } public Duration duration() { return this.duration; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #11	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/util/profiling/jfr/stats/ChunkGenStat;
/* 11 */     //   0	8	1	$$0	Ljava/lang/Object; } public ChunkPos chunkPos() { return this.chunkPos; } public ColumnPos worldPos() { return this.worldPos; } public ChunkStatus status() { return this.status; } public String level() { return this.level; }
/*    */   
/*    */   public static ChunkGenStat from(RecordedEvent $$0) {
/* 14 */     return new ChunkGenStat($$0.getDuration(), new ChunkPos($$0
/* 15 */           .getInt("chunkPosX"), $$0.getInt("chunkPosX")), new ColumnPos($$0
/* 16 */           .getInt("worldPosX"), $$0.getInt("worldPosZ")), 
/* 17 */         ChunkStatus.byName($$0.getString("status")), $$0
/* 18 */         .getString("level"));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\stats\ChunkGenStat.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */