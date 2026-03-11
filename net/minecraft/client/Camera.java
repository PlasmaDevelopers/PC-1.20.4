/*     */ package net.minecraft.client;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.Direction;
/*     */ import net.minecraft.core.Position;
/*     */ import net.minecraft.tags.FluidTags;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.entity.LivingEntity;
/*     */ import net.minecraft.world.level.BlockGetter;
/*     */ import net.minecraft.world.level.ClipContext;
/*     */ import net.minecraft.world.level.block.Blocks;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.level.material.FluidState;
/*     */ import net.minecraft.world.level.material.FogType;
/*     */ import net.minecraft.world.phys.BlockHitResult;
/*     */ import net.minecraft.world.phys.HitResult;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ import org.joml.Quaternionf;
/*     */ import org.joml.Quaternionfc;
/*     */ import org.joml.Vector3f;
/*     */ 
/*     */ public class Camera
/*     */ {
/*     */   private boolean initialized;
/*  28 */   private Vec3 position = Vec3.ZERO; private BlockGetter level; private Entity entity;
/*  29 */   private final BlockPos.MutableBlockPos blockPosition = new BlockPos.MutableBlockPos();
/*  30 */   private final Vector3f forwards = new Vector3f(0.0F, 0.0F, 1.0F);
/*  31 */   private final Vector3f up = new Vector3f(0.0F, 1.0F, 0.0F);
/*  32 */   private final Vector3f left = new Vector3f(1.0F, 0.0F, 0.0F);
/*     */   private float xRot;
/*     */   private float yRot;
/*  35 */   private final Quaternionf rotation = new Quaternionf(0.0F, 0.0F, 0.0F, 1.0F);
/*     */   private boolean detached;
/*     */   private float eyeHeight;
/*     */   private float eyeHeightOld;
/*     */   private float partialTickTime;
/*     */   public static final float FOG_DISTANCE_SCALE = 0.083333336F;
/*     */   
/*     */   public void setup(BlockGetter $$0, Entity $$1, boolean $$2, boolean $$3, float $$4) {
/*  43 */     this.initialized = true;
/*  44 */     this.level = $$0;
/*  45 */     this.entity = $$1;
/*  46 */     this.detached = $$2;
/*  47 */     this.partialTickTime = $$4;
/*     */     
/*  49 */     setRotation($$1.getViewYRot($$4), $$1.getViewXRot($$4));
/*  50 */     setPosition(Mth.lerp($$4, $$1.xo, $$1.getX()), Mth.lerp($$4, $$1.yo, $$1.getY()) + Mth.lerp($$4, this.eyeHeightOld, this.eyeHeight), Mth.lerp($$4, $$1.zo, $$1.getZ()));
/*     */     
/*  52 */     if ($$2) {
/*  53 */       if ($$3) {
/*  54 */         setRotation(this.yRot + 180.0F, -this.xRot);
/*     */       }
/*     */       
/*  57 */       move(-getMaxZoom(4.0D), 0.0D, 0.0D);
/*  58 */     } else if ($$1 instanceof LivingEntity && ((LivingEntity)$$1).isSleeping()) {
/*  59 */       Direction $$5 = ((LivingEntity)$$1).getBedOrientation();
/*  60 */       setRotation(($$5 != null) ? ($$5.toYRot() - 180.0F) : 0.0F, 0.0F);
/*  61 */       move(0.0D, 0.3D, 0.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tick() {
/*  66 */     if (this.entity != null) {
/*  67 */       this.eyeHeightOld = this.eyeHeight;
/*  68 */       this.eyeHeight += (this.entity.getEyeHeight() - this.eyeHeight) * 0.5F;
/*     */     } 
/*     */   }
/*     */   
/*     */   private double getMaxZoom(double $$0) {
/*  73 */     for (int $$1 = 0; $$1 < 8; $$1++) {
/*  74 */       float $$2 = (($$1 & 0x1) * 2 - 1);
/*  75 */       float $$3 = (($$1 >> 1 & 0x1) * 2 - 1);
/*  76 */       float $$4 = (($$1 >> 2 & 0x1) * 2 - 1);
/*     */       
/*  78 */       $$2 *= 0.1F;
/*  79 */       $$3 *= 0.1F;
/*  80 */       $$4 *= 0.1F;
/*     */       
/*  82 */       Vec3 $$5 = this.position.add($$2, $$3, $$4);
/*  83 */       Vec3 $$6 = new Vec3(this.position.x - this.forwards.x() * $$0 + $$2, this.position.y - this.forwards.y() * $$0 + $$3, this.position.z - this.forwards.z() * $$0 + $$4);
/*  84 */       BlockHitResult blockHitResult = this.level.clip(new ClipContext($$5, $$6, ClipContext.Block.VISUAL, ClipContext.Fluid.NONE, this.entity));
/*  85 */       if (blockHitResult.getType() != HitResult.Type.MISS) {
/*  86 */         double $$8 = blockHitResult.getLocation().distanceTo(this.position);
/*  87 */         if ($$8 < $$0) {
/*  88 */           $$0 = $$8;
/*     */         }
/*     */       } 
/*     */     } 
/*  92 */     return $$0;
/*     */   }
/*     */   
/*     */   protected void move(double $$0, double $$1, double $$2) {
/*  96 */     double $$3 = this.forwards.x() * $$0 + this.up.x() * $$1 + this.left.x() * $$2;
/*  97 */     double $$4 = this.forwards.y() * $$0 + this.up.y() * $$1 + this.left.y() * $$2;
/*  98 */     double $$5 = this.forwards.z() * $$0 + this.up.z() * $$1 + this.left.z() * $$2;
/*  99 */     setPosition(new Vec3(this.position.x + $$3, this.position.y + $$4, this.position.z + $$5));
/*     */   }
/*     */   
/*     */   protected void setRotation(float $$0, float $$1) {
/* 103 */     this.xRot = $$1;
/* 104 */     this.yRot = $$0;
/*     */     
/* 106 */     this.rotation.rotationYXZ(-$$0 * 0.017453292F, $$1 * 0.017453292F, 0.0F);
/*     */     
/* 108 */     this.forwards.set(0.0F, 0.0F, 1.0F).rotate((Quaternionfc)this.rotation);
/* 109 */     this.up.set(0.0F, 1.0F, 0.0F).rotate((Quaternionfc)this.rotation);
/* 110 */     this.left.set(1.0F, 0.0F, 0.0F).rotate((Quaternionfc)this.rotation);
/*     */   }
/*     */   
/*     */   protected void setPosition(double $$0, double $$1, double $$2) {
/* 114 */     setPosition(new Vec3($$0, $$1, $$2));
/*     */   }
/*     */   
/*     */   protected void setPosition(Vec3 $$0) {
/* 118 */     this.position = $$0;
/* 119 */     this.blockPosition.set($$0.x, $$0.y, $$0.z);
/*     */   }
/*     */   
/*     */   public Vec3 getPosition() {
/* 123 */     return this.position;
/*     */   }
/*     */   
/*     */   public BlockPos getBlockPosition() {
/* 127 */     return (BlockPos)this.blockPosition;
/*     */   }
/*     */   
/*     */   public float getXRot() {
/* 131 */     return this.xRot;
/*     */   }
/*     */   
/*     */   public float getYRot() {
/* 135 */     return this.yRot;
/*     */   }
/*     */   
/*     */   public Quaternionf rotation() {
/* 139 */     return this.rotation;
/*     */   }
/*     */   
/*     */   public Entity getEntity() {
/* 143 */     return this.entity;
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 147 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public boolean isDetached() {
/* 151 */     return this.detached;
/*     */   }
/*     */   
/*     */   public NearPlane getNearPlane() {
/* 155 */     Minecraft $$0 = Minecraft.getInstance();
/* 156 */     double $$1 = $$0.getWindow().getWidth() / $$0.getWindow().getHeight();
/* 157 */     double $$2 = Math.tan((((Integer)$$0.options.fov().get()).intValue() * 0.017453292F) / 2.0D) * 0.05000000074505806D;
/* 158 */     double $$3 = $$2 * $$1;
/*     */     
/* 160 */     Vec3 $$4 = (new Vec3(this.forwards)).scale(0.05000000074505806D);
/* 161 */     Vec3 $$5 = (new Vec3(this.left)).scale($$3);
/* 162 */     Vec3 $$6 = (new Vec3(this.up)).scale($$2);
/*     */     
/* 164 */     return new NearPlane($$4, $$5, $$6);
/*     */   }
/*     */   
/*     */   public FogType getFluidInCamera() {
/* 168 */     if (!this.initialized) {
/* 169 */       return FogType.NONE;
/*     */     }
/*     */     
/* 172 */     FluidState $$0 = this.level.getFluidState((BlockPos)this.blockPosition);
/* 173 */     if ($$0.is(FluidTags.WATER) && 
/* 174 */       this.position.y < (this.blockPosition.getY() + $$0.getHeight(this.level, (BlockPos)this.blockPosition))) {
/* 175 */       return FogType.WATER;
/*     */     }
/*     */ 
/*     */     
/* 179 */     NearPlane $$1 = getNearPlane();
/*     */ 
/*     */     
/* 182 */     List<Vec3> $$2 = Arrays.asList(new Vec3[] { $$1.forward, $$1.getTopLeft(), $$1.getTopRight(), $$1.getBottomLeft(), $$1.getBottomRight() });
/* 183 */     for (Vec3 $$3 : $$2) {
/* 184 */       Vec3 $$4 = this.position.add($$3);
/* 185 */       BlockPos $$5 = BlockPos.containing((Position)$$4);
/* 186 */       FluidState $$6 = this.level.getFluidState($$5);
/* 187 */       if ($$6.is(FluidTags.LAVA)) {
/* 188 */         if ($$4.y <= ($$6.getHeight(this.level, $$5) + $$5.getY()))
/* 189 */           return FogType.LAVA; 
/*     */         continue;
/*     */       } 
/* 192 */       BlockState $$7 = this.level.getBlockState($$5);
/* 193 */       if ($$7.is(Blocks.POWDER_SNOW)) {
/* 194 */         return FogType.POWDER_SNOW;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 199 */     return FogType.NONE;
/*     */   }
/*     */   
/*     */   public final Vector3f getLookVector() {
/* 203 */     return this.forwards;
/*     */   }
/*     */   
/*     */   public final Vector3f getUpVector() {
/* 207 */     return this.up;
/*     */   }
/*     */   
/*     */   public final Vector3f getLeftVector() {
/* 211 */     return this.left;
/*     */   }
/*     */   
/*     */   public void reset() {
/* 215 */     this.level = null;
/* 216 */     this.entity = null;
/* 217 */     this.initialized = false;
/*     */   }
/*     */   
/*     */   public float getPartialTickTime() {
/* 221 */     return this.partialTickTime;
/*     */   }
/*     */   
/*     */   public static class NearPlane {
/*     */     final Vec3 forward;
/*     */     private final Vec3 left;
/*     */     private final Vec3 up;
/*     */     
/*     */     NearPlane(Vec3 $$0, Vec3 $$1, Vec3 $$2) {
/* 230 */       this.forward = $$0;
/* 231 */       this.left = $$1;
/* 232 */       this.up = $$2;
/*     */     }
/*     */     
/*     */     public Vec3 getTopLeft() {
/* 236 */       return this.forward.add(this.up).add(this.left);
/*     */     }
/*     */     
/*     */     public Vec3 getTopRight() {
/* 240 */       return this.forward.add(this.up).subtract(this.left);
/*     */     }
/*     */     
/*     */     public Vec3 getBottomLeft() {
/* 244 */       return this.forward.subtract(this.up).add(this.left);
/*     */     }
/*     */     
/*     */     public Vec3 getBottomRight() {
/* 248 */       return this.forward.subtract(this.up).subtract(this.left);
/*     */     }
/*     */     
/*     */     public Vec3 getPointOnPlane(float $$0, float $$1) {
/* 252 */       return this.forward.add(this.up.scale($$1)).subtract(this.left.scale($$0));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\Camera.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */