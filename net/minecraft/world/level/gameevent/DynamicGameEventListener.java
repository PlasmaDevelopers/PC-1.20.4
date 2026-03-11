/*    */ package net.minecraft.world.level.gameevent;
/*    */ 
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.server.level.ServerLevel;
/*    */ import net.minecraft.world.level.Level;
/*    */ import net.minecraft.world.level.LevelReader;
/*    */ import net.minecraft.world.level.chunk.ChunkAccess;
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DynamicGameEventListener<T extends GameEventListener>
/*    */ {
/*    */   private final T listener;
/*    */   @Nullable
/*    */   private SectionPos lastSection;
/*    */   
/*    */   public DynamicGameEventListener(T $$0) {
/* 22 */     this.listener = $$0;
/*    */   }
/*    */   
/*    */   public void add(ServerLevel $$0) {
/* 26 */     move($$0);
/*    */   }
/*    */   
/*    */   public T getListener() {
/* 30 */     return this.listener;
/*    */   }
/*    */   
/*    */   public void remove(ServerLevel $$0) {
/* 34 */     ifChunkExists((LevelReader)$$0, this.lastSection, $$0 -> $$0.unregister((GameEventListener)this.listener));
/*    */   }
/*    */   
/*    */   public void move(ServerLevel $$0) {
/* 38 */     this.listener.getListenerSource().getPosition((Level)$$0)
/* 39 */       .map(SectionPos::of)
/* 40 */       .ifPresent($$1 -> {
/*    */           if (this.lastSection == null || !this.lastSection.equals($$1)) {
/*    */             ifChunkExists((LevelReader)$$0, this.lastSection, ());
/*    */             this.lastSection = $$1;
/*    */             ifChunkExists((LevelReader)$$0, this.lastSection, ());
/*    */           } 
/*    */         });
/*    */   }
/*    */   
/*    */   private static void ifChunkExists(LevelReader $$0, @Nullable SectionPos $$1, Consumer<GameEventListenerRegistry> $$2) {
/* 50 */     if ($$1 == null) {
/*    */       return;
/*    */     }
/*    */     
/* 54 */     ChunkAccess $$3 = $$0.getChunk($$1.x(), $$1.z(), ChunkStatus.FULL, false);
/*    */     
/* 56 */     if ($$3 != null)
/* 57 */       $$2.accept($$3.getListenerRegistry($$1.y())); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\DynamicGameEventListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */