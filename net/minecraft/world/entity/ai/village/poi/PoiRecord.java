/*    */ package net.minecraft.world.entity.ai.village.poi;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.datafixers.util.Function4;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import net.minecraft.core.BlockPos;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.core.registries.Registries;
/*    */ import net.minecraft.resources.RegistryFixedCodec;
/*    */ import net.minecraft.util.VisibleForDebug;
/*    */ 
/*    */ public class PoiRecord {
/*    */   public static Codec<PoiRecord> codec(Runnable $$0) {
/* 15 */     return RecordCodecBuilder.create($$1 -> $$1.group((App)BlockPos.CODEC.fieldOf("pos").forGetter(()), (App)RegistryFixedCodec.create(Registries.POINT_OF_INTEREST_TYPE).fieldOf("type").forGetter(()), (App)Codec.INT.fieldOf("free_tickets").orElse(Integer.valueOf(0)).forGetter(()), (App)RecordCodecBuilder.point($$0)).apply((Applicative)$$1, PoiRecord::new));
/*    */   }
/*    */ 
/*    */   
/*    */   private final BlockPos pos;
/*    */   
/*    */   private final Holder<PoiType> poiType;
/*    */   
/*    */   private int freeTickets;
/*    */   
/*    */   private final Runnable setDirty;
/*    */ 
/*    */   
/*    */   private PoiRecord(BlockPos $$0, Holder<PoiType> $$1, int $$2, Runnable $$3) {
/* 29 */     this.pos = $$0.immutable();
/* 30 */     this.poiType = $$1;
/* 31 */     this.freeTickets = $$2;
/* 32 */     this.setDirty = $$3;
/*    */   }
/*    */   
/*    */   public PoiRecord(BlockPos $$0, Holder<PoiType> $$1, Runnable $$2) {
/* 36 */     this($$0, $$1, ((PoiType)$$1.value()).maxTickets(), $$2);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   @VisibleForDebug
/*    */   public int getFreeTickets() {
/* 42 */     return this.freeTickets;
/*    */   }
/*    */   
/*    */   protected boolean acquireTicket() {
/* 46 */     if (this.freeTickets <= 0) {
/* 47 */       return false;
/*    */     }
/*    */     
/* 50 */     this.freeTickets--;
/* 51 */     this.setDirty.run();
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   protected boolean releaseTicket() {
/* 56 */     if (this.freeTickets >= ((PoiType)this.poiType.value()).maxTickets()) {
/* 57 */       return false;
/*    */     }
/*    */     
/* 60 */     this.freeTickets++;
/* 61 */     this.setDirty.run();
/* 62 */     return true;
/*    */   }
/*    */   
/*    */   public boolean hasSpace() {
/* 66 */     return (this.freeTickets > 0);
/*    */   }
/*    */   
/*    */   public boolean isOccupied() {
/* 70 */     return (this.freeTickets != ((PoiType)this.poiType.value()).maxTickets());
/*    */   }
/*    */   
/*    */   public BlockPos getPos() {
/* 74 */     return this.pos;
/*    */   }
/*    */   
/*    */   public Holder<PoiType> getPoiType() {
/* 78 */     return this.poiType;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 83 */     if (this == $$0) {
/* 84 */       return true;
/*    */     }
/* 86 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 87 */       return false;
/*    */     }
/*    */     
/* 90 */     return Objects.equals(this.pos, ((PoiRecord)$$0).pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 95 */     return this.pos.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\ai\village\poi\PoiRecord.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */