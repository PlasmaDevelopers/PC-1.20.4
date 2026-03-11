package com.mojang.blaze3d;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;

@Nonnull
@TypeQualifierDefault({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsAreNonnullByDefault {}


/* Location:              C:\Users\Xiao\Downloads\output\client_deobfuscated.jar!\com\mojang\blaze3d\FieldsAreNonnullByDefault.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */