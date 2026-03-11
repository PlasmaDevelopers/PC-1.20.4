/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkLevel
/*    */ {
/*    */   private static final int FULL_CHUNK_LEVEL = 33;
/*    */   private static final int BLOCK_TICKING_LEVEL = 32;
/*    */   private static final int ENTITY_TICKING_LEVEL = 31;
/* 12 */   public static final int MAX_LEVEL = 33 + ChunkStatus.maxDistance();
/*    */   
/*    */   public static ChunkStatus generationStatus(int $$0) {
/* 15 */     if ($$0 < 33) {
/* 16 */       return ChunkStatus.FULL;
/*    */     }
/* 18 */     return ChunkStatus.getStatusAroundFullChunk($$0 - 33);
/*    */   }
/*    */   
/*    */   public static int byStatus(ChunkStatus $$0) {
/* 22 */     return 33 + ChunkStatus.getDistance($$0);
/*    */   }
/*    */   
/*    */   public static FullChunkStatus fullStatus(int $$0) {
/* 26 */     if ($$0 <= 31)
/* 27 */       return FullChunkStatus.ENTITY_TICKING; 
/* 28 */     if ($$0 <= 32)
/* 29 */       return FullChunkStatus.BLOCK_TICKING; 
/* 30 */     if ($$0 <= 33) {
/* 31 */       return FullChunkStatus.FULL;
/*    */     }
/* 33 */     return FullChunkStatus.INACCESSIBLE;
/*    */   }
/*    */   
/*    */   public static int byStatus(FullChunkStatus $$0) {
/* 37 */     switch ($$0) { default: throw new IncompatibleClassChangeError();case INACCESSIBLE: case FULL: case BLOCK_TICKING: case ENTITY_TICKING: break; }  return 
/*    */ 
/*    */ 
/*    */       
/* 41 */       31;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean isEntityTicking(int $$0) {
/* 46 */     return ($$0 <= 31);
/*    */   }
/*    */   
/*    */   public static boolean isBlockTicking(int $$0) {
/* 50 */     return ($$0 <= 32);
/*    */   }
/*    */   
/*    */   public static boolean isLoaded(int $$0) {
/* 54 */     return ($$0 <= MAX_LEVEL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\ChunkLevel.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */