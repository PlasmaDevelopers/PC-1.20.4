/*     */ package net.minecraft.network.chat.contents;
/*     */ import com.mojang.brigadier.StringReader;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import java.util.function.BiFunction;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.commands.CommandSourceStack;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelector;
/*     */ import net.minecraft.commands.arguments.selector.EntitySelectorParser;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentContents;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.network.chat.FormattedText;
/*     */ import net.minecraft.network.chat.MutableComponent;
/*     */ import net.minecraft.network.chat.Style;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.Entity;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SelectorContents implements ComponentContents {
/*  27 */   private static final Logger LOGGER = LogUtils.getLogger(); public static final MapCodec<SelectorContents> CODEC;
/*     */   static {
/*  29 */     CODEC = RecordCodecBuilder.mapCodec($$0 -> $$0.group((App)Codec.STRING.fieldOf("selector").forGetter(SelectorContents::getPattern), (App)ExtraCodecs.strictOptionalField(ComponentSerialization.CODEC, "separator").forGetter(SelectorContents::getSeparator)).apply((Applicative)$$0, SelectorContents::new));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  34 */   public static final ComponentContents.Type<SelectorContents> TYPE = new ComponentContents.Type(CODEC, "selector");
/*     */   
/*     */   private final String pattern;
/*     */   @Nullable
/*     */   private final EntitySelector selector;
/*     */   protected final Optional<Component> separator;
/*     */   
/*     */   public SelectorContents(String $$0, Optional<Component> $$1) {
/*  42 */     this.pattern = $$0;
/*  43 */     this.separator = $$1;
/*  44 */     this.selector = parseSelector($$0);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static EntitySelector parseSelector(String $$0) {
/*  49 */     EntitySelector $$1 = null;
/*     */     try {
/*  51 */       EntitySelectorParser $$2 = new EntitySelectorParser(new StringReader($$0));
/*  52 */       $$1 = $$2.parse();
/*  53 */     } catch (CommandSyntaxException $$3) {
/*  54 */       LOGGER.warn("Invalid selector component: {}: {}", $$0, $$3.getMessage());
/*     */     } 
/*  56 */     return $$1;
/*     */   }
/*     */ 
/*     */   
/*     */   public ComponentContents.Type<?> type() {
/*  61 */     return TYPE;
/*     */   }
/*     */   
/*     */   public String getPattern() {
/*  65 */     return this.pattern;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public EntitySelector getSelector() {
/*  70 */     return this.selector;
/*     */   }
/*     */   
/*     */   public Optional<Component> getSeparator() {
/*  74 */     return this.separator;
/*     */   }
/*     */ 
/*     */   
/*     */   public MutableComponent resolve(@Nullable CommandSourceStack $$0, @Nullable Entity $$1, int $$2) throws CommandSyntaxException {
/*  79 */     if ($$0 == null || this.selector == null) {
/*  80 */       return Component.empty();
/*     */     }
/*  82 */     Optional<? extends Component> $$3 = ComponentUtils.updateForEntity($$0, this.separator, $$1, $$2);
/*  83 */     return ComponentUtils.formatList(this.selector.findEntities($$0), $$3, Entity::getDisplayName);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> Optional<T> visit(FormattedText.StyledContentConsumer<T> $$0, Style $$1) {
/*  89 */     return $$0.accept($$1, this.pattern);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> Optional<T> visit(FormattedText.ContentConsumer<T> $$0) {
/*  94 */     return $$0.accept(this.pattern);
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
/*     */     //   8: instanceof net/minecraft/network/chat/contents/SelectorContents
/*     */     //   11: ifeq -> 51
/*     */     //   14: aload_1
/*     */     //   15: checkcast net/minecraft/network/chat/contents/SelectorContents
/*     */     //   18: astore_2
/*     */     //   19: aload_0
/*     */     //   20: getfield pattern : Ljava/lang/String;
/*     */     //   23: aload_2
/*     */     //   24: getfield pattern : Ljava/lang/String;
/*     */     //   27: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   30: ifeq -> 51
/*     */     //   33: aload_0
/*     */     //   34: getfield separator : Ljava/util/Optional;
/*     */     //   37: aload_2
/*     */     //   38: getfield separator : Ljava/util/Optional;
/*     */     //   41: invokevirtual equals : (Ljava/lang/Object;)Z
/*     */     //   44: ifeq -> 51
/*     */     //   47: iconst_1
/*     */     //   48: goto -> 52
/*     */     //   51: iconst_0
/*     */     //   52: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #99	-> 0
/*     */     //   #100	-> 5
/*     */     //   #105	-> 7
/*     */     //   #103	-> 14
/*     */     //   #104	-> 27
/*     */     //   #105	-> 41
/*     */     //   #103	-> 52
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	53	0	this	Lnet/minecraft/network/chat/contents/SelectorContents;
/*     */     //   0	53	1	$$0	Ljava/lang/Object;
/*     */     //   19	32	2	$$1	Lnet/minecraft/network/chat/contents/SelectorContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     int $$0 = this.pattern.hashCode();
/* 111 */     $$0 = 31 * $$0 + this.separator.hashCode();
/* 112 */     return $$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 117 */     return "pattern{" + this.pattern + "}";
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\contents\SelectorContents.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */