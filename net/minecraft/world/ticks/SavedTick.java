/*    */ package net.minecraft.world.ticks;
/*    */ public final class SavedTick<T> extends Record {
/*    */   private final T type;
/*    */   private final BlockPos pos;
/*    */   private final int delay;
/*    */   private final TickPriority priority;
/*    */   private static final String TAG_ID = "i";
/*    */   private static final String TAG_X = "x";
/*    */   private static final String TAG_Y = "y";
/*    */   private static final String TAG_Z = "z";
/*    */   private static final String TAG_DELAY = "t";
/*    */   private static final String TAG_PRIORITY = "p";
/*    */   
/* 14 */   public SavedTick(T $$0, BlockPos $$1, int $$2, TickPriority $$3) { this.type = $$0; this.pos = $$1; this.delay = $$2; this.priority = $$3; } public final String toString() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/ticks/SavedTick;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/SavedTick;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 14 */     //   0	7	0	this	Lnet/minecraft/world/ticks/SavedTick<TT;>; } public T type() { return this.type; } public final int hashCode() { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/ticks/SavedTick;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/SavedTick;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	7	0	this	Lnet/minecraft/world/ticks/SavedTick<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/ticks/SavedTick;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #14	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/ticks/SavedTick;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */     // Local variable type table:
/*    */     //   start	length	slot	name	signature
/* 14 */     //   0	8	0	this	Lnet/minecraft/world/ticks/SavedTick<TT;>; } public BlockPos pos() { return this.pos; } public int delay() { return this.delay; } public TickPriority priority() { return this.priority; }
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
/* 27 */   public static final Hash.Strategy<SavedTick<?>> UNIQUE_TICK_HASH = new Hash.Strategy<SavedTick<?>>()
/*    */     {
/*    */       public int hashCode(SavedTick<?> $$0) {
/* 30 */         return 31 * $$0.pos().hashCode() + $$0.type().hashCode();
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean equals(@Nullable SavedTick<?> $$0, @Nullable SavedTick<?> $$1) {
/* 35 */         if ($$0 == $$1) {
/* 36 */           return true;
/*    */         }
/* 38 */         if ($$0 == null || $$1 == null) {
/* 39 */           return false;
/*    */         }
/* 41 */         return ($$0.type() == $$1.type() && $$0.pos().equals($$1.pos()));
/*    */       }
/*    */     };
/*    */   
/*    */   public static <T> void loadTickList(ListTag $$0, Function<String, Optional<T>> $$1, ChunkPos $$2, Consumer<SavedTick<T>> $$3) {
/* 46 */     long $$4 = $$2.toLong();
/* 47 */     for (int $$5 = 0; $$5 < $$0.size(); $$5++) {
/* 48 */       CompoundTag $$6 = $$0.getCompound($$5);
/*    */       
/* 50 */       loadTick($$6, $$1).ifPresent($$2 -> {
/*    */             if (ChunkPos.asLong($$2.pos()) == $$0) {
/*    */               $$1.accept($$2);
/*    */             }
/*    */           });
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static <T> Optional<SavedTick<T>> loadTick(CompoundTag $$0, Function<String, Optional<T>> $$1) {
/* 61 */     return ((Optional)$$1.apply($$0.getString("i"))).map($$1 -> {
/*    */           BlockPos $$2 = new BlockPos($$0.getInt("x"), $$0.getInt("y"), $$0.getInt("z"));
/*    */           return new SavedTick($$1, $$2, $$0.getInt("t"), TickPriority.byValue($$0.getInt("p")));
/*    */         });
/*    */   }
/*    */   
/*    */   private static CompoundTag saveTick(String $$0, BlockPos $$1, int $$2, TickPriority $$3) {
/* 68 */     CompoundTag $$4 = new CompoundTag();
/* 69 */     $$4.putString("i", $$0);
/* 70 */     $$4.putInt("x", $$1.getX());
/* 71 */     $$4.putInt("y", $$1.getY());
/* 72 */     $$4.putInt("z", $$1.getZ());
/* 73 */     $$4.putInt("t", $$2);
/* 74 */     $$4.putInt("p", $$3.getValue());
/* 75 */     return $$4;
/*    */   }
/*    */   
/*    */   public static <T> CompoundTag saveTick(ScheduledTick<T> $$0, Function<T, String> $$1, long $$2) {
/* 79 */     return saveTick($$1.apply($$0.type()), $$0.pos(), (int)($$0.triggerTick() - $$2), $$0.priority());
/*    */   }
/*    */   
/*    */   public CompoundTag save(Function<T, String> $$0) {
/* 83 */     return saveTick($$0.apply(this.type), this.pos, this.delay, this.priority);
/*    */   }
/*    */   
/*    */   public ScheduledTick<T> unpack(long $$0, long $$1) {
/* 87 */     return new ScheduledTick<>(this.type, this.pos, $$0 + this.delay, this.priority, $$1);
/*    */   }
/*    */   
/*    */   public static <T> SavedTick<T> probe(T $$0, BlockPos $$1) {
/* 91 */     return new SavedTick<>($$0, $$1, 0, TickPriority.NORMAL);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\ticks\SavedTick.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */