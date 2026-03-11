/*     */ package net.minecraft.network.chat.contents;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function4;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.stream.Collectors;
/*     */ import java.util.stream.Stream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.NbtPathArgument;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.Tag;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentContents;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class NbtContents implements ComponentContents {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final MapCodec<NbtContents> CODEC;
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("nbt").forGetter(NbtContents::getNbtPath), (App)Codec.BOOL.optionalFieldOf("interpret", Boolean.valueOf(false)).forGetter(NbtContents::isInterpreting), (App)ComponentSerialization.CODEC.optionalFieldOf("separator").forGetter(NbtContents::getSeparator), (App)DataSource.CODEC.forGetter(NbtContents::getDataSource)).apply((Applicative)$$0, NbtContents::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  37 */   public static final ComponentContents.Type<NbtContents> TYPE = new ComponentContents.Type(CODEC, "nbt");
/*     */   
/*     */   private final boolean interpreting;
/*     */   
/*     */   private final Optional<Component> separator;
/*     */   private final String nbtPathPattern;
/*     */   private final DataSource dataSource;
/*     */   @Nullable
/*     */   protected final NbtPathArgument.NbtPath compiledNbtPath;
/*     */   
/*     */   public NbtContents(String $$0, boolean $$1, Optional<Component> $$2, DataSource $$3) {
/*  48 */     this($$0, compileNbtPath($$0), $$1, $$2, $$3);
/*     */   }
/*     */   
/*     */   private NbtContents(String $$0, @Nullable NbtPathArgument.NbtPath $$1, boolean $$2, Optional<Component> $$3, DataSource $$4) {
/*  52 */     this.nbtPathPattern = $$0;
/*  53 */     this.compiledNbtPath = $$1;
/*  54 */     this.interpreting = $$2;
/*  55 */     this.separator = $$3;
/*  56 */     this.dataSource = $$4;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static NbtPathArgument.NbtPath compileNbtPath(String $$0) {
/*     */     try {
/*  62 */       return (new NbtPathArgument()).parse(new StringReader($$0));
/*  63 */     } catch (CommandSyntaxException $$1) {
/*  64 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getNbtPath() {
/*  69 */     return this.nbtPathPattern;
/*     */   }
/*     */   
/*     */   public boolean isInterpreting() {
/*  73 */     return this.interpreting;
/*     */   }
/*     */   
/*     */   public Optional<Component> getSeparator() {
/*  77 */     return this.separator;
/*     */   }
/*     */   
/*     */   public DataSource getDataSource() {
/*  81 */     return this.dataSource;
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
/*     */     //   8: instanceof net/minecraft/network/chat/contents/NbtContents
/*     */     //   11: ifeq -> 76
/*     */     //   14: aload_1
/*     */     //   15: checkcast net/minecraft/network/chat/contents/NbtContents
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: getfield dataSource : Lnet/minecraft/network/chat/contents/DataSource;
/*     */     //   23: aload_2
/*     */     //   24: getfield dataSource : Lnet/minecraft/network/chat/contents/DataSource;
/*     */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   30: ifeq -> 76
/*     */     //   33: aload_0
/*     */     //   34: getfield separator : Ljava/util/Optional;
/*     */     //   37: aload_2
/*     */     //   38: getfield separator : Ljava/util/Optional;
/*     */     //   41: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   44: ifeq -> 76
/*     */     //   47: aload_0
/*     */     //   48: getfield interpreting : Z
/*     */     //   51: aload_2
/*     */     //   52: getfield interpreting : Z
/*     */     //   55: if_icmpne -> 76
/*     */     //   58: aload_0
/*     */     //   59: getfield nbtPathPattern : Ljava/lang/String;
/*     */     //   62: aload_2
/*     */     //   63: getfield nbtPathPattern : Ljava/lang/String;
/*     */     //   66: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   69: ifeq -> 76
/*     */     //   72: iconst_1
/*     */     //   73: goto -> 77
/*     */     //   76: iconst_0
/*     */     //   77: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #86	-> 0
/*     */     //   #87	-> 5
/*     */     //   #93	-> 7
/*     */     //   #89	-> 14
/*     */     //   #90	-> 27
/*     */     //   #91	-> 41
/*     */     //   #93	-> 66
/*     */     //   #89	-> 77
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	78	0	this	Lnet/minecraft/network/chat/contents/NbtContents;
/*     */     //   0	78	1	$$0	Ljava/lang/Object;
/*     */     //   19	57	2	$$1	Lnet/minecraft/network/chat/contents/NbtContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     int $$0 = this.interpreting ? 1 : 0;
/*  99 */     $$0 = 31 * $$0 + this.separator.hashCode();
/* 100 */     $$0 = 31 * $$0 + this.nbtPathPattern.hashCode();
/* 101 */     $$0 = 31 * $$0 + this.dataSource.hashCode();
/* 102 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 107 */     return "nbt{" + this.dataSource + ", interpreting=" + this.interpreting + ", separator=" + this.separator + "}";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MutableComponent resolve(@Nullable CommandSourceStack $$0, @Nullable Entity $$1, int $$2) throws CommandSyntaxException {
/* 115 */     if ($$0 == null || this.compiledNbtPath == null) {
/* 116 */       return Component.empty();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     Stream<String> $$3 = this.dataSource.getData($$0).flatMap($$0 -> { try { return this.compiledNbtPath.get((Tag)$$0).stream(); } catch (CommandSyntaxException $$1) { return Stream.empty(); }  }).map(Tag::getAsString);
/*     */     
/* 128 */     if (this.interpreting) {
/* 129 */       Component $$4 = (Component)DataFixUtils.orElse(ComponentUtils.updateForEntity($$0, this.separator, $$1, $$2), ComponentUtils.DEFAULT_NO_STYLE_SEPARATOR);
/* 130 */       return $$3.<MutableComponent>flatMap($$3 -> {
/*     */             try {
/*     */               MutableComponent $$4 = Component.Serializer.fromJson($$3);
/*     */               return Stream.of(ComponentUtils.updateForEntity($$0, (Component)$$4, $$1, $$2));
/* 134 */             } catch (Exception $$5) {
/*     */               LOGGER.warn("Failed to parse component: {}", $$3, $$5);
/*     */               
/*     */               return Stream.of(new MutableComponent[0]);
/*     */             } 
/* 139 */           }).reduce(($$1, $$2) -> $$1.append($$0).append((Component)$$2))
/* 140 */         .orElseGet(Component::empty);
/*     */     } 
/* 142 */     return ComponentUtils.updateForEntity($$0, this.separator, $$1, $$2)
/* 143 */       .map($$1 -> (MutableComponent)$$0.map(Component::literal).reduce(()).orElseGet(Component::empty))
/*     */       
/* 145 */       .orElseGet(() -> Component.literal($$0.collect(Collectors.joining(", "))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ComponentContents.Type<?> type() {
/* 154 */     return TYPE;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\NbtContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */