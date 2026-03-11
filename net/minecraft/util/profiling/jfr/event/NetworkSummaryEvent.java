/*    */ package net.minecraft.util.profiling.jfr.event;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicInteger;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ import jdk.jfr.Category;
/*    */ import jdk.jfr.DataAmount;
/*    */ import jdk.jfr.Event;
/*    */ import jdk.jfr.EventType;
/*    */ import jdk.jfr.Label;
/*    */ import jdk.jfr.Name;
/*    */ import jdk.jfr.Period;
/*    */ import jdk.jfr.StackTrace;
/*    */ import net.minecraft.obfuscate.DontObfuscate;
/*    */ 
/*    */ @Name("minecraft.NetworkSummary")
/*    */ @Label("Network Summary")
/*    */ @Category({"Minecraft", "Network"})
/*    */ @StackTrace(false)
/*    */ @Period("10 s")
/*    */ @DontObfuscate
/*    */ public class NetworkSummaryEvent
/*    */   extends Event
/*    */ {
/*    */   public static final String EVENT_NAME = "minecraft.NetworkSummary";
/* 25 */   public static final EventType TYPE = EventType.getEventType((Class)NetworkSummaryEvent.class);
/*    */   
/*    */   @Name("remoteAddress")
/*    */   @Label("Remote Address")
/*    */   public final String remoteAddress;
/*    */   
/*    */   @Name("sentBytes")
/*    */   @Label("Sent Bytes")
/*    */   @DataAmount
/*    */   public long sentBytes;
/*    */   
/*    */   @Name("sentPackets")
/*    */   @Label("Sent Packets")
/*    */   public int sentPackets;
/*    */   
/*    */   @Name("receivedBytes")
/*    */   @Label("Received Bytes")
/*    */   @DataAmount
/*    */   public long receivedBytes;
/*    */   
/*    */   @Name("receivedPackets")
/*    */   @Label("Received Packets")
/*    */   public int receivedPackets;
/*    */   
/*    */   public NetworkSummaryEvent(String $$0) {
/* 50 */     this.remoteAddress = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   public static final class Fields
/*    */   {
/*    */     public static final String REMOTE_ADDRESS = "remoteAddress";
/*    */     public static final String SENT_BYTES = "sentBytes";
/*    */     private static final String SENT_PACKETS = "sentPackets";
/*    */     public static final String RECEIVED_BYTES = "receivedBytes";
/*    */     private static final String RECEIVED_PACKETS = "receivedPackets";
/*    */   }
/*    */   
/*    */   public static final class SumAggregation
/*    */   {
/* 65 */     private final AtomicLong sentBytes = new AtomicLong();
/* 66 */     private final AtomicInteger sentPackets = new AtomicInteger();
/* 67 */     private final AtomicLong receivedBytes = new AtomicLong();
/* 68 */     private final AtomicInteger receivedPackets = new AtomicInteger();
/*    */     private final NetworkSummaryEvent event;
/*    */     
/*    */     public SumAggregation(String $$0) {
/* 72 */       this.event = new NetworkSummaryEvent($$0);
/* 73 */       this.event.begin();
/*    */     }
/*    */     
/*    */     public void trackSentPacket(int $$0) {
/* 77 */       this.sentPackets.incrementAndGet();
/* 78 */       this.sentBytes.addAndGet($$0);
/*    */     }
/*    */     
/*    */     public void trackReceivedPacket(int $$0) {
/* 82 */       this.receivedPackets.incrementAndGet();
/* 83 */       this.receivedBytes.addAndGet($$0);
/*    */     }
/*    */     
/*    */     public void commitEvent() {
/* 87 */       this.event.sentBytes = this.sentBytes.get();
/* 88 */       this.event.sentPackets = this.sentPackets.get();
/* 89 */       this.event.receivedBytes = this.receivedBytes.get();
/* 90 */       this.event.receivedPackets = this.receivedPackets.get();
/* 91 */       this.event.commit();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraf\\util\profiling\jfr\event\NetworkSummaryEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */