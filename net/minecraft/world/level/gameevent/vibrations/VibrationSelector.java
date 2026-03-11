/*    */ package net.minecraft.world.level.gameevent.vibrations;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import org.apache.commons.lang3.tuple.Pair;
/*    */ 
/*    */ public class VibrationSelector {
/*    */   static {
/* 10 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)VibrationInfo.CODEC.optionalFieldOf("event").forGetter(()), (App)Codec.LONG.fieldOf("tick").forGetter(())).apply((Applicative)$$0, VibrationSelector::new));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<VibrationSelector> CODEC;
/*    */   private Optional<Pair<VibrationInfo, Long>> currentVibrationData;
/*    */   
/*    */   public VibrationSelector(Optional<VibrationInfo> $$0, long $$1) {
/* 18 */     this.currentVibrationData = $$0.map($$1 -> Pair.of($$1, Long.valueOf($$0)));
/*    */   }
/*    */   
/*    */   public VibrationSelector() {
/* 22 */     this.currentVibrationData = Optional.empty();
/*    */   }
/*    */   
/*    */   public void addCandidate(VibrationInfo $$0, long $$1) {
/* 26 */     if (shouldReplaceVibration($$0, $$1)) {
/* 27 */       this.currentVibrationData = Optional.of(Pair.of($$0, Long.valueOf($$1)));
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean shouldReplaceVibration(VibrationInfo $$0, long $$1) {
/* 32 */     if (this.currentVibrationData.isEmpty()) {
/* 33 */       return true;
/*    */     }
/* 35 */     Pair<VibrationInfo, Long> $$2 = this.currentVibrationData.get();
/* 36 */     long $$3 = ((Long)$$2.getRight()).longValue();
/* 37 */     if ($$1 != $$3)
/*    */     {
/* 39 */       return false;
/*    */     }
/* 41 */     VibrationInfo $$4 = (VibrationInfo)$$2.getLeft();
/* 42 */     if ($$0.distance() < $$4.distance())
/* 43 */       return true; 
/* 44 */     if ($$0.distance() > $$4.distance()) {
/* 45 */       return false;
/*    */     }
/* 47 */     return (VibrationSystem.getGameEventFrequency($$0.gameEvent()) > VibrationSystem.getGameEventFrequency($$4.gameEvent()));
/*    */   }
/*    */   
/*    */   public Optional<VibrationInfo> chosenCandidate(long $$0) {
/* 51 */     if (this.currentVibrationData.isEmpty()) {
/* 52 */       return Optional.empty();
/*    */     }
/* 54 */     if (((Long)((Pair)this.currentVibrationData.get()).getRight()).longValue() < $$0) {
/* 55 */       return Optional.of((VibrationInfo)((Pair)this.currentVibrationData.get()).getLeft());
/*    */     }
/* 57 */     return Optional.empty();
/*    */   }
/*    */   
/*    */   public void startOver() {
/* 61 */     this.currentVibrationData = Optional.empty();
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\vibrations\VibrationSelector.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */