/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import com.mojang.blaze3d.audio.Channel;
/*    */ import java.util.function.Consumer;
/*    */ import javax.annotation.Nullable;
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
/*    */ public class ChannelHandle
/*    */ {
/*    */   @Nullable
/*    */   Channel channel;
/*    */   private boolean stopped;
/*    */   
/*    */   public boolean isStopped() {
/* 23 */     return this.stopped;
/*    */   }
/*    */   
/*    */   public ChannelHandle(Channel $$1) {
/* 27 */     this.channel = $$1;
/*    */   }
/*    */   
/*    */   public void execute(Consumer<Channel> $$0) {
/* 31 */     ChannelAccess.this.executor.execute(() -> {
/*    */           if (this.channel != null) {
/*    */             $$0.accept(this.channel);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void release() {
/* 41 */     this.stopped = true;
/* 42 */     ChannelAccess.this.library.releaseChannel(this.channel);
/* 43 */     this.channel = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\ChannelAccess$ChannelHandle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */