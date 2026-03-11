/*    */ package net.minecraft.world.entity.ai.gossip;
/*    */ 
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function3;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.core.UUIDUtil;
/*    */ import net.minecraft.util.ExtraCodecs;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class GossipEntry
/*    */   extends Record
/*    */ {
/*    */   final UUID target;
/*    */   final GossipType type;
/*    */   final int value;
/*    */   public static final Codec<GossipEntry> CODEC;
/*    */   
/*    */   public final String toString() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;)Ljava/lang/String;
/*    */     //   6: areturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;
/*    */   }
/*    */   
/*    */   public final int hashCode() {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;)I
/*    */     //   6: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	7	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;
/*    */   }
/*    */   
/*    */   public final boolean equals(Object $$0) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: aload_1
/*    */     //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;Ljava/lang/Object;)Z
/*    */     //   7: ireturn
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #37	-> 0
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	8	0	this	Lnet/minecraft/world/entity/ai/gossip/GossipContainer$GossipEntry;
/*    */     //   0	8	1	$$0	Ljava/lang/Object;
/*    */   }
/*    */   
/*    */   GossipEntry(UUID $$0, GossipType $$1, int $$2) {
/* 37 */     this.target = $$0; this.type = $$1; this.value = $$2; } public UUID target() { return this.target; } public GossipType type() { return this.type; } public int value() { return this.value; } static {
/* 38 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)UUIDUtil.CODEC.fieldOf("Target").forGetter(GossipEntry::target), (App)GossipType.CODEC.fieldOf("Type").forGetter(GossipEntry::type), (App)ExtraCodecs.POSITIVE_INT.fieldOf("Value").forGetter(GossipEntry::value)).apply((Applicative)$$0, GossipEntry::new));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 44 */   public static final Codec<List<GossipEntry>> LIST_CODEC = CODEC.listOf();
/*    */   
/*    */   public int weightedValue() {
/* 47 */     return this.value * this.type.weight;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\gossip\GossipContainer$GossipEntry.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */