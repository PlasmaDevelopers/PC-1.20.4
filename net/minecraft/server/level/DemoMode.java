/*     */ package net.minecraft.server.level;
/*     */ 
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.protocol.Packet;
/*     */ import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
/*     */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ 
/*     */ public class DemoMode
/*     */   extends ServerPlayerGameMode {
/*     */   public static final int DEMO_DAYS = 5;
/*     */   public static final int TOTAL_PLAY_TICKS = 120500;
/*     */   private boolean displayedIntro;
/*     */   private boolean demoHasEnded;
/*     */   private int demoEndedReminder;
/*     */   private int gameModeTicks;
/*     */   
/*     */   public DemoMode(ServerPlayer $$0) {
/*  25 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  30 */     super.tick();
/*  31 */     this.gameModeTicks++;
/*     */     
/*  33 */     long $$0 = this.level.getGameTime();
/*  34 */     long $$1 = $$0 / 24000L + 1L;
/*     */     
/*  36 */     if (!this.displayedIntro && this.gameModeTicks > 20) {
/*  37 */       this.displayedIntro = true;
/*  38 */       this.player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 0.0F));
/*     */     } 
/*     */     
/*  41 */     this.demoHasEnded = ($$0 > 120500L);
/*  42 */     if (this.demoHasEnded) {
/*  43 */       this.demoEndedReminder++;
/*     */     }
/*     */     
/*  46 */     if ($$0 % 24000L == 500L) {
/*  47 */       if ($$1 <= 6L) {
/*  48 */         if ($$1 == 6L) {
/*  49 */           this.player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 104.0F));
/*     */         } else {
/*  51 */           this.player.sendSystemMessage((Component)Component.translatable("demo.day." + $$1));
/*     */         } 
/*     */       }
/*  54 */     } else if ($$1 == 1L) {
/*  55 */       if ($$0 == 100L) {
/*  56 */         this.player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 101.0F));
/*  57 */       } else if ($$0 == 175L) {
/*  58 */         this.player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 102.0F));
/*  59 */       } else if ($$0 == 250L) {
/*  60 */         this.player.connection.send((Packet)new ClientboundGameEventPacket(ClientboundGameEventPacket.DEMO_EVENT, 103.0F));
/*     */       } 
/*  62 */     } else if ($$1 == 5L && 
/*  63 */       $$0 % 24000L == 22000L) {
/*  64 */       this.player.sendSystemMessage((Component)Component.translatable("demo.day.warning"));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void outputDemoReminder() {
/*  70 */     if (this.demoEndedReminder > 100) {
/*  71 */       this.player.sendSystemMessage((Component)Component.translatable("demo.reminder"));
/*  72 */       this.demoEndedReminder = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleBlockBreakAction(BlockPos $$0, ServerboundPlayerActionPacket.Action $$1, Direction $$2, int $$3, int $$4) {
/*  78 */     if (this.demoHasEnded) {
/*  79 */       outputDemoReminder();
/*     */       return;
/*     */     } 
/*  82 */     super.handleBlockBreakAction($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useItem(ServerPlayer $$0, Level $$1, ItemStack $$2, InteractionHand $$3) {
/*  87 */     if (this.demoHasEnded) {
/*  88 */       outputDemoReminder();
/*  89 */       return InteractionResult.PASS;
/*     */     } 
/*  91 */     return super.useItem($$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useItemOn(ServerPlayer $$0, Level $$1, ItemStack $$2, InteractionHand $$3, BlockHitResult $$4) {
/*  96 */     if (this.demoHasEnded) {
/*  97 */       outputDemoReminder();
/*  98 */       return InteractionResult.PASS;
/*     */     } 
/* 100 */     return super.useItemOn($$0, $$1, $$2, $$3, $$4);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\server\level\DemoMode.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */