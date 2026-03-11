/*     */ package net.minecraft.world.level;
/*     */ import java.util.function.IntFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.util.ByIdMap;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.player.Abilities;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ 
/*     */ public enum GameType implements StringRepresentable {
/*     */   public static final GameType DEFAULT_MODE;
/*     */   public static final StringRepresentable.EnumCodec<GameType> CODEC;
/*  13 */   SURVIVAL(0, "survival"),
/*  14 */   CREATIVE(1, "creative"),
/*  15 */   ADVENTURE(2, "adventure"),
/*  16 */   SPECTATOR(3, "spectator");
/*     */   
/*     */   static {
/*  19 */     DEFAULT_MODE = SURVIVAL;
/*     */     
/*  21 */     CODEC = StringRepresentable.fromEnum(GameType::values);
/*     */     
/*  23 */     BY_ID = ByIdMap.continuous(GameType::getId, (Object[])values(), ByIdMap.OutOfBoundsStrategy.ZERO);
/*     */   }
/*     */   private static final IntFunction<GameType> BY_ID;
/*     */   private static final int NOT_SET = -1;
/*     */   private final int id;
/*     */   private final String name;
/*     */   private final Component shortName;
/*     */   private final Component longName;
/*     */   
/*     */   GameType(int $$0, String $$1) {
/*  33 */     this.id = $$0;
/*  34 */     this.name = $$1;
/*  35 */     this.shortName = (Component)Component.translatable("selectWorld.gameMode." + $$1);
/*  36 */     this.longName = (Component)Component.translatable("gameMode." + $$1);
/*     */   }
/*     */   
/*     */   public int getId() {
/*  40 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  44 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/*  49 */     return this.name;
/*     */   }
/*     */   
/*     */   public Component getLongDisplayName() {
/*  53 */     return this.longName;
/*     */   }
/*     */   
/*     */   public Component getShortDisplayName() {
/*  57 */     return this.shortName;
/*     */   }
/*     */   
/*     */   public void updatePlayerAbilities(Abilities $$0) {
/*  61 */     if (this == CREATIVE) {
/*  62 */       $$0.mayfly = true;
/*  63 */       $$0.instabuild = true;
/*  64 */       $$0.invulnerable = true;
/*  65 */     } else if (this == SPECTATOR) {
/*  66 */       $$0.mayfly = true;
/*  67 */       $$0.instabuild = false;
/*  68 */       $$0.invulnerable = true;
/*  69 */       $$0.flying = true;
/*     */     } else {
/*  71 */       $$0.mayfly = false;
/*  72 */       $$0.instabuild = false;
/*  73 */       $$0.invulnerable = false;
/*  74 */       $$0.flying = false;
/*     */     } 
/*  76 */     $$0.mayBuild = !isBlockPlacingRestricted();
/*     */   }
/*     */   
/*     */   public boolean isBlockPlacingRestricted() {
/*  80 */     return (this == ADVENTURE || this == SPECTATOR);
/*     */   }
/*     */   
/*     */   public boolean isCreative() {
/*  84 */     return (this == CREATIVE);
/*     */   }
/*     */   
/*     */   public boolean isSurvival() {
/*  88 */     return (this == SURVIVAL || this == ADVENTURE);
/*     */   }
/*     */   
/*     */   public static GameType byId(int $$0) {
/*  92 */     return BY_ID.apply($$0);
/*     */   }
/*     */   
/*     */   public static GameType byName(String $$0) {
/*  96 */     return byName($$0, SURVIVAL);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   @Contract("_,!null->!null;_,null->_")
/*     */   public static GameType byName(String $$0, @Nullable GameType $$1) {
/* 102 */     GameType $$2 = (GameType)CODEC.byName($$0);
/* 103 */     return ($$2 != null) ? $$2 : $$1;
/*     */   }
/*     */   
/*     */   public static int getNullableId(@Nullable GameType $$0) {
/* 107 */     return ($$0 != null) ? $$0.id : -1;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static GameType byNullableId(int $$0) {
/* 112 */     if ($$0 == -1) {
/* 113 */       return null;
/*     */     }
/* 115 */     return byId($$0);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameType.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */