/*     */ package net.minecraft.world.item;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.ChatFormatting;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.ListTag;
/*     */ import net.minecraft.network.chat.CommonComponents;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.stats.Stats;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.InteractionResultHolder;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.entity.projectile.FireworkRocketEntity;
/*     */ import net.minecraft.world.item.context.UseOnContext;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class FireworkRocketItem extends Item {
/*  27 */   public static final byte[] CRAFTABLE_DURATIONS = new byte[] { 1, 2, 3 };
/*     */   
/*     */   public static final String TAG_FIREWORKS = "Fireworks";
/*     */   
/*     */   public static final String TAG_EXPLOSION = "Explosion";
/*     */   public static final String TAG_EXPLOSIONS = "Explosions";
/*     */   public static final String TAG_FLIGHT = "Flight";
/*     */   public static final String TAG_EXPLOSION_TYPE = "Type";
/*     */   public static final String TAG_EXPLOSION_TRAIL = "Trail";
/*     */   public static final String TAG_EXPLOSION_FLICKER = "Flicker";
/*     */   public static final String TAG_EXPLOSION_COLORS = "Colors";
/*     */   public static final String TAG_EXPLOSION_FADECOLORS = "FadeColors";
/*     */   public static final double ROCKET_PLACEMENT_OFFSET = 0.15D;
/*     */   
/*     */   public FireworkRocketItem(Item.Properties $$0) {
/*  42 */     super($$0);
/*     */   }
/*     */   
/*     */   public enum Shape {
/*  46 */     SMALL_BALL(0, "small_ball"),
/*  47 */     LARGE_BALL(1, "large_ball"),
/*  48 */     STAR(2, "star"),
/*  49 */     CREEPER(3, "creeper"),
/*  50 */     BURST(4, "burst");
/*     */ 
/*     */     
/*  53 */     private static final IntFunction<Shape> BY_ID = ByIdMap.continuous(Shape::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */     
/*     */     private final int id;
/*     */ 
/*     */     
/*     */     Shape(int $$0, String $$1) {
/*  59 */       this.id = $$0;
/*  60 */       this.name = $$1;
/*     */     } private final String name; static {
/*     */     
/*     */     } public int getId() {
/*  64 */       return this.id;
/*     */     }
/*     */     
/*     */     public String getName() {
/*  68 */       return this.name;
/*     */     }
/*     */     
/*     */     public static Shape byId(int $$0) {
/*  72 */       return BY_ID.apply($$0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult useOn(UseOnContext $$0) {
/*  78 */     Level $$1 = $$0.getLevel();
/*  79 */     if (!$$1.isClientSide) {
/*  80 */       ItemStack $$2 = $$0.getItemInHand();
/*     */       
/*  82 */       Vec3 $$3 = $$0.getClickLocation();
/*  83 */       Direction $$4 = $$0.getClickedFace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  89 */       FireworkRocketEntity $$5 = new FireworkRocketEntity($$1, (Entity)$$0.getPlayer(), $$3.x + $$4.getStepX() * 0.15D, $$3.y + $$4.getStepY() * 0.15D, $$3.z + $$4.getStepZ() * 0.15D, $$2);
/*     */ 
/*     */       
/*  92 */       $$1.addFreshEntity((Entity)$$5);
/*     */       
/*  94 */       $$2.shrink(1);
/*     */     } 
/*  96 */     return InteractionResult.sidedSuccess($$1.isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResultHolder<ItemStack> use(Level $$0, Player $$1, InteractionHand $$2) {
/* 101 */     if ($$1.isFallFlying()) {
/* 102 */       ItemStack $$3 = $$1.getItemInHand($$2);
/* 103 */       if (!$$0.isClientSide) {
/* 104 */         FireworkRocketEntity $$4 = new FireworkRocketEntity($$0, $$3, (LivingEntity)$$1);
/* 105 */         $$0.addFreshEntity((Entity)$$4);
/* 106 */         if (!($$1.getAbilities()).instabuild) {
/* 107 */           $$3.shrink(1);
/*     */         }
/* 109 */         $$1.awardStat(Stats.ITEM_USED.get(this));
/*     */       } 
/*     */       
/* 112 */       return InteractionResultHolder.sidedSuccess($$1.getItemInHand($$2), $$0.isClientSide());
/*     */     } 
/* 114 */     return InteractionResultHolder.pass($$1.getItemInHand($$2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendHoverText(ItemStack $$0, @Nullable Level $$1, List<Component> $$2, TooltipFlag $$3) {
/* 120 */     CompoundTag $$4 = $$0.getTagElement("Fireworks");
/* 121 */     if ($$4 == null) {
/*     */       return;
/*     */     }
/* 124 */     if ($$4.contains("Flight", 99)) {
/* 125 */       $$2.add(Component.translatable("item.minecraft.firework_rocket.flight").append(CommonComponents.SPACE).append(String.valueOf($$4.getByte("Flight"))).withStyle(ChatFormatting.GRAY));
/*     */     }
/*     */     
/* 128 */     ListTag $$5 = $$4.getList("Explosions", 10);
/* 129 */     if (!$$5.isEmpty()) {
/* 130 */       for (int $$6 = 0; $$6 < $$5.size(); $$6++) {
/* 131 */         CompoundTag $$7 = $$5.getCompound($$6);
/*     */         
/* 133 */         List<Component> $$8 = Lists.newArrayList();
/* 134 */         FireworkStarItem.appendHoverText($$7, $$8);
/*     */         
/* 136 */         if (!$$8.isEmpty()) {
/* 137 */           for (int $$9 = 1; $$9 < $$8.size(); $$9++) {
/* 138 */             $$8.set($$9, Component.literal("  ").append($$8.get($$9)).withStyle(ChatFormatting.GRAY));
/*     */           }
/*     */           
/* 141 */           $$2.addAll($$8);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void setDuration(ItemStack $$0, byte $$1) {
/* 148 */     $$0.getOrCreateTagElement("Fireworks").putByte("Flight", $$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getDefaultInstance() {
/* 153 */     ItemStack $$0 = new ItemStack(this);
/* 154 */     setDuration($$0, (byte)1);
/* 155 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\item\FireworkRocketItem.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */