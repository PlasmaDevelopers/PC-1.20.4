/*     */ package com.mojang.blaze3d.audio;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class CountingChannelPool
/*     */   implements Library.ChannelPool
/*     */ {
/*     */   private final int limit;
/*  94 */   private final Set<Channel> activeChannels = Sets.newIdentityHashSet();
/*     */   
/*     */   public CountingChannelPool(int $$0) {
/*  97 */     this.limit = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Channel acquire() {
/* 103 */     if (this.activeChannels.size() >= this.limit) {
/* 104 */       if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 105 */         Library.LOGGER.warn("Maximum sound pool size {} reached", Integer.valueOf(this.limit));
/*     */       }
/* 107 */       return null;
/*     */     } 
/*     */     
/* 110 */     Channel $$0 = Channel.create();
/* 111 */     if ($$0 != null) {
/* 112 */       this.activeChannels.add($$0);
/*     */     }
/*     */     
/* 115 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(Channel $$0) {
/* 120 */     if (!this.activeChannels.remove($$0)) {
/* 121 */       return false;
/*     */     }
/* 123 */     $$0.destroy();
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanup() {
/* 129 */     this.activeChannels.forEach(Channel::destroy);
/* 130 */     this.activeChannels.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxCount() {
/* 135 */     return this.limit;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUsedCount() {
/* 140 */     return this.activeChannels.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\audio\Library$CountingChannelPool.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */