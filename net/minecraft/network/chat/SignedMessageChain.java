/*     */ package net.minecraft.network.chat;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.security.SignatureException;
/*     */ import java.time.Instant;
/*     */ import java.util.UUID;
/*     */ import java.util.function.BooleanSupplier;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.SignatureUpdater;
/*     */ import net.minecraft.util.SignatureValidator;
/*     */ import net.minecraft.util.Signer;
/*     */ import net.minecraft.world.entity.player.ProfilePublicKey;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SignedMessageChain {
/*  15 */   private static final Logger LOGGER = LogUtils.getLogger();
/*     */   
/*     */   @Nullable
/*     */   private SignedMessageLink nextLink;
/*  19 */   private Instant lastTimeStamp = Instant.EPOCH;
/*     */   
/*     */   public SignedMessageChain(UUID $$0, UUID $$1) {
/*  22 */     this.nextLink = SignedMessageLink.root($$0, $$1);
/*     */   }
/*     */   
/*     */   public Encoder encoder(Signer $$0) {
/*  26 */     return $$1 -> {
/*     */         SignedMessageLink $$2 = advanceLink();
/*     */         return ($$2 == null) ? null : new MessageSignature($$0.sign(()));
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decoder decoder(ProfilePublicKey $$0) {
/*  36 */     SignatureValidator $$1 = $$0.createSignatureValidator();
/*  37 */     return ($$2, $$3) -> {
/*     */         SignedMessageLink $$4 = advanceLink();
/*     */         if ($$4 == null) {
/*     */           throw new DecodeException(Component.translatable("chat.disabled.chain_broken"), false);
/*     */         }
/*     */         if ($$0.data().hasExpired()) {
/*     */           throw new DecodeException(Component.translatable("chat.disabled.expiredProfileKey"), false);
/*     */         }
/*     */         if ($$3.timeStamp().isBefore(this.lastTimeStamp)) {
/*     */           throw new DecodeException(Component.translatable("multiplayer.disconnect.out_of_order_chat"), true);
/*     */         }
/*     */         this.lastTimeStamp = $$3.timeStamp();
/*     */         PlayerChatMessage $$5 = new PlayerChatMessage($$4, $$2, $$3, null, FilterMask.PASS_THROUGH);
/*     */         if (!$$5.verify($$1)) {
/*     */           throw new DecodeException(Component.translatable("multiplayer.disconnect.unsigned_chat"), true);
/*     */         }
/*     */         if ($$5.hasExpiredServer(Instant.now())) {
/*     */           LOGGER.warn("Received expired chat: '{}'. Is the client/server system time unsynchronized?", $$3.content());
/*     */         }
/*     */         return $$5;
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private SignedMessageLink advanceLink() {
/*  68 */     SignedMessageLink $$0 = this.nextLink;
/*  69 */     if ($$0 != null) {
/*  70 */       this.nextLink = $$0.advance();
/*     */     }
/*  72 */     return $$0;
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Encoder {
/*     */     public static final Encoder UNSIGNED = $$0 -> null;
/*     */     
/*     */     @Nullable
/*     */     MessageSignature pack(SignedMessageBody param1SignedMessageBody);
/*     */   }
/*     */   
/*     */   @FunctionalInterface
/*     */   public static interface Decoder {
/*     */     static Decoder unsigned(UUID $$0, BooleanSupplier $$1) {
/*  86 */       return ($$2, $$3) -> {
/*     */           if ($$0.getAsBoolean())
/*     */             throw new SignedMessageChain.DecodeException(Component.translatable("chat.disabled.missingProfileKey"), false); 
/*     */           return PlayerChatMessage.unsigned($$1, $$3.content());
/*     */         };
/*     */     }
/*     */     
/*     */     PlayerChatMessage unpack(@Nullable MessageSignature param1MessageSignature, SignedMessageBody param1SignedMessageBody) throws SignedMessageChain.DecodeException;
/*     */   }
/*     */   
/*     */   public static class DecodeException
/*     */     extends ThrowingComponent {
/*     */     private final boolean shouldDisconnect;
/*     */     
/*     */     public DecodeException(Component $$0, boolean $$1) {
/* 101 */       super($$0);
/* 102 */       this.shouldDisconnect = $$1;
/*     */     }
/*     */     
/*     */     public boolean shouldDisconnect() {
/* 106 */       return this.shouldDisconnect;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\SignedMessageChain.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */