/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import jdk.jfr.EventType;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import net.minecraft.obfuscate.DontObfuscate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Name("minecraft.PacketSent")
/*    */ @Label("Network Packet Sent")
/*    */ @DontObfuscate
/*    */ public class PacketSentEvent
/*    */   extends PacketEvent
/*    */ {
/*    */   public static final String NAME = "minecraft.PacketSent";
/* 20 */   public static final EventType TYPE = EventType.getEventType((Class)PacketSentEvent.class);
/*    */   
/*    */   public PacketSentEvent(String $$0, int $$1, SocketAddress $$2, int $$3) {
/* 23 */     super($$0, $$1, $$2, $$3);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\PacketSentEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */