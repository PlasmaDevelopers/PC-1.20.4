/*      */ package net.minecraft.world.entity;
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import com.mojang.math.Transformation;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import it.unimi.dsi.fastutil.ints.IntSet;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.function.IntFunction;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.commands.CommandSourceStack;
/*      */ import net.minecraft.core.HolderGetter;
/*      */ import net.minecraft.core.registries.Registries;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.NbtUtils;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.ComponentUtils;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.util.Brightness;
/*      */ import net.minecraft.util.ByIdMap;
/*      */ import net.minecraft.util.FastColor;
/*      */ import net.minecraft.util.FormattedCharSequence;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.util.StringRepresentable;
/*      */ import net.minecraft.world.item.ItemDisplayContext;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.level.Level;
/*      */ import net.minecraft.world.level.block.Blocks;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.level.material.PushReaction;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import org.joml.Quaternionf;
/*      */ import org.joml.Vector3f;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public abstract class Display extends Entity {
/*   45 */   static final Logger LOGGER = LogUtils.getLogger();
/*      */ 
/*      */   
/*      */   public static final int NO_BRIGHTNESS_OVERRIDE = -1;
/*      */ 
/*      */   
/*   51 */   private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
/*      */   
/*   53 */   private static final EntityDataAccessor<Integer> DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
/*      */   
/*   55 */   private static final EntityDataAccessor<Integer> DATA_POS_ROT_INTERPOLATION_DURATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
/*      */   
/*   57 */   private static final EntityDataAccessor<Vector3f> DATA_TRANSLATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.VECTOR3);
/*      */   
/*   59 */   private static final EntityDataAccessor<Vector3f> DATA_SCALE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.VECTOR3);
/*      */   
/*   61 */   private static final EntityDataAccessor<Quaternionf> DATA_LEFT_ROTATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.QUATERNION);
/*      */   
/*   63 */   private static final EntityDataAccessor<Quaternionf> DATA_RIGHT_ROTATION_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.QUATERNION);
/*      */   
/*   65 */   private static final EntityDataAccessor<Byte> DATA_BILLBOARD_RENDER_CONSTRAINTS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.BYTE);
/*      */   
/*   67 */   private static final EntityDataAccessor<Integer> DATA_BRIGHTNESS_OVERRIDE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
/*      */   
/*   69 */   private static final EntityDataAccessor<Float> DATA_VIEW_RANGE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
/*      */   
/*   71 */   private static final EntityDataAccessor<Float> DATA_SHADOW_RADIUS_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
/*      */   
/*   73 */   private static final EntityDataAccessor<Float> DATA_SHADOW_STRENGTH_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
/*      */   
/*   75 */   private static final EntityDataAccessor<Float> DATA_WIDTH_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
/*      */   
/*   77 */   private static final EntityDataAccessor<Float> DATA_HEIGHT_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.FLOAT);
/*      */   
/*   79 */   private static final EntityDataAccessor<Integer> DATA_GLOW_COLOR_OVERRIDE_ID = SynchedEntityData.defineId(Display.class, EntityDataSerializers.INT);
/*      */   
/*   81 */   private static final IntSet RENDER_STATE_IDS = IntSet.of(new int[] { DATA_TRANSLATION_ID
/*   82 */         .getId(), DATA_SCALE_ID
/*   83 */         .getId(), DATA_LEFT_ROTATION_ID
/*   84 */         .getId(), DATA_RIGHT_ROTATION_ID
/*   85 */         .getId(), DATA_BILLBOARD_RENDER_CONSTRAINTS_ID
/*   86 */         .getId(), DATA_BRIGHTNESS_OVERRIDE_ID
/*   87 */         .getId(), DATA_SHADOW_RADIUS_ID
/*   88 */         .getId(), DATA_SHADOW_STRENGTH_ID
/*   89 */         .getId() }); private static final float INITIAL_SHADOW_RADIUS = 0.0F; private static final float INITIAL_SHADOW_STRENGTH = 1.0F; private static final int NO_GLOW_COLOR_OVERRIDE = -1; public static final String TAG_POS_ROT_INTERPOLATION_DURATION = "teleport_duration"; public static final String TAG_TRANSFORMATION_INTERPOLATION_DURATION = "interpolation_duration"; public static final String TAG_TRANSFORMATION_START_INTERPOLATION = "start_interpolation"; public static final String TAG_TRANSFORMATION = "transformation"; public static final String TAG_BILLBOARD = "billboard"; public static final String TAG_BRIGHTNESS = "brightness";
/*      */   public static final String TAG_VIEW_RANGE = "view_range";
/*      */   public static final String TAG_SHADOW_RADIUS = "shadow_radius";
/*      */   public static final String TAG_SHADOW_STRENGTH = "shadow_strength";
/*      */   public static final String TAG_WIDTH = "width";
/*      */   public static final String TAG_HEIGHT = "height";
/*      */   public static final String TAG_GLOW_COLOR_OVERRIDE = "glow_color_override";
/*      */   
/*   97 */   public enum BillboardConstraints implements StringRepresentable { FIXED((byte)0, "fixed"),
/*   98 */     VERTICAL((byte)1, "vertical"),
/*   99 */     HORIZONTAL((byte)2, "horizontal"),
/*  100 */     CENTER((byte)3, "center");
/*      */ 
/*      */     
/*  103 */     public static final Codec<BillboardConstraints> CODEC = (Codec<BillboardConstraints>)StringRepresentable.fromEnum(BillboardConstraints::values);
/*  104 */     public static final IntFunction<BillboardConstraints> BY_ID = ByIdMap.continuous(BillboardConstraints::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO); private final byte id; private final String name;
/*      */     static {
/*      */     
/*      */     }
/*      */     
/*      */     BillboardConstraints(byte $$0, String $$1) {
/*  110 */       this.name = $$1;
/*  111 */       this.id = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getSerializedName() {
/*  116 */       return this.name;
/*      */     }
/*      */     
/*      */     byte getId() {
/*  120 */       return this.id;
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  140 */   private long interpolationStartClientTick = -2147483648L;
/*      */   
/*      */   private int interpolationDuration;
/*      */   
/*      */   private float lastProgress;
/*      */   
/*      */   private AABB cullingBoundingBox;
/*      */   
/*      */   protected boolean updateRenderState;
/*      */   private boolean updateStartTick;
/*      */   private boolean updateInterpolationDuration;
/*      */   @Nullable
/*      */   private RenderState renderState;
/*      */   @Nullable
/*      */   private PosRotInterpolationTarget posRotInterpolationTarget;
/*      */   
/*      */   public Display(EntityType<?> $$0, Level $$1) {
/*  157 */     super($$0, $$1);
/*  158 */     this.noPhysics = true;
/*  159 */     this.noCulling = true;
/*  160 */     this.cullingBoundingBox = getBoundingBox();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  165 */     super.onSyncedDataUpdated($$0);
/*      */     
/*  167 */     if (DATA_HEIGHT_ID.equals($$0) || DATA_WIDTH_ID.equals($$0)) {
/*  168 */       updateCulling();
/*      */     }
/*      */     
/*  171 */     if (DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID.equals($$0)) {
/*  172 */       this.updateStartTick = true;
/*      */     }
/*      */     
/*  175 */     if (DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID.equals($$0)) {
/*  176 */       this.updateInterpolationDuration = true;
/*      */     }
/*      */     
/*  179 */     if (RENDER_STATE_IDS.contains($$0.getId())) {
/*  180 */       this.updateRenderState = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private static Transformation createTransformation(SynchedEntityData $$0) {
/*  185 */     Vector3f $$1 = (Vector3f)$$0.get(DATA_TRANSLATION_ID);
/*  186 */     Quaternionf $$2 = (Quaternionf)$$0.get(DATA_LEFT_ROTATION_ID);
/*  187 */     Vector3f $$3 = (Vector3f)$$0.get(DATA_SCALE_ID);
/*  188 */     Quaternionf $$4 = (Quaternionf)$$0.get(DATA_RIGHT_ROTATION_ID);
/*  189 */     return new Transformation($$1, $$2, $$3, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  194 */     Entity $$0 = getVehicle();
/*  195 */     if ($$0 != null && $$0.isRemoved()) {
/*  196 */       stopRiding();
/*      */     }
/*      */     
/*  199 */     if ((level()).isClientSide) {
/*  200 */       if (this.updateStartTick) {
/*  201 */         this.updateStartTick = false;
/*      */         
/*  203 */         int $$1 = getTransformationInterpolationDelay();
/*  204 */         this.interpolationStartClientTick = (this.tickCount + $$1);
/*      */       } 
/*      */       
/*  207 */       if (this.updateInterpolationDuration) {
/*  208 */         this.updateInterpolationDuration = false;
/*  209 */         this.interpolationDuration = getTransformationInterpolationDuration();
/*      */       } 
/*      */       
/*  212 */       if (this.updateRenderState) {
/*  213 */         this.updateRenderState = false;
/*      */         
/*  215 */         boolean $$2 = (this.interpolationDuration != 0);
/*  216 */         if ($$2 && this.renderState != null) {
/*  217 */           this.renderState = createInterpolatedRenderState(this.renderState, this.lastProgress);
/*      */         } else {
/*  219 */           this.renderState = createFreshRenderState();
/*      */         } 
/*  221 */         updateRenderSubState($$2, this.lastProgress);
/*      */       } 
/*      */       
/*  224 */       if (this.posRotInterpolationTarget != null) {
/*  225 */         if (this.posRotInterpolationTarget.steps == 0) {
/*  226 */           this.posRotInterpolationTarget.applyTargetPosAndRot(this);
/*      */           
/*  228 */           setOldPosAndRot();
/*  229 */           this.posRotInterpolationTarget = null;
/*      */         } else {
/*  231 */           this.posRotInterpolationTarget.applyLerpStep(this);
/*  232 */           this.posRotInterpolationTarget.steps--;
/*  233 */           if (this.posRotInterpolationTarget.steps == 0)
/*      */           {
/*  235 */             this.posRotInterpolationTarget = null;
/*      */           }
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  246 */     this.entityData.define(DATA_POS_ROT_INTERPOLATION_DURATION_ID, Integer.valueOf(0));
/*  247 */     this.entityData.define(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID, Integer.valueOf(0));
/*  248 */     this.entityData.define(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, Integer.valueOf(0));
/*  249 */     this.entityData.define(DATA_TRANSLATION_ID, new Vector3f());
/*  250 */     this.entityData.define(DATA_SCALE_ID, new Vector3f(1.0F, 1.0F, 1.0F));
/*  251 */     this.entityData.define(DATA_RIGHT_ROTATION_ID, new Quaternionf());
/*  252 */     this.entityData.define(DATA_LEFT_ROTATION_ID, new Quaternionf());
/*  253 */     this.entityData.define(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, Byte.valueOf(BillboardConstraints.FIXED.getId()));
/*  254 */     this.entityData.define(DATA_BRIGHTNESS_OVERRIDE_ID, Integer.valueOf(-1));
/*  255 */     this.entityData.define(DATA_VIEW_RANGE_ID, Float.valueOf(1.0F));
/*  256 */     this.entityData.define(DATA_SHADOW_RADIUS_ID, Float.valueOf(0.0F));
/*  257 */     this.entityData.define(DATA_SHADOW_STRENGTH_ID, Float.valueOf(1.0F));
/*  258 */     this.entityData.define(DATA_WIDTH_ID, Float.valueOf(0.0F));
/*  259 */     this.entityData.define(DATA_HEIGHT_ID, Float.valueOf(0.0F));
/*  260 */     this.entityData.define(DATA_GLOW_COLOR_OVERRIDE_ID, Integer.valueOf(-1));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  265 */     if ($$0.contains("transformation")) {
/*  266 */       Objects.requireNonNull(LOGGER); Transformation.EXTENDED_CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("transformation")).resultOrPartial(Util.prefix("Display entity", LOGGER::error)).ifPresent($$0 -> setTransformation((Transformation)$$0.getFirst()));
/*      */     } 
/*      */     
/*  269 */     if ($$0.contains("interpolation_duration", 99)) {
/*  270 */       int $$1 = $$0.getInt("interpolation_duration");
/*  271 */       setTransformationInterpolationDuration($$1);
/*      */     } 
/*      */     
/*  274 */     if ($$0.contains("start_interpolation", 99)) {
/*  275 */       int $$2 = $$0.getInt("start_interpolation");
/*  276 */       setTransformationInterpolationDelay($$2);
/*      */     } 
/*      */     
/*  279 */     if ($$0.contains("teleport_duration", 99)) {
/*  280 */       int $$3 = $$0.getInt("teleport_duration");
/*      */       
/*  282 */       setPosRotInterpolationDuration(Mth.clamp($$3, 0, 59));
/*      */     } 
/*      */     
/*  285 */     if ($$0.contains("billboard", 8)) {
/*  286 */       Objects.requireNonNull(LOGGER); BillboardConstraints.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("billboard")).resultOrPartial(Util.prefix("Display entity", LOGGER::error)).ifPresent($$0 -> setBillboardConstraints((BillboardConstraints)$$0.getFirst()));
/*      */     } 
/*      */     
/*  289 */     if ($$0.contains("view_range", 99)) {
/*  290 */       setViewRange($$0.getFloat("view_range"));
/*      */     }
/*      */     
/*  293 */     if ($$0.contains("shadow_radius", 99)) {
/*  294 */       setShadowRadius($$0.getFloat("shadow_radius"));
/*      */     }
/*      */     
/*  297 */     if ($$0.contains("shadow_strength", 99)) {
/*  298 */       setShadowStrength($$0.getFloat("shadow_strength"));
/*      */     }
/*      */     
/*  301 */     if ($$0.contains("width", 99)) {
/*  302 */       setWidth($$0.getFloat("width"));
/*      */     }
/*      */     
/*  305 */     if ($$0.contains("height", 99)) {
/*  306 */       setHeight($$0.getFloat("height"));
/*      */     }
/*      */     
/*  309 */     if ($$0.contains("glow_color_override", 99)) {
/*  310 */       setGlowColorOverride($$0.getInt("glow_color_override"));
/*      */     }
/*      */     
/*  313 */     if ($$0.contains("brightness", 10)) {
/*  314 */       Objects.requireNonNull(LOGGER); Brightness.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("brightness")).resultOrPartial(Util.prefix("Display entity", LOGGER::error)).ifPresent($$0 -> setBrightnessOverride((Brightness)$$0.getFirst()));
/*      */     } else {
/*  316 */       setBrightnessOverride((Brightness)null);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setTransformation(Transformation $$0) {
/*  321 */     this.entityData.set(DATA_TRANSLATION_ID, $$0.getTranslation());
/*  322 */     this.entityData.set(DATA_LEFT_ROTATION_ID, $$0.getLeftRotation());
/*  323 */     this.entityData.set(DATA_SCALE_ID, $$0.getScale());
/*  324 */     this.entityData.set(DATA_RIGHT_ROTATION_ID, $$0.getRightRotation());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  329 */     Transformation.EXTENDED_CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, createTransformation(this.entityData)).result().ifPresent($$1 -> $$0.put("transformation", $$1));
/*  330 */     BillboardConstraints.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getBillboardConstraints()).result().ifPresent($$1 -> $$0.put("billboard", $$1));
/*  331 */     $$0.putInt("interpolation_duration", getTransformationInterpolationDuration());
/*  332 */     $$0.putInt("teleport_duration", getPosRotInterpolationDuration());
/*  333 */     $$0.putFloat("view_range", getViewRange());
/*  334 */     $$0.putFloat("shadow_radius", getShadowRadius());
/*  335 */     $$0.putFloat("shadow_strength", getShadowStrength());
/*  336 */     $$0.putFloat("width", getWidth());
/*  337 */     $$0.putFloat("height", getHeight());
/*  338 */     $$0.putInt("glow_color_override", getGlowColorOverride());
/*  339 */     Brightness $$1 = getBrightnessOverride();
/*  340 */     if ($$1 != null) {
/*  341 */       Brightness.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, $$1).result().ifPresent($$1 -> $$0.put("brightness", $$1));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void lerpTo(double $$0, double $$1, double $$2, float $$3, float $$4, int $$5) {
/*  347 */     int $$6 = getPosRotInterpolationDuration();
/*  348 */     this.posRotInterpolationTarget = new PosRotInterpolationTarget($$6, $$0, $$1, $$2, $$3, $$4);
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetX() {
/*  353 */     return (this.posRotInterpolationTarget != null) ? this.posRotInterpolationTarget.targetX : getX();
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetY() {
/*  358 */     return (this.posRotInterpolationTarget != null) ? this.posRotInterpolationTarget.targetY : getY();
/*      */   }
/*      */ 
/*      */   
/*      */   public double lerpTargetZ() {
/*  363 */     return (this.posRotInterpolationTarget != null) ? this.posRotInterpolationTarget.targetZ : getZ();
/*      */   }
/*      */ 
/*      */   
/*      */   public float lerpTargetXRot() {
/*  368 */     return (this.posRotInterpolationTarget != null) ? (float)this.posRotInterpolationTarget.targetXRot : getXRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public float lerpTargetYRot() {
/*  373 */     return (this.posRotInterpolationTarget != null) ? (float)this.posRotInterpolationTarget.targetYRot : getYRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public AABB getBoundingBoxForCulling() {
/*  378 */     return this.cullingBoundingBox;
/*      */   }
/*      */ 
/*      */   
/*      */   public PushReaction getPistonPushReaction() {
/*  383 */     return PushReaction.IGNORE;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isIgnoringBlockTriggers() {
/*  388 */     return true;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public RenderState renderState() {
/*  393 */     return this.renderState;
/*      */   }
/*      */   
/*      */   private void setTransformationInterpolationDuration(int $$0) {
/*  397 */     this.entityData.set(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   private int getTransformationInterpolationDuration() {
/*  401 */     return ((Integer)this.entityData.get(DATA_TRANSFORMATION_INTERPOLATION_DURATION_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setTransformationInterpolationDelay(int $$0) {
/*  405 */     this.entityData.set(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID, Integer.valueOf($$0), true);
/*      */   }
/*      */   
/*      */   private int getTransformationInterpolationDelay() {
/*  409 */     return ((Integer)this.entityData.get(DATA_TRANSFORMATION_INTERPOLATION_START_DELTA_TICKS_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setPosRotInterpolationDuration(int $$0) {
/*  413 */     this.entityData.set(DATA_POS_ROT_INTERPOLATION_DURATION_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   private int getPosRotInterpolationDuration() {
/*  417 */     return ((Integer)this.entityData.get(DATA_POS_ROT_INTERPOLATION_DURATION_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setBillboardConstraints(BillboardConstraints $$0) {
/*  421 */     this.entityData.set(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID, Byte.valueOf($$0.getId()));
/*      */   }
/*      */   
/*      */   private BillboardConstraints getBillboardConstraints() {
/*  425 */     return BillboardConstraints.BY_ID.apply(((Byte)this.entityData.get(DATA_BILLBOARD_RENDER_CONSTRAINTS_ID)).byteValue());
/*      */   }
/*      */   
/*      */   private void setBrightnessOverride(@Nullable Brightness $$0) {
/*  429 */     this.entityData.set(DATA_BRIGHTNESS_OVERRIDE_ID, Integer.valueOf(($$0 != null) ? $$0.pack() : -1));
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   private Brightness getBrightnessOverride() {
/*  434 */     int $$0 = ((Integer)this.entityData.get(DATA_BRIGHTNESS_OVERRIDE_ID)).intValue();
/*  435 */     return ($$0 != -1) ? Brightness.unpack($$0) : null;
/*      */   }
/*      */   
/*      */   private int getPackedBrightnessOverride() {
/*  439 */     return ((Integer)this.entityData.get(DATA_BRIGHTNESS_OVERRIDE_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setViewRange(float $$0) {
/*  443 */     this.entityData.set(DATA_VIEW_RANGE_ID, Float.valueOf($$0));
/*      */   }
/*      */   
/*      */   private float getViewRange() {
/*  447 */     return ((Float)this.entityData.get(DATA_VIEW_RANGE_ID)).floatValue();
/*      */   }
/*      */   
/*      */   private void setShadowRadius(float $$0) {
/*  451 */     this.entityData.set(DATA_SHADOW_RADIUS_ID, Float.valueOf($$0));
/*      */   }
/*      */   
/*      */   private float getShadowRadius() {
/*  455 */     return ((Float)this.entityData.get(DATA_SHADOW_RADIUS_ID)).floatValue();
/*      */   }
/*      */   
/*      */   private void setShadowStrength(float $$0) {
/*  459 */     this.entityData.set(DATA_SHADOW_STRENGTH_ID, Float.valueOf($$0));
/*      */   }
/*      */   
/*      */   private float getShadowStrength() {
/*  463 */     return ((Float)this.entityData.get(DATA_SHADOW_STRENGTH_ID)).floatValue();
/*      */   }
/*      */   
/*      */   private void setWidth(float $$0) {
/*  467 */     this.entityData.set(DATA_WIDTH_ID, Float.valueOf($$0));
/*      */   }
/*      */   
/*      */   private float getWidth() {
/*  471 */     return ((Float)this.entityData.get(DATA_WIDTH_ID)).floatValue();
/*      */   }
/*      */   
/*      */   private void setHeight(float $$0) {
/*  475 */     this.entityData.set(DATA_HEIGHT_ID, Float.valueOf($$0));
/*      */   }
/*      */   
/*      */   private int getGlowColorOverride() {
/*  479 */     return ((Integer)this.entityData.get(DATA_GLOW_COLOR_OVERRIDE_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setGlowColorOverride(int $$0) {
/*  483 */     this.entityData.set(DATA_GLOW_COLOR_OVERRIDE_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   public float calculateInterpolationProgress(float $$0) {
/*  487 */     int $$1 = this.interpolationDuration;
/*  488 */     if ($$1 <= 0) {
/*  489 */       return 1.0F;
/*      */     }
/*      */     
/*  492 */     float $$2 = (float)(this.tickCount - this.interpolationStartClientTick);
/*  493 */     float $$3 = $$2 + $$0;
/*  494 */     float $$4 = Mth.clamp(Mth.inverseLerp($$3, 0.0F, $$1), 0.0F, 1.0F);
/*  495 */     this.lastProgress = $$4;
/*  496 */     return $$4;
/*      */   }
/*      */   
/*      */   private float getHeight() {
/*  500 */     return ((Float)this.entityData.get(DATA_HEIGHT_ID)).floatValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPos(double $$0, double $$1, double $$2) {
/*  505 */     super.setPos($$0, $$1, $$2);
/*  506 */     updateCulling();
/*      */   }
/*      */   
/*      */   private void updateCulling() {
/*  510 */     float $$0 = getWidth();
/*  511 */     float $$1 = getHeight();
/*      */     
/*  513 */     if ($$0 == 0.0F || $$1 == 0.0F) {
/*  514 */       this.noCulling = true;
/*      */     } else {
/*  516 */       this.noCulling = false;
/*  517 */       float $$2 = $$0 / 2.0F;
/*  518 */       double $$3 = getX();
/*  519 */       double $$4 = getY();
/*  520 */       double $$5 = getZ();
/*  521 */       this.cullingBoundingBox = new AABB($$3 - $$2, $$4, $$5 - $$2, $$3 + $$2, $$4 + $$1, $$5 + $$2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean shouldRenderAtSqrDistance(double $$0) {
/*  527 */     return ($$0 < Mth.square(getViewRange() * 64.0D * getViewScale()));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTeamColor() {
/*  532 */     int $$0 = getGlowColorOverride();
/*  533 */     return ($$0 != -1) ? $$0 : super.getTeamColor();
/*      */   }
/*      */   
/*      */   private RenderState createFreshRenderState() {
/*  537 */     return new RenderState(
/*  538 */         GenericInterpolator.constant(createTransformation(this.entityData)), 
/*  539 */         getBillboardConstraints(), 
/*  540 */         getPackedBrightnessOverride(), 
/*  541 */         FloatInterpolator.constant(getShadowRadius()), 
/*  542 */         FloatInterpolator.constant(getShadowStrength()), 
/*  543 */         getGlowColorOverride());
/*      */   }
/*      */ 
/*      */   
/*      */   private RenderState createInterpolatedRenderState(RenderState $$0, float $$1) {
/*  548 */     Transformation $$2 = $$0.transformation.get($$1);
/*  549 */     float $$3 = $$0.shadowRadius.get($$1);
/*  550 */     float $$4 = $$0.shadowStrength.get($$1);
/*      */     
/*  552 */     return new RenderState(new TransformationInterpolator($$2, 
/*  553 */           createTransformation(this.entityData)), 
/*  554 */         getBillboardConstraints(), 
/*  555 */         getPackedBrightnessOverride(), new LinearFloatInterpolator($$3, 
/*  556 */           getShadowRadius()), new LinearFloatInterpolator($$4, 
/*  557 */           getShadowStrength()), 
/*  558 */         getGlowColorOverride());
/*      */   }
/*      */   protected abstract void updateRenderSubState(boolean paramBoolean, float paramFloat);
/*      */   public static final class RenderState extends Record { final Display.GenericInterpolator<Transformation> transformation; private final Display.BillboardConstraints billboardConstraints; private final int brightnessOverride; final Display.FloatInterpolator shadowRadius; final Display.FloatInterpolator shadowStrength; private final int glowColorOverride;
/*  562 */     public RenderState(Display.GenericInterpolator<Transformation> $$0, Display.BillboardConstraints $$1, int $$2, Display.FloatInterpolator $$3, Display.FloatInterpolator $$4, int $$5) { this.transformation = $$0; this.billboardConstraints = $$1; this.brightnessOverride = $$2; this.shadowRadius = $$3; this.shadowStrength = $$4; this.glowColorOverride = $$5; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$RenderState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #562	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$RenderState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$RenderState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #562	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$RenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$RenderState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #562	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$RenderState;
/*  562 */       //   0	8	1	$$0	Ljava/lang/Object; } public Display.GenericInterpolator<Transformation> transformation() { return this.transformation; } public Display.BillboardConstraints billboardConstraints() { return this.billboardConstraints; } public int brightnessOverride() { return this.brightnessOverride; } public Display.FloatInterpolator shadowRadius() { return this.shadowRadius; } public Display.FloatInterpolator shadowStrength() { return this.shadowStrength; } public int glowColorOverride() { return this.glowColorOverride; }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static class ItemDisplay
/*      */     extends Display
/*      */   {
/*      */     private static final String TAG_ITEM = "item";
/*      */ 
/*      */     
/*      */     private static final String TAG_ITEM_DISPLAY = "item_display";
/*      */     
/*  576 */     private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK_ID = SynchedEntityData.defineId(ItemDisplay.class, EntityDataSerializers.ITEM_STACK);
/*      */     
/*  578 */     private static final EntityDataAccessor<Byte> DATA_ITEM_DISPLAY_ID = SynchedEntityData.defineId(ItemDisplay.class, EntityDataSerializers.BYTE);
/*      */     
/*  580 */     private final SlotAccess slot = new SlotAccess()
/*      */       {
/*      */         public ItemStack get() {
/*  583 */           return Display.ItemDisplay.this.getItemStack();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean set(ItemStack $$0) {
/*  588 */           Display.ItemDisplay.this.setItemStack($$0);
/*  589 */           return true;
/*      */         }
/*      */       };
/*      */     
/*      */     @Nullable
/*      */     private ItemRenderState itemRenderState;
/*      */     
/*      */     public ItemDisplay(EntityType<?> $$0, Level $$1) {
/*  597 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void defineSynchedData() {
/*  602 */       super.defineSynchedData();
/*  603 */       this.entityData.define(DATA_ITEM_STACK_ID, ItemStack.EMPTY);
/*  604 */       this.entityData.define(DATA_ITEM_DISPLAY_ID, Byte.valueOf(ItemDisplayContext.NONE.getId()));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  609 */       super.onSyncedDataUpdated($$0);
/*      */       
/*  611 */       if (DATA_ITEM_STACK_ID.equals($$0) || DATA_ITEM_DISPLAY_ID.equals($$0)) {
/*  612 */         this.updateRenderState = true;
/*      */       }
/*      */     }
/*      */     
/*      */     ItemStack getItemStack() {
/*  617 */       return (ItemStack)this.entityData.get(DATA_ITEM_STACK_ID);
/*      */     }
/*      */     
/*      */     void setItemStack(ItemStack $$0) {
/*  621 */       this.entityData.set(DATA_ITEM_STACK_ID, $$0);
/*      */     }
/*      */     
/*      */     private void setItemTransform(ItemDisplayContext $$0) {
/*  625 */       this.entityData.set(DATA_ITEM_DISPLAY_ID, Byte.valueOf($$0.getId()));
/*      */     }
/*      */     
/*      */     private ItemDisplayContext getItemTransform() {
/*  629 */       return ItemDisplayContext.BY_ID.apply(((Byte)this.entityData.get(DATA_ITEM_DISPLAY_ID)).byteValue());
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readAdditionalSaveData(CompoundTag $$0) {
/*  634 */       super.readAdditionalSaveData($$0);
/*  635 */       setItemStack(ItemStack.of($$0.getCompound("item")));
/*  636 */       if ($$0.contains("item_display", 8)) {
/*  637 */         Objects.requireNonNull(Display.LOGGER); ItemDisplayContext.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("item_display")).resultOrPartial(Util.prefix("Display entity", Display.LOGGER::error)).ifPresent($$0 -> setItemTransform((ItemDisplayContext)$$0.getFirst()));
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(CompoundTag $$0) {
/*  643 */       super.addAdditionalSaveData($$0);
/*  644 */       $$0.put("item", (Tag)getItemStack().save(new CompoundTag()));
/*  645 */       ItemDisplayContext.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getItemTransform()).result().ifPresent($$1 -> $$0.put("item_display", $$1));
/*      */     }
/*      */ 
/*      */     
/*      */     public SlotAccess getSlot(int $$0) {
/*  650 */       if ($$0 == 0) {
/*  651 */         return this.slot;
/*      */       }
/*  653 */       return SlotAccess.NULL;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public ItemRenderState itemRenderState() {
/*  658 */       return this.itemRenderState;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void updateRenderSubState(boolean $$0, float $$1) {
/*  663 */       ItemStack $$2 = getItemStack();
/*  664 */       $$2.setEntityRepresentation(this);
/*  665 */       this.itemRenderState = new ItemRenderState($$2, getItemTransform());
/*      */     }
/*      */     public static final class ItemRenderState extends Record { private final ItemStack itemStack; private final ItemDisplayContext itemTransform;
/*  668 */       public ItemRenderState(ItemStack $$0, ItemDisplayContext $$1) { this.itemStack = $$0; this.itemTransform = $$1; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #668	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #668	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #668	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;
/*  668 */         //   0	8	1	$$0	Ljava/lang/Object; } public ItemStack itemStack() { return this.itemStack; } public ItemDisplayContext itemTransform() { return this.itemTransform; } } } class null implements SlotAccess { public ItemStack get() { return Display.ItemDisplay.this.getItemStack(); } public boolean set(ItemStack $$0) { Display.ItemDisplay.this.setItemStack($$0); return true; } } public static final class ItemRenderState extends Record { private final ItemStack itemStack; private final ItemDisplayContext itemTransform; public ItemRenderState(ItemStack $$0, ItemDisplayContext $$1) { this.itemStack = $$0; this.itemTransform = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #668	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #668	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #668	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$ItemDisplay$ItemRenderState;
/*  668 */       //   0	8	1	$$0	Ljava/lang/Object; } public ItemStack itemStack() { return this.itemStack; } public ItemDisplayContext itemTransform() { return this.itemTransform; }
/*      */      }
/*      */ 
/*      */ 
/*      */   
/*      */   public static class BlockDisplay
/*      */     extends Display
/*      */   {
/*      */     public static final String TAG_BLOCK_STATE = "block_state";
/*      */     
/*  678 */     private static final EntityDataAccessor<BlockState> DATA_BLOCK_STATE_ID = SynchedEntityData.defineId(BlockDisplay.class, EntityDataSerializers.BLOCK_STATE);
/*      */     
/*      */     @Nullable
/*      */     private BlockRenderState blockRenderState;
/*      */     
/*      */     public BlockDisplay(EntityType<?> $$0, Level $$1) {
/*  684 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void defineSynchedData() {
/*  689 */       super.defineSynchedData();
/*  690 */       this.entityData.define(DATA_BLOCK_STATE_ID, Blocks.AIR.defaultBlockState());
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  695 */       super.onSyncedDataUpdated($$0);
/*      */       
/*  697 */       if ($$0.equals(DATA_BLOCK_STATE_ID)) {
/*  698 */         this.updateRenderState = true;
/*      */       }
/*      */     }
/*      */     
/*      */     private BlockState getBlockState() {
/*  703 */       return (BlockState)this.entityData.get(DATA_BLOCK_STATE_ID);
/*      */     }
/*      */     
/*      */     private void setBlockState(BlockState $$0) {
/*  707 */       this.entityData.set(DATA_BLOCK_STATE_ID, $$0);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readAdditionalSaveData(CompoundTag $$0) {
/*  712 */       super.readAdditionalSaveData($$0);
/*  713 */       setBlockState(NbtUtils.readBlockState((HolderGetter)level().holderLookup(Registries.BLOCK), $$0.getCompound("block_state")));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(CompoundTag $$0) {
/*  718 */       super.addAdditionalSaveData($$0);
/*  719 */       $$0.put("block_state", (Tag)NbtUtils.writeBlockState(getBlockState()));
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public BlockRenderState blockRenderState() {
/*  724 */       return this.blockRenderState;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void updateRenderSubState(boolean $$0, float $$1) {
/*  729 */       this.blockRenderState = new BlockRenderState(getBlockState());
/*      */     }
/*      */     public static final class BlockRenderState extends Record { private final BlockState blockState;
/*  732 */       public BlockRenderState(BlockState $$0) { this.blockState = $$0; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #732	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #732	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #732	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;
/*  732 */         //   0	8	1	$$0	Ljava/lang/Object; } public BlockState blockState() { return this.blockState; } } } public static final class BlockRenderState extends Record { private final BlockState blockState; public BlockRenderState(BlockState $$0) { this.blockState = $$0; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #732	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #732	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #732	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$BlockDisplay$BlockRenderState;
/*  732 */       //   0	8	1	$$0	Ljava/lang/Object; } public BlockState blockState() { return this.blockState; }
/*      */      }
/*      */   public static class TextDisplay extends Display { public static final String TAG_TEXT = "text"; private static final String TAG_LINE_WIDTH = "line_width"; private static final String TAG_TEXT_OPACITY = "text_opacity"; private static final String TAG_BACKGROUND_COLOR = "background"; private static final String TAG_SHADOW = "shadow"; private static final String TAG_SEE_THROUGH = "see_through"; private static final String TAG_USE_DEFAULT_BACKGROUND = "default_background"; private static final String TAG_ALIGNMENT = "alignment"; public static final byte FLAG_SHADOW = 1; public static final byte FLAG_SEE_THROUGH = 2; public static final byte FLAG_USE_DEFAULT_BACKGROUND = 4;
/*      */     public static final byte FLAG_ALIGN_LEFT = 8;
/*      */     public static final byte FLAG_ALIGN_RIGHT = 16;
/*      */     private static final byte INITIAL_TEXT_OPACITY = -1;
/*      */     public static final int INITIAL_BACKGROUND = 1073741824;
/*      */     
/*  740 */     public enum Align implements StringRepresentable { CENTER("center"),
/*  741 */       LEFT("left"),
/*  742 */       RIGHT("right");
/*      */       
/*  744 */       public static final Codec<Align> CODEC = (Codec<Align>)StringRepresentable.fromEnum(Align::values); private final String name;
/*      */       static {
/*      */       
/*      */       }
/*      */       Align(String $$0) {
/*  749 */         this.name = $$0;
/*      */       }
/*      */ 
/*      */       
/*      */       public String getSerializedName() {
/*  754 */         return this.name;
/*      */       } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  776 */     private static final EntityDataAccessor<Component> DATA_TEXT_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.COMPONENT);
/*  777 */     private static final EntityDataAccessor<Integer> DATA_LINE_WIDTH_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
/*  778 */     private static final EntityDataAccessor<Integer> DATA_BACKGROUND_COLOR_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
/*  779 */     private static final EntityDataAccessor<Byte> DATA_TEXT_OPACITY_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
/*  780 */     private static final EntityDataAccessor<Byte> DATA_STYLE_FLAGS_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
/*      */     
/*  782 */     private static final IntSet TEXT_RENDER_STATE_IDS = IntSet.of(new int[] { DATA_TEXT_ID
/*  783 */           .getId(), DATA_LINE_WIDTH_ID
/*  784 */           .getId(), DATA_BACKGROUND_COLOR_ID
/*  785 */           .getId(), DATA_TEXT_OPACITY_ID
/*  786 */           .getId(), DATA_STYLE_FLAGS_ID
/*  787 */           .getId() });
/*      */     
/*      */     @Nullable
/*      */     private CachedInfo clientDisplayCache;
/*      */     
/*      */     @Nullable
/*      */     private TextRenderState textRenderState;
/*      */ 
/*      */     
/*      */     public TextDisplay(EntityType<?> $$0, Level $$1) {
/*  797 */       super($$0, $$1);
/*      */     }
/*      */ 
/*      */     
/*      */     protected void defineSynchedData() {
/*  802 */       super.defineSynchedData();
/*  803 */       this.entityData.define(DATA_TEXT_ID, Component.empty());
/*  804 */       this.entityData.define(DATA_LINE_WIDTH_ID, Integer.valueOf(200));
/*  805 */       this.entityData.define(DATA_BACKGROUND_COLOR_ID, Integer.valueOf(1073741824));
/*  806 */       this.entityData.define(DATA_TEXT_OPACITY_ID, Byte.valueOf((byte)-1));
/*  807 */       this.entityData.define(DATA_STYLE_FLAGS_ID, Byte.valueOf((byte)0));
/*      */     }
/*      */ 
/*      */     
/*      */     public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  812 */       super.onSyncedDataUpdated($$0);
/*      */       
/*  814 */       if (TEXT_RENDER_STATE_IDS.contains($$0.getId())) {
/*  815 */         this.updateRenderState = true;
/*      */       }
/*      */     }
/*      */     
/*      */     private Component getText() {
/*  820 */       return (Component)this.entityData.get(DATA_TEXT_ID);
/*      */     }
/*      */     
/*      */     private void setText(Component $$0) {
/*  824 */       this.entityData.set(DATA_TEXT_ID, $$0);
/*      */     }
/*      */     
/*      */     private int getLineWidth() {
/*  828 */       return ((Integer)this.entityData.get(DATA_LINE_WIDTH_ID)).intValue();
/*      */     }
/*      */     
/*      */     private void setLineWidth(int $$0) {
/*  832 */       this.entityData.set(DATA_LINE_WIDTH_ID, Integer.valueOf($$0));
/*      */     }
/*      */     
/*      */     private byte getTextOpacity() {
/*  836 */       return ((Byte)this.entityData.get(DATA_TEXT_OPACITY_ID)).byteValue();
/*      */     }
/*      */     
/*      */     private void setTextOpacity(byte $$0) {
/*  840 */       this.entityData.set(DATA_TEXT_OPACITY_ID, Byte.valueOf($$0));
/*      */     }
/*      */     
/*      */     private int getBackgroundColor() {
/*  844 */       return ((Integer)this.entityData.get(DATA_BACKGROUND_COLOR_ID)).intValue();
/*      */     }
/*      */     
/*      */     private void setBackgroundColor(int $$0) {
/*  848 */       this.entityData.set(DATA_BACKGROUND_COLOR_ID, Integer.valueOf($$0));
/*      */     }
/*      */     
/*      */     private byte getFlags() {
/*  852 */       return ((Byte)this.entityData.get(DATA_STYLE_FLAGS_ID)).byteValue();
/*      */     }
/*      */     
/*      */     private void setFlags(byte $$0) {
/*  856 */       this.entityData.set(DATA_STYLE_FLAGS_ID, Byte.valueOf($$0));
/*      */     }
/*      */     
/*      */     private static byte loadFlag(byte $$0, CompoundTag $$1, String $$2, byte $$3) {
/*  860 */       if ($$1.getBoolean($$2)) {
/*  861 */         return (byte)($$0 | $$3);
/*      */       }
/*  863 */       return $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     protected void readAdditionalSaveData(CompoundTag $$0) {
/*  868 */       super.readAdditionalSaveData($$0);
/*      */       
/*  870 */       if ($$0.contains("line_width", 99)) {
/*  871 */         setLineWidth($$0.getInt("line_width"));
/*      */       }
/*      */       
/*  874 */       if ($$0.contains("text_opacity", 99)) {
/*  875 */         setTextOpacity($$0.getByte("text_opacity"));
/*      */       }
/*      */       
/*  878 */       if ($$0.contains("background", 99)) {
/*  879 */         setBackgroundColor($$0.getInt("background"));
/*      */       }
/*      */ 
/*      */       
/*  883 */       byte $$1 = loadFlag((byte)0, $$0, "shadow", (byte)1);
/*  884 */       $$1 = loadFlag($$1, $$0, "see_through", (byte)2);
/*  885 */       $$1 = loadFlag($$1, $$0, "default_background", (byte)4);
/*      */       
/*  887 */       Objects.requireNonNull(Display.LOGGER); Optional<Align> $$2 = Align.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("alignment")).resultOrPartial(Util.prefix("Display entity", Display.LOGGER::error)).map(Pair::getFirst);
/*  888 */       if ($$2.isPresent()) {
/*  889 */         switch ((Align)$$2.get()) { default: throw new IncompatibleClassChangeError();
/*      */           case CENTER: 
/*      */           case LEFT: 
/*  892 */           case RIGHT: break; }  $$1 = (byte)($$1 | 0x10);
/*      */       } 
/*      */ 
/*      */       
/*  896 */       setFlags($$1);
/*      */       
/*  898 */       if ($$0.contains("text", 8)) {
/*  899 */         String $$3 = $$0.getString("text");
/*      */         try {
/*  901 */           MutableComponent mutableComponent = Component.Serializer.fromJson($$3);
/*  902 */           if (mutableComponent != null) {
/*  903 */             CommandSourceStack $$5 = createCommandSourceStack().withPermission(2);
/*  904 */             MutableComponent mutableComponent1 = ComponentUtils.updateForEntity($$5, (Component)mutableComponent, this, 0);
/*  905 */             setText((Component)mutableComponent1);
/*      */           } else {
/*  907 */             setText((Component)Component.empty());
/*      */           } 
/*  909 */         } catch (Exception $$7) {
/*  910 */           Display.LOGGER.warn("Failed to parse display entity text {}", $$3, $$7);
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*      */     private static void storeFlag(byte $$0, CompoundTag $$1, String $$2, byte $$3) {
/*  916 */       $$1.putBoolean($$2, (($$0 & $$3) != 0));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void addAdditionalSaveData(CompoundTag $$0) {
/*  921 */       super.addAdditionalSaveData($$0);
/*  922 */       $$0.putString("text", Component.Serializer.toJson(getText()));
/*  923 */       $$0.putInt("line_width", getLineWidth());
/*  924 */       $$0.putInt("background", getBackgroundColor());
/*  925 */       $$0.putByte("text_opacity", getTextOpacity());
/*      */       
/*  927 */       byte $$1 = getFlags();
/*  928 */       storeFlag($$1, $$0, "shadow", (byte)1);
/*  929 */       storeFlag($$1, $$0, "see_through", (byte)2);
/*  930 */       storeFlag($$1, $$0, "default_background", (byte)4);
/*  931 */       Align.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getAlign($$1)).result().ifPresent($$1 -> $$0.put("alignment", $$1));
/*      */     }
/*      */ 
/*      */     
/*      */     protected void updateRenderSubState(boolean $$0, float $$1) {
/*  936 */       if ($$0 && this.textRenderState != null) {
/*  937 */         this.textRenderState = createInterpolatedTextRenderState(this.textRenderState, $$1);
/*      */       } else {
/*  939 */         this.textRenderState = createFreshTextRenderState();
/*      */       } 
/*  941 */       this.clientDisplayCache = null;
/*      */     }
/*      */     
/*      */     @Nullable
/*      */     public TextRenderState textRenderState() {
/*  946 */       return this.textRenderState;
/*      */     }
/*      */     
/*      */     private TextRenderState createFreshTextRenderState() {
/*  950 */       return new TextRenderState(
/*  951 */           getText(), 
/*  952 */           getLineWidth(), 
/*  953 */           Display.IntInterpolator.constant(getTextOpacity()), 
/*  954 */           Display.IntInterpolator.constant(getBackgroundColor()), 
/*  955 */           getFlags());
/*      */     }
/*      */ 
/*      */     
/*      */     private TextRenderState createInterpolatedTextRenderState(TextRenderState $$0, float $$1) {
/*  960 */       int $$2 = $$0.backgroundColor.get($$1);
/*  961 */       int $$3 = $$0.textOpacity.get($$1);
/*      */       
/*  963 */       return new TextRenderState(
/*  964 */           getText(), 
/*  965 */           getLineWidth(), new Display.LinearIntInterpolator($$3, 
/*  966 */             getTextOpacity()), new Display.ColorInterpolator($$2, 
/*  967 */             getBackgroundColor()), 
/*  968 */           getFlags());
/*      */     }
/*      */     public static final class CachedLine extends Record { private final FormattedCharSequence contents; private final int width;
/*      */       
/*  972 */       public CachedLine(FormattedCharSequence $$0, int $$1) { this.contents = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #972	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #972	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #972	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;
/*  972 */         //   0	8	1	$$0	Ljava/lang/Object; } public FormattedCharSequence contents() { return this.contents; } public int width() { return this.width; }
/*      */        } public static final class CachedInfo extends Record { private final List<Display.TextDisplay.CachedLine> lines; private final int width;
/*  974 */       public CachedInfo(List<Display.TextDisplay.CachedLine> $$0, int $$1) { this.lines = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #974	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #974	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #974	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;
/*  974 */         //   0	8	1	$$0	Ljava/lang/Object; } public List<Display.TextDisplay.CachedLine> lines() { return this.lines; } public int width() { return this.width; }
/*      */        }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public CachedInfo cacheDisplay(LineSplitter $$0) {
/*  982 */       if (this.clientDisplayCache == null) {
/*  983 */         if (this.textRenderState != null) {
/*  984 */           this.clientDisplayCache = $$0.split(this.textRenderState.text(), this.textRenderState.lineWidth());
/*      */         } else {
/*  986 */           this.clientDisplayCache = new CachedInfo(List.of(), 0);
/*      */         } 
/*      */       }
/*      */       
/*  990 */       return this.clientDisplayCache;
/*      */     }
/*      */     
/*      */     public static Align getAlign(byte $$0) {
/*  994 */       if (($$0 & 0x8) != 0) {
/*  995 */         return Align.LEFT;
/*      */       }
/*  997 */       if (($$0 & 0x10) != 0) {
/*  998 */         return Align.RIGHT;
/*      */       }
/* 1000 */       return Align.CENTER;
/*      */     }
/*      */     public static final class TextRenderState extends Record { private final Component text; private final int lineWidth; final Display.IntInterpolator textOpacity; final Display.IntInterpolator backgroundColor; private final byte flags;
/* 1003 */       public TextRenderState(Component $$0, int $$1, Display.IntInterpolator $$2, Display.IntInterpolator $$3, byte $$4) { this.text = $$0; this.lineWidth = $$1; this.textOpacity = $$2; this.backgroundColor = $$3; this.flags = $$4; } public final String toString() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)Ljava/lang/String;
/*      */         //   6: areturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1003	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final int hashCode() { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)I
/*      */         //   6: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1003	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */         //   0: aload_0
/*      */         //   1: aload_1
/*      */         //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;Ljava/lang/Object;)Z
/*      */         //   7: ireturn
/*      */         // Line number table:
/*      */         //   Java source line number -> byte code offset
/*      */         //   #1003	-> 0
/*      */         // Local variable table:
/*      */         //   start	length	slot	name	descriptor
/*      */         //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;
/* 1003 */         //   0	8	1	$$0	Ljava/lang/Object; } public Component text() { return this.text; } public int lineWidth() { return this.lineWidth; } public Display.IntInterpolator textOpacity() { return this.textOpacity; } public Display.IntInterpolator backgroundColor() { return this.backgroundColor; } public byte flags() { return this.flags; } } @FunctionalInterface public static interface LineSplitter { Display.TextDisplay.CachedInfo split(Component param2Component, int param2Int); } } public enum Align implements StringRepresentable { CENTER("center"), LEFT("left"), RIGHT("right"); public static final Codec<Align> CODEC = (Codec<Align>)StringRepresentable.fromEnum(Align::values); private final String name; static {  } Align(String $$0) { this.name = $$0; } public String getSerializedName() { return this.name; } } public static final class CachedLine extends Record { private final FormattedCharSequence contents; private final int width; public CachedLine(FormattedCharSequence $$0, int $$1) { this.contents = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;
/*      */       //   0	8	1	$$0	Ljava/lang/Object; } public FormattedCharSequence contents() { return this.contents; } public int width() { return this.width; } } public static final class CachedInfo extends Record { private final List<Display.TextDisplay.CachedLine> lines; private final int width; public CachedInfo(List<Display.TextDisplay.CachedLine> $$0, int $$1) { this.lines = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;
/* 1003 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<Display.TextDisplay.CachedLine> lines() { return this.lines; } public int width() { return this.width; } } public static final class TextRenderState extends Record { private final Component text; private final int lineWidth; public TextRenderState(Component $$0, int $$1, Display.IntInterpolator $$2, Display.IntInterpolator $$3, byte $$4) { this.text = $$0; this.lineWidth = $$1; this.textOpacity = $$2; this.backgroundColor = $$3; this.flags = $$4; } final Display.IntInterpolator textOpacity; final Display.IntInterpolator backgroundColor; private final byte flags; public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;
/* 1003 */       //   0	8	1	$$0	Ljava/lang/Object; } public Component text() { return this.text; } public int lineWidth() { return this.lineWidth; } public Display.IntInterpolator textOpacity() { return this.textOpacity; } public Display.IntInterpolator backgroundColor() { return this.backgroundColor; } public byte flags() { return this.flags; }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface GenericInterpolator<T>
/*      */   {
/*      */     static <T> GenericInterpolator<T> constant(T $$0) {
/* 1017 */       return $$1 -> $$0;
/*      */     }
/*      */     T get(float param1Float); }
/*      */   private static final class TransformationInterpolator extends Record implements GenericInterpolator<Transformation> { private final Transformation previous;
/*      */     private final Transformation current;
/*      */     
/* 1023 */     TransformationInterpolator(Transformation $$0, Transformation $$1) { this.previous = $$0; this.current = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TransformationInterpolator;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1023	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TransformationInterpolator; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TransformationInterpolator;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1023	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TransformationInterpolator; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TransformationInterpolator;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1023	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TransformationInterpolator;
/* 1023 */       //   0	8	1	$$0	Ljava/lang/Object; } public Transformation previous() { return this.previous; } public Transformation current() { return this.current; }
/*      */     
/*      */     public Transformation get(float $$0) {
/* 1026 */       if ($$0 >= 1.0D) {
/* 1027 */         return this.current;
/*      */       }
/* 1029 */       return this.previous.slerp(this.current, $$0);
/*      */     } }
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface IntInterpolator {
/*      */     static IntInterpolator constant(int $$0) {
/* 1036 */       return $$1 -> $$0;
/*      */     }
/*      */     int get(float param1Float); }
/*      */   private static final class LinearIntInterpolator extends Record implements IntInterpolator { private final int previous;
/*      */     private final int current;
/*      */     
/* 1042 */     LinearIntInterpolator(int $$0, int $$1) { this.previous = $$0; this.current = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$LinearIntInterpolator;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1042	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$LinearIntInterpolator; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$LinearIntInterpolator;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1042	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$LinearIntInterpolator; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$LinearIntInterpolator;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1042	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$LinearIntInterpolator;
/* 1042 */       //   0	8	1	$$0	Ljava/lang/Object; } public int previous() { return this.previous; } public int current() { return this.current; }
/*      */     
/*      */     public int get(float $$0) {
/* 1045 */       return Mth.lerpInt($$0, this.previous, this.current);
/*      */     } }
/*      */   private static final class ColorInterpolator extends Record implements IntInterpolator { private final int previous; private final int current;
/*      */     
/* 1049 */     ColorInterpolator(int $$0, int $$1) { this.previous = $$0; this.current = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$ColorInterpolator;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$ColorInterpolator; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$ColorInterpolator;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$ColorInterpolator; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$ColorInterpolator;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1049	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$ColorInterpolator;
/* 1049 */       //   0	8	1	$$0	Ljava/lang/Object; } public int previous() { return this.previous; } public int current() { return this.current; }
/*      */     
/*      */     public int get(float $$0) {
/* 1052 */       return FastColor.ARGB32.lerp($$0, this.previous, this.current);
/*      */     } }
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface FloatInterpolator {
/*      */     static FloatInterpolator constant(float $$0) {
/* 1059 */       return $$1 -> $$0;
/*      */     }
/*      */     float get(float param1Float); }
/*      */   private static final class LinearFloatInterpolator extends Record implements FloatInterpolator { private final float previous;
/*      */     private final float current;
/*      */     
/* 1065 */     LinearFloatInterpolator(float $$0, float $$1) { this.previous = $$0; this.current = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1065	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$LinearFloatInterpolator; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1065	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$LinearFloatInterpolator; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1065	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$LinearFloatInterpolator;
/* 1065 */       //   0	8	1	$$0	Ljava/lang/Object; } public float previous() { return this.previous; } public float current() { return this.current; }
/*      */     
/*      */     public float get(float $$0) {
/* 1068 */       return Mth.lerp($$0, this.previous, this.current);
/*      */     } }
/*      */ 
/*      */   
/*      */   private static class PosRotInterpolationTarget {
/*      */     int steps;
/*      */     final double targetX;
/*      */     final double targetY;
/*      */     final double targetZ;
/*      */     final double targetYRot;
/*      */     final double targetXRot;
/*      */     
/*      */     PosRotInterpolationTarget(int $$0, double $$1, double $$2, double $$3, double $$4, double $$5) {
/* 1081 */       this.steps = $$0;
/* 1082 */       this.targetX = $$1;
/* 1083 */       this.targetY = $$2;
/* 1084 */       this.targetZ = $$3;
/* 1085 */       this.targetYRot = $$4;
/* 1086 */       this.targetXRot = $$5;
/*      */     }
/*      */     
/*      */     void applyTargetPosAndRot(Entity $$0) {
/* 1090 */       $$0.setPos(this.targetX, this.targetY, this.targetZ);
/* 1091 */       $$0.setRot((float)this.targetYRot, (float)this.targetXRot);
/*      */     }
/*      */     
/*      */     void applyLerpStep(Entity $$0) {
/* 1095 */       $$0.lerpPositionAndRotationStep(this.steps, this.targetX, this.targetY, this.targetZ, this.targetYRot, this.targetXRot);
/*      */     }
/*      */   }
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface LineSplitter {
/*      */     Display.TextDisplay.CachedInfo split(Component param1Component, int param1Int);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Display.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */