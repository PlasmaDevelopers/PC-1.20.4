/*     */ package net.minecraft.advancements;
/*     */ import com.mojang.datafixers.kinds.App;
/*     */ import com.mojang.serialization.Codec;
/*     */ import com.mojang.serialization.codecs.RecordCodecBuilder;
/*     */ import java.util.Optional;
/*     */ import net.minecraft.network.FriendlyByteBuf;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentSerialization;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.util.ExtraCodecs;
/*     */ import net.minecraft.world.item.ItemStack;
/*     */ 
/*     */ public class DisplayInfo {
/*     */   static {
/*  15 */     CODEC = RecordCodecBuilder.create($$0 -> $$0.group((App)ItemStack.ADVANCEMENT_ICON_CODEC.fieldOf("icon").forGetter(DisplayInfo::getIcon), (App)ComponentSerialization.CODEC.fieldOf("title").forGetter(DisplayInfo::getTitle), (App)ComponentSerialization.CODEC.fieldOf("description").forGetter(DisplayInfo::getDescription), (App)ExtraCodecs.strictOptionalField(ResourceLocation.CODEC, "background").forGetter(DisplayInfo::getBackground), (App)ExtraCodecs.strictOptionalField(AdvancementType.CODEC, "frame", AdvancementType.TASK).forGetter(DisplayInfo::getType), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "show_toast", Boolean.valueOf(true)).forGetter(DisplayInfo::shouldShowToast), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "announce_to_chat", Boolean.valueOf(true)).forGetter(DisplayInfo::shouldAnnounceChat), (App)ExtraCodecs.strictOptionalField((Codec)Codec.BOOL, "hidden", Boolean.valueOf(false)).forGetter(DisplayInfo::isHidden)).apply((Applicative)$$0, DisplayInfo::new));
/*     */   }
/*     */ 
/*     */   
/*     */   public static final Codec<DisplayInfo> CODEC;
/*     */   
/*     */   private final Component title;
/*     */   
/*     */   private final Component description;
/*     */   
/*     */   private final ItemStack icon;
/*     */   
/*     */   private final Optional<ResourceLocation> background;
/*     */   
/*     */   private final AdvancementType type;
/*     */   
/*     */   private final boolean showToast;
/*     */   private final boolean announceChat;
/*     */   private final boolean hidden;
/*     */   private float x;
/*     */   private float y;
/*     */   
/*     */   public DisplayInfo(ItemStack $$0, Component $$1, Component $$2, Optional<ResourceLocation> $$3, AdvancementType $$4, boolean $$5, boolean $$6, boolean $$7) {
/*  38 */     this.title = $$1;
/*  39 */     this.description = $$2;
/*  40 */     this.icon = $$0;
/*  41 */     this.background = $$3;
/*  42 */     this.type = $$4;
/*  43 */     this.showToast = $$5;
/*  44 */     this.announceChat = $$6;
/*  45 */     this.hidden = $$7;
/*     */   }
/*     */   
/*     */   public void setLocation(float $$0, float $$1) {
/*  49 */     this.x = $$0;
/*  50 */     this.y = $$1;
/*     */   }
/*     */   
/*     */   public Component getTitle() {
/*  54 */     return this.title;
/*     */   }
/*     */   
/*     */   public Component getDescription() {
/*  58 */     return this.description;
/*     */   }
/*     */   
/*     */   public ItemStack getIcon() {
/*  62 */     return this.icon;
/*     */   }
/*     */   
/*     */   public Optional<ResourceLocation> getBackground() {
/*  66 */     return this.background;
/*     */   }
/*     */   
/*     */   public AdvancementType getType() {
/*  70 */     return this.type;
/*     */   }
/*     */   
/*     */   public float getX() {
/*  74 */     return this.x;
/*     */   }
/*     */   
/*     */   public float getY() {
/*  78 */     return this.y;
/*     */   }
/*     */   
/*     */   public boolean shouldShowToast() {
/*  82 */     return this.showToast;
/*     */   }
/*     */   
/*     */   public boolean shouldAnnounceChat() {
/*  86 */     return this.announceChat;
/*     */   }
/*     */   
/*     */   public boolean isHidden() {
/*  90 */     return this.hidden;
/*     */   }
/*     */   
/*     */   public void serializeToNetwork(FriendlyByteBuf $$0) {
/*  94 */     $$0.writeComponent(this.title);
/*  95 */     $$0.writeComponent(this.description);
/*  96 */     $$0.writeItem(this.icon);
/*  97 */     $$0.writeEnum(this.type);
/*  98 */     int $$1 = 0;
/*  99 */     if (this.background.isPresent()) {
/* 100 */       $$1 |= 0x1;
/*     */     }
/* 102 */     if (this.showToast) {
/* 103 */       $$1 |= 0x2;
/*     */     }
/* 105 */     if (this.hidden) {
/* 106 */       $$1 |= 0x4;
/*     */     }
/* 108 */     $$0.writeInt($$1);
/* 109 */     Objects.requireNonNull($$0); this.background.ifPresent($$0::writeResourceLocation);
/* 110 */     $$0.writeFloat(this.x);
/* 111 */     $$0.writeFloat(this.y);
/*     */   }
/*     */   
/*     */   public static DisplayInfo fromNetwork(FriendlyByteBuf $$0) {
/* 115 */     Component $$1 = $$0.readComponentTrusted();
/* 116 */     Component $$2 = $$0.readComponentTrusted();
/* 117 */     ItemStack $$3 = $$0.readItem();
/* 118 */     AdvancementType $$4 = (AdvancementType)$$0.readEnum(AdvancementType.class);
/* 119 */     int $$5 = $$0.readInt();
/* 120 */     Optional<ResourceLocation> $$6 = (($$5 & 0x1) != 0) ? Optional.<ResourceLocation>of($$0.readResourceLocation()) : Optional.<ResourceLocation>empty();
/* 121 */     boolean $$7 = (($$5 & 0x2) != 0);
/* 122 */     boolean $$8 = (($$5 & 0x4) != 0);
/* 123 */     DisplayInfo $$9 = new DisplayInfo($$3, $$1, $$2, $$6, $$4, $$7, false, $$8);
/* 124 */     $$9.setLocation($$0.readFloat(), $$0.readFloat());
/* 125 */     return $$9;
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\advancements\DisplayInfo.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */