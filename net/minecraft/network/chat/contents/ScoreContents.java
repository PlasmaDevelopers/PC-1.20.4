/*     */ package net.minecraft.network.chat.contents;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.List;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentContents;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.numbers.StyledFormat;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.ServerScoreboard;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import net.minecraft.world.scores.Objective;
/*     */ import net.minecraft.world.scores.ReadOnlyScoreInfo;
/*     */ import net.minecraft.world.scores.ScoreHolder;
/*     */ 
/*     */ public class ScoreContents implements ComponentContents {
/*     */   static {
/*  27 */     INNER_CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("name").forGetter(ScoreContents::getName), (App)Codec.STRING.fieldOf("objective").forGetter(ScoreContents::getObjective)).apply((Applicative)$$0, ScoreContents::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final MapCodec<ScoreContents> INNER_CODEC;
/*  32 */   public static final MapCodec<ScoreContents> CODEC = INNER_CODEC.fieldOf("score");
/*     */   
/*  34 */   public static final ComponentContents.Type<ScoreContents> TYPE = new ComponentContents.Type(CODEC, "score");
/*     */   
/*     */   private final String name;
/*     */   @Nullable
/*     */   private final EntitySelector selector;
/*     */   private final String objective;
/*     */   
/*     */   @Nullable
/*     */   private static EntitySelector parseSelector(String $$0) {
/*     */     try {
/*  44 */       return (new EntitySelectorParser(new StringReader($$0))).parse();
/*  45 */     } catch (CommandSyntaxException commandSyntaxException) {
/*     */       
/*  47 */       return null;
/*     */     } 
/*     */   }
/*     */   public ScoreContents(String $$0, String $$1) {
/*  51 */     this.name = $$0;
/*  52 */     this.selector = parseSelector($$0);
/*  53 */     this.objective = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ComponentContents.Type<?> type() {
/*  58 */     return TYPE;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  62 */     return this.name;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public EntitySelector getSelector() {
/*  67 */     return this.selector;
/*     */   }
/*     */   
/*     */   public String getObjective() {
/*  71 */     return this.objective;
/*     */   }
/*     */   
/*     */   private ScoreHolder findTargetName(CommandSourceStack $$0) throws CommandSyntaxException {
/*  75 */     if (this.selector != null) {
/*  76 */       List<? extends Entity> $$1 = this.selector.findEntities($$0);
/*  77 */       if (!$$1.isEmpty()) {
/*  78 */         if ($$1.size() != 1) {
/*  79 */           throw EntityArgument.ERROR_NOT_SINGLE_ENTITY.create();
/*     */         }
/*  81 */         return (ScoreHolder)$$1.get(0);
/*     */       } 
/*     */     } 
/*  84 */     return ScoreHolder.forNameOnly(this.name);
/*     */   }
/*     */   
/*     */   private MutableComponent getScore(ScoreHolder $$0, CommandSourceStack $$1) {
/*  88 */     MinecraftServer $$2 = $$1.getServer();
/*  89 */     if ($$2 != null) {
/*  90 */       ServerScoreboard serverScoreboard = $$2.getScoreboard();
/*  91 */       Objective $$4 = serverScoreboard.getObjective(this.objective);
/*     */       
/*  93 */       if ($$4 != null) {
/*  94 */         ReadOnlyScoreInfo $$5 = serverScoreboard.getPlayerScoreInfo($$0, $$4);
/*  95 */         if ($$5 != null) {
/*  96 */           return $$5.formatValue($$4.numberFormatOrDefault((NumberFormat)StyledFormat.NO_STYLE));
/*     */         }
/*     */       } 
/*     */     } 
/* 100 */     return Component.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableComponent resolve(@Nullable CommandSourceStack $$0, @Nullable Entity $$1, int $$2) throws CommandSyntaxException {
/* 105 */     if ($$0 == null) {
/* 106 */       return Component.empty();
/*     */     }
/*     */     
/* 109 */     ScoreHolder $$3 = findTargetName($$0);
/* 110 */     ScoreHolder $$4 = ($$1 != null && $$3.equals(ScoreHolder.WILDCARD)) ? (ScoreHolder)$$1 : $$3;
/* 111 */     return getScore($$4, $$0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: aload_1
/*     */     //   2: if_acmpne -> 7
/*     */     //   5: iconst_1
/*     */     //   6: ireturn
/*     */     //   7: aload_1
/*     */     //   8: instanceof net/minecraft/network/chat/contents/ScoreContents
/*     */     //   11: ifeq -> 51
/*     */     //   14: aload_1
/*     */     //   15: checkcast net/minecraft/network/chat/contents/ScoreContents
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: getfield name : Ljava/lang/String;
/*     */     //   23: aload_2
/*     */     //   24: getfield name : Ljava/lang/String;
/*     */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   30: ifeq -> 51
/*     */     //   33: aload_0
/*     */     //   34: getfield objective : Ljava/lang/String;
/*     */     //   37: aload_2
/*     */     //   38: getfield objective : Ljava/lang/String;
/*     */     //   41: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   44: ifeq -> 51
/*     */     //   47: iconst_1
/*     */     //   48: goto -> 52
/*     */     //   51: iconst_0
/*     */     //   52: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #116	-> 0
/*     */     //   #117	-> 5
/*     */     //   #121	-> 7
/*     */     //   #119	-> 14
/*     */     //   #120	-> 27
/*     */     //   #121	-> 41
/*     */     //   #119	-> 52
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	53	0	this	Lnet/minecraft/network/chat/contents/ScoreContents;
/*     */     //   0	53	1	$$0	Ljava/lang/Object;
/*     */     //   19	32	2	$$1	Lnet/minecraft/network/chat/contents/ScoreContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 126 */     int $$0 = this.name.hashCode();
/* 127 */     $$0 = 31 * $$0 + this.objective.hashCode();
/* 128 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 133 */     return "score{name='" + this.name + "', objective='" + this.objective + "'}";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\ScoreContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */