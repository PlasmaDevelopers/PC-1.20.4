/*    */ package net.minecraft.server.level.progress;
/*    */ 
/*    */ import java.util.Objects;
/*    */ import java.util.concurrent.Executor;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.thread.ProcessorMailbox;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.chunk.ChunkStatus;
/*    */ 
/*    */ public class ProcessorChunkProgressListener implements ChunkProgressListener {
/*    */   private final ChunkProgressListener delegate;
/*    */   private final ProcessorMailbox<Runnable> mailbox;
/*    */   
/*    */   private ProcessorChunkProgressListener(ChunkProgressListener $$0, Executor $$1) {
/* 15 */     this.delegate = $$0;
/* 16 */     this.mailbox = ProcessorMailbox.create($$1, "progressListener");
/*    */   }
/*    */   
/*    */   public static ProcessorChunkProgressListener createStarted(ChunkProgressListener $$0, Executor $$1) {
/* 20 */     ProcessorChunkProgressListener $$2 = new ProcessorChunkProgressListener($$0, $$1);
/* 21 */     $$2.start();
/* 22 */     return $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateSpawnPos(ChunkPos $$0) {
/* 27 */     this.mailbox.tell(() -> this.delegate.updateSpawnPos($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onStatusChange(ChunkPos $$0, @Nullable ChunkStatus $$1) {
/* 32 */     this.mailbox.tell(() -> this.delegate.onStatusChange($$0, $$1));
/*    */   }
/*    */ 
/*    */   
/*    */   public void start() {
/* 37 */     Objects.requireNonNull(this.delegate); this.mailbox.tell(this.delegate::start);
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 42 */     Objects.requireNonNull(this.delegate); this.mailbox.tell(this.delegate::stop);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\progress\ProcessorChunkProgressListener.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */