/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import jdk.jfr.Category;
/*    */ import jdk.jfr.Enabled;
/*    */ import jdk.jfr.Event;
/*    */ import jdk.jfr.EventType;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import jdk.jfr.StackTrace;
/*    */ import net.minecraft.obfuscate.DontObfuscate;
/*    */ import net.minecraft.resources.ResourceKey;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.Level;
/*    */ 
/*    */ @Name("minecraft.ChunkGeneration")
/*    */ @Label("Chunk Generation")
/*    */ @Category({"Minecraft", "World Generation"})
/*    */ @StackTrace(false)
/*    */ @Enabled(false)
/*    */ @DontObfuscate
/*    */ public class ChunkGenerationEvent
/*    */   extends Event {
/*    */   public static final String EVENT_NAME = "minecraft.ChunkGeneration";
/* 24 */   public static final EventType TYPE = EventType.getEventType((Class)ChunkGenerationEvent.class);
/*    */   
/*    */   @Name("worldPosX")
/*    */   @Label("First Block X World Position")
/*    */   public final int worldPosX;
/*    */   
/*    */   @Name("worldPosZ")
/*    */   @Label("First Block Z World Position")
/*    */   public final int worldPosZ;
/*    */   
/*    */   @Name("chunkPosX")
/*    */   @Label("Chunk X Position")
/*    */   public final int chunkPosX;
/*    */   
/*    */   @Name("chunkPosZ")
/*    */   @Label("Chunk Z Position")
/*    */   public final int chunkPosZ;
/*    */   
/*    */   @Name("status")
/*    */   @Label("Status")
/*    */   public final String targetStatus;
/*    */   
/*    */   @Name("level")
/*    */   @Label("Level")
/*    */   public final String level;
/*    */   
/*    */   public ChunkGenerationEvent(ChunkPos $$0, ResourceKey<Level> $$1, String $$2) {
/* 51 */     this.targetStatus = $$2;
/* 52 */     this.level = $$1.toString();
/* 53 */     this.chunkPosX = $$0.x;
/* 54 */     this.chunkPosZ = $$0.z;
/* 55 */     this.worldPosX = $$0.getMinBlockX();
/* 56 */     this.worldPosZ = $$0.getMinBlockZ();
/*    */   }
/*    */   
/*    */   public static class Fields {
/*    */     public static final String WORLD_POS_X = "worldPosX";
/*    */     public static final String WORLD_POS_Z = "worldPosZ";
/*    */     public static final String CHUNK_POS_X = "chunkPosX";
/*    */     public static final String CHUNK_POS_Z = "chunkPosZ";
/*    */     public static final String STATUS = "status";
/*    */     public static final String LEVEL = "level";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\ChunkGenerationEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */