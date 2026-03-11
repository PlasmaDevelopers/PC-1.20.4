/*     */ package net.minecraft.client.particle;
/*     */ import com.mojang.blaze3d.vertex.VertexConsumer;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.multiplayer.ClientLevel;
/*     */ import net.minecraft.client.renderer.LevelRenderer;
/*     */ import net.minecraft.core.BlockPos;
/*     */ import net.minecraft.core.particles.ParticleGroup;
/*     */ import net.minecraft.util.Mth;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.BlockAndTintGetter;
/*     */ import net.minecraft.world.level.Level;
/*     */ import net.minecraft.world.phys.AABB;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ public abstract class Particle {
/*  19 */   private static final AABB INITIAL_AABB = new AABB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*  20 */   private static final double MAXIMUM_COLLISION_VELOCITY_SQUARED = Mth.square(100.0D);
/*     */   
/*     */   protected final ClientLevel level;
/*     */   protected double xo;
/*     */   protected double yo;
/*     */   protected double zo;
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected double xd;
/*     */   protected double yd;
/*     */   protected double zd;
/*  32 */   private AABB bb = INITIAL_AABB;
/*     */   
/*     */   protected boolean onGround;
/*     */   
/*     */   protected boolean hasPhysics = true;
/*     */   private boolean stoppedByCollision;
/*     */   protected boolean removed;
/*  39 */   protected float bbWidth = 0.6F;
/*  40 */   protected float bbHeight = 1.8F;
/*     */   
/*  42 */   protected final RandomSource random = RandomSource.create();
/*     */   
/*     */   protected int age;
/*     */   protected int lifetime;
/*     */   protected float gravity;
/*  47 */   protected float rCol = 1.0F;
/*  48 */   protected float gCol = 1.0F;
/*  49 */   protected float bCol = 1.0F;
/*  50 */   protected float alpha = 1.0F;
/*     */   protected float roll;
/*     */   protected float oRoll;
/*  53 */   protected float friction = 0.98F;
/*     */   protected boolean speedUpWhenYMotionIsBlocked = false;
/*     */   
/*     */   protected Particle(ClientLevel $$0, double $$1, double $$2, double $$3) {
/*  57 */     this.level = $$0;
/*     */     
/*  59 */     setSize(0.2F, 0.2F);
/*  60 */     setPos($$1, $$2, $$3);
/*  61 */     this.xo = $$1;
/*  62 */     this.yo = $$2;
/*  63 */     this.zo = $$3;
/*     */     
/*  65 */     this.lifetime = (int)(4.0F / (this.random.nextFloat() * 0.9F + 0.1F));
/*     */   }
/*     */   
/*     */   public Particle(ClientLevel $$0, double $$1, double $$2, double $$3, double $$4, double $$5, double $$6) {
/*  69 */     this($$0, $$1, $$2, $$3);
/*     */     
/*  71 */     this.xd = $$4 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/*  72 */     this.yd = $$5 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/*  73 */     this.zd = $$6 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
/*  74 */     double $$7 = (Math.random() + Math.random() + 1.0D) * 0.15000000596046448D;
/*     */     
/*  76 */     double $$8 = Math.sqrt(this.xd * this.xd + this.yd * this.yd + this.zd * this.zd);
/*  77 */     this.xd = this.xd / $$8 * $$7 * 0.4000000059604645D;
/*  78 */     this.yd = this.yd / $$8 * $$7 * 0.4000000059604645D + 0.10000000149011612D;
/*  79 */     this.zd = this.zd / $$8 * $$7 * 0.4000000059604645D;
/*     */   }
/*     */   
/*     */   public Particle setPower(float $$0) {
/*  83 */     this.xd *= $$0;
/*  84 */     this.yd = (this.yd - 0.10000000149011612D) * $$0 + 0.10000000149011612D;
/*  85 */     this.zd *= $$0;
/*  86 */     return this;
/*     */   }
/*     */   
/*     */   public void setParticleSpeed(double $$0, double $$1, double $$2) {
/*  90 */     this.xd = $$0;
/*  91 */     this.yd = $$1;
/*  92 */     this.zd = $$2;
/*     */   }
/*     */   
/*     */   public Particle scale(float $$0) {
/*  96 */     setSize(0.2F * $$0, 0.2F * $$0);
/*  97 */     return this;
/*     */   }
/*     */   
/*     */   public void setColor(float $$0, float $$1, float $$2) {
/* 101 */     this.rCol = $$0;
/* 102 */     this.gCol = $$1;
/* 103 */     this.bCol = $$2;
/*     */   }
/*     */   
/*     */   protected void setAlpha(float $$0) {
/* 107 */     this.alpha = $$0;
/*     */   }
/*     */   
/*     */   public void setLifetime(int $$0) {
/* 111 */     this.lifetime = $$0;
/*     */   }
/*     */   
/*     */   public int getLifetime() {
/* 115 */     return this.lifetime;
/*     */   }
/*     */   
/*     */   public void tick() {
/* 119 */     this.xo = this.x;
/* 120 */     this.yo = this.y;
/* 121 */     this.zo = this.z;
/*     */     
/* 123 */     if (this.age++ >= this.lifetime) {
/* 124 */       remove();
/*     */       
/*     */       return;
/*     */     } 
/* 128 */     this.yd -= 0.04D * this.gravity;
/* 129 */     move(this.xd, this.yd, this.zd);
/* 130 */     if (this.speedUpWhenYMotionIsBlocked && this.y == this.yo) {
/* 131 */       this.xd *= 1.1D;
/* 132 */       this.zd *= 1.1D;
/*     */     } 
/* 134 */     this.xd *= this.friction;
/* 135 */     this.yd *= this.friction;
/* 136 */     this.zd *= this.friction;
/*     */     
/* 138 */     if (this.onGround) {
/* 139 */       this.xd *= 0.699999988079071D;
/* 140 */       this.zd *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract void render(VertexConsumer paramVertexConsumer, Camera paramCamera, float paramFloat);
/*     */   
/*     */   public abstract ParticleRenderType getRenderType();
/*     */   
/*     */   public String toString() {
/* 150 */     return getClass().getSimpleName() + ", Pos (" + getClass().getSimpleName() + "," + this.x + "," + this.y + "), RGBA (" + this.z + "," + this.rCol + "," + this.gCol + "," + this.bCol + "), Age " + this.alpha;
/*     */   }
/*     */   
/*     */   public void remove() {
/* 154 */     this.removed = true;
/*     */   }
/*     */   
/*     */   protected void setSize(float $$0, float $$1) {
/* 158 */     if ($$0 != this.bbWidth || $$1 != this.bbHeight) {
/* 159 */       this.bbWidth = $$0;
/* 160 */       this.bbHeight = $$1;
/* 161 */       AABB $$2 = getBoundingBox();
/* 162 */       double $$3 = ($$2.minX + $$2.maxX - $$0) / 2.0D;
/* 163 */       double $$4 = ($$2.minZ + $$2.maxZ - $$0) / 2.0D;
/* 164 */       setBoundingBox(new AABB($$3, $$2.minY, $$4, $$3 + this.bbWidth, $$2.minY + this.bbHeight, $$4 + this.bbWidth));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setPos(double $$0, double $$1, double $$2) {
/* 169 */     this.x = $$0;
/* 170 */     this.y = $$1;
/* 171 */     this.z = $$2;
/* 172 */     float $$3 = this.bbWidth / 2.0F;
/* 173 */     float $$4 = this.bbHeight;
/* 174 */     setBoundingBox(new AABB($$0 - $$3, $$1, $$2 - $$3, $$0 + $$3, $$1 + $$4, $$2 + $$3));
/*     */   }
/*     */   
/*     */   public void move(double $$0, double $$1, double $$2) {
/* 178 */     if (this.stoppedByCollision) {
/*     */       return;
/*     */     }
/*     */     
/* 182 */     double $$3 = $$0;
/* 183 */     double $$4 = $$1;
/* 184 */     double $$5 = $$2;
/*     */     
/* 186 */     if (this.hasPhysics && ($$0 != 0.0D || $$1 != 0.0D || $$2 != 0.0D) && $$0 * $$0 + $$1 * $$1 + $$2 * $$2 < MAXIMUM_COLLISION_VELOCITY_SQUARED) {
/* 187 */       Vec3 $$6 = Entity.collideBoundingBox(null, new Vec3($$0, $$1, $$2), getBoundingBox(), (Level)this.level, List.of());
/* 188 */       $$0 = $$6.x;
/* 189 */       $$1 = $$6.y;
/* 190 */       $$2 = $$6.z;
/*     */     } 
/*     */     
/* 193 */     if ($$0 != 0.0D || $$1 != 0.0D || $$2 != 0.0D) {
/* 194 */       setBoundingBox(getBoundingBox().move($$0, $$1, $$2));
/* 195 */       setLocationFromBoundingbox();
/*     */     } 
/*     */     
/* 198 */     if (Math.abs($$4) >= 9.999999747378752E-6D && Math.abs($$1) < 9.999999747378752E-6D) {
/* 199 */       this.stoppedByCollision = true;
/*     */     }
/*     */     
/* 202 */     this.onGround = ($$4 != $$1 && $$4 < 0.0D);
/*     */     
/* 204 */     if ($$3 != $$0) {
/* 205 */       this.xd = 0.0D;
/*     */     }
/* 207 */     if ($$5 != $$2) {
/* 208 */       this.zd = 0.0D;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setLocationFromBoundingbox() {
/* 213 */     AABB $$0 = getBoundingBox();
/* 214 */     this.x = ($$0.minX + $$0.maxX) / 2.0D;
/* 215 */     this.y = $$0.minY;
/* 216 */     this.z = ($$0.minZ + $$0.maxZ) / 2.0D;
/*     */   }
/*     */   
/*     */   protected int getLightColor(float $$0) {
/* 220 */     BlockPos $$1 = BlockPos.containing(this.x, this.y, this.z);
/* 221 */     if (this.level.hasChunkAt($$1)) {
/* 222 */       return LevelRenderer.getLightColor((BlockAndTintGetter)this.level, $$1);
/*     */     }
/* 224 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isAlive() {
/* 228 */     return !this.removed;
/*     */   }
/*     */   
/*     */   public AABB getBoundingBox() {
/* 232 */     return this.bb;
/*     */   }
/*     */   
/*     */   public void setBoundingBox(AABB $$0) {
/* 236 */     this.bb = $$0;
/*     */   }
/*     */   
/*     */   public Optional<ParticleGroup> getParticleGroup() {
/* 240 */     return Optional.empty();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\particle\Particle.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */