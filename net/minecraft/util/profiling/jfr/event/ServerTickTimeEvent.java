/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import jdk.jfr.Category;
/*    */ import jdk.jfr.Event;
/*    */ import jdk.jfr.EventType;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import jdk.jfr.Period;
/*    */ import jdk.jfr.StackTrace;
/*    */ import jdk.jfr.Timespan;
/*    */ import net.minecraft.obfuscate.DontObfuscate;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Name("minecraft.ServerTickTime")
/*    */ @Label("Server Tick Time")
/*    */ @Category({"Minecraft", "Ticking"})
/*    */ @StackTrace(false)
/*    */ @Period("1 s")
/*    */ @DontObfuscate
/*    */ public class ServerTickTimeEvent
/*    */   extends Event
/*    */ {
/*    */   public static final String EVENT_NAME = "minecraft.ServerTickTime";
/* 25 */   public static final EventType TYPE = EventType.getEventType((Class)ServerTickTimeEvent.class);
/*    */   
/*    */   @Name("averageTickDuration")
/*    */   @Label("Average Server Tick Duration")
/*    */   @Timespan
/*    */   public final long averageTickDurationNanos;
/*    */ 
/*    */   
/*    */   public ServerTickTimeEvent(float $$0) {
/* 34 */     this.averageTickDurationNanos = (long)(1000000.0F * $$0);
/*    */   }
/*    */   
/*    */   public static class Fields {
/*    */     public static final String AVERAGE_TICK_DURATION = "averageTickDuration";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\ServerTickTimeEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */