/*    */ package net.minecraft.server.level;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Vec3i;
/*    */ import net.minecraft.util.Unit;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ 
/*    */ 
/*    */ public class TicketType<T>
/*    */ {
/*    */   private final String name;
/*    */   private final Comparator<T> comparator;
/*    */   private final long timeout;
/* 15 */   public static final TicketType<Unit> START = create("start", ($$0, $$1) -> 0);
/* 16 */   public static final TicketType<Unit> DRAGON = create("dragon", ($$0, $$1) -> 0);
/* 17 */   public static final TicketType<ChunkPos> PLAYER = create("player", Comparator.comparingLong(ChunkPos::toLong));
/* 18 */   public static final TicketType<ChunkPos> FORCED = create("forced", Comparator.comparingLong(ChunkPos::toLong));
/* 19 */   public static final TicketType<ChunkPos> LIGHT = create("light", Comparator.comparingLong(ChunkPos::toLong));
/* 20 */   public static final TicketType<BlockPos> PORTAL = create("portal", Vec3i::compareTo, 300);
/* 21 */   public static final TicketType<Integer> POST_TELEPORT = create("post_teleport", Integer::compareTo, 5);
/* 22 */   public static final TicketType<ChunkPos> UNKNOWN = create("unknown", Comparator.comparingLong(ChunkPos::toLong), 1);
/*    */   
/*    */   public static <T> TicketType<T> create(String $$0, Comparator<T> $$1) {
/* 25 */     return new TicketType<>($$0, $$1, 0L);
/*    */   }
/*    */   
/*    */   public static <T> TicketType<T> create(String $$0, Comparator<T> $$1, int $$2) {
/* 29 */     return new TicketType<>($$0, $$1, $$2);
/*    */   }
/*    */   
/*    */   protected TicketType(String $$0, Comparator<T> $$1, long $$2) {
/* 33 */     this.name = $$0;
/* 34 */     this.comparator = $$1;
/* 35 */     this.timeout = $$2;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return this.name;
/*    */   }
/*    */   
/*    */   public Comparator<T> getComparator() {
/* 44 */     return this.comparator;
/*    */   }
/*    */   
/*    */   public long timeout() {
/* 48 */     return this.timeout;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\TicketType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */