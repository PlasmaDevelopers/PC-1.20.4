/*      */ package net.minecraft.client.player;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.logging.LogUtils;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.stream.Stream;
/*      */ import java.util.stream.StreamSupport;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.client.ClientRecipeBook;
/*      */ import net.minecraft.client.KeyMapping;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.screens.Screen;
/*      */ import net.minecraft.client.gui.screens.inventory.BookEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.MinecartCommandBlockEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.SignEditScreen;
/*      */ import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
/*      */ import net.minecraft.client.multiplayer.ClientLevel;
/*      */ import net.minecraft.client.multiplayer.ClientPacketListener;
/*      */ import net.minecraft.client.resources.sounds.AmbientSoundHandler;
/*      */ import net.minecraft.client.resources.sounds.BiomeAmbientSoundsHandler;
/*      */ import net.minecraft.client.resources.sounds.BubbleColumnAmbientSoundHandler;
/*      */ import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SimpleSoundInstance;
/*      */ import net.minecraft.client.resources.sounds.SoundInstance;
/*      */ import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundHandler;
/*      */ import net.minecraft.client.resources.sounds.UnderwaterAmbientSoundInstances;
/*      */ import net.minecraft.core.BlockPos;
/*      */ import net.minecraft.core.Direction;
/*      */ import net.minecraft.core.Position;
/*      */ import net.minecraft.core.particles.ParticleOptions;
/*      */ import net.minecraft.core.particles.ParticleTypes;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.protocol.Packet;
/*      */ import net.minecraft.network.protocol.game.ServerboundClientCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundContainerClosePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundRecipeBookSeenRecipePacket;
/*      */ import net.minecraft.network.protocol.game.ServerboundSwingPacket;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.sounds.SoundEvent;
/*      */ import net.minecraft.sounds.SoundEvents;
/*      */ import net.minecraft.sounds.SoundSource;
/*      */ import net.minecraft.stats.StatsCounter;
/*      */ import net.minecraft.tags.FluidTags;
/*      */ import net.minecraft.util.Mth;
/*      */ import net.minecraft.world.InteractionHand;
/*      */ import net.minecraft.world.damagesource.DamageSource;
/*      */ import net.minecraft.world.effect.MobEffect;
/*      */ import net.minecraft.world.effect.MobEffectInstance;
/*      */ import net.minecraft.world.effect.MobEffects;
/*      */ import net.minecraft.world.entity.Entity;
/*      */ import net.minecraft.world.entity.EquipmentSlot;
/*      */ import net.minecraft.world.entity.HumanoidArm;
/*      */ import net.minecraft.world.entity.LivingEntity;
/*      */ import net.minecraft.world.entity.MoverType;
/*      */ import net.minecraft.world.entity.PlayerRideableJumping;
/*      */ import net.minecraft.world.entity.Pose;
/*      */ import net.minecraft.world.entity.vehicle.AbstractMinecart;
/*      */ import net.minecraft.world.entity.vehicle.Boat;
/*      */ import net.minecraft.world.inventory.ClickAction;
/*      */ import net.minecraft.world.item.ElytraItem;
/*      */ import net.minecraft.world.item.ItemStack;
/*      */ import net.minecraft.world.item.Items;
/*      */ import net.minecraft.world.item.crafting.RecipeHolder;
/*      */ import net.minecraft.world.item.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.world.level.BaseCommandBlock;
/*      */ import net.minecraft.world.level.BlockGetter;
/*      */ import net.minecraft.world.level.GameType;
/*      */ import net.minecraft.world.level.block.entity.CommandBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.HangingSignBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.JigsawBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.SignBlockEntity;
/*      */ import net.minecraft.world.level.block.entity.StructureBlockEntity;
/*      */ import net.minecraft.world.level.block.state.BlockState;
/*      */ import net.minecraft.world.phys.AABB;
/*      */ import net.minecraft.world.phys.Vec2;
/*      */ import net.minecraft.world.phys.Vec3;
/*      */ import net.minecraft.world.phys.shapes.CollisionContext;
/*      */ import net.minecraft.world.phys.shapes.VoxelShape;
/*      */ import org.slf4j.Logger;
/*      */ 
/*      */ public class LocalPlayer
/*      */   extends AbstractClientPlayer {
/*   95 */   public static final Logger LOGGER = LogUtils.getLogger();
/*      */   
/*      */   private static final int POSITION_REMINDER_INTERVAL = 20;
/*      */   
/*      */   private static final int WATER_VISION_MAX_TIME = 600;
/*      */   
/*      */   private static final int WATER_VISION_QUICK_TIME = 100;
/*      */   private static final float WATER_VISION_QUICK_PERCENT = 0.6F;
/*      */   private static final double SUFFOCATING_COLLISION_CHECK_SCALE = 0.35D;
/*      */   private static final double MINOR_COLLISION_ANGLE_THRESHOLD_RADIAN = 0.13962633907794952D;
/*      */   private static final float DEFAULT_SNEAKING_MOVEMENT_FACTOR = 0.3F;
/*      */   public final ClientPacketListener connection;
/*      */   private final StatsCounter stats;
/*      */   private final ClientRecipeBook recipeBook;
/*  109 */   private final List<AmbientSoundHandler> ambientSoundHandlers = Lists.newArrayList();
/*      */   
/*  111 */   private int permissionLevel = 0;
/*      */   
/*      */   private double xLast;
/*      */   
/*      */   private double yLast1;
/*      */   
/*      */   private double zLast;
/*      */   
/*      */   private float yRotLast;
/*      */   
/*      */   private float xRotLast;
/*      */   
/*      */   private boolean lastOnGround;
/*      */   
/*      */   private boolean crouching;
/*      */   
/*      */   private boolean wasShiftKeyDown;
/*      */   private boolean wasSprinting;
/*      */   private int positionReminder;
/*      */   private boolean flashOnSetHealth;
/*      */   public Input input;
/*      */   protected final Minecraft minecraft;
/*      */   protected int sprintTriggerTime;
/*      */   public float yBob;
/*      */   public float xBob;
/*      */   public float yBobO;
/*      */   public float xBobO;
/*      */   private int jumpRidingTicks;
/*      */   private float jumpRidingScale;
/*      */   public float spinningEffectIntensity;
/*      */   public float oSpinningEffectIntensity;
/*      */   private boolean startedUsingItem;
/*      */   @Nullable
/*      */   private InteractionHand usingItemHand;
/*      */   private boolean handsBusy;
/*      */   private boolean autoJumpEnabled = true;
/*      */   private int autoJumpTime;
/*      */   private boolean wasFallFlying;
/*      */   private int waterVisionTime;
/*      */   private boolean showDeathScreen = true;
/*      */   private boolean doLimitedCrafting = false;
/*      */   
/*      */   public LocalPlayer(Minecraft $$0, ClientLevel $$1, ClientPacketListener $$2, StatsCounter $$3, ClientRecipeBook $$4, boolean $$5, boolean $$6) {
/*  154 */     super($$1, $$2.getLocalGameProfile());
/*  155 */     this.minecraft = $$0;
/*  156 */     this.connection = $$2;
/*  157 */     this.stats = $$3;
/*  158 */     this.recipeBook = $$4;
/*  159 */     this.wasShiftKeyDown = $$5;
/*  160 */     this.wasSprinting = $$6;
/*  161 */     this.ambientSoundHandlers.add(new UnderwaterAmbientSoundHandler(this, $$0.getSoundManager()));
/*  162 */     this.ambientSoundHandlers.add(new BubbleColumnAmbientSoundHandler(this));
/*  163 */     this.ambientSoundHandlers.add(new BiomeAmbientSoundsHandler(this, $$0.getSoundManager(), $$1.getBiomeManager()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hurt(DamageSource $$0, float $$1) {
/*  168 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float $$0) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity $$0, boolean $$1) {
/*  178 */     if (!super.startRiding($$0, $$1)) {
/*  179 */       return false;
/*      */     }
/*      */     
/*  182 */     if ($$0 instanceof AbstractMinecart) {
/*      */ 
/*      */ 
/*      */       
/*  186 */       this.minecraft.getSoundManager().play((SoundInstance)new RidingMinecartSoundInstance(this, (AbstractMinecart)$$0, true));
/*  187 */       this.minecraft.getSoundManager().play((SoundInstance)new RidingMinecartSoundInstance(this, (AbstractMinecart)$$0, false));
/*      */     } 
/*      */     
/*  190 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeVehicle() {
/*  195 */     super.removeVehicle();
/*  196 */     this.handsBusy = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getViewXRot(float $$0) {
/*  201 */     return getXRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getViewYRot(float $$0) {
/*  206 */     if (isPassenger()) {
/*  207 */       return super.getViewYRot($$0);
/*      */     }
/*  209 */     return getYRot();
/*      */   }
/*      */ 
/*      */   
/*      */   public void tick() {
/*  214 */     if (!level().hasChunkAt(getBlockX(), getBlockZ())) {
/*      */       return;
/*      */     }
/*      */     
/*  218 */     super.tick();
/*      */     
/*  220 */     if (isPassenger()) {
/*  221 */       this.connection.send((Packet)new ServerboundMovePlayerPacket.Rot(getYRot(), getXRot(), onGround()));
/*  222 */       this.connection.send((Packet)new ServerboundPlayerInputPacket(this.xxa, this.zza, this.input.jumping, this.input.shiftKeyDown));
/*  223 */       Entity $$0 = getRootVehicle();
/*  224 */       if ($$0 != this && $$0.isControlledByLocalInstance()) {
/*  225 */         this.connection.send((Packet)new ServerboundMoveVehiclePacket($$0));
/*  226 */         sendIsSprintingIfNeeded();
/*      */       } 
/*      */     } else {
/*  229 */       sendPosition();
/*      */     } 
/*      */     
/*  232 */     for (AmbientSoundHandler $$1 : this.ambientSoundHandlers) {
/*  233 */       $$1.tick();
/*      */     }
/*      */   }
/*      */   
/*      */   public float getCurrentMood() {
/*  238 */     for (AmbientSoundHandler $$0 : this.ambientSoundHandlers) {
/*  239 */       if ($$0 instanceof BiomeAmbientSoundsHandler) {
/*  240 */         return ((BiomeAmbientSoundsHandler)$$0).getMoodiness();
/*      */       }
/*      */     } 
/*  243 */     return 0.0F;
/*      */   }
/*      */   
/*      */   private void sendPosition() {
/*  247 */     sendIsSprintingIfNeeded();
/*      */     
/*  249 */     boolean $$0 = isShiftKeyDown();
/*  250 */     if ($$0 != this.wasShiftKeyDown) {
/*  251 */       ServerboundPlayerCommandPacket.Action $$1 = $$0 ? ServerboundPlayerCommandPacket.Action.PRESS_SHIFT_KEY : ServerboundPlayerCommandPacket.Action.RELEASE_SHIFT_KEY;
/*  252 */       this.connection.send((Packet)new ServerboundPlayerCommandPacket((Entity)this, $$1));
/*  253 */       this.wasShiftKeyDown = $$0;
/*      */     } 
/*      */     
/*  256 */     if (isControlledCamera()) {
/*  257 */       double $$2 = getX() - this.xLast;
/*  258 */       double $$3 = getY() - this.yLast1;
/*  259 */       double $$4 = getZ() - this.zLast;
/*      */       
/*  261 */       double $$5 = (getYRot() - this.yRotLast);
/*  262 */       double $$6 = (getXRot() - this.xRotLast);
/*      */       
/*  264 */       this.positionReminder++;
/*      */       
/*  266 */       boolean $$7 = (Mth.lengthSquared($$2, $$3, $$4) > Mth.square(2.0E-4D) || this.positionReminder >= 20);
/*  267 */       boolean $$8 = ($$5 != 0.0D || $$6 != 0.0D);
/*      */       
/*  269 */       if (isPassenger()) {
/*  270 */         Vec3 $$9 = getDeltaMovement();
/*  271 */         this.connection.send((Packet)new ServerboundMovePlayerPacket.PosRot($$9.x, -999.0D, $$9.z, getYRot(), getXRot(), onGround()));
/*  272 */         $$7 = false;
/*      */       }
/*  274 */       else if ($$7 && $$8) {
/*  275 */         this.connection.send((Packet)new ServerboundMovePlayerPacket.PosRot(getX(), getY(), getZ(), getYRot(), getXRot(), onGround()));
/*  276 */       } else if ($$7) {
/*  277 */         this.connection.send((Packet)new ServerboundMovePlayerPacket.Pos(getX(), getY(), getZ(), onGround()));
/*  278 */       } else if ($$8) {
/*  279 */         this.connection.send((Packet)new ServerboundMovePlayerPacket.Rot(getYRot(), getXRot(), onGround()));
/*  280 */       } else if (this.lastOnGround != onGround()) {
/*  281 */         this.connection.send((Packet)new ServerboundMovePlayerPacket.StatusOnly(onGround()));
/*      */       } 
/*      */ 
/*      */       
/*  285 */       if ($$7) {
/*  286 */         this.xLast = getX();
/*  287 */         this.yLast1 = getY();
/*  288 */         this.zLast = getZ();
/*  289 */         this.positionReminder = 0;
/*      */       } 
/*  291 */       if ($$8) {
/*  292 */         this.yRotLast = getYRot();
/*  293 */         this.xRotLast = getXRot();
/*      */       } 
/*  295 */       this.lastOnGround = onGround();
/*      */       
/*  297 */       this.autoJumpEnabled = ((Boolean)this.minecraft.options.autoJump().get()).booleanValue();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void sendIsSprintingIfNeeded() {
/*  302 */     boolean $$0 = isSprinting();
/*  303 */     if ($$0 != this.wasSprinting) {
/*  304 */       ServerboundPlayerCommandPacket.Action $$1 = $$0 ? ServerboundPlayerCommandPacket.Action.START_SPRINTING : ServerboundPlayerCommandPacket.Action.STOP_SPRINTING;
/*  305 */       this.connection.send((Packet)new ServerboundPlayerCommandPacket((Entity)this, $$1));
/*  306 */       this.wasSprinting = $$0;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean drop(boolean $$0) {
/*  311 */     ServerboundPlayerActionPacket.Action $$1 = $$0 ? ServerboundPlayerActionPacket.Action.DROP_ALL_ITEMS : ServerboundPlayerActionPacket.Action.DROP_ITEM;
/*  312 */     ItemStack $$2 = getInventory().removeFromSelected($$0);
/*  313 */     this.connection.send((Packet)new ServerboundPlayerActionPacket($$1, BlockPos.ZERO, Direction.DOWN));
/*  314 */     return !$$2.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public void swing(InteractionHand $$0) {
/*  319 */     super.swing($$0);
/*  320 */     this.connection.send((Packet)new ServerboundSwingPacket($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void respawn() {
/*  325 */     this.connection.send((Packet)new ServerboundClientCommandPacket(ServerboundClientCommandPacket.Action.PERFORM_RESPAWN));
/*  326 */     KeyMapping.resetToggleKeys();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void actuallyHurt(DamageSource $$0, float $$1) {
/*  331 */     if (isInvulnerableTo($$0)) {
/*      */       return;
/*      */     }
/*  334 */     setHealth(getHealth() - $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/*  339 */     this.connection.send((Packet)new ServerboundContainerClosePacket(this.containerMenu.containerId));
/*  340 */     clientSideCloseContainer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void clientSideCloseContainer() {
/*  345 */     super.closeContainer();
/*  346 */     this.minecraft.setScreen(null);
/*      */   }
/*      */   
/*      */   public void hurtTo(float $$0) {
/*  350 */     if (this.flashOnSetHealth) {
/*  351 */       float $$1 = getHealth() - $$0;
/*  352 */       if ($$1 <= 0.0F) {
/*  353 */         setHealth($$0);
/*  354 */         if ($$1 < 0.0F) {
/*  355 */           this.invulnerableTime = 10;
/*      */         }
/*      */       } else {
/*  358 */         this.lastHurt = $$1;
/*  359 */         this.invulnerableTime = 20;
/*  360 */         setHealth($$0);
/*  361 */         this.hurtDuration = 10;
/*  362 */         this.hurtTime = this.hurtDuration;
/*      */       } 
/*      */     } else {
/*  365 */       setHealth($$0);
/*  366 */       this.flashOnSetHealth = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onUpdateAbilities() {
/*  372 */     this.connection.send((Packet)new ServerboundPlayerAbilitiesPacket(getAbilities()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLocalPlayer() {
/*  377 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSuppressingSlidingDownLadder() {
/*  382 */     return (!(getAbilities()).flying && super.isSuppressingSlidingDownLadder());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSpawnSprintParticle() {
/*  387 */     return (!(getAbilities()).flying && super.canSpawnSprintParticle());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSpawnSoulSpeedParticle() {
/*  392 */     return (!(getAbilities()).flying && super.canSpawnSoulSpeedParticle());
/*      */   }
/*      */   
/*      */   protected void sendRidingJump() {
/*  396 */     this.connection.send((Packet)new ServerboundPlayerCommandPacket((Entity)this, ServerboundPlayerCommandPacket.Action.START_RIDING_JUMP, Mth.floor(getJumpRidingScale() * 100.0F)));
/*      */   }
/*      */   
/*      */   public void sendOpenInventory() {
/*  400 */     this.connection.send((Packet)new ServerboundPlayerCommandPacket((Entity)this, ServerboundPlayerCommandPacket.Action.OPEN_INVENTORY));
/*      */   }
/*      */   
/*      */   public StatsCounter getStats() {
/*  404 */     return this.stats;
/*      */   }
/*      */   
/*      */   public ClientRecipeBook getRecipeBook() {
/*  408 */     return this.recipeBook;
/*      */   }
/*      */   
/*      */   public void removeRecipeHighlight(RecipeHolder<?> $$0) {
/*  412 */     if (this.recipeBook.willHighlight($$0)) {
/*  413 */       this.recipeBook.removeHighlight($$0);
/*  414 */       this.connection.send((Packet)new ServerboundRecipeBookSeenRecipePacket($$0));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getPermissionLevel() {
/*  420 */     return this.permissionLevel;
/*      */   }
/*      */   
/*      */   public void setPermissionLevel(int $$0) {
/*  424 */     this.permissionLevel = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayClientMessage(Component $$0, boolean $$1) {
/*  429 */     this.minecraft.getChatListener().handleSystemMessage($$0, $$1);
/*      */   }
/*      */   
/*      */   private void moveTowardsClosestSpace(double $$0, double $$1) {
/*  433 */     BlockPos $$2 = BlockPos.containing($$0, getY(), $$1);
/*      */     
/*  435 */     if (!suffocatesAt($$2)) {
/*      */       return;
/*      */     }
/*      */     
/*  439 */     double $$3 = $$0 - $$2.getX();
/*  440 */     double $$4 = $$1 - $$2.getZ();
/*      */     
/*  442 */     Direction $$5 = null;
/*  443 */     double $$6 = Double.MAX_VALUE;
/*      */     
/*  445 */     Direction[] $$7 = { Direction.WEST, Direction.EAST, Direction.NORTH, Direction.SOUTH };
/*  446 */     for (Direction $$8 : $$7) {
/*  447 */       double $$9 = $$8.getAxis().choose($$3, 0.0D, $$4);
/*  448 */       double $$10 = ($$8.getAxisDirection() == Direction.AxisDirection.POSITIVE) ? (1.0D - $$9) : $$9;
/*  449 */       if ($$10 < $$6 && !suffocatesAt($$2.relative($$8))) {
/*  450 */         $$6 = $$10;
/*  451 */         $$5 = $$8;
/*      */       } 
/*      */     } 
/*      */     
/*  455 */     if ($$5 != null) {
/*      */       
/*  457 */       Vec3 $$11 = getDeltaMovement();
/*  458 */       if ($$5.getAxis() == Direction.Axis.X) {
/*  459 */         setDeltaMovement(0.1D * $$5.getStepX(), $$11.y, $$11.z);
/*      */       } else {
/*  461 */         setDeltaMovement($$11.x, $$11.y, 0.1D * $$5.getStepZ());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean suffocatesAt(BlockPos $$0) {
/*  468 */     AABB $$1 = getBoundingBox();
/*  469 */     AABB $$2 = (new AABB($$0.getX(), $$1.minY, $$0.getZ(), $$0.getX() + 1.0D, $$1.maxY, $$0.getZ() + 1.0D)).deflate(1.0E-7D);
/*  470 */     return level().collidesWithSuffocatingBlock((Entity)this, $$2);
/*      */   }
/*      */   
/*      */   public void setExperienceValues(float $$0, int $$1, int $$2) {
/*  474 */     this.experienceProgress = $$0;
/*  475 */     this.totalExperience = $$1;
/*  476 */     this.experienceLevel = $$2;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendSystemMessage(Component $$0) {
/*  481 */     this.minecraft.gui.getChat().addMessage($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEvent(byte $$0) {
/*  486 */     if ($$0 >= 24 && $$0 <= 28) {
/*  487 */       setPermissionLevel($$0 - 24);
/*      */     } else {
/*  489 */       super.handleEntityEvent($$0);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setShowDeathScreen(boolean $$0) {
/*  494 */     this.showDeathScreen = $$0;
/*      */   }
/*      */   
/*      */   public boolean shouldShowDeathScreen() {
/*  498 */     return this.showDeathScreen;
/*      */   }
/*      */   
/*      */   public void setDoLimitedCrafting(boolean $$0) {
/*  502 */     this.doLimitedCrafting = $$0;
/*      */   }
/*      */   
/*      */   public boolean getDoLimitedCrafting() {
/*  506 */     return this.doLimitedCrafting;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent $$0, float $$1, float $$2) {
/*  511 */     level().playLocalSound(getX(), getY(), getZ(), $$0, getSoundSource(), $$1, $$2, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playNotifySound(SoundEvent $$0, SoundSource $$1, float $$2, float $$3) {
/*  516 */     level().playLocalSound(getX(), getY(), getZ(), $$0, $$1, $$2, $$3, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEffectiveAi() {
/*  521 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startUsingItem(InteractionHand $$0) {
/*  526 */     ItemStack $$1 = getItemInHand($$0);
/*  527 */     if ($$1.isEmpty() || isUsingItem()) {
/*      */       return;
/*      */     }
/*      */     
/*  531 */     super.startUsingItem($$0);
/*      */     
/*  533 */     this.startedUsingItem = true;
/*  534 */     this.usingItemHand = $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUsingItem() {
/*  539 */     return this.startedUsingItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopUsingItem() {
/*  544 */     super.stopUsingItem();
/*  545 */     this.startedUsingItem = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public InteractionHand getUsedItemHand() {
/*  551 */     return Objects.<InteractionHand>requireNonNullElse(this.usingItemHand, InteractionHand.MAIN_HAND);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  556 */     super.onSyncedDataUpdated($$0);
/*      */     
/*  558 */     if (DATA_LIVING_ENTITY_FLAGS.equals($$0)) {
/*  559 */       boolean $$1 = ((((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue() & 0x1) > 0);
/*  560 */       InteractionHand $$2 = ((((Byte)this.entityData.get(DATA_LIVING_ENTITY_FLAGS)).byteValue() & 0x2) > 0) ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
/*      */       
/*  562 */       if ($$1 && !this.startedUsingItem) {
/*  563 */         startUsingItem($$2);
/*  564 */       } else if (!$$1 && this.startedUsingItem) {
/*  565 */         stopUsingItem();
/*      */       } 
/*      */     } 
/*  568 */     if (DATA_SHARED_FLAGS_ID.equals($$0) && 
/*  569 */       isFallFlying() && !this.wasFallFlying) {
/*  570 */       this.minecraft.getSoundManager().play((SoundInstance)new ElytraOnPlayerSoundInstance(this));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PlayerRideableJumping jumpableVehicle() {
/*  577 */     Entity entity = getControlledVehicle(); if (entity instanceof PlayerRideableJumping) { PlayerRideableJumping $$0 = (PlayerRideableJumping)entity; if ($$0.canJump()); }  return null;
/*      */   }
/*      */   
/*      */   public float getJumpRidingScale() {
/*  581 */     return this.jumpRidingScale;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTextFilteringEnabled() {
/*  586 */     return this.minecraft.isTextFilteringEnabled();
/*      */   }
/*      */ 
/*      */   
/*      */   public void openTextEdit(SignBlockEntity $$0, boolean $$1) {
/*  591 */     if ($$0 instanceof HangingSignBlockEntity) { HangingSignBlockEntity $$2 = (HangingSignBlockEntity)$$0;
/*  592 */       this.minecraft.setScreen((Screen)new HangingSignEditScreen((SignBlockEntity)$$2, $$1, this.minecraft.isTextFilteringEnabled())); }
/*      */     else
/*  594 */     { this.minecraft.setScreen((Screen)new SignEditScreen($$0, $$1, this.minecraft.isTextFilteringEnabled())); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   public void openMinecartCommandBlock(BaseCommandBlock $$0) {
/*  600 */     this.minecraft.setScreen((Screen)new MinecartCommandBlockEditScreen($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openCommandBlock(CommandBlockEntity $$0) {
/*  605 */     this.minecraft.setScreen((Screen)new CommandBlockEditScreen($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openStructureBlock(StructureBlockEntity $$0) {
/*  610 */     this.minecraft.setScreen((Screen)new StructureBlockEditScreen($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openJigsawBlock(JigsawBlockEntity $$0) {
/*  615 */     this.minecraft.setScreen((Screen)new JigsawBlockEditScreen($$0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void openItemGui(ItemStack $$0, InteractionHand $$1) {
/*  620 */     if ($$0.is(Items.WRITABLE_BOOK)) {
/*  621 */       this.minecraft.setScreen((Screen)new BookEditScreen(this, $$0, $$1));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void crit(Entity $$0) {
/*  627 */     this.minecraft.particleEngine.createTrackingEmitter($$0, (ParticleOptions)ParticleTypes.CRIT);
/*      */   }
/*      */ 
/*      */   
/*      */   public void magicCrit(Entity $$0) {
/*  632 */     this.minecraft.particleEngine.createTrackingEmitter($$0, (ParticleOptions)ParticleTypes.ENCHANTED_HIT);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isShiftKeyDown() {
/*  637 */     return (this.input != null && this.input.shiftKeyDown);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCrouching() {
/*  642 */     return this.crouching;
/*      */   }
/*      */   
/*      */   public boolean isMovingSlowly() {
/*  646 */     return (isCrouching() || isVisuallyCrawling());
/*      */   }
/*      */ 
/*      */   
/*      */   public void serverAiStep() {
/*  651 */     super.serverAiStep();
/*      */     
/*  653 */     if (isControlledCamera()) {
/*  654 */       this.xxa = this.input.leftImpulse;
/*  655 */       this.zza = this.input.forwardImpulse;
/*  656 */       this.jumping = this.input.jumping;
/*  657 */       this.yBobO = this.yBob;
/*  658 */       this.xBobO = this.xBob;
/*  659 */       this.xBob += (getXRot() - this.xBob) * 0.5F;
/*  660 */       this.yBob += (getYRot() - this.yBob) * 0.5F;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean isControlledCamera() {
/*  665 */     return (this.minecraft.getCameraEntity() == this);
/*      */   }
/*      */   
/*      */   public void resetPos() {
/*  669 */     setPose(Pose.STANDING);
/*  670 */     if (level() != null) {
/*  671 */       double $$0 = getY();
/*  672 */       while ($$0 > level().getMinBuildHeight() && $$0 < level().getMaxBuildHeight()) {
/*  673 */         setPos(getX(), $$0, getZ());
/*  674 */         if (level().noCollision((Entity)this)) {
/*      */           break;
/*      */         }
/*  677 */         $$0++;
/*      */       } 
/*  679 */       setDeltaMovement(Vec3.ZERO);
/*  680 */       setXRot(0.0F);
/*      */     } 
/*      */     
/*  683 */     setHealth(getMaxHealth());
/*  684 */     this.deathTime = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void aiStep() {
/*  689 */     if (this.sprintTriggerTime > 0) {
/*  690 */       this.sprintTriggerTime--;
/*      */     }
/*      */     
/*  693 */     if (!(this.minecraft.screen instanceof net.minecraft.client.gui.screens.ReceivingLevelScreen)) {
/*  694 */       handleNetherPortalClient();
/*      */     }
/*      */     
/*  697 */     boolean $$0 = this.input.jumping;
/*  698 */     boolean $$1 = this.input.shiftKeyDown;
/*  699 */     boolean $$2 = hasEnoughImpulseToStartSprinting();
/*      */     
/*  701 */     this.crouching = (!(getAbilities()).flying && !isSwimming() && !isPassenger() && canPlayerFitWithinBlocksAndEntitiesWhen(Pose.CROUCHING) && (isShiftKeyDown() || (!isSleeping() && !canPlayerFitWithinBlocksAndEntitiesWhen(Pose.STANDING))));
/*      */     
/*  703 */     float $$3 = Mth.clamp(0.3F + EnchantmentHelper.getSneakingSpeedBonus((LivingEntity)this), 0.0F, 1.0F);
/*      */     
/*  705 */     this.input.tick(isMovingSlowly(), $$3);
/*  706 */     this.minecraft.getTutorial().onInput(this.input);
/*  707 */     if (isUsingItem() && !isPassenger()) {
/*  708 */       this.input.leftImpulse *= 0.2F;
/*  709 */       this.input.forwardImpulse *= 0.2F;
/*  710 */       this.sprintTriggerTime = 0;
/*      */     } 
/*  712 */     boolean $$4 = false;
/*  713 */     if (this.autoJumpTime > 0) {
/*  714 */       this.autoJumpTime--;
/*  715 */       $$4 = true;
/*  716 */       this.input.jumping = true;
/*      */     } 
/*      */     
/*  719 */     if (!this.noPhysics) {
/*  720 */       moveTowardsClosestSpace(getX() - getBbWidth() * 0.35D, getZ() + getBbWidth() * 0.35D);
/*  721 */       moveTowardsClosestSpace(getX() - getBbWidth() * 0.35D, getZ() - getBbWidth() * 0.35D);
/*  722 */       moveTowardsClosestSpace(getX() + getBbWidth() * 0.35D, getZ() - getBbWidth() * 0.35D);
/*  723 */       moveTowardsClosestSpace(getX() + getBbWidth() * 0.35D, getZ() + getBbWidth() * 0.35D);
/*      */     } 
/*      */     
/*  726 */     if ($$1) {
/*  727 */       this.sprintTriggerTime = 0;
/*      */     }
/*      */     
/*  730 */     boolean $$5 = canStartSprinting();
/*  731 */     boolean $$6 = isPassenger() ? getVehicle().onGround() : onGround();
/*  732 */     boolean $$7 = (!$$1 && !$$2);
/*      */     
/*  734 */     if (($$6 || isUnderWater()) && $$7 && $$5) {
/*  735 */       if (this.sprintTriggerTime > 0 || this.minecraft.options.keySprint.isDown()) {
/*  736 */         setSprinting(true);
/*      */       } else {
/*  738 */         this.sprintTriggerTime = 7;
/*      */       } 
/*      */     }
/*  741 */     if ((!isInWater() || isUnderWater()) && $$5 && this.minecraft.options.keySprint.isDown()) {
/*  742 */       setSprinting(true);
/*      */     }
/*  744 */     if (isSprinting()) {
/*  745 */       boolean $$8 = (!this.input.hasForwardImpulse() || !hasEnoughFoodToStartSprinting());
/*  746 */       boolean $$9 = ($$8 || (this.horizontalCollision && !this.minorHorizontalCollision) || (isInWater() && !isUnderWater()));
/*  747 */       if (isSwimming()) {
/*  748 */         if ((!onGround() && !this.input.shiftKeyDown && $$8) || !isInWater()) {
/*  749 */           setSprinting(false);
/*      */         }
/*  751 */       } else if ($$9) {
/*  752 */         setSprinting(false);
/*      */       } 
/*      */     } 
/*      */     
/*  756 */     boolean $$10 = false;
/*  757 */     if ((getAbilities()).mayfly) {
/*  758 */       if (this.minecraft.gameMode.isAlwaysFlying()) {
/*  759 */         if (!(getAbilities()).flying) {
/*  760 */           (getAbilities()).flying = true;
/*  761 */           $$10 = true;
/*  762 */           onUpdateAbilities();
/*      */         }
/*      */       
/*  765 */       } else if (!$$0 && this.input.jumping && !$$4) {
/*  766 */         if (this.jumpTriggerTime == 0) {
/*  767 */           this.jumpTriggerTime = 7;
/*  768 */         } else if (!isSwimming()) {
/*  769 */           (getAbilities()).flying = !(getAbilities()).flying;
/*  770 */           $$10 = true;
/*  771 */           onUpdateAbilities();
/*  772 */           this.jumpTriggerTime = 0;
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  778 */     if (this.input.jumping && !$$10 && !$$0 && !(getAbilities()).flying && !isPassenger() && !onClimbable()) {
/*  779 */       ItemStack $$11 = getItemBySlot(EquipmentSlot.CHEST);
/*  780 */       if ($$11.is(Items.ELYTRA) && ElytraItem.isFlyEnabled($$11) && 
/*  781 */         tryToStartFallFlying()) {
/*  782 */         this.connection.send((Packet)new ServerboundPlayerCommandPacket((Entity)this, ServerboundPlayerCommandPacket.Action.START_FALL_FLYING));
/*      */       }
/*      */     } 
/*      */     
/*  786 */     this.wasFallFlying = isFallFlying();
/*      */     
/*  788 */     if (isInWater() && this.input.shiftKeyDown && isAffectedByFluids()) {
/*  789 */       goDownInWater();
/*      */     }
/*      */     
/*  792 */     if (isEyeInFluid(FluidTags.WATER)) {
/*  793 */       int $$12 = isSpectator() ? 10 : 1;
/*  794 */       this.waterVisionTime = Mth.clamp(this.waterVisionTime + $$12, 0, 600);
/*  795 */     } else if (this.waterVisionTime > 0) {
/*  796 */       isEyeInFluid(FluidTags.WATER);
/*  797 */       this.waterVisionTime = Mth.clamp(this.waterVisionTime - 10, 0, 600);
/*      */     } 
/*      */     
/*  800 */     if ((getAbilities()).flying && isControlledCamera()) {
/*      */       
/*  802 */       int $$13 = 0;
/*      */       
/*  804 */       if (this.input.shiftKeyDown) {
/*  805 */         $$13--;
/*      */       }
/*  807 */       if (this.input.jumping) {
/*  808 */         $$13++;
/*      */       }
/*      */       
/*  811 */       if ($$13 != 0) {
/*  812 */         setDeltaMovement(getDeltaMovement().add(0.0D, ($$13 * getAbilities().getFlyingSpeed() * 3.0F), 0.0D));
/*      */       }
/*      */     } 
/*      */     
/*  816 */     PlayerRideableJumping $$14 = jumpableVehicle();
/*  817 */     if ($$14 != null && $$14.getJumpCooldown() == 0) {
/*  818 */       if (this.jumpRidingTicks < 0) {
/*  819 */         this.jumpRidingTicks++;
/*  820 */         if (this.jumpRidingTicks == 0)
/*      */         {
/*  822 */           this.jumpRidingScale = 0.0F;
/*      */         }
/*      */       } 
/*  825 */       if ($$0 && !this.input.jumping) {
/*      */         
/*  827 */         this.jumpRidingTicks = -10;
/*  828 */         $$14.onPlayerJump(Mth.floor(getJumpRidingScale() * 100.0F));
/*  829 */         sendRidingJump();
/*  830 */       } else if (!$$0 && this.input.jumping) {
/*      */         
/*  832 */         this.jumpRidingTicks = 0;
/*  833 */         this.jumpRidingScale = 0.0F;
/*  834 */       } else if ($$0) {
/*      */         
/*  836 */         this.jumpRidingTicks++;
/*  837 */         if (this.jumpRidingTicks < 10) {
/*  838 */           this.jumpRidingScale = this.jumpRidingTicks * 0.1F;
/*      */         } else {
/*  840 */           this.jumpRidingScale = 0.8F + 2.0F / (this.jumpRidingTicks - 9) * 0.1F;
/*      */         } 
/*      */       } 
/*      */     } else {
/*  844 */       this.jumpRidingScale = 0.0F;
/*      */     } 
/*      */     
/*  847 */     super.aiStep();
/*  848 */     if (onGround() && (getAbilities()).flying && !this.minecraft.gameMode.isAlwaysFlying()) {
/*  849 */       (getAbilities()).flying = false;
/*  850 */       onUpdateAbilities();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void tickDeath() {
/*  857 */     this.deathTime++;
/*  858 */     if (this.deathTime == 20) {
/*  859 */       remove(Entity.RemovalReason.KILLED);
/*      */     }
/*      */   }
/*      */   
/*      */   private void handleNetherPortalClient() {
/*  864 */     this.oSpinningEffectIntensity = this.spinningEffectIntensity;
/*  865 */     float $$0 = 0.0F;
/*      */     
/*  867 */     if (this.isInsidePortal) {
/*  868 */       if (this.minecraft.screen != null && !this.minecraft.screen.isPauseScreen() && !(this.minecraft.screen instanceof net.minecraft.client.gui.screens.DeathScreen)) {
/*  869 */         if (this.minecraft.screen instanceof net.minecraft.client.gui.screens.inventory.AbstractContainerScreen) {
/*  870 */           closeContainer();
/*      */         }
/*  872 */         this.minecraft.setScreen(null);
/*      */       } 
/*      */       
/*  875 */       if (this.spinningEffectIntensity == 0.0F) {
/*  876 */         this.minecraft.getSoundManager().play((SoundInstance)SimpleSoundInstance.forLocalAmbience(SoundEvents.PORTAL_TRIGGER, this.random.nextFloat() * 0.4F + 0.8F, 0.25F));
/*      */       }
/*  878 */       $$0 = 0.0125F;
/*  879 */       this.isInsidePortal = false;
/*  880 */     } else if (hasEffect(MobEffects.CONFUSION) && !getEffect(MobEffects.CONFUSION).endsWithin(60)) {
/*  881 */       $$0 = 0.006666667F;
/*      */     }
/*  883 */     else if (this.spinningEffectIntensity > 0.0F) {
/*  884 */       $$0 = -0.05F;
/*      */     } 
/*      */ 
/*      */     
/*  888 */     this.spinningEffectIntensity = Mth.clamp(this.spinningEffectIntensity + $$0, 0.0F, 1.0F);
/*  889 */     processPortalCooldown();
/*      */   }
/*      */ 
/*      */   
/*      */   public void rideTick() {
/*  894 */     super.rideTick();
/*  895 */     this.handsBusy = false;
/*      */     
/*  897 */     Entity entity = getControlledVehicle(); if (entity instanceof Boat) { Boat $$0 = (Boat)entity;
/*  898 */       $$0.setInput(this.input.left, this.input.right, this.input.up, this.input.down);
/*      */       
/*  900 */       this.handsBusy |= (this.input.left || this.input.right || this.input.up || this.input.down) ? 1 : 0; }
/*      */   
/*      */   }
/*      */   
/*      */   public boolean isHandsBusy() {
/*  905 */     return this.handsBusy;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MobEffectInstance removeEffectNoUpdate(@Nullable MobEffect $$0) {
/*  911 */     if ($$0 == MobEffects.CONFUSION) {
/*  912 */       this.oSpinningEffectIntensity = 0.0F;
/*  913 */       this.spinningEffectIntensity = 0.0F;
/*      */     } 
/*      */     
/*  916 */     return super.removeEffectNoUpdate($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void move(MoverType $$0, Vec3 $$1) {
/*  921 */     double $$2 = getX();
/*  922 */     double $$3 = getZ();
/*  923 */     super.move($$0, $$1);
/*  924 */     updateAutoJump((float)(getX() - $$2), (float)(getZ() - $$3));
/*      */   }
/*      */   
/*      */   public boolean isAutoJumpEnabled() {
/*  928 */     return this.autoJumpEnabled;
/*      */   }
/*      */   
/*      */   protected void updateAutoJump(float $$0, float $$1) {
/*  932 */     if (!canAutoJump()) {
/*      */       return;
/*      */     }
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
/*  967 */     Vec3 $$2 = position();
/*  968 */     Vec3 $$3 = $$2.add($$0, 0.0D, $$1);
/*  969 */     Vec3 $$4 = new Vec3($$0, 0.0D, $$1);
/*      */ 
/*      */     
/*  972 */     float $$5 = getSpeed();
/*  973 */     float $$6 = (float)$$4.lengthSqr();
/*  974 */     if ($$6 <= 0.001F) {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  979 */       Vec2 $$7 = this.input.getMoveVector();
/*  980 */       float $$8 = $$5 * $$7.x;
/*  981 */       float $$9 = $$5 * $$7.y;
/*      */ 
/*      */       
/*  984 */       float $$10 = Mth.sin(getYRot() * 0.017453292F);
/*  985 */       float $$11 = Mth.cos(getYRot() * 0.017453292F);
/*  986 */       $$4 = new Vec3(($$8 * $$11 - $$9 * $$10), $$4.y, ($$9 * $$11 + $$8 * $$10));
/*      */       
/*  988 */       $$6 = (float)$$4.lengthSqr();
/*      */ 
/*      */       
/*  991 */       if ($$6 <= 0.001F) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  997 */     float $$12 = Mth.invSqrt($$6);
/*  998 */     Vec3 $$13 = $$4.scale($$12);
/*      */ 
/*      */ 
/*      */     
/* 1002 */     Vec3 $$14 = getForward();
/* 1003 */     float $$15 = (float)($$14.x * $$13.x + $$14.z * $$13.z);
/* 1004 */     if ($$15 < -0.15F) {
/*      */       return;
/*      */     }
/*      */     
/* 1008 */     CollisionContext $$16 = CollisionContext.of((Entity)this);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1013 */     BlockPos $$17 = BlockPos.containing(getX(), (getBoundingBox()).maxY, getZ());
/* 1014 */     BlockState $$18 = level().getBlockState($$17);
/* 1015 */     if (!$$18.getCollisionShape((BlockGetter)level(), $$17, $$16).isEmpty()) {
/*      */       return;
/*      */     }
/*      */     
/* 1019 */     $$17 = $$17.above();
/* 1020 */     BlockState $$19 = level().getBlockState($$17);
/* 1021 */     if (!$$19.getCollisionShape((BlockGetter)level(), $$17, $$16).isEmpty()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1032 */     float $$20 = 7.0F;
/* 1033 */     float $$21 = 1.2F;
/* 1034 */     if (hasEffect(MobEffects.JUMP)) {
/* 1035 */       $$21 += (getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.75F;
/*      */     }
/*      */ 
/*      */     
/* 1039 */     float $$22 = Math.max($$5 * 7.0F, 1.0F / $$12);
/*      */ 
/*      */     
/* 1042 */     Vec3 $$23 = $$2;
/* 1043 */     Vec3 $$24 = $$3.add($$13.scale($$22));
/*      */ 
/*      */ 
/*      */     
/* 1047 */     float $$25 = getBbWidth();
/* 1048 */     float $$26 = getBbHeight();
/* 1049 */     AABB $$27 = (new AABB($$23, $$24.add(0.0D, $$26, 0.0D))).inflate($$25, 0.0D, $$25);
/*      */ 
/*      */ 
/*      */     
/* 1053 */     $$23 = $$23.add(0.0D, 0.5099999904632568D, 0.0D);
/* 1054 */     $$24 = $$24.add(0.0D, 0.5099999904632568D, 0.0D);
/*      */ 
/*      */ 
/*      */     
/* 1058 */     Vec3 $$28 = $$13.cross(new Vec3(0.0D, 1.0D, 0.0D));
/* 1059 */     Vec3 $$29 = $$28.scale(($$25 * 0.5F));
/*      */ 
/*      */     
/* 1062 */     Vec3 $$30 = $$23.subtract($$29);
/* 1063 */     Vec3 $$31 = $$24.subtract($$29);
/* 1064 */     Vec3 $$32 = $$23.add($$29);
/* 1065 */     Vec3 $$33 = $$24.add($$29);
/*      */ 
/*      */     
/* 1068 */     Iterable<VoxelShape> $$34 = level().getCollisions((Entity)this, $$27);
/* 1069 */     Iterator<AABB> $$35 = StreamSupport.stream($$34.spliterator(), false).flatMap($$0 -> $$0.toAabbs().stream()).iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1074 */     float $$36 = Float.MIN_VALUE;
/*      */     
/* 1076 */     while ($$35.hasNext()) {
/* 1077 */       AABB $$37 = $$35.next();
/* 1078 */       if (!$$37.intersects($$30, $$31) && !$$37.intersects($$32, $$33)) {
/*      */         continue;
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1085 */       $$36 = (float)$$37.maxY;
/* 1086 */       Vec3 $$38 = $$37.getCenter();
/* 1087 */       BlockPos $$39 = BlockPos.containing((Position)$$38);
/*      */ 
/*      */       
/* 1090 */       for (int $$40 = 1; $$40 < $$21; $$40++) {
/* 1091 */         BlockPos $$41 = $$39.above($$40);
/* 1092 */         BlockState $$42 = level().getBlockState($$41); VoxelShape $$43;
/* 1093 */         if (!($$43 = $$42.getCollisionShape((BlockGetter)level(), $$41, $$16)).isEmpty()) {
/* 1094 */           $$36 = (float)$$43.max(Direction.Axis.Y) + $$41.getY();
/* 1095 */           if ($$36 - getY() > $$21) {
/*      */             return;
/*      */           }
/*      */         } 
/* 1099 */         if ($$40 > 1) {
/* 1100 */           $$17 = $$17.above();
/* 1101 */           BlockState $$44 = level().getBlockState($$17);
/* 1102 */           if (!$$44.getCollisionShape((BlockGetter)level(), $$17, $$16).isEmpty()) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1113 */     if ($$36 == Float.MIN_VALUE) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1122 */     float $$45 = (float)($$36 - getY());
/* 1123 */     if ($$45 <= 0.5F || $$45 > $$21) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/* 1128 */     this.autoJumpTime = 1;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isHorizontalCollisionMinor(Vec3 $$0) {
/* 1133 */     float $$1 = getYRot() * 0.017453292F;
/* 1134 */     double $$2 = Mth.sin($$1);
/* 1135 */     double $$3 = Mth.cos($$1);
/* 1136 */     double $$4 = this.xxa * $$3 - this.zza * $$2;
/* 1137 */     double $$5 = this.zza * $$3 + this.xxa * $$2;
/* 1138 */     double $$6 = Mth.square($$4) + Mth.square($$5);
/* 1139 */     double $$7 = Mth.square($$0.x) + Mth.square($$0.z);
/* 1140 */     if ($$6 < 9.999999747378752E-6D || $$7 < 9.999999747378752E-6D) {
/* 1141 */       return false;
/*      */     }
/* 1143 */     double $$8 = $$4 * $$0.x + $$5 * $$0.z;
/* 1144 */     double $$9 = Math.acos($$8 / Math.sqrt($$6 * $$7));
/* 1145 */     return ($$9 < 0.13962633907794952D);
/*      */   }
/*      */   
/*      */   private boolean canAutoJump() {
/* 1149 */     return (isAutoJumpEnabled() && this.autoJumpTime <= 0 && 
/*      */       
/* 1151 */       onGround() && 
/* 1152 */       !isStayingOnGroundSurface() && 
/* 1153 */       !isPassenger() && 
/* 1154 */       isMoving() && 
/* 1155 */       getBlockJumpFactor() >= 1.0D);
/*      */   }
/*      */   
/*      */   private boolean isMoving() {
/* 1159 */     Vec2 $$0 = this.input.getMoveVector();
/* 1160 */     return ($$0.x != 0.0F || $$0.y != 0.0F);
/*      */   }
/*      */   
/*      */   private boolean canStartSprinting() {
/* 1164 */     return (!isSprinting() && 
/* 1165 */       hasEnoughImpulseToStartSprinting() && 
/* 1166 */       hasEnoughFoodToStartSprinting() && 
/* 1167 */       !isUsingItem() && 
/* 1168 */       !hasEffect(MobEffects.BLINDNESS) && (
/* 1169 */       !isPassenger() || vehicleCanSprint(getVehicle())) && 
/* 1170 */       !isFallFlying());
/*      */   }
/*      */   
/*      */   private boolean vehicleCanSprint(Entity $$0) {
/* 1174 */     return ($$0.canSprint() && $$0.isControlledByLocalInstance());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasEnoughImpulseToStartSprinting() {
/* 1180 */     double $$0 = 0.8D;
/* 1181 */     return isUnderWater() ? this.input.hasForwardImpulse() : ((this.input.forwardImpulse >= 0.8D));
/*      */   }
/*      */   
/*      */   private boolean hasEnoughFoodToStartSprinting() {
/* 1185 */     return (isPassenger() || getFoodData().getFoodLevel() > 6.0F || (getAbilities()).mayfly);
/*      */   }
/*      */   
/*      */   public float getWaterVision() {
/* 1189 */     if (!isEyeInFluid(FluidTags.WATER)) {
/* 1190 */       return 0.0F;
/*      */     }
/* 1192 */     float $$0 = 600.0F;
/* 1193 */     float $$1 = 100.0F;
/* 1194 */     if (this.waterVisionTime >= 600.0F) {
/* 1195 */       return 1.0F;
/*      */     }
/* 1197 */     float $$2 = Mth.clamp(this.waterVisionTime / 100.0F, 0.0F, 1.0F);
/* 1198 */     float $$3 = (this.waterVisionTime < 100.0F) ? 0.0F : Mth.clamp((this.waterVisionTime - 100.0F) / 500.0F, 0.0F, 1.0F);
/* 1199 */     return $$2 * 0.6F + $$3 * 0.39999998F;
/*      */   }
/*      */   
/*      */   public void onGameModeChanged(GameType $$0) {
/* 1203 */     if ($$0 == GameType.SPECTATOR)
/*      */     {
/* 1205 */       setDeltaMovement(getDeltaMovement().with(Direction.Axis.Y, 0.0D));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUnderWater() {
/* 1211 */     return this.wasUnderwater;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean updateIsUnderwater() {
/* 1216 */     boolean $$0 = this.wasUnderwater;
/* 1217 */     boolean $$1 = super.updateIsUnderwater();
/*      */     
/* 1219 */     if (isSpectator()) {
/* 1220 */       return this.wasUnderwater;
/*      */     }
/*      */     
/* 1223 */     if (!$$0 && $$1) {
/* 1224 */       level().playLocalSound(getX(), getY(), getZ(), SoundEvents.AMBIENT_UNDERWATER_ENTER, SoundSource.AMBIENT, 1.0F, 1.0F, false);
/* 1225 */       this.minecraft.getSoundManager().play((SoundInstance)new UnderwaterAmbientSoundInstances.UnderwaterAmbientSoundInstance(this));
/*      */     } 
/*      */     
/* 1228 */     if ($$0 && !$$1) {
/* 1229 */       level().playLocalSound(getX(), getY(), getZ(), SoundEvents.AMBIENT_UNDERWATER_EXIT, SoundSource.AMBIENT, 1.0F, 1.0F, false);
/*      */     }
/*      */     
/* 1232 */     return this.wasUnderwater;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getRopeHoldPosition(float $$0) {
/* 1237 */     if (this.minecraft.options.getCameraType().isFirstPerson()) {
/* 1238 */       float $$1 = Mth.lerp($$0 * 0.5F, getYRot(), this.yRotO) * 0.017453292F;
/* 1239 */       float $$2 = Mth.lerp($$0 * 0.5F, getXRot(), this.xRotO) * 0.017453292F;
/* 1240 */       double $$3 = (getMainArm() == HumanoidArm.RIGHT) ? -1.0D : 1.0D;
/* 1241 */       Vec3 $$4 = new Vec3(0.39D * $$3, -0.6D, 0.3D);
/* 1242 */       return $$4.xRot(-$$2).yRot(-$$1).add(getEyePosition($$0));
/*      */     } 
/* 1244 */     return super.getRopeHoldPosition($$0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTutorialInventoryAction(ItemStack $$0, ItemStack $$1, ClickAction $$2) {
/* 1249 */     this.minecraft.getTutorial().onInventoryAction($$0, $$1, $$2);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getVisualRotationYInDegrees() {
/* 1254 */     return getYRot();
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\player\LocalPlayer.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */