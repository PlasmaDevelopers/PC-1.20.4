/*     */ package net.minecraft.network.chat.contents;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.locale.Language;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentContents;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ 
/*     */ public class TranslatableContents implements ComponentContents {
/*  32 */   public static final Object[] NO_ARGS = new Object[0];
/*     */   
/*  34 */   private static final Codec<Object> PRIMITIVE_ARG_CODEC = ExtraCodecs.validate(ExtraCodecs.JAVA, TranslatableContents::filterAllowedArguments); private static final Codec<Object> ARG_CODEC;
/*     */   
/*     */   private static DataResult<Object> filterAllowedArguments(@Nullable Object $$0) {
/*  37 */     if (!isAllowedPrimitiveArgument($$0)) {
/*  38 */       return DataResult.error(() -> "This value needs to be parsed as component");
/*     */     }
/*  40 */     return DataResult.success($$0);
/*     */   }
/*     */   public static final MapCodec<TranslatableContents> CODEC;
/*     */   public static boolean isAllowedPrimitiveArgument(@Nullable Object $$0) {
/*  44 */     return ($$0 instanceof Number || $$0 instanceof Boolean || $$0 instanceof String);
/*     */   }
/*     */   
/*     */   static {
/*  48 */     ARG_CODEC = Codec.either(PRIMITIVE_ARG_CODEC, ComponentSerialization.CODEC).xmap($$0 -> $$0.map((), ()), $$0 -> {
/*     */           Component $$1 = (Component)$$0;
/*     */ 
/*     */           
/*     */           return ($$0 instanceof Component) ? Either.right($$1) : Either.left($$0);
/*     */         });
/*     */     
/*  55 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("translate").forGetter(()), (App)Codec.STRING.optionalFieldOf("fallback").forGetter(()), (App)ExtraCodecs.strictOptionalField(ARG_CODEC.listOf(), "with").forGetter(())).apply((Applicative)$$0, TranslatableContents::create));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Optional<List<Object>> adjustArgs(Object[] $$0) {
/*  62 */     return ($$0.length == 0) ? Optional.<List<Object>>empty() : Optional.<List<Object>>of(Arrays.asList($$0));
/*     */   }
/*     */   
/*     */   private static Object[] adjustArgs(Optional<List<Object>> $$0) {
/*  66 */     return $$0.<Object[]>map($$0 -> $$0.isEmpty() ? NO_ARGS : $$0.toArray()).orElse(NO_ARGS);
/*     */   }
/*     */   
/*     */   private static TranslatableContents create(String $$0, Optional<String> $$1, Optional<List<Object>> $$2) {
/*  70 */     return new TranslatableContents($$0, $$1.orElse(null), adjustArgs($$2));
/*     */   }
/*     */   
/*  73 */   public static final ComponentContents.Type<TranslatableContents> TYPE = new ComponentContents.Type(CODEC, "translatable");
/*     */   
/*  75 */   private static final FormattedText TEXT_PERCENT = FormattedText.of("%");
/*  76 */   private static final FormattedText TEXT_NULL = FormattedText.of("null");
/*     */   
/*     */   private final String key;
/*     */   
/*     */   @Nullable
/*     */   private final String fallback;
/*     */   private final Object[] args;
/*     */   @Nullable
/*     */   private Language decomposedWith;
/*  85 */   private List<FormattedText> decomposedParts = (List<FormattedText>)ImmutableList.of();
/*     */   
/*  87 */   private static final Pattern FORMAT_PATTERN = Pattern.compile("%(?:(\\d+)\\$)?([A-Za-z%]|$)");
/*     */   
/*     */   public TranslatableContents(String $$0, @Nullable String $$1, Object[] $$2) {
/*  90 */     this.key = $$0;
/*  91 */     this.fallback = $$1;
/*  92 */     this.args = $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   public ComponentContents.Type<?> type() {
/*  97 */     return TYPE;
/*     */   }
/*     */   
/*     */   private void decompose() {
/* 101 */     Language $$0 = Language.getInstance();
/* 102 */     if ($$0 == this.decomposedWith) {
/*     */       return;
/*     */     }
/* 105 */     this.decomposedWith = $$0;
/*     */ 
/*     */     
/* 108 */     String $$1 = (this.fallback != null) ? $$0.getOrDefault(this.key, this.fallback) : $$0.getOrDefault(this.key);
/*     */     try {
/* 110 */       ImmutableList.Builder<FormattedText> $$2 = ImmutableList.builder();
/* 111 */       Objects.requireNonNull($$2); decomposeTemplate($$1, $$2::add);
/* 112 */       this.decomposedParts = (List<FormattedText>)$$2.build();
/* 113 */     } catch (TranslatableFormatException $$3) {
/* 114 */       this.decomposedParts = (List<FormattedText>)ImmutableList.of(FormattedText.of($$1));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void decomposeTemplate(String $$0, Consumer<FormattedText> $$1) {
/* 119 */     Matcher $$2 = FORMAT_PATTERN.matcher($$0);
/*     */     
/*     */     try {
/* 122 */       int $$3 = 0;
/* 123 */       int $$4 = 0;
/*     */       
/* 125 */       while ($$2.find($$4)) {
/* 126 */         int $$5 = $$2.start();
/* 127 */         int $$6 = $$2.end();
/*     */         
/* 129 */         if ($$5 > $$4) {
/* 130 */           String $$7 = $$0.substring($$4, $$5);
/* 131 */           if ($$7.indexOf('%') != -1) {
/* 132 */             throw new IllegalArgumentException();
/*     */           }
/* 134 */           $$1.accept(FormattedText.of($$7));
/*     */         } 
/*     */         
/* 137 */         String $$8 = $$2.group(2);
/* 138 */         String $$9 = $$0.substring($$5, $$6);
/*     */ 
/*     */         
/* 141 */         if ("%".equals($$8) && "%%".equals($$9)) {
/* 142 */           $$1.accept(TEXT_PERCENT);
/* 143 */         } else if ("s".equals($$8)) {
/* 144 */           String $$10 = $$2.group(1);
/* 145 */           int $$11 = ($$10 != null) ? (Integer.parseInt($$10) - 1) : $$3++;
/* 146 */           $$1.accept(getArgument($$11));
/*     */         } else {
/* 148 */           throw new TranslatableFormatException(this, "Unsupported format: '" + $$9 + "'");
/*     */         } 
/*     */         
/* 151 */         $$4 = $$6;
/*     */       } 
/*     */       
/* 154 */       if ($$4 < $$0.length()) {
/* 155 */         String $$12 = $$0.substring($$4);
/* 156 */         if ($$12.indexOf('%') != -1) {
/* 157 */           throw new IllegalArgumentException();
/*     */         }
/* 159 */         $$1.accept(FormattedText.of($$12));
/*     */       } 
/* 161 */     } catch (IllegalArgumentException $$13) {
/* 162 */       throw new TranslatableFormatException(this, $$13);
/*     */     } 
/*     */   }
/*     */   
/*     */   private FormattedText getArgument(int $$0) {
/* 167 */     if ($$0 < 0 || $$0 >= this.args.length) {
/* 168 */       throw new TranslatableFormatException(this, $$0);
/*     */     }
/*     */     
/* 171 */     Object $$1 = this.args[$$0];
/*     */     
/* 173 */     if ($$1 instanceof Component) { Component $$2 = (Component)$$1;
/* 174 */       return (FormattedText)$$2; }
/*     */     
/* 176 */     return ($$1 == null) ? TEXT_NULL : FormattedText.of($$1.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/* 182 */     decompose();
/*     */     
/* 184 */     for (FormattedText $$2 : this.decomposedParts) {
/* 185 */       Optional<T> $$3 = $$2.visit($$0, $$1);
/* 186 */       if ($$3.isPresent()) {
/* 187 */         return $$3;
/*     */       }
/*     */     } 
/*     */     
/* 191 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/* 196 */     decompose();
/*     */     
/* 198 */     for (FormattedText $$1 : this.decomposedParts) {
/* 199 */       Optional<T> $$2 = $$1.visit($$0);
/* 200 */       if ($$2.isPresent()) {
/* 201 */         return $$2;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return Optional.empty();
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableComponent resolve(@Nullable CommandSourceStack $$0, @Nullable Entity $$1, int $$2) throws CommandSyntaxException {
/* 210 */     Object[] $$3 = new Object[this.args.length];
/*     */     
/* 212 */     for (int $$4 = 0; $$4 < $$3.length; $$4++) {
/* 213 */       Object $$5 = this.args[$$4];
/* 214 */       if ($$5 instanceof Component) { Component $$6 = (Component)$$5;
/* 215 */         $$3[$$4] = ComponentUtils.updateForEntity($$0, $$6, $$1, $$2); }
/*     */       else
/* 217 */       { $$3[$$4] = $$5; }
/*     */     
/*     */     } 
/* 220 */     return MutableComponent.create(new TranslatableContents(this.key, this.fallback, $$3));
/*     */   }
/*     */ 
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
/*     */     //   8: instanceof net/minecraft/network/chat/contents/TranslatableContents
/*     */     //   11: ifeq -> 65
/*     */     //   14: aload_1
/*     */     //   15: checkcast net/minecraft/network/chat/contents/TranslatableContents
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: getfield key : Ljava/lang/String;
/*     */     //   23: aload_2
/*     */     //   24: getfield key : Ljava/lang/String;
/*     */     //   27: invokestatic equals : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   30: ifeq -> 65
/*     */     //   33: aload_0
/*     */     //   34: getfield fallback : Ljava/lang/String;
/*     */     //   37: aload_2
/*     */     //   38: getfield fallback : Ljava/lang/String;
/*     */     //   41: invokestatic equals : (Ljava/lang/Object;Ljava/lang/Object;)Z
/*     */     //   44: ifeq -> 65
/*     */     //   47: aload_0
/*     */     //   48: getfield args : [Ljava/lang/Object;
/*     */     //   51: aload_2
/*     */     //   52: getfield args : [Ljava/lang/Object;
/*     */     //   55: invokestatic equals : ([Ljava/lang/Object;[Ljava/lang/Object;)Z
/*     */     //   58: ifeq -> 65
/*     */     //   61: iconst_1
/*     */     //   62: goto -> 66
/*     */     //   65: iconst_0
/*     */     //   66: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #225	-> 0
/*     */     //   #226	-> 5
/*     */     //   #232	-> 7
/*     */     //   #229	-> 14
/*     */     //   #230	-> 27
/*     */     //   #231	-> 41
/*     */     //   #232	-> 55
/*     */     //   #229	-> 66
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	67	0	this	Lnet/minecraft/network/chat/contents/TranslatableContents;
/*     */     //   0	67	1	$$0	Ljava/lang/Object;
/*     */     //   19	46	2	$$1	Lnet/minecraft/network/chat/contents/TranslatableContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 237 */     int $$0 = Objects.hashCode(this.key);
/* 238 */     $$0 = 31 * $$0 + Objects.hashCode(this.fallback);
/* 239 */     $$0 = 31 * $$0 + Arrays.hashCode(this.args);
/* 240 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 245 */     return "translation{key='" + this.key + "'" + (
/*     */       
/* 247 */       (this.fallback != null) ? (", fallback='" + this.fallback + "'") : "") + ", args=" + 
/* 248 */       Arrays.toString(this.args) + "}";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getKey() {
/* 253 */     return this.key;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public String getFallback() {
/* 258 */     return this.fallback;
/*     */   }
/*     */   
/*     */   public Object[] getArgs() {
/* 262 */     return this.args;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\TranslatableContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */