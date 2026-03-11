/*    */ package net.minecraft.world.level.entity;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.stream.Stream;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ 
/*    */ 
/*    */ public class ChunkEntities<T>
/*    */ {
/*    */   private final ChunkPos pos;
/*    */   private final List<T> entities;
/*    */   
/*    */   public ChunkEntities(ChunkPos $$0, List<T> $$1) {
/* 14 */     this.pos = $$0;
/* 15 */     this.entities = $$1;
/*    */   }
/*    */   
/*    */   public ChunkPos getPos() {
/* 19 */     return this.pos;
/*    */   }
/*    */   
/*    */   public Stream<T> getEntities() {
/* 23 */     return this.entities.stream();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 27 */     return this.entities.isEmpty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\entity\ChunkEntities.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */