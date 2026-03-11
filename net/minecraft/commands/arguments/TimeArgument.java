/*     */ package net.minecraft.commands.arguments;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.brigadier.Message;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.arguments.ArgumentType;
/*     */ import com.mojang.brigadier.context.CommandContext;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
/*     */ import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
/*     */ import com.mojang.brigadier.suggestion.Suggestions;
/*     */ import com.mojang.brigadier.suggestion.SuggestionsBuilder;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import net.minecraft.commands.CommandBuildContext;
/*     */ import net.minecraft.commands.SharedSuggestionProvider;
/*     */ import net.minecraft.commands.synchronization.ArgumentTypeInfo;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ 
/*     */ public class TimeArgument
/*     */   implements ArgumentType<Integer>
/*     */ {
/*  27 */   private static final Collection<String> EXAMPLES = Arrays.asList(new String[] { "0d", "0s", "0t", "0" }); private static final Dynamic2CommandExceptionType ERROR_TICK_COUNT_TOO_LOW;
/*  28 */   private static final SimpleCommandExceptionType ERROR_INVALID_UNIT = new SimpleCommandExceptionType((Message)Component.translatable("argument.time.invalid_unit")); static {
/*  29 */     ERROR_TICK_COUNT_TOO_LOW = new Dynamic2CommandExceptionType(($$0, $$1) -> Component.translatableEscape("argument.time.tick_count_too_low", new Object[] { $$1, $$0 }));
/*     */   }
/*  31 */   private static final Object2IntMap<String> UNITS = (Object2IntMap<String>)new Object2IntOpenHashMap(); final int minimum;
/*     */   
/*     */   static {
/*  34 */     UNITS.put("d", 24000);
/*  35 */     UNITS.put("s", 20);
/*  36 */     UNITS.put("t", 1);
/*  37 */     UNITS.put("", 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private TimeArgument(int $$0) {
/*  43 */     this.minimum = $$0;
/*     */   }
/*     */   
/*     */   public static TimeArgument time() {
/*  47 */     return new TimeArgument(0);
/*     */   }
/*     */   
/*     */   public static TimeArgument time(int $$0) {
/*  51 */     return new TimeArgument($$0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Integer parse(StringReader $$0) throws CommandSyntaxException {
/*  56 */     float $$1 = $$0.readFloat();
/*  57 */     String $$2 = $$0.readUnquotedString();
/*  58 */     int $$3 = UNITS.getOrDefault($$2, 0);
/*  59 */     if ($$3 == 0) {
/*  60 */       throw ERROR_INVALID_UNIT.create();
/*     */     }
/*     */     
/*  63 */     int $$4 = Math.round($$1 * $$3);
/*  64 */     if ($$4 < this.minimum) {
/*  65 */       throw ERROR_TICK_COUNT_TOO_LOW.create(Integer.valueOf($$4), Integer.valueOf(this.minimum));
/*     */     }
/*     */     
/*  68 */     return Integer.valueOf($$4);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> $$0, SuggestionsBuilder $$1) {
/*  73 */     StringReader $$2 = new StringReader($$1.getRemaining());
/*     */     try {
/*  75 */       $$2.readFloat();
/*  76 */     } catch (CommandSyntaxException $$3) {
/*  77 */       return $$1.buildFuture();
/*     */     } 
/*     */     
/*  80 */     return SharedSuggestionProvider.suggest((Iterable)UNITS.keySet(), $$1.createOffset($$1.getStart() + $$2.getCursor()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getExamples() {
/*  85 */     return EXAMPLES;
/*     */   }
/*     */   
/*     */   public static class Info implements ArgumentTypeInfo<TimeArgument, Info.Template> {
/*     */     public final class Template implements ArgumentTypeInfo.Template<TimeArgument> {
/*     */       final int min;
/*     */       
/*     */       Template(int $$1) {
/*  93 */         this.min = $$1;
/*     */       }
/*     */ 
/*     */       
/*     */       public TimeArgument instantiate(CommandBuildContext $$0) {
/*  98 */         return TimeArgument.time(this.min);
/*     */       }
/*     */ 
/*     */       
/*     */       public ArgumentTypeInfo<TimeArgument, ?> type() {
/* 103 */         return TimeArgument.Info.this;
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToNetwork(Template $$0, FriendlyByteBuf $$1) {
/* 109 */       $$1.writeInt($$0.min);
/*     */     }
/*     */ 
/*     */     
/*     */     public Template deserializeFromNetwork(FriendlyByteBuf $$0) {
/* 114 */       int $$1 = $$0.readInt();
/* 115 */       return new Template($$1);
/*     */     }
/*     */ 
/*     */     
/*     */     public void serializeToJson(Template $$0, JsonObject $$1) {
/* 120 */       $$1.addProperty("min", Integer.valueOf($$0.min));
/*     */     }
/*     */ 
/*     */     
/*     */     public Template unpack(TimeArgument $$0) {
/* 125 */       return new Template($$0.minimum);
/*     */     }
/*     */   }
/*     */   
/*     */   public final class Template implements ArgumentTypeInfo.Template<TimeArgument> {
/*     */     final int min;
/*     */     
/*     */     Template(int $$1) {
/*     */       this.min = $$1;
/*     */     }
/*     */     
/*     */     public TimeArgument instantiate(CommandBuildContext $$0) {
/*     */       return TimeArgument.time(this.min);
/*     */     }
/*     */     
/*     */     public ArgumentTypeInfo<TimeArgument, ?> type() {
/*     */       return TimeArgument.Info.this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\commands\arguments\TimeArgument.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */