/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import jdk.jfr.Category;
/*    */ import jdk.jfr.Event;
/*    */ import jdk.jfr.EventType;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import jdk.jfr.StackTrace;
/*    */ import net.minecraft.obfuscate.DontObfuscate;
/*    */ 
/*    */ @Name("minecraft.LoadWorld")
/*    */ @Label("Create/Load World")
/*    */ @Category({"Minecraft", "World Generation"})
/*    */ @StackTrace(false)
/*    */ @DontObfuscate
/*    */ public class WorldLoadFinishedEvent
/*    */   extends Event {
/*    */   public static final String EVENT_NAME = "minecraft.LoadWorld";
/* 19 */   public static final EventType TYPE = EventType.getEventType((Class)WorldLoadFinishedEvent.class);
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\WorldLoadFinishedEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */