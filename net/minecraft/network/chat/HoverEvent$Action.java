/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.Encoder;
/*     */ import com.mojang.serialization.Lifecycle;
/*     */ import java.util.function.Function;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.util.StringRepresentable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Action<T>
/*     */   implements StringRepresentable
/*     */ {
/* 223 */   public static final Action<Component> SHOW_TEXT = new Action("show_text", true, (Codec)ComponentSerialization.CODEC, DataResult::success);
/*     */   
/* 225 */   public static final Action<HoverEvent.ItemStackInfo> SHOW_ITEM = new Action("show_item", true, (Codec)HoverEvent.ItemStackInfo.CODEC, HoverEvent.ItemStackInfo::legacyCreate);
/*     */   
/* 227 */   public static final Action<HoverEvent.EntityTooltipInfo> SHOW_ENTITY = new Action("show_entity", true, (Codec)HoverEvent.EntityTooltipInfo.CODEC, HoverEvent.EntityTooltipInfo::legacyCreate);
/*     */ 
/*     */   
/* 230 */   public static final Codec<Action<?>> UNSAFE_CODEC = StringRepresentable.fromValues(() -> new Action[] { SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY });
/* 231 */   public static final Codec<Action<?>> CODEC = ExtraCodecs.validate(UNSAFE_CODEC, Action::filterForSerialization);
/*     */   
/*     */   private final String name;
/*     */   private final boolean allowFromServer;
/*     */   final Codec<HoverEvent.TypedHoverEvent<T>> codec;
/*     */   final Codec<HoverEvent.TypedHoverEvent<T>> legacyCodec;
/*     */   
/*     */   public Action(String $$0, boolean $$1, Codec<T> $$2, Function<Component, DataResult<T>> $$3) {
/* 239 */     this.name = $$0;
/* 240 */     this.allowFromServer = $$1;
/* 241 */     this.codec = $$2.xmap($$0 -> new HoverEvent.TypedHoverEvent<>(this, (T)$$0), $$0 -> $$0.value).fieldOf("contents").codec();
/* 242 */     this.legacyCodec = Codec.of(
/* 243 */         Encoder.error("Can't encode in legacy format"), ComponentSerialization.CODEC
/* 244 */         .flatMap($$3).map($$0 -> new HoverEvent.TypedHoverEvent<>(this, (T)$$0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAllowedFromServer() {
/* 249 */     return this.allowFromServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSerializedName() {
/* 254 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   T cast(Object $$0) {
/* 259 */     return (T)$$0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 264 */     return "<action " + this.name + ">";
/*     */   }
/*     */   
/*     */   private static DataResult<Action<?>> filterForSerialization(@Nullable Action<?> $$0) {
/* 268 */     if ($$0 == null) {
/* 269 */       return DataResult.error(() -> "Unknown action");
/*     */     }
/* 271 */     if (!$$0.isAllowedFromServer()) {
/* 272 */       return DataResult.error(() -> "Action not allowed: " + $$0);
/*     */     }
/* 274 */     return DataResult.success($$0, Lifecycle.stable());
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\HoverEvent$Action.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */