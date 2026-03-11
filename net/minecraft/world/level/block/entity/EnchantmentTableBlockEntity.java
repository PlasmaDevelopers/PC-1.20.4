/*     */ package net.minecraft.world.level.block.entity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.Nameable;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ 
/*     */ public class EnchantmentTableBlockEntity
/*     */   extends BlockEntity
/*     */   implements Nameable {
/*     */   public int time;
/*     */   public float flip;
/*     */   public float oFlip;
/*     */   public float flipT;
/*     */   public float flipA;
/*     */   public float open;
/*     */   public float oOpen;
/*     */   public float rot;
/*     */   public float oRot;
/*     */   public float tRot;
/*  27 */   private static final RandomSource RANDOM = RandomSource.create();
/*     */   private Component name;
/*     */   
/*     */   public EnchantmentTableBlockEntity(BlockPos $$0, BlockState $$1) {
/*  31 */     super(BlockEntityType.ENCHANTING_TABLE, $$0, $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void saveAdditional(CompoundTag $$0) {
/*  36 */     super.saveAdditional($$0);
/*  37 */     if (hasCustomName()) {
/*  38 */       $$0.putString("CustomName", Component.Serializer.toJson(this.name));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(CompoundTag $$0) {
/*  44 */     super.load($$0);
/*  45 */     if ($$0.contains("CustomName", 8)) {
/*  46 */       this.name = (Component)Component.Serializer.fromJson($$0.getString("CustomName"));
/*     */     }
/*     */   }
/*     */   
/*     */   public static void bookAnimationTick(Level $$0, BlockPos $$1, BlockState $$2, EnchantmentTableBlockEntity $$3) {
/*  51 */     $$3.oOpen = $$3.open;
/*  52 */     $$3.oRot = $$3.rot;
/*     */     
/*  54 */     Player $$4 = $$0.getNearestPlayer($$1.getX() + 0.5D, $$1.getY() + 0.5D, $$1.getZ() + 0.5D, 3.0D, false);
/*  55 */     if ($$4 != null) {
/*  56 */       double $$5 = $$4.getX() - $$1.getX() + 0.5D;
/*  57 */       double $$6 = $$4.getZ() - $$1.getZ() + 0.5D;
/*     */       
/*  59 */       $$3.tRot = (float)Mth.atan2($$6, $$5);
/*     */       
/*  61 */       $$3.open += 0.1F;
/*     */       
/*  63 */       if ($$3.open < 0.5F || RANDOM.nextInt(40) == 0) {
/*  64 */         float $$7 = $$3.flipT;
/*     */         do {
/*  66 */           $$3.flipT += (RANDOM.nextInt(4) - RANDOM.nextInt(4));
/*  67 */         } while ($$7 == $$3.flipT);
/*     */       } 
/*     */     } else {
/*  70 */       $$3.tRot += 0.02F;
/*  71 */       $$3.open -= 0.1F;
/*     */     } 
/*     */     
/*  74 */     while ($$3.rot >= 3.1415927F) {
/*  75 */       $$3.rot -= 6.2831855F;
/*     */     }
/*  77 */     while ($$3.rot < -3.1415927F) {
/*  78 */       $$3.rot += 6.2831855F;
/*     */     }
/*  80 */     while ($$3.tRot >= 3.1415927F) {
/*  81 */       $$3.tRot -= 6.2831855F;
/*     */     }
/*  83 */     while ($$3.tRot < -3.1415927F) {
/*  84 */       $$3.tRot += 6.2831855F;
/*     */     }
/*  86 */     float $$8 = $$3.tRot - $$3.rot;
/*  87 */     while ($$8 >= 3.1415927F) {
/*  88 */       $$8 -= 6.2831855F;
/*     */     }
/*  90 */     while ($$8 < -3.1415927F) {
/*  91 */       $$8 += 6.2831855F;
/*     */     }
/*     */     
/*  94 */     $$3.rot += $$8 * 0.4F;
/*     */     
/*  96 */     $$3.open = Mth.clamp($$3.open, 0.0F, 1.0F);
/*     */     
/*  98 */     $$3.time++;
/*  99 */     $$3.oFlip = $$3.flip;
/*     */     
/* 101 */     float $$9 = ($$3.flipT - $$3.flip) * 0.4F;
/* 102 */     float $$10 = 0.2F;
/* 103 */     $$9 = Mth.clamp($$9, -0.2F, 0.2F);
/* 104 */     $$3.flipA += ($$9 - $$3.flipA) * 0.9F;
/*     */     
/* 106 */     $$3.flip += $$3.flipA;
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getName() {
/* 111 */     if (this.name != null) {
/* 112 */       return this.name;
/*     */     }
/* 114 */     return (Component)Component.translatable("container.enchant");
/*     */   }
/*     */   
/*     */   public void setCustomName(@Nullable Component $$0) {
/* 118 */     this.name = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Component getCustomName() {
/* 124 */     return this.name;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\entity\EnchantmentTableBlockEntity.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */