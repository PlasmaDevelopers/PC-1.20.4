/*    */ package net.minecraft.world.ticks;
/*    */ public final class ScheduledTick<T> extends Record {
/*    */   private final T type;
/*    */   private final BlockPos pos;
/*    */   private final long triggerTick;
/*    */   private final TickPriority priority;
/*    */   private final long subTickOrder;
/*    */   
/*  9 */   public T type() { return this.type; } public static final Comparator<ScheduledTick<?>> DRAIN_ORDER; public static final Comparator<ScheduledTick<?>> INTRA_TICK_DRAIN_ORDER; public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/ticks/ScheduledTick;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/ScheduledTick;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/ScheduledTick<TT;>; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/ticks/ScheduledTick;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/ScheduledTick;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/ScheduledTick<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/ticks/ScheduledTick;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #9	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/ticks/ScheduledTick;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*  9 */     //   0	8	0	this	Lnet/minecraft/world/ticks/ScheduledTick<TT;>; } public BlockPos pos() { return this.pos; } public long triggerTick() { return this.triggerTick; } public TickPriority priority() { return this.priority; } public long subTickOrder() { return this.subTickOrder; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static {
/* 17 */     DRAIN_ORDER = (($$0, $$1) -> {
/*    */         int $$2 = Long.compare($$0.triggerTick, $$1.triggerTick);
/*    */ 
/*    */         
/*    */         if ($$2 != 0) {
/*    */           return $$2;
/*    */         }
/*    */ 
/*    */         
/*    */         $$2 = $$0.priority.compareTo($$1.priority);
/*    */         
/*    */         return ($$2 != 0) ? $$2 : Long.compare($$0.subTickOrder, $$1.subTickOrder);
/*    */       });
/*    */     
/* 31 */     INTRA_TICK_DRAIN_ORDER = (($$0, $$1) -> {
/*    */         int $$2 = $$0.priority.compareTo($$1.priority);
/*    */         return ($$2 != 0) ? $$2 : Long.compare($$0.subTickOrder, $$1.subTickOrder);
/*    */       });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public static final Hash.Strategy<ScheduledTick<?>> UNIQUE_TICK_HASH = new Hash.Strategy<ScheduledTick<?>>()
/*    */     {
/*    */       public int hashCode(ScheduledTick<?> $$0) {
/* 43 */         return 31 * $$0.pos().hashCode() + $$0.type().hashCode();
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(@Nullable ScheduledTick<?> $$0, @Nullable ScheduledTick<?> $$1) {
/* 48 */         if ($$0 == $$1) {
/* 49 */           return true;
/*    */         }
/* 51 */         if ($$0 == null || $$1 == null) {
/* 52 */           return false;
/*    */         }
/* 54 */         return ($$0.type() == $$1.type() && $$0.pos().equals($$1.pos()));
/*    */       }
/*    */     };
/*    */   
/*    */   public ScheduledTick(T $$0, BlockPos $$1, long $$2, long $$3) {
/* 59 */     this($$0, $$1, $$2, TickPriority.NORMAL, $$3);
/*    */   }
/*    */   
/*    */   public ScheduledTick(T $$0, BlockPos $$1, long $$2, TickPriority $$3, long $$4) {
/* 63 */     $$1 = $$1.immutable(); this.type = $$0;
/*    */     this.pos = $$1;
/*    */     this.triggerTick = $$2;
/*    */     this.priority = $$3;
/* 67 */     this.subTickOrder = $$4; } public static <T> ScheduledTick<T> probe(T $$0, BlockPos $$1) { return new ScheduledTick<>($$0, $$1, 0L, TickPriority.NORMAL, 0L); }
/*    */ 
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\ScheduledTick.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */