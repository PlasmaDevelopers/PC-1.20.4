/*    */ package net.minecraft.client;
/*    */ 
/*    */ import java.util.function.IntFunction;
/*    */ import net.minecraft.util.ByIdMap;
/*    */ import net.minecraft.util.OptionEnum;
/*    */ 
/*    */ public enum PrioritizeChunkUpdates implements OptionEnum {
/*    */   private static final IntFunction<PrioritizeChunkUpdates> BY_ID;
/*  9 */   NONE(0, "options.prioritizeChunkUpdates.none"),
/* 10 */   PLAYER_AFFECTED(1, "options.prioritizeChunkUpdates.byPlayer"),
/* 11 */   NEARBY(2, "options.prioritizeChunkUpdates.nearby");
/*    */   
/*    */   static {
/* 14 */     BY_ID = ByIdMap.continuous(PrioritizeChunkUpdates::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.WRAP);
/*    */   }
/*    */   
/*    */   private final int id;
/*    */   
/*    */   PrioritizeChunkUpdates(int $$0, String $$1) {
/* 20 */     this.id = $$0;
/* 21 */     this.key = $$1;
/*    */   }
/*    */   private final String key;
/*    */   
/*    */   public int getId() {
/* 26 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getKey() {
/* 31 */     return this.key;
/*    */   }
/*    */   
/*    */   public static PrioritizeChunkUpdates byId(int $$0) {
/* 35 */     return BY_ID.apply($$0);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\PrioritizeChunkUpdates.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */