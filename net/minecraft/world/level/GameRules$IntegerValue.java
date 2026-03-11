/*     */ package net.minecraft.world.level;
/*     */ 
/*     */ import com.mojang.brigadier.arguments.IntegerArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import java.util.function.BiConsumer;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IntegerValue
/*     */   extends GameRules.Value<GameRules.IntegerValue>
/*     */ {
/*     */   private int value;
/*     */   
/*     */   private static GameRules.Type<IntegerValue> create(int $$0, BiConsumer<MinecraftServer, IntegerValue> $$1) {
/* 318 */     return new GameRules.Type<>(IntegerArgumentType::integer, $$1 -> new IntegerValue($$1, $$0), $$1, GameRules.GameRuleTypeVisitor::visitInteger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static GameRules.Type<IntegerValue> create(int $$0) {
/* 327 */     return create($$0, ($$0, $$1) -> {
/*     */         
/*     */         });
/*     */   }
/*     */   
/*     */   public IntegerValue(GameRules.Type<IntegerValue> $$0, int $$1) {
/* 333 */     super($$0);
/* 334 */     this.value = $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateFromArgument(CommandContext<CommandSourceStack> $$0, String $$1) {
/* 339 */     this.value = IntegerArgumentType.getInteger($$0, $$1);
/*     */   }
/*     */   
/*     */   public int get() {
/* 343 */     return this.value;
/*     */   }
/*     */   
/*     */   public void set(int $$0, @Nullable MinecraftServer $$1) {
/* 347 */     this.value = $$0;
/* 348 */     onChanged($$1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String serialize() {
/* 353 */     return Integer.toString(this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deserialize(String $$0) {
/* 358 */     this.value = safeParse($$0);
/*     */   }
/*     */   
/*     */   public boolean tryDeserialize(String $$0) {
/*     */     try {
/* 363 */       this.value = Integer.parseInt($$0);
/* 364 */       return true;
/* 365 */     } catch (NumberFormatException numberFormatException) {
/*     */ 
/*     */       
/* 368 */       return false;
/*     */     } 
/*     */   }
/*     */   private static int safeParse(String $$0) {
/* 372 */     if (!$$0.isEmpty()) {
/*     */       try {
/* 374 */         return Integer.parseInt($$0);
/* 375 */       } catch (NumberFormatException $$1) {
/* 376 */         GameRules.LOGGER.warn("Failed to parse integer {}", $$0);
/*     */       } 
/*     */     }
/* 379 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCommandResult() {
/* 384 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IntegerValue getSelf() {
/* 389 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   protected IntegerValue copy() {
/* 394 */     return new IntegerValue(this.type, this.value);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFrom(IntegerValue $$0, @Nullable MinecraftServer $$1) {
/* 399 */     this.value = $$0.value;
/* 400 */     onChanged($$1);
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\GameRules$IntegerValue.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */