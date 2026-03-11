/*     */ package net.minecraft.world.item;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.BlockParticleOption;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.EquipmentSlot;
/*     */ import net.minecraft.world.entity.HumanoidArm;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.ProjectileUtil;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.BrushableBlock;
/*     */ import net.minecraft.world.level.block.RenderShape;
/*     */ import net.minecraft.world.level.block.entity.BlockEntity;
/*     */ import net.minecraft.world.level.block.entity.BrushableBlockEntity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class BrushItem extends Item {
/*     */   public static final int ANIMATION_DURATION = 10;
/*     */   private static final int USE_DURATION = 200;
/*     */   
/*     */   public BrushItem(Item.Properties $$0) {
/*  35 */     super($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  40 */     Player $$1 = $$0.getPlayer();
/*  41 */     if ($$1 != null && calculateHitResult($$1).getType() == HitResult.Type.BLOCK) {
/*  42 */       $$1.startUsingItem($$0.getHand());
/*     */     }
/*     */     
/*  45 */     return InteractionResult.CONSUME;
/*     */   }
/*     */ 
/*     */   
/*     */   public UseAnim getUseAnimation(ItemStack $$0) {
/*  50 */     return UseAnim.BRUSH;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getUseDuration(ItemStack $$0) {
/*  55 */     return 200;
/*     */   }
/*     */   
/*     */   public void onUseTick(Level $$0, LivingEntity $$1, ItemStack $$2, int $$3) {
/*     */     Player $$4;
/*  60 */     if ($$3 >= 0 && $$1 instanceof Player) { $$4 = (Player)$$1; }
/*  61 */     else { $$1.releaseUsingItem();
/*     */       
/*     */       return; }
/*     */     
/*  65 */     HitResult $$6 = calculateHitResult($$4);
/*  66 */     if ($$6 instanceof BlockHitResult) { BlockHitResult $$7 = (BlockHitResult)$$6; if ($$6.getType() == HitResult.Type.BLOCK) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         int $$9 = getUseDuration($$2) - $$3 + 1;
/*  72 */         boolean $$10 = ($$9 % 10 == 5);
/*     */         
/*  74 */         if ($$10) {
/*  75 */           SoundEvent $$16; BlockPos $$11 = $$7.getBlockPos();
/*  76 */           BlockState $$12 = $$0.getBlockState($$11);
/*     */ 
/*     */ 
/*     */           
/*  80 */           HumanoidArm $$13 = ($$1.getUsedItemHand() == InteractionHand.MAIN_HAND) ? $$4.getMainArm() : $$4.getMainArm().getOpposite();
/*     */           
/*  82 */           if ($$12.shouldSpawnTerrainParticles() && $$12.getRenderShape() != RenderShape.INVISIBLE) {
/*  83 */             spawnDustParticles($$0, $$7, $$12, $$1.getViewVector(0.0F), $$13);
/*     */           }
/*     */ 
/*     */           
/*  87 */           Block block = $$12.getBlock(); if (block instanceof BrushableBlock) { BrushableBlock $$14 = (BrushableBlock)block;
/*  88 */             SoundEvent $$15 = $$14.getBrushSound(); }
/*     */           else
/*  90 */           { $$16 = SoundEvents.BRUSH_GENERIC; }
/*     */ 
/*     */           
/*  93 */           $$0.playSound($$4, $$11, $$16, SoundSource.BLOCKS);
/*     */           
/*  95 */           if (!$$0.isClientSide()) {
/*  96 */             BlockEntity blockEntity = $$0.getBlockEntity($$11); if (blockEntity instanceof BrushableBlockEntity) { BrushableBlockEntity $$17 = (BrushableBlockEntity)blockEntity;
/*  97 */               boolean $$18 = $$17.brush($$0.getGameTime(), $$4, $$7.getDirection());
/*     */               
/*  99 */               if ($$18) {
/*     */ 
/*     */                 
/* 102 */                 EquipmentSlot $$19 = $$2.equals($$4.getItemBySlot(EquipmentSlot.OFFHAND)) ? EquipmentSlot.OFFHAND : EquipmentSlot.MAINHAND;
/* 103 */                 $$2.hurtAndBreak(1, $$1, $$1 -> $$1.broadcastBreakEvent($$0));
/*     */               }  }
/*     */           
/*     */           } 
/*     */         }  return;
/*     */       }  }
/*     */     
/* 110 */     $$1.releaseUsingItem(); } private HitResult calculateHitResult(Player $$0) { return ProjectileUtil.getHitResultOnViewVector((Entity)$$0, $$0 -> (!$$0.isSpectator() && $$0.isPickable()), Player.getPickRange($$0.isCreative())); }
/*     */ 
/*     */   
/*     */   private void spawnDustParticles(Level $$0, BlockHitResult $$1, BlockState $$2, Vec3 $$3, HumanoidArm $$4) {
/* 114 */     double $$5 = 3.0D;
/* 115 */     int $$6 = ($$4 == HumanoidArm.RIGHT) ? 1 : -1;
/* 116 */     int $$7 = $$0.getRandom().nextInt(7, 12);
/* 117 */     BlockParticleOption $$8 = new BlockParticleOption(ParticleTypes.BLOCK, $$2);
/*     */     
/* 119 */     Direction $$9 = $$1.getDirection();
/* 120 */     DustParticlesDelta $$10 = DustParticlesDelta.fromDirection($$3, $$9);
/* 121 */     Vec3 $$11 = $$1.getLocation();
/*     */     
/* 123 */     for (int $$12 = 0; $$12 < $$7; $$12++)
/* 124 */       $$0.addParticle((ParticleOptions)$$8, $$11.x - (
/*     */           
/* 126 */           ($$9 == Direction.WEST) ? 1.0E-6F : 0.0F), $$11.y, $$11.z - (
/*     */           
/* 128 */           ($$9 == Direction.NORTH) ? 1.0E-6F : 0.0F), $$10
/* 129 */           .xd() * $$6 * 3.0D * $$0.getRandom().nextDouble(), 0.0D, $$10
/*     */           
/* 131 */           .zd() * $$6 * 3.0D * $$0.getRandom().nextDouble()); 
/*     */   }
/*     */   private static final class DustParticlesDelta extends Record { private final double xd; private final double yd; private final double zd; private static final double ALONG_SIDE_DELTA = 1.0D;
/*     */     private static final double OUT_FROM_SIDE_DELTA = 0.1D;
/*     */     
/* 136 */     private DustParticlesDelta(double $$0, double $$1, double $$2) { this.xd = $$0; this.yd = $$1; this.zd = $$2; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #136	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 136 */       //   0	7	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta; } public double xd() { return this.xd; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #136	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #136	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/item/BrushItem$DustParticlesDelta;
/* 136 */       //   0	8	1	$$0	Ljava/lang/Object; } public double yd() { return this.yd; } public double zd() { return this.zd; }
/*     */ 
/*     */     
/*     */     public static DustParticlesDelta fromDirection(Vec3 $$0, Direction $$1) {
/*     */       // Byte code:
/*     */       //   0: dconst_0
/*     */       //   1: dstore_2
/*     */       //   2: getstatic net/minecraft/world/item/BrushItem$1.$SwitchMap$net$minecraft$core$Direction : [I
/*     */       //   5: aload_1
/*     */       //   6: invokevirtual ordinal : ()I
/*     */       //   9: iaload
/*     */       //   10: tableswitch default -> 48, 1 -> 56, 2 -> 56, 3 -> 76, 4 -> 91, 5 -> 108, 6 -> 125
/*     */       //   48: new java/lang/IncompatibleClassChangeError
/*     */       //   51: dup
/*     */       //   52: invokespecial <init> : ()V
/*     */       //   55: athrow
/*     */       //   56: new net/minecraft/world/item/BrushItem$DustParticlesDelta
/*     */       //   59: dup
/*     */       //   60: aload_0
/*     */       //   61: invokevirtual z : ()D
/*     */       //   64: dconst_0
/*     */       //   65: aload_0
/*     */       //   66: invokevirtual x : ()D
/*     */       //   69: dneg
/*     */       //   70: invokespecial <init> : (DDD)V
/*     */       //   73: goto -> 137
/*     */       //   76: new net/minecraft/world/item/BrushItem$DustParticlesDelta
/*     */       //   79: dup
/*     */       //   80: dconst_1
/*     */       //   81: dconst_0
/*     */       //   82: ldc2_w -0.1
/*     */       //   85: invokespecial <init> : (DDD)V
/*     */       //   88: goto -> 137
/*     */       //   91: new net/minecraft/world/item/BrushItem$DustParticlesDelta
/*     */       //   94: dup
/*     */       //   95: ldc2_w -1.0
/*     */       //   98: dconst_0
/*     */       //   99: ldc2_w 0.1
/*     */       //   102: invokespecial <init> : (DDD)V
/*     */       //   105: goto -> 137
/*     */       //   108: new net/minecraft/world/item/BrushItem$DustParticlesDelta
/*     */       //   111: dup
/*     */       //   112: ldc2_w -0.1
/*     */       //   115: dconst_0
/*     */       //   116: ldc2_w -1.0
/*     */       //   119: invokespecial <init> : (DDD)V
/*     */       //   122: goto -> 137
/*     */       //   125: new net/minecraft/world/item/BrushItem$DustParticlesDelta
/*     */       //   128: dup
/*     */       //   129: ldc2_w 0.1
/*     */       //   132: dconst_0
/*     */       //   133: dconst_1
/*     */       //   134: invokespecial <init> : (DDD)V
/*     */       //   137: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #142	-> 0
/*     */       //   #143	-> 2
/*     */       //   #144	-> 56
/*     */       //   #145	-> 76
/*     */       //   #146	-> 91
/*     */       //   #147	-> 108
/*     */       //   #148	-> 125
/*     */       //   #143	-> 137
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	138	0	$$0	Lnet/minecraft/world/phys/Vec3;
/*     */       //   0	138	1	$$1	Lnet/minecraft/core/Direction;
/*     */       //   2	136	2	$$2	D
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BrushItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */