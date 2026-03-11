/*     */ package net.minecraft.world.entity.vehicle;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.particles.ParticleOptions;
/*     */ import net.minecraft.core.particles.ParticleTypes;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.network.syncher.EntityDataAccessor;
/*     */ import net.minecraft.network.syncher.EntityDataSerializers;
/*     */ import net.minecraft.network.syncher.SynchedEntityData;
/*     */ import net.minecraft.world.InteractionHand;
/*     */ import net.minecraft.world.InteractionResult;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.entity.player.Player;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ import net.minecraft.world.item.Items;
/*     */ import net.minecraft.world.item.crafting.Ingredient;
/*     */ import net.minecraft.world.level.ItemLike;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.FurnaceBlock;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.block.state.properties.Property;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public class MinecartFurnace extends AbstractMinecart {
/*  27 */   private static final EntityDataAccessor<Boolean> DATA_ID_FUEL = SynchedEntityData.defineId(MinecartFurnace.class, EntityDataSerializers.BOOLEAN);
/*     */   
/*     */   private int fuel;
/*     */   public double xPush;
/*     */   public double zPush;
/*  32 */   private static final Ingredient INGREDIENT = Ingredient.of(new ItemLike[] { (ItemLike)Items.COAL, (ItemLike)Items.CHARCOAL });
/*     */   
/*     */   public MinecartFurnace(EntityType<? extends MinecartFurnace> $$0, Level $$1) {
/*  35 */     super($$0, $$1);
/*     */   }
/*     */   
/*     */   public MinecartFurnace(Level $$0, double $$1, double $$2, double $$3) {
/*  39 */     super(EntityType.FURNACE_MINECART, $$0, $$1, $$2, $$3);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractMinecart.Type getMinecartType() {
/*  44 */     return AbstractMinecart.Type.FURNACE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void defineSynchedData() {
/*  49 */     super.defineSynchedData();
/*  50 */     this.entityData.define(DATA_ID_FUEL, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/*  55 */     super.tick();
/*     */     
/*  57 */     if (!level().isClientSide()) {
/*  58 */       if (this.fuel > 0) {
/*  59 */         this.fuel--;
/*     */       }
/*  61 */       if (this.fuel <= 0) {
/*  62 */         this.xPush = 0.0D;
/*  63 */         this.zPush = 0.0D;
/*     */       } 
/*  65 */       setHasFuel((this.fuel > 0));
/*     */     } 
/*     */     
/*  68 */     if (hasFuel() && this.random.nextInt(4) == 0) {
/*  69 */       level().addParticle((ParticleOptions)ParticleTypes.LARGE_SMOKE, getX(), getY() + 0.8D, getZ(), 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected double getMaxSpeed() {
/*  75 */     return (isInWater() ? 3.0D : 4.0D) / 20.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/*  80 */     return Items.FURNACE_MINECART;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void moveAlongTrack(BlockPos $$0, BlockState $$1) {
/*  86 */     double $$2 = 1.0E-4D;
/*  87 */     double $$3 = 0.001D;
/*     */     
/*  89 */     super.moveAlongTrack($$0, $$1);
/*     */     
/*  91 */     Vec3 $$4 = getDeltaMovement();
/*     */     
/*  93 */     double $$5 = $$4.horizontalDistanceSqr();
/*  94 */     double $$6 = this.xPush * this.xPush + this.zPush * this.zPush;
/*  95 */     if ($$6 > 1.0E-4D && $$5 > 0.001D) {
/*  96 */       double $$7 = Math.sqrt($$5);
/*  97 */       double $$8 = Math.sqrt($$6);
/*     */ 
/*     */       
/* 100 */       this.xPush = $$4.x / $$7 * $$8;
/* 101 */       this.zPush = $$4.z / $$7 * $$8;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyNaturalSlowdown() {
/* 107 */     double $$0 = this.xPush * this.xPush + this.zPush * this.zPush;
/*     */     
/* 109 */     if ($$0 > 1.0E-7D) {
/* 110 */       $$0 = Math.sqrt($$0);
/* 111 */       this.xPush /= $$0;
/* 112 */       this.zPush /= $$0;
/*     */ 
/*     */       
/* 115 */       Vec3 $$1 = getDeltaMovement().multiply(0.8D, 0.0D, 0.8D).add(this.xPush, 0.0D, this.zPush);
/*     */       
/* 117 */       if (isInWater()) {
/* 118 */         $$1 = $$1.scale(0.1D);
/*     */       }
/* 120 */       setDeltaMovement($$1);
/*     */     } else {
/* 122 */       setDeltaMovement(getDeltaMovement().multiply(0.98D, 0.0D, 0.98D));
/*     */     } 
/*     */     
/* 125 */     super.applyNaturalSlowdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public InteractionResult interact(Player $$0, InteractionHand $$1) {
/* 130 */     ItemStack $$2 = $$0.getItemInHand($$1);
/* 131 */     if (INGREDIENT.test($$2) && this.fuel + 3600 <= 32000) {
/* 132 */       if (!($$0.getAbilities()).instabuild) {
/* 133 */         $$2.shrink(1);
/*     */       }
/* 135 */       this.fuel += 3600;
/*     */     } 
/*     */     
/* 138 */     if (this.fuel > 0) {
/* 139 */       this.xPush = getX() - $$0.getX();
/* 140 */       this.zPush = getZ() - $$0.getZ();
/*     */     } 
/*     */     
/* 143 */     return InteractionResult.sidedSuccess((level()).isClientSide);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addAdditionalSaveData(CompoundTag $$0) {
/* 148 */     super.addAdditionalSaveData($$0);
/* 149 */     $$0.putDouble("PushX", this.xPush);
/* 150 */     $$0.putDouble("PushZ", this.zPush);
/* 151 */     $$0.putShort("Fuel", (short)this.fuel);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAdditionalSaveData(CompoundTag $$0) {
/* 156 */     super.readAdditionalSaveData($$0);
/* 157 */     this.xPush = $$0.getDouble("PushX");
/* 158 */     this.zPush = $$0.getDouble("PushZ");
/* 159 */     this.fuel = $$0.getShort("Fuel");
/*     */   }
/*     */   
/*     */   protected boolean hasFuel() {
/* 163 */     return ((Boolean)this.entityData.get(DATA_ID_FUEL)).booleanValue();
/*     */   }
/*     */   
/*     */   protected void setHasFuel(boolean $$0) {
/* 167 */     this.entityData.set(DATA_ID_FUEL, Boolean.valueOf($$0));
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getDefaultDisplayBlockState() {
/* 172 */     return (BlockState)((BlockState)Blocks.FURNACE.defaultBlockState().setValue((Property)FurnaceBlock.FACING, (Comparable)Direction.NORTH)).setValue((Property)FurnaceBlock.LIT, Boolean.valueOf(hasFuel()));
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\vehicle\MinecartFurnace.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */