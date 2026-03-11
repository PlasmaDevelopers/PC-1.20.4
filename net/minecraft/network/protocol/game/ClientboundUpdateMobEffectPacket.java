/*     */ package net.minecraft.network.protocol.game;
/*     */ 
/*     */ import com.mojang.serialization.DynamicOps;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.IdMap;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.NbtOps;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.PacketListener;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.world.effect.MobEffect;
/*     */ import net.minecraft.world.effect.MobEffectInstance;
/*     */ 
/*     */ public class ClientboundUpdateMobEffectPacket
/*     */   implements Packet<ClientGamePacketListener>
/*     */ {
/*     */   private static final int FLAG_AMBIENT = 1;
/*     */   private static final int FLAG_VISIBLE = 2;
/*     */   private static final int FLAG_SHOW_ICON = 4;
/*     */   private final int entityId;
/*     */   private final MobEffect effect;
/*     */   private final byte effectAmplifier;
/*     */   private final int effectDurationTicks;
/*     */   private final byte flags;
/*     */   @Nullable
/*     */   private final MobEffectInstance.FactorData factorData;
/*     */   
/*     */   public ClientboundUpdateMobEffectPacket(int $$0, MobEffectInstance $$1) {
/*  29 */     this.entityId = $$0;
/*  30 */     this.effect = $$1.getEffect();
/*  31 */     this.effectAmplifier = (byte)($$1.getAmplifier() & 0xFF);
/*  32 */     this.effectDurationTicks = $$1.getDuration();
/*  33 */     byte $$2 = 0;
/*     */     
/*  35 */     if ($$1.isAmbient()) {
/*  36 */       $$2 = (byte)($$2 | 0x1);
/*     */     }
/*  38 */     if ($$1.isVisible()) {
/*  39 */       $$2 = (byte)($$2 | 0x2);
/*     */     }
/*  41 */     if ($$1.showIcon()) {
/*  42 */       $$2 = (byte)($$2 | 0x4);
/*     */     }
/*  44 */     this.flags = $$2;
/*  45 */     this.factorData = $$1.getFactorData().orElse(null);
/*     */   }
/*     */   
/*     */   public ClientboundUpdateMobEffectPacket(FriendlyByteBuf $$0) {
/*  49 */     this.entityId = $$0.readVarInt();
/*  50 */     this.effect = (MobEffect)$$0.readById((IdMap)BuiltInRegistries.MOB_EFFECT);
/*  51 */     this.effectAmplifier = $$0.readByte();
/*  52 */     this.effectDurationTicks = $$0.readVarInt();
/*  53 */     this.flags = $$0.readByte();
/*  54 */     this.factorData = (MobEffectInstance.FactorData)$$0.readNullable($$0 -> (MobEffectInstance.FactorData)$$0.readWithCodecTrusted((DynamicOps)NbtOps.INSTANCE, MobEffectInstance.FactorData.CODEC));
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(FriendlyByteBuf $$0) {
/*  59 */     $$0.writeVarInt(this.entityId);
/*  60 */     $$0.writeId((IdMap)BuiltInRegistries.MOB_EFFECT, this.effect);
/*  61 */     $$0.writeByte(this.effectAmplifier);
/*  62 */     $$0.writeVarInt(this.effectDurationTicks);
/*  63 */     $$0.writeByte(this.flags);
/*  64 */     $$0.writeNullable(this.factorData, ($$0, $$1) -> $$0.writeWithCodec((DynamicOps)NbtOps.INSTANCE, MobEffectInstance.FactorData.CODEC, $$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public void handle(ClientGamePacketListener $$0) {
/*  69 */     $$0.handleUpdateMobEffect(this);
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*  73 */     return this.entityId;
/*     */   }
/*     */   
/*     */   public MobEffect getEffect() {
/*  77 */     return this.effect;
/*     */   }
/*     */   
/*     */   public byte getEffectAmplifier() {
/*  81 */     return this.effectAmplifier;
/*     */   }
/*     */   
/*     */   public int getEffectDurationTicks() {
/*  85 */     return this.effectDurationTicks;
/*     */   }
/*     */   
/*     */   public boolean isEffectVisible() {
/*  89 */     return ((this.flags & 0x2) == 2);
/*     */   }
/*     */   
/*     */   public boolean isEffectAmbient() {
/*  93 */     return ((this.flags & 0x1) == 1);
/*     */   }
/*     */   
/*     */   public boolean effectShowsIcon() {
/*  97 */     return ((this.flags & 0x4) == 4);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MobEffectInstance.FactorData getFactorData() {
/* 102 */     return this.factorData;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\protocol\game\ClientboundUpdateMobEffectPacket.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */