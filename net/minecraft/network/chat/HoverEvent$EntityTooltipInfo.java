/*     */ package net.minecraft.network.chat;
/*     */ 
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.datafixers.kinds.Applicative;
/*     */ import com.mojang.datafixers.util.Function3;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.DataResult;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.core.UUIDUtil;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.nbt.CompoundTag;
/*     */ import net.minecraft.nbt.TagParser;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.entity.EntityType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityTooltipInfo
/*     */ {
/*     */   public static final Codec<EntityTooltipInfo> CODEC;
/*     */   public final EntityType<?> type;
/*     */   public final UUID id;
/*     */   public final Optional<Component> name;
/*     */   @Nullable
/*     */   private List<Component> linesCache;
/*     */   
/*     */   static {
/*  79 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(()), (App)UUIDUtil.LENIENT_CODEC.fieldOf("id").forGetter(()), (App)ExtraCodecs.strictOptionalField(ComponentSerialization.CODEC, "name").forGetter(())).apply((Applicative)$$0, EntityTooltipInfo::new));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityTooltipInfo(EntityType<?> $$0, UUID $$1, @Nullable Component $$2) {
/*  93 */     this($$0, $$1, Optional.ofNullable($$2));
/*     */   }
/*     */   
/*     */   public EntityTooltipInfo(EntityType<?> $$0, UUID $$1, Optional<Component> $$2) {
/*  97 */     this.type = $$0;
/*  98 */     this.id = $$1;
/*  99 */     this.name = $$2;
/*     */   }
/*     */   
/*     */   public static DataResult<EntityTooltipInfo> legacyCreate(Component $$0) {
/*     */     try {
/* 104 */       CompoundTag $$1 = TagParser.parseTag($$0.getString());
/* 105 */       Component $$2 = Component.Serializer.fromJson($$1.getString("name"));
/* 106 */       EntityType<?> $$3 = (EntityType)BuiltInRegistries.ENTITY_TYPE.get(new ResourceLocation($$1.getString("type")));
/* 107 */       UUID $$4 = UUID.fromString($$1.getString("id"));
/* 108 */       return DataResult.success(new EntityTooltipInfo($$3, $$4, $$2));
/* 109 */     } catch (Exception $$5) {
/* 110 */       return DataResult.error(() -> "Failed to parse tooltip: " + $$0.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<Component> getTooltipLines() {
/* 115 */     if (this.linesCache == null) {
/* 116 */       this.linesCache = new ArrayList<>();
/* 117 */       Objects.requireNonNull(this.linesCache); this.name.ifPresent(this.linesCache::add);
/* 118 */       this.linesCache.add(Component.translatable("gui.entity_tooltip.type", new Object[] { this.type.getDescription() }));
/* 119 */       this.linesCache.add(Component.literal(this.id.toString()));
/*     */     } 
/* 121 */     return this.linesCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object $$0) {
/* 126 */     if (this == $$0) {
/* 127 */       return true;
/*     */     }
/* 129 */     if ($$0 == null || getClass() != $$0.getClass()) {
/* 130 */       return false;
/*     */     }
/*     */     
/* 133 */     EntityTooltipInfo $$1 = (EntityTooltipInfo)$$0;
/* 134 */     return (this.type.equals($$1.type) && this.id.equals($$1.id) && this.name.equals($$1.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 139 */     int $$0 = this.type.hashCode();
/* 140 */     $$0 = 31 * $$0 + this.id.hashCode();
/* 141 */     $$0 = 31 * $$0 + this.name.hashCode();
/* 142 */     return $$0;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\network\chat\HoverEvent$EntityTooltipInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */