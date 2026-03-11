/*    */ package net.minecraft.client.sounds;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.blaze3d.audio.OggAudioStream;
/*    */ import com.mojang.blaze3d.audio.SoundBuffer;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.CompletableFuture;
/*    */ import java.util.concurrent.CompletionException;
/*    */ import net.minecraft.Util;
/*    */ import net.minecraft.client.resources.sounds.Sound;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ import net.minecraft.server.packs.resources.ResourceProvider;
/*    */ 
/*    */ 
/*    */ public class SoundBufferLibrary
/*    */ {
/*    */   private final ResourceProvider resourceManager;
/* 22 */   private final Map<ResourceLocation, CompletableFuture<SoundBuffer>> cache = Maps.newHashMap();
/*    */   
/*    */   public SoundBufferLibrary(ResourceProvider $$0) {
/* 25 */     this.resourceManager = $$0;
/*    */   }
/*    */   
/*    */   public CompletableFuture<SoundBuffer> getCompleteBuffer(ResourceLocation $$0) {
/* 29 */     return this.cache.computeIfAbsent($$0, $$0 -> CompletableFuture.supplyAsync((), Util.backgroundExecutor()));
/*    */   }
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
/*    */   public CompletableFuture<AudioStream> getStream(ResourceLocation $$0, boolean $$1) {
/* 42 */     return CompletableFuture.supplyAsync(() -> {
/*    */           try {
/*    */             InputStream $$2 = this.resourceManager.open($$0);
/*    */             return $$1 ? new LoopingAudioStream(OggAudioStream::new, $$2) : (AudioStream)new OggAudioStream($$2);
/* 46 */           } catch (IOException $$3) {
/*    */             throw new CompletionException($$3);
/*    */           } 
/* 49 */         }Util.backgroundExecutor());
/*    */   }
/*    */   
/*    */   public void clear() {
/* 53 */     this.cache.values().forEach($$0 -> $$0.thenAccept(SoundBuffer::discardAlBuffer));
/* 54 */     this.cache.clear();
/*    */   }
/*    */   
/*    */   public CompletableFuture<?> preload(Collection<Sound> $$0) {
/* 58 */     return CompletableFuture.allOf((CompletableFuture<?>[])$$0.stream().map($$0 -> getCompleteBuffer($$0.getPath())).toArray($$0 -> new CompletableFuture[$$0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundBufferLibrary.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */