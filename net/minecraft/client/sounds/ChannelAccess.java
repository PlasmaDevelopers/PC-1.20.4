/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.blaze3d.audio.Channel;
/*    */ import com.mojang.blaze3d.audio.Library;
/*    */ import java.util.Iterator;
/*    */ import java.util.Objects;
/*    */ import java.util.Set;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.Executor;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.stream.Stream;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ChannelAccess
/*    */ {
/*    */   public class ChannelHandle {
/*    */     @Nullable
/*    */     Channel channel;
/*    */     private boolean stopped;
/*    */     
/*    */     public boolean isStopped() {
/* 23 */       return this.stopped;
/*    */     }
/*    */     
/*    */     public ChannelHandle(Channel $$1) {
/* 27 */       this.channel = $$1;
/*    */     }
/*    */     
/*    */     public void execute(Consumer<Channel> $$0) {
/* 31 */       ChannelAccess.this.executor.execute(() -> {
/*    */             if (this.channel != null) {
/*    */               $$0.accept(this.channel);
/*    */             }
/*    */           });
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     public void release() {
/* 41 */       this.stopped = true;
/* 42 */       ChannelAccess.this.library.releaseChannel(this.channel);
/* 43 */       this.channel = null;
/*    */     }
/*    */   }
/*    */   
/* 47 */   private final Set<ChannelHandle> channels = Sets.newIdentityHashSet();
/*    */   
/*    */   final Library library;
/*    */   
/*    */   final Executor executor;
/*    */   
/*    */   public ChannelAccess(Library $$0, Executor $$1) {
/* 54 */     this.library = $$0;
/* 55 */     this.executor = $$1;
/*    */   }
/*    */   
/*    */   public CompletableFuture<ChannelHandle> createHandle(Library.Pool $$0) {
/* 59 */     CompletableFuture<ChannelHandle> $$1 = new CompletableFuture<>();
/* 60 */     this.executor.execute(() -> {
/*    */           Channel $$2 = this.library.acquireChannel($$0);
/*    */           if ($$2 != null) {
/*    */             ChannelHandle $$3 = new ChannelHandle($$2);
/*    */             this.channels.add($$3);
/*    */             $$1.complete($$3);
/*    */           } else {
/*    */             $$1.complete(null);
/*    */           } 
/*    */         });
/* 70 */     return $$1;
/*    */   }
/*    */   
/*    */   public void executeOnChannels(Consumer<Stream<Channel>> $$0) {
/* 74 */     this.executor.execute(() -> $$0.accept(this.channels.stream().map(()).filter(Objects::nonNull)));
/*    */   }
/*    */   
/*    */   public void scheduleTick() {
/* 78 */     this.executor.execute(() -> {
/*    */           Iterator<ChannelHandle> $$0 = this.channels.iterator();
/*    */           while ($$0.hasNext()) {
/*    */             ChannelHandle $$1 = $$0.next();
/*    */             $$1.channel.updateStream();
/*    */             if ($$1.channel.stopped()) {
/*    */               $$1.release();
/*    */               $$0.remove();
/*    */             } 
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 93 */     this.channels.forEach(ChannelHandle::release);
/* 94 */     this.channels.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\ChannelAccess.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */