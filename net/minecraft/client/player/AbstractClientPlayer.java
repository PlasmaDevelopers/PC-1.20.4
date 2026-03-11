/*     */ package net.minecraft.client.player;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.multiplayer.PlayerInfo;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.client.resources.PlayerSkin;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.ai.attributes.Attributes;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.level.GameType;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class AbstractClientPlayer
/*     */   extends Player
/*     */ {
/*     */   @Nullable
/*     */   private PlayerInfo playerInfo;
/*  24 */   protected Vec3 deltaMovementOnPreviousTick = Vec3.ZERO;
/*     */   
/*     */   public float elytraRotX;
/*     */   public float elytraRotY;
/*     */   public float elytraRotZ;
/*     */   public final ClientLevel clientLevel;
/*     */   
/*     */   public AbstractClientPlayer(ClientLevel $$0, GameProfile $$1) {
/*  32 */     super((Level)$$0, $$0.getSharedSpawnPos(), $$0.getSharedSpawnAngle(), $$1);
/*  33 */     this.clientLevel = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/*  38 */     PlayerInfo $$0 = getPlayerInfo();
/*  39 */     return ($$0 != null && $$0.getGameMode() == GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  44 */     PlayerInfo $$0 = getPlayerInfo();
/*  45 */     return ($$0 != null && $$0.getGameMode() == GameType.CREATIVE);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   protected PlayerInfo getPlayerInfo() {
/*  50 */     if (this.playerInfo == null) {
/*  51 */       this.playerInfo = Minecraft.getInstance().getConnection().getPlayerInfo(getUUID());
/*     */     }
/*  53 */     return this.playerInfo;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  58 */     this.deltaMovementOnPreviousTick = getDeltaMovement();
/*  59 */     super.tick();
/*     */   }
/*     */   
/*     */   public Vec3 getDeltaMovementLerped(float $$0) {
/*  63 */     return this.deltaMovementOnPreviousTick.lerp(getDeltaMovement(), $$0);
/*     */   }
/*     */   
/*     */   public PlayerSkin getSkin() {
/*  67 */     PlayerInfo $$0 = getPlayerInfo();
/*  68 */     return ($$0 == null) ? DefaultPlayerSkin.get(getUUID()) : $$0.getSkin();
/*     */   }
/*     */   
/*     */   public float getFieldOfViewModifier() {
/*  72 */     float $$0 = 1.0F;
/*     */ 
/*     */     
/*  75 */     if ((getAbilities()).flying) {
/*  76 */       $$0 *= 1.1F;
/*     */     }
/*  78 */     $$0 *= ((float)getAttributeValue(Attributes.MOVEMENT_SPEED) / getAbilities().getWalkingSpeed() + 1.0F) / 2.0F;
/*     */     
/*  80 */     if (getAbilities().getWalkingSpeed() == 0.0F || Float.isNaN($$0) || Float.isInfinite($$0)) {
/*  81 */       $$0 = 1.0F;
/*     */     }
/*     */     
/*  84 */     ItemStack $$1 = getUseItem();
/*  85 */     if (isUsingItem()) {
/*  86 */       if ($$1.is(Items.BOW)) {
/*  87 */         int $$2 = getTicksUsingItem();
/*  88 */         float $$3 = $$2 / 20.0F;
/*  89 */         if ($$3 > 1.0F) {
/*  90 */           $$3 = 1.0F;
/*     */         } else {
/*  92 */           $$3 *= $$3;
/*     */         } 
/*  94 */         $$0 *= 1.0F - $$3 * 0.15F;
/*  95 */       } else if ((Minecraft.getInstance()).options.getCameraType().isFirstPerson() && isScoping()) {
/*  96 */         return 0.1F;
/*     */       } 
/*     */     }
/*     */     
/* 100 */     return Mth.lerp(((Double)(Minecraft.getInstance()).options.fovEffectScale().get()).floatValue(), 1.0F, $$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\AbstractClientPlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */