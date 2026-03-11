/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import jdk.jfr.Category;
/*    */ import jdk.jfr.DataAmount;
/*    */ import jdk.jfr.Enabled;
/*    */ import jdk.jfr.Event;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import jdk.jfr.StackTrace;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Category({"Minecraft", "Network"})
/*    */ @StackTrace(false)
/*    */ @Enabled(false)
/*    */ public abstract class PacketEvent
/*    */   extends Event
/*    */ {
/*    */   @Name("protocolId")
/*    */   @Label("Protocol Id")
/*    */   public final String protocolId;
/*    */   @Name("packetId")
/*    */   @Label("Packet Id")
/*    */   public final int packetId;
/*    */   @Name("remoteAddress")
/*    */   @Label("Remote Address")
/*    */   public final String remoteAddress;
/*    */   @Name("bytes")
/*    */   @Label("Bytes")
/*    */   @DataAmount
/*    */   public final int bytes;
/*    */   
/*    */   public PacketEvent(String $$0, int $$1, SocketAddress $$2, int $$3) {
/* 37 */     this.protocolId = $$0;
/* 38 */     this.packetId = $$1;
/* 39 */     this.remoteAddress = $$2.toString();
/* 40 */     this.bytes = $$3;
/*    */   }
/*    */   
/*    */   public static final class Fields {
/*    */     public static final String REMOTE_ADDRESS = "remoteAddress";
/*    */     public static final String PROTOCOL_ID = "protocolId";
/*    */     public static final String PACKET_ID = "packetId";
/*    */     public static final String BYTES = "bytes";
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\PacketEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */