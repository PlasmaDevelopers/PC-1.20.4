/*    */ package net.minecraft.world.level.block.state.properties;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ import java.util.function.Predicate;
/*    */ import java.util.stream.Collectors;
/*    */ import net.minecraft.util.StringRepresentable;
/*    */ 
/*    */ public class EnumProperty<T extends Enum<T> & StringRepresentable>
/*    */   extends Property<T> {
/*    */   private final ImmutableSet<T> values;
/* 17 */   private final Map<String, T> names = Maps.newHashMap();
/*    */   
/*    */   protected EnumProperty(String $$0, Class<T> $$1, Collection<T> $$2) {
/* 20 */     super($$0, $$1);
/* 21 */     this.values = ImmutableSet.copyOf($$2);
/*    */     
/* 23 */     for (Enum enum_ : $$2) {
/* 24 */       String $$4 = ((StringRepresentable)enum_).getSerializedName();
/* 25 */       if (this.names.containsKey($$4)) {
/* 26 */         throw new IllegalArgumentException("Multiple values have the same name '" + $$4 + "'");
/*    */       }
/* 28 */       this.names.put($$4, (T)enum_);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<T> getPossibleValues() {
/* 34 */     return (Collection<T>)this.values;
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<T> getValue(String $$0) {
/* 39 */     return Optional.ofNullable(this.names.get($$0));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName(T $$0) {
/* 44 */     return ((StringRepresentable)$$0).getSerializedName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object $$0) {
/* 49 */     if (this == $$0) {
/* 50 */       return true;
/*    */     }
/*    */     
/* 53 */     if ($$0 instanceof EnumProperty) { EnumProperty<?> $$1 = (EnumProperty)$$0; if (super.equals($$0)) {
/* 54 */         return (this.values.equals($$1.values) && this.names.equals($$1.names));
/*    */       } }
/*    */     
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int generateHashCode() {
/* 62 */     int $$0 = super.generateHashCode();
/* 63 */     $$0 = 31 * $$0 + this.values.hashCode();
/* 64 */     $$0 = 31 * $$0 + this.names.hashCode();
/* 65 */     return $$0;
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1) {
/* 69 */     return create($$0, $$1, $$0 -> true);
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1, Predicate<T> $$2) {
/* 73 */     return create($$0, $$1, (Collection<T>)Arrays.<T>stream($$1.getEnumConstants()).filter($$2).collect(Collectors.toList()));
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1, T... $$2) {
/* 77 */     return create($$0, $$1, Lists.newArrayList((Object[])$$2));
/*    */   }
/*    */   
/*    */   public static <T extends Enum<T> & StringRepresentable> EnumProperty<T> create(String $$0, Class<T> $$1, Collection<T> $$2) {
/* 81 */     return new EnumProperty<>($$0, $$1, $$2);
/*    */   }
/*    */ }


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\net\minecraft\world\level\block\state\properties\EnumProperty.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */