/*    */ package net.minecraft.sounds;
/*    */ import com.mojang.datafixers.kinds.App;
/*    */ import com.mojang.datafixers.kinds.Applicative;
/*    */ import com.mojang.serialization.Codec;
/*    */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*    */ import java.util.Optional;
/*    */ import net.minecraft.core.Holder;
/*    */ import net.minecraft.network.FriendlyByteBuf;
/*    */ import net.minecraft.resources.RegistryFileCodec;
/*    */ import net.minecraft.resources.ResourceLocation;
/*    */ 
/*    */ public class SoundEvent {
/*    */   static {
/* 14 */     DIRECT_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ResourceLocation.CODEC.fieldOf("sound_id").forGetter(SoundEvent::getLocation), (App)Codec.FLOAT.optionalFieldOf("range").forGetter(SoundEvent::fixedRange)).apply((Applicative)$$0, SoundEvent::create));
/*    */   }
/*    */ 
/*    */   
/*    */   public static final Codec<SoundEvent> DIRECT_CODEC;
/* 19 */   public static final Codec<Holder<SoundEvent>> CODEC = (Codec<Holder<SoundEvent>>)RegistryFileCodec.create(Registries.SOUND_EVENT, DIRECT_CODEC);
/*    */ 
/*    */   
/*    */   private static final float DEFAULT_RANGE = 16.0F;
/*    */   
/*    */   private final ResourceLocation location;
/*    */   
/*    */   private final float range;
/*    */   
/*    */   private final boolean newSystem;
/*    */ 
/*    */   
/*    */   private static SoundEvent create(ResourceLocation $$0, Optional<Float> $$1) {
/* 32 */     return $$1.<SoundEvent>map($$1 -> createFixedRangeEvent($$0, $$1.floatValue())).orElseGet(() -> createVariableRangeEvent($$0));
/*    */   }
/*    */   
/*    */   public static SoundEvent createVariableRangeEvent(ResourceLocation $$0) {
/* 36 */     return new SoundEvent($$0, 16.0F, false);
/*    */   }
/*    */   
/*    */   public static SoundEvent createFixedRangeEvent(ResourceLocation $$0, float $$1) {
/* 40 */     return new SoundEvent($$0, $$1, true);
/*    */   }
/*    */   
/*    */   private SoundEvent(ResourceLocation $$0, float $$1, boolean $$2) {
/* 44 */     this.location = $$0;
/* 45 */     this.range = $$1;
/* 46 */     this.newSystem = $$2;
/*    */   }
/*    */   
/*    */   public ResourceLocation getLocation() {
/* 50 */     return this.location;
/*    */   }
/*    */   
/*    */   public float getRange(float $$0) {
/* 54 */     if (this.newSystem) {
/* 55 */       return this.range;
/*    */     }
/* 57 */     return ($$0 > 1.0F) ? (16.0F * $$0) : 16.0F;
/*    */   }
/*    */   
/*    */   private Optional<Float> fixedRange() {
/* 61 */     return this.newSystem ? Optional.<Float>of(Float.valueOf(this.range)) : Optional.<Float>empty();
/*    */   }
/*    */   
/*    */   public void writeToNetwork(FriendlyByteBuf $$0) {
/* 65 */     $$0.writeResourceLocation(this.location);
/* 66 */     $$0.writeOptional(fixedRange(), FriendlyByteBuf::writeFloat);
/*    */   }
/*    */   
/*    */   public static SoundEvent readFromNetwork(FriendlyByteBuf $$0) {
/* 70 */     ResourceLocation $$1 = $$0.readResourceLocation();
/* 71 */     Optional<Float> $$2 = $$0.readOptional(FriendlyByteBuf::readFloat);
/* 72 */     return create($$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\sounds\SoundEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */