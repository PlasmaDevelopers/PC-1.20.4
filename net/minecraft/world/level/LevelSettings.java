/*    */ package net.minecraft.world.level;
/*    */ 
/*    */ import com.mojang.serialization.Dynamic;
/*    */ import com.mojang.serialization.DynamicLike;
/*    */ import net.minecraft.world.Difficulty;
/*    */ 
/*    */ public final class LevelSettings {
/*    */   private final String levelName;
/*    */   private final GameType gameType;
/*    */   private final boolean hardcore;
/*    */   private final Difficulty difficulty;
/*    */   private final boolean allowCommands;
/*    */   private final GameRules gameRules;
/*    */   private final WorldDataConfiguration dataConfiguration;
/*    */   
/*    */   public LevelSettings(String $$0, GameType $$1, boolean $$2, Difficulty $$3, boolean $$4, GameRules $$5, WorldDataConfiguration $$6) {
/* 17 */     this.levelName = $$0;
/* 18 */     this.gameType = $$1;
/* 19 */     this.hardcore = $$2;
/* 20 */     this.difficulty = $$3;
/* 21 */     this.allowCommands = $$4;
/* 22 */     this.gameRules = $$5;
/* 23 */     this.dataConfiguration = $$6;
/*    */   }
/*    */   
/*    */   public static LevelSettings parse(Dynamic<?> $$0, WorldDataConfiguration $$1) {
/* 27 */     GameType $$2 = GameType.byId($$0.get("GameType").asInt(0));
/* 28 */     return new LevelSettings($$0.get("LevelName").asString(""), $$2, $$0
/*    */         
/* 30 */         .get("hardcore").asBoolean(false), $$0
/* 31 */         .get("Difficulty").asNumber().map($$0 -> Difficulty.byId($$0.byteValue())).result().orElse(Difficulty.NORMAL), $$0
/* 32 */         .get("allowCommands").asBoolean(($$2 == GameType.CREATIVE)), new GameRules((DynamicLike<?>)$$0
/* 33 */           .get("GameRules")), $$1);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String levelName() {
/* 39 */     return this.levelName;
/*    */   }
/*    */   
/*    */   public GameType gameType() {
/* 43 */     return this.gameType;
/*    */   }
/*    */   
/*    */   public boolean hardcore() {
/* 47 */     return this.hardcore;
/*    */   }
/*    */   
/*    */   public Difficulty difficulty() {
/* 51 */     return this.difficulty;
/*    */   }
/*    */   
/*    */   public boolean allowCommands() {
/* 55 */     return this.allowCommands;
/*    */   }
/*    */   
/*    */   public GameRules gameRules() {
/* 59 */     return this.gameRules;
/*    */   }
/*    */   
/*    */   public WorldDataConfiguration getDataConfiguration() {
/* 63 */     return this.dataConfiguration;
/*    */   }
/*    */   
/*    */   public LevelSettings withGameType(GameType $$0) {
/* 67 */     return new LevelSettings(this.levelName, $$0, this.hardcore, this.difficulty, this.allowCommands, this.gameRules, this.dataConfiguration);
/*    */   }
/*    */   
/*    */   public LevelSettings withDifficulty(Difficulty $$0) {
/* 71 */     return new LevelSettings(this.levelName, this.gameType, this.hardcore, $$0, this.allowCommands, this.gameRules, this.dataConfiguration);
/*    */   }
/*    */   
/*    */   public LevelSettings withDataConfiguration(WorldDataConfiguration $$0) {
/* 75 */     return new LevelSettings(this.levelName, this.gameType, this.hardcore, this.difficulty, this.allowCommands, this.gameRules, $$0);
/*    */   }
/*    */   
/*    */   public LevelSettings copy() {
/* 79 */     return new LevelSettings(this.levelName, this.gameType, this.hardcore, this.difficulty, this.allowCommands, this.gameRules.copy(), this.dataConfiguration);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\LevelSettings.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */