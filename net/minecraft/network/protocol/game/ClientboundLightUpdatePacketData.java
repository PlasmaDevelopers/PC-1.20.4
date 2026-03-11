/*    */ package net.minecraft.network.protocol.game;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.BitSet;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.core.SectionPos;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.world.level.ChunkPos;
/*    */ import net.minecraft.world.level.LightLayer;
/*    */ import net.minecraft.world.level.chunk.DataLayer;
/*    */ import net.minecraft.world.level.lighting.LevelLightEngine;
/*    */ 
/*    */ public class ClientboundLightUpdatePacketData
/*    */ {
/*    */   private final BitSet skyYMask;
/*    */   private final BitSet blockYMask;
/*    */   private final BitSet emptySkyYMask;
/*    */   private final BitSet emptyBlockYMask;
/*    */   private final List<byte[]> skyUpdates;
/*    */   private final List<byte[]> blockUpdates;
/*    */   
/*    */   public ClientboundLightUpdatePacketData(ChunkPos $$0, LevelLightEngine $$1, @Nullable BitSet $$2, @Nullable BitSet $$3) {
/* 24 */     this.skyYMask = new BitSet();
/* 25 */     this.blockYMask = new BitSet();
/* 26 */     this.emptySkyYMask = new BitSet();
/* 27 */     this.emptyBlockYMask = new BitSet();
/* 28 */     this.skyUpdates = Lists.newArrayList();
/* 29 */     this.blockUpdates = Lists.newArrayList();
/* 30 */     for (int $$4 = 0; $$4 < $$1.getLightSectionCount(); $$4++) {
/* 31 */       if ($$2 == null || $$2.get($$4)) {
/* 32 */         prepareSectionData($$0, $$1, LightLayer.SKY, $$4, this.skyYMask, this.emptySkyYMask, this.skyUpdates);
/*    */       }
/* 34 */       if ($$3 == null || $$3.get($$4)) {
/* 35 */         prepareSectionData($$0, $$1, LightLayer.BLOCK, $$4, this.blockYMask, this.emptyBlockYMask, this.blockUpdates);
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public ClientboundLightUpdatePacketData(FriendlyByteBuf $$0, int $$1, int $$2) {
/* 41 */     this.skyYMask = $$0.readBitSet();
/* 42 */     this.blockYMask = $$0.readBitSet();
/* 43 */     this.emptySkyYMask = $$0.readBitSet();
/* 44 */     this.emptyBlockYMask = $$0.readBitSet();
/* 45 */     this.skyUpdates = $$0.readList($$0 -> $$0.readByteArray(2048));
/* 46 */     this.blockUpdates = $$0.readList($$0 -> $$0.readByteArray(2048));
/*    */   }
/*    */   
/*    */   public void write(FriendlyByteBuf $$0) {
/* 50 */     $$0.writeBitSet(this.skyYMask);
/* 51 */     $$0.writeBitSet(this.blockYMask);
/* 52 */     $$0.writeBitSet(this.emptySkyYMask);
/* 53 */     $$0.writeBitSet(this.emptyBlockYMask);
/* 54 */     $$0.writeCollection(this.skyUpdates, FriendlyByteBuf::writeByteArray);
/* 55 */     $$0.writeCollection(this.blockUpdates, FriendlyByteBuf::writeByteArray);
/*    */   }
/*    */   
/*    */   private void prepareSectionData(ChunkPos $$0, LevelLightEngine $$1, LightLayer $$2, int $$3, BitSet $$4, BitSet $$5, List<byte[]> $$6) {
/* 59 */     DataLayer $$7 = $$1.getLayerListener($$2).getDataLayerData(SectionPos.of($$0, $$1.getMinLightSection() + $$3));
/* 60 */     if ($$7 != null) {
/* 61 */       if ($$7.isEmpty()) {
/* 62 */         $$5.set($$3);
/*    */       } else {
/* 64 */         $$4.set($$3);
/* 65 */         $$6.add($$7.copy().getData());
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BitSet getSkyYMask() {
/* 72 */     return this.skyYMask;
/*    */   }
/*    */   
/*    */   public BitSet getEmptySkyYMask() {
/* 76 */     return this.emptySkyYMask;
/*    */   }
/*    */   
/*    */   public List<byte[]> getSkyUpdates() {
/* 80 */     return this.skyUpdates;
/*    */   }
/*    */   
/*    */   public BitSet getBlockYMask() {
/* 84 */     return this.blockYMask;
/*    */   }
/*    */   
/*    */   public BitSet getEmptyBlockYMask() {
/* 88 */     return this.emptyBlockYMask;
/*    */   }
/*    */   
/*    */   public List<byte[]> getBlockUpdates() {
/* 92 */     return this.blockUpdates;
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundLightUpdatePacketData.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */