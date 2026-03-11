/*    */ package net.minecraft.client.renderer.chunk;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.world.level.chunk.LevelChunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class ChunkInfo
/*    */ {
/*    */   private final LevelChunk chunk;
/*    */   @Nullable
/*    */   private RenderChunk renderChunk;
/*    */   
/*    */   ChunkInfo(LevelChunk $$0) {
/* 20 */     this.chunk = $$0;
/*    */   }
/*    */   
/*    */   public LevelChunk chunk() {
/* 24 */     return this.chunk;
/*    */   }
/*    */   
/*    */   public RenderChunk renderChunk() {
/* 28 */     if (this.renderChunk == null) {
/* 29 */       this.renderChunk = new RenderChunk(this.chunk);
/*    */     }
/* 31 */     return this.renderChunk;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\renderer\chunk\RenderRegionCache$ChunkInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */