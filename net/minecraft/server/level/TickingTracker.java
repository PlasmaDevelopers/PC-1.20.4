/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import com.mojang.datafixers.util.Pair;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.SortedArraySet;
/*     */ import net.minecraft.world.level.ChunkPos;
/*     */ 
/*     */ public class TickingTracker
/*     */   extends ChunkTracker
/*     */ {
/*     */   public static final int MAX_LEVEL = 33;
/*     */   private static final int INITIAL_TICKET_LIST_CAPACITY = 4;
/*  19 */   protected final Long2ByteMap chunks = (Long2ByteMap)new Long2ByteOpenHashMap();
/*  20 */   private final Long2ObjectOpenHashMap<SortedArraySet<Ticket<?>>> tickets = new Long2ObjectOpenHashMap();
/*     */   
/*     */   public TickingTracker() {
/*  23 */     super(34, 16, 256);
/*  24 */     this.chunks.defaultReturnValue((byte)33);
/*     */   }
/*     */   
/*     */   private SortedArraySet<Ticket<?>> getTickets(long $$0) {
/*  28 */     return (SortedArraySet<Ticket<?>>)this.tickets.computeIfAbsent($$0, $$0 -> SortedArraySet.create(4));
/*     */   }
/*     */   
/*     */   private int getTicketLevelAt(SortedArraySet<Ticket<?>> $$0) {
/*  32 */     return $$0.isEmpty() ? 34 : ((Ticket)$$0.first()).getTicketLevel();
/*     */   }
/*     */   
/*     */   public void addTicket(long $$0, Ticket<?> $$1) {
/*  36 */     SortedArraySet<Ticket<?>> $$2 = getTickets($$0);
/*  37 */     int $$3 = getTicketLevelAt($$2);
/*  38 */     $$2.add($$1);
/*  39 */     if ($$1.getTicketLevel() < $$3) {
/*  40 */       update($$0, $$1.getTicketLevel(), true);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeTicket(long $$0, Ticket<?> $$1) {
/*  45 */     SortedArraySet<Ticket<?>> $$2 = getTickets($$0);
/*  46 */     $$2.remove($$1);
/*  47 */     if ($$2.isEmpty()) {
/*  48 */       this.tickets.remove($$0);
/*     */     }
/*  50 */     update($$0, getTicketLevelAt($$2), false);
/*     */   }
/*     */   
/*     */   public <T> void addTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/*  54 */     addTicket($$1.toLong(), new Ticket($$0, $$2, $$3));
/*     */   }
/*     */   
/*     */   public <T> void removeTicket(TicketType<T> $$0, ChunkPos $$1, int $$2, T $$3) {
/*  58 */     Ticket<T> $$4 = new Ticket<>($$0, $$2, $$3);
/*  59 */     removeTicket($$1.toLong(), $$4);
/*     */   }
/*     */   
/*     */   public void replacePlayerTicketsLevel(int $$0) {
/*  63 */     List<Pair<Ticket<ChunkPos>, Long>> $$1 = new ArrayList<>();
/*  64 */     for (ObjectIterator<Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>>> objectIterator = this.tickets.long2ObjectEntrySet().iterator(); objectIterator.hasNext(); ) { Long2ObjectMap.Entry<SortedArraySet<Ticket<?>>> $$2 = objectIterator.next();
/*  65 */       for (Ticket<?> $$3 : (Iterable<Ticket<?>>)$$2.getValue()) {
/*  66 */         if ($$3.getType() == TicketType.PLAYER) {
/*  67 */           $$1.add(Pair.of($$3, Long.valueOf($$2.getLongKey())));
/*     */         }
/*     */       }  }
/*     */     
/*  71 */     for (Pair<Ticket<ChunkPos>, Long> $$4 : $$1) {
/*  72 */       Long $$5 = (Long)$$4.getSecond();
/*  73 */       Ticket<ChunkPos> $$6 = (Ticket<ChunkPos>)$$4.getFirst();
/*  74 */       removeTicket($$5.longValue(), $$6);
/*  75 */       ChunkPos $$7 = new ChunkPos($$5.longValue());
/*  76 */       TicketType<ChunkPos> $$8 = $$6.getType();
/*  77 */       addTicket($$8, $$7, $$0, $$7);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLevelFromSource(long $$0) {
/*  83 */     SortedArraySet<Ticket<?>> $$1 = (SortedArraySet<Ticket<?>>)this.tickets.get($$0);
/*  84 */     if ($$1 == null || $$1.isEmpty()) {
/*  85 */       return Integer.MAX_VALUE;
/*     */     }
/*  87 */     return ((Ticket)$$1.first()).getTicketLevel();
/*     */   }
/*     */   
/*     */   public int getLevel(ChunkPos $$0) {
/*  91 */     return getLevel($$0.toLong());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getLevel(long $$0) {
/*  96 */     return this.chunks.get($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setLevel(long $$0, int $$1) {
/* 101 */     if ($$1 >= 33) {
/* 102 */       this.chunks.remove($$0);
/*     */     } else {
/* 104 */       this.chunks.put($$0, (byte)$$1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void runAllUpdates() {
/* 109 */     runUpdates(2147483647);
/*     */   }
/*     */   
/*     */   public String getTicketDebugString(long $$0) {
/* 113 */     SortedArraySet<Ticket<?>> $$1 = (SortedArraySet<Ticket<?>>)this.tickets.get($$0);
/* 114 */     if ($$1 == null || $$1.isEmpty()) {
/* 115 */       return "no_ticket";
/*     */     }
/* 117 */     return ((Ticket)$$1.first()).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\TickingTracker.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */