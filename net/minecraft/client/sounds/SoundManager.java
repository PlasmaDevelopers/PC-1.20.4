/*     */ package net.minecraft.client.sounds;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.mojang.blaze3d.audio.ListenerTransform;
/*     */ import com.mojang.logging.LogUtils;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.SharedConstants;
/*     */ import net.minecraft.client.Camera;
/*     */ import net.minecraft.client.Options;
/*     */ import net.minecraft.client.resources.sounds.Sound;
/*     */ import net.minecraft.client.resources.sounds.SoundEventRegistration;
/*     */ import net.minecraft.client.resources.sounds.SoundEventRegistrationSerializer;
/*     */ import net.minecraft.client.resources.sounds.SoundInstance;
/*     */ import net.minecraft.client.resources.sounds.TickableSoundInstance;
/*     */ import net.minecraft.core.registries.BuiltInRegistries;
/*     */ import net.minecraft.network.chat.Component;
/*     */ import net.minecraft.network.chat.ComponentUtils;
/*     */ import net.minecraft.resources.ResourceLocation;
/*     */ import net.minecraft.server.packs.resources.Resource;
/*     */ import net.minecraft.server.packs.resources.ResourceManager;
/*     */ import net.minecraft.server.packs.resources.ResourceProvider;
/*     */ import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
/*     */ import net.minecraft.sounds.SoundSource;
/*     */ import net.minecraft.util.GsonHelper;
/*     */ import net.minecraft.util.RandomSource;
/*     */ import net.minecraft.util.profiling.ProfilerFiller;
/*     */ import net.minecraft.util.valueproviders.ConstantFloat;
/*     */ import net.minecraft.util.valueproviders.MultipliedFloats;
/*     */ import net.minecraft.util.valueproviders.SampledFloat;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class SoundManager extends SimplePreparableReloadListener<SoundManager.Preparations> {
/*  42 */   public static final Sound EMPTY_SOUND = new Sound("minecraft:empty", (SampledFloat)ConstantFloat.of(1.0F), (SampledFloat)ConstantFloat.of(1.0F), 1, Sound.Type.FILE, false, false, 16);
/*     */ 
/*     */ 
/*     */   
/*  46 */   public static final ResourceLocation INTENTIONALLY_EMPTY_SOUND_LOCATION = new ResourceLocation("minecraft", "intentionally_empty");
/*  47 */   public static final WeighedSoundEvents INTENTIONALLY_EMPTY_SOUND_EVENT = new WeighedSoundEvents(INTENTIONALLY_EMPTY_SOUND_LOCATION, null);
/*  48 */   public static final Sound INTENTIONALLY_EMPTY_SOUND = new Sound(INTENTIONALLY_EMPTY_SOUND_LOCATION.toString(), (SampledFloat)ConstantFloat.of(1.0F), (SampledFloat)ConstantFloat.of(1.0F), 1, Sound.Type.FILE, false, false, 16);
/*  49 */   static final Logger LOGGER = LogUtils.getLogger();
/*     */   private static final String SOUNDS_PATH = "sounds.json";
/*  51 */   private static final Gson GSON = (new GsonBuilder())
/*  52 */     .registerTypeHierarchyAdapter(Component.class, new Component.SerializerAdapter())
/*  53 */     .registerTypeAdapter(SoundEventRegistration.class, new SoundEventRegistrationSerializer())
/*  54 */     .create();
/*     */   
/*  56 */   private static final TypeToken<Map<String, SoundEventRegistration>> SOUND_EVENT_REGISTRATION_TYPE = new TypeToken<Map<String, SoundEventRegistration>>() {  }
/*  57 */   ; private final Map<ResourceLocation, WeighedSoundEvents> registry = Maps.newHashMap();
/*     */   private final SoundEngine soundEngine;
/*  59 */   private final Map<ResourceLocation, Resource> soundCache = new HashMap<>();
/*     */   
/*     */   public SoundManager(Options $$0) {
/*  62 */     this.soundEngine = new SoundEngine(this, $$0, ResourceProvider.fromMap(this.soundCache));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Preparations prepare(ResourceManager $$0, ProfilerFiller $$1) {
/*  67 */     Preparations $$2 = new Preparations();
/*     */     
/*  69 */     $$1.startTick();
/*  70 */     $$1.push("list");
/*  71 */     $$2.listResources($$0);
/*  72 */     $$1.pop();
/*     */     
/*  74 */     for (String $$3 : $$0.getNamespaces()) {
/*  75 */       $$1.push($$3);
/*     */       try {
/*  77 */         List<Resource> $$4 = $$0.getResourceStack(new ResourceLocation($$3, "sounds.json"));
/*  78 */         for (Resource $$5 : $$4) {
/*  79 */           $$1.push($$5.sourcePackId()); 
/*  80 */           try { Reader $$6 = $$5.openAsReader(); 
/*  81 */             try { $$1.push("parse");
/*  82 */               Map<String, SoundEventRegistration> $$7 = (Map<String, SoundEventRegistration>)GsonHelper.fromJson(GSON, $$6, SOUND_EVENT_REGISTRATION_TYPE);
/*  83 */               $$1.popPush("register");
/*  84 */               for (Map.Entry<String, SoundEventRegistration> $$8 : $$7.entrySet()) {
/*  85 */                 $$2.handleRegistration(new ResourceLocation($$3, $$8.getKey()), $$8.getValue());
/*     */               }
/*  87 */               $$1.pop();
/*  88 */               if ($$6 != null) $$6.close();  } catch (Throwable throwable) { if ($$6 != null) try { $$6.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (RuntimeException $$9)
/*  89 */           { LOGGER.warn("Invalid {} in resourcepack: '{}'", new Object[] { "sounds.json", $$5.sourcePackId(), $$9 }); }
/*     */           
/*  91 */           $$1.pop();
/*     */         } 
/*  93 */       } catch (IOException iOException) {}
/*     */       
/*  95 */       $$1.pop();
/*     */     } 
/*     */     
/*  98 */     $$1.endTick();
/*  99 */     return $$2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void apply(Preparations $$0, ResourceManager $$1, ProfilerFiller $$2) {
/* 104 */     $$0.apply(this.registry, this.soundCache, this.soundEngine);
/*     */ 
/*     */     
/* 107 */     if (SharedConstants.IS_RUNNING_IN_IDE) {
/* 108 */       for (ResourceLocation $$3 : this.registry.keySet()) {
/* 109 */         WeighedSoundEvents $$4 = this.registry.get($$3);
/*     */         
/* 111 */         if (!ComponentUtils.isTranslationResolvable($$4.getSubtitle()) && BuiltInRegistries.SOUND_EVENT.containsKey($$3)) {
/* 112 */           LOGGER.error("Missing subtitle {} for sound event: {}", $$4.getSubtitle(), $$3);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 118 */     if (LOGGER.isDebugEnabled()) {
/* 119 */       for (ResourceLocation $$5 : this.registry.keySet()) {
/* 120 */         if (!BuiltInRegistries.SOUND_EVENT.containsKey($$5)) {
/* 121 */           LOGGER.debug("Not having sound event for: {}", $$5);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 126 */     this.soundEngine.reload();
/*     */   }
/*     */   
/*     */   public List<String> getAvailableSoundDevices() {
/* 130 */     return this.soundEngine.getAvailableSoundDevices();
/*     */   }
/*     */   
/*     */   public ListenerTransform getListenerTransform() {
/* 134 */     return this.soundEngine.getListenerTransform();
/*     */   }
/*     */   
/*     */   protected static class Preparations {
/* 138 */     final Map<ResourceLocation, WeighedSoundEvents> registry = Maps.newHashMap();
/* 139 */     private Map<ResourceLocation, Resource> soundCache = Map.of();
/*     */     
/*     */     void listResources(ResourceManager $$0) {
/* 142 */       this.soundCache = Sound.SOUND_LISTER.listMatchingResources($$0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void handleRegistration(ResourceLocation $$0, SoundEventRegistration $$1) {
/*     */       // Byte code:
/*     */       //   0: aload_0
/*     */       //   1: getfield registry : Ljava/util/Map;
/*     */       //   4: aload_1
/*     */       //   5: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   10: checkcast net/minecraft/client/sounds/WeighedSoundEvents
/*     */       //   13: astore_3
/*     */       //   14: aload_3
/*     */       //   15: ifnonnull -> 22
/*     */       //   18: iconst_1
/*     */       //   19: goto -> 23
/*     */       //   22: iconst_0
/*     */       //   23: istore #4
/*     */       //   25: iload #4
/*     */       //   27: ifne -> 37
/*     */       //   30: aload_2
/*     */       //   31: invokevirtual isReplace : ()Z
/*     */       //   34: ifeq -> 78
/*     */       //   37: iload #4
/*     */       //   39: ifne -> 53
/*     */       //   42: getstatic net/minecraft/client/sounds/SoundManager.LOGGER : Lorg/slf4j/Logger;
/*     */       //   45: ldc 'Replaced sound event location {}'
/*     */       //   47: aload_1
/*     */       //   48: invokeinterface debug : (Ljava/lang/String;Ljava/lang/Object;)V
/*     */       //   53: new net/minecraft/client/sounds/WeighedSoundEvents
/*     */       //   56: dup
/*     */       //   57: aload_1
/*     */       //   58: aload_2
/*     */       //   59: invokevirtual getSubtitle : ()Ljava/lang/String;
/*     */       //   62: invokespecial <init> : (Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;)V
/*     */       //   65: astore_3
/*     */       //   66: aload_0
/*     */       //   67: getfield registry : Ljava/util/Map;
/*     */       //   70: aload_1
/*     */       //   71: aload_3
/*     */       //   72: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
/*     */       //   77: pop
/*     */       //   78: aload_0
/*     */       //   79: getfield soundCache : Ljava/util/Map;
/*     */       //   82: invokestatic fromMap : (Ljava/util/Map;)Lnet/minecraft/server/packs/resources/ResourceProvider;
/*     */       //   85: astore #5
/*     */       //   87: aload_2
/*     */       //   88: invokevirtual getSounds : ()Ljava/util/List;
/*     */       //   91: invokeinterface iterator : ()Ljava/util/Iterator;
/*     */       //   96: astore #6
/*     */       //   98: aload #6
/*     */       //   100: invokeinterface hasNext : ()Z
/*     */       //   105: ifeq -> 229
/*     */       //   108: aload #6
/*     */       //   110: invokeinterface next : ()Ljava/lang/Object;
/*     */       //   115: checkcast net/minecraft/client/resources/sounds/Sound
/*     */       //   118: astore #7
/*     */       //   120: aload #7
/*     */       //   122: invokevirtual getLocation : ()Lnet/minecraft/resources/ResourceLocation;
/*     */       //   125: astore #8
/*     */       //   127: getstatic net/minecraft/client/sounds/SoundManager$2.$SwitchMap$net$minecraft$client$resources$sounds$Sound$Type : [I
/*     */       //   130: aload #7
/*     */       //   132: invokevirtual getType : ()Lnet/minecraft/client/resources/sounds/Sound$Type;
/*     */       //   135: invokevirtual ordinal : ()I
/*     */       //   138: iaload
/*     */       //   139: lookupswitch default -> 202, 1 -> 164, 2 -> 185
/*     */       //   164: aload #7
/*     */       //   166: aload_1
/*     */       //   167: aload #5
/*     */       //   169: invokestatic validateSoundResource : (Lnet/minecraft/client/resources/sounds/Sound;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/server/packs/resources/ResourceProvider;)Z
/*     */       //   172: ifne -> 178
/*     */       //   175: goto -> 98
/*     */       //   178: aload #7
/*     */       //   180: astore #9
/*     */       //   182: goto -> 220
/*     */       //   185: new net/minecraft/client/sounds/SoundManager$Preparations$1
/*     */       //   188: dup
/*     */       //   189: aload_0
/*     */       //   190: aload #8
/*     */       //   192: aload #7
/*     */       //   194: invokespecial <init> : (Lnet/minecraft/client/sounds/SoundManager$Preparations;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/sounds/Sound;)V
/*     */       //   197: astore #9
/*     */       //   199: goto -> 220
/*     */       //   202: new java/lang/IllegalStateException
/*     */       //   205: dup
/*     */       //   206: aload #7
/*     */       //   208: invokevirtual getType : ()Lnet/minecraft/client/resources/sounds/Sound$Type;
/*     */       //   211: <illegal opcode> makeConcatWithConstants : (Lnet/minecraft/client/resources/sounds/Sound$Type;)Ljava/lang/String;
/*     */       //   216: invokespecial <init> : (Ljava/lang/String;)V
/*     */       //   219: athrow
/*     */       //   220: aload_3
/*     */       //   221: aload #9
/*     */       //   223: invokevirtual addSound : (Lnet/minecraft/client/sounds/Weighted;)V
/*     */       //   226: goto -> 98
/*     */       //   229: return
/*     */       // Line number table:
/*     */       //   Java source line number -> byte code offset
/*     */       //   #146	-> 0
/*     */       //   #147	-> 14
/*     */       //   #148	-> 25
/*     */       //   #149	-> 37
/*     */       //   #150	-> 42
/*     */       //   #152	-> 53
/*     */       //   #153	-> 66
/*     */       //   #156	-> 78
/*     */       //   #158	-> 87
/*     */       //   #159	-> 120
/*     */       //   #162	-> 127
/*     */       //   #164	-> 164
/*     */       //   #165	-> 175
/*     */       //   #168	-> 178
/*     */       //   #169	-> 182
/*     */       //   #171	-> 185
/*     */       //   #208	-> 199
/*     */       //   #210	-> 202
/*     */       //   #213	-> 220
/*     */       //   #214	-> 226
/*     */       //   #215	-> 229
/*     */       // Local variable table:
/*     */       //   start	length	slot	name	descriptor
/*     */       //   0	230	0	this	Lnet/minecraft/client/sounds/SoundManager$Preparations;
/*     */       //   0	230	1	$$0	Lnet/minecraft/resources/ResourceLocation;
/*     */       //   0	230	2	$$1	Lnet/minecraft/client/resources/sounds/SoundEventRegistration;
/*     */       //   14	216	3	$$2	Lnet/minecraft/client/sounds/WeighedSoundEvents;
/*     */       //   25	205	4	$$3	Z
/*     */       //   87	143	5	$$4	Lnet/minecraft/server/packs/resources/ResourceProvider;
/*     */       //   120	106	7	$$5	Lnet/minecraft/client/resources/sounds/Sound;
/*     */       //   127	99	8	$$6	Lnet/minecraft/resources/ResourceLocation;
/*     */       //   182	3	9	$$7	Lnet/minecraft/client/sounds/Weighted;
/*     */       //   199	3	9	$$8	Lnet/minecraft/client/sounds/Weighted;
/*     */       //   220	6	9	$$9	Lnet/minecraft/client/sounds/Weighted;
/*     */       // Local variable type table:
/*     */       //   start	length	slot	name	signature
/*     */       //   182	3	9	$$7	Lnet/minecraft/client/sounds/Weighted<Lnet/minecraft/client/resources/sounds/Sound;>;
/*     */       //   199	3	9	$$8	Lnet/minecraft/client/sounds/Weighted<Lnet/minecraft/client/resources/sounds/Sound;>;
/*     */       //   220	6	9	$$9	Lnet/minecraft/client/sounds/Weighted<Lnet/minecraft/client/resources/sounds/Sound;>;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void apply(Map<ResourceLocation, WeighedSoundEvents> $$0, Map<ResourceLocation, Resource> $$1, SoundEngine $$2) {
/* 218 */       $$0.clear();
/* 219 */       $$1.clear();
/*     */       
/* 221 */       $$1.putAll(this.soundCache);
/*     */       
/* 223 */       for (Map.Entry<ResourceLocation, WeighedSoundEvents> $$3 : this.registry.entrySet()) {
/* 224 */         $$0.put($$3.getKey(), $$3.getValue());
/* 225 */         ((WeighedSoundEvents)$$3.getValue()).preloadIfRequired($$2);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static boolean validateSoundResource(Sound $$0, ResourceLocation $$1, ResourceProvider $$2) {
/* 231 */     ResourceLocation $$3 = $$0.getPath();
/* 232 */     if ($$2.getResource($$3).isEmpty()) {
/* 233 */       LOGGER.warn("File {} does not exist, cannot add it to event {}", $$3, $$1);
/* 234 */       return false;
/*     */     } 
/* 236 */     return true;
/*     */   }
/*     */   class null implements Weighted<Sound> {
/*     */     public int getWeight() { WeighedSoundEvents $$0 = SoundManager.Preparations.this.registry.get(soundLocation); return ($$0 == null) ? 0 : $$0.getWeight(); }
/*     */     public Sound getSound(RandomSource $$0) { WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation); if ($$1 == null) return SoundManager.EMPTY_SOUND;  Sound $$2 = $$1.getSound($$0); return new Sound($$2.getLocation().toString(), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2.getVolume(), this.val$sound.getVolume() }, ), (SampledFloat)new MultipliedFloats(new SampledFloat[] { $$2.getPitch(), this.val$sound.getPitch() }, ), sound.getWeight(), Sound.Type.FILE, ($$2.shouldStream() || sound.shouldStream()), $$2.shouldPreload(), $$2.getAttenuationDistance()); } public void preloadIfRequired(SoundEngine $$0) { WeighedSoundEvents $$1 = SoundManager.Preparations.this.registry.get(soundLocation); if ($$1 == null)
/* 241 */         return;  $$1.preloadIfRequired($$0); } } @Nullable public WeighedSoundEvents getSoundEvent(ResourceLocation $$0) { return this.registry.get($$0); }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getAvailableSounds() {
/* 245 */     return this.registry.keySet();
/*     */   }
/*     */   
/*     */   public void queueTickingSound(TickableSoundInstance $$0) {
/* 249 */     this.soundEngine.queueTickingSound($$0);
/*     */   }
/*     */   
/*     */   public void play(SoundInstance $$0) {
/* 253 */     this.soundEngine.play($$0);
/*     */   }
/*     */   
/*     */   public void playDelayed(SoundInstance $$0, int $$1) {
/* 257 */     this.soundEngine.playDelayed($$0, $$1);
/*     */   }
/*     */   
/*     */   public void updateSource(Camera $$0) {
/* 261 */     this.soundEngine.updateSource($$0);
/*     */   }
/*     */   
/*     */   public void pause() {
/* 265 */     this.soundEngine.pause();
/*     */   }
/*     */   
/*     */   public void stop() {
/* 269 */     this.soundEngine.stopAll();
/*     */   }
/*     */   
/*     */   public void destroy() {
/* 273 */     this.soundEngine.destroy();
/*     */   }
/*     */   
/*     */   public void emergencyShutdown() {
/* 277 */     this.soundEngine.emergencyShutdown();
/*     */   }
/*     */   
/*     */   public void tick(boolean $$0) {
/* 281 */     this.soundEngine.tick($$0);
/*     */   }
/*     */   
/*     */   public void resume() {
/* 285 */     this.soundEngine.resume();
/*     */   }
/*     */   
/*     */   public void updateSourceVolume(SoundSource $$0, float $$1) {
/* 289 */     if ($$0 == SoundSource.MASTER && $$1 <= 0.0F) {
/* 290 */       stop();
/*     */     }
/*     */     
/* 293 */     this.soundEngine.updateCategoryVolume($$0, $$1);
/*     */   }
/*     */   
/*     */   public void stop(SoundInstance $$0) {
/* 297 */     this.soundEngine.stop($$0);
/*     */   }
/*     */   
/*     */   public boolean isActive(SoundInstance $$0) {
/* 301 */     return this.soundEngine.isActive($$0);
/*     */   }
/*     */   
/*     */   public void addListener(SoundEventListener $$0) {
/* 305 */     this.soundEngine.addEventListener($$0);
/*     */   }
/*     */   
/*     */   public void removeListener(SoundEventListener $$0) {
/* 309 */     this.soundEngine.removeEventListener($$0);
/*     */   }
/*     */   
/*     */   public void stop(@Nullable ResourceLocation $$0, @Nullable SoundSource $$1) {
/* 313 */     this.soundEngine.stop($$0, $$1);
/*     */   }
/*     */   
/*     */   public String getDebugString() {
/* 317 */     return this.soundEngine.getDebugString();
/*     */   }
/*     */   
/*     */   public void reload() {
/* 321 */     this.soundEngine.reload();
/*     */   }
/*     */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\client\sounds\SoundManager.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */