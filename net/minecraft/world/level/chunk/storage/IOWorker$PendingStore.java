/*    */ package net.minecraft.world.level.chunk.storage;
/*    */ 
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.nbt.CompoundTag;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class PendingStore
/*    */ {
/*    */   @Nullable
/*    */   CompoundTag data;
/* 57 */   final CompletableFuture<Void> result = new CompletableFuture<>();
/*    */   
/*    */   public PendingStore(@Nullable CompoundTag $$0) {
/* 60 */     this.data = $$0;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\chunk\storage\IOWorker$PendingStore.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */