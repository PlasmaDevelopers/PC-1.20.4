/*    */ package net.minecraft.world.entity.ai.gossip;
/*    */ 
/*    */ import com.mojang.serialization.Codec;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public enum GossipType
/*    */   implements StringRepresentable {
/*  8 */   MAJOR_NEGATIVE("major_negative", -5, 100, 10, 10),
/*  9 */   MINOR_NEGATIVE("minor_negative", -1, 200, 20, 20),
/*    */   
/* 11 */   MINOR_POSITIVE("minor_positive", 1, 25, 1, 5),
/* 12 */   MAJOR_POSITIVE("major_positive", 5, 20, 0, 20),
/*    */   
/* 14 */   TRADING("trading", 1, 25, 2, 20);
/*    */   
/*    */   public static final int REPUTATION_CHANGE_PER_EVENT = 25;
/*    */   public static final int REPUTATION_CHANGE_PER_EVERLASTING_MEMORY = 20;
/*    */   public static final int REPUTATION_CHANGE_PER_TRADE = 2;
/*    */   public final String id;
/*    */   public final int weight;
/*    */   public final int max;
/*    */   public final int decayPerDay;
/*    */   public final int decayPerTransfer;
/*    */   public static final Codec<GossipType> CODEC;
/*    */   
/*    */   static {
/* 27 */     CODEC = (Codec<GossipType>)StringRepresentable.fromEnum(GossipType::values);
/*    */   }
/*    */   GossipType(String $$0, int $$1, int $$2, int $$3, int $$4) {
/* 30 */     this.id = $$0;
/* 31 */     this.weight = $$1;
/* 32 */     this.max = $$2;
/* 33 */     this.decayPerDay = $$3;
/* 34 */     this.decayPerTransfer = $$4;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getSerializedName() {
/* 39 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\gossip\GossipType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */