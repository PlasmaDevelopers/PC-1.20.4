/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.server.level.ServerPlayer;
/*     */ import net.minecraft.sounds.SoundEvent;
/*     */ import net.minecraft.sounds.SoundEvents;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.LevelAccessor;
/*     */ import net.minecraft.world.level.block.Block;
/*     */ import net.minecraft.world.level.block.BucketPickup;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.gameevent.GameEvent;
/*     */ import net.minecraft.world.level.material.Fluid;
/*     */ import net.minecraft.world.level.material.Fluids;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ 
/*     */ public class BucketItem
/*     */   extends Item
/*     */   implements DispensibleContainerItem
/*     */ {
/*     */   private final Fluid content;
/*     */   
/*     */   public BucketItem(Fluid $$0, Item.Properties $$1) {
/*  36 */     super($$1);
/*  37 */     this.content = $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/*  42 */     ItemStack $$3 = $$1.getItemInHand($$2);
/*  43 */     BlockHitResult $$4 = getPlayerPOVHitResult($$0, $$1, (this.content == Fluids.EMPTY) ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);
/*  44 */     if ($$4.getType() == HitResult.Type.MISS) {
/*  45 */       return InteractionResultHolder.pass($$3);
/*     */     }
/*     */     
/*  48 */     if ($$4.getType() == HitResult.Type.BLOCK) {
/*  49 */       BlockPos $$5 = $$4.getBlockPos();
/*  50 */       Direction $$6 = $$4.getDirection();
/*  51 */       BlockPos $$7 = $$5.relative($$6);
/*     */       
/*  53 */       if (!$$0.mayInteract($$1, $$5) || !$$1.mayUseItemAt($$7, $$6, $$3)) {
/*  54 */         return InteractionResultHolder.fail($$3);
/*     */       }
/*     */       
/*  57 */       if (this.content == Fluids.EMPTY) {
/*  58 */         BlockState $$8 = $$0.getBlockState($$5);
/*     */         
/*  60 */         Block block = $$8.getBlock(); if (block instanceof BucketPickup) { BucketPickup $$9 = (BucketPickup)block;
/*  61 */           ItemStack $$10 = $$9.pickupBlock($$1, (LevelAccessor)$$0, $$5, $$8);
/*  62 */           if (!$$10.isEmpty()) {
/*  63 */             $$1.awardStat(Stats.ITEM_USED.get(this));
/*  64 */             $$9.getPickupSound().ifPresent($$1 -> $$0.playSound($$1, 1.0F, 1.0F));
/*  65 */             $$0.gameEvent((Entity)$$1, GameEvent.FLUID_PICKUP, $$5);
/*  66 */             ItemStack $$11 = ItemUtils.createFilledResult($$3, $$1, $$10);
/*  67 */             if (!$$0.isClientSide) {
/*  68 */               CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)$$1, $$10);
/*     */             }
/*  70 */             return InteractionResultHolder.sidedSuccess($$11, $$0.isClientSide());
/*     */           }  }
/*     */ 
/*     */         
/*  74 */         return InteractionResultHolder.fail($$3);
/*     */       } 
/*  76 */       BlockState $$12 = $$0.getBlockState($$5);
/*  77 */       BlockPos $$13 = ($$12.getBlock() instanceof net.minecraft.world.level.block.LiquidBlockContainer && this.content == Fluids.WATER) ? $$5 : $$7;
/*     */       
/*  79 */       if (emptyContents($$1, $$0, $$13, $$4)) {
/*  80 */         checkExtraContent($$1, $$0, $$3, $$13);
/*  81 */         if ($$1 instanceof ServerPlayer) {
/*  82 */           CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)$$1, $$13, $$3);
/*     */         }
/*  84 */         $$1.awardStat(Stats.ITEM_USED.get(this));
/*  85 */         return InteractionResultHolder.sidedSuccess(getEmptySuccessItem($$3, $$1), $$0.isClientSide());
/*     */       } 
/*  87 */       return InteractionResultHolder.fail($$3);
/*     */     } 
/*     */ 
/*     */     
/*  91 */     return InteractionResultHolder.pass($$3);
/*     */   }
/*     */   
/*     */   public static ItemStack getEmptySuccessItem(ItemStack $$0, Player $$1) {
/*  95 */     if (!($$1.getAbilities()).instabuild) {
/*  96 */       return new ItemStack(Items.BUCKET);
/*     */     }
/*  98 */     return $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkExtraContent(@Nullable Player $$0, Level $$1, ItemStack $$2, BlockPos $$3) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean emptyContents(@Nullable Player $$0, Level $$1, BlockPos $$2, @Nullable BlockHitResult $$3) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   4: astore #6
/*     */     //   6: aload #6
/*     */     //   8: instanceof net/minecraft/world/level/material/FlowingFluid
/*     */     //   11: ifeq -> 24
/*     */     //   14: aload #6
/*     */     //   16: checkcast net/minecraft/world/level/material/FlowingFluid
/*     */     //   19: astore #5
/*     */     //   21: goto -> 26
/*     */     //   24: iconst_0
/*     */     //   25: ireturn
/*     */     //   26: aload_2
/*     */     //   27: aload_3
/*     */     //   28: invokevirtual getBlockState : (Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   31: astore #6
/*     */     //   33: aload #6
/*     */     //   35: invokevirtual getBlock : ()Lnet/minecraft/world/level/block/Block;
/*     */     //   38: astore #7
/*     */     //   40: aload #6
/*     */     //   42: aload_0
/*     */     //   43: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   46: invokevirtual canBeReplaced : (Lnet/minecraft/world/level/material/Fluid;)Z
/*     */     //   49: istore #8
/*     */     //   51: aload #6
/*     */     //   53: invokevirtual isAir : ()Z
/*     */     //   56: ifne -> 98
/*     */     //   59: iload #8
/*     */     //   61: ifne -> 98
/*     */     //   64: aload #7
/*     */     //   66: instanceof net/minecraft/world/level/block/LiquidBlockContainer
/*     */     //   69: ifeq -> 102
/*     */     //   72: aload #7
/*     */     //   74: checkcast net/minecraft/world/level/block/LiquidBlockContainer
/*     */     //   77: astore #10
/*     */     //   79: aload #10
/*     */     //   81: aload_1
/*     */     //   82: aload_2
/*     */     //   83: aload_3
/*     */     //   84: aload #6
/*     */     //   86: aload_0
/*     */     //   87: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   90: invokeinterface canPlaceLiquid : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/Fluid;)Z
/*     */     //   95: ifeq -> 102
/*     */     //   98: iconst_1
/*     */     //   99: goto -> 103
/*     */     //   102: iconst_0
/*     */     //   103: istore #9
/*     */     //   105: iload #9
/*     */     //   107: ifne -> 144
/*     */     //   110: aload #4
/*     */     //   112: ifnull -> 142
/*     */     //   115: aload_0
/*     */     //   116: aload_1
/*     */     //   117: aload_2
/*     */     //   118: aload #4
/*     */     //   120: invokevirtual getBlockPos : ()Lnet/minecraft/core/BlockPos;
/*     */     //   123: aload #4
/*     */     //   125: invokevirtual getDirection : ()Lnet/minecraft/core/Direction;
/*     */     //   128: invokevirtual relative : (Lnet/minecraft/core/Direction;)Lnet/minecraft/core/BlockPos;
/*     */     //   131: aconst_null
/*     */     //   132: invokevirtual emptyContents : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/BlockHitResult;)Z
/*     */     //   135: ifeq -> 142
/*     */     //   138: iconst_1
/*     */     //   139: goto -> 143
/*     */     //   142: iconst_0
/*     */     //   143: ireturn
/*     */     //   144: aload_2
/*     */     //   145: invokevirtual dimensionType : ()Lnet/minecraft/world/level/dimension/DimensionType;
/*     */     //   148: invokevirtual ultraWarm : ()Z
/*     */     //   151: ifeq -> 276
/*     */     //   154: aload_0
/*     */     //   155: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   158: getstatic net/minecraft/tags/FluidTags.WATER : Lnet/minecraft/tags/TagKey;
/*     */     //   161: invokevirtual is : (Lnet/minecraft/tags/TagKey;)Z
/*     */     //   164: ifeq -> 276
/*     */     //   167: aload_3
/*     */     //   168: invokevirtual getX : ()I
/*     */     //   171: istore #10
/*     */     //   173: aload_3
/*     */     //   174: invokevirtual getY : ()I
/*     */     //   177: istore #11
/*     */     //   179: aload_3
/*     */     //   180: invokevirtual getZ : ()I
/*     */     //   183: istore #12
/*     */     //   185: aload_2
/*     */     //   186: aload_1
/*     */     //   187: aload_3
/*     */     //   188: getstatic net/minecraft/sounds/SoundEvents.FIRE_EXTINGUISH : Lnet/minecraft/sounds/SoundEvent;
/*     */     //   191: getstatic net/minecraft/sounds/SoundSource.BLOCKS : Lnet/minecraft/sounds/SoundSource;
/*     */     //   194: ldc_w 0.5
/*     */     //   197: ldc_w 2.6
/*     */     //   200: aload_2
/*     */     //   201: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   204: invokeinterface nextFloat : ()F
/*     */     //   209: aload_2
/*     */     //   210: getfield random : Lnet/minecraft/util/RandomSource;
/*     */     //   213: invokeinterface nextFloat : ()F
/*     */     //   218: fsub
/*     */     //   219: ldc_w 0.8
/*     */     //   222: fmul
/*     */     //   223: fadd
/*     */     //   224: invokevirtual playSound : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V
/*     */     //   227: iconst_0
/*     */     //   228: istore #13
/*     */     //   230: iload #13
/*     */     //   232: bipush #8
/*     */     //   234: if_icmpge -> 274
/*     */     //   237: aload_2
/*     */     //   238: getstatic net/minecraft/core/particles/ParticleTypes.LARGE_SMOKE : Lnet/minecraft/core/particles/SimpleParticleType;
/*     */     //   241: iload #10
/*     */     //   243: i2d
/*     */     //   244: invokestatic random : ()D
/*     */     //   247: dadd
/*     */     //   248: iload #11
/*     */     //   250: i2d
/*     */     //   251: invokestatic random : ()D
/*     */     //   254: dadd
/*     */     //   255: iload #12
/*     */     //   257: i2d
/*     */     //   258: invokestatic random : ()D
/*     */     //   261: dadd
/*     */     //   262: dconst_0
/*     */     //   263: dconst_0
/*     */     //   264: dconst_0
/*     */     //   265: invokevirtual addParticle : (Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V
/*     */     //   268: iinc #13, 1
/*     */     //   271: goto -> 230
/*     */     //   274: iconst_1
/*     */     //   275: ireturn
/*     */     //   276: aload #7
/*     */     //   278: instanceof net/minecraft/world/level/block/LiquidBlockContainer
/*     */     //   281: ifeq -> 328
/*     */     //   284: aload #7
/*     */     //   286: checkcast net/minecraft/world/level/block/LiquidBlockContainer
/*     */     //   289: astore #10
/*     */     //   291: aload_0
/*     */     //   292: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   295: getstatic net/minecraft/world/level/material/Fluids.WATER : Lnet/minecraft/world/level/material/FlowingFluid;
/*     */     //   298: if_acmpne -> 328
/*     */     //   301: aload #10
/*     */     //   303: aload_2
/*     */     //   304: aload_3
/*     */     //   305: aload #6
/*     */     //   307: aload #5
/*     */     //   309: iconst_0
/*     */     //   310: invokevirtual getSource : (Z)Lnet/minecraft/world/level/material/FluidState;
/*     */     //   313: invokeinterface placeLiquid : (Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)Z
/*     */     //   318: pop
/*     */     //   319: aload_0
/*     */     //   320: aload_1
/*     */     //   321: aload_2
/*     */     //   322: aload_3
/*     */     //   323: invokevirtual playEmptySound : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V
/*     */     //   326: iconst_1
/*     */     //   327: ireturn
/*     */     //   328: aload_2
/*     */     //   329: getfield isClientSide : Z
/*     */     //   332: ifne -> 355
/*     */     //   335: iload #8
/*     */     //   337: ifeq -> 355
/*     */     //   340: aload #6
/*     */     //   342: invokevirtual liquid : ()Z
/*     */     //   345: ifne -> 355
/*     */     //   348: aload_2
/*     */     //   349: aload_3
/*     */     //   350: iconst_1
/*     */     //   351: invokevirtual destroyBlock : (Lnet/minecraft/core/BlockPos;Z)Z
/*     */     //   354: pop
/*     */     //   355: aload_2
/*     */     //   356: aload_3
/*     */     //   357: aload_0
/*     */     //   358: getfield content : Lnet/minecraft/world/level/material/Fluid;
/*     */     //   361: invokevirtual defaultFluidState : ()Lnet/minecraft/world/level/material/FluidState;
/*     */     //   364: invokevirtual createLegacyBlock : ()Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   367: bipush #11
/*     */     //   369: invokevirtual setBlock : (Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z
/*     */     //   372: ifne -> 386
/*     */     //   375: aload #6
/*     */     //   377: invokevirtual getFluidState : ()Lnet/minecraft/world/level/material/FluidState;
/*     */     //   380: invokevirtual isSource : ()Z
/*     */     //   383: ifeq -> 395
/*     */     //   386: aload_0
/*     */     //   387: aload_1
/*     */     //   388: aload_2
/*     */     //   389: aload_3
/*     */     //   390: invokevirtual playEmptySound : (Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/core/BlockPos;)V
/*     */     //   393: iconst_1
/*     */     //   394: ireturn
/*     */     //   395: iconst_0
/*     */     //   396: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #107	-> 0
/*     */     //   #108	-> 24
/*     */     //   #111	-> 26
/*     */     //   #112	-> 33
/*     */     //   #113	-> 40
/*     */     //   #116	-> 51
/*     */     //   #115	-> 53
/*     */     //   #116	-> 72
/*     */     //   #118	-> 105
/*     */     //   #121	-> 110
/*     */     //   #124	-> 144
/*     */     //   #125	-> 167
/*     */     //   #126	-> 173
/*     */     //   #127	-> 179
/*     */     //   #129	-> 185
/*     */     //   #131	-> 227
/*     */     //   #132	-> 237
/*     */     //   #131	-> 268
/*     */     //   #134	-> 274
/*     */     //   #137	-> 276
/*     */     //   #138	-> 301
/*     */     //   #139	-> 319
/*     */     //   #140	-> 326
/*     */     //   #144	-> 328
/*     */     //   #145	-> 348
/*     */     //   #149	-> 355
/*     */     //   #150	-> 386
/*     */     //   #151	-> 393
/*     */     //   #154	-> 395
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	397	0	this	Lnet/minecraft/world/item/BucketItem;
/*     */     //   0	397	1	$$0	Lnet/minecraft/world/entity/player/Player;
/*     */     //   0	397	2	$$1	Lnet/minecraft/world/level/Level;
/*     */     //   0	397	3	$$2	Lnet/minecraft/core/BlockPos;
/*     */     //   0	397	4	$$3	Lnet/minecraft/world/phys/BlockHitResult;
/*     */     //   21	3	5	$$4	Lnet/minecraft/world/level/material/FlowingFluid;
/*     */     //   26	371	5	$$5	Lnet/minecraft/world/level/material/FlowingFluid;
/*     */     //   33	364	6	$$6	Lnet/minecraft/world/level/block/state/BlockState;
/*     */     //   40	357	7	$$7	Lnet/minecraft/world/level/block/Block;
/*     */     //   51	346	8	$$8	Z
/*     */     //   79	19	10	$$9	Lnet/minecraft/world/level/block/LiquidBlockContainer;
/*     */     //   105	292	9	$$10	Z
/*     */     //   173	103	10	$$11	I
/*     */     //   179	97	11	$$12	I
/*     */     //   185	91	12	$$13	I
/*     */     //   230	44	13	$$14	I
/*     */     //   291	37	10	$$15	Lnet/minecraft/world/level/block/LiquidBlockContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playEmptySound(@Nullable Player $$0, LevelAccessor $$1, BlockPos $$2) {
/* 158 */     SoundEvent $$3 = this.content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
/* 159 */     $$1.playSound($$0, $$2, $$3, SoundSource.BLOCKS, 1.0F, 1.0F);
/* 160 */     $$1.gameEvent((Entity)$$0, GameEvent.FLUID_PLACE, $$2);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\BucketItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */