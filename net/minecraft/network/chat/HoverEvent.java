/*     */ package net.minecraft.network.chat;
/*     */ import com.mojang.brigadier.exceptions.CommandSyntaxException;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Either;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.MapCodec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ import net.minecraft.world.item.Item;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class HoverEvent {
/*     */   public static final Codec<HoverEvent> CODEC;
/*     */   private final TypedHoverEvent<?> event;
/*     */   
/*     */   static {
/*  33 */     CODEC = Codec.either(TypedHoverEvent.CODEC.codec(), TypedHoverEvent.LEGACY_CODEC.codec()).xmap($$0 -> new HoverEvent((TypedHoverEvent)$$0.map((), ())), $$0 -> Either.left($$0.event));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> HoverEvent(Action<T> $$0, T $$1) {
/*  38 */     this(new TypedHoverEvent($$0, $$1));
/*     */   }
/*     */   
/*     */   private HoverEvent(TypedHoverEvent<?> $$0) {
/*  42 */     this.event = $$0;
/*     */   }
/*     */   
/*     */   public Action<?> getAction() {
/*  46 */     return this.event.action;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public <T> T getValue(Action<T> $$0) {
/*  51 */     if (this.event.action == $$0) {
/*  52 */       return $$0.cast(this.event.value);
/*     */     }
/*  54 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/*  59 */     if (this == $$0) {
/*  60 */       return true;
/*     */     }
/*  62 */     if ($$0 == null || getClass() != $$0.getClass()) {
/*  63 */       return false;
/*     */     }
/*  65 */     return ((HoverEvent)$$0).event.equals(this.event);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  70 */     return this.event.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  75 */     return this.event.hashCode();
/*     */   } public static class EntityTooltipInfo { public static final Codec<EntityTooltipInfo> CODEC; public final EntityType<?> type; public final UUID id; public final Optional<Component> name; @Nullable
/*     */     private List<Component> linesCache;
/*     */     static {
/*  79 */       CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(()), (App)UUIDUtil.LENIENT_CODEC.fieldOf("id").forGetter(()), (App)ExtraCodecs.strictOptionalField(ComponentSerialization.CODEC, "name").forGetter(())).apply((Applicative)$$0, EntityTooltipInfo::new));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public EntityTooltipInfo(EntityType<?> $$0, UUID $$1, @Nullable Component $$2) {
/*  93 */       this($$0, $$1, Optional.ofNullable($$2));
/*     */     }
/*     */     
/*     */     public EntityTooltipInfo(EntityType<?> $$0, UUID $$1, Optional<Component> $$2) {
/*  97 */       this.type = $$0;
/*  98 */       this.id = $$1;
/*  99 */       this.name = $$2;
/*     */     }
/*     */     
/*     */     public static DataResult<EntityTooltipInfo> legacyCreate(Component $$0) {
/*     */       try {
/* 104 */         CompoundTag $$1 = TagParser.parseTag($$0.getString());
/* 105 */         Component $$2 = Component.Serializer.fromJson($$1.getString("name"));
/* 106 */         EntityType<?> $$3 = (EntityType)BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation($$1.getString("type")));
/* 107 */         UUID $$4 = UUID.fromString($$1.getString("id"));
/* 108 */         return DataResult.success(new EntityTooltipInfo($$3, $$4, $$2));
/* 109 */       } catch (Exception $$5) {
/* 110 */         return DataResult.error(() -> "Failed to parse tooltip: " + $$0.getMessage());
/*     */       } 
/*     */     }
/*     */     
/*     */     public List<Component> getTooltipLines() {
/* 115 */       if (this.linesCache == null) {
/* 116 */         this.linesCache = new ArrayList<>();
/* 117 */         Objects.requireNonNull(this.linesCache); this.name.ifPresent(this.linesCache::add);
/* 118 */         this.linesCache.add(Component.translatable("gui.entity_tooltip.type", new Object[] { this.type.getDescription() }));
/* 119 */         this.linesCache.add(Component.literal(this.id.toString()));
/*     */       } 
/* 121 */       return this.linesCache;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 126 */       if (this == $$0) {
/* 127 */         return true;
/*     */       }
/* 129 */       if ($$0 == null || getClass() != $$0.getClass()) {
/* 130 */         return false;
/*     */       }
/*     */       
/* 133 */       EntityTooltipInfo $$1 = (EntityTooltipInfo)$$0;
/* 134 */       return (this.type.equals($$1.type) && this.id.equals($$1.id) && this.name.equals($$1.name));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 139 */       int $$0 = this.type.hashCode();
/* 140 */       $$0 = 31 * $$0 + this.id.hashCode();
/* 141 */       $$0 = 31 * $$0 + this.name.hashCode();
/* 142 */       return $$0;
/*     */     } }
/*     */   public static class ItemStackInfo { public static final Codec<ItemStackInfo> FULL_CODEC; public static final Codec<ItemStackInfo> CODEC; private final Item item; private final int count; private final Optional<CompoundTag> tag; @Nullable
/*     */     private ItemStack itemStack;
/*     */     
/*     */     static {
/* 148 */       FULL_CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ITEM.byNameCodec().fieldOf("id").forGetter(()), (App)ExtraCodecs.strictOptionalField((Codec)Codec.INT, "count", Integer.valueOf(1)).forGetter(()), (App)ExtraCodecs.strictOptionalField(TagParser.AS_CODEC, "tag").forGetter(())).apply((Applicative)$$0, ItemStackInfo::new));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       CODEC = Codec.either(BuiltInRegistries.ITEM.byNameCodec(), FULL_CODEC).xmap($$0 -> (ItemStackInfo)$$0.map((), ()), Either::right);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ItemStackInfo(Item $$0, int $$1, @Nullable CompoundTag $$2) {
/* 170 */       this($$0, $$1, Optional.ofNullable($$2));
/*     */     }
/*     */     
/*     */     ItemStackInfo(Item $$0, int $$1, Optional<CompoundTag> $$2) {
/* 174 */       this.item = $$0;
/* 175 */       this.count = $$1;
/* 176 */       this.tag = $$2;
/*     */     }
/*     */     
/*     */     public ItemStackInfo(ItemStack $$0) {
/* 180 */       this($$0.getItem(), $$0.getCount(), ($$0.getTag() != null) ? Optional.<CompoundTag>of($$0.getTag().copy()) : Optional.<CompoundTag>empty());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object $$0) {
/* 185 */       if (this == $$0) {
/* 186 */         return true;
/*     */       }
/* 188 */       if ($$0 == null || getClass() != $$0.getClass()) {
/* 189 */         return false;
/*     */       }
/*     */       
/* 192 */       ItemStackInfo $$1 = (ItemStackInfo)$$0;
/* 193 */       return (this.count == $$1.count && this.item.equals($$1.item) && this.tag.equals($$1.tag));
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 198 */       int $$0 = this.item.hashCode();
/* 199 */       $$0 = 31 * $$0 + this.count;
/* 200 */       $$0 = 31 * $$0 + this.tag.hashCode();
/* 201 */       return $$0;
/*     */     }
/*     */     
/*     */     public ItemStack getItemStack() {
/* 205 */       if (this.itemStack == null) {
/* 206 */         this.itemStack = new ItemStack((ItemLike)this.item, this.count);
/* 207 */         Objects.requireNonNull(this.itemStack); this.tag.ifPresent(this.itemStack::setTag);
/*     */       } 
/* 209 */       return this.itemStack;
/*     */     }
/*     */     
/*     */     private static DataResult<ItemStackInfo> legacyCreate(Component $$0) {
/*     */       try {
/* 214 */         CompoundTag $$1 = TagParser.parseTag($$0.getString());
/* 215 */         return DataResult.success(new ItemStackInfo(ItemStack.of($$1)));
/* 216 */       } catch (CommandSyntaxException $$2) {
/* 217 */         return DataResult.error(() -> "Failed to parse item tag: " + $$0.getMessage());
/*     */       } 
/*     */     } }
/*     */ 
/*     */   
/*     */   public static class Action<T> implements StringRepresentable {
/* 223 */     public static final Action<Component> SHOW_TEXT = new Action("show_text", true, (Codec)ComponentSerialization.CODEC, DataResult::success);
/*     */     
/* 225 */     public static final Action<HoverEvent.ItemStackInfo> SHOW_ITEM = new Action("show_item", true, (Codec)HoverEvent.ItemStackInfo.CODEC, HoverEvent.ItemStackInfo::legacyCreate);
/*     */     
/* 227 */     public static final Action<HoverEvent.EntityTooltipInfo> SHOW_ENTITY = new Action("show_entity", true, (Codec)HoverEvent.EntityTooltipInfo.CODEC, HoverEvent.EntityTooltipInfo::legacyCreate);
/*     */ 
/*     */     
/* 230 */     public static final Codec<Action<?>> UNSAFE_CODEC = StringRepresentable.fromValues(() -> new Action[] { SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY });
/* 231 */     public static final Codec<Action<?>> CODEC = ExtraCodecs.validate(UNSAFE_CODEC, Action::filterForSerialization);
/*     */     
/*     */     private final String name;
/*     */     private final boolean allowFromServer;
/*     */     final Codec<HoverEvent.TypedHoverEvent<T>> codec;
/*     */     final Codec<HoverEvent.TypedHoverEvent<T>> legacyCodec;
/*     */     
/*     */     public Action(String $$0, boolean $$1, Codec<T> $$2, Function<Component, DataResult<T>> $$3) {
/* 239 */       this.name = $$0;
/* 240 */       this.allowFromServer = $$1;
/* 241 */       this.codec = $$2.xmap($$0 -> new HoverEvent.TypedHoverEvent<>(this, (T)$$0), $$0 -> $$0.value).fieldOf("contents").codec();
/* 242 */       this.legacyCodec = Codec.of(
/* 243 */           Encoder.error("Can't encode in legacy format"), ComponentSerialization.CODEC
/* 244 */           .flatMap($$3).map($$0 -> new HoverEvent.TypedHoverEvent<>(this, (T)$$0)));
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAllowedFromServer() {
/* 249 */       return this.allowFromServer;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getSerializedName() {
/* 254 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     T cast(Object $$0) {
/* 259 */       return (T)$$0;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 264 */       return "<action " + this.name + ">";
/*     */     }
/*     */     
/*     */     private static DataResult<Action<?>> filterForSerialization(@Nullable Action<?> $$0) {
/* 268 */       if ($$0 == null) {
/* 269 */         return DataResult.error(() -> "Unknown action");
/*     */       }
/* 271 */       if (!$$0.isAllowedFromServer()) {
/* 272 */         return DataResult.error(() -> "Action not allowed: " + $$0);
/*     */       }
/* 274 */       return DataResult.success($$0, Lifecycle.stable());
/*     */     } }
/*     */   private static final class TypedHoverEvent<T> extends Record { final HoverEvent.Action<T> action; final T value; public static final MapCodec<TypedHoverEvent<?>> CODEC; public static final MapCodec<TypedHoverEvent<?>> LEGACY_CODEC;
/*     */     
/* 278 */     TypedHoverEvent(HoverEvent.Action<T> $$0, T $$1) { this.action = $$0; this.value = $$1; } public final String toString() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> toString : (Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;)Ljava/lang/String;
/*     */       //   6: areturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #278	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 278 */       //   0	7	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent<TT;>; } public HoverEvent.Action<T> action() { return this.action; } public final int hashCode() { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;)I
/*     */       //   6: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #278	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   0	7	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent<TT;>; } public final boolean equals(Object $$0) { // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: aload_1
/*     */       //   2: <illegal opcode> equals : (Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;Ljava/lang/Object;)Z
/*     */       //   7: ireturn
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #278	-> 0
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	8	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent;
/*     */       //   0	8	1	$$0	Ljava/lang/Object;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/* 278 */       //   0	8	0	this	Lnet/minecraft/network/chat/HoverEvent$TypedHoverEvent<TT;>; } public T value() { return this.value; }
/*     */      static {
/* 280 */       CODEC = HoverEvent.Action.CODEC.dispatchMap("action", TypedHoverEvent::action, $$0 -> $$0.codec);
/* 281 */       LEGACY_CODEC = HoverEvent.Action.CODEC.dispatchMap("action", TypedHoverEvent::action, $$0 -> $$0.legacyCodec);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\HoverEvent.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */