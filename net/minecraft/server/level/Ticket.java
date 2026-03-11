/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ public final class Ticket<T> implements Comparable<Ticket<?>> {
/*    */   private final TicketType<T> type;
/*    */   private final int ticketLevel;
/*    */   private final T key;
/*    */   private long createdTick;
/*    */   
/*    */   protected Ticket(TicketType<T> $$0, int $$1, T $$2) {
/* 12 */     this.type = $$0;
/* 13 */     this.ticketLevel = $$1;
/* 14 */     this.key = $$2;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(Ticket<?> $$0) {
/* 20 */     int $$1 = Integer.compare(this.ticketLevel, $$0.ticketLevel);
/* 21 */     if ($$1 != 0) {
/* 22 */       return $$1;
/*    */     }
/*    */     
/* 25 */     int $$2 = Integer.compare(System.identityHashCode(this.type), System.identityHashCode($$0.type));
/* 26 */     if ($$2 != 0) {
/* 27 */       return $$2;
/*    */     }
/*    */     
/* 30 */     return this.type.getComparator().compare(this.key, $$0.key);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 35 */     if (this == $$0) {
/* 36 */       return true;
/*    */     }
/* 38 */     if (!($$0 instanceof Ticket)) {
/* 39 */       return false;
/*    */     }
/* 41 */     Ticket<?> $$1 = (Ticket)$$0;
/* 42 */     return (this.ticketLevel == $$1.ticketLevel && Objects.equals(this.type, $$1.type) && Objects.equals(this.key, $$1.key));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 47 */     return Objects.hash(new Object[] { this.type, Integer.valueOf(this.ticketLevel), this.key });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "Ticket[" + this.type + " " + this.ticketLevel + " (" + this.key + ")] at " + this.createdTick;
/*    */   }
/*    */   
/*    */   public TicketType<T> getType() {
/* 56 */     return this.type;
/*    */   }
/*    */   
/*    */   public int getTicketLevel() {
/* 60 */     return this.ticketLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setCreatedTick(long $$0) {
/* 65 */     this.createdTick = $$0;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean timedOut(long $$0) {
/* 70 */     long $$1 = this.type.timeout();
/* 71 */     return ($$1 != 0L && $$0 - this.createdTick > $$1);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\Ticket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */