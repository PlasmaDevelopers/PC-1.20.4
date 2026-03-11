/*     */ package net.minecraft.world.level.gameevent;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.Holder;
/*     */ import net.minecraft.core.Registry;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.tags.TagKey;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.level.block.state.BlockState;
/*     */ import net.minecraft.world.phys.Vec3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GameEvent
/*     */ {
/*  17 */   public static final GameEvent BLOCK_ACTIVATE = register("block_activate");
/*  18 */   public static final GameEvent BLOCK_ATTACH = register("block_attach");
/*  19 */   public static final GameEvent BLOCK_CHANGE = register("block_change");
/*  20 */   public static final GameEvent BLOCK_CLOSE = register("block_close");
/*  21 */   public static final GameEvent BLOCK_DEACTIVATE = register("block_deactivate");
/*  22 */   public static final GameEvent BLOCK_DESTROY = register("block_destroy");
/*  23 */   public static final GameEvent BLOCK_DETACH = register("block_detach");
/*  24 */   public static final GameEvent BLOCK_OPEN = register("block_open");
/*  25 */   public static final GameEvent BLOCK_PLACE = register("block_place");
/*  26 */   public static final GameEvent CONTAINER_CLOSE = register("container_close");
/*  27 */   public static final GameEvent CONTAINER_OPEN = register("container_open");
/*  28 */   public static final GameEvent DRINK = register("drink");
/*  29 */   public static final GameEvent EAT = register("eat");
/*  30 */   public static final GameEvent ELYTRA_GLIDE = register("elytra_glide");
/*  31 */   public static final GameEvent ENTITY_DAMAGE = register("entity_damage");
/*  32 */   public static final GameEvent ENTITY_DIE = register("entity_die");
/*  33 */   public static final GameEvent ENTITY_DISMOUNT = register("entity_dismount");
/*  34 */   public static final GameEvent ENTITY_INTERACT = register("entity_interact");
/*  35 */   public static final GameEvent ENTITY_MOUNT = register("entity_mount");
/*  36 */   public static final GameEvent ENTITY_PLACE = register("entity_place");
/*  37 */   public static final GameEvent ENTITY_ACTION = register("entity_action");
/*  38 */   public static final GameEvent EQUIP = register("equip");
/*  39 */   public static final GameEvent EXPLODE = register("explode");
/*  40 */   public static final GameEvent FLAP = register("flap");
/*  41 */   public static final GameEvent FLUID_PICKUP = register("fluid_pickup");
/*  42 */   public static final GameEvent FLUID_PLACE = register("fluid_place");
/*  43 */   public static final GameEvent HIT_GROUND = register("hit_ground");
/*  44 */   public static final GameEvent INSTRUMENT_PLAY = register("instrument_play");
/*  45 */   public static final GameEvent ITEM_INTERACT_FINISH = register("item_interact_finish");
/*  46 */   public static final GameEvent ITEM_INTERACT_START = register("item_interact_start");
/*  47 */   public static final GameEvent JUKEBOX_PLAY = register("jukebox_play", 10);
/*  48 */   public static final GameEvent JUKEBOX_STOP_PLAY = register("jukebox_stop_play", 10);
/*  49 */   public static final GameEvent LIGHTNING_STRIKE = register("lightning_strike");
/*  50 */   public static final GameEvent NOTE_BLOCK_PLAY = register("note_block_play");
/*  51 */   public static final GameEvent PRIME_FUSE = register("prime_fuse");
/*  52 */   public static final GameEvent PROJECTILE_LAND = register("projectile_land");
/*  53 */   public static final GameEvent PROJECTILE_SHOOT = register("projectile_shoot");
/*  54 */   public static final GameEvent SCULK_SENSOR_TENDRILS_CLICKING = register("sculk_sensor_tendrils_clicking");
/*  55 */   public static final GameEvent SHEAR = register("shear");
/*  56 */   public static final GameEvent SHRIEK = register("shriek", 32);
/*  57 */   public static final GameEvent SPLASH = register("splash");
/*  58 */   public static final GameEvent STEP = register("step");
/*  59 */   public static final GameEvent SWIM = register("swim");
/*  60 */   public static final GameEvent TELEPORT = register("teleport");
/*  61 */   public static final GameEvent UNEQUIP = register("unequip");
/*  62 */   public static final GameEvent RESONATE_1 = register("resonate_1");
/*  63 */   public static final GameEvent RESONATE_2 = register("resonate_2");
/*  64 */   public static final GameEvent RESONATE_3 = register("resonate_3");
/*  65 */   public static final GameEvent RESONATE_4 = register("resonate_4");
/*  66 */   public static final GameEvent RESONATE_5 = register("resonate_5");
/*  67 */   public static final GameEvent RESONATE_6 = register("resonate_6");
/*  68 */   public static final GameEvent RESONATE_7 = register("resonate_7");
/*  69 */   public static final GameEvent RESONATE_8 = register("resonate_8");
/*  70 */   public static final GameEvent RESONATE_9 = register("resonate_9");
/*  71 */   public static final GameEvent RESONATE_10 = register("resonate_10");
/*  72 */   public static final GameEvent RESONATE_11 = register("resonate_11");
/*  73 */   public static final GameEvent RESONATE_12 = register("resonate_12");
/*  74 */   public static final GameEvent RESONATE_13 = register("resonate_13");
/*  75 */   public static final GameEvent RESONATE_14 = register("resonate_14");
/*  76 */   public static final GameEvent RESONATE_15 = register("resonate_15");
/*     */   
/*     */   public static final int DEFAULT_NOTIFICATION_RADIUS = 16;
/*     */   
/*     */   private final int notificationRadius;
/*  81 */   private final Holder.Reference<GameEvent> builtInRegistryHolder = BuiltInRegistries.GAME_EVENT.createIntrusiveHolder(this);
/*     */   
/*     */   public GameEvent(int $$0) {
/*  84 */     this.notificationRadius = $$0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNotificationRadius() {
/*  93 */     return this.notificationRadius;
/*     */   }
/*     */   
/*     */   private static GameEvent register(String $$0) {
/*  97 */     return register($$0, 16);
/*     */   }
/*     */   
/*     */   private static GameEvent register(String $$0, int $$1) {
/* 101 */     return (GameEvent)Registry.register((Registry)BuiltInRegistries.GAME_EVENT, $$0, new GameEvent($$1));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 106 */     return "Game Event{ " + builtInRegistryHolder().key().location() + " , " + this.notificationRadius + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public Holder.Reference<GameEvent> builtInRegistryHolder() {
/* 114 */     return this.builtInRegistryHolder;
/*     */   }
/*     */   
/*     */   public boolean is(TagKey<GameEvent> $$0) {
/* 118 */     return this.builtInRegistryHolder.is($$0);
/*     */   } public static final class Context extends Record { @Nullable
/*     */     private final Entity sourceEntity; @Nullable
/* 121 */     private final BlockState affectedState; public Context(@Nullable Entity $$0, @Nullable BlockState $$1) { this.sourceEntity = $$0; this.affectedState = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/level/gameevent/GameEvent$Context;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #121	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/* 121 */       //   0	7	0	this	Lnet/minecraft/world/level/gameevent/GameEvent$Context; } @Nullable public Entity sourceEntity() { return this.sourceEntity; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/level/gameevent/GameEvent$Context;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #121	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/world/level/gameevent/GameEvent$Context; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/level/gameevent/GameEvent$Context;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #121	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/world/level/gameevent/GameEvent$Context;
/* 121 */       //   0	8	1	$$0	Ljava/lang/Object; } @Nullable public BlockState affectedState() { return this.affectedState; }
/*     */      public static Context of(@Nullable Entity $$0) {
/* 123 */       return new Context($$0, null);
/*     */     }
/*     */     
/*     */     public static Context of(@Nullable BlockState $$0) {
/* 127 */       return new Context(null, $$0);
/*     */     }
/*     */     
/*     */     public static Context of(@Nullable Entity $$0, @Nullable BlockState $$1) {
/* 131 */       return new Context($$0, $$1);
/*     */     } }
/*     */ 
/*     */   
/*     */   public static final class ListenerInfo implements Comparable<ListenerInfo> {
/*     */     private final GameEvent gameEvent;
/*     */     private final Vec3 source;
/*     */     private final GameEvent.Context context;
/*     */     private final GameEventListener recipient;
/*     */     private final double distanceToRecipient;
/*     */     
/*     */     public ListenerInfo(GameEvent $$0, Vec3 $$1, GameEvent.Context $$2, GameEventListener $$3, Vec3 $$4) {
/* 143 */       this.gameEvent = $$0;
/* 144 */       this.source = $$1;
/* 145 */       this.context = $$2;
/* 146 */       this.recipient = $$3;
/* 147 */       this.distanceToRecipient = $$1.distanceToSqr($$4);
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(ListenerInfo $$0) {
/* 152 */       return Double.compare(this.distanceToRecipient, $$0.distanceToRecipient);
/*     */     }
/*     */     
/*     */     public GameEvent gameEvent() {
/* 156 */       return this.gameEvent;
/*     */     }
/*     */     
/*     */     public Vec3 source() {
/* 160 */       return this.source;
/*     */     }
/*     */     
/*     */     public GameEvent.Context context() {
/* 164 */       return this.context;
/*     */     }
/*     */     
/*     */     public GameEventListener recipient() {
/* 168 */       return this.recipient;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\gameevent\GameEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */