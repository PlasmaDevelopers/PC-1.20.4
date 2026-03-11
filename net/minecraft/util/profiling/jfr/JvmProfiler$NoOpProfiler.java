/*    */ package net.minecraft.util.profiling.jfr;
/*    */ 
/*    */ import com.mojang.logging.LogUtils;
/*    */ import java.net.SocketAddress;
/*    */ import java.nio.file.Path;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.ConnectionProtocol;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.util.profiling.jfr.callback.ProfiledDuration;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ import org.slf4j.Logger;
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
/*    */ public class NoOpProfiler
/*    */   implements JvmProfiler
/*    */ {
/* 42 */   private static final Logger LOGGER = LogUtils.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean start(Environment $$0) {
/* 47 */     LOGGER.warn("Attempted to start Flight Recorder, but it's not supported on this JVM");
/* 48 */     return false;
/*    */   } static final ProfiledDuration noOpCommit = () -> {
/*    */     
/*    */     };
/*    */   public Path stop() {
/* 53 */     throw new IllegalStateException("Attempted to stop Flight Recorder, but it's not supported on this JVM");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/* 58 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAvailable() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacketReceived(ConnectionProtocol $$0, int $$1, SocketAddress $$2, int $$3) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onPacketSent(ConnectionProtocol $$0, int $$1, SocketAddress $$2, int $$3) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void onServerTick(float $$0) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public ProfiledDuration onWorldLoadedStarted() {
/* 83 */     return noOpCommit;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ProfiledDuration onChunkGenerate(ChunkPos $$0, ResourceKey<Level> $$1, String $$2) {
/* 89 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\JvmProfiler$NoOpProfiler.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */