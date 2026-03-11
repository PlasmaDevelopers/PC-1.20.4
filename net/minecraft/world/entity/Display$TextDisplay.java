/*      */ package net.minecraft.world.entity;
/*      */ 
/*      */ import com.mojang.datafixers.util.Pair;
/*      */ import com.mojang.serialization.Codec;
/*      */ import com.mojang.serialization.DynamicOps;
/*      */ import it.unimi.dsi.fastutil.ints.IntSet;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.Util;
/*      */ import net.minecraft.commands.CommandSourceStack;
/*      */ import net.minecraft.nbt.CompoundTag;
/*      */ import net.minecraft.nbt.NbtOps;
/*      */ import net.minecraft.nbt.Tag;
/*      */ import net.minecraft.network.chat.Component;
/*      */ import net.minecraft.network.chat.ComponentUtils;
/*      */ import net.minecraft.network.chat.MutableComponent;
/*      */ import net.minecraft.network.syncher.EntityDataAccessor;
/*      */ import net.minecraft.network.syncher.EntityDataSerializers;
/*      */ import net.minecraft.network.syncher.SynchedEntityData;
/*      */ import net.minecraft.util.FormattedCharSequence;
/*      */ import net.minecraft.util.StringRepresentable;
/*      */ import net.minecraft.world.level.Level;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class TextDisplay
/*      */   extends Display
/*      */ {
/*      */   public static final String TAG_TEXT = "text";
/*      */   private static final String TAG_LINE_WIDTH = "line_width";
/*      */   private static final String TAG_TEXT_OPACITY = "text_opacity";
/*      */   private static final String TAG_BACKGROUND_COLOR = "background";
/*      */   private static final String TAG_SHADOW = "shadow";
/*      */   private static final String TAG_SEE_THROUGH = "see_through";
/*      */   private static final String TAG_USE_DEFAULT_BACKGROUND = "default_background";
/*      */   private static final String TAG_ALIGNMENT = "alignment";
/*      */   public static final byte FLAG_SHADOW = 1;
/*      */   public static final byte FLAG_SEE_THROUGH = 2;
/*      */   public static final byte FLAG_USE_DEFAULT_BACKGROUND = 4;
/*      */   public static final byte FLAG_ALIGN_LEFT = 8;
/*      */   public static final byte FLAG_ALIGN_RIGHT = 16;
/*      */   private static final byte INITIAL_TEXT_OPACITY = -1;
/*      */   public static final int INITIAL_BACKGROUND = 1073741824;
/*      */   
/*      */   public enum Align
/*      */     implements StringRepresentable
/*      */   {
/*  740 */     CENTER("center"),
/*  741 */     LEFT("left"),
/*  742 */     RIGHT("right");
/*      */     
/*  744 */     public static final Codec<Align> CODEC = (Codec<Align>)StringRepresentable.fromEnum(Align::values); private final String name;
/*      */     static {
/*      */     
/*      */     }
/*      */     Align(String $$0) {
/*  749 */       this.name = $$0;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getSerializedName() {
/*  754 */       return this.name;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  776 */   private static final EntityDataAccessor<Component> DATA_TEXT_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.COMPONENT);
/*  777 */   private static final EntityDataAccessor<Integer> DATA_LINE_WIDTH_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
/*  778 */   private static final EntityDataAccessor<Integer> DATA_BACKGROUND_COLOR_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.INT);
/*  779 */   private static final EntityDataAccessor<Byte> DATA_TEXT_OPACITY_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
/*  780 */   private static final EntityDataAccessor<Byte> DATA_STYLE_FLAGS_ID = SynchedEntityData.defineId(TextDisplay.class, EntityDataSerializers.BYTE);
/*      */   
/*  782 */   private static final IntSet TEXT_RENDER_STATE_IDS = IntSet.of(new int[] { DATA_TEXT_ID
/*  783 */         .getId(), DATA_LINE_WIDTH_ID
/*  784 */         .getId(), DATA_BACKGROUND_COLOR_ID
/*  785 */         .getId(), DATA_TEXT_OPACITY_ID
/*  786 */         .getId(), DATA_STYLE_FLAGS_ID
/*  787 */         .getId() });
/*      */   
/*      */   @Nullable
/*      */   private CachedInfo clientDisplayCache;
/*      */   
/*      */   @Nullable
/*      */   private TextRenderState textRenderState;
/*      */ 
/*      */   
/*      */   public TextDisplay(EntityType<?> $$0, Level $$1) {
/*  797 */     super($$0, $$1);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void defineSynchedData() {
/*  802 */     super.defineSynchedData();
/*  803 */     this.entityData.define(DATA_TEXT_ID, Component.empty());
/*  804 */     this.entityData.define(DATA_LINE_WIDTH_ID, Integer.valueOf(200));
/*  805 */     this.entityData.define(DATA_BACKGROUND_COLOR_ID, Integer.valueOf(1073741824));
/*  806 */     this.entityData.define(DATA_TEXT_OPACITY_ID, Byte.valueOf((byte)-1));
/*  807 */     this.entityData.define(DATA_STYLE_FLAGS_ID, Byte.valueOf((byte)0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onSyncedDataUpdated(EntityDataAccessor<?> $$0) {
/*  812 */     super.onSyncedDataUpdated($$0);
/*      */     
/*  814 */     if (TEXT_RENDER_STATE_IDS.contains($$0.getId())) {
/*  815 */       this.updateRenderState = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private Component getText() {
/*  820 */     return (Component)this.entityData.get(DATA_TEXT_ID);
/*      */   }
/*      */   
/*      */   private void setText(Component $$0) {
/*  824 */     this.entityData.set(DATA_TEXT_ID, $$0);
/*      */   }
/*      */   
/*      */   private int getLineWidth() {
/*  828 */     return ((Integer)this.entityData.get(DATA_LINE_WIDTH_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setLineWidth(int $$0) {
/*  832 */     this.entityData.set(DATA_LINE_WIDTH_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   private byte getTextOpacity() {
/*  836 */     return ((Byte)this.entityData.get(DATA_TEXT_OPACITY_ID)).byteValue();
/*      */   }
/*      */   
/*      */   private void setTextOpacity(byte $$0) {
/*  840 */     this.entityData.set(DATA_TEXT_OPACITY_ID, Byte.valueOf($$0));
/*      */   }
/*      */   
/*      */   private int getBackgroundColor() {
/*  844 */     return ((Integer)this.entityData.get(DATA_BACKGROUND_COLOR_ID)).intValue();
/*      */   }
/*      */   
/*      */   private void setBackgroundColor(int $$0) {
/*  848 */     this.entityData.set(DATA_BACKGROUND_COLOR_ID, Integer.valueOf($$0));
/*      */   }
/*      */   
/*      */   private byte getFlags() {
/*  852 */     return ((Byte)this.entityData.get(DATA_STYLE_FLAGS_ID)).byteValue();
/*      */   }
/*      */   
/*      */   private void setFlags(byte $$0) {
/*  856 */     this.entityData.set(DATA_STYLE_FLAGS_ID, Byte.valueOf($$0));
/*      */   }
/*      */   
/*      */   private static byte loadFlag(byte $$0, CompoundTag $$1, String $$2, byte $$3) {
/*  860 */     if ($$1.getBoolean($$2)) {
/*  861 */       return (byte)($$0 | $$3);
/*      */     }
/*  863 */     return $$0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void readAdditionalSaveData(CompoundTag $$0) {
/*  868 */     super.readAdditionalSaveData($$0);
/*      */     
/*  870 */     if ($$0.contains("line_width", 99)) {
/*  871 */       setLineWidth($$0.getInt("line_width"));
/*      */     }
/*      */     
/*  874 */     if ($$0.contains("text_opacity", 99)) {
/*  875 */       setTextOpacity($$0.getByte("text_opacity"));
/*      */     }
/*      */     
/*  878 */     if ($$0.contains("background", 99)) {
/*  879 */       setBackgroundColor($$0.getInt("background"));
/*      */     }
/*      */ 
/*      */     
/*  883 */     byte $$1 = loadFlag((byte)0, $$0, "shadow", (byte)1);
/*  884 */     $$1 = loadFlag($$1, $$0, "see_through", (byte)2);
/*  885 */     $$1 = loadFlag($$1, $$0, "default_background", (byte)4);
/*      */     
/*  887 */     Objects.requireNonNull(Display.LOGGER); Optional<Align> $$2 = Align.CODEC.decode((DynamicOps)NbtOps.INSTANCE, $$0.get("alignment")).resultOrPartial(Util.prefix("Display entity", Display.LOGGER::error)).map(Pair::getFirst);
/*  888 */     if ($$2.isPresent()) {
/*  889 */       switch (Display.null.$SwitchMap$net$minecraft$world$entity$Display$TextDisplay$Align[((Align)$$2.get()).ordinal()]) { default: throw new IncompatibleClassChangeError();
/*      */         case 1: 
/*      */         case 2: 
/*  892 */         case 3: break; }  $$1 = (byte)($$1 | 0x10);
/*      */     } 
/*      */ 
/*      */     
/*  896 */     setFlags($$1);
/*      */     
/*  898 */     if ($$0.contains("text", 8)) {
/*  899 */       String $$3 = $$0.getString("text");
/*      */       try {
/*  901 */         MutableComponent mutableComponent = Component.Serializer.fromJson($$3);
/*  902 */         if (mutableComponent != null) {
/*  903 */           CommandSourceStack $$5 = createCommandSourceStack().withPermission(2);
/*  904 */           MutableComponent mutableComponent1 = ComponentUtils.updateForEntity($$5, (Component)mutableComponent, this, 0);
/*  905 */           setText((Component)mutableComponent1);
/*      */         } else {
/*  907 */           setText((Component)Component.empty());
/*      */         } 
/*  909 */       } catch (Exception $$7) {
/*  910 */         Display.LOGGER.warn("Failed to parse display entity text {}", $$3, $$7);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void storeFlag(byte $$0, CompoundTag $$1, String $$2, byte $$3) {
/*  916 */     $$1.putBoolean($$2, (($$0 & $$3) != 0));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addAdditionalSaveData(CompoundTag $$0) {
/*  921 */     super.addAdditionalSaveData($$0);
/*  922 */     $$0.putString("text", Component.Serializer.toJson(getText()));
/*  923 */     $$0.putInt("line_width", getLineWidth());
/*  924 */     $$0.putInt("background", getBackgroundColor());
/*  925 */     $$0.putByte("text_opacity", getTextOpacity());
/*      */     
/*  927 */     byte $$1 = getFlags();
/*  928 */     storeFlag($$1, $$0, "shadow", (byte)1);
/*  929 */     storeFlag($$1, $$0, "see_through", (byte)2);
/*  930 */     storeFlag($$1, $$0, "default_background", (byte)4);
/*  931 */     Align.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, getAlign($$1)).result().ifPresent($$1 -> $$0.put("alignment", $$1));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateRenderSubState(boolean $$0, float $$1) {
/*  936 */     if ($$0 && this.textRenderState != null) {
/*  937 */       this.textRenderState = createInterpolatedTextRenderState(this.textRenderState, $$1);
/*      */     } else {
/*  939 */       this.textRenderState = createFreshTextRenderState();
/*      */     } 
/*  941 */     this.clientDisplayCache = null;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public TextRenderState textRenderState() {
/*  946 */     return this.textRenderState;
/*      */   }
/*      */   
/*      */   private TextRenderState createFreshTextRenderState() {
/*  950 */     return new TextRenderState(
/*  951 */         getText(), 
/*  952 */         getLineWidth(), 
/*  953 */         Display.IntInterpolator.constant(getTextOpacity()), 
/*  954 */         Display.IntInterpolator.constant(getBackgroundColor()), 
/*  955 */         getFlags());
/*      */   }
/*      */ 
/*      */   
/*      */   private TextRenderState createInterpolatedTextRenderState(TextRenderState $$0, float $$1) {
/*  960 */     int $$2 = $$0.backgroundColor.get($$1);
/*  961 */     int $$3 = $$0.textOpacity.get($$1);
/*      */     
/*  963 */     return new TextRenderState(
/*  964 */         getText(), 
/*  965 */         getLineWidth(), new Display.LinearIntInterpolator($$3, 
/*  966 */           getTextOpacity()), new Display.ColorInterpolator($$2, 
/*  967 */           getBackgroundColor()), 
/*  968 */         getFlags());
/*      */   }
/*      */   public static final class CachedLine extends Record { private final FormattedCharSequence contents; private final int width;
/*      */     
/*  972 */     public CachedLine(FormattedCharSequence $$0, int $$1) { this.contents = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*  972 */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public FormattedCharSequence contents() { return this.contents; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #972	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedLine;
/*  972 */       //   0	8	1	$$0	Ljava/lang/Object; } public int width() { return this.width; }
/*      */      } public static final class CachedInfo extends Record { private final List<Display.TextDisplay.CachedLine> lines; private final int width;
/*  974 */     public CachedInfo(List<Display.TextDisplay.CachedLine> $$0, int $$1) { this.lines = $$0; this.width = $$1; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #974	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;
/*  974 */       //   0	8	1	$$0	Ljava/lang/Object; } public List<Display.TextDisplay.CachedLine> lines() { return this.lines; } public int width() { return this.width; }
/*      */      }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CachedInfo cacheDisplay(LineSplitter $$0) {
/*  982 */     if (this.clientDisplayCache == null) {
/*  983 */       if (this.textRenderState != null) {
/*  984 */         this.clientDisplayCache = $$0.split(this.textRenderState.text(), this.textRenderState.lineWidth());
/*      */       } else {
/*  986 */         this.clientDisplayCache = new CachedInfo(List.of(), 0);
/*      */       } 
/*      */     }
/*      */     
/*  990 */     return this.clientDisplayCache;
/*      */   }
/*      */   
/*      */   public static Align getAlign(byte $$0) {
/*  994 */     if (($$0 & 0x8) != 0) {
/*  995 */       return Align.LEFT;
/*      */     }
/*  997 */     if (($$0 & 0x10) != 0) {
/*  998 */       return Align.RIGHT;
/*      */     }
/* 1000 */     return Align.CENTER;
/*      */   }
/*      */   public static final class TextRenderState extends Record { private final Component text; private final int lineWidth; final Display.IntInterpolator textOpacity; final Display.IntInterpolator backgroundColor; private final byte flags;
/* 1003 */     public TextRenderState(Component $$0, int $$1, Display.IntInterpolator $$2, Display.IntInterpolator $$3, byte $$4) { this.text = $$0; this.lineWidth = $$1; this.textOpacity = $$2; this.backgroundColor = $$3; this.flags = $$4; } public final String toString() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> toString : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)Ljava/lang/String;
/*      */       //   6: areturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final int hashCode() { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: <illegal opcode> hashCode : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;)I
/*      */       //   6: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	7	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState; } public final boolean equals(Object $$0) { // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: aload_1
/*      */       //   2: <illegal opcode> equals : (Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;Ljava/lang/Object;)Z
/*      */       //   7: ireturn
/*      */       // Line number table:
/*      */       //   Java source line number -> byte code offset
/*      */       //   #1003	-> 0
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	descriptor
/*      */       //   0	8	0	this	Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;
/* 1003 */       //   0	8	1	$$0	Ljava/lang/Object; } public Component text() { return this.text; } public int lineWidth() { return this.lineWidth; } public Display.IntInterpolator textOpacity() { return this.textOpacity; } public Display.IntInterpolator backgroundColor() { return this.backgroundColor; } public byte flags() { return this.flags; }
/*      */      }
/*      */ 
/*      */   
/*      */   @FunctionalInterface
/*      */   public static interface LineSplitter {
/*      */     Display.TextDisplay.CachedInfo split(Component param2Component, int param2Int);
/*      */   }
/*      */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\entity\Display$TextDisplay.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */